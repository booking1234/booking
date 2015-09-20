/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.shared.Event;
import net.cbtltd.shared.Journal;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Rate;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Task;
import net.cbtltd.shared.account.AccountAction;
import net.cbtltd.shared.api.HasItem;
import net.cbtltd.shared.journal.EventJournal;
import net.cbtltd.shared.journal.EventJournalTable;

public interface EventMapper<T extends HasItem> {
	
	void create(Event<T> event);
	//Event<T> exists(Event<T> event);
	Event<T> read(String id);
	void update(Event<T> event);
	void delete(String id);

	void deleteevent(Event<T> event);
	void deleteitem(Event<T> event);
	void deleterelation(Event<T> event);
	
	ArrayList<net.cbtltd.json.journal.JournalWidgetItem> journalwidget (AccountAction action);
	ArrayList<EventJournal> listbyaccount (AccountAction action);
	ArrayList<EventJournal> listbyevent (AccountAction action);
	void downloaded(String eventid);
	
	Double balancebyeventid(String eventid);
	Integer countbyeventid(String eventid);
	ArrayList<Journal> listbyeventid(String eventid);
	
	Double quantitybyentity(EventJournal entity);
	Double balancebyentity(EventJournal entity);
	Integer countbyentity(EventJournal entity);
	ArrayList<EventJournal> listbyentity(EventJournalTable entity);

	Double balancebyreservation(String reservationid);
	Integer countbyreservation(String reservationid);
	ArrayList<EventJournal> listbyreservation (String reservationid);

	Double balancebytask(Task task);
	Integer countbytask(Task task);
	ArrayList<EventJournal> listbytask (Task task);

	Event<Rate> ratebyparent(Rate rate); //RateByParent
	String flipkeyexists(String eventid); //Flipkey Rating
	
	ArrayList<NameId> nameidbyid (NameIdAction action);
	ArrayList<NameId> nameidbyname (NameIdAction action);
	ArrayList<NameId> currency (NameIdAction action);
	ArrayList<NameId> state (NameIdAction action);
	ArrayList<NameId> type (NameIdAction action);
	ArrayList<NameId> eventtype (NameIdAction action);
	ArrayList<NameId> location (NameIdAction action);
	ArrayList<NameId> subledger (NameIdAction action);
	ArrayList<NameId> product (NameIdAction action);
	
}