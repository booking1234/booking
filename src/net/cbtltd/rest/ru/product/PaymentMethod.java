package net.cbtltd.rest.ru.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentMethod {
	
	@XmlAttribute(name="PaymentMethodID")
	protected int PaymentMethodID;
	@XmlValue
	protected String value;
	
	/**
	 * @return the paymentMethodID
	 */
	public int getPaymentMethodID() {
		return PaymentMethodID;
	}
	
	/**
	 * set paymentMethodID
	 */
	public void setPaymentMethodID(int paymentMethodID) {
		PaymentMethodID = paymentMethodID;
	}
	
	/**
	 * @return the paymentMethod text (Payment method details, i.e. Bank account)
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * set paymentMethod
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
