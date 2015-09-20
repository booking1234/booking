package com.mybookingpal.utils.upload.ru.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.cbtltd.rest.ru.PropertyMinStay;
import net.cbtltd.rest.ru.request.Authentication;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Push_PutMinstay_RQ")
public class PutMinstay {
	
	@XmlElement(name = "Authentication", required = true)
	protected Authentication authentication;
	@XmlElement(name = "PropertyMinStay", required = true)
	protected PropertyMinStay propertyMinStay;
	
	/**
	 * set the authentication details
	 */
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
	
	/**
	 * set the information about property's minimum stay restrictions
	 */
	public void setPropertyMinStay(PropertyMinStay propertyMinStay) {
		this.propertyMinStay = propertyMinStay;
	}

}
