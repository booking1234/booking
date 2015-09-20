package net.cbtltd.rest.ru;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	    "Price"
	})
@XmlRootElement(name = "LOSP")
public class LOSP {
	
	@XmlAttribute(name="NrOfGuests")
	protected int NrOfGuests;
	@XmlElement(required = true)
	protected Double Price;
	
	/**
	 * @return the Number of guests
	 */
	public int getNrOfGuests() {
		return NrOfGuests;
	}
	
	/**
	 * set the Number of guests
	 */
	public void setNrOfGuests(int nrOfGuests) {
		NrOfGuests = nrOfGuests;
	}
	
	/**
	 * @return the Nightly price
	 */
	public Double getPrice() {
		return Price;
	}
	
	/**
	 * set the price
	 */
	public void setPrice(Double price) {
		Price = price;
	}
	

}
