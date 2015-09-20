package net.cbtltd.rest.ru;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class Amenity {
	
	@XmlAttribute(name="AmenityID")
	protected int AmenityID;
	@XmlValue
	protected String Amenity;
	
	/**
	 * @return the amenityID
	 */
	public int getAmenityID() {
		return AmenityID;
	}
	
	/**
	 * set the amenityID
	 */
	public void setAmenityID(int amenityID) {
		AmenityID = amenityID;
	}
	
	/**
	 * @return the amenity text value
	 */
	public String getAmenity() {
		return Amenity;
	}
	
	/**
	 * set the amenity
	 */
	public void setAmenity(String amenity) {
		Amenity = amenity;
	}
	

}
