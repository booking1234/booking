package net.cbtltd.json.email;

import java.util.HashMap;

import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Party;

import org.apache.ibatis.session.SqlSession;

/**
 * Handles the request for a party's name from its email address.
 */
public class EmailHandler implements Handler {

	public String service() {return JSONRequest.EMAIL.name();}

	/**
	 * Gets a party's name using its email address, or a message if it does not exist.
	 *
	 * @param the parameter map.
	 * @return the party's name, or error message.
	 */
	public JSONResponse execute(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 					// the point of sale code.
		String emailaddress = parameters.get("emailaddress"); 	// the email address of the party.

		if (emailaddress == null || emailaddress.isEmpty() || emailaddress.length() > 100) {throw new ServiceException(Error.party_emailaddress, emailaddress);}

		SqlSession sqlSession = RazorServer.openSession();
		EmailWidgetItem result = new EmailWidgetItem();
		try {
			JSONService.getParty(sqlSession, pos);
			Party party = sqlSession.getMapper(PartyMapper.class).readbyemailaddress(emailaddress);
			if (party == null) {throw new ServiceException(Error.party_emailaddress, emailaddress);}
			result.setFamilyname(party.getFamilyName());
			result.setFirstname(party.getFirstName());
		}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		finally {sqlSession.close();}
		return result;
	}	

}
