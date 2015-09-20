package net.cbtltd.server.util.price;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.TaxMapper;
import net.cbtltd.server.util.ReservationUtil;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Tax;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.HasPrice;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class TaxesCalculationHelper {

	public static final Logger LOG = Logger.getLogger(TaxesCalculationHelper.class.getName());
	private static Double totIncludedTaxAmount = 0.0;
	
	public TaxesCalculationHelper() {
	    
	}
	
	// Taxes comparator by type field
	public static Comparator<Tax> taxTypeOrder =  new Comparator<Tax>() {
        public int compare(Tax t1, Tax t2) {
            	return Tax.Type.getByName(t1.getType()).getValue() - Tax.Type.getByName(t1.getType()).getValue(); 
        	}
	};

	// 1. Add taxes
	// 1.1 Add included taxes
	// 1.2 Add excluded taxes
	// 1.3 Add tax on tax values
	
    /**
     * Compute all taxes, add them to price value and create quote details objects 
     * with information about taxes that was used for price calculation.
     * 
     * @param sqlSession - current session
     * @param hasPrice  - reservation
     * @param priceValue - local object which uses as price holder in price calculation process 
     * @return
     */
	public static PriceComplexValue addTaxes(SqlSession sqlSession, HasPrice hasPrice, PriceComplexValue priceValue){
		
		totIncludedTaxAmount = 0.0;
		
		if (hasPrice == null || priceValue == null){
			LOG.error("Null input parameters in TaxesCalculationUtil.addTaxes()");
			throw new ServiceException(Error.parameter_null);
		}
		try{
			
			Tax action = new Tax();		
			action.setPartyid(hasPrice.getSupplierid());
			action.setProductId(hasPrice.getProductid());
			action.setMandatoryType(Tax.MandatoryType.MandatoryTax.name());
			
			ArrayList<Tax> taxes = sqlSession.getMapper(TaxMapper.class).readbyproductid(action);
			Collections.sort(taxes, taxTypeOrder);
			
			double initialTotalQuote = priceValue.getTotQuote();
			
			for (Tax tax : taxes) {
				
				if (tax.getType().equals(Tax.Type.SalesTaxIncluded.name())){
				
					Double includedTaxAmount = getIncludedTaxAmount(tax, hasPrice, initialTotalQuote);
					totIncludedTaxAmount -= includedTaxAmount; 
				}
				
				if (tax.getType().equals(Tax.Type.SalesTaxExcluded.name())){
					
					Double excludedTaxAmount = getExcludedTaxAmount(tax, hasPrice, initialTotalQuote);
					priceValue.addTotalQuote(excludedTaxAmount); 
					priceValue.addTotalExtra(excludedTaxAmount);
				}
				
				if (tax.getType().equals(Tax.Type.SalesTaxOnTax.name())){
					
					Double taxOnTax = getTaxOnTaxAmount(tax, hasPrice, priceValue.getTotQuote());
					priceValue.addTotalQuote(taxOnTax); 
					priceValue.addTotalExtra(taxOnTax);
				}
			}
			
			ReservationUtil.addQuotedetail(hasPrice, Model.ZERO, Price.INCLUDED, Price.TAX_INCLUDED, "", 1.0, Unit.EA, totIncludedTaxAmount, null, null);
			
		} catch (Throwable ex){			
			LOG.error("Something wrong with taxes calculation. Details: " + ex.getMessage(), ex);
			throw new ServiceException(Error.tax_calculation, " Product id: " + hasPrice.getProductid());
		}
		
		
		
		return priceValue;
	}
	
	/**
	 * Calculate and return taxes value on input value
	 * 
	 * @param sqlSession
	 * @param hasPrice
	 * @param inputValue
	 * @return
	 */
	public static Double calculateTaxesOnValue(SqlSession sqlSession, HasPrice hasPrice, Double inputValue){
	    
	    Double result = 0.;
	    
	    if (hasPrice == null || inputValue == null){
		LOG.error("Null input parameters in TaxesCalculationUtil.calculateTaxesOnValue()");
		throw new ServiceException(Error.parameter_null);
	    }
	    
	    try{
		
		Tax action = new Tax();		
		action.setPartyid(hasPrice.getSupplierid());
		action.setProductId(hasPrice.getProductid());
		action.setMandatoryType(Tax.MandatoryType.MandatoryTax.name());
		
		ArrayList<Tax> taxes = sqlSession.getMapper(TaxMapper.class).readbyproductid(action);
		Collections.sort(taxes, taxTypeOrder);
		
		for (Tax tax : taxes) {
		    
		    if (tax.getType().equals(Tax.Type.SalesTaxExcluded.name())){
			Double excludedTaxAmount = getExcludedTaxAmount(tax, hasPrice, inputValue);
			result += excludedTaxAmount;
		    }
		
		    if (tax.getType().equals(Tax.Type.SalesTaxOnTax.name())){
			Double taxOnTax = getTaxOnTaxAmount(tax, hasPrice, inputValue + result);
			result += taxOnTax;
		    }
		}
		
		
    	} catch (Throwable ex){			
    		LOG.error("Something wrong with taxes calculation. Details: " + ex.getMessage(), ex);
    		throw new ServiceException(Error.tax_calculation, " Product id: " + hasPrice.getProductid());
    	}
	    
	    return result;
	}
	
	/**
	 * Get included tax amount
	 * 
	 * @param tax
	 * @param hasPrice
	 * @param priceValue
	 * @return
	 */
	private static Double getIncludedTaxAmount(Tax tax, HasPrice hasPrice, Double inputValue){
		
		Double result = tax.getTaxIncluded(inputValue);
		
		ReservationUtil.addQuotedetail(hasPrice, tax.getId(), tax.getName(), Price.TAX_INCLUDED, tax.getPartyname(), 1.0, Unit.EA, result, null, null);
		
		return result;
	}
	
	/**
	 * Get Excluded tax amount
	 * 
	 * @param tax
	 * @param hasPrice
	 * @param priceValue
	 * @return
	 */
	private static Double getExcludedTaxAmount(Tax tax, HasPrice hasPrice, Double inputValue){
		
		Double result = tax.getTaxExcluded(inputValue);
		
		ReservationUtil.addQuotedetail(hasPrice, tax.getId(), tax.getName(), Price.TAX_EXCLUDED, tax.getPartyname(), 1.0, Unit.EA, result, null, null);
		
		return result;
	}
	
	/**
	 * Get tax on tax amount
	 * 
	 * @param tax
	 * @param hasPrice
	 * @param priceValue
	 * @return
	 */
	private static Double getTaxOnTaxAmount(Tax tax,  HasPrice hasPrice, Double inputValue){
		
		Double result = tax.getTaxExcluded(inputValue);		
		
		ReservationUtil.addQuotedetail(hasPrice, tax.getId(), tax.getName(), Price.TAX_ON_TAX, tax.getPartyname(), 1.0, Unit.EA, result, null, null);
		
		return result;
	}
	
	/**
	 * Test method
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Initialize parameters
		
		SqlSession sqlSession = RazorServer.openSession();
		
		Reservation reservation = new Reservation();
		reservation.setQuotedetail( new ArrayList<Price>());
		reservation.setSupplierid("325806");
		reservation.setProductid("427819");
		reservation.setId("0");
		reservation.setCurrency("USD");
		
		PriceComplexValue priceValue = new PriceComplexValue();
		priceValue.setTotQuote(100.0);
		
		priceValue = addTaxes(sqlSession, reservation, priceValue);
		
		
	}
	
}
