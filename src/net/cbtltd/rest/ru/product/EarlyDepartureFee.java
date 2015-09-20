package net.cbtltd.rest.ru.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class EarlyDepartureFee {
	
	@XmlAttribute(name="From")
	protected String From;
	@XmlAttribute(name="To")
	protected String To;
	@XmlValue
	protected double EarlyDepartureFee;
	
	/**
	 * @return start hour of a range (HH:MM format, 24h format)
	 */
	public String getFrom() {
		return From;
	}
	
	/**
	 * set from
	 */
	public void setFrom(String from) {
		From = from;
	}
	
	/**
	 * @return the end hour of a range (HH:MM format, 24h format)
	 */
	public String getTo() {
		return To;
	}
	
	/**
	 * set to
	 */
	public void setTo(String to) {
		To = to;
	}
	
	/**
	 * @return the earlyDepartureFee value
	 */
	public double getEarlyDepartureFee() {
		return EarlyDepartureFee;
	}
	
	/**
	 * set earlyDepartureFee
	 */
	public void setEarlyDepartureFee(double earlyDepartureFee) {
		EarlyDepartureFee = earlyDepartureFee;
	}


}
