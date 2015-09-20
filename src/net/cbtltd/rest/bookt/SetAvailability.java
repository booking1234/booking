
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
 *         &lt;element name="propertyID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="effDates" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfdateTime" minOccurs="0"/>
 *         &lt;element name="numAvailabile" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfint" minOccurs="0"/>
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
    "propertyID",
    "effDates",
    "numAvailabile"
})
@XmlRootElement(name = "SetAvailability")
public class SetAvailability {

    @XmlElementRef(name = "apiKey", namespace = "https://connect.bookt.com/connect", type = JAXBElement.class)
    protected JAXBElement<String> apiKey;
    protected Integer propertyID;
    @XmlElementRef(name = "effDates", namespace = "https://connect.bookt.com/connect", type = JAXBElement.class)
    protected JAXBElement<ArrayOfdateTime> effDates;
    @XmlElementRef(name = "numAvailabile", namespace = "https://connect.bookt.com/connect", type = JAXBElement.class)
    protected JAXBElement<ArrayOfint> numAvailabile;

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
     * Gets the value of the propertyID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPropertyID() {
        return propertyID;
    }

    /**
     * Sets the value of the propertyID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPropertyID(Integer value) {
        this.propertyID = value;
    }

    /**
     * Gets the value of the effDates property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfdateTime }{@code >}
     *     
     */
    public JAXBElement<ArrayOfdateTime> getEffDates() {
        return effDates;
    }

    /**
     * Sets the value of the effDates property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfdateTime }{@code >}
     *     
     */
    public void setEffDates(JAXBElement<ArrayOfdateTime> value) {
        this.effDates = ((JAXBElement<ArrayOfdateTime> ) value);
    }

    /**
     * Gets the value of the numAvailabile property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfint }{@code >}
     *     
     */
    public JAXBElement<ArrayOfint> getNumAvailabile() {
        return numAvailabile;
    }

    /**
     * Sets the value of the numAvailabile property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfint }{@code >}
     *     
     */
    public void setNumAvailabile(JAXBElement<ArrayOfint> value) {
        this.numAvailabile = ((JAXBElement<ArrayOfint> ) value);
    }

}
