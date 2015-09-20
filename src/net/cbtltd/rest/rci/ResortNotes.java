
package net.cbtltd.rest.rci;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resort-notes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resort-notes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="strata" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="resortType" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="UUAllowed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resort-notes", propOrder = {
    "strata",
    "resortType",
    "uuAllowed"
})
public class ResortNotes {

    @XmlElement(required = true)
    protected String strata;
    protected List<String> resortType;
    @XmlElement(name = "UUAllowed")
    protected String uuAllowed;

    /**
     * Gets the value of the strata property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrata() {
        return strata;
    }

    /**
     * Sets the value of the strata property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrata(String value) {
        this.strata = value;
    }

    /**
     * Gets the value of the resortType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resortType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResortType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getResortType() {
        if (resortType == null) {
            resortType = new ArrayList<String>();
        }
        return this.resortType;
    }

    /**
     * Gets the value of the uuAllowed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUUAllowed() {
        return uuAllowed;
    }

    /**
     * Sets the value of the uuAllowed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUUAllowed(String value) {
        this.uuAllowed = value;
    }

}
