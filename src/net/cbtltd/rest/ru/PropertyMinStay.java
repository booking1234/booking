package net.cbtltd.rest.ru;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "MinStay"
})
@XmlRootElement(name = "PropertyMinStay")
public class PropertyMinStay {
	
	@XmlAttribute(name = "PropertyID")
	protected int propertyID;
	protected List<MinStay> MinStay;
	
	/**
	 * @return the code that uniquely identifies a property in RU system
	 */
	public int getPropertyID() {
		return this.propertyID;
	}
	
	/**
	 * set the propertyID (code that uniquely identifies a property in RU system)
	 */
	public void setPropertyID(int propertyID) {
		this.propertyID = propertyID;
	}
	
	/**
	 * @return the Minimum stay restriction, any stay between DateFrom, DateTo had to be at least MS long
	 */
	public List<MinStay> getMinStay() {
		if (MinStay == null) {
			MinStay = new ArrayList<MinStay>();
		}
		return MinStay;
	}
	
	/**
	 * set the Minimum stay restriction, any stay between DateFrom, DateTo had to be at least MS long
	 */
	public void setMinStay(List<MinStay> minStay) {
		MinStay = minStay;
	}

}
