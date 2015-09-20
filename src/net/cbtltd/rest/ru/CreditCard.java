package net.cbtltd.rest.ru;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	    "CCNumber",
	    "CVC",
	    "NameOnCard",
	    "Expiration",
	    "BillingAddress",
	    "CardType",
	    "Comments"
	})
@XmlRootElement(name = "CreditCard")
public class CreditCard {
	
	public static final String VISA = "VISA";
	public static final String MASTERCARD = "MASTERCARD";
	public static final String AMEX = "AMEX";
	public static final String DISCOVER = "DISCOVER";
	public static final String DINERS = "DINERS";
	public static final String CARTE_BLANCHE = "CARTE_BLANCHE";
	public static final String JCB = "JCB";
	public static final String ENROUTE = "ENROUTE";
	public static final String JAL = "JAL";
	public static final String MAESTRO_UK = "MAESTRO_UK";
	public static final String DELTA = "DELTA";
	public static final String SOLO = "SOLO";
	public static final String VISA_ELECTRON = "VISA_ELECTRON";
	public static final String DANKORT = "DANKORT";
	public static final String LASER = "LASER";
	public static final String CARTE_BLEU = "CARTE_BLEU";
	public static final String CARTA_SI = "CARTA_SI";
	public static final String MAESTRO_INTERNATIO = "MAESTRO_INTERNATIO";
	
	public static final String[] VALUES = {VISA, MASTERCARD, AMEX, DISCOVER, DINERS, CARTE_BLANCHE, JCB, ENROUTE, JAL, MAESTRO_UK, DELTA, SOLO, VISA_ELECTRON, DANKORT, LASER, CARTE_BLEU, CARTA_SI, MAESTRO_INTERNATIO};
	
	@XmlElement(required = true)
    protected String CCNumber;
	@XmlElement
    protected String CVC;
	@XmlElement(required = true)
    protected String NameOnCard;
	@XmlElement(required = true)
    protected String Expiration;
	@XmlElement
    protected String BillingAddress;
	@XmlElement(required = true)
    protected String CardType;
	@XmlElement
    protected String Comments;
	
	/**
	 * @return the Credit Card number
	 */
	public String getCCNumber() {
		return CCNumber;
	}
	
	/**
	 * set the Credit Card number
	 */
	public void setCCNumber(String cCNumber) {
		CCNumber = cCNumber;
	}
	
	/**
	 * @return the Credit Card security code
	 */
	public String getCVC() {
		return CVC;
	}
	
	/**
	 * set the Credit Card security code
	 */
	public void setCVC(String cVC) {
		CVC = cVC;
	}
	
	/**
	 * @return the Credit Card holder name
	 */
	public String getNameOnCard() {
		return NameOnCard;
	}
	
	/**
	 * set the Credit Card holder name
	 */
	public void setNameOnCard(String nameOnCard) {
		NameOnCard = nameOnCard;
	}
	
	/**
	 * @return the Credit Card expiration date (MM/YYYY format)
	 */
	public String getExpiration() {
		return Expiration;
	}
	
	/**
	 * set the Credit Card expiration date (MM/YYYY format)
	 */
	public void setExpiration(String expiration) {
		Expiration = expiration;
	}
	
	/**
	 * @return the billing address for the customer
	 */
	public String getBillingAddress() {
		return BillingAddress;
	}
	
	/**
	 * set the billing address for the customer
	 */
	public void setBillingAddress(String billingAddress) {
		BillingAddress = billingAddress;
	}
	
	/**
	 * @return the Credit Card provider
	 */
	public String getCardType() {
		return CardType;
	}
	
	/**
	 * set the Credit Card provider
	 */
	public void setCardType(String cardType) {
		CardType = cardType;
	}
	
	/**
	 * @return the additional comments for Credit Card
	 */
	public String getComments() {
		return Comments;
	}
	
	/**
	 * set the additional comments for Credit Card
	 */
	public void setComments(String comments) {
		Comments = comments;
	}
	
}
