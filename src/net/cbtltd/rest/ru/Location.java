package net.cbtltd.rest.ru;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class Location {
	
	@XmlValue
	protected String name;
	@XmlAttribute(name="LocationID")
	protected int locationid;
	@XmlAttribute(name="LocationTypeID")
	protected int locationtype;
	@XmlAttribute(name="ParentLocationID")
	protected int parentlocationid;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getLocationid() {
		return locationid;
	}
	
	public void setLocationid(int locationid) {
		this.locationid = locationid;
	}
	
	public int getLocationtype() {
		return locationtype;
	}
	
	public void setLocationtype(int locationtype) {
		this.locationtype = locationtype;
	}
	
	public int getParentlocationid() {
		return parentlocationid;
	}
	
	public void setParentlocationid(int parentlocationid) {
		this.parentlocationid = parentlocationid;
	}
}
