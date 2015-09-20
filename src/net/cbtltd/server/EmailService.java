/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.response.CancellationItem;
import net.cbtltd.server.api.ChannelPartnerMapper;
import net.cbtltd.server.api.CountryMapper;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.api.ManagerToGatewayMapper;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PaymentGatewayProviderMapper;
import net.cbtltd.server.api.PaymentTransactionMapper;
import net.cbtltd.server.api.PendingTransactionMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.config.RazorHostsConfig;
import net.cbtltd.server.mail.ConfirmationMailBuilderFactory;
import net.cbtltd.server.mail.PMConfirmationMailBuilder;
import net.cbtltd.server.mail.PMConfirmationMailContent;
import net.cbtltd.server.mail.PMPendingPaymentMailBuilder;
import net.cbtltd.server.mail.PMPendingPaymentMailContent;
import net.cbtltd.server.mail.RenterConfirmationMailBuilder;
import net.cbtltd.server.mail.RenterConfirmationMailContent;
import net.cbtltd.server.mail.RenterPendingPaymentBuilder;
import net.cbtltd.server.mail.RenterPendingPaymentContent;
import net.cbtltd.server.mail.TextHelper;
import net.cbtltd.server.util.CalendarUtil;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.shared.ChannelPartner;
import net.cbtltd.shared.Country;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Currencyrate;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.Mail;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.PaymentGatewayProvider;
import net.cbtltd.shared.PaymentTransaction;
import net.cbtltd.shared.PendingTransaction;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.shared.reservation.Brochure;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.mybookingpal.config.RazorConfig;

/**
 * The Class Email is to send email messages in response to reservation events.
 * TODO: Internationalize via Java resource bundles.
 * @see <pre>http://java.sun.com/developer/technicalArticles/Intl/ResourceBundles/</pre> 
 */
public class EmailService {

	private static final Logger LOG = Logger.getLogger(EmailService.class.getName());
	private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	private static final NumberFormat NF = new DecimalFormat("#0.00");
	
	private static final String DATE_FORMAT_PATTERN = "MMMM dd, yyyy";
	
	
	private static final String REGISTRATION_MAIL_SUBJECT = "Bookingpal and PMS registration information";
	private static final String REGISTRATION_MAIL_CONTENT = "On behalf of %s and Bookingpal we would like to welcome you to the Bookingpal platform. Your user credentials are provided below. "
			+ "If you have not completed your registration then please use the credentials to complete your registration process.<br><br><br>username: %s <br>password: %s <br><br>Click  %s to log-in."
			+ "<br><br><br>Thank you <br><br>BookingPal Support Team";

	/**
	 * Sends an email message to an affiliate with its affiliate code.
	 *
	 * @param action the specified affiliate.
	 */
	public static void affiliate(Party party) {
		Mail mail = new Mail();
		mail.setSubject("Razor Affiliate Code");
		StringBuilder sb = new StringBuilder();
		sb.append("Dear ").append(Party.getNaturalName(party.getName()));
		sb.append("\nThank you for registering as a Razor Affiliate");
		sb.append("\nYour affiliate code is ").append(Party.encrypt(party.getId()));
		sb.append("\nPlease go to " + RazorHostsConfig.getAffiliateHelpUrl() + " to learn how to use your code to earn affiliate fees.");
		mail.setContent(sb.toString());
		mail.setRecipients(party.getEmailaddress());
		MailService.send(mail);
	}
	
	/**
	 * Sends an email message to a guest with a URL to a brochure.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the brochure which is requested.
	 */
	public static void brochure(SqlSession sqlSession, Brochure action, String emailaddress) {
		LOG.debug("Brochure in " + emailaddress + ", " + action);
		Mail mail = new Mail();
		mail.setSubject("Accommodation Options from " + Party.getNaturalName(action.getActorname()));
		StringBuilder sb = new StringBuilder();
		sb.append(action.getNotes());
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		sb.append("Please go to " + RazorHostsConfig.getBrochureUrl() + action.getId() + " to see accommodation options sent to you by " + Party.getNaturalName(action.getActorname()));
		mail.setContent(sb.toString());
		mail.setRecipients(emailaddress);
		LOG.debug("Brochure out " + mail);
		MailService.send(mail);
	}

	/** 
	 * Sends an email message to a manager requesting cancellation of a reservation.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param reservation_form the reservation for which feedback is requested.
	 */
//	public static void canceledReservation(SqlSession sqlSession, String organizationid, Reservation action) {
//		
//		Mail mail = new Mail();
//		Party party = sqlSession.getMapper(PartyMapper.class).read(organizationid);
//		Reservation reservation = sqlSession.getMapper(ReservationMapper.class).exists(action);
//
//		if (reservation == null) {
//			mail.setSubject("Cancel reservation " + action.getName());
//			StringBuilder sb = new StringBuilder();
//			sb.append(party.getName())
//			.append(" has requested to cancel reservation ")
//			.append(action.getName())
//			.append("\nThis cannot be done because the reservation reference number is incorrect.")
//			.append("\n\nSent automatically from Razor - The Accommodation Marketplace");
//			mail.setContent(sb.toString());
//			mail.setRecipients(party.getEmailaddress() == null ? HasUrls.EMAIL_ADDRESS : party.getEmailaddress());
//		}
//		else {
//			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
//			Party supplier = sqlSession.getMapper(PartyMapper.class).read(reservation.getSupplierid());
//			if (party == null || supplier == null || product == null) {return;}
//
//			mail.setSubject("Cancel reservation " + reservation.getName());
//			StringBuilder sb = new StringBuilder();
//			sb.append(party.getName())
//			.append(" has requested you to cancel reservation ")
//			.append(reservation.getName())
//			.append(" for ")
//			.append(product.getName())
//			.append(" from ")
//			.append(reservation.getFromdate())
//			.append(" to ")
//			.append(reservation.getTodate())
//			.append("\nThis must be done by the manager because the reservation is not provisional.")
//			.append("\n\nSent automatically from Razor - The Accommodation Marketplace");
//			mail.setContent(sb.toString());
//			mail.setRecipients(supplier.getEmailaddress() == null ? HasUrls.EMAIL_ADDRESS : supplier.getEmailaddress());
//		}
//		MailService.send(mail);
//	}
	
//	public static void main(String[] args) {
//		SqlSession sqlSession = RazorServer.openSession();
//		PaymentTransaction paymentTransaction = sqlSession.getMapper(PaymentTransactionMapper.class).read(45);
//		reservationPaymentToPropertyManager(sqlSession, paymentTransaction);
//		reservationPaymentToRenter(sqlSession, paymentTransaction);
//	}
	
	private static void sendEmail(String subject, String content, String recipients) {
		Mail mail = new Mail();
		mail.setSubject(subject);
		mail.setContent(content);
		mail.setRecipients(recipients);
		MailService.send(mail);
	}
	
	private static void sendEmailWithAttachment(String subject, String content, String recipients,  List<String> filelocations ) {
		Mail mail = new Mail();
		mail.setSubject(subject);
		mail.setContent(content);
		mail.setRecipients(recipients);
		MailService.send(mail,filelocations);
	}
	
	
	public static void emailWithAttachment( String subject, String body, String emailaddress,  List<String > filelocations  ){
		sendEmailWithAttachment(subject , body ,emailaddress  , filelocations);
	}

	
	public static void cancelWithGatewayToRenterRefund(Party propertyManager, Party renter, Reservation reservation, Double refund, String propertyAddress) { 
		String content = "Your reservation for the property located at " + propertyAddress + " has been cancelled. " +
				"A refund in the amount of " + TextHelper.AMOUNT_TWO_DECIMALS + " " + reservation.getCurrency() + " has been issued to your account.\n\n"
				+ "If you have any questions or need to change your plans please call the property manager directly.\n\n"
				+ "Property Manager Name: "	+ propertyManager.getFirstName() + " " + propertyManager.getFamilyName() + "\n"
				+ "Property Manager Phone Number: "	+ propertyManager.getDayphone() + "\n"
				+ "Property Manager Email address: " + propertyManager.getEmailaddress() + "\n\n\n"
				+ "Thank You\n"
				+ "Your Support Team";
		
		content = String.format(content, refund);
		
		String subject = "Cancel Confirmation for property at " + propertyAddress + "";
		String recipients = renter.getEmailaddress();
		
		sendEmail(subject, content, recipients);
	}
	
	public static void cancelWithGatewayToPMRefund(Party propertyManager, Party renter, Reservation reservation, Double refund, String propertyAddress) {
		String content = "The reservation for the property located at " + propertyAddress + " that was booked From " + TextHelper.DATE + " to " + TextHelper.DATE + " has been canceled. " +
				"A refund in the amount of " + TextHelper.AMOUNT_TWO_DECIMALS + " " + reservation.getCurrency() + " has been issued to renters account.\n\n" + 
				"Renter Information:\n" +
				"First Name: " + renter.getFirstName() + "\n" +
				"Last Name: " + renter.getFamilyName() + "\n" +
				"Email Address: " + renter.getEmailaddress() + "\n" +
				"Telephone Number: " + renter.getDayphone() + "\n\n" +

				"Thank You\n" +
				"Your Support Team";
		content = String.format(content, reservation.getFromdate(), reservation.getTodate(), refund);
		String subject = "MyBookingpal - Cancel Confirmation for property at " + propertyAddress + "";
		String recipients = propertyManager.getEmailaddress();
		
		sendEmail(subject, content, recipients);
	}
	
	public static void cancelWithoutGatewayToPMRefund(Party propertyManager, Party renter, Reservation reservation, Double refund, String propertyAddress) {
		String content = "The reservation for the property located at " + propertyAddress + " that was booked From " + TextHelper.DATE + " to " + TextHelper.DATE + " has been canceled. " +
				"A refund in the amount of " + TextHelper.AMOUNT_TWO_DECIMALS + " " + reservation.getCurrency() + " could not be made because we do not have your payment gateway information.  Please issue the credit to the renter.\n " +
				"Renter Information:\n" +
				"First Name: " + renter.getFirstName() + "\n" +
				"Last Name: " + renter.getFamilyName() + "\n" +
				"Email Address: " + renter.getEmailaddress() + "\n" +
				"Telephone Number: " + renter.getDayphone() + "\n\n" +
				"Thank You\n" +
				"Your Support Team  at MyBookingPal";

		content = String.format(content, reservation.getFromdate(), reservation.getTodate(), refund);
		
		String subject = "MyBookingpal - Cancel Confirmation for property at " + propertyAddress + "";
		String recipients = propertyManager.getEmailaddress();
		
		sendEmail(subject, content, recipients);
	}
	
	public static void cancelWithGatewayToRenterCharge(Party propertyManager, Party renter, Reservation reservation, Double refund, String propertyAddress) { 
		String content = "Your reservation for the property located at " + propertyAddress + " has been canceled." +
				" A cancellation fee in the amount of " + TextHelper.AMOUNT_TWO_DECIMALS + " " + reservation.getCurrency() + " has been charged to your account.\n\n" + 
				"If you have any questions or need to change your plans please call the property manager directly.\n\n" + 
				"Property Manager Name: " + propertyManager.getFirstName() + " " + propertyManager.getFamilyName() + "\n" + 
				"Property Manager Phone Number: " + propertyManager.getDayphone() + "\n" +
				"Property Manager Email address: " + propertyManager.getEmailaddress() + "\n\n\n" +
				"Thank You\n" +
				"Your Support Team";
		
		content = String.format(content, refund);
		
		String subject = "Cancel Confirmation for property at " + propertyAddress + "";
		String recipients = renter.getEmailaddress();
		
		sendEmail(subject, content, recipients);
	}

	public static void cancelWithGatewayToPMCharge(Party propertyManager, Party renter, Reservation reservation, Double refund, String propertyAddress) {
		String content = "The reservation for the property located at " + propertyAddress + " that was booked From " + TextHelper.DATE + " to " + TextHelper.DATE + " has been canceled." +
				" A cancel charge in the amount of " + TextHelper.AMOUNT_TWO_DECIMALS + " " + reservation.getCurrency() + " has been collected from renters account.\n\n" + 
				"Renter Information:" + "\n" +
				"First Name: " + renter.getFirstName() + "\n" +
				"Last Name: " + renter.getFamilyName() + "\n" +
				"Email Address: " + renter.getEmailaddress() + "\n" +
				"Telephone Number: " + renter.getDayphone() + "\n\n\n" +
				"Thank You\n" + 
				"Your Support Team";

		content = String.format(content, reservation.getFromdate(), reservation.getTodate(), refund);
		
		String subject = "MyBookingpal - Cancel Confirmation for property at " + propertyAddress + "";
		String recipients = propertyManager.getEmailaddress();
		
		sendEmail(subject, content, recipients);
	}
	
	public static void cancelWithoutGatewayToPMCharge(Party propertyManager, Party renter, Reservation reservation, Double refund, String propertyAddress) {
		String content = "The reservation for the property located at " + propertyAddress + " that was booked From " + TextHelper.DATE + " to " + TextHelper.DATE + " has been canceled. " +
				"A refund in the amount of " + TextHelper.AMOUNT_TWO_DECIMALS + " " + reservation.getCurrency() + " could not be made because we do not have your payment gateway information.  Please charge the renter.\n\n" +
				"Renter Information:\n" +
				"First Name: " + renter.getFirstName() + "\n" +
				"Last Name: " + renter.getFamilyName() + "\n" +
				"Email Address: " + renter.getEmailaddress() + "\n" +
				"Telephone Number: " + renter.getDayphone() + "\n\n" +
				
				"Thank You\n" +
				"Your Support Team at MyBookingPal";

		content = String.format(content, reservation.getFromdate(), reservation.getTodate(), refund);
		
		String subject = "MyBookingpal - Cancel Confirmation for property at " + propertyAddress + "";
		String recipients = propertyManager.getEmailaddress();
		
		sendEmail(subject, content, recipients);
	}
	
	public static void cancelPMWithoutFundsHolder(SqlSession sqlSession, Party propertyManager, Party renter, Reservation reservation, String propertyAddress) {
		String content = "The property at " + propertyAddress + " has been requested to be canceled. Please contact the renter and cancel the property in your PMS or through the MyBookingPal portal.\n\n" +
				"Renter Information: " + "\n" +
				"\tFirst Name: " + renter.getFirstName() + "\n" +
				"\tLast Name: " + renter.getFamilyName() + "\n" +
				"\tEmail Address: " + renter.getEmailaddress() + "\n" +
				"\tTelephone Number: " + renter.getDayphone() + "\n" +
				"Reserved From: " + TextHelper.DATE + " To: " + TextHelper.DATE + "\n" +
				
				"Payment Details" + "\n" +
				"Total amount of Booking: " + TextHelper.AMOUNT_TWO_DECIMALS + "\n" +
				"Deposit & Down payment Collected: " + TextHelper.AMOUNT_TWO_DECIMALS + "\n" +
				"Commission %%: " + TextHelper.AMOUNT_TWO_DECIMALS + "\n" +
				"Commission amount for total Booking: " + TextHelper.AMOUNT_TWO_DECIMALS + "\n" +
				"Currency: " + reservation.getCurrency() + "\n" +
				"Credit Card Fee: " + TextHelper.AMOUNT_TWO_DECIMALS + "\n" +
				"Balance to be charged on: " + TextHelper.DATE + "\n" +
				"Last 4 credit card: " + TextHelper.INTEGER + "\n" + "\n" +
				
				"The please cancel this booking by logging into http://admin.mybookingpal.com or on your PMS.";
		
		List<PaymentTransaction> paymentTransactions = sqlSession.getMapper(PaymentTransactionMapper.class).readByReservationId(Integer.valueOf(reservation.getId()));
		Double collectedAmount = getCollectedAmount(paymentTransactions);
		Double totalCommission = getTotalCommission(paymentTransactions);
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		Double creditCardFee = getCreditCardFee(paymentTransactions);
		PendingTransaction pendingTransaction = sqlSession.getMapper(PendingTransactionMapper.class).readByReservation(reservation);
		Date secondPaymentDate = null;
		if(pendingTransaction != null) {
			secondPaymentDate = pendingTransaction.getChargeDate();
		}
		Integer lastFour = getLastFour(paymentTransactions);
		
		String subject = "MyBookingpal - Booking Cancellation for " + propertyAddress;
		content = String.format(content, reservation.getFromdate(), reservation.getTodate(), reservation.getQuote(), collectedAmount, product.getDiscount(), totalCommission,
				creditCardFee, secondPaymentDate, lastFour);
		String recipients = propertyManager.getEmailaddress();
		
		sendEmail(subject, content, recipients);
	}
	
	public static void paymentErrorNovasol(SqlSession sqlSession, Party customer, Reservation reservation, Map<String, String> resultMap) throws ParseException {
		Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(reservation.getOrganizationid());
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		Party propertyManager = sqlSession.getMapper(PartyMapper.class).read(product.getSupplierid());
		Location location = sqlSession.getMapper(LocationMapper.class).read(product.getLocationid());
		Country productCountry = sqlSession.getMapper(CountryMapper.class).read(location.getCountry());
		Country customerCountry = sqlSession.getMapper(CountryMapper.class).read(customer.getCountry());
		String reservationDate = CalendarUtil.formatDate(DATE_FORMAT_PATTERN, reservation.getDate());
		String checkinDate = CalendarUtil.formatDate(DF.toPattern(), DATE_FORMAT_PATTERN, reservation.getCheckin());
		String checkoutDate = CalendarUtil.formatDate(DF.toPattern(), DATE_FORMAT_PATTERN, reservation.getCheckout());
		String state = customer.getRegion() == null ? "" : "\n\tState: " + customer.getRegion(); 
		String partnerName = partner.getName() == null || partner.getName().equals("") ? "" : "\n\tBooking site:  " + partner.getName();
		String errorReason = resultMap == null || resultMap.get(GatewayHandler.ERROR_MSG) == null ? " (reason unknown)" : " (" + resultMap.get(GatewayHandler.ERROR_MSG) + ")"; 
		String content = "The following booking was generated by BookingPal. There was a problem processing the credit card. Please follow up with the traveler to sort out the credit card issue." +
		"\n\tBooking Reference number: " +  reservation.getAltid() +
		"\n\tDate of Booking: " + reservationDate +
		partnerName + 
		"\n\tProperty ID: " + product.getAltid() + 
		"\n\tBooking Amount: " + reservation.getQuote() + " " + reservation.getCurrency() +
		"\n\tCity: " + location.getName() +
		"\n\tCountry: " + productCountry +
		"\n\tCheck In Date: " + checkinDate +
		"\n\tCheck Out Date: " + checkoutDate + 
		"\n\tPayment Id:  Failed" + errorReason +

		"\n\nTraveler info" +
		"\n\tName: " + customer.getFirstName() + " " + customer.getFamilyName() +
		"\n\tEmail: " + customer.getEmailaddress() +
		"\n\tPhone: " + customer.getDayphone() +
		"\n\tAddress: " + customer.getLocalAddress() +
		"\n\tCity: " + customer.getCity() +
		state +
		"\n\tZip: " + customer.getPostalcode() +
		"\n\tCountry: " + customerCountry +

		"\n\nIf you need additional information please contact us at:  Support@mybookingpal.com or Telephone : 1-949-216-7137.";
		
		String subject = "MyBookingpal - Payment failed for reservation " + reservation.getAltid();
		String recipients = propertyManager.getEmailaddress();
		
		sendEmail(subject, content, recipients);
	}
	
	private static Double getCollectedAmount(List<PaymentTransaction> paymentTransactions) {
		Double collected = 0.;
		for(PaymentTransaction transaction : paymentTransactions) {
			if(transaction.getStatus().equals(GatewayHandler.ACCEPTED)) {
				collected += transaction.getTotalAmount();
			}
		}
		return collected;
	}
	
	private static Double getTotalCommission(List<PaymentTransaction> paymentTransactions) {
		return paymentTransactions.get(0).getTotalCommission();
	}
	
	private static Double getCreditCardFee(List<PaymentTransaction> paymentTransactions) {
		return paymentTransactions.get(0).getCreditCardFee();
	}
	
	private static Integer getLastFour(List<PaymentTransaction> paymentTransactions) {
		return paymentTransactions.get(0).getPartialIin();
	}
	
	public static void cancelRenterWithoutFundsHolder(Party propertyManager, Party renter, Reservation reservation, String propertyAddress) {
		String content = "The property manager of the property located at " + propertyAddress + " has been contacted to cancel your reservation. They will issue the credit for the property if one is due." + "\n" + "\n" +  
			
			"If you have any questions or need to change your plans please call the property manager directly."  + "\n" + "\n" +
			
			"Property Manager Name:" + propertyManager.getName() + "\n" +
			"Property Manager Phone Number:" + propertyManager.getDayphone() + "\n" +
			"Property Manager Email address:" + propertyManager.getEmailaddress() + "\n" + "\n" + "\n" +
			
			
			"Thank You" + "\n" +
			"Your Support Team";
		String subject = "Cancel for property at " + propertyAddress;
		String recipients = renter.getEmailaddress();
		
		sendEmail(subject, content, recipients);
	}
	
	public static void secondPaymentNotification(Party renter, String currency, Double amount) {
		String content = "PayPal payment notification is currently not implemented\nTest data: \n\tcurrency[" + currency + "],\n\tamount[" + amount + "]";
		String subject = "PayPal test data";
		String recipients = renter.getEmailaddress();
		
		sendEmail(subject, content, recipients);
	}
	
	public static void inquiredReservationForManager(SqlSession sqlSession, Reservation reservation) {
		Party propertyManager = sqlSession.getMapper(PartyMapper.class).read(reservation.getSupplierid());
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		ChannelPartner channelPartner = sqlSession.getMapper(ChannelPartnerMapper.class).readByPartyId(Integer.valueOf(reservation.getAgentid()));
		Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
		
		//String confirmationLine = reservation.getConfirmationId() == null ? "" : TextHelper.TAB + "BookingPal Confirmation: " + reservation.getConfirmationId() + TextHelper.NEW_LINE; 
		
		String subject = "Property at " + TextHelper.STRING + " has been requested for " + TextHelper.DATE + " to " + TextHelper.DATE;
		String content = "Your property at " + TextHelper.STRING + " has been requested from " + TextHelper.DATE + " to " + TextHelper.DATE + ".\n\n" +
				"Please note that an email has been sent to the renter indicating that the booking is on hold and not yet confirmed until you send them a confirmation email " +
				"or call them with confirmation details. Also, if you would like to cancel this reservation either log into the BookingPal portal or your PMS portal and cancel " +
				"this booking.\n\n\n" +
				"Here are the details for this booking:\n\n" +
				"Property Information" +
				TextHelper.TAB + "PMS Property ID: " + TextHelper.STRING + TextHelper.NEW_LINE +
				TextHelper.TAB + "BookingPal Product ID: " + TextHelper.INTEGER + TextHelper.NEW_LINE +
//				confirmationLine + 
				TextHelper.TAB + "Channel Partner: " + TextHelper.STRING + TextHelper.NEW_LINE +
				TextHelper.TAB + "Reserved From: " + TextHelper.DATE + " to " + TextHelper.DATE + TextHelper.NEW_LINE + TextHelper.NEW_LINE +
				"Renter Information:" + TextHelper.NEW_LINE +
				TextHelper.TAB + "First Name: " + customer.getFirstName() + TextHelper.NEW_LINE +
				TextHelper.TAB + "Last Name: " + customer.getFamilyName() + TextHelper.NEW_LINE +
				TextHelper.TAB + "Email address: " + customer.getEmailaddress() + TextHelper.NEW_LINE +
				TextHelper.TAB + "Country:" + customer.getCountry() + TextHelper.NEW_LINE +
				TextHelper.TAB + "Telephone Number: " + customer.getDayphone() + TextHelper.NEW_LINE + TextHelper.NEW_LINE +
		"Additional information from client:" + TextHelper.NEW_LINE + reservation.getNotes() + TextHelper.NEW_LINE + TextHelper.NEW_LINE +
		"Thank You" + TextHelper.NEW_LINE + 
		"BookingPal Support Team";
		
		
		String recipients = propertyManager.getEmailaddress();
		
		String propertyLocation = ReservationService.getPropertyLocation(sqlSession, product);
		Date fromDate = reservation.getFromdate();
		Date toDate = reservation.getTodate();
		String channelPartnerName = channelPartner == null ? "BookingPal" : channelPartner.getChannelName();
		
		// formatting subject and content
		subject = String.format(subject, propertyLocation, fromDate, toDate);
		content = String.format(content, propertyLocation, fromDate, toDate, product.getAltid(), Integer.valueOf(product.getId()), channelPartnerName,
				fromDate, toDate);
		
		sendEmail(subject, content, recipients);
	}
	
	public static void inquiredReservationForRenter(SqlSession sqlSession, Reservation reservation) {
		String subject = "Property at " + TextHelper.STRING + " has been requested for " + TextHelper.DATE + " to " + TextHelper.DATE;
		String content = "Your request to reserve the property at " + TextHelper.STRING + " has been requested from " + TextHelper.DATE + " to " + TextHelper.DATE + ". The property manager for this property should be contacting you shortly." + TextHelper.NEW_LINE + TextHelper.NEW_LINE +
				"Thank You" + TextHelper.NEW_LINE +
				"BookingPal Support Team";

		Date fromDate = reservation.getFromdate();
		Date toDate = reservation.getTodate();
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		String propertyLocation = ReservationService.getPropertyLocation(sqlSession, product);
		
		subject = String.format(subject, propertyLocation, fromDate, toDate);
		content = String.format(content, propertyLocation, fromDate, toDate);
		
		Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
		String recipients = customer.getEmailaddress();
		
		sendEmail(subject, content, recipients);
	}
	
	public static void reservationPaymentToRenter(SqlSession sqlSession, PaymentTransaction paymentTransaction, String chargeType) throws ParseException {
		Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(String.valueOf(paymentTransaction.getReservationId()));
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		PendingTransaction pendingTransaction = sqlSession.getMapper(PendingTransactionMapper.class).readByReservation(reservation);
		ChannelPartner channelPartner = sqlSession.getMapper(ChannelPartnerMapper.class).readByPartyId(Integer.valueOf(reservation.getAgentid()));
		Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
		PropertyManagerInfo propertyManager = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(paymentTransaction.getSupplierId());
		Party propertyManagerParty = sqlSession.getMapper(PartyMapper.class).read(propertyManager.getPropertyManagerId().toString());
		
		Date bookingDate = paymentTransaction.getCreateDate();
		String propertyAddress = ReservationService.getPropertyLocation(sqlSession, product);
		Date fromDate = reservation.getFromdate();
		Date toDate = reservation.getTodate();
		Date checkInTime = propertyManager.getCheckInTime();
		Date checkOutTime = propertyManager.getCheckOutTime();
		Integer partialIin = paymentTransaction.getPartialIin();
		Double firstPayment = paymentTransaction.getTotalAmount();
		Double fullPayment = paymentTransaction.getFinalAmount();
		Double securityDeposit = product.getSecuritydeposit();
		Double cleeningFee = product.getCleaningfee();
		String currency = reservation.getCurrency();
		String channelPartnerName = channelPartner == null ? "BookingPal" : channelPartner.getChannelName();
		String reservationPos = Model.encrypt(reservation.getId());
		
		Set<CancellationItem> cancellationItems = PaymentHelper.getCancellationItems(sqlSession, reservation);
		
		// Pending transactions field
		Double secondPayment = 0.;
		Date secondChargeDate = null;
		
		if (pendingTransaction != null) {
			secondPayment = pendingTransaction.getChargeAmount();
			secondChargeDate = pendingTransaction.getChargeDate();
		}
		
		// PM fields
		String propertyManagerName = propertyManagerParty.getFirstName() + " " + propertyManagerParty.getFamilyName();
		String propertyManagerPhone = propertyManagerParty.getDayphone();
		String propertyManagerEmail = propertyManagerParty.getEmailaddress();
		
		String content = "";
		String subject = String.format("Confirmation for property at " + TextHelper.STRING + " from " + TextHelper.DATE + " to " + TextHelper.DATE, propertyAddress, fromDate, toDate);
		RenterConfirmationMailContent contentEntity = initRenterContent(channelPartnerName, bookingDate, propertyAddress, fromDate, checkInTime, toDate, checkOutTime, partialIin, firstPayment, currency, secondPayment, secondChargeDate, propertyManagerName, propertyManagerPhone, propertyManagerEmail, fullPayment, securityDeposit, cleeningFee, cancellationItems, reservationPos);
		RenterConfirmationMailBuilder mailBuilder = ConfirmationMailBuilderFactory.getInstance().getRenterConfirmationMailBuilder();
		
		if(PaymentHelper.isFullPaymentMethod(chargeType)) {
			content = mailBuilder.getFullMail(contentEntity);
		} else if(PaymentHelper.isDepositPaymentMethod(chargeType)) {
			content = mailBuilder.getDepositMail(contentEntity);
		} else if(chargeType.equalsIgnoreCase(PaymentHelper.MAIL_PAYMENT_METHOD)) {
			content = mailBuilder.getWithoutPaymentMail(contentEntity);
		}
		
//		if(firstPayment != null && firstPayment > 0) {
//			if(secondPayment != null && secondPayment > 0) {
//				content = mailBuilder.getDepositMail(contentEntity);
//			} else {
//				content = mailBuilder.getFullMail(contentEntity);
//			}
//		} else {
//			content = mailBuilder.getWithoutPaymentMail(contentEntity);
//		}
		
		Mail mail = new Mail();
		mail.setSubject(subject);
		mail.setContent(content);
		mail.setRecipients(customer.getEmailaddress() == null ? RazorHostsConfig.getEmailAddress() : customer.getEmailaddress());
		MailService.send(mail);
	}
	
	public static void failedPaymentTransactionToAdmin(SqlSession sqlSession, PaymentTransaction paymentTransaction) {
		Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(String.valueOf(paymentTransaction.getReservationId()));
		Product product = null;
		PaymentGatewayProvider provider = null;
		Party propertyManager = sqlSession.getMapper(PartyMapper.class).read(String.valueOf(paymentTransaction.getSupplierId()));
		if(reservation != null) {
			product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		}
		if(paymentTransaction != null && paymentTransaction.getGatewayId() != null) {
			provider = sqlSession.getMapper(PaymentGatewayProviderMapper.class).read(paymentTransaction.getGatewayId());
		}
		
		String providerName = provider == null ? "Unknown" : provider.getId() + ", " + provider.getName();
		String content = "Transaction ID: " + paymentTransaction.getId() + TextHelper.NEW_LINE +
				"PM: " + propertyManager.getId() + TextHelper.NEW_LINE +
				"PM name: " + propertyManager.getName() + TextHelper.NEW_LINE +
				"Payment gateway: " + providerName + TextHelper.NEW_LINE +
				"Amount: " + paymentTransaction.getTotalAmount() + TextHelper.NEW_LINE;
				if(product != null) {
					content += "Property ID: " + product.getId() + TextHelper.NEW_LINE;
				}
				if(reservation != null) {
					content += "Reservation ID: " + reservation.getId() + TextHelper.NEW_LINE;
				}
				content += "Message: " + paymentTransaction.getMessage();
		
		Mail mail = new Mail();
		mail.setSubject("Failed payment");
		mail.setContent(content);
		mail.setRecipients(HasUrls.ADMIN_EMAIL + "," + HasUrls.CHIRAYU_SHAH_EMAIL);
		MailService.send(mail);
	}
	
	public static void failedCancellationToAdmin(SqlSession sqlSession, PaymentTransaction paymentTransaction) {
		String subject = "Cancellation fail";
		String content = "Reservation ID: " + paymentTransaction.getReservationId() +
				"Payment ID: " + paymentTransaction.getId() +
				"Message: " + paymentTransaction.getMessage();
		Mail mail = new Mail();
		mail.setSubject(subject);
		mail.setContent(content);
		mail.setRecipients(HasUrls.ADMIN_EMAIL);
		MailService.send(mail);
	}
	
	public static void reservationPaymentToPropertyManager(SqlSession sqlSession, PaymentTransaction paymentTransaction, String chargeType) {
		Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(String.valueOf(paymentTransaction.getReservationId()));
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		PendingTransaction pendingTransaction = sqlSession.getMapper(PendingTransactionMapper.class).readByReservation(reservation);
		Party propertyManagerParty = sqlSession.getMapper(PartyMapper.class).read(String.valueOf(paymentTransaction.getSupplierId()));
		ChannelPartner channelPartner = sqlSession.getMapper(ChannelPartnerMapper.class).readByPartyId(Integer.valueOf(reservation.getAgentid()));
		
		String propertyAddress = ReservationService.getPropertyLocation(sqlSession, product);
		Date fromDate = reservation.getFromdate();
		Date toDate = reservation.getTodate();
		String altPropertyId = product.getAltid() == null ? "" : product.getAltid();
		String propertyId = reservation.getProductid();
//		String pmsConfirmation = reservation.getConfirmationId() == null ? "" : reservation.getConfirmationId();
		String confirmation = reservation.getId();
		String channelPartnerName = channelPartner == null ? "BookingPal" : channelPartner.getChannelName();
		Double firstPayment = paymentTransaction.getTotalAmount();
		Double totalAmount = reservation.getQuote();
		Double commissionAmount = paymentTransaction.getTotalCommission();
		Double commission =0.0;
		if(product.getDiscount()!=null){
			commission = channelPartner == null ? 0.0 : Double.valueOf(product.getDiscount());
		}
		
		String currency = reservation.getCurrency();
		Double creditCardFee = paymentTransaction.getCreditCardFee() == null ? 0. : paymentTransaction.getCreditCardFee();
		Integer partialIin = paymentTransaction.getPartialIin();
		String notes = reservation.getNotes();
		
		Double totalFees = product.getCleaningfee();
		
		Double secondPayment = null;
		Date secondPaymentDate = null;
		if(pendingTransaction != null) {
			secondPaymentDate = pendingTransaction.getChargeDate();
			secondPayment = pendingTransaction.getChargeAmount();
		}
		
		Party renterParty = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
		String firstName = renterParty.getFirstName();
		String lastName = renterParty.getFamilyName();
		String phone = renterParty.getDayphone();
		String email = renterParty.getEmailaddress();
		
		PMConfirmationMailContent contentEntity = initPMContent(propertyAddress, fromDate, toDate, altPropertyId, propertyId, /*pmsConfirmation, */confirmation, channelPartnerName,
				totalAmount, firstPayment, commission, commissionAmount, currency, creditCardFee, totalFees, secondPayment, secondPaymentDate, partialIin, firstName, 
				lastName, email, phone, notes);
		PMConfirmationMailBuilder mailBuilder = ConfirmationMailBuilderFactory.getInstance().getPMConfirmationBuilder();

		String content = "";
		if(PaymentHelper.isFullPaymentMethod(chargeType)) {
			content = mailBuilder.getFullMail(contentEntity);
		} else if(PaymentHelper.isDepositPaymentMethod(chargeType)) {
			content = mailBuilder.getDepositMail(contentEntity);
		} else if(chargeType.equalsIgnoreCase(PaymentHelper.MAIL_PAYMENT_METHOD)) {
			content = mailBuilder.getWithoutPaymentMail(contentEntity);
		}
		
		Mail mail = new Mail();
		mail.setSubject(String.format("Property at " + TextHelper.STRING + " has been booked from " + TextHelper.DATE + " to " + TextHelper.DATE, propertyAddress, fromDate, toDate));
		mail.setContent(content);
		String recipients = (propertyManagerParty.getEmailaddress() == null ? RazorHostsConfig.getEmailAddress() : propertyManagerParty.getEmailaddress()) 
				+ "," + HasUrls.RAY_KARIMI_EMAIL;
		mail.setRecipients(recipients);
		MailService.send(mail);
	}

	private static PMConfirmationMailContent initPMContent(String propertyAddress, Date arrivalDate, Date departureDate, String propertyAltId, String productId, /*String pmsConfirmationId,*/
			String confirmationId, String channelPartnerName, Double totalAmount, Double firstPayment, Double commissionPercentage, Double commissionAmount, String currency,
			Double creditCardFee, Double totalFees, Double secondPayment, Date secondPaymentDate, Integer lastFour, String renterFirstName, String renterLastName,
			String renterEmail, String renterPhone, String notes) {
		PMConfirmationMailContent content = new PMConfirmationMailContent();
		content.setArrivalDate(arrivalDate);
		content.setChannelPartnerName(channelPartnerName);
		content.setCommissionAmount(commissionAmount);
		content.setCommissionPercentage(commissionPercentage);
//		content.setConfirmationId(confirmationId);
		content.setCreditCardFee(creditCardFee);
		content.setCurrency(currency);
		content.setDepartureDate(departureDate);
		content.setFirstPayment(firstPayment);
		content.setLastFour(lastFour);
		content.setNotes(notes);
//		content.setPmsConfirmationId(pmsConfirmationId);
		content.setProductId(productId);
		content.setPropertyAddress(propertyAddress);
		content.setPropertyAltId(propertyAltId);
		content.setRenterEmail(renterEmail);
		content.setRenterFirstName(renterFirstName);
		content.setRenterLastName(renterLastName);
		content.setRenterPhone(renterPhone);
		content.setSecondPayment(secondPayment);
		content.setSecondPaymentDate(secondPaymentDate);
		content.setTotalAmount(totalAmount);
		content.setTotalFees(totalFees);
		return content;
	}
	
	private static RenterConfirmationMailContent initRenterContent(String channelPartnerName, Date bookingCreated, String propertyAddress, Date arrivalDate, Date checkInTime, Date departureDate, Date checkOutTime, 
			Integer lastFour, Double firstPayment, String currency, Double secondPayment, Date secondPaymentDate, String propertyManagerName, String propertyManagerPhone, 
			String propertyManagerEmail, Double totalCharge, Double securityDeposit, Double totalFees, Set<CancellationItem> cancellationItems, String reservationPos) {
		RenterConfirmationMailContent content = new RenterConfirmationMailContent();
		content.setArrivalDate(arrivalDate);
		content.setBookingCreated(bookingCreated);
		content.setCancellationItems(cancellationItems);
		content.setChannelPartnerName(channelPartnerName);
		content.setCheckInTime(checkInTime);
		content.setCheckOutTime(checkOutTime);
		content.setCurrency(currency);
		content.setDepartureDate(departureDate);
		content.setFirstPayment(firstPayment);
		content.setLastFour(lastFour);
		content.setPropertyAddress(propertyAddress);
		content.setPropertyManagerEmail(propertyManagerEmail);
		content.setPropertyManagerName(propertyManagerName);
		content.setPropertyManagerPhone(propertyManagerPhone);
		content.setSecondPayment(secondPayment);
		content.setSecondPaymentDate(secondPaymentDate);
		content.setSecurityDeposit(securityDeposit);
		content.setTotalCharge(totalCharge);
		content.setTotalFees(totalFees);
		content.setReservationPos(reservationPos);
		return content;
	}

	public static void sendPMPendingPaymentEmail(SqlSession sqlSession, PendingTransaction pendingTransaction, Map<String, String> result) {

		Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(pendingTransaction.getBookingId());
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		Party propertyManagerParty = sqlSession.getMapper(PartyMapper.class).read(product.getSupplierid());
		Party renterParty = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
		ChannelPartner channelPartner = sqlSession.getMapper(ChannelPartnerMapper.class).readByPartyId(Integer.valueOf(reservation.getAgentid()));
		ManagerToGateway managerToGateway = sqlSession.getMapper(ManagerToGatewayMapper.class).readBySupplierId(Integer.valueOf(propertyManagerParty.getId()));
		PaymentGatewayProvider paymentGatewayProvider = sqlSession.getMapper(PaymentGatewayProviderMapper.class).read(managerToGateway.getPaymentGatewayId());
		List<PaymentTransaction> paymentTransactions = sqlSession.getMapper(PaymentTransactionMapper.class).readByReservationId(Integer.valueOf(reservation.getId()));
		
		String additionalInfo = reservation.getNotes();
		String altProductId = product.getAltid();
		String channelPartnerName = channelPartner == null ? "BookingPal" : channelPartner.getChannelName();
		Double commissionAmount = pendingTransaction.getCommission();
		Double commissionPercentage = product.getDiscount();
//		String confirmationId = reservation.getConfirmationId() == null ? "" : reservation.getConfirmationId();
		Double totalAmount = reservation.getQuote();
		Double totalCollected = getCollectedAmount(paymentTransactions);
		Double creditCardFee = totalAmount * (paymentGatewayProvider.getFee() / 100.);
		String currency = reservation.getCurrency();
		Date fromDate = reservation.getFromdate();
		Integer partialIin = pendingTransaction.getPartialIin();
//		String pmsConfirmationId = reservation.getConfirmationId() == null ? "" : reservation.getConfirmationId();
		String productId = product.getId();
		String propertyAddress = ReservationService.getPropertyLocation(sqlSession, product);
		String renterEmail = renterParty.getEmailaddress();
		String renterFirstName = renterParty.getFirstName();
		String renterLastName = renterParty.getFamilyName();
		String renterPhone = renterParty.getDayphone();
		Date toDate = reservation.getTodate();
		
		PMPendingPaymentMailContent content = initPMPendingContent(additionalInfo, altProductId, channelPartnerName, commissionAmount, commissionPercentage,
				/*confirmationId, */creditCardFee, currency, fromDate, partialIin, /*pmsConfirmationId, */productId, propertyAddress, renterEmail, renterFirstName,
				renterLastName, renterPhone, toDate, totalAmount, totalCollected, result);
		
		PMPendingPaymentMailBuilder builder = new PMPendingPaymentMailBuilder();
		String mailContent = null;
		String subject = null;
		if(result.get(GatewayHandler.STATE).equals(GatewayHandler.ACCEPTED)) {
			subject = builder.getSuccessfulMailSubject(content);
			mailContent = builder.getSuccessfulMail(content);
		} else {
			subject = builder.getFailedMailSubject(content);
			mailContent = builder.getFailedMail(content);
		}
		
		String recipients = propertyManagerParty.getEmailaddress();
		sendEmail(subject, mailContent, recipients);
	}
	
	public static void sendRenterPendingPaymentEmail(SqlSession sqlSession, PendingTransaction pendingTransaction, Map<String, String> result) {
		Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(pendingTransaction.getBookingId());
		List<PaymentTransaction> paymentTransaction = sqlSession.getMapper(PaymentTransactionMapper.class).readByReservationId(Integer.valueOf(reservation.getId()));
		ChannelPartner channelPartner = sqlSession.getMapper(ChannelPartnerMapper.class).readByPartyId(Integer.valueOf(pendingTransaction.getPartnerId()));
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		Party propertyManagerParty = sqlSession.getMapper(PartyMapper.class).read(product.getSupplierid());
		Party renterParty = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
		
		Date bookingDate = paymentTransaction.get(0).getCreateDate();
		String channelPartnerName = channelPartner.getChannelName();
		Date fromDate = reservation.getFromdate();
		Integer partialIin = pendingTransaction.getPartialIin();
		String propertyAddress = ReservationService.getPropertyLocation(sqlSession, product);
		String propertyManagerEmail = propertyManagerParty.getEmailaddress();
		String propertyManagerName = propertyManagerParty.getName();
		String propertyManagerPhone = propertyManagerParty.getDayphone();
		Double remainingBalance = pendingTransaction.getChargeAmount();
		Date toDate = reservation.getTodate();
		
		RenterPendingPaymentContent content = initRenterPendingContent(bookingDate, channelPartnerName, fromDate, partialIin, propertyAddress, 
				propertyManagerEmail, propertyManagerName, propertyManagerPhone, remainingBalance, toDate);
		
		RenterPendingPaymentBuilder builder = new RenterPendingPaymentBuilder();
		String mailContent = null;
		String subject = null;
		if(result.get(GatewayHandler.STATE).equals(GatewayHandler.ACCEPTED)) {
			subject = builder.getSuccessfulMailSubject(content);
			mailContent = builder.getSuccessfulMail(content);
		} else {
			subject = builder.getFailedMailSubject(content);
			mailContent = builder.getFailedMail(content);
		}
		
		String recipients = renterParty.getEmailaddress();
		sendEmail(subject, mailContent, recipients);
	}
	
	private static RenterPendingPaymentContent initRenterPendingContent(Date bookingDate, String channelPartnerName, Date fromDate, Integer partialIin, String propertyAddress,
			String propertyManagerEmail, String propertyManagerName, String propertyManagerPhone, Double remainingBalance, Date toDate) {
		RenterPendingPaymentContent content = new RenterPendingPaymentContent();
		content.setBookingDate(bookingDate);
		content.setChannelPartnerName(channelPartnerName);
		content.setFromDate(fromDate);
		content.setPartialIin(partialIin);
		content.setPropertyAddress(propertyAddress);
		content.setPropertyManagerEmail(propertyManagerEmail);
		content.setPropertyManagerName(propertyManagerName);
		content.setPropertyManagerPhone(propertyManagerPhone);
		content.setRemainingBalance(remainingBalance);
		content.setToDate(toDate);
		return content;
	}
	
	private static PMPendingPaymentMailContent initPMPendingContent(String additionalInfo, String altProductId, String channelPartnerName, Double commissionAmount, Double commissionPercentage,
			/*String confirmationId, */Double creditCardFee, String currency, Date fromDate, Integer partialIin, /*String pmsConfirmationId, */String productId,
			String propertyAddress, String renterEmail, String renterFirstName, String renterLastName, String renterPhone, Date toDate, Double totalAmount,
			Double totalCollected, Map<String, String> result) {
		PMPendingPaymentMailContent content = new PMPendingPaymentMailContent();
		content.setAdditionalInfo(additionalInfo);
		content.setAltProductId(altProductId);
		content.setChannelPartnerName(channelPartnerName);
		content.setCommissionAmount(commissionAmount);
		content.setCommissionPercentage(commissionPercentage);
//		content.setConfirmationId(confirmationId);
		content.setCreditCardFee(creditCardFee);
		content.setCurrency(currency);
		content.setFromDate(fromDate);
		content.setPartialIin(partialIin);
//		content.setPmsConfirmationId(pmsConfirmationId);
		content.setProductId(productId);
		content.setPropertyAddress(propertyAddress);
		content.setRenterEmail(renterEmail);
		content.setRenterFirstName(renterFirstName);
		content.setRenterLastName(renterLastName);
		content.setRenterPhone(renterPhone);
		content.setToDate(toDate);
		content.setTotalAmount(totalAmount);
		content.setTotalCollected(totalCollected);
		
		return content;
	}
	
	/** 
	 * Sends an email message to a guest requesting feedback after departure.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation for which feedback is requested.
	 */
	public static void departedReservation(SqlSession sqlSession, Reservation reservation) {
		if (reservation.hasOrganizationid(reservation.getCustomerid())) {return;}
		Party supplier = sqlSession.getMapper(PartyMapper.class).read(reservation.getSupplierid());
		if (supplier.notOption(0)) {return;}
		Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		if (customer == null || supplier == null || product == null) {return;}

		Mail mail = new Mail();
		mail.setSubject("Please tell us about your recent accommodation experience at " + product.getName());
		StringBuilder sb = new StringBuilder();
		sb.append(supplier.getName()).append(" would like your opinion.");
		sb.append("\nAs a valued customer, you have been invited by ").append(supplier.getName()).append(" to share your opinion of ").append(product.getName()).append(".");
		sb.append("\nPlease take a moment to tell us about your experience with the property and the services that were provided.");
		sb.append("\nThis will help us to improve our service and will also help future travelers to make better accommodation decisions.");
		sb.append("\nClick on the link " + RazorHostsConfig.getRateUrl() + reservation.getId() + " to open the feedback form.");
		sb.append("\nShould you not wish to participate in this survey please forward this message to " + RazorHostsConfig.getEmailAddress());
		sb.append("\n\nThank you!\n\n").append(supplier.getName());
		sb.append("\n\nSent automatically from Razor - The Accommodation Marketplace");
		mail.setContent(sb.toString());
		mail.setRecipients(customer.getEmailaddress() == null ? RazorHostsConfig.getEmailAddress() : customer.getEmailaddress());
		MailService.send(mail);
	}

	/** 
	 * Sends an email message to the manager that an off line reservation has been accepted.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation that has been received.
	 */
	public static void acceptedReservation(SqlSession sqlSession, Reservation reservation) {
		Party organization = sqlSession.getMapper(PartyMapper.class).read(reservation.getOrganizationid());
		if (organization == null) {throw new ServiceException(Error.party_id, reservation.getOrganizationid());}
		Party party = null;
		party = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
		if (party == null) {party = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());}
		if (party == null) {throw new ServiceException(Error.party_id, reservation.getAgentid() + " or " + reservation.getCustomerid());}
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}

		Mail mail = new Mail();
		mail.setSubject("Reservation " + reservation.getName());
		StringBuilder sb = new StringBuilder();
		sb.append("The reservation request " + reservation.getName() + " collides with another so has been cancelled.");
		mail.setContent(sb.toString());
		mail.setRecipients(organization.getEmailaddress());
		MailService.send(mail);
		mail.setRecipients(party.getEmailaddress());
		MailService.send(mail);
	}

	/** 
	 * Sends an email message to the manager that new language version of text has been created.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param text the text that has been changed.
	 */
	public static void translatedText(SqlSession sqlSession, Text text) {
		String partyid = getId(NameId.Type.Party, text.getId());
		String productid = getId(NameId.Type.Product, text.getId());
		if (productid != null) {
			Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
			if (product == null) {throw new ServiceException(Error.product_id, productid);}
			partyid = product.getSupplierid();
		}
		Party party = sqlSession.getMapper(PartyMapper.class).read(partyid);
		if (party == null) {throw new ServiceException(Error.party_id, partyid);}

		Mail mail = new Mail();
		mail.setSubject("New Text Language for " + text.getName());
		StringBuilder sb = new StringBuilder();
		sb.append("The text with ID " + text.getId() + " has been translated to laguage code " + text.getLanguage());
		mail.setContent(sb.toString());
		mail.setRecipients(party.getEmailaddress());
		MailService.send(mail);
	}

	/**
	 * Return the product or party id according to the type.
	 * @param type is either Party or Product
	 * @param id is the text id
	 * @return the party or product id or null
	 */
	private static String getId(NameId.Type type, String id) {
		String args[] = id.split(type.name());
		if (args == null || args.length == 1) {return null;}
		else if (args.length == 2) {return args[0];}
		else {return args[2];}
	}
	
	/**
	 * Sends an email message to the manager that an off line reservation has been created.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation that has been received.
	 */
	public static void offlineReservation(SqlSession sqlSession, Reservation reservation, String bookemailaddress, String bookwebaddress) {
		if (reservation == null) {throw new ServiceException(Error.reservation_bad, "Email Initial Reservation");}
		Party organization = sqlSession.getMapper(PartyMapper.class).read(reservation.getOrganizationid());
		if (organization == null) {throw new ServiceException(Error.party_id, reservation.getOrganizationid());}
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}

		Mail mail = new Mail();
		mail.setSubject("Reservation " + reservation.getName());
		StringBuilder sb = new StringBuilder();
		sb.append("A reservation request has been received on " + DF.format(reservation.getDate()));
		sb.append("\nProperty " + product.getName());
		sb.append("\nCheck in " + DF.format(reservation.getFromdate()));
		sb.append("\nCheck out " + DF.format(reservation.getTodate()));
		sb.append("\nQuoted price for stay " + NF.format(reservation.getQuote()));
		sb.append("\nSTO rate for stay " + NF.format(reservation.getCost()));
		sb.append("\nPlease accept or reject the request at " + (bookwebaddress == null ? RazorHostsConfig.getOfflineUrl() : bookwebaddress) + reservation.getId());
		mail.setContent(sb.toString());
		mail.setRecipients(bookemailaddress == null ? organization.getEmailaddress() : bookemailaddress);
		MailService.send(mail);
	}

	/** 
	 * Sends an email message to interested parties that a provisional reservation has been created.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation that has been received.
	 */
	public static void provisionalReservation(SqlSession sqlSession, Reservation reservation) {
		if (reservation.hasActorid(Party.NO_ACTOR) && !reservation.hasOrganizationid(reservation.getAgentid())) {
			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
			Party supplier = sqlSession.getMapper(PartyMapper.class).read(product.getSupplierid());
			if (supplier == null) {throw new ServiceException(Error.party_id, product.getSupplierid());}
			if (supplier.notOption(1)) {return;}
			
			Party agent = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());
			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());

			Mail mail = new Mail();
			mail.setSubject("Reservation " + reservation.getName());
			StringBuilder sb = new StringBuilder();
			sb.append("New reservation has been received on " + DF.format(reservation.getDate()) + " by " + supplier.getName());
			sb.append(" from " + (agent == null ? "unkown agent " : agent.getName()) + " for " + product.getName());
			if (customer == null) {sb.append(".\nGuest details are not known.");}
			else {
				sb.append("\nGuest " + customer.getName());
				sb.append("\nEmail address " + customer.getEmailaddress());
			}
			sb.append("\nArrives on ").append(DF.format(reservation.getFromdate()));
			sb.append("\nDeparts on ").append(DF.format(reservation.getTodate()));
			sb.append("\nQuoted price for stay ").append(NF.format(reservation.getQuote()));
			if (reservation.getExtra() > 0.009) {sb.append("\nIncluding extra items of ").append(NF.format(reservation.getExtra()));}
			sb.append("\nSTO rate for stay ").append(NF.format(reservation.getCost()));
//			sb.append("\nMade up as follows:");
//			for (Price price : reservation.getQuotedetail()) {sb.append("\n" + price.getName()).append(" ").append(NF.format(price.getValue()));}
			sb.append("\nPlease check your workflow at " + RazorHostsConfig.getApplicationUrl());
			mail.setContent(sb.toString());
			mail.setRecipients(agent == null || agent.getEmailaddress() == null ? RazorHostsConfig.getEmailAddress() : agent.getEmailaddress());
			MailService.send(mail);
			mail.setRecipients(supplier.getEmailaddresses());
			MailService.send(mail);
		}
	}

	/** 
	 * Sends an email message to the guest confirmaing that a provisional reservation has been created.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation that has been made.
	 */
	public static void guestReservation(SqlSession sqlSession, Reservation reservation) {
		if (reservation.hasCustomerid()) {
			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			if (customer == null) {throw new ServiceException(Error.party_id, reservation.getCustomerid());}
			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
			Party organization = sqlSession.getMapper(PartyMapper.class).read(product.getSupplierid());
			if (organization == null) {throw new ServiceException(Error.party_id, product.getSupplierid());}
			Party agent = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());

			Mail mail = new Mail();
			mail.setSubject("Confirmation of Reservation " + reservation.getName());
			StringBuilder sb = new StringBuilder();
			sb.append("This is to confirm the reservation ").append(reservation.getName()).append(" made on " + DF.format(reservation.getDate()));
			sb.append("\nPlease check the following:");
			sb.append("\nGuest Name: " + customer.getName());
			sb.append("\nEmail Address: " + customer.getEmailaddress());
			sb.append("\nProperty Name: ").append(product.getName());
			sb.append("\nCheck In Date: " + DF.format(reservation.getFromdate()));
			sb.append("\nCheck Out Date: " + DF.format(reservation.getTodate()));
			sb.append("\nQuoted Price: " + NF.format(reservation.getQuote()));
			sb.append("\n\nPlease contact the property manager at ").append(organization.getEmailaddress()).append(" if you have any questions.");
			mail.setContent(sb.toString());
			mail.setRecipients(customer.getEmailaddress());
			MailService.send(mail);
			mail.setRecipients(organization.getEmailaddresses());
			MailService.send(mail);
			if (agent != null) {
				mail.setRecipients(agent.getEmailaddresses());
				MailService.send(mail);
			}
		}
	}

	/** 
	 * Sends an email message to the agent or guest that an off line reservation has been rejected.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation that has been received.
	 */
	public static void rejectedReservation(SqlSession sqlSession, Reservation reservation) {
		Party organization = sqlSession.getMapper(PartyMapper.class).read(reservation.getOrganizationid());
		if (organization == null) {throw new ServiceException(Error.party_id, reservation.getOrganizationid());}
		Party party = null;
		party = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
		if (party == null) {party = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());}
		if (party == null) {throw new ServiceException(Error.party_id, reservation.getAgentid() + " " + reservation.getCustomerid());}
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}

		Mail mail = new Mail();
		mail.setSubject("Reservation " + reservation.getName());
		StringBuilder sb = new StringBuilder();
		sb.append("The reservation request sent on " + DF.format(reservation.getDate()) + " for " + product.getName());
		sb.append("\nArriving on " + DF.format(reservation.getFromdate()));
		sb.append("\nDeparting on " + DF.format(reservation.getTodate()));
		sb.append("\nhas been rejected by the property manager.");
		mail.setContent(sb.toString());
		mail.setRecipients(organization.getEmailaddress());
		MailService.send(mail);
	}

	/**
	 * Send email to a newly registered user.
	 * 
	 * @param party the newly registered user.
	 */
	public static void partyCreate (Party party) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><body>Dear new Razor user,<p>Thank you very much for registering and we hope that Razor and My BookignPal will be beneficial to your business.");
		sb.append("<p>To get started you can use our online tutorials to guide you through the processes required to setting up your account, get your properties loaded and start managing your reservations.");
		sb.append(" These tutorials can be found on our <a href=\"http://www.razorpms.com/info/support/getting-started/\" target=\"_blank\">Getting started</a> pages of our website, click on the relevant user to view the tutorials.");
		
		sb.append("<p>Alternatively you can contact us to set up some online training. ");		
		sb.append("Contact our Partner Relations Manager Charlie Gordon-Finlayson on charlie@mybookingpal.com and he will get the required sessions set up. ");
		
		sb.append("<p>We hope that you will use Razor as a gateway to the My BookingPal distribution network.  ");
		sb.append("My BookignPal has the ability to get your properties in front of millions of new potential clients from around the world.  ");
		sb.append("Take a look at our service on our website, www.mybookingpal.com, or check out the videos below that explain the My BookingPal services.");
		
		sb.append("<p><a href=\"http://www.youtube.com/watch?v=aAWVPUNkiY4&amp;authuser=0\" style=\"font-size:12.7272720336914px\" target=\"_blank\">My BookingPal - Accommodation Supplier services</a>");
		sb.append("<p><a href=\"http://www.youtube.com/watch?v=aVMtNy5azFw&amp;authuser=0\" target=\"_blank\">My BookingPal - Demand channel services</a>");
		
		sb.append("<p>Should you have any queries on the My BookingPal service please contact Charlie, he will provide you with all the information you will need.");
		sb.append("<p>All the best.<p>Razor and My BookignPal</body></html>");
		
		Mail mail = new Mail();
		mail.setContentType(Mail.CONTENT_TYPE_TEXT_HTML);
		mail.setContent(sb.toString());
		mail.setSubject("New Razor User: " + party.getName());
		mail.setRecipients(party.getEmailaddress());
		MailService.send(mail);
		newRazorAccount(party);
	}
	
	/**
	 * Send email to Charlie about new registered account
	 * 
	 * @param party
	 */
	public static void newRazorAccount(Party party){
		StringBuilder sb = new StringBuilder();
		sb.append("New razor account was created. ");
		sb.append("\n\n Account name: " + party.getName());
		sb.append("\n\n Contact email: " + party.getEmailaddress());
		sb.append("\n\n Telephone number: " + party.getDayphone());
		sb.append("\n\n Razor server: " + RazorConfig.getHostname());		
		Mail mail = new Mail();
		mail.setContentType(Mail.CONTENT_TYPE_TEXT_PLAIN);
		mail.setContent(sb.toString());
		mail.setSubject("New Razor User: " + party.getName());
		mail.setRecipients("charlie@mybookingpal.com");
		MailService.send(mail);
	}

	/** 
	 * Sends an email message to the agent or guest that an off line reservation has been rejected.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation that has been received.
	 */
	public static void partyCreator(SqlSession sqlSession, Party action) {
		String emailaddress = action.getEmailaddress();
		if (action.noName() || !Party.isEmailAddress(emailaddress)) {return;}
		Party actor = sqlSession.getMapper(PartyMapper.class).read(action.getCreatorid());
		Party organization = sqlSession.getMapper(PartyMapper.class).read(action.getOrganizationid());
		String affiliatename = action.getName();
		//String affiliatetype = action.getType();
		{
			StringBuilder sb = new StringBuilder();
			sb.append(Party.getNaturalName(actor.getName()) + " of " + organization.getName() + " has suggested that you join Razor, the fastest growing accommodation marketplace.");
			sb.append("\n\nPlease go to http://razor-cloud.com/razor/?affiliate=" + Model.encrypt(actor.getId()) + " to register and start enjoying the benefits of Razor!");
			sb.append("\n\nIf you need direct support, please don't hesitate to contact us at info@razor-cloud.com");
			sb.append("\n\nBest wishes from the Razor Team.");
			Mail mail = new Mail();
			mail.setContent(sb.toString());
			mail.setSubject("Razor Referral for " + affiliatename + " by " + Party.getNaturalName(actor.getName()));
			mail.setRecipients(emailaddress);
			MailService.send(mail);
		}
		{
			StringBuilder sb = new StringBuilder();
			sb.append("Thank you for recommending Razor to " + affiliatename);
			sb.append("\n\nYour affiliate URL is http://razor-cloud.com/razor/?affiliate=" + Model.encrypt(actor.getId()) + ". Anyone who registers with Razor after logging in with this URL will be allocated to your commission account.");
			sb.append("\n\nWe strongly suggest that you visit http://razor-cloud.com/info/?page_id=1030 to learn more about how you can earn money from the Razor Affiliate program.");
			sb.append("\n\nIf you need any more information or have any suggestions, please don't hesitate to contact us at info@razor-cloud.com");
			sb.append("\n\nBest wishes from the Razor Team.");
			Mail mail = new Mail();
			mail.setContent(sb.toString());
			mail.setSubject("Razor Referral for " + affiliatename + " by " + Party.getNaturalName(actor.getName()));
			mail.setRecipients(actor.getEmailaddress() + ", info@razor-cloud.com");
			MailService.send(mail);
		}
	}

	/**
	 * Lists the payment accounts and exchange rates.
	 *
	 * @param sb the string builder to which to append payment details.
	 * @param balance the balance in USD to be paid.
	 * @param currencyrate the currency exchange rate to be used.
	 */
	private static String paymentaccounts(Double balance, Currencyrate currencyrate) {
		StringBuilder sb = new StringBuilder();
		if (currencyrate != null && currencyrate.hasId(Currency.Code.ZAR.name())) {
			Double exchangerate = 1.05 / currencyrate.getRate();
			sb.append("\nPlease make payment in ZAR to the following account:");
			sb.append("\nAccount Name: CBT Ltd\nAccount Number: 10011346233\nBranch Code: 580105");
			sb.append("\nBranch Name: Grayston Drive\nBank Name: Investec Private Bank");
			sb.append("\n\nThe " + currencyrate.getId() + " to " + currencyrate.getToid() + " exchange rate to be used is " + NF.format(exchangerate));
			sb.append("\n\nThis is the current ECB interbank rate +5% to account for currency exchange and transfers. ");
		}
		else if (currencyrate != null && currencyrate.hasId(Currency.Code.EUR.name())) {
			Double exchangerate = 1.05 / currencyrate.getRate();
			sb.append("\nPlease make payment in EUR to the following account:");
			sb.append("\nAccount Name: CBT Ltd\nAccount Number: 59225818\nBranch Code: 121103");
			sb.append("\nIBAN Number: GB51 BOFS 1211 0359 2258 18\nBank Name: Bank of Scotland");
			sb.append("\nThe " + currencyrate.getId() + " to " + currencyrate.getToid() + " exchange rate to be used is " + NF.format(exchangerate));
			sb.append("\n\nThis is the current ECB interbank rate +5% to account for currency exchange and transfers.");
		}
		else if (currencyrate != null && currencyrate.hasId(Currency.Code.GBP.name())) {
			Double exchangerate = 1.05 / currencyrate.getRate();
			sb.append("\nPlease make payment in GBP to the following account:");
			sb.append("\nAccount Name: CBT Ltd\nAccount Number: 6110566\nBranch Code: 121103");
			sb.append("\nIBAN Number: GB29 BOFS 1211 0306 1105 66\nBank Name: Bank of Scotland");
			sb.append("\nThe " + currencyrate.getId() + " to " + currencyrate.getToid() + " exchange rate to be used is " + NF.format(exchangerate));
			sb.append("\n\nThis is the current ECB interbank rate +5% to account for currency exchange and transfers.");
		}
		else {
			sb.append("\nPlease make payment in USD to the following account:");
			sb.append("\n\nAccount Name: CBT Ltd\nAccount Number: 12234416\nBranch Code: 121103");
			sb.append("\nIBAN Number: GB48 BOFS 1211 0312 2344 16\nBank Name: Bank of Scotland");
		}
		return sb.toString();
	}
	
	/**
	 * Gets the body of the email message advising the outstanding balance of an account.
	 * 
	 * @param report the report of the invoice.
	 * @param emailaddress the emailaddress of the recipient.
	 * @param balance the outstanding balance.
	 * @param currencyrate the currency exchange rate to convert the balance to the target currency.
	 */
	public static void licensebalance(String emailaddress, Double balance, Currencyrate currencyrate) {
		Mail mail = new Mail();
		mail.setSubject("Razor Account");
		mail.setContent("Razor Account Statement");
		NumberFormat NF = new DecimalFormat("#0.00");
		StringBuilder sb = new StringBuilder();
		
		//String url = HasUrls.REPORT_URL + report.getId() + "." + report.getFormat();

		sb.append("Our records show that there is an amount of USD " + NF.format(balance) + " outstanding on your Razor account.");
		sb.append("\nYou can view your statement by printing the details in the Razor console.");
		sb.append("\nGo to the Reporting tab and run the License reports for full details.\n");
		sb.append(paymentaccounts(balance, currencyrate));
		sb.append("\n\nSome agencies have recently complained about incorrect availability and pricing information on Razor.");
		sb.append("\nPlease note that Razor is a LIVE system, and as per our conditions of use your company will be held liable");
		sb.append("\nfor all agency and CBT commissions for any provisional reservations cancelled as a result of incorrect ");
		sb.append("\navailability, pricing or other information. Please ensure that all information on the system is correct");
		sb.append("\nat all times. If you cannot commit to this, please let us know so we can deactivate your account.");
		sb.append("\nPlease also note that your account may be deactivated if amount owing is not paid without delay!");
		sb.append("\n\nDo not hesitate to contact us at marcella@mybookingpal.com if you have an queries.");
		sb.append("\n\nYours sincerely,\n\nRazor Team");
		mail.setContent(sb.toString()); //getBalanceBody(balance.getBalance(), currencyrate));
		mail.setRecipients(emailaddress);
		MailService.send(mail);
		LOG.debug("licensebalance " + mail);
	}

	/**
	 * Sends the email message advising that an account has been invoiced.
	 * 
	 * @param emailaddress the emailaddress of the recipient.
	 * @param balance the outstanding balance.
	 * @param currencyrate the currency exchange rate to convert the balance to the target currency.
	 */
	public static void licenseinvoiced(String emailaddress, Double balance, Currencyrate currencyrate) {
		Mail mail = new Mail();
		mail.setRecipients(emailaddress);
		mail.setSubject("Razor Account");

		StringBuilder sb = new StringBuilder();
//		String url = HasUrls.REPORT_URL + report.getId() + "." + report.getFormat();
		sb.append("\nYour Razor account has been updated.");
		sb.append("\nYou can print the details in the Report page of Razor console.");
		sb.append(paymentaccounts(balance, currencyrate));
		sb.append("\n\nDo not hesitate to contact us at marcella@mybookingpal.com if you have an queries.");
		sb.append("\n\nYours sincerely,\n\nRazor Team");
		
		mail.setContent(sb.toString());
		MailService.send(mail);
		LOG.debug("licenseinvoiced " + mail);
	}

	/**
	 * Sends the email message advising that an account has been suspended.
	 * 
	 * @param emailaddress the emailaddress of the recipient.
	 * @param balance the outstanding balance that caused the account to be suspended.
	 * @param currencyrate the currency exchange rate to convert the balance to the target currency.
	 */
	public static void licensesuspended(String emailaddress, Double balance, Currencyrate currencyrate) {
		Mail mail = new Mail();
		mail.setRecipients(emailaddress);
		mail.setSubject("Razor Account");

		NumberFormat NF = new DecimalFormat("#0.00");
		StringBuilder sb = new StringBuilder();
//		String url = HasUrls.REPORT_URL + report.getId() + "." + report.getFormat();
		sb.append("Our records show that there was an amount of USD " + NF.format(balance) + " outstanding on your Razor account at the end of last month.");
		sb.append("\nYou can view your statement in the Razor console.");
		sb.append("\nYour account has been suspended until such time that it is paid.");
		sb.append(paymentaccounts(balance, currencyrate));
		sb.append("\n\nDo not hesitate to contact us at marcella@mybookingpal.com if you have an queries.");
		sb.append("\n\nYours sincerely,\n\nRazor Team");
		mail.setContent(sb.toString());
		MailService.send(mail);
		LOG.debug("licensesuspended " + mail);
	}
	
	public static void registration(String emailaddress, String companyName, String password) {
		
		Mail mail = new Mail();
		mail.setContentType(Mail.CONTENT_TYPE_TEXT_HTML);
		mail.setSubject(REGISTRATION_MAIL_SUBJECT);
		mail.setContent(String.format(REGISTRATION_MAIL_CONTENT, companyName, emailaddress, password, "<a href=\"http://mybookingpal.com/registration/index.html\" target=\"_blank\">here</a>"));
		mail.setRecipients(emailaddress);
		MailService.send(mail);
		
	}

	public static void notSupported(Party propertyManager) {
		sendEmail("Not supported", "email payment method is unsupported currently", propertyManager.getEmailaddress());
	}
	
	public static void sendBooking(String subject,String emailaddress,String body){
		Mail mail = new Mail();
		mail.setSubject(subject);
		mail.setContent(body);
		mail.setRecipients(emailaddress);
		
		MailService.send(mail);
	}
	
	public static void reservationNotPersisted(String bookingComResponse){
		ArrayList<String> listRecipients=new ArrayList<String>();
		Mail mail = new Mail();
		mail.setSubject("Booking Reservation Failed to Persist");
		mail.setContent(bookingComResponse);
		//listRecipients.add("chirayu@mybookingpal.com");
		listRecipients.add("senthil@mybookingpal.com");
		mail.setRecipients(listRecipients);
		
		MailService.send(mail);
	}
	
}


