/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.flipkey;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.cbtltd.shared.api.HasXsl;

@XmlRootElement(name = "Property")
//@XmlType(propOrder={"property_id", "name", "address", "details", "suitability", "amenities", "photos", "rates"})
public class Property
implements HasXsl {
	@XmlAttribute (name = "property_id")
	private String Property_id;
	@XmlAttribute (name = "manager_id")
	private String Manager_id;
	@XmlAttribute (name = "last_update")
	private Date lastUpdate = new Date();
	private String Name;
	private Address Address;
	private Details Details;
	private Suitability Suitability;
	private Amenities Amenities;
	private Photos Photos;
	private Rates Rates;
	private String xsl;
	
	public Property(){}
	
	public Property(String property_id){
		this.Property_id = property_id;
	}

	public Property(String property_id, String manager_id, String name, Address address,
			Details details, Suitability suitability, Amenities amenities,
			Photos photos, Rates rates, String xsl) {
		super();
		Property_id = property_id;
		Manager_id = manager_id;
		Name = name;
		Address = address;
		Details = details;
		Suitability = suitability;
		Amenities = amenities;
		Photos = photos;
		Rates = rates;
		this.xsl = xsl;
	}
	
	
	@XmlTransient
	public String getProperty_id() {
		return Property_id;
	}

	public void setProperty_id(String property_id) {
		this.Property_id = property_id;
	}

	@XmlTransient
	public String getManager_id() {
		return Manager_id;
	}

	public void setManager_id(String manager_id) {
		this.Manager_id = manager_id;
	}

	@XmlElement( name = "PropertyName" , required = true )
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	@XmlElement( name = "Address" , required = true )
	public Address getAddress() {
		return Address;
	}

	public void setAddress(Address address) {
		this.Address = address;
	}

	@XmlElement( name = "Details" , required = true )
	public Details getDetails() {
		return Details;
	}

	public void setDetails(Details details) {
		this.Details = details;
	}

	@XmlElement( name = "Suitability" , required = true )
	public Suitability getSuitability() {
		return Suitability;
	}

	public void setSuitability(Suitability suitability) {
		this.Suitability = suitability;
	}

	@XmlElement( name = "Amenities" , required = true )
	public Amenities getAmenities() {
		return Amenities;
	}

	public void setAmenities(Amenities amenities) {
		this.Amenities = amenities;
	}

	@XmlElement( name = "Photos" , required = true )
	public Photos getPhotos() {
		return Photos;
	}

	public void setPhotos(Photos photos) {
		this.Photos = photos;
	}

	@XmlElement( name = "Rates" , required = true )
	public Rates getRates() {
		return Rates;
	}

	public void setRates(Rates rates) {
		this.Rates = rates;
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
}
