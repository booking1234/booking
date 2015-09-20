package net.cbtltd.rest.nextpax.junit;

import static org.junit.Assert.*;

import java.security.Provider;
import java.security.Security;
import java.util.Date;

import net.cbtltd.rest.nextpax.A_Handler;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Reservation.State;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.CreditCardType;

import org.apache.ibatis.session.SqlSession;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Before;
import org.junit.Test;

public class BookingTestCase {

	CreditCard creditCard;
	Partner partner;
	SqlSession sqlSession;
	String altpartyid;
	A_Handler handler;
	Reservation reservation;
	Provider provider;
	@Before
	public void setUp() throws Exception {
//		altpartyid = "179798";
		altpartyid = "210252";
		sqlSession = RazorServer.openSession();
		partner =  sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
		if (partner == null) { fail(Error.party_id+ altpartyid);}
	     handler = new A_Handler(partner);
		setUpCreditCard(creditCard);
		
		Provider[] providers = Security.getProviders();
		for(Provider provider:providers)
			System.out.println("Security provider: " + provider.getName());
     provider = new BouncyCastleProvider();
     
     Security.addProvider(provider);
		
	
	}
	
	@Test
	public void testCreateReservation() {
		
		  handler.createReservationAndPayment(sqlSession, reservation, creditCard);
		fail("Not yet implemented");
	}

	@Test
	public void testCancelReservation() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateReservationAndPayment() {
		fail("Not yet implemented");
	}
	
	@Test
	public void isAvailableReservation() {
		//get random records and test them from all suppliers: http://jan.kneschke.de/projects/mysql/order-by-rand/
		Product product = new Product();
		product.setId("67783");
		product.setSupplierid("210252");
		product = sqlSession.getMapper(ProductMapper.class).read("67783");
		reservation = new Reservation();
		reservation.setProduct(product);
		reservation.setState(State.Provisional.name());
		reservation.setProductid(product.getId());
		reservation.setAgentid("5");
		reservation.setCustomerid("1");
		Date fromDate = new Date(2015, 11, 11);
		reservation.setFromdate(fromDate);
		handler.isAvailable(sqlSession, reservation);
		Provider[] providers = Security.getProviders();
		for(Provider provider:providers)
			System.out.println("Security provider: " + provider.getName());
		Security.removeProvider(provider.getName());
		providers = Security.getProviders();
		for(Provider provider:providers)
			System.out.println("Security provider: " + provider.getName());

	}

	private void setUpCreditCard(CreditCard creditCard)
	{
		creditCard = new CreditCard();
		creditCard.setFirstName("John");
		creditCard.setLastName("Doe");
		creditCard.setYear("2015");
		creditCard.setMonth("09");
		creditCard.setSecurityCode("321");
		creditCard.setNumber("4111111111111111");
		creditCard.setType(CreditCardType.VISA);
	}
}
