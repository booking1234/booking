
package net.cbtltd.rest.bookt;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Property complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Property">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Address1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Address2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AdjLivingSpace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AltID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Amenities" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfstring" minOccurs="0"/>
 *         &lt;element name="AmenityTypes" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfstring" minOccurs="0"/>
 *         &lt;element name="AvailableOnline" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="AvgReview" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Bathrooms" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Bedrooms" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="CheckInInstructions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="City" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="County" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Development" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fees" type="{http://connect.bookt.com/Schemas/Fee.xsd}ArrayOfFee" minOccurs="0"/>
 *         &lt;element name="Floor" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="GarageSpaces" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Headline" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Images" type="{http://connect.bookt.com/Schemas/Media.xsd}ArrayOfMedia" minOccurs="0"/>
 *         &lt;element name="IsBookable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Latitude" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Longitude" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="LotSize" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ManagedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MasterID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaxRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="MaxRateCurrency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Metro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MinRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="MinRateCurrency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Neighborhood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumReviews" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="PostalCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrimaryImage" type="{http://connect.bookt.com/Schemas/Media.xsd}Media" minOccurs="0"/>
 *         &lt;element name="Region" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sleeps" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="State" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Stories" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Summary" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tags" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfstring" minOccurs="0"/>
 *         &lt;element name="Taxes" type="{http://connect.bookt.com/Schemas/Tax.xsd}ArrayOfTax" minOccurs="0"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Units" type="{http://connect.bookt.com/Schemas/Unit.xsd}ArrayOfUnit" minOccurs="0"/>
 *         &lt;element name="YearBuilt" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Property", namespace = "http://connect.bookt.com/Schemas/Property.xsd", propOrder = {
    "address1",
    "address2",
    "adjLivingSpace",
    "altID",
    "amenities",
    "amenityTypes",
    "availableOnline",
    "avgReview",
    "bathrooms",
    "bedrooms",
    "checkInInstructions",
    "city",
    "country",
    "county",
    "description",
    "development",
    "fees",
    "floor",
    "garageSpaces",
    "headline",
    "id",
    "images",
    "isBookable",
    "latitude",
    "longitude",
    "lotSize",
    "managedBy",
    "masterID",
    "maxRate",
    "maxRateCurrency",
    "metro",
    "minRate",
    "minRateCurrency",
    "neighborhood",
    "numReviews",
    "postalCode",
    "primaryImage",
    "region",
    "sleeps",
    "state",
    "status",
    "stories",
    "summary",
    "tags",
    "taxes",
    "type",
    "units",
    "yearBuilt"
})
public class Property {

    @XmlElementRef(name = "Address1", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> address1;
    @XmlElementRef(name = "Address2", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> address2;
    @XmlElementRef(name = "AdjLivingSpace", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> adjLivingSpace;
    @XmlElementRef(name = "AltID", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> altID;
    @XmlElementRef(name = "Amenities", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<ArrayOfstring> amenities;
    @XmlElementRef(name = "AmenityTypes", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<ArrayOfstring> amenityTypes;
    @XmlElement(name = "AvailableOnline")
    protected Boolean availableOnline;
    @XmlElement(name = "AvgReview")
    protected BigDecimal avgReview;
    @XmlElement(name = "Bathrooms")
    protected BigDecimal bathrooms;
    @XmlElement(name = "Bedrooms")
    protected Integer bedrooms;
    @XmlElementRef(name = "CheckInInstructions", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> checkInInstructions;
    @XmlElementRef(name = "City", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> city;
    @XmlElementRef(name = "Country", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> country;
    @XmlElementRef(name = "County", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> county;
    @XmlElementRef(name = "Description", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> description;
    @XmlElementRef(name = "Development", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> development;
    @XmlElementRef(name = "Fees", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<ArrayOfFee> fees;
    @XmlElement(name = "Floor")
    protected Integer floor;
    @XmlElement(name = "GarageSpaces")
    protected Integer garageSpaces;
    @XmlElementRef(name = "Headline", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> headline;
    @XmlElement(name = "ID")
    protected Integer id;
    @XmlElementRef(name = "Images", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<ArrayOfMedia> images;
    @XmlElement(name = "IsBookable")
    protected Boolean isBookable;
    @XmlElement(name = "Latitude")
    protected BigDecimal latitude;
    @XmlElement(name = "Longitude")
    protected BigDecimal longitude;
    @XmlElement(name = "LotSize")
    protected Integer lotSize;
    @XmlElementRef(name = "ManagedBy", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> managedBy;
    @XmlElementRef(name = "MasterID", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> masterID;
    @XmlElement(name = "MaxRate")
    protected BigDecimal maxRate;
    @XmlElementRef(name = "MaxRateCurrency", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> maxRateCurrency;
    @XmlElementRef(name = "Metro", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> metro;
    @XmlElement(name = "MinRate")
    protected BigDecimal minRate;
    @XmlElementRef(name = "MinRateCurrency", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> minRateCurrency;
    @XmlElementRef(name = "Neighborhood", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> neighborhood;
    @XmlElement(name = "NumReviews")
    protected Integer numReviews;
    @XmlElementRef(name = "PostalCode", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> postalCode;
    @XmlElementRef(name = "PrimaryImage", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<Media> primaryImage;
    @XmlElementRef(name = "Region", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> region;
    @XmlElement(name = "Sleeps")
    protected Integer sleeps;
    @XmlElementRef(name = "State", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> state;
    @XmlElementRef(name = "Status", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> status;
    @XmlElement(name = "Stories")
    protected Integer stories;
    @XmlElementRef(name = "Summary", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> summary;
    @XmlElementRef(name = "Tags", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<ArrayOfstring> tags;
    @XmlElementRef(name = "Taxes", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<ArrayOfTax> taxes;
    @XmlElementRef(name = "Type", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<String> type;
    @XmlElementRef(name = "Units", namespace = "http://connect.bookt.com/Schemas/Property.xsd", type = JAXBElement.class)
    protected JAXBElement<ArrayOfUnit> units;
    @XmlElement(name = "YearBuilt")
    protected Integer yearBuilt;

    /**
     * Gets the value of the address1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAddress1() {
        return address1;
    }

    /**
     * Sets the value of the address1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAddress1(JAXBElement<String> value) {
        this.address1 = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the address2 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAddress2() {
        return address2;
    }

    /**
     * Sets the value of the address2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAddress2(JAXBElement<String> value) {
        this.address2 = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the adjLivingSpace property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAdjLivingSpace() {
        return adjLivingSpace;
    }

    /**
     * Sets the value of the adjLivingSpace property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAdjLivingSpace(JAXBElement<String> value) {
        this.adjLivingSpace = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the altID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAltID() {
        return altID;
    }

    /**
     * Sets the value of the altID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAltID(JAXBElement<String> value) {
        this.altID = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the amenities property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public JAXBElement<ArrayOfstring> getAmenities() {
        return amenities;
    }

    /**
     * Sets the value of the amenities property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public void setAmenities(JAXBElement<ArrayOfstring> value) {
        this.amenities = ((JAXBElement<ArrayOfstring> ) value);
    }

    /**
     * Gets the value of the amenityTypes property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public JAXBElement<ArrayOfstring> getAmenityTypes() {
        return amenityTypes;
    }

    /**
     * Sets the value of the amenityTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public void setAmenityTypes(JAXBElement<ArrayOfstring> value) {
        this.amenityTypes = ((JAXBElement<ArrayOfstring> ) value);
    }

    /**
     * Gets the value of the availableOnline property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAvailableOnline() {
        return availableOnline;
    }

    /**
     * Sets the value of the availableOnline property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAvailableOnline(Boolean value) {
        this.availableOnline = value;
    }

    /**
     * Gets the value of the avgReview property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAvgReview() {
        return avgReview;
    }

    /**
     * Sets the value of the avgReview property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAvgReview(BigDecimal value) {
        this.avgReview = value;
    }

    /**
     * Gets the value of the bathrooms property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBathrooms() {
        return bathrooms;
    }

    /**
     * Sets the value of the bathrooms property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBathrooms(BigDecimal value) {
        this.bathrooms = value;
    }

    /**
     * Gets the value of the bedrooms property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBedrooms() {
        return bedrooms;
    }

    /**
     * Sets the value of the bedrooms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBedrooms(Integer value) {
        this.bedrooms = value;
    }

    /**
     * Gets the value of the checkInInstructions property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCheckInInstructions() {
        return checkInInstructions;
    }

    /**
     * Sets the value of the checkInInstructions property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCheckInInstructions(JAXBElement<String> value) {
        this.checkInInstructions = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCity(JAXBElement<String> value) {
        this.city = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCountry(JAXBElement<String> value) {
        this.country = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the county property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCounty() {
        return county;
    }

    /**
     * Sets the value of the county property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCounty(JAXBElement<String> value) {
        this.county = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDescription(JAXBElement<String> value) {
        this.description = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the development property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDevelopment() {
        return development;
    }

    /**
     * Sets the value of the development property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDevelopment(JAXBElement<String> value) {
        this.development = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the fees property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfFee }{@code >}
     *     
     */
    public JAXBElement<ArrayOfFee> getFees() {
        return fees;
    }

    /**
     * Sets the value of the fees property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfFee }{@code >}
     *     
     */
    public void setFees(JAXBElement<ArrayOfFee> value) {
        this.fees = ((JAXBElement<ArrayOfFee> ) value);
    }

    /**
     * Gets the value of the floor property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFloor() {
        return floor;
    }

    /**
     * Sets the value of the floor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFloor(Integer value) {
        this.floor = value;
    }

    /**
     * Gets the value of the garageSpaces property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGarageSpaces() {
        return garageSpaces;
    }

    /**
     * Sets the value of the garageSpaces property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGarageSpaces(Integer value) {
        this.garageSpaces = value;
    }

    /**
     * Gets the value of the headline property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getHeadline() {
        return headline;
    }

    /**
     * Sets the value of the headline property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setHeadline(JAXBElement<String> value) {
        this.headline = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setID(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the images property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfMedia }{@code >}
     *     
     */
    public JAXBElement<ArrayOfMedia> getImages() {
        return images;
    }

    /**
     * Sets the value of the images property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfMedia }{@code >}
     *     
     */
    public void setImages(JAXBElement<ArrayOfMedia> value) {
        this.images = ((JAXBElement<ArrayOfMedia> ) value);
    }

    /**
     * Gets the value of the isBookable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsBookable() {
        return isBookable;
    }

    /**
     * Sets the value of the isBookable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsBookable(Boolean value) {
        this.isBookable = value;
    }

    /**
     * Gets the value of the latitude property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of the latitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLatitude(BigDecimal value) {
        this.latitude = value;
    }

    /**
     * Gets the value of the longitude property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * Sets the value of the longitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLongitude(BigDecimal value) {
        this.longitude = value;
    }

    /**
     * Gets the value of the lotSize property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLotSize() {
        return lotSize;
    }

    /**
     * Sets the value of the lotSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLotSize(Integer value) {
        this.lotSize = value;
    }

    /**
     * Gets the value of the managedBy property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getManagedBy() {
        return managedBy;
    }

    /**
     * Sets the value of the managedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setManagedBy(JAXBElement<String> value) {
        this.managedBy = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the masterID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMasterID() {
        return masterID;
    }

    /**
     * Sets the value of the masterID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMasterID(JAXBElement<String> value) {
        this.masterID = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the maxRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaxRate() {
        return maxRate;
    }

    /**
     * Sets the value of the maxRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaxRate(BigDecimal value) {
        this.maxRate = value;
    }

    /**
     * Gets the value of the maxRateCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMaxRateCurrency() {
        return maxRateCurrency;
    }

    /**
     * Sets the value of the maxRateCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMaxRateCurrency(JAXBElement<String> value) {
        this.maxRateCurrency = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the metro property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMetro() {
        return metro;
    }

    /**
     * Sets the value of the metro property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMetro(JAXBElement<String> value) {
        this.metro = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the minRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMinRate() {
        return minRate;
    }

    /**
     * Sets the value of the minRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMinRate(BigDecimal value) {
        this.minRate = value;
    }

    /**
     * Gets the value of the minRateCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMinRateCurrency() {
        return minRateCurrency;
    }

    /**
     * Sets the value of the minRateCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMinRateCurrency(JAXBElement<String> value) {
        this.minRateCurrency = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the neighborhood property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNeighborhood() {
        return neighborhood;
    }

    /**
     * Sets the value of the neighborhood property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNeighborhood(JAXBElement<String> value) {
        this.neighborhood = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the numReviews property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumReviews() {
        return numReviews;
    }

    /**
     * Sets the value of the numReviews property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumReviews(Integer value) {
        this.numReviews = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPostalCode(JAXBElement<String> value) {
        this.postalCode = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the primaryImage property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Media }{@code >}
     *     
     */
    public JAXBElement<Media> getPrimaryImage() {
        return primaryImage;
    }

    /**
     * Sets the value of the primaryImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Media }{@code >}
     *     
     */
    public void setPrimaryImage(JAXBElement<Media> value) {
        this.primaryImage = ((JAXBElement<Media> ) value);
    }

    /**
     * Gets the value of the region property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRegion() {
        return region;
    }

    /**
     * Sets the value of the region property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRegion(JAXBElement<String> value) {
        this.region = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the sleeps property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSleeps() {
        return sleeps;
    }

    /**
     * Sets the value of the sleeps property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSleeps(Integer value) {
        this.sleeps = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setState(JAXBElement<String> value) {
        this.state = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStatus(JAXBElement<String> value) {
        this.status = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the stories property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStories() {
        return stories;
    }

    /**
     * Sets the value of the stories property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStories(Integer value) {
        this.stories = value;
    }

    /**
     * Gets the value of the summary property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSummary() {
        return summary;
    }

    /**
     * Sets the value of the summary property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSummary(JAXBElement<String> value) {
        this.summary = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the tags property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public JAXBElement<ArrayOfstring> getTags() {
        return tags;
    }

    /**
     * Sets the value of the tags property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public void setTags(JAXBElement<ArrayOfstring> value) {
        this.tags = ((JAXBElement<ArrayOfstring> ) value);
    }

    /**
     * Gets the value of the taxes property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfTax }{@code >}
     *     
     */
    public JAXBElement<ArrayOfTax> getTaxes() {
        return taxes;
    }

    /**
     * Sets the value of the taxes property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfTax }{@code >}
     *     
     */
    public void setTaxes(JAXBElement<ArrayOfTax> value) {
        this.taxes = ((JAXBElement<ArrayOfTax> ) value);
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setType(JAXBElement<String> value) {
        this.type = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the units property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfUnit }{@code >}
     *     
     */
    public JAXBElement<ArrayOfUnit> getUnits() {
        return units;
    }

    /**
     * Sets the value of the units property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfUnit }{@code >}
     *     
     */
    public void setUnits(JAXBElement<ArrayOfUnit> value) {
        this.units = ((JAXBElement<ArrayOfUnit> ) value);
    }

    /**
     * Gets the value of the yearBuilt property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getYearBuilt() {
        return yearBuilt;
    }

    /**
     * Sets the value of the yearBuilt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setYearBuilt(Integer value) {
        this.yearBuilt = value;
    }

}
