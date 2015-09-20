package net.cbtltd.rest.ru.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class CompositionRoomID {
	
	@XmlAttribute(name="Count")
	protected int count;
	@XmlValue
	protected int value;
	
	public CompositionRoomID() {}
	
	public CompositionRoomID(int count, int value){
		this.count = count;
		this.value = value;
	}
	
	/**
	 * @return the Number of each room
	 */
	public int getCount() {
		return this.count;
	}
	
	/**
	 * set the Number of each room
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * set value
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
