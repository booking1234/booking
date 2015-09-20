package net.cbtltd.rest.ru;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Block"
})
@XmlRootElement(name = "PropertyBlock")
public class PropertyBlock {

	@XmlAttribute(name="PropertyID")
	protected int PropertyID;
	protected List<Block> Block;
	
    public List<Block> getBlock() {
        if (Block == null) {
        	Block = new ArrayList<Block>();
        }
        return this.Block;
    }
    
	/**
	 * @return the code that uniquely identifies a property
	 */
	public int getPropertyID() {
		return PropertyID;
	}
	
	/**
	 * set PropertyID
	 */
	public void setPropertyID(int propertyid) {
		PropertyID = propertyid;
	}
}
