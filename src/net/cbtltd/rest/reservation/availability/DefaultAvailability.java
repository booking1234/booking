package net.cbtltd.rest.reservation.availability;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import net.cbtltd.rest.ReservationRequest;
import net.cbtltd.rest.reservation.CreateReservationContent;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.SessionService;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Serial;

public class DefaultAvailability implements Available {

	@Override
	public void available(CreateReservationContent content) {
		SqlSession sqlSession = RazorServer.openSession();
		try {
			Reservation reservation = content.getReservation();
			ReservationRequest request = content.getReservationRequest();
			boolean available = sqlSession.getMapper(ReservationMapper.class).available(reservation);
			if (!available) {throw new ServiceException(Error.product_not_available);}
			reservation.setState(Reservation.State.Provisional.name());
			
			reservation.setName(SessionService.pop(sqlSession, reservation.getOrganizationid(), Serial.RESERVATION));
						
			reservation.setCollisions(ReservationService.getCollisions(sqlSession, reservation));
			if (reservation.hasCollisions()) {throw new ServiceException(Error.product_not_available, reservation.getProductFromToDate());}
			
			if(StringUtils.isNotEmpty(request.getAltId())){
				reservation.setAltid(request.getAltId());	
			}
			
			// TODO : it was not SQL session commitment here so we need to call update for reservation in further callings
			sqlSession.getMapper(ReservationMapper.class).create(reservation); 
			
			ReservationService.createEvent(sqlSession, null, reservation, content.getCreditCard());
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
	
}
