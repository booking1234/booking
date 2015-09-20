/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import net.cbtltd.server.api.EventMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.JournalMapper;
import net.cbtltd.server.api.TaskMapper;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.DoubleResponse;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.Journal;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Serial;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.Task;
import net.cbtltd.shared.journal.EventJournal;
import net.cbtltd.shared.journal.EventJournalBalance;
import net.cbtltd.shared.journal.EventJournalQuantity;
import net.cbtltd.shared.journal.EventJournalTable;
import net.cbtltd.shared.journal.JournalCreate;
import net.cbtltd.shared.journal.JournalCurrency;
import net.cbtltd.shared.journal.JournalDelete;
import net.cbtltd.shared.journal.JournalExportCSV;
import net.cbtltd.shared.journal.JournalExportSQL;
import net.cbtltd.shared.journal.JournalRead;
import net.cbtltd.shared.journal.JournalState;
import net.cbtltd.shared.journal.JournalTaskUpdate;
import net.cbtltd.shared.journal.JournalType;
import net.cbtltd.shared.journal.JournalUpdate;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** The Class JournalService responds to journal requests. */
public class JournalService
implements IsService {

	private static final Logger LOG = Logger.getLogger(JournalService.class.getName());
	private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd"); //MySQL format
	private static JournalService service;

	/**
	 * Gets the single instance of JournalService to manage Journal events.
	 * @see net.cbtltd.shared.Event
	 * @see net.cbtltd.shared.Journal
	 *
	 * @return single instance of JournalService
	 */
	public static synchronized JournalService getInstance() {
		if (service == null) {service = new JournalService();}
		return service;
	}

	/**
	 * Executes the JournalCreate action to create an Event<Journal> instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	protected Event<Journal> execute(SqlSession sqlSession, JournalCreate action) {
		LOG.debug("JournalCreate in " + action);
		try {
			action.setName(SessionService.pop(sqlSession, action.getOrganizationid(), Serial.JOURNAL));
			action.clearItems();
			sqlSession.getMapper(EventMapper.class).create(action);
		}
		catch(Exception x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("JournalCreate out " + action);
		return action;
	}

	/**
	 * Executes the JournalRead action to read an Event<Journal> instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	protected Event<Journal> execute(SqlSession sqlSession, JournalRead action) {
		Event<Journal> event = null;
		try {
			event = sqlSession.getMapper(EventMapper.class).read(action.getId());
			if (event != null) {event.setItems(sqlSession.getMapper(JournalMapper.class).read(action.getId()));} // read items of event in current state
		}
		catch(Exception x) {sqlSession.rollback(); MonitorService.log(x);}
		return event;
	}

	/**
	 * Reads an Event<Journal> instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public static Event<Journal> journalRead(SqlSession sqlSession, String eventid) {
		Event<Journal> event = null;
		try {
			event = sqlSession.getMapper(EventMapper.class).read(eventid);
			if (event != null) {event.setItems(sqlSession.getMapper(JournalMapper.class).read(eventid));} // read items of event in current state
		}
		catch(Exception x) {sqlSession.rollback(); MonitorService.log(x);}
		return event;
	}

	/**
	 * Executes the JournalTaskUpdate action to update a task's Event<Journal> instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	protected final Event<Journal> execute(SqlSession sqlSession, JournalTaskUpdate action) {
		String taskid = action.getTaskid();
		if (taskid != null) {
			Task task = sqlSession.getMapper(TaskMapper.class).read(taskid);
			task.setState(action.getTaskstate());
			sqlSession.getMapper(TaskMapper.class).update(task);
		}
		return journalUpdate(sqlSession, action);
	}

	/**
	 * Executes the JournalUpdate action to update an Event<Journal> instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Event<Journal> execute(SqlSession sqlSession, JournalUpdate action) {
		return journalUpdate(sqlSession, action);
	}

	/**
	 * Updates the specified Event<Journal> instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public static final Event<Journal> journalUpdate (SqlSession sqlSession, Event<Journal> action) {
		LOG.debug("journalUpdate in " + action);
		try {
			if (action.hasId()) {sqlSession.getMapper(EventMapper.class).update(action);}
			else {
				action.setName(SessionService.pop(sqlSession, action.getOrganizationid(), Serial.JOURNAL));
				sqlSession.getMapper(EventMapper.class).create(action);
			}
			for (Journal item : action.getItems()) {
				item.setEventid(action.getId());
				if (item.noId()) {sqlSession.getMapper(JournalMapper.class).create(item);}
				else {sqlSession.getMapper(JournalMapper.class).update(item);}
			}
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Event, action);
		}
		catch(Exception x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("journalUpdate out " + action);
		return action;
	}

	/**
	 * Executes the JournalDelete action to delete an Event<Journal> instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	protected Event<Journal> execute(SqlSession sqlSession, JournalDelete action) {
		try {
			action.setState(Event.FINAL);
			action.setDone();
			sqlSession.getMapper(EventMapper.class).update(action);
		}
		catch(Exception x) {sqlSession.rollback(); MonitorService.log(x);}
		return null;
	}

	/**
	 * Executes the NameIdAction action to read a list of journal event NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	protected Table<NameId> execute(SqlSession sqlSession, NameIdAction action) {
		LOG.debug("NameIdAction " + action);
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(EventMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(EventMapper.class).nameidbyname(action));}
		}
		catch(Exception x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("NameIdAction " + table);
		return table;
	}

	/**
	 * Executes the JournalCurrency action to read a list of journal currency NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, JournalCurrency action) {
		Table<NameId> response = new Table<NameId>();
		try {response.setValue(sqlSession.getMapper(JournalMapper.class).currencynameid(action));}
		catch(Exception x) {sqlSession.rollback(); MonitorService.log(x);}
		return response;
	}

	/**
	 * Executes the JournalState action to read a list of journal event state NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, JournalState action) {
		Table<NameId> response = new Table<NameId>();
		try {response.setValue(sqlSession.getMapper(JournalMapper.class).statenameid(action));}
		catch(Exception x) {sqlSession.rollback(); MonitorService.log(x);}
		return response;
	}

	/**
	 * Executes the JournalType action to read a list of journal event type NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, JournalType action) {
		Table<NameId> response = new Table<NameId>();
		try {response.setValue(sqlSession.getMapper(JournalMapper.class).typenameid(action));}
		catch(Exception x) {sqlSession.rollback(); MonitorService.log(x);}
		return response;
	}

	/**
	 * Executes the EventJournalTable action to read a list of EventJournal instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<EventJournal> execute(SqlSession sqlSession, EventJournalTable action) {
		LOG.debug(action);
		Table<EventJournal> table = new Table<EventJournal>();
		try {
			table.setDatasize(sqlSession.getMapper(EventMapper.class).countbyentity(action));
			table.setValue(sqlSession.getMapper(EventMapper.class).listbyentity(action));
		}
		catch(Exception x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug(table);
		return table;
	}
	
	/**
	 * Executes the EventJournalBalance action to get the financial balance of an entity.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final DoubleResponse execute(SqlSession sqlSession, EventJournalBalance action) {
		DoubleResponse response = new DoubleResponse();
		response.setValue(sqlSession.getMapper(EventMapper.class).balancebyentity(action));
		return response;
	}

	/**
	 * Executes the EventJournalQuantity action to get the total quantity of an entity.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final DoubleResponse execute(SqlSession sqlSession, EventJournalQuantity action) {
		DoubleResponse response = new DoubleResponse();
		response.setValue(sqlSession.getMapper(EventMapper.class).quantitybyentity(action));
		return response;
	}

	/**
	 * Executes the JournalExport action to export a CSV list of journal events.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Event execute(SqlSession sqlSession, JournalExportCSV action) {
		Event<Journal> event = null;
		try {
			event = sqlSession.getMapper(EventMapper.class).read(action.getId());
			if (event != null) {
				event.setItems(sqlSession.getMapper(JournalMapper.class).read(action.getId()));
				PrintWriter outputStream = null;
				outputStream = new PrintWriter(new FileWriter(action.getFilename()));
				outputStream.println(event.toString());
				outputStream.close();
			}
		}
		catch(Exception x) {sqlSession.rollback(); MonitorService.log(x);}
		return event;
	}
	
	/**
	 * Executes the JournalExport action to export an SQL list of journal events.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Event execute(SqlSession sqlSession, JournalExportSQL action) {
		Event<Journal> event = null;
		try {
			event = sqlSession.getMapper(EventMapper.class).read(action.getId());
			if (event != null) {
				event.setItems(sqlSession.getMapper(JournalMapper.class).read(action.getId()));
				StringBuilder sb = new StringBuilder();
				sb.append("INSERT INTO event (ID,OrganizationID,ActorID,ParentID,Name,State,Activity,Process,Type,Date,DueDate,DoneDate,Notes) VALUES ");
				sb.append(event.getId() + "," + event.getOrganizationid() + "," + event.getActorid() + "," + event.getParentid() + "," + event.getName() + "," + 
						event.getState() + "," + event.getActivity() + "," + event.getProcess() + "," + event.getType() + "," + DF.format(event.getDate()) + "," + 
						DF.format(event.getDuedate()) + "," + DF.format(event.getDonedate()) + "," + event.getNotes());
				sb.append("INSERT INTO journal (ID,EventID,MatchID,AccountID,OrganizationID,LocationID,EntityType,EntityID,Quantity,Unit,Unitprice,CreditAmount,DebitAmount,Currency,Description) VALUES ");
				for (Journal journal : event.getItems()) {
					sb.append(journal.getId() + "," + journal.getEventid() + "," + journal.getMatchid() + "," + journal.getAccountid() + "," + 
							journal.getOrganizationid() + "," + journal.getLocationid() + "," + journal.getEntitytype() + "," + journal.getEntityid() + "," + 
							journal.getQuantity() + "," + journal.getUnit() + "," + journal.getUnitprice() + "," + journal.getCreditamount() + "," + 
							journal.getDebitamount() + "," + journal.getCurrency() + "," + journal.getDescription() + ",");
				}
				sb.deleteCharAt(sb.length() - 1);
				final PrintWriter outputStream = new PrintWriter(new FileWriter(action.getFilename()));
				outputStream.println(sb.toString());
				outputStream.close();
			}
		}
		catch(Exception x) {sqlSession.rollback(); MonitorService.log(x);}
		return event;
	}
}