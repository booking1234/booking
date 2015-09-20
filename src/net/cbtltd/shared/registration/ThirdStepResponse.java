package net.cbtltd.shared.registration;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.cbtltd.rest.error.ApiResponse;
import net.cbtltd.rest.registration.AdditionalParams;
import net.cbtltd.rest.registration.ProductInfo;
import net.cbtltd.rest.registration.ProductInfoList;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
public class ThirdStepResponse extends StepResponse implements RegistrationResponse {

	private static final Logger LOG = Logger.getLogger(ThirdStepResponse.class.getName());
	
	@XmlElement(name = "step_data")
	private AdditionalParams stepData;
	@XmlElement(name = "products")
	private ProductInfoList productInfo;

	@XmlTransient
	private Gson GSON = new Gson();
	@XmlTransient
	private RegistrationHelper regHelper;

	public ThirdStepResponse() {
		super();
		setStepData(new AdditionalParams(3, "", ""));
	}

	public ThirdStepResponse(String request) {
		super();
		ThirdStepRequest requestObject = null;
		regHelper = new RegistrationHelper(LOG);
		SqlSession sqlSession = RazorServer.openSession();
		try {

			requestObject = GSON.fromJson(request, ThirdStepRequest.class);

			if (requestObject != null) {
				setManagerId(requestObject.getPos());

				execute(sqlSession, requestObject);
				init(sqlSession, requestObject);
				sqlSession.commit();

			} else {
				throw new ServiceException(Error.step_request_json);
			}

		} catch (Exception e) {
			sqlSession.rollback();
			setStatus(new ApiResponse(e.getMessage()));
			setStepData(new AdditionalParams(requestObject.getStep(), "", ""));
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * Update data for pm regarding to step number and input request.
	 * 
	 * @param pmId
	 * @param request
	 * @throws Exception 
	 */
	private void execute(SqlSession sqlSession, ThirdStepRequest requestObject) throws Exception {

		Product product = null;
		ProductInfoList productInfoList = requestObject.getProducts();
		
		if (productInfoList != null){
			List<ProductInfo> products = productInfoList.getList();
			if (products != null) {
				for (ProductInfo productInfo : products) {
					if (productInfo != null){
						product = sqlSession.getMapper(ProductMapper.class).read(productInfo.getId());
						if (product != null){
							Boolean isChecked = productInfo.getSelected();
							String securityDeposit = productInfo.getSecurityDeposit();
							String cleaningFee = productInfo.getCleaningFee();
							String commission = productInfo.getCommission();
							
							if (isChecked == null) { throw new Exception("Invalid assigned to manager value."); }
							
							if (isChecked){
								if (StringUtils.isEmpty(securityDeposit) ) { throw new Exception("Invalid Security Deposit."); }
								if (StringUtils.isEmpty(cleaningFee)) { throw new Exception("Invalid Cleaning Fee."); }
								if (StringUtils.isEmpty(commission)) { throw new Exception("Invalid Commision value."); }
							}
							product.setDiscount(StringUtils.isEmpty(commission) ? null : Double.valueOf(commission));						
							product.setSecuritydeposit(securityDeposit == null ? null : Double.valueOf(securityDeposit));
							product.setCleaningfee(cleaningFee == null ? null : Double.valueOf(cleaningFee));

							product.setAssignedtomanager(isChecked);
							if (isChecked) { product.setState(Product.CREATED); }
							else { product.setState(Product.SUSPENDED); }
							/*product.setSupplierid(pos);*/
							sqlSession.getMapper(ProductMapper.class).update(product);
						}
					}
				}
				sqlSession.commit();
			}			
		}
	}

	/**
	 * Initializing response data.
	 * 
	 * @param request
	 * @throws Exception 
	 */
	private void init(SqlSession sqlSession, ThirdStepRequest requestObject) throws Exception {

		ProductInfoList productInfoList = requestObject.getProducts();
		Integer nextStep = requestObject.getNextStep();
		
		if (productInfoList != null){
			
			Integer pageInt = RegistrationUtils.getInteger(productInfoList.getPage());
			Integer perPageInt = RegistrationUtils.getInteger(productInfoList.getPerPage());
			
			PropertyManagerInfo managerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(this.getPmId());
			if (managerInfo != null) {
				if (pageInt == null && perPageInt == null) {
					if (managerInfo.getPmsId() == null){
						throw new Exception("Property management system not found.  You need to set partner for your account.");	
					}
					Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(managerInfo.getPmsId().toString());					
					Integer selectedProducts = regHelper.getSelectedProductsCount(sqlSession, this.getPmId().toString());
					if (selectedProducts == 0 ) {
						if (partner != null && partner.getRegisteremptyproperties() != null){
								if (!partner.getRegisteremptyproperties()){
									throw new Exception("You need to choose at least one product.");
								}else{
									managerInfo.setRegistrationStepId(requestObject.getNextStep());
								}
						}else{
							throw new Exception("You need to choose at least one product.");
						}						
					}else {
						managerInfo.setRegistrationStepId(requestObject.getNextStep());
					}
					
				}else if(pageInt != null && perPageInt != null){
					ProductInfoList productInfo = new ProductInfoList();
					
					productInfo.setPage(pageInt.toString());
					productInfo.setPerPage(perPageInt.toString());
					Integer offset = (pageInt - 1) * perPageInt;
					
					NameIdAction readAction = new NameIdAction();
					readAction.setId(String.valueOf(this.getPmId()));
					readAction.setNumrows(perPageInt);
					readAction.setOffsetrows(offset);
									
					productInfo.setList(regHelper.getProductsList(sqlSession, readAction, managerInfo));
					productInfo.setTotalCount(regHelper.getProductsCount(sqlSession, this.getPmId().toString(), false, null, null).toString());
					
					this.setProductInfo(productInfo);
					
					nextStep = requestObject.getStep();
					managerInfo.setRegistrationStepId(requestObject.getStep());
				}
				sqlSession.getMapper(PropertyManagerInfoMapper.class).updatebypmid(managerInfo);
			}
		}
		
		setStepData(new AdditionalParams(nextStep, "", ""));
		setStatus(new ApiResponse());
	}

	public AdditionalParams getStepData() {
		return stepData;
	}

	public void setStepData(AdditionalParams stepData) {
		this.stepData = stepData;
	}

	public ProductInfoList getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfoList products) {
		this.productInfo = products;
	}

	@Override
	public RegistrationResponse getResponse() {
		return this;
	}

}
