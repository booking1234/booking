
package net.cbtltd.rest.barefoot.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="IsPropertyAvailabilityResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "isPropertyAvailabilityResult"
})
@XmlRootElement(name = "IsPropertyAvailabilityResponse")
public class IsPropertyAvailabilityResponse {

    @XmlElement(name = "IsPropertyAvailabilityResult")
    protected boolean isPropertyAvailabilityResult;

    /**
     * Gets the value of the isPropertyAvailabilityResult property.
     * 
     */
    public boolean isIsPropertyAvailabilityResult() {
        return isPropertyAvailabilityResult;
    }

    /**
     * Sets the value of the isPropertyAvailabilityResult property.
     * 
     */
    public void setIsPropertyAvailabilityResult(boolean value) {
        this.isPropertyAvailabilityResult = value;
    }

}
