
package net.cbtltd.rest.rci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for entertainment-furnishing-detail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="entertainment-furnishing-detail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="value" type="{}details" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entertainment-furnishing-detail", propOrder = {
    "value"
})
public class EntertainmentFurnishingDetail {

    protected Details value;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link Details }
     *     
     */
    public Details getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link Details }
     *     
     */
    public void setValue(Details value) {
        this.value = value;
    }

}
