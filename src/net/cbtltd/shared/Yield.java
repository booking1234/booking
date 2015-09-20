/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Yield
extends ModelTable {

	public static final String MODIFIER = "modifier";
	public static final String AMOUNT = "amount";
	public static final String FROMDATE = "fromdate";
	public static final String TODATE = "todate";
	
	//Entity types
	public static final String PRODUCT = "Product";

	// States
	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";
	public static final String SUSPENDED = "Suspended";
	public static final String[] STATES = {INITIAL, CREATED, SUSPENDED, FINAL};

	public static final String INCREASE_PERCENT = "Increase Percent";
	public static final String DECREASE_PERCENT = "Decrease Percent";
	public static final String INCREASE_AMOUNT = "Increase Amount";
	public static final String DECREASE_AMOUNT = "Decrease Amount";
	public static final String[] MODIFIERS = {INCREASE_PERCENT,
			DECREASE_PERCENT, INCREASE_AMOUNT, DECREASE_AMOUNT};

	public static final String DATE_RANGE = "Date Range";
	public static final String DAY_OF_WEEK = "Day of Week";
	public static final String GAP_FILLER = "Maximum Gap Filler";
	public static final String EARLY_BIRD = "Early Booking Lead Time";
	public static final String LAST_MINUTE = "Last Minute Lead Time";
	public static final String LENGTH_OF_STAY = "Length of Stay";
	public static final String OCCUPANCY_ABOVE = "Occupancy Above";
	public static final String OCCUPANCY_BELOW = "Occupancy Below";
	public static final String WEEKEND = "Weekend";

	public static final String[] RULES = { WEEKEND, DATE_RANGE, DAY_OF_WEEK, GAP_FILLER, EARLY_BIRD,
			LAST_MINUTE, LENGTH_OF_STAY, OCCUPANCY_ABOVE, OCCUPANCY_BELOW };

	private static final String SUN = "0";
	private static final String MON = "1";
	private static final String TUE = "2";
	private static final String WED = "3";
	private static final String THU = "4";
	private static final String FRI = "5";
	private static final String SAT = "6";
	public static final String[] DAYS = { SUN, MON, TUE, WED, THU, FRI, SAT };
	
	public static final int DAYS_OF_WEEKEND_SAT_SUN = 0;
	public static final int DAYS_OF_WEEKEND_FRI_SAT = 1;
	
	private static final Map<Integer, ArrayList<String>> WEEKEND_DAYS_MAP;
    static {
        WEEKEND_DAYS_MAP = new HashMap<Integer, ArrayList<String>>();
        WEEKEND_DAYS_MAP.put(DAYS_OF_WEEKEND_SAT_SUN, new ArrayList<String>() {{
            add(SAT);
            add(SUN);
        }});
        WEEKEND_DAYS_MAP.put(DAYS_OF_WEEKEND_FRI_SAT, new ArrayList<String>() {{
            add(FRI);
            add(SAT);
        }});
    }

    List<String> temp = new ArrayList<String>();
	
	private String entitytype;
	private String entityid;
	private String modifier;
	private Integer param;
	private Double amount;
	private Date fromdate;
	private Date todate;
	private Date version;

	public Yield() {}

	public Yield(String entitytype, String entityid) {
		this.entitytype = entitytype;
		this.entityid = entityid;
	}

	public Yield(String entitytype, String entityid, Date fromdate, Date todate) {
		this.entitytype = entitytype;
		this.entityid = entityid;
		this.fromdate = fromdate;
		this.todate = todate;
	}

	public Service service() {return Service.PRICE;}

	public String getEntitytype() {
		return entitytype;
	}

	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}

	public boolean noEntitytype() {
		return entitytype == null || entitytype.isEmpty();
	}

	public String getEntityid() {
		return entityid;
	}

	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}

	public boolean noEntityid() {
		return entityid == null || entityid.isEmpty();
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public boolean noModifier() {
		return modifier == null || modifier.isEmpty();
	}

	public boolean hasModifier(String modifier) {
		return this.modifier.equalsIgnoreCase(modifier);
	}

	public Integer getParam() {
		return param;
	}

	public void setParam(Integer param) {
		this.param = param;
	}

	public boolean noParam() {
		return param == null || param <= 0;
	}

	public boolean hasParam() {
		return !noParam();
	}

	public boolean hasParam(Integer param) {
		return this.param == param;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public boolean noAmount() {
		return amount == null || amount == 0;
	}
	
	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
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

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public boolean noTodate() {
		return todate == null;
	}

	public boolean noDate() {
		return noFromdate() || noTodate() || getTodate().before(getFromdate());
	}

	public boolean hasDate() {
		return !noDate();
	}

	public boolean isInDateRange(Date date) {
		return date.compareTo(fromdate) >= 0 && date.compareTo(todate) <= 0;
	}
	
	public boolean isWeekend(Date date, Integer param) {
		ArrayList<String> daysOfWeekend = WEEKEND_DAYS_MAP.get(DAYS_OF_WEEKEND_SAT_SUN);
		if(param!=null && WEEKEND_DAYS_MAP.get(param)!=null){
			daysOfWeekend = WEEKEND_DAYS_MAP.get(param);
		}
		for(String day : daysOfWeekend){
			if(date.getDay() == Integer.parseInt(day)){
				return true;
			}
		}
		return false;
		
//		return ((date.getDay() == Integer.parseInt(Yield.DAYS[0])) 
//				|| (date.getDay() == Integer.parseInt(Yield.DAYS[6])));
	}

	public boolean isDayOfWeek(Date date) {
		return param == date.getDay();
	}
	
	

	public boolean isEarlyBird(Date date) {
		Date deadline = Time.addDuration(new Date(), param, Time.DAY);
		return date.after(deadline);
	}

	public boolean isGapFiller(boolean fillsGap, Integer stay) {
		return fillsGap && stay <= param;
	}

	public boolean isGapFiller() {
		return GAP_FILLER.equalsIgnoreCase(name);
	}

	public boolean isLastMinute(Date date) {
		Date deadline = Time.addDuration(new Date(), param, Time.DAY);
		return date.before(deadline);
	}

	public boolean isLengthOfStay(Integer stay) {
		return param <= stay;
	}

	public boolean isOccupancyAbove(Integer occupancy) {
		return param < occupancy;
	}

	public boolean isOccupancyBelow(Integer occupancy) {
		return param > occupancy;
	}

	public Double getValue(Double value) {
		if (hasModifier(INCREASE_AMOUNT)) {return value + amount;} 
		else if (hasModifier(DECREASE_AMOUNT)) {return value - amount;}
		else if (hasModifier(INCREASE_PERCENT)) {return value * (100.0 + amount) / 100.0;}
		else if (hasModifier(DECREASE_PERCENT)) {return value * (100.0 - amount) / 100.0;}
		else return value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Yield [entitytype=");
		builder.append(entitytype);
		builder.append(", entityid=");
		builder.append(entityid);
		builder.append(", modifier=");
		builder.append(modifier);
		builder.append(", param=");
		builder.append(param);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", fromdate=");
		builder.append(fromdate);
		builder.append(", todate=");
		builder.append(todate);
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
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((entityid == null) ? 0 : entityid.hashCode());
		result = prime * result + ((entitytype == null) ? 0 : entitytype.hashCode());
		result = prime * result + ((fromdate == null) ? 0 : fromdate.hashCode());
		result = prime * result + ((modifier == null) ? 0 : modifier.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((param == null) ? 0 : param.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((todate == null) ? 0 : todate.hashCode());
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
		Yield other = (Yield) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (entityid == null) {
			if (other.entityid != null)
				return false;
		} else if (!entityid.equals(other.entityid))
			return false;
		if (entitytype == null) {
			if (other.entitytype != null)
				return false;
		} else if (!entitytype.equals(other.entitytype))
			return false;
		if (fromdate == null) {
			if (other.fromdate != null)
				return false;
		} else if (!fromdate.equals(other.fromdate))
			return false;
		if (modifier == null) {
			if (other.modifier != null)
				return false;
		} else if (!modifier.equals(other.modifier))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (param == null) {
			if (other.param != null)
				return false;
		} else if (!param.equals(other.param))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (todate == null) {
			if (other.todate != null)
				return false;
		} else if (!todate.equals(other.todate))
			return false;
		return true;
	}
	
	
	
}
