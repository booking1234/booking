package net.cbtltd.rest.ru.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class Deposit {
	
	@XmlAttribute(name="DepositTypeID")
	protected int DepositTypeID;
	@XmlValue
	protected double DepositValue;
	
	/**
	 * @return the depositTypeID
	 */
	public int getDepositTypeID() {
		return DepositTypeID;
	}
	
	/**
	 * set depositTypeID
	 */
	public void setDepositTypeID(int depositTypeID) {
		DepositTypeID = depositTypeID;
	}
	
	/**
	 * @return the depositValue
	 */
	public double getDepositValue() {
		return DepositValue;
	}
	
	/**
	 * set depositValue
	 */
	public void setDepositValue(double depositValue) {
		DepositValue = depositValue;
	}

}
