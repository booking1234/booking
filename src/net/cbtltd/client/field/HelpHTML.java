/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.field.AbstractField.Level;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.UIObject;

/**
 * The Class HelpHTML displays HTML help text near a field when its label is clicked.
 */
public class HelpHTML extends HTML {
	
	final String helpText;
	final UIObject targetUIObject;
	
	/**
	 * Instantiates a new HTML help message.
	 *
	 * @param html the HTML help text.
	 * @param help the help
	 * @param target the widget near to which the message is to be displayed.
	 */
	public HelpHTML (String html, String help, UIObject target) {
		super(html);
		helpText = help;
		targetUIObject = target;
		setStylePrimaryName(AbstractField.CSS.cbtAbstractField());
		addStyleName(AbstractField.CSS.cbtAbstractCursor());
		addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new MessagePanel(Level.VERBOSE, helpText).showRelativeTo(targetUIObject);
			}
		});
	}
}
