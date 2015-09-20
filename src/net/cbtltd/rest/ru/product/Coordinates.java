package net.cbtltd.rest.ru.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Latitude",
    "Longitude"
})
@XmlRootElement(name = "Coordinates")
public class Coordinates {

	@XmlElement(required = true)
	protected String Latitude;
	@XmlElement(required = true)
	protected String Longitude;
	
	public Coordinates() {}
	
	public Coordinates(String Latitude, String Longitude){
		this.Latitude = Latitude;
		this.Longitude = Longitude;
	}
	
	/**
	 * @return the geographic latitude
	 */
	public String getLatitude() {
		return Latitude;
	}
	
	/**
	 * set latitude
	 */
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	
	/**
	 * @return the geographic longitude
	 */
	public String getLongitude() {
		return Longitude;
	}
	
	/**
	 * set longitude
	 */
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

}
