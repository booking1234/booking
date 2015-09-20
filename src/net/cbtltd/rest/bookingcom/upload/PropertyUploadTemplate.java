package net.cbtltd.rest.bookingcom.upload;

import java.util.Date;

/**
 * The entity class to store the property info
 * @author nibodha
 *
 */
public class PropertyUploadTemplate {
	
	//General Property Information
	private String	propertyReferenceID="";
	private String	propertyName="";
	private String	address="";
	private String	city="";
	private String	latitude="";
	private String	longitude="";
	private String	zipcode="";
	private String	countryCode="";
	private String	accommodationType="Villa";
	private String	chain="BookingPal";
	private String	starrating="";
	private String	totalNumberofBookableOptions="1";
	private String	checkInFrom="14:00";
	private String	checkInUntil="14:00";
	private String	checkOutFrom="10:00";
	private String	checkOutUntil="10:00";
	private String	currencyCode="";
	private String	acceptedCreditCards="";
	private String	vatNumber="";
	private String	propertyURL="";
	private String	quickDescription="";
	private String	importantInformation="";
	 
	//Accomodation General Facilities
	private int	hour24FrontDesk;
	private int	petsAllowed;
	private int	parking; //:On_site/Nearby/None
	private int	internetAccess; //(Y/N)
	private int	nonSmokingArea;//Non_Smokingarea
	private int	allergyFreeRoom;
	private int	breakfastBuffet;
	private int	elevator;
	private int	garden;
	private int	gayFriendly;
	private int	heating;
	private int	luggageStorage;
	private int	nonSmokingRooms;
	private int	privateBeachArea;
	private int	restaurant;
	private int	roomsFacilitiesForDisabled;
	private int	safeDepositBox;
	private int	skiStorage;
	private int	sunTerrace;
	private int	terrace;
	private int	bbqfacilities;
	private int	fitnessRoom;
	private int	outdoorSwimmingpool;//(allyear)
	private int	indoorSwimmingpool;
	private int	hottub;
	private int	billiard;
	private int	bowling;
	private int	canoeing;
	private int	childrenPlayground;
	private int	cycling;
	private int	darts;
	private int	diving;
	private int	fishing;
	private int	gameRoom;
	private int	golfcourse;//(within3km)
	private int	hiking;
	private int	horseRiding;
	private int	hotSpringBath;
	private int	library;
	private int	miniGolf;
	private int	sauna;
	private int	skiSchool;
	private int	skiing;
	private int	snorkeling;
	private int	solarium;
	private int	spaWellnessCentre;
	private int	squash;
	private int	tableTennis;
	private int	tennisCourt;
	private int	turkishSteambath;
	private int	windSurfing;
	private int	bicyclerent;
	private int	bikesAvailable;//(free)
	private int	skiEquipmenThireonsite;
	private int	skiPassVendor;
	private int	skiToDoorAccess;
	
	//Contact Information
	private String	generalContactName="";
	private String	generalContactEmail="";
	private String	generalContactPhone="";
	private String	generalContactFax="";
	private String	generalContactLanguage="";
	private String	availabilityContactName="";
	private String	availabilityContactEmail="";
	private String	availabilityContactPhone="";
	private String	availabilityContactLanguage="";
	private String	centralReservationsContactName="";
	private String	centralReservationsContactEmail="";
	private String	centralReservationsContactPhone="";
	private String	centralReservationsContactLanguage="";
	private String	invoicesContactName="";
	private String	invoicesContactEmail="";
	private String	invoicesContactPhone="";
	private String	invoicesContactLanguage="";
	private String	contractContactName="";
	private String	contractContactEmail="";
	private String	contractContactPhone="";
	private String	contractContactLanguage="";
	private String	parityContactName="";
	private String	parityContactEmail="";
	private String	parityContactPhone="";
	private String	parityContactLanguage="";
	private String	specialRequestContactName="";
	private String	specialRequestContactEmail="";
	private String	specialRequestContactPhone="";
	private String	specialRequestContactLanguage="";
	private String	reservationContactName="";
	private String	reservationContactEmail="";
	private String	reservationContactPhone="";
	private String	reservationContactFax="";
	private String	reversationContactLanguage="";
	private String	contentContactName="";
	private String	contentContactEmail="";
	private String	contentContactPhone="";
	private String	contentContactLanguage="";
	private String	priceContactName="Ray Karimi";
	private String	priceContactEmail="support@mybookingpal.com";
	private String	priceContactPhone="949-333-0724";
	private String	priceContactLanguage="EN";
	
	//General Room Details
	private int numOfBedRooms;
	private String	roomName="";//Format:Two_BedroomVilla,Three_BedroomHolidayHome
	private String	roomType="Villa";
	private String	maxPersonOfStayInTheVilla="";
	private String	minimumSizeofRoom="";
	private String	smokingNonSmoking="Unknown";
	private String typeOfBedding="";
	
	//Room facilities 
	private int	airconditioning;
	private int	washingmachine;
	private int	clothesdryer;
	private int	desk;
	private int	extralongbeds;// >2meter
	private int	fan;
	private int	firePlace;
	private int	roomHeating;
	private int	roomHottub;
	private int	interConnectingRoomsAvailable;
	private int	clothingIron;
	private int	ironingFacilities;
	private int	mosquitonet;
	private int	privatePool;
	private int	roomSafeDepositBox;
	private int	seatingarea;
	private int	sofa;
	private int	soundProofing;
	private int	trouserpress;
	private int	shower;
	private int	bathroom;
	private int	sharedBathroom;
	private int	handShower;
	private int	bidet;
	private int	hairdryer;
	private int	roomSauna;
	private int	spabath;
	private int	toilet;
	private int	cdPlayer;
	private int	radio;
	private int	ipad;
	private int	ipodDockingStation;
	private int	cableChannels;
	private int	satelliteChannels;
	private int	tv;
	private int	dvdPlayer;
	private int	computer;
	private int	laptop;
	private int	laptopSafeBox;
	private int	gameConsole;
	private int	telephone;
	private int	videoGames;
	private int	barbecue;
	private int	diningarea;
	private int	dishwasher;
	private int	electrickettle;
	private int	kitchen;
	private int	kitchenette;
	private int	kitchenware;
	private int	microwave;
	private int	minibar;
	private int	oven;
	private int	refrigerator;
	private int	stove;
	private int	coffeeTeamaker;
	private int	toaster;
	private int	alarmclock;
	private int	balcony;
	private int	gardenView;
	private int	lakeView;
	private int	landmarkView;
	private int	mountainView;
	private int	patio;
	private int	poolview;
	private int	seaview;
	
	// Invoicing details
	private boolean useBPInvoiceDetails = true;
	private String	companyNameForInvoicing="";
	private String	attentionOff="Account Payable MyBookingPal";
	private String	legalAdress="101 Pacifica, Suite #250";
	private String	legalZipCode="92618";
	private String	legalCity="Irvine";
	private String	country="USA";
	private String	invoiceMedium="";
	
	private String	serviceChargetype="";
	private String	serviceChargeStatus="";
	private String	serviceChargeAmount="";
	
	//extra setting
	private String	childrenAllowed="Y";
	private String	numberOfChildrenAllowedStaying="0";
	private String	childBedcost="0";
	private String	groupPolicy="";
	private String	roomPetsAllowed="";
	private String	petsFee="0";
	private String	parkingFee="0";
	private String	roomParking="Free";
	private String	parkingReservationNeeded="N";
	private String	roomInternetAccess="";
	private String	internet="";
	private String	internetAccessType="";//wired/wireless
	private String	internetAccessInRoom="N";
	private String	internetAccessInPublicArea="N";
	private String	internetAccessInBusinessCentre="N";
	private String	mealsPlan="";//breakfast/lunch/dinner
	private String	mealsPlanType="";//Price included/excluded
	
	//ExtraCost & Tax 
	
	private String contractFee="";                           //A
	private String vatOrTax="VAT";                           //C 
	private String taxIncludedOrExcluded="Included";
	private String vatOrTaxAmount="NA";
	private String vatType="NA";      // VAT Type (NA - not applicable, PC - percentage, IC - incalculable)
	private String cityTaxAmount="NA";
	private String cityTaxType="NA"; // City tax type (NA - not applicable, PS - per stay, PPS - per person per stay, PN - per night, PPN - per person per night)
	private String cityTaxStatus="NA";
	private String securityDeposit="NA";                       
	private String finalCleaningFee="N";
	private String finalCleaningIncludedOrExcluded="NA";
	private String finalCleaningPrice = "NA";
	private String finalCleaningSpecification = "NA";
	private String bedLinenAndTowels = "NA";
	private String bedLinenAndTowelsPrice = "NA";
	private String airconditioningType = "Included";   // Included, Optional, Mandatory
	private String airconditioningPrice = "NA";
	private String electircity = "NA";
	private String gasOil = "NA";
	private String heatingType = "NA";
	
	
	// specification  (NA - not applicable, PS - per stay, PPS - per person per stay, PN - per night, PPN - per person per night, PC - percentage, PPNR - per person per night restricted)
	public static enum ChargeSpecifications {NA, PS, PPS, PN, PPN, PC, PPNR};
	
	
	// Policy
	private String paymentAmount = "";
	private String remainderPaymentDate = "";
	
		
	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getRemainderPaymentDate() {
		return remainderPaymentDate;
	}

	public void setRemainderPaymentDate(String remainderPaymentDate) {
		this.remainderPaymentDate = remainderPaymentDate;
	}

	public boolean isUseBPInvoiceDetails() {
		return useBPInvoiceDetails;
	}

	public void setUseBPInvoiceDetails(boolean useBPInvoiceDetails) {
		this.useBPInvoiceDetails = useBPInvoiceDetails;
	}

	public String getFinalCleaningIncludedOrExcluded() {
		return finalCleaningIncludedOrExcluded;
	}

	public void setFinalCleaningIncludedOrExcluded(
			String finalCleaningIncludedOrExcluded) {
		this.finalCleaningIncludedOrExcluded = finalCleaningIncludedOrExcluded;
	}

	public String getFinalCleaningPrice() {
		return finalCleaningPrice;
	}

	public void setFinalCleaningPrice(String finalCleaningPrice) {
		this.finalCleaningPrice = finalCleaningPrice;
	}

	public String getFinalCleaningSpecification() {
		return finalCleaningSpecification;
	}

	public void setFinalCleaningSpecification(String finalCleaningSpecification) {
		this.finalCleaningSpecification = finalCleaningSpecification;
	}

	public String getBedLinenAndTowels() {
		return bedLinenAndTowels;
	}

	public void setBedLinenAndTowels(String bedLinenAndTowels) {
		this.bedLinenAndTowels = bedLinenAndTowels;
	}

	public String getBedLinenAndTowelsPrice() {
		return bedLinenAndTowelsPrice;
	}

	public void setBedLinenAndTowelsPrice(String bedLinenAndTowelsPrice) {
		this.bedLinenAndTowelsPrice = bedLinenAndTowelsPrice;
	}

	public String getAirconditioningType() {
		return airconditioningType;
	}

	public void setAirconditioningType(String airconditioningType) {
		this.airconditioningType = airconditioningType;
	}

	public String getAirconditioningPrice() {
		return airconditioningPrice;
	}

	public void setAirconditioningPrice(String airconditioningPrice) {
		this.airconditioningPrice = airconditioningPrice;
	}

	public String getElectircity() {
		return electircity;
	}

	public void setElectircity(String electircity) {
		this.electircity = electircity;
	}

	public String getGasOil() {
		return gasOil;
	}

	public void setGasOil(String gasOil) {
		this.gasOil = gasOil;
	}

	public String getHeatingType() {
		return heatingType;
	}

	public void setHeatingType(String heatingType) {
		this.heatingType = heatingType;
	}

	/**
	 * @return roomName
	 */
	public String getRoomName() {
		if(numOfBedRooms==1){
			return "One-Bedroom " + accommodationType;
		}
		else if(numOfBedRooms==2){
			return "Two-Bedroom " + accommodationType;
		}
		else if(numOfBedRooms==3){
			return "Three-Bedroom " + accommodationType;
		}
		else if(numOfBedRooms==4){
			return "Four-Bedroom " + accommodationType;
		}
		else if(numOfBedRooms==5){
			return "Five-Bedroom " + accommodationType;
		}
		else if(numOfBedRooms==6){
			return "Six-Bedroom " + accommodationType;
		}
		else if(numOfBedRooms==7){
			return "Seven-Bedroom " + accommodationType;
		}
		else if(numOfBedRooms==8){
			return "Eight-Bedroom " + accommodationType;
		}
		else if(numOfBedRooms>8){
			return "Eight-Bedroom plus " + accommodationType;
		}
		return roomName;
	}

	/**
	 * @return the propertyReferenceID
	 */
	public String getPropertyReferenceID() {
		return propertyReferenceID;
	}

	/**
	 * @param propertyReferenceID the propertyReferenceID to set
	 */
	public void setPropertyReferenceID(String propertyReferenceID) {
		this.propertyReferenceID = propertyReferenceID;
	}

	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @param propertyName the propertyName to set
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * @param zipcode the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return the accommodationType
	 */
	public String getAccommodationType() {
		return accommodationType;
	}

	/**
	 * @param accommodationType the accommodationType to set
	 */
	public void setAccommodationType(String accommodationType) {
		this.accommodationType = accommodationType;
	}

	/**
	 * @return the chain
	 */
	public String getChain() {
		return chain;
	}

	/**
	 * @param chain the chain to set
	 */
	public void setChain(String chain) {
		this.chain = chain;
	}

	/**
	 * @return the starrating
	 */
	public String getStarrating() {
		return starrating;
	}

	/**
	 * @param starrating the starrating to set
	 */
	public void setStarrating(String starrating) {
		this.starrating = starrating;
	}

	/**
	 * @return the totalNumberofBookableOptions
	 */
	public String getTotalNumberofBookableOptions() {
		return totalNumberofBookableOptions;
	}

	/**
	 * @param totalNumberofBookableOptions the totalNumberofBookableOptions to set
	 */
	public void setTotalNumberofBookableOptions(String totalNumberofBookableOptions) {
		this.totalNumberofBookableOptions = totalNumberofBookableOptions;
	}

	/**
	 * @return the checkInFrom
	 */
	public String getCheckInFrom() {
		return checkInFrom;
	}

	/**
	 * @param checkInFrom the checkInFrom to set
	 */
	public void setCheckInFrom(String checkInFrom) {
		this.checkInFrom = checkInFrom;
	}

	/**
	 * @return the checkInUntil
	 */
	public String getCheckInUntil() {
		return checkInUntil;
	}

	/**
	 * @param checkInUntil the checkInUntil to set
	 */
	public void setCheckInUntil(String checkInUntil) {
		this.checkInUntil = checkInUntil;
	}

	/**
	 * @return the checkOutFrom
	 */
	public String getCheckOutFrom() {
		return checkOutFrom;
	}

	/**
	 * @param checkOutFrom the checkOutFrom to set
	 */
	public void setCheckOutFrom(String checkOutFrom) {
		this.checkOutFrom = checkOutFrom;
	}

	/**
	 * @return the checkOutUntil
	 */
	public String getCheckOutUntil() {
		return checkOutUntil;
	}

	/**
	 * @param checkOutUntil the checkOutUntil to set
	 */
	public void setCheckOutUntil(String checkOutUntil) {
		this.checkOutUntil = checkOutUntil;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the acceptedCreditCards
	 */
	public String getAcceptedCreditCards() {
		return acceptedCreditCards;
	}

	/**
	 * @param acceptedCreditCards the acceptedCreditCards to set
	 */
	public void setAcceptedCreditCards(String acceptedCreditCards) {
		this.acceptedCreditCards = acceptedCreditCards;
	}

	/**
	 * @return the vatNumber
	 */
	public String getVatNumber() {
		return vatNumber;
	}

	/**
	 * @param vatNumber the vatNumber to set
	 */
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	/**
	 * @return the propertyURL
	 */
	public String getPropertyURL() {
		return propertyURL;
	}

	/**
	 * @param propertyURL the propertyURL to set
	 */
	public void setPropertyURL(String propertyURL) {
		this.propertyURL = propertyURL;
	}

	/**
	 * @return the quickDescription
	 */
	public String getQuickDescription() {
		return quickDescription;
	}

	/**
	 * @param quickDescription the quickDescription to set
	 */
	public void setQuickDescription(String quickDescription) {
		this.quickDescription = quickDescription;
	}

	/**
	 * @return the importantInformation
	 */
	public String getImportantInformation() {
		return importantInformation;
	}

	/**
	 * @param importantInformation the importantInformation to set
	 */
	public void setImportantInformation(String importantInformation) {
		this.importantInformation = importantInformation;
	}

	/**
	 * @return the hour24FrontDesk
	 */
	public int getHour24FrontDesk() {
		return hour24FrontDesk;
	}

	/**
	 * @param hour24FrontDesk the hour24FrontDesk to set
	 */
	public void setHour24FrontDesk(int hour24FrontDesk) {
		this.hour24FrontDesk = hour24FrontDesk;
	}

	/**
	 * @return the petsAllowed
	 */
	public int getPetsAllowed() {
		return petsAllowed;
	}

	/**
	 * @param petsAllowed the petsAllowed to set
	 */
	public void setPetsAllowed(int petsAllowed) {
		this.petsAllowed = petsAllowed;
	}

	/**
	 * @return the parking
	 */
	public int getParking() {
		return parking;
	}

	/**
	 * @param parking the parking to set
	 */
	public void setParking(int parking) {
		this.parking = parking;
	}

	/**
	 * @return the internetAccess
	 */
	public int getInternetAccess() {
		return internetAccess;
	}

	/**
	 * @param internetAccess the internetAccess to set
	 */
	public void setInternetAccess(int internetAccess) {
		this.internetAccess = internetAccess;
	}

	/**
	 * @return the nonSmokingArea
	 */
	public int getNonSmokingArea() {
		return nonSmokingArea;
	}

	/**
	 * @param nonSmokingArea the nonSmokingArea to set
	 */
	public void setNonSmokingArea(int nonSmokingArea) {
		this.nonSmokingArea = nonSmokingArea;
	}

	/**
	 * @return the allergyFreeRoom
	 */
	public int getAllergyFreeRoom() {
		return allergyFreeRoom;
	}

	/**
	 * @param allergyFreeRoom the allergyFreeRoom to set
	 */
	public void setAllergyFreeRoom(int allergyFreeRoom) {
		this.allergyFreeRoom = allergyFreeRoom;
	}

	/**
	 * @return the breakfastBuffet
	 */
	public int getBreakfastBuffet() {
		return breakfastBuffet;
	}

	/**
	 * @param breakfastBuffet the breakfastBuffet to set
	 */
	public void setBreakfastBuffet(int breakfastBuffet) {
		this.breakfastBuffet = breakfastBuffet;
	}

	/**
	 * @return the elevator
	 */
	public int getElevator() {
		return elevator;
	}

	/**
	 * @param elevator the elevator to set
	 */
	public void setElevator(int elevator) {
		this.elevator = elevator;
	}

	/**
	 * @return the garden
	 */
	public int getGarden() {
		return garden;
	}

	/**
	 * @param garden the garden to set
	 */
	public void setGarden(int garden) {
		this.garden = garden;
	}

	/**
	 * @return the gayFriendly
	 */
	public int getGayFriendly() {
		return gayFriendly;
	}

	/**
	 * @param gayFriendly the gayFriendly to set
	 */
	public void setGayFriendly(int gayFriendly) {
		this.gayFriendly = gayFriendly;
	}

	/**
	 * @return the heating
	 */
	public int getHeating() {
		return heating;
	}

	/**
	 * @param heating the heating to set
	 */
	public void setHeating(int heating) {
		this.heating = heating;
	}

	/**
	 * @return the luggageStorage
	 */
	public int getLuggageStorage() {
		return luggageStorage;
	}

	/**
	 * @param luggageStorage the luggageStorage to set
	 */
	public void setLuggageStorage(int luggageStorage) {
		this.luggageStorage = luggageStorage;
	}

	/**
	 * @return the nonSmokingRooms
	 */
	public int getNonSmokingRooms() {
		return nonSmokingRooms;
	}

	/**
	 * @param nonSmokingRooms the nonSmokingRooms to set
	 */
	public void setNonSmokingRooms(int nonSmokingRooms) {
		this.nonSmokingRooms = nonSmokingRooms;
	}

	/**
	 * @return the privateBeachArea
	 */
	public int getPrivateBeachArea() {
		return privateBeachArea;
	}

	/**
	 * @param privateBeachArea the privateBeachArea to set
	 */
	public void setPrivateBeachArea(int privateBeachArea) {
		this.privateBeachArea = privateBeachArea;
	}

	/**
	 * @return the restaurant
	 */
	public int getRestaurant() {
		return restaurant;
	}

	/**
	 * @param restaurant the restaurant to set
	 */
	public void setRestaurant(int restaurant) {
		this.restaurant = restaurant;
	}

	/**
	 * @return the roomsFacilitiesForDisabled
	 */
	public int getRoomsFacilitiesForDisabled() {
		return roomsFacilitiesForDisabled;
	}

	/**
	 * @param roomsFacilitiesForDisabled the roomsFacilitiesForDisabled to set
	 */
	public void setRoomsFacilitiesForDisabled(int roomsFacilitiesForDisabled) {
		this.roomsFacilitiesForDisabled = roomsFacilitiesForDisabled;
	}

	/**
	 * @return the safeDepositBox
	 */
	public int getSafeDepositBox() {
		return safeDepositBox;
	}

	/**
	 * @param safeDepositBox the safeDepositBox to set
	 */
	public void setSafeDepositBox(int safeDepositBox) {
		this.safeDepositBox = safeDepositBox;
	}

	/**
	 * @return the skiStorage
	 */
	public int getSkiStorage() {
		return skiStorage;
	}

	/**
	 * @param skiStorage the skiStorage to set
	 */
	public void setSkiStorage(int skiStorage) {
		this.skiStorage = skiStorage;
	}

	/**
	 * @return the sunTerrace
	 */
	public int getSunTerrace() {
		return sunTerrace;
	}

	/**
	 * @param sunTerrace the sunTerrace to set
	 */
	public void setSunTerrace(int sunTerrace) {
		this.sunTerrace = sunTerrace;
	}

	/**
	 * @return the terrace
	 */
	public int getTerrace() {
		return terrace;
	}

	/**
	 * @param terrace the terrace to set
	 */
	public void setTerrace(int terrace) {
		this.terrace = terrace;
	}

	/**
	 * @return the bbqfacilities
	 */
	public int getBbqfacilities() {
		return bbqfacilities;
	}

	/**
	 * @param bbqfacilities the bbqfacilities to set
	 */
	public void setBbqfacilities(int bbqfacilities) {
		this.bbqfacilities = bbqfacilities;
	}

	/**
	 * @return the fitnessRoom
	 */
	public int getFitnessRoom() {
		return fitnessRoom;
	}

	/**
	 * @param fitnessRoom the fitnessRoom to set
	 */
	public void setFitnessRoom(int fitnessRoom) {
		this.fitnessRoom = fitnessRoom;
	}

	/**
	 * @return the outdoorSwimmingpool
	 */
	public int getOutdoorSwimmingpool() {
		return outdoorSwimmingpool;
	}

	/**
	 * @param outdoorSwimmingpool the outdoorSwimmingpool to set
	 */
	public void setOutdoorSwimmingpool(int outdoorSwimmingpool) {
		this.outdoorSwimmingpool = outdoorSwimmingpool;
	}

	/**
	 * @return the hottub
	 */
	public int getHottub() {
		return hottub;
	}

	/**
	 * @param hottub the hottub to set
	 */
	public void setHottub(int hottub) {
		this.hottub = hottub;
	}

	/**
	 * @return the billiard
	 */
	public int getBilliard() {
		return billiard;
	}

	/**
	 * @param billiard the billiard to set
	 */
	public void setBilliard(int billiard) {
		this.billiard = billiard;
	}

	/**
	 * @return the bowling
	 */
	public int getBowling() {
		return bowling;
	}

	/**
	 * @param bowling the bowling to set
	 */
	public void setBowling(int bowling) {
		this.bowling = bowling;
	}

	/**
	 * @return the canoeing
	 */
	public int getCanoeing() {
		return canoeing;
	}

	/**
	 * @param canoeing the canoeing to set
	 */
	public void setCanoeing(int canoeing) {
		this.canoeing = canoeing;
	}

	/**
	 * @return the childrenPlayground
	 */
	public int getChildrenPlayground() {
		return childrenPlayground;
	}

	/**
	 * @param childrenPlayground the childrenPlayground to set
	 */
	public void setChildrenPlayground(int childrenPlayground) {
		this.childrenPlayground = childrenPlayground;
	}

	/**
	 * @return the cycling
	 */
	public int getCycling() {
		return cycling;
	}

	/**
	 * @param cycling the cycling to set
	 */
	public void setCycling(int cycling) {
		this.cycling = cycling;
	}

	/**
	 * @return the darts
	 */
	public int getDarts() {
		return darts;
	}

	/**
	 * @param darts the darts to set
	 */
	public void setDarts(int darts) {
		this.darts = darts;
	}

	/**
	 * @return the diving
	 */
	public int getDiving() {
		return diving;
	}

	/**
	 * @param diving the diving to set
	 */
	public void setDiving(int diving) {
		this.diving = diving;
	}

	/**
	 * @return the fishing
	 */
	public int getFishing() {
		return fishing;
	}

	/**
	 * @param fishing the fishing to set
	 */
	public void setFishing(int fishing) {
		this.fishing = fishing;
	}

	/**
	 * @return the gameRoom
	 */
	public int getGameRoom() {
		return gameRoom;
	}

	/**
	 * @param gameRoom the gameRoom to set
	 */
	public void setGameRoom(int gameRoom) {
		this.gameRoom = gameRoom;
	}

	/**
	 * @return the golfcourse
	 */
	public int getGolfcourse() {
		return golfcourse;
	}

	/**
	 * @param golfcourse the golfcourse to set
	 */
	public void setGolfcourse(int golfcourse) {
		this.golfcourse = golfcourse;
	}

	/**
	 * @return the hiking
	 */
	public int getHiking() {
		return hiking;
	}

	/**
	 * @param hiking the hiking to set
	 */
	public void setHiking(int hiking) {
		this.hiking = hiking;
	}

	/**
	 * @return the horseRiding
	 */
	public int getHorseRiding() {
		return horseRiding;
	}

	/**
	 * @param horseRiding the horseRiding to set
	 */
	public void setHorseRiding(int horseRiding) {
		this.horseRiding = horseRiding;
	}

	/**
	 * @return the hotSpringBath
	 */
	public int getHotSpringBath() {
		return hotSpringBath;
	}

	/**
	 * @param hotSpringBath the hotSpringBath to set
	 */
	public void setHotSpringBath(int hotSpringBath) {
		this.hotSpringBath = hotSpringBath;
	}

	/**
	 * @return the library
	 */
	public int getLibrary() {
		return library;
	}

	/**
	 * @param library the library to set
	 */
	public void setLibrary(int library) {
		this.library = library;
	}

	/**
	 * @return the miniGolf
	 */
	public int getMiniGolf() {
		return miniGolf;
	}

	/**
	 * @param miniGolf the miniGolf to set
	 */
	public void setMiniGolf(int miniGolf) {
		this.miniGolf = miniGolf;
	}

	/**
	 * @return the sauna
	 */
	public int getSauna() {
		return sauna;
	}

	/**
	 * @param sauna the sauna to set
	 */
	public void setSauna(int sauna) {
		this.sauna = sauna;
	}

	/**
	 * @return the skiSchool
	 */
	public int getSkiSchool() {
		return skiSchool;
	}

	/**
	 * @param skiSchool the skiSchool to set
	 */
	public void setSkiSchool(int skiSchool) {
		this.skiSchool = skiSchool;
	}

	/**
	 * @return the skiing
	 */
	public int getSkiing() {
		return skiing;
	}

	/**
	 * @param skiing the skiing to set
	 */
	public void setSkiing(int skiing) {
		this.skiing = skiing;
	}

	/**
	 * @return the snorkeling
	 */
	public int getSnorkeling() {
		return snorkeling;
	}

	/**
	 * @param snorkeling the snorkeling to set
	 */
	public void setSnorkeling(int snorkeling) {
		this.snorkeling = snorkeling;
	}

	/**
	 * @return the solarium
	 */
	public int getSolarium() {
		return solarium;
	}

	/**
	 * @param solarium the solarium to set
	 */
	public void setSolarium(int solarium) {
		this.solarium = solarium;
	}

	/**
	 * @return the spaWellnessCentre
	 */
	public int getSpaWellnessCentre() {
		return spaWellnessCentre;
	}

	/**
	 * @param spaWellnessCentre the spaWellnessCentre to set
	 */
	public void setSpaWellnessCentre(int spaWellnessCentre) {
		this.spaWellnessCentre = spaWellnessCentre;
	}

	/**
	 * @return the squash
	 */
	public int getSquash() {
		return squash;
	}

	/**
	 * @param squash the squash to set
	 */
	public void setSquash(int squash) {
		this.squash = squash;
	}

	/**
	 * @return the tableTennis
	 */
	public int getTableTennis() {
		return tableTennis;
	}

	/**
	 * @param tableTennis the tableTennis to set
	 */
	public void setTableTennis(int tableTennis) {
		this.tableTennis = tableTennis;
	}

	/**
	 * @return the tennisCourt
	 */
	public int getTennisCourt() {
		return tennisCourt;
	}

	/**
	 * @param tennisCourt the tennisCourt to set
	 */
	public void setTennisCourt(int tennisCourt) {
		this.tennisCourt = tennisCourt;
	}

	/**
	 * @return the turkishSteambath
	 */
	public int getTurkishSteambath() {
		return turkishSteambath;
	}

	/**
	 * @param turkishSteambath the turkishSteambath to set
	 */
	public void setTurkishSteambath(int turkishSteambath) {
		this.turkishSteambath = turkishSteambath;
	}

	/**
	 * @return the windSurfing
	 */
	public int getWindSurfing() {
		return windSurfing;
	}

	/**
	 * @param windSurfing the windSurfing to set
	 */
	public void setWindSurfing(int windSurfing) {
		this.windSurfing = windSurfing;
	}

	/**
	 * @return the bicyclerent
	 */
	public int getBicyclerent() {
		return bicyclerent;
	}

	/**
	 * @param bicyclerent the bicyclerent to set
	 */
	public void setBicyclerent(int bicyclerent) {
		this.bicyclerent = bicyclerent;
	}

	/**
	 * @return the bikesAvailable
	 */
	public int getBikesAvailable() {
		return bikesAvailable;
	}

	/**
	 * @param bikesAvailable the bikesAvailable to set
	 */
	public void setBikesAvailable(int bikesAvailable) {
		this.bikesAvailable = bikesAvailable;
	}

	/**
	 * @return the skiEquipmenThireonsite
	 */
	public int getSkiEquipmenThireonsite() {
		return skiEquipmenThireonsite;
	}

	/**
	 * @param skiEquipmenThireonsite the skiEquipmenThireonsite to set
	 */
	public void setSkiEquipmenThireonsite(int skiEquipmenThireonsite) {
		this.skiEquipmenThireonsite = skiEquipmenThireonsite;
	}

	/**
	 * @return the skiPassVendor
	 */
	public int getSkiPassVendor() {
		return skiPassVendor;
	}

	/**
	 * @param skiPassVendor the skiPassVendor to set
	 */
	public void setSkiPassVendor(int skiPassVendor) {
		this.skiPassVendor = skiPassVendor;
	}

	/**
	 * @return the skiToDoorAccess
	 */
	public int getSkiToDoorAccess() {
		return skiToDoorAccess;
	}

	/**
	 * @param skiToDoorAccess the skiToDoorAccess to set
	 */
	public void setSkiToDoorAccess(int skiToDoorAccess) {
		this.skiToDoorAccess = skiToDoorAccess;
	}

	/**
	 * @return the generalContactName
	 */
	public String getGeneralContactName() {
		return generalContactName;
	}

	/**
	 * @param generalContactName the generalContactName to set
	 */
	public void setGeneralContactName(String generalContactName) {
		this.generalContactName = generalContactName;
	}

	/**
	 * @return the generalContactEmail
	 */
	public String getGeneralContactEmail() {
		return generalContactEmail;
	}

	/**
	 * @param generalContactEmail the generalContactEmail to set
	 */
	public void setGeneralContactEmail(String generalContactEmail) {
		this.generalContactEmail = generalContactEmail;
	}

	/**
	 * @return the generalContactPhone
	 */
	public String getGeneralContactPhone() {
		return generalContactPhone;
	}

	/**
	 * @param generalContactPhone the generalContactPhone to set
	 */
	public void setGeneralContactPhone(String generalContactPhone) {
		this.generalContactPhone = generalContactPhone;
	}

	/**
	 * @return the generalContactFax
	 */
	public String getGeneralContactFax() {
		return generalContactFax;
	}

	/**
	 * @param generalContactFax the generalContactFax to set
	 */
	public void setGeneralContactFax(String generalContactFax) {
		this.generalContactFax = generalContactFax;
	}

	/**
	 * @return the generalContactLanguage
	 */
	public String getGeneralContactLanguage() {
		return generalContactLanguage;
	}

	/**
	 * @param generalContactLanguage the generalContactLanguage to set
	 */
	public void setGeneralContactLanguage(String generalContactLanguage) {
		this.generalContactLanguage = generalContactLanguage;
	}

	/**
	 * @return the availabilityContactName
	 */
	public String getAvailabilityContactName() {
		return availabilityContactName;
	}

	/**
	 * @param availabilityContactName the availabilityContactName to set
	 */
	public void setAvailabilityContactName(String availabilityContactName) {
		this.availabilityContactName = availabilityContactName;
	}

	/**
	 * @return the availabilityContactEmail
	 */
	public String getAvailabilityContactEmail() {
		return availabilityContactEmail;
	}

	/**
	 * @param availabilityContactEmail the availabilityContactEmail to set
	 */
	public void setAvailabilityContactEmail(String availabilityContactEmail) {
		this.availabilityContactEmail = availabilityContactEmail;
	}

	/**
	 * @return the availabilityContactPhone
	 */
	public String getAvailabilityContactPhone() {
		return availabilityContactPhone;
	}

	/**
	 * @param availabilityContactPhone the availabilityContactPhone to set
	 */
	public void setAvailabilityContactPhone(String availabilityContactPhone) {
		this.availabilityContactPhone = availabilityContactPhone;
	}

	/**
	 * @return the availabilityContactLanguage
	 */
	public String getAvailabilityContactLanguage() {
		return availabilityContactLanguage;
	}

	/**
	 * @param availabilityContactLanguage the availabilityContactLanguage to set
	 */
	public void setAvailabilityContactLanguage(String availabilityContactLanguage) {
		this.availabilityContactLanguage = availabilityContactLanguage;
	}

	/**
	 * @return the centralReservationsContactName
	 */
	public String getCentralReservationsContactName() {
		return centralReservationsContactName;
	}

	/**
	 * @param centralReservationsContactName the centralReservationsContactName to set
	 */
	public void setCentralReservationsContactName(
			String centralReservationsContactName) {
		this.centralReservationsContactName = centralReservationsContactName;
	}

	/**
	 * @return the centralReservationsContactEmail
	 */
	public String getCentralReservationsContactEmail() {
		return centralReservationsContactEmail;
	}

	/**
	 * @param centralReservationsContactEmail the centralReservationsContactEmail to set
	 */
	public void setCentralReservationsContactEmail(
			String centralReservationsContactEmail) {
		this.centralReservationsContactEmail = centralReservationsContactEmail;
	}

	/**
	 * @return the centralReservationsContactPhone
	 */
	public String getCentralReservationsContactPhone() {
		return centralReservationsContactPhone;
	}

	/**
	 * @param centralReservationsContactPhone the centralReservationsContactPhone to set
	 */
	public void setCentralReservationsContactPhone(
			String centralReservationsContactPhone) {
		this.centralReservationsContactPhone = centralReservationsContactPhone;
	}

	/**
	 * @return the centralReservationsContactLanguage
	 */
	public String getCentralReservationsContactLanguage() {
		return centralReservationsContactLanguage;
	}

	/**
	 * @param centralReservationsContactLanguage the centralReservationsContactLanguage to set
	 */
	public void setCentralReservationsContactLanguage(
			String centralReservationsContactLanguage) {
		this.centralReservationsContactLanguage = centralReservationsContactLanguage;
	}

	/**
	 * @return the invoicesContactName
	 */
	public String getInvoicesContactName() {
		return invoicesContactName;
	}

	/**
	 * @param invoicesContactName the invoicesContactName to set
	 */
	public void setInvoicesContactName(String invoicesContactName) {
		this.invoicesContactName = invoicesContactName;
	}

	/**
	 * @return the invoicesContactEmail
	 */
	public String getInvoicesContactEmail() {
		return invoicesContactEmail;
	}

	/**
	 * @param invoicesContactEmail the invoicesContactEmail to set
	 */
	public void setInvoicesContactEmail(String invoicesContactEmail) {
		this.invoicesContactEmail = invoicesContactEmail;
	}

	/**
	 * @return the invoicesContactPhone
	 */
	public String getInvoicesContactPhone() {
		return invoicesContactPhone;
	}

	/**
	 * @param invoicesContactPhone the invoicesContactPhone to set
	 */
	public void setInvoicesContactPhone(String invoicesContactPhone) {
		this.invoicesContactPhone = invoicesContactPhone;
	}

	/**
	 * @return the invoicesContactLanguage
	 */
	public String getInvoicesContactLanguage() {
		return invoicesContactLanguage;
	}

	/**
	 * @param invoicesContactLanguage the invoicesContactLanguage to set
	 */
	public void setInvoicesContactLanguage(String invoicesContactLanguage) {
		this.invoicesContactLanguage = invoicesContactLanguage;
	}

	/**
	 * @return the contractContactName
	 */
	public String getContractContactName() {
		return contractContactName;
	}

	/**
	 * @param contractContactName the contractContactName to set
	 */
	public void setContractContactName(String contractContactName) {
		this.contractContactName = contractContactName;
	}

	/**
	 * @return the contractContactEmail
	 */
	public String getContractContactEmail() {
		return contractContactEmail;
	}

	/**
	 * @param contractContactEmail the contractContactEmail to set
	 */
	public void setContractContactEmail(String contractContactEmail) {
		this.contractContactEmail = contractContactEmail;
	}

	/**
	 * @return the contractContactPhone
	 */
	public String getContractContactPhone() {
		return contractContactPhone;
	}

	/**
	 * @param contractContactPhone the contractContactPhone to set
	 */
	public void setContractContactPhone(String contractContactPhone) {
		this.contractContactPhone = contractContactPhone;
	}

	/**
	 * @return the contractContactLanguage
	 */
	public String getContractContactLanguage() {
		return contractContactLanguage;
	}

	/**
	 * @param contractContactLanguage the contractContactLanguage to set
	 */
	public void setContractContactLanguage(String contractContactLanguage) {
		this.contractContactLanguage = contractContactLanguage;
	}

	/**
	 * @return the parityContactName
	 */
	public String getParityContactName() {
		return parityContactName;
	}

	/**
	 * @param parityContactName the parityContactName to set
	 */
	public void setParityContactName(String parityContactName) {
		this.parityContactName = parityContactName;
	}

	/**
	 * @return the parityContactEmail
	 */
	public String getParityContactEmail() {
		return parityContactEmail;
	}

	/**
	 * @param parityContactEmail the parityContactEmail to set
	 */
	public void setParityContactEmail(String parityContactEmail) {
		this.parityContactEmail = parityContactEmail;
	}

	/**
	 * @return the parityContactPhone
	 */
	public String getParityContactPhone() {
		return parityContactPhone;
	}

	/**
	 * @param parityContactPhone the parityContactPhone to set
	 */
	public void setParityContactPhone(String parityContactPhone) {
		this.parityContactPhone = parityContactPhone;
	}

	/**
	 * @return the parityContactLanguage
	 */
	public String getParityContactLanguage() {
		return parityContactLanguage;
	}

	/**
	 * @param parityContactLanguage the parityContactLanguage to set
	 */
	public void setParityContactLanguage(String parityContactLanguage) {
		this.parityContactLanguage = parityContactLanguage;
	}

	/**
	 * @return the specialRequestContactName
	 */
	public String getSpecialRequestContactName() {
		return specialRequestContactName;
	}

	/**
	 * @param specialRequestContactName the specialRequestContactName to set
	 */
	public void setSpecialRequestContactName(String specialRequestContactName) {
		this.specialRequestContactName = specialRequestContactName;
	}

	/**
	 * @return the specialRequestContactEmail
	 */
	public String getSpecialRequestContactEmail() {
		return specialRequestContactEmail;
	}

	/**
	 * @param specialRequestContactEmail the specialRequestContactEmail to set
	 */
	public void setSpecialRequestContactEmail(String specialRequestContactEmail) {
		this.specialRequestContactEmail = specialRequestContactEmail;
	}

	/**
	 * @return the specialRequestContactPhone
	 */
	public String getSpecialRequestContactPhone() {
		return specialRequestContactPhone;
	}

	/**
	 * @param specialRequestContactPhone the specialRequestContactPhone to set
	 */
	public void setSpecialRequestContactPhone(String specialRequestContactPhone) {
		this.specialRequestContactPhone = specialRequestContactPhone;
	}

	/**
	 * @return the specialRequestContactLanguage
	 */
	public String getSpecialRequestContactLanguage() {
		return specialRequestContactLanguage;
	}

	/**
	 * @param specialRequestContactLanguage the specialRequestContactLanguage to set
	 */
	public void setSpecialRequestContactLanguage(
			String specialRequestContactLanguage) {
		this.specialRequestContactLanguage = specialRequestContactLanguage;
	}

	/**
	 * @return the reservationContactName
	 */
	public String getReservationContactName() {
		return reservationContactName;
	}

	/**
	 * @param reservationContactName the reservationContactName to set
	 */
	public void setReservationContactName(String reservationContactName) {
		this.reservationContactName = reservationContactName;
	}

	/**
	 * @return the reservationContactEmail
	 */
	public String getReservationContactEmail() {
		return reservationContactEmail;
	}

	/**
	 * @param reservationContactEmail the reservationContactEmail to set
	 */
	public void setReservationContactEmail(String reservationContactEmail) {
		this.reservationContactEmail = reservationContactEmail;
	}

	/**
	 * @return the reservationContactPhone
	 */
	public String getReservationContactPhone() {
		return reservationContactPhone;
	}

	/**
	 * @param reservationContactPhone the reservationContactPhone to set
	 */
	public void setReservationContactPhone(String reservationContactPhone) {
		this.reservationContactPhone = reservationContactPhone;
	}

	/**
	 * @return the reservationContactFax
	 */
	public String getReservationContactFax() {
		return reservationContactFax;
	}

	/**
	 * @param reservationContactFax the reservationContactFax to set
	 */
	public void setReservationContactFax(String reservationContactFax) {
		this.reservationContactFax = reservationContactFax;
	}

	/**
	 * @return the reversationContactLanguage
	 */
	public String getReversationContactLanguage() {
		return reversationContactLanguage;
	}

	/**
	 * @param reversationContactLanguage the reversationContactLanguage to set
	 */
	public void setReversationContactLanguage(String reversationContactLanguage) {
		this.reversationContactLanguage = reversationContactLanguage;
	}

	/**
	 * @return the contentContactName
	 */
	public String getContentContactName() {
		return contentContactName;
	}

	/**
	 * @param contentContactName the contentContactName to set
	 */
	public void setContentContactName(String contentContactName) {
		this.contentContactName = contentContactName;
	}

	/**
	 * @return the contentContactEmail
	 */
	public String getContentContactEmail() {
		return contentContactEmail;
	}

	/**
	 * @param contentContactEmail the contentContactEmail to set
	 */
	public void setContentContactEmail(String contentContactEmail) {
		this.contentContactEmail = contentContactEmail;
	}

	/**
	 * @return the contentContactPhone
	 */
	public String getContentContactPhone() {
		return contentContactPhone;
	}

	/**
	 * @param contentContactPhone the contentContactPhone to set
	 */
	public void setContentContactPhone(String contentContactPhone) {
		this.contentContactPhone = contentContactPhone;
	}

	/**
	 * @return the contentContactLanguage
	 */
	public String getContentContactLanguage() {
		return contentContactLanguage;
	}

	/**
	 * @param contentContactLanguage the contentContactLanguage to set
	 */
	public void setContentContactLanguage(String contentContactLanguage) {
		this.contentContactLanguage = contentContactLanguage;
	}

	/**
	 * @return the numOfBedRooms
	 */
	public int getNumOfBedRooms() {
		return numOfBedRooms;
	}

	/**
	 * @param numOfBedRooms the numOfBedRooms to set
	 */
	public void setNumOfBedRooms(int numOfBedRooms) {
		this.numOfBedRooms = numOfBedRooms;
	}

	/**
	 * @return the roomType
	 */
	public String getRoomType() {
		return roomType;
	}

	/**
	 * @param roomType the roomType to set
	 */
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	/**
	 * @return the maxPersonOfStayInTheVilla
	 */
	public String getMaxPersonOfStayInTheVilla() {
		return maxPersonOfStayInTheVilla;
	}

	/**
	 * @param maxPersonOfStayInTheVilla the maxPersonOfStayInTheVilla to set
	 */
	public void setMaxPersonOfStayInTheVilla(String maxPersonOfStayInTheVilla) {
		this.maxPersonOfStayInTheVilla = maxPersonOfStayInTheVilla;
	}

	/**
	 * @return the minimumSizeofRoom
	 */
	public String getMinimumSizeofRoom() {
		return minimumSizeofRoom;
	}

	/**
	 * @param minimumSizeofRoom the minimumSizeofRoom to set
	 */
	public void setMinimumSizeofRoom(String minimumSizeofRoom) {
		this.minimumSizeofRoom = minimumSizeofRoom;
	}

	/**
	 * @return the smokingNonSmoking
	 */
	public String getSmokingNonSmoking() {
		return smokingNonSmoking;
	}

	/**
	 * @param smokingNonSmoking the smokingNonSmoking to set
	 */
	public void setSmokingNonSmoking(String smokingNonSmoking) {
		this.smokingNonSmoking = smokingNonSmoking;
	}

	/**
	 * @return the typeOfBedding
	 */
	public String getTypeOfBedding() {
		return typeOfBedding;
	}

	/**
	 * @param typeOfBedding the typeOfBedding to set
	 */
	public void setTypeOfBedding(String typeOfBedding) {
		this.typeOfBedding = typeOfBedding;
	}

	/**
	 * @return the airconditioning
	 */
	public int getAirconditioning() {
		return airconditioning;
	}

	/**
	 * @param airconditioning the airconditioning to set
	 */
	public void setAirconditioning(int airconditioning) {
		this.airconditioning = airconditioning;
	}

	/**
	 * @return the washingmachine
	 */
	public int getWashingmachine() {
		return washingmachine;
	}

	/**
	 * @param washingmachine the washingmachine to set
	 */
	public void setWashingmachine(int washingmachine) {
		this.washingmachine = washingmachine;
	}

	/**
	 * @return the clothesdryer
	 */
	public int getClothesdryer() {
		return clothesdryer;
	}

	/**
	 * @param clothesdryer the clothesdryer to set
	 */
	public void setClothesdryer(int clothesdryer) {
		this.clothesdryer = clothesdryer;
	}

	/**
	 * @return the desk
	 */
	public int getDesk() {
		return desk;
	}

	/**
	 * @param desk the desk to set
	 */
	public void setDesk(int desk) {
		this.desk = desk;
	}

	/**
	 * @return the extralongbeds
	 */
	public int getExtralongbeds() {
		return extralongbeds;
	}

	/**
	 * @param extralongbeds the extralongbeds to set
	 */
	public void setExtralongbeds(int extralongbeds) {
		this.extralongbeds = extralongbeds;
	}

	/**
	 * @return the fan
	 */
	public int getFan() {
		return fan;
	}

	/**
	 * @param fan the fan to set
	 */
	public void setFan(int fan) {
		this.fan = fan;
	}

	/**
	 * @return the firePlace
	 */
	public int getFirePlace() {
		return firePlace;
	}

	/**
	 * @param firePlace the firePlace to set
	 */
	public void setFirePlace(int firePlace) {
		this.firePlace = firePlace;
	}

	/**
	 * @return the roomHeating
	 */
	public int getRoomHeating() {
		return roomHeating;
	}

	/**
	 * @param roomHeating the roomHeating to set
	 */
	public void setRoomHeating(int roomHeating) {
		this.roomHeating = roomHeating;
	}

	/**
	 * @return the roomHottub
	 */
	public int getRoomHottub() {
		return roomHottub;
	}

	/**
	 * @param roomHottub the roomHottub to set
	 */
	public void setRoomHottub(int roomHottub) {
		this.roomHottub = roomHottub;
	}

	/**
	 * @return the interConnectingRoomsAvailable
	 */
	public int getInterConnectingRoomsAvailable() {
		return interConnectingRoomsAvailable;
	}

	/**
	 * @param interConnectingRoomsAvailable the interConnectingRoomsAvailable to set
	 */
	public void setInterConnectingRoomsAvailable(int interConnectingRoomsAvailable) {
		this.interConnectingRoomsAvailable = interConnectingRoomsAvailable;
	}

	/**
	 * @return the clothingIron
	 */
	public int getClothingIron() {
		return clothingIron;
	}

	/**
	 * @param clothingIron the clothingIron to set
	 */
	public void setClothingIron(int clothingIron) {
		this.clothingIron = clothingIron;
	}

	/**
	 * @return the ironingFacilities
	 */
	public int getIroningFacilities() {
		return ironingFacilities;
	}

	/**
	 * @param ironingFacilities the ironingFacilities to set
	 */
	public void setIroningFacilities(int ironingFacilities) {
		this.ironingFacilities = ironingFacilities;
	}

	/**
	 * @return the mosquitonet
	 */
	public int getMosquitonet() {
		return mosquitonet;
	}

	/**
	 * @param mosquitonet the mosquitonet to set
	 */
	public void setMosquitonet(int mosquitonet) {
		this.mosquitonet = mosquitonet;
	}

	/**
	 * @return the privatePool
	 */
	public int getPrivatePool() {
		return privatePool;
	}

	/**
	 * @param privatePool the privatePool to set
	 */
	public void setPrivatePool(int privatePool) {
		this.privatePool = privatePool;
	}

	/**
	 * @return the roomSafeDepositBox
	 */
	public int getRoomSafeDepositBox() {
		return roomSafeDepositBox;
	}

	/**
	 * @param roomSafeDepositBox the roomSafeDepositBox to set
	 */
	public void setRoomSafeDepositBox(int roomSafeDepositBox) {
		this.roomSafeDepositBox = roomSafeDepositBox;
	}

	/**
	 * @return the seatingarea
	 */
	public int getSeatingarea() {
		return seatingarea;
	}

	/**
	 * @param seatingarea the seatingarea to set
	 */
	public void setSeatingarea(int seatingarea) {
		this.seatingarea = seatingarea;
	}

	/**
	 * @return the sofa
	 */
	public int getSofa() {
		return sofa;
	}

	/**
	 * @param sofa the sofa to set
	 */
	public void setSofa(int sofa) {
		this.sofa = sofa;
	}

	/**
	 * @return the soundProofing
	 */
	public int getSoundProofing() {
		return soundProofing;
	}

	/**
	 * @param soundProofing the soundProofing to set
	 */
	public void setSoundProofing(int soundProofing) {
		this.soundProofing = soundProofing;
	}

	/**
	 * @return the trouserpress
	 */
	public int getTrouserpress() {
		return trouserpress;
	}

	/**
	 * @param trouserpress the trouserpress to set
	 */
	public void setTrouserpress(int trouserpress) {
		this.trouserpress = trouserpress;
	}

	/**
	 * @return the shower
	 */
	public int getShower() {
		return shower;
	}

	/**
	 * @param shower the shower to set
	 */
	public void setShower(int shower) {
		this.shower = shower;
	}

	/**
	 * @return the bathroom
	 */
	public int getBathroom() {
		return bathroom;
	}

	/**
	 * @param bathroom the bathroom to set
	 */
	public void setBathroom(int bathroom) {
		this.bathroom = bathroom;
	}

	/**
	 * @return the sharedBathroom
	 */
	public int getSharedBathroom() {
		return sharedBathroom;
	}

	/**
	 * @param sharedBathroom the sharedBathroom to set
	 */
	public void setSharedBathroom(int sharedBathroom) {
		this.sharedBathroom = sharedBathroom;
	}

	/**
	 * @return the handShower
	 */
	public int getHandShower() {
		return handShower;
	}

	/**
	 * @param handShower the handShower to set
	 */
	public void setHandShower(int handShower) {
		this.handShower = handShower;
	}

	/**
	 * @return the bidet
	 */
	public int getBidet() {
		return bidet;
	}

	/**
	 * @param bidet the bidet to set
	 */
	public void setBidet(int bidet) {
		this.bidet = bidet;
	}

	/**
	 * @return the hairdryer
	 */
	public int getHairdryer() {
		return hairdryer;
	}

	/**
	 * @param hairdryer the hairdryer to set
	 */
	public void setHairdryer(int hairdryer) {
		this.hairdryer = hairdryer;
	}

	/**
	 * @return the roomSauna
	 */
	public int getRoomSauna() {
		return roomSauna;
	}

	/**
	 * @param roomSauna the roomSauna to set
	 */
	public void setRoomSauna(int roomSauna) {
		this.roomSauna = roomSauna;
	}

	/**
	 * @return the spabath
	 */
	public int getSpabath() {
		return spabath;
	}

	/**
	 * @param spabath the spabath to set
	 */
	public void setSpabath(int spabath) {
		this.spabath = spabath;
	}

	/**
	 * @return the toilet
	 */
	public int getToilet() {
		return toilet;
	}

	/**
	 * @param toilet the toilet to set
	 */
	public void setToilet(int toilet) {
		this.toilet = toilet;
	}

	/**
	 * @return the cdPlayer
	 */
	public int getCdPlayer() {
		return cdPlayer;
	}

	/**
	 * @param cdPlayer the cdPlayer to set
	 */
	public void setCdPlayer(int cdPlayer) {
		this.cdPlayer = cdPlayer;
	}

	/**
	 * @return the radio
	 */
	public int getRadio() {
		return radio;
	}

	/**
	 * @param radio the radio to set
	 */
	public void setRadio(int radio) {
		this.radio = radio;
	}

	/**
	 * @return the ipad
	 */
	public int getIpad() {
		return ipad;
	}

	/**
	 * @param ipad the ipad to set
	 */
	public void setIpad(int ipad) {
		this.ipad = ipad;
	}

	/**
	 * @return the ipodDockingStation
	 */
	public int getIpodDockingStation() {
		return ipodDockingStation;
	}

	/**
	 * @param ipodDockingStation the ipodDockingStation to set
	 */
	public void setIpodDockingStation(int ipodDockingStation) {
		this.ipodDockingStation = ipodDockingStation;
	}

	/**
	 * @return the cableChannels
	 */
	public int getCableChannels() {
		return cableChannels;
	}

	/**
	 * @param cableChannels the cableChannels to set
	 */
	public void setCableChannels(int cableChannels) {
		this.cableChannels = cableChannels;
	}

	/**
	 * @return the satelliteChannels
	 */
	public int getSatelliteChannels() {
		return satelliteChannels;
	}

	/**
	 * @param satelliteChannels the satelliteChannels to set
	 */
	public void setSatelliteChannels(int satelliteChannels) {
		this.satelliteChannels = satelliteChannels;
	}

	/**
	 * @return the tv
	 */
	public int getTv() {
		return tv;
	}

	/**
	 * @param tv the tv to set
	 */
	public void setTv(int tv) {
		this.tv = tv;
	}

	/**
	 * @return the dvdPlayer
	 */
	public int getDvdPlayer() {
		return dvdPlayer;
	}

	/**
	 * @param dvdPlayer the dvdPlayer to set
	 */
	public void setDvdPlayer(int dvdPlayer) {
		this.dvdPlayer = dvdPlayer;
	}

	/**
	 * @return the computer
	 */
	public int getComputer() {
		return computer;
	}

	/**
	 * @param computer the computer to set
	 */
	public void setComputer(int computer) {
		this.computer = computer;
	}

	/**
	 * @return the laptop
	 */
	public int getLaptop() {
		return laptop;
	}

	/**
	 * @param laptop the laptop to set
	 */
	public void setLaptop(int laptop) {
		this.laptop = laptop;
	}

	/**
	 * @return the laptopSafeBox
	 */
	public int getLaptopSafeBox() {
		return laptopSafeBox;
	}

	/**
	 * @param laptopSafeBox the laptopSafeBox to set
	 */
	public void setLaptopSafeBox(int laptopSafeBox) {
		this.laptopSafeBox = laptopSafeBox;
	}

	/**
	 * @return the gameConsole
	 */
	public int getGameConsole() {
		return gameConsole;
	}

	/**
	 * @param gameConsole the gameConsole to set
	 */
	public void setGameConsole(int gameConsole) {
		this.gameConsole = gameConsole;
	}

	/**
	 * @return the telephone
	 */
	public int getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the videoGames
	 */
	public int getVideoGames() {
		return videoGames;
	}

	/**
	 * @param videoGames the videoGames to set
	 */
	public void setVideoGames(int videoGames) {
		this.videoGames = videoGames;
	}

	/**
	 * @return the barbecue
	 */
	public int getBarbecue() {
		return barbecue;
	}

	/**
	 * @param barbecue the barbecue to set
	 */
	public void setBarbecue(int barbecue) {
		this.barbecue = barbecue;
	}

	/**
	 * @return the diningarea
	 */
	public int getDiningarea() {
		return diningarea;
	}

	/**
	 * @param diningarea the diningarea to set
	 */
	public void setDiningarea(int diningarea) {
		this.diningarea = diningarea;
	}

	/**
	 * @return the dishwasher
	 */
	public int getDishwasher() {
		return dishwasher;
	}

	/**
	 * @param dishwasher the dishwasher to set
	 */
	public void setDishwasher(int dishwasher) {
		this.dishwasher = dishwasher;
	}

	/**
	 * @return the electrickettle
	 */
	public int getElectrickettle() {
		return electrickettle;
	}

	/**
	 * @param electrickettle the electrickettle to set
	 */
	public void setElectrickettle(int electrickettle) {
		this.electrickettle = electrickettle;
	}

	/**
	 * @return the kitchen
	 */
	public int getKitchen() {
		return kitchen;
	}

	/**
	 * @param kitchen the kitchen to set
	 */
	public void setKitchen(int kitchen) {
		this.kitchen = kitchen;
	}

	/**
	 * @return the kitchenette
	 */
	public int getKitchenette() {
		return kitchenette;
	}

	/**
	 * @param kitchenette the kitchenette to set
	 */
	public void setKitchenette(int kitchenette) {
		this.kitchenette = kitchenette;
	}

	/**
	 * @return the kitchenware
	 */
	public int getKitchenware() {
		return kitchenware;
	}

	/**
	 * @param kitchenware the kitchenware to set
	 */
	public void setKitchenware(int kitchenware) {
		this.kitchenware = kitchenware;
	}

	/**
	 * @return the microwave
	 */
	public int getMicrowave() {
		return microwave;
	}

	/**
	 * @param microwave the microwave to set
	 */
	public void setMicrowave(int microwave) {
		this.microwave = microwave;
	}

	/**
	 * @return the minibar
	 */
	public int getMinibar() {
		return minibar;
	}

	/**
	 * @param minibar the minibar to set
	 */
	public void setMinibar(int minibar) {
		this.minibar = minibar;
	}

	/**
	 * @return the oven
	 */
	public int getOven() {
		return oven;
	}

	/**
	 * @param oven the oven to set
	 */
	public void setOven(int oven) {
		this.oven = oven;
	}

	/**
	 * @return the refrigerator
	 */
	public int getRefrigerator() {
		return refrigerator;
	}

	/**
	 * @param refrigerator the refrigerator to set
	 */
	public void setRefrigerator(int refrigerator) {
		this.refrigerator = refrigerator;
	}

	/**
	 * @return the stove
	 */
	public int getStove() {
		return stove;
	}

	/**
	 * @param stove the stove to set
	 */
	public void setStove(int stove) {
		this.stove = stove;
	}

	/**
	 * @return the coffeeTeamaker
	 */
	public int getCoffeeTeamaker() {
		return coffeeTeamaker;
	}

	/**
	 * @param coffeeTeamaker the coffeeTeamaker to set
	 */
	public void setCoffeeTeamaker(int coffeeTeamaker) {
		this.coffeeTeamaker = coffeeTeamaker;
	}

	/**
	 * @return the toaster
	 */
	public int getToaster() {
		return toaster;
	}

	/**
	 * @param toaster the toaster to set
	 */
	public void setToaster(int toaster) {
		this.toaster = toaster;
	}

	/**
	 * @return the alarmclock
	 */
	public int getAlarmclock() {
		return alarmclock;
	}

	/**
	 * @param alarmclock the alarmclock to set
	 */
	public void setAlarmclock(int alarmclock) {
		this.alarmclock = alarmclock;
	}

	/**
	 * @return the balcony
	 */
	public int getBalcony() {
		return balcony;
	}

	/**
	 * @param balcony the balcony to set
	 */
	public void setBalcony(int balcony) {
		this.balcony = balcony;
	}

	/**
	 * @return the gardenView
	 */
	public int getGardenView() {
		return gardenView;
	}

	/**
	 * @param gardenView the gardenView to set
	 */
	public void setGardenView(int gardenView) {
		this.gardenView = gardenView;
	}

	/**
	 * @return the lakeView
	 */
	public int getLakeView() {
		return lakeView;
	}

	/**
	 * @param lakeView the lakeView to set
	 */
	public void setLakeView(int lakeView) {
		this.lakeView = lakeView;
	}

	/**
	 * @return the landmarkView
	 */
	public int getLandmarkView() {
		return landmarkView;
	}

	/**
	 * @param landmarkView the landmarkView to set
	 */
	public void setLandmarkView(int landmarkView) {
		this.landmarkView = landmarkView;
	}

	/**
	 * @return the mountainView
	 */
	public int getMountainView() {
		return mountainView;
	}

	/**
	 * @param mountainView the mountainView to set
	 */
	public void setMountainView(int mountainView) {
		this.mountainView = mountainView;
	}

	/**
	 * @return the patio
	 */
	public int getPatio() {
		return patio;
	}

	/**
	 * @param patio the patio to set
	 */
	public void setPatio(int patio) {
		this.patio = patio;
	}

	/**
	 * @return the poolview
	 */
	public int getPoolview() {
		return poolview;
	}

	/**
	 * @param poolview the poolview to set
	 */
	public void setPoolview(int poolview) {
		this.poolview = poolview;
	}

	/**
	 * @return the seaview
	 */
	public int getSeaview() {
		return seaview;
	}

	/**
	 * @param seaview the seaview to set
	 */
	public void setSeaview(int seaview) {
		this.seaview = seaview;
	}

	/**
	 * @return the companyNameForInvoicing
	 */
	public String getCompanyNameForInvoicing() {
		return companyNameForInvoicing;
	}

	/**
	 * @param companyNameForInvoicing the companyNameForInvoicing to set
	 */
	public void setCompanyNameForInvoicing(String companyNameForInvoicing) {
		this.companyNameForInvoicing = companyNameForInvoicing;
	}

	/**
	 * @return the attentionOff
	 */
	public String getAttentionOff() {
		return attentionOff;
	}

	/**
	 * @param attentionOff the attentionOff to set
	 */
	public void setAttentionOff(String attentionOff) {
		this.attentionOff = attentionOff;
	}

	/**
	 * @return the legalAdress
	 */
	public String getLegalAdress() {
		return legalAdress;
	}

	/**
	 * @param legalAdress the legalAdress to set
	 */
	public void setLegalAdress(String legalAdress) {
		this.legalAdress = legalAdress;
	}

	/**
	 * @return the legalZipCode
	 */
	public String getLegalZipCode() {
		return legalZipCode;
	}

	/**
	 * @param legalZipCode the legalZipCode to set
	 */
	public void setLegalZipCode(String legalZipCode) {
		this.legalZipCode = legalZipCode;
	}

	/**
	 * @return the legalCity
	 */
	public String getLegalCity() {
		return legalCity;
	}

	/**
	 * @param legalCity the legalCity to set
	 */
	public void setLegalCity(String legalCity) {
		this.legalCity = legalCity;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the invoiceMedium
	 */
	public String getInvoiceMedium() {
		return invoiceMedium;
	}

	/**
	 * @param invoiceMedium the invoiceMedium to set
	 */
	public void setInvoiceMedium(String invoiceMedium) {
		this.invoiceMedium = invoiceMedium;
	}

	/**
	 * vatOrTax="";
	private String vatType="";
	private String taxIncludedOrExcluded="";
	private String vatOrTaxAmount="";
	private String cityTaxType="NA";
	private String cityTaxStatus="";
	private String cityTaxAmount="";
	 */
	/**
	 * @return the vat_tax
	 */
	public String getVatOrTax() {
		return vatOrTax.toUpperCase();
	}

	/**
	 * @param vat_tax the vat_tax to set
	 */
	public void setVatOrTax(String vatOrTax) {
		this.vatOrTax = vatOrTax;
	}

	/**
	 * @return the vat_type
	 */
	public String getVatType() {
		return vatType;
	}

	/**
	 * @param vat_type the vat_type to set
	 */
	public void setVatType(String vatType) {
		this.vatType = vatType;
	}

	/**
	 * @return the included_Excluded
	 */
	public String getTaxIncludedOrExcluded() {
		return taxIncludedOrExcluded;
	}

	/**
	 * @param included_Excluded the included_Excluded to set
	 */
	public void setTaxIncludedOrExcluded(String taxIncludedOrExcluded) {
		this.taxIncludedOrExcluded = taxIncludedOrExcluded;
	}

	/**
	 * @return the vatAmount
	 */
	public String getVatOrTaxAmount() {
		return vatOrTaxAmount;
	}

	/**
	 * @param vatAmount the vatAmount to set
	 */
	public void setVatOrTaxAmount(String vatOrTaxAmount) {
		this.vatOrTaxAmount = vatOrTaxAmount;
	}

	/**
	 * @return the cityTaxType
	 */
	public String getCityTaxType() {
		return cityTaxType;
	}

	/**
	 * @param cityTaxType the cityTaxType to set
	 */
	public void setCityTaxType(String cityTaxType) {
		this.cityTaxType = cityTaxType;
	}

	/**
	 * @return the cityTaxStatus
	 */
	public String getCityTaxStatus() {
		return cityTaxStatus;
	}

	/**
	 * @param cityTaxStatus the cityTaxStatus to set
	 */
	public void setCityTaxStatus(String cityTaxStatus) {
		this.cityTaxStatus = cityTaxStatus;
	}

	/**
	 * @return the cityTaxAmount
	 */
	public String getCityTaxAmount() {
		return cityTaxAmount;
	}

	/**
	 * @param cityTaxAmount the cityTaxAmount to set
	 */
	public void setCityTaxAmount(String cityTaxAmount) {
		this.cityTaxAmount = cityTaxAmount;
	}

	/**
	 * @return the serviceChargetype
	 */
	public String getServiceChargetype() {
		return serviceChargetype;
	}

	/**
	 * @param serviceChargetype the serviceChargetype to set
	 */
	public void setServiceChargetype(String serviceChargetype) {
		this.serviceChargetype = serviceChargetype;
	}

	/**
	 * @return the serviceChargeStatus
	 */
	public String getServiceChargeStatus() {
		return serviceChargeStatus;
	}

	/**
	 * @param serviceChargeStatus the serviceChargeStatus to set
	 */
	public void setServiceChargeStatus(String serviceChargeStatus) {
		this.serviceChargeStatus = serviceChargeStatus;
	}

	/**
	 * @return the serviceChargeAmount
	 */
	public String getServiceChargeAmount() {
		return serviceChargeAmount;
	}

	/**
	 * @param serviceChargeAmount the serviceChargeAmount to set
	 */
	public void setServiceChargeAmount(String serviceChargeAmount) {
		this.serviceChargeAmount = serviceChargeAmount;
	}

	/**
	 * @return the childrenAllowed
	 */
	public String getChildrenAllowed() {
		return childrenAllowed;
	}

	/**
	 * @param childrenAllowed the childrenAllowed to set
	 */
	public void setChildrenAllowed(String childrenAllowed) {
		this.childrenAllowed = childrenAllowed;
	}

	/**
	 * @return the numberOfChildrenAllowedStaying
	 */
	public String getNumberOfChildrenAllowedStaying() {
		return numberOfChildrenAllowedStaying;
	}

	/**
	 * @param numberOfChildrenAllowedStaying the numberOfChildrenAllowedStaying to set
	 */
	public void setNumberOfChildrenAllowedStaying(
			String numberOfChildrenAllowedStaying) {
		this.numberOfChildrenAllowedStaying = numberOfChildrenAllowedStaying;
	}

	/**
	 * @return the childBedcost
	 */
	public String getChildBedcost() {
		return childBedcost;
	}

	/**
	 * @param childBedcost the childBedcost to set
	 */
	public void setChildBedcost(String childBedcost) {
		this.childBedcost = childBedcost;
	}

	/**
	 * @return the groupPolicy
	 */
	public String getGroupPolicy() {
		return groupPolicy;
	}

	/**
	 * @param groupPolicy the groupPolicy to set
	 */
	public void setGroupPolicy(String groupPolicy) {
		this.groupPolicy = groupPolicy;
	}

	/**
	 * @return the roomPetsAllowed
	 */
	public String getRoomPetsAllowed() {
		return roomPetsAllowed;
	}

	/**
	 * @param roomPetsAllowed the roomPetsAllowed to set
	 */
	public void setRoomPetsAllowed(String roomPetsAllowed) {
		this.roomPetsAllowed = roomPetsAllowed;
	}

	/**
	 * @return the petsFee
	 */
	public String getPetsFee() {
		return petsFee;
	}

	/**
	 * @param petsFee the petsFee to set
	 */
	public void setPetsFee(String petsFee) {
		this.petsFee = petsFee;
	}

	/**
	 * @return the parkingFee
	 */
	public String getParkingFee() {
		return parkingFee;
	}

	/**
	 * @param parkingFee the parkingFee to set
	 */
	public void setParkingFee(String parkingFee) {
		this.parkingFee = parkingFee;
	}

	/**
	 * @return the roomParking
	 */
	public String getRoomParking() {
		return roomParking;
	}

	/**
	 * @param roomParking the roomParking to set
	 */
	public void setRoomParking(String roomParking) {
		this.roomParking = roomParking;
	}

	/**
	 * @return the parkingReservationNeeded
	 */
	public String getParkingReservationNeeded() {
		return parkingReservationNeeded;
	}

	/**
	 * @param parkingReservationNeeded the parkingReservationNeeded to set
	 */
	public void setParkingReservationNeeded(String parkingReservationNeeded) {
		this.parkingReservationNeeded = parkingReservationNeeded;
	}

	/**
	 * @return the roomInternetAccess
	 */
	public String getRoomInternetAccess() {
		return roomInternetAccess;
	}

	/**
	 * @param roomInternetAccess the roomInternetAccess to set
	 */
	public void setRoomInternetAccess(String roomInternetAccess) {
		this.roomInternetAccess = roomInternetAccess;
	}

	/**
	 * @return the internet
	 */
	public String getInternet() {
		return internet;
	}

	/**
	 * @param internet the internet to set
	 */
	public void setInternet(String internet) {
		this.internet = internet;
	}

	/**
	 * @return the internetAccessType
	 */
	public String getInternetAccessType() {
		return internetAccessType;
	}

	/**
	 * @param internetAccessType the internetAccessType to set
	 */
	public void setInternetAccessType(String internetAccessType) {
		this.internetAccessType = internetAccessType;
	}

	/**
	 * @return the internetAccessInRoom
	 */
	public String getInternetAccessInRoom() {
		return internetAccessInRoom;
	}

	/**
	 * @param internetAccessInRoom the internetAccessInRoom to set
	 */
	public void setInternetAccessInRoom(String internetAccessInRoom) {
		this.internetAccessInRoom = internetAccessInRoom;
	}

	/**
	 * @return the internetAccessInPublicArea
	 */
	public String getInternetAccessInPublicArea() {
		return internetAccessInPublicArea;
	}

	/**
	 * @param internetAccessInPublicArea the internetAccessInPublicArea to set
	 */
	public void setInternetAccessInPublicArea(String internetAccessInPublicArea) {
		this.internetAccessInPublicArea = internetAccessInPublicArea;
	}

	/**
	 * @return the internetAccessInBusinessCentre
	 */
	public String getInternetAccessInBusinessCentre() {
		return internetAccessInBusinessCentre;
	}

	/**
	 * @param internetAccessInBusinessCentre the internetAccessInBusinessCentre to set
	 */
	public void setInternetAccessInBusinessCentre(
			String internetAccessInBusinessCentre) {
		this.internetAccessInBusinessCentre = internetAccessInBusinessCentre;
	}

	/**
	 * @return the mealsPlan
	 */
	public String getMealsPlan() {
		return mealsPlan;
	}

	/**
	 * @param mealsPlan the mealsPlan to set
	 */
	public void setMealsPlan(String mealsPlan) {
		this.mealsPlan = mealsPlan;
	}

	/**
	 * @return the mealsPlanType
	 */
	public String getMealsPlanType() {
		return mealsPlanType;
	}

	/**
	 * @param mealsPlanType the mealsPlanType to set
	 */
	public void setMealsPlanType(String mealsPlanType) {
		this.mealsPlanType = mealsPlanType;
	}

	/**
	 * @param roomName the roomName to set
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getContractFee() {
		return contractFee;
	}

	public void setContractFee(String contractFee) {
		this.contractFee = contractFee;
	}

	public int getIndoorSwimmingpool() {
		return indoorSwimmingpool;
	}

	public void setIndoorSwimmingpool(int indoorSwimmingpool) {
		this.indoorSwimmingpool = indoorSwimmingpool;
	}

	public String getFinalCleaningFee() {
		return finalCleaningFee;
	}

	public void setIsFinalCleaningFee(String finalCleaningFee) {
		this.finalCleaningFee = finalCleaningFee;
	}

	public String getSecurityDeposit() {
		return securityDeposit;
	}

	public void setSecurityDeposit(String securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

	public String getPriceContactName() {
		// TODO Auto-generated method stub
		return priceContactName;
	}

	public String getPriceContactEmail() {
		// TODO Auto-generated method stub
		return priceContactEmail;
	}

	public String getPriceContactPhone() {
		// TODO Auto-generated method stub
		return priceContactPhone;
	}

	public String getPriceContactLanguage() {
		// TODO Auto-generated method stub
		return priceContactLanguage;
	}

//	We will use the default values (Bookingpal info) for price contact.
//	public void setPriceContactName(String priceContactName) {
//		this.priceContactName = priceContactName;
//	}
//
//	public void setPriceContactEmail(String priceContactEmail) {
//		this.priceContactEmail = priceContactEmail;
//	}
//
//	public void setPriceContactPhone(String priceContactPhone) {
//		this.priceContactPhone = priceContactPhone;
//	}
//
//	public void setPriceContactLanguage(String priceContactLanguage) {
//		this.priceContactLanguage = priceContactLanguage;
//	}
//


}