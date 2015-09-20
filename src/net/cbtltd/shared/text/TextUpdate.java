/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.text;

import net.cbtltd.shared.Text;

public class TextUpdate extends Text {

	public TextUpdate() {}
	
	public TextUpdate (String id, String name, String notes, String language) {
		this.id = id;
		this.name = name;
		this.notes = notes;
		this.language = language;
	}
}
