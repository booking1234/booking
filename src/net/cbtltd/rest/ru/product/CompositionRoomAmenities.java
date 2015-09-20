package net.cbtltd.rest.ru.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	    "Amenities"
	})
@XmlRootElement(name = "CompositionRoomAmenities")
public class CompositionRoomAmenities {
	
	@XmlAttribute(name="CompositionRoomID")
	protected int CompositionRoomID;
	@XmlElement
	protected Amenities Amenities;
	
	/**
	 * @return the compositionRoomID
	 */
	public int getCompositionRoomID() {
		return CompositionRoomID;
	}
	
	/**
	 * set compositionRoomID
	 */
	public void setCompositionRoomID(int compositionRoomID) {
		CompositionRoomID = compositionRoomID;
	}
	
	/**
	 * @return the collection of amenities available in a given room
	 */
	public Amenities getAmenities() {
		return Amenities;
	}
	
	/**
	 * @param amenities the amenities to set
	 */
	public void setAmenities(Amenities amenities) {
		Amenities = amenities;
	}

}
