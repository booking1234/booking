package net.cbtltd.json.account;

import java.util.HashMap;

import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.AccountMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Model;

import org.apache.ibatis.session.SqlSession;

/**
 * Handles the request for details of an account.
 */
public class AccountHandler implements Handler {

	public String service() {return JSONRequest.ACCOUNT.name();}

	/**
	 * Gets company details, or a message if not available.
	 *
	 * @param the parameter map.
	 * @return the company details, or error message.
	 */
	public JSONResponse execute(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 	// the point of sale code.
		String id = parameters.get("id"); 		// the ID of the account

		if (id == null || id.isEmpty() || id.length() > 10) {throw new ServiceException(Error.account_id, id);}

		SqlSession sqlSession = RazorServer.openSession();
		AccountWidgetItem result = new AccountWidgetItem();
		try {
			JSONService.getParty(sqlSession, pos);
			result = sqlSession.getMapper(AccountMapper.class).accountwidget(id);
			if (result == null) {throw new ServiceException(Error.account_id, id);}
		}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		finally {sqlSession.close();}
		return result;
	}	

}
