package net.cbtltd.rest.reservation.payment;

import java.util.Map;

import net.cbtltd.rest.ReservationRequest;
import net.cbtltd.rest.reservation.CreateReservationContent;
import net.cbtltd.rest.response.ReservationResponse;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ManagerToGatewayMapper;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.PaymentGatewayProvider;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.finance.gateway.PaymentGatewayHolder;

import org.apache.ibatis.session.SqlSession;

public class LocalNovasolPayment extends AbstractPayment implements Payable {

	@Override
	public void pay(CreateReservationContent content) throws Exception {
		SqlSession sqlSession = RazorServer.openSession();
		try {
			super.pay(content);
			ReservationResponse response = content.getReservationResponse();
			ReservationRequest request = content.getReservationRequest();
			PropertyManagerInfo propertyManagerInfo = content.getPropertyManagerInfo();
			Reservation reservation = content.getReservation();
			Product product = content.getProduct();
			ManagerToGateway managerToGateway = null;
			PaymentGatewayProvider paymentGatewayProvider = null;
			Map<String, String> resultMap = null;
			int paymentGatewayId = content.getPaymentGatewayId();
			
			// Processing charge type
			String chargeType = content.getChargeType();
			Double firstPayment = content.getFirstPayment();
			int supplierId = Integer.valueOf(product.getSupplierid());
			managerToGateway = sqlSession.getMapper(ManagerToGatewayMapper.class).readBySupplierId(supplierId);
			paymentGatewayId = managerToGateway.getPaymentGatewayId();
			paymentGatewayProvider = PaymentGatewayHolder.getPaymentGateway(paymentGatewayId);
			response.setDownPayment(firstPayment);
			
			content.setPaymentTransaction(PaymentHelper.preparePaymentTransaction(sqlSession, reservation, propertyManagerInfo, firstPayment, managerToGateway, request.getCardNumber(),
					Integer.valueOf(content.getChannelPartnerParty().getId()), product, resultMap, false));
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
