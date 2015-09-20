package com.mybookingpal.rest.channel;

import java.util.List;
import java.util.Map;

import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Reservation;

public interface ChannelEventHandler {
	public void postReservation(Reservation reservation,ChannelProductMap channelProductMap);
	public void postPrice(Price price,ChannelProductMap channelProductMap);
	public void postReservations(Map<ChannelProductMap, List<Reservation>> mapReservation);
	public void postPrices(Map<ChannelProductMap,List<Price>> priceMap);
}
