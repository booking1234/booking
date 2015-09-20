package com.mybookingpal.server;

import com.google.inject.Inject;
import com.mybookingpal.wrapper.ReservationWrapper;

import net.cbtltd.shared.Party;
import net.cbtltd.shared.Reservation;

public class EmailService {
	
	@Inject public static TemplateService templateEngine;
	
	public static void sendReservationEmail(ReservationWrapper reservationWrapper) {
		String emailMessage = templateEngine.buildReservationEmail(reservationWrapper);
		
		System.out.println("sendReservationEmail Output: " + emailMessage);
		
		net.cbtltd.server.EmailService.sendBooking("Your Booking is reserved", reservationWrapper.getReservation().getEmailaddress(), emailMessage);
		
	}
	
	public static void sendCancellationEmail(ReservationWrapper reservationWrapper) {
		
		String emailMessage = templateEngine.buildCancellationEmail(reservationWrapper);
		System.out.println("sendCancellationEmail Output: " + emailMessage);
		net.cbtltd.server.EmailService.sendBooking("Your Booking is Cancelled", reservationWrapper.getReservation().getEmailaddress(), emailMessage);
	}

}
