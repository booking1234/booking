package net.cbtltd.rest.reservation.partner;

import net.cbtltd.rest.reservation.CreateReservationContent;
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

public class DefaultPartnerReserve extends AbstractPartnerReserve implements PartnerReserve {

	protected static final Logger LOG = Logger.getLogger(DefaultPartnerReserve.class);
	
	@Override
	public void partnerReserve(CreateReservationContent content) throws Exception {
		SqlSession sqlSession = RazorServer.openSession();
		if(!super.checkAndProcessFailedPayment(sqlSession, content)) {
			Product product = content.getProduct();
			String chargeType = content.getChargeType();
			Reservation reservation = content.getReservation();
			try {
				if(product.hasAltpartyid() && !PaymentHelper.isApiPaymentMethod(chargeType)) {
					if(reservation.getState() == null) {
						reservation.setState(Reservation.State.Confirmed.name());
					}
					try {
						PartnerService.createReservation(sqlSession, reservation);
					} catch (ServiceException e){
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

}
