
package net.cbtltd.rest.maxxton;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Resort complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Resort">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResortId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ResortParentId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ShortDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VisitaddressManagerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="MailaddressManagerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ImagemanagerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="PropertyManagerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Language" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Resort", propOrder = {
    "resortId",
    "resortParentId",
    "code",
    "name",
    "shortDescription",
    "description",
    "visitaddressManagerId",
    "mailaddressManagerId",
    "imagemanagerId",
    "propertyManagerId",
    "language"
})
public class Resort {

    @XmlElement(name = "ResortId")
    protected long resortId;
    @XmlElementRef(name = "ResortParentId", type = JAXBElement.class)
    protected JAXBElement<Long> resortParentId;
    @XmlElement(name = "Code", required = true)
    protected String code;
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElementRef(name = "ShortDescription", type = JAXBElement.class)
    protected JAXBElement<String> shortDescription;
    @XmlElementRef(name = "Description", type = JAXBElement.class)
    protected JAXBElement<String> description;
    @XmlElement(name = "VisitaddressManagerId")
    protected long visitaddressManagerId;
    @XmlElement(name = "MailaddressManagerId")
    protected long mailaddressManagerId;
    @XmlElement(name = "ImagemanagerId")
    protected long imagemanagerId;
    @XmlElement(name = "PropertyManagerId")
    protected long propertyManagerId;
    @XmlElement(name = "Language")
    protected String language;

    /**
     * Gets the value of the resortId property.
     * 
     */
    public long getResortId() {
        return resortId;
    }

    /**
     * Sets the value of the resortId property.
     * 
     */
    public void setResortId(long value) {
        this.resortId = value;
    }

    /**
     * Gets the value of the resortParentId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public JAXBElement<Long> getResortParentId() {
        return resortParentId;
    }

    /**
     * Sets the value of the resortParentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public void setResortParentId(JAXBElement<Long> value) {
        this.resortParentId = ((JAXBElement<Long> ) value);
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the shortDescription property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the value of the shortDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setShortDescription(JAXBElement<String> value) {
        this.shortDescription = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDescription(JAXBElement<String> value) {
        this.description = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the visitaddressManagerId property.
     * 
     */
    public long getVisitaddressManagerId() {
        return visitaddressManagerId;
    }

    /**
     * Sets the value of the visitaddressManagerId property.
     * 
     */
    public void setVisitaddressManagerId(long value) {
        this.visitaddressManagerId = value;
    }

    /**
     * Gets the value of the mailaddressManagerId property.
     * 
     */
    public long getMailaddressManagerId() {
        return mailaddressManagerId;
    }

    /**
     * Sets the value of the mailaddressManagerId property.
     * 
     */
    public void setMailaddressManagerId(long value) {
        this.mailaddressManagerId = value;
    }

    /**
     * Gets the value of the imagemanagerId property.
     * 
     */
    public long getImagemanagerId() {
        return imagemanagerId;
    }

    /**
     * Sets the value of the imagemanagerId property.
     * 
     */
    public void setImagemanagerId(long value) {
        this.imagemanagerId = value;
    }

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
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

}
