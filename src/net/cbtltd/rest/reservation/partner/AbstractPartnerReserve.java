package net.cbtltd.rest.reservation.partner;

import java.util.Map;

import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.reservation.CreateReservationContent;
import net.cbtltd.rest.response.ReservationResponse;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public abstract class AbstractPartnerReserve implements PartnerReserve {

	protected static final Logger LOG = Logger.getLogger(AbstractPartnerReserve.class);	
	
	// TODO : we don't need to call it for Novasol
	public boolean checkAndProcessFailedPayment(SqlSession sqlSession, CreateReservationContent content) {
		Reservation reservation = content.getReservation();
		Map<String, String> resultMap = content.getResultMap();
		ReservationResponse response = content.getReservationResponse();
//		PaymentGatewayProvider paymentGatewayProvider = content.getPaymentGatewayProvider();
		//  || (paymentGatewayProvider != null && paymentGatewayProvider.getName().equals(PaymentGatewayHolder.DIBS))
		if(resultMap == null || !resultMap.get(GatewayHandler.STATE).equals(GatewayHandler.ACCEPTED)) {
			reservation.setState(Reservation.State.Cancelled.name());
			sqlSession.getMapper(ReservationMapper.class).update(reservation);		
			if(resultMap != null && resultMap.get(GatewayHandler.ERROR_MSG) != null) {
				LOG.error("An error occurred while processing your payment");
				response.setErrorMessage(resultMap.get(GatewayHandler.ERROR_MSG));
			} else {
				LOG.error("An error occurred while processing your payment");
				response.setErrorMessage("Something went wrong with payment processing");
			}
			return true;
		}
		return false;
	}
	
	public void createPartnerReservation(CreateReservationContent content) {
		SqlSession sqlSession = RazorServer.openSession();
		Product product = content.getProduct();
		String chargeType = content.getChargeType();
		Reservation reservation = content.getReservation();
		try {
			if(product.hasAltpartyid() && !PaymentHelper.isApiPaymentMethod(chargeType)) {
				if(reservation.getState() == null || reservation.getState().equals(Reservation.State.Provisional)) {
					reservation.setState(Reservation.State.Confirmed.name());
				}
				try {
					PartnerService.createReservation(sqlSession, reservation);
				} catch (ServiceException e){
					reservation.setState(Reservation.State.Cancelled.name());
					LOG.error(e.getMessage());
				}
			}
			sqlSession.getMapper(ReservationMapper.class).update(reservation);
			sqlSession.commit();
			MonitorService.update(sqlSession, Data.Origin.JQUERY, NameId.Type.Reservation, reservation);
		} finally {
			sqlSession.close();
		}
	}

}