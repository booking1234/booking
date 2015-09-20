package net.cbtltd.rest.bookingdom;

import java.util.ArrayList;

public class BookingDomLocation {
	private String name;
	private String locationDescriptionShort;
	private String locationDescriptionLong;
	private String url;
	private int numPropLocation;
	private String locationCode;
	private String locationType;
	private BookingDomLocation parentLocation;
	private double longitude;
	private double latitude;
	private boolean mainLocation; // ?
	private int locationID; // Bookingdom location id
	private int versionID;
	private ArrayList<BookingDomLocation> locations;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocationDescriptionShort() {
		return locationDescriptionShort;
	}
	public void setLocationDescriptionShort(String locationDescriptionShort) {
		this.locationDescriptionShort = locationDescriptionShort;
	}
	public String getLocationDescriptionLong() {
		return locationDescriptionLong;
	}
	public void setLocationDescriptionLong(String locationDescriptionLong) {
		this.locationDescriptionLong = locationDescriptionLong;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getNumPropLocation() {
		return numPropLocation;
	}
	public void setNumPropLocation(int numPropLocation) {
		this.numPropLocation = numPropLocation;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public BookingDomLocation getParentLocation() {
		return parentLocation;
	}
	public void setParentLocation(BookingDomLocation parentLocation) {
		this.parentLocation = parentLocation;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public boolean isMainLocation() {
		return mainLocation;
	}
	public void setMainLocation(boolean mainLocation) {
		this.mainLocation = mainLocation;
	}
	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	public int getVersionID() {
		return versionID;
	}
	public void setVersionID(int versionID) {
		this.versionID = versionID;
	}
	public ArrayList<BookingDomLocation> getLocations() {
		return locations;
	}
	public void setLocations(ArrayList<BookingDomLocation> locations) {
		this.locations = locations;
	}
}
