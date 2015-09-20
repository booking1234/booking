package net.cbtltd.json.finance;

import java.util.HashMap;

import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.FinanceMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Model;

import org.apache.ibatis.session.SqlSession;

/**
 * Handles the request for bank account details.
 */
public class FinanceHandler implements Handler {

	public String service() {return JSONRequest.FINANCE.name();}

	/**
	 * Gets bank account details, or a message if not available.
	 *
	 * @param the parameter map.
	 * @return the bank account details, or error message.
	 */
	public JSONResponse execute(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 	// the point of sale code.
		String id = parameters.get("id"); 		// the ID of the bank or cash account.

		if (id == null || id.isEmpty() || id.length() > 10) {throw new ServiceException(Error.finance_id, id);}

		SqlSession sqlSession = RazorServer.openSession();
		FinanceWidgetItem result = new FinanceWidgetItem();
		try {
			JSONService.getParty(sqlSession, pos);
			FinanceWidgetItem finance = sqlSession.getMapper(FinanceMapper.class).financewidget(id);
			if (finance == null) {throw new ServiceException(Error.finance_id, id);}
			result.setAccount(finance.getAccount());
			result.setAccountnumber(Model.decrypt(finance.getAccountnumber()));
			result.setBankname(Model.decrypt(finance.getBankname()));
			result.setBranchname(Model.decrypt(finance.getBranchname()));
			result.setBranchnumber(Model.decrypt(finance.getBranchnumber()));
			result.setContactperson(finance.getContactperson());
			result.setCurrency(finance.getCurrency());
			result.setCurrencyname(finance.getCurrencyname());
			result.setIbanswift(finance.getIbanswift());
			result.setName(finance.getName());
			result.setOwner(finance.getOwner());
			result.setPhonenumber(finance.getPhonenumber());
			result.setType(finance.getType());
		}
		catch (ServiceException x) {throw new RuntimeException(x.getMessage());}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		finally {sqlSession.close();}
		return result;
	}
}
