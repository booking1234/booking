package net.cbtltd.shared.registration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.cbtltd.rest.error.ApiResponse;
import net.cbtltd.rest.registration.AdditionalParams;
import net.cbtltd.rest.registration.ProductInfoList;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.PropertyManagerInfo;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
public class SecondStepResponse extends StepResponse implements RegistrationResponse {

	private static final Logger LOG = Logger.getLogger(SecondStepResponse.class.getName());

	@XmlElement(name = "step_data")
	private AdditionalParams stepData;
	@XmlElement(name = "products")
	private ProductInfoList products;
	
	@XmlTransient
	private RegistrationHelper regHelper;

	public SecondStepResponse() {
		super();
		setStepData(new AdditionalParams(2, "", ""));
		regHelper = new RegistrationHelper(LOG);
	}

	public SecondStepResponse(String request) {

		LOG.debug("SecondStepResponse request:" + request + ";");

		regHelper = new RegistrationHelper(LOG);
		Gson GSON = new Gson();
		SecondStepRequest requestObject = GSON.fromJson(request, SecondStepRequest.class);

		LOG.debug("Create object from request String: OK");

		SqlSession sqlSession = RazorServer.openSession();

		try {

			if (requestObject != null) {
				setManagerId(requestObject.getPos());
				LOG.debug("Manager ID:" + this.getPmId());
				execute(sqlSession,  requestObject);
				init(sqlSession, requestObject);
				sqlSession.commit();
				LOG.debug("SQlSEssion commit:OK");
			}
			else {
				throw new ServiceException(Error.step_request_json);
			}
		} catch (Exception e) {
			sqlSession.rollback();
			setStatus(new ApiResponse(e.getMessage()));
			setStepData(new AdditionalParams(requestObject.getStep(), requestObject.getPos(), ""));
		} finally {
			sqlSession.close();
		}
		LOG.debug("Response:" + this.toString() + ";");
	}

	/**
	 * Update data for pm regarding to step number and input request.
	 * @param pmId
	 * @param request
	 */
	private void execute(SqlSession sqlSession, SecondStepRequest requestObject) {
		regHelper.updateChannelPartnersList(sqlSession, requestObject.getChannel_partners_list_ids(), this.getPmId());			
	}

	/**
	 * Initializing response data.
	 * @param request
	 */
	private void init(SqlSession sqlSession, SecondStepRequest requestObject) throws Exception {
		
		PropertyManagerInfo managerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(this.getPmId());
		if (managerInfo == null) {
			throw new ServiceException(Error.organization_info);
		}
		
		ProductInfoList productList = new ProductInfoList();
		productList.setPage("1");
		productList.setPerPage(StepResponse.DEFAULT_PRODUCTS_COUNT_PER_PAGE);
		
		NameIdAction readAction = new NameIdAction();
		readAction.setId(String.valueOf(this.getPmId()));
		readAction.setNumrows(Integer.valueOf(StepResponse.DEFAULT_PRODUCTS_COUNT_PER_PAGE));
		
		productList.setList(regHelper.getProductsList(sqlSession, readAction, managerInfo));
		productList.setTotalCount(regHelper.getProductsCount(sqlSession, this.getPmId().toString(), false, null, null).toString());
		
		this.setProducts(productList);
		
		managerInfo.setRegistrationStepId(requestObject.getNextStep());
		sqlSession.getMapper(PropertyManagerInfoMapper.class).updatebypmid(managerInfo);

		LOG.debug("Updating Property Manager Info:OK;");

		setStepData(new AdditionalParams(requestObject.getNextStep(), "", ""));
		setStatus(new ApiResponse());
		
		LOG.debug("Init response with data:OK");		
	}

	@Override
	public RegistrationResponse getResponse() {
		return this;
	}
	
	public ProductInfoList getProducts() {
		return products;
	}

	public void setProducts(ProductInfoList products) {
		this.products = products;
	}

	public AdditionalParams getStepData() {
		return stepData;
	}

	public void setStepData(AdditionalParams stepData) {
		this.stepData = stepData;
	}
}
