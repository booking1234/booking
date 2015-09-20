package net.cbtltd.rest.reservation;

import java.util.HashMap;
import java.util.Map;

import net.cbtltd.server.PartnerService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.PropertyManagerConfigurationMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.shared.PropertyManagerConfiguration;
import net.cbtltd.shared.PropertyManagerInfo;

import org.apache.ibatis.session.SqlSession;

import com.mybookingpal.config.RazorConfig;

/**
 * PM configuration. This is the class which is responsible for specific reservation algorithms.
 * 
 * @author rmelnyk
 *
 */
public class ReservationBuilderConfiguration {
	
	private ReservationBuilderConfiguration() {
		super();
		configuration = new HashMap<Step, Integer>();
		for(Step step : Step.values()) {
			configuration.put(step, 0);
		}
	}
	
	private Map<Step, Integer> configuration;

	public Map<Step, Integer> getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Map<Step, Integer> configuration) {
		this.configuration = configuration;
	}
	
	public static ReservationBuilderConfiguration getConfigurationForPm(Integer propertyManagerId) {
		ReservationBuilderConfiguration configuration = new ReservationBuilderConfiguration();
		SqlSession sqlSession = RazorServer.openSession();
		try {
			PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(propertyManagerId);
			PropertyManagerConfiguration config = sqlSession.getMapper(PropertyManagerConfigurationMapper.class).read(propertyManagerInfo.getConfigurationId());
			if(config == null) {
				config = sqlSession.getMapper(PropertyManagerConfigurationMapper.class).read(1);
			} 

			configuration.setConfiguration(createConfigurationObject(config));
			for(String livePricingPmId : PartnerService.getPartnerlivePricingIds()) {
				if(livePricingPmId.equals(propertyManagerId)) {
					configuration.getConfiguration().put(Step.PRICE, 1);
				}
			}
			
//			1 - Gateway
//			2 - Email method
//			3 - PMS API calling method
			switch(propertyManagerInfo.getPaymentProcessingType()) {
			case 1 : {
				configuration.getConfiguration().put(Step.PAYMENT, 0);
				break;
			}
			case 2 : {
				configuration.getConfiguration().put(Step.PAYMENT, 3);
				break;
			}
			case 3 : {
				configuration.getConfiguration().put(Step.PAYMENT, 1);
				break;
			}
			}
			
			if(isNovasol(propertyManagerInfo)) {
				configuration.getConfiguration().put(Step.PAYMENT, 2);
				configuration.getConfiguration().put(Step.PARTNER_RESERVATION, 1);
				configuration.getConfiguration().put(Step.VERIFY_AND_CANCEL, 1);
			}
			return configuration;
		} finally {
			sqlSession.close();
		}
	}
	
	private static Map<Step, Integer> createConfigurationObject(PropertyManagerConfiguration configuration) {
		Map<Step, Integer> map = new HashMap<Step, Integer>();
		map.put(Step.VALIDATION, configuration.getValidation());
		map.put(Step.INITIALIZATION, configuration.getInitialization());
		map.put(Step.PRICE, configuration.getPrice());
		map.put(Step.AVAILABILITY, configuration.getAvailability());
		map.put(Step.PAYMENT, configuration.getPayment());
		map.put(Step.PARTNER_RESERVATION, configuration.getPartnerReservation());
		map.put(Step.VERIFY_AND_CANCEL, configuration.getVerifyAndCancel());
		map.put(Step.PAYMENT_TRANSACTION, configuration.getPaymentTransaction());
		map.put(Step.FORMAT, configuration.getFormat());
		map.put(Step.SEND_ADMIN_EMAIL, configuration.getSendAdminEmail());
		return map;
	}
	
	private static boolean isNovasol(PropertyManagerInfo propertyManagerInfo) {
		return propertyManagerInfo.getPropertyManagerId() == Integer.valueOf(RazorConfig.getNextpaxNovasolId());  
	}
}
