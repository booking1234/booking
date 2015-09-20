package net.cbtltd.server;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.PriceExtMapper;
import net.cbtltd.server.api.PropertyDealRatesMapper;
import net.cbtltd.shared.PriceExt;
import net.cbtltd.shared.PropertyDealRates;

public class PropertyDealRatesService implements IsService  {

	private static final Logger LOG = Logger.getLogger(PropertyDealRatesService.class.getName());
	private static PropertyDealRatesService service;
	
	public static synchronized PropertyDealRatesService getInstance() {
		if (service == null) {service = new PropertyDealRatesService();}
		return service;
	}
	
	public final void create(SqlSession sqlSession,  PropertyDealRates action) {
		try {
			PropertyDealRates ratesFromDB=sqlSession.getMapper(PropertyDealRatesMapper.class).existsForProductId(action);
			if(ratesFromDB==null){
				sqlSession.getMapper(PropertyDealRatesMapper.class).create(action);	
			}else{
				LOG.info("Deal Rate is already created for this property");
			}
			
		} catch (Throwable x) {
			x.printStackTrace();
			sqlSession.rollback();
			MonitorService.log(x);
		}
		
	}
	
	
}
