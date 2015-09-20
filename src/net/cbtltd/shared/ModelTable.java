/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import javax.xml.bind.annotation.XmlTransient;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import net.cbtltd.shared.api.HasTableService;

public abstract class ModelTable 
extends Model
implements HasTableService {

	private String orderby;
	
	@XStreamOmitField 
	private int startrow = 0;
	
	@XStreamOmitField
	private int numrows = Integer.MAX_VALUE;

	public ModelTable() {}
	
	@XmlTransient
	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public boolean noOrderby() {
		return orderby == null || orderby.isEmpty();
	}
	
	@XmlTransient
	public int getStartrow() {
		return startrow;
	}

	public void setStartrow(int startrow) {
		this.startrow = startrow;
	}

	@XmlTransient
	public int getNumrows() {
		return numrows;
	}

	public void setNumrows(int numrows) {
		this.numrows = numrows;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(" OrderBy ").append(getOrderby());
		sb.append(" StartRow ").append(getStartrow());
		sb.append(" NumRows ").append(getNumrows());
		return sb.toString();
	}

}
