
package net.cbtltd.rest.maxxton;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResourceAddition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResourceAddition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResourceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ResortId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ShortDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaxQuantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MaxReservable" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="MinQuantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ImageManagerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResourceAddition", propOrder = {
    "resourceId",
    "resortId",
    "type",
    "code",
    "name",
    "shortDescription",
    "description",
    "maxQuantity",
    "maxReservable",
    "minQuantity",
    "imageManagerId"
})
public class ResourceAddition {

    @XmlElement(name = "ResourceId")
    protected long resourceId;
    @XmlElement(name = "ResortId")
    protected long resortId;
    @XmlElement(name = "Type", required = true)
    protected String type;
    @XmlElement(name = "Code", required = true)
    protected String code;
    @XmlElementRef(name = "Name", type = JAXBElement.class)
    protected JAXBElement<String> name;
    @XmlElementRef(name = "ShortDescription", type = JAXBElement.class)
    protected JAXBElement<String> shortDescription;
    @XmlElementRef(name = "Description", type = JAXBElement.class)
    protected JAXBElement<String> description;
    @XmlElement(name = "MaxQuantity")
    protected int maxQuantity;
    @XmlElementRef(name = "MaxReservable", type = JAXBElement.class)
    protected JAXBElement<Integer> maxReservable;
    @XmlElement(name = "MinQuantity")
    protected int minQuantity;
    @XmlElement(name = "ImageManagerId")
    protected long imageManagerId;

    /**
     * Gets the value of the resourceId property.
     * 
     */
    public long getResourceId() {
        return resourceId;
    }

    /**
     * Sets the value of the resourceId property.
     * 
     */
    public void setResourceId(long value) {
        this.resourceId = value;
    }

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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
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
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setName(JAXBElement<String> value) {
        this.name = ((JAXBElement<String> ) value);
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
     * Gets the value of the maxQuantity property.
     * 
     */
    public int getMaxQuantity() {
        return maxQuantity;
    }

    /**
     * Sets the value of the maxQuantity property.
     * 
     */
    public void setMaxQuantity(int value) {
        this.maxQuantity = value;
    }

    /**
     * Gets the value of the maxReservable property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getMaxReservable() {
        return maxReservable;
    }

    /**
     * Sets the value of the maxReservable property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setMaxReservable(JAXBElement<Integer> value) {
        this.maxReservable = ((JAXBElement<Integer> ) value);
    }

    /**
     * Gets the value of the minQuantity property.
     * 
     */
    public int getMinQuantity() {
        return minQuantity;
    }

    /**
     * Sets the value of the minQuantity property.
     * 
     */
    public void setMinQuantity(int value) {
        this.minQuantity = value;
    }

    /**
     * Gets the value of the imageManagerId property.
     * 
     */
    public long getImageManagerId() {
        return imageManagerId;
    }

    /**
     * Sets the value of the imageManagerId property.
     * 
     */
    public void setImageManagerId(long value) {
        this.imageManagerId = value;
    }

}
