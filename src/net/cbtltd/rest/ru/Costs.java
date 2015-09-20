package net.cbtltd.rest.ru;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	    "RUPrice",
	    "ClientPrice",
	    "AlreadyPaid"
	})
@XmlRootElement(name = "Costs")
public class Costs {
	
	@XmlElement(required = true)
	protected double RUPrice;
	@XmlElement(required = true)
	protected double ClientPrice;
	@XmlElement(required = true)
	protected double AlreadyPaid;
	
	/**
	 * @return the price returned by one of the RU methods for a given property in selected dates
	 */
	public double getRUPrice() {
		return RUPrice;
	}
	
	/**
	 *set the price returned by one of the RU methods for a given property in selected dates
	 */
	public void setRUPrice(double rUPrice) {
		RUPrice = rUPrice;
	}
	
	/**
	 * @return the final price for the customer
	 */
	public double getClientPrice() {
		return ClientPrice;
	}
	
	/**
	 * set the final price for the customer
	 */
	public void setClientPrice(double clientPrice) {
		ClientPrice = clientPrice;
	}
	
	/**
	 * @return the amount already paid by the customer
	 */
	public double getAlreadyPaid() {
		return AlreadyPaid;
	}
	
	/**
	 * set the amount already paid by the customer
	 */
	public void setAlreadyPaid(double alreadyPaid) {
		AlreadyPaid = alreadyPaid;
	}
}
