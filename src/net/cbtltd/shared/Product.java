/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlTransient;

import net.cbtltd.shared.product.ProductValue;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class Product extends Entity implements ProductValue {

	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";
	public static final String SUSPENDED = "Suspended";
	public static final String PROCESSING = "Processing";
	public static final String UNLICENSED = "Unlicensed";
	
	// inquire states
	public static final String USE_API = "Use API";
	public static final String SEND_EMAIL = "Send e-mail";
	
	private static final String[] STATES = {INITIAL, CREATED, SUSPENDED, PROCESSING, FINAL};
	
	public enum Servicetype {NoService, DayofArrival, DayofDeparture, RefreshDay, LinenChange};
	
	public enum Type {Accommodation, Consumable, Inventory, Maintenance, Marketing};
	public enum Value {
		Brand,
		City,
		CleaningCost,
		Country,
		DamageFee,
		Elevator, //true if it has an elevator
		Floor,
		HalfBaths,
		InsuranceRate,
		KingBeds,
		Maxrentalprice,
		Minrentalprice,
		PropertyType,
		QueenBeds,
		Region,
		ResortTaxRate,
		SingleBeds,
		SlopeProximity,
		Smoking,
		SquareFeet,
		SquareMetre,
		TaxRate,
		ThreeQuarterBaths,
		Unit,
		YearBuilt
	};

	public static final String ACCOMMODATION_SIZE = "Accommodation Size";
	public static final String GRADING = "Grading";
	public static String[] ATTRIBUTE_TYPES = { ACCOMMODATION_SIZE, GRADING };
	public static final Double DEFAULT_SECUIRTY_DEPOSIT = 0.0; 
	public static final Double DEFAULT_CLEANING_FEE = 0.0;

	private String altsupplierid; 
	
	private String partofid;
	@XStreamOmitField
	private String ownerid;
	@XStreamAlias("pm-id")
	private String supplierid;
	@XStreamOmitField
	private String unspsc;
	@XStreamOmitField
	private String code;
	private String webaddress;
	private String physicaladdress;
	@XStreamOmitField
	private String tax;
	private String servicedays; //service weekdays Sum = 0
	
	private Integer room;
	private Integer bathroom;
	private Integer toilet;
	private Integer floor;
	private String space;
	private Integer quantity;
	private Integer person;
	private Integer child;
	private Integer infant;
	private Integer rating;
	private Integer linenchange; //frequency of linen change in days
	private Integer refresh; //frequency of refresh in days
	private Double commission; // manager's commission
	private Double discount; // standard agent's discount
	private Double securitydeposit; // property manager securitydeposit
	private Double cleaningfee; // property manager cleaning fee
	@XStreamOmitField
	private Boolean useonepricerow; //if property have more prices for one starting date. Also in that case date and toDate in price table must be used for start and end reservation date.
	private String inquireState;
	
	private String pmAbbrev;
	private String pmsAbbrev;

	private Boolean displayaddress=true;
	
	/**
	 * @return the displayaddress
	 */
	public Boolean getDisplayaddress() {
		return displayaddress;
	}

	/**
	 * @param displayaddress the displayaddress to set
	 */
	public void setDisplayaddress(Boolean displayaddress) {
		this.displayaddress = displayaddress;
	}

	public Double getCleaningfee() {
		return cleaningfee;
	}

	public void setCleaningfee(Double cleaningfee) {
		this.cleaningfee = cleaningfee;
	}


	public void setAltSupplierId(String altsupplierid){
		this.altsupplierid = altsupplierid;
	}
	public String getAltSupplierId(){
		return altsupplierid;
	}
	

	private Boolean assignedtomanager;
	public Boolean getAssignedtomanager() {
		return assignedtomanager;
	}

	public void setAssignedtomanager(Boolean assignedtomanager) {
		this.assignedtomanager = assignedtomanager;
	}

	private Double ownerdiscount; // special owner's discount
	private Double rank; // product rank, negative if off line
	private Boolean dynamicpricingenabled;
	private ArrayList<NameId> files;
	
	private String productImageRootLocation;

	public Product() {
		super(NameId.Type.Product.name());
		setRank(0.);
		setUseonepricerow(false);
		setInquireState(Product.SEND_EMAIL);
	}

	public Service service() {return Service.PRODUCT;}

	public boolean isValidState () {return hasState(STATES);}
	
	public boolean notValidState () {return !isValidState();}

	public String getPartofid() {
		return partofid;
	}

	public void setPartofid(String partofid) {
		this.partofid = partofid;
	}

	public boolean noPartofid() {
		return partofid == null || partofid.isEmpty();
	}
	
	public boolean hasPartofid() {
		return !noPartofid();
	}

	@Description(value = "Identity of party that owns the product", target = DocTarget.METHOD)
	public String getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}

	public boolean noOwnerid() {
		return ownerid == null || ownerid.trim().isEmpty();
	}
	
	public boolean hasOwnerid() {
		return !noOwnerid();
	}

	public boolean hasOwnerid(String ownerid) {
		return this.ownerid == null ? false : this.ownerid.equalsIgnoreCase(ownerid);
	}

	@Description(value = "Identity of party that supplies the product or manages the property", target = DocTarget.METHOD)
	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public boolean noSupplierid() {
		return supplierid == null || supplierid.trim().isEmpty();
	}
	
	public boolean hasSupplierid() {
		return !noSupplierid();
	}

	public boolean hasSupplierid(String supplierid) {
		return this.supplierid == null ? false : this.supplierid.equalsIgnoreCase(supplierid);
	}

	@Description(value = "The UNSPSC of the product", target = DocTarget.METHOD)
	public String getUnspsc() {
		return unspsc;
	}

	public void setUnspsc(String unspsc) {
		this.unspsc = unspsc;
	}

	@Description(value = "Product or bar code of the product", target = DocTarget.METHOD)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Description(value = "Web URL for product information", target = DocTarget.METHOD)
	public String getWebaddress() {
		return webaddress;
	}

	public void setWebaddress(String webaddress) {
		this.webaddress = webaddress;
	}

	@Description(value = "Physical or street address of the product", target = DocTarget.METHOD)
	public String getPhysicaladdress() {
		return physicaladdress;
	}

	public void setPhysicaladdress(String physicaladdress) {
		this.physicaladdress = physicaladdress;
	}

	@Description(value = "Physical or street address lines of the property", target = DocTarget.METHOD)
	public String[] getAddress() {
		return physicaladdress == null ? null : physicaladdress.split("[\\r\\n]+");
	}
	
	@Description(value = "Physical or street address lines of the property", target = DocTarget.METHOD)
	public String getFormattedAddress() {
		return physicaladdress == null ? null : physicaladdress.replaceAll("[\\t\\n\\r]+",", ");
	}

	public void setAddress(String[] address) {
		//
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getServicedays() {
		return servicedays;
	}

	public void setServicedays(String servicedays) {
		this.servicedays = servicedays;
	}

	public boolean noServicedays() {
		return servicedays == null || servicedays.equals("0000000");
	}
	
	public boolean hasServicedays() {
		return !noServicedays();
	}
	
	@Description(value = "Number of bedrooms in the property", target = DocTarget.METHOD)
	public Integer getRoom() {
		return room;
	}

	public void setRoom(Integer room) {
		this.room = room;
	}

	@Description(value = "Number of bathrooms in the property", target = DocTarget.METHOD)
	public Integer getBathroom() {
		return bathroom;
	}

	public void setBathroom(Integer bathroom) {
		this.bathroom = bathroom;
	}

	@Description(value = "Number of toilets in the property", target = DocTarget.METHOD)
	public Integer getToilet() {
		return toilet;
	}

	public void setToilet(Integer toilet) {
		this.toilet = toilet;
	}
	
	@Description(value = "Floor number of the property", target = DocTarget.METHOD)
	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	
	@Description(value = "Square space of the property", target = DocTarget.METHOD)
	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	@Description(value = "Number of units in the property", target = DocTarget.METHOD)
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public boolean noQuantity() {
		return quantity == null || quantity <= 1;
	}
	
	public boolean hasQuantity() {
		return !noQuantity();
	}
	
	@Description(value = "Maximum number of people (adults + children) allowed in each unit of the property", target = DocTarget.METHOD)
	public Integer getPerson() {
//		return person == null ? 0 : person + (child == null ? 0 : child);
		return person;
	}

	public void setPerson(Integer person) {
		this.person = person;
	}

	@Description(value = "Maximum number of adults allowed in each unit of the property", target = DocTarget.METHOD)
	public Integer getAdult() {
		return person;
	}

	public void setAdult(Integer person) {
		this.person = person;
	}

	@Description(value = "Maximum number of children allowed in each unit of the property", target = DocTarget.METHOD)
	public Integer getChild() {
		return child;
	}

	public void setChild(Integer child) {
		this.child = child;
	}

	@Description(value = "Maximum number of infants allowed in each unit of the property", target = DocTarget.METHOD)
	public Integer getInfant() {
		return infant;
	}

	public void setInfant(Integer infant) {
		this.infant = infant;
	}

	@Description(value = "Rating of the property in the range 1 to 10", target = DocTarget.METHOD)
	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	@XmlTransient
	public Integer getLinenchange() {
		return linenchange;
	}

	public void setLinenchange(Integer linenchange) {
		this.linenchange = linenchange;
	}

	@XmlTransient
	public Integer getRefresh() {
		return refresh;
	}

	public void setRefresh(Integer refresh) {
		this.refresh = refresh;
	}

	@XmlTransient
	public Double getCommissionValue() {
		return (commission == null) ? 0 : commission; 
	}
	
	@XmlTransient
	public Double getCommission() {
		return  commission; 
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}
	
	@XmlTransient
	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@XmlTransient
	public Double getOwnerdiscount() {
		return ownerdiscount;
	}

	public void setOwnerdiscount(Double ownerdiscount) {
		this.ownerdiscount = ownerdiscount;
	}

	@XmlTransient
	public Double getRank() {
		return rank;
	}

	public void setRank(Double rank) {
		this.rank = rank;
	}

	public boolean noRank() {
		return rank == null || rank < 0.0;
	}
	
	public boolean hasRank() {
		return !noRank();
	}

	@XmlTransient
	public Boolean getDynamicpricingenabled() {
		return dynamicpricingenabled;
	}

	public void setDynamicpricingenabled(Boolean dynamicpricingenabled) {
		this.dynamicpricingenabled = dynamicpricingenabled;
	}

	public Boolean getUseonepricerow() {
		return useonepricerow;
	}

	public void setUseonepricerow(Boolean useonepricerow) {
		this.useonepricerow = useonepricerow;
	}
	
	public Boolean IsUseonepricerow() {
		return (useonepricerow == null || !useonepricerow) ? false : true;
	}
	
	@XmlTransient
	public ArrayList<NameId> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<NameId> files) {
		this.files = files;
	}
	
	public Double getSecuritydeposit() {
		return securitydeposit;
	}

	public void setSecuritydeposit(Double securitydeposit) {
		this.securitydeposit = securitydeposit;
	}

	// ---------------------------------------------------------------------------------
	// Private text is specific to an organization
	// ---------------------------------------------------------------------------------
	//NOT A GETTER SO DO NOT ANNOTATE
	public static String getPrivateId(String organizationid, String id) {
		return organizationid + NameId.Type.Product.name() + id;
	}

	@XmlTransient
	public String getPrivateLabel() {
		return "Private Notes"; // make multilingual
	}

	public Text getPrivateText(String organizationid) {
		return getText(organizationid, NameId.Type.Product, id);
	}

	public void setPrivateText(String organizationid, Text value) {
		setText(organizationid, NameId.Type.Product, id, value);
	}

	// ---------------------------------------------------------------------------------
	// Public text 
	// ---------------------------------------------------------------------------------
	public String getPublicId() {
		return id == null || id.isEmpty() ? null : NameId.Type.Product.name() + id + Text.Code.Public.name();
	}

	public String getPublicLabel() {
		return "Description"; // make multilingual
	}

	@XmlTransient
	public Text getPublicText() {
		return getText(NameId.Type.Product, id, Text.Code.Public);
	}

	public void setPublicText(Text value) {
		setText(NameId.Type.Product, id, Text.Code.Public, value);
	}

	// ---------------------------------------------------------------------------------
	// Inside text 
	// ---------------------------------------------------------------------------------
	public String getInsideId() {
		return id == null || id.isEmpty() ? null : NameId.Type.Product.name() + id + Text.Code.Inside.name();
	}

	// ---------------------------------------------------------------------------------
	// Outside text 
	// ---------------------------------------------------------------------------------
	public String getOutsideId() {
		return id == null || id.isEmpty() ? null : NameId.Type.Product.name() + id + Text.Code.Outside.name();
	}

	// ---------------------------------------------------------------------------------
	// Product check in text
	// ---------------------------------------------------------------------------------
	public String getCheckinId() {
		return id == null || id.isEmpty() ? null : NameId.Type.Product.name() + id + Text.Code.Checkin.name();
	}

	public String getCheckinLabel() {
		return "Check In Instructions"; // make multilingual
	}

	@XmlTransient
	public Text getCheckinText() {
		return getText(NameId.Type.Product, id, Text.Code.Checkin);
	}

	public void setCheckinText(Text value) {
		setText(NameId.Type.Product, id, Text.Code.Checkin, value);
	}

	// ---------------------------------------------------------------------------------
	// Product contents text
	// ---------------------------------------------------------------------------------
	public String getContentsId() {
		return id == null || id.isEmpty() ? null : NameId.Type.Product.name() + id + Text.Code.Contents.name();
	}

	public String getContentsLabel() {
		return "What's Included"; // make multilingual
	}

	@XmlTransient
	public Text getContentsText() {
		return getText(NameId.Type.Product, id, Text.Code.Contents);
	}

	public void setContentsText(Text value) {
		setText(NameId.Type.Product, id, Text.Code.Contents, value);
	}

	// ---------------------------------------------------------------------------------
	// Product options text
	// ---------------------------------------------------------------------------------
	public String getOptionsId() {
		return id == null || id.isEmpty() ? null : NameId.Type.Product.name() + id + Text.Code.Options.name();
	}

	public String getOptionsLabel() {
		return "Available Options"; // make multilingual
	}

	@XmlTransient
	public Text getOptionsText() {
		return getText(NameId.Type.Product, id, Text.Code.Options);
	}

	public void setOptionsText(Text value) {
		setText(NameId.Type.Product, id, Text.Code.Options, value);
	}


	//---------------------------------------------------------------------------------
	// Conditions of use text 
	//---------------------------------------------------------------------------------
	public final String getConditionsId() {
		return id == null || id.isEmpty() ? null : NameId.Type.Product.name() + id + Text.Code.Condition.name();
	}

	public String getConditionsLabel() {
		return "Guest Conditions"; // make multilingual
	}

	@XmlTransient
	public Text getConditionsText() {
		return getText(NameId.Type.Product, id, Text.Code.Condition);
	}

	public void setConditionsText(Text value){
		setText(NameId.Type.Product, id, Text.Code.Condition, value);
	}

	//---------------------------------------------------------------------------------
	// Service text 
	//---------------------------------------------------------------------------------
	public static final String getServiceId(String id){
		return id == null || id.isEmpty() ? null : NameId.Type.Product.name() + id + Text.Code.Service.name();
	}

	public String getServiceLabel() {
		return "Service Description"; // make multilingual
	}

	@XmlTransient
	public Text getServiceText(){
		return getText(NameId.Type.Product, id, Text.Code.Service);
	}

	public void setServiceText(Text value, String type){
		setText(NameId.Type.Product, id, Text.Code.Service, value);
	}

	public String getPmAbbrev() {
		return pmAbbrev;
	}

	public void setPmAbbrev(String pmAbbrev) {
		this.pmAbbrev = pmAbbrev;
	}

	public String getPmsAbbrev() {
		return pmsAbbrev;
	}

	public void setPmsAbbrev(String pmsAbbrev) {
		this.pmsAbbrev = pmsAbbrev;
	}
	
	public String getProductImageRootLocation() {
		return productImageRootLocation;
	}

	public void setProductImageRootLocation(String productImageRootLocation) {
		this.productImageRootLocation = productImageRootLocation;
	}

	@XmlTransient
	@Description(value = "Product inquiry state", target = DocTarget.METHOD)
	public String getInquireState() {
		return inquireState;
	}

	public void setInquireState(String inquireState) {
		this.inquireState = inquireState;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [partofid=");
		builder.append(partofid);
		builder.append(", \nownerid=");
		builder.append(ownerid);
		builder.append(", \nsupplierid=");
		builder.append(supplierid);
		builder.append(", \nunspsc=");
		builder.append(unspsc);
		builder.append(", \ncode=");
		builder.append(code);
		builder.append(", \nwebaddress=");
		builder.append(webaddress);
		builder.append(", \nphysicaladdress=");
		builder.append(physicaladdress);
		builder.append(", \ntax=");
		builder.append(tax);
		builder.append(", \nservicedays=");
		builder.append(servicedays);
		builder.append(", \nroom=");
		builder.append(room);
		builder.append(", \nbathroom=");
		builder.append(bathroom);
		builder.append(", \ntoilet=");
		builder.append(toilet);
		builder.append(", \nfloor=");
		builder.append(floor);
		builder.append(", \nquantity=");
		builder.append(quantity);
		builder.append(", \nperson=");
		builder.append(person);
		builder.append(", \nchild=");
		builder.append(child);
		builder.append(", \ninfant=");
		builder.append(infant);
		builder.append(", \nrating=");
		builder.append(rating);
		builder.append(", \nlinenchange=");
		builder.append(linenchange);
		builder.append(", \nrefresh=");
		builder.append(refresh);
		builder.append(", \ncommission=");
		builder.append(commission);
		builder.append(", \ndiscount=");
		builder.append(discount);
		builder.append(", \nownerdiscount=");
		builder.append(ownerdiscount);
		builder.append(", \nrank=");
		builder.append(rank);
		builder.append(", \ndynamicpricingenabled=");
		builder.append(dynamicpricingenabled);
		builder.append(", \nuseonepricerow=");
		builder.append(useonepricerow);
		builder.append(", \nfiles=");
		builder.append(files);
		builder.append(", \ntype=");
		builder.append(type);
		builder.append(", \nentitytype=");
		builder.append(entitytype);
		builder.append(", \nlocationid=");
		builder.append(locationid);
		builder.append(", \ncurrency=");
		builder.append(currency);
		builder.append(", \nunit=");
		builder.append(unit);
		builder.append(", \nlatitude=");
		builder.append(latitude);
		builder.append(", \nlongitude=");
		builder.append(longitude);
		builder.append(", \naltitude=");
		builder.append(altitude);
		builder.append(", \ntaxes=");
		builder.append(taxes);
		builder.append(", \norganizationid=");
		builder.append(organizationid);
		builder.append(", \nstatus=");
		builder.append(status);
		builder.append(", \nstate=");
		builder.append(state);
		builder.append(", \nvalues=");
		builder.append(values);
		builder.append(", \nattributes=");
		builder.append(attributemap);
		builder.append(", \ntexts=");
		builder.append(texts);
		builder.append(", \nimages=");
		builder.append(imageurls);
		builder.append(", \nname=");
		builder.append(name);
		builder.append(", \nid=");
		builder.append(id);
		builder.append(", \nservice()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}
}

// public static String APPLIANCE = "Appliance";
// public static String CLOTHING = "Clothing";
// public static String EDUCATION = "Education";
// public static String ENTERTAINMENT = "Entertainment";
// public static String EQUIPMENT = "Equipment";
// public static String FOOD = "FoodBeverage";
// public static String FINANCIAL = "Financial";
// public static String FURNISHING = "Furnishing";
// public static String HEALTHCARE = "Healthcare";
// public static String HOUSEKEEPING = "Housekeeping";
// public static String INFOTECH = "Infotech";
// public static String MAINTENANCE = "Maintenance";
// public static String MARKETING = "Marketing";
// public static String PAPER = "PaperPrint";
// public static String PROFESSIONAL = "Professional";
// public static String SPORTING = "Sporting";
// public static String TRAVEL = "Travel";
// public static String UTILITY = "Utility";
// public static String VEHICLE = "Vehicle";
// public static String[] TYPES = {ACCOMMODATION, APPLIANCE, CLOTHING,
// EDUCATION, ENTERTAINMENT,
// EQUIPMENT, FOOD, FINANCIAL, FURNISHING, HEALTHCARE, HOUSEKEEPING, INFOTECH,
// MAINTENANCE, MARKETING, PAPER, PROFESSIONAL, SPORTING, TRAVEL, UTILITY,
// VEHICLE};
// productTypes = Accommodation, Appliance, Clothing, Education, Entertainment,
// Equipment,\
// Food & Beverage, Financial, Furnishing, Healthcare, Housekeeping, Infotech,\
// Maintenance, Marketing, Paper & Print, Professional, Sporting, Travel,
// Utility, Vehicle

