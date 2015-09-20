/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.Date;

import net.cbtltd.shared.api.HasResponse;
import net.cbtltd.shared.api.HasService;

public class Data implements HasResponse, HasService {

	public enum Origin {CONSOLE, XML_JSON, JQUERY, DATABASE};
	
	private String id;
	private String organizationid;
	private String actorid;
	private String origin;
	private String datatype;
	private String dataid;
	private String state;
	private String tostring;
	private Date version;
	private int status;
	
	public Service service() {return Service.MONITOR;}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getActorid() {
		return actorid;
	}

	public void setActorid(String actorid) {
		this.actorid = actorid;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getDataid() {
		return dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTostring() {
		return tostring;
	}

	public void setTostring(String tostring) {
		this.tostring = NameId.trim(tostring, 5000);
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Data [id=");
		builder.append(id);
		builder.append(", organizationid=");
		builder.append(organizationid);
		builder.append(", actorid=");
		builder.append(actorid);
		builder.append(", origin=");
		builder.append(origin);
		builder.append(", datatype=");
		builder.append(datatype);
		builder.append(", dataid=");
		builder.append(dataid);
		builder.append(", state=");
		builder.append(state);
//		builder.append(", tostring=");
//		builder.append(tostring);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}

}