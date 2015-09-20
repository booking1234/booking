/**
 * @author	abookingnet
 * @see			License at http://razor-cloud.com/razor/License.html
 * @version		2.00
 */
package net.cbtltd.client.panel;

import net.cbtltd.client.field.AbstractField;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * The Class LoadingPopup is to display a loading icon.
 * http://www.ajaxload.info/ for loader icons
 */
public class LoadingPopup 
extends PopupPanel {
	public LoadingPopup() {
		super(true);
		setWidget(new Image(AbstractField.BUNDLE.loader()));
		setStylePrimaryName(AbstractField.CSS.cbtAbstractLoading());
	}

	private static LoadingPopup instance;
	public static synchronized LoadingPopup getInstance() {
		if (instance == null) {instance = new LoadingPopup();}
		return instance;
	}
}
