//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.12.05 at 09:44:01 AM IST 
//


package net.cbtltd.rest.nextpax;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "packageID",
    "description",
    "numberOfAdults",
    "numberOfChildren",
    "numberOfBabies",
    "departureDate",
    "departurePoint",
    "accommodationType",
    "boardType",
    "unitType",
    "facilityType",
    "duration",
    "alternative",
    "multiMedia"
})
@XmlRootElement(name = "PackageDetails")
public class PackageDetails {

    @XmlAttribute(name = "WaitListCheck")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String waitListCheck;
    @XmlAttribute(name = "NumberOfAlternatives")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String numberOfAlternatives;
    @XmlElement(name = "PackageID", required = true)
    protected String packageID;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "NumberOfAdults", required = true)
    protected NumberOfAdults numberOfAdults;
    @XmlElement(name = "NumberOfChildren", required = true)
    protected NumberOfChildren numberOfChildren;
    @XmlElement(name = "NumberOfBabies", required = true)
    protected NumberOfBabies numberOfBabies;
    @XmlElement(name = "DepartureDate", required = true)
    protected DepartureDate departureDate;
    @XmlElement(name = "DeparturePoint")
    protected String departurePoint;
    @XmlElement(name = "AccommodationType")
    protected String accommodationType;
    @XmlElement(name = "BoardType")
    protected String boardType;
    @XmlElement(name = "UnitType")
    protected String unitType;
    @XmlElement(name = "FacilityType")
    protected List<FacilityType> facilityType;
    @XmlElement(name = "Duration", required = true)
    protected Duration duration;
    @XmlElement(name = "Alternative")
    protected List<Alternative> alternative;
    @XmlElement(name = "MultiMedia")
    protected List<MultiMedia> multiMedia;

    /**
     * Gets the value of the waitListCheck property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWaitListCheck() {
        if (waitListCheck == null) {
            return "ja";
        } else {
            return waitListCheck;
        }
    }

    /**
     * Sets the value of the waitListCheck property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWaitListCheck(String value) {
        this.waitListCheck = value;
    }

    /**
     * Gets the value of the numberOfAlternatives property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberOfAlternatives() {
        return numberOfAlternatives;
    }

    /**
     * Sets the value of the numberOfAlternatives property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberOfAlternatives(String value) {
        this.numberOfAlternatives = value;
    }

    /**
     * Gets the value of the packageID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackageID() {
        return packageID;
    }

    /**
     * Sets the value of the packageID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackageID(String value) {
        this.packageID = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the numberOfAdults property.
     * 
     * @return
     *     possible object is
     *     {@link NumberOfAdults }
     *     
     */
    public NumberOfAdults getNumberOfAdults() {
        return numberOfAdults;
    }

    /**
     * Sets the value of the numberOfAdults property.
     * 
     * @param value
     *     allowed object is
     *     {@link NumberOfAdults }
     *     
     */
    public void setNumberOfAdults(NumberOfAdults value) {
        this.numberOfAdults = value;
    }

    /**
     * Gets the value of the numberOfChildren property.
     * 
     * @return
     *     possible object is
     *     {@link NumberOfChildren }
     *     
     */
    public NumberOfChildren getNumberOfChildren() {
        return numberOfChildren;
    }

    /**
     * Sets the value of the numberOfChildren property.
     * 
     * @param value
     *     allowed object is
     *     {@link NumberOfChildren }
     *     
     */
    public void setNumberOfChildren(NumberOfChildren value) {
        this.numberOfChildren = value;
    }

    /**
     * Gets the value of the numberOfBabies property.
     * 
     * @return
     *     possible object is
     *     {@link NumberOfBabies }
     *     
     */
    public NumberOfBabies getNumberOfBabies() {
        return numberOfBabies;
    }

    /**
     * Sets the value of the numberOfBabies property.
     * 
     * @param value
     *     allowed object is
     *     {@link NumberOfBabies }
     *     
     */
    public void setNumberOfBabies(NumberOfBabies value) {
        this.numberOfBabies = value;
    }

    /**
     * Gets the value of the departureDate property.
     * 
     * @return
     *     possible object is
     *     {@link DepartureDate }
     *     
     */
    public DepartureDate getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the value of the departureDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepartureDate }
     *     
     */
    public void setDepartureDate(DepartureDate value) {
        this.departureDate = value;
    }

    /**
     * Gets the value of the departurePoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeparturePoint() {
        return departurePoint;
    }

    /**
     * Sets the value of the departurePoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeparturePoint(String value) {
        this.departurePoint = value;
    }

    /**
     * Gets the value of the accommodationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccommodationType() {
        return accommodationType;
    }

    /**
     * Sets the value of the accommodationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccommodationType(String value) {
        this.accommodationType = value;
    }

    /**
     * Gets the value of the boardType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBoardType() {
        return boardType;
    }

    /**
     * Sets the value of the boardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBoardType(String value) {
        this.boardType = value;
    }

    /**
     * Gets the value of the unitType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitType() {
        return unitType;
    }

    /**
     * Sets the value of the unitType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitType(String value) {
        this.unitType = value;
    }

    /**
     * Gets the value of the facilityType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the facilityType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFacilityType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FacilityType }
     * 
     * 
     */
    public List<FacilityType> getFacilityType() {
        if (facilityType == null) {
            facilityType = new ArrayList<FacilityType>();
        }
        return this.facilityType;
    }

    /**
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setDuration(Duration value) {
        this.duration = value;
    }

    /**
     * Gets the value of the alternative property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alternative property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlternative().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Alternative }
     * 
     * 
     */
    public List<Alternative> getAlternative() {
        if (alternative == null) {
            alternative = new ArrayList<Alternative>();
        }
        return this.alternative;
    }

    /**
     * Gets the value of the multiMedia property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the multiMedia property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMultiMedia().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MultiMedia }
     * 
     * 
     */
    public List<MultiMedia> getMultiMedia() {
        if (multiMedia == null) {
            multiMedia = new ArrayList<MultiMedia>();
        }
        return this.multiMedia;
    }

}
