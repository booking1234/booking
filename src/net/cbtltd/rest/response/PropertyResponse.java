package net.cbtltd.rest.response;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.rest.Property;
import net.cbtltd.rest.error.ApiResponse;

@XmlRootElement(name="property_response")
public class PropertyResponse extends ApiResponse {
	
	private Property property;
	
	public PropertyResponse() {
		super();
	}

	public Property getProperty() {
		return property;
	}
	
	public void setProperty(Property property) {
		this.property = property;
	}
	
}
