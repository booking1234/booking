
package net.cbtltd.rest.barefoot.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfMinimumDayRule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfMinimumDayRule">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MinimumDayRule" type="{http://www.barefoot.com/Services/}MinimumDayRule" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfMinimumDayRule", propOrder = {
    "minimumDayRules"
})
public class ArrayOfMinimumDayRule {

    @XmlElement(name = "MinimumDayRule", nillable = true)
    protected List<MinimumDayRule> minimumDayRules;

    /**
     * Gets the value of the minimumDayRules property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the minimumDayRules property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMinimumDayRules().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MinimumDayRule }
     * 
     * 
     */
    public List<MinimumDayRule> getMinimumDayRules() {
        if (minimumDayRules == null) {
            minimumDayRules = new ArrayList<MinimumDayRule>();
        }
        return this.minimumDayRules;
    }

}
