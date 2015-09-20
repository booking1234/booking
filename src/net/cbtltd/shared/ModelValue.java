/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;

import net.cbtltd.shared.api.HasServiceResponse;

public class ModelValue
implements HasServiceResponse {

	private String id; //model id
	private ArrayList<String> values;
	private int status = 0;
	
	public ModelValue() {}

	public ModelValue(String id, ArrayList<String> values) {
		super();
		this.id = id;
		this.values = values;
	}

	public Service service() {return Service.SESSION;}

	public String getId() {
		return id;
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public boolean noValue() {
		return values == null || values.isEmpty();
	}
	
	public boolean hasValues() {
		return !noValue();
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
		builder.append("ModelValue [id=");
		builder.append(id);
		builder.append(", values=");
		builder.append(values);
		builder.append(", status=");
		builder.append(status);
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}
	
}
