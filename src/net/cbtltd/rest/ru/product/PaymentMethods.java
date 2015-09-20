package net.cbtltd.rest.ru.product;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "PaymentMethod"
})
@XmlRootElement(name = "PaymentMethods")
public class PaymentMethods {
	
	protected List<PaymentMethod> PaymentMethod;
	 
	public List<PaymentMethod> getPaymentMethod() {
		if (PaymentMethod == null) {
			PaymentMethod = new ArrayList<PaymentMethod>();
		}
		return this.PaymentMethod;
	}
	
	public void setPaymentMethods(List<PaymentMethod> paymentMethod){
		this.PaymentMethod = paymentMethod;
	}
}
