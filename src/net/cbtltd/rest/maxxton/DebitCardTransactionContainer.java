
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DebitCardTransactionContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DebitCardTransactionContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DebitCardConsumptions" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://service.newyse.ws.services.newyse.maxxton/}DebitCardConsumption" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Outlet" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ReservationId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="OrderId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="DebitCardNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CustomerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ResortCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DebitCardTransactionContainer", propOrder = {
    "debitCardConsumptions",
    "outlet",
    "reservationId",
    "orderId",
    "debitCardNumber",
    "customerId",
    "resortCode"
})
public class DebitCardTransactionContainer {

    @XmlElement(name = "DebitCardConsumptions")
    protected DebitCardTransactionContainer.DebitCardConsumptions debitCardConsumptions;
    @XmlElement(name = "Outlet", required = true)
    protected String outlet;
    @XmlElement(name = "ReservationId")
    protected Long reservationId;
    @XmlElement(name = "OrderId")
    protected Long orderId;
    @XmlElement(name = "DebitCardNumber", required = true)
    protected String debitCardNumber;
    @XmlElement(name = "CustomerId")
    protected long customerId;
    @XmlElement(name = "ResortCode", required = true)
    protected String resortCode;

    /**
     * Gets the value of the debitCardConsumptions property.
     * 
     * @return
     *     possible object is
     *     {@link DebitCardTransactionContainer.DebitCardConsumptions }
     *     
     */
    public DebitCardTransactionContainer.DebitCardConsumptions getDebitCardConsumptions() {
        return debitCardConsumptions;
    }

    /**
     * Sets the value of the debitCardConsumptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link DebitCardTransactionContainer.DebitCardConsumptions }
     *     
     */
    public void setDebitCardConsumptions(DebitCardTransactionContainer.DebitCardConsumptions value) {
        this.debitCardConsumptions = value;
    }

    /**
     * Gets the value of the outlet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutlet() {
        return outlet;
    }

    /**
     * Sets the value of the outlet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutlet(String value) {
        this.outlet = value;
    }

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
     * Gets the value of the orderId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * Sets the value of the orderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOrderId(Long value) {
        this.orderId = value;
    }

    /**
     * Gets the value of the debitCardNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDebitCardNumber() {
        return debitCardNumber;
    }

    /**
     * Sets the value of the debitCardNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDebitCardNumber(String value) {
        this.debitCardNumber = value;
    }

    /**
     * Gets the value of the customerId property.
     * 
     */
    public long getCustomerId() {
        return customerId;
    }

    /**
     * Sets the value of the customerId property.
     * 
     */
    public void setCustomerId(long value) {
        this.customerId = value;
    }

    /**
     * Gets the value of the resortCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResortCode() {
        return resortCode;
    }

    /**
     * Sets the value of the resortCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResortCode(String value) {
        this.resortCode = value;
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
     *         &lt;element ref="{http://service.newyse.ws.services.newyse.maxxton/}DebitCardConsumption" maxOccurs="unbounded"/>
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
        "debitCardConsumption"
    })
    public static class DebitCardConsumptions {

        @XmlElement(name = "DebitCardConsumption", namespace = "http://service.newyse.ws.services.newyse.maxxton/", required = true)
        protected List<DebitCardConsumption> debitCardConsumption;

        /**
         * Gets the value of the debitCardConsumption property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the debitCardConsumption property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDebitCardConsumption().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DebitCardConsumption }
         * 
         * 
         */
        public List<DebitCardConsumption> getDebitCardConsumption() {
            if (debitCardConsumption == null) {
                debitCardConsumption = new ArrayList<DebitCardConsumption>();
            }
            return this.debitCardConsumption;
        }

    }

}
