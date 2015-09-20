/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.Date;

import net.cbtltd.shared.api.HasResponse;

import com.google.gwt.user.client.rpc.IsSerializable;

public class VisualizationData 
implements HasResponse, IsSerializable {

	private String name;
	private String category;
	private Date date;
	private double x;
	private double y;
	private int status = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getX(boolean reversed) {
		if (date == null){
			if (reversed){return -x;}
			else{return x;}
		}
		else {return date.getTime();}
	}

	public double getX() {
		if (date == null){return x;}
		else {return date.getTime();}
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY(boolean inverted) {
		if (inverted){return -y;}
		else{return y;}
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
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
		builder.append("VisualizationData [name=");
		builder.append(name);
		builder.append(", category=");
		builder.append(category);
		builder.append(", date=");
		builder.append(date);
		builder.append(", x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append("]");
		return builder.toString();
	}
}
