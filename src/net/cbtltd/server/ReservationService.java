/**
 * @author	abookingnet
 * @see		License at http://razorpms.com/razor/License.html
 * @version	2.00
 */
package net.cbtltd.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.cbtltd.json.SharedService;
import net.cbtltd.rest.response.QuoteDetail;
import net.cbtltd.server.api.AdjustmentMapper;
import net.cbtltd.server.api.AlertMapper;
import net.cbtltd.server.api.ContactMapper;
import net.cbtltd.server.api.ContractMapper;
import net.cbtltd.server.api.CountryMapper;
import net.cbtltd.server.api.EventMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PaymentTransactionMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.ReservationExtMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.api.SpecialMapper;
import net.cbtltd.server.api.TaxMapper;
import net.cbtltd.server.api.WorkflowMapper;
import net.cbtltd.server.api.YieldMapper;
import net.cbtltd.server.config.RazorHostsConfig;
import net.cbtltd.server.project.PartyIds;
import net.cbtltd.server.util.CommissionCalculationUtil;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.server.util.PriceUtil;
import net.cbtltd.server.util.ReservationUtil;
import net.cbtltd.server.util.price.FeeCalculationHelper;
import net.cbtltd.server.util.price.PriceComplexValue;
import net.cbtltd.server.util.price.TaxesCalculationHelper;
import net.cbtltd.shared.Adjustment;
import net.cbtltd.shared.Alert;
import net.cbtltd.shared.Contract;
import net.cbtltd.shared.Country;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.DoubleResponse;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.PaymentTransaction;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerCancellationRule;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.ReservationExt;
import net.cbtltd.shared.Serial;
import net.cbtltd.shared.Special;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.Tax;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.Workflow;
import net.cbtltd.shared.Yield;
import net.cbtltd.shared.api.HasAlert;
import net.cbtltd.shared.api.HasCollision;
import net.cbtltd.shared.api.HasItem;
import net.cbtltd.shared.api.HasPrice;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.journal.EventJournal;
import net.cbtltd.shared.party.Organization;
import net.cbtltd.shared.reservation.Available;
import net.cbtltd.shared.reservation.AvailableItem;
import net.cbtltd.shared.reservation.AvailableWidget;
import net.cbtltd.shared.reservation.Brochure;
import net.cbtltd.shared.reservation.BrochurePrice;
import net.cbtltd.shared.reservation.BrochureProduct;
import net.cbtltd.shared.reservation.BrochureRead;
import net.cbtltd.shared.reservation.BrochureUpdate;
import net.cbtltd.shared.reservation.LookBook;
import net.cbtltd.shared.reservation.LookBookSpecial;
import net.cbtltd.shared.reservation.OfflineAccept;
import net.cbtltd.shared.reservation.OfflineRead;
import net.cbtltd.shared.reservation.OfflineReject;
import net.cbtltd.shared.reservation.PriceResponse;
import net.cbtltd.shared.reservation.ReservationCreate;
import net.cbtltd.shared.reservation.ReservationDelete;
import net.cbtltd.shared.reservation.ReservationEntities;
import net.cbtltd.shared.reservation.ReservationEventJournalBalance;
import net.cbtltd.shared.reservation.ReservationEventJournalTable;
import net.cbtltd.shared.reservation.ReservationHistory;
import net.cbtltd.shared.reservation.ReservationPrice;
import net.cbtltd.shared.reservation.ReservationPriceAdjust;
import net.cbtltd.shared.reservation.ReservationRead;
import net.cbtltd.shared.reservation.ReservationTable;
import net.cbtltd.shared.reservation.ReservationUndo;
import net.cbtltd.shared.reservation.ReservationUpdate;
import net.cbtltd.shared.reservation.ReservationWidget;
import net.cbtltd.shared.reservation.WidgetQuote;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.mybookingpal.utils.BPThreadLocal;

/** The Class ReservationService responds to reservation requests. */

public class ReservationService implements IsService {

	public static final Logger LOG = Logger.getLogger(ReservationService.class.getName());
	private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	private static ReservationService service;
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("yyy-MM-dd");

	/**
	 * Gets the single instance of ReservationService to manage Reservation instances.
	 * @see net.cbtltd.shared.Reservation
	 *
	 * @return single instance of ReservationService
	 */
	public static synchronized ReservationService getInstance() {
		if (service == null){service = new ReservationService();}
		return service;
	}

	/**
	 * Executes the OfflineRead action to read an off line Reservation instance.
	 * Reads the reservation for its name, state, arrival and departure dates, prices and currency.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Reservation execute(SqlSession sqlSession, OfflineRead action) {
		Date timestamp = new Date();
		try {return sqlSession.getMapper(ReservationMapper.class).offlineread(action.getId());}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		MonitorService.monitor("OfflineRead", timestamp);
		return action;
	}

	/**
	 * Executes the OfflineAccept action to accept an off line reservation.
	 * Checks if the reservation has already been accepted or rejected.
	 * Cancels the reservation and email the agent or guest if it collides with another.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Reservation execute(SqlSession sqlSession, OfflineAccept action) {
		return offline(sqlSession, action, true);
	}
	
	/**
	 * Executes the OfflineReject action to reject an off line reservation.
	 * Email the agent or guest accordingly.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Reservation execute(SqlSession sqlSession, OfflineReject action) {
		return offline(sqlSession, action, false);
	}

	public static final Reservation offline(SqlSession sqlSession, Reservation action, boolean accept) {
		Date timestamp = new Date();
		try {
			//LOG.debug("OfflineAccept " + action);
			Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(action.getId());
			if (reservation.hasState(Reservation.State.Initial.name())) {
				reservation.setState(accept ? Reservation.State.Confirmed.name() : Reservation.State.Final.name());
//				reservationUpdate(sqlSession, reservation);
				if (reservation.hasCollisions()) {reservation.setState(Reservation.State.Final.name());}
				sqlSession.getMapper(ReservationMapper.class).update(reservation);
				if (reservation.hasState(Reservation.State.Final.name())) {EmailService.rejectedReservation(sqlSession, reservation);}
				else {EmailService.acceptedReservation(sqlSession, reservation);}
				MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Reservation, reservation);
			}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		MonitorService.monitor("Offline", timestamp);
		return action;
	}

	/**
	 * Executes the ReservationWidget action to create a Reservation instance if the manager is not known.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Reservation execute(SqlSession sqlSession, ReservationWidget action) {
		Date timestamp = new Date();
		try {
			Product product = sqlSession.getMapper(ProductMapper.class).read(action.getProductid());
			action.setOrganizationid(product.getSupplierid());
			action.setName(SessionService.pop(sqlSession, action.getOrganizationid(), Serial.RESERVATION));
			sqlSession.getMapper(ReservationMapper.class).create(action);
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Reservation, action);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		MonitorService.monitor("ReservationWidget", timestamp);
		return action;
	}

	/**
	 * Executes the ReservationCreate action to create a Reservation instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Reservation execute(SqlSession sqlSession, ReservationCreate action) {
		Date timestamp = new Date();
		//LOG.debug("ReservationCreate in " + action);
		try {
			action.setName(SessionService.pop(sqlSession, action.getOrganizationid(), Serial.RESERVATION));
			sqlSession.getMapper(ReservationMapper.class).create(action);
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Reservation, action);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		//LOG.debug("ReservationCreate out " + action);
		MonitorService.monitor("ReservationCreate", timestamp);
		return action;
	}

	/**
	 * Executes the ReservationRead action to read a Reservation instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Reservation execute(SqlSession sqlSession, ReservationRead action) {
		Date timestamp = new Date();
		//LOG.debug("ReservationRead in " + action);
		Reservation reservation = null;
		try {
			reservation = sqlSession.getMapper(ReservationMapper.class).read(action.getId());
			reservation.setQuotedetail(sqlSession.getMapper(PriceMapper.class).quotedetail(action.getId()));
			reservation.setTasks(TaskService.readbyparentid(sqlSession, action.getId()));
			reservation.setOldstate(reservation.getState());
			getWorkflow(sqlSession, reservation);
			//LOG.debug("ReservationRead out " + reservation);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		MonitorService.monitor("ReservationRead", timestamp);
		return reservation;
	}

	/* 
	 * Sets the previous and next states of the specified reservation based on its work flow rules and current state.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param reservation the specified reservation.
	 * @return the workflow
	 */
	private static final void getWorkflow(SqlSession sqlSession, Reservation reservation) {
		ArrayList<String> donestates = sqlSession.getMapper(ReservationMapper.class).donestates(reservation.getId());
		ArrayList<Workflow> workflows = sqlSession.getMapper(WorkflowMapper.class).read(reservation.getOrganizationid());
		if (workflows == null || workflows.isEmpty()) {return;}
		String oldstate = Reservation.State.Provisional.name();
		String duestate = Reservation.State.Departed.name();
		Date now = new Date();
		Date olddate = new Date(0);
		Date duedate = Time.addDuration(now, 1500, Time.DAY); //Archive in 5 years time

		for (Workflow workflow : workflows) {
			if (workflow.notEnabled() || reservation.isAfterState(workflow.getState())) {continue;}
//				if (workflow.getEnabled()) {
//				if (reservation.hasState(workflow.getState())) {continue;}
				Date workflowdate = Time.addDuration(reservation.getWorkflowDate(workflow.getDatename()), workflow.getSignedDuration(), Time.DAY);
				if (donestates != null && donestates.contains(workflow.getState())) {
					if (workflowdate.after(olddate)) {
						olddate = workflowdate;
						oldstate = workflow.getState();
					}
				}
				else {
					if (workflowdate.before(duedate)) {
						duedate = workflowdate;
						duestate = workflow.getState();
					}
				}
//			}
		}
		if (!reservation.hasState(reservation.getOldstate())) {reservation.setDuedate(duedate.before(now) ? now : duedate);}
		reservation.setOldstate(oldstate);
		reservation.setDuestate(duestate);
	}

	/**
	 * Records changes to reservation state.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation whose state change is to be recorded.
	 */
	private static void onStateChange(SqlSession sqlSession, Reservation reservation, Product product) {
		if (reservation.noState() 
				|| reservation.hasState(Reservation.State.Initial.name())
//				|| reservation.hadState(reservation.getState())
		) {return;}
		Event<HasItem> event = new Event<HasItem>();
		event.setActivity(NameId.Type.Reservation.name());
		event.setActorid(reservation.getActorid());
		event.setAmount(reservation.getQuote());
		event.setDate(new Date());
		event.setName(reservation.getName());
		event.setNotes("Property " + product.getName() + " from  " + DF.format(reservation.getFromdate()) + " to " + DF.format(reservation.getTodate()));
		event.setOrganizationid(reservation.getOrganizationid());
		event.setParentid(reservation.getId());
		event.setProcess("StateChange");
		event.setState(reservation.getState());
		event.setType(Event.LOGGING);
		sqlSession.getMapper(EventMapper.class).create(event);
	}

	/**
	 * Executes the ReservationUpdate action to update a Reservation instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Reservation execute(SqlSession sqlSession, ReservationUpdate action) {
		return reservationUpdate(sqlSession, action);
	}

	/**
	 * Executes the ReservationUndo action to return a Reservation instance to its previous state.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Reservation execute(SqlSession sqlSession, ReservationUndo action) {
		try {sqlSession.getMapper(ReservationMapper.class).deleteoldstate(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return reservationUpdate(sqlSession, action);
	}

	/**
	 * Updates the specified Reservation instance if it does not collide with any other reservations.
	 * The collision test is done for the product itself and for parent and child properties if the 
	 * reserved product is part of a composite property. Text is updated and the next and previous
	 * states for the new state are set using he work flow rules of the organization.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be updated.
	 * @return the updated reservation.
	 */
	public static final Reservation reservationUpdate(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		LOG.debug("reservationUpdate in " + reservation);
		try {
			reservation.setCollisions(getCollisions(sqlSession, reservation));
			
			if (reservation.noCollisions()) {
				Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
				if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
				reservation.setAltpartyid(product.getAltpartyid());
				reservation.setOrganizationid(product.getSupplierid());
				if (reservation.noActorid()) {reservation.setActorid(Party.NO_ACTOR);}
				
				//TODO: extend for other APIs
				if (product.hasAltpartyid(PartyIds.PARTY_INTERHOME_ID)) {
					Party agent = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());
					if (agent == null) {throw new ServiceException(Error.party_id, reservation.getAgentid());}
					agent.setValues(RelationService.read(sqlSession, Relation.PARTY_VALUE, reservation.getAgentid(), null));
					String interhome = agent.getValue(Party.Value.Interhome.name());
					if (interhome == null || interhome.isEmpty()) {throw new ServiceException(Error.reservation_api, "You need an Interhome retailer code to book this property - request for a code for RAZOR at partners@interhome.com");}
					reservation.setAgentname(interhome);
				}

				TextService.update(sqlSession, reservation.getTexts());

				if (reservation.hasState(Reservation.State.Initial.name())) {
					sqlSession.getMapper(ReservationMapper.class).update(reservation);
					EmailService.offlineReservation(sqlSession, reservation, null, RazorHostsConfig.getOfflineUrl());
				}
				else {
					TaskService.update(sqlSession, reservation.getId(), reservation.getTasks());
					getWorkflow(sqlSession, reservation);
					sqlSession.getMapper(ReservationMapper.class).update(reservation);
					createQuotedetail(sqlSession, reservation);
					sqlSession.getMapper(SpecialMapper.class).deletequotecollision(reservation);
					onStateChange(sqlSession, reservation, product);
					if (reservation.hasState(Reservation.State.Provisional.name())) {
						if (reservation.hasAltpartyid()) {PartnerService.createReservation(sqlSession, reservation);}
						if (reservation.isActive() && product.noAltpartyid()) {EmailService.provisionalReservation(sqlSession, reservation);}
					}
					else if (reservation.hasState(Reservation.State.Departed.name())) {EmailService.departedReservation(sqlSession, reservation);}
					else if (reservation.hasAltpartyid() && reservation.hasState(Reservation.State.Confirmed.name())) {PartnerService.confirmReservation(sqlSession, reservation);}
					else if (reservation.hasAltpartyid() && reservation.hasState(Reservation.State.Cancelled.name())) {PartnerService.cancelReservation(sqlSession, reservation);}
				}
				sqlSession.getMapper(ReservationMapper.class).update(reservation);
				MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Reservation, reservation);
			}
		}
		catch (Throwable x) {
			sqlSession.rollback(); 
			reservation.setMessage(x.getMessage());
			MonitorService.log(x);
		}
		//LOG.debug("reservationUpdate out " + reservation);
		MonitorService.monitor("ReservationUpdate", timestamp);
		return reservation;
	}

	/**
	 * Gets the reservation collisions.
	 *
	 * @param sqlSession the current SQL session.
	 * @param hascollision the reservation to be checked for collisions.
	 * @return the list of collisions with the reservation, if any.
	 */
	public static ArrayList<NameId> getCollisions(SqlSession sqlSession, HasCollision hascollision) {
		ArrayList<NameId> collisions = sqlSession.getMapper(ReservationMapper.class).collisions(hascollision);
		Integer minavailable = sqlSession.getMapper(ReservationMapper.class).minavailable(hascollision);
		if (minavailable == null || minavailable <= 0) {
			collisions = new ArrayList<NameId>();
			collisions.add(new NameId("No Available Units", "0"));
			hascollision.setCollisions(collisions);
		}
//		else if (collisions == null || collisions.size() < minavailable) {reservation.setCollisions(null);}
//		else {reservation.setCollisions(collisions);}
		else if (collisions == null) {hascollision.setCollisions(null);}
		else {
			Integer size = hascollision.getQuantity();
			for (NameId collision : collisions) {
				Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(collision.getId());
				size += reservation.getQuantity();
			}
//			LOG.error("getCollisions " + hascollision.getQuantity() + " " + size + " " + minavailable + " " + (size <= minavailable));
			if (size <= minavailable) {hascollision.setCollisions(null);}
			else {hascollision.setCollisions(collisions);}
		}
		hascollision.addCollisions(sqlSession.getMapper(ReservationMapper.class).parentcollisions(hascollision));
		hascollision.addCollisions(sqlSession.getMapper(ReservationMapper.class).childcollisions(hascollision));
		return hascollision.getCollisions();
	}

	/**
	 * Gets the number of available units.
	 *
	 * @param sqlSession the current SQL session.
	 * @param hascollision the reservation to be checked for available units.
	 * @return the number of available properties.
	 */
	public static Integer getAvailable(SqlSession sqlSession, HasCollision hascollision) {
		ArrayList<NameId> collisions = sqlSession.getMapper(ReservationMapper.class).collisions(hascollision);
		Integer minavailable = sqlSession.getMapper(ReservationMapper.class).minavailable(hascollision);
		if (minavailable == null || minavailable <= 0) {return 0;}
		else if (collisions == null) {return minavailable;}
		else {
			Integer size = 0; //hascollision.getQuantity();
			for (NameId collision : collisions) {
				Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(collision.getId());
				size += reservation.getQuantity();
			}
			//LOG.error("getAvailable " + hascollision.getQuantity() + " " + size + " " + minavailable + " " + (minavailable - size) + " " + collisions);
			return minavailable - size;
		}
	}

	/**
	 * Creates the quote detail items.
	 *
	 * @param sqlSession the current SQL session.
	 * @param hasprice the object that has quoted price.
	 */
	private static void createQuotedetail(SqlSession sqlSession, HasPrice hasprice) {
		LOG.debug("getQuotedetail " + hasprice.getQuotedetail());
		Price price  = new Price();
		price.setEntitytype(NameId.Type.Reservation.name());
		price.setEntityid(hasprice.getReservationid());
		sqlSession.getMapper(PriceMapper.class).deletebyexample(price);
		price.setState(Price.CREATED);
		price.setMinimum(0.0);
		price.setUnit(Unit.EA);
		if (hasprice.getQuotedetail() != null) {
			for (Price quotedetail : hasprice.getQuotedetail()) {
				price.setQuantity(quotedetail.getQuantity());
				price.setValue(quotedetail.getValue());
				price.setCurrency(quotedetail.getCurrency());
				price.setType(quotedetail.getType());
				price.setName(quotedetail.getName());
				sqlSession.getMapper(PriceMapper.class).create(price);
			}
		}
	}

	/**
	 * Executes the ReservationDelete action to delete a Reservation instance.
	 * This sets its state to final but does not delete the instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Reservation execute(SqlSession sqlSession, ReservationDelete action) {
		Date timestamp = new Date();
		try {
			action.setState(Reservation.State.Final.name());
			sqlSession.getMapper(ReservationMapper.class).update(action);
			if (action.hasAltpartyid()) {PartnerService.cancelReservation(sqlSession, action);}
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Reservation, action);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		MonitorService.monitor("ReservationDelete", timestamp);
		return null;
	}

	/**
	 * Executes the NameIdAction action to read a list of reservation NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdAction action) {
		Date timestamp = new Date();
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(ReservationMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(ReservationMapper.class).nameidbyname(action));}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		MonitorService.monitor("Reservation NameIdAction", timestamp);
		return table;
	}

	/**
	 * Executes the LookBookSpecial action to read a list of special offer AvailableItem instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<AvailableItem> execute(SqlSession sqlSession, LookBookSpecial action) {
		Date timestamp = new Date();
//TODO: test		action.setProductids(sqlSession.getMapper(ReservationMapper.class).productsatposition(action));
		ArrayList<String> productids = sqlSession.getMapper(ReservationMapper.class).productsatposition(action);
		
		action.setProductids(LicenseService.getLicensed(sqlSession, action.getOrganizationid(), action.getAgentid(), productids, License.Type.Console, License.DEFAULT_WAIT));

		if (action.hasAttributes()) {action.setProductids(sqlSession.getMapper(ReservationMapper.class).productswithattributes(action));}

		ArrayList<AvailableItem> availableitems = sqlSession.getMapper(SpecialMapper.class).speciallist(action);
		//LOG.debug("ReservationService LookBookSpecial availableitems " + availableitems);
		Table<AvailableItem> table = new Table<AvailableItem>();
		if (availableitems == null) {return table;}
		ArrayList<AvailableItem> result = new ArrayList<AvailableItem>();
		int count = 0;
		for (AvailableItem availableitem : availableitems) {

		    	availableitem.setPerson(action.getCount());
			resetPrice(sqlSession, availableitem, action.getPriceunit());
			resetCurrency(sqlSession, availableitem, action.getCurrency());
			availableitem.setCost((availableitem.getQuote() - availableitem.getExtra()) * getDiscountfactor(sqlSession, availableitem));

			if (availableitem.noPrice()
					|| availableitem.getQuote() < action.getQuotemin() 
					|| availableitem.getQuote() > action.getQuotemax()
					|| availableitem.getCost() < 0.0
					|| ((availableitem.getQuote() - availableitem.getCost()) < (availableitem.getQuote() * action.getDiscount() / 100.0))
					) {continue;}
			setAlert(sqlSession, availableitem, availableitem.getFromdate(), availableitem.getTodate());
			if (count++ >= action.getStartrow() && result.size() < action.getNumrows()) {result.add(availableitem);}
		}
		Collections.sort(result);
		table.setDatasize(count);
		table.setValue(result);
		MonitorService.monitor("LookBookSpecial", timestamp);
		return table;
	}

	/**
	 * Executes the LookBook action to read a list of normal AvailableItem instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<AvailableItem> execute(SqlSession sqlSession, LookBook action) {
		Date timestamp = new Date();
		Table<AvailableItem> table = new Table<AvailableItem>();
		try {
//TODO: test			action.setProductids(sqlSession.getMapper(ReservationMapper.class).productsatposition(action));
			ArrayList<String> productids = sqlSession.getMapper(ReservationMapper.class).productsatposition(action);
			action.setProductids(LicenseService.getLicensed(sqlSession, action.getOrganizationid(), action.getAgentid(), productids, License.Type.Console, License.DEFAULT_WAIT));

			if (action.hasAttributes()) {action.setProductids(sqlSession.getMapper(ReservationMapper.class).productswithattributes(action));} // properties meeting criteria

			if (action.hasDuration()) {
				action.setCollisions(sqlSession.getMapper(ReservationMapper.class).productcollisions(action)); //remove properties that are not available between the dates
				action.removeCollisions(sqlSession.getMapper(ReservationMapper.class).productnocollisions(action)); //add back multiple unit collisions where product.quantity > count(reservations)
			} 

			if (action.noProductids()) {return table;}

			ArrayList<AvailableItem> availableitems = sqlSession.getMapper(ReservationMapper.class).lookbookitems(action);
			if (availableitems == null) {return table;}
			ArrayList<AvailableItem> priceditems = new ArrayList<AvailableItem>();
			int row = 0;
			for (AvailableItem availableitem : availableitems) {
				if (priceditems.size() >= action.getNumrows()) {break;}
				if (row++ >= action.getStartrow()) {
				    	availableitem.setPerson(action.getCount());
					resetPrice(sqlSession, availableitem, action.getPriceunit());
					resetCurrency(sqlSession, availableitem, action.getCurrency());
					availableitem.setCost((availableitem.getQuote() - availableitem.getExtra()) * getDiscountfactor(sqlSession, availableitem));

					if (availableitem.noPrice()
							|| availableitem.noQuote()
							|| availableitem.getQuote() < action.getQuotemin() 
							|| availableitem.getQuote() > action.getQuotemax()
							|| availableitem.getCost() < 0.0
							|| ((availableitem.getQuote() - availableitem.getCost()) < (availableitem.getQuote() * action.getDiscount() / 100.0))
							) {continue;}

					setAlert(sqlSession, availableitem, availableitem.getFromdate(), availableitem.getTodate());
					priceditems.add(availableitem);
				}
			}

			Collections.sort(priceditems);

			table.setDatasize(priceditems.size());
			table.setValue(priceditems);
		} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		MonitorService.monitor("LookBook", timestamp);
		return table;
	}

	/*
	 * Sets the alert string if one or more applies to the date range.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param availableitem the available item for the alert.
	 */
	private static void setAlert(SqlSession sqlSession, AvailableItem availableitem, Date fromdate, Date todate) {
		if (availableitem == null) {return;}
		Alert action = new Alert();
		action.setEntitytype(NameId.Type.Product.name());
		action.setEntityid(availableitem.getProductid());
		action.setFromdate(fromdate);
		action.setTodate(todate);
		ArrayList<Alert> alerts = sqlSession.getMapper(AlertMapper.class).exists(action);
		if (alerts == null || alerts.isEmpty()) {availableitem.setAlert(null);}
		else {
			StringBuilder sb =  new StringBuilder();
			for (Alert alert : alerts) {sb.append(DF.format(alert.getFromdate())).append(" - ").append(DF.format(alert.getTodate())).append(" ").append(alert.getName()).append("\n");}
			availableitem.setAlert(sb.toString());
		}
	}
	
	/*
	 * Sets the alert string if one or more applies to the date range.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param availableitem the available item for the alert.
	 */
	private static ArrayList<Alert> getAlerts(SqlSession sqlSession, HasAlert hasalert) {
		Alert alert = new Alert();
		alert.setEntitytype(hasalert.getEntitytype());
		alert.setEntityid(hasalert.getEntityid());
		alert.setFromdate(hasalert.getFromdate());
		alert.setTodate(hasalert.getTodate());
		return sqlSession.getMapper(AlertMapper.class).exists(alert);
	}
	
	/*
	 * Resets the prices of the specified HasPrice (reservation or available item) instance for a night or the whole stay.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param hasprice the reservation or available item to be priced.
	 * @param priceunit the price unit is true if prices are for the whole stay, or false if prices are per night. 
	 */
	/**
	 * Reset price.
	 *
	 * @param sqlSession the sql session
	 * @param hasprice the hasprice
	 * @param priceunit the priceunit
	 */
	private void resetPrice(SqlSession sqlSession, HasPrice hasprice, Boolean priceunit) {
		Date timestamp = new Date();
		if (hasprice.getDuration(Time.DAY) <= 0) {return;}

		computePrice(sqlSession, hasprice, null);
		Double discountfactor = getDiscountfactor(sqlSession, hasprice);
		hasprice.setCost((hasprice.getQuote() - hasprice.getExtra()) * discountfactor);

		if (!hasprice.noPrice() && !priceunit) { //false = night true = stay
			Double unitfactor = 1.0 / hasprice.getDuration(Time.DAY);
			hasprice.setPrice(hasprice.getPrice() * unitfactor);
			hasprice.setQuote(hasprice.getQuote() * unitfactor);
			hasprice.setExtra(hasprice.getExtra() * unitfactor);
			hasprice.setCost(hasprice.getCost() * unitfactor);
		}
		hasprice.setPriceunit(priceunit);

		MonitorService.monitor("ResetPrice", timestamp);
	}

	/*
	 * Resets the prices of the specified HasPrice (reservation or available item) instance to the specified currency.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param hasprice the reservation or available item to be priced.
	 * @param tocurrency the code of the currency of the prices. 
	 */
	/**
	 * Reset currency.
	 *
	 * @param sqlSession the sql session
	 * @param hasprice the hasprice
	 * @param tocurrency the tocurrency
	 */
	private void resetCurrency(SqlSession sqlSession, HasPrice hasprice, String tocurrency) {
		Date timestamp = new Date();
		if (hasprice == null || hasprice.hasCurrency(tocurrency)) {return;}
		Double exchangerate = WebService.getRate(sqlSession, hasprice.getCurrency(), tocurrency, new Date());
		hasprice.setPrice(hasprice.getPrice() * exchangerate);
		hasprice.setQuote(hasprice.getQuote() * exchangerate);
		hasprice.setCurrency(tocurrency);
		MonitorService.monitor("ResetCurrency", timestamp);
	}

	/**
	 * Executes the WidgetQuote action to get a PriceResponse instance of a reservation.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the price response.
	 */
	public final PriceResponse execute(SqlSession sqlSession, WidgetQuote action) {
		Date timestamp = new Date();
		Product product = sqlSession.getMapper(ProductMapper.class).read(action.getProductid());
		action.setOrganizationid(product.getSupplierid());
		PriceResponse result = getPriceResponse(sqlSession, action);
		MonitorService.monitor("WidgetQuote", timestamp);
		return result;
	}

	/**
	 * Executes the ReservationPrice action to get a PriceResponse instance of a reservation.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the price response.
	 */
	public final PriceResponse execute(SqlSession sqlSession, ReservationPrice action) {
		Date timestamp = new Date();
		PriceResponse result = getPriceResponse(sqlSession, action);
		MonitorService.monitor("ReservationPrice", timestamp);
		return result;
	}

	/**
	 * Executes the ReservationEventJournalTable action to read a table of EventJournal (journal event) instances.
	 * @see net.cbtltd.shared.journal.EventJournal
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<EventJournal> execute(SqlSession sqlSession, ReservationEventJournalTable action) {
		Date timestamp = new Date();
		Table<EventJournal> table = new Table<EventJournal>();
		try {
			table.setDatasize(sqlSession.getMapper(EventMapper.class).countbyreservation(action.getId()));
			table.setValue(sqlSession.getMapper(EventMapper.class).listbyreservation(action.getId()));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		MonitorService.monitor("ReservationEventJournalTable", timestamp);
		return table;
	}

	/**
	 * Executes the ReservationActionBalance action to get the financial balance of a reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the double financial balance of a reservation.
	 */
	public final DoubleResponse execute(SqlSession sqlSession, ReservationEventJournalBalance action) {
		Date timestamp = new Date();
		DoubleResponse response = new DoubleResponse();
		response.setValue(sqlSession.getMapper(EventMapper.class).balancebyreservation(action.getId()));
		MonitorService.monitor("ReservationEventJournalBalance", timestamp);
		return response;
	}

	/**
	 * Gets the price response.
	 *
	 * @param sqlSession the sql session
	 * @param action the action
	 * @return the price response
	 */
	private PriceResponse getPriceResponse(SqlSession sqlSession, ReservationPrice action) {
		Date timestamp = new Date();
		PriceResponse response = new PriceResponse();
		try {
			computePrice(sqlSession, action, null);
			response.setValue(action.getPrice());
			response.setQuote(action.getQuote()); //yielded
			response.setQuotedetail(action.getQuotedetail());
			response.setExtra(action.getExtra());
			response.setCurrency(action.getCurrency());
			Double discountfactor = getDiscountfactor(sqlSession, action);
			response.setDiscountfactor(discountfactor);
			response.setCost((response.getQuote() - response.getExtra()) * discountfactor);
			response.setDeposit(getDeposit(sqlSession, action)); //.getOrganizationid(), action.getFromdate()));
			response.setCollisions(getCollisions(sqlSession, action));
			response.setAlerts(getAlerts(sqlSession, action));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		MonitorService.monitor("ReservationPrice", timestamp);
		return response;
	}

	/**
	 * Gets the deposit percentage required by a property manager (organization) on the specified date
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation for which the deposit percentage is to be calculated.
	 * @return the deposit percentage.
	 */
	public static final Double getDeposit(SqlSession sqlSession, Reservation reservation) {
		String temp = RelationService.value(sqlSession, Relation.PARTY_VALUE, reservation.getOrganizationid(), Party.Value.Payfull.name());
		Integer payfull = temp == null ? 30 : Integer.valueOf(temp);
		temp = RelationService.value(sqlSession, Relation.PARTY_VALUE, reservation.getOrganizationid(), Party.Value.Payunit.name());
		String unit = temp == null ? Unit.DAY : temp;
		if (Time.WEEK.name().equalsIgnoreCase(unit)) {payfull *= 7;}
		else if (Time.MONTH.name().equalsIgnoreCase(unit)) {payfull *= 30;}
		if (Time.getDuration(new Date(), reservation.getFromdate(), Time.DAY) <= payfull) {return 100.;}
		
		String type = RelationService.value(sqlSession, Relation.PARTY_VALUE, reservation.getOrganizationid(), Party.Value.DepositType.name());
		if (type == null) {type = Party.DEPOSITS[1];}
		temp = RelationService.value(sqlSession, Relation.PARTY_VALUE, reservation.getOrganizationid(), Party.Value.Deposit.name());
		Double value = temp == null ? 0.0 : Double.valueOf(temp);
//		if (Party.DEPOSITS[0].equalsIgnoreCase(type)) {value = (value / 100.0) * reservation.getQuote();} // % Booking Value
		if (Party.DEPOSITS[1].equalsIgnoreCase(type)) {value = (value / 100.0) * reservation.getQuote() / reservation.getDuration(Time.DAY);} // % Daily Rate
		else if (Party.DEPOSITS[2].equalsIgnoreCase(type)) {;} // Amount per Booking
		else if (Party.DEPOSITS[3].equalsIgnoreCase(type)) {value = value * reservation.getDuration(Time.DAY);} // Amount per Day
		else {value = (value / 100.0) * reservation.getQuote();} // % Booking Value
		LOG.debug("" + type + " " + temp + " " + value + " " + reservation.getQuote() + " " + (value * 100.0 / reservation.getQuote()) + " " + reservation.getDuration(Time.DAY));
		value = value * 100.0 / reservation.getQuote();
		return value.doubleValue();
	}
	
	/**
	 * Gets the deposit percentage required by a property manager (organization) on the specified date.
	 * 
	 * @param reservation the reservation for which the deposit percentage is to be calculated.
	 * @param propertyManagerInfo property manager that is responsible for the reservation.
	 * @return the deposit percentage.
	 */
	public static final Double getDeposit(Reservation reservation, PropertyManagerInfo propertyManagerInfo) {
		if(propertyManagerInfo.getNumberOfPayments() == null) {
			throw new ServiceException(Error.database_cannot_find, "numberOfPayments in property manager info");
		}
		// 1. Check number_of_payments, return 100 if 1 payment should be processed
		if(propertyManagerInfo.getNumberOfPayments() == 1) {return 100.;}
		
		// 2. Return 100 if current date is between payment remainder date and arrival date 
		Integer secondPaymentRemainder = propertyManagerInfo.getRemainderPaymentDate();
		if (Time.getDuration(new Date(), reservation.getFromdate(), Time.DAY) <= secondPaymentRemainder) {return 100.;}

		// 3. Calculate deposit percentage regarding the payment rules
		Integer paymentType = propertyManagerInfo.getPaymentType();
		switch(paymentType) {
		case 1:
			return propertyManagerInfo.getPaymentAmount().doubleValue();
		case 2:
			Double percentage = propertyManagerInfo.getPaymentAmount() / reservation.getQuote() * 100.;
			return percentage.doubleValue();
		default:
			throw new ServiceException(Error.payment_type_unsupported);
		}
	}

	/**
	 * Gets the payment type required by a property manager for deposits and full payment.
	 *
	 * @param sqlSession the current SQL session.
	 * @param organizationid the ID of the property manager.
	 * @return the payment policy.
	 */
	public static final String getPaytype(SqlSession sqlSession, String organizationid) {
		String value = RelationService.value(sqlSession, Relation.PARTY_VALUE, organizationid, Party.Value.Paytype.name());
		return value == null ? Organization.Paytype.None.name() : value;
	}

	/**
	 * Gets the discount factor available to an agency (agent) from a property manager (organization).
	 *
	 * @param sqlSession the current SQL session.
	 * @param hasprice the reservation.
	 * @return the discount factor modified by a contracted discount if it exists.
	 */
	public static final Double getDiscountfactor(SqlSession sqlSession, HasPrice hasprice) {
		Double discountfactor = 1.0;
		if (hasprice.noAgentid()) {return discountfactor;}
		Contract contract = new Contract(NameId.Type.Reservation.name(), hasprice.getSupplierid(), hasprice.getAgentid());
		contract = sqlSession.getMapper(ContractMapper.class).readbyexample(contract);
		if (contract == null) {
			Product product = sqlSession.getMapper(ProductMapper.class).read(hasprice.getProductid());
			discountfactor =  (product == null || product.getDiscount() == null) ? 1.0 : (100.0 - product.getDiscount()) / 100.0;
		}
		else if (contract.hasState(Contract.SUSPENDED)) {discountfactor = -1.0;}
		else discountfactor = (100.0 - contract.getDiscount()) / 100.0;
		return discountfactor;
	}

	/**
	 * Adjusts the quoted price for the specified property, agent, date range and currency.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action an item that can be priced.
	 */
	public static final PriceResponse execute(SqlSession sqlSession, ReservationPriceAdjust action) {
		Date timestamp = new Date();
		PriceResponse response = new PriceResponse();
		try {
			Double newquote = 0.0;
			ArrayList<Price> newquotedetail = new ArrayList<Price>();
			if (action.getQuote() >= 0.01) {
				Double oldquote = 0.0;
				Double oldtax = 0.0;
				for (Price price : action.getQuotedetail()) {
					if (price.hasType(Price.TAX_INCLUDED) 
							|| price.hasType(Price.TAX_EXCLUDED) 
							|| price.hasType(Price.TAX_ON_TAX)) {oldtax += price.getTotalvalue();}
					oldquote += price.getTotalvalue();
				}
				Double taxratio = action.getQuote() / oldquote;

				for (Price price : action.getQuotedetail()) {
					if (price.hasType(Price.MANDATORY) 
							|| price.hasType(Price.OPTIONAL)) {
						newquotedetail.add(price);
						newquote += price.getTotalvalue();
					}
					else if (price.hasType(Price.TAX_INCLUDED) 
							|| price.hasType(Price.TAX_EXCLUDED) 
							|| price.hasType(Price.TAX_ON_TAX)) {
						price.setValue(NameId.round(price.getValue() * taxratio));
						newquotedetail.add(price);
						newquote += price.getTotalvalue();
					}
				}
				Double value = action.getQuote() - newquote;
				Double quantity = action.getDuration(Time.DAY);
				Price price = new Price();
				price.setId(Model.ZERO);
				price.setEntitytype(NameId.Type.Reservation.name());
				price.setEntityid(action.getReservationid());
				price.setName(Price.ADJUSTED);
				price.setType(Price.RATE);
				price.setPartyname("");
				price.setQuantity(quantity);
				price.setUnit(Time.DAY.name());
				price.setValue(value/quantity);
				price.setCurrency(action.getCurrency());
				newquotedetail.add(price);
				newquote += price.getTotalvalue();
			}
			response.setValue(action.getPrice());
			response.setQuote(newquote); //adjusted
			response.setQuotedetail(newquotedetail);
			response.setExtra(action.getExtra());
			response.setCurrency(action.getCurrency());
			Double discountfactor = getDiscountfactor(sqlSession, action);
			response.setDiscountfactor(discountfactor);
			response.setCost((response.getQuote() - response.getExtra()) * discountfactor);
			response.setAlerts(getAlerts(sqlSession, action));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		MonitorService.monitor("ReservationPriceAdjust", timestamp);
		return response;
	}

	private static boolean hasMinimum = true;

	/**
	 * Gets the quoted or yielded price for the specified property, agent, date range and currency.
	 * Applies yield management rules if yielded is true. 
	 *
	 * @param sqlSession the current SQL session.
	 * @param hasPrice the item that can be priced.
	 */
	public static final net.cbtltd.rest.payment.ReservationPrice computePrice(SqlSession sqlSession, HasPrice hasPrice, String currency) {
		net.cbtltd.rest.payment.ReservationPrice reservationPrice = new net.cbtltd.rest.payment.ReservationPrice();
		List<QuoteDetail> quoteDetails = new ArrayList<QuoteDetail>();
		reservationPrice.setTotal(0.0);
		
		Date timestamp = new Date();
		PriceComplexValue priceValue = new PriceComplexValue();
		hasMinimum = true;
		
		Product product = sqlSession.getMapper(ProductMapper.class).read(hasPrice.getProductid());
		PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(product.getSupplierid()));
		hasPrice.setSupplierid(product.getSupplierid());
		
		hasPrice.setQuotedetail(new ArrayList<Price>());
		priceValue = addPriceAdjustments(sqlSession, hasPrice, priceValue);
		priceValue = addTotalPrice(sqlSession, hasPrice, priceValue);
		
		if (priceValue == null){
			return reservationPrice;
		}
										
		priceValue.setTotPrice(priceValue.getTotPrice() * hasPrice.getQuantity()); 
		priceValue.setTotQuote(priceValue.getTotQuote() * hasPrice.getQuantity());
		
		// Util class for commission calculation (NetRate, CreditCardFee)
		CommissionCalculationUtil commissionUtil = new CommissionCalculationUtil(sqlSession, hasPrice, priceValue.getTotPrice(), 0.0);
		Double newPublishedPrice = 0.0;

		if (commissionUtil.isNetRate()){
		    
		    // Save nightly rate quote for future calculation
		    hasPrice.setNightlyrate(priceValue.getTotPrice());
		    
		    // Calculate new NetRates prices
            	    newPublishedPrice = commissionUtil.getNewPublishedNightlyRate();
            	    Double newPublichedPriceCCFee = commissionUtil.getCreditCardFeeValue(newPublishedPrice);            	    
            	    priceValue.setTotPrice(newPublishedPrice + newPublichedPriceCCFee);
            	    
            	    commissionUtil.setPriceAmount(priceValue.getTotQuote());  // Price + Yields
            	    Double newQuoteAmount = commissionUtil.getNewPublishedNightlyRate();
            	    
            	    if (commissionUtil.isPMFundsHolder()){
            		newQuoteAmount += newPublichedPriceCCFee;
            	    }
            	    
            	    priceValue.setTotQuote(newQuoteAmount);
		    /*Double newPublishedPriceAmount = commissionUtil.getNewPublishedNightlyRate();
		    Double newPublichedPriceCCFee = commissionUtil.getCreditCardFeeValue(newPublishedPriceAmount);
		    
		    priceValue.setTotPrice(newPublishedPriceAmount + newPublichedPriceCCFee);
		    priceValue.setTotQuote(newPublishedPriceAmount);*/		    
		}
		
		priceValue = addPriceFeatures(sqlSession, hasPrice, priceValue);
		// new version of Fees from Fee table (Streamline)
		priceValue = FeeCalculationHelper.addTaxableFees(sqlSession, hasPrice, priceValue);
		
		//  taxes with relations (will be deprecated)
		priceValue = addPriceTaxes(sqlSession, hasPrice, priceValue);
		// new type of taxes (Streamline)
		priceValue = TaxesCalculationHelper.addTaxes(sqlSession, hasPrice, priceValue);
		
		priceValue = addNotTaxedPrices(sqlSession, hasPrice, priceValue);
		// new version of Fees from Fee table (Streamline)
		priceValue = FeeCalculationHelper.addNotTaxableFees(sqlSession, hasPrice, priceValue);
		
		// Damage insurance and cleaning fee adding
		if(product.getSecuritydeposit() != null) {
			Double depositValue = product.getSecuritydeposit();
			priceValue.addTotalQuote(depositValue);
			priceValue.addTotalExtra(depositValue);
		}
		if(product.getCleaningfee() != null) {
			Double feeValue = product.getCleaningfee();
			priceValue.addTotalQuote(feeValue);
			priceValue.addTotalExtra(feeValue);
		}
		
		// add special mandatory Fees from Fee table (Streamline, ...)
		priceValue = FeeCalculationHelper.addSpecialFees(sqlSession, hasPrice, priceValue);

		if (commissionUtil.isNetRate() && commissionUtil.isBPFundsHolder()){
		    
            	    commissionUtil.setExtraAmount(priceValue.getTotExtra());
            	    
            	    //Double newPublishedQuoteAmount = priceValue.getTotQuote() - priceValue.getTotExtra();
            	    Double newPublishedQuoteCCFee = commissionUtil.getCreditCardFeeValue(newPublishedPrice);
            
            	    priceValue.addTotalQuote(newPublishedQuoteCCFee);
		}

		LOG.debug("computePrice getQuotedetail " + hasPrice.getQuotedetail());
		hasPrice.setPrice(priceValue.getTotPrice()); /*hasprice.setPrice(totPrice);*/
		hasPrice.setQuote(priceValue.getTotQuote()); /*hasprice.setQuote(totQuote);*/
		hasPrice.setExtra(priceValue.getTotExtra());  /*hasprice.setExtra(totExtra);*/
		
		quoteDetails = ReservationUtil.getReservationPriceQuoteDetails(hasPrice);		
		quoteDetails.add(new QuoteDetail(String.valueOf(priceValue.getTotQuote()), currency, "Total quote", "", "", false));
		
		if(propertyManagerInfo != null &&
				propertyManagerInfo.getAdditionalCommission() != null && 
					propertyManagerInfo.getFundsHolder() == ManagerToGateway.PROPERTY_MANAGER_HOLDER) {
			PriceUtil.addCommission(hasPrice, propertyManagerInfo.getAdditionalCommission() / 100.);
		}
		
		reservationPrice.setPrice(priceValue.getTotPrice());
		reservationPrice.setTotal(priceValue.getTotQuote());
		reservationPrice.setCurrency(hasPrice.getCurrency());
		reservationPrice.setQuoteDetails(quoteDetails);
		
		MonitorService.monitor("computePrice", timestamp);
		
		if(currency != null) {
			hasPrice = PriceUtil.convertCurrency(sqlSession, hasPrice, currency);
			reservationPrice = PriceUtil.convertCurrency(sqlSession, reservationPrice, currency);
		}
		
		return reservationPrice;
	}
	
	/**
	 * Gets the quoted or yielded price for the specified property, agent, date range and currency during search process.
	 * Applies yield management rules if yielded is true. 
	 *
	 * @param sqlSession the current SQL session.
	 * @param hasPrice the item that can be priced.
	 */
	public static final net.cbtltd.rest.payment.ReservationPrice computeLivePrice(SqlSession sqlSession, HasPrice hasPrice, net.cbtltd.rest.payment.ReservationPrice reservationPrice,  String currency) {
		
		if (hasPrice == null || hasPrice.getPrice() == null || hasPrice.getPrice() <= 0){
		    return reservationPrice;
		}
		
		Date timestamp = new Date();
		PriceComplexValue priceValue = new PriceComplexValue();
		
		priceValue.setTotPrice(hasPrice.getPrice());
		priceValue.setTotQuote(hasPrice.getPrice());
		
		Product product = sqlSession.getMapper(ProductMapper.class).read(hasPrice.getProductid());
		PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(product.getSupplierid()));
		hasPrice.setSupplierid(product.getSupplierid());

		if (hasPrice.getQuantity() != null){
		    priceValue.setTotPrice(priceValue.getTotPrice() * hasPrice.getQuantity());
		    priceValue.setTotQuote(priceValue.getTotQuote() * hasPrice.getQuantity());
		}
		
		// Util class for commission calculation (NetRate, CreditCardFee)
		CommissionCalculationUtil commissionUtil = new CommissionCalculationUtil(sqlSession, hasPrice, priceValue.getTotPrice(), 0.0);
		
		if (commissionUtil.isNetRate()){
		    //return reservationPrice;
		
        		reservationPrice = new net.cbtltd.rest.payment.ReservationPrice();
        		List<QuoteDetail> quoteDetails = new ArrayList<QuoteDetail>();
        		reservationPrice.setTotal(0.0);
        		
        		hasPrice.setQuotedetail(new ArrayList<Price>());
    		    
		    // Save nightly rate quote for future calculation
		    hasPrice.setNightlyrate(priceValue.getTotPrice());
		    
		    // Calculate new NetRates prices
		    Double newPublishedPrice = commissionUtil.getNewPublishedNightlyRate();
		    Double newPublichedPriceCCFee = commissionUtil.getCreditCardFeeValue(newPublishedPrice);            	    
		    priceValue.setTotPrice(newPublishedPrice + newPublichedPriceCCFee);
		    
		    if (commissionUtil.isPMFundsHolder()){
			priceValue.setTotQuote(newPublishedPrice + newPublichedPriceCCFee);
            	    }else {
            		priceValue.setTotQuote(newPublishedPrice);
            	    }
		    
        		priceValue = addPriceFeatures(sqlSession, hasPrice, priceValue);
        		// new version of Fees from Fee table (Streamline)
        		priceValue = FeeCalculationHelper.addTaxableFees(sqlSession, hasPrice, priceValue);
        
        		//  taxes with relations (will be deprecated)
        		priceValue = addPriceTaxes(sqlSession, hasPrice, priceValue);
        		// new type of taxes (Streamline)
        		priceValue = TaxesCalculationHelper.addTaxes(sqlSession, hasPrice, priceValue);
        
        		priceValue = addNotTaxedPrices(sqlSession, hasPrice, priceValue);
        		// new version of Fees from Fee table (Streamline)
        		priceValue = FeeCalculationHelper.addNotTaxableFees(sqlSession, hasPrice, priceValue);
        
        		// Damage insurance and cleaning fee adding
        		if(product.getSecuritydeposit() != null) {
        			Double depositValue = product.getSecuritydeposit();
        			priceValue.addTotalQuote(depositValue);
        			priceValue.addTotalExtra(depositValue);
        		}
        		if(product.getCleaningfee() != null) {
        			Double feeValue = product.getCleaningfee();
        			priceValue.addTotalQuote(feeValue);
        			priceValue.addTotalExtra(feeValue);
        		}
        
        		// add special mandatory Fees from Fee table (Streamline, ...)
        		priceValue = FeeCalculationHelper.addSpecialFees(sqlSession, hasPrice, priceValue);
        		
        		if (commissionUtil.isBPFundsHolder()){
        		    
        		    	commissionUtil.setExtraAmount(priceValue.getTotExtra());
    		        	Double newPublishedQuoteCCFee = commissionUtil.getCreditCardFeeValue(newPublishedPrice);
    		        	priceValue.addTotalQuote(newPublishedQuoteCCFee);
        		}

        		LOG.debug("computePrice getQuotedetail " + hasPrice.getQuotedetail());
        		hasPrice.setPrice(priceValue.getTotPrice()); /*hasprice.setPrice(totPrice);*/
        		hasPrice.setQuote(priceValue.getTotQuote()); /*hasprice.setQuote(totQuote);*/
        		hasPrice.setExtra(priceValue.getTotExtra());  /*hasprice.setExtra(totExtra);*/

        		quoteDetails = ReservationUtil.getReservationPriceQuoteDetails(hasPrice);		
        		quoteDetails.add(new QuoteDetail(String.valueOf(priceValue.getTotQuote()), currency, "Total quote", "", "", false));
        		reservationPrice.setQuoteDetails(quoteDetails);
        		
        		reservationPrice.setCurrency(hasPrice.getCurrency());
        		
		}

		if(propertyManagerInfo != null &&
				propertyManagerInfo.getAdditionalCommission() != null && 
					propertyManagerInfo.getFundsHolder() == ManagerToGateway.PROPERTY_MANAGER_HOLDER) {
			PriceUtil.addCommission(hasPrice, propertyManagerInfo.getAdditionalCommission() / 100.);
		}

		if (reservationPrice != null){
		    reservationPrice.setPrice(hasPrice.getPrice());
		    reservationPrice.setTotal(hasPrice.getQuote());    
		}
		
		MonitorService.monitor("computeLivePrice", timestamp);

		if(currency != null) {
			hasPrice = PriceUtil.convertCurrency(sqlSession, hasPrice, currency);
			if (reservationPrice != null){
			    reservationPrice = PriceUtil.convertCurrency(sqlSession, reservationPrice, currency);
			}
		}

		return reservationPrice;
									
	}
	
	/**
	 * Add adjustments to the TotalPrice.
	 * @param sqlSession
	 * @param hasPrice
	 * @param priceValue
	 * @return
	 */
	public static PriceComplexValue addPriceAdjustments(SqlSession sqlSession, HasPrice hasPrice, PriceComplexValue priceValue) {
		
		byte b = 0x0;
		if (hasPrice != null && priceValue != null){
			
			if (hasPrice.getFromdate() == null || hasPrice.getTodate() == null
					|| !StringUtils.isNotEmpty(hasPrice.getProductid()) || !StringUtils.isNotEmpty(hasPrice.getSupplierid())){
				return priceValue; 
			}
			Calendar calendar =  Calendar.getInstance();
			calendar.setTime(hasPrice.getFromdate());	
			int checkInDay = calendar.get(Calendar.DAY_OF_WEEK);
			// create mask for service days			
			b |= 1 << checkInDay - 1;
			
			// select adjustment by reservation example
			Adjustment example = new Adjustment();
			example.setProductID(hasPrice.getProductid());
			example.setPartyID(hasPrice.getSupplierid());
			example.setFromDate(hasPrice.getFromdate());
			example.setState(Adjustment.Created);
			example.setToDate(Time.addDuration(hasPrice.getTodate(), -1.0, Time.DAY));
			example.setMinStay(hasPrice.getDuration(Time.DAY).intValue());
			example.setMaxStay(hasPrice.getDuration(Time.DAY).intValue());
			
			Adjustment adj = sqlSession.getMapper(AdjustmentMapper.class).readbyexample(example);
			
			// check adj for null and if checkin day in service days
			if (adj == null || (b & adj.getServicedays()) == 0){
				// select adjustment with fix price value.
				Adjustment action = new Adjustment();
				action.setProductID(hasPrice.getProductid());
				action.setPartyID(hasPrice.getSupplierid());
				action.setMaxStay(Adjustment.MAX_STAY_VALUE);
				action.setFromDate(hasPrice.getFromdate());
				action.setState(Adjustment.Created);
				Double fixPriceValue = sqlSession.getMapper(AdjustmentMapper.class).getfixprice(action);
				
				if (fixPriceValue != null){
					priceValue.addTotalPrice(fixPriceValue); 
					priceValue.addTotalQuote(fixPriceValue);
				}
			}else{
				//get reservation duration in days
				Double duration = Time.getDuration(hasPrice.getFromdate(), hasPrice.getTodate(), Time.DAY);
				//if adjustment value is not null
				if (adj.getExtra() != null){
					// get total adjustment value
					Double adjustmentValue = duration * adj.getExtra();
					priceValue.addTotalPrice(adjustmentValue);
					priceValue.addTotalQuote(adjustmentValue);
				}
				if (adj.getFixPrice() != null){
					priceValue.addTotalPrice(adj.getFixPrice()); 
					priceValue.addTotalQuote(adj.getFixPrice());
				}
			}
		}
		
		return priceValue;
	}

	public static final void computeOneRowPrice(SqlSession sqlSession, HasPrice hasPrice, Product product, String currency) {
		
		Date timestamp = new Date();
		PriceComplexValue priceValue = new PriceComplexValue();
		
		PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(product.getSupplierid()));
		
		hasPrice.setSupplierid(product.getSupplierid());
		hasPrice.setQuotedetail(new ArrayList<Price>());
		
		if (!product.IsUseonepricerow()){
			return;
		}
			
		final Price example = new Price();
		example.setEntitytype(NameId.Type.Product.name());
		example.setEntityid(hasPrice.getProductid());
		example.setPartyid(hasPrice.getSupplierid());
		example.setDate(hasPrice.getFromdate());
		example.setTodate(hasPrice.getTodate());
		example.setQuantity(hasPrice.getDuration(Time.DAY));
		example.setUnit(hasPrice.getUnit());
		
		Price price = sqlSession.getMapper(PriceMapper.class).readexactmatch(example);
		if (price == null){
			hasPrice.setPrice(0.0);
			hasPrice.setQuote(0.0);
			hasPrice.setExtra(0.0);
			return ;
		}
		hasPrice.setCurrency(price.getCurrency());
		
		double totMinimum = 0.0;
		totMinimum = (totMinimum < price.getMinimum()) ? price.getMinimum() : totMinimum;
		Double duration = Time.getDuration(hasPrice.getFromdate(), hasPrice.getTodate(), Time.DAY);
		double value = price.getValue() * duration;
		priceValue.addTotalPrice(value); 
		
		ReservationUtil.addQuotedetail(hasPrice, price.getId(), price.getName(), Price.RATE, price.getSupplierid(), duration, price.getUnit(), (hasMinimum && value < totMinimum) ? totMinimum : value, price.getDate(), price.getTodate());
		
		Yield yield = new Yield(NameId.Type.Product.name(), hasPrice.getProductid(), hasPrice.getFromdate(), hasPrice.getTodate());
		ArrayList<Yield> rules = sqlSession.getMapper(YieldMapper.class).listbyentity(yield);
		
		// apply yield management rules for each day!
		Integer occupancy = 0;
		if (hasRule(rules, Yield.OCCUPANCY_ABOVE) || hasRule(rules, Yield.OCCUPANCY_BELOW)) {
			occupancy = sqlSession.getMapper(ReservationMapper.class).occupancy(yield);
			occupancy = (occupancy == null) ? 0 : occupancy;
		}

		boolean gap = false;
		if (hasRule(rules, Yield.GAP_FILLER)) {
			Integer previous = sqlSession.getMapper(ReservationMapper.class).previous(yield);
			Integer next = sqlSession.getMapper(ReservationMapper.class).next(yield);
			gap = (previous != null && previous == 1 && next != null && next == 1);
		}

		for (int day = Time.getDay(hasPrice.getFromdate()); day < Time.getDay(hasPrice.getTodate()); day++) {
			Double yieldValue = getYieldValue(rules, Time.getDate(day), gap, hasPrice.getDuration(Time.DAY).intValue(), occupancy, price.getValue(), hasPrice);
			priceValue.addTotalQuote(yieldValue);
		}
		
		priceValue.setTotPrice((hasMinimum && priceValue.getTotPrice() < totMinimum) ? totMinimum : priceValue.getTotPrice());
		
		if (hasMinimum && priceValue.getTotQuote() < totMinimum) {
			priceValue.setTotQuote(totMinimum);
			ArrayList<Price> prices = new ArrayList<Price>(hasPrice.getQuotedetail());
			for (Price p : prices) {
				if (p.hasType(Price.YIELD)) {hasPrice.getQuotedetail().remove(p);}
			}
		}
	
		/*addTotalPrice(sqlSession, hasprice, priceValue);*/

		priceValue.setTotPrice(priceValue.getTotPrice() * hasPrice.getQuantity()); /*totPrice *= hasprice.getQuantity();*/
		priceValue.setTotQuote(priceValue.getTotQuote() * hasPrice.getQuantity()); /*totQuote *= hasprice.getQuantity();*/

		// Util class for commission calculation (NetRate, CreditCardFee)
		CommissionCalculationUtil commissionUtil = new CommissionCalculationUtil(sqlSession, hasPrice, priceValue.getTotPrice(), 0.0);
		Double newPublishedPrice = 0.0;
		
		if (commissionUtil.isNetRate()){
		    
		    // Save nightly rate quote for future calculation
		    hasPrice.setNightlyrate(priceValue.getTotPrice());
		    
		    // Calculate new NetRates prices
            	    newPublishedPrice = commissionUtil.getNewPublishedNightlyRate();
            	    Double newPublichedPriceCCFee = commissionUtil.getCreditCardFeeValue(newPublishedPrice);            	    
            	    priceValue.setTotPrice(newPublishedPrice + newPublichedPriceCCFee);
            	    
            	    commissionUtil.setPriceAmount(priceValue.getTotQuote());  // Price + Yields
            	    Double newQuoteAmount = commissionUtil.getNewPublishedNightlyRate();
            	    
            	    if (commissionUtil.isPMFundsHolder()){
        		newQuoteAmount += newPublichedPriceCCFee;
        	    }
            	    
            	    priceValue.setTotQuote(newQuoteAmount);
		    /*Double newPublishedPriceAmount = commissionUtil.getNewPublishedNightlyRate();
		    Double newPublichedPriceCCFee = commissionUtil.getCreditCardFeeValue(newPublishedPriceAmount);
		    
		    priceValue.setTotPrice(newPublishedPriceAmount + newPublichedPriceCCFee);
		    priceValue.setTotQuote(newPublishedPriceAmount);*/		    
		}
		
		priceValue = addPriceFeatures(sqlSession, hasPrice, priceValue);
		// new version of Fees from Fee table (Streamline)
		priceValue = FeeCalculationHelper.addTaxableFees(sqlSession, hasPrice, priceValue);
		
		//  taxes with relations (will be deprecated)
		priceValue = addPriceTaxes(sqlSession, hasPrice, priceValue);
		// new type of taxes (Streamline)
		priceValue = TaxesCalculationHelper.addTaxes(sqlSession, hasPrice, priceValue);
		
		priceValue = addNotTaxedPrices(sqlSession, hasPrice, priceValue);
		// new version of Fees from Fee table (Streamline)
		priceValue = FeeCalculationHelper.addNotTaxableFees(sqlSession, hasPrice, priceValue);
		
		// Damage insurance and cleaning fee adding
		if(product.getSecuritydeposit() != null) {
			Double depositValue = product.getSecuritydeposit();
			priceValue.addTotalQuote(depositValue);
			priceValue.addTotalExtra(depositValue);
		}
		if(product.getCleaningfee() != null) {
			Double feeValue = product.getCleaningfee();
			priceValue.addTotalQuote(feeValue);
			priceValue.addTotalExtra(feeValue);
		}
		
		// add special mandatory Fees from Fee table (Streamline, ...)
		priceValue = FeeCalculationHelper.addSpecialFees(sqlSession, hasPrice, priceValue);
		
		if (commissionUtil.isNetRate() && commissionUtil.isBPFundsHolder()){
		    
            	    commissionUtil.setExtraAmount(priceValue.getTotExtra());
            	    
            	    //Double newPublishedQuoteAmount = priceValue.getTotQuote() - priceValue.getTotExtra();
            	    Double newPublishedQuoteCCFee = commissionUtil.getCreditCardFeeValue(newPublishedPrice);
            
            	    priceValue.addTotalQuote(newPublishedQuoteCCFee);
                       	   
		}

		LOG.debug("computePrice getQuotedetail " + hasPrice.getQuotedetail());
		hasPrice.setPrice(priceValue.getTotPrice());
		hasPrice.setQuote(priceValue.getTotQuote());
		hasPrice.setExtra(priceValue.getTotExtra());
		
		if(propertyManagerInfo != null &&
				propertyManagerInfo.getAdditionalCommission() != null && 
					propertyManagerInfo.getFundsHolder() == ManagerToGateway.PROPERTY_MANAGER_HOLDER) {
			PriceUtil.addCommission(hasPrice, propertyManagerInfo.getAdditionalCommission() / 100.);
		}
		
		MonitorService.monitor("computePrice", timestamp);
		
		if(currency != null) { hasPrice = PriceUtil.convertCurrency(sqlSession, hasPrice, currency); }
	}

	private static PriceComplexValue addTotalPrice(SqlSession sqlSession, HasPrice hasprice, PriceComplexValue priceValue) {
		double totMinimum = 0.0;
		hasMinimum = true;
		
		final Price example = new Price();
		example.setPartyid(hasprice.getSupplierid());
		example.setEntitytype(NameId.Type.Product.name());
		example.setEntityid(hasprice.getProductid());
		example.setDate(Time.addDuration(hasprice.getTodate(), -1.0, Time.DAY));
		example.setQuantity(hasprice.getDuration(Time.DAY));
		example.setUnit(hasprice.getUnit());		
				
		int startDay = Time.getDay(hasprice.getFromdate());
		int endDay = Time.getDay(hasprice.getTodate());
		int i = 0;
		while (i++ < 1000) {
			example.setDate(Time.getDate(endDay - 1));
			example.setDateStr(DF.format(example.getDate()));
			
			Price price = sqlSession.getMapper(PriceMapper.class).readbydate(example);
		
			if (hasprice == null || price == null || price.getDate().after(price.getTodate())) {
				hasprice.setPrice(0.0);
				hasprice.setQuote(0.0);
				hasprice.setExtra(0.0);
				hasprice.setQuotedetail(new ArrayList<Price>());
				return null;
			}
			hasprice.setCurrency(price.getCurrency());
			int priceDay = Time.getDay(price.getDate());
			totMinimum = (totMinimum < price.getMinimum()) ? price.getMinimum() : totMinimum;
			
			Yield yield = new Yield(NameId.Type.Product.name(), hasprice.getProductid(), hasprice.getFromdate(), hasprice.getTodate());
			ArrayList<Yield> rules = sqlSession.getMapper(YieldMapper.class).listbyentity(yield);
			int duration = (startDay > priceDay) ? endDay - startDay : endDay - priceDay; 
			double value = (endDay <= startDay) ? price.getValue() : price.getValue() * duration;
			priceValue.addTotalPrice(value);
			
			// skip the rules for booking.com like channels
			if(BPThreadLocal.get() != null && BPThreadLocal.get()) {
				// do not apply the rules
				priceValue.setTotQuote(priceValue.getTotPrice());
			}
			else {
				// apply yield management rules for each day!
				Integer occupancy = 0;
				if (hasRule(rules, Yield.OCCUPANCY_ABOVE) || hasRule(rules, Yield.OCCUPANCY_BELOW)) {
					occupancy = sqlSession.getMapper(ReservationMapper.class).occupancy(yield);
					occupancy = (occupancy == null) ? 0 : occupancy;
				}
	
				boolean gap = false;
				if (hasRule(rules, Yield.GAP_FILLER)) {
					Integer previous = sqlSession.getMapper(ReservationMapper.class).previous(yield);
					Integer next = sqlSession.getMapper(ReservationMapper.class).next(yield);
					gap = (previous != null && previous == 1 && next != null && next == 1);
				}
				
				for (int day = (startDay > priceDay) ? startDay : priceDay; day < endDay; day++) {
					Double yieldValue = getYieldValue(rules, Time.getDate(day), gap, hasprice.getDuration(Time.DAY).intValue(), occupancy, price.getValue(), hasprice);
					priceValue.addTotalQuote(yieldValue);
				}
			}
			
			ReservationUtil.addQuotedetail(hasprice, price.getId(), price.getName(), Price.RATE, price.getSupplierid(), Double.valueOf(duration), price.getUnit(), (hasMinimum && value < totMinimum) ? totMinimum : value, price.getDate(), price.getTodate());

			if (priceDay <= startDay) {	break;}
			else {endDay = priceDay;}
		}
		
		priceValue.setTotPrice((hasMinimum && priceValue.getTotPrice() < totMinimum) ? totMinimum : priceValue.getTotPrice());
		
		if (hasMinimum && priceValue.getTotQuote() < totMinimum) {
			priceValue.setTotQuote(totMinimum);
			ArrayList<Price> prices = new ArrayList<Price>(hasprice.getQuotedetail());
			for (Price price : prices) {
				if (price.hasType(Price.YIELD)) {hasprice.getQuotedetail().remove(price);}
			}
		}
		return priceValue;
	}

	/**
	 *  Add feature prices to total and extra price.
	 * @param sqlSession
	 * @param hasprice
	 * @param priceValue
	 * @param quoteDetails
	 * @return
	 */
	private static PriceComplexValue addPriceFeatures(SqlSession sqlSession, HasPrice hasprice, PriceComplexValue priceValue) {
		
		// Mandatory per stay prices
		Price action = new Price();
		action.setEntitytype(NameId.Type.Mandatory.name());
		action.setEntityid(hasprice.getProductid());
		action.setDate(hasprice.getFromdate());
		action.setOrderby(Price.ID);

		ArrayList<Price> features = sqlSession.getMapper(PriceMapper.class).entityfeature(action);
		if (features != null && !features.isEmpty()) {
			for (Price feature : features) {
				if (feature.isTaxable()){
					Double value = feature.getValue();
					priceValue.addTotalQuote(value);
					priceValue.addTotalExtra(value);
					ReservationUtil.addQuotedetail(hasprice, feature.getId(), feature.getName(), Price.MANDATORY, feature.getSupplierid(), 1.0, feature.getUnit(), value, feature.getDate(), feature.getTodate());
					}
				}					
			}

		// Mandatory per day prices
		action.setEntitytype(NameId.Type.MandatoryPerDay.name());
		
		features = sqlSession.getMapper(PriceMapper.class).entityfeature(action);
		if (features != null && !features.isEmpty()) {
			for (Price feature : features) {
				Double quantity = hasprice.getDuration(Time.DAY);
				Double value = feature.getValue() * quantity;				
				priceValue.addTotalQuote(value);
				priceValue.addTotalExtra(value);
				ReservationUtil.addQuotedetail(hasprice, feature.getId(), feature.getName(), NameId.Type.MandatoryPerDay.name(), feature.getSupplierid(), quantity, feature.getUnit(), value, feature.getDate(), feature.getTodate());
				}	
			}

		// Optional features
		features = sqlSession.getMapper(PriceMapper.class).quotedetail(hasprice.getReservationid());
		if (features != null && !features.isEmpty()) {
			for (Price feature : features) {
				if (feature.isOptional()) {
					Double value = feature.getValue();
					priceValue.addTotalQuote(value); 
					priceValue.addTotalExtra(value); 
					ReservationUtil.addQuotedetail(hasprice, feature.getId(), feature.getName(), Price.OPTIONAL, feature.getPartyname(), 1.0, feature.getUnit(), value, feature.getDate(), feature.getTodate());
					}
				}
			}
		
		return priceValue;
	}

	/**
	 * Add price values which are not taxable.
	 * @param sqlSession
	 * @param hasprice
	 * @param priceValue
	 * @param quoteDetails
	 * @return
	 */
	private static PriceComplexValue addNotTaxedPrices(SqlSession sqlSession, HasPrice hasprice, PriceComplexValue priceValue) {
		
		// Example price to get not taxable prices
		final Price example = new Price();
		example.setPartyid(hasprice.getSupplierid());
		example.setEntitytype(NameId.Type.Mandatory.name());
		example.setEntityid(hasprice.getProductid());
		example.setType(Price.NOT_TAXABLE);
		example.setDate(hasprice.getFromdate());
		example.setDateStr(DF.format(example.getDate()));
		example.setTodate(hasprice.getTodate());
		/*example.setQuantity(hasprice.getDuration(Time.DAY));
		example.setUnit(hasprice.getUnit());*/
				
		ArrayList<Price> prices = sqlSession.getMapper(PriceMapper.class).readbytype(example);
		
		if (prices != null){
			for (Price price : prices) {
				if (price.getValue() != null){
					priceValue.addTotalQuote(price.getValue());	
					priceValue.addTotalExtra(price.getValue());
					// Add price to reservation price history.
					ReservationUtil.addQuotedetail(hasprice, price.getId(), price.getName(), Price.NOT_TAXABLE, price.getPartyname(), 1.0, Unit.EA, price.getValue(), price.getDate(), price.getTodate());
					}
				}
			}
		
		return priceValue;
	}
	
	private static PriceComplexValue addPriceTaxes(SqlSession sqlSession, HasPrice hasprice, PriceComplexValue priceValue) {
		
		Price action = new Price();
		action.setEntitytype(NameId.Type.Mandatory.name());
		action.setEntityid(hasprice.getProductid());
		action.setDate(hasprice.getFromdate());
		action.setOrderby(Price.ID);
		
		//Tax included in price
		action.setType(Tax.Type.SalesTaxIncluded.name());
		ArrayList<Tax> taxes = sqlSession.getMapper(TaxMapper.class).taxdetail(action);
		if (taxes != null && !taxes.isEmpty()) {
			Double tottaxValue = 0.0;
			for (Tax tax : taxes) {
				ReservationUtil.addQuotedetail(hasprice, tax.getId(), tax.getName(), Price.TAX_INCLUDED, tax.getPartyname(), 1.0, Unit.EA, tax.getTaxIncluded(priceValue.getTotQuote()), null, null);
				tottaxValue -= tax.getTaxIncluded(priceValue.getTotQuote()); 
				}
			ReservationUtil.addQuotedetail(hasprice, Model.ZERO, Price.INCLUDED, Price.TAX_INCLUDED, "", 1.0, Unit.EA, tottaxValue, null, null);
			}
		
		//Tax excluded from price
		action.setType(Tax.Type.SalesTaxExcluded.name());
		taxes = sqlSession.getMapper(TaxMapper.class).taxdetail(action);
		if (taxes != null && !taxes.isEmpty()) {
			double totRate = priceValue.getTotQuote(); 
			for (Tax tax : taxes) {
				Double value = tax.getTaxExcluded(totRate);
				priceValue.addTotalQuote(value); 
				priceValue.addTotalExtra(value);
				ReservationUtil.addQuotedetail(hasprice, tax.getId(), tax.getName(), Price.TAX_EXCLUDED, tax.getPartyname(), 1.0, Unit.EA, value, null, null);
				}
			}
		
		//Tax on price and previous taxes
		action.setType(Tax.Type.SalesTaxOnTax.name());
		taxes = sqlSession.getMapper(TaxMapper.class).taxdetail(action);
		if (taxes != null && !taxes.isEmpty()) {
			for (Tax tax : taxes) {
				Double value = tax.getTaxExcluded(priceValue.getTotQuote());
				priceValue.addTotalQuote(value); 
				priceValue.addTotalExtra(value); 				
				ReservationUtil.addQuotedetail(hasprice, tax.getId(), tax.getName(), Price.TAX_ON_TAX, tax.getPartyname(), 1.0, Unit.EA, value, null, null);
				}
			}
		
		return priceValue;
	}

	
	/**
	 * Returns negative value in case of it is needed to make a refund and positive in case of surcharge
	 * 
	 * @param sqlSession current SQL session
	 * @param reservation reservation to cancel
	 * @param propertyManagerInfo property manager that obtains the booking to be cancelled
	 * @return value to charge or refund
	 */
	public static double calculateCancellationAmount(SqlSession sqlSession, Reservation reservation, PropertyManagerInfo propertyManagerInfo) {
		
		List<PaymentTransaction> paymentTransactions = sqlSession.getMapper(PaymentTransactionMapper.class).readByReservationId(Integer.valueOf(reservation.getId()));
		if(paymentTransactions == null || paymentTransactions.isEmpty()) {
			throw new ServiceException(Error.database_cannot_find, "payment transactions for reservation");
		}
		
		double chargedAmountToManager = getChargedAmountToManager(paymentTransactions);
		String transactionCurrency = paymentTransactions.get(0).getCurrency();
		Double reservationQuoteTransactionCurrency = PaymentService.convertCurrency(sqlSession, reservation.getCurrency(), transactionCurrency, reservation.getQuote());
		double amountToManager = 0;

		PropertyManagerCancellationRule cancellationRule = PaymentHelper.getRuleForCurrentDate(sqlSession, reservation, propertyManagerInfo.getPropertyManagerId());

		if(cancellationRule != null) {
			double cancellationTransactionFee = cancellationRule.getCancellationTransactionFee() == null ? 0 : cancellationRule.getCancellationTransactionFee();
			double amountToRenter = PaymentHelper.getCancellationAmountWithoutFee(reservation, cancellationRule, transactionCurrency);
			amountToManager = cancellationTransactionFee + reservationQuoteTransactionCurrency - amountToRenter - chargedAmountToManager;
		} else if(chargedAmountToManager != reservationQuoteTransactionCurrency) {
			amountToManager = reservationQuoteTransactionCurrency - chargedAmountToManager;
		}
		return amountToManager;
	}
	
	public static void createEvent(SqlSession sqlSession, PaymentTransaction paymentTransaction, Reservation reservation, CreditCard creditCard) {
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
//		Reservation oldReservation = sqlSession.getMapper(ReservationMapper.class).read(reservation.getId());
//		if(reservation.getState().equals(Reservation.State.Provisional.name()) ||  !oldReservation.getState().equals(reservation.getState())) { // FIXME : hardcode
		SharedService.onReservationStateChange(sqlSession, reservation, product);
//		}
		if(paymentTransaction != null && (reservation.getState().equals(Reservation.State.Confirmed.name()) || reservation.getState().equals(Reservation.State.FullyPaid.name()))) {
			Double amount = paymentTransaction.getTotalAmount();
			String cardholder = creditCard.getLastName() + ", " + creditCard.getFirstName();
			String notes = getNotes(paymentTransaction);
			SharedService.cardReceipt(sqlSession, reservation, notes, cardholder, amount);
		}
	}
	
	public static void createEvent(SqlSession sqlSession, PaymentTransaction paymentTransaction, Reservation reservation, String cardholderName) {
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
//		Reservation oldReservation = sqlSession.getMapper(ReservationMapper.class).read(reservation.getId());
//		if(reservation.getState().equals(Reservation.State.Provisional.name()) ||  !oldReservation.getState().equals(reservation.getState())) { // FIXME : hardcode
		SharedService.onReservationStateChange(sqlSession, reservation, product);
//		}
		if(paymentTransaction != null && (reservation.getState().equals(Reservation.State.Confirmed.name()) || reservation.getState().equals(Reservation.State.FullyPaid.name()))) {
			Double amount = paymentTransaction.getTotalAmount();
			String cardholder = cardholderName;
			String notes = getNotes(paymentTransaction);
			SharedService.cardReceipt(sqlSession, reservation, notes, cardholder, amount);
		}
	}
	
	private static String getNotes(PaymentTransaction paymentTransaction) {
		StringBuilder string = new StringBuilder();
		Integer gatewayId = paymentTransaction.getGatewayId();
		String message = paymentTransaction.getMessage();
		Double finalAmount = paymentTransaction.getFinalAmount();
		Integer partialIin = paymentTransaction.getPartialIin();
		String chargeType = paymentTransaction.getChargeType();
		Integer paymentMethod = paymentTransaction.getPaymentMethod();
		
		if(gatewayId != null && !gatewayId.equals("")) {
			string.append("Gateway ID: " + gatewayId);
		}
		if(message != null && !message.equals("")) {
			string.append("Message: " + message);
		}
		if(finalAmount != null) {
			string.append("Final amount: " + finalAmount);
		}
		if(partialIin != null) {
			string.append("Last four credit card digits: " + partialIin);
		}
		if(chargeType != null && !chargeType.equals("")) {
			string.append("Charge type: " + chargeType);
		}
		if(paymentMethod != null && !paymentMethod.equals("")) {
			string.append("Payment method: " + paymentMethod);
		}
		return string.toString();
	}
	
	private static double getChargedAmountToManager(List<PaymentTransaction> paymentTransactions) {
		double chargedAmountToManager = 0;
		for(PaymentTransaction paymentTransaction : paymentTransactions) {
			chargedAmountToManager += paymentTransaction.getTotalAmount();
		}
		return chargedAmountToManager;
	}
	
	/**
	 * Get the property address. Return address from product table in case it is exists and get the address from location if not.
	 * 
	 * @param sqlSession current SQL session
	 * @param product product to get the location for
	 * @return string of property address
	 */
	public static String getPropertyLocation(SqlSession sqlSession, Product product) {
		String address = Arrays.toString(product.getAddress()).replace("[", "").replace("]", "").trim();
		if(StringUtils.isEmpty(address) || address.equals("null")) {
			Location location = sqlSession.getMapper(LocationMapper.class).read(product.getLocationid());
			if(location == null) {
				LOG.error("There is no location set for property " + product.getId());
				return "";
			}
			Country country = sqlSession.getMapper(CountryMapper.class).read(location.getCountry());
			address = location.getName() + ", " + country.getName();
		}
		return address;
	}

	/**
	 * Checks if the rule name is in the list of yield management rules.
	 *
	 * @param rules the list of rules. 
	 * @param name the rule name.
	 * @return true, if the rule name is in the list of yield management rules.
	 */
	private static boolean hasRule(ArrayList<Yield> rules, String name) {
		for (Yield yield : rules) {
			if (yield.hasName(name)) {return true;}
		}
		return false;
	}

	/**
	 * Gets the price after yield management rules, if any, have been applied to the specified value.
	 * 
	 * @param rules the yield management rules to be applied.
	 * @param date the date for which rules are to be applied.
	 * @param fillsGap is true if the reservation fills a gap precisely.
	 * @param stay the length of stay in nights.
	 * @param occupancy the occupancy percentage.
	 * @param value the price value to be adjusted by the yield management rules.
	 * @return the price after yield management rules have been applied.
	 */
	private static final Double getYieldValue(ArrayList<Yield>rules, Date date, boolean fillsGap, Integer stay, Integer occupancy, Double value, HasPrice hasprice) {
		if (rules == null || rules.isEmpty()) {return value;}
		Date timestamp = new Date();
		Map<String, Yield>yields = new HashMap<String, Yield>();
		for (Yield yield : rules) {
//TODO: CJM			if (yield.getFromdate().after(date) || yield.getTodate().before(date)) {continue;}
			if (yield.isInDateRange(date)) {
				Yield oldyield = null;
//TODO: CJM				if (yield.hasName(Yield.DATE_RANGE) && yield.isInDateRange(date)) {yields.put(Yield.DATE_RANGE, yield);}
				if (yield.hasName(Yield.DATE_RANGE)) {yields.put(Yield.DATE_RANGE, yield);}
				if (yield.hasName(Yield.DAY_OF_WEEK) && yield.isDayOfWeek(date)) {yields.put(Yield.DATE_RANGE, yield);}
				else if (yield.hasName(Yield.EARLY_BIRD) && yield.isEarlyBird(date)) {
					oldyield = yields.get(Yield.EARLY_BIRD);
					if (oldyield == null || oldyield.getParam() > yield.getParam()) {yields.put(Yield.EARLY_BIRD, yield);}
				}
				else if (yield.hasName(Yield.GAP_FILLER) && yield.isGapFiller(fillsGap, stay)) {
					oldyield = yields.get(Yield.GAP_FILLER);
					if (oldyield == null || oldyield.getParam() < yield.getParam()) {yields.put(Yield.GAP_FILLER, yield);}
				}
				else if (yield.hasName(Yield.LAST_MINUTE) && yield.isLastMinute(date)) {
					oldyield = yields.get(Yield.LAST_MINUTE);
					if (oldyield == null || oldyield.getParam() > yield.getParam()) {yields.put(Yield.LAST_MINUTE, yield);}
				}
				else if (yield.hasName(Yield.LENGTH_OF_STAY) && yield.isLengthOfStay(stay)) {
					oldyield = yields.get(Yield.LENGTH_OF_STAY);
					if (oldyield == null || oldyield.getParam() < yield.getParam()) {yields.put(Yield.LENGTH_OF_STAY, yield);}
				}
				else if (yield.hasName(Yield.OCCUPANCY_ABOVE) && yield.isOccupancyAbove(occupancy)) {
					oldyield = yields.get(Yield.OCCUPANCY_ABOVE);
					if (oldyield == null || oldyield.getParam() < yield.getParam()) {yields.put(Yield.OCCUPANCY_ABOVE, yield);}
				}
				else if (yield.hasName(Yield.OCCUPANCY_BELOW) && yield.isOccupancyBelow(occupancy)) {
					oldyield = yields.get(Yield.OCCUPANCY_BELOW);
					if (oldyield == null || oldyield.getParam() > yield.getParam()) {yields.put(Yield.OCCUPANCY_BELOW, yield);}
				}
				else if (yield.hasName(Yield.WEEKEND) && yield.isWeekend(date, yield.getParam())) {
					oldyield = yields.get(Yield.WEEKEND);
					if (oldyield == null || oldyield.getParam() > yield.getParam()) {yields.put(Yield.WEEKEND, yield);}
				}
				//			else if (yield.hasName(Yield.RETURNING_GUEST) && yield.isOccupancyBelow(occupancy)) {
				//				oldyield = yields.get(Yield.RETURNING_GUEST);
				//				if (oldyield == null || oldyield.getParam() > yield.getParam()) {yields.put(Yield.OCCUPANCY_BELOW, yield);}
				//			}
			}
		}
		for (Yield yield : yields.values()) {
			if (yield.isGapFiller()) {hasMinimum = false;}
			Double oldvalue = value;
			value = yield.getValue(value);
			ReservationUtil.addQuotedetail(hasprice, yield.getId(), yield.getName(), Price.YIELD, "", 1.0, Unit.EA, value - oldvalue, null, null);
		}
		MonitorService.monitor("GetYieldValue", timestamp);
		return value;
	}

	/**
	 * Executes the ReservationHistory action to read a Reservation table of previous stays.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Reservation> execute(SqlSession sqlSession, ReservationHistory action) {
		Date timestamp = new Date();
		Table<Reservation> table = new Table<Reservation>();
		//TODO: CJM SLOW QUERY 
		try {table.setValue(sqlSession.getMapper(ReservationMapper.class).listbycustomerid(action));}
		catch (Throwable x) {MonitorService.log(x);}
		MonitorService.monitor("ReservationHistory", timestamp);
		return table;
	}

	/**
	 * Refreshes the special offers, which is invoked periodically by the RazorServer scheduler.
	 * Deletes the existing special list, creates new special list, calculates new special prices.
	 */
	public static final void specialrefresh() { 
		Date timestamp = new Date();
		SqlSession sqlSession = RazorServer.openSession();
		try {
			sqlSession.getMapper(SpecialMapper.class).deleteall();
			ArrayList<Yield> maximumgapfillers = sqlSession.getMapper(YieldMapper.class).maximumgapfillers();
			for (Yield yield : maximumgapfillers) {sqlSession.getMapper(SpecialMapper.class).refresh(yield);}
			ArrayList<Special> specials = sqlSession.getMapper(SpecialMapper.class).readall();
			if (specials == null || specials.isEmpty()) {return;}
			Reservation reservation = new Reservation();
			for (Special special : specials) {
				
				reservation.setOrganizationid(special.getSupplierid());
				reservation.setActorid(Party.NO_ACTOR);
				reservation.setFromdate(special.getStartdate());
				reservation.setTodate(special.getEnddate());
				reservation.setUnit(Unit.DAY);
				reservation.setCurrency(special.getCurrency());
				reservation.setProductid(special.getProductid());

				computePrice(sqlSession, reservation, null);
				Double price = reservation.getPrice();
				Double quote = reservation.getQuote();
				Double extra = reservation.getExtra();
				if (price == null 
						|| quote == null 
						|| price <= 0.01 
						|| quote <= 0.01 
						|| price <= quote 
						|| special.getStartdate().after(special.getEnddate())
				) {sqlSession.getMapper(SpecialMapper.class).delete(special.getId());}
				else {
					special.setPrice(price);
					special.setQuote(quote);
					sqlSession.getMapper(SpecialMapper.class).update(special);
					createQuotedetail(sqlSession, reservation);
				}
			}
			sqlSession.commit();
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		MonitorService.monitor("specialrefresh", timestamp);
	}

	/**
	 * Executes the Available action to get a table of AvailableItem instances from which to create a schedule of availability.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<AvailableItem> execute(SqlSession sqlSession, Available action) {
		Date timestamp = new Date();
		ArrayList<String> productids = sqlSession.getMapper(ReservationMapper.class).availableatposition(action);
		action.setProductids(LicenseService.getLicensed(sqlSession, action.getOrganizationid(), action.getAgentid(), productids, License.Type.Console, License.DEFAULT_WAIT));
		Table<AvailableItem> table = available(sqlSession, action);
		MonitorService.monitor("Available", timestamp);
		return table;
	}

	/**
	 * Executes the AvailableWidget action to get a table of AvailableItem instances from which to create a calendar widget.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<AvailableItem> execute(SqlSession sqlSession, AvailableWidget action) {
		Date timestamp = new Date();
		Table<AvailableItem> table = available(sqlSession, action);
		MonitorService.monitor("AvailableWidget", timestamp);
		return table;
	}

	/*
	 * Gets a table of AvailableItem instances from which to create a schedule of availability.
	 * @see net.cbtltd.client.form.AvailableForm
	 * 
	 * @param sqlSession the current SQL session.
	 * @param action the action that specifies the criteria by which instances are selected.
	 * @return the table of available items.
	 */
	private static final Table<AvailableItem> available(SqlSession sqlSession, Available action) {
		Table<AvailableItem> table = new Table<AvailableItem>();
		try {
			if (action.noProductids()) {return table;}
			ArrayList<AvailableItem> availableitems = sqlSession.getMapper(ReservationMapper.class).availableitems(action);
			childitems(sqlSession, action, availableitems);
			parentitems(sqlSession, action, availableitems);
			Collections.sort(availableitems);
			if (availableitems != null) {
				for (AvailableItem availableitem : availableitems) {setAlert(sqlSession, availableitem, action.getFromdate(), Time.addDuration(action.getFromdate(), 60, Time.DAY));}
			}
			table.setValue(availableitems);
			table.setDatasize(sqlSession.getMapper(ReservationMapper.class).countatposition(action));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/*
	 * Resets the specified table of AvailableItem instances to include the effect of the availability of child properties.
	 * @see net.cbtltd.client.form.AvailableForm
	 * 
	 * @param sqlSession the current SQL session.
	 * @param action the action that specifies the criteria by which instances are selected.
	 * @param the specified table of available items.
	 */
	private static void childitems (SqlSession sqlSession, Available action, ArrayList<AvailableItem> availableitems) {
		ArrayList<String> productids = action.getProductids();
		for (String productid : action.getProductids()) {
			action.setId(productid);
			ArrayList<String> childids = new ArrayList<String>();
			childids.add(productid);
			action.setProductids(childids);
			childids = sqlSession.getMapper(ReservationMapper.class).childids(action);
			if (childids == null || childids.isEmpty()) {continue;}
			action.setProductids(childids);
			ArrayList<AvailableItem> childavailableitems = sqlSession.getMapper(ReservationMapper.class).availableitem(action);
			//LOG.debug("\n\nchilditems " + productid + ", " + childids + " " + action + "\n" + childavailableitems);
			if (childavailableitems != null) {availableitems.addAll(childavailableitems);}
		}
		action.setProductids(productids);
	}

	/*
	 * Resets the specified table of AvailableItem instances to include the effect of the availability of parent properties.
	 * @see net.cbtltd.client.form.AvailableForm
	 * 
	 * @param sqlSession the current SQL session.
	 * @param action the action that specifies the criteria by which instances are selected.
	 * @param the specified table of available items.
	 */
	private static void parentitems (SqlSession sqlSession, Available action, ArrayList<AvailableItem> availableitems) {
		ArrayList<String> productids = action.getProductids();
		for (String productid : productids) {
			ArrayList<String> parentids = new ArrayList<String>();
			parentids.add(productid);
			action.setProductids(parentids);
			action.setId(productid);
			parentids = sqlSession.getMapper(ReservationMapper.class).parentids(action);
			if (parentids == null || parentids.isEmpty()) {continue;}
			action.setProductids(parentids);
			ArrayList<AvailableItem> parentavailableitems = sqlSession.getMapper(ReservationMapper.class).availableitem(action);
			//LOG.debug("\n\nparentitems " + productid + ", " + parentids + " " + action + "\n" + parentavailableitems);
			if (parentavailableitems != null) {availableitems.addAll(parentavailableitems);}
		}
		action.setProductids(productids);
	}

	/**
	 * Executes the ReservationEntities action to read a ReservationEntities instance.
	 * The instance has details of the entities (guest, agent, property) related to the reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response. entities
	 */
	public final ReservationEntities execute(SqlSession sqlSession, ReservationEntities action) {
		Date timestamp = new Date();
		try {
			action.setReservation(sqlSession.getMapper(ReservationMapper.class).read(action.getReservation().getId()));
			if (action.getReservation() == null) {throw new ServiceException(Error.reservation_id, action.getReservation().getId());}
			action.getReservation().setDiscountfactor(getDiscountfactor(sqlSession, action.getReservation()));
			if (action.getReservation().hasProductid()) {action.setProduct(sqlSession.getMapper(ProductMapper.class).read(action.getReservation().getProductid()));}
			if (action.getProduct() != null && action.getProduct().getOwnerid() != null) {action.setOwner(sqlSession.getMapper(PartyMapper.class).read(action.getProduct().getOwnerid()));}
			if (action.getProduct() != null && action.getProduct().getSupplierid() != null) {action.setManager(sqlSession.getMapper(PartyMapper.class).read(action.getProduct().getSupplierid()));}
			if (action.getReservation().hasAgentid()) {action.setAgent(sqlSession.getMapper(PartyMapper.class).read(action.getReservation().getAgentid()));}
			if (action.getReservation().hasCustomerid()) {action.setCustomer(sqlSession.getMapper(PartyMapper.class).read(action.getReservation().getCustomerid()));}
			if (action.getReservation().hasServiceid()) {action.setService(sqlSession.getMapper(PartyMapper.class).read(action.getReservation().getServiceid()));}
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		MonitorService.monitor("ReservationEntities", timestamp);
		return action;
	}

	/**
	 * Executes the BrochureProduct action to read a Brochure instance for a property (product).
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Brochure execute(SqlSession sqlSession, BrochureProduct action) {
		Date timestamp = new Date();
		try {
			String productid = action.getId();
			sqlSession.getMapper(ContactMapper.class).create(action);
			String[] productids = productid.split(",");
			action.setAvailableitems(sqlSession.getMapper(ReservationMapper.class).brochureproduct(productids));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		MonitorService.monitor("BrochureProduct", timestamp);
		return action;
	}

	/**
	 * Executes the BrochureRead action to read a Brochure instance given its ID.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Brochure execute(SqlSession sqlSession, BrochureRead action) {
		Date timestamp = new Date();
		Brochure brochure = action;
		try {
			brochure = sqlSession.getMapper(ContactMapper.class).brochure(action.getId());
			if (brochure != null) {brochure.setAvailableitems(sqlSession.getMapper(ReservationMapper.class).brochureitems(action.getId()));}
		} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		MonitorService.monitor("BrochureRead", timestamp);
		return brochure;
	}

	/**
	 * Executes the BrochureUpdate action to update a Brochure instance and email its URL to the prospective guest.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Brochure execute(SqlSession sqlSession, BrochureUpdate action) {
		Date timestamp = new Date();
		//LOG.debug("BrochureUpdate " + action);
		try {
			sqlSession.getMapper(ContactMapper.class).create(action);
			RelationService.create(sqlSession, Relation.CONTACT_PARTY, action.getId(), action.getCustomerid());

			for (AvailableItem availableitem : action.getAvailableitems()) {
				Reservation reservation = availableitem.getReservation(action.getActorid(), action.getCustomerid(), action.getNotes());
				reservation.setCustomerid(action.getCustomerid());
				reservation.setDuedate(reservation.getDate());
				reservation.setMarket(action.getParentid());
				reservation.setParentid(action.getId());
				reservation.setState(Reservation.State.Initial.name());
				reservation.setName(SessionService.pop(sqlSession, reservation.getOrganizationid(), Serial.RESERVATION));
				sqlSession.getMapper(ReservationMapper.class).create(reservation);
			}

			Party customer = sqlSession.getMapper(PartyMapper.class).read(action.getCustomerid());
			Party organization = sqlSession.getMapper(PartyMapper.class).read(action.getOrganizationid());
			action.setActorname(organization.getName());
			EmailService.brochure(sqlSession, action, customer.getEmailaddress());
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		MonitorService.monitor("BrochureUpdate", timestamp);
		return action;
	}

	/**
	 * Executes the BrochurePrice action to refresh its price.
	 * This occurs when the currency or duration of the prospective stay is changed.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final PriceResponse execute(SqlSession sqlSession, BrochurePrice action) {
		Date timestamp = new Date();
		PriceResponse response = new PriceResponse();
		try {
			resetPrice(sqlSession, action, action.getPriceunit());
			resetCurrency(sqlSession, action, action.getTocurrency());
			response.setValue(action.getPrice());
			response.setQuote(action.getQuote());
			response.setQuotedetail(action.getQuotedetail());
			response.setCost((action.getQuote() - action.getExtra()) * getDiscountfactor(sqlSession, action));
			response.setCurrency(action.getCurrency());
//			response.setCollisions(sqlSession.getMapper(ReservationMapper.class).collisions(action));
//			response.addCollisions(sqlSession.getMapper(ReservationMapper.class).parentcollisions(action));
//			response.addCollisions(sqlSession.getMapper(ReservationMapper.class).childcollisions(action));
			response.setCollisions(getCollisions(sqlSession, action));
			response.setAlerts(getAlerts(sqlSession, action));
		} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		MonitorService.monitor("BrochurePrice", timestamp);
		return response;
	}

	/**
	 * Executes the ReservationTable action to read a list of Reservation instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action action the action to be executed.
	 * @return the response.
	 */
	public final Table<Reservation> execute(SqlSession sqlSession, ReservationTable action) {
		Date timestamp = new Date();
		Table<Reservation> table = new Table<Reservation>();
		try {
			table.setDatasize(sqlSession.getMapper(ReservationMapper.class).count(action));
			table.setValue(sqlSession.getMapper(ReservationMapper.class).list(action)); //CJM ACTION!
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		MonitorService.monitor("ReservationTable", timestamp);
		return table;
	}
	
	
	public final List<ReservationEntities> fetchReservation(SqlSession sqlSession,
			String lastFetch) {
		Date timestamp = new Date();
		List<ReservationEntities> listReservationEntities=new ArrayList<ReservationEntities>();
		try {
			List<Reservation> listReservation=sqlSession.getMapper(ReservationMapper.class).readBasedOnTime(lastFetch);
			
			for(Reservation reservation:listReservation){
				//get ReservationExt
				ReservationExt ext=new ReservationExt();
				ext.setReservationId(reservation.getAltid());
				List<ReservationExt> listReservationExt=sqlSession.getMapper(ReservationExtMapper.class).readReservationExt(ext);
				if(listReservationExt!=null){
					reservation.setListReservationExt(listReservationExt);
				}
				
				//get Product mapped to reservation.
				Product product=sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
				
				reservation.setProduct(product);
				Party customer=sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
				reservation.setCustomer(customer);
				
				ReservationEntities action=new ReservationEntities();
				action.setReservation(reservation);
				
			if (action.getReservation() == null) {
				throw new ServiceException(Error.reservation_id, action
						.getReservation().getId());
			}
			action.getReservation().setDiscountfactor(
					getDiscountfactor(sqlSession, action.getReservation()));
			if (action.getReservation().hasProductid()) {
				action.setProduct(sqlSession.getMapper(ProductMapper.class)
						.read(action.getReservation().getProductid()));
			}
			if (action.getProduct() != null
					&& action.getProduct().getOwnerid() != null) {
				action.setOwner(sqlSession.getMapper(PartyMapper.class).read(
						action.getProduct().getOwnerid()));
			}
			if (action.getProduct() != null
					&& action.getProduct().getSupplierid() != null) {
				action.setManager(sqlSession.getMapper(PartyMapper.class).read(
						action.getProduct().getSupplierid()));
			}
			if (action.getReservation().hasAgentid()) {
				action.setAgent(sqlSession.getMapper(PartyMapper.class).read(
						action.getReservation().getAgentid()));
			}
			if (action.getReservation().hasCustomerid()) {
				action.setCustomer(sqlSession.getMapper(PartyMapper.class)
						.read(action.getReservation().getCustomerid()));
			}
			if (action.getReservation().hasServiceid()) {
				action.setService(sqlSession.getMapper(PartyMapper.class).read(
						action.getReservation().getServiceid()));
			}
			listReservationEntities.add(action);
		}
		} catch (Throwable x) {
			sqlSession.rollback();
			MonitorService.log(x);
		}
		MonitorService.monitor("ReservationEntities", timestamp);
		return listReservationEntities;
	}
}
