/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.shared.NameId;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle.MultiWordSuggestion;

/*****************************************************************************************
 * Suggest response class
 ****************************************************************************************/
public class SuggestResponse
extends MultiWordSuggestion {
	private String id;

	public SuggestResponse() {}

	public SuggestResponse(String name, String id) {
		super(name, name);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return getDisplayString();
	}

	public NameId getNameId() {
		return new NameId(getDisplayString(), id);
	}
}