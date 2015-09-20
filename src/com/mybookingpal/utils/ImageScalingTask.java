package com.mybookingpal.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.mybookingpal.config.RazorConfig;
import com.mybookingpal.service.StorageService;
import com.mybookingpal.storage.data.ImageMetaData;


public class ImageScalingTask  implements Callable<List<ImageMetaData>> {
	
	private static final Logger LOG = Logger.getLogger(ImageScalingTask.class.getName());
	
	ImageMetaData imageData;
	boolean compress;
	
	
	public ImageScalingTask(ImageMetaData image, boolean compress) {
		this.imageData = image;
		this.compress = compress;
	}
	
	public List<ImageMetaData> call() {
		List<ImageMetaData> imageMetaDataList = new ArrayList<ImageMetaData>();
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("Scaling file named: " + imageData.getName());
			
			if(StringUtils.isEmpty(imageData.getFormat())) {
				// let's assume jpeg are being uploaded
				imageData.setFormat("JPEG");
			}
			else if(imageData.getFormat().contains("image/")) {
				imageData.setFormat(RazorConfig.getMimeExtension(imageData.getFormat()));
			}
 			
			// get scaled images
			Map<String, byte[]> images;
			if(compress)
				images = ImageUtils.getCompressedImagesByteArrayInThreeSizes(imageData.getName(), imageData.getData(), imageData.getFormat());
			else
				images = ImageUtils.getImagesByteArrayInThreeSizes(imageData.getName(), imageData.getData(), imageData.getFormat());
			
			String contentType = RazorConfig.getMimeType(imageData.getFormat());
			
			for(Map.Entry<String, byte[]> entry:images.entrySet()) {
				imageMetaDataList.add(new ImageMetaData(entry.getKey(), 
															contentType, 
															compress ? StorageService.GZIP : null, 
															entry.getValue(), 
															ImageUtils.getImageType(entry.getKey())
														)
									);
			}
			
			long endTime = System.currentTimeMillis();
			LOG.debug("Total time taken to scale images " + imageData.getName() + " : " + (endTime - startTime) + " miliseconds.");

		} catch(Exception e) {
			LOG.error(e.getMessage() + " Image URL: " + imageData.getName());
		}
		return imageMetaDataList;
	}
	
}
