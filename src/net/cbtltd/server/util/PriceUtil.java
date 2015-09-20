package net.cbtltd.server.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.response.QuoteDetail;
import net.cbtltd.server.WebService;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.api.HasPrice;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

public final class PriceUtil {

	
	/**
	 * Currency convertation for array of prices.  
	 * @param sqlSession
	 * @param prices
	 * @param toCurrency
	 * @return
	 */
	public static List<Price> convertCurrency(SqlSession sqlSession, List<Price> prices, String toCurrency) {
		
		HashMap<String, Double> currencyRate = new  HashMap<String, Double>(); 
		
		if (prices != null && !StringUtils.isEmpty(toCurrency)){			
			for(Price price : prices){
				String fromCurrency = price.getCurrency();
				
				if (fromCurrency != toCurrency){					
					Double value = price.getValue();
					Double minimum = price.getMinimum();
					
					if (!currencyRate.containsKey(fromCurrency)){
						currencyRate.put(fromCurrency, WebService.getRate(sqlSession, fromCurrency, toCurrency, new Date()));							
					}
					Double exchangeRate = currencyRate.get(fromCurrency);
					
					price.setValue(value * exchangeRate);
					price.setCurrency(toCurrency);
					price.setMinimum(minimum * exchangeRate);
				}
			}
		}
		
		return prices;
	}
	
	
	/**
	 * Convert to another currency reservationPrice object.
	 * 
	 * @param sqlSession current SQL session
	 * @param reservationPrice reservationPrice object that is needed to convert
	 * @param toCurrency currency in which to convert
	 */
	public static ReservationPrice convertCurrency(SqlSession sqlSession, ReservationPrice reservationPrice, String toCurrency) {
		List<QuoteDetail> quoteDetails = reservationPrice.getQuoteDetails();
		Double total = reservationPrice.getTotal();
		String fromCurrency = reservationPrice.getCurrency();

		Double exchangeRate = WebService.getRate(sqlSession, fromCurrency, toCurrency, new Date());
		
		for(QuoteDetail quoteDetail : quoteDetails) {
			Double amount = Double.valueOf(quoteDetail.getAmount());
			quoteDetail.setAmount(String.valueOf(amount * exchangeRate));
			quoteDetail.setCurrency(toCurrency);
		}
		
		reservationPrice.setTotal(total * exchangeRate);
		reservationPrice.setCurrency(toCurrency);
		return reservationPrice;
	}
	
	/**
	 * Convert currency for all values in the HasPrice object.
	 * 
	 * @param sqlSession current SQL session
	 * @param hasPrice hasPrice object that is needed to convert
	 * @param toCurrency currency in which to convert
	 */
	public static HasPrice convertCurrency(SqlSession sqlSession, HasPrice hasPrice, String toCurrency) {
		String fromCurrency = hasPrice.getCurrency();
		List<Price> quoteDetails = hasPrice.getQuotedetail();
		Double cost = hasPrice.getCost();
		Double extra = hasPrice.getExtra();
		Double price = hasPrice.getPrice();
		Double quote = hasPrice.getQuote();
		Double nightlyRate = hasPrice.getNightlyrate();
		
		Double exchangeRate = WebService.getRate(sqlSession, fromCurrency, toCurrency, new Date());
		
		hasPrice.setCost(cost * exchangeRate);
		hasPrice.setExtra(extra * exchangeRate);
		hasPrice.setPrice(price * exchangeRate);
		hasPrice.setQuote(quote * exchangeRate);
		if (nightlyRate != null){
			hasPrice.setNightlyrate(nightlyRate * exchangeRate);
		}
		
		if(quoteDetails != null) {
			for(Price quoteDetail : quoteDetails) {
				if(!quoteDetail.getCurrency().equals(toCurrency)) {
					exchangeRate = WebService.getRate(sqlSession, quoteDetail.getCurrency(), toCurrency, new Date());
					multiplyPrice(quoteDetail, exchangeRate);
				}
			}
		}
		
		hasPrice.setCurrency(toCurrency);
		return hasPrice;
	}
	
	public static HasPrice convertCurrency(SqlSession sqlSession, HasPrice hasPrice, String toCurrency, Date date) {
		String fromCurrency = hasPrice.getCurrency();
		List<Price> quoteDetails = hasPrice.getQuotedetail();
		Double cost = hasPrice.getCost();
		Double extra = hasPrice.getExtra();
		Double price = hasPrice.getPrice();
		Double quote = hasPrice.getQuote();
		Double nightlyRate = hasPrice.getNightlyrate();
		
		Double exchangeRate = WebService.getRate(sqlSession, fromCurrency, toCurrency, new Date());
		
		hasPrice.setCost(cost * exchangeRate);
		hasPrice.setExtra(extra * exchangeRate);
		hasPrice.setPrice(price * exchangeRate);
		hasPrice.setQuote(quote * exchangeRate);
		if (nightlyRate != null){
			hasPrice.setNightlyrate(nightlyRate * exchangeRate);
		}
		
		if(quoteDetails != null) {
			for(Price quoteDetail : quoteDetails) {
				if(!quoteDetail.getCurrency().equals(toCurrency)) {
					exchangeRate = WebService.getRate(sqlSession, quoteDetail.getCurrency(), toCurrency, date);
					multiplyPrice(quoteDetail, exchangeRate);
				}
			}
		}
		
		hasPrice.setCurrency(toCurrency);
		return hasPrice;
	}
	
	/**
	 * Adding commission to all of the prices.
	 * 
	 * @param hasPrice object to add commission
	 * @param commission commission to add
	 * @return multiplied HasPrice object
	 */
	public static HasPrice addCommission(HasPrice hasPrice, Double commission) {
		List<Price> quoteDetails = hasPrice.getQuotedetail();
		if (quoteDetails == null) {
			quoteDetails = new ArrayList<net.cbtltd.shared.Price>();
		}
		Double cost = hasPrice.getCost();
		Double extra = hasPrice.getExtra();
		Double price = hasPrice.getPrice();
		Double quote = hasPrice.getQuote();
		
		// priceAmount / (1 - getAdditionalCommission()) - priceAmount;
		hasPrice.setCost(cost + CommissionCalculationUtil.getCommissionValue(cost, commission));
		hasPrice.setExtra(extra + CommissionCalculationUtil.getCommissionValue(extra, commission));
		hasPrice.setPrice(price + CommissionCalculationUtil.getCommissionValue(price, commission));
		hasPrice.setQuote(quote + CommissionCalculationUtil.getCommissionValue(quote, commission)); 
		
		for(Price quoteDetail : quoteDetails) {
			CommissionCalculationUtil.addCommissionToPrice(quoteDetail, commission);
		}
		
		return hasPrice;
	}
	
	private static void multiplyPrice(Price price, Double multiplier) {
		Double quoteValue = price.getValue();
		Double quoteMinimum = price.getMinimum();
		
		if(quoteValue != null) {
			price.setValue(quoteValue * multiplier);
		}
		
		if(quoteMinimum != null) {
			price.setMinimum(quoteMinimum * multiplier);
		}
	}
	
}
