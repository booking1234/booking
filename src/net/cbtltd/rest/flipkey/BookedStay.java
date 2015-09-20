/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.flipkey;

public class BookedStay {
	public String ArrivalDate;
	public String DepartureDate;
	
	public BookedStay(){}
	
	public BookedStay(String arrivaldate, String departuredate) {
		super();
		this.ArrivalDate = arrivaldate;
		this.DepartureDate = departuredate;
	}
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("\narrivaldate ").append(ArrivalDate);
		sb.append(" departuredate ").append(DepartureDate);
		return sb.toString();
	}
}
