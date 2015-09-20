
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for KCPincodes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="KCPincodes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResortCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PincodeLength" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="LockCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PincodeItems" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://service.newyse.ws.services.newyse.maxxton/}PincodeItem" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "KCPincodes", propOrder = {
    "resortCode",
    "pincodeLength",
    "lockCode",
    "pincodeItems"
})
public class KCPincodes {

    @XmlElement(name = "ResortCode", required = true)
    protected String resortCode;
    @XmlElement(name = "PincodeLength")
    protected int pincodeLength;
    @XmlElement(name = "LockCode", required = true)
    protected String lockCode;
    @XmlElement(name = "PincodeItems")
    protected KCPincodes.PincodeItems pincodeItems;

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
     * Gets the value of the pincodeLength property.
     * 
     */
    public int getPincodeLength() {
        return pincodeLength;
    }

    /**
     * Sets the value of the pincodeLength property.
     * 
     */
    public void setPincodeLength(int value) {
        this.pincodeLength = value;
    }

    /**
     * Gets the value of the lockCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLockCode() {
        return lockCode;
    }

    /**
     * Sets the value of the lockCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLockCode(String value) {
        this.lockCode = value;
    }

    /**
     * Gets the value of the pincodeItems property.
     * 
     * @return
     *     possible object is
     *     {@link KCPincodes.PincodeItems }
     *     
     */
    public KCPincodes.PincodeItems getPincodeItems() {
        return pincodeItems;
    }

    /**
     * Sets the value of the pincodeItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link KCPincodes.PincodeItems }
     *     
     */
    public void setPincodeItems(KCPincodes.PincodeItems value) {
        this.pincodeItems = value;
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
     *         &lt;element ref="{http://service.newyse.ws.services.newyse.maxxton/}PincodeItem" maxOccurs="unbounded" minOccurs="0"/>
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
        "pincodeItem"
    })
    public static class PincodeItems {

        @XmlElement(name = "PincodeItem", namespace = "http://service.newyse.ws.services.newyse.maxxton/")
        protected List<PincodeItem> pincodeItem;

        /**
         * Gets the value of the pincodeItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the pincodeItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPincodeItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PincodeItem }
         * 
         * 
         */
        public List<PincodeItem> getPincodeItem() {
            if (pincodeItem == null) {
                pincodeItem = new ArrayList<PincodeItem>();
            }
            return this.pincodeItem;
        }

    }

}
