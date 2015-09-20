package net.cbtltd.rest.homeaway.feedparser.receiver.test;

import java.rmi.RemoteException;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Reservation;

import org.apache.ibatis.session.SqlSession;

import com.instantsoftware.secureweblinkplusapi.WsWeblinkPlusAPISoap;

/**
 * supply the handler to launch the flipkey export
 * @author nibodha
 *
 */
public class Launcher  {

	private String altpartyid;
	private Partner partner;
	private A_Handler handler;
	public Launcher(String altpartyid) {
		super();
		this.altpartyid = altpartyid;
	}
	/**
	 * @return handler to launch 
	 * 
	 */
	public A_Handler getHandler() {
		if (handler != null) { return handler; }
		SqlSession sqlSession = RazorServer.openSession();
		partner= sqlSession.getMapper(PartnerMapper.class).exists(
				altpartyid);
		if (partner == null) {
			throw new ServiceException(Error.party_id, altpartyid);
		}
		return handler = new A_Handler(partner); 
	}
	
	/**
	 * To initiate the property export
	 * @throws RemoteException 
	 */
	public void loadPropertyData() throws RemoteException{
		getHandler().readProducts();
	}
	public void loadReservation() throws RemoteException{
		getHandler().readSchedule();
	}
	public void loadPrice() throws RemoteException{
		getHandler().readPrices();
	}
	
	public static void main(String[] args) throws RemoteException {
		new Launcher("201").loadPropertyData();
		new Launcher("201").loadReservation();
		new Launcher("201").loadPrice();
	}
}
