package net.cbtltd.rest.webchalet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RateWC{
	private String start;
	private String end;
	private int minimum_nights;
	private Double nightly;
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	
	public Date getStartDate() throws ParseException{
		return DF.parse(this.start);
	}
	
	public Date getEndDate() throws ParseException{
		return DF.parse(this.end);
	}
	
	public int getMinimumStay(){
		return this.minimum_nights;
	}
	
	public Double getNightly(){
		return this.nightly;
	}
	
}