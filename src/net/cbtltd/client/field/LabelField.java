/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.ArrayList;

import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class LabelField is to display the content of text objects from the TextService.
 * It translates text from the primary language into any language listed in the type list box.
 * It plays an audio version of the text in the target language, if available.
 */
public class LabelField
extends AbstractTextField<Label> {

	/**
	 * Instantiates a new label field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label text displayed in the header of the field.
	 * @param action to populate the type (language) list box using a NameId service.
	 * @param tab number of the field.
	 */
	public LabelField(
			HasComponents form,
			short[] permission,
			String label,
			NameIdAction action,
			int tab) {
		this(form, permission, label, null, action, tab);
	}

	/*
	 * Instantiates a new label field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label text displayed in the header of the field.
	 * @param value the text to be displayed if the TextService is not used.
	 * @param action to populate the type (language) list box using a NameId service.
	 * @param tab number of the field.
	 */
	private LabelField(
			HasComponents form,
			short[] permission,
			String label,
			String value,
			NameIdAction action,
			int tab) {
		super(new Label(), form, permission, label, action, tab);
		field.addStyleName(CSS.cbtLabelFieldField());
		field.setText(value);
	}

	/**
	 * Instantiates a new label field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label text displayed in the header of the field.
	 * @param types the list of name id pairs to populate the type (language) list box.
	 * @param tab number of the field.
	 */
	public LabelField(
			HasComponents form,
			short[] permission,
			String label,
			ArrayList<NameId> types,
			int tab) {
		super(new Label(), form, permission, label, types, tab);
		field.addStyleName(CSS.cbtLabelFieldField());
	}

	/**
	 * Sets the CSS style to format the field.
	 *
	 * @param style to format the field.
	 */
	public void setFieldStyle(String style) {
		field.addStyleName(style);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public String getValue() {
		String value = field.getText();
		if (secure) {return Model.encrypt(value);}
		else {return value;}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(String value) {
		if (value == null) {value = Model.BLANK;}
		else if (secure) {value = Model.decrypt(value);}
		field.setText(value);
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