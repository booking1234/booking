package net.cbtltd.rest.ru.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.cbtltd.rest.ru.Locations;
import net.cbtltd.rest.ru.Status;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Status",
    "Location"
})
@XmlRootElement(name = "Pull_GetLocationDetails_RS")
public class GetLocationDetails {

	@XmlElement(name="Status",required = true)
	protected Status Status;
//	@XmlElement(name="Locations")
//	protected Locations Locations;

	@XmlElementWrapper(name="Locations",required = true)
	@XmlElement(name="Location",required = true)
	protected List<net.cbtltd.rest.ru.Location> Location;

	/**
	 * get Location ID
	 * @return
	 */
    public List<net.cbtltd.rest.ru.Location> getLocation() {
        if (Location == null) {
        	Location = new ArrayList<net.cbtltd.rest.ru.Location>();
        }
        return this.Location;
    }

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return Status;
	}
	
	/**
	 * set status
	 */
	public void setStatus(Status status) {
		Status = status;
	}
	
/*	*//**
	 * @return the locations
	 *//*
	public Locations getLocations() {
		return Locations;
	}
	
	*//**
	 * set locations
	 *//*
	public void setLocations(Locations locations) {
		Locations = locations;
	} */
}
