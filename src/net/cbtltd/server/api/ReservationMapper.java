/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;
import java.util.List;

import com.mybookingpal.service.annotations.SqlUpdateMarker;

import net.cbtltd.rest.Download;
import net.cbtltd.rest.ScheduleItem;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Yield;
import net.cbtltd.shared.api.HasCollision;
import net.cbtltd.shared.api.HasTableService;
import net.cbtltd.shared.reservation.Available;
import net.cbtltd.shared.reservation.AvailableItem;
import net.cbtltd.shared.reservation.LookBook;
import net.cbtltd.soap.ota.server.OtaInvCount;
import net.cbtltd.soap.ota.server.OtaRoomStay;

public interface ReservationMapper
extends AbstractMapper<Reservation> {
	
	Reservation offlineread(String reservationid);
	Reservation collides(Reservation action);
	Reservation exists(Reservation action);
	Reservation existsWithState(Reservation action);
	void cancelversion(Reservation action);
	void cancelversionbydate(Reservation action);
	void cancelversionbypartyid(Reservation action);
	void cancelreservationlist(List<String> reservationIdList);
	void deletebetween(Reservation action);
	void deletebyexample(Reservation action);
	
	Integer count(HasTableService action);
	ArrayList<Reservation> list(HasTableService action);
	
	Integer minavailable(HasCollision action); // Minimum units available during reservation period
	
	Integer occupancy(Yield yield); //Occupancy
	Integer next(Yield yield); //Next
	Integer previous(Yield yield); //Previous
	Integer previousstaycount(String customerid); //Previous stay count
	Integer readreservationcount(String organizationID);
	
	Double availableitemdiscount(AvailableItem availableitem); //AvailableItemDiscount
	Double availableitemprice(AvailableItem availableitem);  //AvailableItemPrice

	void deleteoldstate(Reservation reservation);
	ArrayList<String> donestates(String reservationid);
	ArrayList<Reservation> listbycustomerid(Reservation reservation);

	ArrayList<NameId> collisions(HasCollision reservation); //Collide
	ArrayList<NameId> parentcollisions(HasCollision reservation);
	ArrayList<NameId> childcollisions(HasCollision reservation);
	
	ArrayList<NameId> productpartof(Available action);
	
	ArrayList<AvailableItem> brochureproduct(String[] productid);
	ArrayList<AvailableItem> brochureitems(String id);
	ArrayList<AvailableItem> availableitems(Available action); //Available
//	ArrayList<AvailableItem> availabledatewidget(AvailableDateWidget action); //AvailableDateWidget
	ArrayList<AvailableItem> availableitem(Available action); // fro parent and child items
	ArrayList<String> parentids(Available action);
	ArrayList<String> childids(Available action);

	ArrayList<OtaInvCount> hotelinvcountnotif(Reservation reservation); //HotelInvCountNotifRQ
	ArrayList<OtaRoomStay> hotelavailability(Reservation reservation); //HotelAvailRS
	ArrayList<OtaRoomStay> hotelavailabilities(Reservation reservation); //HotelAvailRS isSummary

	ArrayList<NameId> actornameid(NameIdAction action);
	ArrayList<NameId> previousstays(String customerid); //Notes

	Integer countatposition(Available action);
	ArrayList<Reservation> getschedule(String productid);
	ArrayList<String> availableatposition(Available action); //ProductsAtLocation

	ArrayList<AvailableItem> lookbookitems(LookBook action); //Available
	ArrayList<String> productsatposition(LookBook action); //products within distance of center
	ArrayList<String> productswithattributes(LookBook action);
	ArrayList<String> childrennotavailable(LookBook action);
	ArrayList<String> parentsnotavailable(LookBook action);
	ArrayList<String> productsnotavailable(LookBook action);
	ArrayList<String> productcollisions(LookBook action);
	ArrayList<String> productnocollisions(LookBook action);

	//REST SQL queries
	Boolean available(Reservation action);
	ArrayList<ScheduleItem> locationschedule(Reservation action);
	ArrayList<ScheduleItem> productschedule(Reservation action);
	Reservation readbyforeignid(String id); //RESTReservationByForeignid
	Reservation readbyname(Reservation action); //RESTReservationidExists
	Reservation readbyorganization(Reservation action);
	ArrayList<Download> download(String id); //RESTDownload
	ArrayList<Download> downloads(String id); //RESTDownloads
	ArrayList<Download> uploads(String id); //RESTUploads
	ArrayList<net.cbtltd.rest.flipkey.History> flipkeyhistory(); //FlipKeyHistory
	ArrayList<net.cbtltd.rest.flipkey.Updated> flipkeyproperties(); //FlipKeyProperties
	net.cbtltd.rest.flipkey.Location flipkeylocation(String locationid); //FlipKeyRead
	ArrayList<net.cbtltd.rest.flipkey.ScheduleItem> flipkeyavailability(); //FlipKeyAvailability
	ArrayList<net.cbtltd.rest.flipkey.ScheduleItem> flipkeyavailabilityproduct(String productid); //FlipKeyAvailabilityProduct
	ArrayList<net.cbtltd.rest.flipkey.ScheduleItem> flipkeyreservation(net.cbtltd.rest.flipkey.ScheduleItem action); //FlipKeyReservation

	//JSON SQL queries
	Boolean availablewidget(net.cbtltd.json.Parameter action);
	ArrayList<net.cbtltd.json.calendar.CalendarWidgetItem> calendarwidget(net.cbtltd.json.Parameter action);
	ArrayList<net.cbtltd.rest.response.CalendarElement> calendarelement(net.cbtltd.json.Parameter action);
	ArrayList<net.cbtltd.rest.response.CalendarElement> reversedCalendarElement(net.cbtltd.json.Parameter action);
	net.cbtltd.json.EnquiryWidgetItem enquirywidgetrq(String id);
	ArrayList<net.cbtltd.json.reservation.ReservationWidgetItem> listbyparentid(String parentid);
	net.cbtltd.json.reservation.ReservationWidgetItem readwidget(net.cbtltd.json.Parameter action);
	void offline(net.cbtltd.json.reservation.ReservationWidgetItem action);

	//Partner SQL queries
	Reservation kigoread(Integer kigoid);
	void remove(Reservation action);

	//Foreign SQL queries
	Reservation altread(NameId action);
	Reservation altreadforchannel(Reservation action);
	
	//XML writer
	ArrayList<Reservation> reserveredDatesForPropertyid(Reservation action); 
	ArrayList<java.util.Date> reservedDateForProperty(String productid); //to fetch all bookedDate of the property
	
	ArrayList<Reservation> productApiReserveredDates(Reservation action); 
	void delete(String productid);
	void deleteDate(String productid,java.util.Date date); ///to remove cancelled bookingDate from DB
	
	List<Reservation> readBasedOnTime(String lastFetch);
	List<Reservation> readActiveBasedOnTime(Reservation tmp);
	
	@SqlUpdateMarker @Override
	void create(Reservation action);
	
	@SqlUpdateMarker @Override
	void update(Reservation action);
	
	@SqlUpdateMarker @Override
	void write(Reservation action);
}
