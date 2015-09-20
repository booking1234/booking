
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReservationCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReservationCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReservationCategoryCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Accommodation" type="{http://service.newyse.ws.services.newyse.maxxton/}Accommodation"/>
 *         &lt;element name="Preferences" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PreferenceItem" type="{http://service.newyse.ws.services.newyse.maxxton/}Preference" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Language" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SubjectQuantities" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SubjectQuantityItem" type="{http://service.newyse.ws.services.newyse.maxxton/}SubjectQuantity" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="CustomerId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="Remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Voucher" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SourceCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SendMethodCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Additions" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AdditionItem" type="{http://service.newyse.ws.services.newyse.maxxton/}Addition" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ReturnBill" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReservationCriteria", propOrder = {
    "reservationCategoryCode",
    "accommodation",
    "preferences",
    "language",
    "subjectQuantities",
    "customerId",
    "remark",
    "voucher",
    "sourceCode",
    "sendMethodCode",
    "additions",
    "returnBill"
})
public class ReservationCriteria {

    @XmlElement(name = "ReservationCategoryCode", required = true)
    protected String reservationCategoryCode;
    @XmlElement(name = "Accommodation", required = true)
    protected Accommodation accommodation;
    @XmlElement(name = "Preferences")
    protected ReservationCriteria.Preferences preferences;
    @XmlElement(name = "Language")
    protected String language;
    @XmlElement(name = "SubjectQuantities")
    protected ReservationCriteria.SubjectQuantities subjectQuantities;
    @XmlElement(name = "CustomerId")
    protected Long customerId;
    @XmlElement(name = "Remark")
    protected String remark;
    @XmlElement(name = "Voucher")
    protected String voucher;
    @XmlElementRef(name = "SourceCode", type = JAXBElement.class)
    protected JAXBElement<String> sourceCode;
    @XmlElementRef(name = "SendMethodCode", type = JAXBElement.class)
    protected JAXBElement<String> sendMethodCode;
    @XmlElement(name = "Additions")
    protected ReservationCriteria.Additions additions;
    @XmlElement(name = "ReturnBill", defaultValue = "false")
    protected Boolean returnBill;

    /**
     * Gets the value of the reservationCategoryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReservationCategoryCode() {
        return reservationCategoryCode;
    }

    /**
     * Sets the value of the reservationCategoryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReservationCategoryCode(String value) {
        this.reservationCategoryCode = value;
    }

    /**
     * Gets the value of the accommodation property.
     * 
     * @return
     *     possible object is
     *     {@link Accommodation }
     *     
     */
    public Accommodation getAccommodation() {
        return accommodation;
    }

    /**
     * Sets the value of the accommodation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Accommodation }
     *     
     */
    public void setAccommodation(Accommodation value) {
        this.accommodation = value;
    }

    /**
     * Gets the value of the preferences property.
     * 
     * @return
     *     possible object is
     *     {@link ReservationCriteria.Preferences }
     *     
     */
    public ReservationCriteria.Preferences getPreferences() {
        return preferences;
    }

    /**
     * Sets the value of the preferences property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReservationCriteria.Preferences }
     *     
     */
    public void setPreferences(ReservationCriteria.Preferences value) {
        this.preferences = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * Gets the value of the subjectQuantities property.
     * 
     * @return
     *     possible object is
     *     {@link ReservationCriteria.SubjectQuantities }
     *     
     */
    public ReservationCriteria.SubjectQuantities getSubjectQuantities() {
        return subjectQuantities;
    }

    /**
     * Sets the value of the subjectQuantities property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReservationCriteria.SubjectQuantities }
     *     
     */
    public void setSubjectQuantities(ReservationCriteria.SubjectQuantities value) {
        this.subjectQuantities = value;
    }

    /**
     * Gets the value of the customerId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * Sets the value of the customerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCustomerId(Long value) {
        this.customerId = value;
    }

    /**
     * Gets the value of the remark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Sets the value of the remark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    /**
     * Gets the value of the voucher property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoucher() {
        return voucher;
    }

    /**
     * Sets the value of the voucher property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoucher(String value) {
        this.voucher = value;
    }

    /**
     * Gets the value of the sourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSourceCode() {
        return sourceCode;
    }

    /**
     * Sets the value of the sourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSourceCode(JAXBElement<String> value) {
        this.sourceCode = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the sendMethodCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSendMethodCode() {
        return sendMethodCode;
    }

    /**
     * Sets the value of the sendMethodCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSendMethodCode(JAXBElement<String> value) {
        this.sendMethodCode = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the additions property.
     * 
     * @return
     *     possible object is
     *     {@link ReservationCriteria.Additions }
     *     
     */
    public ReservationCriteria.Additions getAdditions() {
        return additions;
    }

    /**
     * Sets the value of the additions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReservationCriteria.Additions }
     *     
     */
    public void setAdditions(ReservationCriteria.Additions value) {
        this.additions = value;
    }

    /**
     * Gets the value of the returnBill property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReturnBill() {
        return returnBill;
    }

    /**
     * Sets the value of the returnBill property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReturnBill(Boolean value) {
        this.returnBill = value;
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
     *         &lt;element name="AdditionItem" type="{http://service.newyse.ws.services.newyse.maxxton/}Addition" maxOccurs="unbounded" minOccurs="0"/>
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
        "additionItem"
    })
    public static class Additions {

        @XmlElement(name = "AdditionItem")
        protected List<Addition> additionItem;

        /**
         * Gets the value of the additionItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the additionItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAdditionItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Addition }
         * 
         * 
         */
        public List<Addition> getAdditionItem() {
            if (additionItem == null) {
                additionItem = new ArrayList<Addition>();
            }
            return this.additionItem;
        }

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
     *         &lt;element name="PreferenceItem" type="{http://service.newyse.ws.services.newyse.maxxton/}Preference" maxOccurs="unbounded" minOccurs="0"/>
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
        "preferenceItem"
    })
    public static class Preferences {

        @XmlElement(name = "PreferenceItem")
        protected List<Preference> preferenceItem;

        /**
         * Gets the value of the preferenceItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the preferenceItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPreferenceItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Preference }
         * 
         * 
         */
        public List<Preference> getPreferenceItem() {
            if (preferenceItem == null) {
                preferenceItem = new ArrayList<Preference>();
            }
            return this.preferenceItem;
        }

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
     *         &lt;element name="SubjectQuantityItem" type="{http://service.newyse.ws.services.newyse.maxxton/}SubjectQuantity" maxOccurs="unbounded"/>
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
        "subjectQuantityItem"
    })
    public static class SubjectQuantities {

        @XmlElement(name = "SubjectQuantityItem", required = true)
        protected List<SubjectQuantity> subjectQuantityItem;

        /**
         * Gets the value of the subjectQuantityItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the subjectQuantityItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSubjectQuantityItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SubjectQuantity }
         * 
         * 
         */
        public List<SubjectQuantity> getSubjectQuantityItem() {
            if (subjectQuantityItem == null) {
                subjectQuantityItem = new ArrayList<SubjectQuantity>();
            }
            return this.subjectQuantityItem;
        }

    }

}
