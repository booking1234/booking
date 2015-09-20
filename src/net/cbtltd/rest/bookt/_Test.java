/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.0.10
 */

package net.cbtltd.rest.bookt;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.script.Handleable;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.apache.tools.ant.types.CommandlineJava.SysProperties;

/** The class _Test is to test the handler. */
public final class _Test implements Handleable {
	private static final Logger LOG = Logger.getLogger(_Test.class.getName());

	/**
	 * The main test method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	
	
    public static int numDaysBetween(Date date1, Date date2) {
        long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
        int diffInDays = (int) ((date1.getTime() - date2.getTime())/ DAY_IN_MILLIS );
       
        return Math.abs(diffInDays);
    } 
    
   // long daydiffernce = (int)reservation.getFromdate().getTime() - reservation.getTodate().getTime() /(24 * 60 * 60 * 1000);; 
    
    
	public static void main(String args[]) throws Exception {
		
	
		LOG.error("Bookt Started");
		SqlSession sqlSession = RazorServer.openSession();
		try {
			
			String altpartyid = "325567";
			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
			
		
			
			
			if (partner == null) {throw new ServiceException(Error.party_id, altpartyid);}
			A_Handler handler = new A_Handler(partner);
			if (handler == null) {throw new ServiceException(Error.service_absent, "Handler for " + altpartyid);}
			
			handler.readPrice(sqlSession, null, null, null);
			
			 if(true)return;  
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
			Reservation reservation = new Reservation(); 
			 
			
			 reservation.setFromdate(sdf.parse("2014-08-22"));
			 reservation.setTodate(sdf.parse("2014-08-23"));
			 reservation.setProductid("329541");
			 
			System.out.println( handler.isAvailable(sqlSession, reservation));;
	        if(true)return;  
	         ArrayList<Product> products = sqlSession.getMapper(ProductMapper.class).altlist("325567");
	        
	     //    System.out.println(handler.isAvailable(sqlSession, reservation));;
	    
	         
	         for(int i= 0; i<products.size();i++){
	        	 reservation.setProductid(products.get(i).getId());
	        	 if(handler.isAvailable(sqlSession, reservation)){
	        		 System.out.println("altID = "+ products.get(i).getAltid()+" ID = "+ products.get(i).getId()); 
	        		 
	        	 }
	         }
	         
	         
	         
			//handler.readProducts();
			//handler.readSchedule();
//			handler.readPrices();
//			handler.readSchedule();
//			Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationid);
//			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
//			System.out.println("\nisAvailable = " + handler.isAvailable(sqlSession, reservation, product.getAltid()));
//			handler.createReservation(sqlSession, reservation);
//			System.out.println("\ncreateReservation = " + reservation);
//			handler.readReservation(sqlSession, reservation);
//			handler.updateReservation(sqlSession, reservation);
//			handler.cancelReservation(sqlSession, reservation);
			sqlSession.commit();
		} 
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
			x.printStackTrace();
		} 
		finally {sqlSession.close();}
		LOG.error("Bookt Terminated");
		System.exit(0);
	}

	private A_Handler getHandler() {
		SqlSession sqlSession = RazorServer.openSession();
		String altpartyid = "61447";
		Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
		if (partner == null) {throw new ServiceException(Error.party_id, altpartyid);}
		A_Handler handler = new A_Handler(partner);
		return handler;
	}
	
	@Override
	public void readProducts() {
		getHandler().readProducts();
	}

	@Override
	public void readPrices() {
		getHandler().readPrices();
	}

	@Override
	public void readSchedules() {
		getHandler().readSchedule();
	}

	@Override
	public void readAlerts() {
		getHandler().readAlerts();
	}

	@Override
	public void readSpecials() {
		getHandler().readSpecials();
	}
	
	@Override
	public void readLocations() {
		getHandler().readLocations();
	}
	
	@Override
	public void readDescriptions() {
		getHandler().readDescriptions();
	}

	@Override
	public void readImages() {
		// TODO Auto-generated method stub
		
	}
}
