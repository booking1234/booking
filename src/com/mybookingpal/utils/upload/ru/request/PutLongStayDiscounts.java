package com.mybookingpal.utils.upload.ru.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mybookingpal.utils.upload.ru.LongStays;

import net.cbtltd.rest.ru.request.Authentication;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Push_PutLongStayDiscounts_RQ")
public class PutLongStayDiscounts {
	
	@XmlElement(name = "Authentication", required = true)
	protected Authentication authentication;
	@XmlElement(name = "LongStays", required = true)
	protected LongStays longStays;
	
	/**
	 * set the authentication details
	 */
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
	
	/**
	 * set the collection of long stay discounts
	 */
	public void setLongStays(LongStays longStays) {
		this.longStays = longStays;
	}
	
}
