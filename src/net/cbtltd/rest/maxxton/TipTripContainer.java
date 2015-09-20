
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipTripContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TipTripContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipTrips" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TipTripItem" type="{http://service.newyse.ws.services.newyse.maxxton/}TipTrip" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "TipTripContainer", propOrder = {
    "tipTrips"
})
public class TipTripContainer {

    @XmlElement(name = "TipTrips")
    protected TipTripContainer.TipTrips tipTrips;

    /**
     * Gets the value of the tipTrips property.
     * 
     * @return
     *     possible object is
     *     {@link TipTripContainer.TipTrips }
     *     
     */
    public TipTripContainer.TipTrips getTipTrips() {
        return tipTrips;
    }

    /**
     * Sets the value of the tipTrips property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipTripContainer.TipTrips }
     *     
     */
    public void setTipTrips(TipTripContainer.TipTrips value) {
        this.tipTrips = value;
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
     *         &lt;element name="TipTripItem" type="{http://service.newyse.ws.services.newyse.maxxton/}TipTrip" maxOccurs="unbounded" minOccurs="0"/>
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
        "tipTripItem"
    })
    public static class TipTrips {

        @XmlElement(name = "TipTripItem")
        protected List<TipTrip> tipTripItem;

        /**
         * Gets the value of the tipTripItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the tipTripItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTipTripItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TipTrip }
         * 
         * 
         */
        public List<TipTrip> getTipTripItem() {
            if (tipTripItem == null) {
                tipTripItem = new ArrayList<TipTrip>();
            }
            return this.tipTripItem;
        }

    }

}
