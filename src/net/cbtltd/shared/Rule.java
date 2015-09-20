/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.cbtltd.shared.api.HasResponse;
import net.cbtltd.shared.api.HasTableService;

@XmlRootElement(name = "rule")
public class Rule
implements HasResponse, HasTableService {

	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";
	public static final String SUSPENDED = "Suspended";
	public static final String[] STATES = {INITIAL, CREATED, SUSPENDED, FINAL};

	public static final String NAME = "rule.name";
	public static final String FROMDATE = "rule.fromdate";
	public static final String TODATE = "rule.todate";
	public static final String QUANTITY = "rule.quantity";
	public static final String UNIT = "rule.unit";
	public static final String VALUE = "rule.value";

	private String id;
	private String modeltype;
	private String modelid;
	private String name;
	private String unit;
	private String currency;
	private Date fromdate;
	private Date todate;
	private Double quantity;
	private Double value;
	private Date version; //latest change
	private String orderby;
	private int startrow = 0;
	private int numrows = Integer.MAX_VALUE;
	private int status = 0;

	public Rule() {}

	public Service service() {return Service.RULE;}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlTransient
	public String getModeltype() {
		return modeltype;
	}

	public void setModeltype(String modeltype) {
		this.modeltype = modeltype;
	}

	@XmlTransient
	public String getModelid() {
		return modelid;
	}

	public void setModelid(String modelid) {
		this.modelid = modelid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public boolean noTodate() {
		return todate == null;
	}
	
	public boolean hasTodate() {
		return !noTodate();
	}
	
	public boolean noDuration(Double duration, Time unit) {
		return fromdate == null || todate == null || Time.getDuration(fromdate, todate, unit) < duration;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	@XmlTransient
	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

	@XmlTransient
	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public boolean noOrderby() {
		return orderby == null || orderby.isEmpty();
	}
	
	@XmlTransient
	public int getStartrow() {
		return startrow;
	}

	public void setStartrow(int startrow) {
		this.startrow = startrow;
	}

	@XmlTransient
	public int getNumrows() {
		return numrows;
	}

	public void setNumrows(int numrows) {
		this.numrows = numrows;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Rule [name=");
		builder.append(name);
		builder.append(", modeltype=");
		builder.append(modeltype);
		builder.append(", modelid=");
		builder.append(modelid);
		builder.append(", fromdate=");
		builder.append(fromdate);
		builder.append(", todate=");
		builder.append(todate);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", value=");
		builder.append(value);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}
}