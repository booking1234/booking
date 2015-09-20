package net.cbtltd.rest.ru.product;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Distance"
})
@XmlRootElement(name = "Distances")
public class Distances {
	
	 protected List<Distance> Distance;

	    /**
	     * Gets the value of the Distance.
	     * 
	     * <p>
	     * This accessor method returns a reference to the live list,
	     * not a snapshot. Therefore any modification you make to the
	     * returned list will be present inside the JAXB object.
	     * This is why there is not a <CODE>set</CODE> method for the picture property.
	     * 
	     * <p>
	     * For example, to add a new item, do as follows:
	     * <pre>
	     *    getDistance().add(newItem);
	     * </pre>
	     * 
	     * 
	     * <p>
	     * Objects of the following type(s) are allowed in the list
	     * {@link Distance }
	     * 
	     * 
	     */
	    public List<Distance> getDistance() {
	        if (Distance == null) {
	            Distance = new ArrayList<Distance>();
	        }
	        return this.Distance;
	    }

}
