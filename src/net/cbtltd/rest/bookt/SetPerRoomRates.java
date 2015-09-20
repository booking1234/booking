
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
 *         &lt;element name="apiKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rates" type="{http://connect.bookt.com/Schemas/Rate.xsd}ArrayOfRate" minOccurs="0"/>
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
    "apiKey",
    "rates"
})
@XmlRootElement(name = "SetPerRoomRates")
public class SetPerRoomRates {

    @XmlElementRef(name = "apiKey", namespace = "https://connect.bookt.com/connect", type = JAXBElement.class)
    protected JAXBElement<String> apiKey;
    @XmlElementRef(name = "rates", namespace = "https://connect.bookt.com/connect", type = JAXBElement.class)
    protected JAXBElement<ArrayOfRate> rates;

    /**
     * Gets the value of the apiKey property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getApiKey() {
        return apiKey;
    }

    /**
     * Sets the value of the apiKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setApiKey(JAXBElement<String> value) {
        this.apiKey = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the rates property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfRate }{@code >}
     *     
     */
    public JAXBElement<ArrayOfRate> getRates() {
        return rates;
    }

    /**
     * Sets the value of the rates property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfRate }{@code >}
     *     
     */
    public void setRates(JAXBElement<ArrayOfRate> value) {
        this.rates = ((JAXBElement<ArrayOfRate> ) value);
    }

}
