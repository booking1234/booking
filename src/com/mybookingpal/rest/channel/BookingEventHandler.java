package com.mybookingpal.rest.channel;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.cbtltd.rest.bookingcom.availability.AvailabilityUtils;
import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Reservation;

public class BookingEventHandler implements ChannelEventHandler {

	private static final Logger LOG = Logger.getLogger(BookingEventHandler.class
			.getName());

	@Override
	public void postPrice(Price price,ChannelProductMap channelProductMap) {
		LOG.info("Entering post price of BookingEventHandler");
		AvailabilityUtils availabilityUtils=new AvailabilityUtils();
		availabilityUtils.fetchAndUpdateAvailabilityToBookingCom(price, channelProductMap);
		LOG.info("Exiting post price of BookingEventHandler");

	}
	

	
	
	@Override
	public void postReservation(Reservation reservation,ChannelProductMap channelProductMap) {
		LOG.info("Entering post Reservation of BookingEventHandler");
		AvailabilityUtils availabilityUtils=new AvailabilityUtils();
		availabilityUtils.fetchAndUpdateAvailabilityToBookingCom(reservation, channelProductMap);
		LOG.info("Exiting post Reservation of BookingEventHandler");
		

	}

	




	@Override
	public void postPrices(Map<ChannelProductMap, List<Price>> priceMap) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void postReservations(
			Map<ChannelProductMap, List<Reservation>> mapReservation) {
		// TODO Auto-generated method stub
		
	}


}
