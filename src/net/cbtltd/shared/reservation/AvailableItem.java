/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.reservation;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.HasAlert;
import net.cbtltd.shared.api.HasCollision;
import net.cbtltd.shared.api.HasIcons;
import net.cbtltd.shared.api.HasKey;
import net.cbtltd.shared.api.HasPrice;
import net.cbtltd.shared.api.HasResponse;
import net.cbtltd.shared.api.HasSchedule;
import net.cbtltd.shared.api.HasState;

import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.user.client.rpc.IsSerializable;

public class AvailableItem
implements HasAlert, HasCollision, HasIcons, HasKey, HasPrice, HasResponse, HasSchedule, HasState, IsSerializable, Comparable<AvailableItem> {

	public static final String COMMISSION = "(quote - cost)";
	public static final String COST = "cost";
	public static final String DURATION = "duration";
	public static final String FROMDATE = "fromdate";
	public static final String PARTYNAME = "partyname";
	public static final String PRICE = "price";
	public static final String PRODUCTNAME = "productname";
	public static final String QUOTE = "quote";
	public static final String RANK = "product.rank";
	public static final String RATING = "rating";
	public static final String ROOM = "room";
	public static final String SUPPLIERNAME = "suppliername";
	public static final String TODATE = "todate";
	public static final String UNKNOWN_GUEST = "UNKNOWN GUEST";

	private String orderby;
	private String productid;
	private String supplierid;
	private String agentid;
	private String reservationid;
	private String state;
	private String productname;
	private String suppliername;
	private String partyname;
	private String webaddress;
	private String currency;
	private String alert;
	private Boolean priceunit; //true = night false = stay
	private String unit; // per day
	private Date fromdate;
	private Date todate;
	private Double rank;
	private Double nightlyrate;
	private Double price;
	private Double quote;
	private Double extra;
	private Double cost;
	private Double latitude;
	private Double longitude;
	private Integer rating;
	private Integer room;
	private Integer person;
	private int status;
	private ArrayList<HasSchedule> items = new ArrayList<HasSchedule>();
	private ArrayList<NameId> collisions;
	private ArrayList<Price> quotedetail;

	public Service service() {return Service.RESERVATION;}

	public int compareTo(AvailableItem comparable) {
		if (PRODUCTNAME.equalsIgnoreCase(orderby)) {return getProductname().compareTo(comparable.getProductname());}
		else if (PARTYNAME.equalsIgnoreCase(orderby)) {return getPartyname().compareTo(comparable.getPartyname());}
		else if (SUPPLIERNAME.equalsIgnoreCase(orderby)) {return getSuppliername().compareTo(comparable.getSuppliername());}
		else if (FROMDATE.equalsIgnoreCase(orderby)) {return getFromdate().compareTo(comparable.getFromdate());}
		else if (TODATE.equalsIgnoreCase(orderby)) {return getTodate().compareTo(comparable.getTodate());}
		else if (COST.equalsIgnoreCase(orderby)) {return getCost().compareTo(comparable.getCost());}
		else if (PRICE.equalsIgnoreCase(orderby)) {return getPrice().compareTo(comparable.getPrice());}
		else if (QUOTE.equalsIgnoreCase(orderby)) {return getQuote().compareTo(comparable.getQuote());}
		else if (RATING.equalsIgnoreCase(orderby)) {return getRating().compareTo(comparable.getRating());}
		else if (ROOM.equalsIgnoreCase(orderby)) {return getRoom().compareTo(comparable.getRoom());}
		else if (DURATION.equalsIgnoreCase(orderby)) {return getDuration(Time.DAY).compareTo(comparable.getDuration(Time.DAY));}
		else if (getProductname() != null && getFromdate() != null) {
			// to sort by product name then by date
			int name = getProductname().compareTo(comparable.getProductname());
			if (name < 0) {name = -1;} else if (name > 0) {name = 1;}
			int date = getFromdate().compareTo(comparable.getFromdate());
			if (date < 0) {date = -1;} else if (date > 0) {date = 1;}
			return name + name + date;
		}
		else return 0;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public String getId() {
		return productid;
	}

	public boolean noId() {
		return productid == null || productid.isEmpty();
	}

	public boolean hasId() {
		return !noId();
	}

	public boolean hasId(String id) {
		return hasId() && this.productid.equalsIgnoreCase(id);
	}

	public String getEntitytype() {
		return NameId.Type.Product.name();
	}
	
	public String getEntityid() {
		return productid;
	}
	
	public String getProductid(){
		return productid;
	}

	public void setProductid(String productid){
		this.productid = productid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean noState() {
		return state == null || state.isEmpty();
	}

	public boolean hasState(String state) {
		return this.state != null && this.state.equalsIgnoreCase(state);
	}

	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	public boolean noAgentid() {
		return agentid == null || agentid.isEmpty();
	}

	public String getWebaddress() {
		return webaddress;
	}

	public void setWebaddress(String webaddress) {
		this.webaddress = webaddress;
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public String getReservationid() {
		return reservationid;
	}

	public void setReservationid(String reservationid) {
		this.reservationid = reservationid;
	}

	public boolean noReservationid() {
		return reservationid == null || reservationid.isEmpty();
	}

	public boolean hasReservationid() {
		return !noReservationid();
	}

	public boolean isReservationid(String reservationid) {
		return this.reservationid.equals(reservationid);
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getProductname(int length) {
		return NameId.trim(productname, length);
	}

	public String getSuppliername() {
		return noRank() ? "" : suppliername;
	}

	public String getSuppliername(int length) {
		return NameId.trim(getSuppliername(), length);
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public String getPartyname() {
		return partyname;
	}

	public void setPartyname(String partyname) {
		this.partyname = partyname;
	}

	public String getLabel(int length) {		
		if (partyname == null || partyname.isEmpty()){ return "|"; }
		if (partyname.trim().equals(",")) {return AvailableItem.UNKNOWN_GUEST;}		
		return NameId.trim(partyname, ",", length);
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean noCurrency() {
		return this.currency == null || this.currency.isEmpty();
	}

	public boolean hasCurrency(String currency) {
		return this.currency == null || currency == null || this.currency.equalsIgnoreCase(currency);
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public Integer getIconkey() {
		return alert == null || alert.isEmpty() ? 0 : 1;
	}

	public String getIcontitle() {
		return alert;
	}
	public Boolean getPriceunit() {
		return priceunit;
	}

	public void setPriceunit(Boolean priceunit) {
		this.priceunit = priceunit;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public boolean noFromdate () {
		return fromdate == null;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public boolean noTodate () {
		return todate == null;
	}

	public Double getDuration(Time unit) {
		return Time.getDuration(fromdate, todate, unit);
	}

	public Double getRank() {
		return rank;
	}

	public void setRank(Double rank) {
		this.rank = rank;
	}

	public boolean hasRank() {
		return rank == null || rank >= 0.0;
	}
	
	public boolean noRank() {
		return !hasRank();
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

	public boolean noPrice () {
		return price == null || price <= 0.0;
	}

	public boolean hasPrice (){
		return !noPrice();
	}

	public Double getQuote() {
		return quote == null ? 0.0 : quote;
	}

	public void setQuote(Double quote) {
		this.quote = quote;
	}

	public boolean noQuote () {
		return quote == null || quote <= 0.0;
	}

	public Double getExtra() {
		return extra == null ? 0.0 : extra;
	}

	public void setExtra(Double extra) {
		this.extra = extra;
	}

	public Double getCommission() {
		return getQuote() - getCost() - getExtra();
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public boolean isSpecial(double discount) {
		return (price == null || quote == null || quote <= 0.0) ? false : quote/price < discount;
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

	public LatLng getLatLng(){
		if (latitude == null || longitude == null){return null;}
		return LatLng.newInstance(latitude, longitude);
	}

	public void setLatLng(LatLng value){
		if (value == null){return;}
		latitude = value.getLatitude();
		longitude = value.getLongitude();
	}

	public boolean noLatLng() {
		return latitude == null || latitude < -90.0 || latitude > 90.0
		||  longitude == null || longitude < -180.0 || longitude > 180.0; // || altitude == null;
	}

	public boolean hasLatLng() {
		return !noLatLng();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public AvailableItem getItem(Date date) {
		if (isDayBooked(date)) {return this;}
		if (items != null && !items.isEmpty()) {
			for (HasSchedule item : items) {
				if (item.isDayBooked(date)) {return (AvailableItem) item;}
			}
		}
		return clone();
	}
	
	//---------------------------------------------------------------------------------
	// Implements HasSchedule interface
	//---------------------------------------------------------------------------------
	public String getActivityid() {
		return reservationid;
	}

	public boolean isDayBooked(Date date) {
		return !date.before(fromdate) && todate.after(date);
	}

	public int getDaysToEnd(Date date) {
		return Time.getDuration(date, todate, Time.DAY).intValue();
	}

	public ArrayList<HasSchedule> getItems() {
		return items;
	}

	public void addItem(HasSchedule item) {
		items.add(item);
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public int getKey() {
		return rating;
	}

	public Integer getRoom() {
		return room;
	}

	public void setRoom(Integer room) {
		this.room = room;
	}

	public Integer getPerson() {
		return person;
	}

	public void setPerson(Integer person) {
		this.person = person;
	}
	
	public Integer getQuantity() {
		return 1;
	}

	public void setQuantity(Integer quantity) {
		//this.quantity = quantity;
	}

	public Product getProduct() {
		Product product = new Product();
		product.setId(productid);
		return product;	 
	}

	public void setQuotedetail(ArrayList<Price> quotedetail) {
		this.quotedetail = quotedetail;
	}

	public ArrayList<Price> getQuotedetail() {
		return quotedetail;
	}

//	public void addQuotedetail(String id, String name, String type, Double value, String currency) {
//		if (name == null || name.isEmpty() || value == null) {return;}
//		if (quotedetail == null) {quotedetail = new ArrayList<Price>();}
//		for (Price pricedetail : quotedetail) {
//			if (pricedetail.hasId(id) && pricedetail.hasType(type) && pricedetail.hasCurrency(currency)) {
//				pricedetail.addValue(value);
//				return;
//			}
//		}
//		quotedetail.add(new Price(id, name, type, 1.0, value, currency));
//	}

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

	public Reservation getReservation() {
		return getReservation(fromdate, todate, Party.NO_ACTOR, null, null);	 
	}

	public Reservation getReservation(Date fromdate, Date todate, String actorid, String customerid, String notes) {
		this.fromdate = fromdate;
		this.todate = todate;
		return getReservation(actorid, customerid, notes);	 
	}

	public Reservation getReservation(String actorid, String customerid, String notes) {
		Reservation reservation = new Reservation();
		reservation.setId(reservationid);
		reservation.setActorid(actorid);
		reservation.setAgentid(agentid);
		reservation.setOrganizationid(supplierid);
		reservation.setProductid(productid);
		reservation.setCustomerid(customerid);
		reservation.setState(Reservation.State.Initial.name());
		reservation.setDate(new Date());
		reservation.setFromdate(fromdate);
		reservation.setTodate(todate);
		reservation.setPrice(getPrice());
		reservation.setQuote(getQuote());
		reservation.setCost(getCost());
		reservation.setCurrency(currency);
		reservation.setPriceunit(priceunit);
		reservation.setUnit(Unit.DAY);
		reservation.setNotes(notes);
		return reservation;
	}

	public AvailableItem clone() {
		AvailableItem newitem = new AvailableItem();
		newitem.setAgentid(agentid);
		newitem.setProductid(productid);
		newitem.setProductname(productname);
		newitem.setState(Reservation.State.Initial.name());
		newitem.setSupplierid(supplierid);
		newitem.setSuppliername(suppliername);
		newitem.setWebaddress(webaddress);
		newitem.setCurrency(currency);
		newitem.setPriceunit(priceunit);
		newitem.setUnit(unit);
		newitem.setFromdate(fromdate);
		newitem.setTodate(todate);
		newitem.setPrice(price);
		newitem.setQuote(quote);
		newitem.setCost(cost);
		newitem.setLatitude(latitude);
		newitem.setLongitude(longitude);
		newitem.setRating(rating);
		newitem.setRoom(room);
		newitem.setPerson(person);
		return newitem;
	}

	@Override
	public Integer getGuestCount() {
	    Integer result = 1;
	    if (person != null && person > 0){
		result = person;		
	    }
	    
	    return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nAvailableItem [productid=");
		builder.append(productid);
		builder.append(", supplierid=");
		builder.append(supplierid);
		builder.append(", agentid=");
		builder.append(agentid);
		builder.append(", reservationid=");
		builder.append(reservationid);
//		builder.append(", orderby=");
//		builder.append(orderby);
		builder.append(", state=");
		builder.append(state);
		builder.append(", productname=");
		builder.append(productname);
//		builder.append(", suppliername=");
//		builder.append(suppliername);
//		builder.append(", partyname=");
//		builder.append(partyname);
//		builder.append(", webaddress=");
//		builder.append(webaddress);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", priceunit=");
		builder.append(priceunit);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", fromdate=");
		builder.append(fromdate);
		builder.append(", todate=");
		builder.append(todate);
		builder.append(", rank=");
		builder.append(rank);
		builder.append(", price=");
		builder.append(price);
		builder.append(", quote=");
		builder.append(quote);
//		builder.append(", cost=");
//		builder.append(cost);
//		builder.append(", latitude=");
//		builder.append(latitude);
//		builder.append(", longitude=");
//		builder.append(longitude);
//		builder.append(", rating=");
//		builder.append(rating);
//		builder.append(", status=");
//		builder.append(status);
//		builder.append(", items=");
//		builder.append(items);
//		builder.append(", service()=");
//		builder.append(service());
		builder.append(", alert=");
		builder.append(alert);
		builder.append("]");
		return builder.toString();
	}
}
