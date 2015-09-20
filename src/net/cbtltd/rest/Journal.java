package net.cbtltd.rest;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.shared.journal.EventJournal;

@XmlRootElement(name = "journal")
public class Journal {
	
	public String journalid;
	public String eventid;
	public String organizationid;
	public String accountid;
	public String entitytype;
	public String entityid;
	public Double quantity;
	public String unit;
	public String currency;
	public Double debitamount;
	public Double creditamount;
	public String description;
	public String eventname;
	public String entityname;
	public String accountname;
	public String process;
    public Date date;
  
	public Journal(){} //NB HAS NULL ARGUMENT CONSTRUCTOR

	public Journal(EventJournal item) {
		super();
		this.journalid = item.getActionid();
		this.eventid = item.getId();
		this.organizationid = item.getOrganizationid();
		this.accountid = item.getAccountid();
		this.entitytype = item.getEntitytype();
		this.entityid = item.getEntityid();
		this.quantity = item.getQuantity();
		this.unit = item.getUnit();
		this.currency = item.getCurrency();
		this.debitamount = item.getDebitamount();
		this.creditamount = item.getCreditamount();
		this.description = item.getDescription();
		this.eventname = item.getName();
		this.entityname = item.getEntityname();
		this.accountname = item.getAccountname();
		this.process = item.getProcess();
		this.date = item.getDate();
	}
}
