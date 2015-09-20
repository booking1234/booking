package net.cbtltd.rest.rent.paymentrequest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CreditCardPayment")
public class CreditCardPayment {
	// required fields
	private String username;
	private String password;
	private String propertyCode;
	private String number;
	private String expiration;
	private String cardholder;
	private String type;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String country;
	private String phone;
	private String email;
	private Integer amount;
	private String id;
	private String personFirstname;
	private String personLastname;

	// optional fields
	private String propertyType;
	private String accountNumber;
	private String personUsername;
	private String arrivalDate;
	private String departureDate;
	private String ipAddress;
	private Integer damageDeposit;
	private Integer totalInstallments;
	private Integer installmentNumber;
	private RoomRate roomRate;
	private RoomTax roomTax;
	private String cvv;
	private String preAuthorization;
	private String reservationNumber;
	private String unit;
	private String magneticStrip;
	private String guid;
	private String customCreditCardDesc;
	private String customPhoneCity;
	private Boolean returnToken;

	public CreditCardPayment() {
		super();
	}

	@XmlElement(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@XmlElement(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElement(name = "propertyCode")
	public String getPropertyCode() {
		return propertyCode;
	}

	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	}

	@XmlElement(name = "number")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@XmlElement(name = "expiration")
	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}

	@XmlElement(name = "cardholder")
	public String getCardholder() {
		return cardholder;
	}

	public void setCardholder(String cardholder) {
		this.cardholder = cardholder;
	}

	@XmlElement(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlElement(name = "street")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@XmlElement(name = "city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@XmlElement(name = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@XmlElement(name = "zip")
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@XmlElement(name = "country")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@XmlElement(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@XmlElement(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement(name = "amount")
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@XmlElement(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "personFirstname")
	public String getPersonFirstname() {
		return personFirstname;
	}

	public void setPersonFirstname(String personFirstname) {
		this.personFirstname = personFirstname;
	}

	@XmlElement(name = "personLastname")
	public String getPersonLastname() {
		return personLastname;
	}

	public void setPersonLastname(String personLastname) {
		this.personLastname = personLastname;
	}

	@XmlElement(name = "propertyType")
	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	@XmlElement(name = "accountNumber")
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@XmlElement(name = "personUsername")
	public String getPersonUsername() {
		return personUsername;
	}

	public void setPersonUsername(String personUsername) {
		this.personUsername = personUsername;
	}

	@XmlElement(name = "arrivalDate")
	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	@XmlElement(name = "departureDate")
	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	@XmlElement(name = "ipAddress")
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@XmlElement(name = "damageDeposit")
	public Integer getDamageDeposit() {
		return damageDeposit;
	}

	public void setDamageDeposit(Integer damageDeposit) {
		this.damageDeposit = damageDeposit;
	}

	@XmlElement(name = "totalInstallments")
	public Integer getTotalInstallments() {
		return totalInstallments;
	}

	public void setTotalInstallments(Integer totalInstallments) {
		this.totalInstallments = totalInstallments;
	}

	@XmlElement(name = "installmentNumber")
	public Integer getInstallmentNumber() {
		return installmentNumber;
	}

	public void setInstallmentNumber(Integer installmentNumber) {
		this.installmentNumber = installmentNumber;
	}

	@XmlElement(name = "roomRate")
	public RoomRate getRoomRate() {
		return roomRate;
	}

	public void setRoomRate(RoomRate roomRate) {
		this.roomRate = roomRate;
	}

	@XmlElement(name = "roomTax")
	public RoomTax getRoomTax() {
		return roomTax;
	}

	public void setRoomTax(RoomTax roomTax) {
		this.roomTax = roomTax;
	}

	@XmlElement(name = "cvv")
	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	@XmlElement(name = "preAuthorization")
	public String getPreAuthorization() {
		return preAuthorization;
	}

	public void setPreAuthorization(String preAuthorization) {
		this.preAuthorization = preAuthorization;
	}

	@XmlElement(name = "reservationNumber")
	public String getReservationNumber() {
		return reservationNumber;
	}

	public void setReservationNumber(String reservationNumber) {
		this.reservationNumber = reservationNumber;
	}

	@XmlElement(name = "unit")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@XmlElement(name = "magneticStrip")
	public String getMagneticStrip() {
		return magneticStrip;
	}

	public void setMagneticStrip(String magneticStrip) {
		this.magneticStrip = magneticStrip;
	}

	@XmlElement(name = "GUID")
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@XmlElement(name = "customCreditCardDesc")
	public String getCustomCreditCardDesc() {
		return customCreditCardDesc;
	}

	public void setCustomCreditCardDesc(String customCreditCardDesc) {
		this.customCreditCardDesc = customCreditCardDesc;
	}

	@XmlElement(name = "customPhoneCity")
	public String getCustomPhoneCity() {
		return customPhoneCity;
	}

	public void setCustomPhoneCity(String customPhoneCity) {
		this.customPhoneCity = customPhoneCity;
	}

	@XmlElement(name = "ReturnToken")
	public Boolean getReturnToken() {
		return returnToken;
	}

	public void setReturnToken(Boolean returnToken) {
		this.returnToken = returnToken;
	}
}
