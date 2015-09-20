package net.cbtltd.rest.odalys.priceavailabilitycache;

import java.util.ArrayList;
import java.util.List;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.script.Handleable;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Partner;

import org.apache.ibatis.session.SqlSession;

public class Launcher implements Handleable {

	String altpartyid =null;
	/**
	 * @param altpartyid
	 *            -- ALT Party id
	 */
	public Launcher(String altpartyid) {
		super();
		this.altpartyid=altpartyid;
	}

	@Override
	public void readProducts() {
		
		getHandler( altpartyid )
				.readProducts();

	}

	public A_Handler getHandler(String altpartyid ) {

		SqlSession sqlSession = RazorServer.openSession();
		Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(
				altpartyid);
		if (partner == null) {
			throw new ServiceException(Error.party_id, altpartyid);
		}
		return new A_Handler(partner);
	}

	@Override
	public void readPrices() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readSchedules() {
		// TODO Auto-generated method stub

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

	public String getAltpartyid() {
		return altpartyid;
	}

	public void setAltpartyid(String altpartyid) {
		this.altpartyid = altpartyid;
	}

}
