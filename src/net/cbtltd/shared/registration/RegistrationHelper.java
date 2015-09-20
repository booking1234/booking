package net.cbtltd.shared.registration;

import java.util.ArrayList;
import java.util.List;

import net.cbtltd.rest.Constants;
import net.cbtltd.rest.registration.ProductInfo;
import net.cbtltd.server.BCrypt;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ChannelPartnerMapper;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.ChannelPartner;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.PropertyManager;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.party.Organization;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/**
 *  Class with helper functionality for registration.
 * @author Kambur Vova
 *
 */
public class RegistrationHelper {

	private Logger LOG;

	public RegistrationHelper() {
		LOG = Logger.getLogger(RegistrationHelper.class.getName());
	}

	public RegistrationHelper(Logger logger) {

		if (logger == null) {
			LOG = Logger.getLogger(RegistrationHelper.class.getName());
		} else {
			this.LOG = logger;
		}
	}

	/**
	 * Check if user credentials are valid.
	 * @param login
	 * @param password
	 */
	public Boolean validateUser(String login, String password) {

		Boolean result = false;

		if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
			return result;
		}

		SqlSession sqlSession = RazorServer.openSession();
		try {

			PropertyManager item = sqlSession.getMapper(PartyMapper.class).readbyemailwithinfo(login);
			if (item != null) {
				String hashedPassword = item.getPassword();
				if (BCrypt.checkpw(password, hashedPassword)) {
					result = true;
				}
			}
		} catch (Throwable x) {
		} finally {
			sqlSession.close();
		}
		return result;
	}
	
	/**
	 * Check if user credentials are valid
	 * @param login
	 * @param password
	 * @return id of user.
	 */
	public String validateUserId(String login, String password) {

		String result = "false";

		if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
			return result;
		}

		SqlSession sqlSession = RazorServer.openSession();
		try {

			PropertyManager item = sqlSession.getMapper(PartyMapper.class).readbyemailwithinfo(login);
			if (item != null) {
				String hashedPassword = item.getPassword();
				if (BCrypt.checkpw(password, hashedPassword)) {
					result = item.getId();
				}
			}
		} catch (Throwable x) {
		} finally {
			sqlSession.close();
		}
		return result;
	}

	/**
	 * Get list of channel partners.  
	 * @param sqlSession
	 * @param pmId
	 * @return List<ChannelPartner>
	 */
	public List<ChannelPartner> getChannelPartnersList(SqlSession sqlSession, Integer pmId) {

		List<ChannelPartner> listCP = null;

		listCP = sqlSession.getMapper(ChannelPartnerMapper.class).list();

		if (listCP != null) {

			LOG.debug("List of  Channel Partners: count:'" + listCP.toArray().length + "'...");

			List<Integer> selectedChannelsId = sqlSession.getMapper(PartyMapper.class).listChannelID(pmId);

			if (selectedChannelsId != null && !selectedChannelsId.isEmpty()) {

				for (ChannelPartner channel : listCP) {
					if (selectedChannelsId.contains(channel.getId())) {
						channel.setSelected(true);
					}
				}
			}
		} else {
			throw new ServiceException(Error.channel_partner_data, "Invalid channel partner data.");
		}

		return listCP;
	}

	/**
	 * Save a list of channel partners.
	 * @param sqlSession
	 * @param channelPartnersIds
	 * @param pmId
	 */
	public void updateChannelPartnersList(SqlSession sqlSession, List<Integer> channelPartnersIds, Integer pmId) {

		if (pmId != null) {
			sqlSession.getMapper(PartyMapper.class).deleteChannelPartners(pmId);

			LOG.debug("Binding channel partners...");

			if (channelPartnersIds != null && !channelPartnersIds.isEmpty()) {
				for (Integer channelId : channelPartnersIds) {
					sqlSession.getMapper(PartyMapper.class).insertChannelPatner(pmId, channelId);
				}
			} else {
				throw new ServiceException(Error.channel_partner_data, "Listof channel partners ids is empty.");
			}
		} else {
			throw new ServiceException(Error.organization_id, "Invalid property manager id.");
		}

		LOG.debug("Binding channel partners:OK;");
	}

	/**
	 * Get property manager products count.
	 * @param sqlSession
	 * @param pmId
	 * @param newRegistration
	 * @param pmsID
	 * @param pmAltId
	 * @return Integer
	 */
	public Integer getProductsCount(SqlSession sqlSession, String pmId, Boolean newRegistration, String pmsID, String pmAltId) {

		Integer result = 0;
		String count = "";

		LOG.debug("Read list of products.");

		if (newRegistration) {

			count = sqlSession.getMapper(ProductMapper.class).productCountByAltSupplier(pmAltId, pmsID);

			if (count != null && !count.isEmpty()) {
				result = Integer.valueOf(count);
			}
		}else{
			
			if (pmId != null && !pmId.isEmpty()) {
				count = sqlSession.getMapper(ProductMapper.class).productCountBySupplier(pmId);

				if (count != null && !count.isEmpty()) {
					result = Integer.valueOf(count);
				}
			}			
		}
		
		return result;
	}
	
	/**
	 * Get products count selected by pm.
	 * @param sqlSession
	 * @param pmId
	 * @param newRegistration
	 * @param pmsID
	 * @param pmAltId
	 * @return Integer
	 */
	public Integer getSelectedProductsCount(SqlSession sqlSession, String pmId) {

		Integer result = null;
		String count = "";

		LOG.debug("Read list of selected products.");
			
		if (pmId != null && !pmId.isEmpty()) {
			
			count = sqlSession.getMapper(ProductMapper.class).selectedProductCountBySupplier(pmId);
			
			result = RegistrationUtils.getInteger(count);
		}			
		
		if ( result != null) { return result; }
		else { return 0; }
	}
	

	/**
	 * Get property manager products list.
	 * @param sqlSession
	 * @param action
	 * @param managerInfo
	 * @return List<ProductInfo>
	 */
	public List<ProductInfo> getProductsList(SqlSession sqlSession, NameIdAction action, PropertyManagerInfo managerInfo) {

		if (managerInfo == null) {
			throw new ServiceException(Error.organization_info, "Manager info is empty.");
		}

		LOG.debug("Read list of products.");
		List<ProductInfo> result = new ArrayList<ProductInfo>();

		result = sqlSession.getMapper(ProductMapper.class).productInfoListBySupplier(action);
/*
		if (listBySupplier != null && !listBySupplier.isEmpty()) {
			result.addAll(listBySupplier);
		}

		Boolean isNewRegistered = managerInfo.getNewRegistration() == null ? false : managerInfo.getNewRegistration();

		if (isNewRegistered) {
			Party party = sqlSession.getMapper(PartyMapper.class).read(pmId.toString());
			if (party != null && party.getAltid() != null && managerInfo.getPmsId() != null) {

				List<ProductInfo> newProducts = sqlSession.getMapper(ProductMapper.class).productInfoListByAltSupplier(party.getAltid(),
						String.valueOf(managerInfo.getPmsId()));

				if (newProducts != null && !newProducts.isEmpty()) {
					result.addAll(newProducts);
				}
			}
		}*/

		return result;
	}

	/**
	 * Is property manager support credit card. It depends on PMS information.
	 * @param sqlSession
	 * @param managerInfo
	 * @return Boolean
	 */
	public Boolean isManagerSupportCredirCard(SqlSession sqlSession, PropertyManagerInfo managerInfo) {

		Boolean result = false;
		if (managerInfo != null && managerInfo.getPmsId() != null) {
			result = sqlSession.getMapper(PartnerMapper.class).isSupportCreditCard(managerInfo.getPmsId().toString());
		}

		if (result != null) { return result;} 
		else { return false; }
	}
	
	/**
	 * Prepare new partner record for property manager.
	 * @param sqlSession
	 * @param organization - property manager
	 * @param partner
	 * @return
	 */
	
	public Partner getPartnerToCreate(SqlSession sqlSession, Party organization,  Partner partner){
		//check if PMS and pm exists 	
		if (partner == null || organization == null){ return null; }		
		
		// new partner record
		partner.setId(null);			
		partner.setPartyname(organization.getName());
		partner.setOrganizationid(partner.getOrganizationid());
		partner.setPartyid(organization.getId());
		partner.setState(Party.CREATED);
		partner.setAbbrevation(Constants.BLANK);			
		
		return partner;
	}

}
