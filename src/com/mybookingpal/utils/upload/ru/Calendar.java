package com.mybookingpal.utils.upload.ru;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.cbtltd.rest.ru.LastMinute;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Availability"
})
@XmlRootElement(name = "Calendar")
public class Calendar {
	
	@XmlAttribute(name = "PropertyID")
	protected int propertyID;
	protected List<Availability> Availability;
	
	/**
	 * @return the propertyID
	 */
	public int getPropertyID() {
		return propertyID;
	}
	
	/**
	 * set the propertyID
	 */
	public void setPropertyID(int propertyID) {
		this.propertyID = propertyID;
	}
	
	/**
	 * @return the availability
	 */
	public List<Availability> getAvailability() {
		if (Availability == null) {
			Availability = new ArrayList<Availability>();
		}
		return Availability;
	}
	
	/**
	 * @param availability the availability to set
	 */
	public void setAvailability(List<Availability> availability) {
		Availability = availability;
	}

}
