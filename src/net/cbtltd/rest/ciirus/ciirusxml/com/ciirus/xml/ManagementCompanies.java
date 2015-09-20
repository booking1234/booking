
package net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ManagementCompanies complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManagementCompanies">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MCUserID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MCCompanyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManagementCompanies", propOrder = {
    "mcUserID",
    "mcCompanyName"
})
public class ManagementCompanies {

    @XmlElement(name = "MCUserID")
    protected int mcUserID;
    @XmlElement(name = "MCCompanyName")
    protected String mcCompanyName;

    /**
     * Gets the value of the mcUserID property.
     * 
     */
    public int getMCUserID() {
        return mcUserID;
    }

    /**
     * Sets the value of the mcUserID property.
     * 
     */
    public void setMCUserID(int value) {
        this.mcUserID = value;
    }

    /**
     * Gets the value of the mcCompanyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMCCompanyName() {
        return mcCompanyName;
    }

    /**
     * Sets the value of the mcCompanyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMCCompanyName(String value) {
        this.mcCompanyName = value;
    }

}
