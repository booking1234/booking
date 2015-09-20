package net.cbtltd.rest.nextpax;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
		"houseID",
		"picture"
})
@XmlRootElement(name = "Pictures")
public class Pictures {

	@XmlElement(name = "HouseID", required = true)
	protected String houseID;
	@XmlElement(name = "Picture", required = true)
	protected List<Picture> picture;

	/**
	 * @return the houseID
	 */
	public String getHouseID() {
		return houseID;
	}

	/**
	 * @param houseID the houseID to set
	 */
	public void setHouseID(String houseID) {
		this.houseID = houseID;
	}

	/**
	 * @return the picture
	 */
	public List<Picture> getPicture() {
		if (picture == null) {
			picture = new ArrayList<Picture>();
		}
		return picture;
	}

}
