/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.cbtltd.shared.api.HasAlert;
import net.cbtltd.shared.api.HasCollision;
import net.cbtltd.shared.api.HasData;
import net.cbtltd.shared.api.HasPrice;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

@XmlRootElement(name = "reservation")
@Description(value = "Reservation of a property between two dates", target = DocTarget.RESPONSE)
public class Reservation
extends Process
implements HasAlert, HasData, HasCollision, HasPrice {

	public enum State {Initial, Provisional, Reserved, Confirmed, FullyPaid, Briefed, Arrived, PreDeparture, Departed, Closed, Cancelled, Final, Inquiry};
	public static final String[] STATES = { State.Initial.name(), State.Provisional.name(), State.Reserved.name(), State.Confirmed.name(), State.FullyPaid.name(), 
		State.Briefed.name(), State.Arrived.name(), State.PreDeparture.name(), State.Departed.name(), State.Closed.name(), State.Cancelled.name(), State.Final.name(), State.Inquiry.name() };
	private static final String[] ACTIVE_STATES = {State.Provisional.name(), State.Reserved.name(), State.Confirmed.name(), State.FullyPaid.name(), 
		State.Briefed.name(), State.Arrived.name()};
	public static final String[] WORKFLOW_STATES = { State.Reserved.name(), State.Confirmed.name(), State.FullyPaid.name(), 
		State.Briefed.name(), State.Arrived.name(), State.PreDeparture.name(), State.Departed.name()};

	public static final String ARRIVALTIME = "14:00:00";
	public static final String DEPARTURETIME = "10:00:00";
	public static final String SERVICEFROM = "10:00:00";
	public static final String SERVICETO = "18:00:00";

	//Sortable columns
	public static final String NAME = "reservation.name"; 
	public static final String DATE = "reservation.date";
	public static final String FROMDATE = "reservation.fromdate";
	public static final String TODATE = "reservation.todate";
	public static final String ACTORNAME = "reservation.actorname";
	public static final String PROCESS = "reservation.process";
	public static final String CUSTOMERNAME = "party.name";
	public static final String PRODUCTNAME = "product.name";
	
	private List<ReservationExt> listReservationExt=new ArrayList<ReservationExt>();

	private String customerid;
	private String productid;
	private String agentid;
	private String serviceid;
	private String financeid;
	private String contractid;
	private String oldstate;
	private String duestate;
	private String flat;
	private String market;
	private String outcome;
	private String servicepayer;
	private String arrivaltime;
	private String departuretime;
	private String servicefrom;
	private String serviceto;
	private Date fromdate;
	private Date todate;
	private Double nightlyrate;
	private Double price;
	private Double quote;
	private Double extra;
	private Double cost;
	private Double discountfactor;
	private Double taxrate;
	private Double deposit;
	private Integer adult;
	private Integer child;
	private Integer infant;
	private Integer quantity;
	private String unit;
	private String currency;
	private String emailaddress;
	private String phoneNumber;
	private String agentname;
	private String customername;
	private String suppliername;
	private String productname;
	private String alert;
	private String terms;
	private String confirmationId;
	
	private String cardholder;
	private String cardnumber;
	private String cardmonth;
	private String cardyear;
	private String cardcode;
	private String cardType;
	
	private String guestName="";
	
	private String city="";
	private String country="";
	private String zip="";
	private String addrress="";
	

	private String message;
	private Boolean priceunit;
	private Boolean termsaccepted;
	private Boolean reverse = false; // go to earlier state

	private Party actor;
	private Party agent;
	private Party customer;
	private Party supplier;
	private Product product;

	private ArrayList<Task> tasks;
	private ArrayList<NameId> collisions;
	private ArrayList<Price> quotedetail;
	
	private static final String format(Date date) {
		return date == null ? null : String.valueOf(date.getYear() + 1900) + "-" + String.valueOf(date.getMonth() + 101).substring(1, 3) + "-" + String.valueOf(date.getDate() + 100).substring(1, 3);
	}

	private static final Date parse (String date) {
		return date == null ? null : new Date(Integer.valueOf(date.substring(0,4)) - 1900, Integer.valueOf(date.substring(5, 7)) - 1, Integer.valueOf(date.substring(8, 10)));
	}

	public Reservation() {}

	public Reservation(String id) {
		this.id = id;
	}

//	public Reservation(
//			String organizationid,
//			String productid,
//			Date fromdate,
//			Date todate,
//			String state
//	) {
//		this.organizationid = organizationid;
//		this.productid = productid;
//		this.fromdate = fromdate;
//		this.todate = todate;
//		this.state = state;
//	}

	public Service service() {return Service.RESERVATION;}

	public boolean isActive () {return hasState(ACTIVE_STATES);}
	
	public boolean notActive () {return !isActive();}
	
	public boolean isValidState () {return hasState(STATES);}
	
	public boolean notValidState () {return !isValidState();}
	
	public boolean isAfterState(String state) {
		return State.valueOf(this.state).ordinal() >= State.valueOf(state).ordinal();
	}

	public String getReservationid() {
		return id;
	}

	public String getSupplierid() {
		return organizationid;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public void setSupplierid(String supplierid) {
		this.organizationid = supplierid;
	}

	public boolean noSupplierid() {
		return organizationid == null || organizationid.isEmpty() || organizationid.equalsIgnoreCase(Model.ZERO);
	}
	
	public String getEntitytype() {
		return NameId.Type.Product.name();
	}
	
	public String getEntityid() {
		return productid;
	}
	
	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public boolean noProductid() {
		return productid == null || productid.isEmpty() || productid.equalsIgnoreCase(Model.ZERO);
	}
	
	public boolean hasProductid() {
		return !noProductid();
	}
	
	@XmlTransient
	public String getFinanceid() {
		return financeid;
	}

	public void setFinanceid(String financeid) {
		this.financeid = financeid;
	}

	public boolean noFinanceid() {
		return financeid == null || financeid.isEmpty()
				|| financeid.equalsIgnoreCase(Model.ZERO);
	}

	public boolean hasFinanceid() {
		return !noFinanceid();
	}

	@XmlTransient
	public String getProcess() {
		return NameId.Type.Reservation.name();
	}

	public void setDate(Date date) {
		super.setDate(date);
	}

	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public boolean noFromdate() {
		return fromdate == null;
	}

	public String getCheckin() {
		return format(fromdate);
	}

	public void setCheckin(String checkin) {
		fromdate = parse(checkin);
	}

	public String getProductFromToDate() {
		return getProductid() + " " + getCheckin() + " " + getCheckout();
	}
	
	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public boolean noTodate() {
		return todate == null;
	}

	public String getCheckout() {
		return format(todate);
	}

	public void setCheckout(String checkout) {
		todate = parse(checkout);
	}

	public Double getDuration(Time unit) {
		return Time.getDuration(fromdate, todate, unit);
	}

	public boolean noDuration(Double duration, Time unit) {
		return noFromdate() || noTodate() || Time.getDuration(fromdate, todate, unit) < duration;
	}

	public Date getWorkflowDate(String datename) {
		if (DATE.equalsIgnoreCase(datename)) {return date;}
		else if (FROMDATE.equalsIgnoreCase(datename)) {return fromdate;}
		else if (TODATE.equalsIgnoreCase(datename)) {return todate;}
		else {return null;}
	}
	
	public String getArrivaltime() {
		return arrivaltime;
	}

	public void setArrivaltime(String arrivaltime) {
		this.arrivaltime = arrivaltime;
	}

	public String getDeparturetime() {
		return departuretime;
	}

	public void setDeparturetime(String departuretime) {
		this.departuretime = departuretime;
	}

	public String getServicefrom() {
		return servicefrom;
	}

	public void setServicefrom(String servicefrom) {
		this.servicefrom = servicefrom;
	}

	public String getServiceto() {
		return serviceto;
	}

	public void setServiceto(String serviceto) {
		this.serviceto = serviceto;
	}

	@XmlTransient
	public void setDiscountfactor(Double discountfactor) {
		this.discountfactor = discountfactor;
	}

	public Double getDiscountfactor() {
		return discountfactor == null ? 1.0 : discountfactor;
	}

	public Double getDiscount() {
		return discountfactor == null ? 0.0 : (1.0 - discountfactor) * 100.0;
	}

	public Double getTaxrate() {
		return taxrate;
	}

	public void setTaxrate(Double taxrate) {
		this.taxrate = taxrate;
	}

	public Double getDeposit() {
		return deposit;
	}

	public Double getDeposit(Double amount) {
		return (amount == null || deposit == null) ? 0.0 : amount * deposit / 100.0;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public Integer getAdult() {
		return adult == null ? 0 : adult;
	}

	public void setAdult(Integer adult) {
		this.adult = adult;
	}

	public Integer getChild() {
		return child == null ? 0 : child;
	}

	public void setChild(Integer child) {
		this.child = child;
	}

	public Integer getInfant() {
		return infant == null ? 0 : infant;
	}

	public void setInfant(Integer infant) {
		this.infant = infant;
	}

	public Integer getQuantity() {
		return quantity == null ? 1 : quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public boolean noCustomerid() {
		return customerid == null || customerid.isEmpty()
				|| customerid.equalsIgnoreCase(Model.ZERO);
	}

	public boolean hasCustomerid() {
		return !noCustomerid();
	}

	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	public boolean noAgentid() {
		return agentid == null || agentid.isEmpty()
				|| agentid.equalsIgnoreCase(Model.ZERO);
	}

	public boolean hasAgentid() {
		return !noAgentid();
	}

	public boolean hasAgentid(String agentid) {
		return this.agentid != null && this.agentid.equals(agentid);
	}

	@XmlTransient
	public String getServiceid() {
		return serviceid;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}

	public boolean noServiceid() {
		return serviceid == null || serviceid.isEmpty()
				|| serviceid.equalsIgnoreCase(Model.ZERO);
	}

	public boolean hasServiceid() {
		return !noServiceid();
	}

	@XmlTransient
	public String getServicepayer() {
		return servicepayer;
	}

	public void setServicepayer(String servicepayer) {
		this.servicepayer = servicepayer;
	}

	@XmlTransient
	public String getOldstate() {
		return oldstate;
	}

	public void setOldstate(String oldstate) {
		this.oldstate = oldstate;
	}

	public boolean noOldstate() {
		return oldstate == null || oldstate.isEmpty();
	}

	public boolean hadState(String state) {
		return oldstate != null && !oldstate.isEmpty() && oldstate.equalsIgnoreCase(state);
	}

	public String getDuestate() {
		return duestate;
	}

	public void setDuestate(String duestate) {
		this.duestate = duestate;
	}

	public boolean noDuestate() {
		return duestate == null || duestate.isEmpty();
	}

	@XmlTransient
	public Boolean getReverse() {
		return reverse;
	}

	public void setReverse(Boolean reverse) {
		this.reverse = reverse;
	}

	public String getFlat() {
		return flat;
	}

	public void setFlat(String flat) {
		this.flat = flat;
	}

	@XmlTransient
	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public boolean noMarket() {
		return market == null || market.isEmpty();
	}

	@XmlTransient
	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}	

	public Double getNightlyrate() {
		return nightlyrate;
	}

	public void setNightlyrate(Double nightlyrate) {
		this.nightlyrate = nightlyrate;
	}

	public Double getPrice() {
		return price == null ? 0.0 : price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public boolean noPrice (){
		return price == null;
	}

	public Double getQuote() {
		return quote == null ? 0.0 : quote;
	}

	public void setQuote(Double quote) {
		this.quote = quote;
	}

	public Double getExtra() {
		return extra == null ? 0.0 : extra;
	}

	public void setExtra(Double extra) {
		this.extra = extra;
	}

	public Double getCost() {
		return cost == null ? 0.0 : cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public boolean noUnit() {
		return unit == null || unit.isEmpty();
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean noCurrency() {
		return currency == null || currency.isEmpty();
	}

	public boolean hasCurrency() {
		return !noCurrency();
	}

	public boolean hasCurrency(String currency) {
		return hasCurrency() && this.currency.equalsIgnoreCase(currency);
	}

	public boolean notCurrency(String currency) {
		return !hasCurrency(currency);
	}

	public Boolean getPriceunit() {
		return priceunit;
	}

	public void setPriceunit(Boolean priceunit) {
		this.priceunit = priceunit;
	}

	public Boolean getTermsaccepted() {
		return termsaccepted;
	}

	public void setTermsaccepted(Boolean termsaccepted) {
		this.termsaccepted = termsaccepted;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getAgentname() {
		return agentname;
	}

	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getConfirmationId() {
		return confirmationId;
	}

	public void setConfirmationId(String confirmationId) {
		this.confirmationId = confirmationId;
	}
	
	public void setCustomername(String familyname, String firstname) {
		if (firstname == null || firstname.isEmpty()) {this.customername = familyname;}
		else if (familyname == null || familyname.isEmpty()) {this.customername = firstname;}
		else {this.customername = familyname.trim() + ", " + firstname.trim();}
	}

	@Description(value = "First name of the party if an individual", target = DocTarget.METHOD)
	public String getFirstname() {
		if (customername == null || customername.isEmpty()) {return null;}
		String[] args = customername.split(",");
		return args.length > 1 ? args[1].trim() : args[0].trim();
	}

	public void setFirstname(String firstname) {
		if (customername == null) {customername = "familyname," + firstname;}
//		else if (customername.split(",").length < 2) {customername = customername + "," + firstname;}
		else {customername = customername.split(",")[0] + "," + firstname;}
	}
	
	@Description(value = "Name if an organization, or family or last name of the party if an individual", target = DocTarget.METHOD)
	public String getFamilyname() {
		if (customername == null || customername.isEmpty()){return null;}
		String[] args = customername.split(",");
		return args[0].trim();
	}

	public void setFamilyname(String familyname) {
		if (customername == null || customername.split(",").length < 2) {customername = familyname;}
		else {customername = familyname + customername.split(",")[1];}
	}
	
	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public boolean noCard() {
		return cardholder == null
				|| cardholder.isEmpty()
				|| cardnumber == null
				|| cardnumber.isEmpty()
				|| cardmonth == null
				|| cardmonth.isEmpty()
				|| cardyear == null
				|| cardyear.isEmpty()
//				|| cardcode == null
//				|| cardcode.isEmpty()
			;				
	}
	
	public boolean hasCard() {
		return !noCard();
	}
	
	public String getCardholder() {
		return cardholder;
	}

	public void setCardholder(String cardholder) {
		this.cardholder = cardholder;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getCardmonth() {
		return cardmonth;
	}

	public void setCardmonth(String cardmonth) {
		this.cardmonth = cardmonth;
	}

	public String getCardyear() {
		return cardyear;
	}

	public void setCardyear(String cardyear) {
		this.cardyear = cardyear;
	}

	public String getCardcode() {
		return cardcode;
	}

	public void setCardcode(String cardcode) {
		this.cardcode = cardcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean noMessage() {
		return message == null || message.isEmpty();
	}

	public boolean hasMessage() {
		return !noMessage();
	}

	public Party getActor() {
		return actor;
	}

	public void setActor(Party actor) {
		this.actor = actor;
	}

	public Party getAgent() {
		return agent;
	}

	public void setAgent(Party agent) {
		this.agent = agent;
	}

	public Party getCustomer() {
		return customer;
	}

	public void setCustomer(Party customer) {
		this.customer = customer;
	}

	public Party getSupplier() {
		return supplier;
	}

	public void setSupplier(Party supplier) {
		this.supplier = supplier;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

	public ArrayList<NameId> getCollisions() {
		return collisions;
	}

	public void setCollisions(ArrayList<NameId> collisions) {
		this.collisions = collisions;
	}

	public void addCollisions(ArrayList<NameId> collisions) {
		if (this.collisions == null) this.collisions = new ArrayList<NameId>();
		this.collisions.addAll(collisions);
	}

	public boolean noCollisions() {
		return collisions == null || collisions.isEmpty();
	}

	public boolean hasCollisions() {
		return !noCollisions();
	}

	public ArrayList<Price> getQuotedetail() {
		return quotedetail;
	}
	
	public void setQuotedetail(ArrayList<Price> quotedetail) {
		this.quotedetail = quotedetail;
	}

	public boolean noQuotedetail() {
		return quotedetail == null || quotedetail.isEmpty();
	}

	public boolean hasQuotedetail() {
		return !noQuotedetail();
	}

	//---------------------------------------------------------------------------------
	// Customer Text is specific to an organization
	//---------------------------------------------------------------------------------
	public static Text getCustomerText(String organizationid, String customerid, String language) {
		String textid = organizationid + NameId.Type.Party.name() + customerid;
		return new Text(textid, language);
	}

	public void setCustomerText(Text value){
		setText(organizationid, NameId.Type.Party, customerid, value);
	}

	public String workflowToString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Reservation [name=");
		sb.append(name);
		sb.append(", id=");
		sb.append(id);
		sb.append(", name=");
		sb.append(name);
		sb.append(", state=");
		sb.append(state);
		sb.append("oldstate=");
		sb.append(oldstate);
		sb.append("duestate=");
		sb.append(duestate);
		sb.append(", duedate=");
		sb.append(duedate);
		sb.append(", service()=");
		sb.append(service());
		return sb.toString();
	}
	
	public String paramString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Reservation [customerid=");
		builder.append(customerid);
		builder.append(", productid=");
		builder.append(productid);
		builder.append(", agentid=");
		builder.append(agentid);
		builder.append(", arrivaltime=");
		builder.append(arrivaltime);
		builder.append(", departuretime=");
		builder.append(departuretime);
		builder.append(", fromdate=");
		builder.append(fromdate);
		builder.append(", todate=");
		builder.append(todate);
		builder.append(", quote=");
		builder.append(quote);
		builder.append(", adult=");
		builder.append(adult);
		builder.append(", child=");
		builder.append(child);
		builder.append(", infant=");
		builder.append(infant);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", termsaccepted=");
		builder.append(termsaccepted);
		builder.append(", reverse=");
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", organizationid=");
		builder.append(organizationid);
		builder.append(", state=");
		builder.append(state);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Reservation [customerid=");
		builder.append(customerid);
		builder.append(", \nproductid=");
		builder.append(productid);
		builder.append(", \naltpartyid=");
		builder.append(altpartyid);
		builder.append(", \naltid=");
		builder.append(altid);
		builder.append(", \nagentid=");
		builder.append(agentid);
		builder.append(", \nserviceid=");
		builder.append(serviceid);
		builder.append(", \nfinanceid=");
		builder.append(financeid);
		builder.append(", \ncontractid=");
		builder.append(contractid);
		builder.append(", \noldstate=");
		builder.append(oldstate);
		builder.append(", \nduestate=");
		builder.append(duestate);
		builder.append(", \nflat=");
		builder.append(flat);
		builder.append(", \nmarket=");
		builder.append(market);
		builder.append(", \noutcome=");
		builder.append(outcome);
		builder.append(", \nservicepayer=");
		builder.append(servicepayer);
		builder.append(", \narrivaltime=");
		builder.append(arrivaltime);
		builder.append(", \ndeparturetime=");
		builder.append(departuretime);
		builder.append(", \nservicefrom=");
		builder.append(servicefrom);
		builder.append(", \nserviceto=");
		builder.append(serviceto);
		builder.append(", \nfromdate=");
		builder.append(fromdate);
		builder.append(", \ntodate=");
		builder.append(todate);
		builder.append(", \nprice=");
		builder.append(price);
		builder.append(", \nquote=");
		builder.append(quote);
		builder.append(", \ncost=");
		builder.append(cost);
		builder.append(", \ndiscount=");
		builder.append(discountfactor);
		builder.append(", \ndeposit=");
		builder.append(deposit);
		builder.append(", \nadult=");
		builder.append(adult);
		builder.append(", \nchild=");
		builder.append(child);
		builder.append(", \ninfant=");
		builder.append(infant);
		builder.append(", \nquantity=");
		builder.append(quantity);
		builder.append(", \nunit=");
		builder.append(unit);
		builder.append(", \ncurrency=");
		builder.append(currency);
		builder.append(", \ncardholder=");
		builder.append(cardholder);
		builder.append(", \ncardnumber=");
		builder.append(cardnumber);
		builder.append(", \ncardmonth=");
		builder.append(cardmonth);
		builder.append(", \ncardyear=");
		builder.append(cardyear);
		builder.append(", \ncardcode=");
		builder.append(cardcode);
		builder.append(", \nagentname=");
		builder.append(agentname);
		builder.append(", \ncustomername=");
		builder.append(customername);
		builder.append(", \nproductname=");
		builder.append(productname);
		builder.append(", \npriceunit=");
		builder.append(priceunit);
		builder.append(", \ntermsaccepted=");
		builder.append(termsaccepted);
		builder.append(", \nreverse=");
		builder.append(reverse);
		builder.append(", \ntasks=");
		builder.append(tasks);
		builder.append(", \nparentid=");
		builder.append(parentid);
		builder.append(", \nactorid=");
		builder.append(actorid);
		builder.append(", \nlocationid=");
		builder.append(locationid);
		builder.append(", \nparentname=");
		builder.append(parentname);
		builder.append(", \nactorname=");
		builder.append(actorname);
		builder.append(", \nprocess=");
		builder.append(process);
		builder.append(", \nactivity=");
		builder.append(activity);
		builder.append(", \nnotes=");
		builder.append(notes);
		builder.append(", \nduedate=");
		builder.append(duedate);
		builder.append(", \ndate=");
		builder.append(date);
		builder.append(", \ndonedate=");
		builder.append(donedate);
		builder.append(", \norganizationid=");
		builder.append(organizationid);
		builder.append(", \nstatus=");
		builder.append(status);
		builder.append(", \nstate=");
		builder.append(state);
		builder.append("\ncollisions=");
		builder.append(collisions);
		builder.append("\nvalues=");
		builder.append(values);
		builder.append("\nkeyvalues=");
		builder.append(getKeyvalues());
		builder.append("\nattributes=");
		builder.append(attributemap);
		builder.append("\ntexts=");
		builder.append(texts);
		builder.append("\nimages=");
		builder.append(imageurls);
		builder.append("\nquotedetail=");
		builder.append(quotedetail);
		builder.append("\nname=");
		builder.append(name);
		builder.append(", \nid=");
		builder.append(id);
		builder.append(", \nservice()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddrress() {
		return addrress;
	}

	public void setAddrress(String addrress) {
		this.addrress = addrress;
	}

	public List<ReservationExt> getListReservationExt() {
		return listReservationExt;
	}

	public void setListReservationExt(List<ReservationExt> listReservationExt) {
		this.listReservationExt = listReservationExt;
	}

	@Override
	public Integer getGuestCount() {
		Integer result = 0;
		if (adult != null){
			result += adult;
		}
		if (child != null){
			result += child;
		}
		return result;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fromdate == null) ? 0 : fromdate.hashCode());
		result = prime * result + ((productid == null) ? 0 : productid.hashCode());
		result = prime * result + ((todate == null) ? 0 : todate.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){return true;}
		if (obj == null){return false;}
		if (getClass() != obj.getClass()){
			return false;
		}
		Reservation otherReservation = (Reservation) obj;
		if (fromdate == null) {
			if (otherReservation.fromdate != null){
				return false;
			}
		} else if (!fromdate.equals(otherReservation.fromdate)){
			return false;
		}
		if (productid == null) {
			if (otherReservation.productid != null){
				return false;
			}
		} else if (!productid.equals(otherReservation.productid)){
			return false;
		}
		if (todate == null) {
			if (otherReservation.todate != null){
				return false;
			}
		} else if (!todate.equals(otherReservation.todate)){
			return false;
		}
		if (state == null) {
			if (otherReservation.state != null){
				return false;
			}
		} else if (!state.equals(otherReservation.state)){
			return false;
		}
		return true;
	}
	
//	public boolean equals(Reservation reservation){
//		if (this == reservation) {
//	        return true;
//	    }else{
//	    	return false;
//	    }
//	}
	
	
	
	
}