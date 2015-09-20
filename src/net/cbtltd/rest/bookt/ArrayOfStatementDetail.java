
package net.cbtltd.rest.bookt;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfStatementDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfStatementDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StatementDetail" type="{http://connect.bookt.com/Schemas/StatementDetail.xsd}StatementDetail" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfStatementDetail", namespace = "http://connect.bookt.com/Schemas/StatementDetail.xsd", propOrder = {
    "statementDetail"
})
public class ArrayOfStatementDetail {

    @XmlElement(name = "StatementDetail", nillable = true)
    protected List<StatementDetail> statementDetail;

    /**
     * Gets the value of the statementDetail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the statementDetail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStatementDetail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StatementDetail }
     * 
     * 
     */
    public List<StatementDetail> getStatementDetail() {
        if (statementDetail == null) {
            statementDetail = new ArrayList<StatementDetail>();
        }
        return this.statementDetail;
    }

}
