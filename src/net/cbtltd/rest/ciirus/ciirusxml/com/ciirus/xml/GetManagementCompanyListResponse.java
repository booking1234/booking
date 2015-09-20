
package net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="GetManagementCompanyListResult" type="{http://xml.ciirus.com/}ArrayOfManagementCompanies" minOccurs="0"/>
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
    "getManagementCompanyListResult"
})
@XmlRootElement(name = "GetManagementCompanyListResponse")
public class GetManagementCompanyListResponse {

    @XmlElement(name = "GetManagementCompanyListResult")
    protected ArrayOfManagementCompanies getManagementCompanyListResult;

    /**
     * Gets the value of the getManagementCompanyListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfManagementCompanies }
     *     
     */
    public ArrayOfManagementCompanies getGetManagementCompanyListResult() {
        return getManagementCompanyListResult;
    }

    /**
     * Sets the value of the getManagementCompanyListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfManagementCompanies }
     *     
     */
    public void setGetManagementCompanyListResult(ArrayOfManagementCompanies value) {
        this.getManagementCompanyListResult = value;
    }

}
