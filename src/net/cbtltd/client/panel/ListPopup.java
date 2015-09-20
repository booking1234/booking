/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.panel;

import java.util.ArrayList;

import net.cbtltd.client.HasValue;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.shared.NameId;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The Class ListPopup is to display and change list value instances.
 */
public class ListPopup
extends PopupPanel {

//	private static final FieldConstants CONSTANTS = GWT.create(FieldConstants.class);

	private static ListBox field =  new ListBox();
	private static HasValue<String> parentField; // field that invoked the popup
	
	/** Instantiates a new value pop up panel. */
	public ListPopup() {
		super(true);
		VerticalPanel form = new VerticalPanel();
		setWidget(form);
		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ListPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);
		form.add(field);
		field.addChangeHandler(new ChangeHandler() {
		public void onChange(ChangeEvent event) {
			parentField.setValue(field.getValue(field.getSelectedIndex()));
		}
	});

//		form.add(createCommands());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	private static ListPopup instance;
	
	/**
	 * Gets the single instance of ValuePopup.
	 *
	 * @return single instance of ValuePopup
	 */
	public static synchronized ListPopup getInstance() {
		if (instance == null) {instance = new ListPopup();}
		return instance;
	}

	/**
	 * Shows the panel for the specified value.
	 *
	 * @param key the key of the key-value pair.
	 * @param id the ID of the entity which has the value. 
	 * @param parentField the field that invoked the pop up panel.
	 */
	public void show(String label, ArrayList<NameId> items, HasValue<String> parentField) {
		setTitle(label);
		field.clear();
		for (NameId item : items) {field.addItem(item.getName(), item.getId());}
		center();
	}
}
