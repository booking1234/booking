package net.cbtltd.rest.openbook;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Reservation;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class _Test {
	private static final Logger LOG = Logger.getLogger(_Test.class.getName());

	/**
	 * The main test method.
	 * 
	 * Razor System: Live � It is my impression that we are using a test hotel within the production/live system
	 * Manager Login: testing@openbook.com
	 * Product/Property: Razor ID and Partner (OpenBook) ID 26ea2b3185789e5f
	 * Reservation: Razor ID and Partner (OpenBook) ID 133832
	 * Data Source: Razor Console, XML or jQuery �
	 * Description: Brief description of the question/problem.
	 * 
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String args[]) throws Exception {
		LOG.error("Openbook Started");
		SqlSession sqlSession = RazorServer.openSession();
		try {
			String altpartyid = "133832"; //Openbook manager 
			String reservationid = "7224903"; //"2750513"; //"2742989"; //Openbook reservation 2733822, 2733826, 2743287, 2744901, 2744897, 2742982, 2742989
			
			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
			if (partner == null) {throw new ServiceException(Error.party_id, altpartyid);}
			A_Handler handler = new A_Handler(partner);
//			handler.readLocations();
//			handler.readDescriptions(sqlSession, "outsidedescription_" + "en" + ".xml", Language.Code.en, "House/Residence");
//			handler.readDescriptions(sqlSession, "insidedescription_" + "en" + ".xml", Language.Code.en, "Interior");
//			handler.readProducts();
//			handler.readPrices();
//			handler.readSchedule();
			Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationid);
//			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
//			System.out.println("isAvailable = " + handler.isAvailable(sqlSession, reservation, product.getAltid()));
			handler.createReservation(sqlSession, reservation);
//			handler.readReservation(sqlSession, reservation);
//			handler.updateReservation(sqlSession, reservation);
//			handler.cancelReservation(sqlSession, reservation);
//			sqlSession.commit();
		} 
		catch (Throwable x) {
			sqlSession.rollback();
			x.getMessage();
		} 
		finally {sqlSession.close();}
		LOG.error("Openbook Terminated");
		System.exit(0);
	}
}
