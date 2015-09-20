package net.cbtltd.rest.ru.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.cbtltd.rest.ru.PropertyBlock;
import net.cbtltd.rest.ru.Status;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Status",
    "PropertyBlock"
})
@XmlRootElement(name = "Pull_ListPropertyBlocks_RS")
public class ListPropertyBlocks {

	@XmlElement(required = true)
	protected Status Status;
	@XmlElement(required = true)
	protected PropertyBlock PropertyBlock;
	
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return Status;
	}
	
	/**
	 * set status
	 */
	public void setStatus(Status status) {
		Status = status;
	}
	
	/**
	 * @return the collection of property blocks
	 */
	public PropertyBlock getPropertyBlock() {
		return PropertyBlock;
	}
	
	/**
	 * set the collection of property blocks
	 */
	public void setPropertyBlock(PropertyBlock propertyBlock) {
		PropertyBlock = propertyBlock;
	}
}
