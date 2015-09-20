/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.cbtltd.json.JSONService;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.HasPrice;
import net.cbtltd.shared.api.HasXsl;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

@XmlRootElement(name = "quote")
@Description(value = "Standard and special prices", target = DocTarget.RESPONSE)
public class Quote implements HasPrice, HasXsl {
	
	// url ��������, �����, ����, managed by, sleeps.
	
	private static final String SUGGESTED_BY_CHECK_IN = "checkinday";
	private static final String SUGGESTED_BY_MIN_STAY = "minstay";
	
	private String altpartyid;
	private String locationid;
	private String productid;
	private String productname;
	private String bedroom;
	private String bathroom;
	private String guests;
	private Integer minstay;
	private String arrive;
	private String depart;
	private Double nightlyrate;
	private String rack;
	private Double quote;
	private Double extra;
	private Integer quantity;
	private String taxrate;
	private String sto;
	private String deposit;
	private String currency;
	private String alert;
	private String terms;
	private String message;
	private String xsl;
	// new
	private Double agentCommission;
	private Double agentCommissionValue;
	private String pictureLocation;
	private Integer picturesQuantity;
	private String address;
	private String managerName;
	private String latitude;
	private String longitude;
	private String productClassType;
	private String pricecheckindayrule;
	private Boolean exactmatch;
	private Boolean inquiryOnly;
	private List<String> suggestedby;
	//attributes
	private List<String> attributes;
	
	private ArrayList<net.cbtltd.shared.Price> quotedetail;
	
	private Integer guestCount;
	
	public Quote() {
		super();
	}
	
	public Quote(String message) {
		super();
		this.message = message;
	}
	
	public Quote(String productid, 
			String arrive, 
			String depart, 
			String currency,  
			String message) {
		super();
		this.productid = productid;
		this.arrive = arrive;
		this.depart = depart;
		this.currency = currency;
		this.message = message;
		this.xsl = Constants.NO_XSL;
	}

	public Quote(String altpartyid, String locationid, String productid, String productname, String arrive, String depart, String checkindayrule,
			Integer minstay, String rack, String quote, String sto, String agentCommission, String agentCommissionValue, String extra, String deposit, 
			Integer quantity, String currency, String alert, String terms, String taxrate, ArrayList<net.cbtltd.shared.Price> quotedetail, String message, 
				String xsl) {
		super();
		this.altpartyid = altpartyid;
		this.locationid = locationid;
		this.productid = productid;
		this.productname = productname;
		this.arrive = arrive;
		this.depart = depart;
		this.pricecheckindayrule = checkindayrule;
		this.minstay = minstay;
		this.rack = rack;
		this.quote = quote == null ? null : Double.valueOf(quote);
		this.sto = sto;
		this.agentCommission = agentCommission == null ? null : Double.valueOf(agentCommission);
		this.agentCommissionValue = agentCommissionValue == null ? null : Double.valueOf(agentCommissionValue);
		this.extra = extra == null ? null : Double.valueOf(extra);
		this.deposit = deposit;
		this.quantity = quantity;
		this.currency = currency;
		this.alert = alert;
		this.terms = terms;
		this.taxrate = taxrate;
		this.quotedetail = quotedetail;
		this.message = message;
		this.xsl = xsl;
		this.attributes = new ArrayList<String>();
	}
	

	@XmlTransient
	public String getAltpartyid() {
		return altpartyid;
	}

	public void setAltpartyid(String altpartyid) {
		this.altpartyid = altpartyid;
	}

	public final boolean noAltpartyid() {
		return this.altpartyid == null || altpartyid.isEmpty();
	}

	public final boolean hasAltpartyid() {
		return !noAltpartyid();
	}

	@XmlTransient
	public String getReservationid() {
		return null;
	}

	@Description(value = "Location ID of the price", target = DocTarget.METHOD)
	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	@Description(value = "Product ID of the price", target = DocTarget.METHOD)
	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	@Description(value = "Product name of the price", target = DocTarget.METHOD)
	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}		

	public String getBedroom() {
		return bedroom;
	}
	

	public void setBedroom(String bedroom) {
		this.bedroom = bedroom;
	}
	

	public String getBathroom() {
		return bathroom;
	}
	

	public void setBathroom(String bathroom) {
		this.bathroom = bathroom;
	}
	

	public String getGuests() {
		return guests;
	}
	

	public void setGuests(String guests) {
		this.guests = guests;
	}
	
	public Integer getMinstay() {
		return minstay;
	}

	public void setMinstay(Integer mainstay) {
		this.minstay = mainstay;
	}

	public Boolean getExactmatch() {
		return exactmatch;
	}

	public void setExactmatch(Boolean exactmatch) {
		this.exactmatch = exactmatch;
	}
	
	@Description(value = "Is product inquiry only", target = DocTarget.METHOD)
	public Boolean getInquiryOnly() {
		return inquiryOnly;
	}
	
	public void setInquiryOnly(Boolean inquiryOnly) {
		this.inquiryOnly = inquiryOnly;
	}
	
	public List<String> getSuggestedby() {
		return suggestedby;
	}

	public void setSuggestedby(List<String> suggestedby) {
		this.suggestedby = suggestedby;
	}

	public void updateExactMatch() {
		Date date = null;
		Date departDate = null;
		try{
			date = JSONService.DF.parse(this.getArrive());
			departDate = JSONService.DF.parse(this.getDepart());
		}catch(ParseException ex){
			this.exactmatch = false;
			return;
		}
		
		if (date != null && departDate != null){
			Boolean checkInMatch = this.getPriceCheckInDayRule() == Price.EXACTMATCH ? true : false;
			Boolean minStayMatch = false;
			if (this.getMinstay() != null){
				minStayMatch = this.getMinstay() <= Days.daysBetween(new LocalDate(date), new LocalDate(departDate)).getDays() ? true : false;
				if (checkInMatch && minStayMatch){
					this.exactmatch = true;
					return;
				}
			}
			this.exactmatch = false;
			this.suggestedby  = new ArrayList<String>();
			if (!minStayMatch) {this.suggestedby.add(Quote.SUGGESTED_BY_MIN_STAY); }
			if (!checkInMatch) {this.suggestedby.add(Quote.SUGGESTED_BY_CHECK_IN); }
		}		
	}
	

	public String getManagerName() {
		return managerName;
	}
	

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	

	@Description(value = "Check in date for the price", target = DocTarget.METHOD)
	public String getCheckin() {
		return arrive;
	}

	public void setCheckin(String arrive) {
		this.arrive = arrive;
	}

	@Description(value = "Arrival date for the price", target = DocTarget.METHOD)
	public String getArrive() {
		return arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}

	@Description(value = "Check out date for the price", target = DocTarget.METHOD)
	public String getCheckout() {
		return depart;
	}
	
	public void setCheckout(String depart) {
		this.depart = depart;
	}

	@Description(value = "Departure date for the price", target = DocTarget.METHOD)
	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	@Description(value = "Nightly Rate", target = DocTarget.METHOD)
	public Double getNightlyrate() {
		return nightlyrate;
	}

	public void setNightlyrate(Double nightlyrate) {
		this.nightlyrate = nightlyrate;
	}

	@Description(value = "Rack rate or list price", target = DocTarget.METHOD)
	public String getRack() {
		return rack;
	}

	public void setRack(String rack) {
		this.rack = rack;
	}

	@Description(value = "Best available rate", target = DocTarget.METHOD)
	public String getBar() {
		return quote == null ? null : String.valueOf(NameId.round(quote));
	}

	public void setBar(String quote) {
		this.quote = quote == null ? null : Double.valueOf(quote);
	}

	@Description(value = "Quoted price", target = DocTarget.METHOD)
	public Double getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote == null ? null : Double.valueOf(quote);
	}

	@XmlTransient
	public Double getExtra() {
		return extra;
	}

	public void setExtra(Double extra) {
		this.extra = extra;
	}

	@Description(value = "Standard tourism operator rate", target = DocTarget.METHOD)
	public String getSto() {
		return sto;
	}

	public void setSto(String sto) {
		this.sto = sto;
	}
	
	public void updatePriceCheckInDayRule(){
		String[] daysArray = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		Date date = null;
		if (this.getPriceCheckInDayRule()!=null && !this.getPriceCheckInDayRule().isEmpty()){
			if (this.getPriceCheckInDayRule().equals(Price.Rule.AnyCheckIn.name())){
				this.setPriceCheckInDayRule(Price.EXACTMATCH);
				return;
			}
			if (this.getArrive() != null && !this.getArrive().isEmpty()){
				Calendar calendar =  Calendar.getInstance();			
				try{
					date = JSONService.DF.parse(this.getArrive());
					calendar.setTime(date);	
				}catch(ParseException ex){
					return;
				}
				if (date != null){									
					int day = calendar.get(Calendar.DAY_OF_WEEK);
					String checkinDay = Price.RULES[day-1];
					if (!checkinDay.equals(this.getPriceCheckInDayRule())){
						List<String> rulesList = Arrays.asList(Price.RULES);
						int index = rulesList.indexOf(this.getPriceCheckInDayRule());
						//0 - Sunday
						if (index >= 0 && index < daysArray.length){
							this.setPriceCheckInDayRule(daysArray[index]);	
						}
					}else { this.setPriceCheckInDayRule(Price.EXACTMATCH); }
				}
			}
		}
	}
	
	@Description(value = "Commission value in percent for standard tourism operator", target = DocTarget.METHOD)
	public Double getAgentCommission() {
		return agentCommission;
	}

	public void setAgentCommission(Double agentCommission) {
		this.agentCommission = agentCommission;		
	}
	
	@Description(value = "Commission value for standard tourism operator", target = DocTarget.METHOD)
	public Double getAgentCommissionValue() {
		return agentCommissionValue;
	}

	public void setAgentCommissionValue(Double agentCommissionValue) {
		this.agentCommissionValue = agentCommissionValue;
	}

	@Description(value = "Deposit required to confirm a reservation", target = DocTarget.METHOD)
	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	@Description(value = "Quantity of units available for the specified dates", target = DocTarget.METHOD)
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Description(value = "Currency code of the prices", target = DocTarget.METHOD)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean hasCurrency(String currency) {
		return this.currency != null && currency != null && this.currency.equalsIgnoreCase(currency);
	}

	public boolean notCurrency(String currency) {
		return !hasCurrency(currency);
	}

	@Description(value = "Alert(s) for the period of the quote", target = DocTarget.METHOD)
	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	@Description(value = "Terms and conditions which apply", target = DocTarget.METHOD)
	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	@Description(value = "Percentage tax rate", target = DocTarget.METHOD)
	public String getTaxrate() {
		return taxrate;
	}

	public void setTaxrate(String taxrate) {
		this.taxrate = taxrate;
	}

	@Description(value = "Detail of the quoted price", target = DocTarget.METHOD)
	public ArrayList<net.cbtltd.shared.Price> getQuotedetail() {
		return quotedetail;
	}

	public void setQuotedetail(ArrayList<net.cbtltd.shared.Price> quotedetail) {
		this.quotedetail = quotedetail;
	}

	@Description(value = "Diagnostic message in the response", target = DocTarget.METHOD)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getXsl() {
		return xsl;
	}

	public void setXsl(String xsl) {
		this.xsl = xsl;
	}

	@Override
	public String getAgentid() {
		return null;
	}

	@Override
	public boolean noAgentid() {
		return false;
	}

	@Override
	public void setPrice(Double value) {;
		rack = String.valueOf(NameId.round(value));
	}

	@Override
	public Double getPrice() {
		return rack == null ? null : Double.valueOf(rack);
	}

	@Override
	public boolean noPrice() {
		return rack == null;
	}

	@Override
	public void setQuote(Double value) {
		quote = value;
	}

	@Override
	public void setCost(Double value) {
		sto = String.valueOf(NameId.round(value));
	}

	@Override
	public Double getCost() {
		return sto == null ? null : Double.valueOf(sto);
	}

	@Override
	public void setPriceunit(Boolean total) {}

	@Override
	public Boolean getPriceunit() {
		return null;
	}

	@Override
	public void setSupplierid(String supplierid) {
	}

	@Override
	public String getSupplierid() {
		return null;
	}

	@Override
	public String getUnit() {
		return Unit.DAY;
	}

	@Override
	public Date getFromdate() {
		return Constants.parseDate(arrive);
	}

	@Override
	public Date getTodate() {
		return Constants.parseDate(depart);
	}

	@Override
	public Double getDuration(Time unit) {
		return Time.getDuration(getFromdate(), getTodate(), unit);
	}

	@Override
	public Integer getGuestCount() {
		return guestCount;
	}
	
	public void setGuestCount(Integer guestCount) {
		this.guestCount = guestCount;
	}

	public String getPictureLocation() {
		return pictureLocation;
	}

	public void setPictureLocation(String pictureLocation) {
		this.pictureLocation = pictureLocation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public List<String> getAttributes() {
		
		if (attributes == null){
			attributes = new ArrayList<String>();
		}
				
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}

	public String getProductClassType() {
		return productClassType;
	}

	public void setProductClassType(String productClassType) {
		this.productClassType = productClassType;
	}

	public Integer getPicturesQuantity() {
		return picturesQuantity;
	}

	public void setPicturesQuantity(Integer picturesSize) {
		this.picturesQuantity = picturesSize;
	}

	@XmlElement(name="CheckInDayRequired")
	public String getPriceCheckInDayRule() {
		return pricecheckindayrule;
	}

	public void setPriceCheckInDayRule(String priceCheckInDayRule) {
		this.pricecheckindayrule = priceCheckInDayRule;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Quote [locationid=");
		builder.append(locationid);
		builder.append(", productid=");
		builder.append(productid);
		builder.append(", productname=");
		builder.append(productname);
		builder.append(", bedroom=");
		builder.append(bedroom);
		builder.append(", bathroom=");
		builder.append(bathroom);
		builder.append(", guests=");
		builder.append(guests);
//		builder.append(", mainstay=");
//		builder.append(mainstay);
		builder.append(", managedby=");
		builder.append(managerName);		
		builder.append(", arrive=");
		builder.append(arrive);
		builder.append(", depart=");
		builder.append(depart);
		builder.append(", rack=");
		builder.append(rack);
		builder.append(", quote=");
		builder.append(quote);
		builder.append(", taxrate=");
		builder.append(taxrate);
		builder.append(", extra=");
		builder.append(extra);
		builder.append(", sto=");
		builder.append(sto);
		builder.append(", deposit=");
		builder.append(deposit);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", terms=");
		builder.append(terms);
		builder.append(", message=");
		builder.append(message);
		builder.append(", xsl=");
		builder.append(xsl);
		builder.append(", quotedetail=");
		builder.append(quotedetail);
		builder.append("]");
		return builder.toString();
	}

}
