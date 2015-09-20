/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.flipkey;

public class Details {
	public Integer MaximumOccupancy;
	public String PropertyDescription;
	public String PropertyType;
	public Integer Bedrooms;
	public Integer Bathrooms;
	public Integer MinimumStayLength;
	public String CheckIn;
	public String CheckOut;
	public String Currency;
	
	public Details(){}
	
	public Details(Integer maximumOccupancy, String propertyDescription,
			String propertyType, Integer bedrooms, Integer bathrooms,
			Integer minimumStayLength, String checkIn, String checkOut,
			String currency) {
		super();
		this.MaximumOccupancy = maximumOccupancy;
		this.PropertyDescription = propertyDescription;
		this.PropertyType = propertyType;
		this.Bedrooms = bedrooms;
		this.Bathrooms = bathrooms;
		this.MinimumStayLength = minimumStayLength;
		this.CheckIn = checkIn;
		this.CheckOut = checkOut;
		this.Currency = currency;
	}
}
