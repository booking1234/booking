
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
 *         &lt;element name="GetEventResult" type="{http://connect.bookt.com/Schemas/Event.xsd}Event" minOccurs="0"/>
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
    "getEventResult"
})
@XmlRootElement(name = "GetEventResponse")
public class GetEventResponse {

    @XmlElementRef(name = "GetEventResult", namespace = "https://connect.bookt.com/connect", type = JAXBElement.class)
    protected JAXBElement<Event> getEventResult;

    /**
     * Gets the value of the getEventResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Event }{@code >}
     *     
     */
    public JAXBElement<Event> getGetEventResult() {
        return getEventResult;
    }

    /**
     * Sets the value of the getEventResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Event }{@code >}
     *     
     */
    public void setGetEventResult(JAXBElement<Event> value) {
        this.getEventResult = ((JAXBElement<Event> ) value);
    }

}
