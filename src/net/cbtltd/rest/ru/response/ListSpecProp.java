package net.cbtltd.rest.ru.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.cbtltd.rest.ru.Status;
import net.cbtltd.rest.ru.product.Property;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Status",
    "Property"
})
@XmlRootElement(name = "Pull_ListSpecProp_RS")
public class ListSpecProp {
	
	@XmlElement(required = true)
	protected Status Status;
	@XmlElement(required = true)
	protected Property Property;
	
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return Status;
	}
	
	/**
	 * set status
	 */
	public void setStatus(Status status) {
		Status = status;
	}
	
	/**
	 * @return the single property
	 */
	public Property getProperty() {
		return Property;
	}
	
	/**
	 * set property
	 */
	public void setProperty(Property property) {
		Property = property;
	}

}
