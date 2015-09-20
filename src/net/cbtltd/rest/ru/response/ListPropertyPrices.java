package net.cbtltd.rest.ru.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.cbtltd.rest.ru.Status;
import net.cbtltd.rest.ru.Prices;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Status",
    "Prices"
})
@XmlRootElement(name = "Pull_ListPropertyPrices_RS")
public class ListPropertyPrices {

	@XmlElement(required = true)
	protected Status Status;
	@XmlElement(required = true)
	protected Prices Prices;
	
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
	 * @return the collection of property prices
	 */
	public Prices getPrices() {
		return Prices;
	}
	
	/**
	 * set the prices
	 */
	public void setPrices(Prices prices) {
		Prices = prices;
	}
	
}
