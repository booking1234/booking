package net.cbtltd.rest.ru;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	    "LocationID"
	})
@XmlRootElement(name = "Currency")
public class Currency {
	
	@XmlAttribute(name="CurrencyCode")
	protected String CurrencyCode;
	@XmlElementWrapper(name="Locations",required = true)
	@XmlElement(name="LocationID",required = true)
	protected List<Integer> LocationID;
	
	/**
	 * @return the CurrencyCode
	 */
	public String getCurrencyCode() {
		return CurrencyCode;
	}
	
	/**
	 * set CurrencyCode
	 */
	public void setCurrencyCode(String currencyCode) {
		CurrencyCode = currencyCode;
	}
	
	/**
	 * get Location ID
	 * @return
	 */
    public List<Integer> getLocationID() {
        if (LocationID == null) {
        	LocationID = new ArrayList<Integer>();
        }
        return this.LocationID;
    }
}
