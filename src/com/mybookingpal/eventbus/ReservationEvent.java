package com.mybookingpal.eventbus;

import java.util.List;

import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Reservation;

public class ReservationEvent {
	
	Reservation reservation;
	List<ChannelProductMap> channelProductMaps;
	
	public ReservationEvent(Reservation reservation,
			List<ChannelProductMap> channelProductMaps) {
		super();
		this.reservation = reservation;
		this.channelProductMaps = channelProductMaps;
	}

	public Reservation getReservation() {
		return reservation;
	}

	
	public List<ChannelProductMap> getChannelProductMaps() {
		return channelProductMaps;
	}

	public void setChannelProductMaps(List<ChannelProductMap> channelProductMaps) {
		this.channelProductMaps = channelProductMaps;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

}
