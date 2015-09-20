package net.cbtltd.rest.common.utils.report;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Data")
public class Data {
	
	String type;

	String value;
	
	public Data() {
	}
	public Data(String type,String value) {
		this.type=type;
		this.value=value;
	}
	public Data(String value) {
		this.type="String";
		this.value=value;
	}
	
	
	
	/**
	 * @return the type
	 */
	@XmlAttribute(name="ss:Type")
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the value
	 */
	@XmlValue
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
}
