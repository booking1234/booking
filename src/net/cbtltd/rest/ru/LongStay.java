package net.cbtltd.rest.ru;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class LongStay {
	
	@XmlValue
	protected Integer value;
	@XmlAttribute(name = "DateFrom")
    protected String dateFrom;
	@XmlAttribute(name = "DateTo")
    protected String dateTo;
	@XmlAttribute(name = "Bigger")
	protected Integer bigger;
	@XmlAttribute(name = "Smaller")
	protected Integer smaller;
	
	/**
	 * @return the discount details
	 */
	public Integer getValue() {
		return value;
	}
	
	/**
	 * set the discount details
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
	 * set start of the range (YYYY-MM-DD format)
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
	 * set end of the range (YYYY-MM-DD format)
	 */
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	
	/**
	 * @return the minimum length of stay for the discount 
	 */
	public Integer getBigger() {
		return this.bigger;
	}
	
	/**
	 * set the minimum length of stay for the discount
	 */
	public void setBigger(Integer bigger) {
		this.bigger = bigger;
	}
	
	/**
	 * @return the maximum length of stay for the discount
	 */
	public Integer getSmaller() {
		return this.smaller;
	}
	
	/**
	 * set the maximum length of stay for the discount
	 */
	public void setSmaller(Integer smaller) {
		this.smaller = smaller;
	}

}
