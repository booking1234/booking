
package net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="GetPoolHeatSettingsResult" type="{http://xml.ciirus.com/}PoolHeatSettings"/>
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
    "getPoolHeatSettingsResult"
})
@XmlRootElement(name = "GetPoolHeatSettingsResponse")
public class GetPoolHeatSettingsResponse {

    @XmlElement(name = "GetPoolHeatSettingsResult", required = true)
    protected PoolHeatSettings getPoolHeatSettingsResult;

    /**
     * Gets the value of the getPoolHeatSettingsResult property.
     * 
     * @return
     *     possible object is
     *     {@link PoolHeatSettings }
     *     
     */
    public PoolHeatSettings getGetPoolHeatSettingsResult() {
        return getPoolHeatSettingsResult;
    }

    /**
     * Sets the value of the getPoolHeatSettingsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link PoolHeatSettings }
     *     
     */
    public void setGetPoolHeatSettingsResult(PoolHeatSettings value) {
        this.getPoolHeatSettingsResult = value;
    }

}
