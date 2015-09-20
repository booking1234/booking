/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import net.cbtltd.client.HasComponents;
import net.cbtltd.client.RemoteRequest;
import net.cbtltd.shared.AttributeMapAction;
import net.cbtltd.shared.License;
import net.cbtltd.shared.MapResponse;
import net.cbtltd.shared.NameId;

import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * The Class StackField is to select items from a value having multiple options.
 * Its primary use is to display and select the attributes of an entity and is an alternative to SelectorField.
 */
public class StackField
extends AbstractField<HashMap<String,ArrayList<String>>>
implements ClickHandler {

	private MapRequest mapRequest;
	private final VerticalPanel panel = new VerticalPanel();
	private Label label;
	private StackPanel field = new StackPanel();
	private String[] uniquekeys;

	/* Inner Class MapRequest extends RemoteRequest to fetch attribute maps to be rendered from the server. */
	private class MapRequest
	extends RemoteRequest<AttributeMapAction, MapResponse<NameId>> {

		private AttributeMapAction action;

		public MapRequest(AttributeMapAction action){this.action = action;}
		protected void receive(MapResponse<NameId> response) {setItems(response.getValue());}
		public void execute(AttributeMapAction action) {
			this.action = action;
			execute();
		}
		public boolean execute() {
			if (action == null || action.noKeys()) {return true;}
			super.send(action);
			return false;
		}
		public AttributeMapAction getAction() {return action;}
	}

	/**
	 * Instantiates a new image field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param action to be executed to populate the field from the server.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	public StackField(
			HasComponents form,
			short[] permission,
			AttributeMapAction action,
			String label,
			int tab) {

		initialize(panel, form, permission, CSS.cbtStackField());

		mapRequest = new MapRequest(action);

		if(label != null) {
			this.label = new Label(label);
			this.label.addStyleName(CSS.cbtStackFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}

		field.addStyleName(CSS.cbtStackFieldField());
		panel.add(field);
		setTabIndex(tab);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		if (isEnabled() && event.getSource() == this) {fireChange(this);}
	}

	/*
	 * Gets if the specified key may only have a single option.
	 * 
	 * @param key the specified key.
	 * @return true if key may only have a single option.
	 */
	private boolean isUniquekey(String key) {
		if (uniquekeys == null) {return false;}
		for (String uniquekey : uniquekeys) {
			if (uniquekey.equalsIgnoreCase(key.substring(0,3))) {return true;}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#onRefresh()
	 */
	@Override
	public void onRefresh() {
		mapRequest.execute();
	}

	/**
	 * Sets the array of keys for which only one option may be selected at a time.
	 *
	 * @param uniquekeys the new array of unique keys.
	 */
	public void setUniquekeys(String[] uniquekeys) {
		this.uniquekeys = uniquekeys;
	}

	/**
	 * Sets and executes the action to get the available key-option(s) from the server.
	 *
	 * @param action the new action to get the available key-option(s) from the server.
	 */
	public void setAction(AttributeMapAction action) {
		if (mapRequest != null) {mapRequest.execute(action);}
	}

	/** Refreshes the available key-option(s) from the server. */
	public void execute() {
		if (mapRequest != null) {mapRequest.execute();}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int index) {}

	/**
	 * Sets the CSS style of the field value.
	 *
	 * @param style the CSS style of the field.
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
		label.addStyleName(style);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setSecure(boolean)
	 */
	@Override
	public void setSecure(boolean secure) {
		super.setSecure(secure);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focused){
		//field.setFocus(focused);
	}

	/** De-selects all selected options for all keys. */
	public void deselect() {
		for (int index = 0; index < field.getWidgetCount(); index++) {
			VerticalPanel options = (VerticalPanel)field.getWidget(index);
			for (int row = 0; row < options.getWidgetCount(); row++) {
				CheckBox cb = (CheckBox) options.getWidget(row);
				if (cb == null) {Window.alert("StackField deselect " + row);}
				else {cb.setValue(false);}
			}
		}
	}

	/*
	 * De-selects all selected items having the specified key.
	 *
	 * @param key the specified key.
	 */
	private void deselect(String key) {
		for (int index = 0; index < field.getWidgetCount(); index++) {
			VerticalPanel options = (VerticalPanel)field.getWidget(index);
			if (key.substring(0,3).equalsIgnoreCase(options.getTitle())) {
				for (int row = 0; row < options.getWidgetCount(); row++) {
					CheckBox cb = (CheckBox) options.getWidget(row);
					if (cb == null) {Window.alert("StackField deselect " + row);}
					else {cb.setValue(false);}
				}
			}
		}
	}

	/**
	 * Renders the field from the specified map of available key-option(s) into the field.
	 *
	 * @param map the map of available key-option(s).
	 */
	private void setItems(HashMap<NameId, ArrayList<NameId>> map) {
		if (map == null) {return;}
		field.clear();
		ArrayList<NameId> keys = new ArrayList<NameId>(map.keySet());
		Collections.sort(keys);
		for (NameId key : keys) {
			ArrayList<NameId> values = map.get(key);
			VerticalPanel options = new VerticalPanel();
			options.setTitle(key.getId());
			field.add(options, "<img src='" + BUNDLE.open().getURL() + "' style='padding: 1px;'</img>" + key.getName(), true);
//			field.add(options, key.getName());
			for (NameId value : values) {
				CheckBox cb = new CheckBox(value.getName());
				cb.setFormValue(value.getId());
				cb.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						CheckBox cb = (CheckBox)event.getSource();
						if (isUniquekey(cb.getFormValue())) {
							deselect(cb.getFormValue());
							cb.setValue(true);
						}
					}
				});
				options.add(cb);
			}
		}
	}

	/**
	 * Sets the selected options in the field from the specified map of selected key-option(s).
	 *
	 * @param value the map of selected key-option(s).
	 */
	public void setValue(HashMap<String, ArrayList<String>> value) {
		for (int index = 0; index < field.getWidgetCount(); index++) {
			VerticalPanel options = (VerticalPanel)field.getWidget(index);
			String key = options.getTitle();
			for (int row = 0; row < options.getWidgetCount(); row++) {
				CheckBox cb = (CheckBox) options.getWidget(row);
				if (cb == null) {Window.alert("setValue " + row);}
				String id = cb.getFormValue();
				if (value == null) {cb.setValue(false);}
				else if (value.containsKey(key)) {cb.setValue(value.get(key).contains(id));}
			}
		}
		super.setChanged();
	}

	/**
	 * Gets the selected options of the field.
	 *
	 * @return map the map of selected key-option(s).
	 */
	public HashMap<String,ArrayList<String>> getValue() {
		ArrayList<String> item = null;
		HashMap<String, ArrayList<String>> value = new HashMap<String,ArrayList<String>>();
		for (int index = 0; index < field.getWidgetCount(); index++) {
			VerticalPanel options = (VerticalPanel)field.getWidget(index);
			String key = options.getTitle();
			for (int row = 0; row < options.getWidgetCount(); row++) {
				CheckBox cb = (CheckBox) options.getWidget(row);
				if (cb == null) {Window.alert("StackField getValue " + row);}
				String id = cb.getFormValue();
				if (cb.getValue()) {
					if (!value.containsKey(key)) {
						item = new ArrayList<String>();
						value.put(key, item);
					}
					item.add(id);
				}
			}
		}
		return value;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#noValue()
	 */
	public boolean noValue() {
		return getValue().keySet() == null || getValue().keySet().isEmpty();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender){
		return (sender == this);
	}
}
