package net.cbtltd.rest.bookingcom;

import net.cbtltd.rest.bookingcom.availability.AvailabilityUtils;
import net.cbtltd.rest.bookingcom.reservation.ReservationHandler;
import net.cbtltd.server.cron4j.Scheduler;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

public class BookingComScheduler {
	
	private static final Logger LOG = Logger.getLogger(BookingComScheduler.class);

	public static void main(String[] args) {
		BookingComScheduler.startReservationScheduler();
		BookingComScheduler.startReservationUpdateScheduler();
		BookingComScheduler.startBulkAvailabiltyUploadScheduler();
	}
	
	private static void startReservationScheduler () {
		Scheduler pollReservations = new Scheduler();
		pollReservations.setDaemon(false);
		// run the poller every 15 minutes
		pollReservations.schedule("*/15 * * * *", new Runnable() { 
		public void run() {
			LOG.debug("Running Reservaion poller at: " + ReservationHandler.vdtf.print(new DateTime()));
			ReservationHandler.fetchAllReservations();
		}
		});
		pollReservations.start();
	}
	
	private static void startReservationUpdateScheduler () {
		Scheduler pollReservations = new Scheduler();
		pollReservations.setDaemon(false);
		// run the poller every 15 minutes
		pollReservations.schedule("*/10 * * * *", new Runnable() { 
			public void run() {
				LOG.debug("Running Availability update poller at: " + ReservationHandler.vdtf.print(new DateTime()));
				ReservationHandler.updateAvailabilityInBookingCom();
			}
		});
		pollReservations.start();
	}
	
	private static void startBulkAvailabiltyUploadScheduler () {
		Scheduler availabilityUploader = new Scheduler();
		availabilityUploader.setDaemon(false);
		// every saturday at 12
		availabilityUploader.schedule("* 12 * * Sat", new Runnable() { 
//		availabilityUploader.schedule("*/10 * * * *", new Runnable() { 
			public void run() {
				LOG.debug("Running Bulk availability uploader: " + ReservationHandler.vdtf.print(new DateTime()));
				AvailabilityUtils availabilityUtils = new AvailabilityUtils();
				availabilityUtils.setLoadSelectedProducts(false);
				availabilityUtils.getListInputProducts().add("121");
				availabilityUtils.setRateType(0);
				availabilityUtils.fetchAndUpdateAvailabilityToBookingCom();
			}
		});
		availabilityUploader.start();
	}
}