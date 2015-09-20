package com.mybookingpal.utils.upload.ru;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class Availability {
	
	@XmlValue
	protected boolean value;
	@XmlAttribute(name = "DateFrom")
    protected String dateFrom;
	@XmlAttribute(name = "DateTo")
    protected String dateTo;
	
	/**
	 * @return the True or False value that specifies the property is available for booking in a given dates
	 */
	public boolean isValue() {
		return value;
	}
	
	/**
	 * set the True or False value that specifies the property is available for booking in a given dates
	 */
	public void setValue(boolean value) {
		this.value = value;
	}
	
	/**
	 * @return the start of the range when an availability value is valid (YYYY-MM-DD format)
	 */
	public String getDateFrom() {
		return this.dateFrom;
	}
	
	/**
	 * set the start of the range when an availability value is valid (YYYY-MM-DD format)
	 */
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	
	/**
	 * @return the end of the range when an availability value is valid (YYYY-MM-DD format)
	 */
	public String getDateTo() {
		return this.dateTo;
	}
	
	/**
	 * set the end of the range when an availability value is valid (YYYY-MM-DD format)
	 */
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

}
