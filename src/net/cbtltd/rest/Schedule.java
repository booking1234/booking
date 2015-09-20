/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.shared.License;
import net.cbtltd.shared.api.HasXsl;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
@XStreamAlias("schedule")
@XmlRootElement(name="schedule")
@Description(value = "Availability schedule", target = DocTarget.RESPONSE)
public class Schedule implements HasXsl {
	public String locationid;
	public String message;
	@XStreamImplicit(itemFieldName="schedule")
	public Collection<ScheduleRow> row;
	private String xsl; //NB HAS GET&SET = private
	
	public Schedule() {}
	
	public Schedule(
			String locationid,
			String message,
			Collection<ScheduleRow> item, 
			String xsl) {
		this.locationid = locationid;
		this.message = message;
		this.row = item;
		this.xsl = xsl;
	}
	
	public String getXsl() {
		return xsl;
	}

	public void setXsl(String xsl) {
		this.xsl = xsl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Schedule [locationid=");
		builder.append(locationid);
		builder.append(", message=");
		builder.append(message);
		builder.append(", row=");
		builder.append(row);
		builder.append(", xsl=");
		builder.append(xsl);
		builder.append("]");
		return builder.toString();
	}

}
