package com.mybookingpal.eventbus;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Reservation;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.mybookingpal.config.RazorConfig;
import com.mybookingpal.rest.channel.BookingEventHandler;
import com.mybookingpal.rest.channel.ExpediaEventHandler;
import com.mybookingpal.rest.channel.HotelsCombinedEventHandler;

public class ChannelEventListener {
		 
    @Inject
    public ChannelEventListener(EventBus sqlEventBus) {
    	System.out.println("Listener constructor called.");
        sqlEventBus.register(this);
    }
    
	
    @Subscribe
    public void priceEvent(PriceEvent event) {
        // handle event
    	System.out.println("Received price event: " + event.getPrice());
    	System.out.println("event.getChannelProductMaps().size() " + event.getChannelProductMaps().size());
    	
    	//code to call the respectiveHanlder based on the map
    	if(event.getChannelProductMaps()!=null && event.getChannelProductMaps().size()>0){
    		for(ChannelProductMap channelProductMap:event.getChannelProductMaps()){
    			//creating handler for Booking.com event
    			if(channelProductMap.getChannelId()==RazorConfig.getBookingChannelPartnerId()){
    				BookingEventHandler bookingEventHandler=new BookingEventHandler();
    				bookingEventHandler.postPrice(event.getPrice(),channelProductMap);
    			}else if(channelProductMap.getChannelId()==RazorConfig.getHotelsCombinedChannelPartnerId()){
    				Map<ChannelProductMap,List<Price>> priceMap=new HashMap<ChannelProductMap,List<Price>>();
    				List<Price> listPrice=new ArrayList<Price>();
    				listPrice.add(event.getPrice());
    				priceMap.put(channelProductMap, listPrice);
    				HotelsCombinedEventHandler hotelsCombinedEventHandler=new HotelsCombinedEventHandler();
    				hotelsCombinedEventHandler.postPrices(priceMap);
    				
    			}else if(channelProductMap.getChannelId()==RazorConfig.getExpediaChannelPartnerId()){
    				Map<ChannelProductMap,List<Price>> priceMap=new HashMap<ChannelProductMap,List<Price>>();
    				List<Price> listPrice=new ArrayList<Price>();
    				listPrice.add(event.getPrice());
    				priceMap.put(channelProductMap, listPrice);
    				ExpediaEventHandler expediaEventHandler=new ExpediaEventHandler();
    				expediaEventHandler.postPrices(priceMap);
    				
    			}
    		}
    		
    	}
    }
    
    @Subscribe
    public void reservationEvent(ReservationEvent event) {
    	// handle event
    	System.out.println("Received price event: " + event.getReservation());
    	System.out.println("event.getChannelProductMaps().size() " + event.getChannelProductMaps().size());
    	
    	//code to call the respectiveHanlder based on the map
    	if(event.getChannelProductMaps()!=null && event.getChannelProductMaps().size()>0){
    		for(ChannelProductMap channelProductMap:event.getChannelProductMaps()){
    			//creating handler for Booking.com event
    			if(channelProductMap.getChannelId()==RazorConfig.getBookingChannelPartnerId()){
    				BookingEventHandler bookingEventHandler=new BookingEventHandler();
    				bookingEventHandler.postReservation(event.getReservation(),channelProductMap);
    				
    			}else if(channelProductMap.getChannelId()==RazorConfig.getHotelsCombinedChannelPartnerId()){
    				Map<ChannelProductMap,List<Reservation>> mapReservation=new HashMap<ChannelProductMap,List<Reservation>>();
    				List<Reservation> listReservation=new ArrayList<Reservation>();
    				listReservation.add(event.getReservation());
    				mapReservation.put(channelProductMap, listReservation);
    				HotelsCombinedEventHandler hotelsCombinedEventHandler=new HotelsCombinedEventHandler();
    				hotelsCombinedEventHandler.postReservations(mapReservation);
    				
    			}else if(channelProductMap.getChannelId()==RazorConfig.getExpediaChannelPartnerId()){
    				Map<ChannelProductMap,List<Reservation>> mapReservation=new HashMap<ChannelProductMap,List<Reservation>>();
    				List<Reservation> listReservation=new ArrayList<Reservation>();
    				listReservation.add(event.getReservation());
    				mapReservation.put(channelProductMap, listReservation);
    				ExpediaEventHandler expediaEventHandler=new ExpediaEventHandler();
    				expediaEventHandler.postReservations(mapReservation);
    				
    			}
    		}
    		
    	}
    }
}
