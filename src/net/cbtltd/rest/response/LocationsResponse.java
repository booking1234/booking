package net.cbtltd.rest.response;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.rest.LabelId;
import net.cbtltd.rest.error.ApiResponse;

@XmlRootElement(name="locations_response")
public class LocationsResponse extends ApiResponse {
	
	public LocationsResponse() {
		super();
	}

	public LocationsResponse(String errorMessage) {
		super(errorMessage);
	}

	private List<LabelId> locations;

	@XmlElement(name = "locations")
	public List<LabelId> getLocations() {
		return locations;
	}

	public void setLocations(List<LabelId> locations) {
		this.locations = locations;
	}
}
