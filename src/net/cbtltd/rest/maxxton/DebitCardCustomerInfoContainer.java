
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DebitCardCustomerInfoContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DebitCardCustomerInfoContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DebitCardCustomerInfoItems" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="DebitCardCustomerInfoItem" type="{http://service.newyse.ws.services.newyse.maxxton/}DebitCardCustomerInfo" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DebitCardCustomerInfoContainer", propOrder = {
    "debitCardCustomerInfoItems"
})
public class DebitCardCustomerInfoContainer {

    @XmlElement(name = "DebitCardCustomerInfoItems")
    protected DebitCardCustomerInfoContainer.DebitCardCustomerInfoItems debitCardCustomerInfoItems;

    /**
     * Gets the value of the debitCardCustomerInfoItems property.
     * 
     * @return
     *     possible object is
     *     {@link DebitCardCustomerInfoContainer.DebitCardCustomerInfoItems }
     *     
     */
    public DebitCardCustomerInfoContainer.DebitCardCustomerInfoItems getDebitCardCustomerInfoItems() {
        return debitCardCustomerInfoItems;
    }

    /**
     * Sets the value of the debitCardCustomerInfoItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link DebitCardCustomerInfoContainer.DebitCardCustomerInfoItems }
     *     
     */
    public void setDebitCardCustomerInfoItems(DebitCardCustomerInfoContainer.DebitCardCustomerInfoItems value) {
        this.debitCardCustomerInfoItems = value;
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
     *         &lt;element name="DebitCardCustomerInfoItem" type="{http://service.newyse.ws.services.newyse.maxxton/}DebitCardCustomerInfo" maxOccurs="unbounded" minOccurs="0"/>
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
        "debitCardCustomerInfoItem"
    })
    public static class DebitCardCustomerInfoItems {

        @XmlElement(name = "DebitCardCustomerInfoItem")
        protected List<DebitCardCustomerInfo> debitCardCustomerInfoItem;

        /**
         * Gets the value of the debitCardCustomerInfoItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the debitCardCustomerInfoItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDebitCardCustomerInfoItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DebitCardCustomerInfo }
         * 
         * 
         */
        public List<DebitCardCustomerInfo> getDebitCardCustomerInfoItem() {
            if (debitCardCustomerInfoItem == null) {
                debitCardCustomerInfoItem = new ArrayList<DebitCardCustomerInfo>();
            }
            return this.debitCardCustomerInfoItem;
        }

    }

}
