
package net.cbtltd.rest.maxxton;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ImageCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImageCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ImageManagerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="OnlyImageUrl" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImageCriteria", propOrder = {
    "imageManagerId",
    "onlyImageUrl"
})
public class ImageCriteria {

    @XmlElement(name = "ImageManagerId")
    protected long imageManagerId;
    @XmlElementRef(name = "OnlyImageUrl", type = JAXBElement.class)
    protected JAXBElement<Boolean> onlyImageUrl;

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

    /**
     * Gets the value of the onlyImageUrl property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getOnlyImageUrl() {
        return onlyImageUrl;
    }

    /**
     * Sets the value of the onlyImageUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setOnlyImageUrl(JAXBElement<Boolean> value) {
        this.onlyImageUrl = ((JAXBElement<Boolean> ) value);
    }

}
