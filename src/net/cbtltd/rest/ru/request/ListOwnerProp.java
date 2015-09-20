package net.cbtltd.rest.ru.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "authentication",
    "ownerID",
    "includeNLA"
})
@XmlRootElement(name = "Pull_ListOwnerProp_RQ")
public class ListOwnerProp {
	
	@XmlElement(name = "Authentication", required = true)
	protected Authentication authentication;
	@XmlElement(name = "OwnerID", required = true)
	protected int ownerID;
	@XmlElement(name = "IncludeNLA")
	protected boolean includeNLA;
	
	/**
	 * @return the authentication
	 */
	public Authentication getAuthentication() {
		return authentication;
	}

	/**
	 * set authentication
	 */
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	/**
	 * @return the owenerID
	 */
	public int getOwnerID() {
		return ownerID;
	}

	/**
	 * set the owenerID
	 */
	public void setOwnerID(int owenerID) {
		this.ownerID = owenerID;
	}

	public boolean isIncludeNLA() {
		return includeNLA;
	}

	public void setIncludeNLA(boolean includeNLA) {
		this.includeNLA = includeNLA;
	}
}
