/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.0.8
 */
package net.cbtltd.rest.kigo;

import net.cbtltd.rest.bookt.A_Handler;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.api.HasUrls;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/**
 * The Class KigoRest is to access kigo RESTful web services. 
 * 
 * Will eventually be called by scheduler
 * @see https://sites.google.com/site/gson/gson-user-guide
 * @see http://www.mkyong.com/webservices/jax-rs/restfull-java-client-with-java-net-url/
 * @see http://www.xyzws.com/javafaq/how-to-add-basic-authentication-into-httpurlconnection/140
 * @see kigo.net and app.kigo.net
 */
public class Test {

	private static final Logger LOG = Logger.getLogger(Test.class.getName());

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SqlSession sqlSession = RazorServer.openSession();
		try {
			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists("1234");
			A_Handler handler = new A_Handler(partner);
			handler.readProducts();
			//handler.readAvailability(sqlSession, HasUrls.PARTY_KIGO_ID);
//			Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read("155144");
//			handler.createReservation(sqlSession, reservation);
//			handler.readReservation(sqlSession, reservation);
//			handler.updateReservation(sqlSession, reservation);
//			handler.cancelReservation(sqlSession, reservation);
			sqlSession.commit();
		} 
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.debug(x.getMessage());
		} 
		finally {sqlSession.close();}
		LOG.debug("Kigo Test Terminated");
		System.exit(0);
	}
}
