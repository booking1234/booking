package net.cbtltd.rest;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.shared.journal.EventJournal;

@XmlRootElement(name = "account")
public class Account {

	public String id;
	public String name;
	public String type;
	public Collection<Journal> journal;
	
	public Account(){} //NB HAS NULL ARGUMENT CONSTRUCTOR

	public Account(EventJournal item, Collection<Journal> journal) {
		super();
		this.id = item.getAccountid();
		this.name = item.getAccountname();
		this.type = item.getType();
		this.journal = journal;
	}
}
