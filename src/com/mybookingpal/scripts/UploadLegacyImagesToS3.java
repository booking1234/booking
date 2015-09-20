package com.mybookingpal.scripts;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.zip.GZIPInputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ImageMapper;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.shared.Image;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Text;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mybookingpal.config.RazorConfig;
import com.mybookingpal.server.ImageService;
import com.mybookingpal.service.StorageService;
import com.mybookingpal.service.StorageServiceFactory;
import com.mybookingpal.storage.data.ImageMetaData;
import com.mybookingpal.storage.exception.StorageException;
import com.mybookingpal.utils.ImageUtils;

public class UploadLegacyImagesToS3 {
	
	private final static Logger logger = LoggerFactory.getLogger(UploadLegacyImagesToS3.class);
	private final static StorageService storageService = StorageServiceFactory.getInstance();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		UploadLegacyImagesToS3.fixLegacyImagesThumbnailRecords();
//		UploadLegacyImagesToS3.uploadThumbnailImages();
//		UploadLegacyImagesToS3.uploadImagesForInActiveProperties();
//		UploadLegacyImagesToS3.uploadThumbnailImagesForProducts(false);
//		UploadLegacyImagesToS3.fixDuplicateImageRecords(true);
//		UploadLegacyImagesToS3.fixDuplicateFinalImageRecords(true);
//		UploadLegacyImagesToS3.removeFinalImageRecordsAndImage(true);
//		UploadLegacyImagesToS3.uploadImagesForRazorProperties(true);
//		UploadLegacyImagesToS3.fixContentTypesForRazorProducts(true);
//		UploadLegacyImagesToS3.fixContentTypesAndFilenamesForNonRegularFiles("90640");  // 327518 
//		UploadLegacyImagesToS3.fixContentTypesAndFilenamesForRazorProducts("13748");  // 327518 
//		UploadLegacyImagesToS3.fixFilenamesThatBeginsWithNull();  // 327518 
//		UploadLegacyImagesToS3.checkAndLoadMisingImagesForARazorProduct("981");
		
		// fix Axsys San Diego UAT suppllier id
//		UploadLegacyImagesToS3.fixContentTypesAndFilenamesForNonRegularFiles("325771");
		
		UploadLegacyImagesToS3.uploadStandardImagesForProducts("90640");
	}
	
	public static void uploadMissingImages() {
		SqlSession sqlSession = RazorServer.openSession();
		
		int batch = 2000;
		int offset = 0;
		int totalRecords = 6000 - offset;
		
		int productCount = 0;
		

			System.out.println("Loading images records: " + offset + " to " + (offset + batch));
			RowBounds criteria = new RowBounds(offset, batch);
	
			// BEGIN: replace this by Product and Text lookup...create Image instances
			//specify the required version
			LocalDateTime version = new LocalDateTime(2014, 8, 1, 0, 0, 0);
			ArrayList<String> productIds = sqlSession.getMapper(ProductMapper.class).
					getProductsWithAltIdAndAltPartyIdasNullbyVersion(
							version.getYear() + "-" + version.getMonthOfYear()
							+ "-" + version.getDayOfMonth()
							);
			
			ArrayList<Image> images = new ArrayList<Image>();
			
			for(String productId : productIds)
			{
				NameId action = new NameId();
				action.setName("Product");
				action.setId(productId);
				
				ArrayList<Text> results = sqlSession.getMapper(TextMapper.class).imagesAndVersionByNameId(action);
				
				for(Text result : results)
				{
					Date imageVersion = result.getVersion();

					LocalDateTime  imgVersion = new LocalDateTime(imageVersion.getYear(), imageVersion.getMonth() + 1,
							imageVersion.getDate(), imageVersion.getHours(), imageVersion.getMinutes(),
							imageVersion.getSeconds());
					
					if(imgVersion.compareTo(version) >= 0)
					{
						Image image = new Image();
						image.setProductId(Integer.parseInt(productId));
						image.setName(result.getId());
						image.setOldName(result.getId());
						image.setNotes(result.getNotes());
						images.add(image);
					}
				}
			}
//			List<Image> images = sqlSession.selectList(ImageMapper.class.getName() + ".readoldproducts", criteria);;
					
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
			
			// END

			
			System.out.println("Total products for which images will be uploaded: " + productImages.keySet().size());
			
			System.out.println("Product 37934 exists: " + (productImages.get("37934") != null ? "true" : "false"));
			
			UploadLegacyImagesToS3 instance = new UploadLegacyImagesToS3();
			
			
			
			for (Map.Entry<String, List<Image>> entry : productImages.entrySet()) {
				Product product = sqlSession.getMapper(ProductMapper.class).read(entry.getKey());
				if(product == null) {
					System.out.println("Product not found for image product id: " + entry.getKey());
					continue;
				}
				if(!product.getSupplierid().equals("90640")) {
					sqlSession = RazorServer.openSession();
					ExecutorService executor = Executors.newFixedThreadPool(25);
					Map<FutureTask<List<ImageMetaData>>, Image> futures = new HashMap<FutureTask<List<ImageMetaData>>, Image>();
					
					System.out.println("Uploading images for product: " + entry.getKey() + " total images: " + entry.getValue().size());
					for(Image image:entry.getValue()) {
						image.setName(image.getOldName());
						FutureTask<List<ImageMetaData>> task = new FutureTask<List<ImageMetaData>>(instance.new ImageDownloadTask(image));
						futures.put(task, image);
						executor.execute(task);
					}
					int totalImagesUploaded;
					try {
						totalImagesUploaded = instance.uploadImagesToStorageAndCreateImageRecords(sqlSession, product, futures);
						executor.shutdown();
						futures.clear();
						sqlSession.commit();
						System.out.println("Images downloaded for entity " + product.getId() + " : " + totalImagesUploaded);
						productCount++;
					} catch (StorageException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					System.out.println("Not Uploading images for product: " + entry.getKey() + " supplier id: " + product.getSupplierid());
				}
				
			}
			sqlSession.commit();
			
			totalRecords = totalRecords - batch;
			offset = offset + batch;
		
		System.out.println("Images upload for total products: " + productCount);
		
		
	}
	
	public static void uploadImagesForRazorProperties(boolean active) {
		SqlSession sqlSession = RazorServer.openSession();
		
		int batch = 2000;
		int offset = 0;
		int totalRecords = 6000 - offset;
		
		int productCount = 0;
		

			System.out.println("Loading images records: " + offset + " to " + (offset + batch));
			RowBounds criteria = new RowBounds(offset, batch);
			// BEGIN: replace this by Product and Text lookup...create Image instances
			//specify the required version
			LocalDateTime version = new LocalDateTime(2005, 1, 1, 0, 0, 0);
			List<String> productIds = new ArrayList<String>();
			if(active) {
				productIds = sqlSession.getMapper(ProductMapper.class).
				getProductsWithAltIdAndAltPartyIdasNullbyVersion(
						version.getYear() + "-" + version.getMonthOfYear()
						+ "-" + version.getDayOfMonth()
						);
			}
			else {
				productIds = sqlSession.getMapper(ProductMapper.class).getInActiveProductsWithAltIdAndAltPartyIdasNull(null);
			}
			
			ArrayList<Image> images = new ArrayList<Image>();
			Map<String, List<Image>> productImages = new HashMap<String, List<Image>>();
			
			for(String productId : productIds)
			{
				NameId action = new NameId();
				action.setName("Product");
				action.setId(productId);
				
				ArrayList<Text> results = sqlSession.getMapper(TextMapper.class).imagesAndVersionByNameId(action);
				images = new ArrayList<Image>();
				Product product = sqlSession.getMapper(ProductMapper.class).read(productId);
				for(Text result : results)
				{
					Image image = new Image();
					image.setProductId(Integer.parseInt(productId));
					image.setName(result.getId());
					image.setOldName(result.getId());
					image.setNotes(result.getNotes());
					image.setLanguage(result.getLanguage());
					image.setState(result.getState());
																	
					Image temp = sqlSession.getMapper(ImageMapper.class).exists(image);
					if(temp == null) {
						logger.info("Adding missing image for product id: " + productId + " name: " + image.getName());
						String thumbFileName = image.getName().substring(0, image.getName().indexOf(".")) + "Thumb." + image.getName().substring(image.getName().indexOf(".") + 1);
						try {
							if(!storageService.isFileExist("raz", product.getSupplierid(), product.getId(), image.getName())) {
								images.add(image);
							}
							else {
								// create image record only
								if(storageService.isFileExist("raz", product.getSupplierid(), product.getId(), thumbFileName)) {
									// just create image record
									image.setThumbnail(true);
									logger.info("Adding missing image record for product id: " + productId + " name: " + image.getName());
									sqlSession.getMapper(ImageMapper.class).create(image);
								}
								else {
									images.add(image);
								}
								
							}
						} catch (StorageException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
				if(images.size() > 0)
					productImages.put(productId, images);
			}


			
			logger.info("Total products for which images will be uploaded: " + productImages.keySet().size());
			logger.info("Product with missing image 53967 exists: " + productImages.keySet().contains("53967"));
						
			UploadLegacyImagesToS3 instance = new UploadLegacyImagesToS3();
			
			
			
			for (Map.Entry<String, List<Image>> entry : productImages.entrySet()) {
				Product product = sqlSession.getMapper(ProductMapper.class).read(entry.getKey());
				if(product == null) {
					logger.error("Product not found for image product id: " + entry.getKey());
					continue;
				}
				if(!product.getSupplierid().equals("90640")) {
					sqlSession = RazorServer.openSession();
					ExecutorService executor = Executors.newFixedThreadPool(25);
					Map<FutureTask<List<ImageMetaData>>, Image> futures = new HashMap<FutureTask<List<ImageMetaData>>, Image>();
					
					System.out.println("Uploading images for product: " + entry.getKey() + " total images: " + entry.getValue().size());
					for(Image image:entry.getValue()) {
						image.setName(image.getOldName());
						FutureTask<List<ImageMetaData>> task = new FutureTask<List<ImageMetaData>>(instance.new ImageDownloadTask(image));
						futures.put(task, image);
						executor.execute(task);
					}
					int totalImagesUploaded;
					try {
						totalImagesUploaded = instance.uploadImagesToStorageAndCreateImageRecords(sqlSession, product, futures);
						executor.shutdown();
						futures.clear();
						sqlSession.commit();
						System.out.println("Downloaded images for product " + product.getId() + " : " + totalImagesUploaded);
						productCount++;
					} catch (StorageException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					System.out.println("Not Uploading images for product: " + entry.getKey() + " supplier id: " + product.getSupplierid());
				}
				
			}
			sqlSession.commit();
		    sqlSession.close();	
		
		
		System.out.println("Images upload for total products: " + productCount);
		
		
	}
	
	public static void checkAndLoadMisingImagesForRazorProducts() {
		
		SqlSession sqlSession = RazorServer.openSession();
		
		int productCount = 0;

		// BEGIN: replace this by Product and Text lookup...create Image instances
		//specify the required version
		LocalDateTime version = new LocalDateTime(2005, 1, 1, 0, 0, 0);
		ArrayList<String> productIds = sqlSession.getMapper(ProductMapper.class).
				getProductsWithAltIdAndAltPartyIdasNullbyVersion(
						version.getYear() + "-" + version.getMonthOfYear()
						+ "-" + version.getDayOfMonth()
						);
		
		ArrayList<Image> images;
		Map<String, List<Image>> productImages = new HashMap<String, List<Image>>();
		for(String productId : productIds)
		{
			NameId action = new NameId();
			action.setName("Product");
			action.setId(productId);
			
			ArrayList<Text> results = sqlSession.getMapper(TextMapper.class).imagesAndVersionByNameId(action);
			
			if(results.size() > 0) {
				List<Image> imagesForProduct = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(action);
				if(imagesForProduct.isEmpty()) {
					images = new ArrayList<Image>();
					System.out.println("Adding missing images for product id: " + productId);
					for(Text result : results)
					{
						Image image = new Image();
						image.setProductId(Integer.parseInt(productId));
						image.setName(result.getId());
						image.setOldName(result.getId());
						images.add(image);
					}
					productImages.put(productId, images);
				}
			}
		}

		UploadLegacyImagesToS3 instance = new UploadLegacyImagesToS3();
		
		for (Map.Entry<String, List<Image>> entry : productImages.entrySet()) {
			Product product = sqlSession.getMapper(ProductMapper.class).read(entry.getKey());
			if(product == null) {
				System.out.println("Product not found for image product id: " + entry.getKey());
				continue;
			}
			if(!product.getSupplierid().equals("90640")) {
				sqlSession = RazorServer.openSession();
				ExecutorService executor = Executors.newFixedThreadPool(25);
				Map<FutureTask<List<ImageMetaData>>, Image> futures = new HashMap<FutureTask<List<ImageMetaData>>, Image>();
				
				System.out.println("Uploading images for product: " + entry.getKey() + " total images: " + entry.getValue().size());
				for(Image image:entry.getValue()) {
					image.setName(image.getOldName());
					FutureTask<List<ImageMetaData>> task = new FutureTask<List<ImageMetaData>>(instance.new ImageDownloadTask(image));
					futures.put(task, image);
					executor.execute(task);
				}
				int totalImagesUploaded;
				try {
					totalImagesUploaded = instance.uploadImagesToStorageAndCreateImageRecords(sqlSession, product, futures);
					executor.shutdown();
					futures.clear();
					sqlSession.commit();
					System.out.println("Images downloaded for entity " + product.getId() + " : " + totalImagesUploaded);
					productCount++;
				} catch (StorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("Not Uploading images for product: " + entry.getKey() + " supplier id: " + product.getSupplierid());
			}
			
		}
		sqlSession.commit();
		
		System.out.println("Images upload for total products: " + productCount);
	}
	
	/**
	 * Load missing images for a Razor product
	 * @param productId
	 */
	public static void checkAndLoadMisingImagesForARazorProduct(String productId) {
		
		SqlSession sqlSession = RazorServer.openSession();
		
		
		
		ArrayList<Image> images;
		Map<String, List<Image>> productImages = new HashMap<String, List<Image>>();
		
		NameId action = new NameId();
		action.setName("Product");
		action.setId(productId);
		
		ArrayList<Text> results = sqlSession.getMapper(TextMapper.class).imagesAndVersionByNameId(action);
		
		if(results.size() > 0) {
			List<Image> imagesForProduct = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(action);
			if(imagesForProduct.isEmpty()) {
				images = new ArrayList<Image>();
				System.out.println("Adding missing images for product id: " + productId);
				for(Text result : results)
				{
					Image image = new Image();
					image.setProductId(Integer.parseInt(productId));
					image.setName(result.getId());
					image.setOldName(result.getId());
					images.add(image);
				}
				productImages.put(productId, images);
			}
		}
		

		UploadLegacyImagesToS3 instance = new UploadLegacyImagesToS3();
		
		for (Map.Entry<String, List<Image>> entry : productImages.entrySet()) {
			Product product = sqlSession.getMapper(ProductMapper.class).read(entry.getKey());
			if(product == null) {
				System.out.println("Product not found for image product id: " + entry.getKey());
				continue;
			}
			if(!product.getSupplierid().equals("90640")) {
				sqlSession = RazorServer.openSession();
				ExecutorService executor = Executors.newFixedThreadPool(25);
				Map<FutureTask<List<ImageMetaData>>, Image> futures = new HashMap<FutureTask<List<ImageMetaData>>, Image>();
				
				System.out.println("Uploading images for product: " + entry.getKey() + " total images: " + entry.getValue().size());
				for(Image image:entry.getValue()) {
					image.setName(image.getOldName());
					FutureTask<List<ImageMetaData>> task = new FutureTask<List<ImageMetaData>>(instance.new ImageDownloadTask(image));
					futures.put(task, image);
					executor.execute(task);
				}
				int totalImagesUploaded;
				try {
					totalImagesUploaded = instance.uploadImagesToStorageAndCreateImageRecords(sqlSession, product, futures);
					executor.shutdown();
					futures.clear();
					sqlSession.commit();
					System.out.println("Images downloaded for entity " + product.getId() + " : " + totalImagesUploaded);
		
				} catch (StorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("Not Uploading images for product: " + entry.getKey() + " supplier id: " + product.getSupplierid());
			}
			
		}
		sqlSession.commit();
	
	}
	
	public static void fixDuplicateImageRecords(boolean active) {
		SqlSession sqlSession = RazorServer.openSession();
		
		int productCount = 0;

		// BEGIN: replace this by Product and Text lookup...create Image instances
		//specify the required version
		LocalDateTime version = new LocalDateTime(2005, 1, 1, 0, 0, 0);
		List<String> productIds = new ArrayList<String>();
		if(active) {
			productIds = sqlSession.getMapper(ProductMapper.class).
			getProductsWithAltIdAndAltPartyIdasNullbyVersion(
					version.getYear() + "-" + version.getMonthOfYear()
					+ "-" + version.getDayOfMonth()
					);
		}
		else {
			productIds = sqlSession.getMapper(ProductMapper.class).getInActiveProductsWithAltIdAndAltPartyIdasNull(null);
		}
		
		ArrayList<Image> images;
		Map<String, List<Image>> productImages = new HashMap<String, List<Image>>();
		for(String productId : productIds)
		{
			NameId action = new NameId();
			action.setName("Product");
			action.setId(productId);
			
			ArrayList<Image> results = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(action);
			
			if(results.size() > 0) {
				images = new ArrayList<Image>();
				List<String> imageIds = new ArrayList<String>();
				for(Image result : results) {
					if(imageIds.contains(result.getName()))
						images.add(result);
					imageIds.add(result.getName());
				}
				if(images.size() > 0)
					productImages.put(productId, images);
			}
			
		}
		
		for (Map.Entry<String, List<Image>> entry : productImages.entrySet()) {
			sqlSession = RazorServer.openSession();
			if(entry.getValue().size() > 0) {
				logger.info("Removing image records for product: " + entry.getKey() + " total records: " + entry.getValue().size());
				for(Image image:entry.getValue()) {
					sqlSession.getMapper(ImageMapper.class).deletebyexample(image);
				}
			}
			sqlSession.commit();
			sqlSession.close();
		}
	}
	
	public static void fixDuplicateFinalImageRecords(boolean active) {
		SqlSession sqlSession = RazorServer.openSession();
		
		// BEGIN: replace this by Product and Text lookup...create Image instances
		//specify the required version
		LocalDateTime version = new LocalDateTime(2005, 1, 1, 0, 0, 0);
		List<String> productIds = new ArrayList<String>();
		if(active) {
			productIds = sqlSession.getMapper(ProductMapper.class).
			getProductsWithAltIdAndAltPartyIdasNullbyVersion(
					version.getYear() + "-" + version.getMonthOfYear()
					+ "-" + version.getDayOfMonth()
					);
		}
		else {
			productIds = sqlSession.getMapper(ProductMapper.class).getInActiveProductsWithAltIdAndAltPartyIdasNull(null);
		}
		
		ArrayList<Image> images;
		Map<String, List<Image>> productImages = new HashMap<String, List<Image>>();
		for(String productId : productIds) {
			NameId action = new NameId();
			action.setName("Product");
			action.setId(productId);
			
			ArrayList<Text> results = sqlSession.getMapper(TextMapper.class).imagesAndVersionByNameId(action);
			
			if(results.size() > 0) {
				images = new ArrayList<Image>();
				for(Text result : results) {
					if(result.getState() != null && (result.getState().equalsIgnoreCase(Text.State.Final.name()) || result.getState().equalsIgnoreCase(Text.State.Initial.name()))) {
						Image image = new Image();
						image.setProductId(Integer.parseInt(productId));
						image.setName(result.getId());
						
						image = sqlSession.getMapper(ImageMapper.class).exists(image);
						if(image != null) {
							image.setState(result.getState());
							images.add(image);
						}
					}
				}
				
				if(images.size() > 0)
					productImages.put(productId, images);
			}
			
		}
		
		for (Map.Entry<String, List<Image>> entry : productImages.entrySet()) {
			sqlSession = RazorServer.openSession();
			if(entry.getValue().size() > 0) {
				logger.info("Updating image records for product: " + entry.getKey() + " total records: " + entry.getValue().size());
				for(Image image:entry.getValue()) {
					sqlSession.getMapper(ImageMapper.class).update(image);
				}
			}
			sqlSession.commit();
			sqlSession.close();
		}
	}
	
	public static void removeFinalImageRecordsAndImage(boolean active) {
		SqlSession sqlSession = RazorServer.openSession();
		
		// BEGIN: replace this by Product and Text lookup...create Image instances
		//specify the required version
		LocalDateTime version = new LocalDateTime(2005, 1, 1, 0, 0, 0);
		List<String> productIds = new ArrayList<String>();
		if(active) {
			productIds = sqlSession.getMapper(ProductMapper.class).
			getProductsWithAltIdAndAltPartyIdasNullbyVersion(
					version.getYear() + "-" + version.getMonthOfYear()
					+ "-" + version.getDayOfMonth()
					);
		}
		else {
			productIds = sqlSession.getMapper(ProductMapper.class).getInActiveProductsWithAltIdAndAltPartyIdasNull(null);
		}
		
	
		
		for(String productId : productIds) {
			NameId action = new NameId();
			action.setName("Product");
			action.setId(productId);
			Product product = sqlSession.getMapper(ProductMapper.class).read(productId);
			List<Image> results = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(action);
			
			if(results.size() > 0) {
				for(Image result : results) {
					if(result.getState() != null && (result.getState().equalsIgnoreCase(Text.State.Final.name()) || result.getState().equalsIgnoreCase(Text.State.Initial.name()))) {
						logger.info("Removing image for product: " + productId + " image name: " + result.getName() + " image state: " + result.getState());
						String thumbFile = result.getName().substring(0, result.getName().indexOf(".")) + "Thumb." + result.getName().substring(result.getName().indexOf(".") + 1);
						try {
							if(storageService.isFileExist("raz", product.getSupplierid(), product.getId(), result.getName())) {
								storageService.deleteImaget("raz", product.getSupplierid(), product.getId(), result.getName());			
							}
							if(storageService.isFileExist("raz", product.getSupplierid(), product.getId(), thumbFile)) {
								storageService.deleteImaget("raz", product.getSupplierid(), product.getId(), thumbFile);	
							}
							sqlSession.getMapper(ImageMapper.class).deletebyexample(result);
							sqlSession.commit();
						} catch (StorageException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
		}
		sqlSession.close();
		
		
	}
	public static void fixContentTypesForRazorProducts(boolean active) {
		SqlSession sqlSession = RazorServer.openSession();
		
		// BEGIN: replace this by Product and Text lookup...create Image instances
		//specify the required version
		LocalDateTime version = new LocalDateTime(2005, 1, 1, 0, 0, 0);
		List<String> productIds = new ArrayList<String>();
		if(active) {
			productIds = sqlSession.getMapper(ProductMapper.class).
					getProductsWithAltIdAndAltPartyIdasNullbyVersion(
							version.getYear() + "-" + version.getMonthOfYear()
							+ "-" + version.getDayOfMonth()
							);
		}
		else {
			productIds = sqlSession.getMapper(ProductMapper.class).getInActiveProductsWithAltIdAndAltPartyIdasNull(null);
		}
		
		
		
		for(String productId : productIds) {
			NameId action = new NameId();
			action.setName("Product");
			action.setId(productId);
			Product product = sqlSession.getMapper(ProductMapper.class).read(productId);
			List<Image> results = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(action);
			
			if(results.size() > 0) {
				for(Image result : results) {
					if(result.getState() != null && (result.getState().equalsIgnoreCase(Text.State.Created.name()))) {
						logger.info("Updating content type for product image: " + productId + " image name: " + result.getName());
						String thumbFile = result.getName().substring(0, result.getName().indexOf(".")) + "Thumb." + result.getName().substring(result.getName().indexOf(".") + 1);
						String stdFile = result.getName().substring(0, result.getName().indexOf(".")) + "Standard." + result.getName().substring(result.getName().indexOf(".") + 1);
						try {
							if(storageService.isFileExist("raz", product.getSupplierid(), product.getId(), result.getName())) {
								storageService.updateFileContentType("raz", product.getSupplierid(), product.getId(), result.getName());			
							}
							if(result.isThumbnail() && storageService.isFileExist("raz", product.getSupplierid(), product.getId(), thumbFile)) {
								storageService.updateFileContentType("raz", product.getSupplierid(), product.getId(), thumbFile);	
							}
							if(result.isStandard() && storageService.isFileExist("raz", product.getSupplierid(), product.getId(), stdFile)) {
								storageService.updateFileContentType("raz", product.getSupplierid(), product.getId(), stdFile);	
							}
						
						} catch (StorageException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
		}
		sqlSession.close();
		
		
	}
	
	public static void fixContentTypesAndFilenamesForNonRegularFiles(String supplierId) {
		SqlSession sqlSession = RazorServer.openSession();
		
		// BEGIN: replace this by Product and Text lookup...create Image instances
		//specify the required version
		NameIdAction supplier = new NameIdAction();
		supplier.setId(supplierId);
		List<String> productIds = sqlSession.getMapper(ProductMapper.class).activeProductIdListBySupplier(supplier);
		sqlSession.close();
		int cnt = 0;
		for(String productId : productIds) {
			cnt++;
//			if(cnt < 25784) {
//				continue;
//			}
			sqlSession = RazorServer.openSession();
			NameId action = new NameId();
			action.setName("Product");
			action.setId(productId);
			Product product = sqlSession.getMapper(ProductMapper.class).read(productId);
			List<Image> results = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(action);
			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(product.getSupplierid());
			ImageService.setPMSPMAbbrevations(sqlSession, product, partner);
			logger.info("Working product number " + cnt);
			if(results.size() > 0) {
				for(Image result : results) {
					if(result.getState() != null && (result.getState().equalsIgnoreCase(Text.State.Created.name()))) {
						logger.info("Looking up content type for product image: " + productId + " image name: " + result.getName());
						String contentType = "jpeg";
						try {
							if(storageService.isFileExist(product.getPmsAbbrev(), product.getPmAbbrev(), product.getAltid(), result.getName())) {
								contentType = storageService.getFileContentType(product.getPmsAbbrev(), product.getPmAbbrev(), product.getAltid(), result.getName());
								storageService.updateFileContentType(product.getPmsAbbrev(), product.getPmAbbrev(), product.getAltid(), result.getName());	
							}
							
							if(!StringUtils.isEmpty(contentType)) {
								if(contentType.contains("image")) {
									contentType = contentType.substring(contentType.indexOf('/') + 1).toLowerCase();
									if(contentType.equals("jpg")) {
										contentType = "jpeg";
									}
								}
								contentType = contentType.toLowerCase();
							}
							else 
								contentType = "jpeg";
							
							logger.info("Updating content type for product image: " + productId + " image name: " + result.getName());
							String newThumbFile = result.getName().indexOf(".") > 0 
													? (result.getName().substring(0, result.getName().indexOf(".")) + "Thumb" + result.getName().substring(result.getName().indexOf(".")))
													: result.getName() + "Thumb" ;
							String thumbFile = (result.getName().indexOf(".") > 0 ? result.getName().substring(0, result.getName().indexOf(".")) : result.getName()) + "Thumb." + contentType;
							
							if(result.isThumbnail() && storageService.isFileExist(product.getPmsAbbrev(), product.getPmAbbrev(), product.getAltid(), thumbFile)) {
								storageService.updateFilenameAndContentType(product.getPmsAbbrev(), product.getPmAbbrev(), product.getAltid(), thumbFile, newThumbFile);	
								storageService.deleteImaget(product.getPmsAbbrev(), product.getPmAbbrev(), product.getAltid(), thumbFile);	
							}
							
							String stdFile = (result.getName().indexOf(".") > 0 ? result.getName().substring(0, result.getName().indexOf(".")) : result.getName()) + "Standard." + contentType;
							String newStdFile = result.getName().indexOf(".") > 0 
													? result.getName().substring(0, result.getName().indexOf(".")) + "Standard" + result.getName().substring(result.getName().indexOf("."))
													: result.getName()+ "Standard";
													
							if(result.isStandard() && storageService.isFileExist(product.getPmsAbbrev(), product.getPmAbbrev(), product.getAltid(), stdFile)) {
								storageService.updateFilenameAndContentType(product.getPmsAbbrev(), product.getPmAbbrev(), product.getAltid(), stdFile, newStdFile);	
								storageService.deleteImaget(product.getPmsAbbrev(), product.getPmAbbrev(), product.getAltid(), stdFile);	
							}
						
						} catch (StorageException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
			sqlSession.close();
		}
	}
	
	public static void fixContentTypesAndFilenamesForRazorProducts(String supplierId) {
		SqlSession sqlSession = RazorServer.openSession();
		
		// BEGIN: replace this by Product and Text lookup...create Image instances
		//specify the required version
		NameIdAction supplier = new NameIdAction();
		supplier.setId(supplierId);
		List<String> productIds = sqlSession.getMapper(ProductMapper.class).activeProductIdListBySupplier(supplier);
		sqlSession.close();
		int cnt = 0;
		for(String productId : productIds) {
			sqlSession = RazorServer.openSession();
			NameId action = new NameId();
			action.setName("Product");
			action.setId(productId);
			Product product = sqlSession.getMapper(ProductMapper.class).read(productId);
			List<Image> results = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(action);
			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(product.getSupplierid());
			ImageService.setPMSPMAbbrevations(sqlSession, product, partner);
			logger.info("Working product number " + cnt);
			if(results.size() > 0) {
				for(Image result : results) {
					if(result.getState() != null && (result.getState().equalsIgnoreCase(Text.State.Created.name()))) {
						logger.info("Looking up content type for product image: " + productId + " image name: " + result.getName());
						String contentType = "jpeg";
						try {
							if(storageService.isFileExist(product.getPmsAbbrev(), product.getPmAbbrev(), product.getId(), result.getName())) {
								contentType = storageService.getFileContentType(product.getPmsAbbrev(), product.getPmAbbrev(), product.getId(), result.getName());
								storageService.updateFileContentType(product.getPmsAbbrev(), product.getPmAbbrev(), product.getId(), result.getName());	
							}
							
//							if(!StringUtils.isEmpty(contentType)) {
//								if(contentType.contains("image")) {
//									contentType = contentType.substring(contentType.indexOf('/') + 1).toLowerCase();
//									if(contentType.equals("jpg")) {
//										contentType = "jpeg";
//									}
//								}
//								contentType = contentType.toLowerCase();
//							}
//							else 
//								contentType = "jpeg";
							
							logger.info("Updating content type for product image: " + productId + " image name: " + result.getName());
							
							String thumbFile = (result.getName().indexOf(".") > 0 ? result.getName().substring(0, result.getName().indexOf(".")) : result.getName()) + "Thumb" + result.getName().substring(result.getName().indexOf("."));
							
							if(result.isThumbnail() && storageService.isFileExist(product.getPmsAbbrev(), product.getPmAbbrev(), product.getId(), thumbFile)) {
								storageService.updateFileContentType(product.getPmsAbbrev(), product.getPmAbbrev(), product.getId(), thumbFile);	
//								storageService.deleteImaget(product.getPmsAbbrev(), product.getPmAbbrev(), product.getAltid(), thumbFile);	
							}
							
							String stdFile = (result.getName().indexOf(".") > 0 ? result.getName().substring(0, result.getName().indexOf(".")) : result.getName()) + "Standard" + result.getName().substring(result.getName().indexOf("."));
							
													
							if(result.isStandard() && storageService.isFileExist(product.getPmsAbbrev(), product.getPmAbbrev(), product.getId(), stdFile)) {
								storageService.updateFileContentType(product.getPmsAbbrev(), product.getPmAbbrev(), product.getId(), stdFile);	
							}
						
						} catch (StorageException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
			sqlSession.close();
		}
		
		
		
	}
	
	
	public static void fixFilenamesThatBeginsWithNull() {
		SqlSession sqlSession = RazorServer.openSession();
		
		// BEGIN: replace this by Product and Text lookup...create Image instances
		//specify the required version
		List<Image> images = sqlSession.getMapper(ImageMapper.class).readimagesbeginswithnull();

		int cnt = 0;
		for(Image image : images) {
			NameId action = new NameId();
			action.setName("Product");
			String productId = String.valueOf(image.getProductId());
			action.setId(productId);
			Product product = sqlSession.getMapper(ProductMapper.class).read(productId);
			Partner partner = sqlSession.getMapper(PartnerMapper.class).read(product.getSupplierid());

			ImageService.setPMSPMAbbrevations(sqlSession, product, partner);
			logger.info("Working product number " + cnt);
			
			if(image.getState() != null && (image.getState().equalsIgnoreCase(Text.State.Created.name())) && product.getAltid() == null) {
				logger.info("Looking up content type for product image: " + productId + " image name: " + image.getName());
				String newFilename = image.getName().replaceAll("null", "");
				try {
					if(storageService.isFileExist(product.getPmsAbbrev(), product.getPmAbbrev(), product.getId(), image.getName())) {
						storageService.updateFilenameAndContentType(product.getPmsAbbrev(), product.getPmAbbrev(), product.getId(), image.getName(), newFilename);	
						storageService.deleteImaget(product.getPmsAbbrev(), product.getPmAbbrev(), product.getId(), image.getName());	
					}
					
				
					logger.info("Updating content type for product image: " + productId + " image name: " + image.getName());
					String thumbFile = image.getName().indexOf(".") > 0 ? image.getName().substring(0, image.getName().indexOf("."))+ "Thumb" + image.getName().substring(image.getName().indexOf("."))
							 											: image.getName() + "Thumb" ;
							
					if(image.isThumbnail() && storageService.isFileExist(product.getPmsAbbrev(), product.getPmAbbrev(), product.getId(), thumbFile)) {
						String newThumbFile = newFilename.indexOf(".") > 0 
								? (newFilename.substring(0, newFilename.indexOf(".")) + "Thumb" + newFilename.substring(newFilename.indexOf(".")))
										: newFilename + "Thumb" ;
						storageService.updateFilenameAndContentType(product.getPmsAbbrev(), product.getPmAbbrev(), product.getId(), thumbFile, newThumbFile);	
						storageService.deleteImaget(product.getPmsAbbrev(), product.getPmAbbrev(), product.getId(), thumbFile);	
					}
					
					String stdFile = image.getName().indexOf(".") > 0 ? image.getName().substring(0, image.getName().indexOf(".")) + "Standard" + image.getName().substring(image.getName().indexOf("."))
																		: image.getName() + "Standard";
					if(image.isStandard() && storageService.isFileExist(product.getPmsAbbrev(), product.getPmAbbrev(), product.getId(), stdFile)) {
						String newStdFile = newFilename.indexOf(".") > 0 
								? newFilename.substring(0, newFilename.indexOf(".")) + "Standard" + newFilename.substring(newFilename.indexOf("."))
										: newFilename + "Standard";
										
						
						storageService.updateFilenameAndContentType(product.getPmsAbbrev(), product.getPmAbbrev(), product.getId(), stdFile, newStdFile);	
						storageService.deleteImaget(product.getPmsAbbrev(), product.getPmAbbrev(), product.getId(), stdFile);	
					}
					image.setName(newFilename);
					sqlSession.getMapper(ImageMapper.class).update(image);
					sqlSession.commit();
									
				} catch (StorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		sqlSession.close();
				
	}
	
	
	
	public static void uploadThumbnailImagesForProducts(boolean active) {
		
		SqlSession sqlSession = RazorServer.openSession();
		
		int productCount = 0;
		

		// BEGIN: replace this by Product and Text lookup...create Image instances
		//specify the required version
		LocalDateTime version = new LocalDateTime(2005, 6, 1, 0, 0, 0);
		List<String> productIds = new ArrayList<String>(); 
		if(active) {
				productIds = sqlSession.getMapper(ProductMapper.class).
				getProductsWithAltIdAndAltPartyIdasNullbyVersion(
						version.getYear() + "-" + version.getMonthOfYear()
						+ "-" + version.getDayOfMonth()
						);
		}
		else {
			productIds = sqlSession.getMapper(ProductMapper.class).getInActiveProductsWithAltIdAndAltPartyIdasNull(null);
		}
		
		ArrayList<Image> images = new ArrayList<Image>();
		Map<String, List<Image>> productImages = new HashMap<String, List<Image>>();
		
		for(String productId : productIds)
		{
			NameId action = new NameId();
			action.setName("Product");
			action.setId(productId);
			
			ArrayList<Image> results = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(action);
			images = new ArrayList<Image>();
			
			for(Image result : results)
			{
				if(!result.isThumbnail())
					images.add(result);
				
			}
			if(images.size() > 0)
				productImages.put(productId, images);
		}
					
		

		
		UploadLegacyImagesToS3 instance = new UploadLegacyImagesToS3();
		
		logger.info("Product 58680 exists: " + (productImages.get("58680") != null ? "true" : "false"));
		logger.info("Fixing images for total products : " + productImages.size());
		
		
		
		for (Map.Entry<String, List<Image>> entry : productImages.entrySet()) {
			Product product = sqlSession.getMapper(ProductMapper.class).read(entry.getKey());
			if(product == null) {
				System.out.println("Product not found for image product id: " + entry.getKey());
				continue;
			}
			
			sqlSession = RazorServer.openSession();
			
			
			logger.info("Fixing images for product: " + entry.getKey() + " total images: " + entry.getValue().size());
			if(entry.getValue().size() > 0) {
				ExecutorService executor = Executors.newFixedThreadPool(20);
				Map<FutureTask<List<ImageMetaData>>, Image> futures = new HashMap<FutureTask<List<ImageMetaData>>, Image>();
				
				System.out.println("Uploading images for product: " + entry.getKey() + " total images: " + entry.getValue().size());
				for(Image image:entry.getValue()) {
					try {
						String filename = image.getName().substring(0, image.getName().indexOf(".")) + "Thumb." + image.getName().substring(image.getName().indexOf(".") + 1);
						if(!image.isThumbnail() && !storageService.isFileExist("raz", product.getSupplierid(), product.getId(), filename)) {
							image.setThumbnail(true);
							FutureTask<List<ImageMetaData>> task = new FutureTask<List<ImageMetaData>>(instance.new ThumbnailImageDownloadTask(image, product));
							futures.put(task, image);
							executor.execute(task);
						}
						else if(!image.isThumbnail() && storageService.isFileExist("raz", product.getSupplierid(), product.getId(), filename)) {
							image.setThumbnail(true);
							sqlSession.getMapper(ImageMapper.class).update(image);
						}
					}
					catch(StorageException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				int totalImagesUploaded;
				try {
					totalImagesUploaded = instance.uploadImagesToStorageAndUpdateImageRecords(sqlSession, product, futures);
					executor.shutdown();
					futures.clear();
					sqlSession.commit();
					System.out.println("Images downloaded for entity " + product.getId() + " : " + totalImagesUploaded);
					productCount++;
				} catch (StorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}		
					
		}
		sqlSession.commit();
			
				
		logger.info("Images upload for total products: " + productCount);
		
		
	}
	
	public static void uploadStandardImagesForProducts(String supplierId) {
		
		int productCount = 0;
		int offsetrows = 0;
		int numRows = 1000;
		SqlSession sqlSession = RazorServer.openSession();
		for(;;) {
			logger.info("At product row offset: " + offsetrows);
			
			// BEGIN: replace this by Product and Text lookup...create Image instances
			//specify the required version
			NameIdAction supplier = new NameIdAction();
			supplier.setId(supplierId);
			supplier.setOffsetrows(offsetrows);
			supplier.setNumrows(numRows);
			List<String> productIds = sqlSession.getMapper(ProductMapper.class).activeProductIdListBySupplier(supplier);
			if(productIds == null || productIds.size() <= 0) {
				break;
			}
			
			
			ArrayList<Image> images = new ArrayList<Image>();
			Map<String, List<Image>> productImages = new HashMap<String, List<Image>>();
			
			for(String productId : productIds) {
				NameId action = new NameId();
				action.setName("Product");
				action.setId(productId);
				
				ArrayList<Image> results = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(action);
				images = new ArrayList<Image>();
				
				for(Image result : results)
				{
					if(!result.isStandard())
						images.add(result);
					
				}
				if(images.size() > 0) {
					productImages.put(productId, images);
					productCount++;
				}
				
			}
			
			
//			sqlSession.close();
			
			logger.info("Product 58680 exists: " + (productImages.get("58680") != null ? "true" : "false"));
			logger.info("Fixing images for total products : " + productImages.size());
			
			if(!productImages.isEmpty()) {
				UploadLegacyImagesToS3.processProductImagesToUploadStandardImages(productImages, sqlSession);
			}
			
			offsetrows += numRows;
			
		}
		sqlSession.close();
		logger.info("Images upload for total products: " + productCount);
		
		
	}
	
	public static void processProductImagesToUploadStandardImages(Map<String, List<Image>> productImages, SqlSession sqlSession) {
		
		UploadLegacyImagesToS3 instance = new UploadLegacyImagesToS3();
		for (Map.Entry<String, List<Image>> entry : productImages.entrySet()) {
			Product product = sqlSession.getMapper(ProductMapper.class).read(entry.getKey());
			if(product == null) {
				System.out.println("Product not found for image product id: " + entry.getKey());
				continue;
			}
			
			
			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(product.getSupplierid());
			ImageService.setPMSPMAbbrevations(sqlSession, product, partner);
			
			
			logger.info("Fixing images for product: " + entry.getKey() + " total images: " + entry.getValue().size());
			if(entry.getValue().size() > 0) {
				ExecutorService executor = Executors.newFixedThreadPool(20);
				Map<FutureTask<List<ImageMetaData>>, Image> futures = new HashMap<FutureTask<List<ImageMetaData>>, Image>();
				
				System.out.println("Uploading images for product: " + entry.getKey() + " total images: " + entry.getValue().size());
				for(Image image:entry.getValue()) {
					try {
												
						String stdFile = image.getName().indexOf(".") > 0 
												? image.getName().substring(0, image.getName().indexOf(".")) + "Standard" + image.getName().substring(image.getName().indexOf("."))
												: image.getName()+ "Standard";
						String stdOldFile = image.getName().indexOf(".") > 0 
								? image.getName().substring(0, image.getName().indexOf(".")) + "Standard" + image.getName().substring(image.getName().indexOf("."))
										: image.getName()+ "Standard" + ".jpeg";
												
						if(!image.isStandard() && storageService.isFileExist(product.getPmsAbbrev(), product.getPmAbbrev(), product.getAltid(), stdOldFile)) {
							storageService.updateFilenameAndContentType(product.getPmsAbbrev(), product.getPmAbbrev(), product.getAltid(), stdOldFile, stdFile);	
							storageService.deleteImaget(product.getPmsAbbrev(), product.getPmAbbrev(), product.getAltid(), stdOldFile);	
							
							image.setStandard(true);
							sqlSession.getMapper(ImageMapper.class).update(image);
						}
						else if(!image.isStandard() && !storageService.isFileExist(product.getPmsAbbrev(), product.getPmAbbrev(), product.getAltid(), stdFile)) {
							image.setStandard(true);
							String imageBaseLocation = ImageService.getProductImageLocation(sqlSession, product);
							FutureTask<List<ImageMetaData>> task = new FutureTask<List<ImageMetaData>>(instance.new StandardImageDownloadTask(image, product, imageBaseLocation));
							futures.put(task, image);
							executor.execute(task);
						}
						else if(!image.isStandard() && storageService.isFileExist(product.getPmsAbbrev(), product.getPmAbbrev(), product.getAltid(), stdFile)) {
							image.setStandard(true);
							sqlSession.getMapper(ImageMapper.class).update(image);
						}
					}
					catch(StorageException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				int totalImagesUploaded;
				try {
					totalImagesUploaded = instance.uploadImagesToStorageAndUpdateImageRecords(sqlSession, product, futures);
					executor.shutdown();
					futures.clear();
					sqlSession.commit();
					System.out.println("Images downloaded for entity " + product.getId() + " : " + totalImagesUploaded);
				} catch (StorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}		
			
		}
//		sqlSession.commit();
	}
	
	public static void fixLegacyImagesThumbnailRecords() {
		
		SqlSession sqlSession = RazorServer.openSession();
		
		int productCount = 0;
		

		// BEGIN: replace this by Product and Text lookup...create Image instances
		//specify the required version
		LocalDateTime version = new LocalDateTime(2005, 6, 1, 0, 0, 0);
		ArrayList<String> productIds = sqlSession.getMapper(ProductMapper.class).
				getProductsWithAltIdAndAltPartyIdasNullbyVersion(
						version.getYear() + "-" + version.getMonthOfYear()
						+ "-" + version.getDayOfMonth()
						);
		
		ArrayList<Image> images = new ArrayList<Image>();
		Map<String, List<Image>> productImages = new HashMap<String, List<Image>>();
		
		for(String productId : productIds)
		{
			NameId action = new NameId();
			action.setName("Product");
			action.setId(productId);
			
			ArrayList<Image> results = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(action);
			images = new ArrayList<Image>();
			
			for(Image result : results)
			{
				if(!result.isThumbnail())
					images.add(result);
				
			}
			if(images.size() > 0)
				productImages.put(productId, images);
		}
					
		

		
		UploadLegacyImagesToS3 instance = new UploadLegacyImagesToS3();
		
		logger.info("Product 58680 exists: " + (productImages.get("58680") != null ? "true" : "false"));
		logger.info("Fixing images for total products : " + productImages.size());
		
		
		
		for (Map.Entry<String, List<Image>> entry : productImages.entrySet()) {
			Product product = sqlSession.getMapper(ProductMapper.class).read(entry.getKey());
			if(product == null) {
				System.out.println("Product not found for image product id: " + entry.getKey());
				continue;
			}
			if(!product.getSupplierid().equals("90640")) {
				sqlSession = RazorServer.openSession();
				
				
				logger.info("Fixing images for product: " + entry.getKey() + " total images: " + entry.getValue().size());
				if(entry.getValue().size() > 0) {
					int totalImagesUploaded;
					try {
						totalImagesUploaded = instance.uploadImagesRecordsForThumbnail(sqlSession, product, entry.getValue());
	
						sqlSession.commit();
						logger.info("Images fixed for product " + product.getId() + " total images: " + totalImagesUploaded);
						productCount++;
					} catch (StorageException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				logger.info("Not Fixing images for product: " + entry.getKey() + " supplier id: " + product.getSupplierid());
			}
			
		}
		sqlSession.commit();
			
				
		logger.info("Images upload for total products: " + productCount);
		
		
	}
	
	private int uploadImagesRecordsForThumbnail(SqlSession sqlSession, Product product, final List<Image> images) throws StorageException {
		System.out.println("Now checking images on the storage...");
		
		// upload images to the storage
		
		int count = 0;
		
		if(images.size() > 0) {

			// now update the image records
			for(Image image:images) {
				String filename = image.getName().substring(0, image.getName().indexOf(".")) + "Thumb." + image.getName().substring(image.getName().indexOf(".") + 1);
				if(!image.isThumbnail() && storageService.isFileExist("raz", product.getSupplierid(), product.getId(), filename)) {
					image.setThumbnail(true);
					sqlSession.getMapper(ImageMapper.class).update(image);
					count++;
				}
			}
		}
		
		return count;
	}
	
	private int uploadImagesToStorageAndCreateImageRecords(SqlSession sqlSession, Product product, final Map<FutureTask<List<ImageMetaData>>, Image> futures) throws StorageException {
		System.out.println("Now uploading images to the storage...");
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
						image.setType(Image.Type.Hosted);
						image.setLanguage("EN");
						images.add(image);
						imagesToBeUploaded.addAll(processedImages);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					count--;
					
			    }
			    
			}
			if(count <= 0) {
		    	break;
		    }
		    	
		}
		processedTasks.clear();
		
		// upload images to the storage
		StorageService storageService = StorageServiceFactory.getInstance();
		
		
		if(imagesToBeUploaded.size() > 0) {
			storageService.uploadImages("raz", product.getSupplierid(), product.getId(), imagesToBeUploaded);
			// now create the image records
			for(Image image:images) {
				sqlSession.getMapper(ImageMapper.class).create(image);
			}
		}
		
		return imagesToBeUploaded.size();
	}
	
	private int uploadImagesToStorageAndUpdateImageRecords(SqlSession sqlSession, Product product, final Map<FutureTask<List<ImageMetaData>>, Image> futures) throws StorageException {
		System.out.println("Now uploading thumbnail/standard images to the storage...");
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
							
						}
					
						images.add(image);
						imagesToBeUploaded.addAll(processedImages);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					count--;
					
			    }
			    
			}
			if(count <= 0) {
		    	break;
		    }
		    	
		}
		processedTasks.clear();
		
		// upload images to the storage
		StorageService storageService = StorageServiceFactory.getInstance();
		
		
		if(imagesToBeUploaded.size() > 0) {
//			storageService.uploadImages("raz", product.getSupplierid(), product.getId(), imagesToBeUploaded);
			storageService.uploadImages(product.getPmsAbbrev(), product.getPmAbbrev(), product.getAltid(), imagesToBeUploaded);
			// now create the image records
			for(Image image:images) {
				sqlSession.getMapper(ImageMapper.class).update(image);
			}
		}
		
		return imagesToBeUploaded.size();
	}
	
	
	class ThumbnailImageDownloadTask  implements Callable<List<ImageMetaData>> {
		
		Image imageData;
		boolean compressImage = true;
		Product product;
				
		public ThumbnailImageDownloadTask(Image imageData, Product product) {
			this.imageData = imageData;
			this.product = product;
		}
		
		public List<ImageMetaData> call() {
			List<ImageMetaData> imageMetaDataList = new ArrayList<ImageMetaData>();
			try {
				long startTime = System.currentTimeMillis();
				logger.debug("Downloading file named: " + imageData.getId());
				
				BufferedImage image;
				ImageInputStream iis;
				
				String urlString = RazorConfig.getS3ImageURL() + "raz/" + product.getSupplierid() + "/" + product.getId() + "/" + imageData.getName();
				URL url = new URL(urlString.replace(" ", "%20"));
				iis = ImageIO.createImageInputStream(url.openStream());
				
			
				
				Iterator<ImageReader> imgReaders = ImageIO.getImageReaders(iis);
				
				//get the first image reader from the collection
				ImageReader reader = (ImageReader) imgReaders.next();
				String formatName = reader.getFormatName();
				String formatExtension = imageData.getName().substring(imageData.getName().indexOf(".") + 1);
				 
				reader.setInput(iis);
				 
				//to ensure that the default read parameters are used when
				//decoding data from the stream
				ImageReadParam param = reader.getDefaultReadParam();

				//read the image
				image = reader.read(0, param);
				
				// dispose reader and close stream
				reader.dispose();
				iis.close();

				long endTime = System.currentTimeMillis();
				logger.debug("Total time taken to download image " + imageData.getId() + " : " + (endTime - startTime) + " miliseconds.");
				
				// get scaled images
				Map<String, byte[]> images;
				images = ImageUtils.getCompressedThumbnailImagesByteArray(imageData.getName(), image, formatName.toLowerCase(), formatExtension);
				
				for(Map.Entry<String, byte[]> entry:images.entrySet()) {
					imageMetaDataList.add(new ImageMetaData(entry.getKey(), 
																formatName, 
																compressImage ? StorageService.GZIP : null, 
																entry.getValue(), 
																ImageUtils.getImageType(entry.getKey())
															)
										);
				}
		
			} catch (FileNotFoundException e) {
				logger.error("Bad or Absent Image URL: " + imageData.getId() + " " + e.getMessage());
			} catch (MalformedURLException e) {
				logger.error("Bad or Absent Image URL: " + imageData.getId());
			} catch (IOException e) {
				logger.error("Bad or Absent Image URL: " + imageData.getId());
			} catch(Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage() + " Image URL: " + imageData.getId());
			}
			return imageMetaDataList;
		}
		
	
		
	}
	
	class StandardImageDownloadTask  implements Callable<List<ImageMetaData>> {
		
		Image imageData;
		boolean compressImage = true;
		Product product;
		String imageBaseLocation;
		
		public StandardImageDownloadTask(Image imageData, Product product, String imageBaseLcoation) {
			this.imageData = imageData;
			this.product = product;
			this.imageBaseLocation = imageBaseLcoation;
		}
		
		public List<ImageMetaData> call() {
			List<ImageMetaData> imageMetaDataList = new ArrayList<ImageMetaData>();
			try {
				long startTime = System.currentTimeMillis();
				logger.debug("Downloading file named: " + imageData.getId());
				
				BufferedImage image;
				ImageInputStream iis;
				String urlString = imageBaseLocation + imageData.getName();
				URL url = new URL(urlString.replace(" ", "%20"));
				iis = ImageIO.createImageInputStream(new GZIPInputStream(url.openStream()));
				
				
				
				Iterator<ImageReader> imgReaders = ImageIO.getImageReaders(iis);
				
				//get the first image reader from the collection
				ImageReader reader = (ImageReader) imgReaders.next();
				String formatName = reader.getFormatName();
				String formatExtension = imageData.getName().indexOf(".") > 0 ? imageData.getName().substring(imageData.getName().indexOf(".") + 1)
																				:"";
				
				reader.setInput(iis);
				
				//to ensure that the default read parameters are used when
				//decoding data from the stream
				ImageReadParam param = reader.getDefaultReadParam();
				
				//read the image
				image = reader.read(0, param);
				
				// dispose reader and close stream
				reader.dispose();
				iis.close();
				
				long endTime = System.currentTimeMillis();
				logger.debug("Total time taken to download image " + imageData.getId() + " : " + (endTime - startTime) + " miliseconds.");
				
				// get scaled images
				Map<String, byte[]> images;
				images = ImageUtils.getCompressedStandardImagesByteArray(imageData.getName(), image, formatName.toLowerCase(), formatExtension);
				
				if(!StringUtils.isEmpty(formatName)) {
					formatName = "image/" + formatName.toLowerCase();
				}
				
				for(Map.Entry<String, byte[]> entry:images.entrySet()) {
					imageMetaDataList.add(new ImageMetaData(entry.getKey(), 
							formatName, 
							compressImage ? StorageService.GZIP : null, 
									entry.getValue(), 
									ImageUtils.getImageType(entry.getKey())
							)
							);
				}
				
			} catch (FileNotFoundException e) {
				logger.error("Bad or Absent Image URL: " + imageData.getId() + " " + e.getMessage());
			} catch (MalformedURLException e) {
				logger.error("Bad or Absent Image URL: " + imageData.getId());
			} catch (IOException e) {
				logger.error("Bad or Absent Image URL: " + imageData.getId());
			} catch(Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage() + " Image URL: " + imageData.getId());
			}
			return imageMetaDataList;
		}
		
		
		
	}
	
	
	
	class ImageDownloadTask  implements Callable<List<ImageMetaData>> {
		
		Image imageData;
		boolean compressImage = false;
				
		public ImageDownloadTask(Image imageData) {
			this.imageData = imageData;
		}
		
		public List<ImageMetaData> call() {
			List<ImageMetaData> imageMetaDataList = new ArrayList<ImageMetaData>();
			try {
				long startTime = System.currentTimeMillis();
				System.out.println("Downloading file named: " + imageData.getName());
				
				
				ByteArrayOutputStream bOutputReg = new ByteArrayOutputStream();
				ByteArrayOutputStream bOutputThumb = new ByteArrayOutputStream();
						
				String urlString = RazorConfig.getImageURL() + imageData.getName();
				
				Map<String, BufferedImage> imageMap = null;
				byte[] rawImage = null;
				
				String formatName = null;
				BufferedImage image = null;
				
				
				// regular file
				try {
					imageMap = downloadImage(urlString);
					List<byte[]> rawImages;
					for (Map.Entry<String, BufferedImage> entry : imageMap.entrySet()) {
					    formatName = entry.getKey();
					    image = entry.getValue();
					    break;
					}
					
					ImageIO.write(image, formatName, bOutputReg);
					bOutputReg.close();
					rawImage = bOutputReg.toByteArray();
					
					imageMetaDataList.add(new ImageMetaData(imageData.getName(), 
							formatName, 
							compressImage ? StorageService.GZIP : null, 
							rawImage, 
							ImageUtils.getImageType(imageData.getName())
						)
					);
				}
				catch (IOException x) {
					System.out.println("Error saving full size image " + x.getMessage());
				}
				
				
				
				// thumb nail
				imageMap = null;
				try {
					String imageName = imageData.getName().substring(0, imageData.getName().indexOf(".")) + "Thumb." + (formatName.equalsIgnoreCase("jpeg") ? "jpg" : formatName);
					urlString = RazorConfig.getImageURL() + imageName;
					imageMap = downloadImage(urlString);
					
					rawImage = null;
					formatName = null;
					image = null;
					
					for (Map.Entry<String, BufferedImage> entry : imageMap.entrySet()) {
					    formatName = entry.getKey();
					    image = entry.getValue();
					    break;
					}
					
					
					ImageIO.write(image, formatName, bOutputThumb);
					bOutputThumb.close();
					rawImage = bOutputThumb.toByteArray();
					
					imageMetaDataList.add(new ImageMetaData(imageName, 
															formatName, 
															compressImage ? StorageService.GZIP : null, 
															rawImage, 
															ImageUtils.getImageType(imageName)
															)
											);
				}
				catch (IOException x) {
					System.out.println("Error saving Thumb size image " + x.getMessage());
				}
				
				

				long endTime = System.currentTimeMillis();
				System.out.println("Total time taken to download image " + imageData.getId() + " : " + (endTime - startTime) + " miliseconds.");
				
				
				
		
			} catch(Exception e) {
				System.err.println(e.getMessage() + " Image URL: " + imageData.getId());
			}
			return imageMetaDataList;
		}
		
		public Map<String, BufferedImage> downloadImage(String urlString) throws IOException {
			URL url = new URL(urlString.replace(" ", "%20"));
			ImageInputStream iis = ImageIO.createImageInputStream(url.openStream());
			Iterator<ImageReader> imgReaders = ImageIO.getImageReaders(iis);
			
			//get the first image reader from the collection
			ImageReader reader = (ImageReader) imgReaders.next();
			String formatName = reader.getFormatName();
			 
			reader.setInput(iis);
			 
			//to ensure that the default read parameters are used when
			//decoding data from the stream
			ImageReadParam param = reader.getDefaultReadParam();

			//read the image
			Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
			map.put(formatName, reader.read(0, param));
			
			// dispose reader and close stream
			reader.dispose();
			iis.close();
			
			return map;
		}
		
	}

}
