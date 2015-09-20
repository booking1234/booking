
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
 *         &lt;element name="GetPropertyResult" type="{http://connect.bookt.com/Schemas/Property.xsd}Property" minOccurs="0"/>
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
    "getPropertyResult"
})
@XmlRootElement(name = "GetPropertyResponse")
public class GetPropertyResponse {

    @XmlElementRef(name = "GetPropertyResult", namespace = "https://connect.bookt.com/connect", type = JAXBElement.class)
    protected JAXBElement<Property> getPropertyResult;

    /**
     * Gets the value of the getPropertyResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Property }{@code >}
     *     
     */
    public JAXBElement<Property> getGetPropertyResult() {
        return getPropertyResult;
    }

    /**
     * Sets the value of the getPropertyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Property }{@code >}
     *     
     */
    public void setGetPropertyResult(JAXBElement<Property> value) {
        this.getPropertyResult = ((JAXBElement<Property> ) value);
    }

}
