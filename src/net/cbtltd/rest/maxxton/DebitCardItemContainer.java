
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DebitCardItemContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DebitCardItemContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DebitCardItems" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://service.newyse.ws.services.newyse.maxxton/}DebitCardItem" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "DebitCardItemContainer", propOrder = {
    "debitCardItems"
})
public class DebitCardItemContainer {

    @XmlElement(name = "DebitCardItems")
    protected DebitCardItemContainer.DebitCardItems debitCardItems;

    /**
     * Gets the value of the debitCardItems property.
     * 
     * @return
     *     possible object is
     *     {@link DebitCardItemContainer.DebitCardItems }
     *     
     */
    public DebitCardItemContainer.DebitCardItems getDebitCardItems() {
        return debitCardItems;
    }

    /**
     * Sets the value of the debitCardItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link DebitCardItemContainer.DebitCardItems }
     *     
     */
    public void setDebitCardItems(DebitCardItemContainer.DebitCardItems value) {
        this.debitCardItems = value;
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
     *         &lt;element ref="{http://service.newyse.ws.services.newyse.maxxton/}DebitCardItem" maxOccurs="unbounded" minOccurs="0"/>
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
        "debitCardItem"
    })
    public static class DebitCardItems {

        @XmlElement(name = "DebitCardItem", namespace = "http://service.newyse.ws.services.newyse.maxxton/")
        protected List<DebitCardItem> debitCardItem;

        /**
         * Gets the value of the debitCardItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the debitCardItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDebitCardItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DebitCardItem }
         * 
         * 
         */
        public List<DebitCardItem> getDebitCardItem() {
            if (debitCardItem == null) {
                debitCardItem = new ArrayList<DebitCardItem>();
            }
            return this.debitCardItem;
        }

    }

}
