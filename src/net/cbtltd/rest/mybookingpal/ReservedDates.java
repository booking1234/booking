package net.cbtltd.rest.mybookingpal;

public class ReservedDates {

	private String prodctid;
	private String reservationid;
	private String fromdate;
	private String todate;
	
	public ReservedDates(String prodctid, String reservationid ,String fromdate, String todate)
	{
		this.prodctid= prodctid;
		this.reservationid=reservationid;
		this.fromdate = fromdate; 
		this.todate = todate;
	}

	public String getProdctid() {
		return prodctid;
	}

	public void setProdctid(String prodctid) {
		this.prodctid = prodctid;
	}

	public String getReservationid() {
		return reservationid;
	}

	public void setReservationid(String reservationid) {
		this.reservationid = reservationid;
	}

	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

}
