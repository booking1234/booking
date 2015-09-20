/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field.table;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

public class StyledNumberCell extends AbstractCell<Number> {

	private final NumberFormat formatter;

	interface Templates extends SafeHtmlTemplates {
		/**
		 * The template for this Cell, which includes styles and a value.
		 * @param styles the styles to include in the style attribute of the div
		 * @param value the safe value.
		 * @return a {@link SafeHtml} instance
		 */
		@SafeHtmlTemplates.Template("<div style=\"{0}\">{1}</div>")
		SafeHtml cell(SafeStyles styles, SafeHtml value);
	}
	private static Templates templates = GWT.create(Templates.class);

	public StyledNumberCell(NumberFormat formatter) {
		this.formatter = formatter;
	}

	@Override
	public void render(Context context, Number value, SafeHtmlBuilder sb) {
		String style = (String) context.getKey(); //from ProvidesKey.getKey()
		if (value != null && style != null) {
			SafeStyles safeStyle = SafeStylesUtils.fromTrustedString(style);
			SafeHtml safeValue = SafeHtmlUtils.fromString(formatter.format(value));
			SafeHtml rendered = templates.cell(safeStyle, safeValue);
			sb.append(rendered);
		}
	}
}
