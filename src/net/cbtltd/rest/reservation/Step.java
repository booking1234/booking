package net.cbtltd.rest.reservation;

/**
 * Create reservation and payment steps.
 * 
 * @author rmelnyk
 *
 */
public enum Step {
	VALIDATION, INITIALIZATION, PRICE, AVAILABILITY, RESERVATION, PAYMENT, PARTNER_RESERVATION,
	VERIFY_AND_CANCEL, PAYMENT_TRANSACTION, FORMAT, SEND_ADMIN_EMAIL
}