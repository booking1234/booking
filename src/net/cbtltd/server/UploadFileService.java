/**
 * @author	abookingnet
 * @see		License at http://razorpms.com/razor/License.html
 * @version	2.00
 */
package net.cbtltd.server;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.server.project.PartyIds;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.server.config.RazorHostsConfig;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.mybookingpal.config.RazorConfig;
import com.mybookingpal.server.ImageService;

/**
 * The Class UploadFileService extends HttpServlet to upload files from client to server.
 */
public class UploadFileService
extends HttpServlet
implements Servlet {
	private static final long serialVersionUID = 8305367618713715640L;
	private static final Logger LOG = Logger.getLogger(UploadFileService.class.getName());
	private static final String FULLSIZE_JPEG = "jpeg";

	// PMS IDs that have new structure to images like:
	// RazorServer.ROOT_DIRECTORY + HasUrls.PICTURES_DIRECTORY + pms + File.separator + pm + File.separator + name + File.separator;
	private static final String[] PMS_IMAGES = {PartyIds.PARTY_SUMMITCOVE_ID, PartyIds.PARTY_INTERHOME_ID};
	public static final String[] STRUCTURED_PMS_IMAGES = (String[]) ArrayUtils.addAll(PartyIds.NEXTPAX_IDS, PMS_IMAGES);
	
	
	private static final String JPEG_FILE_TYPE = "jpg";
	
	private static final Magic parser = new Magic() ;
	
	

	/**
	 * Handles upload file requests is in a page submitted by a HTTP POST method.
	 * Form field values are extracted into a parameter list to set an associated Text instance.
	 * 'File' type merely saves file and creates associated db record having code = file name.
	 * Files may be rendered by reference in a browser if the browser is capable of the file type.
	 * 'Image' type creates and saves thumbnail and full size jpg images and creates associated
	 * text record having code = full size file name. The images may be viewed by reference in a browser.
	 * 'Blob' type saves file and creates associated text instance having code = full size file name
	 * and a byte array data value equal to the binary contents of the file.
	 *
	 * @param request the HTTP upload request.
	 * @param response the HTTP response.
	 * @throws ServletException signals that an HTTP exception has occurred.
	 * @throws IOException signals that an I/O exception has occurred.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		ServletRequestContext ctx = new ServletRequestContext(request);

		if (ServletFileUpload.isMultipartContent(ctx) == false){
			sendResponse(response, new FormResponse(
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
			"The servlet can only handle multipart requests."));
			return;
		}

		LOG.debug("UploadFileService doPost request " + request);

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		LOG.debug("\n doPost upload " + upload);
		
		SqlSession sqlSession = RazorServer.openSession();
		try {
			HashMap<String, String> params = new HashMap<String, String>();
			for (FileItem item : (List<FileItem>)upload.parseRequest(request)) {
				if (item.isFormField()){ // add for field value to parameter list
					String param = item.getFieldName();
					String value = item.getString();
					params.put(param, value);
				}
				else if (item.getSize() > 0) { 									// process uploaded file
//					String fn = RazorServer.ROOT_DIRECTORY + item.getFieldName(); 	// input file path
					String fn = RazorConfig.getImageURL() + item.getFieldName(); 	// input file path
					LOG.debug("doPost fn " + fn);
					byte[] data = item.get();
					String mimeType = item.getContentType();
					
					String productId = item.getFieldName();
					Pattern p = Pattern.compile("\\d+");
			        Matcher m = p.matcher(productId);
			        while(m.find()){
			        	productId = m.group();
			        	LOG.debug("Image uploaded for Product ID: " + productId);
			        	break;
			        }
					
			        // TO DO - convert content type to mime..also check if uploaded type is image
			        
					// getMagicMatch accepts Files or byte[],
					// which is nice if you want to test streams
					MagicMatch match = null;
					try {
						match = parser.getMagicMatch(data, false);
						LOG.debug("Mime type of image: " + match.getMimeType()) ;
					} catch (MagicParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MagicMatchNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MagicException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(match != null) {
						mimeType = match.getMimeType();
					}
					
					// image processor logic needs to know about the format of the image
					String contentType = RazorConfig.getMimeExtension(mimeType);
			        
					if(StringUtils.isNotEmpty(contentType)) {
						ImageService.uploadImages(sqlSession, productId, item.getFieldName(), params.get(Text.FILE_NOTES), data, contentType);
						LOG.debug("doPost commit params " + params);
						sqlSession.commit();
					}
					else {
						// unknown content/mime type...do not upload the file
						sendResponse(response, new FormResponse(HttpServletResponse.SC_BAD_REQUEST, "File type - " + contentType + " is not supported"));
					}
//					File file = new File(fn); 									// output file name
//					File tempf = File.createTempFile(Text.TEMP_FILE, "");
//					item.write(tempf);
//					file.delete();
//					tempf.renameTo(file);
//					int fullsizepixels = Integer.valueOf(params.get(Text.FULLSIZE_PIXELS));
//					if (fullsizepixels <= 0) {fullsizepixels = Text.FULLSIZE_PIXELS_VALUE;}
//					int thumbnailpixels = Integer.valueOf(params.get(Text.THUMBNAIL_PIXELS));
//					if (thumbnailpixels <= 0) {thumbnailpixels = Text.THUMBNAIL_PIXELS_VALUE;}
//
//					setText(sqlSession, file, fn, params.get(Text.FILE_NAME), params.get(Text.FILE_TYPE), params.get(Text.FILE_NOTES), Language.EN, fullsizepixels, thumbnailpixels);
				}
			}
			sendResponse(response, new FormResponse(HttpServletResponse.SC_ACCEPTED, "OK"));
		} 
		catch (Throwable x) {
			sqlSession.rollback();
			sendResponse(response, new FormResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, x.getMessage()));
			LOG.error("doPost error " + x.getMessage());
		}
		finally {sqlSession.close();}
	}

	/**
	 * Copy images.
	 *
	 * @param sqlSession the current SQL session.
	 * @param entitytype the entity type to be used for the image upload.
	 * @param entityid the entity ID to be used for the image upload.
	 * @param language the language of the uploaded text.
	 * @param images the list of image URLs to be uploaded.
	 * @throws Throwable the exception thrown on error.
	 */
	public static void copyImages(SqlSession sqlSession, NameId.Type entitytype, String entityid, String language, ArrayList<NameId> images) throws Throwable {
		uploadImages(sqlSession, entitytype, entityid, language, images, true, null);
	}
	
	/**
	 * Upload images.
	 *
	 * @param sqlSession the current SQL session.
	 * @param entitytype the entity type to be used for the image upload.
	 * @param entityid the entity ID to be used for the image upload.
	 * @param language the language of the uploaded text.
	 * @param images the list of image URLs to be uploaded.
	 * @throws Throwable the exception thrown on error.
	 */
	public static void uploadImages(SqlSession sqlSession, NameId.Type entitytype, String entityid, String language, ArrayList<NameId> images) throws Throwable {
		uploadImages(sqlSession, entitytype, entityid, language, images, false, null);
	}
	
	public static void uploadImages(SqlSession sqlSession, NameId.Type entitytype, String entityid, String language, ArrayList<NameId> images, String localFolder) throws Throwable {
		uploadImages(sqlSession, entitytype, entityid, language, images, false, localFolder);
	}
	
	/*
	 * Upload images.
	 *
	 * @param sqlSession the current SQL session.
	 * @param entitytype the entity type to be used for the image upload.
	 * @param entityid the entity ID to be used for the image upload.
	 * @param language the language of the uploaded text.
	 * @param images the list of image URLs to be uploaded.
	 * @throws Throwable the exception thrown on error.
	 * @deprecated use ImageService.uploadImages(sqlSession, entitytype, entityid, language, images, localFolder) instead
	 */
	private static void uploadImages(SqlSession sqlSession, NameId.Type entitytype, String entityid, String language, ArrayList<NameId> images, boolean copy, String localFolder) throws Throwable {
//		
//		if (!Language.EN.equalsIgnoreCase(language) && !Language.isTranslatable(language)) {throw new ServiceException(Error.image_upload, "because the language code " + language + " cannot be translated");}
//		
//		Product product = sqlSession.getMapper(ProductMapper.class).read(entityid);
//		if(product == null) {
//			throw new ServiceException(Error.database_cannot_find, "product [" + entityid + "]");
//		}
//
//		AtomicInteger count = new AtomicInteger();
//		String lastimage = sqlSession.getMapper(TextMapper.class).lastimage(entitytype.name() + entityid + "-%");
//		if (lastimage != null) {
//			int from = lastimage.indexOf('-') + 1;
//			int to = lastimage.indexOf('.');
//			String last = lastimage.substring(from, to);
//			count.set(Integer.valueOf(last));
//		}
//		
//		// multi threading image download and text creation
//		// number of thread can be optimized by finding out available resources
//		ExecutorService executor = Executors.newFixedThreadPool(25);
//
//		String partner = "";
//		
//		Collection<Future<?>> futures = new LinkedList<Future<?>>();
//		Map<String, byte[]> propertyImages = new HashMap<String, byte[]>();
//		boolean compressImage = false;
//		if(RazorConfig.getEnvironmentId().equals(RazorConfig.PROD_ENVIRONMENT)) {
//			compressImage = true;
//		}
//		// download each image in it's own thread
//		for (NameId image : images) {
//			try {
//				if(count.get() == 0) {
//					int begin = image.getId().indexOf("img") + 4;
//					partner = image.getId().substring(begin, begin + 2);
//				}
//				String filename = NameId.Type.Product.name() + entityid + "-" + String.format("%03d", count.get()) + ".jpg"; //Text.IMAGE_JPG;
//				String name = copy ? HasUrls.PICTURES_URL + filename : image.getId();
//				Text text = new Text();
//				text.setName(name);
//				text.setLanguage(language);
//				text = sqlSession.getMapper(TextMapper.class).exists(text);
//				if (text == null) {
//					futures.add(executor.submit(new UploadFileService.ImageDownloadTask(sqlSession, count.incrementAndGet(), image, language, entityid, copy, propertyImages, compressImage, localFolder)));
//				}
//			} 
//			catch (Throwable x) {
//				LOG.error("Bad or Absent Image URL: " + image.getId());
//				x.printStackTrace();
//			}
//			
//		}
//		executor.shutdown();
//		for (Future<?> future:futures) {
//		    future.get();
//		}
//		
//		// upload images to the storage
//		StorageService storageService = StorageServiceFactory.getInstance();
//		storageService.uploadImages(product.getAltpartyid(), product.getAltSupplierId(), product.getAltid(), propertyImages, UploadFileService.JPEG_FILE_TYPE, compressImage);
//		
//		
//		sqlSession.commit();
//		LOG.error("Images downloaded for partner " + partner + " and entity " + entityid + " : " + count);
		
		ImageService.uploadImages(sqlSession, entitytype, entityid, language, images, localFolder);
	}
	
	static class ImageDownloadTask  extends Thread {
		int count;
		SqlSession sqlSession;
		NameId imageData;
		String language;
		String entityid;
		boolean copy;
		Map<String, byte[]> propertyImages;
		boolean compressImage;
		String localFolder;
		
		public ImageDownloadTask(SqlSession sqlSession, int count, NameId image, String language, String entityid, boolean copy, Map<String, byte[]> propertyImages, boolean compressImage, String localFolder) {
			this.entityid = entityid;
			this.sqlSession = sqlSession;
			this.imageData = image;
			this.language = language;
			this.copy = copy;
			this.count = count;
			this.propertyImages = propertyImages;
			this.compressImage = compressImage;
			this.localFolder = localFolder;
		}
		
		public void run() {
			String filename = NameId.Type.Product.name() + entityid + "-" + String.format("%03d", count) + ".jpg";
			String name = copy ? RazorHostsConfig.getPicturesUrl() + filename : imageData.getId();
			Text text = new Text();
			text.setName(name);
			text.setLanguage(language);
			text = sqlSession.getMapper(TextMapper.class).exists(text);
			if (text == null) {
				try {
					long startTime = System.currentTimeMillis();
					LOG.error("Downloading file named: " + filename);
					BufferedImage image;
					if(StringUtils.isEmpty(localFolder)) {
						URL url = new URL(imageData.getId().replace(" ", "%20"));
						image = ImageIO.read(url);
						long endTime = System.currentTimeMillis();
						LOG.error("Total time taken to download image " + imageData.getId() + " : " + (endTime - startTime) + " miliseconds.");
					}
					else {
						String picture = localFolder + imageData.getId().substring(imageData.getId().lastIndexOf("/"));
						image = ImageIO.read(new File(picture));
						long endTime = System.currentTimeMillis();
						LOG.error("Total time taken to download image " + imageData.getId() + " : " + (endTime - startTime) + " miliseconds.");
					}
					
					
//					setText(sqlSession, new File(fn), fn, name,
//							Text.Type.Image.name(), imageData.getName(), language,
//							Text.FULLSIZE_PIXELS_VALUE,
//							Text.THUMBNAIL_PIXELS_VALUE);
					setText(sqlSession, filename, name,
							Text.Type.Image.name(), imageData.getName(), language,
							image,
							propertyImages,
							compressImage);
				} catch (FileNotFoundException e) {
					LOG.error("Bad or Absent Image URL: " + imageData.getId() + " " + e.getMessage());
				} catch (MalformedURLException e) {
					LOG.error("Bad or Absent Image URL: " + imageData.getId());
				} catch (IOException e) {
					LOG.error("Bad or Absent Image URL: " + imageData.getId());
				} catch(Exception e) {
					LOG.error(e.getMessage() + " Image URL: " + imageData.getId());
				}
			}
		}
	}
	
	public static String getImageLocation(SqlSession sqlSession, String entityid) {
		return getImage(sqlSession, entityid, true);
	}
	
	public static String getImageUrl(SqlSession sqlSession, String entityid) {
		return getImage(sqlSession, entityid, false);
	}
	
	/*
	 * Getting image location for specific property from the local drive or remote URL.
	 */
	private static String getImage(SqlSession sqlSession, String entityid, boolean isLocal) {
		Product product = sqlSession.getMapper(ProductMapper.class).read(entityid);
		if(product == null) {
			throw new ServiceException(Error.database_cannot_find, "product [" + entityid + "]");
		}
		String imageLocation = null;
		String name = product.getAltid() == null ? "default" : product.getAltid();
		String pms = product.getAltpartyid() == null ? "default" : product.getAltpartyid();
		String pm = product.getAltSupplierId() == null ? "default" : product.getAltSupplierId();
		if(isLocal) {
			if (RazorConfig.getPmsesOnS3List().contains(pms)) {
				imageLocation = RazorServer.ROOT_DIRECTORY + HasUrls.PICTURES_DIRECTORY + pms + File.separator + pm + File.separator + name + File.separator;
				new File(imageLocation).mkdirs();
			}
			else {
				imageLocation = RazorServer.ROOT_DIRECTORY + HasUrls.PICTURES_DIRECTORY;
			}
		} else {
			if (RazorConfig.getPmsesOnS3List().contains(pms)) {
				imageLocation = RazorHostsConfig.getAmazonPicturesUrl() + pms + "/" + pm + "/" + name + "/";
			} else {
				imageLocation = RazorHostsConfig.getPicturesUrl();
			}
		}
		return imageLocation;
	}
	
	/*
	 * Create text record for blob, file or image
	 */
	/**
	 * Sets the image text and uploads the image file.
	 *
	 * @param sqlSession the current SQL session.
	 * @param file the image file to be uploaded.
	 * @param fn the file name.
	 * @param name the name in the text record.
	 * @param type the type the record.
	 * @param notes the notes associated with the image.
	 * @param language the language of the notes.
	 * @param fullsizepixels the pixel height of the full size image.
	 * @param thumbnailpixels the pixel height of the thumb nail image.
	 */
	public synchronized static void setText(SqlSession sqlSession, File file, String fn, String name, String type, String notes, String language, int fullsizepixels, int thumbnailpixels) {

		Text text = new Text();
		text.setId(file.getName());
		text.setLanguage(language);
		Text exists = sqlSession.getMapper(TextMapper.class).readbyexample(text);
		if (exists == null) {
			sqlSession.getMapper(TextMapper.class).create(text);
		}
		text.setName(name);
		text.setState(Text.State.Created.name());
		text.setType(type);
		text.setDate(new Date());
		text.setNotes(notes);
		LOG.debug("setText " + fn + ", " + text);
		if (text.hasType(Text.Type.Image) && Text.isImageFile(fn)) {
			boolean isCreated = getImage(fn, fullsizepixels, thumbnailpixels);
			if(!isCreated) {
				text.setState(Text.State.Final.name());
			}
		}
		else if (text.hasType(Text.Type.Blob) && file.length() <= Text.MAX_BLOB_SIZE) {
			text.setData(getBlob(file));
		}
		else if (Text.isImageFile(fn)) {
			text.setData(getImageBlob(fn, fullsizepixels));
			text.setType(Text.Type.Blob.name());
		}
		sqlSession.getMapper(TextMapper.class).update(text);
	}
	
	/**
	 * Sets the image text and uploads the image file.
	 *
	 * @param sqlSession the current SQL session.
	 * @param file the image file to be uploaded.
	 * @param fn the file name.
	 * @param name the name in the text record.
	 * @param type the type the record.
	 * @param notes the notes associated with the image.
	 * @param language the language of the notes.
	 * @param fullsizepixels the pixel height of the full size image.
	 * @param thumbnailpixels the pixel height of the thumb nail image.
	 */
	public synchronized static void setText(SqlSession sqlSession, String fn, String name, String type, String notes, String language, BufferedImage image, Map<String, byte[]> images, boolean compressImage) {

		Text text = new Text();
		text.setId(fn);
		text.setLanguage(language);
		Text exists = sqlSession.getMapper(TextMapper.class).readbyexample(text);
		if (exists == null) {
			sqlSession.getMapper(TextMapper.class).create(text);
		}
		text.setName(name);
		text.setState(Text.State.Created.name());
		text.setType(type);
		text.setDate(new Date());
		text.setNotes(notes);
		LOG.debug("setText " + fn + ", " + text);
		if (text.hasType(Text.Type.Image) && Text.isImageFile(fn)) {
			boolean isCreated = compressImage ? getCompressedImage(fn, image, images) : getImage(fn, image, images);
			if(!isCreated) {
				text.setState(Text.State.Final.name());
			}
		}
		else if (text.hasType(Text.Type.Blob)) {
			byte[] blob = getBlob(image);
			if(blob.length <= Text.MAX_BLOB_SIZE) {
				text.setData(blob);
				text.setType(Text.Type.Blob.name());
			}
		}
		else if (Text.isImageFile(fn)) {
			text.setData(getImageBlob(fn, Text.FULLSIZE_PIXELS_VALUE));
			text.setType(Text.Type.Blob.name());
		}
		sqlSession.getMapper(TextMapper.class).update(text);
	}
	
	/*
	 * Creates and saves thumbnail and full size jpg images from the uploaded image.
	 * 
	 * @param response the HTTP response.
	 * @param fn the server file name.
	 * @param params the key-value map of parameters from the submitted form.
	 */
	private static boolean getImage(String fn, BufferedImage image, Map<String, byte[]> images) {
		int fullsizepixels = Text.FULLSIZE_PIXELS_VALUE;
		int thumbnailpixels = Text.THUMBNAIL_PIXELS_VALUE;
//		ByteBuffer byteArray = new ByteBuffer();
		ByteArrayOutputStream bOutputReg = new ByteArrayOutputStream();
		ByteArrayOutputStream bOutputThumb = new ByteArrayOutputStream();
		ImageIcon imageIcon = new ImageIcon(image);
		//		if(image.getIconHeight() > 0 && image.getIconWidth() > 0) {
		if(imageIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
			ImageIcon fullsizeImage = new ImageIcon(imageIcon.getImage().getScaledInstance(-1, fullsizepixels, Image.SCALE_SMOOTH));
			LOG.debug("\n UploadFileService setImage image= " + imageIcon + " width=" + fullsizeImage.getIconWidth() + "  height=" + fullsizeImage.getIconHeight());
			BufferedImage fullsizeBufferedImage = new BufferedImage(
					fullsizeImage.getIconWidth(),
					fullsizeImage.getIconHeight(),
					BufferedImage.TYPE_INT_RGB
			);
			Graphics fullsizeGraphics = fullsizeBufferedImage.getGraphics();
			fullsizeGraphics.drawImage(fullsizeImage.getImage(), 0, 0, null);
			String fullsizeFile = fn.substring(0,fn.lastIndexOf('.')) + ".jpg";
				
			try {
				ImageIO.write(fullsizeBufferedImage, FULLSIZE_JPEG, bOutputReg);
				bOutputReg.flush();
				images.put(fullsizeFile, bOutputReg.toByteArray());
				bOutputReg.close();
			}
			catch (IOException x) {
				throw new RuntimeException("Error saving full sized image " + x.getMessage());
			}
			catch(Exception e) {
				LOG.error(e.getMessage() + " Error saving full sized image: " + fullsizeFile);
			}
			
			ImageIcon thumbnailImage = new ImageIcon(imageIcon.getImage().getScaledInstance(-1, thumbnailpixels, Image.SCALE_SMOOTH));
			String thumbnailFile = fn.substring(0, fn.lastIndexOf('.')) + "Thumb.jpg";
			
			BufferedImage thumbnailBufferedImage = new BufferedImage(
					thumbnailImage.getIconWidth(),
					thumbnailImage.getIconHeight(),
					BufferedImage.TYPE_INT_RGB
			);
			Graphics thumbnailGraphics = thumbnailBufferedImage.getGraphics();
			thumbnailGraphics.drawImage(thumbnailImage.getImage(), 0, 0, null);
			try {
				ImageIO.write(thumbnailBufferedImage, FULLSIZE_JPEG, bOutputThumb);
				bOutputThumb.flush();
				images.put(thumbnailFile, bOutputThumb.toByteArray());
				bOutputThumb.close();
			}
			catch (IOException x) {
				throw new RuntimeException("Error saving thumbnail image " + x.getMessage());
			}
			catch(Exception e) {
				LOG.error(e.getMessage() + " Error saving thumbnail image: " + thumbnailFile);
			}
			
			return true;
		}
		else {
			LOG.error("\n UploadFileService setImage image= " + imageIcon + " width=" + imageIcon.getIconWidth() + "  height=" + imageIcon.getIconHeight());
			return false;
		}
	}
	
	/*
	 * Creates compressed thumbnail and full size jpg images from the uploaded image.
	 * 
	 * @param response the HTTP response.
	 * @param fn the server file name.
	 * @param params the key-value map of parameters from the submitted form.
	 */
	private static boolean getCompressedImage(String fn, BufferedImage image, Map<String, byte[]> images) {
		int fullsizepixels = Text.FULLSIZE_PIXELS_VALUE;
		int thumbnailpixels = Text.THUMBNAIL_PIXELS_VALUE;
//		ByteBuffer byteArray = new ByteBuffer();
		ByteArrayOutputStream bOutputReg = new ByteArrayOutputStream();
		ByteArrayOutputStream bOutputThumb = new ByteArrayOutputStream();
		ImageIcon imageIcon = new ImageIcon(image);
		try {
			GZIPOutputStream zipStreamReg = new GZIPOutputStream(bOutputReg);
			GZIPOutputStream zipStreamThumb = new GZIPOutputStream(bOutputThumb);
			
			if(imageIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
				ImageIcon fullsizeImage = new ImageIcon(imageIcon.getImage().getScaledInstance(-1, fullsizepixels, Image.SCALE_SMOOTH));
				LOG.debug("\n UploadFileService setImage image= " + imageIcon + " width=" + fullsizeImage.getIconWidth() + "  height=" + fullsizeImage.getIconHeight());
				BufferedImage fullsizeBufferedImage = new BufferedImage(
						fullsizeImage.getIconWidth(),
						fullsizeImage.getIconHeight(),
						BufferedImage.TYPE_INT_RGB
				);
				Graphics fullsizeGraphics = fullsizeBufferedImage.getGraphics();
				fullsizeGraphics.drawImage(fullsizeImage.getImage(), 0, 0, null);
				String fullsizeFile = fn.substring(0,fn.lastIndexOf('.')) + ".jpg";
					
				try {
					ImageIO.write(fullsizeBufferedImage, FULLSIZE_JPEG, zipStreamReg);
					zipStreamReg.close();
					bOutputReg.close();
					images.put(fullsizeFile, bOutputReg.toByteArray());
				}
				catch (IOException x) {
					throw new RuntimeException("Error saving full sized image " + x.getMessage());
				}
				
				ImageIcon thumbnailImage = new ImageIcon(imageIcon.getImage().getScaledInstance(-1, thumbnailpixels, Image.SCALE_SMOOTH));
				String thumbnailFile = fn.substring(0, fn.lastIndexOf('.')) + "Thumb.jpg";
				
				BufferedImage thumbnailBufferedImage = new BufferedImage(
						thumbnailImage.getIconWidth(),
						thumbnailImage.getIconHeight(),
						BufferedImage.TYPE_INT_RGB
				);
				Graphics thumbnailGraphics = thumbnailBufferedImage.getGraphics();
				thumbnailGraphics.drawImage(thumbnailImage.getImage(), 0, 0, null);
				try {
					ImageIO.write(thumbnailBufferedImage, FULLSIZE_JPEG, zipStreamThumb);
					zipStreamThumb.close();
					bOutputThumb.close();
					images.put(thumbnailFile, bOutputThumb.toByteArray());
				}
				catch (IOException x) {
					throw new RuntimeException("Error saving thumbnail image " + x.getMessage());
				}
				
				
				return true;
			}
			else {
				LOG.error("\n UploadFileService setImage image= " + imageIcon + " width=" + imageIcon.getIconWidth() + "  height=" + imageIcon.getIconHeight());
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	/*
	 * Creates and saves thumbnail and full size jpg images from the uploaded image.
	 * 
	 * @param response the HTTP response.
	 * @param fn the server file name.
	 * @param params the key-value map of parameters from the submitted form.
	 */
	private static boolean getImage(String fn, int fullsizepixels, int thumbnailpixels) {

		ImageIcon image = new ImageIcon(fn);
		//		if(image.getIconHeight() > 0 && image.getIconWidth() > 0) {
		if(image.getImageLoadStatus() == MediaTracker.COMPLETE) {
			ImageIcon fullsizeImage = new ImageIcon(image.getImage().getScaledInstance(-1, fullsizepixels, Image.SCALE_SMOOTH));
			LOG.debug("\n UploadFileService setImage image= " + image + " width=" + fullsizeImage.getIconWidth() + "  height=" + fullsizeImage.getIconHeight());
			BufferedImage fullsizeBufferedImage = new BufferedImage(
					fullsizeImage.getIconWidth(),
					fullsizeImage.getIconHeight(),
					BufferedImage.TYPE_INT_RGB
			);
			Graphics fullsizeGraphics = fullsizeBufferedImage.getGraphics();
			fullsizeGraphics.drawImage(fullsizeImage.getImage(), 0, 0, null);
			File fullsizeFile = new File(fn.substring(0,fn.lastIndexOf('.')) + ".jpg");
			fullsizeFile.delete();
	
			try {ImageIO.write(fullsizeBufferedImage, FULLSIZE_JPEG, fullsizeFile);}
			catch (IOException x) {throw new RuntimeException("Error saving full sized image " + x.getMessage());}
	
			ImageIcon thumbnailImage = new ImageIcon(image.getImage().getScaledInstance(-1, thumbnailpixels, Image.SCALE_SMOOTH));
			File thumbnailFile = new File(fn.substring(0, fn.lastIndexOf('.')) + "Thumb.jpg");
			thumbnailFile.delete();
			BufferedImage thumbnailBufferedImage = new BufferedImage(
					thumbnailImage.getIconWidth(),
					thumbnailImage.getIconHeight(),
					BufferedImage.TYPE_INT_RGB
			);
			Graphics thumbnailGraphics = thumbnailBufferedImage.getGraphics();
			thumbnailGraphics.drawImage(thumbnailImage.getImage(), 0, 0, null);
			try {ImageIO.write(thumbnailBufferedImage, FULLSIZE_JPEG, thumbnailFile);}
			catch (IOException x) {throw new RuntimeException("Error saving thumbnail image " + x.getMessage());}
			return true;
		}
		else {
			LOG.error("\n UploadFileService setImage image= " + image + " width=" + image.getIconWidth() + "  height=" + image.getIconHeight());
			return false;
		}
	}

	/*
	 * Creates a byte array from contents of the uploaded file.
	 * 
	 * @param response the HTTP response.
	 * @param file the uploaded file.
	 * @return the byte array.
	 */
	private static byte[] getBlob(File file) {
		try {
			InputStream is = new FileInputStream(file);
			long length = file.length();
			if (length > Integer.MAX_VALUE) {throw new IOException("File is too large for BLOB - maximum size = " + Integer.MAX_VALUE);}
			byte[] bytes = new byte[(int)length];
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
				offset += numRead;
			}
			if (offset < bytes.length) {throw new IOException("Could not completely read file " + file.getName());}
			is.close();
			return bytes;
		}
		catch (IOException x){throw new RuntimeException("Error creating BLOB record " + x.getMessage());}
	}
	
	/*
	 * Creates a byte array from contents of the uploaded file.
	 * 
	 * @param response the HTTP response.
	 * @param file the uploaded file.
	 * @return the byte array.
	 */
	private static byte[] getBlob(BufferedImage image) {
		try {
			ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
			ImageIO.write(image, "jpeg", bOutput);
			return bOutput.toByteArray();
		}
		catch (IOException x){throw new RuntimeException("Error creating BLOB record " + x.getMessage());}
	}

	/*
	 * Creates, resizes and returns a byte array from contents of uploaded image file.
	 * 
	 * @param response the HTTP response.
	 * @param fn the image file name.
	 * @param params the key-value map of parameters from the submitted form.
	 */
	private static byte[] getImageBlob(String fn, int fullsizepixels) {
		try {
			ImageIcon image = new ImageIcon(fn);

			ImageIcon logoImage = new ImageIcon(image.getImage().getScaledInstance(fullsizepixels, -1, Image.SCALE_SMOOTH));
			BufferedImage bufferedImage = new BufferedImage(
					logoImage.getIconWidth(),
					logoImage.getIconHeight(),
					BufferedImage.TYPE_INT_RGB
			);
			Graphics graphics = bufferedImage.getGraphics();
			graphics.drawImage(logoImage.getImage(), 0, 0, null);
			fn = fn.substring(0,fn.lastIndexOf('.')) + ".Blob.jpg";
			File file = new File(fn);
			file.delete();
			ImageIO.write(bufferedImage, FULLSIZE_JPEG, file);

			InputStream is = new FileInputStream(file);
			long length = file.length();
			if (length > Integer.MAX_VALUE) {throw new IOException("File is too large for logo - maximum size = " + Integer.MAX_VALUE);}
			byte[] bytes = new byte[(int)length];
			int offset = 0;
			int numRead = 0;
			while (
					offset < bytes.length 
					&& (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0
			) {offset += numRead;}
			if (offset < bytes.length) {throw new IOException("Could not completely read file " + file.getName());}
			is.close();

			return bytes;
		}
		catch (IOException x){throw new RuntimeException("Error creating BLOB image " + x.getMessage());}
	}

	/*
	 * Returns HTTP response to client with optional error message.
	 * 
	 * @param response the HTTP response.
	 * @param details the details of the response.
	 */
	private void sendResponse(HttpServletResponse response, FormResponse details)
	{
		try	{
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/plain");

			String msg = details.getStatus() + "," + (details.getReason() == null ? "" : details.getReason());
			LOG.debug("sendResponse " + msg);
			response.setContentLength(msg.length());
			response.getWriter().print(msg);
			response.getWriter().flush();
		}
		catch (Throwable x){MonitorService.log(x);}
	}

	/*
	 * Inner Class FormResponse contains the information returned to the HTTP client.
	 */
	private class FormResponse {
		private int status;
		private String reason;
		private boolean deleteFiles = false;

		/**
		 * Instantiates a new form response.
		 *
		 * @param status
		 *            status to be sent as the response. They must be valid HTTP
		 *            status value such as 200, 404, 500, etc.
		 *
		 * @param reason
		 *            optional phrase (or reason string). Example: "Not Found".
		 *
		 * @param deleteFiles
		 *            true to indicate if the FileUpload servlet must delete the
		 *            files after invocation of the FileUploadAction.onSubmit()
		 *            method. If set to true, the FileUploadAction servlet will
		 *            delete the files that it created from the upload request prior to
		 *            invoking the FileUploadAction.onSubmit() method.
		 */
		public FormResponse(int status, String reason, boolean deleteFiles){
			this.status = status;
			this.reason = reason;
			this.deleteFiles = deleteFiles;
		}

		/**
		 * Instantiates a new form response.
		 *
		 * @param status
		 *            status to be sent as the response. They must be valid HTTP
		 *            status value such as 200, 404, 500, etc.
		 *
		 * @param reason
		 *            optional phrase (or reason string). Example: "Not Found".
		 */
		public FormResponse(int status, String reason){
			this(status, reason, false);
		}

		/**
		 * Gets the reason of the response.
		 *
		 * @return the reason.
		 */
		public String getReason(){
			return reason;
		}

		/**
		 * Gets the status of the response.
		 *
		 * @return the status.
		 */
		public int getStatus(){
			return status;
		}
	}
}

