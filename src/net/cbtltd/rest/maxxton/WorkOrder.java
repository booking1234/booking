
package net.cbtltd.rest.maxxton;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WorkOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WorkOrder">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CustomerLastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ObjectNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ResortId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="ObjectId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="WorkOrderCategoryId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="WorkOrderItemId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="AreaId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ShortDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkOrder", propOrder = {
    "customerLastName",
    "objectNumber",
    "resortId",
    "objectId",
    "workOrderCategoryId",
    "workOrderItemId",
    "areaId",
    "shortDescription",
    "description"
})
public class WorkOrder {

    @XmlElement(name = "CustomerLastName", required = true)
    protected String customerLastName;
    @XmlElement(name = "ObjectNumber", required = true)
    protected String objectNumber;
    @XmlElementRef(name = "ResortId", type = JAXBElement.class)
    protected JAXBElement<Long> resortId;
    @XmlElementRef(name = "ObjectId", type = JAXBElement.class)
    protected JAXBElement<Long> objectId;
    @XmlElement(name = "WorkOrderCategoryId")
    protected long workOrderCategoryId;
    @XmlElement(name = "WorkOrderItemId")
    protected long workOrderItemId;
    @XmlElement(name = "AreaId")
    protected long areaId;
    @XmlElementRef(name = "ShortDescription", type = JAXBElement.class)
    protected JAXBElement<String> shortDescription;
    @XmlElementRef(name = "Description", type = JAXBElement.class)
    protected JAXBElement<String> description;

    /**
     * Gets the value of the customerLastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerLastName() {
        return customerLastName;
    }

    /**
     * Sets the value of the customerLastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerLastName(String value) {
        this.customerLastName = value;
    }

    /**
     * Gets the value of the objectNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectNumber() {
        return objectNumber;
    }

    /**
     * Sets the value of the objectNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectNumber(String value) {
        this.objectNumber = value;
    }

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
     * Gets the value of the objectId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public JAXBElement<Long> getObjectId() {
        return objectId;
    }

    /**
     * Sets the value of the objectId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public void setObjectId(JAXBElement<Long> value) {
        this.objectId = ((JAXBElement<Long> ) value);
    }

    /**
     * Gets the value of the workOrderCategoryId property.
     * 
     */
    public long getWorkOrderCategoryId() {
        return workOrderCategoryId;
    }

    /**
     * Sets the value of the workOrderCategoryId property.
     * 
     */
    public void setWorkOrderCategoryId(long value) {
        this.workOrderCategoryId = value;
    }

    /**
     * Gets the value of the workOrderItemId property.
     * 
     */
    public long getWorkOrderItemId() {
        return workOrderItemId;
    }

    /**
     * Sets the value of the workOrderItemId property.
     * 
     */
    public void setWorkOrderItemId(long value) {
        this.workOrderItemId = value;
    }

    /**
     * Gets the value of the areaId property.
     * 
     */
    public long getAreaId() {
        return areaId;
    }

    /**
     * Sets the value of the areaId property.
     * 
     */
    public void setAreaId(long value) {
        this.areaId = value;
    }

    /**
     * Gets the value of the shortDescription property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the value of the shortDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setShortDescription(JAXBElement<String> value) {
        this.shortDescription = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDescription(JAXBElement<String> value) {
        this.description = ((JAXBElement<String> ) value);
    }

}
