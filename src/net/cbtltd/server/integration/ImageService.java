package net.cbtltd.server.integration;

import java.util.List;

import net.cbtltd.server.api.ImageMapper;
import net.cbtltd.shared.Image;
import net.cbtltd.shared.NameId;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;



public class ImageService {
	private static final Logger LOG = Logger.getLogger(ImageService.class.getName());
	private static final String RAZOR_PMS  = "raz";
	
	/**
	 * Fetch the image data from the DB for a property
	 * 
	 */
	public static List<NameId> getProductRegularImageURLsAndDescription(SqlSession sqlSession, String productid) {
		return net.cbtltd.server.ImageService.getProductRegularImageURLsAndDescription(sqlSession, productid);
	}
	
	/**
	 * Fetch the image data from the DB for a property
	 * 
	 */
	public static List<Image> getProductRegularImage(SqlSession sqlSession, String productid) {
	
		List<Image> listImages = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(new NameId(productid));

		
		return listImages;
	}
	
	/**
	 * Persist the image data to the DB for a property
	 * 
	 */
	public static void persistImage(String name, int productId, String language, String notes, Image.Type type, String url, boolean standard, boolean thumbnail, SqlSession sqlSession) {
		net.cbtltd.server.ImageService.persistImage(name, productId, language, notes, type, url, standard, thumbnail, sqlSession);
	}
	
}
