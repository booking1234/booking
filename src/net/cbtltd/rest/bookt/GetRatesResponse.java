
package net.cbtltd.rest.bookt;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
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
 *         &lt;element name="GetRatesResult" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfKeyValueOfdateTimedecimal" minOccurs="0"/>
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
    "getRatesResult"
})
@XmlRootElement(name = "GetRatesResponse")
public class GetRatesResponse {

    @XmlElementRef(name = "GetRatesResult", namespace = "https://connect.bookt.com/connect", type = JAXBElement.class)
    protected JAXBElement<ArrayOfKeyValueOfdateTimedecimal> getRatesResult;

    /**
     * Gets the value of the getRatesResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfdateTimedecimal }{@code >}
     *     
     */
    public JAXBElement<ArrayOfKeyValueOfdateTimedecimal> getGetRatesResult() {
        return getRatesResult;
    }

    /**
     * Sets the value of the getRatesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfdateTimedecimal }{@code >}
     *     
     */
    public void setGetRatesResult(JAXBElement<ArrayOfKeyValueOfdateTimedecimal> value) {
        this.getRatesResult = ((JAXBElement<ArrayOfKeyValueOfdateTimedecimal> ) value);
    }

}
