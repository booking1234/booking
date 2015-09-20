/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import net.cbtltd.shared.api.HasService;

public class Serial
implements HasService {

	//Series
	public static final String AUDIT = "Audit";
	public static final String BROCHURE = "Brochure";
	public static final String JOURNAL = "Journal";
	public static final String RATE = "Rate";
	public static final String RESERVATION = "Reservation";
	public static final String TASK = "Task";
	
    private String id;
    private String partyid;
    private String series;
    private String format;
    private Integer last;

    public Serial() {}

    public Serial(String partyid, String series) {
		this.partyid = partyid;
		this.series = series;
	}

    public Serial(String partyid, String series, String format, Integer last) {
		this.partyid = partyid;
		this.series = series;
		this.format = format;
		this.last = last;
	}

	public Service service() {return Service.SESSION;}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPartyid() {
		return partyid;
	}

	public void setPartyid(String partyid) {
		this.partyid = partyid;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Integer getLast() {
		return last;
	}

	public void setLast(Integer last) {
		this.last = last;
	}

	public String getFolio() {
//		return format + NumberFormat.getFormat("00000").format(last);
//		return NumberFormat.getFormat(format).format(last);
//		return Util.fmtInteger(last, format);
		String name = String.valueOf(last);
		if (name == null || format == null){return null;}
		int offset = format.length() - name.length();
		return format.substring(0, offset) + name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Serial [id=");
		builder.append(id);
		builder.append(", partyid=");
		builder.append(partyid);
		builder.append(", series=");
		builder.append(series);
		builder.append(", format=");
		builder.append(format);
		builder.append(", last=");
		builder.append(last);
		builder.append(", getFolio()=");
		builder.append(getFolio());
		builder.append("]");
		return builder.toString();
	}
}