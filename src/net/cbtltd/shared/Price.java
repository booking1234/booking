/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.cbtltd.rest.util.XmlDateAdapter;

@XmlRootElement(name = "pricedetail")
public class Price
extends ModelTable {

    
	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";
	public static final String INVOICED = "Invoiced";
	public static final String[] STATES = {INITIAL, CREATED, INVOICED, FINAL};
	//TODO: put into enum
	public static final String ADJUSTED = "Adjusted Rate";
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
	public static final String NOT_TAXABLE = "Not Taxable Rate";
	public static final String YIELD = "Yield Management Rule";
	
	// Bookt specific - to be generalized
	public static final String CalculatePerAdult = "Calculate per Adult";
	public static final String CalculatePerChild = "Calculate per Child";
	public static final String CalculatePerNight = "Calculate per Night";
	public static final String CalculatePerPerson = "Calculate per Person";
	public static final String FlatRate = "Flat Rate";

	public enum Rule {SunCheckIn, MonCheckIn, TueCheckIn, WedCheckIn, ThuCheckIn, FriCheckIn, SatCheckIn, AnyCheckIn, DailyRate, FixedRate};
	public static final String[] RULES = {Rule.SunCheckIn.name(), Rule.MonCheckIn.name(), Rule.TueCheckIn.name(), Rule.WedCheckIn.name(), 
		Rule.ThuCheckIn.name(), Rule.FriCheckIn.name(), Rule.SatCheckIn.name(), Rule.AnyCheckIn.name()};

	public enum Payer {Unknown, Customer, Organization, Agent, Owner};
	public static final String[] PAYERS = {Payer.Unknown.name(), Payer.Customer.name(), Payer.Organization.name(), Payer.Agent.name(), Payer.Owner.name()};

	public boolean isOptional() {
		return entitytype != null && entitytype.equalsIgnoreCase(NameId.Type.Feature.name());
	}
	
	public boolean isTaxable() {
		return type != null && !type.equalsIgnoreCase(Price.NOT_TAXABLE);
	}
	
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
	

	protected String altid;
	protected String type;
	protected String partyid;
	protected String partyname;
	protected String supplierid;
	protected String entitytype;
	protected String entityid;
	protected String entityname;
	protected String payer;
	protected String rule;
	protected String dateStr;
	protected Date date;
	protected Date todate;
	protected Double quantity;
	protected String unit;
	protected Double value;
	protected Double minimum;
	protected Double factor;
	protected Double cost;
	protected Integer available;
	protected Integer minStay;
	protected Integer maxStay;
	protected String currency;
	protected Date version; //latest change
	protected Integer idFrom;
	protected PriceExt priceExt;
	
	public PriceExt getPriceExt() {
		return priceExt;
	}


	public void setPriceExt(PriceExt priceExt) {
		this.priceExt = priceExt;
	}


	public Integer getIdFrom() {
		return idFrom;
	}


	public void setIdFrom(Integer idFrom) {
		this.idFrom = idFrom;
	}


	public Integer getIdTo() {
		return idTo;
	}


	public void setIdTo(Integer idTo) {
		this.idTo = idTo;
	}

	protected Integer idTo;

	public Price() {
		minStay = 1;
		minimum = 0.;
	}


//	public Price(
//			String entitytype,
//			String entityid
//	) {
//		this.entitytype = entitytype;
//		this.entityid = entityid;		
//	}
//
//	public Price(
//			String partyid,
//			String entitytype,
//			String entityid,
//			Date date) {
//		this.partyid = partyid;
//		this.entitytype = entitytype;
//		this.entityid = entityid;
//		this.date = date;
//	}
//
//	public Price(
//			String partyid,
//			String entitytype,
//			String entityid,
//			Date date,
//			Double quantity,
//			String unit,
//			String currency) {
//		this.partyid = partyid;
//		this.entitytype = entitytype;
//		this.entityid = entityid;
//		this.date = date;
//		this.quantity = quantity;
//		this.unit = unit;
//		this.currency = currency;
//	}

	public Service service() {return Service.PRICE;}

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
	public String getPartyname() {
		return partyname;
	}

	public void setPartyname(String partyname) {
		this.partyname = partyname;
	}

	@XmlTransient
	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public boolean noSupplierid() {
		return supplierid == null || supplierid.isEmpty();
	}
	
	public boolean hasSupplierid() {
		return !noSupplierid();
	}
	
	@XmlTransient
	public String getEntitytype() {
		return entitytype;
	}

	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}

	public boolean hasAltid()
	{
		return (this.altid!=null && !this.altid.isEmpty()); 
	}
	public boolean hasEntitytype(String entitytype) {
		return this.entitytype != null && entitytype != null && this.entitytype.equalsIgnoreCase(entitytype);
	}

	@XmlTransient
	public String getEntityid() {
		return entityid;
	}

	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}

	public boolean hasEntityid(String entityid) {
		return this.entityid != null && entityid != null && this.entityid.equalsIgnoreCase(entityid);
	}

	@XmlTransient
	public String getEntityname() {
		return entityname;
	}

	public void setEntityname(String entityname) {
		this.entityname = entityname;
	}

	@XmlTransient
	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	@XmlElement(name="date")
	@XmlJavaTypeAdapter(XmlDateAdapter.class)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDateStr() {
		return dateStr;
	}


	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	@XmlElement(name="todate")
	@XmlJavaTypeAdapter(XmlDateAdapter.class)
	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public boolean noTodate() {
		return todate == null;
	}
	
	public boolean hasTodate() {
		return !noTodate();
	}
	
	public boolean noDuration(Double duration, Time unit) {
		return date == null || todate == null || Time.getDuration(date, todate, unit) < duration;
	}
	
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public boolean noQuantity() {
		return quantity == null || quantity <= 0.0;
	}
	
	public String getUnit() {
		return unit;
	}

//	@XmlTransient
	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getMaxStay() {
		return maxStay;
	}


	public void setMaxStay(Integer maxStay) {
		this.maxStay = maxStay;
	}


	public Double getValue() {
		return value;
	}

	public boolean noValue() {
		return value == null || value <= 0.0;
	}

	public boolean hasValue() {
		return !noValue();
	}

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

	@XmlTransient
	public Double getCost() {
		return cost;
	}

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

//	@XmlTransient
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean hasCurrency(String currency) {
		return this.currency != null && this.currency.equalsIgnoreCase(currency);
	}

	
	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}
	
	public Integer getMinStay() {
		return minStay;
	}
	
	public void setMinStay(Integer minStay) {
		this.minStay = minStay;
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
		builder.append(partyname);
		builder.append(", supplierid=");
		builder.append(supplierid);
		builder.append(", entitytype=");
		builder.append(entitytype);
		builder.append(", entityid=");
		builder.append(entityid);
		builder.append(", entityname=");
		builder.append(entityname);
		builder.append(", payer=");
		builder.append(payer);
		builder.append(", rule=");
		builder.append(rule);
		builder.append(", date=");
		builder.append(date);
		builder.append(", todate=");
		builder.append(todate);
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
		builder.append(", images=");
		builder.append(imageurls);
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


	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((altid == null) ? 0 : altid.hashCode());
		result = prime * result + ((available == null) ? 0 : available.hashCode());
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((entityid == null) ? 0 : entityid.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((entitytype == null) ? 0 : entitytype.hashCode());
		result = prime * result + ((factor == null) ? 0 : factor.hashCode());
		result = prime * result + ((maxStay == null) ? 0 : maxStay.hashCode());
		result = prime * result + ((minStay == null) ? 0 : minStay.hashCode());
		result = prime * result + ((minimum == null) ? 0 : minimum.hashCode());
		result = prime * result + ((partyid == null) ? 0 : partyid.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((rule == null) ? 0 : rule.hashCode());
		result = prime * result + ((supplierid == null) ? 0 : supplierid.hashCode());
		result = prime * result + ((todate == null) ? 0 : todate.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Price other = (Price) obj;
		if (altid == null) {
			if (other.altid != null)
				return false;
		} else if (!altid.equals(other.altid))
			return false;
		if (available == null) {
			if (other.available != null)
				return false;
		} else if (!available.equals(other.available))
			return false;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (entityid == null) {
			if (other.entityid != null)
				return false;
		} else if (!entityid.equals(other.entityid))
			return false;
		if (factor == null) {
			if (other.factor != null)
				return false;
		} else if (!factor.equals(other.factor))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (entitytype == null) {
			if (other.entitytype != null)
				return false;
		} else if (!entitytype.equals(other.entitytype))
			return false;
		if (maxStay == null) {
			if (other.maxStay != null)
				return false;
		} else if (!maxStay.equals(other.maxStay))
			return false;
		if (minStay == null) {
			if (other.minStay != null)
				return false;
		} else if (!minStay.equals(other.minStay))
			return false;
		if (minimum == null) {
			if (other.minimum != null)
				return false;
		} else if (!minimum.equals(other.minimum))
			return false;
		if (partyid == null) {
			if (other.partyid != null)
				return false;
		} else if (!partyid.equals(other.partyid))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (rule == null) {
			if (other.rule != null)
				return false;
		} else if (!rule.equals(other.rule))
			return false;
		if (supplierid == null) {
			if (other.supplierid != null)
				return false;
		} else if (!supplierid.equals(other.supplierid))
			return false;
		if (todate == null) {
			if (other.todate != null)
				return false;
		} else if (!todate.equals(other.todate))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
	public boolean equalsWithoutDates(Price other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (getClass() != other.getClass())
			return false;
		if (altid == null) {
			if (other.altid != null)
				return false;
		} else if (!altid.equals(other.altid))
			return false;
		if (available == null) {
			if (other.available != null)
				return false;
		} else if (!available.equals(other.available))
			return false;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (entityid == null) {
			if (other.entityid != null)
				return false;
		} else if (!entityid.equals(other.entityid))
			return false;
		if (factor == null) {
			if (other.factor != null)
				return false;
		} else if (!factor.equals(other.factor))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (entitytype == null) {
			if (other.entitytype != null)
				return false;
		} else if (!entitytype.equals(other.entitytype))
			return false;
		if (maxStay == null) {
			if (other.maxStay != null)
				return false;
		} else if (!maxStay.equals(other.maxStay))
			return false;
		if (minStay == null) {
			if (other.minStay != null)
				return false;
		} else if (!minStay.equals(other.minStay))
			return false;
		if (minimum == null) {
			if (other.minimum != null)
				return false;
		} else if (!minimum.equals(other.minimum))
			return false;
		if (partyid == null) {
			if (other.partyid != null)
				return false;
		} else if (!partyid.equals(other.partyid))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (rule == null) {
			if (other.rule != null)
				return false;
		} else if (!rule.equals(other.rule))
			return false;
		if (supplierid == null) {
			if (other.supplierid != null)
				return false;
		} else if (!supplierid.equals(other.supplierid))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
	
	
	
	
	
}