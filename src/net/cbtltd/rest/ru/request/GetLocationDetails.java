package net.cbtltd.rest.ru.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Authentication",
    "LocationID"
})
@XmlRootElement(name = "Pull_GetLocationDetails_RQ")
public class GetLocationDetails {
	
	@XmlElement(required = true)
	protected Authentication Authentication;
	@XmlElement(required = true)
	protected Integer LocationID;	
	
	public Authentication getAuthentication() {
		return Authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.Authentication = authentication;
	}

	public Integer getLocationid() {
		return LocationID;
	}
	
	public void setLocationid(Integer locationid) {
		this.LocationID = locationid;
	}	

}
