package net.cbtltd.rest.aaxsys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.script.Handleable;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Reservation;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;


public final class _Test implements Handleable {

	private String altpartyid;
	
	public	_Test(String altpartyid)
	{
		this.altpartyid = altpartyid;
	}
	
	public _Test() {}
	
	private static final Logger LOG = Logger.getLogger(_Test.class.getName());
	

	/**
	 * The main test method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String args[]) throws Exception {
		
		LOG.error("Aaxsys API Test Started");
		SqlSession sqlSession = RazorServer.openSession();
		
		try {
			
			String altpartyid = "325770"; 
//			String altpartyid = "179895"; //Aaxsys partner Production
			//String reservationid = "7233443"; //Aaxsys reservation


			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
			//Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(sqlSession.getMapper(PartnerMapper.class).partyIDFromEmail(HasUrls.PARTY_AAXSYS_EMAIL));
			if (partner == null) {
				throw new ServiceException(Error.party_id, altpartyid);
			}
			A_Handler handler = new A_Handler(partner);
			//if (handler == null) {
			//	throw new ServiceException(Error.service_absent, "Handler for "+ altpartyid);
			//}
			
//			handler.createImages(); 
			
 		    handler.readProducts();
//			handler.readSchedule();
				
//			handler.readPrices();

//			handler.readSchedule(); // where the pricing is done. 		
//			handler.readAlerts();
//			Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationid);
//			DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
//			reservation.setFromdate(DF.parse("2014-06-16"));
//			reservation.setTodate(DF.parse("2014-07-31"));

//		   Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
//			System.out.println("isAvailable = " + handler.isAvailable(sqlSession, reservation));
			
//			Reservation reservation = new Reservation(); 
//				        reservation.setProductid("314024");
//				        reservation.setAltid("92-1086");
//				        reservation.setSupplierid("HIIC");
				        //reservation.setAdult(1);
				        //reservation.setId("111111");
//			DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
//						reservation.setFromdate(DF.parse("2014-12-01"));
//						reservation.setTodate(DF.parse("2014-12-31")); //30 days. 
	//					reservation.setState("Briefed");
	//					reservation.setAgentid("3330");
	//		handler.createReservation(sqlSession, reservation);
			
				
//			handler.readReservation(sqlSession, reservation);
//			handler.updateReservation(sqlSession, reservation);
//			handler.cancelReservation(sqlSession, reservation);
			
			sqlSession.commit();
		} 
		catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
		} 
		finally {sqlSession.close();}
		LOG.error("Aaxsys Terminated not an error. ");
		System.exit(0);
	}

	private A_Handler getHandler() {
		SqlSession sqlSession = RazorServer.openSession();
		//String altpartyid = "189858";
		Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
		if (partner == null) {throw new ServiceException(Error.party_id, altpartyid);}
		A_Handler handler = new A_Handler(partner);
		return handler;
	}
	
	private A_Handler setHandler(String altpartyid) {
		SqlSession sqlSession = RazorServer.openSession();
		//String altpartyid = "179895";
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


	public void createImages() {
		//getHandler().createImages();
	}
	
	@Override
	public void readSpecials() {
		getHandler().readSpecials();
	}

	@Override
	public void readLocations() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readDescriptions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readImages() {
		// TODO Auto-generated method stub
		
	}
}
