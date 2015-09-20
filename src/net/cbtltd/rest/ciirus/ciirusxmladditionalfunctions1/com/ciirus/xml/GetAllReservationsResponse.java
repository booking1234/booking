
package net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml;

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
 *         &lt;element name="GetAllReservationsResult" type="{http://xml.ciirus.com/}ArrayOfReservation" minOccurs="0"/>
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
    "getAllReservationsResult"
})
@XmlRootElement(name = "GetAllReservationsResponse")
public class GetAllReservationsResponse {

    @XmlElement(name = "GetAllReservationsResult")
    protected ArrayOfReservation getAllReservationsResult;

    /**
     * Gets the value of the getAllReservationsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfReservation }
     *     
     */
    public ArrayOfReservation getGetAllReservationsResult() {
        return getAllReservationsResult;
    }

    /**
     * Sets the value of the getAllReservationsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfReservation }
     *     
     */
    public void setGetAllReservationsResult(ArrayOfReservation value) {
        this.getAllReservationsResult = value;
    }

}
