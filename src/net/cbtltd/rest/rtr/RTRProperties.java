
package net.cbtltd.rest.rtr;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


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
 *         &lt;element name="Property" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}PropertyManager" minOccurs="0"/>
 *                   &lt;element ref="{}PropertyDetails" minOccurs="0"/>
 *                   &lt;element name="AvailabilityInfo" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Availability" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="Status" use="required">
 *                                       &lt;simpleType>
 *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                           &lt;enumeration value="Available"/>
 *                                           &lt;enumeration value="Unavailable"/>
 *                                         &lt;/restriction>
 *                                       &lt;/simpleType>
 *                                     &lt;/attribute>
 *                                     &lt;attribute ref="{}CheckInDate use="required""/>
 *                                     &lt;attribute ref="{}CheckOutDate use="required""/>
 *                                     &lt;attribute name="AverageRate">
 *                                       &lt;simpleType>
 *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                           &lt;fractionDigits value="2"/>
 *                                         &lt;/restriction>
 *                                       &lt;/simpleType>
 *                                     &lt;/attribute>
 *                                     &lt;attribute name="MinimumRate">
 *                                       &lt;simpleType>
 *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                           &lt;fractionDigits value="2"/>
 *                                         &lt;/restriction>
 *                                       &lt;/simpleType>
 *                                     &lt;/attribute>
 *                                     &lt;attribute name="MaximumRate">
 *                                       &lt;simpleType>
 *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                           &lt;fractionDigits value="2"/>
 *                                         &lt;/restriction>
 *                                       &lt;/simpleType>
 *                                     &lt;/attribute>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="RateInfo" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Rate" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="Description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="Rules" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="Rate" use="required">
 *                                       &lt;simpleType>
 *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                           &lt;fractionDigits value="2"/>
 *                                         &lt;/restriction>
 *                                       &lt;/simpleType>
 *                                     &lt;/attribute>
 *                                     &lt;attribute ref="{}CheckInDate use="required""/>
 *                                     &lt;attribute ref="{}CheckOutDate use="required""/>
 *                                     &lt;attribute name="MinimumStay" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                                     &lt;attribute name="DailyRate">
 *                                       &lt;simpleType>
 *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                           &lt;fractionDigits value="2"/>
 *                                         &lt;/restriction>
 *                                       &lt;/simpleType>
 *                                     &lt;/attribute>
 *                                     &lt;attribute name="CheckInDay" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element ref="{}Lease" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="ReferenceID" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                 &lt;attribute name="PropertyID" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                 &lt;attribute name="ExternalReferenceID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="PropertyManagerID" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                 &lt;attribute name="PropertyManagerName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Status" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="Version" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *       &lt;attribute name="noNamespaceSchemaLocation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="TimeStamp" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "property"
})
@XmlRootElement(name = "RTRProperties", namespace="")
@XStreamAlias("RTRProperties")
public class RTRProperties {

    @XmlElement(name = "Property", required = true)
    @XStreamImplicit
    protected List<RTRProperties.Property> property;
    @XmlAttribute(name = "Version", required = true)
    protected BigDecimal version;
    @XmlAttribute(name = "noNamespaceSchemaLocation")
    protected String noNamespaceSchemaLocation;
    @XmlAttribute(name = "TimeStamp", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timeStamp;

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RTRProperties [property=");
		builder.append(property);
		builder.append(", version=");
		builder.append(version);
		builder.append(", noNamespaceSchemaLocation=");
		builder.append(noNamespaceSchemaLocation);
		builder.append(", timeStamp=");
		builder.append(timeStamp);
		builder.append("]");
		return builder.toString();
	}

	/**
     * Gets the value of the property property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the property property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTRProperties.Property }
     * 
     * 
     */
    public List<RTRProperties.Property> getProperty() {
        if (property == null) {
            property = new ArrayList<RTRProperties.Property>();
        }
        return this.property;
    }

    public void setProperty(List<RTRProperties.Property> property) {
        this.property = property;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVersion(BigDecimal value) {
        this.version = value;
    }

    /**
     * Gets the value of the noNamespaceSchemaLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoNamespaceSchemaLocation() {
        return noNamespaceSchemaLocation;
    }

    /**
     * Sets the value of the noNamespaceSchemaLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoNamespaceSchemaLocation(String value) {
        this.noNamespaceSchemaLocation = value;
    }

    /**
     * Gets the value of the timeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets the value of the timeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimeStamp(XMLGregorianCalendar value) {
        this.timeStamp = value;
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
     *         &lt;element ref="{}PropertyManager" minOccurs="0"/>
     *         &lt;element ref="{}PropertyDetails" minOccurs="0"/>
     *         &lt;element name="AvailabilityInfo" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Availability" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="Status" use="required">
     *                             &lt;simpleType>
     *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                 &lt;enumeration value="Available"/>
     *                                 &lt;enumeration value="Unavailable"/>
     *                               &lt;/restriction>
     *                             &lt;/simpleType>
     *                           &lt;/attribute>
     *                           &lt;attribute ref="{}CheckInDate use="required""/>
     *                           &lt;attribute ref="{}CheckOutDate use="required""/>
     *                           &lt;attribute name="AverageRate">
     *                             &lt;simpleType>
     *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                 &lt;fractionDigits value="2"/>
     *                               &lt;/restriction>
     *                             &lt;/simpleType>
     *                           &lt;/attribute>
     *                           &lt;attribute name="MinimumRate">
     *                             &lt;simpleType>
     *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                 &lt;fractionDigits value="2"/>
     *                               &lt;/restriction>
     *                             &lt;/simpleType>
     *                           &lt;/attribute>
     *                           &lt;attribute name="MaximumRate">
     *                             &lt;simpleType>
     *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                 &lt;fractionDigits value="2"/>
     *                               &lt;/restriction>
     *                             &lt;/simpleType>
     *                           &lt;/attribute>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="RateInfo" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Rate" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="Description" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="Rules" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="Rate" use="required">
     *                             &lt;simpleType>
     *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                 &lt;fractionDigits value="2"/>
     *                               &lt;/restriction>
     *                             &lt;/simpleType>
     *                           &lt;/attribute>
     *                           &lt;attribute ref="{}CheckInDate use="required""/>
     *                           &lt;attribute ref="{}CheckOutDate use="required""/>
     *                           &lt;attribute name="MinimumStay" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *                           &lt;attribute name="DailyRate">
     *                             &lt;simpleType>
     *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                 &lt;fractionDigits value="2"/>
     *                               &lt;/restriction>
     *                             &lt;/simpleType>
     *                           &lt;/attribute>
     *                           &lt;attribute name="CheckInDay" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element ref="{}Lease" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="ReferenceID" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="PropertyID" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="ExternalReferenceID" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="PropertyManagerID" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="PropertyManagerName" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="Status" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Property", namespace="", propOrder = {
        "propertyManager",
        "propertyDetails",
        "availabilityInfo",
        "rateInfo",
        "lease"
    })
    @XStreamAlias("Property")
    public static class Property {

        @XmlElement(name = "PropertyManager")
        @XStreamAlias("PropertyManager")
        protected PropertyManager propertyManager;
        @XmlElement(name = "PropertyDetails")
        @XStreamAlias("PropertyDetails")
        protected PropertyDetails propertyDetails;
        @XmlElement(name = "AvailabilityInfo")
        @XStreamAlias("AvailabilityInfo")
        protected RTRProperties.Property.AvailabilityInfo availabilityInfo;
        @XmlElement(name = "RateInfo")
        @XStreamAlias("RateInfo")
        protected RTRProperties.Property.RateInfo rateInfo;
        @XmlElement(name = "Lease")
        @XStreamAlias("Lease")
        protected Lease lease;
        @XmlAttribute(name = "ReferenceID")
        protected BigInteger referenceID;
        @XmlAttribute(name = "PropertyID")
        protected BigInteger propertyID;
        @XmlAttribute(name = "ExternalReferenceID")
        protected String externalReferenceID;
        @XmlAttribute(name = "PropertyManagerID")
        protected BigInteger propertyManagerID;
        @XmlAttribute(name = "PropertyManagerName")
        protected String propertyManagerName;
        @XmlAttribute(name = "Status")
        protected String status;

        @Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Property [propertyManager=");
			builder.append(propertyManager);
			builder.append(", propertyDetails=");
			builder.append(propertyDetails);
			builder.append(", availabilityInfo=");
			builder.append(availabilityInfo);
			builder.append(", rateInfo=");
			builder.append(rateInfo);
			builder.append(", lease=");
			builder.append(lease);
			builder.append(", referenceID=");
			builder.append(referenceID);
			builder.append(", propertyID=");
			builder.append(propertyID);
			builder.append(", externalReferenceID=");
			builder.append(externalReferenceID);
			builder.append(", propertyManagerID=");
			builder.append(propertyManagerID);
			builder.append(", propertyManagerName=");
			builder.append(propertyManagerName);
			builder.append(", status=");
			builder.append(status);
			builder.append("]");
			return builder.toString();
		}

		/**
         * Gets the value of the propertyManager property.
         * 
         * @return
         *     possible object is
         *     {@link PropertyManager }
         *     
         */
        public PropertyManager getPropertyManager() {
            return propertyManager;
        }

        /**
         * Sets the value of the propertyManager property.
         * 
         * @param value
         *     allowed object is
         *     {@link PropertyManager }
         *     
         */
        public void setPropertyManager(PropertyManager value) {
            this.propertyManager = value;
        }

        /**
         * Gets the value of the propertyDetails property.
         * 
         * @return
         *     possible object is
         *     {@link PropertyDetails }
         *     
         */
        public PropertyDetails getPropertyDetails() {
            return propertyDetails;
        }

        /**
         * Sets the value of the propertyDetails property.
         * 
         * @param value
         *     allowed object is
         *     {@link PropertyDetails }
         *     
         */
        public void setPropertyDetails(PropertyDetails value) {
            this.propertyDetails = value;
        }

        /**
         * Gets the value of the availabilityInfo property.
         * 
         * @return
         *     possible object is
         *     {@link RTRProperties.Property.AvailabilityInfo }
         *     
         */
        public RTRProperties.Property.AvailabilityInfo getAvailabilityInfo() {
            return availabilityInfo;
        }

        /**
         * Sets the value of the availabilityInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link RTRProperties.Property.AvailabilityInfo }
         *     
         */
        public void setAvailabilityInfo(RTRProperties.Property.AvailabilityInfo value) {
            this.availabilityInfo = value;
        }

        /**
         * Gets the value of the rateInfo property.
         * 
         * @return
         *     possible object is
         *     {@link RTRProperties.Property.RateInfo }
         *     
         */
        public RTRProperties.Property.RateInfo getRateInfo() {
            return rateInfo;
        }

        /**
         * Sets the value of the rateInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link RTRProperties.Property.RateInfo }
         *     
         */
        public void setRateInfo(RTRProperties.Property.RateInfo value) {
            this.rateInfo = value;
        }

        /**
         * Gets the value of the lease property.
         * 
         * @return
         *     possible object is
         *     {@link Lease }
         *     
         */
        public Lease getLease() {
            return lease;
        }

        /**
         * Sets the value of the lease property.
         * 
         * @param value
         *     allowed object is
         *     {@link Lease }
         *     
         */
        public void setLease(Lease value) {
            this.lease = value;
        }

        /**
         * Gets the value of the referenceID property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getReferenceID() {
            return referenceID;
        }

        /**
         * Sets the value of the referenceID property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setReferenceID(BigInteger value) {
            this.referenceID = value;
        }

        /**
         * Gets the value of the propertyID property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getPropertyID() {
            return propertyID;
        }

        /**
         * Sets the value of the propertyID property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setPropertyID(BigInteger value) {
            this.propertyID = value;
        }

        /**
         * Gets the value of the externalReferenceID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getExternalReferenceID() {
            return externalReferenceID;
        }

        /**
         * Sets the value of the externalReferenceID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setExternalReferenceID(String value) {
            this.externalReferenceID = value;
        }

        /**
         * Gets the value of the propertyManagerID property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getPropertyManagerID() {
            return propertyManagerID;
        }

        /**
         * Sets the value of the propertyManagerID property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setPropertyManagerID(BigInteger value) {
            this.propertyManagerID = value;
        }

        /**
         * Gets the value of the propertyManagerName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPropertyManagerName() {
            return propertyManagerName;
        }

        /**
         * Sets the value of the propertyManagerName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPropertyManagerName(String value) {
            this.propertyManagerName = value;
        }

        /**
         * Gets the value of the status property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatus() {
            return status;
        }

        /**
         * Sets the value of the status property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatus(String value) {
            this.status = value;
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
         *         &lt;element name="Availability" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="Status" use="required">
         *                   &lt;simpleType>
         *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                       &lt;enumeration value="Available"/>
         *                       &lt;enumeration value="Unavailable"/>
         *                     &lt;/restriction>
         *                   &lt;/simpleType>
         *                 &lt;/attribute>
         *                 &lt;attribute ref="{}CheckInDate use="required""/>
         *                 &lt;attribute ref="{}CheckOutDate use="required""/>
         *                 &lt;attribute name="AverageRate">
         *                   &lt;simpleType>
         *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                       &lt;fractionDigits value="2"/>
         *                     &lt;/restriction>
         *                   &lt;/simpleType>
         *                 &lt;/attribute>
         *                 &lt;attribute name="MinimumRate">
         *                   &lt;simpleType>
         *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                       &lt;fractionDigits value="2"/>
         *                     &lt;/restriction>
         *                   &lt;/simpleType>
         *                 &lt;/attribute>
         *                 &lt;attribute name="MaximumRate">
         *                   &lt;simpleType>
         *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                       &lt;fractionDigits value="2"/>
         *                     &lt;/restriction>
         *                   &lt;/simpleType>
         *                 &lt;/attribute>
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
        @XmlType(name = "", propOrder = {
            "availability"
        })
        public static class AvailabilityInfo {

            @XmlElement(name = "Availability")
            @XStreamImplicit
            protected List<RTRProperties.Property.AvailabilityInfo.Availability> availability;

            /**
             * Gets the value of the availability property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the availability property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAvailability().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RTRProperties.Property.AvailabilityInfo.Availability }
             * 
             * 
             */
            public List<RTRProperties.Property.AvailabilityInfo.Availability> getAvailability() {
                if (availability == null) {
                    availability = new ArrayList<RTRProperties.Property.AvailabilityInfo.Availability>();
                }
                return this.availability;
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
             *       &lt;attribute name="Status" use="required">
             *         &lt;simpleType>
             *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *             &lt;enumeration value="Available"/>
             *             &lt;enumeration value="Unavailable"/>
             *           &lt;/restriction>
             *         &lt;/simpleType>
             *       &lt;/attribute>
             *       &lt;attribute ref="{}CheckInDate use="required""/>
             *       &lt;attribute ref="{}CheckOutDate use="required""/>
             *       &lt;attribute name="AverageRate">
             *         &lt;simpleType>
             *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *             &lt;fractionDigits value="2"/>
             *           &lt;/restriction>
             *         &lt;/simpleType>
             *       &lt;/attribute>
             *       &lt;attribute name="MinimumRate">
             *         &lt;simpleType>
             *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *             &lt;fractionDigits value="2"/>
             *           &lt;/restriction>
             *         &lt;/simpleType>
             *       &lt;/attribute>
             *       &lt;attribute name="MaximumRate">
             *         &lt;simpleType>
             *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *             &lt;fractionDigits value="2"/>
             *           &lt;/restriction>
             *         &lt;/simpleType>
             *       &lt;/attribute>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            @XStreamAlias("Availability")
            public static class Availability {

                @XmlAttribute(name = "Status", required = true)
                protected String status;
                @XmlAttribute(name = "CheckInDate", required = true)
                @XmlSchemaType(name = "date")
                protected XMLGregorianCalendar checkInDate;
                @XmlAttribute(name = "CheckOutDate", required = true)
                @XmlSchemaType(name = "date")
                protected XMLGregorianCalendar checkOutDate;
                @XmlAttribute(name = "AverageRate")
                protected BigDecimal averageRate;
                @XmlAttribute(name = "MinimumRate")
                protected BigDecimal minimumRate;
                @XmlAttribute(name = "MaximumRate")
                protected BigDecimal maximumRate;

                /**
                 * Gets the value of the status property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getStatus() {
                    return status;
                }

                /**
                 * Sets the value of the status property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setStatus(String value) {
                    this.status = value;
                }

                /**
                 * Gets the value of the checkInDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getCheckInDate() {
                    return checkInDate;
                }

                /**
                 * Sets the value of the checkInDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setCheckInDate(XMLGregorianCalendar value) {
                    this.checkInDate = value;
                }

                /**
                 * Gets the value of the checkOutDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getCheckOutDate() {
                    return checkOutDate;
                }

                /**
                 * Sets the value of the checkOutDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setCheckOutDate(XMLGregorianCalendar value) {
                    this.checkOutDate = value;
                }

                /**
                 * Gets the value of the averageRate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getAverageRate() {
                    return averageRate;
                }

                /**
                 * Sets the value of the averageRate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setAverageRate(BigDecimal value) {
                    this.averageRate = value;
                }

                /**
                 * Gets the value of the minimumRate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getMinimumRate() {
                    return minimumRate;
                }

                /**
                 * Sets the value of the minimumRate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setMinimumRate(BigDecimal value) {
                    this.minimumRate = value;
                }

                /**
                 * Gets the value of the maximumRate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getMaximumRate() {
                    return maximumRate;
                }

                /**
                 * Sets the value of the maximumRate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setMaximumRate(BigDecimal value) {
                    this.maximumRate = value;
                }

            }

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
         *         &lt;element name="Rate" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="Description" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="Rules" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="Rate" use="required">
         *                   &lt;simpleType>
         *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                       &lt;fractionDigits value="2"/>
         *                     &lt;/restriction>
         *                   &lt;/simpleType>
         *                 &lt;/attribute>
         *                 &lt;attribute ref="{}CheckInDate use="required""/>
         *                 &lt;attribute ref="{}CheckOutDate use="required""/>
         *                 &lt;attribute name="MinimumStay" type="{http://www.w3.org/2001/XMLSchema}integer" />
         *                 &lt;attribute name="DailyRate">
         *                   &lt;simpleType>
         *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                       &lt;fractionDigits value="2"/>
         *                     &lt;/restriction>
         *                   &lt;/simpleType>
         *                 &lt;/attribute>
         *                 &lt;attribute name="CheckInDay" type="{http://www.w3.org/2001/XMLSchema}string" />
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
        @XmlType(name = "", propOrder = {
            "rate"
        })
        @XStreamAlias("RateInfo")
        public static class RateInfo {

            @XmlElement(name = "Rate")
            @XStreamImplicit
            protected List<RTRProperties.Property.RateInfo.Rate> rate;

            /**
             * Gets the value of the rate property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the rate property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getRate().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RTRProperties.Property.RateInfo.Rate }
             * 
             * 
             */
            public List<RTRProperties.Property.RateInfo.Rate> getRate() {
                if (rate == null) {
                    rate = new ArrayList<RTRProperties.Property.RateInfo.Rate>();
                }
                return this.rate;
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
             *       &lt;attribute name="Description" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="Rules" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="Rate" use="required">
             *         &lt;simpleType>
             *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *             &lt;fractionDigits value="2"/>
             *           &lt;/restriction>
             *         &lt;/simpleType>
             *       &lt;/attribute>
             *       &lt;attribute ref="{}CheckInDate use="required""/>
             *       &lt;attribute ref="{}CheckOutDate use="required""/>
             *       &lt;attribute name="MinimumStay" type="{http://www.w3.org/2001/XMLSchema}integer" />
             *       &lt;attribute name="DailyRate">
             *         &lt;simpleType>
             *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *             &lt;fractionDigits value="2"/>
             *           &lt;/restriction>
             *         &lt;/simpleType>
             *       &lt;/attribute>
             *       &lt;attribute name="CheckInDay" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            @XStreamAlias("Rate")
            public static class Rate {

                @XmlAttribute(name = "Description")
                protected String description;
                @XmlAttribute(name = "Rules")
                protected String rules;
                @XmlAttribute(name = "Rate", required = true)
                protected BigDecimal rate;
                @XmlAttribute(name = "CheckInDate", required = true)
                @XmlSchemaType(name = "date")
                protected XMLGregorianCalendar checkInDate;
                @XmlAttribute(name = "CheckOutDate", required = true)
                @XmlSchemaType(name = "date")
                protected XMLGregorianCalendar checkOutDate;
                @XmlAttribute(name = "MinimumStay")
                protected BigInteger minimumStay;
                @XmlAttribute(name = "DailyRate")
                protected BigDecimal dailyRate;
                @XmlAttribute(name = "CheckInDay")
                protected String checkInDay;

                /**
                 * Gets the value of the description property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDescription() {
                    return description;
                }

                /**
                 * Sets the value of the description property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDescription(String value) {
                    this.description = value;
                }

                /**
                 * Gets the value of the rules property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getRules() {
                    return rules;
                }

                /**
                 * Sets the value of the rules property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setRules(String value) {
                    this.rules = value;
                }

                /**
                 * Gets the value of the rate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getRate() {
                    return rate;
                }

                /**
                 * Sets the value of the rate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setRate(BigDecimal value) {
                    this.rate = value;
                }

                /**
                 * Gets the value of the checkInDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getCheckInDate() {
                    return checkInDate;
                }

                /**
                 * Sets the value of the checkInDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setCheckInDate(XMLGregorianCalendar value) {
                    this.checkInDate = value;
                }

                /**
                 * Gets the value of the checkOutDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getCheckOutDate() {
                    return checkOutDate;
                }

                /**
                 * Sets the value of the checkOutDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setCheckOutDate(XMLGregorianCalendar value) {
                    this.checkOutDate = value;
                }

                /**
                 * Gets the value of the minimumStay property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getMinimumStay() {
                    return minimumStay;
                }

                /**
                 * Sets the value of the minimumStay property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setMinimumStay(BigInteger value) {
                    this.minimumStay = value;
                }

                /**
                 * Gets the value of the dailyRate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getDailyRate() {
                    return dailyRate;
                }

                /**
                 * Sets the value of the dailyRate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setDailyRate(BigDecimal value) {
                    this.dailyRate = value;
                }

                /**
                 * Gets the value of the checkInDay property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCheckInDay() {
                    return checkInDay;
                }

                /**
                 * Sets the value of the checkInDay property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCheckInDay(String value) {
                    this.checkInDay = value;
                }

            }

        }

    }

}
