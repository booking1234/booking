/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

public class Unspsc 
extends Model {
	
    private String unspscid;
    private String level;
    private String notes;

	public Service service() {return Service.PRODUCT;}

	public String getUnspscid() {
		return unspscid;
	}

	public void setUnspscid(String unspscid) {
		this.unspscid = unspscid;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLevelCode() {
		return id == null || level == null ? null : id;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Unspsc [unspscid=");
		builder.append(unspscid);
		builder.append(", level=");
		builder.append(level);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", organizationid=");
		builder.append(organizationid);
		builder.append(", status=");
		builder.append(status);
		builder.append(", state=");
		builder.append(state);
		builder.append(", values=");
		builder.append(values);
		builder.append(", attributes=");
		builder.append(attributemap);
		builder.append(", texts=");
		builder.append(texts);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
}