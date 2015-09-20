package net.cbtltd.rest.hotelscombined.reservation;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import net.cbtltd.rest.Constants;

import net.cbtltd.rest.hotelscombined.reservation.domain.OTAHotelResNotifRQ;
import net.cbtltd.rest.hotelscombined.reservation.domain.OTAHotelResNotifRS;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.log4j.Logger;

@Path("/hotelsCombined")
public class ReservationPushAPI {

	private static final Logger LOG = Logger.getLogger(ReservationPushAPI.class.getName());
	
	@GET
	@Path("/testMethod")
	public void testMethod() {
		System.out.println("Inside test Method");
		LOG.info("Inside test Method");

	}
	
	@POST
	@Path("/reservation")
	public OTAHotelResNotifRS reservationNotification(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,OTAHotelResNotifRQ otaHotelResNotifRQ
			) {
		
		LOG.info("Inside reservationNotification of Hotels Combined");
		System.out.println("Inside reservationNotification of Hotels Combined");
		ReservationUtils reservationUtils=new ReservationUtils();
		return reservationUtils.persistReservations(pos,otaHotelResNotifRQ);
	}

}
