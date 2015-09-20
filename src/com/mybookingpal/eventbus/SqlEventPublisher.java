package com.mybookingpal.eventbus;

import java.util.List;

import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Reservation;



import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;

public class SqlEventPublisher {
	private EventBus sqlEventBus;
	 
    @Inject
    public SqlEventPublisher(EventBus sqlEventBus) {
        this.sqlEventBus = sqlEventBus;
    }
    
    public void postPriceEvent(Price price,List<ChannelProductMap> channelProductMaps) {
    	sqlEventBus.post(new PriceEvent(price,channelProductMaps));
    }
    
    public void postReservationEvent(Reservation reservation,List<ChannelProductMap> channelProductMaps) {
    	sqlEventBus.post(new ReservationEvent(reservation,channelProductMaps));
    }
}
