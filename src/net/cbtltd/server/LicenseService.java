/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import net.cbtltd.server.api.EventMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.JournalMapper;
import net.cbtltd.server.api.LicenseMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.Balance;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Currencyrate;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.Journal;
import net.cbtltd.shared.License;
import net.cbtltd.shared.License.Type;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Serial;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.shared.license.LicenseCreate;
import net.cbtltd.shared.license.LicenseDelete;
import net.cbtltd.shared.license.LicenseRead;
import net.cbtltd.shared.license.LicenseTable;
import net.cbtltd.shared.license.LicenseUpdate;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** The Class LicenseService performs period license procedures. */
public class LicenseService
implements IsService {

	private static final Logger LOG = Logger.getLogger(LicenseService.class.getName());
	private static final HashMap<String, Integer> LICENSED = new HashMap<String, Integer>();
	private static final HashMap<String, Long> ACCESS = new HashMap<String, Long>();
	private static LicenseService service;

	/**
	 * Gets the single instance of LicenseService.
	 *
	 * @return single instance of LicenseService
	 */
	public static synchronized LicenseService getInstance() {
		if (service == null) {service = new LicenseService();}
		return service;
	}

	public static void setAccess(String downstreamid, Long lastaccess) {
		ACCESS.put(downstreamid, lastaccess);
	}

	public static final void checkAccess (
			SqlSession sqlSession, 
			String upstreamid, 
			String downstreamid, 
			String productid, 
			License.Type type,
			Integer licensewait
		) {

//		if (downstreamid == null) {throw new ServiceException(Error.license_bad, downstreamid);}

		
		licensewait = getLicenseWait(sqlSession, upstreamid, downstreamid, productid, type, licensewait);
		if (licensewait == Integer.MAX_VALUE) {throw new ServiceException(Error.license_absent, downstreamid + " " + productid);}
		Long now = System.currentTimeMillis();
		Long lastaccess = ACCESS.get(downstreamid);
		//////////Commneted out for XML test No Production ////////////////////
		//if (lastaccess != null && (lastaccess + licensewait) > now) {throw new ServiceException(Error.license_wait, String.valueOf(licensewait) + "ms");}
        ///////Commneted out for XML test No Production ////////////////////
		LOG.debug("checkAccess " + String.valueOf(lastaccess) + " "  + String.valueOf(licensewait) + " "  + String.valueOf(now));
	}

	/**
	 * Executes the LicenseCreate action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final License execute(SqlSession sqlSession, LicenseCreate action) {
		LOG.debug("LicenseCreate in " + action);
		try {sqlSession.getMapper(LicenseMapper.class).create(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the LicenseRead action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final License execute(SqlSession sqlSession, LicenseRead action) {
		License license = null;
		try {license = sqlSession.getMapper(LicenseMapper.class).read(action.getId());}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return license;
	}

	/**
	 * Executes the LicenseUpdate action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final License execute(SqlSession sqlSession, LicenseUpdate action) {
		LOG.debug("LicenseUpdate " + action);
		try {
			sqlSession.getMapper(LicenseMapper.class).update(action);
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.License, action);
			LICENSED.clear();
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the LicenseDelete action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final License execute(SqlSession sqlSession, LicenseDelete action) {
		try {sqlSession.getMapper(LicenseMapper.class).delete(action.getId());}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return null;
	}

	/**
	 * Executes the LicenseTable action to read an license table for a property.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<License> execute(SqlSession sqlSession, LicenseTable action) {
		LOG.debug("LicenseTable in " + action);
		Table<License> table = new Table<License>();
		try {
			table.setDatasize(sqlSession.getMapper(LicenseMapper.class).count(action));
			table.setValue(sqlSession.getMapper(LicenseMapper.class).list(action));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("LicenseTable out " + table);
		return table;
	}

	/**
	 * Gets the array of licensed product IDs from the supplied list.
	 *
	 * @param sqlSession the current SQL session.
	 * @param upstreamid the ID of the product supplier.
	 * @param downstreamid the ID of the product consumer
	 * @param productids the ID of the product.
	 * @param type the type of license.
	 * @param date the date to be checked.
	 * @return the array of licensed product IDs.
	 */
	public static final ArrayList<String> getLicensed(
			SqlSession sqlSession, 
			String upstreamid, 
			String downstreamid, 
			ArrayList<String> productids, 
			License.Type type,
			Integer licensewait
			) {
		ArrayList<String> licensed = new ArrayList<String>(); 
		for (String productid : productids) {
			if (isLicensed(sqlSession, upstreamid, downstreamid, productid, type, licensewait)) {
				licensed.add(productid);
			}
		}
		return licensed;
	}

	/**
	 * Checks if the product is licensed.
	 *
	 * @param sqlSession the current SQL session.
	 * @param upstreamid the ID of the product supplier.
	 * @param downstreamid the ID of the product consumer
	 * @param productids the ID of the product.
	 * @param type the type of license.
	 * @param date the date to be checked.
	 * @return true, if licensed
	 */
	public static final Integer getLicenseWait(
			SqlSession sqlSession, 
			String upstreamid, 
			String downstreamid, 
			String productid, 
			License.Type type,
			Integer licensewait
		) {
		
//		Integer licensewait = License.DEFAULT_WAIT;
		try {
			if (type == null || License.Type.All == type || License.Type.None == type) {throw new ServiceException(Error.license_type, type.name());}

			if (upstreamid == null && productid != null) {
				Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
				upstreamid = product == null ? null : product.getSupplierid();
			}

//			if (downstreamid == null && upstreamid == null) {return License.DEFAULT_WAIT;} // no product
			if (downstreamid != null && upstreamid != null && downstreamid.equalsIgnoreCase(upstreamid)) {return licensewait;} // agent is manager

			License action = new License(upstreamid, downstreamid, productid, type, licensewait, new Date());
			String key = action.getKey();
			if (LICENSED.containsKey(key)) {return LICENSED.get(key);}
			licensewait = getLicenseWait(sqlSession, action);
			LICENSED.put(key, licensewait);
			LOG.debug("LICENSED " + key + " " + licensewait + " " + LICENSED.size());
		} catch (Throwable x) {x.printStackTrace();}
		return licensewait;
	}

	public static final boolean isLicensed(
		SqlSession sqlSession, 
		String upstreamid, 
		String downstreamid, 
		String productid, 
		License.Type type,
		Integer licensewait
	) {return getLicenseWait(sqlSession, upstreamid, downstreamid, productid, type, licensewait) != Integer.MAX_VALUE;}

	/**
	 * Checks if product is licensed and returns response wait in milliseconds.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the license parameter.
	 * @return response wait in milliseconds.
	 */
	private static final Integer getLicenseWait(SqlSession sqlSession, License action) {

		License exists = sqlSession.getMapper(LicenseMapper.class).exists(action);
		if (exists != null) {return exists.getWait();}

		String type = action.getType();
		action.setType(null);
		exists = sqlSession.getMapper(LicenseMapper.class).exists(action);
		if (exists != null) {return exists.notType(Type.None) ? exists.getWait() : Integer.MAX_VALUE;}

		action.setProductid(null);
		action.setType(type);
		exists = sqlSession.getMapper(LicenseMapper.class).exists(action);
		if (exists != null) {return exists.getWait();}

		action.setType(null);
		exists = sqlSession.getMapper(LicenseMapper.class).exists(action);
		if (exists != null) {return exists.notType(Type.None) ? exists.getWait() : Integer.MAX_VALUE;}

		action.setDownstreamid(null);
		action.setType(type);
		exists = sqlSession.getMapper(LicenseMapper.class).exists(action);
		if (exists != null) {return exists.getWait();}

		action.setType(null);
		exists = sqlSession.getMapper(LicenseMapper.class).exists(action);
		if (exists != null) {return exists.notType(Type.None) ? exists.getWait() : Integer.MAX_VALUE;}

		return action.getWait();
	}

	/**
	 * Service to check if license fees and commission are paid and reinstate if OK.
	 *
	 * @param partyid the ID of the party to be checked.
	 */
	public static void check(SqlSession sqlSession, String partyid) {
		Balance balance = sqlSession.getMapper(JournalMapper.class).licensebalance(new NameId(Party.CBT_LTD_PARTY, partyid));
		if (balance != null && balance.getBalance() >= 0.0) {
			sqlSession.getMapper(ProductMapper.class).restoreparty(partyid);
		}
	}

	/**
	 * Service to calculate license fees and commission at the end of each month.
	 *
	 * @param date of license run.
	 */
	public static void license(Date date) {
		LOG.debug("License run at " + date);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			ArrayList<Balance> openingbalances = sqlSession.getMapper(JournalMapper.class).licensebalances(Party.CBT_LTD_PARTY);

			{
				ArrayList<Journal> journals = sqlSession.getMapper(JournalMapper.class).license(date);
				Event<Journal> event = null;
				String thisOrganizationid = null;

				int i = 0;
				for (Journal journal : journals) {
					if (!journal.hasOrganizationid(thisOrganizationid)) {
						i++;
						endEvent(sqlSession, event, "License Total");
						thisOrganizationid = journal.getOrganizationid();
						event = createEvent(sqlSession, thisOrganizationid, date, "License");
					}
					event.addItem(journal);
				}
				endEvent(sqlSession, event, "License Total");
			}
			{
				ArrayList<Journal> journals = sqlSession.getMapper(JournalMapper.class).commission(date);
				Event<Journal> event = null;
				String thisOrganizationid = null;

				for (Journal journal : journals) {
					if (!journal.hasOrganizationid(thisOrganizationid)) {
						endEvent(sqlSession, event, "Commission Total");
						thisOrganizationid = journal.getOrganizationid();
						event = createEvent(sqlSession, thisOrganizationid, date, "Commission");
					}
					journal.setDebitamount(journal.getDebitamount() * WebService.getRate(sqlSession, journal.getCurrency(), Currency.Code.USD.name(), event.getDate()));
					journal.setCurrency(Currency.Code.USD.name());
					event.addItem(journal);
				}
				endEvent(sqlSession, event, "Commission Total");
			}

			LOG.debug("License openingbalances " + openingbalances);
			suspend(sqlSession, date, openingbalances);
			sqlSession.commit();
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		finally {sqlSession.close();}
	}

	/*
	 * Creates a new license invoice journal event.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param organizationid the ID of the organization being invoiced.
	 * @param date the effective date of the invoice.
	 * @param notes the descriptive text of the invoice.
	 * @return the license invoice journal event.
	 */
	private static Event<Journal> createEvent(SqlSession sqlSession, String organizationid, Date date, String notes) {
		DateFormat df = new SimpleDateFormat("MMMMM yyyy");
		Event<Journal> event = new Event<Journal>();
		event.setOrganizationid(organizationid);
		event.setActorid(Party.NO_ACTOR);
		event.setDate(date);
		event.setDuedate(date);
		event.setProcess(Event.Type.Purchase.name());
		event.setType(Event.ACCOUNTING);
		event.setNotes(notes + " for " + df.format(date));
		Date firstlicensedate = sqlSession.getMapper(JournalMapper.class).firstlicensedate(organizationid);
		if (firstlicensedate == null || date.before(firstlicensedate)) {event.setState(Event.FINAL);}
		else {event.setState(Event.CREATED);}
		return event;
	}

	/*
	 * Creates the line items of a license invoice journal event.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param event the invoice journal event.
	 * @param description the descriptive text of the line item.
	 */
	private static void endEvent(SqlSession sqlSession, Event<Journal> event, String description) {
		LOG.debug("License endEvent " + event);
		if (event == null || event.noItems()) {return;}
		Double balance = 0.0;
		for (Journal item : event.getItems()) {balance += item.getDebitamount();}
		if (Math.abs(balance) < 0.01) {return;}

		Journal journal = new Journal();
		journal.setEntitytype(NameId.Type.Party.name());
		journal.setEntityid(Party.CBT_LTD_PARTY);
		journal.setAccountid(Account.ACCOUNTS_PAYABLE);
		journal.setCreditamount(balance);
		journal.setDebitamount(0.0);
		journal.setDescription(description);
		journal.setEventid(event.getId());
		journal.setOrganizationid(event.getOrganizationid());
		journal.setQuantity(1.0);
		journal.setUnit(Unit.MON);
		journal.setCurrency(Currency.Code.USD.name());
		event.addItem(journal);

		event.setName(SessionService.pop(sqlSession, event.getOrganizationid(), Serial.JOURNAL));
		sqlSession.getMapper(EventMapper.class).create(event);
		for (Journal item : event.getItems()) {
			item.setEventid(event.getId());
			sqlSession.getMapper(JournalMapper.class).create(item);
		}
	}

	/*
	 * Suspends accounts whose balances exceed a threshold.
	 *
	 * @param sqlSession the current SQL session.
	 * @param balances to be evaluated for suspension.
	 */
	private static final void suspend(SqlSession sqlSession, Date date, ArrayList<Balance> balances) {

//		Report report = new Report();
//		report.setActorid(Party.ADMINISTRATOR);
//		report.setCurrency(Currency.Code.USD.name());
//		report.setDate(new Date());
//		report.setDesign(Design.LICENSE_SALE);
//		report.setFormat(Report.PDF);
//		report.setNotes("Automatically generated by License Service");
//		report.setFromdate(date);
//		report.setTodate(date);

		for (Balance balance : balances) {

//			report.setOrganizationid(balance.getPartyid());
//			report = ReportService.execute(sqlSession, report);

			Currencyrate currencyrate = new Currencyrate(balance.getCurrency(), Currency.Code.USD.name(), new Date());
			currencyrate.setRate(WebService.getRate(sqlSession, currencyrate));
			boolean suspended = balance.getBalance() >= 100.0; //Allow margin
			if (suspended) {
				sqlSession.getMapper(ProductMapper.class).suspendparty(balance.getPartyid());
				EmailService.licensesuspended(balance.getEmailaddress(), balance.getBalance(), currencyrate);
			}
			else {EmailService.licenseinvoiced(balance.getEmailaddress(), balance.getBalance(), currencyrate);}
		}
	}

	/** Service to calculate license fees and commission balances and email addresses. */
	public static final void balance(){
		SqlSession sqlSession = RazorServer.openSession();
		try {
//			Report report = new Report();
//			report.setActorid(Party.ADMINISTRATOR);
//			report.setCurrency(Currency.Code.USD.name());
//			report.setDate(new Date());
//			report.setDesign(Design.LICENSE_STATEMENT);
//			report.setFormat(Report.PDF);
//			report.setNotes("Automatically generated by License Service");
//			report.setTodate(new Date());

			ArrayList<Balance> balances = sqlSession.getMapper(JournalMapper.class).licensebalances(Party.CBT_LTD_PARTY);
			LOG.debug("balance " + balances);
			for (Balance balance : balances) {

//				report.setOrganizationid(balance.getPartyid());
//				report = ReportService.execute(sqlSession, report);

				Currencyrate currencyrate = new Currencyrate(balance.getCurrency(), Currency.Code.USD.name(), new Date());
				currencyrate.setRate(WebService.getRate(sqlSession, currencyrate));
				EmailService.licensebalance(balance.getEmailaddress(), balance.getBalance(), currencyrate);
			}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		finally {sqlSession.close();}
	}
}