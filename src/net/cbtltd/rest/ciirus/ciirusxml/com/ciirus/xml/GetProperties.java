
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
 *         &lt;element name="APIUsername" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="APIPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ArriveDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DepartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FilterOptions" type="{http://xml.ciirus.com/}strucSearchFilterOptions"/>
 *         &lt;element name="SearchOptions" type="{http://xml.ciirus.com/}strucSearchOptions"/>
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
    "apiUsername",
    "apiPassword",
    "arriveDate",
    "departDate",
    "filterOptions",
    "searchOptions"
})
@XmlRootElement(name = "GetProperties")
public class GetProperties {

    @XmlElement(name = "APIUsername")
    protected String apiUsername;
    @XmlElement(name = "APIPassword")
    protected String apiPassword;
    @XmlElement(name = "ArriveDate")
    protected String arriveDate;
    @XmlElement(name = "DepartDate")
    protected String departDate;
    @XmlElement(name = "FilterOptions", required = true)
    protected StrucSearchFilterOptions filterOptions;
    @XmlElement(name = "SearchOptions", required = true)
    protected StrucSearchOptions searchOptions;

    /**
     * Gets the value of the apiUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPIUsername() {
        return apiUsername;
    }

    /**
     * Sets the value of the apiUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPIUsername(String value) {
        this.apiUsername = value;
    }

    /**
     * Gets the value of the apiPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPIPassword() {
        return apiPassword;
    }

    /**
     * Sets the value of the apiPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPIPassword(String value) {
        this.apiPassword = value;
    }

    /**
     * Gets the value of the arriveDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArriveDate() {
        return arriveDate;
    }

    /**
     * Sets the value of the arriveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArriveDate(String value) {
        this.arriveDate = value;
    }

    /**
     * Gets the value of the departDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartDate() {
        return departDate;
    }

    /**
     * Sets the value of the departDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartDate(String value) {
        this.departDate = value;
    }

    /**
     * Gets the value of the filterOptions property.
     * 
     * @return
     *     possible object is
     *     {@link StrucSearchFilterOptions }
     *     
     */
    public StrucSearchFilterOptions getFilterOptions() {
        return filterOptions;
    }

    /**
     * Sets the value of the filterOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link StrucSearchFilterOptions }
     *     
     */
    public void setFilterOptions(StrucSearchFilterOptions value) {
        this.filterOptions = value;
    }

    /**
     * Gets the value of the searchOptions property.
     * 
     * @return
     *     possible object is
     *     {@link StrucSearchOptions }
     *     
     */
    public StrucSearchOptions getSearchOptions() {
        return searchOptions;
    }

    /**
     * Sets the value of the searchOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link StrucSearchOptions }
     *     
     */
    public void setSearchOptions(StrucSearchOptions value) {
        this.searchOptions = value;
    }

}
