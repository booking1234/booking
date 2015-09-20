package net.cbtltd.rest.rent.paymentresponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CreateSimpleCreditCardTokenResponse")
public class CreateSimpleCreditCardTokenResponse {
	private String token;

	@XmlElement(name="token")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
