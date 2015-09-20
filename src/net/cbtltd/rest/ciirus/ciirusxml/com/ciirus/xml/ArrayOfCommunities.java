
package net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfCommunities complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfCommunities">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Communities" type="{http://xml.ciirus.com/}Communities" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCommunities", propOrder = {
    "communities"
})
public class ArrayOfCommunities {

    @XmlElement(name = "Communities")
    protected List<Communities> communities;

    /**
     * Gets the value of the communities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the communities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommunities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Communities }
     * 
     * 
     */
    public List<Communities> getCommunities() {
        if (communities == null) {
            communities = new ArrayList<Communities>();
        }
        return this.communities;
    }

}
