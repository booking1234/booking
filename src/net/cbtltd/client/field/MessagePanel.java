/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.resource.FieldBundle;
import net.cbtltd.client.resource.FieldCssResource;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/** The Class MessagePanel is to display a pop up message. */
public class MessagePanel extends PopupPanel {
	private final FieldBundle BUNDLE = FieldBundle.INSTANCE;
	private final FieldCssResource CSS = BUNDLE.css();
	protected final VerticalPanel middle = new VerticalPanel();

	/**
	 * Instantiates a new message panel.
	 *
	 * @param level the level of the message which controls its colour.
	 * @param message the text to be displayed in the message pop up.
	 */
	public MessagePanel(Level level, String message) {
		super(true);
		CSS.ensureInjected();
		setStylePrimaryName(CSS.cbtMessagePopup());
		final FlowPanel panel = new FlowPanel();
		final FlowPanel top = new FlowPanel();
		top.addStyleName(CSS.cbtMessageTop());
		panel.add(top);
		middle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		middle.addStyleName(CSS.cbtMessageMiddle());
		panel.add(middle);
		final FlowPanel bottom = new FlowPanel();
		bottom.addStyleName(CSS.cbtMessageBottom());
		panel.add(bottom);
		
		HTML label = new HTML(message);
		label.addStyleName(CSS.cbtMessage());
		switch (level) {
		case ERROR: label.addStyleName(CSS.cbtMessageLevelError()); break;
		case TERSE: label.addStyleName(CSS.cbtMessageLevelTerse()); break;
		case VERBOSE: label.addStyleName(CSS.cbtMessageLevelVerbose()); break;
		case DEBUG: label.addStyleName(CSS.cbtMessageLevelDebug()); break;
		}
		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		middle.add(label);
		setWidget(panel);
	}
}
