/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.form;

import com.google.gwt.user.client.ui.Label;

/** The Class Tab extends Label to include an index. */
public class Tab extends Label {
	private final int index;

	/**
	 * Instantiates a new tab.
	 *
	 * @param index the index of the tab.
	 * @param text the text to be displayed.
	 */
	public Tab(int index, String text) {
		this.index = index;
		setText(text);
	}

	/**
	 * Gets the index of this tab.
	 *
	 * @return the index  of this tab.
	 */
	public int getIndex() {
		return index;
	}
}
