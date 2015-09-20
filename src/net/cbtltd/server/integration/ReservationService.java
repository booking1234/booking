package net.cbtltd.server.integration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.cbtltd.json.JSONService;
import net.cbtltd.json.Parameter;
import net.cbtltd.rest.response.CalendarElement;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Product;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class ReservationService  implements IsService  {
	private static final Logger LOG = Logger.getLogger(ReservationService.class
			.getName());
	private static ReservationService service;

	/**
	 * Gets the single instance of ProductService to manage Product instances.
	 * 
	 * @see net.cbtltd.shared.Product
	 * 
	 * @return single instance of ProductService.
	 */
	public static synchronized ReservationService getInstance() {
		if (service == null) {
			service = new ReservationService();
		}
		return service;
	}

	

	/**
	 * @param sqlSession
	 * @param product
	 * @param listBookedDate
	 */
	public void persistBookedDate(SqlSession sqlSession, Product product,
			List<Date> listBookedDate) {
		List<Date> reservationList = PartnerService.getReservationList(sqlSession,product.getId());
		 
		for (Date bkDate : listBookedDate) {
			if (!reservationList.contains(bkDate)) {
				PartnerService.createSchedule(sqlSession, product, bkDate,
						bkDate, new Date());
			}
		}
		for (Date cdDate : reservationList) {
			if (!listBookedDate.contains(cdDate)) {
				PartnerService.deleteReservation(sqlSession,product.getId(),cdDate);
			}
		}
	}
	
	public ArrayList<CalendarElement> productSchedule(SqlSession sqlSession,String productid){
		return productSchedule(sqlSession,productid,new Date());
	}
	public ArrayList<CalendarElement> productSchedule(SqlSession sqlSession,String productid,Date startingDate){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(startingDate);
		calendar.add(Calendar.YEAR, 1);
		return productSchedule(sqlSession,productid,startingDate,calendar.getTime());
	}
	public ArrayList<CalendarElement> productSchedule(SqlSession sqlSession,String productid,Date startingDate,Date endingDate){
		Parameter action = new Parameter();
		action.setId(productid);
		action.setFromdate(JSONService.DF.format(startingDate));
		action.setTodate(JSONService.DF.format(endingDate));
		return sqlSession.getMapper(ReservationMapper.class).calendarelement(action);
	}
	
}