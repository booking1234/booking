
package net.cbtltd.rest.maxxton;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.util.Date;

//import net.cbtltd.rest.interhome.A_Handler;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.script.Handleable;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Reservation.State;
import net.cbtltd.shared.api.HasUrls;

import org.apache.ibatis.session.SqlSession;

/**
 * This class was generated by Apache CXF 2.4.0
 * 2012-08-23T11:53:42.957+02:00
 * Generated source version: 2.4.0
 * 
 */
public final class _Test implements Handleable {


	public _Test() { super(); }

	public static void main(String args[]) throws Exception {
		System.out.println("Maxxton Test Started");
		SqlSession sqlSession = RazorServer.openSession();
		try {
			
//			String altpartyid = "189792"; //Maxxton test manager
			String altpartyid = "206546"; //Maxxton test manager
			String reservationid = "6565980"; //Maxxton reservation for 827 product
			
//			 Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(sqlSession.getMapper(PartnerMapper.class).partyIDFromEmail(HasUrls.PARTY_MAXXTON_EMAIL));
			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
			 if (partner == null) {throw new ServiceException(Error.party_id, altpartyid);}
			 A_Handler handler = new A_Handler(partner);
			 //if (handler == null) {throw new ServiceException(Error.service_absent, "Handler for " + altpartyid);}

			 
//			handler.readProducts();
//			handler.readPrices();
			handler.readSchedule();
			 
			 
			 
//			Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationid);
//			Reservation reservation  = createReservation();
//			System.out.println("isAvailable = " + handler.isAvailable(sqlSession, reservation));
//			handler.readPrice(sqlSession, reservation, "827", Currency.Code.EUR.name());
			
//			handler.CreateCustomerTest(sqlSession, "270060");
			
//			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());

			
//			reservation.setId("172");
//			handler.createReservation(sqlSession, reservation);
//			reservation.setAltid("44738773");
//			handler.readReservation(sqlSession, reservation);
//			handler.updateReservation(sqlSession, reservation);
//			handler.cancelReservation(sqlSession, reservation);
//			sqlSession.commit();
		} 
		catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
			//System.out.println(x.getStackTrace());
		} 
		finally {sqlSession.close();}
		System.out.println("Maxxton Terminated");
		System.exit(0);
	}
 
	
	public static Reservation createReservation() {
		String altpartyid = "206546"; 

//		String productid = "58358"; //this is altId 827 (resourceId in Maxxton)
		String productid = "61187"; //this is altId 414003 (resourceId in Maxxton)
		Date fromdate = new Date("12/04/2014"); // m/dd/yyyy
		Date todate = new Date("12/05/2014");
		String customerid = "270060";
		

//		System.out.println(fromdate.toString());
//		System.out.println(todate.toString());
		
		Reservation reservation = new Reservation();
		reservation.setProductid(productid);
		reservation.setAgentid(altpartyid);
		reservation.setCustomerid(customerid);
		reservation.setState(State.Provisional.name());
		reservation.setAltid("44827507");//one maxxton reservationID
		reservation.setCurrency(Currency.Code.EUR.name());
		
		
		reservation.setAdult(2);
		//reservation.setChild(1);
		

		reservation.setFromdate(fromdate);
		reservation.setTodate(todate);

		return reservation;
	}

	private A_Handler getHandler() {
		SqlSession sqlSession = RazorServer.openSession();
		
		String altpartyid = "189792";
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
		throw new ServiceException(Error.service_absent, "Maxxton readLocations()");
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
