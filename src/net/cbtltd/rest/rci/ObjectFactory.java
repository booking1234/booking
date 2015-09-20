
package net.cbtltd.rest.rci;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.cbtltd.rest.rci package. 
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

    private final static QName _ResortDiningInfoFeed_QNAME = new QName("", "resortDiningInfoFeed");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.cbtltd.rest.rci
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DiningInfoFeedEntry }
     * 
     */
    public DiningInfoFeedEntry createDiningInfoFeedEntry() {
        return new DiningInfoFeedEntry();
    }

    /**
     * Create an instance of {@link ResortDiningInfoFeed }
     * 
     */
    public ResortDiningInfoFeed createResortDiningInfoFeed() {
        return new ResortDiningInfoFeed();
    }

    /**
     * Create an instance of {@link ResortDiningInfo }
     * 
     */
    public ResortDiningInfo createResortDiningInfo() {
        return new ResortDiningInfo();
    }

    /**
     * Create an instance of {@link DiningInfoFeedEntry.Value }
     * 
     */
    public DiningInfoFeedEntry.Value createDiningInfoFeedEntryValue() {
        return new DiningInfoFeedEntry.Value();
    }

    /**
     * Create an instance of {@link ResortDiningInfoFeed.Entries }
     * 
     */
    public ResortDiningInfoFeed.Entries createResortDiningInfoFeedEntries() {
        return new ResortDiningInfoFeed.Entries();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResortDiningInfoFeed }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "resortDiningInfoFeed")
    public JAXBElement<ResortDiningInfoFeed> createResortDiningInfoFeed(ResortDiningInfoFeed value) {
        return new JAXBElement<ResortDiningInfoFeed>(_ResortDiningInfoFeed_QNAME, ResortDiningInfoFeed.class, null, value);
    }

}
