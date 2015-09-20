/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.json.value;

import net.cbtltd.shared.Model;

public class ValueWidgetItem {
	private String key;
	private String value;

	public ValueWidgetItem() {
		super();
	}

	public ValueWidgetItem(String item) {
		super();
		String[] args = item.split(Model.DELIMITER);
		if (args.length == 2) {
			key = args[0];
			value = args[1];
		}
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ValueWidgetItem [key=");
		builder.append(key);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

}
