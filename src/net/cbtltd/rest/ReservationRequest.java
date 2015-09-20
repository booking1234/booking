package net.cbtltd.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

@XmlRootElement(name = "reservation")
@XmlAccessorType(XmlAccessType.FIELD)
@Description(value = "Reservation of a property", target = DocTarget.RESPONSE)
public class ReservationRequest {

	public ReservationRequest() {
		super();
	}

	@XmlElement(name="pos")
	private String pos;
	@XmlElement(name="productid")
	private String productId;
	@XmlElement(name="fromdate")
	private String fromDate;
	@XmlElement(name="todate")
	private String toDate;
	@XmlElement(name="emailaddress")
	private String emailAddress;
	@XmlElement(name="familyname")
	private String familyName;
	@XmlElement(name="firstname")
	private String firstName;
	@XmlElement(name="notes")
	private String notes;
	@XmlElement(name="cardnumber")
	private String cardNumber;
	@XmlElement(name="cardmonth")
	private String cardMonth;
	@XmlElement(name="cardyear")
	private String cardYear;
	@XmlElement(name="cc_security_code")
	private Integer cvc;
	@XmlElement(name="cc_address")
	private String address;
	@XmlElement(name="cc_country")
	private String country;
	@XmlElement(name="cc_state")
	private String state;
	@XmlElement(name="cc_city")
	private String city;
	@XmlElement(name="cc_zip")
	private String zip;
	@XmlElement(name="amount")
	private String amount;
	@XmlElement(name="currency")
	private String currency;
	@XmlElement(name="telnumber")
	private String phoneNumber;
	@XmlElement(name="cardtype")
	private Integer cardType;
	@XmlElement(name="cc_bdd")
	private Integer birthDay;
	@XmlElement(name="cc_bdm")
	private Integer birthMonth;
	@XmlElement(name="cc_bdy")
	private Integer birthYear;
	@XmlElement(name="adult")
	private Integer adult;
	@XmlElement(name="child")
	private Integer child;
	@XmlElement(name="altId")
	private String altId;

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardMonth() {
		return cardMonth;
	}

	public void setCardMonth(String cardMonth) {
		this.cardMonth = cardMonth;
	}

	public String getCardYear() {
		return cardYear;
	}

	public void setCardYear(String cardYear) {
		this.cardYear = cardYear;
	}

	public Integer getCvc() {
		return cvc;
	}

	public void setCvc(Integer cvc) {
		this.cvc = cvc;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public Integer getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Integer birthDay) {
		this.birthDay = birthDay;
	}

	public Integer getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(Integer birthMonth) {
		this.birthMonth = birthMonth;
	}

	public Integer getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}

	public Integer getAdult() {
		return adult;
	}

	public void setAdult(Integer adult) {
		this.adult = adult;
	}

	public Integer getChild() {
		return child;
	}

	public void setChild(Integer child) {
		this.child = child;
	}

	public String getAltId() {
		return altId;
	}

	public void setAltId(String altId) {
		this.altId = altId;
	}
}