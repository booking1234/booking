package net.cbtltd.rest.ru.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class ID {
	
	@XmlAttribute(name="BuildingID")
	protected Integer BuildingID;
	@XmlAttribute(name="BuildingName")
	protected String BuildingName;
	@XmlValue
	protected int value;
	
	public ID(){}
	
	public ID(int value){
		this.value = value;
	}
	
	/**
	 * @return the The code that uniquely identifies a single building that containing the property
	 */
	public int getBuildingID() {
		return BuildingID;
	}
	
	/**
	 * set buildingID attribute
	 */
	public void setBuildingID(int buildingID) {
		BuildingID = buildingID;
	}
	
	/**
	 * @return the building name
	 */
	public String getBuildingName() {
		return BuildingName;
	}
	
	/**
	 * set BuildingName attribute
	 */
	public void setBuildingName(String buildingName) {
		BuildingName = buildingName;
	}
	
	/**
	 * @return the id (code) value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * set id value
	 */
	public void setValue(int value) {
		this.value = value;
	}

}
