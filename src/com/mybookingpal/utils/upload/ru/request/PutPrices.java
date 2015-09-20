package com.mybookingpal.utils.upload.ru.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.cbtltd.rest.ru.Prices;
import net.cbtltd.rest.ru.request.Authentication;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Push_PutPrices_RQ")
public class PutPrices {
	
	@XmlElement(name = "Authentication", required = true)
	protected Authentication authentication;
	@XmlElement(name = "Prices", required = true)
	protected Prices prices;
	
	/**
	 * set the authentication details
	 */
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
	
	/**
	 * set the information about property's prices
	 */
	public void setPrices(Prices prices) {
		this.prices = prices;
	}

}
