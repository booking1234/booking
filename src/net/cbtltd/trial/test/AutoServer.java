/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.trial.test;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.server.JournalService;
import net.cbtltd.server.PartyService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.SessionService;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.Area;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.Journal;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Session;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.journal.JournalUpdate;
import net.cbtltd.shared.party.PartyCreate;
import net.cbtltd.shared.reservation.AvailableItem;
import net.cbtltd.shared.reservation.LookBook;
import net.cbtltd.shared.reservation.ReservationCreate;
import net.cbtltd.shared.reservation.ReservationEntities;
import net.cbtltd.shared.reservation.ReservationUpdate;
import net.cbtltd.shared.session.SessionAutoLogin;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/**
 * The Class AutoServer is to create reservations via the server as an alternative to via AutoRazor.
 */
public class AutoServer {
	
	private static final Logger LOG = Logger.getLogger(AutoServer.class.getName());
	private static final int searchresults = 3;
	private static final int leadtime = 100;
	private static final int lengthofstay = 7;
	private static final int invoiceratio = 3;
	private static final int receiptratio = 2;

	private static Session session;
	private static Party customer;
	private static Product product;
	private static AvailableItem item;
	private static Reservation reservation;
	private static ReservationEntities reservationentities;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SqlSession sqlSession = RazorServer.openSession();
		try {
			String pos = Model.encrypt(Data.getRandomString(Data.AGENT_IDS));
			if (pos == null || pos.isEmpty()) {throw new RuntimeException("Invalid POS code");}
			session = SessionService.getInstance().execute(sqlSession, new SessionAutoLogin(pos));
			for (int i = 0;  i < 10000; i++) {lookBook(sqlSession);}
		}
		catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
			LOG.error("Load Error " + x.getStackTrace());
		}
		finally {sqlSession.close();}
	}

	private static final void lookBook(SqlSession sqlSession){
		int reservationcount = 0;
//		Table<AvailableItem> result = ReservationService.getInstance().execute(sqlSession, getValue(new LookBook()));
		ArrayList<AvailableItem> result = getAvailableItems(sqlSession, getValue(new LookBook()));
		if (result == null || result == null || result.isEmpty()) {LOG.debug("No Result " + getValue(new LookBook()));} //throw new RuntimeException("No search results for " + getValue(new LookBook()));}
		else {
			LOG.debug("Results " + result.size());
			int index = Data.getRandomIndex(result.size());
			item = result.get(index);
			customer = PartyService.getInstance().execute(sqlSession, getCustomer());
			product = sqlSession.getMapper(ProductMapper.class).read(item.getProductid());
			reservation = ReservationService.getInstance().execute(sqlSession, (ReservationCreate) getValue(new ReservationCreate()));
	//		reservation = ReservationService.getInstance().execute(sqlSession, (ReservationUpdate)reservation);
			reservationcount++;
			reservationentities = ReservationService.getInstance().execute(sqlSession, new ReservationEntities(reservation));
			if (reservationcount%invoiceratio == 0) {JournalService.getInstance().execute(sqlSession, getReservationSale());}
			if (reservationcount%receiptratio == 0) {JournalService.getInstance().execute(sqlSession, getPayment(false));}
			sqlSession.commit();
		}
	};

	private static ArrayList<AvailableItem> getAvailableItems(SqlSession sqlSession, LookBook action) {
		action.setProductids(sqlSession.getMapper(ReservationMapper.class).productsatposition(action));
		if (action.hasAttributes()) {action.setProductids(sqlSession.getMapper(ReservationMapper.class).productswithattributes(action));} // properties meeting criteria
		if (action.hasDuration()) {action.setCollisions(sqlSession.getMapper(ReservationMapper.class).productcollisions(action));} //remove properties that are not available between the dates
		//LOG.debug("\n\nReservationService LookBook action " + action);
		if (action.noProductids()) {return null;}
		return sqlSession.getMapper(ReservationMapper.class).lookbookitems(action);
	}
	
	private static PartyCreate getCustomer() {
		String familyname = Data.getRandomString(Data.FAMILY_NAMES);
		String firstname = Data.getRandomString(Data.MALE_NAMES);
		String extraname = Data.getRandomString(Data.FEMALE_NAMES);
		Location location = Data.getRandomLocation(Data.LOCATIONS);
		PartyCreate customer = new PartyCreate();
		customer.setName(familyname + "," + firstname);
		customer.setEmailaddress(firstname.trim().toLowerCase().replaceAll(" ", "") + "@" + familyname.trim().toLowerCase().replaceAll(" ", "") + ".com");
		customer.setExtraname(extraname);
		customer.setCountry(location.getCountry());
		customer.setCurrency(item.getCurrency());
		customer.setLanguage(Language.EN);
		customer.setLatitude(location.getLatitude());
		customer.setLongitude(location.getLongitude());
		customer.setLocationid(location.getId());
		customer.setNotes("Guest is from " + location.getName());
		customer.setOrganizationid(item.getSupplierid());
		customer.setPostalcode(location.getCode());
		customer.setState(Party.CREATED);
		customer.setType(Party.Type.Customer.name());
		return customer;
	}

	/**
	 * Gets the value.
	 *
	 * @param lookbook the lookbook
	 * @return the value
	 */
	private static LookBook getValue(LookBook lookbook) {
		Area area = Data.getRandomArea(Data.SEARCH_AREAS);
//		lookbook.setOrganizationid(session.getOrganizationid());
		lookbook.setAgentid(session.getOrganizationid());
		lookbook.setLatitude(area.getLatitude());
		lookbook.setLongitude(area.getLongitude());
		lookbook.setDistance(area.getDistance());
		lookbook.setOffline(false);
		Date fromdate = Data.getRandomDate(leadtime);
		lookbook.setFromdate(fromdate);
		Date todate = Time.addDuration(fromdate, Data.getRandomInteger(1, lengthofstay), Time.DAY);
		lookbook.setState(Reservation.State.Provisional.name());
		lookbook.setTodate(todate);
		lookbook.setSpecial(false);
		lookbook.setSpecialmin(1);
		lookbook.setSpecialmax(10);
		lookbook.setCount(Data.getRandomInteger(1, 14));
		lookbook.setCountunit(true);
		lookbook.setExactcount(false);
		Integer pricemin = Data.getRandomInteger(0, 5000);
		lookbook.setPricemin(pricemin);
		Integer pricemax = pricemin + Data.getRandomInteger(100, 5000);
		lookbook.setPricemax(pricemax);
		lookbook.setPriceunit(true);
		lookbook.setCurrency(Currency.Code.USD.name());
		lookbook.setRating(Data.getRandomIndex(9));
		lookbook.setDiscount(Data.getRandomInteger(0, 50));
		lookbook.setAttributes(null);
		lookbook.setNumrows(searchresults);
		lookbook.setOrderby(AvailableItem.RANK);
//		session.setIntegerValue(Party.Value.LookBookCount.name(), session.getIntegerValue(Party.Value.LookBookCount.name()) + 1);
//		LOG.debug("getValue " + lookbook);
		return lookbook;
	}
	
	/**
	 * Gets the value.
	 *
	 * @param action the action
	 * @return the value
	 */
	private static Reservation getValue(Reservation action) {
		action.setOldstate(Reservation.State.Initial.name());
		action.setId(reservation == null ? null : reservation.getId());
		action.setUnit(Unit.DAY);
		action.setCustomerid(customer.getId());
		action.setOrganizationid(session.getOrganizationid());
		action.setAgentid(session.getOrganizationid());
		action.setActorid(session.getActorid());
		action.setSupplierid(product.getSupplierid());
		action.setDate(new Date());
		action.setDuedate(Time.addDuration(action.getDate(), 3.0, Time.DAY));
		action.setFromdate(Time.getDateServer(item.getFromdate()));
		action.setTodate(Time.getDateServer(item.getTodate()));
		if (item.getFromdate().before(new Date())) {action.setState(Reservation.State.Arrived.name());}
		else if (item.getFromdate().before(Time.addDuration(new Date(), 30, Time.DAY))) {action.setState(Reservation.State.FullyPaid.name());}
		else if (item.getFromdate().before(Time.addDuration(new Date(), 60, Time.DAY))) {action.setState(Reservation.State.Confirmed.name());}
		else {action.setState(Reservation.State.Provisional.name());}
//		action.setState(Reservation.State.Provisional.name());
		action.setArrivaltime("14:00");
		action.setDeparturetime("10:00");
		action.setProcess(NameId.Type.Reservation.name());
		action.setProductid(item.getProductid());
		action.setNotes(customer.getNotes());
		action.setUnit(Unit.DAY); //unitField.getValue());
		action.setPrice(item.getPrice());
		action.setQuote(item.getQuote());
		action.setCost(item.getCost());
		action.setCurrency(item.getCurrency());
		double leadtime = Time.getDuration(action.getDate(), action.getFromdate(), Time.DAY);
		Double deposit = (leadtime > 30.0) ? 50. : 100.;
		action.setDeposit(deposit);
		//reservation.setTasks(serviceGrid.getValue());
//		LOG.debug("getValue " + action);
		return action;
	}

	/*
	 * Reservation Sale - post a sale for a reservation which includes entries for manager commission, 
	 * owner revenue and sales taxes where applicable.
	 */
	private static final JournalUpdate getReservationSale() {
		JournalUpdate event = new JournalUpdate();
		event.setState(Event.CREATED);
		event.setProcess(Event.Type.ReservationSale.name());
		event.setOrganizationid(reservation.getOrganizationid());
		event.setActorid(session.getActorid());
		event.setActivity(NameId.Type.Reservation.name());
		event.setParentid(reservation.getId());
		event.setDate(new Date());
		event.setNotes("Test Invoice");
		event.setType(Event.ACCOUNTING);
		
		Party customer = reservationentities.getCustomer();
		Party owner = reservationentities.getOwner();
		Product product = reservationentities.getProduct();
		Party manager = reservationentities.getManager();
		Double amount = reservation.getQuote();
		String currency = reservation.getCurrency();
		String description = "Invoice " + customer.getName() + " for " + product.getName() + " from " + DateFormat.getDateInstance(DateFormat.SHORT).format(reservation.getFromdate()) + " to " + DateFormat.getDateInstance(DateFormat.SHORT).format(reservation.getTodate());

		Double taxrate = Data.getRandom(0.0, 20.0);
		boolean ownermanager = owner.hasId(manager.getId());
		Double supplieramount = Event.round(ownermanager ? amount : amount * product.getCommissionValue() / 100.0);
		Double suppliertax = Event.round(manager.hasTaxnumber() ? taxrate * supplieramount / (100.0 + taxrate): 0.0);
		Double suppliersale = supplieramount - suppliertax;
		Double owneramount = ownermanager ? 0.0 : amount - supplieramount;
		Double ownertax = Event.round(owner.hasTaxnumber() ? taxrate * owneramount / (100.0 + taxrate): 0.0);
		Double ownersale = owneramount - ownertax;
		Double quantity = reservation.getDuration(Time.DAY);

		// CR Owner Sales
		post(	event,
				owner.getId(),
				owner.getName(),
				Account.SALES_REVENUE,
				Account.SALES_REVENUE_NAME,
				NameId.Type.Product.name(),
				product.getId(),
				product.getName(),
				currency,
				0.0,
				ownersale,
				quantity,
				Unit.DAY,
				description
		);

		// CR Owner VAT
		post(	event,
				owner.getId(),
				owner.getName(),
				Account.VAT_OUTPUT,
				Account.VAT_OUTPUT_NAME,
				null,
				null,
				null,
				currency,
				0.0,
				ownertax,
				description
		);

		// DR Owner Accounts Receivable 
		post(	event,
				owner.getId(),
				owner.getName(),
				Account.ACCOUNTS_RECEIVABLE,
				Account.ACCOUNTS_RECEIVABLE_NAME,
				NameId.Type.Party.name(),
				manager.getId(),
				manager.getName(),
				currency,
				ownersale + ownertax,
				0.0,
				description
		);

		// CR Manager Accounts Payable
		post(	event,
				manager.getId(),
				manager.getName(),
				Account.ACCOUNTS_PAYABLE,
				Account.ACCOUNTS_PAYABLE_NAME,
				NameId.Type.Party.name(),
				owner.getId(),
				owner.getName(),
				currency,
				0.0,
				ownersale + ownertax,
				description
		);

		// CR Manager Sales
		post(	event,
				manager.getId(),
				manager.getName(),
				Account.SALES_REVENUE,
				Account.SALES_REVENUE_NAME,
				NameId.Type.Product.name(),
				product.getId(),
				product.getName(),
				currency,
				0.0,
				suppliersale,
				quantity, 
				Unit.DAY,
				description
		);

		// CR Manager VAT
		post(	event,
				manager.getId(),
				manager.getName(),
				Account.VAT_OUTPUT,
				Account.VAT_OUTPUT_NAME,
				null,
				null,
				null,
				currency,
				0.0,
				suppliertax,
				description
		);

		// DR Manager Accounts Receivable from Customer
		post(	event,
				manager.getId(),
				manager.getName(),
				Account.ACCOUNTS_RECEIVABLE,
				Account.ACCOUNTS_RECEIVABLE_NAME,
				NameId.Type.Party.name(),
				customer.getId(), //Agent if exists, else customer: STO for agent, Quote for Customer
				customer.getName(),
				currency,
				ownersale + ownertax + suppliersale + suppliertax,
				0.0,
				description
		);
		return event;
	}
	
	/*
	 * Post Payment - posts a payment or receipt event to the ledger.
	 * 
	 * @param isPayment is true if a payment, false if a receipt
	 * @return the payment
	 */
	private static JournalUpdate getPayment (boolean isPayment) {
		
		JournalUpdate event = new JournalUpdate();
		event.setState(Event.CREATED);
		event.setProcess(Event.Type.ReservationSale.name());
		event.setOrganizationid(session.getOrganizationid());
		event.setActorid(session.getActorid());
		event.setActivity(NameId.Type.Reservation.name());
		event.setParentid(reservation.getId());
		event.setDate(new Date());
		event.setNotes("Test Invoice");
		event.setType(Event.ACCOUNTING);

		Party customer = reservationentities.getCustomer();
		Party owner = reservationentities.getOwner();
		Product product = reservationentities.getProduct();
		Party manager = reservationentities.getManager();
		Double amount = reservation.getQuote();
		String currency = reservation.getCurrency();
		String description = "Received from " + customer.getName() + " for " + product.getName() + " from " + DateFormat.getDateInstance(DateFormat.SHORT).format(reservation.getFromdate()) + " to " + DateFormat.getDateInstance(DateFormat.SHORT).format(reservation.getTodate());

		post(
				event,
				session.getOrganizationid(),
				session.getOrganizationname(),
				isPayment ? Account.ACCOUNTS_PAYABLE : Account.ACCOUNTS_RECEIVABLE,
				isPayment ? Account.ACCOUNTS_PAYABLE_NAME : Account.ACCOUNTS_RECEIVABLE_NAME,
				NameId.Type.Party.name(),
				customer.getId(),
				customer.getName(),
				currency,
				isPayment ? amount : 0.0,
				isPayment ? 0.0 : amount,
				description
		);
		post(
				event,
				session.getOrganizationid(),
				session.getOrganizationname(),
				Account.FINANCE, 
				Account.FINANCE_NAME, 
				NameId.Type.Finance.name(),
				"1",
				"Dummy Bank Account",
				currency,
				isPayment ? 0.0 : amount,
				isPayment ? amount : 0.0,
				description
		);

		return event;
	}

	/*
	 * Post - posts a journal which has no physical attributes to the ledger.
	 * @param organizationid is the ID of the organization of the transaction.
	 * @param organizationname is the name of the organization of the transaction.
	 * @param accountid is the ID of the ledger account of the transaction.
	 * @param accountname is the name of the ledger account of the transaction.
	 * @param entitytype is the sub-ledger (entity type) of the transaction.
	 * @param entityid is the ID of the sub-account(entity id) of the transaction.
	 * @param entityname is the name of the sub-account(entity name) of the transaction.
	 * @param currency is the currency of the amount(s) in the transaction.
	 * @param debitamount is the debit amount in the specified currency.
	 * @param creditamount is the credit amount in the specified currency.
	 * @param description is the description of the transaction.
	 */
	private static void post(
			Event<Journal> event,
			String organizationid,
			String organizationname,
			String accountid,
			String accountname,
			String entitytype,
			String entityid,
			String entityname,
			String currency,
			Double debitamount,
			Double creditamount,
			String description) {
		post(event, organizationid, organizationname, accountid,accountname, entitytype, entityid, entityname, currency, debitamount, creditamount, 1.0, Unit.EA, description);
	}

	/*
	 * Post - posts a journal which has physical attributes to the ledger.
	 * @param organizationid is the ID of the organization of the transaction.
	 * @param organizationname is the name of the organization of the transaction.
	 * @param accountid is the ID of the ledger account of the transaction.
	 * @param accountname is the name of the ledger account of the transaction.
	 * @param entitytype is the sub-ledger (entity type) of the transaction.
	 * @param entityid is the ID of the sub-account(entity id) of the transaction.
	 * @param entityname is the name of the sub-account(entity name) of the transaction.
	 * @param currency is the currency of the amount(s) in the transaction.
	 * @param debitamount is the debit amount in the specified currency.
	 * @param creditamount is the credit amount in the specified currency.
	 * @param quantity is the optional quantity of the transaction.
	 * @param unit is the optional unit of measure of the quantity.
	 * @param description is the description of the transaction.
	 */
	private static void post(
			Event<Journal> event,
			String organizationid,
			String organizationname,
			String accountid,
			String accountname,
			String entitytype,
			String entityid,
			String entityname,
			String currency,
			Double debitamount,
			Double creditamount,
			Double quantity,
			String unit,
			String description) {
		if (Math.abs(debitamount) < 0.01 && Math.abs(creditamount) < 0.01) {return;}
		getJournal(
				event,
				new Journal(
						event.getId(),
						Model.ZERO,
						organizationid,
						organizationname,
						Model.ZERO,
						accountid,
						accountname,
						entitytype,
						entityid,
						entityname,
						quantity,
						unit,
						0.0,
						currency,
						debitamount,
						creditamount,
						description
				)
		);
	}

	/*
	 * Checks if journal exists and increment if it does.
	 * 
	 * @param arg is the current event
	 * @param arg is the journal to be created or added
	 */
	private static void getJournal(Event<Journal> event, Journal item) {
		if (event == null) {LOG.error("getJournal event null");}
		if (event.hasItems()) {
			for (Journal journal : event.getItems()) {
				if (item.isEqualto(journal)) {
					journal.addQuantity(item.getQuantity());
					journal.addDebitamount(item.getDebitamount());
					journal.addCreditamount(item.getCreditamount());
					return;
				}
			}
		}
		event.addItem(item);
	}
}
