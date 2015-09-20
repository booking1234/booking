
package net.cbtltd.rest.maxxton;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PropertyCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PropertyCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PropertyManagerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="IncludePartial" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropertyCriteria", propOrder = {
    "propertyManagerId",
    "includePartial"
})
public class PropertyCriteria {

    @XmlElement(name = "PropertyManagerId")
    protected long propertyManagerId;
    @XmlElementRef(name = "IncludePartial", type = JAXBElement.class)
    protected JAXBElement<Boolean> includePartial;

    /**
     * Gets the value of the propertyManagerId property.
     * 
     */
    public long getPropertyManagerId() {
        return propertyManagerId;
    }

    /**
     * Sets the value of the propertyManagerId property.
     * 
     */
    public void setPropertyManagerId(long value) {
        this.propertyManagerId = value;
    }

    /**
     * Gets the value of the includePartial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getIncludePartial() {
        return includePartial;
    }

    /**
     * Sets the value of the includePartial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setIncludePartial(JAXBElement<Boolean> value) {
        this.includePartial = ((JAXBElement<Boolean> ) value);
    }

}
