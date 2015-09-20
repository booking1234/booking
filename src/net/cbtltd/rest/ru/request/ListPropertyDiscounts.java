package net.cbtltd.rest.ru.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Authentication",
    "PropertyID"
})
@XmlRootElement(name = "Pull_ListPropertyDiscounts_RQ")
public class ListPropertyDiscounts {

	@XmlElement(required = true)
	protected Authentication Authentication;
	@XmlElement(required = true)
	protected int PropertyID;
	
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
	
}
