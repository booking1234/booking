package net.cbtltd.rest.homeaway.resco.datafeed;

import java.rmi.RemoteException;
import java.util.List;

import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.integration.PartnerService;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.partner.PartnerTable;




import org.apache.ibatis.session.SqlSession;

import com.allen_sauer.gwt.log.client.Log;
import com.instantsoftware.secureweblinkplusapi.WsWeblinkPlusAPISoap;

/**
 * supply the handler to launch the flipkey export
 * @author nibodha
 *
 */
public class Launcher  {

	private String partyId;
	private Partner partner;
	private A_Handler handler;
	public Launcher(String partyId) {
		super();
		this.partyId = partyId;
	}
	/**
	 * @return handler to launch 
	 * 
	 */
	public A_Handler getHandler() {
		if (handler != null) { return handler; }
		SqlSession sqlSession = RazorServer.openSession();
		
		partner= sqlSession.getMapper(PartnerMapper.class).exists(
		partyId);
		if (partner == null) {
			throw new ServiceException(Error.party_id, partyId);
		}
		
		handler = new A_Handler(partner);
		
		if(partner.getApikey()==null||partner.getApikey().isEmpty()){
			List<Partner> partners = sqlSession.getMapper(PartnerMapper.class).list(getPartnerTable(partyId));
			for (Partner partner2 : partners) {
				if(partner2.getApikey()==null||partner2.getApikey().isEmpty()){
					throw new ServiceException(Error.party_value, partyId);
				}
				handler.add(partner2.getApikey(),partner2);
			}
		}
		return handler;
	}
	
	private PartnerTable getPartnerTable(String partyid){
		PartnerTable table=new PartnerTable();
		table.setOrganizationid(partyid);
		table.setOrderby("id");
		table.setStartrow(1);
		table.setNumrows(1000);
		return table;
	}
	
	/**
	 * To initiate the property export
	 * @throws RemoteException 
	 */
	public void loadPropertyData(boolean isInitalLoad) throws RemoteException{
		getHandler().loadPropertyData(isInitalLoad);
	}
	public void inquireReservation() {
		SqlSession sqlSession = RazorServer.openSession();
		getHandler().inquireReservation(sqlSession,null);		
	}
	public WsWeblinkPlusAPISoap getProxy(){
		return handler.proxy;
	}
	public void createReservation(Reservation reservation){
		SqlSession sqlSession = RazorServer.openSession();
		getHandler().createReservation(sqlSession, reservation);
	}
	public ReservationPrice readPrice(Reservation reservation, String productAltId, String currency){
		SqlSession sqlSession = RazorServer.openSession();
		return getHandler().readPrice(sqlSession, reservation, productAltId, currency);
	}
	public static void main(String[] args) {
		if(args.length<1) { System.out.println("altpartyid is missing.."); return; }
		String partyid=args[0].trim();
		boolean downloadFile;
		if(args.length==2)
			downloadFile="true".equalsIgnoreCase(args[1])?true:false;
		else
			downloadFile=PartnerService.changeLogInMin(RazorServer.openSession(),partyid)==0;
		
		System.out.println("Altpartyid "+partyid);
		
		System.out.println("Starting launcher");
		try {
			new Launcher(partyid).getHandler()
			.loadPropertyData(downloadFile);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		
	}
}
