/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;

import net.cbtltd.server.integration.LocationService;
import net.cbtltd.shared.api.HasPosition;
import net.cbtltd.shared.api.HasResetId;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

public abstract class Entity
extends ModelTable
implements HasResetId, HasPosition {

//	public enum Type {Account, Asset, Feature, Finance, Party, Product, Reservation, Service}
	
	public static final String CURRENCY = "currency";
	public static final String UNIT = "unit";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String ALTITUDE = "altitude";
	@XStreamOmitField
	protected String type;
	@XStreamOmitField
	protected String entitytype;
	protected String locationid;
	protected Location location;
	protected String currency;
	@XStreamOmitField
	protected String language;
	@XStreamOmitField
	protected String unit;
	protected Double latitude;
	protected Double longitude;
	@XStreamOmitField
	protected Double altitude;
	protected ArrayList<Tax> taxes;
	
	public Entity() {}
	
	public String getResetid() {
		return id;
	}

	@Description(value = "The current type of the entity", target = DocTarget.METHOD)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean notType(String type) {
		return this.type == null || type == null || !this.type.equalsIgnoreCase(type);
	}
	
	public boolean hasType(String type) {
		return !notType(type);
	}

	public Entity(String entitytype) {
		this.entitytype = entitytype;
	}
	
	@Description(value = "The intrinsic type of the entity", target = DocTarget.METHOD)
	public String getEntitytype() {
		return entitytype;
	}

	@Description(value = "The default pricing unit of measure of the entity", target = DocTarget.METHOD)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public boolean noUnit() {
		return unit == null || unit.isEmpty();
	}

	@Description(value = "The default pricing currency of the entity", target = DocTarget.METHOD)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean noCurrency() {
		return currency == null || currency.isEmpty();
	}

	public boolean hasCurrency() {
		return !noCurrency();
	}

	public boolean hasCurrency(String currency) {
		return this.currency != null && currency != null && this.currency.equalsIgnoreCase(currency);
	}

	@Description(value = "Language of the entity descriptions", target = DocTarget.METHOD)
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean noLanguage() {
		return language == null || language.isEmpty();
	}

	public boolean hasLanguage() {
		return !noLanguage();
	}

	public boolean hasLanguage(String language) {
		return this.language != null && language != null && this.language.equalsIgnoreCase(language);
	}

	//---------------------------------------------------------------------------------
	// Implements HasPosition interface
	//---------------------------------------------------------------------------------
	@Description(value = "The location ID of the entity", target = DocTarget.METHOD)
	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public boolean noLocationid() {
		return locationid == null || locationid.isEmpty() || locationid.equals(Model.ZERO);
	}

	public boolean hasLocationid() {
		return !noLocationid();
	}

	public boolean noLatLng() {
		return latitude == null || latitude < -90.0 || latitude > 90.0
		||  longitude == null || longitude < -180.0 || longitude > 180.0; // || altitude == null;
	}
	
	public boolean hasLatLng() {
		return !noLatLng();
	}
	
	public boolean zeroLatLng() {
		return latitude == 0.0 && longitude == 0.0;
	}
	
	@Description(value = "The latitude of the entity", target = DocTarget.METHOD)
	public Double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	@Description(value = "The longitude of the entity", target = DocTarget.METHOD)
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	@Description(value = "The altitude of the entity in metres", target = DocTarget.METHOD)
	public Double getAltitude() {
		return altitude;
	}
	
	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	public ArrayList<Tax> getTaxes() {
		return taxes;
	}

	public void setTaxes(ArrayList<Tax> taxes) {
		this.taxes = taxes;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Entity [type=");
		builder.append(type);
		builder.append(", entitytype=");
		builder.append(entitytype);
		builder.append(", locationid=");
		builder.append(locationid);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", altitude=");
		builder.append(altitude);
		builder.append(", taxes=");
		builder.append(taxes);
		builder.append(", organizationid=");
		builder.append(organizationid);
		builder.append(", status=");
		builder.append(status);
		builder.append(", state=");
		builder.append(state);
		builder.append(", values=");
		builder.append(values);
		builder.append(", attributes=");
		builder.append(attributemap);
		builder.append(", texts=");
		builder.append(texts);
		builder.append(", images=");
		builder.append(imageurls);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}

	public Location getLocation() {
		
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
