package net.cbtltd.rest.webchalet;

public class PriceWC{
	private String currency;
	private int minimum_nights;
	private Double monthly;
	private Double nightly;
	private Double weekly;
	
	public String getCurrency(){
		return this.currency;
	}
	
	public int getMinimum(){
		return this.minimum_nights;
	}
	
	public Double getMonthly(){
		return this.monthly;
	}
	
	public Double getNightly(){
		return this.nightly;
	}
	
	public Double getWeekly(){
		return this.weekly;
	}
	
}