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


import net.cbtltd.shared.api.HasSchedule;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.user.client.Window;

public class AvailableCell implements HasCell<HasSchedule, String> {

	public AvailableCell() {
		super(); //"click", "dblclick", "mousedown"); //TODO: add consumed events
	}

	private final ClickableTextCell cell = new ClickableTextCell();

	private final FieldUpdater<HasSchedule, String> updater = new FieldUpdater<HasSchedule, String>() {

		/**
		 * Announces a new value for a field within a base object.
		 * 
		 * @param index the current row index of the object
		 * @param object the base object to be updated
		 * @param value the new value of the field being updated
		 */
		@Override
		public void update(int index, HasSchedule object, String value) {
			Window.alert("update " + index + "  to " + value);
		}
	};

	/**
	 * Returns the {@link Cell} of type C.
	 * 
	 * @return a Cell
	 */
	public Cell<String> getCell() {
		return cell;
	}

	/**
	 * Returns the {@link FieldUpdater} instance.
	 * 
	 * @return an instance of FieldUpdater<T, C>
	 */
	public FieldUpdater<HasSchedule, String> getFieldUpdater() {
		return updater;
	}

	/**
	 * Returns the value of type C extracted from the record of type T.
	 * 
	 * @param object a record of type T
	 * @return a value of type C suitable for passing to the cell
	 */
	public String getValue(HasSchedule object) {
		return null; //object.getActivityid();
	}
}