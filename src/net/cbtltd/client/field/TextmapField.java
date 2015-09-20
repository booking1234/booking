/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.List;

import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;

/** The Class TextmapField extends TextField to display and change a string value mapped to another value. */
public class TextmapField
extends TextField {

	private List<NameId> items;

	/**
	 * Instantiates a new text field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param items to populate the list mapping from the visible text and its value.
	 * @param label is the optional text to identify the field.
	 * @param tab index of the field.
	 */
	public TextmapField(
			HasComponents form,
			short[] permission,
			List<NameId> items,
			String label,
			int tab) {
		super(form, permission, label, tab);
		this.items = items;
	}

	/*
	 * Gets the mapped id of the specified name.
	 *
	 * @param name the specified name.
	 * @return the mapped id of the specified name.
	 */
	private String getId(String name) {
		for (NameId item : items) {
			if (item.hasName(name)) {return item.getId();}
		}
		return null;
	}

	/*
	 * Gets the mapped name of the specified id.
	 *
	 * @param id the specified id.
	 * @return the mapped name of the specified id.
	 */
	private String getName(String id) {
		for (NameId item : items) {
			if (item.hasId(id)) {return item.getName();}
		}
		return null;
	}
	
	/**
	 * Checks if this has the specified value.
	 *
	 * @param value the specified value.
	 * @return true, if this has the specified value.
	 */
	public boolean hasValue(String value) {
		return getId(field.getText()).equals(value);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public String getValue() {
		if (secure) {return Model.encrypt(getId(field.getText()));}
		else {return getId(field.getText());}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(String value) {
		if (secure) {field.setText(getName(Model.decrypt(value)));}
		else {field.setText(getName(value));}
		super.setChanged();
	}
}