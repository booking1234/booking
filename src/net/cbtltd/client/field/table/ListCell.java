/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package net.cbtltd.client.field.table;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

/**
 * A cell that renders a list
 * @param <C> the type that this Cell represents
 */
public class ListCell<C> extends AbstractCell<C> {
	private static int id = 0; // to provide unique id
	private String[] text;
	private String selected;
	private String style;
	private final Delegate<C> delegate;

	public interface Delegate<T> {
		void execute(T object);
	}
	//TODO: also use ArrayList<NameId> for separate value and text elements in list 
	public ListCell(String[] text, String selected, String style, Delegate<C> delegate) {
	    super("change");
		id++;
		this.text = text;
		this.selected = selected;
		this.style = style;
		this.delegate = delegate;
	}

	private String getId() {return "ListCell" + id;}

	/**
	 * If you override this method to add support for events, remember to pass the
	 * event types that the cell expects into the constructor.
	 */
	@Override
	public void onBrowserEvent(Context context, Element parent, C value,
			NativeEvent event, ValueUpdater<C> valueUpdater) {
		if ("change".equals(event.getType())) {
			selected = getValueJS(getId());
			delegate.execute(value);
		}
	}

	@Override
	public void render(Context context, C value, SafeHtmlBuilder sb) {
		sb.appendHtmlConstant("<select id='ListCell" + id + "' class='" + style + "'>");
		for (int index = 0; index < text.length; index++) {
			if (text[index].equalsIgnoreCase(selected)) {sb.appendHtmlConstant("<option selected='selected'>" + text[index] + "</option>");}
			else {sb.appendHtmlConstant("<option value='"  + text[index] + "'>" + text[index] + "</option>");}
		}
		sb.appendHtmlConstant("</select>");
	}

	public String getValue() {
		return selected;
	}

	/*****************************************************************************************
	 * Get list value
	 ****************************************************************************************/
	private native String getValueJS(String id) /*-{
		return $doc.getElementById(id).value;
	}-*/;
}