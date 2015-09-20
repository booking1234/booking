/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/** The Class Relation models the relationship between entities. */
public class Relation 
implements IsSerializable {
	//TODO: convert to enum
	public static final String ACCOUNT_LOCATION = "Account Location";
	public static final String CONTACT_PARTY = "Contact Party";
	public static final String DESIGN_ROLE = "Design Role";
	public static final String EVENT_ASSET = "Event Asset";
	public static final String EVENT_CHECK = "Event Check";
	public static final String EVENT_PURCHASE = "Event Purchase";
	public static final String EVENT_RECEIPT = "Event Receipt";
	public static final String EVENT_PAYMENT = "Event Payment";
	public static final String EVENT_RATE = "Event Rate";
	public static final String EVENT_SALE = "Event Sale";
	public static final String EVENT_TYPE = "Event Type";
	
	public static final String ORG_PARTY_ = "ORG_PARTY_";
	public static final String ORGANIZATION_ACCOUNT = "Organization Account";
	public static final String ORGANIZATION_ASSET = "Organization Asset";
	public static final String ORGANIZATION_CURRENCY = "Organization Currency";
	public static final String ORGANIZATION_FINANCE = "Organization Finance";
	public static final String ORGANIZATION_LANGUAGE = "Organization Language";
	public static final String ORGANIZATION_LOCATION = "Organization Location";
	public static final String ORGANIZATION_PRODUCT = "Organization Product";
	public static final String ORGANIZATION_UNIT = "Organization Unit";
	public static final String ORGANIZATION_UNSPSC = "Organization Unspsc";
	
	public static final String PARTY_ACCOUNT = "Party Account";
	public static final String PARTY_ASSET = "Party Asset";
	public static final String PARTY_ATTRIBUTE = "Party Attribute";
	public static final String PARTY_COUNTRY = "Party Country";
	public static final String PARTY_CREATOR = "Party Creator";
	public static final String PARTY_CURRENCY = "Party Currency";
	public static final String PARTY_LOCATION = "Party Location";
	public static final String PARTY_PARTY = "Party Party";
	public static final String PARTY_PRODUCT = "Party Product";
	public static final String PARTY_ROLE = "Party Role";
	public static final String PARTY_TAX = "PARTY_TAX";
	public static final String PARTY_UNIT = "Party Unit";
	public static final String PARTY_VALUE = "Party Value";
	public static final String PARTY_WIDGET = "Party Widget";

	public static final String PRODUCT_ATTRIBUTE = "Product Attribute";
	public static final String PRODUCT_OTA_ATTRIBUTE = "Product Attribute";
	public static final String PRODUCT_FILE = "Product File";
	public static final String PRODUCT_TAX = "PRODUCT_TAX";
	public static final String PRODUCT_VALUE = "Product Value";

	public static final String RESERVATION_VALUE = "Reservation Value";

	public static final String TASK_RESOURCE = "Task Resource";
	
	private String link;
	private String headid;
	private String lineid;


	public Relation(){}

	public Relation(String link, String headid, String lineid) {
		this.link = link;
		this.headid = headid;
		this.lineid = lineid;
	}

	public Relation(Downloaded downloaded, String headid, String lineid) {
		this.link = downloaded.name();
		this.headid = headid;
		this.lineid = lineid;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public boolean noLink() {
		return link == null || link.isEmpty();
	}
	
	public String getHeadid() {
		return headid;
	}

	public void setHeadid(String headid) {
		this.headid = headid;
	}

	public boolean noHeadid() {
		return headid == null || headid.isEmpty();
	}
	
	public String getLineid() {
		return lineid;
	}

	public void setLineid(String lineid) {
		this.lineid = lineid;
	}
	
	public boolean noLineid() {
		return lineid == null || lineid.isEmpty();
	}
	
	public boolean noHeadidOrLineid() {
		return noHeadid() && noLineid();
	}
	
	public boolean isEmpty() {
		return link == null || link.isEmpty() || headid == null || headid.isEmpty() || lineid == null || lineid.isEmpty();
	}
}