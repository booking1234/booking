/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.ota;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.rest.Constants;
import net.cbtltd.shared.api.HasXsl;
import net.cbtltd.soap.ota.server.OTAHotelSearchRS;

@XmlRootElement(name = "hotelSearchRS")
@XmlAccessorType(XmlAccessType.FIELD)
public class HotelSearchRS implements HasXsl {

	public OTAHotelSearchRS rs;
	private String xsl = Constants.NO_XSL; //NB HAS GET&SET = private
	
	public HotelSearchRS() {}
	
	public HotelSearchRS(OTAHotelSearchRS rs) {
		super();
		this.rs = rs;
	}
	
	//---------------------------------------------------------------------------------
	// Implements HasXsl interface
	//---------------------------------------------------------------------------------
	public String getXsl() {
		return xsl;
	}

	public void setXsl(String xsl) {
		this.xsl = xsl;
	}
}

