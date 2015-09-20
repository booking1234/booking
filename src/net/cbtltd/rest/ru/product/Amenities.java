package net.cbtltd.rest.ru.product;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Amenity"
})
@XmlRootElement(name = "Amenities")
public class Amenities {
	
	 protected List<Amenity> Amenity;
	 
	 public List<Amenity> getAmenity() {
	        if (Amenity == null) {
	            Amenity = new ArrayList<Amenity>();
	        }
	        return this.Amenity;
	    }

}
