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

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel.AbstractSelectionModel;

/**
 * A simple selection model that allows multiple objects to be selected.
 * 
 * <p>
 * Note: This class is new and its interface subject to change.
 * </p>
 * 
 * @param <T> the record data type
 */
public class TableSelectionModel<T> extends AbstractSelectionModel<T> {

	//	private boolean inset = false;
	private T lastSelectedObject;
	private Object curKey;
//	private TreeSet<T> selectedSet = new TreeSet<T>();BUG
//	private TreeSet<Object> selectedKeys = new TreeSet<Object>();
	private HashSet<T> selectedSet = new HashSet<T>();
	private HashSet<Object> selectedKeys = new HashSet<Object>();

	  /**
	   * Constructs a NoSelectionModel without a key provider.
	   */
	  public TableSelectionModel() {
	    super(null);
	  }
	  
	  /**
	   * Constructs a NoSelectionModel with the given key provider.
	   *
	   * @param keyProvider an instance of ProvidesKey<T>, or null if the record
	   *        object should act as its own key
	   */
	  public TableSelectionModel(ProvidesKey<T> keyProvider) {
	    super(keyProvider);
	  }

	public boolean isEmpty() {
		return selectedSet.isEmpty();
	}

	public T getLastSelectedObject() {
		return lastSelectedObject;
	}

	public boolean isSelectedObject(T object) {
		if (lastSelectedObject == null || curKey == null || object == null) {
			return false;
		}
		return curKey.equals(getKey(object));
	}
	
	public Set<T> getSelectedSet() {
		return selectedSet;
	}

	public boolean isSelected(T object) {
		//Window.alert("TableSelectionModel isSelected " + selectedKeys.contains(getKey(object)) + " " + "\n" + getKey(object) + "\n" + selectedKeys);
		return selectedKeys.contains(getKey(object));
	}

	public void setSelected(T object, boolean selected) {
		boolean inset = selectedSet.contains(object);
		if (object == null) { //to deselect
			lastSelectedObject = null;
			curKey = null;
			selectedSet = new HashSet<T>();
			selectedKeys = new HashSet<Object>();
//			scheduleSelectionChangeEvent();
//			return;
		}
		else if (!inset) {
			lastSelectedObject = object;
			curKey = getKey(object);
			selectedSet.add(object);
			selectedKeys.add(getKey(object));
		} 
		else {
			lastSelectedObject = null;
			curKey = null;
			selectedSet.remove(object);
			selectedKeys.remove(getKey(object));
		}
		scheduleSelectionChangeEvent();
	}
}
