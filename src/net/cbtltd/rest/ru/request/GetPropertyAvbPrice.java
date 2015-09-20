package net.cbtltd.rest.ru.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Authentication",
    "PropertyID",
    "DateFrom",
    "DateTo",
    "NOP"
})
@XmlRootElement(name = "Pull_GetPropertyAvbPrice_RQ")
public class GetPropertyAvbPrice {
	@XmlElement(required = true)
	protected Authentication Authentication;
	@XmlElement(required = true)
	protected String PropertyID;
	@XmlElement(required = true)
	protected String DateFrom;
	@XmlElement(required = true)
	protected String DateTo;
	@XmlElement(required = false)
	protected int NOP;
	
	/**
	 * @return the authentication
	 */
	public Authentication getAuthentication() {
		return Authentication;
	}
	
	/**
	 * set the authentication to set
	 */
	public void setAuthentication(Authentication authentication) {
		Authentication = authentication;
	}
	
	/**
	 * @return the propertyID
	 */
	public String getPropertyID() {
		return PropertyID;
	}

	/**
	 * set the propertyID
	 */
	public void setPropertyID(String propertyID) {
		PropertyID = propertyID;
	}

	/**
	 * @return the dateFrom
	 */
	public String getDateFrom() {
		return DateFrom;
	}
	
	/**
	 * set the dateFrom
	 */
	public void setDateFrom(String dateFrom) {
		DateFrom = dateFrom;
	}
	
	/**
	 * @return the dateTo
	 */
	public String getDateTo() {
		return DateTo;
	}
	
	/**
	 * set the dateTo
	 */
	public void setDateTo(String dateTo) {
		DateTo = dateTo;
	}
	
	/**
	 * @return the NOP 
	 */
	public int getNOP() {
		return NOP;
	}
	
	/**
	 * set the NOP (Number of guests, if it's provided the response will include price details for the given number of guests only)
	 */
	public void setNOP(int nop) {
		NOP = nop;
	}

}
