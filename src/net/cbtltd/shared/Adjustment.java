package net.cbtltd.shared;

import java.util.Date;

public class Adjustment {

	public final static int MAX_STAY_VALUE = 999; 
	
	// service days		*use hex numbers
	public final static byte SUNDAY = 0x1;    // 00000001
	public final static byte MONDAY = 0x2;  // 00000010
	public final static byte TUESDAY = 0x4;    // 00000100
	public final static byte WEDNESDAY = 0x8;  // 00001000
	public final static byte THURSDAY = 0x10;// 00010000
	public final static byte FRIDAY = 0x20;  // 00100000
	public final static byte SATURDAY = 0x40;   // 01000000
	public final static byte WEEK = SUNDAY|MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY;
	public final static byte WEEKEND = SUNDAY|SATURDAY;
	
	// states
	public final static int Initial = 1;
	public final static int Created = 2;
	public final static int FINAL  = 3;
	
	protected int id;
	protected String productid;
	protected String partyid;
	protected Integer state;
	protected Date fromdate;
	protected Date todate;
	protected Double extra;
	protected Double fixprice;
	protected String currency;
	protected Integer minstay;
	protected Integer maxstay;
	protected Byte servicedays;
	protected Date version;
	
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Byte getServicedays() {
		return servicedays;
	}

	public void setServicedays(Byte servicedays) {
		this.servicedays = servicedays;
	}

	public String getProductID() {
		return productid;
	}
	
	public void setProductID(String productid) {
		this.productid = productid;
	}
	
	public String getPartyID() {
		return partyid;
	}

	public void setPartyID(String partyid) {
		this.partyid = partyid;
	}

	public Date getFromDate() {
		return fromdate;
	}
	
	public void setFromDate(Date fromdate) {
		this.fromdate = fromdate;
	}
	
	public Date getToDate() {
		return todate;
	}
	
	public void setToDate(Date todate) {
		this.todate = todate;
	}
	
	public Double getExtra() {
		return extra;
	}
	
	public void setExtra(Double extra) {
		this.extra = extra;
	}
	
	public Double getFixPrice() {
		return fixprice;
	}
	
	public void setFixPrice(Double fixPrice) {
		this.fixprice = fixPrice;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public Integer getMinStay() {
		return minstay;
	}
	
	public void setMinStay(Integer minStay) {
		this.minstay = minStay;
	}
	
	public Integer getMaxStay() {
		return maxstay;
	}
	
	public void setMaxStay(Integer maxStay) {
		this.maxstay = maxStay;
	}
	
	public Date getVersion() {
		return version;
	}
	
	public void setVersion(Date version) {
		this.version = version;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Adjustment [ID=");
		builder.append(id);
		builder.append(", ProductID=");
		builder.append(productid);
		builder.append(", PartyID=");
		builder.append(partyid);
		builder.append(", State=");
		builder.append(state);
		builder.append(", FromDate=");
		builder.append(fromdate);
		builder.append(", ToDate=");
		builder.append(todate);
		builder.append(", Extra=");
		builder.append(extra);
		builder.append(", FixPrice=");
		builder.append(fixprice);
		builder.append(", Currency=");
		builder.append(currency);
		builder.append(", MinStay=");
		builder.append(minstay);
		builder.append(", MaxStay=");
		builder.append(maxstay);
		builder.append(", Servicedays=");
		builder.append(servicedays);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((extra == null) ? 0 : extra.hashCode());
		result = prime * result + ((fixprice == null) ? 0 : fixprice.hashCode());
		result = prime * result + ((fromdate == null) ? 0 : fromdate.hashCode());
		result = prime * result + ((maxstay == null) ? 0 : maxstay.hashCode());
		result = prime * result + ((minstay == null) ? 0 : minstay.hashCode());
		result = prime * result + ((partyid == null) ? 0 : partyid.hashCode());
		result = prime * result + ((productid == null) ? 0 : productid.hashCode());
		result = prime * result + ((servicedays == null) ? 0 : servicedays.hashCode());
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
		Adjustment other = (Adjustment) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (extra == null) {
			if (other.extra != null)
				return false;
		} else if (!extra.equals(other.extra))
			return false;
		if (fixprice == null) {
			if (other.fixprice != null)
				return false;
		} else if (!fixprice.equals(other.fixprice))
			return false;
		if (fromdate == null) {
			if (other.fromdate != null)
				return false;
		} else if (!fromdate.equals(other.fromdate))
			return false;
		if (maxstay == null) {
			if (other.maxstay != null)
				return false;
		} else if (!maxstay.equals(other.maxstay))
			return false;
		if (minstay == null) {
			if (other.minstay != null)
				return false;
		} else if (!minstay.equals(other.minstay))
			return false;
		if (partyid == null) {
			if (other.partyid != null)
				return false;
		} else if (!partyid.equals(other.partyid))
			return false;
		if (productid == null) {
			if (other.productid != null)
				return false;
		} else if (!productid.equals(other.productid))
			return false;
		if (servicedays == null) {
			if (other.servicedays != null)
				return false;
		} else if (!servicedays.equals(other.servicedays))
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