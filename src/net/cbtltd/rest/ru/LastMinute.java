package net.cbtltd.rest.ru;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class LastMinute {
	
	@XmlValue
	protected Integer value;
	@XmlAttribute(name = "DateFrom")
    @XmlSchemaType(name = "date")
    protected String dateFrom;
	@XmlAttribute(name = "DateTo")
    @XmlSchemaType(name = "date")
    protected String dateTo;
	@XmlAttribute(name = "DaysToArrivalFrom")
	protected Integer daysToArrivalFrom;
	@XmlAttribute(name = "DaysToArrivalTo")
	protected Integer daysToArrivalTo;
	
	/**
	 * @return the last minute discount, percentage of final price
	 */
	public Integer getValue() {
		return value;
	}
	
	/**
	 * set the last minute discount, percentage of final price
	 */
	public void setValue(Integer value) {
		this.value = value;
	}
	
	/**
	 * @return the start of the range (YYYY-MM-DD format)
	 */
	public String getDateFrom() {
		return this.dateFrom;
	}
	
	/**
	 * set the start of the range (YYYY-MM-DD format)
	 */
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	
	/**
	 * @return the end of the range (YYYY-MM-DD format)
	 */
	public String getDateTo() {
		return this.dateTo;
	}
	
	/**
	 * set the end of the range (YYYY-MM-DD format)
	 */
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	
	/**
	 * @return the minimum number of days to arrival when the discount is valid
	 */
	public Integer getDaysToArrivalFrom() {
		return this.daysToArrivalFrom;
	}
	
	/**
	 * set the minimum number of days to arrival when the discount is valid
	 */
	public void setDaysToArrivalFrom(Integer daysToArrivalFrom) {
		this.daysToArrivalFrom = daysToArrivalFrom;
	}
	
	/**
	 * @return the maximum number of days to arrival when the discount is valid
	 */
	public Integer getDaysToArrivalTo() {
		return this.daysToArrivalTo;
	}
	
	/**
	 * set the maximum number of days to arrival when the discount is valid
	 */
	public void setDaysToArrivalTo(Integer daysToArrivalTo) {
		this.daysToArrivalTo = daysToArrivalTo;
	}

}
