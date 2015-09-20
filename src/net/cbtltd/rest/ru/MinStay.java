package net.cbtltd.rest.ru;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MinStay")
public class MinStay {
	
	@XmlAttribute(name = "DateFrom")
    @XmlSchemaType(name = "date")
    protected String dateFrom;
	@XmlAttribute(name = "DateTo")
    @XmlSchemaType(name = "date")
    protected String dateTo;
	@XmlValue
	protected int value;
	
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
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * set the value
	 */
	public void setValue(int value) {
		this.value = value;
	}

}
