
package net.cbtltd.rest.rci;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resort-unit-info-feed-entry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resort-unit-info-feed-entry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resortId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="languageCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="value">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="unitInfo" type="{}unit-info" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resort-unit-info-feed-entry", propOrder = {
    "resortId",
    "languageCode",
    "value"
})
public class ResortUnitInfoFeedEntry {

    @XmlElement(required = true)
    protected String resortId;
    @XmlElement(required = true)
    protected String languageCode;
    @XmlElement(required = true)
    protected ResortUnitInfoFeedEntry.Value value;

    /**
     * Gets the value of the resortId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResortId() {
        return resortId;
    }

    /**
     * Sets the value of the resortId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResortId(String value) {
        this.resortId = value;
    }

    /**
     * Gets the value of the languageCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the value of the languageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguageCode(String value) {
        this.languageCode = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link ResortUnitInfoFeedEntry.Value }
     *     
     */
    public ResortUnitInfoFeedEntry.Value getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortUnitInfoFeedEntry.Value }
     *     
     */
    public void setValue(ResortUnitInfoFeedEntry.Value value) {
        this.value = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="unitInfo" type="{}unit-info" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "unitInfo"
    })
    public static class Value {

        @XmlElement(required = true)
        protected List<UnitInfo> unitInfo;

        /**
         * Gets the value of the unitInfo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the unitInfo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getUnitInfo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link UnitInfo }
         * 
         * 
         */
        public List<UnitInfo> getUnitInfo() {
            if (unitInfo == null) {
                unitInfo = new ArrayList<UnitInfo>();
            }
            return this.unitInfo;
        }

    }

}
