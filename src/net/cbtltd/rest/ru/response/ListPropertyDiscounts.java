package net.cbtltd.rest.ru.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.cbtltd.rest.ru.Discounts;
import net.cbtltd.rest.ru.Status;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Status",
    "Discounts"
})
@XmlRootElement(name = "Pull_ListPropertyDiscounts_RS")
public class ListPropertyDiscounts {
	
	@XmlElement(required = true)
	protected Status Status;
	protected Discounts Discounts;
	
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
	 * @return the collection of long stay discounts
	 */
	public Discounts getDiscounts() {
		return Discounts;
	}

	/**
	 * set the discounts
	 */
	public void setDiscounts(Discounts discounts) {
		Discounts = discounts;
	}
	
}
