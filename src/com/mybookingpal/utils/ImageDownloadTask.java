package com.mybookingpal.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import net.cbtltd.shared.NameId;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.mybookingpal.config.RazorConfig;
import com.mybookingpal.service.StorageService;
import com.mybookingpal.storage.data.ImageMetaData;


public class ImageDownloadTask  implements Callable<List<ImageMetaData>> {
	
	private static final Logger LOG = Logger.getLogger(ImageDownloadTask.class.getName());
	
	String filename;
	NameId imageData;
	boolean compressImage;
	String localFolder;
	
	public ImageDownloadTask(NameId image, String filename, boolean compressImage, String localFolder) {
		this.imageData = image;
		this.filename = filename;
		this.compressImage = compressImage;
		this.localFolder = localFolder;
	}
	
	public List<ImageMetaData> call() {
		List<ImageMetaData> imageMetaDataList = new ArrayList<ImageMetaData>();
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("Downloading file named: " + imageData.getId());
			
			BufferedImage image;
			ImageInputStream iis;
			if(StringUtils.isEmpty(localFolder)) {
				URL url = new URL(imageData.getId().replace(" ", "%20"));
				iis = ImageIO.createImageInputStream(url.openStream());
			}
			else {
				String picture = localFolder + filename;
				iis = ImageIO.createImageInputStream(new File(picture));
			}
			
			Iterator<ImageReader> imgReaders = ImageIO.getImageReaders(iis);
			
			//get the first image reader from the collection
			ImageReader reader = (ImageReader) imgReaders.next();
			String formatName = reader.getFormatName();
						 
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
			LOG.debug("Total time taken to download image " + imageData.getId() + " : " + (endTime - startTime) + " miliseconds.");
			
			// get scaled images
			Map<String, byte[]> images;
			if(compressImage)
				images = ImageUtils.getCompressedImagesByteArrayInThreeSizes(filename, image, formatName.toLowerCase());
			else
				images = ImageUtils.getImagesByteArrayInThreeSizes(filename, image, formatName.toLowerCase());
			
			
			if(!StringUtils.isEmpty(formatName)) {
				formatName = RazorConfig.getMimeType(formatName.toLowerCase());
			}
			if(StringUtils.isEmpty(formatName)) {
				formatName = "";
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
			LOG.error("Bad or Absent Image URL: " + imageData.getId() + " " + e.getMessage());
		} catch (MalformedURLException e) {
			LOG.error("Bad or Absent Image URL: " + imageData.getId());
		} catch (IOException e) {
			LOG.error("Bad or Absent Image URL: " + imageData.getId());
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage() + " Image URL: " + imageData.getId());
		}
		return imageMetaDataList;
	}
	
}
