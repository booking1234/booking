package net.cbtltd.shared.finance.gateway;

import com.paypal.sdk.exceptions.PayPalException;
import net.authorize.data.creditcard.CardType;

public class CreditCard {
	private CreditCardType type;
	private String number;
	private String firstName;
	private String lastName;
	private String month;
	private String year;
	private String securityCode;
	
	public CreditCard() {
		super();
	}

	public String getSecurityCode(){
		return securityCode;
	}
	
	public void setSecurityCode(String securityCode){
		this.securityCode = securityCode;
	}
	
	public CreditCardType getType() {
		return type;
	}

	public void setType(CreditCardType type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public com.paypal.api.payments.CreditCard getPaypalCard() throws java.lang.Exception {
		com.paypal.api.payments.CreditCard creditCard = new com.paypal.api.payments.CreditCard();
		creditCard.setType(getPaypalCardType());
		creditCard.setNumber(number);
		creditCard.setExpireMonth(new Integer(month));
		creditCard.setExpireYear(new Integer(year));
		creditCard.setFirstName(firstName);
		creditCard.setLastName(lastName); 
		creditCard.setCvv2(securityCode);
		return creditCard;
	}
	
	public net.authorize.data.creditcard.CreditCard getANetCard() throws java.lang.Exception {
		net.authorize.data.creditcard.CreditCard creditCard = net.authorize.data.creditcard.CreditCard.createCreditCard();
		creditCard.setCreditCardNumber(number);
		creditCard.setExpirationMonth(month);
		creditCard.setExpirationYear(year);
		creditCard.setCardType(getANetType());
		creditCard.setCardCode(securityCode);
		return creditCard;
	}
	
	private String getPaypalCardType() throws PayPalException {
		switch(type) {
		case MASTER_CARD :
			return "mastercard";
		case VISA :
			return "visa";
		default :
			throw new PayPalException() {
				@Override
				public String getMessage() {
					return "Unsupported credit card type";
				}
			};
		}
	}
	
	private CardType getANetType() throws java.lang.Exception {
		switch(type) {
		case MASTER_CARD :
			return CardType.findByValue("MasterCard");
		case VISA :
			return CardType.findByValue("Visa");
		default :
			throw new java.lang.Exception("Unsupported credit card type");
		}
	}
	
	public String getRentCardType() throws java.lang.Exception {
		switch(type) {
		case MASTER_CARD :
			return "MC";
		case VISA :
			return "Visa";
		case DISCOVER :
			return "Discover";
		default :
			throw new java.lang.Exception("Unsupported credit card type");
		}
	}
}
