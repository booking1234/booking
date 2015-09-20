package net.cbtltd.rest.ru.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class PUID {

	@XmlAttribute(name="BuildingID")
	protected Integer BuildingID;
	@XmlValue
	protected String value;
	
	public PUID(){
		this.BuildingID = -1;
		this.value = "";
	}
	
	public PUID(String value){
		this.value = value;
		this.BuildingID = -1;
	}
	
	public PUID(String value, Integer BuildingID){
		this.value = value;
		this.BuildingID = -1;
	}
	
	/**
	 * @return the code that uniquely identifies a single building that containing the property
	 */
	public int getBuildingID() {
		return BuildingID;
	}
	
	/**
	 * set code that uniquely identifies a single building that containing the property, define -1 when you do not need assign property to a building
	 */
	public void setBuildingID(int buildingID) {
		BuildingID = buildingID;
	}
	
	/**
	 * @return the code that uniquely identifies a single property in uploader system
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * set the code that uniquely identifies a single property in uploader system
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
}
