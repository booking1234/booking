/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.field.AbstractField.Level;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.UIObject;

public class HelpLabel extends Label {
	final String helpText;
	final UIObject targetUIObject;
	
	/**
	 * Instantiates a new help message.
	 *
	 * @param label the help label.
	 * @param help the help text.
	 * @param target the widget near to which the message is to be displayed.
	 */
	public HelpLabel (String label, String help, UIObject target) {
		super(label);
		helpText = help;
		targetUIObject = target;
		setStylePrimaryName(AbstractField.CSS.cbtAbstractLabel());
		addStyleName(AbstractField.CSS.cbtAbstractCursor());
		addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new MessagePanel(Level.VERBOSE, helpText).showRelativeTo(targetUIObject);
			}
		});
	}
}
