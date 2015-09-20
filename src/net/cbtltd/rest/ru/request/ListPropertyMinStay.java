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
    "DateTo"
})
@XmlRootElement(name = "Pull_ListPropertyMinStay_RQ")
public class ListPropertyMinStay {
	
	@XmlElement(required = true)
	protected Authentication Authentication;
	@XmlElement(required = true)
	protected int PropertyID;
	@XmlElement(required = true)
	protected String DateFrom;
	@XmlElement(required = true)
	protected String DateTo;
	
	/**
	 * @return the authentication details
	 */
	public Authentication getAuthentication() {
		return Authentication;
	}
	
	/**
	 * @set the authentication
	 */
	public void setAuthentication(Authentication authentication) {
		Authentication = authentication;
	}
	
	/**
	 * @return the propertyID
	 */
	public int getPropertyID() {
		return PropertyID;
	}
	
	/**
	 * set the propertyID (code that uniquely identifies a property)
	 */
	public void setPropertyID(int propertyID) {
		PropertyID = propertyID;
	}
	
	/**
	 * @return the dateFrom
	 */
	public String getDateFrom() {
		return DateFrom;
	}
	
	/**
	 * set the dateFrom (Start of the range (YYYY-MM-DD format))
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
	 * @set the dateTo (End of the range (YYYY-MM-DD format))
	 */
	public void setDateTo(String dateTo) {
		DateTo = dateTo;
	}
}
