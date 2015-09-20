
package net.cbtltd.rest.rci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for strata complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="strata">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="strata-description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strata-code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "strata", propOrder = {
    "strataDescription",
    "strataCode"
})
public class Strata {

    @XmlElement(name = "strata-description")
    protected String strataDescription;
    @XmlElement(name = "strata-code")
    protected String strataCode;

    /**
     * Gets the value of the strataDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrataDescription() {
        return strataDescription;
    }

    /**
     * Sets the value of the strataDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrataDescription(String value) {
        this.strataDescription = value;
    }

    /**
     * Gets the value of the strataCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrataCode() {
        return strataCode;
    }

    /**
     * Sets the value of the strataCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrataCode(String value) {
        this.strataCode = value;
    }

}
