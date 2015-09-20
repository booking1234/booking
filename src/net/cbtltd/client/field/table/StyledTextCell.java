/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field.table;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

//http://code.google.com/webtoolkit/doc/latest/DevGuideUiCustomCells.html

public class StyledTextCell extends AbstractCell<String> {

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

	@Override
	public void render(Context context, String value, SafeHtmlBuilder sb) {
		String style = (String) context.getKey(); //from ProvidesKey.getKey()
		if (value != null && style != null) {
			SafeStyles safeStyle = SafeStylesUtils.fromTrustedString(style);
			SafeHtml safeValue = SafeHtmlUtils.fromString(value);
			SafeHtml rendered = templates.cell(safeStyle, safeValue);
			sb.append(rendered);
		}
	}
}
