package net.cbtltd.rest.ru;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	    "DateFrom",
	    "DateTo",
	})
@XmlRootElement(name = "Block")
public class Block {

	@XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar DateFrom;
	@XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar DateTo;
	
	/**
	 * @return the start of the block (YYYY-MM-DD format)
	 */
	public XMLGregorianCalendar getDateFrom() {
		return DateFrom;
	}
	
	/**
	 * set the start of the block  (YYYY-MM-DD format)
	 */
	public void setDateFrom(XMLGregorianCalendar dateFrom) {
		DateFrom = dateFrom;
	}
	
	/**
	 * @return the end of the block (YYYY-MM-DD format)
	 */
	public XMLGregorianCalendar getDateTo() {
		return DateTo;
	}
	
	/**
	 * set end of the block (YYYY-MM-DD format)
	 */
	public void setDateTo(XMLGregorianCalendar dateTo) {
		DateTo = dateTo;
	}
	
}