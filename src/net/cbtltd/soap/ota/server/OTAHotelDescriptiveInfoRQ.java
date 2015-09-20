/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */

package net.cbtltd.soap.ota.server;

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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="POS" type="{http://www.opentravel.org/OTA/2003/05}POS_Type" minOccurs="0"/>
 *         &lt;element name="HotelDescriptiveInfos">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="HotelDescriptiveInfo" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://www.opentravel.org/OTA/2003/05}HotelDescriptiveInfoRequestType">
 *                           &lt;attribute name="StateCodeList" type="{http://www.opentravel.org/OTA/2003/05}ListOfStringLength1to8" />
 *                           &lt;attribute name="CountryCodeList" type="{http://www.opentravel.org/OTA/2003/05}ListOfISO3166" />
 *                           &lt;attribute name="BrandCodeList" type="{http://www.opentravel.org/OTA/2003/05}ListOfStringLength1to8" />
 *                           &lt;attribute name="MoreDataEchoToken" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to128" />
 *                           &lt;attribute name="ApplicableDate" type="{http://www.w3.org/2001/XMLSchema}date" />
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="LangRequested" type="{http://www.w3.org/2001/XMLSchema}language" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}OTA_PayloadStdAttributes"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pos",
    "hotelDescriptiveInfos"
})
@XmlRootElement(name = "OTA_HotelDescriptiveInfoRQ")
public class OTAHotelDescriptiveInfoRQ {

    @XmlElement(name = "POS")
    protected POSType pos;
    @XmlElement(name = "HotelDescriptiveInfos", required = true)
    protected OTAHotelDescriptiveInfoRQ.HotelDescriptiveInfos hotelDescriptiveInfos;
    @XmlAttribute(name = "EchoToken")
    protected String echoToken;
    @XmlAttribute(name = "TimeStamp")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timeStamp;
    @XmlAttribute(name = "Target")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String target;
    @XmlAttribute(name = "Version", required = true)
    protected BigDecimal version;
    @XmlAttribute(name = "TransactionIdentifier")
    protected String transactionIdentifier;
    @XmlAttribute(name = "SequenceNmbr")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger sequenceNmbr;
    @XmlAttribute(name = "TransactionStatusCode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String transactionStatusCode;
    @XmlAttribute(name = "RetransmissionIndicator")
    protected Boolean retransmissionIndicator;
    @XmlAttribute(name = "AltLangID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "language")
    protected String altLangID;
    @XmlAttribute(name = "PrimaryLangID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "language")
    protected String primaryLangID;

    /**
     * Gets the value of the pos property.
     * 
     * @return
     *     possible object is
     *     {@link POSType }
     *     
     */
    public POSType getPOS() {
        return pos;
    }

    /**
     * Sets the value of the pos property.
     * 
     * @param value
     *     allowed object is
     *     {@link POSType }
     *     
     */
    public void setPOS(POSType value) {
        this.pos = value;
    }

    /**
     * Gets the value of the hotelDescriptiveInfos property.
     * 
     * @return
     *     possible object is
     *     {@link OTAHotelDescriptiveInfoRQ.HotelDescriptiveInfos }
     *     
     */
    public OTAHotelDescriptiveInfoRQ.HotelDescriptiveInfos getHotelDescriptiveInfos() {
        return hotelDescriptiveInfos;
    }

    /**
     * Sets the value of the hotelDescriptiveInfos property.
     * 
     * @param value
     *     allowed object is
     *     {@link OTAHotelDescriptiveInfoRQ.HotelDescriptiveInfos }
     *     
     */
    public void setHotelDescriptiveInfos(OTAHotelDescriptiveInfoRQ.HotelDescriptiveInfos value) {
        this.hotelDescriptiveInfos = value;
    }

    /**
     * Gets the value of the echoToken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEchoToken() {
        return echoToken;
    }

    /**
     * Sets the value of the echoToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEchoToken(String value) {
        this.echoToken = value;
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
     * Gets the value of the target property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTarget() {
        if (target == null) {
            return "Production";
        } else {
            return target;
        }
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTarget(String value) {
        this.target = value;
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
     * Gets the value of the transactionIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionIdentifier() {
        return transactionIdentifier;
    }

    /**
     * Sets the value of the transactionIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionIdentifier(String value) {
        this.transactionIdentifier = value;
    }

    /**
     * Gets the value of the sequenceNmbr property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSequenceNmbr() {
        return sequenceNmbr;
    }

    /**
     * Sets the value of the sequenceNmbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSequenceNmbr(BigInteger value) {
        this.sequenceNmbr = value;
    }

    /**
     * Gets the value of the transactionStatusCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionStatusCode() {
        return transactionStatusCode;
    }

    /**
     * Sets the value of the transactionStatusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionStatusCode(String value) {
        this.transactionStatusCode = value;
    }

    /**
     * Gets the value of the retransmissionIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRetransmissionIndicator() {
        return retransmissionIndicator;
    }

    /**
     * Sets the value of the retransmissionIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRetransmissionIndicator(Boolean value) {
        this.retransmissionIndicator = value;
    }

    /**
     * Gets the value of the altLangID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAltLangID() {
        return altLangID;
    }

    /**
     * Sets the value of the altLangID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAltLangID(String value) {
        this.altLangID = value;
    }

    /**
     * Gets the value of the primaryLangID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryLangID() {
        return primaryLangID;
    }

    /**
     * Sets the value of the primaryLangID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryLangID(String value) {
        this.primaryLangID = value;
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
     *         &lt;element name="HotelDescriptiveInfo" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://www.opentravel.org/OTA/2003/05}HotelDescriptiveInfoRequestType">
     *                 &lt;attribute name="StateCodeList" type="{http://www.opentravel.org/OTA/2003/05}ListOfStringLength1to8" />
     *                 &lt;attribute name="CountryCodeList" type="{http://www.opentravel.org/OTA/2003/05}ListOfISO3166" />
     *                 &lt;attribute name="BrandCodeList" type="{http://www.opentravel.org/OTA/2003/05}ListOfStringLength1to8" />
     *                 &lt;attribute name="MoreDataEchoToken" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to128" />
     *                 &lt;attribute name="ApplicableDate" type="{http://www.w3.org/2001/XMLSchema}date" />
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="LangRequested" type="{http://www.w3.org/2001/XMLSchema}language" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "hotelDescriptiveInfo"
    })
    public static class HotelDescriptiveInfos {

        @XmlElement(name = "HotelDescriptiveInfo", required = true)
        protected List<OTAHotelDescriptiveInfoRQ.HotelDescriptiveInfos.HotelDescriptiveInfo> hotelDescriptiveInfo;
        @XmlAttribute(name = "LangRequested")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "language")
        protected String langRequested;

        /**
         * Gets the value of the hotelDescriptiveInfo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the hotelDescriptiveInfo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHotelDescriptiveInfo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OTAHotelDescriptiveInfoRQ.HotelDescriptiveInfos.HotelDescriptiveInfo }
         * 
         * 
         */
        public List<OTAHotelDescriptiveInfoRQ.HotelDescriptiveInfos.HotelDescriptiveInfo> getHotelDescriptiveInfo() {
            if (hotelDescriptiveInfo == null) {
                hotelDescriptiveInfo = new ArrayList<OTAHotelDescriptiveInfoRQ.HotelDescriptiveInfos.HotelDescriptiveInfo>();
            }
            return this.hotelDescriptiveInfo;
        }

        /**
         * Gets the value of the langRequested property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLangRequested() {
            return langRequested;
        }

        /**
         * Sets the value of the langRequested property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLangRequested(String value) {
            this.langRequested = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://www.opentravel.org/OTA/2003/05}HotelDescriptiveInfoRequestType">
         *       &lt;attribute name="StateCodeList" type="{http://www.opentravel.org/OTA/2003/05}ListOfStringLength1to8" />
         *       &lt;attribute name="CountryCodeList" type="{http://www.opentravel.org/OTA/2003/05}ListOfISO3166" />
         *       &lt;attribute name="BrandCodeList" type="{http://www.opentravel.org/OTA/2003/05}ListOfStringLength1to8" />
         *       &lt;attribute name="MoreDataEchoToken" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to128" />
         *       &lt;attribute name="ApplicableDate" type="{http://www.w3.org/2001/XMLSchema}date" />
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class HotelDescriptiveInfo
            extends HotelDescriptiveInfoRequestType
        {

            @XmlAttribute(name = "StateCodeList")
            protected List<String> stateCodeList;
            @XmlAttribute(name = "CountryCodeList")
            protected List<String> countryCodeList;
            @XmlAttribute(name = "BrandCodeList")
            protected List<String> brandCodeList;
            @XmlAttribute(name = "MoreDataEchoToken")
            protected String moreDataEchoToken;
            @XmlAttribute(name = "ApplicableDate")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar applicableDate;

            /**
             * Gets the value of the stateCodeList property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the stateCodeList property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getStateCodeList().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getStateCodeList() {
                if (stateCodeList == null) {
                    stateCodeList = new ArrayList<String>();
                }
                return this.stateCodeList;
            }

            /**
             * Gets the value of the countryCodeList property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the countryCodeList property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getCountryCodeList().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getCountryCodeList() {
                if (countryCodeList == null) {
                    countryCodeList = new ArrayList<String>();
                }
                return this.countryCodeList;
            }

            /**
             * Gets the value of the brandCodeList property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the brandCodeList property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getBrandCodeList().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getBrandCodeList() {
                if (brandCodeList == null) {
                    brandCodeList = new ArrayList<String>();
                }
                return this.brandCodeList;
            }

            /**
             * Gets the value of the moreDataEchoToken property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMoreDataEchoToken() {
                return moreDataEchoToken;
            }

            /**
             * Sets the value of the moreDataEchoToken property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMoreDataEchoToken(String value) {
                this.moreDataEchoToken = value;
            }

            /**
             * Gets the value of the applicableDate property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getApplicableDate() {
                return applicableDate;
            }

            /**
             * Sets the value of the applicableDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setApplicableDate(XMLGregorianCalendar value) {
                this.applicableDate = value;
            }

        }

    }

}
