package com.mybookingpal.server.api.junit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import net.cbtltd.server.ChannelProductService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Reservation.State;

import org.apache.ibatis.session.SqlSession;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mybookingpal.config.RazorModule;

public class ReservationCreateUpdateTest {
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	static SqlSession sqlSession=null;
	
	@BeforeClass
	public static void setup(){
		Injector injector = Guice.createInjector(new RazorModule());
		sqlSession = RazorServer.openSession();
	}
	
	
	//@Test
	public void testCreateReservationEvent() throws Exception {
	
		Product product = ChannelProductService.getInstance().readProductFromChannelProductMapper(sqlSession, 276, 
				"13104", "1310404");
		
		Reservation reservation =new Reservation();
		reservation.setAltid("12345");
		reservation.setAltpartyid("5");
		reservation.setProductid(product.getId());
		reservation.setFromdate(DF.parse("2015-09-23"));
		reservation.setTodate(DF.parse("2015-09-25"));
		reservation.setState(State.Reserved.name());
		sqlSession.getMapper(ReservationMapper.class).create(reservation);
		sqlSession.commit();
		System.out.println("Reservation object id " + reservation.getId());
	}
	
	@Test
	public void testUpdateReservationEvent() throws Exception {
		NameId action=new NameId();
		action.setId("12345");
		action.setName("5");
		
		Reservation reservation=sqlSession.getMapper(ReservationMapper.class).altread(action);
		reservation.setTodate(DF.parse("2015-09-27"));
		reservation.setState(State.Reserved.name());
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
		System.out.println("Reservation object id " + reservation.getId());
	}
	
	
	//@Test
	public void testCancelReservationEvent() throws Exception {
	//State.Cancelled.name()

		NameId action=new NameId();
		action.setId("12345");
		action.setName("5");
		Reservation reservation=sqlSession.getMapper(ReservationMapper.class).altread(action);
		reservation.setState(State.Cancelled.name());
		
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
		System.out.println("Reservation object id " + reservation.getId());
	
	}
	
	@AfterClass
	public static void tearDown(){
		sqlSession.close();
	}
}
