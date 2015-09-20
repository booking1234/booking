/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.contract;

import net.cbtltd.shared.Service;
import net.cbtltd.shared.api.HasResponse;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MarketItem
implements IsSerializable, HasResponse {

	public static final String NAME = "name";
	public static final String STATE = "state";
	public static final String LOCATION_NAME = "locationname";
	public static final String PRODUCT_RATING = "productrating";
	public static final String DAY_PHONE = "dayphone";
	public static final String FAX_PHONE = "faxphone";
	public static final String EMAIL_ADDRESS = "emailaddress";
	public static final String WEB_ADDRESS = "webaddress";
	
	private String supplierid;
	private String customerid;
	private String suppliername;
	private String locationname;
	private String productrating;
	private String emailaddress;
	private String webaddress;
	private String dayphone;
	private String faxphone;
	private Integer productquantity;
	protected int status = 0;

	public MarketItem() {}
	
	public MarketItem(String supplierid, String customerid) {
		this.supplierid = supplierid;
		this.customerid = customerid;
	}
	
	public Service service() {return Service.CONTRACT;}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public boolean noSupplierid() {
		return supplierid == null || supplierid.isEmpty();
	}
	
	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public boolean noCustomerid() {
		return customerid == null || customerid.isEmpty();
	}
	
	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public String getLocationname() {
		return locationname;
	}

	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}

	public String getProductrating() {
		return productrating;
	}

	public void setProductrating(String productrating) {
		this.productrating = productrating;
	}

	public Integer getProductquantity() {
		return productquantity;
	}

	public void setProductquantity(Integer productquantity) {
		this.productquantity = productquantity;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getWebaddress() {
		return webaddress;
	}

	public void setWebaddress(String webaddress) {
		this.webaddress = webaddress;
	}

	public String getDayphone() {
		return dayphone;
	}

	public void setDayphone(String dayphone) {
		this.dayphone = dayphone;
	}

	public String getFaxphone() {
		return faxphone;
	}

	public void setFaxphone(String faxphone) {
		this.faxphone = faxphone;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("\nMarketItem ").append(super.toString());
		sb.append(" SupplierId ").append(getSupplierid());
		sb.append(" Location ").append(getLocationname());
		sb.append(" Rating ").append(getProductrating());
		sb.append(" EmailAddress ").append(getEmailaddress());
		sb.append(" WebAddress ").append(getWebaddress());
		sb.append(" DayPhone ").append(getDayphone());
		sb.append(" FaxPhone ").append(getFaxphone());
		sb.append(" MobilePhone ").append(getProductquantity());
		return sb.toString();
	}
}