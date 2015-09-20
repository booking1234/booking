
package net.cbtltd.rest.maxxton;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipTripCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TipTripCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResortCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TipTripCategoryId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipTripCriteria", propOrder = {
    "resortCode",
    "tipTripCategoryId"
})
public class TipTripCriteria {

    @XmlElementRef(name = "ResortCode", type = JAXBElement.class)
    protected JAXBElement<String> resortCode;
    @XmlElementRef(name = "TipTripCategoryId", type = JAXBElement.class)
    protected JAXBElement<Long> tipTripCategoryId;

    /**
     * Gets the value of the resortCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getResortCode() {
        return resortCode;
    }

    /**
     * Sets the value of the resortCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setResortCode(JAXBElement<String> value) {
        this.resortCode = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the tipTripCategoryId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public JAXBElement<Long> getTipTripCategoryId() {
        return tipTripCategoryId;
    }

    /**
     * Sets the value of the tipTripCategoryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public void setTipTripCategoryId(JAXBElement<Long> value) {
        this.tipTripCategoryId = ((JAXBElement<Long> ) value);
    }

}
