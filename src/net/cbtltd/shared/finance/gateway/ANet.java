package net.cbtltd.shared.finance.gateway;

import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.api.HasService;

@Deprecated
public class ANet implements HasService {
	private String id;
	private String token;
	private CreditCard creditCard;
//	private String relayResponseUrl = "http://MERCHANT_HOST/relay_response.jsp";

	public ANet() {
		setId("47R5Rjv2PP");
		setToken("544j66HY9seWAv4k");
		creditCard = new CreditCard();
		creditCard.setNumber("4111 1111 1111 1111");
		creditCard.setMonth("12");
		creditCard.setYear("2015");
	}
	
	public ANet(String apiLoginId, String transactionKey, CreditCard creditCard) {
		setId(apiLoginId);
		setToken(transactionKey);
		setCreditCard(creditCard);
	}
	
	@Override
	public Service service() {return Service.FINANCE;}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String transactionKey) {
		this.token = transactionKey;
	}

	public net.cbtltd.shared.finance.gateway.CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(net.cbtltd.shared.finance.gateway.CreditCard creditCard) {
		this.creditCard = creditCard;
	}
}
