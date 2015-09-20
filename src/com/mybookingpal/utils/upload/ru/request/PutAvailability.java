package com.mybookingpal.utils.upload.ru.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mybookingpal.utils.upload.ru.Calendar;

import net.cbtltd.rest.ru.request.Authentication;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Push_PutAvb_RQ")
public class PutAvailability {
	
	@XmlElement(name = "Authentication", required = true)
	protected Authentication authentication;
	@XmlElement(name = "Calendar", required = true)
	protected Calendar calendar;
	
	/**
	 * set the authentication details
	 */
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
	
	/**
	 * set the information about property's availability calendar
	 */
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

}
