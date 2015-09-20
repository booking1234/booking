package net.cbtltd.rest.summitcove;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.script.Handleable;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class _Test implements Handleable {
	private static final Logger LOG = Logger.getLogger(_Test.class.getName());

	/**
	 * The main test method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String args[]) throws Exception {
		LOG.error("Summitcove Started");
		SqlSession sqlSession = RazorServer.openSession();
		try {
			String altpartyid = "99064"; //Summit Cove test manager
			String reservationid = "1514067"; //Summit Cove test reservation
			
			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
			if (partner == null) {throw new ServiceException(Error.party_id, altpartyid);}
			A_Handler handler = new A_Handler(partner);
			handler.readProducts();
//			handler.readPrices();
//			handler.readSchedule();
//			Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationid);
//			if (reservation == null) {LOG.error("No Reservation " + reservationid);}
//
//			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
//			if (product == null) {LOG.error("No Product " + reservation.getProductid());}

//			System.out.println("isAvailable = " + handler.isAvailable(sqlSession, reservation, product.getAltid()));
//			handler.createReservation(sqlSession, reservation);
//			handler.readReservation(sqlSession, reservation);
//			handler.updateReservation(sqlSession, reservation);
//			handler.cancelReservation(sqlSession, reservation);
		}
		catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
			LOG.error(x.getMessage());
		} 
		finally {sqlSession.close();}
		LOG.error("Summitcove Terminated");
		System.exit(0);
	}

	private A_Handler getHandler() {
		SqlSession sqlSession = RazorServer.openSession();
		String altpartyid = "99064";
		Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
		if (partner == null) {throw new ServiceException(Error.party_id, altpartyid);}
		A_Handler handler = new A_Handler(partner);
		return handler;
	}
	
	@Override
	public void readProducts() {
		getHandler().readProducts();
	}

	@Override
	public void readPrices() {
		getHandler().readPrices();
	}

	@Override
	public void readSchedules() {
		getHandler().readSchedule();
	}

	@Override
	public void readAlerts() {
		getHandler().readAlerts();
	}

	@Override
	public void readSpecials() {
		getHandler().readSpecials();
	}
	
	@Override
	public void readLocations() {
		getHandler().readLocations();
	}

	@Override
	public void readDescriptions() {
		getHandler().readDescriptions();
	}

	@Override
	public void readImages() {
		// TODO Auto-generated method stub
		
	}

}
