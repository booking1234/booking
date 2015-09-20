package net.cbtltd.rest.reservation.payment;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.ReservationRequest;
import net.cbtltd.rest.reservation.CreateReservationContent;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.PendingTransactionMapper;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.shared.PendingTransaction;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.finance.gateway.CreditCard;

public class ApiPayment extends AbstractPayment implements Payable {

	@Override
	public void pay(CreateReservationContent content) throws Exception {
		super.pay(content);
		Reservation reservation = content.getReservation();
		CreditCard creditCard = content.getCreditCard();
		SqlSession sqlSession = RazorServer.openSession();
		PropertyManagerInfo propertyManagerInfo = content.getPropertyManagerInfo();
		Product product = content.getProduct();
		ReservationRequest request = content.getReservationRequest();
		Double amountToCharge = content.getAmountToCharge();
		 
		try {
			if(StringUtils.isEmpty(reservation.getState())) { 
				reservation.setState(Reservation.State.Confirmed.name());
			}
			Map<String, String> resultMap = PartnerService.createReservationAndPayment(sqlSession, reservation, creditCard);
			String chargeType = PaymentHelper.getChargeType(propertyManagerInfo, reservation);
			if (resultMap != null && PaymentHelper.isDepositPaymentMethod(chargeType) && resultMap.get(GatewayHandler.STATE).equals(GatewayHandler.ACCEPTED)) {
				PendingTransaction pendingTransaction = PaymentHelper.preparePendingTransaction(sqlSession, request.getPos(), request.getFamilyName(), 
						request.getFirstName(), request.getCardNumber(), request.getPhoneNumber(), content.getReservation(), content.getProduct(),
						propertyManagerInfo.getPropertyManagerId(), propertyManagerInfo, content.getSecondPayment(), null, resultMap, null);
				sqlSession.getMapper(PendingTransactionMapper.class).create(pendingTransaction);
				content.setPendingTransaction(pendingTransaction);
				sqlSession.commit();
			}
			
			content.setPaymentTransaction(PaymentHelper.preparePaymentTransaction(sqlSession, reservation, propertyManagerInfo, amountToCharge, null, request.getCardNumber(),
					Integer.valueOf(content.getChannelPartnerParty().getId()), product, resultMap, false));
			content.setResultMap(resultMap);
		} finally {
			sqlSession.close();
		}
	}
}
