package net.cbtltd.rest.flipkey.xmlexport;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Partner;

import org.apache.ibatis.session.SqlSession;

/**
 * supply the handler to launch the flipkey export
 * @author nibodha
 *
 */
public class Launcher  {

	private String altpartyid;
	private Partner partner;
	public Launcher(String altpartyid) {
		super();
		this.altpartyid = altpartyid;
	}
	/**
	 * @return handler to launch the flipkey export
	 */
	private A_Handler getHandler() {

		SqlSession sqlSession = RazorServer.openSession();
		partner= sqlSession.getMapper(PartnerMapper.class).exists(
				altpartyid);
		if (partner == null) {
			throw new ServiceException(Error.party_id, altpartyid);
		}
		return new A_Handler(partner);
	}
	
	/**
	 * To initiate the property export
	 */
	public void generatePropertyData(){
		getHandler().generatePropertyXML();
	}
	
	
}
