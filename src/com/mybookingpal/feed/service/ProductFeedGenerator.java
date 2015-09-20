package com.mybookingpal.feed.service;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.mybookingpal.server.ImageService;

import net.cbtltd.rest.AbstractProduct;
import net.cbtltd.rest.Constants;
import net.cbtltd.rest.Items;
import net.cbtltd.rest.PriceList;
import net.cbtltd.rest.Property;
import net.cbtltd.rest.Rating;
import net.cbtltd.rest.Ratings;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.RateMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.NameId;

public class ProductFeedGenerator extends AbstractProduct{

	private static ProductFeedGenerator productFeedGenerator;

	private static final Boolean  TEST = new Boolean(true);  
	private Collection<Rating> items; 
	private ProductFeedGenerator() {}
	//TODO: Make sure design patern is correct. 
	public static ProductFeedGenerator getInstance() {
		if(productFeedGenerator == null) {
			productFeedGenerator = new ProductFeedGenerator();
		}
		return productFeedGenerator;
	}

	//generate Images VIA API. 
	public Items generateImageGallery(String productid, String pos){
		return getImagesById(productid, pos, TEST, null, null);
	}
	//Chiryu method. 
	public List<NameId> generateProductRegularImageURLs(SqlSession sqlSession, String productid){
		return ImageService.getProductRegularImageURLsAndDescription(sqlSession, productid);
	}
	
	public Property generatPropertySummary(String productid,String pos, SqlSession sqlSession ) {
		return getProperty(productid, pos, Language.EN, TEST ,null, null,sqlSession);
	}
	
	public PriceList  generatePriceList( String productid ,String pos, SqlSession sqlSession){
		return getPriceList(productid, pos, TEST, null, null, sqlSession,true);
	}

	//String productid,
	//String pos,
	//String language,
//	String date,
//	String toDate,
//	String currency,
//	Boolean test,
//	String version,
//	String xsl,
//	boolean sourceAPI
	
	public Property generateDetailInformation(String productid,String pos, SqlSession sqlSession) {
		
		Property property = null;
		String debugMessage = "getPropertyDetail: productid=" + productid + ", pos:" + pos + ";";
		try {
			property = getPropertyDetail(sqlSession, productid, pos, Language.EN, null, null, null, null, TEST, null);
			property.setXsl(Constants.NO_XSL);
		}
		catch(Throwable x){
			sqlSession.rollback();
			if (x != null && x.getMessage() != null && !x.getMessage().startsWith(Error.data_unchanged.name())) {
				LOG.error(debugMessage + "\n" + x.getMessage());
			}
			property = new Property();
			property.setMessage(debugMessage + " " + x.getMessage());
			property.setXsl(Constants.NO_XSL);
		}			
		
		return property;
	}
	
	public void genearteScheduleFromAPI(String productid, String pos){
		
	}
	public Items generateProductValues(String productid, String pos){
		return getValues(productid, pos, null);
	}
	

}
