package net.cbtltd.rest.homeaway.escapianet.datafeed;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.script.Handleable;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Partner;

import org.apache.ibatis.session.SqlSession;

 public class Launcher implements Handleable {
	 
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

	public void readProducts() {
		getHandler().readProducts();
		
	}

	public void readPrices() {
		getHandler().readPrices();
		
	}

	public void readSchedules() {
		// TODO Auto-generated method stub
		
	}

	public void readAlerts() {
		// TODO Auto-generated method stub
		
	}

	public void readSpecials() {
		// TODO Auto-generated method stub
		
	}

	public void readLocations() {
		// TODO Auto-generated method stub
		
	}

	public void readDescriptions() {
		// TODO Auto-generated method stub
		
	}

	public void readImages() {
		// TODO Auto-generated method stub
		
	}

}
