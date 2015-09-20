/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.ArrayList;

import net.cbtltd.client.Component;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.NameIdRequest;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Table;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/** The Class VshuttleField is to display and select a list of values from a list suggested items. */
public final class VshuttleField
extends AbstractField<ArrayList<String>>
implements Component, ClickHandler {

	private final NameIdRequest listRequest;

	private final VerticalPanel panel = new VerticalPanel();
	private Label label;
	private SuggestField availableField;
	private ListBox selectedList = new ListBox();
	private final FlowPanel buttons = new FlowPanel();
	private final Button select = new Button();
	private final Button remove = new Button();
	private final Button selectAll = new Button();
	private final Button removeAll = new Button();
	private String[] disabled;

	/**
	 * Instantiates a new vertical shuttle field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility of the field.
	 * @param action to populate the available field.
	 * @param label is the optional text to identify the field.
	 * @param count the count
	 * @param tab index of the field.
	 */
	public VshuttleField(
			HasComponents form,
			short[] permission,
			NameIdAction action,
			String label,
			int count,
			int tab) {

		initialize(panel, form, permission, CSS.cbtVshuttleField());
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		if(label != null) {
			this.label = new Label(label);
			this.label.addStyleName(CSS.cbtVshuttleFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}

		listRequest = new NameIdRequest(action) {
			public void receive(Table<NameId> response) {
				setNameIds(response.getValue());
				//fireChange(VshuttleField.this);
			}
		};

		availableField = new SuggestField(form, permission, action, null, count, tab);
		panel.add(availableField);

		select.setHTML("&gt;");
		select.addClickHandler(this);
		select.setTitle(CONSTANTS.vshuttleselectHelp());

		remove.setHTML("&lt;");
		remove.addClickHandler(this);
		remove.setTitle(CONSTANTS.vshuttleremoveHelp());

		selectAll.setHTML("&gt;&gt;");
		selectAll.addClickHandler(this);
		selectAll.setTitle(CONSTANTS.vshuttleselectallHelp());

		removeAll.setHTML("&lt;&lt;");
		removeAll.addClickHandler(this);
		removeAll.setTitle(CONSTANTS.vshuttleremoveallHelp());

		buttons.add(selectAll);
		buttons.add(select);
		buttons.add(remove);
		buttons.add(removeAll);
		panel.add(buttons);

		selectedList.setVisibleItemCount(count);
		selectedList.setMultipleSelect(true);
		panel.add(selectedList);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setTabIndex(tab);

		availableField.addStyleName(CSS.cbtVshuttleFieldAvailable());

		buttons.addStyleName(CSS.cbtVshuttleFieldButtons());
		select.addStyleName(CSS.cbtVshuttleFieldButton());
		remove.addStyleName(CSS.cbtVshuttleFieldButton());
		selectAll.addStyleName(CSS.cbtVshuttleFieldButton());
		removeAll.addStyleName(CSS.cbtVshuttleFieldButton());

		selectedList.addStyleName(CSS.cbtVshuttleFieldSelected());
	}

	/**
	 * Handles clicking of any of the buttons in the shuttle as follows:
	 * >> button transfers all available items to the selected list.
	 * > button transfers the selected available item, if any, to the selected list.
	 * < button transfers the selected selected item, if any, to the available list.
	 * << button transfers all selected items to the available list.
	 * Sets current type if types list box is clicked.
	 * 
	 * Fires a change event on any change.
	 * 
	 * @param event when clicked.
	 */
	@Override
	public void onClick(ClickEvent event) {
		boolean hasChanged = false;
		int i = 0;
		if (event.getSource() == select && !availableField.getName().equals(Model.BLANK)) {
			if (isDisabled(availableField.getValue())) {return;}
			selectedList.addItem(availableField.getName(), availableField.getValue());
			availableField.setFields(Model.BLANK);
			hasChanged = true;
		}
		else if (event.getSource() == remove && isSelected()) {
			if (isDisabled(selectedList.getValue(selectedList.getSelectedIndex()))) {return;}
			while((i = selectedList.getSelectedIndex()) >= 0) {
				selectedList.removeItem(i);
				hasChanged = true;
			}
		}
		else if (event.getSource() == selectAll) {listRequest.execute();}
		else if (event.getSource() == removeAll) {
			if (isDisabled(selectedList.getValue(0))) {return;}
			while (selectedList.getItemCount() > 0) {
				selectedList.removeItem(0);
				hasChanged = true;
			}
		}
		if (hasChanged) {fireChange(this);}
	}

	private boolean isDisabled(String value) {
		if (disabled == null){return false;}
		for (int i = 0; i < disabled.length; i++) {
			if (disabled[i].equalsIgnoreCase(value)){return true;}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#onReset()
	 */
	@Override
	public void onReset() {
		availableField.setFields(Model.BLANK);
		super.onReset();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#onRefresh()
	 */
	@Override
	public void onRefresh() {
		availableField.onRefresh();
	}

	/**
	 * Adds the button to create a new name ID pair.
	 *
	 * @param button the button.
	 */
	public void addButton(Widget button) {
		availableField.addButton(button);
	}

	/**
	 * Sets the type of the action to get available name ID pairs.
	 *
	 * @param type the new type.
	 */
	public void setType(String type) {
		availableField.setType(type);
	}

	/**
	 * Sets that values for all organizations are to be included in the list.
	 * If this is not set the list is populated with values only for the current organization.
	 *
	 * @param allOrganizations is true if the list contains values for all organizations.
	 */
	public void setAllOrganizations(boolean allOrganizations) {
		availableField.setAllOrganizations(allOrganizations);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int index){
		availableField.setTabIndex(index);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		select.setEnabled(isEnabled());
		selectAll.setEnabled(isEnabled());
		remove.setEnabled(isEnabled());
		removeAll.setEnabled(isEnabled());
	}

	/**
	 * Sets the disabled items.
	 *
	 * @param disabled the new disabled items.
	 */
	public void setDisabled(String[] disabled){
		this.disabled = disabled;
	}

	/**
	 * Sets the CSS style of the available item suggest box.
	 *
	 * @param style the CSS style of the available item suggest box.
	 */
	public void setAvailableStyle(String style) {
		availableField.setFieldStyle(style);
	}

	/**
	 * Sets the button style.
	 *
	 * @param style the new CSS style of the button.
	 */
	public void setButtonStyle(String style) {
		buttons.removeStyleName(CSS.cbtVshuttleFieldButtons());
		buttons.addStyleName(style);
	}

	/**
	 * Sets the CSS style of the selected item list.
	 *
	 * @param style the CSS style of the selected item list.
	 */
	public void setSelectedStyle(String style) {
		selectedList.addStyleName(style);
	}

	/**
	 * Sets the label style.
	 *
	 * @param style the new label style
	 */
	public void setLabelStyle(String style) {
		label.addStyleName(style);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setSecure(boolean)
	 */
	@Override
	public void setSecure(boolean secure) {
		super.setSecure(secure);
		setAvailableStyle(CSS.cbtVshuttleFieldSecure());
	}

	/**
	 * Sets if all items are enabled.
	 *
	 * @param enabled true if all items are enabled.
	 */
	public void setSelectAllEnabled(boolean enabled){
		selectAll.setEnabled(isEnabled());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focused){
		availableField.setFocus(focused);
	}

	/** De-selects all selected items. */
	public void deselect() {
		selectedList.clear();
	}

	/**
	 * Checks if an items are selected.
	 * 
	 * @return true, if any items are selected
	 */
	public boolean isSelected() {
		return selectedList.getItemCount() != 0;
	}

	/**
	 * Checks if this is the specified widget.
	 * 
	 * @param widget the specified widget.
	 * @return true, if this is the specified widget.
	 */
	@Override
	public boolean is(Widget widget){
		return (widget == this);
	}

	/**
	 * Sets the list of selected name ID pairs.
	 * 
	 * @param nameids to populate the selected list.
	 */
	public void setNameIds(ArrayList<NameId> nameids){
		availableField.setFields(Model.BLANK);
		selectedList.clear();
		if (nameids == null) {return;}
		for(NameId nameid : nameids) {selectedList.addItem(nameid.getName(), nameid.getId());}
		super.setChanged();
	}

	/**
	 * Gets the list of selected name ID pairs.
	 * 
	 * @return the list of selected name ID pairs.
	 */
	public ArrayList<NameId> getNameIds() {
		ArrayList<NameId> values = new ArrayList<NameId>(selectedList.getItemCount());
		for (int i = 0; i < selectedList.getItemCount(); i++) {values.add(new NameId(selectedList.getItemText(i), selectedList.getValue(i)));}
		return values;
	}

	/**
	 * Gets the comma delimited list of selected item names.
	 *
	 * @param length the length
	 * @return the comma delimited list of selected item names.
	 */
	public String getName(int length) {
		ArrayList<NameId> value = getNameIds();
		if (value == null || value.isEmpty()) {return null;}
		else {return NameId.trim(NameId.getCdlist(NameId.getNameList(value)).replace("'", ""), length);}
	}

	/**
	 * Selects the specified list of IDs.
	 * 
	 * @param value the specified list of IDs.
	 */
	public void setValue(ArrayList<String> value) {
		if (value == null || value.isEmpty()) {selectedList.clear();}
		else {
			listRequest.getAction().setName(Model.BLANK);
			listRequest.getAction().setIds(value);
			listRequest.getAction().setSuggested(false);
			listRequest.execute(true);
			listRequest.getAction().setIds(null);
		}
		super.setChanged();
	}

	/**
	 * Gets the list of selected IDs.
	 * 
	 * @return the list of selected IDs.
	 */
	public ArrayList<String> getValue() {
		ArrayList<String> values = new ArrayList<String>(selectedList.getItemCount());
		for (int i = 0; i < selectedList.getItemCount(); i++) {values.add(selectedList.getValue(i));}
		return values;
//		return NameId.getIdList(getNameIds());
	}

	/**
	 * Checks if no values are selected.
	 * 
	 * @return true, if no values are selected.
	 */
	public boolean noValue() {
		return selectedList.getItemCount() == 0;
	}

}
