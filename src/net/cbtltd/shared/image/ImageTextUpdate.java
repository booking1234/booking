/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.image;

import net.cbtltd.shared.Service;
import net.cbtltd.shared.Text;

public class ImageTextUpdate extends Text {

	public ImageTextUpdate() {}
	
	public ImageTextUpdate (String id, String name, String notes, String language) {
		this.id = id;
		this.name = name;
		this.notes = notes;
		this.language = language;
	}
	
	public Service service() {
		return Service.IMAGETEXT;
	}
}
