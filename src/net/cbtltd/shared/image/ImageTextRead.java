/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.image;

import net.cbtltd.shared.Service;
import net.cbtltd.shared.Text;

public class ImageTextRead extends Text {

	public ImageTextRead() {}

	public ImageTextRead (String id, String language) {
		this.id = id;
		this.language = language;
	}

	public ImageTextRead (String id, String name, String language) {
		this.id = id;
		this.name = name;
		this.language = language;
	}
	
	public Service service() {
		return Service.IMAGETEXT;
	}
}
