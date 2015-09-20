package net.cbtltd.rest.reservation;

import static net.cbtltd.rest.reservation.Step.AVAILABILITY;
import static net.cbtltd.rest.reservation.Step.VERIFY_AND_CANCEL;
import static net.cbtltd.rest.reservation.Step.FORMAT;
import static net.cbtltd.rest.reservation.Step.INITIALIZATION;
import static net.cbtltd.rest.reservation.Step.PARTNER_RESERVATION;
import static net.cbtltd.rest.reservation.Step.PAYMENT;
import static net.cbtltd.rest.reservation.Step.PAYMENT_TRANSACTION;
import static net.cbtltd.rest.reservation.Step.PRICE;
import static net.cbtltd.rest.reservation.Step.SEND_ADMIN_EMAIL;
import static net.cbtltd.rest.reservation.Step.VALIDATION;

import java.util.Map;

import net.cbtltd.rest.ReservationRequest;
import net.cbtltd.rest.reservation.availability.Available;
import net.cbtltd.rest.reservation.cancel.Verifiable;
import net.cbtltd.rest.reservation.email.Mailable;
import net.cbtltd.rest.reservation.format.Formattable;
import net.cbtltd.rest.reservation.initialization.Initializable;
import net.cbtltd.rest.reservation.partner.PartnerReserve;
import net.cbtltd.rest.reservation.payment.MailPayment;
import net.cbtltd.rest.reservation.payment.Payable;
import net.cbtltd.rest.reservation.price.Pricable;
import net.cbtltd.rest.reservation.transaction.Transactional;
import net.cbtltd.rest.reservation.validation.Validator;
import net.cbtltd.rest.response.ReservationResponse;

/**
 * ReservationBuilder creates a create reservation and payment flow regarding specific PM configuration.
 * 
 * @author rmelnyk
 *
 */
public class ReservationBuilder {
	
	private static ReservationBuilder instance;
	
	private Validator validator;
	private Initializable initializator;
	private Pricable price;
	private Available availability;
	private Payable pay;
	private PartnerReserve patnerReserve;
	private Verifiable verify;
	private Transactional transact;
	private Formattable format;
	private Mailable mail;
	
	public static ReservationBuilder getInstance(ReservationBuilderConfiguration reservationBuilderConfiguration) throws Exception {
		if(instance == null) {
			instance = new ReservationBuilder(reservationBuilderConfiguration);
		}
		return instance;
	}
	
	private ReservationBuilder(ReservationBuilderConfiguration reservationBuilderConfiguration) throws Exception {
		Map<Step, Integer> configuration = reservationBuilderConfiguration.getConfiguration();
		Factory factory = Factory.getInstance();
		validator = factory.getValidator(configuration.get(VALIDATION));
		initializator = factory.getInitializator(configuration.get(INITIALIZATION));
		price = factory.getPrice(configuration.get(PRICE));
		availability = factory.getAvailability(configuration.get(AVAILABILITY));
		pay = factory.getPay(configuration.get(PAYMENT));
		patnerReserve = factory.getPartnerReservation(configuration.get(PARTNER_RESERVATION));
		verify = factory.getCancelAndRefund(configuration.get(VERIFY_AND_CANCEL));
		transact = factory.getTransact(configuration.get(PAYMENT_TRANSACTION));
		format = factory.getFormat(configuration.get(FORMAT));
		mail = factory.getMail(configuration.get(SEND_ADMIN_EMAIL));
	}
	
	public ReservationResponse buildResponse(ReservationRequest request) throws Exception {
		CreateReservationContent content = new CreateReservationContent();
		content.setReservationResponse(new ReservationResponse());
		content.setReservationRequest(request);
		validator.validate(content);
		initializator.initialize(content);
		price.price(content);
		availability.available(content);
		pay.pay(content);
		if(pay instanceof MailPayment) {
			return content.getReservationResponse();
		}
		patnerReserve.partnerReserve(content);
		verify.verify(content);
		transact.transact(content);
		format.format(content);
		mail.email(content);
		
		return content.getReservationResponse();
	}
}
