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
@XmlRootElement(name = "EGP")
public class EGP {

	@XmlAttribute(name="ExtraGuests")
	protected int ExtraGuests;
	@XmlElement(required = true)
	protected Double Price;
	
	/**
	 * @return the number of extra guests for which the price applies
	 */
	public int getExtraGuests() {
		return ExtraGuests;
	}
	
	/**
	 * set the number of extra guests for which the price applies
	 */
	public void setExtraGuests(int extraguests) {
		ExtraGuests = extraguests;
	}
	
	/**
	 * @return the single extra guest price vale for a given number of extra guests
	 */
	public Double getPrice() {
		return Price;
	}
	
	/**
	 * set the single extra guest price vale for a given number of extra guests
	 */
	public void setPrice(Double price) {
		Price = price;
	}
}
