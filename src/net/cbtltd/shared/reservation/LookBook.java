/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.reservation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.HasState;
import net.cbtltd.shared.api.HasTableService;
import net.cbtltd.shared.api.IsPosition;

import com.google.gwt.maps.client.base.LatLng;

public class LookBook
implements HasTableService, HasState, IsPosition {

	public static final String CREATED = "Created";

	private String id; //defines row in schedule = productid
	private String organizationid;
	private String agentid;
	private String state;
	private Double latitude;
	private Double longitude;
	private Double distance; //kilometres
	private Boolean distanceunit = false; //false = KMT true = SMI
	private Date fromdate;
	private Date todate;
	private Boolean offline = false; //false = online true = both
	private Boolean special = false;
	private Integer specialmin;
	private Integer specialmax;
	private Integer count;
	private Boolean countunit = false; //person v bedroom
	private Boolean exactcount = false; //exact or minimum
	private Integer pricemin;
	private Integer pricemax;
	private Boolean priceunit; //false = night true = stay
	private String unit = Unit.DAY;
	private String currency;
	private Integer rating;
	private Integer discount;
	private String orderby;
	private int startrow = 0;
	private int numrows = Integer.MAX_VALUE;
	private ArrayList<String> collisions;
	private ArrayList<String> productids; //product selection
	protected HashMap<String, ArrayList<String>> attributes;

	public Service service() {return Service.RESERVATION;}

	public String getId() {
		return id;
	}

	public String getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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
		if (value == null){
			latitude = null;
			longitude = null;
		}
		else {
			latitude = value.getLatitude();
			longitude = value.getLongitude();
		}
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Boolean getDistanceunit() {
		return distanceunit;
	}

	public void setDistanceunit(Boolean distanceunit) {
		this.distanceunit = distanceunit;
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

	public Integer getDuration() {
		return Time.getDuration(fromdate, todate, Time.DAY).intValue();
	}

	public boolean hasDuration() {
		return getDuration() > 0;
	}

	public Boolean getSpecial() {
		return special;
	}

	public void setSpecial(Boolean special) {
		this.special = special;
	}

	public Integer getSpecialmin() {
		return specialmin;
	}

	public void setSpecialmin(Integer specialmin) {
		this.specialmin = specialmin;
	}

	public Integer getSpecialmax() {
		return specialmax;
	}

	public void setSpecialmax(Integer specialmax) {
		this.specialmax = specialmax;
	}

	public Boolean getOffline() {
		return offline;
	}

	public void setOffline(Boolean offline) {
		this.offline = offline;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Boolean getCountunit() {
		return countunit;
	}

	public void setCountunit(Boolean countunit) {
		this.countunit = countunit;
	}

	public Boolean getExactcount() {
		return exactcount;
	}

	public void setExactcount(Boolean exactcount) {
		this.exactcount = exactcount;
	}

	public Boolean getExactperson() {
		return exactcount && countunit;
	}

	public Boolean getExactroom() {
		return exactcount && !countunit;
	}

	public Boolean getMinimumperson() {
		return !exactcount && countunit;
	}

	public Boolean getMinimumroom() {
		return !exactcount && !countunit;
	}

	public Integer getQuotemin() {
		return pricemin;
	}

	public void setPricemin(Integer pricemin) {
		this.pricemin = pricemin;
	}

	public Integer getQuotemax() {
		return pricemax;
	}

	public void setPricemax(Integer pricemax) {
		this.pricemax = pricemax;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public ArrayList<String> getCollisions() {
		return collisions;
	}

	public void setCollisions(ArrayList<String> collisions) {
		this.collisions = collisions;
	}

	public void addCollisions(ArrayList<String> collisions) {
		if (this.collisions == null) {this.collisions = new ArrayList<String>();}
		this.collisions.addAll(collisions);
	}

	public void removeCollisions(ArrayList<String> collisions) {
		if (this.collisions != null) {this.collisions.removeAll(collisions);}
	}

	public boolean noCollisions() {
		return collisions == null || collisions.isEmpty();
	}

	public boolean hasCollisions() {
		return !noCollisions();
	}

	public String getCollisionlist() {
		return NameId.getCdlist(collisions);
	}

	public ArrayList<String> getProductids() {
		return productids;
	}

	public void setProductid(String productid) {
		productids = new ArrayList<String>();
		productids.add(productid);
	}

	public void setProductids(ArrayList<String> productids) {
		this.productids = productids;
	}

	public void removeProductids(ArrayList<String> productids) {
		if (this.productids != null && productids != null) {this.productids.removeAll(productids);}
	}

	public boolean noProductids() {
		return (productids == null || productids.isEmpty());
	}

	public boolean hasProductids() {
		return !noProductids();
	}

	public void resetProductids() {
		if (hasProductids() && hasCollisions()) {productids.removeAll(collisions);}
	}

	public String getProductlist() {
		return NameId.getCdlist(productids);
	}

	public HashMap<String, ArrayList<String>> getAttributes() {
		return attributes;
	}

	public void setAttributes(HashMap<String, ArrayList<String>> attributes) {
		this.attributes = attributes;
	}

	public boolean hasAttributes() {
		if (attributes == null) {
			return false;
		}
		for (String key : attributes.keySet()) {
			if (attributes.get(key).size() > 0) {
				return true;
			}
		}
		return false;
	}

	public boolean noAttributes() {
		return !hasAttributes();
	}

	public String getAttributelist() {
		return NameId.getCdlist(attributes);
	}

	public Integer getAttributecount() {
		return attributes == null ? 0 : attributes.size();
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

	private Double getAngle() {
		return distance == null ? 0.09 : distanceunit ? distance * 0.0144 : distance * 0.009; //360.0/40008.0; circumference of the earth
	}

	public Double getNelatitude() {
		return latitude == null ? null : latitude + getAngle();
	}

	public Double getNelongitude() {
		return longitude == null ? null : longitude + getAngle();
	}

	public Double getSwlatitude() {
		return latitude == null ? null : latitude - getAngle();
	}

	public Double getSwlongitude() {
		return longitude == null ? null : longitude - getAngle();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LookBook [service()=");
		builder.append(service());
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getOrganizationid()=");
		builder.append(getOrganizationid());
		builder.append(", getAgentid()=");
		builder.append(getAgentid());
		builder.append(", getState()=");
		builder.append(getState());
		builder.append(", getLatitude()=");
		builder.append(getLatitude());
		builder.append(", getLongitude()=");
		builder.append(getLongitude());
		//		builder.append(", getLatLng()=");
		//		builder.append(getLatLng());
		builder.append(", getDistance()=");
		builder.append(getDistance());
		builder.append(", getDistanceunit()=");
		builder.append(getDistanceunit());
		builder.append(", getFromdate()=");
		builder.append(getFromdate());
		builder.append(", getTodate()=");
		builder.append(getTodate());
		builder.append(", getDuration()=");
		builder.append(getDuration());
		builder.append(", hasDuration()=");
		builder.append(hasDuration());
		builder.append(", getSpecial()=");
		builder.append(getSpecial());
		builder.append(", getSpecialmin()=");
		builder.append(getSpecialmin());
		builder.append(", getSpecialmax()=");
		builder.append(getSpecialmax());
		builder.append(", getOffline()=");
		builder.append(getOffline());
		builder.append(", getCount()=");
		builder.append(getCount());
		builder.append(", getCountunit()=");
		builder.append(getCountunit());
		builder.append(", getExactcount()=");
		builder.append(getExactcount());
		builder.append(", getExactperson()=");
		builder.append(getExactperson());
		builder.append(", getExactroom()=");
		builder.append(getExactroom());
		builder.append(", getMinimumperson()=");
		builder.append(getMinimumperson());
		builder.append(", getMinimumroom()=");
		builder.append(getMinimumroom());
		builder.append(", getQuotemin()=");
		builder.append(getQuotemin());
		builder.append(", getQuotemax()=");
		builder.append(getQuotemax());
		builder.append(", getPriceunit()=");
		builder.append(getPriceunit());
		builder.append(", getUnit()=");
		builder.append(getUnit());
		builder.append(", getCurrency()=");
		builder.append(getCurrency());
		builder.append(", getRating()=");
		builder.append(getRating());
		builder.append(", getDiscount()=");
		builder.append(getDiscount());
		builder.append(", getCollisions()=");
		builder.append(getCollisions());
		builder.append(", noCollisions()=");
		builder.append(noCollisions());
		builder.append(", hasCollisions()=");
		builder.append(hasCollisions());
		builder.append(", getCollisionlist()=");
		builder.append(getCollisionlist());
		builder.append(", getProductids()=");
		builder.append(getProductids());
		builder.append(", noProductids()=");
		builder.append(noProductids());
		builder.append(", hasProductids()=");
		builder.append(hasProductids());
		builder.append(", getProductlist()=");
		builder.append(getProductlist());
		builder.append(", getAttributes()=");
		builder.append(getAttributes());
		builder.append(", hasAttributes()=");
		builder.append(hasAttributes());
		builder.append(", noAttributes()=");
		builder.append(noAttributes());
		builder.append(", getAttributelist()=");
		builder.append(getAttributelist());
		builder.append(", getAttributecount()=");
		builder.append(getAttributecount());
		builder.append(", getOrderby()=");
		builder.append(getOrderby());
		builder.append(", noOrderby()=");
		builder.append(noOrderby());
		builder.append(", getStartrow()=");
		builder.append(getStartrow());
		builder.append(", getNumrows()=");
		builder.append(getNumrows());
		builder.append(", getAngle()=");
		builder.append(getAngle());
		builder.append(", getNelatitude()=");
		builder.append(getNelatitude());
		builder.append(", getNelongitude()=");
		builder.append(getNelongitude());
		builder.append(", getSwlatitude()=");
		builder.append(getSwlatitude());
		builder.append(", getSwlongitude()=");
		builder.append(getSwlongitude());
		builder.append("]");
		return builder.toString();
	}
}