/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.Date;

import net.cbtltd.shared.api.HasAlert;
import net.cbtltd.shared.api.HasData;
import net.cbtltd.shared.api.HasResponse;
import net.cbtltd.shared.api.HasService;

public class Alert implements HasAlert, HasService, HasResponse {

	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";

	public static final String NAME = "name";
	public static final String ENTITYTYPE = "entitytype";
	public static final String ENTITYID = "entityid";
	public static final String FROMDATE = "fromdate";
	public static final String TODATE = "todate";

	private String id;
	private String entitytype;
	private String entityid;
	private String name;
	private String language;
	private Date fromdate;
	private Date todate;
	private int status;

	
	public Service service() {return Service.ALERT;}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getEntitytype() {
		return entitytype;
	}

	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}

	public String getEntityid() {
		return entityid;
	}

	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}

	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
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
		builder.append("Alert [id=");
		builder.append(id);
		builder.append(", entitytype=");
		builder.append(entitytype);
		builder.append(", entityid=");
		builder.append(entityid);
		builder.append(", name=");
		builder.append(name);
		builder.append(", language=");
		builder.append(language);
		builder.append(", fromdate=");
		builder.append(fromdate);
		builder.append(", todate=");
		builder.append(todate);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
}