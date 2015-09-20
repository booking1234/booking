package net.cbtltd.server.mail;

import static net.cbtltd.server.mail.TextHelper.AMOUNT_TWO_DECIMALS;
import static net.cbtltd.server.mail.TextHelper.DATE;
import static net.cbtltd.server.mail.TextHelper.INTEGER;
import static net.cbtltd.server.mail.TextHelper.NEW_LINE;
import static net.cbtltd.server.mail.TextHelper.STRING;
import static net.cbtltd.server.mail.TextHelper.TAB;

import java.util.Date;

public class PMPendingPaymentMailContent {
	
	// Content variables
	private String propertyAddress = null;
	private Date fromDate = null;
	private Date toDate = null;
	private String altProductId = null;
	private String productId = null;
//	private String pmsConfirmationId = null;
//	private String confirmationId = null;
	private String channelPartnerName = null;
	private Double totalAmount = null;
	private Double totalCollected = null;
	private Double commissionPercentage = null;
	private Double commissionAmount = null;
	private String currency = null;
	private Double creditCardFee = null;
	private Integer partialIin = null;
	private String renterFirstName = null;
	private String renterLastName = null;
	private String renterEmail = null;
	private String renterPhone = null;
	private String additionalInfo = null;

	// Content
	private static String successfulSubject = "The remaining balance for the Property at " + STRING + " has been collected";
	
	private static String failedSubject = "Failed Collection - The remaining balance for the Property at " + STRING + " could not be collected";
	
	private static String remainingBalancePart = "The remaining balance for your property at " + STRING + 
			" that has been booked from " + DATE + " to " + DATE + ", has been collected. Here are the details for this booking:" + NEW_LINE + NEW_LINE;
	
	private static String unableCollectPart = "We have been unable to collect the remaining balance for your property at " + STRING + " that was booked" +
			" from " + DATE + " to " + DATE + "." +
			" The payment gateway provider as reported a failure when the collection was attempted with the credit card provided." +
			" Please contact the renter, here are the booking details:" + NEW_LINE + NEW_LINE;
	
	private static String propertyInfoPart = "Property Information" + NEW_LINE;
			
	private static String altProductIdString = TAB + "PMS Property ID: " + STRING + NEW_LINE;
	
	private static String propertyInfoDetailsPart = TAB + "BookingPal Product ID: " + STRING + NEW_LINE +
//			TAB + "PMS Confirmation: " + STRING + NEW_LINE +
//			TAB + "BookingPal Confirmation: " + STRING + NEW_LINE +
			TAB + "Channel Partner: " + STRING + NEW_LINE +
			TAB + "Reserved From: " + DATE + " to " + DATE + NEW_LINE + NEW_LINE;
	
	private static String paymentDetailsPart = "Payment Details" + NEW_LINE +
			TAB + "Total amount of Booking: " + AMOUNT_TWO_DECIMALS + NEW_LINE +
			TAB + "Total Collected: " + AMOUNT_TWO_DECIMALS + NEW_LINE +
			TAB + "Commission %%: " + AMOUNT_TWO_DECIMALS + NEW_LINE +
			TAB + "Commission amount for total Booking: " + AMOUNT_TWO_DECIMALS + NEW_LINE +
			TAB + "Currency: " + STRING + NEW_LINE +
			TAB + "Credit Card Fee: " + AMOUNT_TWO_DECIMALS + NEW_LINE +
			TAB + "Last 4 credit card: " + INTEGER + NEW_LINE + NEW_LINE;
	
	private static String renterInfoPart = "Renter Information:" + NEW_LINE +
			"First Name: " + STRING + NEW_LINE +
			"Last Name: " + STRING + NEW_LINE +
			"Email Address: " + STRING + NEW_LINE +
			"Telephone Number: " + STRING + NEW_LINE + NEW_LINE;
	
	private static String additionalInfoPart = "Additional information form client:"  + STRING + NEW_LINE + NEW_LINE;
	
	private static String thanksPart = "Thank You" + NEW_LINE +
			"BookingPal Support Team";

	public String buildSuccessfulSubject() {
		return String.format(successfulSubject, getPropertyAddress());
	}
	
	public String buildFailedSubject() {
		return String.format(failedSubject, getPropertyAddress());
	}
	
	public String buildRemainingBalancePart() {
		return String.format(remainingBalancePart, getPropertyAddress(), getFromDate(), getToDate());
	}
	
	public String buildUnableCollectPart() {
		return String.format(unableCollectPart, getPropertyAddress(), getFromDate(), getToDate());
	}
	
	public String buildPropertyInfoPart() {
		if(getAltProductId() == null || getAltProductId().equals("")) {
			return String.format(propertyInfoPart + propertyInfoDetailsPart, 
					getProductId(), /*getPmsConfirmationId(), getConfirmationId(), */getChannelPartnerName(), getFromDate(), getToDate());
		} else {
			return String.format(propertyInfoPart + altProductIdString + propertyInfoDetailsPart, 
					getAltProductId(), getProductId(), /*getPmsConfirmationId(), getConfirmationId(),*/ getChannelPartnerName(), getFromDate(), getToDate());
		}
	}
	
	public String buildPaymentDetailsPart() {
		return String.format(paymentDetailsPart, getTotalAmount(), getTotalCollected(), getCommissionPercentage(), getCommissionAmount(), getCurrency(), getCreditCardFee(), getPartialIin());
	}
	
	public String buildRenterInfoPart() {
		return String.format(renterInfoPart, getRenterFirstName(), getRenterLastName(), getRenterEmail(), getRenterPhone());
	}
	
	public String buildAdditionalInfoPart() {
		if(getAdditionalInfo() != null) {
			return String.format(additionalInfoPart, getAdditionalInfo());
		} else {
			return "";
		}
	}
	
	public String buildThanksPart() {
		return thanksPart;
	}
	
	// getters and setters
	
	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getAltProductId() {
		return altProductId;
	}

	public void setAltProductId(String altProductId) {
		this.altProductId = altProductId;
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

	public Double getTotalCollected() {
		return totalCollected;
	}

	public void setTotalCollected(Double totalCollected) {
		this.totalCollected = totalCollected;
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

	public Integer getPartialIin() {
		return partialIin;
	}

	public void setPartialIin(Integer partialIin) {
		this.partialIin = partialIin;
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

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	
	public boolean isSuccessfulSubjectComplete() {
		return propertyAddress != null;
	}
	
	public boolean isFailedSubjectComplete() {
		return propertyAddress != null;
	}
	
	public boolean isSuccessfulMailComplete() {
		return 	!(propertyAddress == null ||
				fromDate == null ||
				toDate == null ||
				productId == null ||
//				pmsConfirmationId == null ||
//				confirmationId == null ||
				channelPartnerName == null ||
				totalAmount == null ||
				totalCollected == null ||
				commissionPercentage == null ||
				commissionAmount == null ||
				currency == null ||
				creditCardFee == null ||
				partialIin == null ||
				renterFirstName == null ||
				renterLastName == null ||
				renterEmail == null ||
				renterPhone == null);
	}
	
	public boolean isFailedMailComplete() {
		return 	!(propertyAddress == null ||
				fromDate == null ||
				toDate == null ||
				productId == null ||
//				pmsConfirmationId == null ||
//				confirmationId == null ||
				channelPartnerName == null ||
				totalAmount == null ||
				totalCollected == null ||
				commissionPercentage == null ||
				commissionAmount == null ||
				currency == null ||
				creditCardFee == null ||
				partialIin == null ||
				renterFirstName == null ||
				renterLastName == null ||
				renterEmail == null ||
				renterPhone == null);
	}
}
