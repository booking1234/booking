package net.cbtltd.rest.ru;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	    "LongStays",
	    "LastMinutes"
	})
@XmlRootElement(name = "Discounts")
public class Discounts {

	@XmlAttribute(name = "PropertyID")
	protected Integer ProperrtyID;
	@XmlElementWrapper(name="LongStays",required = true)
	@XmlElement(name="LongStay",required = true)
	protected List<LongStay> LongStays;
	@XmlElementWrapper(name="LastMinutes",required = true)
	@XmlElement(name="LastMinute",required = true)
	protected List<LastMinute> LastMinutes;
	
	/**
	 * @return the properrtyID
	 */
	public Integer getProperrtyID() {
		return ProperrtyID;
	}
	
	/**
	 * @param properrtyID the properrtyID to set
	 */
	public void setProperrtyID(Integer properrtyID) {
		ProperrtyID = properrtyID;
	}
	
	/**
	 * @return the long stay discounts value, percentage of the final price
	 */
	public List<LongStay> getLongStays() {
		return LongStays;
	}
	
	/**
	 * set the long stay discounts value, percentage of the final price
	 */
	public void setLongStays(List<LongStay> longStays) {
		LongStays = longStays;
	}
	
	/**
	 * @return the last minute discounts
	 */
	public List<LastMinute> getLastMinutes() {
		return LastMinutes;
	}
	
	/**
	 * set the last minute discounts
	 */
	public void setLastMinutes(List<LastMinute> lastMinutes) {
		LastMinutes = lastMinutes;
	}
}
