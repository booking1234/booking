/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.trial.test;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import net.cbtltd.json.JSONService;
import net.cbtltd.server.BCrypt;
import net.cbtltd.server.RazorServer;
import net.cbtltd.shared.Party;

/**
 * The Class WidgetTest is to test widgets.
 */
public class WidgetTest {

	public static final Logger LOG = Logger.getLogger(WidgetTest.class.getName());
	private static final String password = BCrypt.hashpw("password", BCrypt.gensalt());

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SqlSession sqlSession = RazorServer.openSession();
		try {
			Party agent = JSONService.getParty(sqlSession, "pos_code");
			Party customer = JSONService.getCustomer (sqlSession, "1@test.africa", "Marshall", "Jim", "5", "(095)-497-87-44", agent);
			System.out.println("Customer " + customer);
			sqlSession.commit();
		} 
		catch(Throwable x) {
			sqlSession.rollback();
			System.out.println("Error " + x.getMessage());
		}
		finally {sqlSession.close();}
		System.out.println("Test Ended");
	}
}
