package net.cbtltd.json.party;

import net.cbtltd.json.JSONResponse;

public class PartyWidgetItem implements JSONResponse {

	private String id;
	private String employerid;
	private String creatorid;
	private String financeid;
	private String locationid;
	private String jurisdictionid;
	private String name;
	private String state;
	private String extraname;
	private String identitynumber;
	private String taxnumber;
	private String postaladdress;
	private String postalcode;
	private String country;
	private String emailaddress;
	private String webaddress;
	private String dayphone;
	private String nightphone;
	private String faxphone;
	private String mobilephone;
	private String password;
	private String birthdate;
	private String currency;
	private String unit;
	private Double latitude;
	private Double longitude;
	private Double altitude;
	private Double commission;
	private Double discount;
	private String notes;
	private String terms;
	private String language;
	private String rank;
	private Boolean owner;
	private String message;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmployerid() {
		return employerid;
	}

	public void setEmployerid(String employerid) {
		this.employerid = employerid;
	}

	public String getCreatorid() {
		return creatorid;
	}

	public void setCreatorid(String creatorid) {
		this.creatorid = creatorid;
	}

	public String getFinanceid() {
		return financeid;
	}

	public void setFinanceid(String financeid) {
		this.financeid = financeid;
	}

	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public String getJurisdictionid() {
		return jurisdictionid;
	}

	public void setJurisdictionid(String jurisdictionid) {
		this.jurisdictionid = jurisdictionid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getExtraname() {
		return extraname;
	}

	public void setExtraname(String extraname) {
		this.extraname = extraname;
	}

	public String getIdentitynumber() {
		return identitynumber;
	}

	public void setIdentitynumber(String identitynumber) {
		this.identitynumber = identitynumber;
	}

	public String getTaxnumber() {
		return taxnumber;
	}

	public void setTaxnumber(String taxnumber) {
		this.taxnumber = taxnumber;
	}

	public String getPostaladdress() {
		return postaladdress;
	}

	public void setPostaladdress(String postaladdress) {
		this.postaladdress = postaladdress;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getWebaddress() {
		return webaddress;
	}

	public void setWebaddress(String webaddress) {
		this.webaddress = webaddress;
	}

	public String getDayphone() {
		return dayphone;
	}

	public void setDayphone(String dayphone) {
		this.dayphone = dayphone;
	}

	public String getNightphone() {
		return nightphone;
	}

	public void setNightphone(String nightphone) {
		this.nightphone = nightphone;
	}

	public String getFaxphone() {
		return faxphone;
	}

	public void setFaxphone(String faxphone) {
		this.faxphone = faxphone;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public Double getAltitude() {
		return altitude;
	}

	public void setAltitude(Double altitude) {
		this.altitude = altitude;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public Boolean getOwner() {
		return owner;
	}

	public void setOwner(Boolean owner) {
		this.owner = owner;
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
		builder.append("PartyWidgetItem [id=");
		builder.append(id);
		builder.append(", employerid=");
		builder.append(employerid);
		builder.append(", creatorid=");
		builder.append(creatorid);
		builder.append(", financeid=");
		builder.append(financeid);
		builder.append(", locationid=");
		builder.append(locationid);
		builder.append(", jurisdictionid=");
		builder.append(jurisdictionid);
		builder.append(", name=");
		builder.append(name);
		builder.append(", state=");
		builder.append(state);
		builder.append(", extraname=");
		builder.append(extraname);
		builder.append(", identitynumber=");
		builder.append(identitynumber);
		builder.append(", taxnumber=");
		builder.append(taxnumber);
		builder.append(", postaladdress=");
		builder.append(postaladdress);
		builder.append(", postalcode=");
		builder.append(postalcode);
		builder.append(", country=");
		builder.append(country);
		builder.append(", emailaddress=");
		builder.append(emailaddress);
		builder.append(", webaddress=");
		builder.append(webaddress);
		builder.append(", dayphone=");
		builder.append(dayphone);
		builder.append(", nightphone=");
		builder.append(nightphone);
		builder.append(", faxphone=");
		builder.append(faxphone);
		builder.append(", mobilephone=");
		builder.append(mobilephone);
		builder.append(", password=");
		builder.append(password);
		builder.append(", birthdate=");
		builder.append(birthdate);
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
		builder.append(", commission=");
		builder.append(commission);
		builder.append(", discount=");
		builder.append(discount);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", terms=");
		builder.append(terms);
		builder.append(", language=");
		builder.append(language);
		builder.append(", rank=");
		builder.append(rank);
		builder.append(", owner=");
		builder.append(owner);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}

}
