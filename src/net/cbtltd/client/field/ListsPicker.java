/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.List;

import net.cbtltd.client.resource.FieldBundle;
import net.cbtltd.client.resource.FieldCssResource;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class ListsPicker.
 */
public class ListsPicker
extends PopupPanel
implements ChangeHandler {

	private static final FieldCssResource CSS = FieldBundle.INSTANCE.css();
	private ListsField field;
	private Grid grid;
	private ListBox[] lists;
	private boolean addBlankItem;

	/**
	 * Instantiates a new lists picker.
	 *
	 * @param field the field
	 * @param labels the labels
	 * @param addBlankItem the add blank item
	 */
	public ListsPicker(
			ListsField field,
			String[] labels,
			boolean addBlankItem) {

		super(true);

		this.field = field;
		this.addBlankItem = addBlankItem;
		setStylePrimaryName(CSS.cbtListsFieldPopup());

		grid = new Grid(labels.length, 2);

		add(grid);
		lists = new ListBox[labels.length];
		for (int i = 0; i < lists.length; i++) {
			grid.setHTML(i, 0, labels[i]);
			lists[i] = new ListBox();
			lists[i].addChangeHandler(this);
			//lists[i].setWidth(width + "px");
			lists[i].addStyleName(CSS.cbtListsFieldPopupField());
			grid.setWidget(i, 1, lists[i]);
		}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ChangeHandler#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent event) {
		for (int level = 0; level < lists.length; level++) {
			if (event.getSource() == lists[level]) {
				NameId value = null;
				int index = lists[level].getSelectedIndex();
				if (index >= 0){value = new NameId(lists[level].getItemText(index),lists[level].getValue(index));}
				field.setValueAndFireChange(level, value);
				if (level >= lists.length - 1){hide(); return;}
				while (++level < lists.length){lists[level].setSelectedIndex(0);}
				return;
			}
		}
	}

	/**
	 * Reset.
	 */
	public void reset() {
		for (int i = 0; i < lists.length; i++){
			if (lists[i].getItemCount() > 0) {lists[i].setSelectedIndex(0);}
		}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.PopupPanel#show()
	 */
	@Override
	public void show() {
		field.setPopupLists();
		super.show();
	}

	/**
	 * Refreshes the list at the specified level and selects the specified item value from list of values.
	 *
	 * @param level the level of the list.
	 * @param nameids the list of name ID pairs to be refreshed.
	 * @param value the value to be selected.
	 */
	public void setList(int level, List<NameId> nameids, String value){
		if (nameids == null) {return;}
		lists[level].clear();
		if (addBlankItem) {lists[level].addItem(Model.BLANK, Model.ZERO);}
		for (NameId nameid : nameids) {lists[level].addItem(nameid.getName(), nameid.getId());}

		for (int i = 0;i < lists[level].getItemCount();i++){
			if (lists[level].getValue(i).equalsIgnoreCase(value)){
				lists[level].setSelectedIndex(i);
				return;
			}
		}
	}
}
