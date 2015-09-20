/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

public class Depreciation
extends Model {

    private String type;
    private String costaccountid;
    private String depreciationaccountid;
    private Double months;
    
	public Service service() {return Service.FINANCE;}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCostaccountid() {
		return costaccountid;
	}

	public void setCostaccountid(String costaccountid) {
		this.costaccountid = costaccountid;
	}

	public String getDepreciationaccountid() {
		return depreciationaccountid;
	}

	public void setDepreciationaccountid(String depreciationaccountid) {
		this.depreciationaccountid = depreciationaccountid;
	}

	public Double getMonths() {
		return months;
	}

	public void setMonths(Double months) {
		this.months = months;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Depreciation [type=");
		builder.append(type);
		builder.append(", costaccountid=");
		builder.append(costaccountid);
		builder.append(", depreciationaccountid=");
		builder.append(depreciationaccountid);
		builder.append(", months=");
		builder.append(months);
		builder.append(", organizationid=");
		builder.append(organizationid);
		builder.append(", status=");
		builder.append(status);
		builder.append(", state=");
		builder.append(state);
		builder.append(", values=");
		builder.append(values);
		builder.append(", attributes=");
		builder.append(attributemap);
		builder.append(", texts=");
		builder.append(texts);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
}