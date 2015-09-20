package net.cbtltd.rest.bookingcom.reservation;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import net.cbtltd.rest.Constants;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.log4j.Logger;

@Path("/bookingcom")
public class ReservationPushAPI {
	private static final Logger LOG = Logger.getLogger(ReservationPushAPI.class.getName());

	@GET
	@Path("/reservation")
	public void reservationNotification(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("reservationId") @QueryParam("reservationId") Integer reservationId,
			@Description("hotelId") @QueryParam("hotelId") Integer hotelId) {

		LOG.info("Inside reservationNotification "+"reservationId "+reservationId+"hotelId "+hotelId);

		ReservationUtils reservationUtils=new ReservationUtils();
		reservationUtils.fetchAndUpdateReservationBasedOnNotification(hotelId, reservationId);

	}
	
	
	
}
