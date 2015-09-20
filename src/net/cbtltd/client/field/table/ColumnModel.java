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

import com.google.gwt.user.cellview.client.Column;

/**
 * The Class ColumnModel to allow sorting of List<Model> using the Column of a CellTable.
 * It is not desirable to extend Model to include the column Comparable interface as the
 * Column is a view element and has no meaning in the Model world.
 */
public class ColumnModel<T> 
implements Comparable<ColumnModel<T>> {
	private final Column<T, ?> column;
	private final T model;
	
	public ColumnModel(Column<T, ?> column, T model) {
		super();
		this.column = column;
		this.model = model;
	}

	public int compareTo(ColumnModel<T> columnModel) {
		Comparable thisValue = (Comparable)column.getValue(this.model);
		Comparable thatValue = (Comparable)column.getValue(columnModel.getModel());
		return thisValue == null || thatValue == null ? -1 : thisValue.compareTo(thatValue);
	}

	public T getModel() {
		return model;
	}
}
