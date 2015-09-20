package net.cbtltd.rest.expedia.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.cbtltd.rest.expedia.availrate.utils.RateUtils;
import net.cbtltd.rest.expedia.booking.utils.ReservationUtils;
import net.cbtltd.rest.expedia.parr.utils.PARRUtils;
import net.cbtltd.server.ChannelProductService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Reservation.State;
import net.cbtltd.shared.Unit;

import org.apache.ibatis.session.SqlSession;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mybookingpal.config.RazorModule;

public class ExpediaTest {
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	static SqlSession sqlSession = null;

	@BeforeClass
	public static void setup() {
		Injector injector = Guice.createInjector(new RazorModule());
		sqlSession = RazorServer.openSession();
	}

	//@Test
	public void testNotifyAvilabilityReservationCreation() throws Exception {

		Product product = ChannelProductService.getInstance()
				.readProductFromChannelProductMapper(sqlSession, 600, "211",
						"20000");

		System.out.println("Product getid " + product.getId());

		Reservation reservation = new Reservation();
		reservation.setAltid("12345");
		reservation.setAgentid("800");
		reservation.setAltpartyid("12345678");
		reservation.setProductid(product.getId());
		reservation.setFromdate(DF.parse("2014-12-08"));
		reservation.setTodate(DF.parse("2014-12-10"));
		reservation.setState(State.Reserved.name());
		sqlSession.getMapper(ReservationMapper.class).create(reservation);
		sqlSession.commit();
		System.out.println("Reservation object id " + reservation.getId());
	}

	// @Test
	public void testNotifyAvilabilityReservationModification() throws Exception {
		NameId action = new NameId();
		action.setId("12345");
		action.setName("12345678");

		Reservation reservation = sqlSession.getMapper(ReservationMapper.class)
				.altread(action);
		reservation.setTodate(DF.parse("2014-12-11"));
		reservation.setState(State.Reserved.name());
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
		System.out.println("Reservation object id " + reservation.getId());
	}

	// @Test
	public void testNotifyAvilabilityReservationCancellation() throws Exception {
		// State.Cancelled.name()

		NameId action = new NameId();
		action.setId("12345");
		action.setName("12345678");
		Reservation reservation = sqlSession.getMapper(ReservationMapper.class)
				.altread(action);
		reservation.setState(State.Cancelled.name());

		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
		System.out.println("Reservation object id " + reservation.getId());

	}

	//@Test
	public void testNotifyRates() {
		RateUtils rateUtils=new RateUtils();
		rateUtils.setLoadSelectedProducts(true);
		List<String> listInputProducts = new ArrayList<String>();
		listInputProducts.add("121");
		listInputProducts.add("125");
		rateUtils.setListInputProducts(listInputProducts);
		rateUtils.notifyRates();
	}

	//@Test
	public void testNotifyRatesTrigger() {
		System.out.println("Env "
				+ sqlSession.getConfiguration().getEnvironment().getId());
		try {
			Product product = ChannelProductService.getInstance()
					.readProductFromChannelProductMapper(sqlSession, 600,
							"311", "30000");
			System.out.println("The Test product id is " + product.getId());
			Price price = new Price();
			price.setPartyid(product.getSupplierid());
			price.setEntitytype(NameId.Type.Product.name());
			price.setEntityid(product.getId());// productID
			price.setCurrency(product.getCurrency());
			price.setQuantity(1.0);
			price.setUnit(Unit.DAY);
			price.setRule(Price.Rule.AnyCheckIn.name());
			price.setName("Rack Rate");
			price.setValue(85.00);
			price.setMinimum(340.0);
			price.setMinStay(4);
			price.setMaxStay(5);
			price.setState(Price.CREATED);
			price.setType(NameId.Type.Reservation.name());
			price.setDate(DF.parse("2014-12-16"));
			price.setTodate(DF.parse("2014-12-31"));
			price.setAvailable(1);
			Price exists = sqlSession.getMapper(PriceMapper.class)
					.exists(price);
			if (exists == null) {
				System.out.println("Creating Price");
				sqlSession.getMapper(PriceMapper.class).create(price);
				sqlSession.commit();
			} else {
				System.out.println("Price already exists");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	 @Test
	public void testBookingRetrivalAndBookingConfirmation() {
		ReservationUtils reservationUtils = new ReservationUtils();
		//reservationUtils.fetchAndUpdateReservationsBasedOnBookingId("2");
		reservationUtils.fetchAndUpdateAllReservations();
	}

	// @Test
	public void testBookingConfirmation() {

	}

	// @Test
	public void testFetchProductDetails() {
		PARRUtils parrUtils = new PARRUtils();
		try {
			parrUtils.fetchProductDetails("211");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @Test
	public void testFetchAvailRateDetails() {
		PARRUtils parrUtils = new PARRUtils();
		try {
			parrUtils.fetchAvailRateDetails("121", "2014-11-11", "2014-11-12");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDown() {
		sqlSession.close();
	}
}
