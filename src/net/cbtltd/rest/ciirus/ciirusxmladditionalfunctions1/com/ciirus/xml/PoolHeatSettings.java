
package net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PoolHeatSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PoolHeatSettings">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HasPrivatePool" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PoolHeatable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PoolHeatIncludedInPrice" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="DailyCharge" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="MinimumNumberOfDaysToCharge" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PoolHeatSettings", propOrder = {
    "hasPrivatePool",
    "poolHeatable",
    "poolHeatIncludedInPrice",
    "dailyCharge",
    "minimumNumberOfDaysToCharge"
})
public class PoolHeatSettings {

    @XmlElement(name = "HasPrivatePool")
    protected boolean hasPrivatePool;
    @XmlElement(name = "PoolHeatable")
    protected boolean poolHeatable;
    @XmlElement(name = "PoolHeatIncludedInPrice")
    protected boolean poolHeatIncludedInPrice;
    @XmlElement(name = "DailyCharge", required = true)
    protected BigDecimal dailyCharge;
    @XmlElement(name = "MinimumNumberOfDaysToCharge")
    protected int minimumNumberOfDaysToCharge;

    /**
     * Gets the value of the hasPrivatePool property.
     * 
     */
    public boolean isHasPrivatePool() {
        return hasPrivatePool;
    }

    /**
     * Sets the value of the hasPrivatePool property.
     * 
     */
    public void setHasPrivatePool(boolean value) {
        this.hasPrivatePool = value;
    }

    /**
     * Gets the value of the poolHeatable property.
     * 
     */
    public boolean isPoolHeatable() {
        return poolHeatable;
    }

    /**
     * Sets the value of the poolHeatable property.
     * 
     */
    public void setPoolHeatable(boolean value) {
        this.poolHeatable = value;
    }

    /**
     * Gets the value of the poolHeatIncludedInPrice property.
     * 
     */
    public boolean isPoolHeatIncludedInPrice() {
        return poolHeatIncludedInPrice;
    }

    /**
     * Sets the value of the poolHeatIncludedInPrice property.
     * 
     */
    public void setPoolHeatIncludedInPrice(boolean value) {
        this.poolHeatIncludedInPrice = value;
    }

    /**
     * Gets the value of the dailyCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    /**
     * Sets the value of the dailyCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDailyCharge(BigDecimal value) {
        this.dailyCharge = value;
    }

    /**
     * Gets the value of the minimumNumberOfDaysToCharge property.
     * 
     */
    public int getMinimumNumberOfDaysToCharge() {
        return minimumNumberOfDaysToCharge;
    }

    /**
     * Sets the value of the minimumNumberOfDaysToCharge property.
     * 
     */
    public void setMinimumNumberOfDaysToCharge(int value) {
        this.minimumNumberOfDaysToCharge = value;
    }

}
