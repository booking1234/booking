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
@XmlRootElement(name = "Pull_ListPropertyChangeLog_RQ")
public class ListPropertyChangeLog {

	@XmlElement(required = true)
	protected Authentication Authentication;
	@XmlElement(required = true)
	protected Integer PropertyID;
	
	public Authentication getAuthentication() {
		return Authentication;
	}
	
	public void setAuthentication(Authentication authentication) {
		this.Authentication = authentication;
	}

	public Integer getPropertyID() {
		return PropertyID;
	}

	public void setPropertyID(Integer propertyID) {
		this.PropertyID = propertyID;
	}
	
}
