package com.mybookingpal.rest.channel;

import java.util.List;
import java.util.Map;



import net.cbtltd.rest.expedia.availrate.utils.AvailabilityUtils;
import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Reservation;

import org.apache.log4j.Logger;

public class ExpediaEventHandler implements ChannelEventHandler  {
	private static final Logger LOG = Logger.getLogger(ExpediaEventHandler.class
			.getName());
	
	public void postPrice(Price price,ChannelProductMap channelProductMap) {
		

	}
	

	@Override
	public void postPrices(Map<ChannelProductMap, List<Price>> mapPrice) {
		LOG.info("Entering post price of ExpediaEventHandler");
		net.cbtltd.rest.expedia.availrate.utils.RateUtils rateUtils=new net.cbtltd.rest.expedia.availrate.utils.RateUtils();
		rateUtils.notifyRates(mapPrice);
		LOG.info("Exiting post price of ExpediaEventHandler");
		
	}

	
	@Override
	public void postReservation(Reservation reservation,ChannelProductMap channelProductMap) {}

	@Override
	public void postReservations(Map<ChannelProductMap, List<Reservation>> mapReservation) {
		LOG.info("Entering post Reservation of ExpediaEventHandler");
		AvailabilityUtils availabilityUtils=new AvailabilityUtils();
		availabilityUtils.notifyAvailability(mapReservation);
		LOG.info("Exiting post Reservation of ExpediaEventHandler");
		
	}







	
}
