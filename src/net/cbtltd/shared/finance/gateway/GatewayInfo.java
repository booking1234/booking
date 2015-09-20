package net.cbtltd.shared.finance.gateway;

import net.cbtltd.server.project.PaymentConfiguration;
import net.cbtltd.shared.finance.gateway.CreditCard;

import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.api.HasService;

public class GatewayInfo implements HasService {

	private String id;
	private String token;
	private String additional;
	private CreditCard creditCard;
	
	@Override
	public Service service() {return Service.FINANCE;}
	
	public GatewayInfo() { // test constructor for PayPal
		setId("EOJ2S-Z6OoN_le_KS1d75wsZ6y0SFdVsY9183IvxFyZp");
		setToken("EClusMEUk8e9ihI7ZdVLF5cZ6y0SFdVsY9183IvxFyZp");
		creditCard = new CreditCard();
		creditCard.setType(CreditCardType.VISA);
		creditCard.setNumber("4446283280247004");
		creditCard.setMonth("11");
		creditCard.setYear("2018");
		creditCard.setFirstName("Joe");
		creditCard.setLastName("Shopper");
	}
	
	public GatewayInfo(CreditCardType cardType, String cardNumber, String cardExpiryMonth, String cardExpiryYear, String cardFirstName, String cardLastName, String securityCode) {
		CreditCard creditCard = new CreditCard();
		creditCard.setType(cardType);
		creditCard.setNumber(cardNumber);
		creditCard.setMonth(cardExpiryMonth);
		creditCard.setYear(cardExpiryYear);
		creditCard.setFirstName(cardFirstName);
		creditCard.setLastName(cardLastName);
		creditCard.setSecurityCode(securityCode);
		
		setCreditCartd(creditCard);
	}
	
	public GatewayInfo(ManagerToGateway managerToGateway, CreditCard creditCard) {
		if(managerToGateway.getPaymentGatewayId() == PaymentGatewayHolder.getBookingPal().getId()) {
			this.setId(PaymentConfiguration.getInstance().getGatewayId());
			this.setToken(PaymentConfiguration.getInstance().getGatewayToken());
		} else {
			this.setId(managerToGateway.getGatewayAccountId());
			this.setToken(managerToGateway.getGatewayCode());
			this.setAdditional(managerToGateway.getAdditionalInfo());
		}
		setCreditCartd(creditCard);
	}
	
	public GatewayInfo(ManagerToGateway managerToGateway, CreditCardType cardType, String cardNumber, String cardExpiryMonth, String cardExpiryYear, String cardFirstName, String cardLastName) {
		if(managerToGateway.getPaymentGatewayId() == PaymentGatewayHolder.getBookingPal().getId()) {
			this.setId(PaymentConfiguration.getInstance().getGatewayId());
			this.setToken(PaymentConfiguration.getInstance().getGatewayToken());
		} else {
			this.setId(managerToGateway.getGatewayAccountId());
			this.setToken(managerToGateway.getGatewayCode());
			this.setAdditional(managerToGateway.getAdditionalInfo());
		}
		CreditCard creditCard = new CreditCard();
		creditCard.setType(cardType);
		creditCard.setNumber(cardNumber);
		creditCard.setMonth(cardExpiryMonth);
		creditCard.setYear(cardExpiryYear);
		creditCard.setFirstName(cardFirstName);
		creditCard.setLastName(cardLastName);
		
		setCreditCartd(creditCard);
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCartd(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAdditional() {
		return additional;
	}

	public void setAdditional(String additional) {
		this.additional = additional;
	}
}
