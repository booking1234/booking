/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "addon")
public class ReservationExt
extends ModelTable {

    
	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";
	public static final String INVOICED = "Invoiced";
	public static final String[] STATES = {INITIAL, CREATED, INVOICED, FINAL};

	//TODO: put into enum
	public static final String ADJUSTED = "Adjusted Rate";
	
	@XmlTransient
	public String getReservationId() {
		return reservationId;
	}


	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}


	@XmlTransient
	public String getGuestName() {
		return guestName;
	}



	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}



	public Integer getAvailable() {
		return available;
	}



	public void setAvailable(Integer available) {
		this.available = available;
	}


	@XmlTransient
	public Integer getMinStay() {
		return minStay;
	}



	public void setMinStay(Integer minStay) {
		this.minStay = minStay;
	}


	public static final String INCLUDED = "Included in Price";
	public static final String RACK_RATE = "Rack Rate";
	public static final String RATE = "Accommodation Rate";
	public static final String COMMISSION = "Commission";
	public static final String DEPOSIT = "Confirmation Deposit";
	public static final String INSURANCE = "Insurance Charge";
	public static final String MANDATORY = "Mandatory Charge";
	public static final String OPTIONAL = "Optional Feature";
	public static final String TAX_INCLUDED = "Tax Included in Rate";
	public static final String TAX_EXCLUDED = "Tax on Rate";
	public static final String TAX_ON_TAX = "Tax on Rate and Tax";

	
	// Bookt specific - to be generalized
	public static final String CalculatePerAdult = "Calculate per Adult";
	public static final String CalculatePerChild = "Calculate per Child";
	public static final String CalculatePerNight = "Calculate per Night";
	public static final String CalculatePerPerson = "Calculate per Person";
	public static final String FlatRate = "Flat Rate";	

	
	
	public static final String DATE = "date";
	public static final String ENTITYNAME = "entityname";
	public static final String TODATE = "todate";
	public static final String QUANTITY = "quantity";
	public static final String UNIT = "unit";
	public static final String VALUE = "value";
	public static final String MINIMUM = "minimum";
	public static final String COST = "cost";
	public static final String AVAILABLE = "available";
	public static final String EXACTMATCH = "exactmatch";
	public static final String GUEST_TYPE = "Guest";
	public static final String ADDON_TYPE = "Addon";
	

	protected String altid;
	protected String type;
	protected String addonType;
	


	protected String partyid;
	protected String reservationId;
	
	protected String guestName;
	protected String name;
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}


	protected Double quantity;
	protected String unit;
	protected Double value=new Double(0);
	protected Double minimum=new Double(0);
	protected Double factor;
	protected Double cost;
	protected Integer available;
	protected Integer minStay;
	protected String currency;
	protected Date version; //latest change

	public ReservationExt() {}



	public Service service() {return Service.PRICE;}
	
	public String getAddonType() {
		return addonType;
	}

	public void setAddonType(String addonType) {
		this.addonType = addonType;
	}

	@XmlTransient
	public String getAltid() {
		return altid;
	}

	public void setAltid(String altid) {
		this.altid = altid;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean hasType(String type) {
		return this.type != null && type != null && this.type.equalsIgnoreCase(type);
	}
	
	@XmlTransient
	public String getPartyid() {
		return partyid;
	}

	public void setPartyid(String partyid) {
		this.partyid = partyid;
	}

	
	@XmlTransient
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public boolean noQuantity() {
		return quantity == null || quantity <= 0.0;
	}
	
	@XmlTransient
	public String getUnit() {
		return unit;
	}

//	@XmlTransient
	public void setUnit(String unit) {
		this.unit = unit;
	}

	@XmlTransient
	public Double getValue() {
		return value;
	}

	public boolean noValue() {
		return value == null || value <= 0.0;
	}

	public boolean hasValue() {
		return !noValue();
	}

	@XmlTransient
	public Double getTotalvalue() {
		return value == null || quantity == null ? null : value * quantity;
	}

	public boolean noTotalvalue() {
		return value == null || quantity == null;
	}

	public boolean hasTotalvalue() {
		return !noTotalvalue();
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public void addValue(Double value) {
		this.value += value;
	}

	@XmlTransient
	public Double getMinimum() {
		return minimum;
	}

	public void setMinimum(Double minimum) {
		this.minimum = minimum;
	}

	public Double getFactor() {
		return factor;
	}

	public void setFactor(Double factor) {
		this.factor = factor;
	}

//	@XmlTransient
	public Double getCost() {
		return cost;
	}

	@XmlTransient
	public Double getTotalcost() {
		return cost == null || quantity == null ? null : cost * quantity;
	}

	public boolean noTotalcost() {
		return cost == null || quantity == null;
	}

	public boolean hasTotalcost() {
		return !noTotalcost();
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	@XmlTransient
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean hasCurrency(String currency) {
		return this.currency != null && this.currency.equalsIgnoreCase(currency);
	}

	
	

	@XmlTransient
	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

	@XmlTransient
	public Text getPublicText() {
		return getText(NameId.Type.Price, id, Text.Code.Public);
	}

	public void setPublicText(Text value) {
		setText(NameId.Type.Price, id, Text.Code.Public, value);
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Price [altid=");
		builder.append(altid);
		builder.append(", type=");
		builder.append(type);
		builder.append(", partyid=");
		builder.append(partyid);
		builder.append(", partyname=");
		builder.append(guestName);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", value=");
		builder.append(value);
		builder.append(", minimum=");
		builder.append(minimum);
		builder.append(", factor=");
		builder.append(factor);
		builder.append(", cost=");
		builder.append(cost);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", available=");
		builder.append(available);
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
		builder.append(", type=");
		builder.append(type);
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