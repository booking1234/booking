
package net.cbtltd.rest.maxxton;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WorkOrderCategoryCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WorkOrderCategoryCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResortId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="WorkOrderCategoryId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkOrderCategoryCriteria", propOrder = {
    "resortId",
    "workOrderCategoryId"
})
public class WorkOrderCategoryCriteria {

    @XmlElementRef(name = "ResortId", type = JAXBElement.class)
    protected JAXBElement<Long> resortId;
    @XmlElementRef(name = "WorkOrderCategoryId", type = JAXBElement.class)
    protected JAXBElement<Long> workOrderCategoryId;

    /**
     * Gets the value of the resortId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public JAXBElement<Long> getResortId() {
        return resortId;
    }

    /**
     * Sets the value of the resortId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public void setResortId(JAXBElement<Long> value) {
        this.resortId = ((JAXBElement<Long> ) value);
    }

    /**
     * Gets the value of the workOrderCategoryId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public JAXBElement<Long> getWorkOrderCategoryId() {
        return workOrderCategoryId;
    }

    /**
     * Sets the value of the workOrderCategoryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public void setWorkOrderCategoryId(JAXBElement<Long> value) {
        this.workOrderCategoryId = ((JAXBElement<Long> ) value);
    }

}
