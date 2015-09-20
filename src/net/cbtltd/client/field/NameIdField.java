/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class NameIdField is to display and change name ID pairs.
 * The NameId type is widely used to link the human readable name to its computer friendly ID.
 */
public class NameIdField
extends AbstractField<NameId> {

	private final FlowPanel panel = new FlowPanel();
	private Label label;
	private TextBox field = new TextBox();
	private String id;
	
	/**
	 * Instantiates a new name id field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	public NameIdField(
			HasComponents form,
			short[] permission,
			String label,
			int tab) {

		initialize(panel, form, permission, CSS.cbtTextField());

		if (label != null){
			this.label  = new Label(label);
			this.label.addStyleName(CSS.cbtTextFieldLabel());
			panel.add(this.label);
		}

		field.addStyleName(CSS.cbtTextFieldField());
		field.setText(Model.BLANK);
		field.setTabIndex(tab);
		field.addChangeHandler(form);
		panel.add(field);
		panel.add(lock);
	}

	/**
	 * Sets if the field may be changed.
	 *
	 * @param enabled is true if the field may be changed.
	 */
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		field.setEnabled(isEnabled());
	}

	/**
	 * Sets the field style.
	 *
	 * @param style the new field style
	 */
	public void setFieldStyle(String style) {
		field.addStyleName(style);
	}

	/**
	 * Sets the CSS style of the field label.
	 *
	 * @param style the CSS style of the field label.
	 */
	public void setLabelStyle(String style) {
		if (label != null) {label.addStyleName(style);}
	}

	/** Sets the CSS style of the half width field. */
	public void setFieldHalf(){
		setFieldStyle(CSS.cbtTextFieldHalf());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setSecure(boolean)
	 */
	@Override
	public void setSecure(boolean secure) {
		super.setSecure(secure);
		setFieldStyle(CSS.cbtTextFieldSecure());
	}

	/**
	 * Sets the maximum length of the name.
	 *
	 * @param length the new maximum length of the name.
	 */
	public void setMaxLength(int length) {
		field.setMaxLength(length);
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

	/**
	 * Checks if there is no name.
	 *
	 * @return true, if there is no name.
	 */
	public boolean noName() {
		return field == null || field.getText().trim().isEmpty();
	}

	/**
	 * Checks if there is no ID.
	 *
	 * @return true, if there is no ID.
	 */
	public boolean noId() {
		return id == null || id.isEmpty();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#noValue()
	 */
	public boolean noValue() {
		return noName() || noId();
	}

	/**
	 * Checks if the field has the specified value.
	 *
	 * @param value the specified value.
	 * @return true, if the field has the specified value.
	 */
	public boolean hasValue(String value) {
		return field.getText().trim().equals(value);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public NameId getValue() {
		if (secure) {return new NameId(Model.encrypt(field.getText()), Model.encrypt(id));}
		else {return new NameId(field.getText(), id);}
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		if (secure) {return Model.encrypt(field.getText());}
		else {return field.getText();}
	}

	/**
	 * Gets the ID.
	 *
	 * @return the ID.
	 */
	public String getId() {
		if (secure) {return Model.encrypt(id);}
		else {return id;}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(NameId value) {
		if (secure) {field.setText(value == null ? null : Model.decrypt(value.getName()));
			id = (value == null) ? null : Model.decrypt(value.getId());
		}
		else {
			field.setText(value == null ? Model.BLANK : value.getName());
			id = (value == null) ? null : value.getId();
		}
		super.setChanged();
	}

	/**
	 * Sets the name.
	 *
	 * @param value the new name.
	 */
	public void setName(String value) {
		if (secure) {field.setText(Model.decrypt(value));}
		else {field.setText(value);}
		super.setChanged();
	}

	/**
	 * Sets the ID.
	 *
	 * @param value the new ID.
	 */
	public void setId(String value) {
		if (secure) {id = Model.decrypt(value);}
		else {id = value;}
		super.setChanged();
	}

	/**
	 * Checks if this is the specified widget.
	 *
	 * @param widget is the specified widget.
	 * @return true if this is the specified widget.
	 */
	@Override
	public boolean is(Widget widget){return (widget == field);}
}
