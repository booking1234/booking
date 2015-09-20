/*
 * @author David Lepe and Devin Held
 * Version 1.0
 */

package net.cbtltd.rest.laketahoeaccommodations;

import java.util.Arrays;
import java.util.HashMap;

public class Property {
	private String unitID;
	private String unitDescription;
	private String streetAddress;
	private double longitude;
	private double latitude;
	private String[] descriptionCodes; // List of property amenities; 
	private int maxOccupancy;
	private int bedrooms;
	private double bathrooms;
	private int squareFeet;
	private String washerDescription;
	private String fireplaceDescription;
	private String parkingDescription;
	private String barbequeType;
	private String beginningAvailibilityDate;
	private Day[] availability;
	private double securityDeposit;
	private double cleaningFee;
	private double taxRate;
	private String taxFlags;
	private double splTax;
	private String guestComment;
	private int numberOfSofaBed;
	private String[] bedList;
	private String[] tvList;
	private static HashMap<String, String> locationCode = null;
	private static HashMap<String, String> regionCode = null;
	static {
		if (locationCode == null) {
			locationCode = new HashMap<String, String>();
			
			locationCode.put("CO", "South Lake Tahoe"); // CA
			locationCode.put("CY", "South Lake Tahoe"); // CA
			locationCode.put("HC", "South Lake Tahoe"); // CA
			locationCode.put("LL", "South Lake Tahoe"); // CA
			locationCode.put("TK", "South Lake Tahoe"); // CA
			locationCode.put("HN", "Stateline"); // NV
			locationCode.put("LV", "Stateline"); // NV
			locationCode.put("NV", "Zephyr Cove"); // NV
			locationCode.put("PW", "Zephyr Cove"); // NV
			locationCode.put("AM", "Squaw Valley"); // CA
			locationCode.put("NS", "Kings Beach"); // CA
			locationCode.put("SV", "Squaw Valley"); // CA
			locationCode.put("TC", "Tahoe City"); // CA
			locationCode.put("WS", "Tahoe City"); // CA
			locationCode.put("CD", "Incline Village"); // NV
			locationCode.put("IV", "Incline Village"); // NV
			locationCode.put("FP", "Incline Village"); // NV
			locationCode.put("MC", "Incline Village"); // NV
			locationCode.put("MS", "Incline Village"); // NV
			locationCode.put("TH", "Incline Village"); // NV
			locationCode.put("WD", "Incline Village"); // NV		
		}
	}
	
	static {
		if (regionCode == null) {
			regionCode = new HashMap<String, String>();
			
			regionCode.put("South Lake Tahoe", "CA");
			regionCode.put("Stateline", "NV");
			regionCode.put("Zephyr Cove", "NV");
			regionCode.put("Squaw Valley", "CA");
			regionCode.put("Kings Beach", "CA");
			regionCode.put("Tahoe City", "CA");
			regionCode.put("Incline Village", "NV");
		}
	}
	
	/**
	 * Translates the location code in the unitID of a property to
	 * it's respective region
	 * @return
	 */
	public String getLocationCode() {
		return locationCode.get(this.unitID.substring(0, 2));
	}
	
	/**
	 * 
	 * @param location
	 * @return
	 */
	public String getUSState(String location) {
		return regionCode.get(location);
	}
	
	public void setBedList(String[] bedList) {
		this.bedList = bedList;
	}
	
	public String[] getBedList() {
		return this.bedList;
	}
	
	/**
	 * @return the availability
	 */
	public Day[] getAvailability() {
		return availability;
	}
	/**
	 * @param availability the availability to set
	 */
	public void setAvailability(Day[] availability) {
		this.availability = availability;
	}
	
	/**
	 * @return the unitID
	 */
	public String getUnitID() {
		return unitID;
	}
	/**
	 * @param unitID the unitID to set
	 */
	public void setUnitID(String unitID) {
		this.unitID = unitID.replaceAll(" ", "") ;
	}
	/**
	 * @return the unitDescription
	 */
	public String getUnitDescription() {
		return unitDescription;
	}
	/**
	 * @param unitDescription the unitDescription to set
	 */
	public void setUnitDescription(String unitDescription) {
		this.unitDescription = unitDescription;
	}
	/**
	 * @return the streetAddress
	 */
	public String getStreetAddress() {
		return streetAddress;
	}
	/**
	 * @param streetAddress the streetAddress to set
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the descriptionCodes
	 */
	public String[] getDescriptionCodes() {
		return descriptionCodes;
	}
	/**
	 * @param descriptionCodes the descriptionCodes to set
	 */
	public void setDescriptionCodes(String[] descriptionCodes) {
		this.descriptionCodes = descriptionCodes;
	}
	/**
	 * @return the maxOccupancy
	 */
	public int getMaxOccupancy() {
		return maxOccupancy;
	}
	/**
	 * @param maxOccupancy the maxOccupancy to set
	 */
	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	/**
	 * @return the bedrooms
	 */
	public int getBedrooms() {
		return bedrooms;
	}
	/**
	 * @param bedrooms the bedrooms to set
	 */
	public void setBedrooms(int bedrooms) {
		this.bedrooms = bedrooms;
	}
	/**
	 * @return the bathrooms
	 */
	public double getBathrooms() {
		return bathrooms;
	}
	/**
	 * @param bathrooms the bathrooms to set
	 */
	public void setBathrooms(double bathrooms) {
		this.bathrooms = bathrooms;
	}
	/**
	 * @return the squareFeet
	 */
	public int getSquareFeet() {
		return squareFeet;
	}
	/**
	 * @param squareFeet the squareFeet to set
	 */
	public void setSquareFeet(int squareFeet) {
		this.squareFeet = squareFeet;
	}
	/**
	 * @return the bedsInBedroom1
	 */
	
	
	public String[] getTvList() {
		return tvList;
	}

	public void setTvList(String[] tvList) {
		this.tvList = tvList;
	}

	/**
	 * @return the numberOfSofaBed
	 */
	public int getNumberOfSofaBed() {
		return numberOfSofaBed;
	}
	/**
	 * @param numberOfSofaBed the numberOfSofaBed to set
	 */
	public void setNumberOfSofaBed(int numberOfSofaBed) {
		this.numberOfSofaBed = numberOfSofaBed;
	}
	/**
	 * @return the washerDescription
	 */
	public String getWasherDescription() {
		return washerDescription;
	}
	/**
	 * @param washerDescription the washerDescription to set
	 */
	public void setWasherDescription(String washerDescription) {
		this.washerDescription = washerDescription;
	}
	/**
	 * @return the fireplaceDescription
	 */
	public String getFireplaceDescription() {
		return fireplaceDescription;
	}
	/**
	 * @param fireplaceDescription the fireplaceDescription to set
	 */
	public void setFireplaceDescription(String fireplaceDescription) {
		this.fireplaceDescription = fireplaceDescription;
	}
	/**
	 * @return the parkingDescription
	 */
	public String getParkingDescription() {
		return parkingDescription;
	}
	/**
	 * @param parkingDescription the parkingDescription to set
	 */
	public void setParkingDescription(String parkingDescription) {
		this.parkingDescription = parkingDescription;
	}
	/**
	 * @return the barbequeType
	 */
	public String getBarbequeType() {
		return barbequeType;
	}
	/**
	 * @param barbequeType the barbequeType to set
	 */
	public void setBarbequeType(String barbequeType) {
		this.barbequeType = barbequeType;
	}
	/**
	 * @return the beginningAvailibilityDate
	 */
	public String getBeginningAvailibilityDate() {
		return beginningAvailibilityDate;
	}
	/**
	 * @param beginningAvailibilityDate the beginningAvailibilityDate to set
	 */
	public void setBeginningAvailibilityDate(String beginningAvailibilityDate) {
		this.beginningAvailibilityDate = beginningAvailibilityDate;
	}

	/**
	 * @return the securityDeposit
	 */
	public double getSecurityDeposit() {
		return securityDeposit;
	}
	/**
	 * @param securityDeposit the securityDeposit to set
	 */
	public void setSecurityDeposit(double securityDeposit) {
		this.securityDeposit = securityDeposit;
	}
	/**
	 * @return the cleaningFee
	 */
	public double getCleaningFee() {
		return cleaningFee;
	}
	/**
	 * @param cleaningFee the cleaningFee to set
	 */
	public void setCleaningFee(double cleaningFee) {
		this.cleaningFee = cleaningFee;
	}
	/**
	 * @return the taxRate
	 */
	public double getTaxRate() {
		return taxRate;
	}
	/**
	 * @param taxRate the taxRate to set
	 */
	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}
	/**
	 * @return the taxFlags
	 */
	public String getTaxFlags() {
		return taxFlags;
	}
	/**
	 * @param taxFlags the taxFlags to set
	 */
	public void setTaxFlags(String taxFlags) {
		this.taxFlags = taxFlags;
	}
	/**
	 * @return the splTax
	 */
	public double getSplTax() {
		return splTax;
	}
	/**
	 * @param splTax the splTax to set
	 */
	public void setSplTax(double splTax) {
		this.splTax = splTax;
	}
	/**
	 * @return the guestComment
	 */
	public String getGuestComment() {
		return guestComment;
	}
	/**
	 * @param guestComment the guestComment to set
	 */
	public void setGuestComment(String guestComment) {
		this.guestComment = guestComment;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Property [unitID=" + unitID + ", unitDescription="
				+ unitDescription + ", streetAddress=" + streetAddress
				+ ", longitude=" + longitude + ", latitude=" + latitude
				+ ", descriptionCodes=" + Arrays.toString(descriptionCodes)
				+ ", maxOccupancy=" + maxOccupancy + ", bedrooms=" + bedrooms
				+ ", bathrooms=" + bathrooms
				+ ", numberOfSofaBed=" + numberOfSofaBed
				+ ", washerDescription=" + washerDescription
				+ ", fireplaceDescription=" + fireplaceDescription
				+ ", parkingDescription=" + parkingDescription
				+ ", barbequeType=" + barbequeType
				+ ", beginningAvailibilityDate=" + beginningAvailibilityDate
				+ ", availability=" + Arrays.toString(availability)
				+ ", securityDeposit=" + securityDeposit + ", cleaningFee="
				+ cleaningFee + ", taxRate=" + taxRate + ", taxFlags="
				+ taxFlags + ", splTax=" + splTax + ", guestComment=" + guestComment + "]";
	}
	
	
	
	
	
	
	
}
