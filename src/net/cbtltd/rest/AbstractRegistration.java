package net.cbtltd.rest;

import java.util.ArrayList;
import java.util.Date;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import net.cbtltd.rest.error.ApiResponse;
import net.cbtltd.rest.registration.AdditionalParams;
import net.cbtltd.rest.registration.ChannelPartnerResponse;
import net.cbtltd.rest.registration.LoginResponse;
import net.cbtltd.rest.registration.StatusResponse;
import net.cbtltd.server.EmailService;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.PartyService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.login.JAASCallbackHandler;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManager;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.partner.PartnerCreate;
import net.cbtltd.shared.party.Organization;
import net.cbtltd.shared.party.OrganizationCreate;
import net.cbtltd.shared.party.OrganizationUpdate;
import net.cbtltd.shared.registration.ManagerRequest;
import net.cbtltd.shared.registration.ManagerResponse;
import net.cbtltd.shared.registration.RegistrationHelper;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class AbstractRegistration extends AbstractParty {

	private static final Logger LOG = Logger.getLogger(AbstractRegistration.class.getName());	
	
	protected StatusResponse getRegistartionStatus(String email, String partyId) {
		SqlSession sqlSession = RazorServer.openSession();
		AdditionalParams additionalParams = new AdditionalParams();
		StatusResponse result;
		Integer step = 1;
		String pmsName = "";
		try {
			if (!email.isEmpty() || !partyId.isEmpty()){
				PropertyManager item = sqlSession.getMapper(PartyMapper.class).readbyemailwithinfo(email);
				if (item != null) {
					additionalParams.setNeedToLogin("1");
					additionalParams.setPos(Model.encrypt(item.getId()));
					if (item.getStepid() != null) {
						step = item.getStepid();
					}
					pmsName = item.getName();
				} else {
					Party pms = sqlSession.getMapper(PartyMapper.class).read(Party.decrypt(partyId));
					if (pms != null) {
						pmsName = pms.getName();
					} else {
						result = new StatusResponse(new ApiResponse("Invalid 'pos' parameter."), new AdditionalParams(null, "", ""));
						LOG.debug(result);
						return result;
					}
				}
				ApiResponse status = new ApiResponse();

				additionalParams.setPmsName(pmsName);
				additionalParams.setNextStep(step);

				result = new StatusResponse(status, additionalParams);
			}else {
				result = new StatusResponse(new ApiResponse(Error.registration_status_data.getMessage()), new AdditionalParams(step, "", pmsName));
			}
			
		} catch (Throwable x) {
			LOG.error(Error.registration_status_data.getMessage() + "\n" + x.getMessage());
			result = new StatusResponse(new ApiResponse(Error.registration_status_data.getMessage()), new AdditionalParams(step, "", pmsName));
		} finally {
			sqlSession.close();
		}
		LOG.debug(result);
		// MonitorService.monitor(message, timestamp);

		return result;
	}
	
	/*
	 * Property manager new registration process.
	 */
	protected ManagerResponse registerManager (ManagerRequest request){	
		String pos = request.getPos() == null ? Model.encrypt(request.getPmsId()) : request.getPos();
		ChannelPartnerResponse response =  createAccount(pos, null, request.getAccountId(), request.getCompany(), request.getFirstName(),
				request.getLastName(), request.getAddress(), request.getCity(), request.getState(), request.getZip(), "", request.getTelephoneNumber(),
				request.getEmail(), request.getPassword(), true);
		return new ManagerResponse(response.getStatus());
	}
	
	/*
	 * Property manager old registration process.
	 */
	protected ChannelPartnerResponse doCreateManager(String pos, String managerPos, String account, String companyName, String firstName,
			String lastName, String address, String city, String state, String code, String countryId, String telephone, String email,
			String password) {

		return createAccount(pos, managerPos, account, companyName, firstName,
				lastName, address, city, state, code, countryId, telephone,
				email, password, false);
	}

	/*
	 * Create new mybookingpal user
	 */
	private ChannelPartnerResponse createAccount(String pos, String managerPos,
			String account, String companyName, String firstName,
			String lastName, String address, String city, String state,
			String code, String countryId, String telephone, String email,
			String password, Boolean newRegistrationProcess) {
		Organization action;
		OrganizationCreate actionCreate;
		OrganizationUpdate actionUpdate;
		Boolean isNewRegisteredAccount = false;
		
		String creatorId = Party.CBT_LTD_PARTY;
		String pmsId = null;
		String managerId = null;
		
		if (pos != null && !pos.isEmpty()){ 
			pmsId = Model.decrypt(pos);
		}
		if (managerPos != null && !managerPos.isEmpty()){
			managerId = Model.decrypt(managerPos);	
		}

		SqlSession sqlSession = RazorServer.openSession();
		ChannelPartnerResponse response = new ChannelPartnerResponse();
		RegistrationHelper regHelper = new RegistrationHelper(LOG);
		
		PropertyManagerInfo managerInfo = null;
		PartyService partyService;
		
		try {
			
			if (pmsId != null && managerId != null){
				if (pmsId.equals(managerId)) { throw new Exception("Manager and PMS are the same."); }
			}
			
			if (email.isEmpty()) {throw new Exception("Manager email address is empty."); }
			
			Party manager = null;
			Party exists = sqlSession.getMapper(PartyMapper.class).readbyemailaddress(email);
	
			if (managerId == null) {
				if (exists != null) { throw new Exception("Manager with the same email already exists."); }
				isNewRegisteredAccount = true;
			}else{
				manager = sqlSession.getMapper(PartyMapper.class).read(managerId);
				if (manager == null) { throw new Exception("Invalid manager unique identifier."); }
				if (exists != null) {
					if (!managerId.equals(exists.getId())) { throw new Exception("Manager with the same email already exists."); }
				}
				managerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(managerId));
				if (managerInfo != null && managerInfo.getPmsId() != null){
					pmsId = managerInfo.getPmsId().toString();
				}
			}
			
			Integer productsCount = regHelper.getProductsCount(sqlSession, managerId, isNewRegisteredAccount, pmsId, account);
			Partner parentPartner = sqlSession.getMapper(PartnerMapper.class).exists(pmsId);
			
			if (productsCount == null || productsCount == 0){
				// Check if we can register pm without properties. 
				if (parentPartner != null && parentPartner.getRegisteremptyproperties() != null){
					if(!parentPartner.getRegisteremptyproperties()){
						throw new Exception("Product list for manager is empty.");
					}
				}else{
					throw new Exception("Product list for manager is empty.");
				}
			}
			
			if (isNewRegisteredAccount){
				actionCreate = new OrganizationCreate();
				partyService = PartyService.getInstance();
				actionCreate.setState(Party.INITIAL);
				actionCreate.setOldstate(Party.INITIAL);
				actionCreate.setOptions("11");
				actionCreate.setCurrency(Currency.Code.USD.name());
				actionCreate.setLanguage(Language.EN);				
				actionCreate.setCreatorid(creatorId);
				actionCreate.setUsertype(Constants.PM_USER_TYPE);
				action = partyService.execute(sqlSession, actionCreate);
				
				managerId = action.getId();
				EmailService.registration(email, companyName, password);
			}
			
			actionUpdate = new OrganizationUpdate();
			actionUpdate.setEmployerid(managerId);
			actionUpdate.setId(managerId);
			if (isNewRegisteredAccount){
				actionUpdate.setState(Party.SUSPENDED);
				actionUpdate.setOldstate(Party.INITIAL);
			}
			actionUpdate.setOptions("11");
			actionUpdate.setCreatorid(creatorId);
			actionUpdate.setAltid(account);
			actionUpdate.setName(companyName);
			actionUpdate.setExtraname(firstName.trim() + " " + lastName.trim());
			actionUpdate.setPostaladdress(address.trim() + ",\t" + city.trim() + ",\t" + state.trim());
			actionUpdate.setPostalcode(code);
			actionUpdate.setCountry(countryId);
			actionUpdate.setDayphone(telephone);
			actionUpdate.setEmailaddress(email);
			if (password != null && !password.isEmpty()) {
				actionUpdate.setPassword(Model.encrypt(password.trim()));
			}			
			actionUpdate.setNeedToSendEmail(false);
			
			actionUpdate.setValues(RelationService.read(sqlSession, Relation.PARTY_VALUE, actionUpdate.getId(), null));
			actionUpdate.setCurrencies(RelationService.read(sqlSession, Relation.ORGANIZATION_CURRENCY, actionUpdate.getId(), null));
			actionUpdate.setLanguages(RelationService.read(sqlSession, Relation.ORGANIZATION_LANGUAGE, actionUpdate.getId(), null));
			
			action = PartyService.execute(sqlSession, actionUpdate);

			if (parentPartner != null && parentPartner.getSeparatepmaccounts() != null){
				Partner managerPartner = sqlSession.getMapper(PartnerMapper.class).exists(managerId);				
				// check if we need to create new partner record for pm.
				if (parentPartner.getSeparatepmaccounts() && managerPartner == null){
					// prepare new partner record, general info copied from parent partner. 
					Partner partnerToCreate = regHelper.getPartnerToCreate(sqlSession, action, parentPartner);
					sqlSession.getMapper(PartnerMapper.class).create(partnerToCreate);
				}
			}			
			if (managerInfo == null){
				managerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(managerId));
			}

			if (managerInfo == null) {
				managerInfo = new PropertyManagerInfo();
				managerInfo.setPropertyManagerId(Integer.valueOf(action.getId()));
				if (pmsId != null) { managerInfo.setPmsId(Integer.valueOf(pmsId)); }
				if (isNewRegisteredAccount != null) { managerInfo.setNewRegistration(isNewRegisteredAccount); } 
				managerInfo.setCreatedDate(new Date());
				sqlSession.getMapper(PropertyManagerInfoMapper.class).create(managerInfo);
			}

			if (!newRegistrationProcess){
				managerInfo.setRegistrationStepId(2);								
				sqlSession.getMapper(PropertyManagerInfoMapper.class).updatebypmid(managerInfo);
				
				if (isNewRegisteredAccount){
					Product product = new Product();
					product.setSupplierid(managerId);
					product.setAltSupplierId(account);
					product.setAltpartyid(pmsId);
					sqlSession.getMapper(ProductMapper.class).updateSupplierIdByAltSupplier(product);
				}
			}
			
			sqlSession.commit();
			
			if (!newRegistrationProcess){
				LOG.debug("Get List of selected Channels for '" + managerId + "'...");
				response.setChannelPartners(regHelper.getChannelPartnersList(sqlSession, Integer.valueOf(managerId)));
				response.setSupportsCreditCard(regHelper.isManagerSupportCredirCard(sqlSession, managerInfo));
			}			
		
			response.setParams(new AdditionalParams(2, Model.encrypt(action.getId().toString()), ""));
			response.setStatus(new ApiResponse());
			
		} catch (Throwable x) {
			sqlSession.rollback();
			response.setParams(new AdditionalParams(1, managerPos, ""));
			response.setStatus(new ApiResponse("Failed to create manager: " + x.getMessage()));			
			LOG.error("\n" + x.getMessage());
		} finally {
			sqlSession.close();
		}
		// LOG.debug(result);

		return response;
	}
	
	protected LoginResponse doLogin(String user, String password) {
		SqlSession sqlSession = RazorServer.openSession();
		LoginResponse result;
		Integer step = 1;
		String pmsName = "";
		try {
			
			LoginContext loginContext = null;
			loginContext = new LoginContext("RazorLogin", new JAASCallbackHandler(user, password));
			loginContext.login();
						
/*			//get the subject 
			Subject subject = loginContext.getSubject();			
			//get principals
			Set<Principal> principals = subject.getPrincipals();*/			

			result = new LoginResponse(new AdditionalParams(step, "", pmsName), new ApiResponse());
		} catch (LoginException e){
			result = new LoginResponse(new AdditionalParams(step, "", pmsName), new ApiResponse("Authentication failed"));
		}
		catch (Throwable x) {
			LOG.error("\n" + x.getMessage());
			result = new LoginResponse(new AdditionalParams(step, "", ""), new ApiResponse(x.getMessage()));
		} finally {
			sqlSession.close();
		}
		return result;

	}
	
	protected String doLogout(){		
		try {
			LoginContext loginContext = new LoginContext("RazorLogin");
			loginContext.logout();
		} catch (LoginException e) {
			return "Fail\n ERROR:" + e.getMessage() + "\n\nStack:" + e.getStackTrace();
		}				
		return "OK";
	}

	public static String GetJsonParam(String jsonLine, String paramName) {
		JsonElement jelement = new JsonParser().parse(jsonLine);
		LOG.error(jsonLine);
		JsonObject jobject = jelement.getAsJsonObject();
		JsonElement paramValue = jobject.get(paramName);
		if (paramValue == null)
			return "";
		return paramValue.toString().replace("\"", "");
	}

}
