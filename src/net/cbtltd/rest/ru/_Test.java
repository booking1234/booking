package net.cbtltd.rest.ru;

import net.cbtltd.rest.ru.A_Handler;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.script.Handleable;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public final class _Test implements Handleable{
	private static final Logger LOG = Logger.getLogger(_Test.class.getName());
	
	private String altpartyid;
	
	public _Test(String altpartyid) {
		this.altpartyid = altpartyid;
	}

	public _Test() {}
	
	public static void main(String args[]) throws Exception {
		LOG.error("RentalsUnited Started");
		SqlSession sqlSession = RazorServer.openSession();
		try {
			String altpartyid = "325009";//"325009";//"113547";
//			String reservationid = "8471208"; //RentalsUnited reservation
//			System.out.println(Model.decrypt("a502d2c65c2f75d3"));
			
			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
			if (partner == null) {throw new ServiceException(Error.party_id, altpartyid);}
			A_Handler handler = new A_Handler(partner);
//			handler.readLocations();
			handler.readProducts();
//			handler.readSchedule();
//			handler.readMinStay();
//			handler.readPrices();
//			System.out.println(Model.encrypt("144"));
//			Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationid);
//			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
//			System.out.println("isAvailable = " + handler.isAvailable(sqlSession, reservation));
//			handler.readPrice(sqlSession, reservation, product.getAltid(), "USD");
//			handler.createReservationAndPayment(sqlSession, reservation, initCreditCard());
//			handler.readReservation(sqlSession, reservation);
//			handler.updateReservation(sqlSession, reservation);
//			handler.cancelReservation(sqlSession, reservation);
//			System.out.println("isAvailable = " + handler.isAvailable(sqlSession, reservation));
			sqlSession.commit();
		}
		catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
		}
		finally {sqlSession.close();}
		LOG.error("RentalsUnited Terminated. ");
		System.exit(0);
	}
	
	private A_Handler getHandler() {
		SqlSession sqlSession = RazorServer.openSession();
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