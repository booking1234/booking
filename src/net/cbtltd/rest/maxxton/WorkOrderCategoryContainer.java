
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WorkOrderCategoryContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WorkOrderCategoryContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="WorkOrderCategories" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="WorkOrderCategory" type="{http://service.newyse.ws.services.newyse.maxxton/}WSWorkOrderCategory" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "WorkOrderCategoryContainer", propOrder = {
    "workOrderCategories"
})
public class WorkOrderCategoryContainer {

    @XmlElement(name = "WorkOrderCategories")
    protected WorkOrderCategoryContainer.WorkOrderCategories workOrderCategories;

    /**
     * Gets the value of the workOrderCategories property.
     * 
     * @return
     *     possible object is
     *     {@link WorkOrderCategoryContainer.WorkOrderCategories }
     *     
     */
    public WorkOrderCategoryContainer.WorkOrderCategories getWorkOrderCategories() {
        return workOrderCategories;
    }

    /**
     * Sets the value of the workOrderCategories property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkOrderCategoryContainer.WorkOrderCategories }
     *     
     */
    public void setWorkOrderCategories(WorkOrderCategoryContainer.WorkOrderCategories value) {
        this.workOrderCategories = value;
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
     *         &lt;element name="WorkOrderCategory" type="{http://service.newyse.ws.services.newyse.maxxton/}WSWorkOrderCategory" maxOccurs="unbounded" minOccurs="0"/>
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
        "workOrderCategory"
    })
    public static class WorkOrderCategories {

        @XmlElement(name = "WorkOrderCategory")
        protected List<WSWorkOrderCategory> workOrderCategory;

        /**
         * Gets the value of the workOrderCategory property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the workOrderCategory property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getWorkOrderCategory().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link WSWorkOrderCategory }
         * 
         * 
         */
        public List<WSWorkOrderCategory> getWorkOrderCategory() {
            if (workOrderCategory == null) {
                workOrderCategory = new ArrayList<WSWorkOrderCategory>();
            }
            return this.workOrderCategory;
        }

    }

}
