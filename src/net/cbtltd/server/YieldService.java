/**
 * @author	Marko Ovuka
 */
package net.cbtltd.server;

import java.util.Date;

import net.cbtltd.server.api.IsService;
import net.cbtltd.shared.Yield;

public class YieldService implements IsService {

	private static YieldService service;

	/**
	 * Gets the single instance of YieldService to create lists of Yield instances.
	 * @see net.cbtltd.shared.Yield
	 *
	 * @return single instance of YieldService
	 */
	public static synchronized YieldService getInstance() {
		if (service == null) {service = new YieldService();}
		return service;
	}

	
	public static synchronized Yield createLengthOfStayDiscount(String productId, double normalDailyPrice, double periodPrice, 
			int numberOfDays, Date fromDate, Date toDate){
		
		double discountDailyPrice = periodPrice / (double)numberOfDays;
		//in this case we actually do not have any discount so this yield we do not wish in DB
		if(normalDailyPrice == discountDailyPrice){
			return null;
		}
		double discountPercentValue = (normalDailyPrice - discountDailyPrice) * 100.0 / normalDailyPrice;
		//TODO should we round this percent value on 2 decimal places
		
		Yield yield = new Yield();
		yield.setEntitytype(Yield.PRODUCT);
		yield.setEntityid(productId);
		yield.setName(Yield.LENGTH_OF_STAY);
		yield.setState(Yield.CREATED);
		yield.setFromdate(fromDate);
		yield.setTodate(toDate);
		yield.setParam(numberOfDays);
		yield.setAmount(discountPercentValue);
		yield.setModifier(Yield.DECREASE_PERCENT);

		return yield;
	}
	
	
	public static synchronized Yield createWeekendRate(String productId, double normalDailyPrice, double weekendPrice, 
			 Date fromDate, Date toDate){
		
		String modifier;
		double discountPercentValue;
		if(normalDailyPrice == weekendPrice){
			//in this case we actually do not have any price change so this yield we do not wish in DB
			return null;
		}else if(normalDailyPrice > weekendPrice){
			modifier = Yield.DECREASE_PERCENT;
			discountPercentValue = (normalDailyPrice - weekendPrice) * 100.0 / normalDailyPrice;
		}else {
			//weekendPrice > normalDailyPrice
			modifier = Yield.INCREASE_PERCENT;
			discountPercentValue = (weekendPrice - normalDailyPrice) * 100.0 / normalDailyPrice;
		}
		
		//TODO should we round this percent value on 2 decimal places
		
		Yield yield = new Yield();
		yield.setEntitytype(Yield.PRODUCT);
		yield.setEntityid(productId);
		yield.setName(Yield.WEEKEND);
		yield.setState(Yield.CREATED);
		yield.setFromdate(fromDate);
		yield.setTodate(toDate);
		yield.setParam(Yield.DAYS_OF_WEEKEND_SAT_SUN);
		yield.setAmount(discountPercentValue);
		yield.setModifier(modifier);

		return yield;
	}
	
	
}

