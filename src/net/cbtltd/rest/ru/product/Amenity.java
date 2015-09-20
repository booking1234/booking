package net.cbtltd.rest.ru.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class Amenity {
	
	@XmlAttribute(name="Count")
	protected int count;
	@XmlValue
	protected int value;
	
	/**
	 * @return the number of each amenity
	 */
	public int getCount() {
		return this.count;
	}
	
	/**
	 * set count
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
	/**
	 * @return the amenity ID
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * set amenity id
	 */
	public void setValue(int amenity) {
		value = amenity;
	}

}
