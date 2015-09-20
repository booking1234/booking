
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WorkOrderItemContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WorkOrderItemContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="WorkOrderItems" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="WorkOrderItem" type="{http://service.newyse.ws.services.newyse.maxxton/}WSWorkOrderItem" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkOrderItemContainer", propOrder = {
    "workOrderItems"
})
public class WorkOrderItemContainer {

    @XmlElement(name = "WorkOrderItems")
    protected WorkOrderItemContainer.WorkOrderItems workOrderItems;

    /**
     * Gets the value of the workOrderItems property.
     * 
     * @return
     *     possible object is
     *     {@link WorkOrderItemContainer.WorkOrderItems }
     *     
     */
    public WorkOrderItemContainer.WorkOrderItems getWorkOrderItems() {
        return workOrderItems;
    }

    /**
     * Sets the value of the workOrderItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkOrderItemContainer.WorkOrderItems }
     *     
     */
    public void setWorkOrderItems(WorkOrderItemContainer.WorkOrderItems value) {
        this.workOrderItems = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="WorkOrderItem" type="{http://service.newyse.ws.services.newyse.maxxton/}WSWorkOrderItem" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "workOrderItem"
    })
    public static class WorkOrderItems {

        @XmlElement(name = "WorkOrderItem")
        protected List<WSWorkOrderItem> workOrderItem;

        /**
         * Gets the value of the workOrderItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the workOrderItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getWorkOrderItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link WSWorkOrderItem }
         * 
         * 
         */
        public List<WSWorkOrderItem> getWorkOrderItem() {
            if (workOrderItem == null) {
                workOrderItem = new ArrayList<WSWorkOrderItem>();
            }
            return this.workOrderItem;
        }

    }

}
