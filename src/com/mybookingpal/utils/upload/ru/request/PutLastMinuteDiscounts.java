package com.mybookingpal.utils.upload.ru.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mybookingpal.utils.upload.ru.LastMinutes;

import net.cbtltd.rest.ru.request.Authentication;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Push_PutLastMinuteDiscounts_RQ")
public class PutLastMinuteDiscounts {
	
	@XmlElement(name = "Authentication", required = true)
	protected Authentication authentication;
	@XmlElement(name = "LastMinutes", required = true)
	protected LastMinutes lastMinutes;
	
	/**
	 * set the authentication details
	 */
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
	
	/**
	 * set the last minutes discounts
	 */
	public void setLastMinutes(LastMinutes lastMinutes) {
		this.lastMinutes = lastMinutes;
	}

}
