package net.cbtltd.rest.pathway;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;


import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.AttributeMapper;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.script.Handleable;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Reservation.State;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.CreditCardType;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;


/*
 * Pathway GDS
 */
public final class _Test implements Handleable {

	private String altpartyid;
	
	public	_Test(String altpartyid)
	{
		this.altpartyid = altpartyid;
	}
	
	public _Test() {}
	
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * The main test method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String args[]) throws Exception {
		System.out.println("Pathway Test Started");
		SqlSession sqlSession = RazorServer.openSession();
		try {
//			String altpartyid = "270200"; //Pathway partner
			String altpartyid = "270201"; //Interhome (Pathway) partner
//			String altpartyid = "325821"; // UAT Solmar partner
			
			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
			if (partner == null) {throw new ServiceException(Error.party_id, altpartyid);}
			A_Handler handler = new A_Handler(partner);
//
//		    handler.readProducts();
//		    handler.readImages();
//			
			handler.readPrices();
//			handler.readSchedule(); 
		//	handler.readAlerts();
		//	Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationid);
//			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			
			
////		    String productAltId = "59185";//this is ID of Pathway accommodation = 8676
//		    String productAltId = "59194";//this is ID of Pathway accommodation = 6871
//			Reservation reservation = new Reservation(); 
//				        reservation.setProductid(productAltId); 
//				        reservation.setId("173"); //set some reservation ID from DB. this not exist at the moment
//				        reservation.setAdult(1);
//				        reservation.setChild(0);
//				        reservation.setCurrency(Currency.Code.GBP.name());
//				        reservation.setCustomerid("270060");
//				        reservation.setAgentid(altpartyid);
//				        reservation.setAltid("15607"); //one created reservation in Pathway system
//				        
//			DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
//						reservation.setFromdate(DF.parse("2014-11-21"));
//						reservation.setTodate(DF.parse("2014-11-28"));  
//						reservation.setState(State.Provisional.name());
//			
//			
//			handler.createReservation(sqlSession, reservation);
//			Map<String,String> createResAndPaymentResult = handler.createReservationAndPayment(sqlSession, reservation, creditCard);
			
//			System.out.println("isAvailable = " + handler.isAvailable(sqlSession, reservation));
			
//			handler.readPrice(sqlSession, reservation, productAltId, Currency.Code.GBP.name());
			
				
//			handler.readReservation(sqlSession, reservation);
//			handler.updateReservation(sqlSession, reservation);
//			handler.cancelReservation(sqlSession, reservation);
//			sqlSession.commit();
			
		} 
		catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
		} 
		finally {sqlSession.close();}
		System.out.println("Pathway Terminated. No errors");
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
		getHandler().readImages();
	}
}
