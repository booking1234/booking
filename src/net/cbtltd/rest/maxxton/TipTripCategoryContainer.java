
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipTripCategoryContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TipTripCategoryContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipTripCategories" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TipTripCategoryItem" type="{http://service.newyse.ws.services.newyse.maxxton/}TipTripCategory" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "TipTripCategoryContainer", propOrder = {
    "tipTripCategories"
})
public class TipTripCategoryContainer {

    @XmlElement(name = "TipTripCategories")
    protected TipTripCategoryContainer.TipTripCategories tipTripCategories;

    /**
     * Gets the value of the tipTripCategories property.
     * 
     * @return
     *     possible object is
     *     {@link TipTripCategoryContainer.TipTripCategories }
     *     
     */
    public TipTripCategoryContainer.TipTripCategories getTipTripCategories() {
        return tipTripCategories;
    }

    /**
     * Sets the value of the tipTripCategories property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipTripCategoryContainer.TipTripCategories }
     *     
     */
    public void setTipTripCategories(TipTripCategoryContainer.TipTripCategories value) {
        this.tipTripCategories = value;
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
     *         &lt;element name="TipTripCategoryItem" type="{http://service.newyse.ws.services.newyse.maxxton/}TipTripCategory" maxOccurs="unbounded" minOccurs="0"/>
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
        "tipTripCategoryItem"
    })
    public static class TipTripCategories {

        @XmlElement(name = "TipTripCategoryItem")
        protected List<TipTripCategory> tipTripCategoryItem;

        /**
         * Gets the value of the tipTripCategoryItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the tipTripCategoryItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTipTripCategoryItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TipTripCategory }
         * 
         * 
         */
        public List<TipTripCategory> getTipTripCategoryItem() {
            if (tipTripCategoryItem == null) {
                tipTripCategoryItem = new ArrayList<TipTripCategory>();
            }
            return this.tipTripCategoryItem;
        }

    }

}
