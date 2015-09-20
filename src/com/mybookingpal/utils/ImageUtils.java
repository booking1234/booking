package com.mybookingpal.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

import com.mybookingpal.exceptions.CompressionException;
import com.mybookingpal.exceptions.ImageConversionException;
import com.mybookingpal.storage.data.ImageMetaData;

/**
 * @author cshah
 *
 */
public class ImageUtils {
	
	public static final String 	THUMBNAIL_PIXELS 			= "Thumbnail Pixels";
	public static final String 	THUMBNAIL_PIXELS_POSTFIX	= "Thumbnail.";
	public static final String 	FULLSIZE_PIXELS 			= "Fullsize Pixels";
	public static final String 	STANDARD_PIXELS_POSTFIX		= "Standard.";
	public static final int    	THUMBNAIL_PIXELS_VALUE 		= 100;
	public static final int 	STANDARD_PIXELS_VALUE 		= 500;
	public static final int 	LOGO_PIXELS_VALUE 			= 100; //high
	public static final int 	MAX_BLOB_SIZE 				= 200000;
	public static final int 	MAX_TEXT_SIZE 				= 20000;
	
	public static final String 	IMAGE 						= "Image";

	
	public static final String 	AUDIO_OGG 					= ".ogg"; //qualifies text id
	public static final String 	AUDIO_MP3 					= ".mp3";
	public static final String 	AUDIO_WAV 					= ".wav";
	
	public static final String 	VIDEO_OGG 					= ".ogg";
	public static final String 	VIDEO_MP4 					= ".mp4";
	public static final String 	VIDEO_WEBM 					= ".webm";
	
	public static final String 	FULLSIZE_JPG 				= ".jpg";
	public static final String 	THUMBNAIL_JPG 				= "Thumb.jpg";
	public static final String 	JPG_FILE_TYPE 				= "jpg";
	public static final String 	JPEG_FILE_TYPE 				= "jpeg";
	
	
	/*
	 * Creates compressed thumbnail, standard and full size images from the uploaded image. The full size image will not be rescaled.
	 * 
	 * @param fn the server file name.
	 * @param image BufferedImage Uploaded image
	 * @param params the key-value map of parameters from the submitted form.
	 */
	public static Map<String, byte[]> getCompressedImagesByteArrayInThreeSizes(String fn, BufferedImage image, String formatName) throws CompressionException {
		Map<String, byte[]> images = new HashMap<String, byte[]>();
		ByteArrayOutputStream bOutputReg = new ByteArrayOutputStream();
		ByteArrayOutputStream bOutputStd = new ByteArrayOutputStream();
		ByteArrayOutputStream bOutputThumb = new ByteArrayOutputStream();
		try {
			GZIPOutputStream zipStreamReg = new GZIPOutputStream(bOutputReg);
			GZIPOutputStream zipStreamStd = new GZIPOutputStream(bOutputStd);
			GZIPOutputStream zipStreamThumb = new GZIPOutputStream(bOutputThumb);
			
			// compress regular file
//			String fullsizeFile = fn.substring(0,fn.lastIndexOf('.')) + "." + formatName;
			String fullsizeFile = fn;
			try {
				ImageIO.write(image, formatName, zipStreamReg);
				zipStreamReg.close();
				bOutputReg.close();
				images.put(fullsizeFile, bOutputReg.toByteArray());
			}
			catch (IOException x) {
				throw new RuntimeException("Error saving full size image " + x.getMessage());
			}
			
			// rescale the image to standard size and
			// compress it.
			ImageIcon standardImage = new ImageIcon(image.getScaledInstance(-1, ImageUtils.STANDARD_PIXELS_VALUE, Image.SCALE_SMOOTH));
			String standardSizeFile = ImageUtils.getStandardFilename(fn);
										
			BufferedImage standardBufferedImage = new BufferedImage(
					standardImage.getIconWidth(),
					standardImage.getIconHeight(),
					BufferedImage.TYPE_INT_RGB
			);
			Graphics standardGraphics = standardBufferedImage.getGraphics();
			standardGraphics.drawImage(standardImage.getImage(), 0, 0, null);
				
			try {
				ImageIO.write(standardBufferedImage, formatName, zipStreamStd);
				zipStreamStd.close();
				bOutputStd.close();
				images.put(standardSizeFile, bOutputStd.toByteArray());
			}
			catch (IOException x) {
				throw new RuntimeException("Error saving standard size image " + x.getMessage());
			}
			standardGraphics.dispose();
			
			// rescale the image to thumb nail size and
			// compress it.
			ImageIcon thumbnailImage = new ImageIcon(image.getScaledInstance(-1, ImageUtils.THUMBNAIL_PIXELS_VALUE, Image.SCALE_SMOOTH));
			String thumbnailFile = ImageUtils.getThumbNailFilename(fn);
			
			BufferedImage thumbnailBufferedImage = new BufferedImage(
					thumbnailImage.getIconWidth(),
					thumbnailImage.getIconHeight(),
					BufferedImage.TYPE_INT_RGB
			);
			Graphics thumbnailGraphics = thumbnailBufferedImage.getGraphics();
			thumbnailGraphics.drawImage(thumbnailImage.getImage(), 0, 0, null);
			try {
				ImageIO.write(thumbnailBufferedImage, formatName, zipStreamThumb);
				zipStreamThumb.close();
				bOutputThumb.close();
				images.put(thumbnailFile, bOutputThumb.toByteArray());
			}
			catch (IOException x) {
				throw new RuntimeException("Error saving thumbnail image " + x.getMessage());
			}
			thumbnailGraphics.dispose();

		} catch (IOException e) {
			throw new CompressionException("Error creating gzip streams.", e);
		}
		return images;
	}
	
	/*
	 * Creates compressed thumbnail, 
	 * 
	 * @param fn the server file name.
	 * @param image BufferedImage Uploaded image
	 * @param params the key-value map of parameters from the submitted form.
	 */
	public static Map<String, byte[]> getCompressedThumbnailImagesByteArray(String fn, BufferedImage image, String formatName, String formatExtension) throws CompressionException {
		Map<String, byte[]> images = new HashMap<String, byte[]>();

		ByteArrayOutputStream bOutputThumb = new ByteArrayOutputStream();
		try {
			GZIPOutputStream zipStreamThumb = new GZIPOutputStream(bOutputThumb);
			
			// rescale the image to thumb nail size and
			// compress it.
			ImageIcon thumbnailImage = new ImageIcon(image.getScaledInstance(-1, ImageUtils.THUMBNAIL_PIXELS_VALUE, Image.SCALE_SMOOTH));
			String thumbnailFile = fn.lastIndexOf('.') > 0 
									? (fn.substring(0, fn.lastIndexOf('.')) + ImageMetaData.Size.Thumb.name() + "." + formatExtension) 
									: (fn + ImageMetaData.Size.Thumb.name() + "." + formatExtension);
			
			BufferedImage thumbnailBufferedImage = new BufferedImage(
					thumbnailImage.getIconWidth(),
					thumbnailImage.getIconHeight(),
					BufferedImage.TYPE_INT_RGB
			);
			Graphics thumbnailGraphics = thumbnailBufferedImage.getGraphics();
			thumbnailGraphics.drawImage(thumbnailImage.getImage(), 0, 0, null);
			try {
				ImageIO.write(thumbnailBufferedImage, formatName, zipStreamThumb);
				zipStreamThumb.close();
				bOutputThumb.close();
				images.put(thumbnailFile, bOutputThumb.toByteArray());
			}
			catch (IOException x) {
				throw new RuntimeException("Error saving thumbnail image " + x.getMessage());
			}
			thumbnailGraphics.dispose();

		} catch (IOException e) {
			throw new CompressionException("Error creating gzip streams.", e);
		}
		return images;
	}
	
	/*
	 * Creates compressed Standard, 
	 * 
	 * @param fn the server file name.
	 * @param image BufferedImage Uploaded image
	 * @param params the key-value map of parameters from the submitted form.
	 */
	public static Map<String, byte[]> getCompressedStandardImagesByteArray(String fn, BufferedImage image, String formatName, String formatExtension) throws CompressionException {
		Map<String, byte[]> images = new HashMap<String, byte[]>();
		
		ByteArrayOutputStream bOutputStd = new ByteArrayOutputStream();
		try {
			GZIPOutputStream zipStreamStd = new GZIPOutputStream(bOutputStd);
			
			// rescale the image to thumb nail size and
			// compress it.
			ImageIcon standardImage = new ImageIcon(image.getScaledInstance(-1, ImageUtils.STANDARD_PIXELS_VALUE, Image.SCALE_SMOOTH));
			String standardFile = fn.lastIndexOf('.') > 0 
					? (fn.substring(0, fn.lastIndexOf('.')) + ImageMetaData.Size.Standard.name() + "." + formatExtension) 
							: (fn + ImageMetaData.Size.Standard.name());
					
					BufferedImage standardBufferedImage = new BufferedImage(
							standardImage.getIconWidth(),
							standardImage.getIconHeight(),
							BufferedImage.TYPE_INT_RGB
							);
					Graphics standardGraphics = standardBufferedImage.getGraphics();
					standardGraphics.drawImage(standardImage.getImage(), 0, 0, null);
					try {
						ImageIO.write(standardBufferedImage, formatName, zipStreamStd);
						zipStreamStd.close();
						bOutputStd.close();
						images.put(standardFile, bOutputStd.toByteArray());
					}
					catch (IOException x) {
						throw new RuntimeException("Error saving thumbnail image " + x.getMessage());
					}
					standardGraphics.dispose();
					
		} catch (IOException e) {
			throw new CompressionException("Error creating gzip streams.", e);
		}
		return images;
	}
	
	/*
	 * Creates compressed thumbnail, standard and full size images from the uploaded image. The full size image will not be rescaled.
	 * 
	 * @param fn the server file name.
	 * @param image BufferedImage Uploaded image
	 * @param params the key-value map of parameters from the submitted form.
	 */
	public static Map<String, byte[]> getImagesByteArrayInThreeSizes(String fn, BufferedImage image, String formatName) throws CompressionException {
		Map<String, byte[]> images = new HashMap<String, byte[]>();
		ByteArrayOutputStream bOutputReg = new ByteArrayOutputStream();
		ByteArrayOutputStream bOutputStd = new ByteArrayOutputStream();
		ByteArrayOutputStream bOutputThumb = new ByteArrayOutputStream();

		// regular file
//		String fullsizeFile = fn.substring(0,fn.lastIndexOf('.')) + "." + formatName;
		String fullsizeFile = fn;
		try {
			ImageIO.write(image, formatName, bOutputReg);
			bOutputReg.close();
			images.put(fullsizeFile, bOutputReg.toByteArray());
		}
		catch (IOException x) {
			throw new RuntimeException("Error saving full size image " + x.getMessage());
		}
		
		// rescale the image to standard size and
		// compress it.
		ImageIcon standardImage = new ImageIcon(image.getScaledInstance(-1, ImageUtils.STANDARD_PIXELS_VALUE, Image.SCALE_SMOOTH));
//		String standardSizeFile = fn.substring(0, fn.lastIndexOf('.') > 0 ? fn.lastIndexOf('.') : fn.length() + 1) + ImageMetaData.Size.Standard.name() + "." + formatName;
		String standardSizeFile = ImageUtils.getStandardFilename(fn);
		BufferedImage standardBufferedImage = new BufferedImage(
				standardImage.getIconWidth(),
				standardImage.getIconHeight(),
				BufferedImage.TYPE_INT_RGB
		);
		Graphics standardGraphics = standardBufferedImage.getGraphics();
		standardGraphics.drawImage(standardImage.getImage(), 0, 0, null);
		try {
			ImageIO.write(standardBufferedImage, formatName, bOutputStd);
			bOutputStd.close();
			images.put(standardSizeFile, bOutputStd.toByteArray());
		}
		catch (IOException x) {
			throw new RuntimeException("Error saving standard size image " + x.getMessage());
		}
		standardGraphics.dispose();
		
		// rescale the image to thumb nail size and
		// compress it.
		ImageIcon thumbnailImage = new ImageIcon(image.getScaledInstance(-1, ImageUtils.THUMBNAIL_PIXELS_VALUE, Image.SCALE_SMOOTH));
		String thumbnailFile = ImageUtils.getThumbNailFilename(fn);
		
		BufferedImage thumbnailBufferedImage = new BufferedImage(
				thumbnailImage.getIconWidth(),
				thumbnailImage.getIconHeight(),
				BufferedImage.TYPE_INT_RGB
		);
		Graphics thumbnailGraphics = thumbnailBufferedImage.getGraphics();
		thumbnailGraphics.drawImage(thumbnailImage.getImage(), 0, 0, null);
		try {
			ImageIO.write(thumbnailBufferedImage, formatName, bOutputThumb);
			bOutputThumb.close();
			images.put(thumbnailFile, bOutputThumb.toByteArray());
		}
		catch (IOException x) {
			throw new RuntimeException("Error saving thumbnail image " + x.getMessage());
		}
		thumbnailGraphics.dispose();
		

		return images;
	}
	
	/*
	 * Creates compressed thumbnail, standard and full size images from the uploaded image. The full size image will not be rescaled.
	 * 
	 * @param fn the server file name.
	 * @param image byte[] Uploaded image
	 * @param params the key-value map of parameters from the submitted form.
	 */
	public static Map<String, byte[]> getCompressedImagesByteArrayInThreeSizes(String fn, byte[] imageInByte, String formatName) throws CompressionException, ImageConversionException {
		
		// convert byte array to BufferedImage
		InputStream in = new ByteArrayInputStream(imageInByte);
		BufferedImage image;
		try {
			image = ImageIO.read(in);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			throw new ImageConversionException("Failed to convert bytes into Image.", e1);
		}
		
		Map<String, byte[]> images = new HashMap<String, byte[]>();
		ByteArrayOutputStream bOutputReg = new ByteArrayOutputStream();
		ByteArrayOutputStream bOutputStd = new ByteArrayOutputStream();
		ByteArrayOutputStream bOutputThumb = new ByteArrayOutputStream();
		try {
			GZIPOutputStream zipStreamReg = new GZIPOutputStream(bOutputReg);
			GZIPOutputStream zipStreamStd = new GZIPOutputStream(bOutputStd);
			GZIPOutputStream zipStreamThumb = new GZIPOutputStream(bOutputThumb);
			
			// compress regular file
//			String fullsizeFile = fn.substring(0,fn.lastIndexOf('.')) + "." + formatName;
			String fullsizeFile = fn;
			try {
				ImageIO.write(image, formatName, zipStreamReg);
				zipStreamReg.close();
				bOutputReg.close();
				images.put(fullsizeFile, bOutputReg.toByteArray());
			}
			catch (IOException x) {
				throw new RuntimeException("Error saving full size image " + x.getMessage());
			}
			
			// rescale the image to standard size and
			// compress it.
			ImageIcon standardImage = new ImageIcon(image.getScaledInstance(-1, ImageUtils.STANDARD_PIXELS_VALUE, Image.SCALE_SMOOTH));
			String standardSizeFile = ImageUtils.getStandardFilename(fn);
			BufferedImage standardBufferedImage = new BufferedImage(
					standardImage.getIconWidth(),
					standardImage.getIconHeight(),
					BufferedImage.TYPE_INT_RGB
			);
			Graphics standardGraphics = standardBufferedImage.getGraphics();
			standardGraphics.drawImage(standardImage.getImage(), 0, 0, null);
				
			try {
				ImageIO.write(standardBufferedImage, formatName, zipStreamStd);
				zipStreamStd.close();
				bOutputStd.close();
				images.put(standardSizeFile, bOutputStd.toByteArray());
			}
			catch (IOException x) {
				throw new RuntimeException("Error saving standard size image " + x.getMessage());
			}
			
			// rescale the image to thumb nail size and
			// compress it.
			ImageIcon thumbnailImage = new ImageIcon(image.getScaledInstance(-1, ImageUtils.THUMBNAIL_PIXELS_VALUE, Image.SCALE_SMOOTH));
			String thumbnailFile = ImageUtils.getThumbNailFilename(fn);
			
			BufferedImage thumbnailBufferedImage = new BufferedImage(
					thumbnailImage.getIconWidth(),
					thumbnailImage.getIconHeight(),
					BufferedImage.TYPE_INT_RGB
			);
			Graphics thumbnailGraphics = thumbnailBufferedImage.getGraphics();
			thumbnailGraphics.drawImage(thumbnailImage.getImage(), 0, 0, null);
			try {
				ImageIO.write(thumbnailBufferedImage, formatName, zipStreamThumb);
				zipStreamThumb.close();
				bOutputThumb.close();
				images.put(thumbnailFile, bOutputThumb.toByteArray());
			}
			catch (IOException x) {
				throw new RuntimeException("Error saving thumbnail image " + x.getMessage());
			}

		} catch (IOException e) {
			throw new CompressionException("Error creating gzip streams.", e);
		}
		return images;
	}
	
	/*
	 * Creates compressed thumbnail, standard and full size images from the uploaded image. The full size image will not be rescaled.
	 * 
	 * @param fn the server file name.
	 * @param image byte[] Uploaded image
	 * @param params the key-value map of parameters from the submitted form.
	 */
	public static Map<String, byte[]> getImagesByteArrayInThreeSizes(String fn, byte[] imageInByte, String formatName) throws CompressionException, ImageConversionException {
		
		// convert byte array to BufferedImage
		InputStream in = new ByteArrayInputStream(imageInByte);
		BufferedImage image;
		try {
			image = ImageIO.read(in);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			throw new ImageConversionException("Failed to convert bytes into Image.", e1);
		}
		
		Map<String, byte[]> images = new HashMap<String, byte[]>();
		ByteArrayOutputStream bOutputStd = new ByteArrayOutputStream();
		ByteArrayOutputStream bOutputThumb = new ByteArrayOutputStream();
	
					
		// store regular file
//		String fullsizeFile = fn.substring(0,fn.lastIndexOf('.')) + "." + formatName;
		String fullsizeFile = fn;
		images.put(fullsizeFile, imageInByte);
		
		
		// rescale the image to standard size
		ImageIcon standardImage = new ImageIcon(image.getScaledInstance(-1, ImageUtils.STANDARD_PIXELS_VALUE, Image.SCALE_SMOOTH));
		String standardSizeFile = ImageUtils.getStandardFilename(fn);
		BufferedImage standardBufferedImage = new BufferedImage(
				standardImage.getIconWidth(),
				standardImage.getIconHeight(),
				BufferedImage.TYPE_INT_RGB
		);
		Graphics standardGraphics = standardBufferedImage.getGraphics();
		standardGraphics.drawImage(standardImage.getImage(), 0, 0, null);
			
		try {
			ImageIO.write(standardBufferedImage, formatName, bOutputStd);
			bOutputStd.close();
			images.put(standardSizeFile, bOutputStd.toByteArray());
		}
		catch (IOException x) {
			throw new RuntimeException("Error saving standard size image " + x.getMessage());
		}
		
		// rescale the image to thumb nail size
		ImageIcon thumbnailImage = new ImageIcon(image.getScaledInstance(-1, ImageUtils.THUMBNAIL_PIXELS_VALUE, Image.SCALE_SMOOTH));
		String thumbnailFile = ImageUtils.getThumbNailFilename(fn);
		
		BufferedImage thumbnailBufferedImage = new BufferedImage(
				thumbnailImage.getIconWidth(),
				thumbnailImage.getIconHeight(),
				BufferedImage.TYPE_INT_RGB
		);
		Graphics thumbnailGraphics = thumbnailBufferedImage.getGraphics();
		thumbnailGraphics.drawImage(thumbnailImage.getImage(), 0, 0, null);
		try {
			ImageIO.write(thumbnailBufferedImage, formatName, bOutputThumb);
			bOutputThumb.close();
			images.put(thumbnailFile, bOutputThumb.toByteArray());
		}
		catch (IOException x) {
			throw new RuntimeException("Error saving thumbnail image " + x.getMessage());
		}

		return images;
	}
	
	public static String getThumbNailFilename(String fn) {
		return fn.lastIndexOf('.') > 0 
				? (fn.substring(0, fn.lastIndexOf('.')) + ImageMetaData.Size.Thumb.name() + fn.substring(fn.lastIndexOf("."))) 
				: (fn + ImageMetaData.Size.Thumb.name());
	}
	
	public static String getStandardFilename(String fn) {
		return fn.lastIndexOf('.') > 0 
				? (fn.substring(0, fn.lastIndexOf('.')) + ImageMetaData.Size.Standard.name() + fn.substring(fn.lastIndexOf("."))) 
						: (fn + ImageMetaData.Size.Standard.name());
	}
	
	/**
	 * Determine size of the image from the filename
	 * @param filename
	 * @return ImageMetaData.Size
	 */
	public static ImageMetaData.Size getImageType(String filename) {
		ImageMetaData.Size size = ImageMetaData.Size.Regular;
		if(filename.contains(ImageMetaData.Size.Thumb.name())) {
			size = ImageMetaData.Size.Thumb;
		}
		else if(filename.contains(ImageMetaData.Size.Standard.name())) {
			size = ImageMetaData.Size.Standard;
		}
		return size;
	}
	
	
	/**
	 * @param urlString
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage downloadImage(String urlString, String format) throws IOException {
		URL url = new URL(urlString.replace(" ", "%20"));
		ImageInputStream iis = ImageIO.createImageInputStream(url.openStream());
		Iterator<ImageReader> imgReaders = ImageIO.getImageReaders(iis);
		
		//get the first image reader from the collection
		ImageReader reader = (ImageReader) imgReaders.next();
		format = reader.getFormatName();
		 
		reader.setInput(iis);
		 
		//to ensure that the default read parameters are used when
		//decoding data from the stream
		ImageReadParam param = reader.getDefaultReadParam();

		//read the image
		BufferedImage image = reader.read(0, param);
		
		// dispose reader and close stream
		reader.dispose();
		iis.close();
		
		return image;
	}
	
}
