
package net.cbtltd.rest.maxxton;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WSResourceCapacity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WSResourceCapacity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResourceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="SubjectId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SubjectName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MinAge" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="MaxAge" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="MinQuantity" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="MaxQuantity" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSResourceCapacity", propOrder = {
    "resourceId",
    "subjectId",
    "type",
    "subjectName",
    "minAge",
    "maxAge",
    "minQuantity",
    "maxQuantity"
})
public class WSResourceCapacity {

    @XmlElement(name = "ResourceId")
    protected long resourceId;
    @XmlElement(name = "SubjectId")
    protected long subjectId;
    @XmlElement(name = "Type")
    protected String type;
    @XmlElement(name = "SubjectName")
    protected String subjectName;
    @XmlElement(name = "MinAge")
    protected long minAge;
    @XmlElement(name = "MaxAge")
    protected long maxAge;
    @XmlElement(name = "MinQuantity")
    protected long minQuantity;
    @XmlElement(name = "MaxQuantity")
    protected long maxQuantity;

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
     * Gets the value of the subjectId property.
     * 
     */
    public long getSubjectId() {
        return subjectId;
    }

    /**
     * Sets the value of the subjectId property.
     * 
     */
    public void setSubjectId(long value) {
        this.subjectId = value;
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
     * Gets the value of the subjectName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Sets the value of the subjectName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubjectName(String value) {
        this.subjectName = value;
    }

    /**
     * Gets the value of the minAge property.
     * 
     */
    public long getMinAge() {
        return minAge;
    }

    /**
     * Sets the value of the minAge property.
     * 
     */
    public void setMinAge(long value) {
        this.minAge = value;
    }

    /**
     * Gets the value of the maxAge property.
     * 
     */
    public long getMaxAge() {
        return maxAge;
    }

    /**
     * Sets the value of the maxAge property.
     * 
     */
    public void setMaxAge(long value) {
        this.maxAge = value;
    }

    /**
     * Gets the value of the minQuantity property.
     * 
     */
    public long getMinQuantity() {
        return minQuantity;
    }

    /**
     * Sets the value of the minQuantity property.
     * 
     */
    public void setMinQuantity(long value) {
        this.minQuantity = value;
    }

    /**
     * Gets the value of the maxQuantity property.
     * 
     */
    public long getMaxQuantity() {
        return maxQuantity;
    }

    /**
     * Sets the value of the maxQuantity property.
     * 
     */
    public void setMaxQuantity(long value) {
        this.maxQuantity = value;
    }

}
