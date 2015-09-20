
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.cbtltd.rest.homeaway.feedparser.domain package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ReservationBatch_QNAME = new QName("", "reservationBatch");
    private final static QName _UnitReservations_QNAME = new QName("", "unitReservations");
    private final static QName _RatePeriodsBatch_QNAME = new QName("", "ratePeriodsBatch");
    private final static QName _Entity_QNAME = new QName("", "entity");
    private final static QName _Listing_QNAME = new QName("", "listing");
    private final static QName _ListingBatch_QNAME = new QName("", "listingBatch");
    private final static QName _UnitRatePeriods_QNAME = new QName("", "unitRatePeriods");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.cbtltd.rest.homeaway.feedparser.domain
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UnitWSD }
     * 
     */
    public UnitWSD createUnitWSD() {
        return new UnitWSD();
    }

    /**
     * Create an instance of {@link Location }
     * 
     */
    public Location createLocation() {
        return new Location();
    }

    /**
     * Create an instance of {@link Bedroom }
     * 
     */
    public Bedroom createBedroom() {
        return new Bedroom();
    }

    /**
     * Create an instance of {@link Bathroom }
     * 
     */
    public Bathroom createBathroom() {
        return new Bathroom();
    }

    /**
     * Create an instance of {@link net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserRatePeriods }
     * 
     */
    public net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserRatePeriods createAdvertiserRatePeriods() {
        return new net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserRatePeriods();
    }

    /**
     * Create an instance of {@link net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserRatePeriods.Advertiser }
     * 
     */
    public net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserRatePeriods.Advertiser createAdvertiserRatePeriodsAdvertiser() {
        return new net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserRatePeriods.Advertiser();
    }

    /**
     * Create an instance of {@link AdvertiserListings }
     * 
     */
    public AdvertiserListings createAdvertiserListings() {
        return new AdvertiserListings();
    }

    /**
     * Create an instance of {@link AdvertiserListings.Advertiser }
     * 
     */
    public AdvertiserListings.Advertiser createAdvertiserListingsAdvertiser() {
        return new AdvertiserListings.Advertiser();
    }

    /**
     * Create an instance of {@link net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserReservations }
     * 
     */
    public net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserReservations createAdvertiserReservations() {
        return new net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserReservations();
    }

    /**
     * Create an instance of {@link net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserReservations.Advertiser }
     * 
     */
    public net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserReservations.Advertiser createAdvertiserReservationsAdvertiser() {
        return new net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserReservations.Advertiser();
    }

    /**
     * Create an instance of {@link AdvertiserListingIndex }
     * 
     */
    public AdvertiserListingIndex createAdvertiserListingIndex() {
        return new AdvertiserListingIndex();
    }

    /**
     * Create an instance of {@link Listing }
     * 
     */
    public Listing createListing() {
        return new Listing();
    }

    /**
     * Create an instance of {@link RatePeriod }
     * 
     */
    public RatePeriod createRatePeriod() {
        return new RatePeriod();
    }

    /**
     * Create an instance of {@link Note }
     * 
     */
    public Note createNote() {
        return new Note();
    }

    /**
     * Create an instance of {@link ReservationsBatch }
     * 
     */
    public ReservationsBatch createReservationsBatch() {
        return new ReservationsBatch();
    }

    /**
     * Create an instance of {@link Unit }
     * 
     */
    public Unit createUnit() {
        return new Unit();
    }

    /**
     * Create an instance of {@link UnitWSD.Bathrooms }
     * 
     */
    public UnitWSD.Bathrooms createUnitWSDBathrooms() {
        return new UnitWSD.Bathrooms();
    }

    /**
     * Create an instance of {@link UnitWSD.Bedrooms }
     * 
     */
    public UnitWSD.Bedrooms createUnitWSDBedrooms() {
        return new UnitWSD.Bedrooms();
    }

    /**
     * Create an instance of {@link UnitWSD.FeatureValues }
     * 
     */
    public UnitWSD.FeatureValues createUnitWSDFeatureValues() {
        return new UnitWSD.FeatureValues();
    }

    /**
     * Create an instance of {@link UnitMonetaryInformation }
     * 
     */
    public UnitMonetaryInformation createUnitMonetaryInformation() {
        return new UnitMonetaryInformation();
    }

    /**
     * Create an instance of {@link RatePeriods }
     * 
     */
    public RatePeriods createRatePeriods() {
        return new RatePeriods();
    }

    /**
     * Create an instance of {@link Reservations }
     * 
     */
    public Reservations createReservations() {
        return new Reservations();
    }

    /**
     * Create an instance of {@link Reservation }
     * 
     */
    public Reservation createReservation() {
        return new Reservation();
    }

    /**
     * Create an instance of {@link ListingReservations }
     * 
     */
    public ListingReservations createListingReservations() {
        return new ListingReservations();
    }

    /**
     * Create an instance of {@link RatePeriodsBatch }
     * 
     */
    public RatePeriodsBatch createRatePeriodsBatch() {
        return new RatePeriodsBatch();
    }

    /**
     * Create an instance of {@link ListingBatch }
     * 
     */
    public ListingBatch createListingBatch() {
        return new ListingBatch();
    }

    /**
     * Create an instance of {@link ListingContentIndex }
     * 
     */
    public ListingContentIndex createListingContentIndex() {
        return new ListingContentIndex();
    }

    /**
     * Create an instance of {@link Images }
     * 
     */
    public Images createImages() {
        return new Images();
    }

    /**
     * Create an instance of {@link Image }
     * 
     */
    public Image createImage() {
        return new Image();
    }

    /**
     * Create an instance of {@link ListingRatePeriods }
     * 
     */
    public ListingRatePeriods createListingRatePeriods() {
        return new ListingRatePeriods();
    }

    /**
     * Create an instance of {@link LatLng }
     * 
     */
    public LatLng createLatLng() {
        return new LatLng();
    }

    /**
     * Create an instance of {@link DateRange }
     * 
     */
    public DateRange createDateRange() {
        return new DateRange();
    }

    /**
     * Create an instance of {@link NearestPlace }
     * 
     */
    public NearestPlace createNearestPlace() {
        return new NearestPlace();
    }

    /**
     * Create an instance of {@link ContactPhone }
     * 
     */
    public ContactPhone createContactPhone() {
        return new ContactPhone();
    }

    /**
     * Create an instance of {@link BedroomFeatureValue }
     * 
     */
    public BedroomFeatureValue createBedroomFeatureValue() {
        return new BedroomFeatureValue();
    }

    /**
     * Create an instance of {@link AdContent }
     * 
     */
    public AdContent createAdContent() {
        return new AdContent();
    }

    /**
     * Create an instance of {@link BathroomFeatureValue }
     * 
     */
    public BathroomFeatureValue createBathroomFeatureValue() {
        return new BathroomFeatureValue();
    }

    /**
     * Create an instance of {@link Phone }
     * 
     */
    public Phone createPhone() {
        return new Phone();
    }

    /**
     * Create an instance of {@link ListingContentIndexEntry }
     * 
     */
    public ListingContentIndexEntry createListingContentIndexEntry() {
        return new ListingContentIndexEntry();
    }

    /**
     * Create an instance of {@link GeoCode }
     * 
     */
    public GeoCode createGeoCode() {
        return new GeoCode();
    }

    /**
     * Create an instance of {@link Rate }
     * 
     */
    public Rate createRate() {
        return new Rate();
    }

    /**
     * Create an instance of {@link Money }
     * 
     */
    public Money createMoney() {
        return new Money();
    }

    /**
     * Create an instance of {@link ListingFeatureValue }
     * 
     */
    public ListingFeatureValue createListingFeatureValue() {
        return new ListingFeatureValue();
    }

    /**
     * Create an instance of {@link Text }
     * 
     */
    public Text createText() {
        return new Text();
    }

    /**
     * Create an instance of {@link Link }
     * 
     */
    public Link createLink() {
        return new Link();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link UnitFeatureValue }
     * 
     */
    public UnitFeatureValue createUnitFeatureValue() {
        return new UnitFeatureValue();
    }

    /**
     * Create an instance of {@link Location.NearestPlaces }
     * 
     */
    public Location.NearestPlaces createLocationNearestPlaces() {
        return new Location.NearestPlaces();
    }

    /**
     * Create an instance of {@link Bedroom.Amenities }
     * 
     */
    public Bedroom.Amenities createBedroomAmenities() {
        return new Bedroom.Amenities();
    }

    /**
     * Create an instance of {@link Bathroom.Amenities }
     * 
     */
    public Bathroom.Amenities createBathroomAmenities() {
        return new Bathroom.Amenities();
    }

    /**
     * Create an instance of {@link net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserRatePeriods.Advertiser.AdvertiserRatePeriods }
     * 
     */
    public net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserRatePeriods.Advertiser.AdvRatePeriods createAdvertiserRatePeriodsAdvertiserAdvertiserRatePeriods() {
        return new net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserRatePeriods.Advertiser.AdvRatePeriods();
    }

    /**
     * Create an instance of {@link AdvertiserListings.Advertiser.Listings }
     * 
     */
    public AdvertiserListings.Advertiser.Listings createAdvertiserListingsAdvertiserListings() {
        return new AdvertiserListings.Advertiser.Listings();
    }

    /**
     * Create an instance of {@link net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserReservations.Advertiser.AdvertiserReservations }
     * 
     */
    public net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserReservations.Advertiser.AdvReservations createAdvertiserReservationsAdvertiserAdvertiserReservations() {
        return new net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserReservations.Advertiser.AdvReservations();
    }

    /**
     * Create an instance of {@link AdvertiserListingIndex.Advertiser }
     * 
     */
    public AdvertiserListingIndex.Advertiser createAdvertiserListingIndexAdvertiser() {
        return new AdvertiserListingIndex.Advertiser();
    }

    /**
     * Create an instance of {@link Listing.FeatureValues }
     * 
     */
    public Listing.FeatureValues createListingFeatureValues() {
        return new Listing.FeatureValues();
    }

    /**
     * Create an instance of {@link Listing.Units }
     * 
     */
    public Listing.Units createListingUnits() {
        return new Listing.Units();
    }

    /**
     * Create an instance of {@link RatePeriod.Rates }
     * 
     */
    public RatePeriod.Rates createRatePeriodRates() {
        return new RatePeriod.Rates();
    }

    /**
     * Create an instance of {@link Note.Texts }
     * 
     */
    public Note.Texts createNoteTexts() {
        return new Note.Texts();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReservationsBatch }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "reservationBatch")
    public JAXBElement<ReservationsBatch> createReservationBatch(ReservationsBatch value) {
        return new JAXBElement<ReservationsBatch>(_ReservationBatch_QNAME, ReservationsBatch.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListingReservations }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "unitReservations")
    public JAXBElement<ListingReservations> createUnitReservations(ListingReservations value) {
        return new JAXBElement<ListingReservations>(_UnitReservations_QNAME, ListingReservations.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RatePeriodsBatch }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ratePeriodsBatch")
    public JAXBElement<RatePeriodsBatch> createRatePeriodsBatch(RatePeriodsBatch value) {
        return new JAXBElement<RatePeriodsBatch>(_RatePeriodsBatch_QNAME, RatePeriodsBatch.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Entity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "entity")
    public JAXBElement<Entity> createEntity(Entity value) {
        return new JAXBElement<Entity>(_Entity_QNAME, Entity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Listing }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "listing")
    public JAXBElement<Listing> createListing(Listing value) {
        return new JAXBElement<Listing>(_Listing_QNAME, Listing.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListingBatch }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "listingBatch")
    public JAXBElement<ListingBatch> createListingBatch(ListingBatch value) {
        return new JAXBElement<ListingBatch>(_ListingBatch_QNAME, ListingBatch.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListingRatePeriods }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "unitRatePeriods")
    public JAXBElement<ListingRatePeriods> createUnitRatePeriods(ListingRatePeriods value) {
        return new JAXBElement<ListingRatePeriods>(_UnitRatePeriods_QNAME, ListingRatePeriods.class, null, value);
    }

    /**
     * Create an instance of {@link RateModifiers }
     * 
     */
    public RateModifiers createRateModifiers() {
        return new RateModifiers();
    }

    /**
     * Create an instance of {@link RateModifiers.Deposits }
     * 
     */
    public RateModifiers.Deposits createRateModifiersDeposits() {
        return new RateModifiers.Deposits();
    }

    /**
     * Create an instance of {@link RateModifiers.Fees }
     * 
     */
    public RateModifiers.Fees createRateModifiersFees() {
        return new RateModifiers.Fees();
    }

    /**
     * Create an instance of {@link RateModifiers.Fees.Fee }
     * 
     */
    public RateModifiers.Fees.Fee createRateModifiersFeesFee() {
        return new RateModifiers.Fees.Fee();
    }

    /**
     * Create an instance of {@link RateModifiers.Taxes }
     * 
     */
    public RateModifiers.Taxes createRateModifiersTaxes() {
        return new RateModifiers.Taxes();
    }

    /**
     * Create an instance of {@link RateModifiers.Discounts }
     * 
     */
    public RateModifiers.Discounts createRateModifiersDiscounts() {
        return new RateModifiers.Discounts();
    }

    /**
     * Create an instance of {@link RateModifiers.Deposits.Deposit }
     * 
     */
    public RateModifiers.Deposits.Deposit createRateModifiersDepositsDeposit() {
        return new RateModifiers.Deposits.Deposit();
    }

    /**
     * Create an instance of {@link RateModifiers.Fees.Fee.Taxes }
     * 
     */
    public RateModifiers.Fees.Fee.Taxes createRateModifiersFeesFeeTaxes() {
        return new RateModifiers.Fees.Fee.Taxes();
    }

    /**
     * Create an instance of {@link RateModifiers.Taxes.Tax }
     * 
     */
    public RateModifiers.Taxes.Tax createRateModifiersTaxesTax() {
        return new RateModifiers.Taxes.Tax();
    }
}
