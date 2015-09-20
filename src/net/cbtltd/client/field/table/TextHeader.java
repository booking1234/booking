/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field.table;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.Header;

/**
 * A Header containing String data rendered by a {@link TextCell}.
 */
public class TextHeader extends Header<String> {

	private String text;

	/**
	 * Construct a new TextHeader.
	 *
	 * @param text the header text as a String
	 */
	public TextHeader(String text) {
		super(new TextCell());
		this.text = text;
	}

	@Override
	public String getValue() {
		return text;
	}

	public void setValue(String text) {
		this.text = text;
	}
}
