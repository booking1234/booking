package net.cbtltd.shared.price;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("pricerange")
public class PriceRange {
	private int week;
	private Integer minPrice;
	private Integer maxPrice;
	private Integer avgPrice;
	private String minstay;
	private String startDate;

	public PriceRange() {
		super();
	}
	
	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public Integer getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	public Integer getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Integer getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(Integer avgPrice) {
		this.avgPrice = avgPrice;
	}

	public String getMinstay() {
		return minstay;
	}

	public void setMinstay(String minstay) {
		this.minstay = minstay;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
}
