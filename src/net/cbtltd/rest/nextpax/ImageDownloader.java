package net.cbtltd.rest.nextpax;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Partner;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageDownloader {
	
	private final static Logger logger = LoggerFactory.getLogger(ImageDownloader.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CompositeConfiguration config = new CompositeConfiguration();
		config.addConfiguration(new SystemConfiguration());
		try {
			config.addConfiguration(new PropertiesConfiguration("storage.properties"));
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		
		String rootFolder = config.getString("bp.root.folder");
		
		String altpartyid = args[0];
		
		SqlSession sqlSession = RazorServer.openSession();
		try {
			if(StringUtils.isBlank(altpartyid)) {
//				altpartyid = "231051"; //NextPax partner GA
				altpartyid = "179795"; 
			}

			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(altpartyid);
			if (partner == null) {throw new ServiceException(Error.party_id, altpartyid);}
			A_Handler handler = new A_Handler(partner);
			handler.downloadImages(rootFolder);
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}

	}

}
