/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.AbstractRequest;
import net.cbtltd.client.AbstractRoot;
import net.cbtltd.shared.api.HasResetId;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/** The Class MessagePopup is to display a pop up message with buttons. */
public class MessagePopup extends PopupPanel {

	private static final Label label = new Label();
	private static final Button okButton = new Button();
	private static final Button cancelButton = new Button();
	private AbstractRequest request;
	private int index;
	private HasResetId hasid;

	private MessagePopup() {
		super(false, true);
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
		setGlassEnabled(true);

		final VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setWidget(panel);

		//-----------------------------------------------
		// Message label - displays message text.
		//-----------------------------------------------
		label.addStyleName(AbstractField.CSS.cbtMessage());
		label.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		addStyleName(AbstractField.CSS.cbtMessageLevelVerbose());
		panel.add(label);

		final HorizontalPanel buttons = new HorizontalPanel();
		panel.add(buttons);

		//-----------------------------------------------
		// OK button - executes request if not null or renders form with specified index, if any.
		//-----------------------------------------------
		okButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		okButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (request != null) {request.execute();}
				else if (index >= 0) {AbstractRoot.render(index, hasid);}
				MessagePopup.this.hide();
			}
		});
		buttons.add(okButton);

		//-----------------------------------------------
		// Cancel button - cancels request if not null.
		//-----------------------------------------------
		cancelButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (request != null) {request.cancel();}
				MessagePopup.this.hide();
			}
		});
		buttons.add(cancelButton);
	}

	private static MessagePopup instance;

	/**
	 * Gets the single instance of MessagePopup.
	 *
	 * @return the single MessagePopup instance.
	 */
	public static synchronized MessagePopup getInstance() {
		if (instance == null) {instance = new MessagePopup();}
		return instance;
	}

	/**
	 * Show the message with yes and no buttons.
	 *
	 * @param text the text to be displayed in the message.
	 * @param request the request to be executed when the yes button is clicked.
	 */
	public void showYesNo(String text, AbstractRequest request) {
		this.request = request;
		this.index = -1;
		okButton.setText(AbstractField.CONSTANTS.allYes());
		cancelButton.setText(AbstractField.CONSTANTS.allNo());
		label.setText(text);
		center();
	}

	/**
	 * Show the message with yes and no buttons.
	 *
	 * @param text the text to be displayed in the message.
	 * @param index the index
	 * @param id the id
	 */
	public void showYesNo(String text, int index, HasResetId hasid) {
		this.request = null;
		this.index = index;
		this.hasid = hasid;
		okButton.setText(AbstractField.CONSTANTS.allYes());
		cancelButton.setText(AbstractField.CONSTANTS.allNo());
		label.setText(text);
		center();
	}
}
