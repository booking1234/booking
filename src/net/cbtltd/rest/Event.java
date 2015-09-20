package net.cbtltd.rest;

import java.util.Collection;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.shared.journal.EventJournal;

@XmlRootElement(name = "event")
public class Event {
	public String eventid;
	public String actorid;
	public String eventname;
	public String actorname;
	public String process;
	public String notes;
	public Date date;
	public Collection<Journal> journal;

	public Event(){} //NB HAS NULL ARGUMENT CONSTRUCTOR

	public Event(EventJournal item, Collection<Journal> journal) {
		super();
		this.eventid = item.getId();
		this.actorid = item.getActorid();
		this.eventname = item.getName();
		this.actorname = item.getActorname();
		this.process = item.getProcess();
		this.notes = item.getNotes();
		this.date = item.getDate();
		this.journal = journal;
	}
}
