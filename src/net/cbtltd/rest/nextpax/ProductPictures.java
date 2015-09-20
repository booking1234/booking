package net.cbtltd.rest.nextpax;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
		"partner",
		"pictures"
})
@XmlRootElement(name = "PaxGeneratorHousePictures")
public class ProductPictures {

	@XmlElement(name = "Partner", required = true)
	protected String partner;
	@XmlElement(name = "Pictures", required = true)
	protected List<Pictures> pictures;

	/**
	 * @return the partner
	 */
	public String getPartner() {
		return partner;
	}

	/**
	 * @param partner the partner to set
	 */
	public void setPartner(String partner) {
		this.partner = partner;
	}

	/**
	 * @return the pictures
	 */
	public List<Pictures> getPictures() {
		if (pictures == null) {
			pictures = new ArrayList<Pictures>();
		}
		return this.pictures;
	}

}
