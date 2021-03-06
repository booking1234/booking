//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.08.13 at 12:56:35 PM CAT 
//


package net.cbtltd.rest.interhome.accommodation;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.cbtltd.rest.interhome package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Theme_QNAME = new QName("", "theme");
    private final static QName _Attribute_QNAME = new QName("", "attribute");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.cbtltd.rest.interhome
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Accommodations }
     * 
     */
    public Accommodations createAccommodations() {
        return new Accommodations();
    }

    /**
     * Create an instance of {@link Pictures }
     * 
     */
    public Pictures createPictures() {
        return new Pictures();
    }

    /**
     * Create an instance of {@link Accommodation }
     * 
     */
    public Accommodation createAccommodation() {
        return new Accommodation();
    }

    /**
     * Create an instance of {@link Attributes }
     * 
     */
    public Attributes createAttributes() {
        return new Attributes();
    }

    /**
     * Create an instance of {@link Themes }
     * 
     */
    public Themes createThemes() {
        return new Themes();
    }

    /**
     * Create an instance of {@link Picture }
     * 
     */
    public Picture createPicture() {
        return new Picture();
    }

    /**
     * Create an instance of {@link Distance }
     * 
     */
    public Distance createDistance() {
        return new Distance();
    }

    /**
     * Create an instance of {@link Geodata }
     * 
     */
    public Geodata createGeodata() {
        return new Geodata();
    }

    /**
     * Create an instance of {@link Distances }
     * 
     */
    public Distances createDistances() {
        return new Distances();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "theme")
    public JAXBElement<String> createTheme(String value) {
        return new JAXBElement<String>(_Theme_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "attribute")
    public JAXBElement<String> createAttribute(String value) {
        return new JAXBElement<String>(_Attribute_QNAME, String.class, null, value);
    }

}
