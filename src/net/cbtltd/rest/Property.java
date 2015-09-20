package net.cbtltd.rest;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.cbtltd.rest.flipkey.Reservations;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerCancellationRule;
import net.cbtltd.shared.PropertyManagerSupportCC;
import net.cbtltd.server.config.RazorHostsConfig;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.mybookingpal.config.RazorConfig;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
@XStreamAlias("product")
@Description(value = "Product or property data", target = DocTarget.RESPONSE)
public class Property extends Product {
	private String city;
	private String region;
	private String country;
	private String description;
	private String contents;
	private String options;
	private String checkin;
	private String conditions;
	private String terms;
	private String message;
	private String xsl;
	private Boolean assignedtomanager;
	@XStreamOmitField
	private String minstay;
	private Attributes attributes;
	private String attributelist;
	private Images images;
	@XStreamOmitField
	private PriceTable pricetable;
	private PriceList pricelist;
	private PriceTable mandatory;
	private PriceTable feature;
	private Reviews reviews;
	@XStreamOmitField
	private Reservations reservations;
	private Boolean inquiryOnly;
	@XStreamAlias("updated")
	@XmlElement(name = "updated")
	private Date version;
	@XmlElement(name="supported_credit_card_types") 
	private PropertyManagerSupportCC supportedcreditcardtypes;
	@XmlElementWrapper(name="canellation_rules")
	@XmlElement(name="cancellation_rule") 
	private List<PropertyManagerCancellationRule> cancellationrules;

	public List<PropertyManagerCancellationRule> getCancellationrules() {
		return cancellationrules;
	}

	public void setCancellationrules(List<PropertyManagerCancellationRule> cancellationrules) {
		this.cancellationrules = cancellationrules;
	}

	public PropertyManagerSupportCC getSupportedcreditcardtypes() {
		return supportedcreditcardtypes;
	}

	public void setSupportedcreditcardtypes(PropertyManagerSupportCC supportedcreditcardtypes) {
		this.supportedcreditcardtypes = supportedcreditcardtypes;
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}
	 
	@Description(value = "Is product inquiry only", target = DocTarget.METHOD)
	public Boolean getInquiryOnly() {
		return inquiryOnly;
	}

	public void setInquiryOnly(Boolean inquiryOnly) {
		this.inquiryOnly = inquiryOnly;
	}

	@Description(value = "Reservation details of the property", target = DocTarget.METHOD)
	public Reservations getReservations() {
		return reservations;
	}

	public void setReservations(Reservations reservations) {
		this.reservations = reservations;
	}

	@Description(value = "Product or property identity", target = DocTarget.METHOD)
	public String getProductid() {
		return id;
	}

	public void setProductid(String productid) {
		this.id = productid;
	}

	@Description(value = "Minimum nights stay", target = DocTarget.METHOD)
	public String getMinstay() {
		return minstay;
	}

	public void setMinstay(String minstay) {
		this.minstay = minstay;
	}

	@Description(value = "City or other location of the property", target = DocTarget.METHOD)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean noCity() {
		return city == null || city.trim().isEmpty();
	}

	public boolean hasCity() {
		return !noCity();
	}

	@Description(value = "State, province, county or other region code of the property", target = DocTarget.METHOD)
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Description(value = "ISO country code of the property", target = DocTarget.METHOD)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean noCountry() {
		return country == null || country.trim().isEmpty();
	}

	public boolean hasCountry() {
		return !noCountry();
	}

	@Description(value = "Number of bedrooms in the property", target = DocTarget.METHOD)
	@XmlElement(required=true)
	public Integer getBedroom() {
		return getRoom();
	}

	public void setBedroom(Integer bedroom) {
		setRoom(bedroom);
	}
	
	@Description(value = "Standart agent commission", target = DocTarget.METHOD)
	@XmlElement(required=true)
	public Double getAgentCommissionValue() {
		return getDiscount();
	}

	public void setAgentCommissionValue(Double agentCommissionValue) {
		setDiscount(agentCommissionValue);
	}

	@Description(value = "Description of the product", target = DocTarget.METHOD)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean noDescription() {
		return description == null || description.trim().isEmpty();
	}

	public boolean hasDescription() {
		return !noDescription();
	}

	@Description(value = "Description of what is always included with the product", target = DocTarget.METHOD)
	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public boolean noContents() {
		return contents == null || contents.trim().isEmpty();
	}

	public boolean hasContents() {
		return !noContents();
	}

	@Description(value = "Description of what options are available with the product", target = DocTarget.METHOD)
	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public boolean noOptions() {
		return options == null || options.trim().isEmpty();
	}

	public boolean hasOptions() {
		return !noOptions();
	}

	@Description(value = "Check in instructions for the property", target = DocTarget.METHOD)
	public String getCheckin() {
		return checkin;
	}

	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}

	public boolean noCheckin() {
		return checkin == null || checkin.trim().isEmpty();
	}

	public boolean hasCheckin() {
		return !noCheckin();
	}

	@Description(value = "Conditions of sale that apply to the customer", target = DocTarget.METHOD)
	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public boolean noConditions() {
		return conditions == null || conditions.trim().isEmpty();
	}

	public boolean hasConditions() {
		return !noConditions();
	}

	@Description(value = "Legal terms and conditions on which the product is offered", target = DocTarget.METHOD)
	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public boolean noTerms() {
		return terms == null || terms.trim().isEmpty();
	}

	public boolean hasTerms() {
		return !noTerms();
	}

	@Description(value = "Attributes of the product in key/values format", target = DocTarget.METHOD)
	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	@XmlTransient
	public String getAttributelist() {
		return attributelist;
	}

	// public void setAttributelist(String attributelist) {
	// this.attributelist = attributelist;
	// }

	@Description(value = "Image URLs of the product", target = DocTarget.METHOD)
	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}

	@Description(value = "Mandatory extra price(s) of the product for the specified dates and currency", target = DocTarget.METHOD)
	public PriceTable getMandatory() {
		return mandatory;
	}

	public void setMandatory(PriceTable mandatory) {
		this.mandatory = mandatory;
	}

	@Description(value = "Optional extra price(s) of the product for the specified dates and currency", target = DocTarget.METHOD)
	public PriceTable getFeature() {
		return feature;
	}

	public void setFeature(PriceTable feature) {
		this.feature = feature;
	}

	@Description(value = "Price table of the product", target = DocTarget.METHOD)
	public PriceTable getPricetable() {
		return pricetable;
	}

	public void setPricetable(PriceTable priceTable) {
		this.pricetable = priceTable;
	}
	
	@Description(value = "Price list of the product", target = DocTarget.METHOD)
	public PriceList getPricelist() {
		return pricelist;
	}

	public void setPricelist(PriceList pricelist) {
		this.pricelist = pricelist;
	}

	@Description(value = "URL for product attributes", target = DocTarget.METHOD)
	public String getAttribute() {
		return RazorHostsConfig.getRootUrl() + id + "/attribute";
	}

	@Description(value = "URL for product image URLs", target = DocTarget.METHOD)
	public String getImage() {
		return RazorHostsConfig.getRootUrl() + id + "/image";
	}

	@Description(value = "Guest reviews", target = DocTarget.METHOD)
	public Reviews getReviews() {
		return reviews;
	}

	public void setReviews(Reviews reviews) {
		this.reviews = reviews;
	}

	// ---------------------------------------------------------------------------------
	// Implements message
	// ---------------------------------------------------------------------------------
	@Description(value = "Diagnostic message in the response", target = DocTarget.METHOD)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	// ---------------------------------------------------------------------------------
	// Implements HasXsl interface
	// ---------------------------------------------------------------------------------
	public String getXsl() {
		return xsl;
	}

	public void setXsl(String xsl) {
		this.xsl = xsl;
	}



	public Boolean getAssignedtomanager() {
		return assignedtomanager;
	}

	public void setAssignedtomanager(Boolean assignedtomanager) {
		this.assignedtomanager = assignedtomanager;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Property [city=");
		builder.append(city);
		builder.append(", region=");
		builder.append(region);
		builder.append(", country=");
		builder.append(country);
		builder.append(", description=");
		builder.append(description);
		builder.append(", contents=");
		builder.append(contents);
		builder.append(", options=");
		builder.append(options);
		builder.append(", checkin=");
		builder.append(checkin);
		builder.append(", conditions=");
		builder.append(conditions);
		builder.append(", terms=");
		builder.append(terms);
		builder.append(", message=");
		builder.append(message);
		builder.append(", xsl=");
		builder.append(xsl);
		builder.append(", assignedtomanager=");
		builder.append(assignedtomanager);
		builder.append(", minstay=");
		builder.append(minstay);
		builder.append(", attributes=");
		builder.append(attributes);
		builder.append(", attributelist=");
		builder.append(attributelist);
		builder.append(", images=");
		builder.append(images);
		builder.append(", pricetable=");
		builder.append(pricetable);
		builder.append(", feature=");
		builder.append(feature);
		builder.append(", reviews=");
		builder.append(reviews);
		builder.append(", reservations=");
		builder.append(reservations);
		builder.append(", inquiryOnly=");
		builder.append(inquiryOnly);
		builder.append(", version=");
		builder.append(version);
		builder.append(", supportedcreditcardtypes=");
		builder.append(supportedcreditcardtypes);
		builder.append("]");
		return builder.toString();
	}

}
