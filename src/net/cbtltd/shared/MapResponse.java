/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;
import java.util.HashMap;

import net.cbtltd.shared.api.HasResponse;

public class MapResponse<T>
implements HasResponse {

	private int status = 0;
	private HashMap<T, ArrayList<T>> value;

	public MapResponse() {}

	public MapResponse(HashMap<T, ArrayList<T>> map) {
		this.value = map;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public HashMap<T, ArrayList<T>> getValue() {
		return value;
	}

	public void setValue(HashMap<T, ArrayList<T>> value) {
		this.value = value;
	}
	
	public boolean noValue() {
		return value == null || value.isEmpty();
	}
	
	public boolean hasValue() {
		return !noValue();
	}
	
}
