
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
 *         &lt;element name="GetReservationsResult" type="{http://xml.ciirus.com/}ArrayOfReservations" minOccurs="0"/>
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
    "getReservationsResult"
})
@XmlRootElement(name = "GetReservationsResponse")
public class GetReservationsResponse {

    @XmlElement(name = "GetReservationsResult")
    protected ArrayOfReservations getReservationsResult;

    /**
     * Gets the value of the getReservationsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfReservations }
     *     
     */
    public ArrayOfReservations getGetReservationsResult() {
        return getReservationsResult;
    }

    /**
     * Sets the value of the getReservationsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfReservations }
     *     
     */
    public void setGetReservationsResult(ArrayOfReservations value) {
        this.getReservationsResult = value;
    }

}
