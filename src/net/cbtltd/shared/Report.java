/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.shared.api.HasPath;
import net.cbtltd.shared.api.HasTableService;

public class Report
extends Model
implements HasPath, HasTableService {

	public static final String CREATED = "Created";

	public static final String HTML = "html";
	public static final String PDF = "pdf";
	public static final String XLS = "xls";

	public static final String[] FORMATS = {Report.HTML,Report.PDF,Report.XLS};

	public Service service() {return Service.REPORT;}
	
	public static final String REPORTID = "report.id";
	public static final String NAME = "report.name";
	public static final String DATE = "report.date";
	public static final String ACTORID = "report.actorid";
	public static final String NOTES = "report.notes";

	private String design;
	private String actorid;
	private String accountid;
	private String entitytype;
	private String entityid;
	private String process;
	private Date date;
	private String fromname;
	private String toname;
	private Date fromdate;
	private Date todate;
	private String groupby;
	private String currency;
	private String language;
	private String states;
	private String types;
	private String notes;
	private String format = PDF;
	private String orderby;
	private int startrow;
	private int numrows;

	public String getName() {
		return name = (name == null) ? design + new Date() : name;
	}
	
	public String getDesign() {
		return design;
	}

	public void setDesign(String design) {
		this.design = design;
	}

	public boolean noDesign() {
		return design == null || design.trim().isEmpty() || design.equalsIgnoreCase(Model.ZERO);
	}
	
	public String getActorid() {
		return actorid;
	}

	public void setActorid(String actorid) {
		this.actorid = actorid;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String getEntitytype() {
		return entitytype;
	}

	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}

	public String getEntityid() {
		return entityid;
	}

	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getFormat() {
		return format == null ? null : format.toLowerCase();
	}

	public void setFormat(String format) {
		this.format = format.toLowerCase();
	}

	public boolean hasFormat(String format) {
		return this.format != null && this.format.equalsIgnoreCase(format);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFromname() {
		return fromname;
	}

	public void setFromname(String fromname) {
		this.fromname = fromname;
	}

	public String getToname() {
		return toname;
	}

	public void setToname(String toname) {
		this.toname = toname;
	}

	public boolean noNameRange(){
		return 
		fromname == null 
		|| fromname.isEmpty() 
		|| toname == null 
		|| toname.isEmpty() 
		|| fromname.compareTo(toname) > 0;
	}

	public boolean hasNameRange(){
		return !noNameRange();
	}

	public void setFromtoname(String name) {
		setFromname(name);
		setToname(name);
	}

	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public boolean noDateRange(){
		return fromdate == null || todate == null || !fromdate.before(todate);
	}

	public boolean hasDateRange(){
		return !noDateRange();
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean noCurrency(){
		return currency == null || currency.isEmpty();
	}

	public boolean hasCurrency(){
		return !noCurrency();
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getStates() {
		return noStates() ? NameId.EMPTY_LIST : states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public void setStatelist(ArrayList<String> states) {
		this.states = NameId.getCdlist(states);
	}

	public boolean noStates() {
		return states == null || states.isEmpty() || states.equalsIgnoreCase(NameId.EMPTY_LIST);
	}
	
	public String getTypes() {
		return noTypes() ? NameId.EMPTY_LIST : types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public void setTypelist(ArrayList<String> types) {
		this.types = NameId.getCdlist(types);
	}

	public boolean noTypes() {
		return types == null || types.isEmpty() || types.equalsIgnoreCase(NameId.EMPTY_LIST);
	}
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getGroupby() {
		return groupby;
	}

	public boolean getGroupbyday() {
		return Time.DAY.name().equalsIgnoreCase(groupby);
	}

	public boolean getGroupbyweek() {
		return Time.WEEK.name().equalsIgnoreCase(groupby);
	}

	public boolean getGroupbymonth() {
		return Time.MONTH.name().equalsIgnoreCase(groupby);
	}

	public boolean getGroupbyquarter() {
		return Time.QUARTER.name().equalsIgnoreCase(groupby);
	}

	public boolean getGroupbyyear() {
		return Time.YEAR.name().equalsIgnoreCase(groupby);
	}

	public void setGroupby(String groupby) {
		this.groupby = groupby;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public boolean noOrderby() {
		return orderby == null || orderby.isEmpty();
	}
	
	public int getStartrow() {
		return startrow;
	}

	public void setStartrow(int startrow) {
		this.startrow = startrow;
	}

	public int getNumrows() {
		return numrows;
	}

	public void setNumrows(int numrows) {
		this.numrows = numrows;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Report [design=");
		builder.append(design);
		builder.append(", actorid=");
		builder.append(actorid);
		builder.append(", accountid=");
		builder.append(accountid);
		builder.append(", entitytype=");
		builder.append(entitytype);
		builder.append(", entityid=");
		builder.append(entityid);
		builder.append(", process=");
		builder.append(process);
		builder.append(", date=");
		builder.append(date);
		builder.append(", fromname=");
		builder.append(fromname);
		builder.append(", toname=");
		builder.append(toname);
		builder.append(", fromdate=");
		builder.append(fromdate);
		builder.append(", todate=");
		builder.append(todate);
		builder.append(", groupby=");
		builder.append(groupby);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", language=");
		builder.append(language);
		builder.append(", states=");
		builder.append(states);
		builder.append(", types=");
		builder.append(types);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", format=");
		builder.append(format);
		builder.append(", orderby=");
		builder.append(orderby);
		builder.append(", startrow=");
		builder.append(startrow);
		builder.append(", numrows=");
		builder.append(numrows);
		builder.append(", organizationid=");
		builder.append(organizationid);
		builder.append(", status=");
		builder.append(status);
		builder.append(", state=");
		builder.append(state);
		builder.append(", values=");
		builder.append(values);
		builder.append(", attributes=");
		builder.append(attributemap);
		builder.append(", texts=");
		builder.append(texts);
		builder.append(", images=");
		builder.append(imageurls);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}
}