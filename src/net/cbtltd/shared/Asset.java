/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.Date;

public class Asset extends Entity {

	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";
	public static final String SUSPENDED = "Suspended";
	public static final String[] STATES = { INITIAL, CREATED, SUSPENDED, FINAL };

	public static enum Type {
		Appliance, Artifact, Building, Equipment, Food_Beverage, Furniture, Kitchenware, Tableware, Toiletries, Towels_Linen
	}

	public static final String[] TYPES = {
		Type.Appliance.name(), 
		Type.Artifact.name(), 
		Type.Building.name(), 
		Type.Equipment.name(), 
		Type.Food_Beverage.name(), 
		Type.Furniture.name(), 
		Type.Kitchenware.name(), 
		Type.Tableware.name(), 
		Type.Toiletries.name(), 
		Type.Towels_Linen.name()
	};

	public static final String TYPE = "type"; //because is in asset table
	public static final String CODE = "code";
	public static final String PLACE = "place";
	public static final String DATEACQUIRED = "dateacquired";
	public static final String DATEDISPOSED = "datedisposed";
	public static final String QUANTITY = "quantity";
	public static final String COST = "cost";
	public static final String PRICE = "price";

	private String parentid;
	private String ownerid;
	private String supplierid;
	private String depreciationid;
	private String code;
	private String place;
	private String notes;
	private Date dateacquired;
	private Date datedisposed;
	private Integer quantity;
	private Double cost;
	private Double price;

	public Asset() {
		super(NameId.Type.Asset.name());
	}

	public Service service() {
		return Service.ASSET;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public String getDepreciationid() {
		return depreciationid;
	}

	public void setDepreciationid(String depreciationid) {
		this.depreciationid = depreciationid;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotes(int length) {
		return NameId.trim(notes, length);
	}

	public Date getDateacquired() {
		return dateacquired;
	}

	public void setDateacquired(Date dateacquired) {
		this.dateacquired = dateacquired;
	}

	public Date getDatedisposed() {
		return datedisposed;
	}

	public void setDatedisposed(Date datedisposed) {
		this.datedisposed = datedisposed;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	// ---------------------------------------------------------------------------------
	// Public text 
	// ---------------------------------------------------------------------------------
//	public static String getPublicId(String id) {
//		return NameId.Type.Asset.name() + id + Text.Type.Public.name();
//	}
//
//	public String getPublicId() {
//		return getPublicId(id);
//	}
//
//	public Text getPublicText() {
//		return getText(getPublicId(id));
//	}
//
//	public void setPublicText(Text value) {
//		setText(getPublicId(id), value);
//	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Asset [parentid=");
		builder.append(parentid);
		builder.append(", ownerid=");
		builder.append(ownerid);
		builder.append(", supplierid=");
		builder.append(supplierid);
		builder.append(", depreciationid=");
		builder.append(depreciationid);
		builder.append(", code=");
		builder.append(code);
		builder.append(", place=");
		builder.append(place);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", dateacquired=");
		builder.append(dateacquired);
		builder.append(", datedisposed=");
		builder.append(datedisposed);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", cost=");
		builder.append(cost);
		builder.append(", price=");
		builder.append(price);
		builder.append(", entitytype=");
		builder.append(entitytype);
		builder.append(", locationid=");
		builder.append(locationid);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", altitude=");
		builder.append(altitude);
		builder.append(", images=");
		builder.append(imageurls);
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
}