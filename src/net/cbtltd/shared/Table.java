/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;

import net.cbtltd.shared.api.HasResponse;

public class Table<T> 
implements HasResponse {
	
	private int status;
	private Integer datasize;
	private ArrayList<T> value;
	
	public Table() {}
	
	public Table(ArrayList<T> value) {
		this.value = value;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getDatasize() {
		return datasize == null ? value == null ? 0 : value.size() : datasize; //Math.max(datasize, value.size());
	}

	public void setDatasize(Integer datasize) {
		this.datasize = datasize;
	}

	public ArrayList<T> getValue() {
		return value;
	}

	public void setValue(ArrayList<T> value) {
		this.value = value;
	}

	public boolean noValue() {
		return value == null || value.isEmpty();
	}
	
	public boolean hasValue() {
		return !noValue();
	}
	
	public T getFirstValue() {
		return (value == null || value.isEmpty()) ? null : value.get(0);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Table [status=");
		builder.append(status);
		builder.append(", datasize=");
		builder.append(getDatasize());
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}
}