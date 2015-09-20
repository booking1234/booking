/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.HashMap;
import java.util.Map;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.form.listener.OptionListener;
import net.cbtltd.client.resource.FieldBundle;
import net.cbtltd.client.resource.FieldCssResource;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.shared.License;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;

public class WindowButton
extends Button
implements ClickHandler, OptionListener  {

	private static final Hosts HOSTS = GWT.create(Hosts.class);
	private static final FieldCssResource CSS = FieldBundle.INSTANCE.css();
	private short[] permission;
	private String value;

	public WindowButton(
			HasComponents form,
			short[] permission,
			String label,
			int tab) {
		this.permission = permission;
		setText(label);
		super.setVisible(false);
		addClickHandler(this);
		setStylePrimaryName(CSS.cbtWindowButton());
	}

	/*****************************************************************************************
	 * Handles click events
	 ****************************************************************************************/
	@Override
	public void onClick(ClickEvent event){
		if (event.getSource() == this && value != null && !value.isEmpty()) {
			Window.open(HOSTS.rootUrl() + value, "Window", "menubar=no,location=no,resizable=no,scrollbars=no,toolbar=no,status=no,height=330,width=440");
		}
	}

	/*****************************************************************************************
	 * Handles option changes
	 ****************************************************************************************/
	private Map<String, Boolean> writeOptions;
	private Map<String, Boolean> readOptions;

	@Override
	public void onOptionChange(String option) {
		if (readOptions != null) {
			if (readOptions.get(option) == null){setVisible(false);}
			else {setVisible(readOptions.get(option));}
		}
		if (writeOptions != null) {
			if (writeOptions.get(option) == null) {setEnabled(false);}
			else {setEnabled(writeOptions.get(option));}
		}
	}

	@Override
	public void setReadOption(String option, boolean visible) {
		if (readOptions == null) readOptions = new HashMap<String, Boolean>();
		readOptions.put(option, visible);
	}

	@Override
	public void setWriteOption(String option, boolean enabled) {
		if (writeOptions == null) writeOptions = new HashMap<String, Boolean>();
		writeOptions.put(option, enabled);
	}

	/*****************************************************************************************
	 * General - setters and getters
	 ****************************************************************************************/
	@Override
	public void setVisible(boolean visible){
		super.setVisible(visible && (AbstractRoot.writeable(permission) || AbstractRoot.readable(permission)));
	}

	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled && AbstractRoot.writeable(permission));
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		setVisible(value != null && !value.isEmpty());
	}
}

//The table shows the features and the string tokens you can use:
//
//	status  The status bar at the bottom of the window.
//	toolbar  The standard browser toolbar, with buttons such as Back and Forward.
//	location  The Location entry field where you enter the URL.
//	menubar  The menu bar of the window
//	directories  The standard browser directory buttons, such as What's New and What's Cool
//	resizable Allow/Disallow the user to resize the window.
//	scrollbars  Enable the scrollbars if the document is bigger than the window
//	height Specifies the height of the window in pixels. (example: height='350')
//	width  Specifies the width of the window in pixels.
//
