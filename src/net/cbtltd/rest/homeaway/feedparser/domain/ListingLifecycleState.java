
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListingLifecycleState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ListingLifecycleState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ARCHIVED"/>
 *     &lt;enumeration value="CANCELLED"/>
 *     &lt;enumeration value="DELETED"/>
 *     &lt;enumeration value="ENABLED"/>
 *     &lt;enumeration value="EXPIRED"/>
 *     &lt;enumeration value="FRAUD_CHECK_FAILED"/>
 *     &lt;enumeration value="FRAUD_CHECK_SKIPPED"/>
 *     &lt;enumeration value="HIDDEN"/>
 *     &lt;enumeration value="INACTIVE_BY_CSR"/>
 *     &lt;enumeration value="INACTIVE_BY_OWNER_REQUEST"/>
 *     &lt;enumeration value="LISTING_CONTENT_COMPLETE"/>
 *     &lt;enumeration value="LISTING_FULFILLED"/>
 *     &lt;enumeration value="MINIMUM_CONTENT_SPECIFIED"/>
 *     &lt;enumeration value="NEW"/>
 *     &lt;enumeration value="PENDING"/>
 *     &lt;enumeration value="PENDING_XSELL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ListingLifecycleState")
@XmlEnum
public enum ListingLifecycleState {

    ARCHIVED,
    CANCELLED,
    DELETED,
    ENABLED,
    EXPIRED,
    FRAUD_CHECK_FAILED,
    FRAUD_CHECK_SKIPPED,
    HIDDEN,
    INACTIVE_BY_CSR,
    INACTIVE_BY_OWNER_REQUEST,
    LISTING_CONTENT_COMPLETE,
    LISTING_FULFILLED,
    MINIMUM_CONTENT_SPECIFIED,
    NEW,
    PENDING,
    PENDING_XSELL;

    public String value() {
        return name();
    }

    public static ListingLifecycleState fromValue(String v) {
        return valueOf(v);
    }

}
