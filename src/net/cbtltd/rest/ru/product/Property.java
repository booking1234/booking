package net.cbtltd.rest.ru.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Property")
public class Property {
	
	@XmlElement(name = "ID", required = true)
	protected ID id;
	@XmlElement(name = "PUID")
	protected PUID puid;
	@XmlElement(name = "Name", required = true)
	protected String name;
	@XmlElement(name = "OwnerID", required = true)
	protected Integer ownerId;
	@XmlElement(name = "DetailedLocationID", required = true)
	protected Integer detailedLocationId;
	@XmlElement(name = "LastMod", required = true)
	protected LastMod lastMod;
	@XmlElement(name = "IMAP", required = true)
	protected boolean imap;
	@XmlElement(name = "IMU", required = true)
	protected boolean imu;
	@XmlElement(name = "IsActive")
	protected boolean isActive;
	@XmlElement(name = "IsArchived")
	protected boolean isArchived;
	@XmlElement(name = "CleaningPrice", required = true)
	protected double cleaningPrice; 
	@XmlElement(name = "Space", required = true)
	protected int space;
	@XmlElement(name = "StandardGuests", required = true)
	protected int standardGuests;
	@XmlElement(name = "CanSleepMax", required = true)
	protected int canSleepMax;
	@XmlElement(name = "PropertyTypeID", required = true)
	protected int propertyTypeID;
	@XmlElement(name = "Floor", required = true)
	protected int floor;
	@XmlElement(name = "Street", required = true)
	protected String street;
	@XmlElement(name = "ZipCode", required = true)
	protected String zipCode;
	@XmlElement(name = "Coordinates", required = true)
	protected Coordinates coordinates;
	@XmlElement(name = "Distances")
	protected Distances distances;
	@XmlElement(name = "CompositionRooms")
	protected CompositionRooms compositionRooms;
	@XmlElement(name = "CompositionRoomsAmenities")
	protected CompositionRoomsAmenities compositionRoomsAmenities;
	@XmlElement(name = "Amenities")
	protected Amenities amenities;
	@XmlElement(name = "Images")
	protected Images images;
	@XmlElement(name = "ArrivalInstructions")
	protected ArrivalInstructions arrivalInstructions;
	@XmlElement(name = "CheckInOut", required = true)
	protected CheckInOut checkInOut;
	@XmlElement(name = "PaymentMethods", required = true)
	protected PaymentMethods paymentMethods;
	@XmlElement(name = "Deposit", required = true)
	protected Deposit deposit;
	@XmlElement(name = "CancellationPolicies", required = true)
	protected CancellationPolicies cancellationPolicies;
	@XmlElement(name = "Descriptions")
	protected Descriptions descriptions;
	@XmlElement(name = "SecurityDeposit", required = true)
	protected Deposit securityDeposit;
	
	/**
	 * @return the code that uniquely identifies a single property
	 */
	public ID getID() {
		return this.id;
	}
	
	/**
	 * set ID
	 */
	public void setID(ID id) {
		this.id = id;
	}
	
	/**
	 * @return the PUID
	 */
	public PUID getPUID() {
		return this.puid;
	}

	/**
	 * set the PUID
	 */
	public void setPUID(PUID puid) {
		this.puid = puid;
	}

	/**
	 * @return the property name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * set property name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the code that uniquely identifies a property owner
	 */
	public Integer getOwnerID() {
		return this.ownerId;
	}
	
	/**
	 * set OwnerID
	 */
	public void setOwnerID(Integer ownerID) {
		this.ownerId = ownerID;
	}
	
	/**
	 * @return the most detailed location
	 */
	public Integer getDetailedLocationID() {
		return this.detailedLocationId;
	}
	
	/**
	 * set DetailedLocationID
	 */
	public void setDetailedLocationID(Integer detailedLocationID) {
		this.detailedLocationId = detailedLocationID;
	}
	
	/**
	 * @return the date of last modification the property static data (format YYYY-MM-DD HH-MM-SS)
	 */
	public LastMod getLastMod() {
		return this.lastMod;
	}
	
	/**
	 * set LastMod
	 */
	public void setLastMod(LastMod lastMod) {
		this.lastMod = lastMod;
	}
	
	/**
	 * @return the IMAP (Special value for internal use)
	 */
	public boolean isIMAP() {
		return imap;
	}
	
	/**
	 * set IMAP
	 */
	public void setIMAP(boolean imap) {
		this.imap = imap;
	}
	
	/**
	 * @return the IMU
	 */
	public boolean isIMU() {
		return this.imu;
	}
	
	/**
	 * @param IMU the IMU to set
	 */
	public void setIMU(boolean imu) {
		this.imu = imu;
	}
	
	/**
	 * @return the IsActive
	 */
	public boolean isIsActive() {
		return this.isActive;
	}
	
	/**
	 * set IsActive
	 */
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	/**
	 * @return the IsArchived
	 */
	public boolean isIsArchived() {
		return this.isArchived;
	}
	
	/**
	 * set IsArchived
	 */
	public void setIsArchived(boolean isArchived) {
		this.isArchived = isArchived;
	}
	
	/**
	 * @return the property cleaning price, always added to final price of booking
	 */
	public double getCleaningPrice() {
		return this.cleaningPrice;
	}
	
	/**
	 * set CleaningPrice
	 */
	public void setCleaningPrice(double cleaningPrice) {
		this.cleaningPrice = cleaningPrice;
	}
	
	/**
	 * @return the living space in square meters
	 */
	public int getSpace() {
		return this.space;
	}
	
	/**
	 * set space
	 */
	public void setSpace(int space) {
		this.space = space;
	}
	
	/**
	 * @return the number of guest included in the base price
	 */
	public int getStandardGuests() {
		return this.standardGuests;
	}
	
	/**
	 * set StandardGuests
	 */
	public void setStandardGuests(int standardGuests) {
		this.standardGuests = standardGuests;
	}
	
	/**
	 * @return the maximum number of guests
	 */
	public int getCanSleepMax() {
		return this.canSleepMax;
	}
	
	/**
	 * set CanSleepMax
	 */
	public void setCanSleepMax(int canSleepMax) {
		this.canSleepMax = canSleepMax;
	}
	
	/**
	 * @return the PropertyTypeID
	 */
	public int getPropertyTypeID() {
		return this.propertyTypeID;
	}
	
	/**
	 * set PropertyTypeID
	 */
	public void setPropertyTypeID(int propertyTypeID) {
		this.propertyTypeID = propertyTypeID;
	}
	
	/**
	 * @return the apartment floor
	 */
	public int getFloor() {
		return this.floor;
	}
	
	/**
	 * set Floor
	 */
	public void setFloor(int floor) {
		this.floor = floor;
	}
	
	/**
	 * @return the property street (street name, building/flat number)
	 */
	public String getStreet() {
		return this.street;
	}
	
	/**
	 * set Street
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	
	/**
	 * @return the property zip code
	 */
	public String getZipCode() {
		return this.zipCode;
	}
	
	/**
	 * set ZipCode
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	/**
	 * @return the geographic coordinates of property
	 */
	public Coordinates getCoordinates() {
		return this.coordinates;
	}
	
	/**
	 * set Coordinates
	 */
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
	
	/**
	 * @return the collection of distances to main city attractions/point
	 */
	public Distances getDistances() {
		return this.distances;
	}
	
	/**
	 * set Distances
	 */
	public void setDistances(Distances distances) {
		this.distances = distances;
	}
	
	/**
	 * @return the collection of rooms
	 */
	public CompositionRooms getCompositionRooms() {
		return this.compositionRooms;
	}
	
	/**
	 * set CompositionRooms
	 */
	public void setCompositionRooms(CompositionRooms compositionRooms) {
		this.compositionRooms = compositionRooms;
	}
	
	/**
	 * @return the collection of rooms with amenities (this element contains informations about amenities available in a given room, 
	 * some general amenities (e.g. parking or wireless internet connection) will be defined in Pull_ListSpecProp_RS/Property/Amenities element)
	 */
	public CompositionRoomsAmenities getCompositionRoomsAmenities() {
		return this.compositionRoomsAmenities;
	}
	
	/**
	 * set compositionRoomsAmenities
	 */
	public void setCompositionRoomsAmenities(CompositionRoomsAmenities compositionRoomsAmenities) {
		this.compositionRoomsAmenities = compositionRoomsAmenities;
	}
	
	/**
	 * @return the collection of apartment amenities
	 */
	public Amenities getAmenities() {
		return this.amenities;
	}
	
	/**
	 * set amenities
	 */
	public void setAmenities(Amenities amenities) {
		this.amenities = amenities;
	}
	
	/**
	 * @return the collection of property images
	 */
	public Images getImages() {
		return this.images;
	}
	
	/**
	 * set images
	 */
	public void setImages(Images images) {
		this.images = images;
	}
	
	/**
	 * @return the property arrival instructions
	 */
	public ArrivalInstructions getArrivalInstructions() {
		return this.arrivalInstructions;
	}
	
	/**
	 * set arrivalInstructions
	 */
	public void setArrivalInstructions(ArrivalInstructions arrivalInstructions) {
		this.arrivalInstructions = arrivalInstructions;
	}
	
	/**
	 * @return the check in, check out details
	 */
	public CheckInOut getCheckInOut() {
		return this.checkInOut;
	}
	
	/**
	 * set checkInOut
	 */
	public void setCheckInOut(CheckInOut checkInOut) {
		this.checkInOut = checkInOut;
	}
	
	/**
	 * @return the collection of acceptable payment methods
	 */
	public PaymentMethods getPaymentMethods() {
		return this.paymentMethods;
	}
	
	/**
	 * set paymentMethods
	 */
	public void setPaymentMethods(PaymentMethods paymentMethods) {
		this.paymentMethods = paymentMethods;
	}
	/**
	 * @return the deposit amount depending on deposit type
	 */
	public Deposit getDeposit() {
		return this.deposit;
	}
	/**
	 * set deposit 
	 */
	public void setDeposit(Deposit deposit) {
		this.deposit = deposit;
	}
	/**
	 * @return the collection of cancellation policies
	 */
	public CancellationPolicies getCancellationPolicies() {
		return this.cancellationPolicies;
	}
	/**
	 * set cancellationPolicies
	 */
	public void setCancellationPolicies(CancellationPolicies cancellationPolicies) {
		this.cancellationPolicies = cancellationPolicies;
	}
	/**
	 * @return the collection of descriptions
	 */
	public Descriptions getDescriptions() {
		return this.descriptions;
	}
	/**
	 * set descriptions
	 */
	public void setDescriptions(Descriptions descriptions) {
		this.descriptions = descriptions;
	}
	/**
	 * @return the refundable security deposit amount depending on deposit type
	 */
	public Deposit getSecurityDeposit() {
		return this.securityDeposit;
	}
	/**
	 * set securityDeposit
	 */
	public void setSecurityDeposit(Deposit securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

}
