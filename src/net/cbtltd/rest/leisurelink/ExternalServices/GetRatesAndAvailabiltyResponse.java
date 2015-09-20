
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
 *         &lt;element name="GetRatesAndAvailabiltyResult" type="{http://tempuri.org/}RateAvailabilityUpdateRQ" minOccurs="0"/>
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
    "getRatesAndAvailabiltyResult"
})
@XmlRootElement(name = "GetRatesAndAvailabiltyResponse")
public class GetRatesAndAvailabiltyResponse {

    @XmlElement(name = "GetRatesAndAvailabiltyResult")
    protected RateAvailabilityUpdateRQ getRatesAndAvailabiltyResult;

    /**
     * Gets the value of the getRatesAndAvailabiltyResult property.
     * 
     * @return
     *     possible object is
     *     {@link RateAvailabilityUpdateRQ }
     *     
     */
    public RateAvailabilityUpdateRQ getGetRatesAndAvailabiltyResult() {
        return getRatesAndAvailabiltyResult;
    }

    /**
     * Sets the value of the getRatesAndAvailabiltyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link RateAvailabilityUpdateRQ }
     *     
     */
    public void setGetRatesAndAvailabiltyResult(RateAvailabilityUpdateRQ value) {
        this.getRatesAndAvailabiltyResult = value;
    }

}
