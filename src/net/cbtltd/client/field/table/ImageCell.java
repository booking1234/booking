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

import net.cbtltd.shared.api.HasIcons;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

/**
 * A cell that renders an image and takes a delegate to perform actions on mouseUp.
 * @param <C> the type that this Cell represents
 */
public class ImageCell<C> extends AbstractCell<C> {

	private ImageResource[] images;
	private String style;
	private Delegate<C> delegate;

	public ImageCell(ImageResource[] images, String style) {
		super("click", "keydown");
		this.images = images;
		this.style = style;
	}

	public ImageCell(ImageResource image, String style, Delegate<C> delegate) {
		super("click", "keydown");
		this.images = new ImageResource[1];
		this.images[0] = image;
		this.style = style;
		this.delegate = delegate;
	}

	public ImageCell(ImageResource[] images, String style, Delegate<C> delegate) {
		super("click", "keydown");
		this.images = new ImageResource[1];
		this.images = images;
		this.style = style;
		this.delegate = delegate;
	}

	public interface Delegate<T> {
		void execute(T object);
	}

	/**
	 * If you override this method to add support for events, remember to pass the
	 * event types that the cell expects into the constructor.
	 */
	@Override
	public void onBrowserEvent(Context context, Element parent, C value,
			NativeEvent event, ValueUpdater<C> valueUpdater) {
		if ("click".equals(event.getType())) {
			delegate.execute(value);
		}
		// Special case the ENTER key for a unified user experience.
		if ("keydown".equals(event.getType()) && event.getKeyCode() == KeyCodes.KEY_ENTER) {
			onEnterKeyDown(context, parent, value, event, valueUpdater);
		}
	}

	@Override
	public void render(Context context, C value, SafeHtmlBuilder sb) {
		if (images == null || images.length == 0) {return;}
		int key = 0;
		String title = null;
		if (value != null && value instanceof Integer) {key = (Integer) value;}
		if (value != null && value instanceof HasIcons) {
			key = ((HasIcons) value).getIconkey();
			title = ((HasIcons) value).getIcontitle();
		}
		if (key >= images.length) {key = 0;}
		sb.append(SafeHtmlUtils.fromTrustedString("<div class='" + style + "'><img src='" + images[key].getURL() + (title == null ? "" : "' title='" + title) + "' /></div>"));
	}
}