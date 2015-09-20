
package net.cbtltd.rest.bookt;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="reviewType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastMod" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="useInternalID" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
    "reviewType",
    "lastMod",
    "useInternalID"
})
@XmlRootElement(name = "GetReviewIDs")
public class GetReviewIDs {

    @XmlElementRef(name = "apiKey", namespace = "https://connect.bookt.com/connect", type = JAXBElement.class)
    protected JAXBElement<String> apiKey;
    @XmlElementRef(name = "reviewType", namespace = "https://connect.bookt.com/connect", type = JAXBElement.class)
    protected JAXBElement<String> reviewType;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastMod;
    protected Boolean useInternalID;

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
     * Gets the value of the reviewType property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getReviewType() {
        return reviewType;
    }

    /**
     * Sets the value of the reviewType property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setReviewType(JAXBElement<String> value) {
        this.reviewType = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the lastMod property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastMod() {
        return lastMod;
    }

    /**
     * Sets the value of the lastMod property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastMod(XMLGregorianCalendar value) {
        this.lastMod = value;
    }

    /**
     * Gets the value of the useInternalID property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseInternalID() {
        return useInternalID;
    }

    /**
     * Sets the value of the useInternalID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseInternalID(Boolean value) {
        this.useInternalID = value;
    }

}
