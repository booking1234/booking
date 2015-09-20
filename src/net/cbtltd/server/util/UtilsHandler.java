package net.cbtltd.server.util;

import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ImageMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.Image;
import net.cbtltd.shared.NameId;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.mybookingpal.server.ImageService;

public class UtilsHandler {
	
	private static final Logger LOG = Logger.getLogger(UtilsHandler.class.getName());
	
	public static void cleanImageTable(String partyID) {
		LOG.debug("Cleanin bad images for party id " + partyID);

		SqlSession sqlSession = RazorServer.openSession();
		try {
			//get list of productIDs (don't need whole product list).
			ArrayList<String> products = sqlSession.getMapper(ProductMapper.class).flipkeyproducts(partyID);
			//get list of images for selected productID
			for (String id : products) {
				LOG.debug("Cleanin images for product id " + id + ":");
				
				NameId action = new NameId("",id);
				ArrayList<Image> images = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(action);
				int i = 1;
				for (Image image : images) {
					boolean isImage = true;
					String imageLocation = ImageService.getProductImageLocation(sqlSession, id);
					java.net.URL url = new java.net.URL(imageLocation + image.getName());
					try {
						ImageIO.read(url);
					} catch (IOException e) {
						//Not an Image
						isImage = false;
					}
					if (!isImage) {
						//delete bad image entry
						sqlSession.getMapper(ImageMapper.class).deletebyexample(image);
						LOG.info(i++ + "Image " + image.getId() + " (" + url.toString() + ") has been deleted.");
					} else LOG.debug(i++ + "Image " + image.getId() + " (" + url.toString() + ") - OK");
				}
			}
			LOG.debug("Cleaning was done.");
		} catch (Throwable x) {
			x.printStackTrace();
			LOG.error("Cleaning was interrupted, error: " + x.getMessage());
		}
		finally {
			sqlSession.commit();
			sqlSession.close();
		}
		
	}
	
}
