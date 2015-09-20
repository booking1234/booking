/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.price;

import net.cbtltd.shared.Model;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Time;

/** The Class PriceExport is to export and import price data in CSV format. */
public class PriceCSV
extends Price {
	
	/**
	 * Sets the price record from its CSV string.
	 *
	 * @param csv the CSV string.
	 */
	public void setCSV(String csv) {
		String[] fields = csv.split(",");
		int i = 1;
		setEntitytype(fields[i++]);
		setEntityid(fields[i++]);
//		setDate(Time.stringToDate(fields[i++]));
		if (fields[i] == null) {i++;}
//		else {setTodate(Time.stringToDate(fields[i++]));}
		setQuantity(Double.valueOf(fields[i++]));
		setUnit(fields[i++]);
		setValue(Double.valueOf(fields[i++]));
		setMinimum(Double.valueOf(fields[i++]));
		setCurrency(fields[i++]);
		setPartyid(fields[i++]);
	}

	/**
	 * Gets the CSV string from the price record.
	 *
	 * @return the CSV string.
	 */
	public String getCSV() {
		StringBuilder sb = new StringBuilder();
		sb.append((getEntityname() == null) ? Model.BLANK : getEntityname());
		sb.append(",").append((getEntitytype() == null) ? Model.BLANK : getEntitytype());
		sb.append(",").append((getEntityid() == null) ? Model.BLANK : getEntityid());
		sb.append(",").append(Time.dateToString(getDate()));
		sb.append(",").append(Time.dateToString(getTodate()));
		sb.append(",").append((getQuantity() == null) ? Model.BLANK : getQuantity());
		sb.append(",").append((getUnit() == null) ? Model.BLANK : getUnit());
		sb.append(",").append((getValue() == null) ? Model.BLANK : getValue());
		sb.append(",").append((getMinimum() == null) ? Model.BLANK : getMinimum());
		sb.append(",").append((getCurrency() == null) ? Model.BLANK : getCurrency());
		sb.append(",").append((getPartyid() == null) ? Model.BLANK : getPartyid());
		return sb.toString();
	}
}