package net.cbtltd.rest.ru.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.cbtltd.rest.ru.PropertyMinStay;
import net.cbtltd.rest.ru.Status;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Status",
    "PropertyMinStay"
})
@XmlRootElement(name = "Pull_ListPropertyMinStay_RS")
public class ListPropertyMinStay {
	
	@XmlElement(required = true)
	protected Status Status;
	@XmlElement(required = true)
	protected PropertyMinStay PropertyMinStay;
	
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return Status;
	}
	
	/**
	 * set the status
	 */
	public void setStatus(Status status) {
		Status = status;
	}
	
	/**
	 * @return the single property MinStay values
	 */
	public PropertyMinStay getPropertyMinStay() {
		return PropertyMinStay;
	}
	
	/**
	 * set the single property MinStay values
	 */
	public void setPropertyMinStay(PropertyMinStay propertyMinStay) {
		PropertyMinStay = propertyMinStay;
	}

}