
package net.cbtltd.rest.maxxton;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Image complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Image">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ImageId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ImageType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ImageData" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="MimeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Filename" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ImagemanagerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="DefaultImage" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ImageUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Image", propOrder = {
    "imageId",
    "imageType",
    "imageData",
    "mimeType",
    "filename",
    "imagemanagerId",
    "defaultImage",
    "imageUrl"
})
public class Image {

    @XmlElement(name = "ImageId")
    protected long imageId;
    @XmlElement(name = "ImageType", required = true)
    protected String imageType;
    @XmlElement(name = "ImageData", required = true)
    protected byte[] imageData;
    @XmlElement(name = "MimeType", required = true)
    protected String mimeType;
    @XmlElement(name = "Filename", required = true)
    protected String filename;
    @XmlElement(name = "ImagemanagerId")
    protected long imagemanagerId;
    @XmlElement(name = "DefaultImage")
    protected boolean defaultImage;
    @XmlElement(name = "ImageUrl")
    protected String imageUrl;

    /**
     * Gets the value of the imageId property.
     * 
     */
    public long getImageId() {
        return imageId;
    }

    /**
     * Sets the value of the imageId property.
     * 
     */
    public void setImageId(long value) {
        this.imageId = value;
    }

    /**
     * Gets the value of the imageType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImageType() {
        return imageType;
    }

    /**
     * Sets the value of the imageType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImageType(String value) {
        this.imageType = value;
    }

    /**
     * Gets the value of the imageData property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getImageData() {
        return imageData;
    }

    /**
     * Sets the value of the imageData property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setImageData(byte[] value) {
        this.imageData = ((byte[]) value);
    }

    /**
     * Gets the value of the mimeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Sets the value of the mimeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMimeType(String value) {
        this.mimeType = value;
    }

    /**
     * Gets the value of the filename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets the value of the filename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilename(String value) {
        this.filename = value;
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
     * Gets the value of the defaultImage property.
     * 
     */
    public boolean isDefaultImage() {
        return defaultImage;
    }

    /**
     * Sets the value of the defaultImage property.
     * 
     */
    public void setDefaultImage(boolean value) {
        this.defaultImage = value;
    }

    /**
     * Gets the value of the imageUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the value of the imageUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImageUrl(String value) {
        this.imageUrl = value;
    }

}
