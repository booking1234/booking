
package net.cbtltd.rest.rci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resort-guide-only-internal-info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resort-guide-only-internal-info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resortNotes" type="{}resort-notes"/>
 *         &lt;element name="filtersForResortDirectory" type="{}filters-for-resort-directory" minOccurs="0"/>
 *         &lt;element name="resortServicing" type="{}resort-servicing-info"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resort-guide-only-internal-info", propOrder = {
    "resortNotes",
    "filtersForResortDirectory",
    "resortServicing"
})
public class ResortGuideOnlyInternalInfo {

    @XmlElement(required = true)
    protected ResortNotes resortNotes;
    protected FiltersForResortDirectory filtersForResortDirectory;
    @XmlElement(required = true)
    protected ResortServicingInfo resortServicing;

    /**
     * Gets the value of the resortNotes property.
     * 
     * @return
     *     possible object is
     *     {@link ResortNotes }
     *     
     */
    public ResortNotes getResortNotes() {
        return resortNotes;
    }

    /**
     * Sets the value of the resortNotes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortNotes }
     *     
     */
    public void setResortNotes(ResortNotes value) {
        this.resortNotes = value;
    }

    /**
     * Gets the value of the filtersForResortDirectory property.
     * 
     * @return
     *     possible object is
     *     {@link FiltersForResortDirectory }
     *     
     */
    public FiltersForResortDirectory getFiltersForResortDirectory() {
        return filtersForResortDirectory;
    }

    /**
     * Sets the value of the filtersForResortDirectory property.
     * 
     * @param value
     *     allowed object is
     *     {@link FiltersForResortDirectory }
     *     
     */
    public void setFiltersForResortDirectory(FiltersForResortDirectory value) {
        this.filtersForResortDirectory = value;
    }

    /**
     * Gets the value of the resortServicing property.
     * 
     * @return
     *     possible object is
     *     {@link ResortServicingInfo }
     *     
     */
    public ResortServicingInfo getResortServicing() {
        return resortServicing;
    }

    /**
     * Sets the value of the resortServicing property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortServicingInfo }
     *     
     */
    public void setResortServicing(ResortServicingInfo value) {
        this.resortServicing = value;
    }

}
