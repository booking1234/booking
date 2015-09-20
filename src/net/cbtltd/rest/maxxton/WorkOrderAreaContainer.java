
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WorkOrderAreaContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WorkOrderAreaContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="WorkOrderAreas" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="WorkOrderArea" type="{http://service.newyse.ws.services.newyse.maxxton/}WSWorkOrderArea" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "WorkOrderAreaContainer", propOrder = {
    "workOrderAreas"
})
public class WorkOrderAreaContainer {

    @XmlElement(name = "WorkOrderAreas")
    protected WorkOrderAreaContainer.WorkOrderAreas workOrderAreas;

    /**
     * Gets the value of the workOrderAreas property.
     * 
     * @return
     *     possible object is
     *     {@link WorkOrderAreaContainer.WorkOrderAreas }
     *     
     */
    public WorkOrderAreaContainer.WorkOrderAreas getWorkOrderAreas() {
        return workOrderAreas;
    }

    /**
     * Sets the value of the workOrderAreas property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkOrderAreaContainer.WorkOrderAreas }
     *     
     */
    public void setWorkOrderAreas(WorkOrderAreaContainer.WorkOrderAreas value) {
        this.workOrderAreas = value;
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
     *         &lt;element name="WorkOrderArea" type="{http://service.newyse.ws.services.newyse.maxxton/}WSWorkOrderArea" maxOccurs="unbounded" minOccurs="0"/>
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
        "workOrderArea"
    })
    public static class WorkOrderAreas {

        @XmlElement(name = "WorkOrderArea")
        protected List<WSWorkOrderArea> workOrderArea;

        /**
         * Gets the value of the workOrderArea property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the workOrderArea property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getWorkOrderArea().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link WSWorkOrderArea }
         * 
         * 
         */
        public List<WSWorkOrderArea> getWorkOrderArea() {
            if (workOrderArea == null) {
                workOrderArea = new ArrayList<WSWorkOrderArea>();
            }
            return this.workOrderArea;
        }

    }

}
