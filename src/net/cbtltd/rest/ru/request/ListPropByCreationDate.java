package net.cbtltd.rest.ru.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Authentication",
    "CreationFrom",
    "CreationTo",
    "IncludeNLA"
})
@XmlRootElement(name = "Pull_ListPropByCreationDate_RQ")
public class ListPropByCreationDate {
	
	@XmlElement(required = true)
	protected Authentication Authentication;
	@XmlElement(required = true)
	protected String CreationFrom;
	@XmlElement(required = true)
	protected String CreationTo;
	protected boolean IncludeNLA;
	
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
	 * @return the start date of creation
	 */
	public String getCreationFrom() {
		return CreationFrom;
	}

	/**
	 * set the start date of creation
	 */
	public void setCreationFrom(String creationFrom) {
		CreationFrom = creationFrom;
	}
	
	/**
	 * @return the end date of creation
	 */
	public String getCreationTo() {
		return CreationTo;
	}

	/**
	 * set the end date of creation
	 */
	public void setCreationTo(String creationTo) {
		CreationTo = creationTo;
	}

	public boolean isIncludeNLA() {
		return IncludeNLA;
	}

	public void setIncludeNLA(boolean includeNLA) {
		IncludeNLA = includeNLA;
	}

}
