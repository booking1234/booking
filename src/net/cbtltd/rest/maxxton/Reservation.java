
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Reservation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Reservation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReservationId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="ReservationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ArrivalDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="DepartureDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="BookDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TotalPrice" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="CustomerTotalPrice" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="AgentTotalPrice" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="PayingCustomerTotalPrice" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Resort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DistributionChannel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillLines" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="BillLineItem" type="{http://service.newyse.ws.services.newyse.maxxton/}ReservationBillLine" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AgentBillLines" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AgentBillLineItem" type="{http://service.newyse.ws.services.newyse.maxxton/}ReservationBillLine" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="PayingCustomerBillLines" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PayingCustomerBillLineItem" type="{http://service.newyse.ws.services.newyse.maxxton/}ReservationBillLine" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Infotexts" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="InfotextItem" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ReservedResources" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ReservedResourceItem" type="{http://service.newyse.ws.services.newyse.maxxton/}WSReservedResource" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="CustomerItem" type="{http://service.newyse.ws.services.newyse.maxxton/}Customer" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Reservation", propOrder = {
    "reservationId",
    "reservationNumber",
    "arrivalDate",
    "departureDate",
    "bookDate",
    "status",
    "totalPrice",
    "customerTotalPrice",
    "agentTotalPrice",
    "payingCustomerTotalPrice",
    "resort",
    "distributionChannel",
    "billLines",
    "agentBillLines",
    "payingCustomerBillLines",
    "infotexts",
    "reservedResources",
    "customerItem"
})
public class Reservation {

    @XmlElement(name = "ReservationId")
    protected Long reservationId;
    @XmlElement(name = "ReservationNumber")
    protected String reservationNumber;
    @XmlElement(name = "ArrivalDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar arrivalDate;
    @XmlElement(name = "DepartureDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar departureDate;
    @XmlElement(name = "BookDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar bookDate;
    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "TotalPrice")
    protected Double totalPrice;
    @XmlElement(name = "CustomerTotalPrice")
    protected Double customerTotalPrice;
    @XmlElement(name = "AgentTotalPrice")
    protected Double agentTotalPrice;
    @XmlElement(name = "PayingCustomerTotalPrice")
    protected Double payingCustomerTotalPrice;
    @XmlElement(name = "Resort")
    protected String resort;
    @XmlElement(name = "DistributionChannel")
    protected String distributionChannel;
    @XmlElement(name = "BillLines")
    protected Reservation.BillLines billLines;
    @XmlElement(name = "AgentBillLines")
    protected Reservation.AgentBillLines agentBillLines;
    @XmlElement(name = "PayingCustomerBillLines")
    protected Reservation.PayingCustomerBillLines payingCustomerBillLines;
    @XmlElement(name = "Infotexts")
    protected Reservation.Infotexts infotexts;
    @XmlElement(name = "ReservedResources")
    protected Reservation.ReservedResources reservedResources;
    @XmlElement(name = "CustomerItem")
    protected Customer customerItem;

    /**
     * Gets the value of the reservationId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getReservationId() {
        return reservationId;
    }

    /**
     * Sets the value of the reservationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setReservationId(Long value) {
        this.reservationId = value;
    }

    /**
     * Gets the value of the reservationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReservationNumber() {
        return reservationNumber;
    }

    /**
     * Sets the value of the reservationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReservationNumber(String value) {
        this.reservationNumber = value;
    }

    /**
     * Gets the value of the arrivalDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getArrivalDate() {
        return arrivalDate;
    }

    /**
     * Sets the value of the arrivalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setArrivalDate(XMLGregorianCalendar value) {
        this.arrivalDate = value;
    }

    /**
     * Gets the value of the departureDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the value of the departureDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDepartureDate(XMLGregorianCalendar value) {
        this.departureDate = value;
    }

    /**
     * Gets the value of the bookDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBookDate() {
        return bookDate;
    }

    /**
     * Sets the value of the bookDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBookDate(XMLGregorianCalendar value) {
        this.bookDate = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the totalPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the value of the totalPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTotalPrice(Double value) {
        this.totalPrice = value;
    }

    /**
     * Gets the value of the customerTotalPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCustomerTotalPrice() {
        return customerTotalPrice;
    }

    /**
     * Sets the value of the customerTotalPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCustomerTotalPrice(Double value) {
        this.customerTotalPrice = value;
    }

    /**
     * Gets the value of the agentTotalPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAgentTotalPrice() {
        return agentTotalPrice;
    }

    /**
     * Sets the value of the agentTotalPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAgentTotalPrice(Double value) {
        this.agentTotalPrice = value;
    }

    /**
     * Gets the value of the payingCustomerTotalPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPayingCustomerTotalPrice() {
        return payingCustomerTotalPrice;
    }

    /**
     * Sets the value of the payingCustomerTotalPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPayingCustomerTotalPrice(Double value) {
        this.payingCustomerTotalPrice = value;
    }

    /**
     * Gets the value of the resort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResort() {
        return resort;
    }

    /**
     * Sets the value of the resort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResort(String value) {
        this.resort = value;
    }

    /**
     * Gets the value of the distributionChannel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistributionChannel() {
        return distributionChannel;
    }

    /**
     * Sets the value of the distributionChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistributionChannel(String value) {
        this.distributionChannel = value;
    }

    /**
     * Gets the value of the billLines property.
     * 
     * @return
     *     possible object is
     *     {@link Reservation.BillLines }
     *     
     */
    public Reservation.BillLines getBillLines() {
        return billLines;
    }

    /**
     * Sets the value of the billLines property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reservation.BillLines }
     *     
     */
    public void setBillLines(Reservation.BillLines value) {
        this.billLines = value;
    }

    /**
     * Gets the value of the agentBillLines property.
     * 
     * @return
     *     possible object is
     *     {@link Reservation.AgentBillLines }
     *     
     */
    public Reservation.AgentBillLines getAgentBillLines() {
        return agentBillLines;
    }

    /**
     * Sets the value of the agentBillLines property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reservation.AgentBillLines }
     *     
     */
    public void setAgentBillLines(Reservation.AgentBillLines value) {
        this.agentBillLines = value;
    }

    /**
     * Gets the value of the payingCustomerBillLines property.
     * 
     * @return
     *     possible object is
     *     {@link Reservation.PayingCustomerBillLines }
     *     
     */
    public Reservation.PayingCustomerBillLines getPayingCustomerBillLines() {
        return payingCustomerBillLines;
    }

    /**
     * Sets the value of the payingCustomerBillLines property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reservation.PayingCustomerBillLines }
     *     
     */
    public void setPayingCustomerBillLines(Reservation.PayingCustomerBillLines value) {
        this.payingCustomerBillLines = value;
    }

    /**
     * Gets the value of the infotexts property.
     * 
     * @return
     *     possible object is
     *     {@link Reservation.Infotexts }
     *     
     */
    public Reservation.Infotexts getInfotexts() {
        return infotexts;
    }

    /**
     * Sets the value of the infotexts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reservation.Infotexts }
     *     
     */
    public void setInfotexts(Reservation.Infotexts value) {
        this.infotexts = value;
    }

    /**
     * Gets the value of the reservedResources property.
     * 
     * @return
     *     possible object is
     *     {@link Reservation.ReservedResources }
     *     
     */
    public Reservation.ReservedResources getReservedResources() {
        return reservedResources;
    }

    /**
     * Sets the value of the reservedResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reservation.ReservedResources }
     *     
     */
    public void setReservedResources(Reservation.ReservedResources value) {
        this.reservedResources = value;
    }

    /**
     * Gets the value of the customerItem property.
     * 
     * @return
     *     possible object is
     *     {@link Customer }
     *     
     */
    public Customer getCustomerItem() {
        return customerItem;
    }

    /**
     * Sets the value of the customerItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link Customer }
     *     
     */
    public void setCustomerItem(Customer value) {
        this.customerItem = value;
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
     *         &lt;element name="AgentBillLineItem" type="{http://service.newyse.ws.services.newyse.maxxton/}ReservationBillLine" maxOccurs="unbounded" minOccurs="0"/>
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
        "agentBillLineItem"
    })
    public static class AgentBillLines {

        @XmlElement(name = "AgentBillLineItem")
        protected List<ReservationBillLine> agentBillLineItem;

        /**
         * Gets the value of the agentBillLineItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the agentBillLineItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAgentBillLineItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ReservationBillLine }
         * 
         * 
         */
        public List<ReservationBillLine> getAgentBillLineItem() {
            if (agentBillLineItem == null) {
                agentBillLineItem = new ArrayList<ReservationBillLine>();
            }
            return this.agentBillLineItem;
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
     *         &lt;element name="BillLineItem" type="{http://service.newyse.ws.services.newyse.maxxton/}ReservationBillLine" maxOccurs="unbounded" minOccurs="0"/>
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
        "billLineItem"
    })
    public static class BillLines {

        @XmlElement(name = "BillLineItem")
        protected List<ReservationBillLine> billLineItem;

        /**
         * Gets the value of the billLineItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the billLineItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBillLineItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ReservationBillLine }
         * 
         * 
         */
        public List<ReservationBillLine> getBillLineItem() {
            if (billLineItem == null) {
                billLineItem = new ArrayList<ReservationBillLine>();
            }
            return this.billLineItem;
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
     *         &lt;element name="InfotextItem" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
        "infotextItem"
    })
    public static class Infotexts {

        @XmlElement(name = "InfotextItem")
        protected List<String> infotextItem;

        /**
         * Gets the value of the infotextItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the infotextItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInfotextItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getInfotextItem() {
            if (infotextItem == null) {
                infotextItem = new ArrayList<String>();
            }
            return this.infotextItem;
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
     *         &lt;element name="PayingCustomerBillLineItem" type="{http://service.newyse.ws.services.newyse.maxxton/}ReservationBillLine" maxOccurs="unbounded" minOccurs="0"/>
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
        "payingCustomerBillLineItem"
    })
    public static class PayingCustomerBillLines {

        @XmlElement(name = "PayingCustomerBillLineItem")
        protected List<ReservationBillLine> payingCustomerBillLineItem;

        /**
         * Gets the value of the payingCustomerBillLineItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the payingCustomerBillLineItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPayingCustomerBillLineItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ReservationBillLine }
         * 
         * 
         */
        public List<ReservationBillLine> getPayingCustomerBillLineItem() {
            if (payingCustomerBillLineItem == null) {
                payingCustomerBillLineItem = new ArrayList<ReservationBillLine>();
            }
            return this.payingCustomerBillLineItem;
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
     *         &lt;element name="ReservedResourceItem" type="{http://service.newyse.ws.services.newyse.maxxton/}WSReservedResource" maxOccurs="unbounded" minOccurs="0"/>
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
        "reservedResourceItem"
    })
    public static class ReservedResources {

        @XmlElement(name = "ReservedResourceItem")
        protected List<WSReservedResource> reservedResourceItem;

        /**
         * Gets the value of the reservedResourceItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the reservedResourceItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getReservedResourceItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link WSReservedResource }
         * 
         * 
         */
        public List<WSReservedResource> getReservedResourceItem() {
            if (reservedResourceItem == null) {
                reservedResourceItem = new ArrayList<WSReservedResource>();
            }
            return this.reservedResourceItem;
        }

    }

}
