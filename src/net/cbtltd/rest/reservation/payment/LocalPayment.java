package net.cbtltd.rest.reservation.payment;

import java.util.Map;

import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.ReservationRequest;
import net.cbtltd.rest.reservation.CreateReservationContent;
import net.cbtltd.rest.response.ReservationResponse;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ManagerToGatewayMapper;
import net.cbtltd.server.api.PendingTransactionMapper;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.PaymentGatewayProvider;
import net.cbtltd.shared.PendingTransaction;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.PaymentGatewayHolder;

import org.apache.ibatis.session.SqlSession;

public class LocalPayment extends AbstractPayment implements Payable {

	@Override
	public void pay(CreateReservationContent content) throws Exception {
		SqlSession sqlSession = RazorServer.openSession();
		try {
			super.pay(content);
			ReservationRequest request = content.getReservationRequest();
			ReservationResponse response = content.getReservationResponse();
			PropertyManagerInfo propertyManagerInfo = content.getPropertyManagerInfo();
			Reservation reservation = content.getReservation();
			CreditCard creditCard = content.getCreditCard();
			Product product = content.getProduct();
			GatewayHandler handler = content.getHandler();
			ManagerToGateway managerToGateway = null;
			PaymentGatewayProvider paymentGatewayProvider = null;
			Map<String, String> resultMap = null;
			int paymentGatewayId = content.getPaymentGatewayId();
			
			// Processing charge type
			String chargeType = content.getChargeType();
			Double firstPayment = content.getFirstPayment();
			Double secondPayment = content.getSecondPayment();
			int supplierId = Integer.valueOf(product.getSupplierid());
			managerToGateway = sqlSession.getMapper(ManagerToGatewayMapper.class).readBySupplierId(supplierId);
			paymentGatewayId = managerToGateway.getPaymentGatewayId();
			paymentGatewayProvider = PaymentGatewayHolder.getPaymentGateway(paymentGatewayId);
			handler = PaymentHelper.initializeHandler(propertyManagerInfo, managerToGateway, creditCard);
			response.setDownPayment(firstPayment);
			Double amountToCharge = content.getAmountToCharge();
			
			resultMap = PaymentHelper.processPayment(sqlSession, paymentGatewayProvider, amountToCharge, reservation, handler, content.getCurrency(),
						creditCard, paymentGatewayId);
	
			// Pending transaction processing
			if (resultMap != null && chargeType.equalsIgnoreCase(PaymentHelper.DEPOSIT_PAYMENT_METHOD) && resultMap.get(GatewayHandler.STATE).equals(GatewayHandler.ACCEPTED)) {
				PendingTransaction pendingTransaction = PaymentHelper.preparePendingTransaction(sqlSession, request.getPos(), request.getFamilyName(), 
						request.getFirstName(), request.getCardNumber(), request.getPhoneNumber(), content.getReservation(), content.getProduct(),
						supplierId, propertyManagerInfo, secondPayment, paymentGatewayProvider, resultMap, paymentGatewayId);
				sqlSession.getMapper(PendingTransactionMapper.class).create(pendingTransaction);
				content.setPendingTransaction(pendingTransaction);
				sqlSession.commit();
			}
			
			content.setPaymentTransaction(PaymentHelper.preparePaymentTransaction(sqlSession, reservation, propertyManagerInfo, amountToCharge, managerToGateway, request.getCardNumber(),
					Integer.valueOf(content.getChannelPartnerParty().getId()), product, resultMap, false));
	
			content.setAmountToCharge(amountToCharge);
			content.setChargeType(chargeType);
			content.setPaymentGatewayProvider(paymentGatewayProvider);
			content.setResultMap(resultMap);
			content.setManagerToGateway(managerToGateway);
			content.setPaymentGatewayId(paymentGatewayId);
		} finally {
			sqlSession.close();
		}
	}

}
