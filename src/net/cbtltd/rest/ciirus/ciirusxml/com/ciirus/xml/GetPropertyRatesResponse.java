
package net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml;

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
 *         &lt;element name="GetPropertyRatesResult" type="{http://xml.ciirus.com/}ArrayOfRate" minOccurs="0"/>
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
    "getPropertyRatesResult"
})
@XmlRootElement(name = "GetPropertyRatesResponse")
public class GetPropertyRatesResponse {

    @XmlElement(name = "GetPropertyRatesResult")
    protected ArrayOfRate getPropertyRatesResult;

    /**
     * Gets the value of the getPropertyRatesResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRate }
     *     
     */
    public ArrayOfRate getGetPropertyRatesResult() {
        return getPropertyRatesResult;
    }

    /**
     * Sets the value of the getPropertyRatesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRate }
     *     
     */
    public void setGetPropertyRatesResult(ArrayOfRate value) {
        this.getPropertyRatesResult = value;
    }

}
