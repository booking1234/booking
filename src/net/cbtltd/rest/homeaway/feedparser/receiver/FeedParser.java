package net.cbtltd.rest.homeaway.feedparser.receiver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.cbtltd.rest.common.utils.CommonUtils;
import net.cbtltd.rest.homeaway.HomeAwayUtils;
import net.cbtltd.rest.homeaway.feedparser.domain.Address;
import net.cbtltd.rest.homeaway.feedparser.domain.Images;
import net.cbtltd.rest.homeaway.feedparser.domain.Listing;
import net.cbtltd.rest.homeaway.feedparser.domain.ListingReservations;
import net.cbtltd.rest.homeaway.feedparser.domain.RatePeriod;
import net.cbtltd.rest.homeaway.feedparser.domain.RatePeriod.Rates;
import net.cbtltd.rest.homeaway.feedparser.domain.RatePeriods;
import net.cbtltd.rest.homeaway.feedparser.domain.RateType;
import net.cbtltd.rest.homeaway.feedparser.domain.Text;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Unit;

import org.apache.commons.lang3.StringUtils;

public abstract class FeedParser {
	public enum Version{
		VERSION3_3 
	}
	private IFeedReceiver receiver;
	
	private Version myVersion=Version.VERSION3_3;
	
	
	public abstract  void load() throws MalformedURLException;
	
	
	public List<Date> extractReservationDates(net.cbtltd.rest.homeaway.feedparser.domain.UnitWSD unit){
		if(unit.getReservations()==null||unit.getReservations().getReservation()==null||unit.getReservations().getReservation().size()==0) 
			return new ArrayList<Date>(0);
		List<net.cbtltd.rest.homeaway.feedparser.domain.Reservation> reservations=unit.getReservations().getReservation();
		return extractReservationDates(reservations);
	}
	public List<Date> extractReservationDates(
			List<net.cbtltd.rest.homeaway.feedparser.domain.Reservation> reservations) {
		List<Date> bookedDates=new ArrayList<Date>();
		for(net.cbtltd.rest.homeaway.feedparser.domain.Reservation reservation:reservations){
			Calendar startDate=reservation.getReservationDates().getBeginDate().toGregorianCalendar();
			Calendar endDate=reservation.getReservationDates().getEndDate().toGregorianCalendar();
			while(startDate.before(endDate))
			{
				bookedDates.add(startDate.getTime());
				startDate.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		return bookedDates;
	}
	public List<Price> extractPrice(Product product,
			net.cbtltd.rest.homeaway.feedparser.domain.UnitWSD unit) {
		if(unit.getRatePeriods()==null||unit.getRatePeriods().getRatePeriod()==null||unit.getRatePeriods().getRatePeriod().size()==0) 
			return new ArrayList<Price>(0);
		List<RatePeriod> rates=unit.getRatePeriods().getRatePeriod();
		return extractPrice(product, rates);
	}
	protected List<Price> extractPrice(Product product, List<RatePeriod> rates) {
		List<Price> priceList =new ArrayList<Price>();
		for(net.cbtltd.rest.homeaway.feedparser.domain.RatePeriod ratePeriodItr : rates){
			Calendar startDate=ratePeriodItr.getDateRange().getBeginDate().toGregorianCalendar();
			Calendar endDate=ratePeriodItr.getDateRange().getBeginDate().toGregorianCalendar();
			while(startDate.before(endDate)||startDate.equals(endDate))
			{
				Price price = new Price();
				price.setEntityid(product.getId());
				price.setPartyid(product.getAltpartyid());
				price.setAltid(product.getId());
				price.setPartyid(product.getAltpartyid());
				price.setType(NameId.Type.Reservation.name());
				price.setOrganizationid(product.getAltpartyid());
				price.setEntitytype(NameId.Type.Product.name());
				price.setAvailable(1);
				price.setFactor(1.0);
				price.setState(Price.CREATED);
				price.setCurrency(product.getCurrency());
				price.setDate( startDate.getTime());
				price.setTodate(startDate.getTime());
				price.setRule(HomeAwayUtils.getDayOfWeek(price.getDate()));
				net.cbtltd.rest.homeaway.feedparser.domain.Rate appliedRate=getRateForPrice(startDate, ratePeriodItr.getRates());
				if(appliedRate==null) { startDate.add(Calendar.DAY_OF_MONTH, 1); continue; }
				if (appliedRate.getRateType() == RateType.WEEKLY) {
					price.setName("Week Rates");
					price.setUnit(Unit.WEE);
				} else if (appliedRate.getRateType() == RateType.MONTHLY) {
					price.setName("Monthly Rate");
					price.setUnit(Unit.MON);
				} else {
					price.setName("Daily Rates");
					price.setUnit(Unit.DAY);
				}
				price.setCurrency(appliedRate.getAmount().getCurrency());
				price.setCost(appliedRate.getAmount().getValue().doubleValue());
				price.setMinStay(ratePeriodItr.getMinimumStay());
				priceList.add(price);
				startDate.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		return priceList;
	}
	private net.cbtltd.rest.homeaway.feedparser.domain.Rate getRateForPrice(Calendar c, Rates rates){
		boolean isWeekendDay=c.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||c.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY;
		if(rates==null||rates.getRate()==null||rates.getRate().size()==0) return null;
		if(rates.getRate().size()==1) return rates.getRate().get(0);
		for(net.cbtltd.rest.homeaway.feedparser.domain.Rate rate:rates.getRate()){
			switch(rate.getRateType()){
			case WEEKLY: return rate;
			case MONTHLY: return rate;
			case NIGHTLY_WEEKEND: 
			case WEEKEND: if(isWeekendDay) return rate; break;
			case NIGHTLY_WEEKDAY:
			case EXTRA_NIGHT: if(!isWeekendDay) return rate; break;
			}
		}
		if(rates.getRate().size()==1) return rates.getRate().get(0);
		return null;
	}
	public Set<String> extractUnitFeatures(List<net.cbtltd.rest.homeaway.feedparser.domain.UnitFeatureValue> list) {
		Set<String> features=new HashSet<String>();
		for(net.cbtltd.rest.homeaway.feedparser.domain.UnitFeatureValue feature:list){
			features.add( feature.getUnitFeatureName().getCode());
		}
		return features;
	}
	public Set<String> extractListFeatures(List<net.cbtltd.rest.homeaway.feedparser.domain.ListingFeatureValue> list) {
		Set<String> features=new HashSet<String>();
		for(net.cbtltd.rest.homeaway.feedparser.domain.ListingFeatureValue feature:list){
			features.add( feature.getListingFeatureName().getCode());
		}
		return features;
	}
	public List<net.cbtltd.shared.Image> extractImage(Product product, Images images) {
		List<net.cbtltd.shared.Image> imageList=new ArrayList<net.cbtltd.shared.Image>();
		net.cbtltd.shared.Image imageObj=null;
		for(net.cbtltd.rest.homeaway.feedparser.domain.Image image:images.getImage()){
			imageObj=new net.cbtltd.shared.Image();
			imageObj.setName(image.getExternalId());
			imageObj.setNotes(concatAll(image.getTitle().getTexts().getText()));
			imageObj.setUrl(image.getUri());
			imageList.add(imageObj);
	}
		return imageList;
	}
	
	public String extract(Address address) {
		StringBuilder builder=new StringBuilder();
		if(!StringUtils.isEmpty(address.getAddressLine1()))
		{
			builder.append(address.getAddressLine1()).append(",");
		}
		if(!StringUtils.isEmpty(address.getAddressLine2()))
		{
			builder.append(address.getAddressLine2()).append(",");
		}
		if(!StringUtils.isEmpty(address.getCity()))
		{
			builder.append(address.getCity()).append(",");
		}
		if(!StringUtils.isEmpty(address.getStateOrProvince()))
		{
			builder.append(address.getStateOrProvince()).append(",");
		}
		builder.append(address.getCountry().name()).append(" ").append( address.getPostalCode());
		return builder.length()>100?builder.substring(0, 99): builder.toString(); 
	}
	private <T> String concatAll(List<T> source){ return concatAll(source,""); }
	@SuppressWarnings("unchecked")
	private <T> String concatAll(List<T> source,String delimeter){
		if(source==null) return "";
		if(source.size()==0) return "";
		if(source.get(0) instanceof net.cbtltd.rest.homeaway.feedparser.domain.Text){
			return concatAllText((List<Text>) source,delimeter);
		}
		return "";
	}
	private  String concatAllText(List<net.cbtltd.rest.homeaway.feedparser.domain.Text> source,String delimeter){
		StringBuilder builder=new StringBuilder();
		Iterator<net.cbtltd.rest.homeaway.feedparser.domain.Text> iterator=source.iterator();
		net.cbtltd.rest.homeaway.feedparser.domain.Text obj=null;
		while(iterator.hasNext()){
			obj=iterator.next();
				builder.append( obj.getTextValue());
			if(iterator.hasNext()) builder.append(delimeter);
		}
		return builder.toString();
	}
	
	public Map<String,net.cbtltd.rest.homeaway.feedparser.domain.Reservations> loadReservation(String baseUrlFormat,String... altIds) throws MalformedURLException{
		String url;
		if(baseUrlFormat==null||altIds==null||altIds.length==0||baseUrlFormat.isEmpty()) return null;
		Map<String,net.cbtltd.rest.homeaway.feedparser.domain.Reservations> listings=new HashMap<String,net.cbtltd.rest.homeaway.feedparser.domain.Reservations>();
		ListingReservations reservationList; 
		for(String altId : altIds){
			if(altId.isEmpty()) continue;
			url=String.format(baseUrlFormat,altId);
			reservationList=loadReservation(new URL(url));
			listings.put(altId,reservationList.getReservations());
		}
		return listings;
	}
	public Map<String,Listing> loadProduct(String baseUrlFormat,String... altIds) throws MalformedURLException{
		String url;
		if(baseUrlFormat==null||altIds==null||altIds.length==0||baseUrlFormat.isEmpty()) return null;
		Map<String,Listing> listings=new HashMap<String,Listing>();
		for(String altId : altIds){
			if(altId.isEmpty()) continue;
			url=String.format(baseUrlFormat,altId);
			listings.put(altId,loadProduct(new URL(url)));
		}
		return listings;
	}
	public Listing loadProduct(URL url){
		Listing listing = CommonUtils.unmarshall(url, Listing.class);
		return listing;
	}
	public ListingReservations loadReservation(URL url){
		ListingReservations listing=CommonUtils.unmarshall(url,ListingReservations.class);
		return listing;
	}
	public Map<String,RatePeriods> loadPrice(String baseUrlFormat,String... altIds) throws MalformedURLException {
		String url;
		if(baseUrlFormat==null||altIds==null||altIds.length==0||baseUrlFormat.isEmpty()) return null;
		Map<String,RatePeriods> listings=new HashMap<String,RatePeriods>();
		for(String altId : altIds){
			if(altId.isEmpty()) continue;
			url=String.format(baseUrlFormat,altId);
			listings.put(altId, loadPrice(new URL(url)));
		}
		return listings;
	}

	public RatePeriods loadPrice(URL url) {
		RatePeriods listing=CommonUtils.unmarshall(url,RatePeriods.class);
		return listing;
	}

	/**
	 * @return the myVersion
	 */
	protected final Version getMyVersion() {
		return myVersion;
	}

	/**
	 * @param myVersion the myVersion to set
	 */
	protected final void setMyVersion(Version myVersion) {
		this.myVersion = myVersion;
	}


	/**
	 * @return the receiver
	 */
	protected final IFeedReceiver getReceiver() {
		return receiver;
	}


	/**
	 * @param receiver the receiver to set
	 */
	protected final void setReceiver(IFeedReceiver receiver) {
		this.receiver = receiver;
	}
	
}
