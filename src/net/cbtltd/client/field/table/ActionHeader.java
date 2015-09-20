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
import com.google.gwt.user.cellview.client.Header;

/**
 * A Header containing String data rendered by an ActionCell.
 */
public class ActionHeader<H> extends Header<H> {

	public ActionHeader(AbstractCell<H> cell) {
		super(cell);
	}

	public void render(String value, Object viewData, StringBuilder sb) {
	    if (value != null) {
	      sb.append(value);
	    }
	  }

	@Override
	public H getValue() {
		return null; //TODO:
	}
}
