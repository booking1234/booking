
package net.cbtltd.rest.leaderstay;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for returnpropertiesInfoList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="returnpropertiesInfoList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="String" type="{urn:leaderstay}propertiesInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "returnpropertiesInfoList", propOrder = {
    "string"
})
public class ReturnpropertiesInfoList {

    @XmlElement(name = "String")
    protected List<PropertiesInfo> string;

    /**
     * Gets the value of the string property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the string property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getString().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropertiesInfo }
     * 
     * 
     */
    public List<PropertiesInfo> getString() {
        if (string == null) {
            string = new ArrayList<PropertiesInfo>();
        }
        return this.string;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReturnpropertiesInfoList [string=");
		builder.append(string);
		builder.append("]");
		return builder.toString();
	}

}
