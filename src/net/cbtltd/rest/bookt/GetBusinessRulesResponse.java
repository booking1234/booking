
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
 *         &lt;element name="GetBusinessRulesResult" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfKeyValueOfstringstring" minOccurs="0"/>
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
    "getBusinessRulesResult"
})
@XmlRootElement(name = "GetBusinessRulesResponse")
public class GetBusinessRulesResponse {

    @XmlElementRef(name = "GetBusinessRulesResult", namespace = "https://connect.bookt.com/connect", type = JAXBElement.class)
    protected JAXBElement<ArrayOfKeyValueOfstringstring> getBusinessRulesResult;

    /**
     * Gets the value of the getBusinessRulesResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfstringstring }{@code >}
     *     
     */
    public JAXBElement<ArrayOfKeyValueOfstringstring> getGetBusinessRulesResult() {
        return getBusinessRulesResult;
    }

    /**
     * Sets the value of the getBusinessRulesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfstringstring }{@code >}
     *     
     */
    public void setGetBusinessRulesResult(JAXBElement<ArrayOfKeyValueOfstringstring> value) {
        this.getBusinessRulesResult = ((JAXBElement<ArrayOfKeyValueOfstringstring> ) value);
    }

}
