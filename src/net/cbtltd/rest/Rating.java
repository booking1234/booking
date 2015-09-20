/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.shared.License;
import net.cbtltd.shared.api.HasXsl;

@XmlRootElement(name = "rating")
public class Rating
implements HasXsl {
    private String id;
    private String name;
    private String state;
    private Integer count;
    private Double average;
	private String xsl; //NB HAS GET&SET = private

	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}

	public boolean noId() {
		return getId() == null || getId().isEmpty();
	}
	
	public boolean hasId() {
		return !noId();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getAverage() {
		return average;
	}

	public void setAverage(Double average) {
		this.average = average;
	}

	//---------------------------------------------------------------------------------
	// Implements HasXsl interface
	//---------------------------------------------------------------------------------
	public String getXsl() {
		return xsl;
	}

	public void setXsl(String xsl) {
		this.xsl = xsl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Rating [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", state=");
		builder.append(state);
		builder.append(", count=");
		builder.append(count);
		builder.append(", average=");
		builder.append(average);
		builder.append(", xsl=");
		builder.append(xsl);
		builder.append("]");
		return builder.toString();
	}
}