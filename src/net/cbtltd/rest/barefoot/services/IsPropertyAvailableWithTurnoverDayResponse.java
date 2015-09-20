
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
 *         &lt;element name="IsPropertyAvailableWithTurnoverDayResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "isPropertyAvailableWithTurnoverDayResult"
})
@XmlRootElement(name = "IsPropertyAvailableWithTurnoverDayResponse")
public class IsPropertyAvailableWithTurnoverDayResponse {

    @XmlElement(name = "IsPropertyAvailableWithTurnoverDayResult")
    protected boolean isPropertyAvailableWithTurnoverDayResult;

    /**
     * Gets the value of the isPropertyAvailableWithTurnoverDayResult property.
     * 
     */
    public boolean isIsPropertyAvailableWithTurnoverDayResult() {
        return isPropertyAvailableWithTurnoverDayResult;
    }

    /**
     * Sets the value of the isPropertyAvailableWithTurnoverDayResult property.
     * 
     */
    public void setIsPropertyAvailableWithTurnoverDayResult(boolean value) {
        this.isPropertyAvailableWithTurnoverDayResult = value;
    }

}
