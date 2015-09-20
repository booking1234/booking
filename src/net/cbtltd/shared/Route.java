/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

public class Route
extends Model {

	private String fromlocationid;
	private String tolocationid;
	private Double distance;

	public Route(){}

	public Route(Location fromlocation, Location tolocation, Double distance) {
		this.fromlocationid = fromlocation.getId();
		this.tolocationid = tolocation.getId();
		this.distance = distance;
	}

	public Service service() {return Service.LOCATION;}

	public String getFromlocationid() {
		return fromlocationid;
	}

	public void setFromlocationid(String fromlocationid) {
		this.fromlocationid = fromlocationid;
	}

	public String getTolocationid() {
		return tolocationid;
	}

	public void setTolocationid(String tolocationid) {
		this.tolocationid = tolocationid;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Route [fromlocationid=");
		builder.append(fromlocationid);
		builder.append(", tolocationid=");
		builder.append(tolocationid);
		builder.append(", distance=");
		builder.append(distance);
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
