/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import java.util.Collection;

import net.cbtltd.shared.License;

public class ScheduleRow {
	public String id;
	public String name;
	public Collection<ScheduleCol> col;
	
	public ScheduleRow() {}
	
	public ScheduleRow(String id, String name, Collection<ScheduleCol> col) {
		super();
		this.id = id;
		this.name = name;
		this.col = col;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ScheduleRow [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", col=");
		builder.append(col);
		builder.append("]");
		return builder.toString();
	}

}