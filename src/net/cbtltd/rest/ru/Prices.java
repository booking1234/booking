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
    "Season"
})
@XmlRootElement(name = "Prices")
public class Prices {
	
	@XmlAttribute(name = "PropertyID")
	protected int propertyID;
	protected List<Season> Season;
	
    public List<Season> getSeason() {
        if (Season == null) {
        	Season = new ArrayList<Season>();
        }
        return this.Season;
    }
    
    public void setSeason(List<Season> seasonPrices) {
    	this.Season = seasonPrices;
    }

	/**
	 * @return the propertyID
	 */
	public int getPropertyID() {
		return propertyID;
	}

	/**
	 * set the propertyID
	 */
	public void setPropertyID(int propertyID) {
		this.propertyID = propertyID;
	}

}
