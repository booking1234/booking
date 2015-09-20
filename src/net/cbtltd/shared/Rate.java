/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import net.cbtltd.shared.api.HasItem;
import net.cbtltd.shared.api.HasService;

public class Rate
implements HasItem, HasService {

	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";
	public static final String RESPONDED = "Responded";
	public static final String RESOLVED = "Resolved";
	public static final String DISPUTED = "Disputed";
	public static final String[] STATES = { INITIAL, CREATED, RESPONDED,
		RESOLVED, DISPUTED, FINAL };

	public static final String RATING = "Rating";
	public static final String OVERALL = "Overall";
	public static final String VALUE = "Value";
	public static final String CHECK_IN = "Check-in";
	public static final String LOCATION = "Location";
	public static final String CLEANLINESS = "Cleanliness";
	public static final String COMFORT = "Comfort";
	public static final String SERVICE = "Service";
	public static final String[] RATING_LIST = { OVERALL, VALUE, CHECK_IN,
		LOCATION, CLEANLINESS, COMFORT, SERVICE };

	public static final String OPINION = "Opinion";
	public static final String FRIENDS_FAMILY = "Friends and Family";
	public static final String YOUNG_SINGLES = "Young Singles";
	public static final String FRIENDS_GETAWAY = "Friends Getaway";
	public static final String LARGE_GROUPS = "Large Groups";
	public static final String DISABILITIES = "People with Disabilities";
	public static final String OLDER = "Older Travellers";
	public static final String RUGGED = "Rugged Travellers";
	public static final String LUXURY = "Luxury Minded Travellers";
	public static final String YOUNG_CHILDREN = "Families with Young Children";
	public static final String TEENAGERS = "Families with Teenagers";
	public static final String TOURISTS = "Tourists";
	public static final String[] OPINION_LIST = { FRIENDS_FAMILY,
		YOUNG_SINGLES, FRIENDS_GETAWAY, LARGE_GROUPS, DISABILITIES, OLDER,
		RUGGED, LUXURY, YOUNG_CHILDREN, TEENAGERS, TOURISTS };

	public static final String REASON = "Reason";
	public static final String BEACH = "Beach / Sun";
	public static final String THEME = "Theme / Amusement Park";
	public static final String BUSINESS = "Business Meeting / Event";
	public static final String FOOD = "Great Food / Wine";
	public static final String GOLF = "Golf";
	public static final String SKIING = "Skiing / Winter Sports";
	public static final String MUSIC = "Concert / Music Festival";
	public static final String MUSEUM = "Museum / Cultural / Historical";
	public static final String GAMBLING = "Gambling / Casinos";
	public static final String OUTDOOR = "Outdoor / Adventure";
	public static final String SHOPPING = "Shopping";
	public static final String SPA = "Spa";
	public static final String SPORT = "Sporting Event";
	public static final String OTHER = "Other";
	public static final String[] REASON_LIST = { BEACH, THEME, BUSINESS, FOOD,
		GOLF, SKIING, MUSIC, MUSEUM, GAMBLING, OUTDOOR, SHOPPING, SPA,
		SPORT, OTHER };

	public static final String QUALITY = "Quality";
	public static final String QUIET = "Quiet";
	public static final String STAFF = "Incredible Staff";
	public static final String ELEGANT = "Elegant";
	public static final String COMFORTABLE = "Comfortable";
	public static final String HOT = "Hot Spot";
	public static final String COZY = "Cozy";
	public static final String TRENDY = "Trendy";
	public static final String PLUSH = "Plush";
	public static final String HIDDEN = "Hidden Gem";
	public static final String ROOMY = "Roomy";
	public static final String CHARMING = "Charming";
	public static final String ROMANTIC = "Romantic";
	public static final String NO_FRILLS = "No Frills";
	public static final String BEAUTIFUL = "Beautiful";
	public static final String[] QUALITY_LIST = { QUIET, STAFF, ELEGANT,
		COMFORTABLE, HOT, COZY, TRENDY, PLUSH, HIDDEN, ROOMY, CHARMING,
		ROMANTIC, NO_FRILLS, BEAUTIFUL };

	public static final String[] TYPES = { RATING, OPINION, REASON, QUALITY};

	protected String id;
	protected String eventid;
	protected String customerid;
	protected String productid;
	protected String type;
	protected String name;
	protected Integer quantity;

	public Rate() {	}

	public Rate(String name, String type, Integer quantity) {
		super();
		this.name = name;
		this.type = type;
		this.quantity = quantity;
	}

	public Rate(String name, String type, boolean value) {
		super();
		this.name = name;
		this.type = type;
		this.quantity = value ? 1 : 0;
	}

	public Service service() {return Service.RATE;}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean noId() {
		return id == null || id.isEmpty() || id.equals(Model.ZERO);
	}

	public String getEventid() {
		return eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public boolean noCustomerid() {
		return customerid == null || customerid.isEmpty()
		|| customerid == Model.ZERO;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public boolean noProductid() {
		return productid == null || productid.isEmpty()
		|| productid == Model.ZERO;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean hasType(String type) {
		return this.type != null && this.type.equals(type);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public boolean noQuantity() {
		return quantity == null || quantity < 0 || quantity >= 100;
	}

	public boolean hasQuantity() {
		return !noQuantity();
	}

	public boolean hasQuantity(int quantity) {
		return this.quantity == quantity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Rate [id=");
		builder.append(id);
		builder.append(", eventid=");
		builder.append(eventid);
		builder.append(", type=");
		builder.append(type);
		builder.append(", name=");
		builder.append(name);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append("]");
		return builder.toString();
	}
}
