
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * Feature value represents a user selected "checkbox" for a particular feature in a feature set.
 * 
 * 
 * <p>Java class for BathroomFeatureValue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BathroomFeatureValue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="description" type="{}Note" minOccurs="0"/>
 *         &lt;element name="bathroomFeatureName" type="{}BathroomFeatureName"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BathroomFeatureValue", propOrder = {
    "count",
    "description",
    "bathroomFeatureName"
})
public class BathroomFeatureValue {

    @XmlElement(defaultValue = "1")
    protected Integer count;
    protected Note description;
    @XmlElement(required = true)
    protected BathroomFeatureName bathroomFeatureName;

    /**
     * Gets the value of the count property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Sets the value of the count property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCount(Integer value) {
        this.count = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setDescription(Note value) {
        this.description = value;
    }

    /**
     * Gets the value of the bathroomFeatureName property.
     * 
     * @return
     *     possible object is
     *     {@link BathroomFeatureName }
     *     
     */
    public BathroomFeatureName getBathroomFeatureName() {
        return bathroomFeatureName;
    }

    /**
     * Sets the value of the bathroomFeatureName property.
     * 
     * @param value
     *     allowed object is
     *     {@link BathroomFeatureName }
     *     
     */
    public void setBathroomFeatureName(BathroomFeatureName value) {
        this.bathroomFeatureName = value;
    }

}
