/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.Date;

public class Bill {
	
	private String procedureid;
	private String operation;
	private String entitytype;
	private String entityid;
	private Double fixed;
	private Double quantity;
	private String unit;
	private Boolean output = false;
	private Date fromdate;
	private Date todate;
	private String description;

	public String getProcedureid() {
		return procedureid;
	}

	public void setProcedureid(String procedureid) {
		this.procedureid = procedureid;
	}

	public boolean noProcedureid() {
		return procedureid == null || procedureid.isEmpty();
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public boolean noOperation() {
		return operation == null || operation.isEmpty();
	}

	public String getEntitytype() {
		return entitytype;
	}

	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}

	public boolean noEntitytype() {
		return entitytype == null || entitytype.isEmpty();
	}

	public String getEntityid() {
		return entityid;
	}

	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}

	public boolean noEntityid() {
		return entityid == null || entityid.equalsIgnoreCase(Model.ZERO);
	}

	public Double getFixed() {
		return fixed;
	}

	public void setFixed(Double fixed) {
		this.fixed = fixed;
	}

	public boolean noFixed() {
		return fixed == null || fixed < 0;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public boolean noQuantity() {
		return quantity == null || quantity < 0;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public boolean noUnit() {
		return unit == null || unit.isEmpty();
	}

	public Boolean getOutput() {
		return output;
	}

	public void setOutput(Boolean output) {
		this.output = output;
	}

	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public boolean noFromdate() {
		return fromdate == null;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public boolean noTodate() {
		return todate == null;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isResource(String type) {
		return entitytype != null && entitytype.equalsIgnoreCase(type);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bill [procedureid=");
		builder.append(procedureid);
		builder.append(", operation=");
		builder.append(operation);
		builder.append(", entitytype=");
		builder.append(entitytype);
		builder.append(", entityid=");
		builder.append(entityid);
		builder.append(", fixed=");
		builder.append(fixed);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", output=");
		builder.append(output);
		builder.append(", fromdate=");
		builder.append(fromdate);
		builder.append(", todate=");
		builder.append(todate);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}

}
