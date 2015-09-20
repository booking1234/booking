/**
 * 
 */
package net.cbtltd.rest.tripvillas.xmlfeed;

import java.util.Map;

import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.script.Handleable;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.finance.gateway.CreditCard;

import org.apache.ibatis.session.SqlSession;

/**
 * @author Suresh Kumar S
 * 
 */
public class Launcher implements Handleable {

	private String altpartyid;
	private boolean downloadFlag = true;

	public Launcher(String altpartyid, boolean downloadFlag) {
		super();
		this.altpartyid = altpartyid;
		this.downloadFlag = downloadFlag;
	}

	@Override
	public void readProducts() {
		getHandler(this.downloadFlag).readProducts();
	}

	public A_Handler getHandler(boolean downloadFlag) {

		SqlSession sqlSession = RazorServer.openSession();
		Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(
				altpartyid);
		if (partner == null) {
			throw new ServiceException(Error.party_id, altpartyid);
		}
		return new A_Handler(partner, downloadFlag);
	}

	@Override
	public void readPrices() {
		getHandler(this.downloadFlag).readPrices();

	}

	@Override
	public void readSchedules() {
		getHandler(this.downloadFlag).readSchedule();

	}

	@Override
	public void readAlerts() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readSpecials() {
		// TODO Auto-generated method stub

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
	public void inquireReservation(SqlSession sqlSession,
			Reservation reservation) {
		getHandler(this.downloadFlag).inquireReservation( sqlSession, reservation);
	}
	public boolean isAvailable(SqlSession sqlSession,
			Reservation reservation) {
		return getHandler(this.downloadFlag).isAvailable( sqlSession, reservation);
	}

	public ReservationPrice readPrice(SqlSession sqlSession,
			Reservation reservationDetails, String altid, String currency) {
		return getHandler(this.downloadFlag).readPrice(sqlSession, reservationDetails, altid, currency);
	}
	public void createReservation(SqlSession sqlSession, Reservation reservation){
		getHandler(this.downloadFlag).createReservation(sqlSession, reservation);
	}
	public Map<String, String> createReservationAndPayment(SqlSession sqlSession, Reservation reservation, CreditCard creditCard){
		return getHandler(this.downloadFlag).createReservationAndPayment(sqlSession, reservation, creditCard);
	}
	public void reloadNullLocationProducts(String altpartyid){
		getHandler(this.downloadFlag).reloadNullLocationProducts(altpartyid);
	}
	public static void main(String[] args) {
		  if(args.length < 2) { System.out.println("Some of the property is missing.."); return; }
		  String partyid=args[0].trim();
		  boolean downloadFile = "true".equalsIgnoreCase(args[1]) ? true : false;
		  
		  System.out.println("Altpartyid : " + partyid);
		  System.out.println("Environment : " + RazorServer.openSession().getConfiguration().getEnvironment().getId());
		  System.out.println("Starting launcher...");
		  try {
		   //new Launcher(partyid,downloadFile).getHandler(downloadFile).readProducts();
		   //new Launcher(partyid,downloadFile).getHandler(downloadFile).readPrices();
		   //new Launcher(partyid,downloadFile).getHandler(downloadFile).readSchedule();
		   new Launcher(partyid,false).getHandler(false).reloadNullLocationProducts(partyid);
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
	}
}
