package net.cbtltd.rest.ru.product;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "CompositionRoomID"
})
@XmlRootElement(name = "CompositionRooms")
public class CompositionRooms {

	protected List<CompositionRoomID> CompositionRoomID;
	
    public List<CompositionRoomID> getCompositionRoomID() {
        if (CompositionRoomID == null) {
        	CompositionRoomID = new ArrayList<CompositionRoomID>();
        }
        return this.CompositionRoomID;
    }
}
