package com.mybookingpal.utils.upload.ru.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.cbtltd.rest.ru.product.Property;
import net.cbtltd.rest.ru.request.Authentication;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Push_PutProperty_RQ")
public class PutProperty {

	@XmlElement(name = "Authentication", required = true)
	protected Authentication authentication;
	@XmlElement(name = "Property", required = true)
	protected Property property;
	
	/**
	 * set the authentication details
	 */
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
	
	/**
	 * set property details
	 */
	public void setProperty(Property property) {
		this.property = property;
	}

}
