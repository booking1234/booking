
package net.cbtltd.rest.leisurelink.ExternalServices;

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
 *         &lt;element name="GetRatesAndAvailabiltyUpdatedResult" type="{http://tempuri.org/}RateAvailabilityUpdateRQ" minOccurs="0"/>
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
    "getRatesAndAvailabiltyUpdatedResult"
})
@XmlRootElement(name = "GetRatesAndAvailabiltyUpdatedResponse")
public class GetRatesAndAvailabiltyUpdatedResponse {

    @XmlElement(name = "GetRatesAndAvailabiltyUpdatedResult")
    protected RateAvailabilityUpdateRQ getRatesAndAvailabiltyUpdatedResult;

    /**
     * Gets the value of the getRatesAndAvailabiltyUpdatedResult property.
     * 
     * @return
     *     possible object is
     *     {@link RateAvailabilityUpdateRQ }
     *     
     */
    public RateAvailabilityUpdateRQ getGetRatesAndAvailabiltyUpdatedResult() {
        return getRatesAndAvailabiltyUpdatedResult;
    }

    /**
     * Sets the value of the getRatesAndAvailabiltyUpdatedResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link RateAvailabilityUpdateRQ }
     *     
     */
    public void setGetRatesAndAvailabiltyUpdatedResult(RateAvailabilityUpdateRQ value) {
        this.getRatesAndAvailabiltyUpdatedResult = value;
    }

}
