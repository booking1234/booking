package net.cbtltd.rest.reservation;

import net.cbtltd.rest.reservation.availability.Available;
import net.cbtltd.rest.reservation.availability.DefaultAvailability;
import net.cbtltd.rest.reservation.cancel.DefaultVerification;
import net.cbtltd.rest.reservation.cancel.NovasolVerification;
import net.cbtltd.rest.reservation.cancel.Verifiable;
import net.cbtltd.rest.reservation.email.DefaultMailing;
import net.cbtltd.rest.reservation.email.Mailable;
import net.cbtltd.rest.reservation.format.DefaultFormatter;
import net.cbtltd.rest.reservation.format.Formattable;
import net.cbtltd.rest.reservation.initialization.DefaultInitializator;
import net.cbtltd.rest.reservation.initialization.Initializable;
import net.cbtltd.rest.reservation.partner.DefaultPartnerReserve;
import net.cbtltd.rest.reservation.partner.NovasolPartnerReserve;
import net.cbtltd.rest.reservation.partner.PartnerReserve;
import net.cbtltd.rest.reservation.payment.ApiPayment;
import net.cbtltd.rest.reservation.payment.LocalNovasolPayment;
import net.cbtltd.rest.reservation.payment.LocalPayment;
import net.cbtltd.rest.reservation.payment.MailPayment;
import net.cbtltd.rest.reservation.payment.Payable;
import net.cbtltd.rest.reservation.price.LivePrice;
import net.cbtltd.rest.reservation.price.LocalPrice;
import net.cbtltd.rest.reservation.price.Pricable;
import net.cbtltd.rest.reservation.transaction.DefaultTransaction;
import net.cbtltd.rest.reservation.transaction.Transactional;
import net.cbtltd.rest.reservation.validation.DefaultValidator;
import net.cbtltd.rest.reservation.validation.Validator;
import net.cbtltd.server.ServiceException;
import net.cbtltd.shared.Error;

/**
 * Factory creates all of the necessary objects of classes-steps regarding PM configuration value of each separate step. 
 * 
 * @author rmelnyk
 *
 */
public class Factory {
	
	private static Factory instance = null;
	
	private Factory() {
		super();
	}
	
	public static Factory getInstance() {
		if(instance == null) {
			instance = new Factory();
		}
		return instance;
	}
	
	public Validator getValidator(Integer validator) throws Exception {
		switch(validator) {
		case 0 : return new DefaultValidator();
		default : throw new ServiceException(Error.configuration_not_exists, "Validator : " + validator);
		}
	}
	
	public Initializable getInitializator(Integer initializator) {
		switch(initializator) {
		case 0 : return new DefaultInitializator();
		default : throw new ServiceException(Error.configuration_not_exists, "Initializator : " + initializator);
		}
	}
	
	public Pricable getPrice(Integer price) {
		switch(price) {
		case 0 : return new LocalPrice();
		case 1 : return new LivePrice();
		default : throw new ServiceException(Error.configuration_not_exists, "Price : " + price);
		} 
	}
	
	public Available getAvailability(Integer availability) {
		switch(availability) {
		case 0 : return new DefaultAvailability();
		default : throw new ServiceException(Error.configuration_not_exists, "Availability : " + availability);
		}
	}
	
	public Payable getPay(Integer pay) {
		switch(pay) {
		case 0 : return new LocalPayment();
		case 1 : return new ApiPayment();
		case 2 : return new LocalNovasolPayment();
		case 3 : return new MailPayment();
		default : throw new ServiceException(Error.configuration_not_exists, "Pay : " + pay);
		}
	}
	
	public PartnerReserve getPartnerReservation(Integer reserve) {
		switch(reserve) {
		case 0 : return new DefaultPartnerReserve();
		case 1 : return new NovasolPartnerReserve();
		default : throw new ServiceException(Error.configuration_not_exists, "Partner reserve : " + reserve);
		}
	}
	
	public Verifiable getCancelAndRefund(Integer cancel) {
		switch(cancel) {
		case 0 : return new DefaultVerification();
		case 1 : return new NovasolVerification();
		default : throw new ServiceException(Error.configuration_not_exists, "Cancel and refund : " + cancel);
		}
	}
	
	public Transactional getTransact(Integer transact) {
		switch(transact) {
		case 0 : return new DefaultTransaction();
		default : throw new ServiceException(Error.configuration_not_exists, "Transact : " + transact);
		}
	}
	
	public Formattable getFormat(Integer format) {
		switch(format) {
		case 0 : return new DefaultFormatter();
		default : throw new ServiceException(Error.configuration_not_exists, "Format : " + format);
		}
	}
	
	public Mailable getMail(Integer mail) {
		switch(mail) {
		case 0 : return new DefaultMailing();
		default : throw new ServiceException(Error.configuration_not_exists, "Mail : " + mail);
		}
	}
}
