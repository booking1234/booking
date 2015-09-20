/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.api;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.shared.Price;
import net.cbtltd.shared.Time;

public interface HasPrice {
	
	String getReservationid();
	Integer getGuestCount();
	
	String getAgentid();
	boolean noAgentid();

	void setCurrency(String currency);
	String getCurrency();
	boolean hasCurrency(String currency);
	
	void setNightlyrate(Double value);
	Double getNightlyrate();
	
	void setPrice(Double value);
	Double getPrice();
	boolean noPrice ();
	
	void setQuote(Double value);
	Double getQuote();
	
	void setExtra(Double value);
	Double getExtra();
	
	void setCost(Double value);
	Double getCost();
	
	void setPriceunit(Boolean total); // per unit = false, total = true
	Boolean getPriceunit();
	
	void setQuantity(Integer quantity);
	Integer getQuantity();
	
	void setSupplierid(String supplierid);
	String getSupplierid();
	String getProductid();
	String getUnit();
	
	Date getFromdate();
	Date getTodate();
	Double getDuration(Time unit);
	
	void setQuotedetail(ArrayList<Price> quotedetail);
	ArrayList<Price> getQuotedetail();
//	void addQuotedetail(Boolean id, String name, String type, Double value, String currency);
}
