
package net.cbtltd.rest.lodgix;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.script.Handleable;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Reservation.State;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.CreditCardType;

import org.apache.ibatis.session.SqlSession;

/**
 * This class was generated by Apache CXF 2.4.0
 * 2012-08-09T13:20:11.648+02:00
 * Generated source version: 2.4.0
 * 
 */
public final class _Test implements Handleable {

	private String altpartyid;
	
	public	_Test(String altpartyid){
		this.altpartyid = altpartyid;
	}
	
	public _Test() {}
	
	/**
	 * The main test method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String args[]) throws Exception {
		System.out.println("Lodgix Test Started");
		SqlSession sqlSession = RazorServer.openSession();
		try {
//			String altpartyid = "270205"; //Test - Lodgix (advertiser ID - 8921) 
//			String altpartyid = "325852"; //UAT DB - Test - Lodgix (advertiser ID - 8921) 
			String altpartyid = "270206"; //Hale Ko'olau - Lodgix (advertiser ID - 5889) 
			
			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
			if (partner == null) {throw new ServiceException(Error.party_id, altpartyid);}
			A_Handler handler = new A_Handler(partner);
//
//		    handler.readProducts();			
			
			handler.readPrices();
//			handler.readSchedule();  	
//			handler.readImages();
		//	handler.readAlerts();
//			Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationid);
//			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());

			String productId = "60073"; //Local altId=25095 
//			String productId = "426530"; // UAT altId=25095 
//			Reservation reservation = new Reservation(); 
//				        reservation.setProductid(productId); //this is ID of Lodgex unitID = 25095
//				        reservation.setId("18"); //set some reservation ID from DB. this not exist at the moment
//				        reservation.setAltid("7698069");
//				        reservation.setConfirmationId("672ab3dd4c832fa5e008b4d611566baae04ab3a9e6e6e870fe3acde94bd91ab0");
//				        reservation.setAdult(2);
//				        reservation.setChild(0);
//				        reservation.setCurrency(Currency.Code.USD.name());
//				        reservation.setCustomerid("264637");
//				        reservation.setAgentid(altpartyid);
//				        
//			DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
//						reservation.setFromdate(DF.parse("2015-08-01"));
//						reservation.setTodate(DF.parse("2015-08-05"));  
//						reservation.setState(State.Provisional.name()); 
// 
//						
//			CreditCard creditCard = new CreditCard();
//			creditCard.setNumber("4222222222222");
//			creditCard.setType(CreditCardType.VISA);
//			creditCard.setMonth("2");
//			creditCard.setYear("2015");
//			creditCard.setSecurityCode("123");
			
//			System.out.println("isAvailable = " + handler.isAvailable(sqlSession, reservation));
			
//			handler.readPrice(sqlSession, reservation, "25095", Currency.Code.USD.name());
//			handler.createReservationAndPayment(sqlSession, reservation, creditCard);
				
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
		System.out.println("Lodgix Terminated. No errors");
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


//	public void createImages() {
//		getHandler().createImages();
//	}
	
	@Override
	public void readSpecials() {
		getHandler().readSpecials();
	}
	
	/*
	public static void testAttribute(SqlSession sqlSession) {
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add("PCT3");
		ArrayList<NameId> pctList = sqlSession.getMapper(AttributeMapper.class)
														.pctListValue(new net.cbtltd.shared.Attribute("PCT", attributes.get(0).substring("PCT".length())));
		for(NameId attribute:pctList) {
			System.out.println("Attribute name: " + attribute.getName());
		}
	}
	*/
	
	@Override
	public void readLocations() {
		throw new ServiceException(Error.service_absent, "Lodgix readLocations()");
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