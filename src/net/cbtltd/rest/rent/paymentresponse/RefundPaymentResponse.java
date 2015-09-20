package net.cbtltd.rest.rent.paymentresponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="RefundPaymentResponse")
public class RefundPaymentResponse {
	private String referenceNumber;

	@XmlElement(name="referenceNumber")
	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
}