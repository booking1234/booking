//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.12.05 at 09:44:01 AM IST 
//


package net.cbtltd.rest.nextpax;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "title",
    "sequence",
    "url"
})
@XmlRootElement(name = "Image")
public class Image {

    @XmlAttribute
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String imagetype;
    @XmlAttribute
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String summer;
    @XmlAttribute
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String winter;
    @XmlElement(name = "Title")
    protected String title;
    @XmlElement(name = "Sequence")
    protected Sequence sequence;
    @XmlElement(name = "Url", required = true)
    protected String url;

    /**
     * Gets the value of the imagetype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImagetype() {
        return imagetype;
    }

    /**
     * Sets the value of the imagetype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImagetype(String value) {
        this.imagetype = value;
    }

    /**
     * Gets the value of the summer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSummer() {
        return summer;
    }

    /**
     * Sets the value of the summer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSummer(String value) {
        this.summer = value;
    }

    /**
     * Gets the value of the winter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWinter() {
        return winter;
    }

    /**
     * Sets the value of the winter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWinter(String value) {
        this.winter = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the sequence property.
     * 
     * @return
     *     possible object is
     *     {@link Sequence }
     *     
     */
    public Sequence getSequence() {
        return sequence;
    }

    /**
     * Sets the value of the sequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sequence }
     *     
     */
    public void setSequence(Sequence value) {
        this.sequence = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

}
