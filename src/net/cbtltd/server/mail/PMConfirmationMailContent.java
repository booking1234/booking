package net.cbtltd.server.mail;

import static net.cbtltd.server.mail.TextHelper.AMOUNT_TWO_DECIMALS;
import static net.cbtltd.server.mail.TextHelper.DATE;
import static net.cbtltd.server.mail.TextHelper.INTEGER;
import static net.cbtltd.server.mail.TextHelper.NEW_LINE;
import static net.cbtltd.server.mail.TextHelper.STRING;
import static net.cbtltd.server.mail.TextHelper.TAB;

import java.util.Date;

public class PMConfirmationMailContent {
	
	public PMConfirmationMailContent() {
		super();
	}
	
	private String propertyAddress = null;
	private Date arrivalDate = null;
	private Date departureDate = null;
	private String propertyAltId = null;
	private String productId = null;
//	private String pmsConfirmationId = null;
//	private String confirmationId = null;
	private String channelPartnerName = null;
	private Double totalAmount = null;
	private Double firstPayment = null;
	private Double commissionPercentage = null;
	private Double commissionAmount = null;
	private String currency = null;
	private Double creditCardFee = null;
	private Double totalFees = null;
	private Double secondPayment = null;
	private Date secondPaymentDate = null;
	private Integer lastFour = null;
	private String renterFirstName = null;
	private String renterLastName = null;
	private String renterEmail = null;
	private String renterPhone = null;
	private String notes = null;
	
	public static String propertyManagerPropertyInfoPart = 
			"Your property at " + STRING + " has been booked from " + DATE + " to " + DATE + ", here are the details for this booking:" + NEW_LINE + NEW_LINE + NEW_LINE +
			"Property Information" + NEW_LINE +
			TAB + "PMS Property ID: " + STRING + NEW_LINE +
			TAB + "Property Address: " + STRING  + NEW_LINE +
			TAB + "BookingPal Product ID: " + STRING + NEW_LINE +
//			TAB + "PMS Confirmation: - " + STRING + NEW_LINE +
//			TAB + "BookingPal Confirmation: " + STRING + NEW_LINE +
			TAB + "Channel Partner: " + STRING + NEW_LINE +
			TAB + "Reserved From: " + DATE + " to " + DATE + NEW_LINE + NEW_LINE + NEW_LINE;
			
	public static String propertyManagerTotalPaymentPart =		
			"Payment Details" + NEW_LINE +
			"Total amount of Booking: " + AMOUNT_TWO_DECIMALS + NEW_LINE;
	
	public static String propertyManagerDepositPart = "Deposit:" + NEW_LINE +
			TAB + "Down payment Collected:" + AMOUNT_TWO_DECIMALS + NEW_LINE;
	
	public static String propertyManagerPaymentDetailsPart = TAB + "Commission %%: " + AMOUNT_TWO_DECIMALS + NEW_LINE +
			TAB + "Commission amount for total Booking: " + AMOUNT_TWO_DECIMALS + NEW_LINE +
			TAB + "Currency: " + STRING + NEW_LINE +
			TAB + "Credit Card Fee: " + AMOUNT_TWO_DECIMALS + NEW_LINE +
			TAB + "All Fees: " + AMOUNT_TWO_DECIMALS + NEW_LINE;
	
	public static String propertyManagerCreditCardPart = TAB + "Last 4 credit card: " + INTEGER + NEW_LINE;
	
	public static String propertyManagerSecondPaymentInfoPart =
			TAB + "Balance to be charged: " + AMOUNT_TWO_DECIMALS + NEW_LINE + 
			TAB + "Balance to be charged on: " + DATE + NEW_LINE;
			
	public static String propertyManagerRenterInfoPart =
			"Renter Information" + NEW_LINE +
			TAB + "First Name: " + STRING + NEW_LINE +
			TAB + "Last Name: " + STRING + NEW_LINE +
			TAB + "Email Address: " + STRING + NEW_LINE +
			TAB + "Telephone Number: " + STRING + NEW_LINE + NEW_LINE + NEW_LINE;
	
	public static String additionalInfoPart = "Additional information from client: " + STRING + NEW_LINE + NEW_LINE;
			
	public static String propertyManagerThanksPart =
			"Thank You" + NEW_LINE +
			"BookingPal Support Team";
	
	public String buildPropertyInfoPart() {
		return String.format(propertyManagerPropertyInfoPart, getPropertyAddress(), getArrivalDate(), getDepartureDate(), getPropertyAltId(), getPropertyAddress(),
				getProductId(), /*getPmsConfirmationId(), getConfirmationId(), */getChannelPartnerName(), getArrivalDate(), getDepartureDate());
	}
	
	public String buildTotalPaymentPart() {
		return String.format(propertyManagerTotalPaymentPart, getTotalAmount());
	}
	
	public String buildDepositPart() {
		return String.format(propertyManagerDepositPart, getFirstPayment());
	}
	
	public String buildPaymentDetailsPart() {
		return String.format(propertyManagerPaymentDetailsPart, getCommissionPercentage(), getCommissionAmount(), getCurrency(), getCreditCardFee(), getTotalFees());
	}
	
	public String buildCreditCardPart() {
		return String.format(propertyManagerCreditCardPart,	getLastFour());
	}
	
	public String buildSecondPaymentInfoPart() {
		return String.format(propertyManagerSecondPaymentInfoPart, getSecondPayment(), getSecondPaymentDate());
	}
	
	public String buildRenterInfoPart() {
		if(getNotes() == null) {
			return String.format(propertyManagerRenterInfoPart, getRenterFirstName(), getRenterLastName(), getRenterEmail(), getRenterPhone());
		} else {
			return String.format(propertyManagerRenterInfoPart, getRenterFirstName(), getRenterLastName(), getRenterEmail(), getRenterPhone())
					+ String.format(additionalInfoPart, getNotes());
		}
	}
	
	public String buildThanksPart() {
		return String.format(propertyManagerThanksPart);
	}
	
	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public String getPropertyAltId() {
		return propertyAltId;
	}

	public void setPropertyAltId(String propertyAltId) {
		this.propertyAltId = propertyAltId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

//	public String getPmsConfirmationId() {
//		return pmsConfirmationId;
//	}
//
//	public void setPmsConfirmationId(String pmsConfirmationId) {
//		this.pmsConfirmationId = pmsConfirmationId;
//	}
//
//	public String getConfirmationId() {
//		return confirmationId;
//	}
//
//	public void setConfirmationId(String confirmationId) {
//		this.confirmationId = confirmationId;
//	}

	public String getChannelPartnerName() {
		return channelPartnerName;
	}

	public void setChannelPartnerName(String channelPartnerName) {
		this.channelPartnerName = channelPartnerName;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getFirstPayment() {
		return firstPayment;
	}

	public void setFirstPayment(Double firstPayment) {
		this.firstPayment = firstPayment;
	}

	public Double getCommissionPercentage() {
		return commissionPercentage;
	}

	public void setCommissionPercentage(Double commissionPercentage) {
		this.commissionPercentage = commissionPercentage;
	}

	public Double getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(Double commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getCreditCardFee() {
		return creditCardFee;
	}

	public void setCreditCardFee(Double creditCardFee) {
		this.creditCardFee = creditCardFee;
	}

	public Double getTotalFees() {
		return totalFees;
	}

	public void setTotalFees(Double totalFees) {
		this.totalFees = totalFees;
	}

	public Double getSecondPayment() {
		return secondPayment;
	}

	public void setSecondPayment(Double secondPayment) {
		this.secondPayment = secondPayment;
	}

	public Date getSecondPaymentDate() {
		return secondPaymentDate;
	}

	public void setSecondPaymentDate(Date secondPaymentDate) {
		this.secondPaymentDate = secondPaymentDate;
	}

	public Integer getLastFour() {
		return lastFour;
	}

	public void setLastFour(Integer lastFour) {
		this.lastFour = lastFour;
	}

	public String getRenterFirstName() {
		return renterFirstName;
	}

	public void setRenterFirstName(String renterFirstName) {
		this.renterFirstName = renterFirstName;
	}

	public String getRenterLastName() {
		return renterLastName;
	}

	public void setRenterLastName(String renterLastName) {
		this.renterLastName = renterLastName;
	}

	public String getRenterEmail() {
		return renterEmail;
	}

	public void setRenterEmail(String renterEmail) {
		this.renterEmail = renterEmail;
	}

	public String getRenterPhone() {
		return renterPhone;
	}

	public void setRenterPhone(String renterPhone) {
		this.renterPhone = renterPhone;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public boolean isDepositPaymentFieldsComplete() {
		return !(propertyAddress == null ||
		arrivalDate == null || 
		departureDate == null || 
		propertyAltId == null || 
		productId == null || 
//		pmsConfirmationId == null ||
//		confirmationId == null || 
		channelPartnerName == null || 
		totalAmount == null || 
		firstPayment == null || 
		commissionPercentage == null || 
		commissionAmount == null || 
		currency == null ||
		creditCardFee == null || 
		totalFees == null || 
		secondPayment == null || 
		secondPaymentDate == null || 
		lastFour == null || 
		renterFirstName == null || 
		renterLastName == null ||
		renterEmail == null || 
		renterPhone == null);
	}
	
	public boolean isFullPaymentFieldsComplete() {
		return !(propertyAddress == null || 
		arrivalDate == null || 
		departureDate == null || 
		propertyAltId == null ||
		productId == null || 
//		pmsConfirmationId == null ||
//		confirmationId == null || 
		channelPartnerName == null || 
		totalAmount == null || 
		commissionPercentage == null || 
		commissionAmount == null || 
		currency == null ||
		creditCardFee == null || 
		totalFees == null || 
		lastFour == null || 
		renterFirstName == null || 
		renterLastName == null || 
		renterEmail == null || 
		renterPhone == null);
	}
	
	public boolean isWithoutPaymentFieldsComplete() {
		return !(propertyAddress == null || 
				arrivalDate == null || 
				departureDate == null || 
				propertyAltId == null ||
				productId == null || 
//				pmsConfirmationId == null ||
//				confirmationId == null || 
				channelPartnerName == null || 
				totalAmount == null || 
				commissionPercentage == null || 
				commissionAmount == null || 
				currency == null ||
				creditCardFee == null || 
				totalFees == null || 
				renterFirstName == null || 
				renterLastName == null || 
				renterEmail == null || 
				renterPhone == null);
	}
}
