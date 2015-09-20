
package net.cbtltd.rest.rci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resort-ai-fee-general complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resort-ai-fee-general">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="infantAge" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="infantFree" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="childAge" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="teenAge" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="depositRules" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditsUsableAtSisterResorts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prepayBenefits" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prepayOption" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resort-ai-fee-general", propOrder = {
    "infantAge",
    "infantFree",
    "childAge",
    "teenAge",
    "depositRules",
    "creditsUsableAtSisterResorts",
    "prepayBenefits",
    "prepayOption"
})
public class ResortAiFeeGeneral {

    protected Integer infantAge;
    protected Integer infantFree;
    protected Integer childAge;
    protected Integer teenAge;
    protected String depositRules;
    protected String creditsUsableAtSisterResorts;
    protected String prepayBenefits;
    protected String prepayOption;

    /**
     * Gets the value of the infantAge property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInfantAge() {
        return infantAge;
    }

    /**
     * Sets the value of the infantAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInfantAge(Integer value) {
        this.infantAge = value;
    }

    /**
     * Gets the value of the infantFree property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInfantFree() {
        return infantFree;
    }

    /**
     * Sets the value of the infantFree property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInfantFree(Integer value) {
        this.infantFree = value;
    }

    /**
     * Gets the value of the childAge property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getChildAge() {
        return childAge;
    }

    /**
     * Sets the value of the childAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setChildAge(Integer value) {
        this.childAge = value;
    }

    /**
     * Gets the value of the teenAge property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTeenAge() {
        return teenAge;
    }

    /**
     * Sets the value of the teenAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTeenAge(Integer value) {
        this.teenAge = value;
    }

    /**
     * Gets the value of the depositRules property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepositRules() {
        return depositRules;
    }

    /**
     * Sets the value of the depositRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepositRules(String value) {
        this.depositRules = value;
    }

    /**
     * Gets the value of the creditsUsableAtSisterResorts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditsUsableAtSisterResorts() {
        return creditsUsableAtSisterResorts;
    }

    /**
     * Sets the value of the creditsUsableAtSisterResorts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditsUsableAtSisterResorts(String value) {
        this.creditsUsableAtSisterResorts = value;
    }

    /**
     * Gets the value of the prepayBenefits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrepayBenefits() {
        return prepayBenefits;
    }

    /**
     * Sets the value of the prepayBenefits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrepayBenefits(String value) {
        this.prepayBenefits = value;
    }

    /**
     * Gets the value of the prepayOption property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrepayOption() {
        return prepayOption;
    }

    /**
     * Sets the value of the prepayOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrepayOption(String value) {
        this.prepayOption = value;
    }

}
