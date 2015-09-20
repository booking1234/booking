/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.response;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.rest.error.ApiResponse;

@XmlRootElement(name="calendar")
@XmlAccessorType(XmlAccessType.FIELD)
public class CalendarResponse extends ApiResponse {
	private CalendarElement[] items;
	
	public CalendarResponse() {
		super();
	}

	public CalendarElement[] getItems() {
		return items;
	}

	public void setItems(CalendarElement[] items) {
		this.items = items;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CalendarResponse [items=");
		builder.append(Arrays.toString(items));
		builder.append("]");
		return builder.toString();
	}
	
}
