/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import net.cbtltd.shared.api.HasServiceResponse;
import net.cbtltd.shared.api.HasState;

public class Unit 
extends NameId
implements HasServiceResponse, HasState {

	public static final String TYPE = "type";
	
	public static final String CREATED = "Created";
	public static final String[] STATES = {CREATED};

	public static final String AREA = "Area";
	public static final String COUNT = "Count";
	public static final String LENGTH = "Length";
	public static final String MASS = "Mass";
	public static final String TIME = "Time";
	public static final String VOLUME = "Volume";
	public static final String[] TYPES = {AREA, COUNT, LENGTH, MASS, TIME, VOLUME};

	public static final String ANN = "ANN"; //year
	public static final String EA = "EA";
	public static final String KGM = "KGM";
	public static final String LTR = "LTR";
	public static final String MON = "MON"; //month
	public static final String MTR = "MTR";
	public static final String SEC = "SEC";
	public static final String MIN = "MIN";
	public static final String HUR = "HUR"; //hour
	public static final String DAY = "DAY"; //day
	public static final String WEE = "WEE";
	public static final String MMT = "MMT";
	public static final String KMT = "KMT";
	public static final String INH = "INH";
	public static final String FOT = "FOT";
	public static final String YRD = "YRD";
	public static final String SMI = "SMI";
	public static final String NMI = "NMI";

	public static final String[] UNITS = {EA, KGM, LTR, MTR, SEC, MIN, HUR, DAY, WEE, MON};
	public static final String[] DWM_UNITS = {DAY, WEE, MON};

    protected String type;
    protected String state;
    protected int status = 0;

	public Service service() {return Service.UNIT;}

    public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getState() {
		return CREATED;
	}

	public void setState(String state) {
		//this.state = state;
	}

	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Unit [type=");
		builder.append(type);
		builder.append(", state=");
		builder.append(state);
		builder.append(", status=");
		builder.append(status);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
}