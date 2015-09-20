/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.resource.richtexttoolbar.RichTextToolbar;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Text;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.Widget;

/** The Class RichTextField is to display and change formatted HTML (rich) text. */
public class RichTextField
extends AbstractTextField<RichTextArea>
implements ChangeHandler {

	/* The tool bar with the buttons to control the modes of the editor. */
	private RichTextToolbar toolbar = new RichTextToolbar(field);

	/**
	 * Instantiates a new rich text field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param tab index of the field.
	 */
	public RichTextField(
			HasComponents form,
			short[] permission,
			int tab) {
		this(form, permission, null, null, tab);
	}

	/**
	 * Instantiates a new rich text field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	public RichTextField(
			HasComponents form,
			short[] permission,
			String label,
			int tab) {
		this(form, permission, label, null, tab);
	}

	/**
	 * Instantiates a new rich text field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.
	 * @param action to populate the type (language) list box using a NameId service.
	 * @param tab index of the field.
	 */
	public RichTextField(
			HasComponents form,
			short[] permission,
			String label,
			NameIdAction action,
			int tab) {
		
		super(new RichTextArea(), form, permission, label, action, tab);

		field.setHTML(Model.BLANK);
		field.addStyleName(CSS.cbtRichTextFieldField());
		field.setTabIndex(tab);

		panel.add(toolbar);
		panel.add(field);
	}

	/**
	 * Fires a change event if the HTML is changed.
	 *
	 * @param widget the widget that sends the change.
	 */
	@Override
	protected void fireChange(Widget widget) {
		if (field == widget && field.getHTML().hashCode() != fired) {
			fired = field.getHTML().hashCode();
			fireChange(field);
		}
	}

	/**
	 * Handles change events.
	 *
	 * @param event when changed.
	 */
	@Override
	public void onChange(ChangeEvent event) {
		if (language == event.getSource()) {
//TODO:	check if the field value has changed before allowing the type (language) to be changed.
//			if (ifPopup(hasChanged(), HasComponents.Level.TERSE, CONSTANTS.errChangeOK())) {
//				type.setSelectedIndex(typeIndex);
//			}
//			else {
//				typeIndex = type.getSelectedIndex();
//				Text text = textRead.getParam();
//				if (text == null){return;}
//				text.setLanguage(type.getValue(typeIndex));
//				textRead.send();
//				fireChange(type);
//			}
		}
		else if (field == event.getSource()){fireChange(field);}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		field.setEnabled(isEnabled());
		toolbar.setVisible(isEnabled());
	}

	/**
	 * Sets the toolbar visible.
	 *
	 * @param visible if the toolbar is visible.
	 */
	public void setToolbarVisible(boolean visible){
		toolbar.setVisible(visible);
	}

	/**
	 * Sets the CSS style of the field value.
	 *
	 * @param style the CSS style of the field.
	 */
	public void setFieldStyle(String style) {
		field.addStyleName(style);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public String getValue() {
		if (secure) {return Model.encrypt(field.getHTML());}
		else if (hasFormat(Text.Type.Text)) {return field.getText();}//CJM
		else {return field.getHTML();}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(String value) {
		if (value == null) {field.setHTML(Model.BLANK);}
		else if (secure) {field.setHTML(Model.decrypt(value));}
		else if (hasFormat(Text.Type.Text)) {field.setText(value);}//CJM
		else {field.setHTML(value);}
		super.setChanged();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractTextField#hasValue(java.lang.String)
	 */
	public boolean hasValue(String value) {
		return field.getHTML().equals(value);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender){
		return (sender == field);
	}
}

