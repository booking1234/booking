package net.cbtltd.rest.ru.request;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Authentication",
    "PropertyIDs"
})
@XmlRootElement(name = "Pull_ListPropertiesChangeLog_RQ")
public class ListPropertiesChangeLog {
	
	@XmlElement(required = true)
	protected Authentication authentication;
	@XmlElementWrapper(name = "PropertyIDs")
	@XmlElement(name = "PropertyID",required = true)
	protected List<Integer> propertyIds;
	
	public Authentication getAuthentication() {
		return authentication;
	}
	
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
	
	/**
	 * get Property ID
	 * @return
	 */
    public List<Integer> getPropertyIDs() {
        if (propertyIds == null) {
        	propertyIds = new ArrayList<Integer>();
        }
        return propertyIds;
    }

	/**
	 * @param propertyID the propertyID to set
	 */
	public void setPropertyID(List<Integer> propertyID) {
		this.propertyIds = propertyID;
	}

}
