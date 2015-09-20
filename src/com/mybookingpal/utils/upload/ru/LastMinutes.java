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
    "LastMinute"
})
@XmlRootElement(name = "LastMinutes")
public class LastMinutes {
	
	@XmlAttribute(name = "PropertyID")
	protected int propertyID;
	protected List<LastMinute> LastMinute;
	
	/**
	 * @return the propertyID
	 */
	public int getPropertyID() {
		return this.propertyID;
	}
	
	/**
	 * set the propertyID
	 */
	public void setPropertyID(int propertyID) {
		this.propertyID = propertyID;
	}
	
	/**
	 * @return the last minute discounts
	 */
	public List<LastMinute> getLastMinute() {
		if (LastMinute == null){
			LastMinute = new ArrayList<LastMinute>();
		}
		return this.LastMinute;
	}
	
	/**
	 * set the last minute discounts
	 */
	public void setLastMinute(List<LastMinute> lastMinute) {
		this.LastMinute = lastMinute;
	}

}
