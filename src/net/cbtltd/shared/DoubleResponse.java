/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

public class DoubleResponse
extends AbstractResponse {

	protected Double value;
	
	public Double getValue() {
		return value == null ? 0.0 : value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	public boolean noValue() {
		return value == null;
	}
	
	public boolean hasValue() {
		return !noValue();
	}
}
