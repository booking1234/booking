/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 * 
 * @see jQuery Mobile http://jquerymobile.com/
 * @see iPad http://itunes.apple.com/us/app/json-designer/id432736126?mt=8
 * @see iPad http://anders.zakrisson.se/projects/creating-html-5-web-app-ipad/
 */
package net.cbtltd.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.cbtltd.json.account.AccountHandler;
import net.cbtltd.json.account.LedgerHandler;
import net.cbtltd.json.available.AvailableHandler;
import net.cbtltd.json.book.BookAvailableHandler;
import net.cbtltd.json.book.BookHandler;
import net.cbtltd.json.booknew.ReservationPaymentHandler;
import net.cbtltd.json.calendar.CalendarHandler;
import net.cbtltd.json.culture.CultureHandler;
import net.cbtltd.json.email.EmailHandler;
import net.cbtltd.json.finance.FinanceHandler;
import net.cbtltd.json.image.ImageHandler;
import net.cbtltd.json.journal.JournalHandler;
import net.cbtltd.json.nameid.NameIdHandler;
import net.cbtltd.json.party.PartyHandler;
import net.cbtltd.json.pay.PayHandler;
import net.cbtltd.json.pay.ReceiveHandler;
import net.cbtltd.json.price.FeatureHandler;
import net.cbtltd.json.price.PriceHandler;
import net.cbtltd.json.product.ProductHandler;
import net.cbtltd.json.quote.QuoteHandler;
import net.cbtltd.json.rate.RateHandler;
import net.cbtltd.json.reservation.ReservationHandler;
import net.cbtltd.json.review.ReviewHandler;
import net.cbtltd.json.text.TextHandler;
import net.cbtltd.json.value.ValueHandler;
import net.cbtltd.rest.Constants;
import net.cbtltd.server.JournalService;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ChannelPartnerMapper;
import net.cbtltd.server.api.FinanceMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.Country;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.Finance;
import net.cbtltd.shared.Journal;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Time;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.jdom.Element;

import com.google.gson.Gson;

/**
 * The Class JSONRPC2Servlet is to respond to JSON requests with JSON results.
 */
public class JSONService extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Gson GSON = new Gson();
	public static final Logger LOG = Logger.getLogger(JSONService.class.getName());
	public static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd"); //ISO date format
	public static final DateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm aa");
	public static Hashtable<String,Handler> requestHandlers;
	public enum Method {EXISTS, LIST, LISTBYPARENTID, GET, OFFLINE, PAY, SET};

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		initRequestHandlers();
	}

	private static synchronized Hashtable<String,Handler> initRequestHandlers () {
		if (requestHandlers == null) {
			requestHandlers = new Hashtable<String,Handler>();
			register(new AccountHandler());
			register(new AvailableHandler());
			register(new BookHandler());
			register(new CalendarHandler());
			register(new CultureHandler());
			register(new EmailHandler());
			register(new FeatureHandler());
			register(new FinanceHandler());
			register(new ImageHandler());
			register(new JournalHandler());
			register(new LedgerHandler());
			register(new NameIdHandler());
			register(new PartyHandler());
			register(new PayHandler());
			register(new PriceHandler());
			register(new BookAvailableHandler());
			register(new ProductHandler());
			register(new QuoteHandler());
			register(new RateHandler());
			register(new ReceiveHandler());
			register(new ReservationHandler());
			register(new ReviewHandler());
			register(new TextHandler());
			register(new ValueHandler());
			register(new ReservationPaymentHandler());
			LOG.debug(requestHandlers.toString());
		}
		return requestHandlers;
	}

	public static void register(final Handler handler) {
		String service = handler.service().toUpperCase();
		//if (requestHandlers.containsKey(service)) {throw new ServiceException(Error.service_duplicate, requestHandlers.toString());}
		requestHandlers.put(service, handler);
	}

	public synchronized JSONResponse dispatch(HashMap<String, String> parameters) {
		final Date start = new Date();
		final String service = parameters.get("service").toUpperCase();
		Handler handler = requestHandlers.get(service);
		if (handler == null) {throw new ServiceException(Error.service_absent, service);}
		JSONResponse response = handler.execute(parameters); // Execute the request
		MonitorService.monitor(service, start);
		return response;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HashMap<String, String> parameters = new HashMap<String, String>();
		try {
			String jsonCallbackParam = request.getParameter("callback");
			Enumeration<String> names = request.getParameterNames();
			while (names.hasMoreElements())	{
				String key = names.nextElement();
				parameters.put(key, request.getParameter(key));
			}
			String culture = request.getLocale().getLanguage().toLowerCase() + "-" + request.getLocale().getCountry().toUpperCase();
			parameters.put("culture", culture);
			parameters.put("remote_host", request.getRemoteHost());
			LOG.debug("parameters " + parameters);

			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String jsonString = "";
			jsonString = GSON.toJson(dispatch(parameters));
			if (jsonCallbackParam != null) jsonString = jsonCallbackParam + "(" + jsonString + ");"; //for JSONP wrapping
			LOG.debug("result " + jsonString);
			out.write(jsonString);
			out.flush();
			out.close();
		}
		catch (Throwable x) {LOG.error(x.getMessage());}
	}

	/**
	 * Creates the journal event to record a credit card receipt.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to which the receipt is allocated.
	 * @param element the HTTP element containing the transaction details.
	 * @param cardholder the name of the credit card holder.
	 * @param amount the amount received in the currency of the reservation.
	 * @return the array of guest reviews, or error message.
	 */
	public static final Event<Journal> cardReceipt (
			SqlSession sqlSession, 
			Reservation reservation, 
			Element element, 
			String cardholder, 
			Double amount) {

		//-----------------------------------------------
		// Journal event
		//-----------------------------------------------
		Event<Journal> event = new Event<Journal>();
		event.setActivity(NameId.Type.Reservation.name());
		event.setActorid(Party.NO_ACTOR);
		event.setDate(new Date());
		event.setDuedate(event.getDate());
		event.setLocationid(Model.ZERO);
		event.setNotes(JSONService.getNotes(element));
		event.setOrganizationid(reservation.getOrganizationid());
		event.setParentid(reservation.getId());
		event.setProcess(Event.Type.Receipt.name());
		event.setType(Event.ACCOUNTING);
		event.setState(Event.CREATED);

		Double fee = Event.round(amount * Finance.CBT_ZAR_FINANCE_FEE, 2);
		String description = "Reservation " + reservation.getName() + " paid by " + cardholder;

		//-----------------------------------------------
		// Organization - credit accounts receivable from guest 
		//-----------------------------------------------
		Journal journal = new Journal();
		journal.setAccountid(Account.ACCOUNTS_RECEIVABLE);
		journal.setAccountname(Account.ACCOUNTS_RECEIVABLE_NAME);
		journal.setCreditamount(amount);
		journal.setDebitamount(0.0);
		journal.setCurrency(reservation.getCurrency());
		journal.setDescription(description);
		journal.setEntityid(reservation.noCustomerid() ? reservation.getAgentid() : reservation.getCustomerid());
		journal.setEntitytype(NameId.Type.Party.name());
		journal.setLocationid(Model.ZERO);
		journal.setOrganizationid(reservation.getOrganizationid());
		journal.setQuantity(reservation.getDuration(Time.DAY));
		journal.setUnit(Time.DAY.name());
		journal.setUnitprice(amount - fee / reservation.getDuration(Time.DAY));
		event.addItem(journal);

		//-----------------------------------------------
		// Organization debit accounts receivable from licensor
		//-----------------------------------------------
		journal = new Journal();
		journal.setAccountid(Account.ACCOUNTS_RECEIVABLE);
		journal.setAccountname(Account.ACCOUNTS_RECEIVABLE_NAME);
		journal.setCreditamount(fee);
		journal.setDebitamount(amount);
		journal.setCurrency(reservation.getCurrency());
		journal.setDescription(description);
		journal.setEntityid(Party.CBT_LTD_PARTY);
		journal.setEntitytype(NameId.Type.Party.name());
		journal.setLocationid(Model.ZERO);
		journal.setOrganizationid(reservation.getOrganizationid());
		journal.setQuantity(reservation.getDuration(Time.DAY));
		journal.setUnit(Time.DAY.name());
		journal.setUnitprice(fee / reservation.getDuration(Time.DAY));
		event.addItem(journal);

		//-----------------------------------------------
		// Organization debit licensor fees
		//-----------------------------------------------
		journal = new Journal();
		journal.setAccountid(Account.CARD_MERCHANT_FEES);
		journal.setAccountname(Account.CARD_MERCHANT_FEES_NAME);
		journal.setCreditamount(0.0);
		journal.setDebitamount(fee);
		journal.setCurrency(reservation.getCurrency());
		journal.setDescription(description);
		//		journal.setEntityid(Party.CBT_LTD_PARTY);
		//		journal.setEntitytype(NameId.Type.Party.name());
		journal.setLocationid(Model.ZERO);
		journal.setOrganizationid(reservation.getOrganizationid());
		journal.setQuantity(reservation.getDuration(Time.DAY));
		journal.setUnit(Time.DAY.name());
		journal.setUnitprice(fee / reservation.getDuration(Time.DAY));
		event.addItem(journal);

		JournalService.journalUpdate(sqlSession, event);
		return event;
	}

	/**
	 * Gets the customer party from the specified details.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param emailaddress the email address.
	 * @param familyname the family name.
	 * @param firstname the first name.
	 * @param organizationid the ID of the organization.
	 * @param agentid the ID of the agent.
	 * @return the party instance.
	 */
	public static final Party getCustomer (
			SqlSession sqlSession, 
			String emailaddress, 
			String familyname, 
			String firstname, 
			String organizationid, 
			String dayphone,
			Party agent) {
		Party party =  sqlSession.getMapper(PartyMapper.class).readbyemailaddress(emailaddress);
		if (party == null) {
			party = new Party();
			party.setDayphone(dayphone);
			party.setEmailaddress(emailaddress);
			party.setName(familyname, firstname);
			party.setState(Party.CREATED);
			party.setCreatorid(agent == null ? organizationid : agent.getId());
			party.setCountry(agent == null ? Country.US : agent.getCountry());
			party.setCurrency(agent == null ? Currency.Code.USD.name() : agent.getCurrency());
			party.setLanguage(agent == null ? Language.EN : agent.getLanguage());
			sqlSession.getMapper(PartyMapper.class).create(party);
		}
		RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Customer.name(), organizationid, party.getId());
		RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Customer.name(), agent.getId(), party.getId());
		return party;
	}
	
	public static final Party getDetailedCustomer (
			SqlSession sqlSession, 
			String emailaddress, 
			String familyname, 
			String firstname, 
			String organizationid, 
			String dayphone,
			Party agent, 
			String address, 
			String country, 
			String city, 
			String zip,
			String state,
			Date birthDate) {
		Party party =  sqlSession.getMapper(PartyMapper.class).readbyemailaddress(emailaddress);
		if (party == null) {
			party = new Party();
			party.setDayphone(dayphone);
			party.setEmailaddress(emailaddress);
			party.setName(familyname, firstname);
			party.setState(Party.CREATED);
			party.setCreatorid(agent == null ? organizationid : agent.getId());
			party.setCountry(country);
			party.setCurrency(agent == null ? Currency.Code.USD.name() : agent.getCurrency());
			party.setLanguage(agent == null ? Language.EN : agent.getLanguage());
			party.setPostalcode(zip);
			party.setLocalAddress(address);
			party.setCity(city);
			party.setRegion(state);
			party.setBirthdate(birthDate);
			party.setUsertype(Constants.RENTER_USER_TYPE);
			sqlSession.getMapper(PartyMapper.class).create(party);
		}
		RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Customer.name(), organizationid, party.getId());
		RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Customer.name(), agent.getId(), party.getId());
		return party;
	}

	/**
	 * Gets the credit card account from the card details.
	 *
	 * @param sqlSession the current SQL session.
	 * @param ownerid the ID of the card owner.
	 * @param cardholder the name of the credit card holder.
	 * @param cardnumber the credit card number.
	 * @param cardmonth the credit card expiry month.
	 * @param cardyear the credit card expiry year.
	 * @param cardcode the credit card CVV digits.
	 * @return the credit card account instance.
	 */
	public static final Finance getFinance(
			SqlSession sqlSession, 
			String ownerid,	
			String cardholder,
			String cardnumber,
			String cardmonth,
			String cardyear,
			String cardcode) {
		Finance finance = new Finance();
		finance.setOwnerid(ownerid);
		finance.setName(cardholder);
		finance.setAccountnumber(Model.encrypt(cardnumber));
		finance.setMonth(Model.encrypt(cardmonth));
		finance.setYear(Model.encrypt(cardyear));
		finance.setCode(Model.encrypt(cardcode));
		finance.setType(Finance.Type.Card.name());
		finance.setCurrency(Currency.Code.USD.name());
		sqlSession.getMapper(FinanceMapper.class).create(finance);
		return finance;
	}

	/** 
	 * Gets the notes from the specified Paygate response element.
	 * 
	 * @param element the specified Paygate response element.
	 * @return the notes.
	 */
	public static String getNotes(Element element) {
		if (element == null) {return "Test Transaction";}
		StringBuilder sb = new StringBuilder();
		sb.append( "Status: " + element.getAttributeValue( "stat" ) );
		sb.append( " Status Description: " + element.getAttributeValue( "sdesc" ) );
		sb.append( " Result: " + element.getAttributeValue( "res" ) );
		sb.append( " Result Description: " + element.getAttributeValue( "rdesc" ) );
		sb.append( " Transaction ID: " + element.getAttributeValue( "tid" ) );
		sb.append( " Reference: " + element.getAttributeValue( "cref" ) );
		sb.append( " Auth Code: " + element.getAttributeValue( "auth" ) );
		sb.append( " Card Type: " + element.getAttributeValue( "ctype" ) );
		return sb.toString();
	}

	/**
	 * Gets the party having the specified point of sale code.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param pos the specified point of sale code.
	 * @throws ServiceException the exception raised by run time errors.
	 */
	public static final Party getParty(SqlSession sqlSession, String pos) throws ServiceException {
		try {
			if (pos == null || pos.isEmpty()) {throw new ServiceException(Error.pos_absent, pos);}
			String partyid = Model.decrypt(pos);
			Party party = sqlSession.getMapper(PartyMapper.class).read(partyid);
			if (party == null) {throw new ServiceException(Error.party_id, pos);}
//			if (!RelationService.exists(sqlSession, Relation.ORG_PARTY_ + Party.Type.Organization.name(), partyid)
//					&& !RelationService.exists(sqlSession, Relation.PARTY_TYPE, partyid, Party.Type.Agent.name())
//					) {throw new ServiceException(Error.party_type, pos);}
			return party;
		}
		catch (Throwable x) {throw new ServiceException(Error.pos_invalid, pos);}
	}
	
	public static final Party getPartyWithPMCheck(SqlSession sqlSession, String pos, String supplierId) throws ServiceException {
		if (pos == null || pos.isEmpty()) {throw new ServiceException(Error.pos_absent, pos);}
		String partyid = Model.decrypt(pos);
		Party party = sqlSession.getMapper(PartyMapper.class).read(partyid);
		if (party == null) {throw new ServiceException(Error.party_id, pos);}
		List<String> listManagerIds = sqlSession.getMapper(ChannelPartnerMapper.class).readRelatedManagersByPartyId(Integer.valueOf(partyid));
		if (!listManagerIds.contains(supplierId)) { throw new ServiceException(Error.agent_to_pm); }
		return party;
	}

}

