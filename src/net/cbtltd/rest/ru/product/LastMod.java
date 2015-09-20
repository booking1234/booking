package net.cbtltd.rest.ru.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class LastMod {
	
	@XmlAttribute(name="NLA")
	protected boolean NLA;
	@XmlValue
	protected String LastMod;
	
	/**
	 * @return the NLA value (The TRUE value means that property is no longer available, 
	 * should be removed from Agent system, NLA properties are automatically removed from XML feed after 1 week from the date of deactivation.)
	 */
	public boolean isNLA() {
		return NLA;
	}
	
	/**
	 * set NLA value
	 */
	public void setNLA(boolean value) {
		NLA = value;
	}
	
	/**
	 * @return The date of last modification
	 */
	public String getLastMod() {
		return LastMod;
	}
	
	/**
	 * set lastMod
	 */
	public void setLastMod(String lastMod) {
		LastMod = lastMod;
	}

}
