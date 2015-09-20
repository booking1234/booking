//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.08.13 at 12:56:35 PM CAT 
//


package net.cbtltd.rest.interhome.accommodation;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="code" type="{}accommodationCodeType"/>
 *         &lt;element name="name">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="80"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="country">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="15"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="region">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="place">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="zip" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="10"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="type">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="details">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="quality" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="brand" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="pax" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="sqm" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="floor" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="rooms" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="bedrooms" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="toilets" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="bathrooms" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element ref="{}geodata" minOccurs="0"/>
 *         &lt;element ref="{}attributes" minOccurs="0"/>
 *         &lt;element ref="{}distances" minOccurs="0"/>
 *         &lt;element ref="{}themes" minOccurs="0"/>
 *         &lt;element ref="{}pictures" minOccurs="0"/>
 *         &lt;element name="minrentalprice" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="13"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="maxrentalprice" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="13"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="picture1" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="picture2" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
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
    "code",
    "name",
    "text",
    "country",
    "region",
    "place",
    "zip",
    "type",
    "details",
    "quality",
    "brand",
    "pax",
    "sqm",
    "floor",
    "rooms",
    "bedrooms",
    "toilets",
    "bathrooms",
    "geodata",
    "attributes",
    "distances",
    "themes",
    "pictures",
    "minrentalprice",
    "maxrentalprice",
    "url",
    "picture1",
    "picture2"
})
@XmlRootElement(name = "accommodation")
public class Accommodation {

    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected String name;
    protected String text;
    @XmlElement(required = true)
    protected String country;
    @XmlElement(required = true)
    protected String region;
    @XmlElement(required = true)
    protected String place;
    protected String zip;
    @XmlElement(required = true)
    protected String type;
    @XmlElement(required = true)
    protected String details;
    protected short quality;
    protected String brand;
    protected Short pax;
    protected Short sqm;
    protected String floor;
    protected Short rooms;
    protected Short bedrooms;
    protected Short toilets;
    protected Short bathrooms;
    protected Geodata geodata;
    protected Attributes attributes;
    protected Distances distances;
    protected Themes themes;
    protected Pictures pictures;
    protected BigDecimal minrentalprice;
    protected BigDecimal maxrentalprice;
    @XmlSchemaType(name = "anyURI")
    protected String url;
    @XmlSchemaType(name = "anyURI")
    protected String picture1;
    @XmlSchemaType(name = "anyURI")
    protected String picture2;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the region property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the value of the region property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegion(String value) {
        this.region = value;
    }

    /**
     * Gets the value of the place property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlace() {
        return place;
    }

    /**
     * Sets the value of the place property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlace(String value) {
        this.place = value;
    }

    /**
     * Gets the value of the zip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the value of the zip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZip(String value) {
        this.zip = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the details property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets the value of the details property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetails(String value) {
        this.details = value;
    }

    /**
     * Gets the value of the quality property.
     * 
     */
    public short getQuality() {
        return quality;
    }

    /**
     * Sets the value of the quality property.
     * 
     */
    public void setQuality(short value) {
        this.quality = value;
    }

    /**
     * Gets the value of the brand property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the value of the brand property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrand(String value) {
        this.brand = value;
    }

    /**
     * Gets the value of the pax property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getPax() {
        return pax;
    }

    /**
     * Sets the value of the pax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setPax(Short value) {
        this.pax = value;
    }

    /**
     * Gets the value of the sqm property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getSqm() {
        return sqm;
    }

    /**
     * Sets the value of the sqm property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setSqm(Short value) {
        this.sqm = value;
    }

    /**
     * Gets the value of the floor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFloor() {
        return floor;
    }

    /**
     * Sets the value of the floor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFloor(String value) {
        this.floor = value;
    }

    /**
     * Gets the value of the rooms property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getRooms() {
        return rooms;
    }

    /**
     * Sets the value of the rooms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setRooms(Short value) {
        this.rooms = value;
    }

    /**
     * Gets the value of the bedrooms property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getBedrooms() {
        return bedrooms;
    }

    /**
     * Sets the value of the bedrooms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setBedrooms(Short value) {
        this.bedrooms = value;
    }

    /**
     * Gets the value of the toilets property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getToilets() {
        return toilets;
    }

    /**
     * Sets the value of the toilets property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setToilets(Short value) {
        this.toilets = value;
    }

    /**
     * Gets the value of the bathrooms property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getBathrooms() {
        return bathrooms;
    }

    /**
     * Sets the value of the bathrooms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setBathrooms(Short value) {
        this.bathrooms = value;
    }

    /**
     * Gets the value of the geodata property.
     * 
     * @return
     *     possible object is
     *     {@link Geodata }
     *     
     */
    public Geodata getGeodata() {
        return geodata;
    }

    /**
     * Sets the value of the geodata property.
     * 
     * @param value
     *     allowed object is
     *     {@link Geodata }
     *     
     */
    public void setGeodata(Geodata value) {
        this.geodata = value;
    }

    /**
     * Gets the value of the attributes property.
     * 
     * @return
     *     possible object is
     *     {@link Attributes }
     *     
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * Sets the value of the attributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Attributes }
     *     
     */
    public void setAttributes(Attributes value) {
        this.attributes = value;
    }

    /**
     * Gets the value of the distances property.
     * 
     * @return
     *     possible object is
     *     {@link Distances }
     *     
     */
    public Distances getDistances() {
        return distances;
    }

    /**
     * Sets the value of the distances property.
     * 
     * @param value
     *     allowed object is
     *     {@link Distances }
     *     
     */
    public void setDistances(Distances value) {
        this.distances = value;
    }

    /**
     * Gets the value of the themes property.
     * 
     * @return
     *     possible object is
     *     {@link Themes }
     *     
     */
    public Themes getThemes() {
        return themes;
    }

    /**
     * Sets the value of the themes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Themes }
     *     
     */
    public void setThemes(Themes value) {
        this.themes = value;
    }

    /**
     * Gets the value of the pictures property.
     * 
     * @return
     *     possible object is
     *     {@link Pictures }
     *     
     */
    public Pictures getPictures() {
        return pictures;
    }

    /**
     * Sets the value of the pictures property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pictures }
     *     
     */
    public void setPictures(Pictures value) {
        this.pictures = value;
    }

    /**
     * Gets the value of the minrentalprice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMinrentalprice() {
        return minrentalprice;
    }

    /**
     * Sets the value of the minrentalprice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMinrentalprice(BigDecimal value) {
        this.minrentalprice = value;
    }

    /**
     * Gets the value of the maxrentalprice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaxrentalprice() {
        return maxrentalprice;
    }

    /**
     * Sets the value of the maxrentalprice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaxrentalprice(BigDecimal value) {
        this.maxrentalprice = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the picture1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPicture1() {
        return picture1;
    }

    /**
     * Sets the value of the picture1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPicture1(String value) {
        this.picture1 = value;
    }

    /**
     * Gets the value of the picture2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPicture2() {
        return picture2;
    }

    /**
     * Sets the value of the picture2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPicture2(String value) {
        this.picture2 = value;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Accommodation [code=");
		builder.append(code);
		builder.append(", name=");
		builder.append(name);
		builder.append(", text=");
		builder.append(text);
		builder.append(", country=");
		builder.append(country);
		builder.append(", region=");
		builder.append(region);
		builder.append(", place=");
		builder.append(place);
		builder.append(", zip=");
		builder.append(zip);
		builder.append(", type=");
		builder.append(type);
		builder.append(", details=");
		builder.append(details);
		builder.append(", quality=");
		builder.append(quality);
		builder.append(", brand=");
		builder.append(brand);
		builder.append(", pax=");
		builder.append(pax);
		builder.append(", sqm=");
		builder.append(sqm);
		builder.append(", floor=");
		builder.append(floor);
		builder.append(", rooms=");
		builder.append(rooms);
		builder.append(", bedrooms=");
		builder.append(bedrooms);
		builder.append(", toilets=");
		builder.append(toilets);
		builder.append(", bathrooms=");
		builder.append(bathrooms);
		builder.append(", geodata=");
		builder.append(geodata);
		builder.append(", attributes=");
		builder.append(attributes);
		builder.append(", distances=");
		builder.append(distances);
		builder.append(", themes=");
		builder.append(themes);
		builder.append(", pictures=");
		builder.append(pictures);
		builder.append(", minrentalprice=");
		builder.append(minrentalprice);
		builder.append(", maxrentalprice=");
		builder.append(maxrentalprice);
		builder.append(", url=");
		builder.append(url);
		builder.append(", picture1=");
		builder.append(picture1);
		builder.append(", picture2=");
		builder.append(picture2);
		builder.append("]");
		return builder.toString();
	}
}
