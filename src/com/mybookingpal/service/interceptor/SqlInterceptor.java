package com.mybookingpal.service.interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.cbtltd.server.ChannelProductService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ChannelPartnerMapper;
import net.cbtltd.shared.ChannelPartner;
import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Reservation;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.mybookingpal.eventbus.EventBusManager;
import com.mybookingpal.service.annotations.SqlUpdateMarker;
import com.mybookingpal.utils.BPThreadLocal;

@Intercepts({ @Signature(type = Executor.class, method = "update", args = {
		MappedStatement.class, Object.class }) })
public class SqlInterceptor implements Interceptor {
	public static String COUNT = "_count";
	private static int MAPPED_STATEMENT_INDEX = 0;
	private static int PARAMETER_INDEX = 1;
	
	private static final Logger LOG = Logger.getLogger(SqlInterceptor.class);
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		
		
		Object object = invocation.proceed();
		
		// once the sql get's executed and entity gets updated process the sql
		// transaction to generate event
		// wrap this in try catch to make sure the main thread process goes on
		try {
			postProcessSql(invocation.getArgs());
		} catch(Exception e) {
			LOG.error("Error post processing db updates: " + e);
			// TO DO - possibly send an email
		}
		
		return object;
	}

	@SuppressWarnings("rawtypes")
	private void postProcessSql(final Object[] queryArgs) {
		final SqlSession sqlSession = RazorServer.openSession();
		Object parameter = queryArgs[PARAMETER_INDEX];
		MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
		
		String mapperMethod = ms.getId();
		String mapperClass = mapperMethod.substring(0, mapperMethod.lastIndexOf('.'));
		mapperMethod = mapperMethod.substring(mapperMethod.lastIndexOf('.') + 1);
		
//		System.out.println("Mapped statement: " + ms);
		
		// if marked for event generation call the publisher
		if(isMarkedForEventGeneration(mapperClass, mapperMethod)) {
			if(parameter instanceof Price) {
				// now check if the product id belongs to ChannelProductMap
				List<ChannelProductMap> channelProductMaps = ChannelProductService.getInstance().findByProductID(sqlSession,((Price)parameter).getEntityid());
				if(channelProductMaps.size() > 0) {
					EventBusManager.getEventPublisher().postPriceEvent((Price)parameter,channelProductMaps);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else if(parameter instanceof Reservation) {
				Reservation reservation=(Reservation) parameter;
				List<ChannelProductMap> channelProductMapsToBeProcessed =new ArrayList<ChannelProductMap>();
				// now check if the product id belongs to ChannelProductMap
				List<ChannelProductMap> channelProductMaps = ChannelProductService.getInstance().findByProductID(sqlSession,reservation.getProductid());
				for(ChannelProductMap channelProductMap:channelProductMaps){
					ChannelPartner channelPartner = sqlSession.getMapper(
							ChannelPartnerMapper.class).read(channelProductMap.getChannelId());
					if(reservation.getAgentid()!=null && (Integer.parseInt(reservation.getAgentid())!=channelPartner.getPartyId())){
						channelProductMapsToBeProcessed.add(channelProductMap);
					}
				}
				// skip the rules for booking.com like channels
				if(BPThreadLocal.get() != null && BPThreadLocal.get()) {
					// do not send reservation event back to channel
				}
				else {
					// now check if the product id belongs to ChannelProductMap
					if(channelProductMapsToBeProcessed.size() > 0) {
					if(channelProductMaps.size() > 0) {
						EventBusManager.getEventPublisher().postReservationEvent((Reservation)parameter,channelProductMaps);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						}
					}
				}
			}
		}
		sqlSession.close();
	}

	private boolean isMarkedForEventGeneration(String className, String methodName) {
		try {
			Class clazz = Class.forName(className);
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				if(method.getName().equalsIgnoreCase(methodName)) {
					SqlUpdateMarker annos = method.getAnnotation(SqlUpdateMarker.class);
		            if (annos != null) {
		                return true;
		            }
				}
	        }
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}


	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}
}
