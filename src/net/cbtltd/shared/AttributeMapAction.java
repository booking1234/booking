/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import net.cbtltd.shared.api.HasService;

public class AttributeMapAction
implements HasService {

	private Service service = Service.ATTRIBUTE;
	private String[] keys;
	private String type = Attribute.RZ;
	private String language = Language.EN;
	private int maxresponses = Integer.MAX_VALUE;

	private AttributeMapAction() {}

	public AttributeMapAction(String[] keys, String type, String language) {
		this.keys = keys;
		this.type = type;
		this.language = language;
	}

	public Service service() {return service;}

	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
	}

	public boolean noKeys() {
		return keys == null || keys.length <= 0;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getMaxresponses() {
		return maxresponses;
	}

	public void setMaxresponses(int maxresponses) {
		this.maxresponses = maxresponses;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nKeys ").append(getKeys());
		sb.append(" Language ").append(getLanguage());
		return sb.toString();
	}
}
