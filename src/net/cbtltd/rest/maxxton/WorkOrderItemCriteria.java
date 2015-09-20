
package net.cbtltd.rest.maxxton;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WorkOrderItemCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WorkOrderItemCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResortId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="WorkOrderCategoryId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="WorkOrderItemId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkOrderItemCriteria", propOrder = {
    "resortId",
    "workOrderCategoryId",
    "workOrderItemId"
})
public class WorkOrderItemCriteria {

    @XmlElementRef(name = "ResortId", type = JAXBElement.class)
    protected JAXBElement<Long> resortId;
    @XmlElementRef(name = "WorkOrderCategoryId", type = JAXBElement.class)
    protected JAXBElement<Long> workOrderCategoryId;
    @XmlElementRef(name = "WorkOrderItemId", type = JAXBElement.class)
    protected JAXBElement<Long> workOrderItemId;

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

    /**
     * Gets the value of the workOrderItemId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public JAXBElement<Long> getWorkOrderItemId() {
        return workOrderItemId;
    }

    /**
     * Sets the value of the workOrderItemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public void setWorkOrderItemId(JAXBElement<Long> value) {
        this.workOrderItemId = ((JAXBElement<Long> ) value);
    }

}
