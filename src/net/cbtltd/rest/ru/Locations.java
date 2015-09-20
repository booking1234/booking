package net.cbtltd.rest.ru;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Location"
})
@XmlRootElement(name = "Locations")
public class Locations {

    protected List<Location> Location;
    
    public List<Location> getLocation() {
        if (Location == null) {
        	Location = new ArrayList<Location>();
        }
        return this.Location;
    }
}
