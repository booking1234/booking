/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class ListsField is the abstract parent of UnspscField and other fields requiring multiple lists to display or change a value.
 */
public abstract class ListsField
extends AbstractField<String>
implements ClickHandler {

	private final FlowPanel panel = new FlowPanel();
	private Label label;
	
	/** The field to display the result of the list selections. */
	protected TextBox field = new TextBox();
	
	/** The pop up panel to display the lists from which options are selected. */
	protected ListsPicker popup;
	
	/** The default value. */
	//protected String defaultValue;
	
	/** The currently selected list values. */
	protected NameId[] values;

	/**
	 * Instantiates a new lists field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param labels is the array of text to identify the lists.  
	 * @param addBlankItem the flag to indicate it a blank item is to be added to the list.
	 * @param tab index of the field.
	 */
	public ListsField (
			HasComponents form,
			short[] permission,
			String label,
			String[] labels,
			boolean addBlankItem,
			int tab) {

		initialize(panel, form, permission, CSS.cbtListsField());

		if (label != null){
			this.label  = new Label(label);
			this.label.addStyleName(CSS.cbtListsFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}

		popup = new ListsPicker(this, labels, addBlankItem);

		values = new Model[labels.length];

		field.addStyleName(CSS.cbtListsFieldField());
		field.setText(Model.BLANK);
		field.setTabIndex(tab);
		field.setTitle(label);
		field.addChangeHandler(form);
		field.addClickHandler(this);
		panel.add(field);
		panel.add(lock);
		setVisible(true);
	}

	/**
	 * Handles click events.
	 *
	 * @param event when the field is clicked.
	 */
	@Override
	public void onClick(ClickEvent event){
		if (event.getSource() == field){showPopup();}
	}

	/* Shows the pop up panel with the selection lists. */
	private void showPopup() {
		//popup.setPopupPosition(this.getAbsoluteLeft()+150, this.getAbsoluteTop());
		//popup.show();
		popup.center();
	}

	/**
	 * Sets if the field is enabled.
	 *
	 * @param enabled is true if the field value can be changed.
	 */
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		field.setEnabled(isEnabled());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setDefaultValue(java.lang.Object)
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Sets the CSS style of the field.
	 *
	 * @param style the CSS style of the field.
	 */
	public void setFieldStyle(String style) {
		field.addStyleName(style);
	}

	/**
	 * Sets the CSS style of the label.
	 *
	 * @param style  the CSS style of the label.
	 */
	public void setLabelStyle(String style) {
		label.addStyleName(style);
	}

	/** Sets the half width CSS style of the list. */
	public void setFieldHalf(){
		setFieldStyle(CSS.cbtListsFieldHalf());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setSecure(boolean)
	 */
	@Override
	public void setSecure(boolean secure) {
		super.setSecure(secure);
		setFieldStyle(CSS.cbtListsFieldSecure());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focussed){
		field.setFocus(focussed);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tab){
		field.setTabIndex(tab);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender){
		return (sender == field);
	}

	/**
	 * Sets the text field from the selected values of the lists and fire an event to notify the form of the change.
	 * 
	 * @param level the level in the code that has changed.
	 * @param value the value of the new code.
	 */
	public abstract void setValueAndFireChange(int level, NameId value);

	/**
	 * Set the selected values of the lists to the field value and refresh the lists.
	 */
	public abstract void setPopupLists();

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public abstract void setValue(String value);

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public abstract String getValue();
}
