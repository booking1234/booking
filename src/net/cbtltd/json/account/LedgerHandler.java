package net.cbtltd.json.account;

import java.util.ArrayList;
import java.util.HashMap;

import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.AccountMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Party;

import org.apache.ibatis.session.SqlSession;

/**
 * Handles the request for the price list for the specified product and currency.
 */
public class LedgerHandler implements Handler {

	public String service() {return JSONRequest.LEDGER.name();}

	/**
	 * Gets the price list for the specified product and currency, or a message on failure.
	 *
	 * @param the parameter map.
	 * @return the price list, or error message.
	 */
	public JSONResponse execute(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 			// the point of sale code.

		SqlSession sqlSession = RazorServer.openSession();
		AccountWidgetItems result = new AccountWidgetItems();
		try {
			Party organization = JSONService.getParty(sqlSession, pos);
			ArrayList<AccountWidgetItem> items = sqlSession.getMapper(AccountMapper.class).ledgerwidget(organization.getId());
			if (items == null || items.isEmpty()) {throw new ServiceException(Error.account_data);}
			AccountWidgetItem[] array = new AccountWidgetItem[items.size()];
			items.toArray(array);
			result.setItems(array);
		}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		finally {sqlSession.close();}
		return result;
	}

}
