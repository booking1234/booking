package com.mybookingpal.server.api.junit;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ImageMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.Image;
import net.cbtltd.shared.Product;

import org.apache.ibatis.session.SqlSession;

import com.mybookingpal.config.RazorConfig;
import com.mybookingpal.service.StorageService;
import com.mybookingpal.service.StorageServiceFactory;
import com.mybookingpal.storage.data.ImageMetaData;
import com.mybookingpal.storage.exception.StorageException;
import com.mybookingpal.utils.ImageUtils;

public class UploadLegacyImagesToS3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SqlSession sqlSession = RazorServer.openSession();
		
		List<Image> images = sqlSession.getMapper(ImageMapper.class).readoldproducts();
				
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
		sqlSession.commit();
		
		System.out.println("Total products for which images will be uploaded: " + productImages.keySet().size());
		
		UploadLegacyImagesToS3 instance = new UploadLegacyImagesToS3();
		
		int productCount = 0;
		
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
	
	private int uploadImagesToStorageAndCreateImageRecords(SqlSession sqlSession, Product product, final Map<FutureTask<List<ImageMetaData>>, Image> futures) throws StorageException {
		System.out.println("Now uploading images to the storage...");
		List<ImageMetaData> imagesToBeUploaded = new ArrayList<ImageMetaData>();
		List<FutureTask<List<ImageMetaData>>> processedTasks = new ArrayList<FutureTask<List<ImageMetaData>>>();
		int count = futures.size();
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
					
			    }
			    
			}
			if(count <= 0) {
		    	break;
		    }
		    	
		}
		processedTasks.clear();
		
		// upload images to the storage
		StorageService storageService = StorageServiceFactory.getInstance();
		storageService.uploadImages("raz", product.getSupplierid(), product.getId(), imagesToBeUploaded);
		return imagesToBeUploaded.size();
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
