/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.stripe;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.cbtltd.server.ServiceException;
import net.cbtltd.shared.api.IsGateway;
import net.cbtltd.shared.finance.GatewayAction;

import org.apache.log4j.Logger;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.ChargeCollection;
import com.stripe.model.Customer;

/**
 * The Class StripeService is to make payments via the Stripe payment gateway.
 * 
 * @see https://stripe.com/docs/api?lang=java
 * 
 * Library: stripe-java-latest.jar
 * Endpoint: https://api.stripe.com/
 * Stripe.apiKey = "vtUQeOtUnYr7PGCLQ96Ul4zqpDUO4sOE"; example
 */
public class StripeHandler implements IsGateway {

	private static final Logger LOG = Logger.getLogger(StripeHandler.class.getName());
	private static final DecimalFormat IF = new DecimalFormat("##");
	private final static String SERVER_URL = "https://api.stripe.com/"; //The URL of the Stripe REST URLs
	private final static String APIKEY = "vtUQeOtUnYr7PGCLQ96Ul4zqpDUO4sOE"; //The s
	private final static String FAIL = "fail"; //The failure condition

	public String getAltpartyid() {
		return "Altpartyid"; //TODO:
	}
	
	public void createPayment(GatewayAction action) {
		
	}

	public GatewayAction getPayment(GatewayAction action) {
		return null;
	}

	public void createRefund(GatewayAction action) {
		
	}

	/**
	 * Creates the specified payment.
	 *
	 * @param cref the transaction reference
	 * @param cname the name on the credit card.
	 * @param cc the credit card number.
	 * @param exp_month the two digit credit card expiry month.
	 * @param exp_year the four digit credit card expiry year.
	 * @param value the payment value in the specified currency.
	 * @param cur the specified currency code.
	 * @param cvv the card CVV digits.
	 * @param email the email address of the payer.
	 * @param ip the IP address of the machine originating the message.
	 * @return the XML message.
	 * @throws ServiceException the runtime exception.
	 */
	public static final Charge setCharge(
			String cref,
			Integer amount,
			String cur,
			Map<String, Object> card
	) throws ServiceException {
		Charge rs = null;
		try {
			if (cref == null || cref.isEmpty()) {throw new ServiceException(net.cbtltd.shared.Error.card_reference, cref);}
			if (amount == null || amount <= 0) {throw new ServiceException(net.cbtltd.shared.Error.card_amount, String.valueOf(amount));}
			if (cur == null || cur.isEmpty()) {throw new ServiceException(net.cbtltd.shared.Error.currency_code, cur);}

			Stripe.apiKey = APIKEY;
		
			Map<String, Object> params = new HashMap<String, Object>(); 
			params.put("description", "Charge for site@stripe.com"); 
			params.put("card", "tok_jOq0M8vJprCUUU"); // obtained with Stripe.js 
			params.put("card", card); // alternative with cc details
			params.put("amount", amount); 
			params.put("currency", cur); 
			//Get transaction response
			rs = Charge.create(params);
			
			LOG.debug( "--Stripe Response Received--");
			LOG.debug( "Amount: " + rs.getAmount());
			LOG.debug( "AmountRefunded: " + rs.getAmountRefunded());
			LOG.debug( "Card: " + rs.getCard());
			LOG.debug( "Created: " + new Date(rs.getCreated() * 1000));
			LOG.debug( "Currency: " + rs.getCurrency());
			LOG.debug( "Customer: " + rs.getCustomer());
			LOG.debug( "Description: " + rs.getDescription());
			LOG.debug( "Disputed: " + rs.getDisputed());
			LOG.debug( "Fee: " + rs.getFee());
			LOG.debug( "Id: " + rs.getId());
			LOG.debug( "Invoice: " + rs.getInvoice());
			LOG.debug( "Livemode: " + rs.getLivemode());
			LOG.debug( "Paid: " + rs.getPaid());
			LOG.debug( "Refunded: " + rs.getRefunded());
			
			if (rs.getCard().getCvcCheck().equalsIgnoreCase(FAIL)) {throw new ServiceException(net.cbtltd.shared.Error.card_code, card.get("cvv").toString());}
			if (rs.getCard().getAddressLine1Check().equalsIgnoreCase(FAIL)) {throw new ServiceException(net.cbtltd.shared.Error.card_address, card.get("address_line1").toString());}
			if (rs.getCard().getAddressZipCheck().equalsIgnoreCase(FAIL)) {throw new ServiceException(net.cbtltd.shared.Error.card_postal_code, card.get("address_zip").toString());}
			return rs;
		} 
		catch(StripeException x) {throw new ServiceException(net.cbtltd.shared.Error.card_gateway, rs.getFailureMessage());}
	}
	
	/**
	 * Creates the specified refund.
	 *
	 * @param id the ID of the payment to be refunded.
	 * @param amount the amount in cents to be refunded.
	 * @throws ServiceException the runtime exception.
	 */
	public static final Charge setRefund(String id, Integer amount, String cur) throws ServiceException {
		Charge rs = null;
		try {
			if (id == null || id.isEmpty()) {throw new ServiceException(net.cbtltd.shared.Error.card_reference, id);}
			if (amount == null || amount <= 0) {throw new ServiceException(net.cbtltd.shared.Error.card_amount, String.valueOf(amount));}

			Stripe.apiKey = APIKEY;
			rs = Charge.retrieve(id);
			Map<String, Object> params = new HashMap<String, Object>(); 
			params.put("amount", amount); 
			params.put("currency", cur); 
			rs = rs.refund(params);
			
			LOG.debug( "--Stripe Refund--");
			LOG.debug( "Amount: " + rs.getAmount());
			LOG.debug( "AmountRefunded: " + rs.getAmountRefunded());
			LOG.debug( "Card: " + rs.getCard());
			LOG.debug( "Created: " + new Date(rs.getCreated() * 1000));
			LOG.debug( "Currency: " + rs.getCurrency());
			LOG.debug( "Customer: " + rs.getCustomer());
			LOG.debug( "Description: " + rs.getDescription());
			LOG.debug( "Disputed: " + rs.getDisputed());
			LOG.debug( "Fee: " + rs.getFee());
			LOG.debug( "Id: " + rs.getId());
			LOG.debug( "Invoice: " + rs.getInvoice());
			LOG.debug( "Livemode: " + rs.getLivemode());
			LOG.debug( "Paid: " + rs.getPaid());
			LOG.debug( "Refunded: " + rs.getRefunded());
			
			return rs;
		} 
		catch(StripeException x) {throw new ServiceException(net.cbtltd.shared.Error.card_gateway, rs == null ? x.getMessage() : rs.getFailureMessage());}
	}
	
	/**
	 * Creates the specified customer.
	 *
	 * @param card the map of card details of the customer.
	 * @param email the email address of the customer.
	 * @param description the description of the customer.
	 * @param account_balance the opening balance of the customer in cents.
	 * @param trial_end the UTC time stamp when trial ends.
	 * @throws ServiceException the runtime exception.
	 */
	public static final Customer setCustomer(
			Map<String, Object> activeCard,
			Date created,
			Date trialEnd,
			String email, 
			String description, 
			Integer accountBalance,
			Boolean delinquent
		) throws ServiceException {
		Customer rs = new Customer();
		try {
			if (activeCard == null) {throw new ServiceException(net.cbtltd.shared.Error.card_reference, activeCard.toString());}
			if (email == null || email.isEmpty()) {throw new ServiceException(net.cbtltd.shared.Error.party_emailaddress, email);}
			if (accountBalance == null) {throw new ServiceException(net.cbtltd.shared.Error.card_amount, String.valueOf(accountBalance));}

			Stripe.apiKey = APIKEY;

//			rs.setAccountBalance(accountBalance);
//			rs.setActiveCard(activeCard);
//			rs.setCreated(created.getTime()/1000);
//			rs.setDelinquent(delinquent);
//			rs.setTrialEnd(trialEnd.getTime()/1000);
			Map<String, Object> params = new HashMap<String, Object>(); 
			params.put("card", activeCard); 
			params.put("email", email); 
			params.put("description", description); 
			params.put("account_balance", accountBalance); 
			params.put("trial_end", trialEnd.getTime() / 1000); 
			
			
			LOG.debug( "--Stripe Customer--");
			LOG.debug( "Description: " + rs.getDescription());
			LOG.debug( "Email: " + rs.getEmail());
			LOG.debug( "Id: " + rs.getId());
			LOG.debug( "Created: " + new Date(rs.getCreated() * 1000));
			LOG.debug( "TrialEnd: " + new Date(rs.getTrialEnd() * 1000));
			LOG.debug( "Plan: " + rs.getPlan());
			LOG.debug( "AccountBalance: " + rs.getAccountBalance());
			LOG.debug( "ActiveCard: " + rs.getActiveCard().toString());
			LOG.debug( "Delinquent: " + rs.getDelinquent());
			LOG.debug( "Discount: " + rs.getDiscount().toString());
			LOG.debug( "NextRecurringCharge: " + rs.getNextRecurringCharge().toString());
			LOG.debug( "Subscription: " + rs.getSubscription().toString());
			LOG.debug( "Livemode: " + rs.getLivemode());
			
			rs = rs.create(params);
			return rs;
		} 
		catch(StripeException x) {throw new ServiceException(net.cbtltd.shared.Error.card_gateway, x.getMessage());}
	}
	
	/**
	 * Creates the specified credit card.
	 *
	 * @param cname the name on the credit card.
	 * @param cc the credit card number.
	 * @param exp_month the two digit credit card expiry month.
	 * @param exp_year the four digit credit card expiry year.
	 * @param cvv the card CVV digits.
	 * @param email the email address of the payer.
	 * @param ip the IP address of the machine originating the message.
	 * @return the credit card map
	 */
	public static final Map<String, Object> setCard(
			String cref,
			String cname,
			String cc,
			String exp_month,	
			String exp_year,	
			Double value,
			String cvv,
			String email,
			String address_line1,
			String address_line2,
			String address_zip,
			String address_state,
			String address_country,
			String description,
			String ip
		) throws ServiceException {
		if (cname == null || cname.isEmpty()) {throw new ServiceException(net.cbtltd.shared.Error.credit_card_error, cname);}
		if (cc == null || cc.isEmpty()) {throw new ServiceException(net.cbtltd.shared.Error.credit_card_error, cc);}
		if (exp_month == null || exp_month.length() != 2) {throw new ServiceException(net.cbtltd.shared.Error.credit_card_error, exp_month);}
		if (exp_year == null || exp_year.length() != 4) {throw new ServiceException(net.cbtltd.shared.Error.credit_card_error, exp_year);}
		if (value == null || value <= 0.0) {throw new ServiceException(net.cbtltd.shared.Error.card_amount, String.valueOf(value));}
		if (cvv == null || cvv.isEmpty()) {throw new ServiceException(net.cbtltd.shared.Error.credit_card_error,  cvv);}
		if (email == null || email.isEmpty()) {throw new ServiceException(net.cbtltd.shared.Error.party_emailaddress, email);}
		if (ip == null || ip.isEmpty()) {throw new ServiceException(net.cbtltd.shared.Error.party_emailaddress, ip);}

		Map<String, Object> card = new HashMap<String, Object>();
		card.put("number", cc);//The card number, as a string without any separators.
		card.put("exp_month", exp_month); //Two digit number representing the card's expiration month.
		card.put("exp_year", exp_year); //Four digit number representing the card's expiration year.
		card.put("cvc", cvv); 
		card.put("name", cname);
		card.put("address_line1", address_line1);
		card.put("address_line2", address_line2);
		card.put("address_zip", address_zip);
		card.put("address_state", address_state);
		card.put("address_country", address_country);
		card.put("description", description);
		return card;
	}

	/**
	 * Gets the specified charge.
	 *
	 * @param id the charge id
	 * @throws ServiceException the runtime exception.
	 */
	public static final Charge getCharge(String id) throws ServiceException {
		Charge rs = null;
		try {
			if (id == null || id.isEmpty()) {throw new ServiceException(net.cbtltd.shared.Error.card_reference, id);}

			Stripe.apiKey = APIKEY;
			
			rs = Charge.retrieve(id);
			
			LOG.debug( "--Stripe Response Received--");
			LOG.debug( "Amount: " + rs.getAmount());
			LOG.debug( "AmountRefunded: " + rs.getAmountRefunded());
			LOG.debug( "Card: " + rs.getCard());
			LOG.debug( "Created: " + new Date(rs.getCreated() * 1000));
			LOG.debug( "Currency: " + rs.getCurrency());
			LOG.debug( "Customer: " + rs.getCustomer());
			LOG.debug( "Description: " + rs.getDescription());
			LOG.debug( "Disputed: " + rs.getDisputed());
			LOG.debug( "Fee: " + rs.getFee());
			LOG.debug( "Id: " + rs.getId());
			LOG.debug( "Invoice: " + rs.getInvoice());
			LOG.debug( "Livemode: " + rs.getLivemode());
			LOG.debug( "Paid: " + rs.getPaid());
			LOG.debug( "Refunded: " + rs.getRefunded());
			return rs;
		} 
		catch(StripeException x) {throw new ServiceException(net.cbtltd.shared.Error.card_gateway, rs == null ? x.getMessage() : rs.getFailureMessage());}
	}
	
	/**
	 * Gets the specified charge.
	 *
	 * @param count the charge count default 10
	 * @param offset the charge offset default 0
	 * @param customerid the charge customerid default all
	 * 
	 * @throws ServiceException the runtime exception.
	 */
	public static final ChargeCollection  getChargeCollection (
			int count,
			int offset,
			String customerid
			) throws ServiceException {
		ChargeCollection rs = null;
		try {
			Stripe.apiKey = APIKEY;
			
			Map<String, Object> params = new HashMap<String, Object>(); 
			params.put("count", count); 
			params.put("offset", offset); 
			params.put("customer", customerid); 
			rs = Charge.all(params);
//			if (rs == null || rs.isEmpty()) {throw new ServiceException(net.cbtltd.shared.Error.card_payments, customerid);}
			
			List<Charge> charges = rs.getData();
			for (Charge charge : charges) {
				LOG.debug( "--Stripe Response Received--");
				LOG.debug( "Amount: " + charge.getAmount());
				LOG.debug( "AmountRefunded: " + charge.getAmountRefunded());
				LOG.debug( "Card: " + charge.getCard());
				LOG.debug( "Created: " + new Date(charge.getCreated() * 1000));
				LOG.debug( "Currency: " + charge.getCurrency());
				LOG.debug( "Customer: " + charge.getCustomer());
				LOG.debug( "Description: " + charge.getDescription());
				LOG.debug( "Disputed: " + charge.getDisputed());
				LOG.debug( "Fee: " + charge.getFee());
				LOG.debug( "Id: " + charge.getId());
				LOG.debug( "Invoice: " + charge.getInvoice());
				LOG.debug( "Livemode: " + charge.getLivemode());
				LOG.debug( "Paid: " + charge.getPaid());
				LOG.debug( "Refunded: " + charge.getRefunded());
			}
			return rs;
		} 
		catch(StripeException x) {throw new ServiceException(net.cbtltd.shared.Error.card_gateway, x.getMessage());}
	}
}
