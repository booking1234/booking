package com.mybookingpal.server.api.junit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ImageMapper;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.Image;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Product;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.mybookingpal.service.StorageService;
import com.mybookingpal.service.StorageServiceFactory;
import com.mybookingpal.storage.data.ImageMetaData;
import com.mybookingpal.storage.exception.StorageException;

public class MoveNewImagesInS3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SqlSession sqlSession = RazorServer.openSession();
		int batch = 2000;
//		int offset = 5000;
//		int offset = 600000;
//		int offset = 1077200;
//		int offset = 1596330;
		int offset = 2000000;
		int totalRecords = 5394660 - offset;
//		int totalRecords = 6000 - offset;

		List<String> partners = Arrays.asList(new String[] {"99039","100296","120523","10942","90640","100293","99058","133832"
						,"99060","99062","99064","189790","325009","210270","210254","210252","210287","210286","231016","231044","231047"
						,"231048","231049","231050","231051","231052","231053","231057"});
		
		int productCount = 0;
		while(totalRecords > 0) {
			System.out.println("Loading images records: " + offset + " to " + (offset + batch));
			RowBounds criteria = new RowBounds(offset, batch);
			
//			List<Image> images = sqlSession.getMapper(ImageMapper.class).readnewproducts(criteria);
			
			List<Image> images = sqlSession.selectList(ImageMapper.class.getName() + ".readnewproducts", criteria);
			
			
			Map<String, List<Image>> productImages = new HashMap<String, List<Image>>();
			
			
			for(Image image:images) {
				List<Image> productImageList = productImages.get(String.valueOf(image.getProductId()));
				if(productImageList == null) {
					productImageList = new ArrayList<Image>();
					productImageList.add(image);
					productImages.put(String.valueOf(image.getProductId()), productImageList);
				} else {
					productImageList.add(image);
				}
					
			}
			
			System.out.println("Start moving images...");
			StorageService storageService = StorageServiceFactory.getInstance();
			
			for (Map.Entry<String, List<Image>> entry : productImages.entrySet()) {
				Product product = sqlSession.getMapper(ProductMapper.class).read(entry.getKey());
				if(product != null) {
					if(partners.contains(product.getSupplierid())) {
						Partner supplierPartner = sqlSession.getMapper(PartnerMapper.class).exists(product.getSupplierid());
						String destinationPm = supplierPartner.getAbbrevation().toLowerCase();
						String destinationPms = null;
						if(supplierPartner == null || supplierPartner.getPartyid().equals(supplierPartner.getOrganizationid())) {
							destinationPms = destinationPm;
						} else {
							Partner organizationPartner = sqlSession.getMapper(PartnerMapper.class).exists(supplierPartner.getOrganizationid());
							destinationPms = organizationPartner.getAbbrevation().toLowerCase();
						}
						
						List<ImageMetaData> imagesToBeMoved = new ArrayList<ImageMetaData>();
						System.out.println("Moving images for product: " + entry.getKey() + " total images: " + entry.getValue().size());
						for(Image image:entry.getValue()) {
							imagesToBeMoved.add(new ImageMetaData(image.getName(), image.getOldName()));
							String oldName = image.getOldName();
							String newName = image.getName();
							
							if(image.isThumbnail()) {
								oldName = (oldName.indexOf('.') > 0 ? oldName.substring(0, oldName.indexOf('.')) : oldName) + ImageMetaData.Size.Thumb + "." + (oldName.indexOf('.') > 0 ? oldName.substring(oldName.indexOf('.') + 1) : "");
								newName = (newName.indexOf('.') > 0 ? newName.substring(0, newName.indexOf('.')) : newName) + ImageMetaData.Size.Thumb + "." + (newName.indexOf('.') > 0 ? newName.substring(newName.indexOf('.') + 1) : "");
								imagesToBeMoved.add(new ImageMetaData(newName, oldName));
							}
							if(image.isStandard()) {
								oldName = (oldName.indexOf('.') > 0 ? oldName.substring(0, oldName.indexOf('.')) : oldName) + ImageMetaData.Size.Standard + "." + (oldName.indexOf('.') > 0 ? oldName.substring(oldName.indexOf('.') + 1) : "");
								newName = (newName.indexOf('.') > 0 ? newName.substring(0, newName.indexOf('.')) : newName) + ImageMetaData.Size.Standard + "." + (newName.indexOf('.') > 0 ? newName.substring(newName.indexOf('.') + 1) : "");
								imagesToBeMoved.add(new ImageMetaData(newName, oldName));
							}
						}
						try {
							storageService.copyImages(product.getAltpartyid(), product.getAltSupplierId(), product.getAltid(), destinationPms, destinationPm, product.getAltid(), imagesToBeMoved);
							productCount++;
						} catch (StorageException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						System.out.println("Product is not available for id: " + entry.getKey());
					}
				}
				
			}
			
			
			
			totalRecords = totalRecords - batch;
			offset = offset + batch;
		}
		
		
		
		
		
		
		
		
		sqlSession.commit();
		System.out.println("Images moved for total products: " + productCount);
		
		
		
	}
	
	
	
	

}
