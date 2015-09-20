package net.cbtltd.rest.reservation.partner;

import java.util.Map;

import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.ReservationRequest;
import net.cbtltd.rest.reservation.CreateReservationContent;
import net.cbtltd.rest.response.ReservationResponse;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.PaymentGatewayProvider;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;

import org.apache.ibatis.session.SqlSession;

public class NovasolPartnerReserve extends DefaultPartnerReserve implements PartnerReserve {

	@Override
	public void partnerReserve(CreateReservationContent content)
			throws Exception {
		SqlSession sqlSession = RazorServer.openSession();
		ReservationRequest request = content.getReservationRequest();
		ReservationResponse response = content.getReservationResponse();
		Product product = content.getProduct();
		Reservation reservation = content.getReservation();
		PaymentGatewayProvider paymentGatewayProvider = content.getPaymentGatewayProvider();
		Party channelPartnerParty = content.getChannelPartnerParty();
		Map<String, String> resultMap = content.getResultMap();
		super.partnerReserve(content);
		try {
			if(!reservation.getState().equals(Reservation.State.Cancelled)) {
				resultMap = PaymentHelper.processPayment(sqlSession, paymentGatewayProvider, content.getAmountToCharge(), reservation, content.getHandler(),
						content.getCurrency(), content.getCreditCard(), content.getPaymentGatewayId());
				content.setPaymentTransaction(PaymentHelper.preparePaymentTransaction(sqlSession, reservation, content.getPropertyManagerInfo(),
						content.getFirstPayment(), content.getManagerToGateway(), request.getCardNumber(),
						Integer.valueOf(channelPartnerParty.getId()), product, resultMap, false));
				if(resultMap == null || !resultMap.get(GatewayHandler.STATE).equals(GatewayHandler.ACCEPTED)) {
					LOG.error("An error occurred while processing your payment");
					PartnerService.cancelReservation(sqlSession, reservation);
					reservation.setState(Reservation.State.Cancelled.name());
					if(resultMap != null) {
						response.setErrorMessage(resultMap.get(GatewayHandler.ERROR_MSG));
					} else {
						response.setErrorMessage("Something went wrong with payment processing");
					}
				} else {
					PartnerService.confirmReservation(sqlSession, reservation);
				}
				content.setResultMap(resultMap);
			}
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}

	}

}
