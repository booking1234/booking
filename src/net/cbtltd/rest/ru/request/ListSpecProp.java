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
@XmlRootElement(name = "Pull_ListSpecProp_RQ")
public class ListSpecProp {

	@XmlElement(required = true)
	protected Authentication Authentication;
	@XmlElement(required = true)
	protected String PropertyID;
	
	/**
	 * @return the authentication
	 */
	public Authentication getAuthentication() {
		return Authentication;
	}
	
	/**
	 * set authentication
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
	 * set propertyID
	 */
	public void setPropertyID(String propertyID) {
		PropertyID = propertyID;
	}	

}
