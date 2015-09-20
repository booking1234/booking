package com.mybookingpal.server.api.junit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import net.cbtltd.server.ChannelProductService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Unit;

import org.apache.ibatis.session.SqlSession;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mybookingpal.config.RazorModule;

public class PriceCreateUpdateTest {
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	static SqlSession sqlSession=null;
	
	@BeforeClass
	public static void setup(){
		Injector injector = Guice.createInjector(new RazorModule());
		sqlSession = RazorServer.openSession();
	}
	
	

	@Test
	public void testPriceEvent() {
		// '1', '121', '13104', '276', '2014-06-18 12:57:26', '1310404'
		System.out.println("Env "+sqlSession.getConfiguration().getEnvironment().getId());
		Product product = ChannelProductService.getInstance().readProductFromChannelProductMapper(sqlSession, 276, 
				"13104", "1310404");
		System.out.println("The Test product id is "+product.getId());
		Price price = new Price();
		price.setPartyid(product.getSupplierid());
		price.setEntitytype(NameId.Type.Product.name());
		price.setEntityid(product.getId());//productID
		price.setCurrency(product.getCurrency());
		price.setQuantity(1.0);
		price.setUnit(Unit.DAY);
		price.setRule(Price.Rule.AnyCheckIn.name());
		price.setName("Rack Rate");
		price.setValue(85.00);
		price.setMinimum(340.0);
		price.setMinStay(4);
		price.setState(Price.CREATED);
//		price.setMaxStay(1);
		price.setType(NameId.Type.Reservation.name());
		
		try {
//			price.setDate(DF.parse("2014-12-13"));
//			price.setTodate(DF.parse("2015-01-11"));
			
//			price.setDate(DF.parse("2015-01-12"));
//			price.setTodate(DF.parse("2015-02-11"));
			
//			price.setDate(DF.parse("2015-06-17"));
//			price.setTodate(DF.parse("2015-07-19"));
			price.setDate(DF.parse("2015-07-19"));
			price.setTodate(DF.parse("2015-08-20"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		price.setAvailable(1);

	
		Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
		if (exists == null) {
			System.out.println("Creating Price");
			sqlSession.getMapper(PriceMapper.class).create(price);
			sqlSession.commit();
		}else{
			System.out.println("Price already exists");
		}
//		fail("Not yet implemented");
	}
	
	@AfterClass
	public static void tearDown(){
		sqlSession.close();
	}

}
