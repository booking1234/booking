/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.panel;

import java.util.ArrayList;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.Razor;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.form.AccessControl;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdTab;
import net.cbtltd.shared.api.HasResetId;
import net.cbtltd.shared.reservation.Brochure;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

/** The Class SearchPopup is to display search results. */
public class SearchPopup 
extends PopupPanel {

	/**
	 * Instantiates a new search pop up panel.
	 *
	 * @param results the search results.
	 */
	public SearchPopup(ArrayList<NameId> results) {
		super(true);
		setGlassEnabled(true);
		final FlowPanel panel = new FlowPanel();
		setWidget(panel);
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				SearchPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		panel.add(closeButton);

		final ScrollPanel scroll = new ScrollPanel();
		scroll.addStyleName(AbstractField.CSS.cbtCommandSearchScroll());
		panel.add(scroll);
		final FlowPanel form = new FlowPanel();
		scroll.add(form);
		final Label title = new Label(AbstractField.CONSTANTS.searchResult());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);
		
		for (NameId result : results) {
			SearchLabel label = new SearchLabel(result.getName(), result.getId(), ((NameIdTab)result).getTab());
			label.addStyleName(AbstractField.CSS.cbtCommandSearchResult());
			label.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					SearchLabel label = (SearchLabel)event.getSource();
					switch (label.getIndex()) {
					case NameIdTab.ACCOUNT: break;
					case NameIdTab.FINANCE: FinancePopup.getInstance().show(label.getResetid()); break;
					case NameIdTab.JOURNAL: JournalPopup.getInstance().show(label.getResetid()); break;
					case NameIdTab.PARTY: if (AbstractRoot.hasPermission(AccessControl.AGENCY)) {PartyPopup.getInstance().show(label.getResetid()); break;}
					else {AbstractRoot.render(Razor.PARTY_TAB, label); break;}
					case NameIdTab.PRODUCT: BrochurePopup.getInstance().show(label.getResetid(), null, Brochure.CREATED); break;
					case NameIdTab.RESERVATION: AbstractRoot.render(Razor.RESERVATION_TAB, label); break;
					case NameIdTab.TASK: AbstractRoot.render(Razor.TASK_TAB, label); break;
					}
					SearchPopup.this.hide();
				}
			});
			form.add(label);
		}
	}
	
	/* The Inner Class SearchLabel extends Label to include search text, id and index. */
	private class SearchLabel extends Label implements HasResetId {
		private final String id;
		private final int index;

		/**
		 * Instantiates a new search label.
		 *
		 * @param text the text of the label.
		 * @param id the id of the search.
		 * @param index the index of the label in the search result list.
		 */
		public SearchLabel(String text, String id, int index) {
			super(text);
			this.id = id;
			this.index = index;
		}

		/**
		 * Gets the reset id of the search.
		 *
		 * @return the reset id of the search.
		 */
		public String getResetid() {return id;}

		/**
		 * Gets the index of the label in the search result list.
		 *
		 * @return the index of the label in the search result list.
		 */
		public int getIndex() {return index;}

		/* (non-Javadoc)
		 * @see com.google.gwt.user.client.ui.UIObject#toString()
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("SearchLabel [id=");
			builder.append(id);
			builder.append(", index=");
			builder.append(index);
			builder.append(", getText()=");
			builder.append(getText());
			builder.append("]");
			return builder.toString();
		}
	}
}
