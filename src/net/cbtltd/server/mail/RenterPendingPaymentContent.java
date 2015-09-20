package net.cbtltd.server.mail;

import static net.cbtltd.server.mail.TextHelper.AMOUNT_TWO_DECIMALS;
import static net.cbtltd.server.mail.TextHelper.DATE;
import static net.cbtltd.server.mail.TextHelper.INTEGER;
import static net.cbtltd.server.mail.TextHelper.NEW_LINE;
import static net.cbtltd.server.mail.TextHelper.STRING;
import static net.cbtltd.server.mail.TextHelper.TAB;

import java.util.Date;

public class RenterPendingPaymentContent {
	
	// Content variables
	private String propertyAddress = null;
	private Date fromDate = null;
	private Date toDate = null;
	private String channelPartnerName = null;
	private Double remainingBalance = null;
	private Integer partialIin = null;
	private Date bookingDate = null;
	private String propertyManagerName = null;
	private String propertyManagerPhone = null;
	private String propertyManagerEmail = null;
	
	// Content
	private static String successfulSubject = "Balance charged for property at " + STRING + " for " + DATE + " to " + DATE;
	
	private static String failedSubject = "Reservation pending Cancellation for property at " + STRING + " for " + DATE + " to " + DATE;
	
	private String chargedPart = "On behalf of " + STRING + " we would like to confirm that the remaining balance of " + AMOUNT_TWO_DECIMALS + " was charged " +
			"to your credit card ending in " + INTEGER + " for the reservation you made on " + DATE + "." +
			" This is for the property located at " + STRING + ", checking in on " + DATE + " and checking out at " + DATE + "." + NEW_LINE + NEW_LINE +    
			"If you have any questions or need to change your plans please call the property manager directly." + NEW_LINE;
	
	private String unableChargePart = "On behalf of " + STRING + " we would like to inform you that the remaining balance of " +
			AMOUNT_TWO_DECIMALS + " was unable to be charged to your credit card ending in " + INTEGER + " for the reservation you made on " + DATE + ". " +
			"This is for the property located at " + STRING + ", checking in on " + DATE + " and checking out at " + DATE + ". " +
			"This reservation is pending cancellation. " +
			"Please call or email the property manager immediately to insure that you do not lose this reservation." + NEW_LINE;
	
	private String propertyManagerInfoPart = 
			TAB + "Property Manager Name: " + STRING + NEW_LINE +
			TAB + "Property Manager Phone Number: " + STRING + NEW_LINE +
			TAB + "Property Manager Email address: " + STRING + NEW_LINE;
	
	private String thanksPart = "We would like to thank you for booking this property at " + STRING + "." + NEW_LINE + NEW_LINE +
		"Thank You" + NEW_LINE +
		"Your support team at " + STRING;
	
	// builders

	public String buildSuccessfulSubject() {
		return String.format(successfulSubject, getPropertyAddress(), getFromDate(), getToDate());
	}
	
	public String buildFailedSubject() {
		return String.format(failedSubject, getPropertyAddress(), getFromDate(), getToDate());
	}
	
	public String buildChargedPart() {
		return String.format(chargedPart, getChannelPartnerName(), getRemainingBalance(), getPartialIin(), getBookingDate(), getPropertyAddress(), getFromDate(), getToDate());
	}
	
	public String buildUnableChargePart() {
		return String.format(unableChargePart, getChannelPartnerName(), getRemainingBalance(), getPartialIin(), getBookingDate(), getPropertyAddress(), getFromDate(), getToDate());
	}
	
	public String buildPropertyManagerInfoPart() {
		return String.format(propertyManagerInfoPart, getPropertyManagerName(), getPropertyManagerPhone(), getPropertyManagerEmail());
	}
	
	public String buildThanksPart() {
		return String.format(thanksPart, getPropertyAddress(), getChannelPartnerName());
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

	public String getChannelPartnerName() {
		return channelPartnerName;
	}

	public void setChannelPartnerName(String channelPartnerName) {
		this.channelPartnerName = channelPartnerName;
	}

	public Double getRemainingBalance() {
		return remainingBalance;
	}

	public void setRemainingBalance(Double remainingBalance) {
		this.remainingBalance = remainingBalance;
	}

	public Integer getPartialIin() {
		return partialIin;
	}

	public void setPartialIin(Integer partialIin) {
		this.partialIin = partialIin;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getPropertyManagerName() {
		return propertyManagerName;
	}

	public void setPropertyManagerName(String propertyManagerName) {
		this.propertyManagerName = propertyManagerName;
	}

	public String getPropertyManagerPhone() {
		return propertyManagerPhone;
	}

	public void setPropertyManagerPhone(String propertyManagerPhone) {
		this.propertyManagerPhone = propertyManagerPhone;
	}

	public String getPropertyManagerEmail() {
		return propertyManagerEmail;
	}

	public void setPropertyManagerEmail(String propertyManagerEmail) {
		this.propertyManagerEmail = propertyManagerEmail;
	}
	
	public boolean isSuccessfulMailComplete() {
		return !(propertyAddress == null ||
		fromDate == null ||
		toDate == null ||
		channelPartnerName == null ||
		remainingBalance == null ||
		partialIin == null ||
		bookingDate == null ||
		propertyManagerName == null ||
		propertyManagerPhone == null ||
		propertyManagerEmail == null);
	}
	
	public boolean isFailedMailComplete() {
		return !(propertyAddress == null ||
		fromDate == null ||
		toDate == null ||
		channelPartnerName == null ||
		remainingBalance == null ||
		partialIin == null ||
		bookingDate == null ||
		propertyManagerName == null ||
		propertyManagerPhone == null ||
		propertyManagerEmail == null);
	}
	
	public boolean isSuccessfulSubjectComplete() {
		return !(propertyAddress == null ||
		fromDate == null ||
		toDate == null);
	}
	
	public boolean isFailedSubjectIncomplete() {
		return !(propertyAddress == null ||
		fromDate == null ||
		toDate == null);
	}
}
