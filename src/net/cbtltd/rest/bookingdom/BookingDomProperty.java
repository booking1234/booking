package net.cbtltd.rest.bookingdom;

import java.util.ArrayList;

public class BookingDomProperty {
	private String id;
	private String name;
	private String address;
	private String internalAddress;
	private String floor;
	private int rating;
	private String url;
	private String shortDescription;
	private String longDescription;
	private double latitude;
	private double longitude;
	private int maxPersion;
	private int bedrooms;
	private int bathrooms;
	private int numSingleBeds;
	private int numDoubleBeds;
	private int numSofaBeds;
	private int numDoubleSofaBeds;
	private double dailyPrice;
	private double calculatedPrice;
	private double originalPrice;
	private String propertyDetailsUrl;
	private String mainImageUrl;
	private BookingDomLocation location;
	private ArrayList<BookingDomTag> tags;
	private String category;
	private boolean onlineBookable;
	private boolean calendarEnabled;
	private ArrayList<BookingDomFacility> facilities; // amenities?
	private String operatorDetailsUrl; // property owner company url
//	private ArrayList<BookingDomImage> images; // probably not going to need
	private ArrayList<String> propertyDescriptionManyLang;
//	private BookingDomOperator operator; // property manager, schema unknown
	private ArrayList<String> imageUrls;
	private String availabilityState;
//	private ArrayList<BookingDomPropertyReview> reviews;
//	private BookingDomPropertyConditions conditions;
	private int squareMeter;
	private String apiMethod;
	private String methodType;
	private String resultCode;
	private String resultText;
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getInternalAddress() {
		return internalAddress;
	}
	public void setInternalAddress(String internalAddress) {
		this.internalAddress = internalAddress;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getLongDescription() {
		return longDescription;
	}
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public int getMaxPersion() {
		return maxPersion;
	}
	public void setMaxPersion(int maxPersion) {
		this.maxPersion = maxPersion;
	}
	public int getBedrooms() {
		return bedrooms;
	}
	public void setBedrooms(int bedrooms) {
		this.bedrooms = bedrooms;
	}
	public int getBathrooms() {
		return bathrooms;
	}
	public void setBathrooms(int bathrooms) {
		this.bathrooms = bathrooms;
	}
	public int getNumSingleBeds() {
		return numSingleBeds;
	}
	public void setNumSingleBeds(int numSingleBeds) {
		this.numSingleBeds = numSingleBeds;
	}
	public int getNumDoubleBeds() {
		return numDoubleBeds;
	}
	public void setNumDoubleBeds(int numDoubleBeds) {
		this.numDoubleBeds = numDoubleBeds;
	}
	public int getNumSofaBeds() {
		return numSofaBeds;
	}
	public void setNumSofaBeds(int numSofaBeds) {
		this.numSofaBeds = numSofaBeds;
	}
	public int getNumDoubleSofaBeds() {
		return numDoubleSofaBeds;
	}
	public void setNumDoubleSofaBeds(int numDoubleSofaBeds) {
		this.numDoubleSofaBeds = numDoubleSofaBeds;
	}
	public double getDailyPrice() {
		return dailyPrice;
	}
	public void setDailyPrice(double dailyPrice) {
		this.dailyPrice = dailyPrice;
	}
	public double getCalculatedPrice() {
		return calculatedPrice;
	}
	public void setCalculatedPrice(double calculatedPrice) {
		this.calculatedPrice = calculatedPrice;
	}
	public double getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}
	public String getPropertyDetailsUrl() {
		return propertyDetailsUrl;
	}
	public void setPropertyDetailsUrl(String propertyDetailsUrl) {
		this.propertyDetailsUrl = propertyDetailsUrl;
	}
	public String getMainImageUrl() {
		return mainImageUrl;
	}
	public void setMainImageUrl(String mainImageUrl) {
		this.mainImageUrl = mainImageUrl;
	}
	public BookingDomLocation getLocation() {
		return location;
	}
	public void setLocation(BookingDomLocation location) {
		this.location = location;
	}
	public ArrayList<BookingDomTag> getTags() {
		return tags;
	}
	public void setTags(ArrayList<BookingDomTag> tags) {
		this.tags = tags;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public boolean isOnlineBookable() {
		return onlineBookable;
	}
	public void setOnlineBookable(boolean onlineBookable) {
		this.onlineBookable = onlineBookable;
	}
	public boolean isCalendarEnabled() {
		return calendarEnabled;
	}
	public void setCalendarEnabled(boolean calendarEnabled) {
		this.calendarEnabled = calendarEnabled;
	}
	public ArrayList<BookingDomFacility> getFacilities() {
		return facilities;
	}
	public void setFacilities(ArrayList<BookingDomFacility> facilities) {
		this.facilities = facilities;
	}
	public String getOperatorDetailsUrl() {
		return operatorDetailsUrl;
	}
	public void setOperatorDetailsUrl(String operatorDetailsUrl) {
		this.operatorDetailsUrl = operatorDetailsUrl;
	}
//	public ArrayList<BookingDomImage> getImages() {
//		return images;
//	}
//	public void setImages(ArrayList<BookingDomImage> images) {
//		this.images = images;
//	}
	public ArrayList<String> getPropertyDescriptionManyLang() {
		return propertyDescriptionManyLang;
	}
	public void setPropertyDescriptionManyLang(
			ArrayList<String> propertyDescriptionManyLang) {
		this.propertyDescriptionManyLang = propertyDescriptionManyLang;
	}
//	public BookingDomOperator getOperator() {
//		return operator;
//	}
//	public void setOperator(BookingDomOperator operator) {
//		this.operator = operator;
//	}
	public ArrayList<String> getImageUrls() {
		return imageUrls;
	}
	public void setImageUrls(ArrayList<String> imageUrls) {
		this.imageUrls = imageUrls;
	}
	public String getAvailabilityState() {
		return availabilityState;
	}
	public void setAvailabilityState(String availabilityState) {
		this.availabilityState = availabilityState;
	}
//	public ArrayList<BookingDomPropertyReview> getReviews() {
//		return reviews;
//	}
//	public void setReviews(ArrayList<BookingDomPropertyReview> reviews) {
//		this.reviews = reviews;
//	}
//	public BookingDomPropertyConditions getConditions() {
//		return conditions;
//	}
//	public void setConditions(BookingDomPropertyConditions conditions) {
//		this.conditions = conditions;
//	}
	public int getSquareMeter() {
		return squareMeter;
	}
	public void setSquareMeter(int squareMeter) {
		this.squareMeter = squareMeter;
	}
	public String getApiMethod() {
		return apiMethod;
	}
	public void setApiMethod(String apiMethod) {
		this.apiMethod = apiMethod;
	}
	public String getMethodType() {
		return methodType;
	}
	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultText() {
		return resultText;
	}
	public void setResultText(String resultText) {
		this.resultText = resultText;
	}
}
