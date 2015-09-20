package net.cbtltd.rest.leisurelink.ExternalServices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.cbtltd.rest.leisurelink package. 
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

    private final static QName _RateAvailabilityUpdateRQ_QNAME = new QName("http://tempuri.org/", "RateAvailabilityUpdateRQ");
    private final static QName _ArrayOfDistributionProperty_QNAME = new QName("http://tempuri.org/", "ArrayOfDistributionProperty");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.cbtltd.rest.leisurelink
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetDistributionPropertesResponse }
     * 
     */
    public GetDistributionPropertesResponse createGetDistributionPropertesResponse() {
        return new GetDistributionPropertesResponse();
    }

    /**
     * Create an instance of {@link ArrayOfDistributionProperty }
     * 
     */
    public ArrayOfDistributionProperty createArrayOfDistributionProperty() {
        return new ArrayOfDistributionProperty();
    }

    /**
     * Create an instance of {@link GetRatesAndAvailabiltyUpdated }
     * 
     */
    public GetRatesAndAvailabiltyUpdated createGetRatesAndAvailabiltyUpdated() {
        return new GetRatesAndAvailabiltyUpdated();
    }

    /**
     * Create an instance of {@link RateAvailabilityUpdateRQ }
     * 
     */
    public RateAvailabilityUpdateRQ createRateAvailabilityUpdateRQ() {
        return new RateAvailabilityUpdateRQ();
    }

    /**
     * Create an instance of {@link GetRatesAndAvailabiltyUpdatedResponse }
     * 
     */
    public GetRatesAndAvailabiltyUpdatedResponse createGetRatesAndAvailabiltyUpdatedResponse() {
        return new GetRatesAndAvailabiltyUpdatedResponse();
    }

    /**
     * Create an instance of {@link GetRatesAndAvailabilty }
     * 
     */
    public GetRatesAndAvailabilty createGetRatesAndAvailabilty() {
        return new GetRatesAndAvailabilty();
    }

    /**
     * Create an instance of {@link GetDistributionPropertes }
     * 
     */
    public GetDistributionPropertes createGetDistributionPropertes() {
        return new GetDistributionPropertes();
    }

    /**
     * Create an instance of {@link GetRatesAndAvailabiltyResponse }
     * 
     */
    public GetRatesAndAvailabiltyResponse createGetRatesAndAvailabiltyResponse() {
        return new GetRatesAndAvailabiltyResponse();
    }

    /**
     * Create an instance of {@link ArrayOfRoomType }
     * 
     */
    public ArrayOfRoomType createArrayOfRoomType() {
        return new ArrayOfRoomType();
    }

    /**
     * Create an instance of {@link RateAvailability }
     * 
     */
    public RateAvailability createRateAvailability() {
        return new RateAvailability();
    }

    /**
     * Create an instance of {@link RoomType }
     * 
     */
    public RoomType createRoomType() {
        return new RoomType();
    }

    /**
     * Create an instance of {@link ArrayOfRateAvailability }
     * 
     */
    public ArrayOfRateAvailability createArrayOfRateAvailability() {
        return new ArrayOfRateAvailability();
    }

    /**
     * Create an instance of {@link ArrayOfRate }
     * 
     */
    public ArrayOfRate createArrayOfRate() {
        return new ArrayOfRate();
    }

    /**
     * Create an instance of {@link DistributionProperty }
     * 
     */
    public DistributionProperty createDistributionProperty() {
        return new DistributionProperty();
    }

    /**
     * Create an instance of {@link Rate }
     * 
     */
    public Rate createRate() {
        return new Rate();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RateAvailabilityUpdateRQ }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "RateAvailabilityUpdateRQ")
    public JAXBElement<RateAvailabilityUpdateRQ> createRateAvailabilityUpdateRQ(RateAvailabilityUpdateRQ value) {
        return new JAXBElement<RateAvailabilityUpdateRQ>(_RateAvailabilityUpdateRQ_QNAME, RateAvailabilityUpdateRQ.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDistributionProperty }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ArrayOfDistributionProperty")
    public JAXBElement<ArrayOfDistributionProperty> createArrayOfDistributionProperty(ArrayOfDistributionProperty value) {
        return new JAXBElement<ArrayOfDistributionProperty>(_ArrayOfDistributionProperty_QNAME, ArrayOfDistributionProperty.class, null, value);
    }

}
