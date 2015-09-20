package net.cbtltd.server;

import java.util.ArrayList;
import java.util.List;

import net.cbtltd.server.api.ChannelProductMapMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Product;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class ChannelProductService implements IsService  {

	
	private static final Logger LOG = Logger.getLogger(ChannelProductService.class.getName());
	private static ChannelProductService service;

	/**
	 * Gets the single instance of ProductService to manage Product instances.
	 * @see net.cbtltd.shared.Product
	 *
	 * @return single instance of ProductService.
	 */
	public static synchronized ChannelProductService getInstance() {
		if (service == null){service = new ChannelProductService();}
		return service;
	}
	
	/**
	 * 
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Product readProductFromChannelProductMapper(SqlSession sqlSession, Integer channelId,String channelProductId,String channelRoomId) {
		ChannelProductMap channelProductMap = null;
		Product product=null;
		try {
			ChannelProductMap tempChannelProductMap =new ChannelProductMap();
			tempChannelProductMap.setChannelId(channelId);
			tempChannelProductMap.setChannelProductId(channelProductId);
			tempChannelProductMap.setChannelRoomId(channelRoomId);
			channelProductMap = sqlSession.getMapper(ChannelProductMapMapper.class).readBPProduct(tempChannelProductMap);
			
			if (channelProductMap != null) {
				product = sqlSession.getMapper(ProductMapper.class).read(channelProductMap.getProductId());
			}
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return product;
	}
	
	/**
	 * 
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final List<ChannelProductMap> readAllProductFromChannelProductMapper(SqlSession sqlSession, Integer channelId) {
		List<ChannelProductMap> listchannelProductMap = null;
		List<Product> listProduct=new ArrayList<Product>();
		try {
			ChannelProductMap tempChannelProductMap =new ChannelProductMap();
			tempChannelProductMap.setChannelId(channelId);
			
			listchannelProductMap = sqlSession.getMapper(ChannelProductMapMapper.class).readAllBPProduct(tempChannelProductMap);
			
			/*if (listchannelProductMap != null && listchannelProductMap.size()>0) {
				for(ChannelProductMap channelProductMap:listchannelProductMap){
					listProduct.add(sqlSession.getMapper(ProductMapper.class).read(channelProductMap.getBpProductId()));	
				}
				
			}*/
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return listchannelProductMap;
	}
	
	/**
	 * 
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final List<ChannelProductMap> findByProductID(SqlSession sqlSession,String productId) {
		List<ChannelProductMap> listchannelProductMap = null;
	//	final SqlSession sqlSession = RazorServer.openSession("uat"); // replace this
		
		try {
			listchannelProductMap = sqlSession.getMapper(ChannelProductMapMapper.class).findByBPProductID(productId);
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return listchannelProductMap;
	}
	/**
	 * 
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final List<ChannelProductMap> findByProductAndChannelId(SqlSession sqlSession,String productId, int channelId) {
		List<ChannelProductMap> listchannelProductMap = new ArrayList<ChannelProductMap>();
		//final SqlSession sqlSession = RazorServer.openSession("uat"); // replace this
		
		try {
			ChannelProductMap channelProductMap = new ChannelProductMap();
			channelProductMap.setChannelId(channelId);
			channelProductMap.setProductId(productId);
			listchannelProductMap.add(sqlSession.getMapper(ChannelProductMapMapper.class).findByBPProductAndChannelId(channelProductMap));
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return listchannelProductMap;
	}



}