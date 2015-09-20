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
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.google.gwt.text.shared.SimpleSafeHtmlRenderer;

public class ClickableNumberCell extends AbstractCell<Number> {

	/**
	 * The {@link NumberFormat} used to render the number.
	 */
	private final NumberFormat format;

	/**
	 * The {@link SafeHtmlRenderer} used to render the formatted number as HTML.
	 */
	private final SafeHtmlRenderer<String> renderer;

	private Delegate<Number> delegate;
	
	public interface Delegate<T> {
		void execute(T object);
	}

	/**
	 * Construct a new {@link ClickableNumberCell} using decimal format and a
	 * {@link SimpleSafeHtmlRenderer}.
	 */
	public ClickableNumberCell(NumberFormat format, Delegate<Number> delegate) {
		this(format, SimpleSafeHtmlRenderer.getInstance(), delegate);
	}

	/**
	 * Construct a new {@link ClickableNumberCell} using the given {@link NumberFormat} and a {@link SafeHtmlRenderer}.
	 *
	 * @param format the {@link NumberFormat} used to render the number
	 * @param renderer the {@link SafeHtmlRenderer} used to render the formatted number as HTML
	 * @param delegate to execute on click
	 */
	public ClickableNumberCell(NumberFormat format, SafeHtmlRenderer<String> renderer, Delegate<Number> delegate) {
		super("click");
		if (format == null) {throw new IllegalArgumentException("format == null");}
		if (renderer == null) {throw new IllegalArgumentException("renderer == null");}
		this.format = format;
		this.renderer = renderer;
		this.delegate = delegate;
	}

	/**
	 * If you override this method to add support for events, remember to pass the
	 * event types that the cell expects into the constructor.
	 */
	@Override
	public void onBrowserEvent(Context context, Element parent, Number value,
			NativeEvent event, ValueUpdater<Number> valueUpdater) {
		if ("click".equals(event.getType())) {
			delegate.execute(value);
		}
	}

	@Override
	public void render(Context context, Number value, SafeHtmlBuilder sb) {
		if (value != null) {
			sb.append(renderer.render(format.format(value)));
		}
	}
}
