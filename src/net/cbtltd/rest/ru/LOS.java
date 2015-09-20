package net.cbtltd.rest.ru;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	    "Price",
	    "LOSPS"
	})
@XmlRootElement(name = "LOS")
public class LOS {
	
	@XmlAttribute(name="Nights")
	protected int Nights;
	@XmlElement(required = true)
	protected Double Price;
	@XmlElement
	protected LOSPS LOSPS;
	
	/**
	 * @return the number of nights for which the price applies
	 */
	public int getNights() {
		return Nights;
	}
	
	/**
	 * set number of nights for which the price applies
	 */
	public void setNights(int nights) {
		Nights = nights;
	}
	
	/**
	 * @return the nightly price vale for a given length of stay
	 */
	public Double getPrice() {
		return Price;
	}
	
	/**
	 * set the nightly price vale for a given length of stay
	 */
	public void setPrice(Double price) {
		Price = price;
	}
	
	/**
	 * @return the lOSPS (collection of nightly price settings depending on number of guests)
	 */
	public LOSPS getLOSPS() {
		return LOSPS;
	}
	
	/**
	 * set the lOSPS (collection of nightly price settings depending on number of guests)
	 */
	public void setLOSPS(LOSPS lOSPS) {
		LOSPS = lOSPS;
	}

}
