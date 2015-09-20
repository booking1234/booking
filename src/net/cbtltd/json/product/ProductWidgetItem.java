package net.cbtltd.json.product;

import net.cbtltd.json.JSONResponse;

public class ProductWidgetItem implements JSONResponse{

	private String code;
	private String id;
	private String name;
	private String locationid;
	private String locationname;
	private String ownerid;
	private String ownername;
	private String partofid;
	private String partofname;
	private String physicaladdress;
	private String servicedays; // service weekdays Sum = 0
	private String supplierid;
	private String suppliername;
	private String tax;
	private String unspsc;
	private String webaddress;
	private Integer room;
	private Integer bathroom;
	private Integer toilet;
	private Integer quantity;
	private Integer person;
	private Integer child;
	private Integer baby;
	private Integer rating;
	private Integer linenchange; // frequency of linen change in days
	private Integer refresh; // frequency of refresh in days
	private Double commission; // manager's commission
	private Double discount; // standard agent's discount
	private Double ownerdiscount; // special owner's discount
	private Double rank; // product rank, negative if off line
	private Double latitude;
	private Double longitude;
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public String getLocationname() {
		return locationname;
	}

	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}

	public String getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}

	public String getOwnername() {
		return ownername;
	}

	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}

	public String getPartofid() {
		return partofid;
	}

	public void setPartofid(String partofid) {
		this.partofid = partofid;
	}

	public String getPartofname() {
		return partofname;
	}

	public void setPartofname(String partofname) {
		this.partofname = partofname;
	}

	public String getPhysicaladdress() {
		return physicaladdress;
	}

	public void setPhysicaladdress(String physicaladdress) {
		this.physicaladdress = physicaladdress;
	}

	public String getServicedays() {
		return servicedays;
	}

	public void setServicedays(String servicedays) {
		this.servicedays = servicedays;
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getUnspsc() {
		return unspsc;
	}

	public void setUnspsc(String unspsc) {
		this.unspsc = unspsc;
	}

	public String getWebaddress() {
		return webaddress;
	}

	public void setWebaddress(String webaddress) {
		this.webaddress = webaddress;
	}

	public Integer getRoom() {
		return room;
	}

	public void setRoom(Integer room) {
		this.room = room;
	}

	public Integer getBathroom() {
		return bathroom;
	}

	public void setBathroom(Integer bathroom) {
		this.bathroom = bathroom;
	}

	public Integer getToilet() {
		return toilet;
	}

	public void setToilet(Integer toilet) {
		this.toilet = toilet;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getPerson() {
		return person;
	}

	public void setPerson(Integer person) {
		this.person = person;
	}

	public Integer getChild() {
		return child;
	}

	public void setChild(Integer child) {
		this.child = child;
	}

	public Integer getBaby() {
		return baby;
	}

	public void setBaby(Integer baby) {
		this.baby = baby;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Integer getLinenchange() {
		return linenchange;
	}

	public void setLinenchange(Integer linenchange) {
		this.linenchange = linenchange;
	}

	public Integer getRefresh() {
		return refresh;
	}

	public void setRefresh(Integer refresh) {
		this.refresh = refresh;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getOwnerdiscount() {
		return ownerdiscount;
	}

	public void setOwnerdiscount(Double ownerdiscount) {
		this.ownerdiscount = ownerdiscount;
	}

	public Double getRank() {
		return rank;
	}

	public void setRank(Double rank) {
		this.rank = rank;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductWidgetItem [code=");
		builder.append(code);
		builder.append(", id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", locationid=");
		builder.append(locationid);
		builder.append(", locationname=");
		builder.append(locationname);
		builder.append(", ownerid=");
		builder.append(ownerid);
		builder.append(", ownername=");
		builder.append(ownername);
		builder.append(", partofid=");
		builder.append(partofid);
		builder.append(", partofname=");
		builder.append(partofname);
		builder.append(", physicaladdress=");
		builder.append(physicaladdress);
		builder.append(", servicedays=");
		builder.append(servicedays);
		builder.append(", supplierid=");
		builder.append(supplierid);
		builder.append(", suppliername=");
		builder.append(suppliername);
		builder.append(", tax=");
		builder.append(tax);
		builder.append(", unspsc=");
		builder.append(unspsc);
		builder.append(", webaddress=");
		builder.append(webaddress);
		builder.append(", room=");
		builder.append(room);
		builder.append(", bathroom=");
		builder.append(bathroom);
		builder.append(", toilet=");
		builder.append(toilet);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", person=");
		builder.append(person);
		builder.append(", child=");
		builder.append(child);
		builder.append(", baby=");
		builder.append(baby);
		builder.append(", rating=");
		builder.append(rating);
		builder.append(", linenchange=");
		builder.append(linenchange);
		builder.append(", refresh=");
		builder.append(refresh);
		builder.append(", commission=");
		builder.append(commission);
		builder.append(", discount=");
		builder.append(discount);
		builder.append(", ownerdiscount=");
		builder.append(ownerdiscount);
		builder.append(", rank=");
		builder.append(rank);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}

}
