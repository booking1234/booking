
package net.cbtltd.rest.rci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resort-fee-info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resort-fee-info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resort-fee-info-deposit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resort-fee-info-utility" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resort-fee-info-clubFacilityCharge" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resort-fee-info-touristTax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resort-fee-info-departureTax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resort-fee-info", propOrder = {
    "resortFeeInfoDeposit",
    "resortFeeInfoUtility",
    "resortFeeInfoClubFacilityCharge",
    "resortFeeInfoTouristTax",
    "resortFeeInfoDepartureTax"
})
public class ResortFeeInfo {

    @XmlElement(name = "resort-fee-info-deposit")
    protected String resortFeeInfoDeposit;
    @XmlElement(name = "resort-fee-info-utility")
    protected String resortFeeInfoUtility;
    @XmlElement(name = "resort-fee-info-clubFacilityCharge")
    protected String resortFeeInfoClubFacilityCharge;
    @XmlElement(name = "resort-fee-info-touristTax")
    protected String resortFeeInfoTouristTax;
    @XmlElement(name = "resort-fee-info-departureTax")
    protected String resortFeeInfoDepartureTax;

    /**
     * Gets the value of the resortFeeInfoDeposit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResortFeeInfoDeposit() {
        return resortFeeInfoDeposit;
    }

    /**
     * Sets the value of the resortFeeInfoDeposit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResortFeeInfoDeposit(String value) {
        this.resortFeeInfoDeposit = value;
    }

    /**
     * Gets the value of the resortFeeInfoUtility property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResortFeeInfoUtility() {
        return resortFeeInfoUtility;
    }

    /**
     * Sets the value of the resortFeeInfoUtility property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResortFeeInfoUtility(String value) {
        this.resortFeeInfoUtility = value;
    }

    /**
     * Gets the value of the resortFeeInfoClubFacilityCharge property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResortFeeInfoClubFacilityCharge() {
        return resortFeeInfoClubFacilityCharge;
    }

    /**
     * Sets the value of the resortFeeInfoClubFacilityCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResortFeeInfoClubFacilityCharge(String value) {
        this.resortFeeInfoClubFacilityCharge = value;
    }

    /**
     * Gets the value of the resortFeeInfoTouristTax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResortFeeInfoTouristTax() {
        return resortFeeInfoTouristTax;
    }

    /**
     * Sets the value of the resortFeeInfoTouristTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResortFeeInfoTouristTax(String value) {
        this.resortFeeInfoTouristTax = value;
    }

    /**
     * Gets the value of the resortFeeInfoDepartureTax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResortFeeInfoDepartureTax() {
        return resortFeeInfoDepartureTax;
    }

    /**
     * Sets the value of the resortFeeInfoDepartureTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResortFeeInfoDepartureTax(String value) {
        this.resortFeeInfoDepartureTax = value;
    }

}
