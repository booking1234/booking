/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import java.util.Collection;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import net.cbtltd.shared.License;

public class Values {

	@XStreamImplicit(itemFieldName="value")
	public Collection<String> value;

	public Values(){}
	
	public Values(Collection<String> value){
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Values [value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}
}
