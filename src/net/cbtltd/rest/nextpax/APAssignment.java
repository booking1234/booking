//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.12.05 at 09:44:01 AM IST 
//


package net.cbtltd.rest.nextpax;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "extrasAssignment",
    "tRentalAssignment"
})
@XmlRootElement(name = "APAssignment")
public class APAssignment {

    @XmlElement(name = "ExtrasAssignment")
    protected List<ExtrasAssignment> extrasAssignment;
    @XmlElement(name = "TRentalAssignment")
    protected List<TRentalAssignment> tRentalAssignment;

    /**
     * Gets the value of the extrasAssignment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extrasAssignment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtrasAssignment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExtrasAssignment }
     * 
     * 
     */
    public List<ExtrasAssignment> getExtrasAssignment() {
        if (extrasAssignment == null) {
            extrasAssignment = new ArrayList<ExtrasAssignment>();
        }
        return this.extrasAssignment;
    }

    /**
     * Gets the value of the tRentalAssignment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tRentalAssignment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTRentalAssignment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TRentalAssignment }
     * 
     * 
     */
    public List<TRentalAssignment> getTRentalAssignment() {
        if (tRentalAssignment == null) {
            tRentalAssignment = new ArrayList<TRentalAssignment>();
        }
        return this.tRentalAssignment;
    }

}
