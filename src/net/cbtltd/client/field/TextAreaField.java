/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;
import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Text;

import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class TextAreaField is to manage multiple line text.
 */
public class TextAreaField
extends AbstractTextField<TextArea> {

	/**
	 * Instantiates a new text area field.
	 *
	 * @param form the form
	 * @param permission the permission
	 * @param tab the tab
	 */
	public TextAreaField(
			HasComponents form,
			short[] permission,
			int tab) {
		this(form, permission, null, null, tab);
	}

	/**
	 * Instantiates a new text area field.
	 *
	 * @param form the form
	 * @param permission the permission
	 * @param label the label
	 * @param tab the tab
	 */
	public TextAreaField(
			HasComponents form,
			short[] permission,
			String label,
			int tab) {
		this(form, permission, label, null, tab);
	}

	/**
	 * Instantiates a new text area field.
	 *
	 * @param form the form
	 * @param permission the permission
	 * @param label the label
	 * @param action the action
	 * @param tab the tab
	 */
	public TextAreaField(
			HasComponents form,
			short[] permission,
			String label,
			NameIdAction action,
			int tab) {

		super(new TextArea(), form, permission, label, action, tab);

		field.addStyleName(CSS.cbtTextAreaFieldField());
		field.setTabIndex(tab);
		panel.add(field);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#fireChange(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	protected void fireChange(Widget sender) {
		if (field == sender && field.getText().hashCode() != fired) {
			fired = field.getText().hashCode();
			fireChange(field);
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setEnabled(boolean)
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

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public String getValue() {
		String value = field.getText();
		if (hasFormat(Text.Type.Text)) {value = Text.stripHTML(value);}//CJM
		else {value = field.getText();}
		if (value.length() > maxlength) {value = value.substring(0, maxlength);}
		if (secure) {return Model.encrypt(value);}
		else {return value;}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(String value) {
		if (value == null) {value = Model.BLANK;}
		else if (secure) {value = Model.decrypt(value);}
		if (hasFormat(Text.Type.Text)) {field.setText(Text.stripHTML(value));}//CJM
		else {field.setText(value);}
		super.setChanged();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender){
		return (sender == field);
	}
}