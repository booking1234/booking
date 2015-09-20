/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import com.google.gwt.user.client.ui.ListBox;

/** The Class ShuttleBox is used by ShuttleField to display and select from its available and selected lists. */
public class ShuttleBox
extends ListBox {

	private static final String KEY_VALUE = "_KEY";

	/** Instantiates a new shuttle box. */
	public ShuttleBox() {
		super();
	}

	/**
	 * Instantiates a new multiple selection shuttle box.
	 *
	 * @param isMultipleSelect is true if more than one item can be selected at a time.
	 */
	public ShuttleBox(boolean isMultipleSelect) {
		super(isMultipleSelect);
	}

	/**
	 * Inserts the specified text-value pair into the list if it is not in the list.
	 *
	 * @param text the text of the item to insert into the list.
	 * @param value the value of the item to insert into the list.
	 */
	public void insertItem(String text, String value) {
		for (int i = 0; i < getItemCount(); i++) {
			if (getItemText(i).compareTo(text) > 0){
				super.insertItem(text, value, i);
				return;
			}
		}
		super.addItem(text, value);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.ListBox#removeItem(int)
	 */
	@Override
	public void removeItem(int index){
		super.removeItem(index);
	}

	/**
	 * Finds or inserts the specified key into the list if it is not in the list.
	 *
	 * @param key the key of the item to insert into the list.
	 * @return the value of the key.
	 */
	public int insertKey(String key) {
		for (int i = 0; i < getItemCount(); i++) {
			if (getItemText(i).compareTo(key) > 0 && isKey(i)){
				super.insertItem(key, KEY_VALUE, i);
				return i;
			}
		}
		super.addItem(key, KEY_VALUE);
		return getItemCount();
	}

	/**
	 * Inserts the specified text-value pair for the specified key.
	 *
	 * @param key the key associated with the text-value pair.
	 * @param text the text of the item to insert into the list.
	 * @param value the value of the item to insert into the list.
	 */
	public void insertKeyItem(String key, String text, String value) {
		int i;
		for (i = 0; i < getItemCount(); i++) {
			if (getItemText(i).equals(key) && isKey(i)) {
				i++;
				while (i < getItemCount() && !isKey(i)) {
					if (getItemText(i).compareTo(text) > 0){
						insertItem(text, value, i);
						return;
					}
					i++;
				}
				insertItem(text, value, i);
				return;
			}
		}
		i = insertKey(key);
		insertItem(text, value, ++i);
	}

	/**
	 * Gets the key at the specified index.
	 *
	 * @param index the specified index.
	 * @return the key at the specified index.
	 */
	public String getKey(int index) {
		while (index >= 0) {
			if (isKey(index)){return getValue(index);}
			index--;
		}
		return null;
	}

	/**
	 * Gets the index of the specified key.
	 *
	 * @param key the specified key.
	 * @return the index of the specified key.
	 */
	public int getKeyIndex(String key) {
		for (int i = 0; i < getItemCount(); i++) {
			if (getItemText(i).equals(key) && isKey(i)) {return i;}
		}
		return -1;
	}

	/**
	 * Checks if the specified index is a key.
	 *
	 * @param index the specified index.
	 * @return true, if the specified index is a key.
	 */
	public boolean isKey(int index) {
		return getValue(index).equalsIgnoreCase(KEY_VALUE);
	}
}
//	/**
//	 * Clean.
//	 */
//	private void clean(){
//		int index = getItemCount() - 1;
//		if (index < 0){return;}
//		if (isKey(index)) removeItem(index--);
//		for (; index > 0; index--) {
//			if (isKey(index) && isKey(index-1)) {removeItem(index-1);}
//		}
//	}
//}