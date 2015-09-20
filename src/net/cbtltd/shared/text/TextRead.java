/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.text;

import net.cbtltd.shared.Text;

public class TextRead extends Text {

	public TextRead() {}

	public TextRead (String id, String language) {
		this.id = id;
		this.language = language;
	}

	public TextRead (String id, String name, String language) {
		this.id = id;
		this.name = name;
		this.language = language;
	}
}
