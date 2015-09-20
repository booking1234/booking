package net.cbtltd.rest.ru.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "DestinationID",
    "DistanceUnitID",
    "DistanceValue"
})
@XmlRootElement(name = "Distance")
public class Distance {

	@XmlElement(required = true)
	protected int DestinationID;
	@XmlElement(required = true)
	protected int DistanceUnitID;
	@XmlElement(required = true)
	protected double DistanceValue;
	
	/**
	 * @return the destinationID
	 */
	public int getDestinationID() {
		return DestinationID;
	}
	
	/**
	 * set destinationID
	 */
	public void setDestinationID(int destinationID) {
		DestinationID = destinationID;
	}
	
	/**
	 * @return the distanceUnitID
	 */
	public int getDistanceUnitID() {
		return DistanceUnitID;
	}
	
	/**
	 * set distanceUnitID
	 */
	public void setDistanceUnitID(int distanceUnitID) {
		DistanceUnitID = distanceUnitID;
	}
	
	/**
	 * @return the distanceValue
	 */
	public double getDistanceValue() {
		return DistanceValue;
	}
	
	/**
	 * set distanceValue
	 */
	public void setDistanceValue(double distanceValue) {
		DistanceValue = distanceValue;
	}

}
