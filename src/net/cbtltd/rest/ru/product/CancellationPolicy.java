package net.cbtltd.rest.ru.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class CancellationPolicy {
	
	@XmlAttribute(name="ValidFrom")
	protected int ValidFrom;
	@XmlAttribute(name="ValidTo")
	protected int ValidTo;
	@XmlValue
	protected double value;
	
	/**
	 * @return the start of the range when the policy is valid
	 */
	public int getValidFrom() {
		return ValidFrom;
	}
	
	/**
	 * set validFrom
	 */
	public void setValidFrom(int validFrom) {
		ValidFrom = validFrom;
	}
	
	/**
	 * @return the end of the range when the policy is valid
	 */
	public int getValidTo() {
		return ValidTo;
	}
	
	/**
	 * set validTo
	 */
	public void setValidTo(int validTo) {
		ValidTo = validTo;
	}
	
	/**
	 * @return the cancellationPolicy value (Percentage of total price, attributes ValidFrom, 
	 * ValidTo represents number of days to arrival between which policy is valid)
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * set cancellationPolicy
	 */
	public void setValue(double value) {
		this.value = value;
	}

}
