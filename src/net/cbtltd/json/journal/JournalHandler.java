package net.cbtltd.json.journal;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.EventMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.account.AccountAction;

import org.apache.ibatis.session.SqlSession;

/**
 * Handles the request for an array of account journals.
 */
public class JournalHandler implements Handler {

	public String service() {return JSONRequest.JOURNAL.name();}

	/**
	 * Gets an array of account journals.
	 *
	 * @param the parameter map.
	 * @return the array of transactions.
	 */
	public JSONResponse execute(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 			// the point of sale code.
		String accountid = parameters.get("accountid"); // the ID of the account.
		String entityid = parameters.get("entityid"); 	// the ID of the subsidiary account.
		String fromdate = parameters.get("fromdate");	// the date from which transactions are to be shown.
		String todate = parameters.get("todate");		// the date to which transactions are to be shown.
		String currency = parameters.get("currency");	// the currency for which transactions are to be shown.
		String rows = parameters.get("rows");			// the maximum number of transaction rows to be shown.

		if (accountid == null || accountid.isEmpty()) {throw new ServiceException(Error.account_id, accountid);}
		if (entityid == null || entityid.isEmpty()) {throw new ServiceException(Error.entity_id, entityid);}
		if (fromdate == null || fromdate.isEmpty() || fromdate.length() != 10) {throw new ServiceException(Error.date_from, fromdate);}
		if (todate == null || todate.isEmpty() || todate.length() != 10) {throw new ServiceException(Error.date_to, todate);}
		if (currency == null || currency.isEmpty() || currency.length() != 3) {throw new ServiceException(Error.currency_code, currency);}
		if (rows == null || rows.isEmpty()) {rows = "1000";}

		SqlSession sqlSession = RazorServer.openSession();
		JournalWidgetItems result = new JournalWidgetItems();
		try {
			Party organization = JSONService.getParty(sqlSession, pos);
			AccountAction action = new AccountAction();
			action.setOrganizationid(organization.getId());
			action.setAccountid(accountid);
			action.setEntityid(entityid);
			action.setFromdate(JSONService.DF.parse(fromdate));
			action.setTodate(JSONService.DF.parse(todate));
			action.setCurrency(currency);
			action.setNumrows(Integer.valueOf(rows));
			ArrayList<JournalWidgetItem> items = sqlSession.getMapper(EventMapper.class).journalwidget(action);
			if (items == null || items.isEmpty()) {throw new ServiceException(Error.account_data);}
			JournalWidgetItem[] array = new JournalWidgetItem[items.size()];
			items.toArray(array);
			result.setItems(array);
		}
		catch (ParseException x) {throw new ServiceException(Error.date_format);}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		finally {sqlSession.close();}
		return result;
	}	

}
