
package net.cbtltd.rest.interhome;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RetailerBookingInputValue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RetailerBookingInputValue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SalesOfficeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LanguageCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AccommodationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CheckIn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CheckOut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AdditionalServices" type="{http://www.interhome.com/webservice}ArrayOfAdditionalServiceInputItem" minOccurs="0"/>
 *         &lt;element name="Adults" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Babies" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Children" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CustomerSalutationType" type="{http://www.interhome.com/webservice}SalutationType"/>
 *         &lt;element name="CustomerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomerFirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RetailerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RetailerExtraCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RetailerContact" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaymentType" type="{http://www.interhome.com/webservice}PaymentType"/>
 *         &lt;element name="CreditCardType" type="{http://www.interhome.com/webservice}CCType"/>
 *         &lt;element name="CreditCardNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreditCardCvc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreditCardExpiry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreditCardHolder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BankAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BankCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BankAccountHolder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetailerBookingInputValue", propOrder = {
    "salesOfficeCode",
    "languageCode",
    "currencyCode",
    "accommodationCode",
    "checkIn",
    "checkOut",
    "additionalServices",
    "adults",
    "babies",
    "children",
    "customerSalutationType",
    "customerName",
    "customerFirstName",
    "retailerCode",
    "retailerExtraCode",
    "retailerContact",
    "comment",
    "paymentType",
    "creditCardType",
    "creditCardNumber",
    "creditCardCvc",
    "creditCardExpiry",
    "creditCardHolder",
    "bankAccountNumber",
    "bankCode",
    "bankAccountHolder"
})
public class RetailerBookingInputValue {

    @XmlElement(name = "SalesOfficeCode")
    protected String salesOfficeCode;
    @XmlElement(name = "LanguageCode")
    protected String languageCode;
    @XmlElement(name = "CurrencyCode")
    protected String currencyCode;
    @XmlElement(name = "AccommodationCode")
    protected String accommodationCode;
    @XmlElement(name = "CheckIn")
    protected String checkIn;
    @XmlElement(name = "CheckOut")
    protected String checkOut;
    @XmlElement(name = "AdditionalServices")
    protected ArrayOfAdditionalServiceInputItem additionalServices;
    @XmlElement(name = "Adults")
    protected int adults;
    @XmlElement(name = "Babies")
    protected int babies;
    @XmlElement(name = "Children")
    protected int children;
    @XmlElement(name = "CustomerSalutationType", required = true)
    protected SalutationType customerSalutationType;
    @XmlElement(name = "CustomerName")
    protected String customerName;
    @XmlElement(name = "CustomerFirstName")
    protected String customerFirstName;
    @XmlElement(name = "RetailerCode")
    protected String retailerCode;
    @XmlElement(name = "RetailerExtraCode")
    protected String retailerExtraCode;
    @XmlElement(name = "RetailerContact")
    protected String retailerContact;
    @XmlElement(name = "Comment")
    protected String comment;
    @XmlElement(name = "PaymentType", required = true)
    protected PaymentType paymentType;
    @XmlElement(name = "CreditCardType", required = true)
    protected CCType creditCardType;
    @XmlElement(name = "CreditCardNumber")
    protected String creditCardNumber;
    @XmlElement(name = "CreditCardCvc")
    protected String creditCardCvc;
    @XmlElement(name = "CreditCardExpiry")
    protected String creditCardExpiry;
    @XmlElement(name = "CreditCardHolder")
    protected String creditCardHolder;
    @XmlElement(name = "BankAccountNumber")
    protected String bankAccountNumber;
    @XmlElement(name = "BankCode")
    protected String bankCode;
    @XmlElement(name = "BankAccountHolder")
    protected String bankAccountHolder;

    /**
     * Gets the value of the salesOfficeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesOfficeCode() {
        return salesOfficeCode;
    }

    /**
     * Sets the value of the salesOfficeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesOfficeCode(String value) {
        this.salesOfficeCode = value;
    }

    /**
     * Gets the value of the languageCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the value of the languageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguageCode(String value) {
        this.languageCode = value;
    }

    /**
     * Gets the value of the currencyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the value of the currencyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencyCode(String value) {
        this.currencyCode = value;
    }

    /**
     * Gets the value of the accommodationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccommodationCode() {
        return accommodationCode;
    }

    /**
     * Sets the value of the accommodationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccommodationCode(String value) {
        this.accommodationCode = value;
    }

    /**
     * Gets the value of the checkIn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckIn() {
        return checkIn;
    }

    /**
     * Sets the value of the checkIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckIn(String value) {
        this.checkIn = value;
    }

    /**
     * Gets the value of the checkOut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckOut() {
        return checkOut;
    }

    /**
     * Sets the value of the checkOut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckOut(String value) {
        this.checkOut = value;
    }

    /**
     * Gets the value of the additionalServices property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAdditionalServiceInputItem }
     *     
     */
    public ArrayOfAdditionalServiceInputItem getAdditionalServices() {
        return additionalServices;
    }

    /**
     * Sets the value of the additionalServices property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAdditionalServiceInputItem }
     *     
     */
    public void setAdditionalServices(ArrayOfAdditionalServiceInputItem value) {
        this.additionalServices = value;
    }

    /**
     * Gets the value of the adults property.
     * 
     */
    public int getAdults() {
        return adults;
    }

    /**
     * Sets the value of the adults property.
     * 
     */
    public void setAdults(int value) {
        this.adults = value;
    }

    /**
     * Gets the value of the babies property.
     * 
     */
    public int getBabies() {
        return babies;
    }

    /**
     * Sets the value of the babies property.
     * 
     */
    public void setBabies(int value) {
        this.babies = value;
    }

    /**
     * Gets the value of the children property.
     * 
     */
    public int getChildren() {
        return children;
    }

    /**
     * Sets the value of the children property.
     * 
     */
    public void setChildren(int value) {
        this.children = value;
    }

    /**
     * Gets the value of the customerSalutationType property.
     * 
     * @return
     *     possible object is
     *     {@link SalutationType }
     *     
     */
    public SalutationType getCustomerSalutationType() {
        return customerSalutationType;
    }

    /**
     * Sets the value of the customerSalutationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalutationType }
     *     
     */
    public void setCustomerSalutationType(SalutationType value) {
        this.customerSalutationType = value;
    }

    /**
     * Gets the value of the customerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the value of the customerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerName(String value) {
        this.customerName = value;
    }

    /**
     * Gets the value of the customerFirstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerFirstName() {
        return customerFirstName;
    }

    /**
     * Sets the value of the customerFirstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerFirstName(String value) {
        this.customerFirstName = value;
    }

    /**
     * Gets the value of the retailerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetailerCode() {
        return retailerCode;
    }

    /**
     * Sets the value of the retailerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetailerCode(String value) {
        this.retailerCode = value;
    }

    /**
     * Gets the value of the retailerExtraCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetailerExtraCode() {
        return retailerExtraCode;
    }

    /**
     * Sets the value of the retailerExtraCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetailerExtraCode(String value) {
        this.retailerExtraCode = value;
    }

    /**
     * Gets the value of the retailerContact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetailerContact() {
        return retailerContact;
    }

    /**
     * Sets the value of the retailerContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetailerContact(String value) {
        this.retailerContact = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the paymentType property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentType }
     *     
     */
    public PaymentType getPaymentType() {
        return paymentType;
    }

    /**
     * Sets the value of the paymentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentType }
     *     
     */
    public void setPaymentType(PaymentType value) {
        this.paymentType = value;
    }

    /**
     * Gets the value of the creditCardType property.
     * 
     * @return
     *     possible object is
     *     {@link CCType }
     *     
     */
    public CCType getCreditCardType() {
        return creditCardType;
    }

    /**
     * Sets the value of the creditCardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCType }
     *     
     */
    public void setCreditCardType(CCType value) {
        this.creditCardType = value;
    }

    /**
     * Gets the value of the creditCardNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * Sets the value of the creditCardNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditCardNumber(String value) {
        this.creditCardNumber = value;
    }

    /**
     * Gets the value of the creditCardCvc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditCardCvc() {
        return creditCardCvc;
    }

    /**
     * Sets the value of the creditCardCvc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditCardCvc(String value) {
        this.creditCardCvc = value;
    }

    /**
     * Gets the value of the creditCardExpiry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditCardExpiry() {
        return creditCardExpiry;
    }

    /**
     * Sets the value of the creditCardExpiry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditCardExpiry(String value) {
        this.creditCardExpiry = value;
    }

    /**
     * Gets the value of the creditCardHolder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditCardHolder() {
        return creditCardHolder;
    }

    /**
     * Sets the value of the creditCardHolder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditCardHolder(String value) {
        this.creditCardHolder = value;
    }

    /**
     * Gets the value of the bankAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    /**
     * Sets the value of the bankAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAccountNumber(String value) {
        this.bankAccountNumber = value;
    }

    /**
     * Gets the value of the bankCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * Sets the value of the bankCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankCode(String value) {
        this.bankCode = value;
    }

    /**
     * Gets the value of the bankAccountHolder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAccountHolder() {
        return bankAccountHolder;
    }

    /**
     * Sets the value of the bankAccountHolder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAccountHolder(String value) {
        this.bankAccountHolder = value;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RetailerBookingInputValue [salesOfficeCode=");
		builder.append(salesOfficeCode);
		builder.append(", languageCode=");
		builder.append(languageCode);
		builder.append(", currencyCode=");
		builder.append(currencyCode);
		builder.append(", accommodationCode=");
		builder.append(accommodationCode);
		builder.append(", checkIn=");
		builder.append(checkIn);
		builder.append(", checkOut=");
		builder.append(checkOut);
		builder.append(", additionalServices=");
		builder.append(additionalServices);
		builder.append(", adults=");
		builder.append(adults);
		builder.append(", babies=");
		builder.append(babies);
		builder.append(", children=");
		builder.append(children);
		builder.append(", customerSalutationType=");
		builder.append(customerSalutationType);
		builder.append(", customerName=");
		builder.append(customerName);
		builder.append(", customerFirstName=");
		builder.append(customerFirstName);
		builder.append(", retailerCode=");
		builder.append(retailerCode);
		builder.append(", retailerExtraCode=");
		builder.append(retailerExtraCode);
		builder.append(", retailerContact=");
		builder.append(retailerContact);
		builder.append(", comment=");
		builder.append(comment);
		builder.append(", paymentType=");
		builder.append(paymentType);
		builder.append(", creditCardType=");
		builder.append(creditCardType);
		builder.append(", creditCardNumber=");
		builder.append(creditCardNumber);
		builder.append(", creditCardCvc=");
		builder.append(creditCardCvc);
		builder.append(", creditCardExpiry=");
		builder.append(creditCardExpiry);
		builder.append(", creditCardHolder=");
		builder.append(creditCardHolder);
		builder.append(", bankAccountNumber=");
		builder.append(bankAccountNumber);
		builder.append(", bankCode=");
		builder.append(bankCode);
		builder.append(", bankAccountHolder=");
		builder.append(bankAccountHolder);
		builder.append("]");
		return builder.toString();
	}

}
