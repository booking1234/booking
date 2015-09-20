/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.json.reservation;

import java.util.Date;

import net.cbtltd.json.JSONResponse;

public class ReservationWidgetItem implements JSONResponse {

	private Date date;
	private Date duedate;
	private Double amount;
	private Double cost;
	private Double latitude;
	private Double longitude;
	private Double price;
	private Double quote;
	private Double balance;
	private Double deposit;
	private Integer productroom;
	private String actorid;
	private String actorname;
	private String agentid;
	private String agentname;
	private String arrivaltime;
	private String currency;
	private String customerid;
	private String customername;
	private String departuretime;
	private String emailaddress;
	private String financeid;
	private String fromdate;
	private String id;
	private String message;
	private String name;
	private String notes;
	private String organizationid;
	private String organizationname;
	private String productid;
	private String productname;
	private String servicefrom;
	private String serviceto;
	private String state;
	private Boolean termsaccepted;
	private String todate;
	private String unit;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDuedate() {
		return duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getQuote() {
		return quote;
	}

	public void setQuote(Double quote) {
		this.quote = quote;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public Integer getProductroom() {
		return productroom;
	}

	public void setProductroom(Integer productroom) {
		this.productroom = productroom;
	}

	public String getActorid() {
		return actorid;
	}

	public void setActorid(String actorid) {
		this.actorid = actorid;
	}

	public String getActorname() {
		return actorname;
	}

	public void setActorname(String actorname) {
		this.actorname = actorname;
	}

	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	public String getAgentname() {
		return agentname;
	}

	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}

	public String getArrivaltime() {
		return arrivaltime;
	}

	public void setArrivaltime(String arrivaltime) {
		this.arrivaltime = arrivaltime;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getDeparturetime() {
		return departuretime;
	}

	public void setDeparturetime(String departuretime) {
		this.departuretime = departuretime;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getFinanceid() {
		return financeid;
	}

	public void setFinanceid(String financeid) {
		this.financeid = financeid;
	}

	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean getTermsaccepted() {
		return termsaccepted;
	}

	public void setTermsaccepted(Boolean termsaccepted) {
		this.termsaccepted = termsaccepted;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReservationWidgetItem [date=");
		builder.append(date);
		builder.append(", duedate=");
		builder.append(duedate);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", cost=");
		builder.append(cost);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", price=");
		builder.append(price);
		builder.append(", quote=");
		builder.append(quote);
		builder.append(", deposit=");
		builder.append(deposit);
		builder.append(", productroom=");
		builder.append(productroom);
		builder.append(", actorid=");
		builder.append(actorid);
		builder.append(", actorname=");
		builder.append(actorname);
		builder.append(", agentid=");
		builder.append(agentid);
		builder.append(", agentname=");
		builder.append(agentname);
		builder.append(", arrivaltime=");
		builder.append(arrivaltime);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", customerid=");
		builder.append(customerid);
		builder.append(", customername=");
		builder.append(customername);
		builder.append(", departuretime=");
		builder.append(departuretime);
		builder.append(", emailaddress=");
		builder.append(emailaddress);
		builder.append(", financeid=");
		builder.append(financeid);
		builder.append(", fromdate=");
		builder.append(fromdate);
		builder.append(", id=");
		builder.append(id);
		builder.append(", message=");
		builder.append(message);
		builder.append(", name=");
		builder.append(name);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", organizationid=");
		builder.append(organizationid);
		builder.append(", organizationname=");
		builder.append(organizationname);
		builder.append(", productid=");
		builder.append(productid);
		builder.append(", productname=");
		builder.append(productname);
		builder.append(", servicefrom=");
		builder.append(servicefrom);
		builder.append(", serviceto=");
		builder.append(serviceto);
		builder.append(", state=");
		builder.append(state);
		builder.append(", termsaccepted=");
		builder.append(termsaccepted);
		builder.append(", todate=");
		builder.append(todate);
		builder.append(", unit=");
		builder.append(unit);
		builder.append("]");
		return builder.toString();
	}

}
