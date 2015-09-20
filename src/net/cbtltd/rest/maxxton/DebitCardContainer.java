
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DebitCardContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DebitCardContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DebitCards" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="DebitCard" type="{http://service.newyse.ws.services.newyse.maxxton/}WSDebitCard" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "DebitCardContainer", propOrder = {
    "debitCards"
})
public class DebitCardContainer {

    @XmlElement(name = "DebitCards")
    protected DebitCardContainer.DebitCards debitCards;

    /**
     * Gets the value of the debitCards property.
     * 
     * @return
     *     possible object is
     *     {@link DebitCardContainer.DebitCards }
     *     
     */
    public DebitCardContainer.DebitCards getDebitCards() {
        return debitCards;
    }

    /**
     * Sets the value of the debitCards property.
     * 
     * @param value
     *     allowed object is
     *     {@link DebitCardContainer.DebitCards }
     *     
     */
    public void setDebitCards(DebitCardContainer.DebitCards value) {
        this.debitCards = value;
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
     *         &lt;element name="DebitCard" type="{http://service.newyse.ws.services.newyse.maxxton/}WSDebitCard" maxOccurs="unbounded" minOccurs="0"/>
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
        "debitCard"
    })
    public static class DebitCards {

        @XmlElement(name = "DebitCard")
        protected List<WSDebitCard> debitCard;

        /**
         * Gets the value of the debitCard property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the debitCard property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDebitCard().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link WSDebitCard }
         * 
         * 
         */
        public List<WSDebitCard> getDebitCard() {
            if (debitCard == null) {
                debitCard = new ArrayList<WSDebitCard>();
            }
            return this.debitCard;
        }

    }

}
