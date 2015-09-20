
package net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfSGuests complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSGuests">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sGuests" type="{http://xml.ciirus.com/}sGuests" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSGuests", propOrder = {
    "sGuests"
})
public class ArrayOfSGuests {

    protected List<SGuests> sGuests;

    /**
     * Gets the value of the sGuests property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sGuests property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSGuests().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SGuests }
     * 
     * 
     */
    public List<SGuests> getSGuests() {
        if (sGuests == null) {
            sGuests = new ArrayList<SGuests>();
        }
        return this.sGuests;
    }

}
