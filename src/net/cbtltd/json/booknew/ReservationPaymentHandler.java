package net.cbtltd.json.booknew;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.paypal.PaypalHelper;
import net.cbtltd.server.EmailService;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.SessionService;
import net.cbtltd.server.api.ManagerToGatewayMapper;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.PaymentGatewayProviderMapper;
import net.cbtltd.server.api.PaymentTransactionMapper;
import net.cbtltd.server.api.PendingTransactionMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.server.util.PendingTransactionStatus;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
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
import net.cbtltd.shared.Serial;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.finance.gateway.GatewayInfo;
import net.cbtltd.shared.finance.gateway.PaymentGatewayHolder;
import net.cbtltd.shared.finance.gateway.PaymentGatewayHandlerFactory;

import org.apache.ibatis.session.SqlSession;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.paypal.api.payments.Payment;

public class ReservationPaymentHandler implements Handler {

	@Override
	public String service() {return JSONRequest.RESERVATION_PAYMENT.name();}

	@Override
	public JSONResponse execute(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 					// the point of sale code.
		String productId = parameters.get("productid"); 		// the ID of the property
		String fromDate = parameters.get("fromdate");			// the date from which availability is to be shown.
		String toDate = parameters.get("todate");				// the date to which availability is to be shown.
		String emailAddress = parameters.get("emailaddress"); 	// the email address of the guest.
		String familyName = parameters.get("familyname");		// the family name of the guest.
		String firstName = parameters.get("firstname");			// the first name of the guest.
		String notes = parameters.get("notes");					// the guest notes or requests.
		String cardNumber = parameters.get("cardnumber");		// the card number.
		String cardExpiryMonth = parameters.get("cardmonth");	// the card expiry month.
		String cardExpiryYear = parameters.get("cardyear");		// the card expiry year.
		String amount = parameters.get("amount");				// the amount to be charged to the card.
		String phoneNumber = parameters.get("telnumber");
		String cardType = parameters.get("cardtype");

//		SqlSession sqlSession = RazorServer.openSession();
//		Product product = sqlSession.getMapper(ProductMapper.class).read(productId);
//		int supplierId = Integer.valueOf(product.getSupplierid());
//		PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(supplierId);
//		ManagerToGateway managerToGateway = sqlSession.getMapper(ManagerToGatewayMapper.class).readBySupplierId(supplierId);
//		int paymentGatewayId = managerToGateway.getPaymentGatewayId();
//		PaymentGatewayProvider paymentGatewayProvider = sqlSession.getMapper(PaymentGatewayProviderMapper.class).read(paymentGatewayId);
//		PaymentGatewayHolder paymentGateway = PaymentGatewayHolder.setPaymentGateway(paymentGatewayId);
//		GatewayInfo gateway = new GatewayInfo(managerToGateway.getGatewayAccountId(), managerToGateway.getGatewayCode(), cardType,
//				cardNumber, cardExpiryMonth, cardExpiryYear, firstName, familyName);
//		GatewayHandler handler = PaymentGatewayHandlerFactory.getHandler(paymentGateway, gateway);
//		
//		Reservation reservation = new Reservation();
//		PaymentTransaction paymentTransaction = null;
//		PendingTransaction pendingTransaction = null;
//		Payment payment = null;
		
		ReservationPaymentWidgetItem item = new ReservationPaymentWidgetItem();
		
//		try {
//			Party agent = JSONService.getParty(sqlSession, pos);
//			if (agent == null) {throw new ServiceException(Error.reservation_agentid);}
//			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(agent.getId());
//			Party customer = JSONService.getCustomer(sqlSession, emailAddress, familyName, firstName, product.getSupplierid(), agent);
//			reservation = PaymentHelper.prepareReservation(agent, customer, product, fromDate, toDate, notes, productId);
//			
//			int deposit = 100;
//			ReservationService.computePrice(sqlSession, reservation);
//			deposit = ReservationService.getDeposit(reservation, propertyManagerInfo);
//			reservation.setDeposit(deposit);
//			reservation.setCost(reservation.getQuote() * ReservationService.getDiscountfactor(sqlSession, reservation));
//
//			boolean available = sqlSession.getMapper(ReservationMapper.class).available(reservation);
//			if (!available) {throw new ServiceException(Error.product_not_available, reservation.getProductFromToDate());}
//			reservation.setName(SessionService.pop(sqlSession, reservation.getOrganizationid(), Serial.RESERVATION));
//			sqlSession.getMapper(ReservationMapper.class).create(reservation);
//			
//			reservation.setCollisions(ReservationService.getCollisions(sqlSession, reservation));
//			if (reservation.hasCollisions()) {throw new ServiceException(Error.product_not_available, reservation.getProductFromToDate());}
//
//			String chargeType = PaymentHelper.getChargeType(propertyManagerInfo);
//			paymentTransaction = PaymentHelper.preparePaymentTransaction(reservation, Double.valueOf(amount), managerToGateway, payment, cardNumber, agent, partner, product, chargeType, paymentGatewayProvider);
//			
//			sqlSession.getMapper(ReservationMapper.class).update(reservation);
//
//			if (product.hasAltpartyid()) {PartnerService.createReservation(sqlSession, reservation);}
//
//			MonitorService.update(sqlSession, Data.Origin.JQUERY, NameId.Type.Reservation, reservation);
//
//			// Creating payments
//			Map<String, String> resultMap = handler.createPayment(product.getCurrency(), Double.valueOf(amount));
//			
//			// Payment transaction start
//			String gatewayTransactionId = resultMap.get(GatewayHandler.TRANSACTION_ID);
//			paymentTransaction.setGatewayTransactionId(gatewayTransactionId);
//			paymentTransaction.setMessage(resultMap.get(GatewayHandler.ERROR_MSG));
//			paymentTransaction.setStatus(resultMap.get(GatewayHandler.STATE));
//			// Payment transaction end
//			
//			if (propertyManagerInfo.getNumberOfPayments() > 1) {
//				// TODO : Pending transaction start
//				double chargeAmount = PaymentHelper.getSecondPayment(sqlSession, reservation, propertyManagerInfo);
//				double bookingpalPayment = chargeAmount * 0.02; // TODO
//				double commission = chargeAmount * product.getCommission() / 100.; // TODO
//				int partialIin = PaymentHelper.getLastDigits(cardNumber, 4);
//				int partyid = Integer.valueOf(Model.decrypt(pos));
//				double partnerPayment = 0; // TODO
//				String pmsConfirmationId = reservation.getConfirmationId(); // TODO
//				pendingTransaction = new PendingTransaction(
//						paymentGatewayProvider, reservation, bookingpalPayment,
//						chargeAmount, propertyManagerInfo, commission,
//						firstName, familyName, gatewayTransactionId,
//						partialIin, partyid, partnerPayment, paymentGatewayId,
//						phoneNumber, pmsConfirmationId,
//						PendingTransactionStatus.Active.toString(), supplierId);
//				sqlSession.getMapper(PendingTransactionMapper.class).create(pendingTransaction);
//			}
//			// Pending transaction end
////			if (reservation.isActive() && product.noAltpartyid()) {EmailService.guestReservation(sqlSession, reservation);}
//			EmailService.reservationPaymentToPropertyManager(sqlSession, paymentTransaction);
//			EmailService.reservationPaymentToRenter(sqlSession, paymentTransaction);
//			sqlSession.commit();
//			item.setMessage("Successful!");
//		}
//		catch (ParseException x) {
//			item.setMessage("Date format, " + x.getMessage());
//			sqlSession.rollback(); throw new ServiceException(Error.date_format);}
//		catch (NumberFormatException x) {
//			item.setMessage("Number format, " + x.getMessage());
//			sqlSession.rollback(); throw new ServiceException(Error.number_format);}
//		catch(Throwable x) {
//			item.setMessage("Widget book, " + x.getMessage());
//			sqlSession.rollback(); throw new ServiceException(Error.widget_book);}
//		finally {sqlSession.close();}
//
//		try{
//			sqlSession = RazorServer.openSession();
//			if(paymentTransaction != null) {
//				sqlSession.getMapper(PaymentTransactionMapper.class).create(paymentTransaction);
//				sqlSession.commit();
//			}
//		}
//		catch(Throwable x) {
//			sqlSession.rollback();
//			throw new ServiceException(Error.widget_book);
//		}
//		finally {sqlSession.close();}
		
		return item;
	}
	
	public static void main(String[] args) throws NumberParseException {
		ReservationPaymentHandler handler = new ReservationPaymentHandler();
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("pos", "d9b0dc2061815e25"); 						// the point of sale code.
		parameters.put("productid", "270"); 							// the ID of the property
		parameters.put("fromdate", "2014-02-09");						// the date from which availability is to be shown.
		parameters.put("todate", "2014-02-10");							// the date to which availability is to be shown.
		parameters.put("emailaddress", "melnikov.roman@gmail.com"); 	// the email address of the guest.
		parameters.put("familyname", "Roman");							// the family name of the guest.
		parameters.put("firstname", "Melnykov");						// the first name of the guest.
		parameters.put("notes", "Note");								// the guest notes or requests.
		parameters.put("cardnumber", "4111111111111111");				// the card number.
		parameters.put("cardmonth", "12");								// the card expiry month.
		parameters.put("cardyear", "2015");								// the card expiry year.
		parameters.put("amount", "52.00");								// the amount to be charged to the card.
		parameters.put("telnumber", "0954978744");
		parameters.put("cardtype", "visa");
		handler.execute(parameters);
	}
	
	private static String parsePhoneNumber(String phoneNumber) throws NumberParseException {
		PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
		Long longNumber = Long.valueOf(phoneNumberUtil.normalizeDigitsOnly(phoneNumber));
		PhoneNumber number = new PhoneNumber();
		number.setCountryCode(38);
		number.setNationalNumber(longNumber);
		number = phoneNumberUtil.parse(phoneNumber, "UA");
		return phoneNumberUtil.format(number, PhoneNumberFormat.E164);

		/*char[] number = phoneNumber.toCharArray();
		StringBuilder finalResult = new StringBuilder("(");
		int i = 0;
		for(char character : number) {
			if(Character.isDigit(character)) {
				finalResult.append(character);
				i++;
				if(i == 3) {
					finalResult.append(") ");
				} else if(i == 6) {
					finalResult.append("-");
				}
			}
		}*/
		
//		return finalResult.toString();
		
	}
}
