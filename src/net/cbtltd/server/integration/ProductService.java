package net.cbtltd.server.integration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.cbtltd.server.MonitorService;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.api.CountryCodeMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.PropertyMinStayMapper;
import net.cbtltd.shared.MinStay;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.product.ProductCreate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class ProductService implements IsService {

	private static final Logger LOG = Logger.getLogger(ProductService.class
			.getName());
	private static ProductService service;

	/**
	 * Gets the single instance of ProductService to manage Product instances.
	 * 
	 * @see net.cbtltd.shared.Product
	 * 
	 * @return single instance of ProductService.
	 */
	public static synchronized ProductService getInstance() {
		if (service == null) {
			service = new ProductService();
		}
		return service;
	}

	/**
	 * Executes the ProductCreate action to create a Product instance.
	 * 
	 * @param sqlSession
	 *            the current SQL session.
	 * @param action
	 *            the action to be executed.
	 * @return the response.
	 */
	public final Product execute(SqlSession sqlSession, ProductCreate action) {
		try {
			sqlSession.getMapper(ProductMapper.class).create(action);
			RelationService.create(sqlSession, Relation.ORGANIZATION_PRODUCT,
					action.getOrganizationid(), action.getId());
		} catch (Throwable x) {
			sqlSession.rollback();
			MonitorService.log(x);
		}
		return action;
	}

	/**
	 * To fetch all products available in the DB
	 * 
	 * @param sqlSession
	 *            the current SQL session.
	 * @param action
	 *            the action to be executed.
	 * @return the response.
	 */
	public final List<Product> fetchAllProduct(SqlSession sqlSession,int count,List<String> listSupplierID) {
		List<Product> listProduct = null;
		try {
			NameIdAction nameIdAction=new NameIdAction();
			if(CollectionUtils.isNotEmpty(listSupplierID));{
				nameIdAction.setIds((ArrayList<String>) listSupplierID);
			}
			
			nameIdAction.setOffsetrows(100*count);
			nameIdAction.setNumrows(100);
			LOG.info("Limit "+nameIdAction.getOffsetrows()+" "+nameIdAction.getNumrows());
			listProduct = sqlSession.getMapper(
					ProductMapper.class).readAllSupplier(nameIdAction);
			if(listProduct==null){
				return null;
			}else if(listProduct!=null && listProduct.size()==0){
				return null;
			}

		} catch (Throwable x) {
			sqlSession.rollback();
			MonitorService.log(x);
		}
		return listProduct;
	}
	
	/**
	 * To fetch all products available in the DB
	 * 
	 * @param sqlSession
	 *            the current SQL session.
	 * @param action
	 *            the action to be executed.
	 * @return the response.
	 */
	public final List<Product> fetchAllProduct(SqlSession sqlSession,int count,List<String> listSupplierID,List<String> listProductID) {
		List<Product> listProduct = null;
		try {
			NameIdAction nameIdAction=new NameIdAction();
			if(CollectionUtils.isNotEmpty(listSupplierID));{
				nameIdAction.setIds((ArrayList<String>) listSupplierID);
			}
			if(CollectionUtils.isNotEmpty(listProductID));{
				nameIdAction.setProductids((ArrayList<String>) listProductID);
			}
			
			nameIdAction.setOffsetrows(0);
			nameIdAction.setNumrows(500);
			LOG.info("Limit "+nameIdAction.getOffsetrows()+" "+nameIdAction.getNumrows());
			listProduct = sqlSession.getMapper(
					ProductMapper.class).readAllSupplier(nameIdAction);
			if(listProduct==null || listProduct.size()==0){
				return null;
			}

		} catch (Throwable x) {
			sqlSession.rollback();
			MonitorService.log(x);
		}
		return listProduct;
	}
	
	/**
	 * To fetch all products available in the DB
	 * 
	 * @param sqlSession
	 *            the current SQL session.
	 * @param action
	 *            the action to be executed.
	 * @return the response.
	 */
	/**
	 * @param sqlSession
	 * @param count
	 * @return
	 */
	public final List<Product> fetchAllProduct(SqlSession sqlSession,int count) {
		List<Product> listProduct = null;
		try {
			NameIdAction nameIdAction=new NameIdAction();
			nameIdAction.setOffsetrows(100*count);
			nameIdAction.setNumrows(100);
			LOG.info("Limit "+nameIdAction.getOffsetrows()+" "+nameIdAction.getNumrows());
			listProduct = sqlSession.getMapper(
					ProductMapper.class).readAll(nameIdAction);
			if(listProduct==null){
				return null;
			}else if(listProduct!=null && listProduct.size()==0){
				return null;
			}

		} catch (Throwable x) {
			sqlSession.rollback();
			MonitorService.log(x);
		}
		return listProduct;
	}
	
	/**
	 * to persist product
	 * @param sqlSession
	 * @param id
	 * @return product
	 */
	public Product getProduct( SqlSession sqlSession, String id){
		return sqlSession.getMapper(ProductMapper.class).read(id) ;
	}
	
	/**
	 * to persist product
	 * @param sqlSession
	 * @param altpartyid
	 * @param altid
	 * @return product
	 */
	public Product getProduct( SqlSession sqlSession, String altpartyid,String altid){
		return PartnerService.getProductWithoutInsert(sqlSession,
				new NameId(altpartyid,altid));
	}
	
	public ArrayList<Product> getUnitlist(SqlSession sqlSession, String altpartyid){
			return sqlSession.getMapper(ProductMapper.class).componentlist(
					altpartyid);
	}
	
	public String getCountryName(SqlSession sqlSession, String iso_code2){
		return sqlSession.getMapper(CountryCodeMapper.class).getCountryName(iso_code2);
	}
	/**
	 * to persist product
	 * @param sqlSession
	 * @param product
	 * @return product
	 */
	public Product persistProduct(SqlSession sqlSession, Product product) {
		Product dbProduct=getProduct( sqlSession,product.getAltpartyid(),product.getAltid());
		return persistProduct(sqlSession, dbProduct, dbProduct==null);
		
	}
	/**
	 * to persist product
	 * @param sqlSession
	 * @param product
	 * @param isNew
	 * @return product
	 */
	public Product persistProduct(SqlSession sqlSession, Product product,
			boolean isNew) {
		LOG.info("Check whether Product data exists for data "
				+ product.getName() + " ID " + product.getAltid());
		
		product.setName((product.getName().length() > 100) ? product.getName()
				.substring(0, 99) : product.getName());

		if (isNew) {
			LOG.info("Product with " + product.getName()
					+ " does not exists so creating the product entry");
			

			// after create retrive the product once again so that we
			// can insert its attributes in relation table.
			product = PartnerService.createProduct(sqlSession, product);
			product.setRefresh(0);
			return product;
		} else {
			product.setRefresh(1);
			PartnerService.updateProduct(sqlSession, product);
			return product;
		}
	}
	
	public ArrayList<Product> reloadNullLocationProducts(SqlSession sqlSession,
			String supplierId) {
		return sqlSession.getMapper(ProductMapper.class).productIdListWithNullLocationBySupplierId(supplierId);
	}
	
	public ArrayList<Product> getProductsForChannel(SqlSession sqlSession, String altpartyid){
		return sqlSession.getMapper(ProductMapper.class).getProductsForChannel(
				altpartyid);
	}
	
	public ArrayList<String> activeProductIdListBySupplier(SqlSession sqlSession, String supplierid){
		NameIdAction nameid=new NameIdAction();
		nameid.setId(supplierid);
		return sqlSession.getMapper(ProductMapper.class).activeProductIdListBySupplier(
				nameid);
	}
	
	public final Product fetchProduct(SqlSession sqlSession,String id) {
		return getProduct(sqlSession,id);
	}
	
	public void persistRooms(SqlSession sqlSession, List<NameId> roomIds) {
		List<String> dbRoomIds = getRoomsForProduct(sqlSession, roomIds.get(0).getId());
		List<NameId> idsToPersist = new ArrayList<NameId>();
		List<String> xmlRoomIds = new ArrayList<String>();
		for(NameId nameId : roomIds){
			xmlRoomIds.add(nameId.getName());
			if(!dbRoomIds.contains(nameId.getName())){
				idsToPersist.add(nameId);
			}
		}
		dbRoomIds.removeAll(xmlRoomIds);
		if(!dbRoomIds.isEmpty()){
			for(NameId nameId : roomIds){
				if(dbRoomIds.contains(nameId.getName())){
					sqlSession.getMapper(ProductMapper.class).deleteRoom(nameId);
				}
			}
		}
		if(!idsToPersist.isEmpty())
			sqlSession.getMapper(ProductMapper.class).insertRoomIds(roomIds);
	}

	public List<String> getRoomsForProduct(SqlSession sqlSession, String id) {
		return sqlSession.getMapper(ProductMapper.class).selectRoomsForProduct(id);
	}

	/**
	 * Creates the new minstay items.
	 *
	 * @param sqlSession the current SQL session.
	 * @param value the minstay day value
	 * @param product the property being scheduled
	 * @param fromdate the date from which minstay value is valid
	 * @param todate the date to which it is valid
	 * @param version the latest version time stamp
	 */
	public void createMinStay(SqlSession sqlSession, Integer value, Product product, Date fromdate, Date todate, Date version) {
		MinStay minstay = new MinStay();
		minstay.setProductid(product.getId());
		minstay.setFromdate(fromdate);
		minstay.setTodate(todate);
		MinStay exists = sqlSession.getMapper(PropertyMinStayMapper.class).exists(minstay); 
		if (exists == null) {sqlSession.getMapper(PropertyMinStayMapper.class).create(minstay);}
		else {minstay = exists;}
		minstay.setValue(value);
		minstay.setSupplierid(product.getAltpartyid());
		minstay.setVersion(version);
		sqlSession.getMapper(PropertyMinStayMapper.class).update(minstay);
		LOG.debug("createMinStay " + minstay.getProductid() + " " + minstay.getVersion() + " " + minstay.getFromdate() + " " + minstay.getTodate());
	}

	public String getPhoneCode(SqlSession sqlSession, String countryName){
		return sqlSession.getMapper(CountryCodeMapper.class).getPhoneCode(countryName);
	}
}
