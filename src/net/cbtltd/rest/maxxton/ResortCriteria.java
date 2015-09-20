
package net.cbtltd.rest.maxxton;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResortCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResortCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DistributionChannelCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DistributionChannelId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="IgnoreRentable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResortCriteria", propOrder = {
    "distributionChannelCode",
    "distributionChannelId",
    "ignoreRentable"
})
public class ResortCriteria {

    @XmlElementRef(name = "DistributionChannelCode", type = JAXBElement.class)
    protected JAXBElement<String> distributionChannelCode;
    @XmlElementRef(name = "DistributionChannelId", type = JAXBElement.class)
    protected JAXBElement<Long> distributionChannelId;
    @XmlElement(name = "IgnoreRentable", required = true, type = Boolean.class, defaultValue = "false", nillable = true)
    protected Boolean ignoreRentable;

    /**
     * Gets the value of the distributionChannelCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDistributionChannelCode() {
        return distributionChannelCode;
    }

    /**
     * Sets the value of the distributionChannelCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDistributionChannelCode(JAXBElement<String> value) {
        this.distributionChannelCode = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the distributionChannelId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public JAXBElement<Long> getDistributionChannelId() {
        return distributionChannelId;
    }

    /**
     * Sets the value of the distributionChannelId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public void setDistributionChannelId(JAXBElement<Long> value) {
        this.distributionChannelId = ((JAXBElement<Long> ) value);
    }

    /**
     * Gets the value of the ignoreRentable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIgnoreRentable() {
        return ignoreRentable;
    }

    /**
     * Sets the value of the ignoreRentable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIgnoreRentable(Boolean value) {
        this.ignoreRentable = value;
    }

}
