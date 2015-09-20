
package net.cbtltd.rest.leaderstay;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for areaStructurelist complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="areaStructurelist">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="countryList" type="{urn:leaderstay}countryListT"/>
 *         &lt;element name="regionList" type="{urn:leaderstay}regionListT"/>
 *         &lt;element name="areaList" type="{urn:leaderstay}areaListT"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "areaStructurelist", propOrder = {

})
public class AreaStructurelist {

    @XmlElement(required = true)
    protected CountryListT countryList;
    @XmlElement(required = true)
    protected RegionListT regionList;
    @XmlElement(required = true)
    protected AreaListT areaList;

    /**
     * Gets the value of the countryList property.
     * 
     * @return
     *     possible object is
     *     {@link CountryListT }
     *     
     */
    public CountryListT getCountryList() {
        return countryList;
    }

    /**
     * Sets the value of the countryList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryListT }
     *     
     */
    public void setCountryList(CountryListT value) {
        this.countryList = value;
    }

    /**
     * Gets the value of the regionList property.
     * 
     * @return
     *     possible object is
     *     {@link RegionListT }
     *     
     */
    public RegionListT getRegionList() {
        return regionList;
    }

    /**
     * Sets the value of the regionList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegionListT }
     *     
     */
    public void setRegionList(RegionListT value) {
        this.regionList = value;
    }

    /**
     * Gets the value of the areaList property.
     * 
     * @return
     *     possible object is
     *     {@link AreaListT }
     *     
     */
    public AreaListT getAreaList() {
        return areaList;
    }

    /**
     * Sets the value of the areaList property.
     * 
     * @param value
     *     allowed object is
     *     {@link AreaListT }
     *     
     */
    public void setAreaList(AreaListT value) {
        this.areaList = value;
    }

}
