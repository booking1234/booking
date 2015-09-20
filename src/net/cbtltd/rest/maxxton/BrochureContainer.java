
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BrochureContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BrochureContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Brochures" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="BrochureItem" type="{http://service.newyse.ws.services.newyse.maxxton/}Brochure" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "BrochureContainer", propOrder = {
    "brochures"
})
public class BrochureContainer {

    @XmlElement(name = "Brochures")
    protected BrochureContainer.Brochures brochures;

    /**
     * Gets the value of the brochures property.
     * 
     * @return
     *     possible object is
     *     {@link BrochureContainer.Brochures }
     *     
     */
    public BrochureContainer.Brochures getBrochures() {
        return brochures;
    }

    /**
     * Sets the value of the brochures property.
     * 
     * @param value
     *     allowed object is
     *     {@link BrochureContainer.Brochures }
     *     
     */
    public void setBrochures(BrochureContainer.Brochures value) {
        this.brochures = value;
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
     *         &lt;element name="BrochureItem" type="{http://service.newyse.ws.services.newyse.maxxton/}Brochure" maxOccurs="unbounded" minOccurs="0"/>
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
        "brochureItem"
    })
    public static class Brochures {

        @XmlElement(name = "BrochureItem")
        protected List<Brochure> brochureItem;

        /**
         * Gets the value of the brochureItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the brochureItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBrochureItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Brochure }
         * 
         * 
         */
        public List<Brochure> getBrochureItem() {
            if (brochureItem == null) {
                brochureItem = new ArrayList<Brochure>();
            }
            return this.brochureItem;
        }

    }

}
