
package net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetExtrasResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetExtrasResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RowCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Extras" type="{http://xml.ciirus.com/}ArrayOfPropertyExtras" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetExtrasResult", propOrder = {
    "rowCount",
    "extras"
})
public class GetExtrasResult {

    @XmlElement(name = "RowCount")
    protected int rowCount;
    @XmlElement(name = "Extras")
    protected ArrayOfPropertyExtras extras;

    /**
     * Gets the value of the rowCount property.
     * 
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Sets the value of the rowCount property.
     * 
     */
    public void setRowCount(int value) {
        this.rowCount = value;
    }

    /**
     * Gets the value of the extras property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPropertyExtras }
     *     
     */
    public ArrayOfPropertyExtras getExtras() {
        return extras;
    }

    /**
     * Sets the value of the extras property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPropertyExtras }
     *     
     */
    public void setExtras(ArrayOfPropertyExtras value) {
        this.extras = value;
    }

}
