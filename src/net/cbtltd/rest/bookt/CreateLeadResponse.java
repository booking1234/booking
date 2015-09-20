
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
 *         &lt;element name="CreateLeadResult" type="{http://connect.bookt.com/Schemas/Person.xsd}Person" minOccurs="0"/>
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
    "createLeadResult"
})
@XmlRootElement(name = "CreateLeadResponse")
public class CreateLeadResponse {

    @XmlElementRef(name = "CreateLeadResult", namespace = "https://connect.bookt.com/connect", type = JAXBElement.class)
    protected JAXBElement<Person> createLeadResult;

    /**
     * Gets the value of the createLeadResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Person }{@code >}
     *     
     */
    public JAXBElement<Person> getCreateLeadResult() {
        return createLeadResult;
    }

    /**
     * Sets the value of the createLeadResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Person }{@code >}
     *     
     */
    public void setCreateLeadResult(JAXBElement<Person> value) {
        this.createLeadResult = ((JAXBElement<Person> ) value);
    }

}
