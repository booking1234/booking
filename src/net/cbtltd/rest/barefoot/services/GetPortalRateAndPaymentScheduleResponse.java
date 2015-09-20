
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
 *         &lt;element name="GetPortalRateAndPaymentScheduleResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getPortalRateAndPaymentScheduleResult"
})
@XmlRootElement(name = "GetPortalRateAndPaymentScheduleResponse")
public class GetPortalRateAndPaymentScheduleResponse {

    @XmlElement(name = "GetPortalRateAndPaymentScheduleResult")
    protected String getPortalRateAndPaymentScheduleResult;

    /**
     * Gets the value of the getPortalRateAndPaymentScheduleResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetPortalRateAndPaymentScheduleResult() {
        return getPortalRateAndPaymentScheduleResult;
    }

    /**
     * Sets the value of the getPortalRateAndPaymentScheduleResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetPortalRateAndPaymentScheduleResult(String value) {
        this.getPortalRateAndPaymentScheduleResult = value;
    }

}
