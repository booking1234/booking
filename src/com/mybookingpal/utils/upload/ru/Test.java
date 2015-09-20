package com.mybookingpal.utils.upload.ru;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Partner;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class Test {

	private static final Logger LOG = Logger.getLogger(Test.class.getName());
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		LOG.debug("Rentals United Started");
		SqlSession sqlSession = RazorServer.openSession();
		try {
			String altpartyid = "325009";
			
			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
			if (partner == null) {throw new ServiceException(Error.party_id, altpartyid);}
			Handler handler = new Handler(partner);
//			handler.uploadAvailability();
//			handler.uploadReservations();
//			handler.uploadProperties();
//			handler.uploadPrices();
		} 
		catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
		} 
		finally {sqlSession.close();}
		LOG.debug("Interhome Terminated");
		System.exit(0);
	}
}
