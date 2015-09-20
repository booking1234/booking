package net.cbtltd.rest.bookingcom.upload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.integration.ImageService;
import net.cbtltd.server.integration.ProductService;
import net.cbtltd.shared.Image;
import net.cbtltd.shared.Product;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/**
 * Utility to upload image 
 * @author nibodha
 *
 */
public class UploadImageUtils {
	private static final Logger LOG = Logger.getLogger(UploadImageUtils.class
			.getName());

	/**
	 * To upload image data for the list of supplier identified by supplier_id
	 * @param listSuppliedID
	 */
	public void uploadImageData(List<String> listSuppliedID) {
		LOG.info("Starting uploadImageData");
		

		SqlSession sqlSession = RazorServer.openSession();
		List<Product> listProduct = new ArrayList<Product>();
		int cnt = 0;
		while (listProduct != null) {
			List<ImageUploadTemplate> listImageUploadData = new ArrayList<ImageUploadTemplate>();
			listProduct = this.fetchProductDetails(sqlSession, cnt,listSuppliedID);
			if (listProduct != null) {
				for (Product bpProduct : listProduct) {
					try {

						listImageUploadData.addAll(this.fetchImageData(
								sqlSession, bpProduct.getId()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						LOG.error(e.getMessage(), e);
					}
				}
			}
			
			try {
				System.out.println("listImageUploadData "+listImageUploadData.size()+" cnt "+cnt);
				CSVUtils.exportImageDataToCSV(listImageUploadData,cnt);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cnt++;
		}
		
		LOG.info("Exiting uploadImageData");

	}

	/**
	 * generate and return list of image upload template for the given product id
	 * @param sqlSession
	 * @param productID
	 * @return list of image upload template
	 */
	private List<ImageUploadTemplate> fetchImageData(SqlSession sqlSession,
			String productID) {
		List<ImageUploadTemplate> listImageUploadTemplate = new ArrayList<ImageUploadTemplate>();
		List<Image> listImage = ImageService
				.getProductRegularImage(sqlSession, productID);
		int cnt = 1;
		for (Image image : listImage) {
			ImageUploadTemplate imageData = new ImageUploadTemplate();
			// imageData.setBookingCode(bookingCode)//To be filled later
			imageData.setSequenceNumber(cnt + "");
			imageData.setPropertyCode(productID);
			imageData.setTag(image.getName());
			String height = "480";
			if (image.getUrl() != null && image.getUrl().contains("thumb")) {
				height = "90";
			} else if (image.getUrl() != null
					&& image.getUrl().contains("large")) {
				height = "240";
			}
			imageData.setHeight(height);
			imageData.setUrl(image.getUrl());
			listImageUploadTemplate.add(imageData);

			cnt++;
		}
		return listImageUploadTemplate;
	}

	/**
	 * To get list of list of products mapped to given set of suppliers identified by supplier_id
	 * @param sqlSession
	 * @param count
	 * @param listSuppliedID
	 * @return list of product
	 */
	private List<Product> fetchProductDetails(SqlSession sqlSession, int count,List<String> listSuppliedID) {
		return ProductService.getInstance().fetchAllProduct(sqlSession, count,listSuppliedID);
	}
}
