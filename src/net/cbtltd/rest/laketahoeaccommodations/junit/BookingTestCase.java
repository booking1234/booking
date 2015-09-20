package net.cbtltd.rest.laketahoeaccommodations.junit;

import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import net.cbtltd.rest.laketahoeaccommodations.A_Handler;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.CreditCardType;

import org.junit.*;
import org.apache.ibatis.session.SqlSession;

public class BookingTestCase {
	
	CreditCard creditCard;
	Partner partner;
	SqlSession sqlSession;
	A_Handler handler;
	Reservation reservation;
	Product product;
	
	
	String altpartyid;
	String reservationid;
	
	
	@Before 
	public void setUp() throws Exception {
		String altpartyid = "189790";
		creditCard = new CreditCard();
		reservation = new Reservation();
		product = new Product();
		sqlSession = RazorServer.openSession();
		partner =  sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
		if (partner == null) { fail(Error.party_id+ altpartyid);}
	    handler = new A_Handler(partner);
		setUpCreditCard(creditCard);
		setUpReservation(reservation);
		setUpProduct(product);
	}
	
	private void setUpCreditCard(CreditCard creditCard) {
		creditCard.setFirstName("John");
		creditCard.setLastName("Doe");
		creditCard.setYear("2015");
		creditCard.setMonth("09");
		creditCard.setSecurityCode("321");
		creditCard.setNumber("4111111111111111");
		creditCard.setType(CreditCardType.VISA);
	}
	
	private void setUpProduct(Product product) {
		product = sqlSession.getMapper(ProductMapper.class).read("323932");
	}
	
	private void setUpReservation(Reservation reservation) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.MONTH, Calendar.JUNE);
		cal.set(Calendar.DATE, 28);
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date fromDate = cal.getTime();
		
		cal.set(Calendar.MONTH, Calendar.JULY);
		cal.set(Calendar.DATE, 2);
		
		Date toDate = cal.getTime();
		
		Date currentDate = Calendar.getInstance().getTime();
		
		
		reservation.setFirstname("David");
		reservation.setFamilyname("Lepe");
		reservation.setEmailaddress("dlepe@uci.edu");
		reservation.setOrganizationid("189790");
		reservation.setProductid("323932");
		//reservation.setActorid("189790");
		reservation.setAdult(2);
		reservation.setDate(currentDate);
		reservation.setFromdate(fromDate);
		reservation.setTodate(toDate);
		reservation.setNotes("Please give me a good time at Lake Tahoe!");
		reservation.setAgentid("1");
		reservation.setState("Provisional");
		reservation.setDeposit(100.00);
		reservation.setCurrency(partner.getCurrency());
		reservation.setCustomerid("6");
		reservation.setUnit("DAY");
		reservation.setArrivaltime("8:00:00");
		reservation.setDeparturetime("10:00:00");
	}
}