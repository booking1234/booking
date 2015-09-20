package net.cbtltd.server.integration;

import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.PropertyManagerSupportCCMapper;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.PropertyManagerSupportCC;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class PropertyService implements IsService{
	private static final Logger LOG = Logger.getLogger(PropertyService.class
			.getName());
	private static PropertyService service;
	/**
	 * Gets the single instance of ProductService to manage Product instances.
	 * 
	 * @see net.cbtltd.shared.Product
	 * 
	 * @return single instance of ProductService.
	 */
	public static synchronized PropertyService getInstance() {
		if (service == null) {
			service = new PropertyService();
		}
		return service;
	}
	/**
	 * to update the property support info
	 * @param sqlSession
	 * @param support
	 */
	public void updatePropertySupportCCInfo(SqlSession sqlSession,
			PropertyManagerSupportCC support) {
		if(support.getId()==null||support.getId()==0){
			sqlSession.getMapper(PropertyManagerSupportCCMapper.class).create(support);	
		}else{
			sqlSession.getMapper(PropertyManagerSupportCCMapper.class).update(support);
		}
	}
	
	/**
	 * to update the property support info
	 * @param sqlSession
	 * @param support
	 */
	public void updatePropertySupportInfo(SqlSession sqlSession,
			PropertyManagerInfo info) {
		if(info.getId()==null||info.getId()==0){
			sqlSession.getMapper(PropertyManagerInfoMapper.class).create(info);	
		}else{
			sqlSession.getMapper(PropertyManagerInfoMapper.class).update(info);
		}
		
		
	}
	
	/**
	 * to persist the property support info
	 * @param sqlSession
	 * @param parseInt
	 * @return PropertyManagerSupportCC
	 */
	public PropertyManagerSupportCC getPropertyManagerInfo(SqlSession sqlSession,int parseInt) {
		PropertyManagerSupportCC supportCC= sqlSession.getMapper(PropertyManagerSupportCCMapper.class).readbypartyid( parseInt );
		if (supportCC == null || supportCC.getPartyId() == null) {
			supportCC = new PropertyManagerSupportCC();
			supportCC.setPartyId(parseInt);
		}
		return supportCC;
	}


}
