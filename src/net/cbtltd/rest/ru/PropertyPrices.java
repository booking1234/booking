package net.cbtltd.rest.ru;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "PropertyPrice"
})
@XmlRootElement(name = "PropertyPrices")
public class PropertyPrices {

	@XmlAttribute(name="PropertyID")
	protected int propertyid;
	protected List<PropertyPrice> PropertyPrice;

	/**
	 * @return a collection of final property prices depending on number of guests
	 */
    public List<PropertyPrice> getPropertyPrice() {
        if (PropertyPrice == null) {
        	PropertyPrice = new ArrayList<PropertyPrice>();
        }
        return this.PropertyPrice;
    }

	/**
	 * @return the  code that uniquely identifies a property
	 */
	public int getPropertyid() {
		return propertyid;
	}

	/**
	 * set the propertyid
	 */
	public void setPropertyid(int propertyid) {
		this.propertyid = propertyid;
	}

}
