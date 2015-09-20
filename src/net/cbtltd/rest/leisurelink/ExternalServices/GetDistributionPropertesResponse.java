
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
 *         &lt;element name="GetDistributionPropertesResult" type="{http://tempuri.org/}ArrayOfDistributionProperty" minOccurs="0"/>
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
    "getDistributionPropertesResult"
})
@XmlRootElement(name = "GetDistributionPropertesResponse")
public class GetDistributionPropertesResponse {

    @XmlElement(name = "GetDistributionPropertesResult")
    protected ArrayOfDistributionProperty getDistributionPropertesResult;

    /**
     * Gets the value of the getDistributionPropertesResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDistributionProperty }
     *     
     */
    public ArrayOfDistributionProperty getGetDistributionPropertesResult() {
        return getDistributionPropertesResult;
    }

    /**
     * Sets the value of the getDistributionPropertesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDistributionProperty }
     *     
     */
    public void setGetDistributionPropertesResult(ArrayOfDistributionProperty value) {
        this.getDistributionPropertesResult = value;
    }

}
