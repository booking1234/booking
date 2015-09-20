package com.mybookingpal.feed.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import net.cbtltd.rest.AbstractReservation;
import net.cbtltd.rest.Property;
import net.cbtltd.rest.Schedule;
import net.cbtltd.rest.mybookingpal.ProductSchedule;
import net.cbtltd.rest.mybookingpal.ReservedDates;
import net.cbtltd.rest.response.AvailabilityResponse;
import net.cbtltd.rest.response.WeeklyPriceResponse;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Reservation;

public class ReservationFeedGenerator extends AbstractReservation {
	final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	ReservedDates rd = null;
	private static ReservationFeedGenerator reservationfeedgenerator;
	private static final Boolean  TEST = new Boolean(true);  
	private ReservationFeedGenerator() {}
	public static ReservationFeedGenerator getInstance() {
		if(reservationfeedgenerator == null) {
			reservationfeedgenerator = new ReservationFeedGenerator();
		}
		return reservationfeedgenerator;
	}


	public  WeeklyPriceResponse getWeeklyPrices(String pos, String productid, String currency, SqlSession sqlSession){
		//return  getWeeklyPrices(pos, productid, "2014-07-25" ,"2015-07-25" , currency); 
		return getWeeklyPrices(pos, productid, null ,null , currency); 
	}

	public Schedule generatScheduleForProduct(String productid, String fromdate, String todate,  String pos  ) {
		return  getProduct( productid, fromdate,  todate, pos, null) ;
	}

	public AvailabilityResponse generateAvailabilitySchedule(String productid ,String POS){
		return getAvailabilityCalendar(POS, productid, null, null);
	}
	public ProductSchedule generatScheduleForProduct(Reservation reservation ,SqlSession sqlSession ) {

		ArrayList<Reservation> reserveddatesforproperty = sqlSession.getMapper(ReservationMapper.class).reserveredDatesForPropertyid(reservation);
		List <ReservedDates> reserveddates = new ArrayList<ReservedDates>(); 
		for(int i =0; i< reserveddatesforproperty.size(); i++){

			reserveddates.add(new ReservedDates(reserveddatesforproperty.get(i).getProductid(),reserveddatesforproperty.get(i).getReservationid(), 
					DF.format(reserveddatesforproperty.get(i).getFromdate()),	DF.format(reserveddatesforproperty.get(i).getTodate())));
		}

		ProductSchedule a = new ProductSchedule(NameId.Type.Product.name()  , reservation.getProductid()  , reserveddates);
		return  a;
	}


}