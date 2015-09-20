package net.cbtltd.rest.ru;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class Status {

	@XmlAttribute(name="ID")
	protected int ID;
	@XmlValue
	protected String value;
	
	/**
	 * @return the Status ID
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * set ID
	 */
	public void setID(int iD) {
		ID = iD;
	}
	
	/**
	 * @return the status text value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * set value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
}
