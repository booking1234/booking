package net.cbtltd.shared.registration;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.cbtltd.rest.error.ApiResponse;
import net.cbtltd.rest.registration.ACHPaymentInfo;
import net.cbtltd.rest.registration.AdditionalParams;
import net.cbtltd.rest.registration.BillingPoliciesInfo;
import net.cbtltd.rest.registration.CancellationPolice;
import net.cbtltd.shared.ChannelPartner;
import net.cbtltd.rest.registration.CreditCardTypes;
import net.cbtltd.rest.registration.PaymentGatewayInfo;
import net.cbtltd.rest.registration.ProductInfoList;
import net.cbtltd.rest.registration.PropertyManager;
import net.cbtltd.rest.registration.TIME_INTERVAL;
import net.cbtltd.rest.rent.RentProperty;
import net.cbtltd.server.PartyService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ManagerToGatewayMapper;
import net.cbtltd.server.api.PaymentMethodMapper;
import net.cbtltd.server.api.PropertyManagerCancellationRuleMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.PropertyManagerSupportCCMapper;
import net.cbtltd.server.util.MarshallerHelper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.PaymentMethod;
import net.cbtltd.shared.PropertyManagerCancellationRule;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.PropertyManagerSupportCC;
import net.cbtltd.shared.finance.gateway.FundsHolderEnum;
import net.cbtltd.shared.finance.gateway.PaymentGatewayHolder;
import net.cbtltd.shared.finance.gateway.PaymentProcessingTypeEnum;
import net.cbtltd.shared.finance.gateway.dibs.model.DibsAuthentication;
import net.cbtltd.shared.party.Organization;
import net.cbtltd.shared.party.OrganizationRead;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
public class FullDataResponse extends StepResponse implements RegistrationResponse {

	private static final Logger LOG = Logger.getLogger("Registration");
	@XmlTransient
	private Gson GSON = new Gson();

	public FullDataResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FullDataResponse(AdditionalParams stepData, ApiResponse status) {
		super();
		this.stepData = stepData;
		setStatus(status);
	}

	@XmlElement(name = "step_data")
	private AdditionalParams stepData;

	/* == Create Manager Information == */
	private PropertyManager manager;

	/* == Second Step Information == */
	@XmlElement(name = "channel_partners")
	private List<ChannelPartner> channelPartners;
	@SerializedName("channel_partners_list_ids")
	private List<Integer> channel_partners_list_ids;

	/* == Third Step Information == */

	@XmlElement(name = "products")
	private ProductInfoList productInfo;

	/* == Fourth Step Information == */
	@XmlElement(name = "billing_policies")
	@SerializedName("billing_policies")
	private BillingPoliciesInfo billingPolicies;

	/* == Fifth Step Information == */
	private List<NameId> gateways;
	@XmlElement(name = "gateway")
	@SerializedName("gateway")
	private PaymentGatewayInfo paymentGatewayInfo;

	private Integer payment = null;

	@XmlElement(name = "support_credit_card")
	@SerializedName("support_credit_card")
	private Boolean supportCreditCard = false;

	@XmlElement(name = "credit_card_types")
	@SerializedName("credit_card_types")
	private CreditCardTypes creditCardTypes;

	/* == Sixth Step Information == */

	@XmlElement(name = "manager_payment_type")
	@SerializedName("manager_payment_type")
	private String managerPaymentType;

	@XmlElement(name = "manager_paypal")
	@SerializedName("manager_paypal")
	private String managerPaypal;

	@XmlElement(name = "manager_ach")
	@SerializedName("manager_ach")
	private ACHPaymentInfo managerACH;

	/* == END Information for Steps== */

	@Override
	public RegistrationResponse getResponse() {
		return this;
	}

	public PaymentGatewayInfo getPaymentGatewayInfo() {
		return paymentGatewayInfo;
	}

	public void setPaymentGatewayInfo(PaymentGatewayInfo paymentGatewayInfo) {
		this.paymentGatewayInfo = paymentGatewayInfo;
	}

	public PropertyManager getManager() {
		return manager;
	}

	public void setManager(PropertyManager manager) {
		this.manager = manager;
	}

	public BillingPoliciesInfo getBillingPolicies() {
		return billingPolicies;

	}

	public void setBillingPolicies(BillingPoliciesInfo billingPolicies) {
		this.billingPolicies = billingPolicies;
	}

	public List<NameId> getGateways() {
		return gateways;
	}

	public void setGateways(List<NameId> gateways) {
		this.gateways = gateways;
	}

	public String getManagerPaymentType() {
		return managerPaymentType;
	}

	public void setManagerPaymentType(String managerPaymentType) {
		this.managerPaymentType = managerPaymentType;
	}

	public String getManagerPaypal() {
		return managerPaypal;
	}

	public void setManagerPaypal(String managerPaypal) {
		this.managerPaypal = managerPaypal;
	}

	public ACHPaymentInfo getManagerACH() {
		return managerACH;
	}

	public void setManagerACH(ACHPaymentInfo managerACH) {
		this.managerACH = managerACH;
	}

	public AdditionalParams getStepData() {
		return stepData;
	}

	public void setStepData(AdditionalParams stepData) {
		this.stepData = stepData;
	}

	public List<ChannelPartner> getChannelPartners() {
		return channelPartners;
	}

	public void setChannelPartners(List<ChannelPartner> channelPartners) {
		this.channelPartners = channelPartners;
	}

	public ProductInfoList getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfoList productInfo) {
		this.productInfo = productInfo;
	}

	public List<Integer> getChannel_partners_list_ids() {
		return channel_partners_list_ids;
	}

	public void setChannel_partners_list_ids(List<Integer> channel_partners_list_ids) {
		this.channel_partners_list_ids = channel_partners_list_ids;
	}

	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	public Boolean getSupportCreditCard() {
		return supportCreditCard;
	}

	public void setSupportCreditCard(Boolean supportCreditCard) {
		this.supportCreditCard = supportCreditCard;
	}

	public CreditCardTypes getCreditCardTypes() {
		return creditCardTypes;
	}

	public void setCreditCardTypes(CreditCardTypes creditCardTypes) {
		this.creditCardTypes = creditCardTypes;
	}

	public FullDataResponse(String pos) {
		super();

		PropertyManagerInfo managerInfo;
		SqlSession sqlSession = RazorServer.openSession();
		RegistrationHelper regHelper = new RegistrationHelper(LOG);
		Integer nextStep = null;

		try {
			setManagerId(pos);

			PartyService partyService = PartyService.getInstance();
			managerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(this.getPmId());

			OrganizationRead organizationRead = new OrganizationRead();
			organizationRead.setId(this.getPmId().toString());
			Organization organization = partyService.execute(sqlSession, organizationRead);

			LOG.debug("PartyService -> organizationRead: ok; party id:" + this.getPmId());

			if (organization != null) {

				LOG.debug("Fill Manager INFO: ...");
				this.fillManager(organization);
			} else {
				throw new ServiceException(Error.organization_id, "Invalid 'pos' value.");
			}

			if (managerInfo != null) {
				
				nextStep = managerInfo.getRegistrationStepId();
				this.setSupportCreditCard(regHelper.isManagerSupportCredirCard(sqlSession, managerInfo));

				LOG.debug("Fill Billing Policies: ... ");
				this.fillBillingPolicies(sqlSession, organization, managerInfo);

				LOG.debug("Get List of selected Channels for '" + this.getPmId() + "'...");
				this.setChannelPartners(regHelper.getChannelPartnersList(sqlSession, this.getPmId()));

				/*
				 * Create ProductInfoList object that consists a list of
				 * products and information related to UI pagination.
				 */
				ProductInfoList productInfoList = new ProductInfoList();
				// Set page number
				productInfoList.setPage("1");
				// Set product count per page
				productInfoList.setPerPage(StepResponse.DEFAULT_PRODUCTS_COUNT_PER_PAGE);
				// Set products total count value 
				productInfoList.setTotalCount(regHelper.getProductsCount(sqlSession, this.getPmId().toString(), false, null, null).toString());
				
				/* Get products list related to manager using NameIdAction */ 
				NameIdAction action = new NameIdAction();
				action.setId(this.getPmId().toString());
				action.setNumrows(Integer.valueOf(StepResponse.DEFAULT_PRODUCTS_COUNT_PER_PAGE));
				productInfoList.setList(regHelper.getProductsList(sqlSession, action, managerInfo));
				
				this.setProductInfo(productInfoList);

				/* Set related Payment Gateway info */

				if (this.getPayment() != null) {

					if (this.getPayment() == 0) { // Another payment gateway selected (PayPal, Authorize .NET, ...)
						ManagerToGateway managerToGateway = sqlSession.getMapper(ManagerToGatewayMapper.class).readBySupplierId(this.getPmId());
						if (managerToGateway != null) {

							PaymentGatewayInfo gatewayInfo = new PaymentGatewayInfo();

							gatewayInfo.setId(String.valueOf(managerToGateway.getPaymentGatewayId()));
							gatewayInfo.setName("");
							gatewayInfo.setAccountId(managerToGateway.getGatewayAccountId());
							gatewayInfo.setTransactionKey(managerToGateway.getGatewayCode());
							try {
								if (PaymentGatewayHolder.getPaymentGateway(managerToGateway.getPaymentGatewayId()).getName().equals(PaymentGatewayHolder.DIBS)){
									gatewayInfo.setAdditionalInfo(MarshallerHelper.fromXml(DibsAuthentication.class, managerToGateway.getAdditionalInfo()).getMerchantId());
								}else if (PaymentGatewayHolder.getPaymentGateway(managerToGateway.getPaymentGatewayId()).getName().equals(PaymentGatewayHolder.RENT)){
									gatewayInfo.setAdditionalInfo(MarshallerHelper.fromXml(RentProperty.class, managerToGateway.getAdditionalInfo()).getPropertyCode());
								}else{									
									gatewayInfo.setAdditionalInfo(managerToGateway.getAdditionalInfo());
								}
							} catch (JAXBException e) {
								LOG.warn("Unable to unmarshal MerchantID.");
								gatewayInfo.setAdditionalInfo(null);
							}

							this.setPaymentGatewayInfo(gatewayInfo);
						}
					} else if (this.getPayment() == 1) { // Bookingpal payment gateway selected to process payments. See FifthStepResponse
						
						PaymentMethod paymentMethod = sqlSession.getMapper(PaymentMethodMapper.class).read_by_pm(this.getPmId());
						if (paymentMethod != null) {

							this.setManagerPaymentType(paymentMethod.getType());

							if (paymentMethod.getType().equals("3")) {
								this.setManagerACH(GSON.fromJson(paymentMethod.getPaymentInfo(), ACHPaymentInfo.class));
							} else if (paymentMethod.getType().equals("2")) {
								this.setManagerPaypal(paymentMethod.getPaymentInfo());
							}
						}
					}
				}
				
				/* Set info about using credit cards. */
				this.fillCreditCardTypes(sqlSession, this.getPmId());
			}

			this.setStepData(new AdditionalParams(nextStep, pos, ""));

		} catch (Throwable e) {
			sqlSession.rollback();
			for (StackTraceElement stackTraceElement : e.getStackTrace()) {
				LOG.error(stackTraceElement.toString());
			}
			if (e.getMessage() != null) {
				setStatus(new ApiResponse(e.getMessage()));
			} else {
				setStatus(new ApiResponse(e.getCause().getMessage()));
			}
			setStepData(new AdditionalParams(1, pos, ""));

			LOG.error(" FullDataResponse(); message:" + e.getMessage());

		} finally {
			sqlSession.close();
		}
	}

	/**
	 * Add information about property manager to response.
	 * 
	 * @param organization
	 */
	private void fillManager(Organization organization) {

		PropertyManager manager = new PropertyManager();

		try {
			if (organization != null) {
				manager.setAccount(organization.getAltid());
				manager.setCompanyName(organization.getName());

				String extraName = organization.getExtraname().trim();
				if (!extraName.isEmpty()) {

					String lastName = "";
					Integer spaceIndex = organization.getExtraname().indexOf(" ");
					if (spaceIndex > 0 && spaceIndex < extraName.length()) {
						lastName = extraName.substring(spaceIndex);
						manager.setFirstName(extraName.replace(lastName, ""));
						manager.setLastName(lastName);
					} else {
						manager.setFirstName(extraName);
					}
				}

				manager.setCountryId(organization.getCountry());

				String postalAddress = organization.getPostaladdress();
				if (!postalAddress.contains("\t")) {
					manager.setAddress(postalAddress);
				} else {
					String[] arrayAddress = postalAddress.split(",\t");
					if (arrayAddress.length > 0) {
						manager.setAddress(arrayAddress[0]);
					}
					if (arrayAddress.length > 2) {
						manager.setState(arrayAddress[2]);
					}
					if (arrayAddress.length > 1) {
						manager.setCity(arrayAddress[1]);
					}
				}

				manager.setCode(organization.getPostalcode());

				String dayPhone = organization.getDayphone();

				if (dayPhone != null) {
					String[] arrayPhone = dayPhone.split("\\)");
					if (arrayPhone.length > 1) {
						manager.setPhoneCountryCode(arrayPhone[0].replace("(", ""));
						manager.setDayPhone(dayPhone.replace(arrayPhone[0] + ")", ""));
					} else {
						manager.setDayPhone(organization.getDayphone());
					}
				}
				manager.setEmail(organization.getEmailaddress());
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOG.debug("ERROR:  FullDataResponse()fillManager(); message:" + e.getMessage());
		}
		this.setManager(manager);
	}

	/**
	 * Fill property manager Billing Policies: Payment Terms, Currency,
	 * Cancellation, Damage coverage options.
	 * 
	 * @param organization
	 * @param managerInfo
	 * @throws Exception
	 */
	private void fillBillingPolicies(SqlSession sqlSession, Organization organization, PropertyManagerInfo managerInfo) throws Exception {

		if (organization != null) {

			BillingPoliciesInfo policiesInfo = new BillingPoliciesInfo();

			if (managerInfo != null && managerInfo.getPropertyManagerId() != null && managerInfo.getNumberOfPayments() != null) {

				if (managerInfo.getNumberOfPayments() == 1) {
					policiesInfo.setNumberOfPayments("1");
				} else if (managerInfo.getNumberOfPayments() == 2) {
					policiesInfo.setNumberOfPayments("2");

					if (managerInfo.getPaymentAmount() == null && managerInfo.getPaymentType() == null
							&& managerInfo.getRemainderPaymentDate() == null) {
						throw new Exception("Payment Terms information isn't correct.");
					}

					policiesInfo.setFirstPaymentAmount(managerInfo.getPaymentAmount().toString());
					policiesInfo.setFirstPaymentType(managerInfo.getPaymentType().toString());

					TIME_INTERVAL remainderPaymentDateType = TIME_INTERVAL.getKey(managerInfo.getRemainderPaymentDate());
					if (remainderPaymentDateType != null) {
						policiesInfo.setSecondPaymentType(String.valueOf(remainderPaymentDateType.getKey()));
					}
				}

			}

			/*
			 * List<String> currencies = RelationService.read(sqlSession,
			 * Relation.ORGANIZATION_CURRENCY, organization.getId(), null); if
			 * (currencies != null && currencies.size() > 0) {
			 * policiesInfo.setCurrency(currencies.get(0)); }
			 * policiesInfo.setCurrency
			 * (String.valueOf(REG_CURRENCY.getCurrencyIndex
			 * (organization.getCurrency()).getKey()));
			 */

			policiesInfo.setCurrency(organization.getCurrency());

			if (managerInfo != null && managerInfo.getPropertyManagerId() != null) {
				Integer cancelType = managerInfo.getCancellationType();
				if (cancelType != null){
					if (cancelType == RegistrationConstants.CANCEL_TO_DAY_OF_TRIP){
						policiesInfo.setCancelationType(cancelType.toString());
					}else if (cancelType == RegistrationConstants.CANCEL_CUSTOM){
						List<PropertyManagerCancellationRule> cancellationRules = sqlSession.getMapper(PropertyManagerCancellationRuleMapper.class).readbypmid(managerInfo.getPropertyManagerId());
						if (cancellationRules != null && cancellationRules.size() > 0) {
							List<CancellationPolice> polices = new ArrayList<CancellationPolice>();							
							policiesInfo.setCancelationType(cancelType.toString());
							
							for (PropertyManagerCancellationRule cancellationRule : cancellationRules) {
								if (cancellationRule != null){
									CancellationPolice police = new CancellationPolice();
									Integer cancelDate = cancellationRule.getCancellationDate();
									if (cancelDate == null){ throw new Exception("Invalid cancellation days count."); }
									/*TIME_INTERVAL cancellationDateType = TIME_INTERVAL.getKey(cancelDate);*/
									police.setCancelationTime(String.valueOf(cancelDate));
									if (cancellationRule.getCancellationRefund() != null) {
										police.setCancelationRefund(cancellationRule.getCancellationRefund().toString());
									}else { throw new Exception("Invalid cancellation refund."); }
									if (cancellationRule.getCancellationTransactionFee() != null) {
										police.setCancelationTransactionFee(cancellationRule.getCancellationTransactionFee().toString());
									}else { throw new Exception("Invalid cancellation transaction fee."); }
									
									polices.add(police);
								}
							}
							policiesInfo.setCancellationPolicies(polices);
						} else { throw new Exception("Cancellltion rules are empty."); };
					}
				}

				if (managerInfo.getDamageCoverageType() != null) {
					policiesInfo.setDamageCoverageType(managerInfo.getDamageCoverageType().toString());

					if (managerInfo.getDamageCoverageType() == 2 && !managerInfo.getDamageInsurance().isEmpty()) {
						policiesInfo.setDamageInsurance(managerInfo.getDamageInsurance());
					}
				}
				
				/* Terms and Conditions */
				if (managerInfo.getCheckInTime() != null && managerInfo.getCheckOutTime() != null){
					policiesInfo.setCheckInTime(managerInfo.getCheckInTime().toString());
					policiesInfo.setCheckOutTime(managerInfo.getCheckOutTime().toString());
					if (managerInfo.getTermsLink() != null){
						policiesInfo.setTermsLink(managerInfo.getTermsLink());
					}
						
				}

				/* Online Payment Options */
				if (managerInfo.getFundsHolder() != null && managerInfo.getPaymentProcessingType() != null) {

					if (managerInfo.getPaymentProcessingType() == PaymentProcessingTypeEnum.GATEWAY.type()) {
						if (managerInfo.getFundsHolder() == FundsHolderEnum.BookingPal.value()) {
							this.setPayment(1); /* BookingPal payment gateway selected to process payments.*/
						} else if (managerInfo.getFundsHolder() == FundsHolderEnum.External.value()) {
							this.setPayment(0); /* Another payment gateway selected (PayPal, Authorize .NET, ...) */
						}
					} else if (managerInfo.getPaymentProcessingType() == PaymentProcessingTypeEnum.Mail.type()) {
						this.setPayment(2); /* None payment option selected (Notifications via email). */
					}
				}
			}

			this.setBillingPolicies(policiesInfo);
		}
	}

	/**
	 * Method used for filling information about PM Credit Card Types.
	 * 
	 * @param sqlSession
	 * @param managerId
	 */
	private void fillCreditCardTypes(SqlSession sqlSession, Integer managerId) {

		this.creditCardTypes = new CreditCardTypes();

		if (sqlSession != null && managerId != null) {

			PropertyManagerSupportCC supportCC = sqlSession.getMapper(PropertyManagerSupportCCMapper.class).readbypartyid(managerId);

			if (supportCC != null) {
				if (supportCC.isNone()) {
					this.creditCardTypes.setNone(true);
				} else {
					this.creditCardTypes.setMasterCard(supportCC.getSupportMC());
					this.creditCardTypes.setVisa(supportCC.getSupportVISA());
					this.creditCardTypes.setAmericanExpress(supportCC.getSupportAE());
					this.creditCardTypes.setDiscover(supportCC.getSupportDISCOVER());
					this.creditCardTypes.setDinersClub(supportCC.getSupportDINERSCLUB());
					this.creditCardTypes.setJbc(supportCC.getSupportJCB());
				}
			}
		}
	}

	// TODO: Util Method.
	private String GetValuebyKey(List<String> values, String key) {
		if (key.isEmpty())
			return "";

		if (values != null && !values.isEmpty()) {

			for (String keyValue : values) {

				String[] array = keyValue.split(":");
				if (array.length == 2)
					if (array[0].equals(key))
						return array[1];
			}
		}

		return "";
	}

}
