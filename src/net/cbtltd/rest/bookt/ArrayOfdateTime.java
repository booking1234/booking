
package net.cbtltd.rest.bookt;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ArrayOfdateTime complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfdateTime">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfdateTime", namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", propOrder = {
    "dateTime"
})
public class ArrayOfdateTime {

    @XmlSchemaType(name = "dateTime")
    protected List<XMLGregorianCalendar> dateTime;

    /**
     * Gets the value of the dateTime property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dateTime property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDateTime().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XMLGregorianCalendar }
     * 
     * 
     */
    public List<XMLGregorianCalendar> getDateTime() {
        if (dateTime == null) {
            dateTime = new ArrayList<XMLGregorianCalendar>();
        }
        return this.dateTime;
    }

}
