/**
 * @author	abookingnet
 * @see			License at http://razor-cloud.com/razor/License.html
 * @version		2.00
 */
package net.cbtltd.client.panel;

import net.cbtltd.client.field.AbstractField;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/** The Class AboutPopup is to display copyright, version and other such information. */
public class AboutPopup 
extends PopupPanel {

	/** Instantiates a new about pop up panel. */
	public AboutPopup() {
		super(true);

		final VerticalPanel form = new VerticalPanel();
		setWidget(form);
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
		final Label title = new Label(AbstractField.CONSTANTS.aboutLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AboutPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);
		HTML message = new HTML(AbstractField.CONSTANTS.aboutMessage());
		message.addStyleName(AbstractField.CSS.cbtAbstractField());
		form.add(message);

	}
}

