package net.cbtltd.server.mail;

import static net.cbtltd.server.mail.TextHelper.AMOUNT_TWO_DECIMALS;
import static net.cbtltd.server.mail.TextHelper.DATE;
import static net.cbtltd.server.mail.TextHelper.INTEGER;
import static net.cbtltd.server.mail.TextHelper.NEW_LINE;
import static net.cbtltd.server.mail.TextHelper.STRING;
import static net.cbtltd.server.mail.TextHelper.TIME;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;

import net.cbtltd.json.JSONService;
import net.cbtltd.rest.response.CancellationItem;
import net.cbtltd.server.config.RazorHostsConfig;

public class RenterConfirmationMailContent {
	
	public RenterConfirmationMailContent() {
		super();
	}
	
	private String channelPartnerName = null;
	private Date bookingCreated = null;
	private String propertyAddress = null;
	private Date arrivalDate = null;
	private Date departureDate = null;
	private Date checkInTime = null;
	private Date checkOutTime = null;
	private Integer lastFour = null;
	private Double firstPayment = null;
	private String currency = null;
	private Double secondPayment = null;
	private Date secondPaymentDate = null;
	private String propertyManagerName = null;
	private String propertyManagerPhone = null;
	private String propertyManagerEmail = null;
	private Double totalCharge = null;
	private Double securityDeposit = null;
	private Double totalFees = null;
	private String reservationPos = null;
	private Set<CancellationItem> cancellationItems = null;
	
	public static String renterGreetingsPart = "On behalf of " + STRING + " we would like to confirm the booking that you have made on " + DATE + 
			" for the property located at " + STRING + ". We have you checking in on " + DATE + " " + TIME + " and checking out on " + DATE + " " + TIME; 

	public static String renterFirstPaymentPart = ". We have charged your credit card ending in " + INTEGER + " with " + AMOUNT_TWO_DECIMALS + " " + STRING + "." + NEW_LINE; 
	public static String renterSecondPaymentPart = " The balance of " + AMOUNT_TWO_DECIMALS + " " + STRING + " will be charged on " + DATE + "." + NEW_LINE;

	public static String renterContactPMPart = "If you have any questions or need to change your plans please call the property manager directly." + NEW_LINE + NEW_LINE +	
			"Property Manager Name: " + STRING + NEW_LINE +
			"Property Manager Phone Number: " + STRING + NEW_LINE +
			"Property Manager Email address: " + STRING + NEW_LINE;
			
	public static String renterPaymentInfoPart = "Here is a breakdown of your charges:" + NEW_LINE +
			"Total Charge: " + AMOUNT_TWO_DECIMALS + NEW_LINE +
			"Security Deposit: " + AMOUNT_TWO_DECIMALS + NEW_LINE;
			
	public static String renterDownPaymentPart = "Down payment: " + AMOUNT_TWO_DECIMALS + NEW_LINE;
	
	public static String renterFeesPart = "Fees: " + AMOUNT_TWO_DECIMALS + NEW_LINE + NEW_LINE;
	
	public static String renterCancellationPart = "You have following cancellation possibilities: " + NEW_LINE + NEW_LINE;
	
	public static String renterCancellationLink = "To Cancel your reservation please click on this link: " + RazorHostsConfig.getCancellationLink() + STRING + NEW_LINE;
			
	public static String renterThanksMessagePart = "We would like to thank you for booking this property at " + STRING + NEW_LINE + NEW_LINE +
			"Thanks You" + NEW_LINE +
			"Your support team at " + STRING;
	
	// Mail confirmation

	public static String renterMailWithoutPayment = "On behalf of " + STRING + " we would like to tentatively confirm the booking that you have made on " + DATE +
			" for the property located at " + STRING + ". We have you checking in on " + DATE + " " + TIME + " and checking out on " + DATE + " " + TIME + ". You will be contacted by " +
			"the property manager with a confirmation for this reservation." + NEW_LINE + NEW_LINE +
			
			"If you have any questions or need to change your plans please call the property manager directly." + NEW_LINE + NEW_LINE +  
 
			"Property Manager Name:" + STRING + NEW_LINE +
			"Property Manager Phone Number:" + STRING + NEW_LINE +
			"Property Manager Email address:" + STRING + NEW_LINE + NEW_LINE +
			 
			"Thank You" + NEW_LINE +
			"Your support team at BookingPal";

	public String buildMailWithoutPayment() {
		return String.format(renterMailWithoutPayment, getChannelPartnerName(), getBookingCreated(), getPropertyAddress(), getArrivalDate(), getCheckInTime(), getDepartureDate(), getCheckOutTime(),
				getPropertyManagerName(), getPropertyManagerPhone(), getPropertyManagerEmail());
	}
	
	public String buildGreetingPart() {
		return String.format(renterGreetingsPart, getChannelPartnerName(), getBookingCreated(), getPropertyAddress(), getArrivalDate(), getCheckInTime(), getDepartureDate(), getCheckOutTime());
	}
	
	public String buildFirstPaymentPart() {
		return String.format(renterFirstPaymentPart, getLastFour(), getFirstPayment(), getCurrency());
	}
	
	public String buildSecondPaymentPart() {
		return String.format(renterSecondPaymentPart, getSecondPayment(), getCurrency(), getSecondPaymentDate());
	}
	
	public String buildContactPMPart() {
		return String.format(renterContactPMPart, getPropertyManagerName(), getPropertyManagerPhone(), getPropertyManagerEmail());
	}
	
	public String buildPaymentInfoPart() {
		return String.format(renterPaymentInfoPart, getTotalCharge(), getSecurityDeposit());
	}
	
	public String buildDownPaymentPart() {
		return String.format(renterDownPaymentPart, getFirstPayment());
	}
	
	public String buildFeesPart() {
		return String.format(renterFeesPart, getTotalFees());
	}
	
	public String buildCancellationPart() throws ParseException {
		String cancellationPart = renterCancellationPart;
		int i = 0;
		for(CancellationItem item : getCancellationItems()) {
			if(item.getCancellationPercentage() != 0.) { 
				String transactionFeePart = item.getTransactionFee() != null || item.getTransactionFee() == 0. ? "" : " less a " + AMOUNT_TWO_DECIMALS + " " + STRING + " processing fee";
				if(!transactionFeePart.equals("")) {
					transactionFeePart = String.format(transactionFeePart, item.getTransactionFee(), getCurrency());
				}
				cancellationPart += String.format("If you cancel before " + DATE + " you will get a " + INTEGER + "%%%% refund\n", JSONService.DF.parse(item.getCancellationDate()), item.getCancellationPercentage().intValue());
				i++;
			}
		}
		if(i <= 0) {
			return "";
		} else {
			return String.format(cancellationPart);
		}
	}
	
	public String buildCancellationLink() {
		return String.format(renterCancellationLink, getReservationPos());
	}
	
	public String buildThanksMessagePart() {
		return String.format(renterThanksMessagePart, getPropertyAddress(), getChannelPartnerName());
	}
	
	public String getChannelPartnerName() {
		return channelPartnerName;
	}

	public void setChannelPartnerName(String channelPartnerName) {
		this.channelPartnerName = channelPartnerName;
	}

	public Date getBookingCreated() {
		return bookingCreated;
	}

	public void setBookingCreated(Date bookingCreated) {
		this.bookingCreated = bookingCreated;
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

	public Date getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
	}

	public Date getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(Date checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public Integer getLastFour() {
		return lastFour;
	}

	public void setLastFour(Integer lastFour) {
		this.lastFour = lastFour;
	}

	public Double getFirstPayment() {
		return firstPayment;
	}

	public void setFirstPayment(Double firstPayment) {
		this.firstPayment = firstPayment;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public Double getTotalCharge() {
		return totalCharge;
	}

	public void setTotalCharge(Double totalCharge) {
		this.totalCharge = totalCharge;
	}

	public Double getSecurityDeposit() {
		return securityDeposit;
	}

	public void setSecurityDeposit(Double securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

	public Double getTotalFees() {
		return totalFees;
	}

	public void setTotalFees(Double totalFees) {
		this.totalFees = totalFees;
	}

	public Set<CancellationItem> getCancellationItems() {
		return cancellationItems;
	}

	public void setCancellationItems(Set<CancellationItem> cancellationItems) {
		this.cancellationItems = cancellationItems;
	}

	public String getReservationPos() {
		return reservationPos;
	}

	public void setReservationPos(String reservationPos) {
		this.reservationPos = reservationPos;
	}
	
	public boolean isDepositPaymentFieldsComplete() {
		return 	!(channelPartnerName == null || 
				bookingCreated == null ||
				propertyAddress == null ||
				arrivalDate == null ||
				departureDate == null ||
				lastFour == null ||
				firstPayment == null ||
				currency == null ||
				secondPayment == null ||
				secondPaymentDate == null ||
				propertyManagerName == null ||
				propertyManagerPhone == null ||
				propertyManagerEmail == null ||
				totalCharge == null ||
				securityDeposit == null ||
				totalFees == null ||
				cancellationItems == null ||
				reservationPos == null);
	}
	
	public boolean isFullPaymentFieldsComplete() {
		return 	!(channelPartnerName == null ||
				bookingCreated == null ||
				propertyAddress == null ||
				arrivalDate == null ||
				departureDate  == null ||
				lastFour == null ||
				firstPayment == null ||
				currency == null ||
				propertyManagerName == null || 
				propertyManagerPhone  == null ||
				propertyManagerEmail  == null ||
				totalCharge == null ||
				securityDeposit  == null ||
				totalFees == null ||
				cancellationItems == null ||
				reservationPos == null);
	}
	
	public boolean isWithoutPaymentFieldsComplete() {
		return 	!(channelPartnerName == null ||
				bookingCreated == null ||
				propertyAddress == null ||
				arrivalDate == null ||
				departureDate == null ||
				propertyManagerName == null ||
				propertyManagerPhone == null ||
				propertyManagerEmail == null ||
				reservationPos == null);
	}
}
