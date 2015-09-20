/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

public class NameIdTab
extends NameId {
	
	public static final int ACCOUNT = 0;
	public static final int FINANCE = 1;
	public static final int JOURNAL = 2;
	public static final int PARTY = 3;
	public static final int PRODUCT = 4;
	public static final int RESERVATION = 5;
	public static final int TASK = 6;

	private int tab;

	public int getTab() {
		return tab;
	}

	public void setTab(int tab) {
		this.tab = tab;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NameIdTab [tab=");
		builder.append(tab);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}

}
