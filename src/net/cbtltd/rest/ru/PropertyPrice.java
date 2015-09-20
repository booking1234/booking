package net.cbtltd.rest.ru;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class PropertyPrice {
	
	@XmlValue
	protected double price;
	@XmlAttribute(name="NOP")
	protected int NOP;
	@XmlAttribute(name="Cleaning")
	protected double Cleaning;
	@XmlAttribute(name="ExtraPersonPrice")
	protected double ExtraPersonPrice;
	@XmlAttribute(name="Deposit")
	protected double Deposit;
	@XmlAttribute(name="SecurityDeposit")
	protected double SecurityDeposit;
	
	/**
	 * @return the final property price depending on number of guests
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * set the price
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * @return the number of guest for which the price is valid
	 */
	public int getNOP() {
		return NOP;
	}
	
	/**
	 * set the NOP
	 */
	public void setNOP(int nOP) {
		NOP = nOP;
	}
	
	/**
	 * @return the cleaning fee (included in PropertyPrice)
	 */
	public double getCleaning() {
		return Cleaning;
	}
	
	/**
	 * @param cleaning the cleaning to set
	 */
	public void setCleaning(double cleaning) {
		Cleaning = cleaning;
	}
	
	/**
	 * @return the extra person fee (included in PropertyPrice)
	 */
	public double getExtraPersonPrice() {
		return ExtraPersonPrice;
	}
	
	/**
	 * set the extraPersonPrice
	 */
	public void setExtraPersonPrice(double extraPersonPrice) {
		ExtraPersonPrice = extraPersonPrice;
	}
	
	/**
	 * @return the deposit amount which should be secured
	 */
	public double getDeposit() {
		return Deposit;
	}
	
	/**
	 * set the deposit
	 */
	public void setDeposit(double deposit) {
		Deposit = deposit;
	}
	
	/**
	 * @return the refundable security deposit paid by the client directly to the property owner on arrival
	 */
	public double getSecurityDeposit() {
		return SecurityDeposit;
	}
	
	/**
	 * set the securityDeposit
	 */
	public void setSecurityDeposit(double securityDeposit) {
		SecurityDeposit = securityDeposit;
	}

}
