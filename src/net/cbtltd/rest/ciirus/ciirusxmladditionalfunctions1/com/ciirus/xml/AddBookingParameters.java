
package net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddBookingParameters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddBookingParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PropertyID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EmailAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Telephone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ArrivalDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DepartureDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddAsTentative" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="TentativeExpiryDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PoolHeat" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="BaseRentalAmountToMCExTax" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="BaseRentalAmountToAgentExTax" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="BasePoolHeatAmountExTax" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="BaseBookingFeeAmountExTax" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="BaseCleaningFeeAmountExTax" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="Tax1Percent" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="Tax2Percent" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="Tax3Percent" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ChargeTax1OnRental" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ChargeTax2OnRental" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ChargeTax3OnRental" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ChargeTax1OnPoolHeat" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ChargeTax2OnPoolHeat" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ChargeTax3OnPoolHeat" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ChargeTax1OnBookingFee" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ChargeTax2OnBookingFee" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ChargeTax3OnBookingFee" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ChargeTax1OnCleaningFee" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ChargeTax2OnCleaningFee" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ChargeTax3OnCleaningFee" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="TaxExemptBooking" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaymentTakenBy" type="{http://xml.ciirus.com/}ePaymentTakenBy"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddBookingParameters", propOrder = {
    "propertyID",
    "firstName",
    "lastName",
    "emailAddress",
    "address",
    "telephone",
    "arrivalDate",
    "departureDate",
    "addAsTentative",
    "tentativeExpiryDate",
    "poolHeat",
    "baseRentalAmountToMCExTax",
    "baseRentalAmountToAgentExTax",
    "basePoolHeatAmountExTax",
    "baseBookingFeeAmountExTax",
    "baseCleaningFeeAmountExTax",
    "tax1Percent",
    "tax2Percent",
    "tax3Percent",
    "chargeTax1OnRental",
    "chargeTax2OnRental",
    "chargeTax3OnRental",
    "chargeTax1OnPoolHeat",
    "chargeTax2OnPoolHeat",
    "chargeTax3OnPoolHeat",
    "chargeTax1OnBookingFee",
    "chargeTax2OnBookingFee",
    "chargeTax3OnBookingFee",
    "chargeTax1OnCleaningFee",
    "chargeTax2OnCleaningFee",
    "chargeTax3OnCleaningFee",
    "taxExemptBooking",
    "comments",
    "paymentTakenBy"
})
public class AddBookingParameters {

    @XmlElement(name = "PropertyID")
    protected int propertyID;
    @XmlElement(name = "FirstName")
    protected String firstName;
    @XmlElement(name = "LastName")
    protected String lastName;
    @XmlElement(name = "EmailAddress")
    protected String emailAddress;
    @XmlElement(name = "Address")
    protected String address;
    @XmlElement(name = "Telephone")
    protected String telephone;
    @XmlElement(name = "ArrivalDate")
    protected String arrivalDate;
    @XmlElement(name = "DepartureDate")
    protected String departureDate;
    @XmlElement(name = "AddAsTentative")
    protected boolean addAsTentative;
    @XmlElement(name = "TentativeExpiryDate")
    protected String tentativeExpiryDate;
    @XmlElement(name = "PoolHeat")
    protected boolean poolHeat;
    @XmlElement(name = "BaseRentalAmountToMCExTax", required = true)
    protected BigDecimal baseRentalAmountToMCExTax;
    @XmlElement(name = "BaseRentalAmountToAgentExTax", required = true)
    protected BigDecimal baseRentalAmountToAgentExTax;
    @XmlElement(name = "BasePoolHeatAmountExTax", required = true)
    protected BigDecimal basePoolHeatAmountExTax;
    @XmlElement(name = "BaseBookingFeeAmountExTax", required = true)
    protected BigDecimal baseBookingFeeAmountExTax;
    @XmlElement(name = "BaseCleaningFeeAmountExTax", required = true)
    protected BigDecimal baseCleaningFeeAmountExTax;
    @XmlElement(name = "Tax1Percent", required = true)
    protected BigDecimal tax1Percent;
    @XmlElement(name = "Tax2Percent", required = true)
    protected BigDecimal tax2Percent;
    @XmlElement(name = "Tax3Percent", required = true)
    protected BigDecimal tax3Percent;
    @XmlElement(name = "ChargeTax1OnRental")
    protected boolean chargeTax1OnRental;
    @XmlElement(name = "ChargeTax2OnRental")
    protected boolean chargeTax2OnRental;
    @XmlElement(name = "ChargeTax3OnRental")
    protected boolean chargeTax3OnRental;
    @XmlElement(name = "ChargeTax1OnPoolHeat")
    protected boolean chargeTax1OnPoolHeat;
    @XmlElement(name = "ChargeTax2OnPoolHeat")
    protected boolean chargeTax2OnPoolHeat;
    @XmlElement(name = "ChargeTax3OnPoolHeat")
    protected boolean chargeTax3OnPoolHeat;
    @XmlElement(name = "ChargeTax1OnBookingFee")
    protected boolean chargeTax1OnBookingFee;
    @XmlElement(name = "ChargeTax2OnBookingFee")
    protected boolean chargeTax2OnBookingFee;
    @XmlElement(name = "ChargeTax3OnBookingFee")
    protected boolean chargeTax3OnBookingFee;
    @XmlElement(name = "ChargeTax1OnCleaningFee")
    protected boolean chargeTax1OnCleaningFee;
    @XmlElement(name = "ChargeTax2OnCleaningFee")
    protected boolean chargeTax2OnCleaningFee;
    @XmlElement(name = "ChargeTax3OnCleaningFee")
    protected boolean chargeTax3OnCleaningFee;
    @XmlElement(name = "TaxExemptBooking")
    protected boolean taxExemptBooking;
    @XmlElement(name = "Comments")
    protected String comments;
    @XmlElement(name = "PaymentTakenBy", required = true)
    protected EPaymentTakenBy paymentTakenBy;

    /**
     * Gets the value of the propertyID property.
     * 
     */
    public int getPropertyID() {
        return propertyID;
    }

    /**
     * Sets the value of the propertyID property.
     * 
     */
    public void setPropertyID(int value) {
        this.propertyID = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the emailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the value of the emailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailAddress(String value) {
        this.emailAddress = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Gets the value of the telephone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Sets the value of the telephone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelephone(String value) {
        this.telephone = value;
    }

    /**
     * Gets the value of the arrivalDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArrivalDate() {
        return arrivalDate;
    }

    /**
     * Sets the value of the arrivalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArrivalDate(String value) {
        this.arrivalDate = value;
    }

    /**
     * Gets the value of the departureDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the value of the departureDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureDate(String value) {
        this.departureDate = value;
    }

    /**
     * Gets the value of the addAsTentative property.
     * 
     */
    public boolean isAddAsTentative() {
        return addAsTentative;
    }

    /**
     * Sets the value of the addAsTentative property.
     * 
     */
    public void setAddAsTentative(boolean value) {
        this.addAsTentative = value;
    }

    /**
     * Gets the value of the tentativeExpiryDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTentativeExpiryDate() {
        return tentativeExpiryDate;
    }

    /**
     * Sets the value of the tentativeExpiryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTentativeExpiryDate(String value) {
        this.tentativeExpiryDate = value;
    }

    /**
     * Gets the value of the poolHeat property.
     * 
     */
    public boolean isPoolHeat() {
        return poolHeat;
    }

    /**
     * Sets the value of the poolHeat property.
     * 
     */
    public void setPoolHeat(boolean value) {
        this.poolHeat = value;
    }

    /**
     * Gets the value of the baseRentalAmountToMCExTax property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBaseRentalAmountToMCExTax() {
        return baseRentalAmountToMCExTax;
    }

    /**
     * Sets the value of the baseRentalAmountToMCExTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBaseRentalAmountToMCExTax(BigDecimal value) {
        this.baseRentalAmountToMCExTax = value;
    }

    /**
     * Gets the value of the baseRentalAmountToAgentExTax property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBaseRentalAmountToAgentExTax() {
        return baseRentalAmountToAgentExTax;
    }

    /**
     * Sets the value of the baseRentalAmountToAgentExTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBaseRentalAmountToAgentExTax(BigDecimal value) {
        this.baseRentalAmountToAgentExTax = value;
    }

    /**
     * Gets the value of the basePoolHeatAmountExTax property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBasePoolHeatAmountExTax() {
        return basePoolHeatAmountExTax;
    }

    /**
     * Sets the value of the basePoolHeatAmountExTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBasePoolHeatAmountExTax(BigDecimal value) {
        this.basePoolHeatAmountExTax = value;
    }

    /**
     * Gets the value of the baseBookingFeeAmountExTax property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBaseBookingFeeAmountExTax() {
        return baseBookingFeeAmountExTax;
    }

    /**
     * Sets the value of the baseBookingFeeAmountExTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBaseBookingFeeAmountExTax(BigDecimal value) {
        this.baseBookingFeeAmountExTax = value;
    }

    /**
     * Gets the value of the baseCleaningFeeAmountExTax property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBaseCleaningFeeAmountExTax() {
        return baseCleaningFeeAmountExTax;
    }

    /**
     * Sets the value of the baseCleaningFeeAmountExTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBaseCleaningFeeAmountExTax(BigDecimal value) {
        this.baseCleaningFeeAmountExTax = value;
    }

    /**
     * Gets the value of the tax1Percent property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTax1Percent() {
        return tax1Percent;
    }

    /**
     * Sets the value of the tax1Percent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTax1Percent(BigDecimal value) {
        this.tax1Percent = value;
    }

    /**
     * Gets the value of the tax2Percent property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTax2Percent() {
        return tax2Percent;
    }

    /**
     * Sets the value of the tax2Percent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTax2Percent(BigDecimal value) {
        this.tax2Percent = value;
    }

    /**
     * Gets the value of the tax3Percent property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTax3Percent() {
        return tax3Percent;
    }

    /**
     * Sets the value of the tax3Percent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTax3Percent(BigDecimal value) {
        this.tax3Percent = value;
    }

    /**
     * Gets the value of the chargeTax1OnRental property.
     * 
     */
    public boolean isChargeTax1OnRental() {
        return chargeTax1OnRental;
    }

    /**
     * Sets the value of the chargeTax1OnRental property.
     * 
     */
    public void setChargeTax1OnRental(boolean value) {
        this.chargeTax1OnRental = value;
    }

    /**
     * Gets the value of the chargeTax2OnRental property.
     * 
     */
    public boolean isChargeTax2OnRental() {
        return chargeTax2OnRental;
    }

    /**
     * Sets the value of the chargeTax2OnRental property.
     * 
     */
    public void setChargeTax2OnRental(boolean value) {
        this.chargeTax2OnRental = value;
    }

    /**
     * Gets the value of the chargeTax3OnRental property.
     * 
     */
    public boolean isChargeTax3OnRental() {
        return chargeTax3OnRental;
    }

    /**
     * Sets the value of the chargeTax3OnRental property.
     * 
     */
    public void setChargeTax3OnRental(boolean value) {
        this.chargeTax3OnRental = value;
    }

    /**
     * Gets the value of the chargeTax1OnPoolHeat property.
     * 
     */
    public boolean isChargeTax1OnPoolHeat() {
        return chargeTax1OnPoolHeat;
    }

    /**
     * Sets the value of the chargeTax1OnPoolHeat property.
     * 
     */
    public void setChargeTax1OnPoolHeat(boolean value) {
        this.chargeTax1OnPoolHeat = value;
    }

    /**
     * Gets the value of the chargeTax2OnPoolHeat property.
     * 
     */
    public boolean isChargeTax2OnPoolHeat() {
        return chargeTax2OnPoolHeat;
    }

    /**
     * Sets the value of the chargeTax2OnPoolHeat property.
     * 
     */
    public void setChargeTax2OnPoolHeat(boolean value) {
        this.chargeTax2OnPoolHeat = value;
    }

    /**
     * Gets the value of the chargeTax3OnPoolHeat property.
     * 
     */
    public boolean isChargeTax3OnPoolHeat() {
        return chargeTax3OnPoolHeat;
    }

    /**
     * Sets the value of the chargeTax3OnPoolHeat property.
     * 
     */
    public void setChargeTax3OnPoolHeat(boolean value) {
        this.chargeTax3OnPoolHeat = value;
    }

    /**
     * Gets the value of the chargeTax1OnBookingFee property.
     * 
     */
    public boolean isChargeTax1OnBookingFee() {
        return chargeTax1OnBookingFee;
    }

    /**
     * Sets the value of the chargeTax1OnBookingFee property.
     * 
     */
    public void setChargeTax1OnBookingFee(boolean value) {
        this.chargeTax1OnBookingFee = value;
    }

    /**
     * Gets the value of the chargeTax2OnBookingFee property.
     * 
     */
    public boolean isChargeTax2OnBookingFee() {
        return chargeTax2OnBookingFee;
    }

    /**
     * Sets the value of the chargeTax2OnBookingFee property.
     * 
     */
    public void setChargeTax2OnBookingFee(boolean value) {
        this.chargeTax2OnBookingFee = value;
    }

    /**
     * Gets the value of the chargeTax3OnBookingFee property.
     * 
     */
    public boolean isChargeTax3OnBookingFee() {
        return chargeTax3OnBookingFee;
    }

    /**
     * Sets the value of the chargeTax3OnBookingFee property.
     * 
     */
    public void setChargeTax3OnBookingFee(boolean value) {
        this.chargeTax3OnBookingFee = value;
    }

    /**
     * Gets the value of the chargeTax1OnCleaningFee property.
     * 
     */
    public boolean isChargeTax1OnCleaningFee() {
        return chargeTax1OnCleaningFee;
    }

    /**
     * Sets the value of the chargeTax1OnCleaningFee property.
     * 
     */
    public void setChargeTax1OnCleaningFee(boolean value) {
        this.chargeTax1OnCleaningFee = value;
    }

    /**
     * Gets the value of the chargeTax2OnCleaningFee property.
     * 
     */
    public boolean isChargeTax2OnCleaningFee() {
        return chargeTax2OnCleaningFee;
    }

    /**
     * Sets the value of the chargeTax2OnCleaningFee property.
     * 
     */
    public void setChargeTax2OnCleaningFee(boolean value) {
        this.chargeTax2OnCleaningFee = value;
    }

    /**
     * Gets the value of the chargeTax3OnCleaningFee property.
     * 
     */
    public boolean isChargeTax3OnCleaningFee() {
        return chargeTax3OnCleaningFee;
    }

    /**
     * Sets the value of the chargeTax3OnCleaningFee property.
     * 
     */
    public void setChargeTax3OnCleaningFee(boolean value) {
        this.chargeTax3OnCleaningFee = value;
    }

    /**
     * Gets the value of the taxExemptBooking property.
     * 
     */
    public boolean isTaxExemptBooking() {
        return taxExemptBooking;
    }

    /**
     * Sets the value of the taxExemptBooking property.
     * 
     */
    public void setTaxExemptBooking(boolean value) {
        this.taxExemptBooking = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
    }

    /**
     * Gets the value of the paymentTakenBy property.
     * 
     * @return
     *     possible object is
     *     {@link EPaymentTakenBy }
     *     
     */
    public EPaymentTakenBy getPaymentTakenBy() {
        return paymentTakenBy;
    }

    /**
     * Sets the value of the paymentTakenBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link EPaymentTakenBy }
     *     
     */
    public void setPaymentTakenBy(EPaymentTakenBy value) {
        this.paymentTakenBy = value;
    }

}
