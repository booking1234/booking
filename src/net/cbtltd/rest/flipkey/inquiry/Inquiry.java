//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.05.13 at 09:24:19 PM IST 
//


package net.cbtltd.rest.flipkey.inquiry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="header">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="source">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="FK"/>
 *                         &lt;enumeration value="TA"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/all>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="data">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="request_date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="total_guests" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                   &lt;element name="guests_adult" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                   &lt;element name="guests_child" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                   &lt;element name="check_in" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="check_out" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="property_id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                   &lt;element name="email" type="{}EmailType"/>
 *                   &lt;element name="phone_number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="point_of_sale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="user_ip" type="{}IPType"/>
 *                   &lt;element name="utm_source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="utm_medium" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="utm_term" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="utm_campaign" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/all>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "inquiry")
public class Inquiry {

    @XmlElement(required = true)
    protected Inquiry.Header header;
    @XmlElement(required = true)
    protected Inquiry.Data data;

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link Inquiry.Header }
     *     
     */
    public Inquiry.Header getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link Inquiry.Header }
     *     
     */
    public void setHeader(Inquiry.Header value) {
        this.header = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link Inquiry.Data }
     *     
     */
    public Inquiry.Data getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link Inquiry.Data }
     *     
     */
    public void setData(Inquiry.Data value) {
        this.data = value;
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
     *       &lt;all>
     *         &lt;element name="request_date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="total_guests" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *         &lt;element name="guests_adult" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *         &lt;element name="guests_child" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *         &lt;element name="check_in" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="check_out" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="property_id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *         &lt;element name="email" type="{}EmailType"/>
     *         &lt;element name="phone_number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="point_of_sale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="user_ip" type="{}IPType"/>
     *         &lt;element name="utm_source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="utm_medium" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="utm_term" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="utm_campaign" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/all>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {

    })
    public static class Data {

        @XmlElement(name = "request_date", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar requestDate;
        @XmlElement(required = true)
        protected String name;
        @XmlElement(name = "total_guests")
        protected Integer totalGuests;
        @XmlElement(name = "guests_adult")
        protected Integer guestsAdult;
        @XmlElement(name = "guests_child")
        protected Integer guestsChild;
        @XmlElement(name = "check_in", required = true)
        protected String checkIn;
        @XmlElement(name = "check_out", required = true)
        protected String checkOut;
        @XmlElement(required = true)
        protected String comment;
        @XmlElement(name = "property_id")
        protected Integer propertyId;
        @XmlElement(required = true)
        protected String email;
        @XmlElement(name = "phone_number")
        protected String phoneNumber;
        @XmlElement(name = "point_of_sale")
        protected String pointOfSale;
        @XmlElement(name = "user_ip", required = true, nillable = true)
        protected String userIp;
        @XmlElement(name = "utm_source")
        protected String utmSource;
        @XmlElement(name = "utm_medium")
        protected String utmMedium;
        @XmlElement(name = "utm_term")
        protected String utmTerm;
        @XmlElement(name = "utm_campaign")
        protected String utmCampaign;

        /**
         * Gets the value of the requestDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getRequestDate() {
            return requestDate;
        }

        /**
         * Sets the value of the requestDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setRequestDate(XMLGregorianCalendar value) {
            this.requestDate = value;
        }

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the totalGuests property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getTotalGuests() {
            return totalGuests;
        }

        /**
         * Sets the value of the totalGuests property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setTotalGuests(Integer value) {
            this.totalGuests = value;
        }

        /**
         * Gets the value of the guestsAdult property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getGuestsAdult() {
            return guestsAdult;
        }

        /**
         * Sets the value of the guestsAdult property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setGuestsAdult(Integer value) {
            this.guestsAdult = value;
        }

        /**
         * Gets the value of the guestsChild property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getGuestsChild() {
            return guestsChild;
        }

        /**
         * Sets the value of the guestsChild property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setGuestsChild(Integer value) {
            this.guestsChild = value;
        }

        /**
         * Gets the value of the checkIn property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public String getCheckIn() {
            return checkIn;
        }

        /**
         * Sets the value of the checkIn property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setCheckIn(String value) {
            this.checkIn = value;
        }

        /**
         * Gets the value of the checkOut property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public String getCheckOut() {
            return checkOut;
        }

        /**
         * Sets the value of the checkOut property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setCheckOut(String value) {
            this.checkOut = value;
        }

        /**
         * Gets the value of the comment property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getComment() {
            return comment;
        }

        /**
         * Sets the value of the comment property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setComment(String value) {
            this.comment = value;
        }

        /**
         * Gets the value of the propertyId property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getPropertyId() {
            return propertyId;
        }

        /**
         * Sets the value of the propertyId property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setPropertyId(Integer value) {
            this.propertyId = value;
        }

        /**
         * Gets the value of the email property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEmail() {
            return email;
        }

        /**
         * Sets the value of the email property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEmail(String value) {
            this.email = value;
        }

        /**
         * Gets the value of the phoneNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPhoneNumber() {
            return phoneNumber;
        }

        /**
         * Sets the value of the phoneNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPhoneNumber(String value) {
            this.phoneNumber = value;
        }

        /**
         * Gets the value of the pointOfSale property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPointOfSale() {
            return pointOfSale;
        }

        /**
         * Sets the value of the pointOfSale property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPointOfSale(String value) {
            this.pointOfSale = value;
        }

        /**
         * Gets the value of the userIp property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUserIp() {
            return userIp;
        }

        /**
         * Sets the value of the userIp property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUserIp(String value) {
            this.userIp = value;
        }

        /**
         * Gets the value of the utmSource property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUtmSource() {
            return utmSource;
        }

        /**
         * Sets the value of the utmSource property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUtmSource(String value) {
            this.utmSource = value;
        }

        /**
         * Gets the value of the utmMedium property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUtmMedium() {
            return utmMedium;
        }

        /**
         * Sets the value of the utmMedium property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUtmMedium(String value) {
            this.utmMedium = value;
        }

        /**
         * Gets the value of the utmTerm property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUtmTerm() {
            return utmTerm;
        }

        /**
         * Sets the value of the utmTerm property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUtmTerm(String value) {
            this.utmTerm = value;
        }

        /**
         * Gets the value of the utmCampaign property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUtmCampaign() {
            return utmCampaign;
        }

        /**
         * Sets the value of the utmCampaign property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUtmCampaign(String value) {
            this.utmCampaign = value;
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
     *       &lt;all>
     *         &lt;element name="source">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="FK"/>
     *               &lt;enumeration value="TA"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *       &lt;/all>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {

    })
    public static class Header {

        @XmlElement(required = true, defaultValue = "FK")
        protected String source;

        /**
         * Gets the value of the source property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSource() {
            return source;
        }

        /**
         * Sets the value of the source property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSource(String value) {
            this.source = value;
        }

    }

}