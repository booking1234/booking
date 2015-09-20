package net.cbtltd.rest.ru.product;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "CompositionRoomAmenities"
})
@XmlRootElement(name = "CompositionRoomsAmenities")
public class CompositionRoomsAmenities {

	protected List<CompositionRoomAmenities> CompositionRoomAmenities;
	
    public List<CompositionRoomAmenities> getCompositionRoomAmenities() {
        if (CompositionRoomAmenities == null) {
        	CompositionRoomAmenities = new ArrayList<CompositionRoomAmenities>();
        }
        return this.CompositionRoomAmenities;
    }

}
