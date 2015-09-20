package com.mybookingpal.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ImageMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Image;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Product;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.mybookingpal.config.RazorConfig;
import com.mybookingpal.service.StorageService;
import com.mybookingpal.service.StorageServiceFactory;
import com.mybookingpal.storage.data.ImageMetaData;
import com.mybookingpal.storage.exception.StorageException;
import com.mybookingpal.utils.ImageDownloadTask;
import com.mybookingpal.utils.ImageScalingTask;

public class ImageService implements IsService {
	
	private static final Logger LOG = Logger.getLogger(ImageService.class.getName());
	
	private static final String RAZOR_PMS  = "raz";
	
	public static void uploadImages(SqlSession sqlSession, String productId, String name, String note, byte[] data, String format) {
		if(name.indexOf('/') > 0) {
			name = name.substring(name.indexOf('/') + 1);
//			if(name.indexOf('.') < 0) name = name + "." + format;
		}
		ImageMetaData imageMetaData = new ImageMetaData();
		imageMetaData.setName(name);
		imageMetaData.setFormat(format);
		imageMetaData.setData(data);
		imageMetaData.setNote(note);
		
		List<ImageMetaData> propertyImages = new ArrayList<ImageMetaData>();
		propertyImages.add(imageMetaData);
		
		try {
			ImageService.uploadImages(sqlSession, NameId.Type.Product, productId, Language.EN, propertyImages);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Upload raw images to the storage
	 *
	 * @param sqlSession the current SQL session.
	 * @param entitytype the entity type to be used for the image upload.
	 * @param entityid the entity ID to be used for the image upload.
	 * @param language the language of the uploaded text.
	 * @param propertyImages the list of image meta data. The format needs to be jpeg, jpg, png etc.
	 */
	public static void uploadImages(SqlSession sqlSession, NameId.Type entitytype, String entityid, String language, List<ImageMetaData> propertyImages) throws Throwable {
		
		if (!Language.EN.equalsIgnoreCase(language) && !Language.isTranslatable(language)) {throw new ServiceException(Error.image_upload, "because the language code " + language + " cannot be translated");}
		
		Product product = sqlSession.getMapper(ProductMapper.class).read(entityid);
		if(product == null) {
			throw new ServiceException(Error.database_cannot_find, "product [" + entityid + "]");
		}
		
		// multi threading image download and text creation
		// number of thread can be optimized by finding out available resources
		ExecutorService executor = Executors.newFixedThreadPool(25);

		String partner = "";
		
		Map<FutureTask<List<ImageMetaData>>, Image> futures = new HashMap<FutureTask<List<ImageMetaData>>, Image>();
		boolean compressImage = false;
		if(RazorConfig.getEnvironmentId().equals(RazorConfig.PROD_ENVIRONMENT) 
				|| RazorConfig.getEnvironmentId().equals(RazorConfig.UAT_ENVIRONMENT)) {
			compressImage = true;
		}
		
		List<Image> images = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(product);
		
		// download each image in it's own thread
		for (ImageMetaData imageData : propertyImages) {
			try {
								
				Image image = new Image();
				image.setName(imageData.getName());
				image.setLanguage(language);
				image.setProductId(Integer.parseInt(entityid));
//				image.setState(Image.State.Created.name());
				image.setNotes(imageData.getNote());
				image.setType(Image.Type.Hosted);
								
//				if (sqlSession.getMapper(ImageMapper.class).exists(image) == null) {
				if (!images.contains(image)) {
					FutureTask<List<ImageMetaData>> task = new FutureTask<List<ImageMetaData>>(new ImageScalingTask(imageData, compressImage));
					futures.put(task, image);
					executor.execute(task);
				} else {
					LOG.error("Image exist for product id: " + entityid  + " url: " + imageData.getName());
				}
			} 
			catch (Throwable x) {
				LOG.error("Bad or Absent Image URL: " + imageData.getName());
				x.printStackTrace();
			}
		}
		
		int totalImagesUploaded = ImageService.uploadImagesToStorageAndCreateImageRecords(sqlSession, product, futures);
		executor.shutdown();
    	futures.clear();
		
		sqlSession.commit();
		LOG.error("Images downloaded for partner " + partner + " and entity " + entityid + " : " + totalImagesUploaded);
		
	}
	
	/*
	 * Upload images to Storage and create corresponding record in the database from the provided Image URLs.
	 *
	 * @param sqlSession the current SQL session.
	 * @param entitytype the entity type to be used for the image upload.
	 * @param entityid the entity ID to be used for the image upload.
	 * @param language the language of the uploaded text.
	 * @param images the list of image URLs to be uploaded.
	 * @param localFolder if provided, images will be loaded from the local folder.
	 * @throws Throwable the exception thrown on error.
	 */
	public static void uploadImages(SqlSession sqlSession, NameId.Type entitytype, String entityid, String language, ArrayList<NameId> images, String localFolder) throws StorageException {
		
		if (!Language.EN.equalsIgnoreCase(language) && !Language.isTranslatable(language)) {throw new ServiceException(Error.image_upload, "because the language code " + language + " cannot be translated");}
		
		Product product = sqlSession.getMapper(ProductMapper.class).read(entityid);
		if(product == null) {
			throw new ServiceException(Error.database_cannot_find, "product [" + entityid + "]");
		}
		
		// multi threading image download and image creation
		// number of thread can be optimized by finding out available resources
		ExecutorService executor = Executors.newFixedThreadPool(25);

		String partner = "";
		
		Map<FutureTask<List<ImageMetaData>>, Image> futures = new HashMap<FutureTask<List<ImageMetaData>>, Image>();
		boolean compressImage = false;
		if(RazorConfig.getEnvironmentId().equals(RazorConfig.PROD_ENVIRONMENT)
				|| RazorConfig.getEnvironmentId().equals(RazorConfig.UAT_ENVIRONMENT)) {
			compressImage = true;
		}
		
		long startTime = System.currentTimeMillis();
		
		List<Image> existingImages = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(product);
		
		// download each image in it's own thread
		int i = 1;
		for (NameId imageData : images) {
			try {
				String filename = imageData.getId().substring(imageData.getId().lastIndexOf("/") + 1);
				
				Image image = new Image();
				image.setName(filename);
				image.setLanguage(language);
				image.setProductId(Integer.parseInt(entityid));
				image.setUrl(imageData.getId());
				image.setNotes(imageData.getName());
				image.setType(Image.Type.Hosted);
				image.setSort(i++);
				
//				image = sqlSession.getMapper(ImageMapper.class).exists(image);
				if (!existingImages.contains(image)) {
					FutureTask<List<ImageMetaData>> task = new FutureTask<List<ImageMetaData>>(new ImageDownloadTask(imageData, filename, compressImage, localFolder));
					futures.put(task, image);
					executor.execute(task);
				} else {
					LOG.error("Image exist for product id: " + entityid  + " url: " + imageData.getId());
					sqlSession.getMapper(ImageMapper.class).update(image);
				}
			} 
			catch (Throwable x) {
				LOG.error("Bad or Absent Image URL: " + imageData.getId());
				x.printStackTrace();
			}
			
		}
		
		int totalImagesUploaded = ImageService.uploadImagesToStorageAndCreateImageRecords(sqlSession, product, futures);
		executor.shutdown();
    	futures.clear();
		
    	long endTime = System.currentTimeMillis();
		sqlSession.commit();
		LOG.error("Images downloaded for partner " + partner + " and entity " + entityid + " : " + totalImagesUploaded + " in mili seconds " + (endTime - startTime));
	}
	
	private static int uploadImagesToStorageAndCreateImageRecords(SqlSession sqlSession, Product product, Map<FutureTask<List<ImageMetaData>>, Image> futures) throws StorageException {
		LOG.debug("Now uploading images to the storage...");
		List<ImageMetaData> imagesToBeUploaded = new ArrayList<ImageMetaData>();
		List<FutureTask<List<ImageMetaData>>> processedTasks = new ArrayList<FutureTask<List<ImageMetaData>>>();
		int count = futures.size();
		List<Image> images = new ArrayList<Image>();
		while(true) {
			for (Map.Entry<FutureTask<List<ImageMetaData>>, Image> futureTaskEntry:futures.entrySet()) {
				FutureTask<List<ImageMetaData>> futureTask = futureTaskEntry.getKey();
			    if(futureTask.isDone() && !processedTasks.contains(futureTask)) {
			    	processedTasks.add(futureTaskEntry.getKey());
			    	List<ImageMetaData> processedImages;
			    	Image image = futureTaskEntry.getValue();
					try {
						processedImages = futureTaskEntry.getKey().get();
						for(ImageMetaData imageMetaData:processedImages) {
							if(imageMetaData.isThumbnail()) {
								image.setThumbnail(true);
							}
							if(imageMetaData.isStandard()) {
								image.setStandard(true);
							}
							image.setState(Image.State.Created.name());
						}
						imagesToBeUploaded.addAll(processedImages);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					count--;
					//  create Image records once images are downloaded and scaled
//					sqlSession.getMapper(ImageMapper.class).create(image);
					images.add(image);
					LOG.debug("Image created: " + image + " count: " + count);
			    }
			    
			}
			if(count <= 0) {
		    	break;
		    }
		    	
		}
		
		// upload images to the storage
		StorageService storageService = StorageServiceFactory.getInstance();
		
		if(imagesToBeUploaded.size() > 0) {
			String id = null;
			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(product.getSupplierid());
			ImageService.setPMSPMAbbrevations(sqlSession, product, partner);
			if(partner != null) {
				id = product.getAltid();
			} else {
				id = product.getId();
			}
			
			if(StringUtils.isEmpty(product.getPmsAbbrev()))
				LOG.error("PMS Abbrevation is not available for product: " + product.getId());
			if(StringUtils.isEmpty(product.getPmAbbrev()))
				LOG.error("PM Abbrevation is not available for product: " + product.getId());
			storageService.uploadImages(product.getPmsAbbrev(), product.getPmAbbrev(), id, imagesToBeUploaded);
			
			// now create the image records
			for(Image image:images) {
				sqlSession.getMapper(ImageMapper.class).create(image);
			}
		}
		return imagesToBeUploaded.size();
	}
	

	public static void persistImage(String name, int productId, String language, String notes, Image.Type type, String url, boolean standard, boolean thumbnail, SqlSession sqlSession) {
		Image image = new Image();
		image.setName(name);
		image.setProductId(productId);
		image.setState(Image.State.Created.name());
		image.setLanguage(language);
		image.setNotes(notes);
		image.setUrl(url);
		image.setStandard(standard);
		image.setThumbnail(thumbnail);
		image.setType(type);
		sqlSession.getMapper(ImageMapper.class).create(image);
	}
	
	
	/**
	 * Getting regular image location for specific property from the local drive or remote URL AND description.
	 * 
	 */
	public static List<NameId> getProductRegularImageURLsAndDescription(SqlSession sqlSession, String productid) {
		String imageLocation = ImageService.getProductImageLocation(sqlSession, productid);
		
		// Build image urls from product.getImageUrs
		List<NameId> images = new ArrayList<NameId>();
		ArrayList<Image> listNameIds = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(new NameId(productid));

		for(int i =0; i < listNameIds.size(); i++) {
			if(listNameIds.get(i).getType().equalsIgnoreCase(Image.Type.Hosted.name())) {
				images.add(new NameId( listNameIds.get(i).getNotes() , imageLocation + listNameIds.get(i).getName()  ));
			} else if(listNameIds.get(i).getType().equals(Image.Type.Linked.name().toLowerCase())) {
				images.add(   new NameId(listNameIds.get(i).getNotes()  , listNameIds.get(i).getUrl()));
			}
		}
		
		return images;
	}
	
	public static String getProductImageLocation(SqlSession sqlSession, String productid) {
		Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
		if(product == null) {
			throw new ServiceException(Error.database_cannot_find, "product [" + productid + "]");
		}
		Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(product.getSupplierid());
		String imageLocation = null;
		
		String name = null;                                                           // product.getAltid() == null ? "default" : product.getAltid();
//		StringBuilder pms = null;                                                            // product.getAltpartyid() == null ? "default" : product.getAltpartyid();
//		StringBuilder pm = null;                                                             // product.getAltSupplierId() == null ? "default" : product.getAltSupplierId();
		
		ImageService.setPMSPMAbbrevations(sqlSession, product, partner);
		
		if (partner != null) {
			name = product.getAltid() == null ? product.getId() : product.getAltid();
		} else {
			name = product.getId();
		}
		
		
		
		if(StringUtils.isEmpty(product.getPmsAbbrev()))
			LOG.error("PMS Abbrevation is not available for product: " + product.getId());
		if(StringUtils.isEmpty(product.getPmAbbrev()))
			LOG.error("PM Abbrevation is not available for product: " + product.getId());
		imageLocation = RazorConfig.getS3ImageURL() + product.getPmsAbbrev() + "/" + product.getPmAbbrev() + "/" + name + "/";
		
		return imageLocation;
	}
	
	public static String getProductImageLocation(SqlSession sqlSession, Product product) {
		if(product == null) {
			throw new ServiceException(Error.database_cannot_find, "product [" + product.getId() + "]");
		}
		Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(product.getSupplierid());
		String imageLocation = null;
		
		String name = null;                                                           // product.getAltid() == null ? "default" : product.getAltid();
//		StringBuilder pms = null;                                                            // product.getAltpartyid() == null ? "default" : product.getAltpartyid();
//		StringBuilder pm = null;                                                             // product.getAltSupplierId() == null ? "default" : product.getAltSupplierId();
		
		ImageService.setPMSPMAbbrevations(sqlSession, product, partner);
		
		if (partner != null) {
			name = product.getAltid() == null ? product.getId() : product.getAltid();
		} else {
			name = product.getId();
		}
		
		
		
		if(StringUtils.isEmpty(product.getPmsAbbrev()))
			LOG.error("PMS Abbrevation is not available for product: " + product.getId());
		if(StringUtils.isEmpty(product.getPmAbbrev()))
			LOG.error("PM Abbrevation is not available for product: " + product.getId());
		imageLocation = RazorConfig.getS3ImageURL() + product.getPmsAbbrev() + "/" + product.getPmAbbrev() + "/" + name + "/";
		
		return imageLocation;
	}
	
	public static void setPMSPMAbbrevations(SqlSession sqlSession, Product product, Partner partner) {
		String pms = product.getSupplierid(); 
		String pm = null;
		if (partner != null) {
			pm = partner.getAbbrevation();
			
			if(!partner.getOrganizationid().equals(partner.getPartyid())) {
				partner = sqlSession.getMapper(PartnerMapper.class).exists(partner.getOrganizationid());
				if(partner == null){
					LOG.error("Cannot find PMS partner");
				}
				else {
					if (partner.getAbbrevation() != null){
						pms = partner.getAbbrevation().toLowerCase();
						if(pm == null) { pm = pms; }						
					}else{
						pms = ImageService.RAZOR_PMS;	
						pm = product.getSupplierid() == null ? "default" : product.getSupplierid();
						LOG.error("PMS abbreviation is null.");
					}
				}
			} else if (pm == null){
					pms = ImageService.RAZOR_PMS;	
					pm = product.getSupplierid() == null ? "default" : product.getSupplierid();
					LOG.error("PM abbreviation is null.");
				} else {
					pm = pm.toLowerCase();
					pms = pm.toLowerCase();
				}
		} else {
			pms = ImageService.RAZOR_PMS;
			pm = product.getSupplierid() == null ? "default" : product.getSupplierid();
		}
		product.setPmAbbrev(pm);
		product.setPmsAbbrev(pms);
	}
	
	/**
	 * Getting public image for specific property from the local drive or remote URL.
	 * 
	 */
	public static String getProductDefaultImageURL(SqlSession sqlSession, String productid) {
		
		String imageLocation = ImageService.getProductImageLocation(sqlSession, productid);
		
		//TODO: Default image url should be setted(if no images in the storage).
		String result = "";
		
		// Get default product image.
		Image image = sqlSession.getMapper(ImageMapper.class).defaultimagebyproductid(new NameId(productid));
		
		if (image != null){
			if(image.getType().equalsIgnoreCase(Image.Type.Hosted.name())) {
				result =  imageLocation + image.getName();
			} 
			else if(image.getType().equalsIgnoreCase(Image.Type.Linked.name())) {
				result = image.getUrl();
			}
		}
		return result;
	}
	
	/**
	 * Getting list of regular images for specific property from the local drive or remote URL.
	 * 
	 */	
	public static List<String> getProductRegularImageURLs(SqlSession sqlSession, String productid) {
		return getProductRegularImageURLs(sqlSession, productid, null);
	}
	
	/**
	 * Getting limited list of images for specific property from the local drive or remote URL.
	 * 
	 */
	public static List<String> getProductRegularImageURLs(SqlSession sqlSession, String productid, Integer limitation) {
		
		String imageLocation = ImageService.getProductImageLocation(sqlSession, productid);
		
		// Build image urls from product.getImageUrs
		List<String> images = new ArrayList<String>();
		
		// Get product images sorted by 'sort'.
		NameIdAction action = new NameIdAction();
		action.setId(productid);
		if (limitation != null){
			action.setNumrows(limitation);
		}		
		ArrayList<Image> listImages = sqlSession.getMapper(ImageMapper.class).imagesbyproductidsortorder(action);
		
		for(Image image:listImages) {
			if(image.getType().equals(Image.Type.Hosted.name().toLowerCase())) {
				images.add(imageLocation + image.getName());
			} 
			else if(image.getType().equals(Image.Type.Linked.name().toLowerCase())) {
				images.add(image.getUrl());
			}
		}
		
		return images;
	}
	
	/**
	 * Getting thumbnail image location for specific property from the local drive or remote URL.
	 * 
	 */
	public static List<String> getProductThumbnailImageURLs(SqlSession sqlSession, String productid) {
		String imageLocation = ImageService.getProductImageLocation(sqlSession, productid);
		
		// Build image urls from product.getImageUrs
		List<String> images = new ArrayList<String>();
		//product.getImageurls();
		
		ArrayList<Image> listNameIds = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(new NameId(productid));
		
		for(Image image:listNameIds) {
			if(image.getType().equals(Image.Type.Hosted.name().toLowerCase())) {
				if(image.isThumbnail()) {
					String imageName = ImageService.getImageThumbFileName(image.getName());
					images.add(imageLocation + imageName);
				}
			} else if(image.getType().equals(Image.Type.Linked.name().toLowerCase())) {
				images.add(image.getUrl());
			}
		}
		
		return images;
	}
	
	/**
	 * Getting thumbnail and origin images for specific property from the local drive or remote URL.
	 * 
	 */
	public static List<String> getProductThumbAndOriginImageURLs(SqlSession sqlSession, String productid) {
		String imageLocation = ImageService.getProductImageLocation(sqlSession, productid);
		
		// Build image urls from product.getImageUrs
		List<String> images = new ArrayList<String>();
		//product.getImageurls();
		
		ArrayList<Image> listNameIds = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(new NameId(productid));
		
		for(Image image:listNameIds) {
			if(image.getType().equals(Image.Type.Hosted.name().toLowerCase())) {
				if(image.isThumbnail()) {
					String imageName = ImageService.getImageThumbFileName(image.getName());
					images.add(imageLocation + imageName);
				}else
				{
					images.add(imageLocation + image.getName());
				}
			} else if(image.getType().equals(Image.Type.Linked.name().toLowerCase())) {
				images.add(image.getUrl());
			}
		}
		
		return images;
	}
	
	/**
	 * Gets the image thumbnail file name.
	 * 
	 * @param value the specified origin image file name with extension.
	 * @return thumbnail image file name.
	 */
	public static String getImageThumbFileName(String value) {
		String result = "";
		
		if (value == null || value.contains(ImageMetaData.Size.Thumb.name()) || value.lastIndexOf('.') <= 0) {
			result = value;
		}else {
			String fileName = value.substring(0, value.indexOf('.'));
			String fileExtension = value.substring(value.indexOf('.'));
			result = fileName + ImageMetaData.Size.Thumb.name() + fileExtension;
		}

		return result;
	}

}