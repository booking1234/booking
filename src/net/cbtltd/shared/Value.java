/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import net.cbtltd.shared.api.HasServiceResponse;

public class Value 
extends Relation 
implements HasServiceResponse {

	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";
	public enum Type {
		AssetPlace, 
		AuditName, 
		FeatureType, 
		MarketingStarter, 
		MarketingOutcome, 
		ProductFeature,
		LeaseRule,
		TaxName
	};

	private int status = 0;

	public Service service() {return Service.ATTRIBUTE;}

	public String getId() {
		return getHeadid();
	}

	public void setId(String id) {
		setHeadid(id);
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
		builder.append("Value [status=");
		builder.append(status);
		builder.append(", service()=");
		builder.append(service());
		builder.append(", getLink()=");
		builder.append(getLink());
		builder.append(", getHeadid()=");
		builder.append(getHeadid());
		builder.append(", getLineid()=");
		builder.append(getLineid());
		builder.append("]");
		return builder.toString();
	}

}
