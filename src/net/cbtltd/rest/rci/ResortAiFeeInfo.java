
package net.cbtltd.rest.rci;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for resort-ai-fee-info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resort-ai-fee-info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fee" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="feeTypes" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="feeType" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="stayLength" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unitType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unitName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="promoID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="promoMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="beginDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="weekNumber" type="{}multivalueType" minOccurs="0"/>
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="minOccupancy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxOccupancy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="privacy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxAdditionalPersons" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxTeenOccupancy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxInfantOccupancy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxChildOccupancy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resort-ai-fee-info", propOrder = {
    "fee",
    "currency",
    "feeTypes",
    "stayLength",
    "unitType",
    "unitName",
    "promoID",
    "promoMsg",
    "beginDate",
    "endDate",
    "weekNumber",
    "notes",
    "minOccupancy",
    "maxOccupancy",
    "privacy",
    "maxAdditionalPersons",
    "maxTeenOccupancy",
    "maxInfantOccupancy",
    "maxChildOccupancy"
})
public class ResortAiFeeInfo {

    protected String fee;
    protected String currency;
    protected ResortAiFeeInfo.FeeTypes feeTypes;
    protected String stayLength;
    protected String unitType;
    protected String unitName;
    protected String promoID;
    protected String promoMsg;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar beginDate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    protected MultivalueType weekNumber;
    protected String notes;
    protected String minOccupancy;
    protected String maxOccupancy;
    protected String privacy;
    protected String maxAdditionalPersons;
    protected String maxTeenOccupancy;
    protected String maxInfantOccupancy;
    protected String maxChildOccupancy;

    /**
     * Gets the value of the fee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFee() {
        return fee;
    }

    /**
     * Sets the value of the fee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFee(String value) {
        this.fee = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the feeTypes property.
     * 
     * @return
     *     possible object is
     *     {@link ResortAiFeeInfo.FeeTypes }
     *     
     */
    public ResortAiFeeInfo.FeeTypes getFeeTypes() {
        return feeTypes;
    }

    /**
     * Sets the value of the feeTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortAiFeeInfo.FeeTypes }
     *     
     */
    public void setFeeTypes(ResortAiFeeInfo.FeeTypes value) {
        this.feeTypes = value;
    }

    /**
     * Gets the value of the stayLength property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStayLength() {
        return stayLength;
    }

    /**
     * Sets the value of the stayLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStayLength(String value) {
        this.stayLength = value;
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
     * Gets the value of the unitName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * Sets the value of the unitName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitName(String value) {
        this.unitName = value;
    }

    /**
     * Gets the value of the promoID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromoID() {
        return promoID;
    }

    /**
     * Sets the value of the promoID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromoID(String value) {
        this.promoID = value;
    }

    /**
     * Gets the value of the promoMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromoMsg() {
        return promoMsg;
    }

    /**
     * Sets the value of the promoMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromoMsg(String value) {
        this.promoMsg = value;
    }

    /**
     * Gets the value of the beginDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBeginDate() {
        return beginDate;
    }

    /**
     * Sets the value of the beginDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBeginDate(XMLGregorianCalendar value) {
        this.beginDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the weekNumber property.
     * 
     * @return
     *     possible object is
     *     {@link MultivalueType }
     *     
     */
    public MultivalueType getWeekNumber() {
        return weekNumber;
    }

    /**
     * Sets the value of the weekNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link MultivalueType }
     *     
     */
    public void setWeekNumber(MultivalueType value) {
        this.weekNumber = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

    /**
     * Gets the value of the minOccupancy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinOccupancy() {
        return minOccupancy;
    }

    /**
     * Sets the value of the minOccupancy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinOccupancy(String value) {
        this.minOccupancy = value;
    }

    /**
     * Gets the value of the maxOccupancy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxOccupancy() {
        return maxOccupancy;
    }

    /**
     * Sets the value of the maxOccupancy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxOccupancy(String value) {
        this.maxOccupancy = value;
    }

    /**
     * Gets the value of the privacy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrivacy() {
        return privacy;
    }

    /**
     * Sets the value of the privacy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrivacy(String value) {
        this.privacy = value;
    }

    /**
     * Gets the value of the maxAdditionalPersons property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxAdditionalPersons() {
        return maxAdditionalPersons;
    }

    /**
     * Sets the value of the maxAdditionalPersons property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxAdditionalPersons(String value) {
        this.maxAdditionalPersons = value;
    }

    /**
     * Gets the value of the maxTeenOccupancy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxTeenOccupancy() {
        return maxTeenOccupancy;
    }

    /**
     * Sets the value of the maxTeenOccupancy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxTeenOccupancy(String value) {
        this.maxTeenOccupancy = value;
    }

    /**
     * Gets the value of the maxInfantOccupancy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxInfantOccupancy() {
        return maxInfantOccupancy;
    }

    /**
     * Sets the value of the maxInfantOccupancy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxInfantOccupancy(String value) {
        this.maxInfantOccupancy = value;
    }

    /**
     * Gets the value of the maxChildOccupancy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxChildOccupancy() {
        return maxChildOccupancy;
    }

    /**
     * Sets the value of the maxChildOccupancy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxChildOccupancy(String value) {
        this.maxChildOccupancy = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="feeType" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "feeType"
    })
    public static class FeeTypes {

        protected List<String> feeType;

        /**
         * Gets the value of the feeType property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the feeType property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFeeType().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getFeeType() {
            if (feeType == null) {
                feeType = new ArrayList<String>();
            }
            return this.feeType;
        }

    }

}
