/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.location;

import net.cbtltd.shared.Service;
import net.cbtltd.shared.api.HasService;

public class LocationPosition
implements HasService {

	private String id;
	
	public LocationPosition(String id) {
		this.id = id;
	}

	public Service service() {return Service.LOCATION;}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
