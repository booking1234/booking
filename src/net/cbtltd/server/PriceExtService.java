package net.cbtltd.server;

import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.PriceExtMapper;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.PriceExt;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class PriceExtService implements IsService {
	private static final Logger LOG = Logger.getLogger(PriceExtService.class.getName());
	private static PriceExtService service;
	
	public static synchronized PriceExtService getInstance() {
		if (service == null) {service = new PriceExtService();}
		return service;
	}
	
	
	public final PriceExt create(SqlSession sqlSession,  PriceExt action) {
		try {
			sqlSession.getMapper(PriceExtMapper.class).create(action);
		} catch (Throwable x) {
			x.printStackTrace();
			sqlSession.rollback();
			MonitorService.log(x);
		}
		return action;
	}
	
	public final PriceExt update(SqlSession sqlSession,  PriceExt action) {
		try {
			sqlSession.getMapper(PriceExtMapper.class).update(action);
		} catch (Throwable x) {
			sqlSession.rollback();
			MonitorService.log(x);
		}
		return action;
	}

}
