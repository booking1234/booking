package com.mybookingpal.rest.channel;

import java.util.List;
import java.util.Map;


import net.cbtltd.rest.hotelscombined.availability.AvailabilityUtils;

import net.cbtltd.rest.hotelscombined.rates.RateUtils;
import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Reservation;

import org.apache.log4j.Logger;

public class HotelsCombinedEventHandler implements ChannelEventHandler  {
	private static final Logger LOG = Logger.getLogger(HotelsCombinedEventHandler.class
			.getName());
	
	public void postPrice(Price price,ChannelProductMap channelProductMap) {
		

	}
	

	@Override
	public void postPrices(Map<ChannelProductMap, List<Price>> mapPrice) {
		LOG.info("Entering post price of HotelsCombinedEventHandler");
		RateUtils rateUtils=new RateUtils();
		rateUtils.notifyRates(mapPrice);
		LOG.info("Exiting post price of HotelsCombinedEventHandler");
		
	}

	
	@Override
	public void postReservation(Reservation reservation,ChannelProductMap channelProductMap) {}

	@Override
	public void postReservations(Map<ChannelProductMap, List<Reservation>> mapReservation) {
		LOG.info("Entering post Reservation of HotelsCombinedEventHandler");
		AvailabilityUtils availabilityUtils=new AvailabilityUtils();
		availabilityUtils.notifyAvialability(mapReservation);
		LOG.info("Exiting post Reservation of HotelsCombinedEventHandler");
		
	}




	
}
