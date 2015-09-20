
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReservationSubjectContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReservationSubjectContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReservationSubjects" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ReservationSubjectItem" type="{http://service.newyse.ws.services.newyse.maxxton/}WSReservationSubject" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "ReservationSubjectContainer", propOrder = {
    "reservationSubjects"
})
public class ReservationSubjectContainer {

    @XmlElement(name = "ReservationSubjects")
    protected ReservationSubjectContainer.ReservationSubjects reservationSubjects;

    /**
     * Gets the value of the reservationSubjects property.
     * 
     * @return
     *     possible object is
     *     {@link ReservationSubjectContainer.ReservationSubjects }
     *     
     */
    public ReservationSubjectContainer.ReservationSubjects getReservationSubjects() {
        return reservationSubjects;
    }

    /**
     * Sets the value of the reservationSubjects property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReservationSubjectContainer.ReservationSubjects }
     *     
     */
    public void setReservationSubjects(ReservationSubjectContainer.ReservationSubjects value) {
        this.reservationSubjects = value;
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
     *         &lt;element name="ReservationSubjectItem" type="{http://service.newyse.ws.services.newyse.maxxton/}WSReservationSubject" maxOccurs="unbounded" minOccurs="0"/>
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
        "reservationSubjectItem"
    })
    public static class ReservationSubjects {

        @XmlElement(name = "ReservationSubjectItem")
        protected List<WSReservationSubject> reservationSubjectItem;

        /**
         * Gets the value of the reservationSubjectItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the reservationSubjectItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getReservationSubjectItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link WSReservationSubject }
         * 
         * 
         */
        public List<WSReservationSubject> getReservationSubjectItem() {
            if (reservationSubjectItem == null) {
                reservationSubjectItem = new ArrayList<WSReservationSubject>();
            }
            return this.reservationSubjectItem;
        }

    }

}
