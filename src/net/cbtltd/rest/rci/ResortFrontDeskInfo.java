
package net.cbtltd.rest.rci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resort-front-desk-info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resort-front-desk-info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resort-front-desk-info-isOnsite" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hours-of-operation-startTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hours-of-operation-endTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="resort-front-desk-info-is24hours" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resort-front-desk-info", propOrder = {
    "resortFrontDeskInfoIsOnsite",
    "hoursOfOperationStartTime",
    "hoursOfOperationEndTime",
    "resortFrontDeskInfoIs24Hours"
})
public class ResortFrontDeskInfo {

    @XmlElement(name = "resort-front-desk-info-isOnsite", required = true)
    protected String resortFrontDeskInfoIsOnsite;
    @XmlElement(name = "hours-of-operation-startTime", required = true)
    protected String hoursOfOperationStartTime;
    @XmlElement(name = "hours-of-operation-endTime", required = true)
    protected String hoursOfOperationEndTime;
    @XmlElement(name = "resort-front-desk-info-is24hours", required = true)
    protected String resortFrontDeskInfoIs24Hours;

    /**
     * Gets the value of the resortFrontDeskInfoIsOnsite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResortFrontDeskInfoIsOnsite() {
        return resortFrontDeskInfoIsOnsite;
    }

    /**
     * Sets the value of the resortFrontDeskInfoIsOnsite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResortFrontDeskInfoIsOnsite(String value) {
        this.resortFrontDeskInfoIsOnsite = value;
    }

    /**
     * Gets the value of the hoursOfOperationStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoursOfOperationStartTime() {
        return hoursOfOperationStartTime;
    }

    /**
     * Sets the value of the hoursOfOperationStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoursOfOperationStartTime(String value) {
        this.hoursOfOperationStartTime = value;
    }

    /**
     * Gets the value of the hoursOfOperationEndTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoursOfOperationEndTime() {
        return hoursOfOperationEndTime;
    }

    /**
     * Sets the value of the hoursOfOperationEndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoursOfOperationEndTime(String value) {
        this.hoursOfOperationEndTime = value;
    }

    /**
     * Gets the value of the resortFrontDeskInfoIs24Hours property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResortFrontDeskInfoIs24Hours() {
        return resortFrontDeskInfoIs24Hours;
    }

    /**
     * Sets the value of the resortFrontDeskInfoIs24Hours property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResortFrontDeskInfoIs24Hours(String value) {
        this.resortFrontDeskInfoIs24Hours = value;
    }

}
