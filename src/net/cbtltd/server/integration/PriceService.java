package net.cbtltd.server.integration;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.shared.DoubleResponse;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.price.PriceCreate;
import net.cbtltd.shared.price.PriceDelete;
import net.cbtltd.shared.price.PriceRead;
import net.cbtltd.shared.price.PriceTable;
import net.cbtltd.shared.price.PriceTableConverted;
import net.cbtltd.shared.price.PriceUpdate;
import net.cbtltd.shared.price.PriceValue;
import net.cbtltd.shared.price.ProductFeatureTable;

import org.apache.commons.lang.time.DateUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** The Class PriceService responds to price requests. */
public class PriceService
implements IsService {

	private static final Logger LOG = Logger.getLogger(PriceService.class.getName());
	private static PriceService service;

	/**
	 * Gets the single instance of PriceService to manage Price and Yield management instances and tables.
	 *
	 * @return single instance of PriceService.
	 * @see net.cbtltd.shared.Price
	 * @see net.cbtltd.shared.Yield
	 */
	public static synchronized PriceService getInstance() {
		if (service == null) {service = new PriceService();}
		return service;
	}

	/**
	 * Executes the PriceCreate action to create a Price instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Price execute(SqlSession sqlSession, PriceCreate action) {
		return net.cbtltd.server.PriceService.getInstance().execute(sqlSession, action);
		}

	/**
	 * Executes the PriceRead action to read a Price instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Price execute(SqlSession sqlSession, PriceRead action) {
		return net.cbtltd.server.PriceService.getInstance().execute(sqlSession, action);
	}

	/**
	 * Executes the PriceUpdate action to update a Price instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Price execute(SqlSession sqlSession, PriceUpdate action) {
		return net.cbtltd.server.PriceService.getInstance().execute(sqlSession, action);
	}

	/**
	 * Executes the PriceDelete action to delete a Price instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Price execute(SqlSession sqlSession, PriceDelete action) {
		return net.cbtltd.server.PriceService.getInstance().execute(sqlSession, action);
	}

	/**
	 * Executes the PriceValue action to calculate a Price value.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the double response
	 */
	public final DoubleResponse execute(SqlSession sqlSession, PriceValue action) {
		return net.cbtltd.server.PriceService.getInstance().execute(sqlSession, action);
	}

	/**
	 * Executes the PriceTable action to read a Price table.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Price> execute(SqlSession sqlSession, PriceTable action) {
		return net.cbtltd.server.PriceService.getInstance().execute(sqlSession, action);
	}

	/**
	 * Executes the PriceTableConverted action to read a Price table converted to a target currency.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Price> execute(SqlSession sqlSession, PriceTableConverted action) {
		return net.cbtltd.server.PriceService.getInstance().execute(sqlSession, action);
	}

	/**
	 * Executes the ProductFeatureTable action to read a feature table of a property.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Price> execute(SqlSession sqlSession, ProductFeatureTable action) {
		return net.cbtltd.server.PriceService.getInstance().execute(sqlSession, action);
	}

	
	/**
	 * Executes the PriceNameId action to read a list of price NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdAction action) {
		
		return net.cbtltd.server.PriceService.getInstance().execute(sqlSession, action);
	}

	/**
	 * To remove the price record
	 * @param sqlSession
	 * @param id
	 */
	public void remove(SqlSession sqlSession, String id) {
		sqlSession.getMapper(PriceMapper.class).delete(id);		
	}
	
	/**
	 * to persist price data
	 * @param sqlSession
	 * @param product
	 * @param listPrice
	 */
	public void persistPriceData(SqlSession sqlSession, Product product,
			List<PriceCreate> listPrice) {
		LOG.info("Updating the price date of Product " + product.getName());
		// delete the existing price data and update the new booking date
		
		PriceService.getInstance().remove(sqlSession, product.getId());
		// update the booked date of product
		/*for (PriceCreate price : listPrice) {
			price.setEntityid(product.getId());
			price.setPartyid(product.getAltpartyid());
			price.setEntitytype(NameId.Type.Product.name());
			PriceService.getInstance().execute(sqlSession, price);
		}*/
		for (int i = 0; i<listPrice.size(); i++) {
			listPrice.get(i).setEntityid(product.getId());
			listPrice.get(i).setPartyid(product.getAltpartyid());
			listPrice.get(i).setEntitytype(NameId.Type.Product.name());
			PriceService.getInstance().execute(sqlSession, listPrice.get(i));
		}

	}
	
	private void removeByEntity(SqlSession sqlSession, String id) {
		sqlSession.getMapper(PriceMapper.class).deleteByEntityId(id);
		
	}

	/**
	 * To remove the price record
	 * @param sqlSession
	 * @param id
	 */
	public ArrayList<Price> get(SqlSession sqlSession, Price price) {
		return sqlSession.getMapper(PriceMapper.class).existsPrice(price);		
	}
	
	public ArrayList<Price> get(SqlSession sqlSession, String entityType,String entityId,String partyId,String altId) {
		Price price=new Price();
		price.setEntitytype(entityType);
		price.setEntityid(entityId);
		price.setPartyid(partyId);
		price.setAltid(altId);
		return get(sqlSession,price);		
	}
	
	/**
	 * to persist price data
	 * @param sqlSession
	 * @param product
	 * @param listPrice
	 */
	public void persistPriceData(SqlSession sqlSession, List<Price> listPrice) {

		if(listPrice==null||listPrice.isEmpty()) return;
		List<Price> existPrice=get(sqlSession,listPrice.get(0));
		for (Price price : listPrice) {
			Price dbPrice=find(existPrice,price);
			if( dbPrice==null ){
				sqlSession.getMapper(PriceMapper.class).create(price);
			}else{
				price.setId(dbPrice.getId());
				sqlSession.getMapper(PriceMapper.class).update(price);
				existPrice.remove(dbPrice);
			}
		}
		for(Price price:existPrice){
			sqlSession.getMapper(PriceMapper.class).delete(price.getId());
		}
	}
	
	/**
	 * to persist price data
	 * @param sqlSession
	 * @param product
	 * @param listPrice
	 */
	public void persistPriceData(SqlSession sqlSession,List<Price> existPrice, 
			List<Price> listPrice) {
		if(listPrice==null||listPrice.isEmpty()) return;
		Price dbPrice=null;
		// update the booked date of product
		for (Price price : listPrice) {
			try{
			dbPrice=find(existPrice,price);
			if( dbPrice==null ){
				sqlSession.getMapper(PriceMapper.class).create(price);
			}else{
				dbPrice.setValue(price.getValue());
				dbPrice.setMinimum(price.getMinimum());
				dbPrice.setCost(price.getCost());
				sqlSession.getMapper(PriceMapper.class).update(dbPrice);
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public Price find(List<Price> existPrice, Date date1, Date date2){
		if(existPrice==null||existPrice.isEmpty()) return null;
		for(Price price:existPrice){
			if(DateUtils.isSameDay(price.getDate(), date1) && DateUtils.isSameDay(price.getTodate(), date2)){
			  return price;	
			}
		}
		return null;
	}


	// persist extra cost
	public void persistPriceData(SqlSession sqlSession,
			net.cbtltd.shared.Price price) {
		if(price == null) return;
		Price existPrice = getExtraCost(sqlSession,price);
		if( existPrice == null ){
			sqlSession.getMapper(PriceMapper.class).create(price);
		}else{
			price.setId(existPrice.getId());
			sqlSession.getMapper(PriceMapper.class).update(price);
		}
	}
	public Price getExtraCost(SqlSession sqlSession, Price price) {
		return sqlSession.getMapper(PriceMapper.class).getExtraCost(price);		
	}

	
	public Price find(List<Price> existPrice, Price price){
		if(existPrice==null||existPrice.isEmpty()) return null;
		for(Price p1:existPrice){
			if(isSameDate(price.getDate(), p1.getDate()) && isSameDate(price.getTodate(), p1.getTodate())&& isSameValue(price.getUnit(),p1.getUnit())
					&&isSameValue(price.getEntitytype(),p1.getEntitytype()) && isSameValue(price.getName(), p1.getName()) ){
			  return p1;	
			}
		}
		return null;
	}
	private  boolean isSameValue(String v1, String v2) {
        if(v1==null && v2==null){
            return true;
        }
        if(v1==null || v2==null){
            return false;
        }
        return v1.equalsIgnoreCase(v2);
	}
	private  boolean isSameDate(Date date1, Date date2) {
        if(date1==null && date2==null){
            return true;
        }
        if(date1==null || date2==null){
            return false;
        }
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date1).equals(sdf.format(date2));
	}
	

}

