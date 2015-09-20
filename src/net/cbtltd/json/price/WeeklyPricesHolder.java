package net.cbtltd.json.price;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.cbtltd.json.JSONService;
import net.cbtltd.json.minstay.WeeklyMinstay;
import net.cbtltd.shared.price.PriceRange;

public class WeeklyPricesHolder {
	
	public WeeklyPricesHolder(List<WeeklyPrice> weeklyPrices, List<WeeklyMinstay> weeklyMinstays) {
		this.setWeeklyPrices(weeklyPrices);
		this.setWeeklyMinstays(weeklyMinstays);
	}
	
	private List<WeeklyPrice> weeklyPrices;
	private List<WeeklyMinstay> weeklyMinstays;
	
	public List<WeeklyPrice> getPriceStartWeek(Date weekStart) {
		List<WeeklyPrice> result = new ArrayList<WeeklyPrice>();
		for(WeeklyPrice price : weeklyPrices) {
			if(price.getStart().equals(weekStart)) {
				result.add(price);
			}
		}
		return result;
	}
	
	public List<WeeklyMinstay> getMinstayStartWeek(Date weekStart) {
		List<WeeklyMinstay> result = new ArrayList<WeeklyMinstay>();
		if(weeklyMinstays != null && weeklyMinstays.size() != 0) {
			for(WeeklyMinstay minstay : weeklyMinstays) {
				if(minstay.getStart().equals(weekStart)) {
					result.add(minstay);
				}
			}
		}
		return result;
	} 
	
	public PriceRange getPriceRange(Date startDate, int week) {
		List<WeeklyPrice> prices = this.getPriceStartWeek(startDate);
		List<WeeklyMinstay> minstays = this.getMinstayStartWeek(startDate);
		PriceRange range = new PriceRange();
		double sum = 0;
		int iterations = 0;
		Integer minimumMinstay = null;
		Integer maxMinimumstay = null;
		for(WeeklyPrice price : prices) {
			Double value = price.getValue();
			Integer intValue = getIntPrice(value);
			if(range.getMaxPrice() == null || (intValue != null && intValue > range.getMaxPrice())) {
				range.setMaxPrice(intValue);
			}
			if(range.getMinPrice() == null || (intValue != null && intValue < range.getMinPrice())) {
				range.setMinPrice(intValue);
			}
			if(intValue != null) {
				sum += intValue;
				iterations++;
			}
			
			Integer priceMinstay = price.getMinStay();
			Integer priceMaxMinstay = price.getMinStay();
			if(minimumMinstay == null) {
				minimumMinstay = priceMinstay;
			}
			if(maxMinimumstay == null) {
				maxMinimumstay = priceMaxMinstay;
			}
			if(priceMinstay != null && priceMinstay < minimumMinstay) {
				minimumMinstay = priceMinstay;
			}
			if(priceMaxMinstay != null && priceMaxMinstay > maxMinimumstay) {
				maxMinimumstay = priceMinstay;
			}
		}
		
		for(WeeklyMinstay minstay : minstays) {
			Integer minstayValue = minstay.getValue();
			Integer maxMinstayValue = minstay.getValue();
			if(minimumMinstay == null) {
				minimumMinstay = minstayValue;
			}
			if(maxMinimumstay == null) {
				maxMinimumstay = maxMinstayValue;
			}
			if(minstayValue != null && minstayValue < minimumMinstay) {
				minimumMinstay = minstayValue;
			}
			if(maxMinstayValue != null && maxMinstayValue > maxMinimumstay) {
				maxMinimumstay = minstayValue;
			}
		}
		
		range.setAvgPrice((int) (sum / iterations));
		range.setWeek(week);
		if(minimumMinstay == maxMinimumstay) {
			range.setMinstay(minimumMinstay.toString());
		} else {
			range.setMinstay(minimumMinstay + "-" + maxMinimumstay);
		}
		range.setStartDate(JSONService.DF.format(startDate));
		return convertAmountWithoutCents(range);
	}
	
	private PriceRange convertAmountWithoutCents(PriceRange priceRange) {
		PriceRange result = priceRange;
		result.setAvgPrice(priceRange.getAvgPrice() / 100);
		result.setMinPrice(priceRange.getMinPrice() / 100);
		result.setMaxPrice(priceRange.getMaxPrice() / 100);
		
		return result;
	}
	
	public Integer getIntPrice(Double price) {
		return (int) (price * 100);
	}
	
	public Map<Date, Integer> getAvailableWeeks() {
		Map<Date, Integer> weeks = new TreeMap<Date, Integer>();
		for(WeeklyPrice price : weeklyPrices) {
			weeks.put(price.getStart(), price.getWeek());
		}
		return weeks;
	}
	
	public List<PriceRange> getAvailalbePriceRanges() {
		List<PriceRange> result = new ArrayList<PriceRange>();
		Map<Date, Integer> weeks = getAvailableWeeks();
		for(Map.Entry<Date, Integer> week : weeks.entrySet()) {
			result.add(getPriceRange(week.getKey(), week.getValue()));
		}
		return result;
	}

	public List<WeeklyMinstay> getWeeklyMinstays() {
		return weeklyMinstays;
	}

	public void setWeeklyMinstays(List<WeeklyMinstay> weeklyMinstays) {
		this.weeklyMinstays = weeklyMinstays;
	}

	public List<WeeklyPrice> getWeeklyPrices() {
		return weeklyPrices;
	}

	public void setWeeklyPrices(List<WeeklyPrice> weeklyPrices) {
		this.weeklyPrices = weeklyPrices;
	}
}
