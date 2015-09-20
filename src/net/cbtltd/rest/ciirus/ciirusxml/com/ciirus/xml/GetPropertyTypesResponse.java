
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
 *         &lt;element name="GetPropertyTypesResult" type="{http://xml.ciirus.com/}ArrayOfPropertyTypes" minOccurs="0"/>
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
    "getPropertyTypesResult"
})
@XmlRootElement(name = "GetPropertyTypesResponse")
public class GetPropertyTypesResponse {

    @XmlElement(name = "GetPropertyTypesResult")
    protected ArrayOfPropertyTypes getPropertyTypesResult;

    /**
     * Gets the value of the getPropertyTypesResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPropertyTypes }
     *     
     */
    public ArrayOfPropertyTypes getGetPropertyTypesResult() {
        return getPropertyTypesResult;
    }

    /**
     * Sets the value of the getPropertyTypesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPropertyTypes }
     *     
     */
    public void setGetPropertyTypesResult(ArrayOfPropertyTypes value) {
        this.getPropertyTypesResult = value;
    }

}
