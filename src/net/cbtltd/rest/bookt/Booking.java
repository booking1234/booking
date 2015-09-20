
package net.cbtltd.rest.bookt;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Booking complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Booking">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AltID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BookedBy" type="{http://connect.bookt.com/Schemas/Person.xsd}Person" minOccurs="0"/>
 *         &lt;element name="CheckIn" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CheckOut" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CreditCard" type="{http://connect.bookt.com/Schemas/CreditCard.xsd}CreditCard" minOccurs="0"/>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="NumAdults" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="NumChildren" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="PaymentTerms" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrivateNotes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PropertyID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="PublicNotes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Renter" type="{http://connect.bookt.com/Schemas/Person.xsd}Person" minOccurs="0"/>
 *         &lt;element name="Statement" type="{http://connect.bookt.com/Schemas/Statement.xsd}Statement" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TotalDueNow" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UnitID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Booking", namespace = "http://connect.bookt.com/Schemas/Booking.xsd", propOrder = {
    "altID",
    "bookedBy",
    "checkIn",
    "checkOut",
    "creditCard",
    "id",
    "numAdults",
    "numChildren",
    "paymentTerms",
    "privateNotes",
    "propertyID",
    "publicNotes",
    "renter",
    "statement",
    "status",
    "totalDueNow",
    "type",
    "unitID"
})
public class Booking {

    @XmlElementRef(name = "AltID", namespace = "http://connect.bookt.com/Schemas/Booking.xsd", type = JAXBElement.class)
    protected JAXBElement<String> altID;
    @XmlElementRef(name = "BookedBy", namespace = "http://connect.bookt.com/Schemas/Booking.xsd", type = JAXBElement.class)
    protected JAXBElement<Person> bookedBy;
    @XmlElement(name = "CheckIn")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar checkIn;
    @XmlElement(name = "CheckOut")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar checkOut;
    @XmlElementRef(name = "CreditCard", namespace = "http://connect.bookt.com/Schemas/Booking.xsd", type = JAXBElement.class)
    protected JAXBElement<CreditCard> creditCard;
    @XmlElement(name = "ID")
    protected Integer id;
    @XmlElement(name = "NumAdults")
    protected Integer numAdults;
    @XmlElement(name = "NumChildren")
    protected Integer numChildren;
    @XmlElementRef(name = "PaymentTerms", namespace = "http://connect.bookt.com/Schemas/Booking.xsd", type = JAXBElement.class)
    protected JAXBElement<String> paymentTerms;
    @XmlElementRef(name = "PrivateNotes", namespace = "http://connect.bookt.com/Schemas/Booking.xsd", type = JAXBElement.class)
    protected JAXBElement<String> privateNotes;
    @XmlElement(name = "PropertyID")
    protected Integer propertyID;
    @XmlElementRef(name = "PublicNotes", namespace = "http://connect.bookt.com/Schemas/Booking.xsd", type = JAXBElement.class)
    protected JAXBElement<String> publicNotes;
    @XmlElementRef(name = "Renter", namespace = "http://connect.bookt.com/Schemas/Booking.xsd", type = JAXBElement.class)
    protected JAXBElement<Person> renter;
    @XmlElementRef(name = "Statement", namespace = "http://connect.bookt.com/Schemas/Booking.xsd", type = JAXBElement.class)
    protected JAXBElement<Statement> statement;
    @XmlElementRef(name = "Status", namespace = "http://connect.bookt.com/Schemas/Booking.xsd", type = JAXBElement.class)
    protected JAXBElement<String> status;
    @XmlElement(name = "TotalDueNow")
    protected BigDecimal totalDueNow;
    @XmlElementRef(name = "Type", namespace = "http://connect.bookt.com/Schemas/Booking.xsd", type = JAXBElement.class)
    protected JAXBElement<String> type;
    @XmlElement(name = "UnitID")
    protected Integer unitID;

    /**
     * Gets the value of the altID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAltID() {
        return altID;
    }

    /**
     * Sets the value of the altID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAltID(JAXBElement<String> value) {
        this.altID = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the bookedBy property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Person }{@code >}
     *     
     */
    public JAXBElement<Person> getBookedBy() {
        return bookedBy;
    }

    /**
     * Sets the value of the bookedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Person }{@code >}
     *     
     */
    public void setBookedBy(JAXBElement<Person> value) {
        this.bookedBy = ((JAXBElement<Person> ) value);
    }

    /**
     * Gets the value of the checkIn property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCheckIn() {
        return checkIn;
    }

    /**
     * Sets the value of the checkIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCheckIn(XMLGregorianCalendar value) {
        this.checkIn = value;
    }

    /**
     * Gets the value of the checkOut property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCheckOut() {
        return checkOut;
    }

    /**
     * Sets the value of the checkOut property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCheckOut(XMLGregorianCalendar value) {
        this.checkOut = value;
    }

    /**
     * Gets the value of the creditCard property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link CreditCard }{@code >}
     *     
     */
    public JAXBElement<CreditCard> getCreditCard() {
        return creditCard;
    }

    /**
     * Sets the value of the creditCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link CreditCard }{@code >}
     *     
     */
    public void setCreditCard(JAXBElement<CreditCard> value) {
        this.creditCard = ((JAXBElement<CreditCard> ) value);
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setID(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the numAdults property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumAdults() {
        return numAdults;
    }

    /**
     * Sets the value of the numAdults property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumAdults(Integer value) {
        this.numAdults = value;
    }

    /**
     * Gets the value of the numChildren property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumChildren() {
        return numChildren;
    }

    /**
     * Sets the value of the numChildren property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumChildren(Integer value) {
        this.numChildren = value;
    }

    /**
     * Gets the value of the paymentTerms property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPaymentTerms() {
        return paymentTerms;
    }

    /**
     * Sets the value of the paymentTerms property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPaymentTerms(JAXBElement<String> value) {
        this.paymentTerms = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the privateNotes property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPrivateNotes() {
        return privateNotes;
    }

    /**
     * Sets the value of the privateNotes property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPrivateNotes(JAXBElement<String> value) {
        this.privateNotes = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the propertyID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPropertyID() {
        return propertyID;
    }

    /**
     * Sets the value of the propertyID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPropertyID(Integer value) {
        this.propertyID = value;
    }

    /**
     * Gets the value of the publicNotes property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPublicNotes() {
        return publicNotes;
    }

    /**
     * Sets the value of the publicNotes property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPublicNotes(JAXBElement<String> value) {
        this.publicNotes = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the renter property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Person }{@code >}
     *     
     */
    public JAXBElement<Person> getRenter() {
        return renter;
    }

    /**
     * Sets the value of the renter property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Person }{@code >}
     *     
     */
    public void setRenter(JAXBElement<Person> value) {
        this.renter = ((JAXBElement<Person> ) value);
    }

    /**
     * Gets the value of the statement property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Statement }{@code >}
     *     
     */
    public JAXBElement<Statement> getStatement() {
        return statement;
    }

    /**
     * Sets the value of the statement property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Statement }{@code >}
     *     
     */
    public void setStatement(JAXBElement<Statement> value) {
        this.statement = ((JAXBElement<Statement> ) value);
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStatus(JAXBElement<String> value) {
        this.status = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the totalDueNow property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalDueNow() {
        return totalDueNow;
    }

    /**
     * Sets the value of the totalDueNow property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalDueNow(BigDecimal value) {
        this.totalDueNow = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setType(JAXBElement<String> value) {
        this.type = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the unitID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUnitID() {
        return unitID;
    }

    /**
     * Sets the value of the unitID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUnitID(Integer value) {
        this.unitID = value;
    }

}
