package net.cbtltd.rest.ru.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.cbtltd.rest.ru.PropertyPrices;
import net.cbtltd.rest.ru.Status;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	"Status",
	"PropertyPrices"
})
@XmlRootElement(name = "Pull_GetPropertyAvbPrice_RS")
public class GetPropertyAvbPrice {

	@XmlElement(name="Status",required = true)
	protected Status Status;
	@XmlElement(name="PropertyPrices")
	protected PropertyPrices PropertyPrices;
	
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return Status;
	}
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		Status = status;
	}
	
	/**
	 * @return the propertyPrices
	 */
	public PropertyPrices getPropertyPrices() {
		return PropertyPrices;
	}
	
	/**
	 * @param propertyPrices the propertyPrices to set
	 */
	public void setPropertyPrices(PropertyPrices propertyPrices) {
		PropertyPrices = propertyPrices;
	}
}
