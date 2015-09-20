/*
 * @author David Lepe and Devin Held
 * Version 1.0
 */

package net.cbtltd.rest.laketahoeaccommodations;

import java.util.Date;


public class Day {
	int rateTable; // rate table index
	int minStay;
	int dayPrice; // the base rate for that specific date, 0 if the property is not available on that date
	Date date; // represents in yyyyDDmm format the day this object represents
	
	public int getDayPrice() {
		return dayPrice;
	}
	
	public void setDayPrice(int dayPrice) {
		this.dayPrice = dayPrice;
	}
	
	/**
	 * @return the rateTable
	 */
	public int getRateTable() {
		return rateTable;
	}
	/**
	 * @param rateTable the rateTable to set
	 */
	public void setRateTable(int rateTable) {
		this.rateTable = rateTable;
	}
	/**
	 * @return the minStay
	 */
	public int getMinStay() {
		return minStay;
	}
	/**
	 * @param minStay the minStay to set
	 */
	public void setMinStay(int minStay) {
		this.minStay = minStay;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	@Override
	public String toString() {
		return "Day [rateTable=" + rateTable + ", minStay=" + minStay
				+ ", date=" + date + "]";
	}
}
