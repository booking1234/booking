/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class SuggestspanField 
extends SuggestField {

	private SuggestBox tosuggestBox;
	private NameId tonameid;
	
	public SuggestspanField (
			HasComponents form,
			short[] permission,
			NameIdAction action,
			String label,
			int limit,
			int tab) {
		super(form, permission, action, label, limit, tab);

//		oracle = new NameSuggestOracle();
		tosuggestBox = new SuggestBox(oracle);
		tosuggestBox.setTabIndex(tab);
		tosuggestBox.setLimit(limit);
		tosuggestBox.addSelectionHandler(this);
		tosuggestBox.addStyleName(CSS.cbtSuggestFieldField());
		horizontalPanel.add(tosuggestBox);
	}
 
	@Override
	public void onSelection(SelectionEvent<Suggestion> event) {
		SuggestResponse selected = (SuggestResponse) event.getSelectedItem();
		setTonameId(selected.getNameId());
		fireChange(this);
	};

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#onReset()
	 */
	@Override
	public void onReset() {
		super.onReset();
		tosuggestBox.setText(defaultName);
	}

	public void setEqual() {
		//setTonameId(getNameId());
	}
	
	public void setTofieldStyle(String style) {
		tosuggestBox.addStyleName(style);
	}

	public void setTofieldHalf(){
		setTofieldStyle(CSS.cbtSuggestFieldHalf());
	}

	public void setTonameId(NameId tonameid) {
		this.tonameid = tonameid;
		if (tonameid != null) {tosuggestBox.setText(tonameid.getName());}
	}
	
	public String getTovalue () {
		return tonameid == null || tonameid.noName() ? null : tonameid.getId();
	}

	public String getToname () {
		return tosuggestBox == null ? null : tosuggestBox.getText(); //tonameid == null || tonameid.noName() || tonameid.hasName(defaultName) ? null : tonameid.getName();
	}

	public boolean noTovalue() {
		return getTovalue() == null;
	}

	public boolean hasTovalue(String value) {
		return tonameid == null ? value == null : tonameid.hasId(value);
	}

	public boolean notTovalue(String value) {
		return !hasTovalue(value);
	}
}
