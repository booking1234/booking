package net.cbtltd.rest.leisurelink;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import net.cbtltd.rest.leisurelink.A_Handler;
import net.cbtltd.shared.Reservation;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.script.Handleable;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Reservation.State;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.CreditCardType;

public final class _Test implements Handleable {
	private String altpartyid;
	
	
	public	_Test(String altpartyid)
	{
		this.altpartyid = altpartyid;
	}
	
	public _Test() {}
	
	private static final Logger LOG = Logger.getLogger(_Test.class.getName());
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LOG.error("LeisureLink Test Started");
		SqlSession sqlSession = RazorServer.openSession();
		try {
//			String altpartyid = "179899";
			String altpartyid = "270207";
			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
			if (partner == null) {throw new ServiceException(Error.party_id, altpartyid);}
			A_Handler handler = new A_Handler(partner);
//			handler.readProducts();
//			handler.readImages();
//			handler.readPrices();
//			handler.readSchedule();
			
			
			Reservation reservation= new Reservation();
			DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");

			//reservation.setProductid("315100");
			
			reservation.setProductid("60823"); //LL altId = 04554-8872 
			reservation.setFromdate(DF.parse("2015-03-24"));
			reservation.setTodate(DF.parse("2015-03-28"));
			
//			reservation.setProductid("60836"); //LL altId = 01831-4403
//			reservation.setFromdate(DF.parse("2014-11-28"));
//			reservation.setTodate(DF.parse("2014-11-30"));
			
			reservation.setAdult(2);
			reservation.setChild(0);
			reservation.setCurrency(Currency.Code.USD.name());
			reservation.setId("19");
			reservation.setState(State.Provisional.name());
			reservation.setCustomerid("264637");
		    reservation.setAgentid(altpartyid);
		    reservation.setAltid("L997SERNX9098");
		    
		    CreditCard creditCard = new CreditCard();
			creditCard.setNumber("4111111111111111");
			creditCard.setType(CreditCardType.VISA);
			creditCard.setMonth("2");
			creditCard.setYear("2015");
			creditCard.setSecurityCode("123");
			
			System.out.println("isAvailable = " + handler.isAvailable(sqlSession, reservation));
//			
//			handler.readPrice(sqlSession, reservation, null, Currency.Code.USD.name());
//			handler.createReservationAndPayment(sqlSession, reservation, creditCard);
//			
//			handler.cancelReservation(sqlSession, reservation);
			
//			handler.createReservation(sqlSession, reservation);
//			sqlSession.commit();
		} 
		catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
		} 
		finally {sqlSession.close();}
		System.out.println("LeisureLink Terminated. No errors. ");
		System.exit(0);	

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
		throw new ServiceException(Error.service_absent, "Leisure Link readLocations()");
		
	}

	@Override
	public void readDescriptions() {
		throw new ServiceException(Error.service_absent, "Leisure Link readDescriptions()");
		
	}

	@Override
	public void readImages() {
		getHandler().readImages();	
	}
	
	private A_Handler getHandler() {
		SqlSession sqlSession = RazorServer.openSession();
		String altpartyid = "325709";
		Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
		if (partner == null) {throw new ServiceException(Error.party_id, altpartyid);}
		A_Handler handler = new A_Handler(partner);
		return handler;
	}
	
//	private A_Handler setHandler(String altpartyid) {
//		SqlSession sqlSession = RazorServer.openSession();
//		//String altpartyid = "179895";
//		Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
//		if (partner == null) {throw new ServiceException(Error.party_id, altpartyid);}
//		A_Handler handler = new A_Handler(partner);
//		return handler;
//	}

}
