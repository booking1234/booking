/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.HasComponents;
import net.cbtltd.client.NameIdRequest;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.product.ProductUnspsc;

public class UnspscField
extends ListsField {

	private final NameIdRequest unspscName = new NameIdRequest(new ProductUnspsc()) {
		public void receive(Table<NameId> response) {
			int level = Integer.valueOf(getAction().getType());
			String id = null;
			if (values[level] != null) {id = values[level].getId();}
			popup.setList(level, response.getValue(), id);
		}				
	};

	/*****************************************************************************************
	 * 	Constructors
	 ****************************************************************************************/

	public UnspscField(
			HasComponents form,
			short[] permission,
			String label,
			String[] labels,
			boolean addBlankItem,
			int tab) {
		super(form, permission, label, labels, addBlankItem, tab);
	}

	/*****************************************************************************************
	 * Model - handles model refresh to current state
	 ****************************************************************************************/
	@Override
	public void onRefresh() {
		unspscName.getAction().setType(Model.ZERO);
		unspscName.execute();
	}

	/**
	 * Sets the text field from the selected values of the lists and fire an event to notify the form of the change.
	 * 
	 * @param level the level in the code that has changed.
	 * @param value the value of the new code.
	 */
	@Override
	public void setValueAndFireChange(int level, NameId value) {
		if (value == null || values == null || values.length < 4) {return;}
		values[level] = value;
		if (level < 3){
			unspscName.getAction().setType(String.valueOf(level + 1));
//TODO: use comma delimited?			unspscName.setList(Arrays.asList(values));
			unspscName.execute();
		}
		if (values[3] == null || values[3].getId().equals(Model.ZERO)) {return;}
		field.setText(values[3].getId());
		fireChange(field);
	}

	/**
	 * Set the selected values of the lists to the field value and refresh the lists.
	 */
	@Override
	public void setPopupLists() {
		if (field.getText().trim().isEmpty()){return;}
		unspscName.getAction().setType(Model.ZERO);
		unspscName.execute();
		for(int level = 1; level < values.length; level++) {
			String code = field.getText();
			String id = code.substring(0,level + level)  + "00000000".substring(level+level, 8);
			values[level - 1] = new NameId(id);
			unspscName.getAction().setType(String.valueOf(level));
			unspscName.execute();
		}
	}

	/*****************************************************************************************
	 * @return the field model
	 ****************************************************************************************/
//	public Model getModel() {
//		return (new NameId(getValue()));
//	}

	/*****************************************************************************************
	 * Set the text field to the specified value
	 * @param value
	 ****************************************************************************************/
	@Override
	public void setValue(String value) {
		field.setText(value);
		super.setChanged();
	}

	/*****************************************************************************************
	 * @return the text field value
	 ****************************************************************************************/
	@Override
	public String getValue() {
		return field.getText().trim();
	}

	/*****************************************************************************************
	 * @return true if the value is null or blank
	 ****************************************************************************************/
	public boolean isEmpty() {
		return (field == null || field.getText() == null  || getValue().equalsIgnoreCase(Model.BLANK));
	}
}
