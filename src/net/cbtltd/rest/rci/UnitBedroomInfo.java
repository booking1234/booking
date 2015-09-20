
package net.cbtltd.rest.rci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for unit-bedroom-info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="unit-bedroom-info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bedroom" type="{}bedroom"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "unit-bedroom-info", propOrder = {
    "bedroom"
})
public class UnitBedroomInfo {

    @XmlElement(required = true)
    protected Bedroom bedroom;

    /**
     * Gets the value of the bedroom property.
     * 
     * @return
     *     possible object is
     *     {@link Bedroom }
     *     
     */
    public Bedroom getBedroom() {
        return bedroom;
    }

    /**
     * Sets the value of the bedroom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Bedroom }
     *     
     */
    public void setBedroom(Bedroom value) {
        this.bedroom = value;
    }

}
