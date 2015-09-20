package net.cbtltd.rest.streamline;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import net.cbtltd.server.PartnerService;
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


public final class _Test implements Handleable {

	private String altpartyid;
	
	public	_Test(String altpartyid){
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
		System.out.println("Streamline Test Started, " + (new Date()).toString() );
		SqlSession sqlSession = RazorServer.openSession();
		try {
//			String altpartyid = "270186"; //Streamline partner
//			String altpartyid = "325804"; //Getaway Vacations (Streamline)
			String altpartyid = "326062"; //Ocean Dream CR LTDA / Stay in costa Rica
//			String altpartyid = "325809"; //Sandy Beach Rentals and Services - UAT
//			String altpartyid = "325938"; //Winter Park Lodging - UAT
//			String altpartyid = "325915"; //Carolina Properties - UAT
			
			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
			if (partner == null) {throw new ServiceException(Error.party_id, altpartyid);}
			A_Handler handler = new A_Handler(partner);
//
		    handler.readProducts();
			
//			testAttribute(sqlSession);
			
//			handler.readPrices();
//			handler.readSchedule();  	
//			handler.createImages();
		//	handler.readAlerts();
//			Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationid);
//			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());

////			
//			Reservation reservation = new Reservation(); 
//				        reservation.setProductid("58559"); //this is ID of Streamline unitID = 28248
////				        reservation.setProductid("60714"); //this is ID of Streamline unitID = 23202
//				        reservation.setId("26"); //set some reservation ID from DB. this not exist at the moment
//				        reservation.setAltid("2021");
//				        reservation.setAdult(2);
//				        reservation.setChild(0);
//				        reservation.setCurrency(Currency.Code.USD.name());
//				        reservation.setCustomerid("264637");
//				        reservation.setAgentid(altpartyid);
//				        
//			DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
//						reservation.setFromdate(DF.parse("2015-07-20"));
//						reservation.setTodate(DF.parse("2015-07-28"));  
//						reservation.setState(State.Provisional.name());
////						reservation.setAgentid("3330");
//						
//			CreditCard creditCard = new CreditCard();
//			creditCard.setNumber("4444333322221111");
//			creditCard.setType(CreditCardType.VISA);
//			creditCard.setMonth("02");
//			creditCard.setYear("2015");
//			creditCard.setSecurityCode("123");
//			
//			System.out.println("isAvailable = " + handler.isAvailable(sqlSession, reservation));
//			
//			handler.readPrice(sqlSession, reservation, "23202", Currency.Code.USD.name());
//			Map<String,String> createResAndPaymentResult = handler.createReservationAndPayment(sqlSession, reservation, creditCard);
//			
//			handler.createReservation(sqlSession, reservation);	
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
		System.out.println("Streamline Terminated. No errors, "+ (new Date()).toString());
		System.exit(0);
	}

	private A_Handler getHandler() {
		SqlSession sqlSession = RazorServer.openSession();
		//String altpartyid = "325712";
		Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
		if (partner == null) {throw new ServiceException(Error.party_id, altpartyid);}
		A_Handler handler = new A_Handler(partner);
		return handler;
	}
	
//	private A_Handler setHandler(String altpartyid) {
//		SqlSession sqlSession = RazorServer.openSession();
//		//String altpartyid = "179769";
//		Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
//		if (partner == null) {throw new ServiceException(Error.party_id, altpartyid);}
//		A_Handler handler = new A_Handler(partner);
//		return handler;
//	}
	
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
		throw new ServiceException(Error.service_absent, "Streamline readLocations()");
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
