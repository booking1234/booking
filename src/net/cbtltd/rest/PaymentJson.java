package net.cbtltd.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.cbtltd.rest.response.PartyResponse;
import net.cbtltd.rest.response.PendingPaymentResponse;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

@Path("/payment")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PaymentJson extends AbstractPayment {
	
	@GET
	@Path("/charge")
	@Descriptions({ 
		@Description(value = "Create the reservation and payment", target = DocTarget.METHOD),
		@Description(value = "Provisional Reservation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized PendingPaymentResponse createReservationAndPayment( 
			@Description(value = "Encrypted reservation ID") @QueryParam("reservationPos") String reservationPos,
			@Description(value = "Renter family name") @QueryParam("familyname") String familyName, 
			@Description(value = "Renter first name") @QueryParam("firstname") String firstName,
			@Description(value = "Renter credit card number") @QueryParam("cardnumber") String cardNumber, 
			@Description(value = "Renter credit card month") @QueryParam("cardmonth") String cardMonth, 
			@Description(value = "Renter credit card expiration year") @QueryParam("cardyear") String cardYear, 
			@Description(value = "Renter credit card verification  code ") @QueryParam("cc_security_code") Integer cvc,
			@Description(value = "Amount to be payed") @QueryParam("amount") String amount, 
			@Description(value = "Currency of payment") @QueryParam("currency") String currency, 
			@Description(value = "0 - Mastercard, 1 - Visa, 2 - American Express") @QueryParam("cardtype") Integer cardType) {
		return chragePendingPayment(reservationPos, firstName, familyName, cardNumber, cardMonth, cardYear, amount, currency, cardType, cvc);
	}
	
	@GET
	@Path("/customer_info")
	@Descriptions({ 
		@Description(value = "Getting the customer info by reservation ID and checking if pending payment is not cleared", target = DocTarget.METHOD),
		@Description(value = "Customer", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized PartyResponse getPendingPaymentCustomerInfo(
			@Description(value = "Encrypted reservation ID") @QueryParam("reservationPos") String reservationPos) {
		return pendingPaymentCustomerInfo(reservationPos);
	}
}
