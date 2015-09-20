package com.mybookingpal.utils.upload.ru;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.cbtltd.rest.ru.LongStay;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "LongStay"
})
@XmlRootElement(name = "LongStays")
public class LongStays {
	
	@XmlAttribute(name = "PropertyID")
	protected int propertyID;
	protected List<LongStay> LongStay;
	
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
	 * @return the collection of long stay discounts
	 */
	public List<LongStay> getLongStay() {
		if (LongStay == null) {
			LongStay = new ArrayList<LongStay>();
		}
		return this.LongStay;
	}
	
	/**
	 * set the collection of long stay discounts
	 */
	public void setLongStay(List<LongStay> longStay) {
		this.LongStay = longStay;
	}
}
