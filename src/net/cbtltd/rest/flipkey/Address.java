/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.flipkey;

class Address {
	public String Address1;
	public String Address2;
	public String City;
	public State State;
	public String ZipCode;
	public Country Country;
	public String Region;
	public String SubRegion;
	public Double Latitude;
	public Double Longitude;
	
	public Address(){}
	
	public Address(String address1, String address2, String city, String state,
			String zipCode, String country, String countryname, String region, String subRegion,
			Double latitude, Double longitude) {
		super();
		this.Address1 = address1;
		this.Address2 = address2;
		this.City = city;
		this.State = new State(state, region);
		this.ZipCode = zipCode;
		this.Country = new Country(country, countryname);
		this.Region = region;
		this.SubRegion = subRegion;
		this.Latitude = latitude;
		this.Longitude = longitude;
	}
}
