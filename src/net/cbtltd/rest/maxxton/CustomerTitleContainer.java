
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerTitleContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerTitleContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CustomerTitles" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CustomerTitleItem" type="{http://service.newyse.ws.services.newyse.maxxton/}WSCustomerTitle" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "CustomerTitleContainer", propOrder = {
    "customerTitles"
})
public class CustomerTitleContainer {

    @XmlElement(name = "CustomerTitles")
    protected CustomerTitleContainer.CustomerTitles customerTitles;

    /**
     * Gets the value of the customerTitles property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerTitleContainer.CustomerTitles }
     *     
     */
    public CustomerTitleContainer.CustomerTitles getCustomerTitles() {
        return customerTitles;
    }

    /**
     * Sets the value of the customerTitles property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerTitleContainer.CustomerTitles }
     *     
     */
    public void setCustomerTitles(CustomerTitleContainer.CustomerTitles value) {
        this.customerTitles = value;
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
     *         &lt;element name="CustomerTitleItem" type="{http://service.newyse.ws.services.newyse.maxxton/}WSCustomerTitle" maxOccurs="unbounded" minOccurs="0"/>
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
        "customerTitleItem"
    })
    public static class CustomerTitles {

        @XmlElement(name = "CustomerTitleItem")
        protected List<WSCustomerTitle> customerTitleItem;

        /**
         * Gets the value of the customerTitleItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the customerTitleItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCustomerTitleItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link WSCustomerTitle }
         * 
         * 
         */
        public List<WSCustomerTitle> getCustomerTitleItem() {
            if (customerTitleItem == null) {
                customerTitleItem = new ArrayList<WSCustomerTitle>();
            }
            return this.customerTitleItem;
        }

    }

}
