package net.cbtltd.server.util.price;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.FeeMapper;
import net.cbtltd.server.util.ReservationUtil;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Fee;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Tax;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.api.HasPrice;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class FeeCalculationHelper {

	public static final Logger LOG = Logger.getLogger(FeeCalculationHelper.class.getName());
	
	public FeeCalculationHelper() {
	}
	
	// Fee comparator by weight field
	public static Comparator<Fee> feeWeightOrder =  new Comparator<Fee>() {
	        public int compare(Fee f1, Fee f2) {
	            		return f1.getWeight() - f2.getWeight();
	        	}
	};

	// TODO: Add PET FEE support
	
	
	// add price features
	
	// 1. add taxable price features
	// 1.1 add taxable mandatory features
	
	// 2. add not taxable price features
	
	// 3. add special mandatory fees
	
	/**
	 * Adding mandatory taxable fees to given price.
	 * 
	 * @param sqlSession
	 * @param hasPrice
	 * @param priceValue
	 */
	public static PriceComplexValue addTaxableFees(SqlSession sqlSession, HasPrice hasPrice, PriceComplexValue priceValue ){
		
		try{
			
			addTaxableMandatoryFees(sqlSession,hasPrice,priceValue);
			
		} catch (Throwable ex){
			LOG.error("Something wrong with taxable fees calculation. Details: " + ex.getMessage(), ex);
			throw new ServiceException(Error.fee_calculation, " Product id: " + hasPrice.getProductid());
		}
		
		
		return priceValue;
	}
	
	/**
	 * Add mandatory, non special, taxable fees to given price.
	 * 
	 * @param sqlSession
	 * @param hasPrice
	 * @param priceValue
	 */
	public static void addTaxableMandatoryFees(SqlSession sqlSession, HasPrice hasPrice, PriceComplexValue priceValue ){
		
		// Searching non special taxable mandatory fees
		Fee action = new Fee();
		action.setEntityType(Fee.MANDATORY);
		action.setProductId(hasPrice.getProductid());
		action.setPartyId(hasPrice.getSupplierid());  // TODO: Check this if its needed
		action.setFromDate(hasPrice.getFromdate());
		action.setTaxType(Fee.TAXABLE);
		action.setState(Fee.CREATED);
		action.setWeight(0); // NON special

		ArrayList<Fee> fees = sqlSession.getMapper(FeeMapper.class).readbydateandproduct(action);
		if (fees != null && !fees.isEmpty()) {
		    
		    Double baseQuoteValue = priceValue.getTotQuote();
			for (Fee fee : fees) {
				
				Double feeValue = calculateFeeValueDependsOnUnit(fee, baseQuoteValue, hasPrice);
				priceValue.addTotalQuote(feeValue);
				priceValue.addTotalExtra(feeValue);
				
				ReservationUtil.addQuotedetail(hasPrice, String.valueOf(fee.getId()), fee.getName(), Fee.EntityTypeEnum.getByInt(fee.getEntityType()).getName(), 
					fee.getPartyId(), 1.0, Fee.FeeUnitEnum.getByInt(fee.getUnit()).getName(), feeValue, fee.getFromDate(), fee.getToDate());
			}
		}
		
		return;
	}
	
	/**
	 * Add optional taxable fees to given price.
	 * 
	 * @param sqlSession
	 * @param hasPrice
	 * @param priceValue
	 */
	
	public static void addTaxableOptionalFees(SqlSession sqlSession, HasPrice hasPrice, PriceComplexValue priceValue ){
		
		// Searching all taxable mandatory fees
		Fee action = new Fee();
		action.setEntityType(Fee.OPTIONAL);
		action.setProductId(hasPrice.getProductid());
		action.setPartyId(hasPrice.getSupplierid());  // TODO: Check this if its needed
		action.setFromDate(hasPrice.getFromdate());
		action.setTaxType(Fee.TAXABLE);
		action.setState(Fee.CREATED);

		ArrayList<Fee> fees = sqlSession.getMapper(FeeMapper.class).readbydateandproduct(action);
		if (fees != null && !fees.isEmpty()) {
		    Double baseQuoteValue = priceValue.getTotQuote();
		    for (Fee fee : fees) {
			
			Double feeValue = calculateFeeValueDependsOnUnit(fee, baseQuoteValue, hasPrice);
			priceValue.addTotalQuote(feeValue);
			priceValue.addTotalExtra(feeValue);
			
			ReservationUtil.addQuotedetail(hasPrice, String.valueOf(fee.getId()), fee.getName(), Fee.EntityTypeEnum.getByInt(fee.getEntityType()).getName(), 
				fee.getPartyId(), 1.0, Fee.FeeUnitEnum.getByInt(fee.getUnit()).getName(), feeValue, fee.getFromDate(), fee.getToDate());
		    }
		}
		
		return;
	}
	
	/**
	 * Add mandatory,  non special, not taxable fees to given price.
	 * 
	 * @param sqlSession
	 * @param hasPrice
	 * @param priceValue
	 */
	public static PriceComplexValue addNotTaxableFees(SqlSession sqlSession, HasPrice hasPrice, PriceComplexValue priceValue ){
		
		try{
			// Searching non special, non taxable mandatory fees
			
			Fee action = new Fee();
			action.setEntityType(Fee.MANDATORY);
			action.setProductId(hasPrice.getProductid());
			action.setPartyId(hasPrice.getSupplierid()); 	// TODO: Check this if its needed
			action.setFromDate(hasPrice.getFromdate());
			action.setTaxType(Fee.NOT_TAXABLE);
			action.setState(Fee.CREATED);
			action.setWeight(0);   		// NON special

			ArrayList<Fee> fees = sqlSession.getMapper(FeeMapper.class).readbydateandproduct(action);
			if (fees != null && !fees.isEmpty()) {
			    
			    Double baseQuoteValue = priceValue.getTotQuote();
			    
				for (Fee fee : fees) {
					
				    Double feeValue = calculateFeeValueDependsOnUnit(fee, baseQuoteValue, hasPrice);
				    priceValue.addTotalQuote(feeValue);
				    priceValue.addTotalExtra(feeValue);
					ReservationUtil.addQuotedetail(hasPrice, String.valueOf(fee.getId()), fee.getName(), Fee.EntityTypeEnum.getByInt(fee.getEntityType()).getName(), 
						fee.getPartyId(), 1.0, Fee.FeeUnitEnum.getByInt(fee.getUnit()).getName(), feeValue, fee.getFromDate(), fee.getToDate());
				}
			}
			
		} catch (Throwable ex){
			LOG.error("Something wrong with not taxable fees calculation. Details: " + ex.getMessage(), ex);
			throw new ServiceException(Error.fee_calculation, " Product id: " + hasPrice.getProductid());
		}
		
		return priceValue;
	}
	
	
	/**
	 * Add all special fees (weight > 0)
	 * 
	 * @param sqlSession
	 * @param hasPrice
	 * @param priceValue
	 * @return
	 */
	public static PriceComplexValue addSpecialFees(SqlSession sqlSession, HasPrice hasPrice, PriceComplexValue priceValue ){
	    
	    try{
		// Searching all special mandatory fees (weight > 0)
		
		Fee action = new Fee();
		action.setEntityType(Fee.MANDATORY);
		action.setProductId(hasPrice.getProductid());
		action.setPartyId(hasPrice.getSupplierid());  // TODO: Check this if its needed
		action.setFromDate(hasPrice.getFromdate());
		action.setState(Fee.CREATED);

		ArrayList<Fee> fees = sqlSession.getMapper(FeeMapper.class).readspecialbydateandproduct(action);
		if (fees != null && !fees.isEmpty()) {
		    Collections.sort(fees, feeWeightOrder);
		    
		    	// initial fee weight
		    	int currentWeight = fees.get(0).getWeight();
		    	// initial quote
		    	Double currentQuote = Double.valueOf(priceValue.getTotQuote());
		    	// total fee value per weight
		    	Double totalWeightFeeValue = 0.;
		    	
			for (Fee fee : fees) {
			    			    
			    if (currentWeight < fee.getWeight()){
				
				// new fee has higher weight
				priceValue.addTotalQuote(totalWeightFeeValue);
				priceValue.addTotalExtra(totalWeightFeeValue);
				currentQuote = priceValue.getTotQuote();
				
				totalWeightFeeValue = 0.0;
			    }
			    
			    Double feeValue = calculateFeeValueDependsOnUnit(fee, currentQuote, hasPrice);			    
			    
			    Double feeTaxValue = 0.0;
			    
			    if (fee.isTaxTypeTaxable()){
				// calculate taxes on special fee value
				feeTaxValue = TaxesCalculationHelper.calculateTaxesOnValue(sqlSession, hasPrice, feeValue);
			    }
				    
			    ReservationUtil.addQuotedetail(hasPrice, String.valueOf(fee.getId()), fee.getName(), Fee.EntityTypeEnum.getByInt(fee.getEntityType()).getName(), 
					fee.getPartyId(), 1.0, Fee.FeeUnitEnum.getByInt(fee.getUnit()).getName(), (feeValue), fee.getFromDate(), fee.getToDate());
			    
			    totalWeightFeeValue += feeValue + feeTaxValue;			    
			    
			    currentWeight = fee.getWeight();
			}
			
			priceValue.addTotalQuote(totalWeightFeeValue);
			priceValue.addTotalExtra(totalWeightFeeValue);
		}
		
    		} catch (Throwable ex){
        		LOG.error("Something wrong with special taxable fees calculation. Details: " + ex.getMessage(), ex);
        		throw new ServiceException(Error.fee_calculation, " Product id: " + hasPrice.getProductid());
        	}
	
	    
	    return priceValue;
	}
	
	/**
	 * Calculate result fee value depends on fee unit (per_day, per_person, per_day_per_person)
	 * 
	 * @param fee
	 * @param priceValue
	 * @return
	 */
	private static Double calculateFeeValueDependsOnUnit(Fee fee, double inputValue, HasPrice hasPrice){
		
	    	Double resultFee = 0.;
		int resDuration = hasPrice.getDuration(Time.DAY).intValue();
		int guestCount = hasPrice.getGuestCount();
		
		if (resDuration <= 0 || guestCount <= 0){
			LOG.error("Reservation id: " + hasPrice.getReservationid() + "has 0 days duration or 0 guest.");
		}
		
		if (fee != null && fee.getUnit() != null){
			
			if (fee.getUnit().equals(Fee.NOT_APPLICABLE)){
				resultFee = getFeeAmountValue(fee, inputValue);
			}
			
			if (fee.getUnit().equals(Fee.PER_DAY)){
				resultFee = getFeeAmountValue(fee, inputValue) * resDuration;
			}
			
			if (fee.getUnit().equals(Fee.PER_PERSON)){
				resultFee = getFeeAmountValue(fee, inputValue) * guestCount;
			}
			
			if (fee.getUnit().equals(Fee.PER_DAY_PER_PERSON)){
				resultFee = getFeeAmountValue(fee, inputValue) * resDuration * guestCount;
			}
		}
		
		return resultFee;	
	}
	
	/**
	 * Get given fee amount
	 * 
	 * @param fee
	 * @param value
	 * @return
	 */
	private static double getFeeAmountValue(Fee fee, double value){
		
		if (fee == null)
			return 0.;
		
		if (fee.getValueType().equals(Fee.FLAT)){
			return fee.getValue();					
			
		}else if (fee.getValueType().equals(Fee.PERCENT)){
		
			// calculate percent from value
			double percentFee = value * fee.getValue() / 100;
			return percentFee;
		}
		
		return 0.;		
	}
	
	/**
	 * Test fees method.
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Initialize parameters
		
		SqlSession sqlSession = RazorServer.openSession();
		
		Reservation reservation = new Reservation();
		reservation.setQuotedetail( new ArrayList<Price>());
		reservation.setSupplierid("325804");
		reservation.setProductid("427608");
		reservation.setId("0");
		reservation.setCurrency("USD");
		reservation.setFromdate(new Date(2000, 1, 1));
		reservation.setAdult(2);
		reservation.setChild(1);
		
		PriceComplexValue priceValue = new PriceComplexValue();
		priceValue.setTotQuote(100.0);
		
		addTaxableFees(sqlSession, reservation, priceValue);
		addNotTaxableFees(sqlSession, reservation, priceValue);
		addSpecialFees(sqlSession, reservation, priceValue);
		
		
	}

}
