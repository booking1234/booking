package net.cbtltd.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.cbtltd.server.api.ImageMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Image;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.image.ImageDelete;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.mybookingpal.service.StorageService;
import com.mybookingpal.service.StorageServiceFactory;
import com.mybookingpal.utils.ImageUtils;


public class ImageService implements IsService {
	
	private static final Logger LOG = Logger.getLogger(ImageService.class.getName());
	private static volatile ImageService instance = null;
	
	private static final String RAZOR_PMS  = "raz";
	private static final String S3_IMAGE_URL = "https://s3.amazonaws.com/mybookingpal/pictures/";
	private static final List<String> S3_PMS_LIST = Arrays.asList(new String[]{"61447", "99064", "90640", "210252", "231053", "231051", "231044", "210254",
		"231048", "210270", "210287", "231049", "210286", "231050", "231057", "231016", "321479"});
	public enum Size {Regular, Standard, Thumb}
	
	
	/**
	 * Gets the single instance of ImageService.
	 * @see net.cbtltd.shared.Image
	 *
	 * @return single instance of ImageService
	 */
	public static ImageService getInstance() {
		if (instance == null) {
			synchronized (ImageService.class) {
				if (instance == null){
					instance = new ImageService(); 
				}
			}
		}
		return instance;
	}

	/**
	 * Executes the ImageDelete action.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action
	 * @return the response.
	 */
	public final net.cbtltd.shared.Image execute(SqlSession sqlSession, ImageDelete action) {
		try {		
			
			if (action.getName().contains(NameId.Type.Product.name())){
				int pindex = action.getName().indexOf(NameId.Type.Product.name());				
				String imageName = action.getName().substring(pindex).replace(NameId.Type.Product.name(), "");
				
				String toremove = imageName.substring(imageName.indexOf("-"));
				action.setProductId(Integer.parseInt(imageName.replace(toremove, "")));
				
				Image image = sqlSession.getMapper(ImageMapper.class).exists(action);			
				if (image != null){
					// remove the image record and delete it from the storage 
					sqlSession.getMapper(ImageMapper.class).deletebyexample(image);
					Product product = sqlSession.getMapper(ProductMapper.class).read(Integer.toString(action.getProductId()));
					if(product == null) {
						LOG.error("Cannot find product with [" + action.getProductId() + "] id");
						throw new ServiceException(Error.database_cannot_find, "product");
					}
					Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(product.getSupplierid());
					com.mybookingpal.server.ImageService.setPMSPMAbbrevations(sqlSession, product, partner);
					
					// get storage service instance
					StorageService storageService = StorageServiceFactory.getInstance();
					// delete origin image from the storage
					storageService.deleteImaget(product.getPmsAbbrev(), product.getPmAbbrev(), product.getId(), image.getName());
					// delete thumb image			
					if (image.isThumbnail()){						
						String imageThumbName  = ImageUtils.getThumbNailFilename(image.getName());
						storageService.deleteImaget(product.getPmsAbbrev(), product.getPmAbbrev(), product.getId(), imageThumbName);
					}
					// delete standard image					
					if (image.isStandard()){						
						String imageStandard  = ImageUtils.getStandardFilename(image.getName());
						storageService.deleteImaget(product.getPmsAbbrev(), product.getPmAbbrev(), product.getId(), imageStandard);
					}
					
					sqlSession.commit();
				}
			}
		}
		catch (Throwable x) {MonitorService.log(x);}
		return action;
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
	
	private static String getProductImageLocation(SqlSession sqlSession, String productid) {
		Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
		if(product == null) {
			throw new ServiceException(Error.database_cannot_find, "product [" + productid + "]");
		}
		String imageLocation = null;
		
		String name = null;                                                           // product.getAltid() == null ? "default" : product.getAltid();

		ImageService.setPMSPMAbbrevations(sqlSession, product);
		
		if (S3_PMS_LIST.contains(product.getSupplierid())) {
			name = product.getAltid() == null ? product.getId() : product.getAltid();
		} else {
			name = product.getId();
		}
		
		
		
		if(StringUtils.isEmpty(product.getPmsAbbrev()))
			LOG.error("PMS Abbrevation is not available for product: " + product.getId());
		if(StringUtils.isEmpty(product.getPmAbbrev()))
			LOG.error("PM Abbrevation is not available for product: " + product.getId());
		imageLocation = S3_IMAGE_URL + product.getPmsAbbrev() + "/" + product.getPmAbbrev() + "/" + name + "/";
		
		return imageLocation;
	}
	
	public static String getProductImageLocation(SqlSession sqlSession, Product product) {
		if(product == null) {
			throw new ServiceException(Error.parameter_null, "product");
		}
		String imageLocation = null;
		
		String name = null;                                                           // product.getAltid() == null ? "default" : product.getAltid();

		ImageService.setPMSPMAbbrevations(sqlSession, product);
		
		if (S3_PMS_LIST.contains(product.getSupplierid())) {
			name = product.getAltid() == null ? product.getId() : product.getAltid();
		} else {
			name = product.getId();
		}
		
		
		
		if(StringUtils.isEmpty(product.getPmsAbbrev()))
			LOG.error("PMS Abbrevation is not available for product: " + product.getId());
		if(StringUtils.isEmpty(product.getPmAbbrev()))
			LOG.error("PM Abbrevation is not available for product: " + product.getId());
		imageLocation = S3_IMAGE_URL + product.getPmsAbbrev() + "/" + product.getPmAbbrev() + "/" + name + "/";
		
		return imageLocation;
	}
	
	private static void setPMSPMAbbrevations(SqlSession sqlSession, Product product) {
		String pms =product.getSupplierid(); 
		String pm = null;
		if (S3_PMS_LIST.contains(pms)) {
			Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(product.getSupplierid());
			if(partner == null || partner.getAbbrevation() == null) {
				LOG.error("Cannot find abbrevation for partner");
			}
			pm = partner.getAbbrevation().toLowerCase();
			if(!partner.getOrganizationid().equals(partner.getPartyid())) {
				partner = sqlSession.getMapper(PartnerMapper.class).exists(partner.getOrganizationid());
				if(partner == null){
					LOG.error("Cannot find abbrevation for partner");
				}
				pms = partner.getAbbrevation().toLowerCase();
			} else {
				pms = pm;
			}
		
		} else {
			pms = ImageService.RAZOR_PMS;
			pm = product.getSupplierid() == null ? "default" : product.getSupplierid();
		}
		product.setPmAbbrev(pm);
		product.setPmsAbbrev(pms);
	}
	
	/**
	 * Get image folder for all the product images
	 * @param sqlSession
	 * @param product
	 * @return
	 */
	private static String getProductImageFolder(SqlSession sqlSession, Product product) {
		if(product == null) {
			throw new ServiceException(Error.product_not_available);
		}
		String imageLocation = null;
		
		String name = null;                                                           // product.getAltid() == null ? "default" : product.getAltid();

		ImageService.setPMSPMAbbrevations(sqlSession, product);
		
		if (S3_PMS_LIST.contains(product.getSupplierid())) {
			name = product.getAltid() == null ? product.getId() : product.getAltid();
		} else {
			name = product.getId();
		}
		
		if(StringUtils.isEmpty(product.getPmsAbbrev()))
			LOG.error("PMS Abbrevation is not available for product: " + product.getId());
		if(StringUtils.isEmpty(product.getPmAbbrev()))
			LOG.error("PM Abbrevation is not available for product: " + product.getId());
		imageLocation = S3_IMAGE_URL + product.getPmsAbbrev() + "/" + product.getPmAbbrev() + "/" + name + "/";
		
		return imageLocation;
	}

	
	/**
	 * Getting regular image location for specific property from the local drive or remote URL.
	 * 
	 */
	public static List<String> getProductRegularImageURLs(SqlSession sqlSession, String productid) {
		
		String imageLocation = ImageService.getProductImageLocation(sqlSession, productid);
		
		
		// Build image urls from product.getImageUrs
		List<String> images = new ArrayList<String>();
				
		ArrayList<Image> listNameIds = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(new NameId(productid));
		
		
		for(Image image:listNameIds) {
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
	 * Getting regular image location for specific property from the local drive or remote URL.
	 * 
	 */
	public static List<String> getProductRegularImageURLs(SqlSession sqlSession, Product product) {
		
		String imageLocation = ImageService.getProductImageFolder(sqlSession, product);
		
		
		// Build image urls from product.getImageUrs
		List<String> images = new ArrayList<String>();
				
		ArrayList<Image> listNameIds = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(new NameId(product.getId()));
		
		
		for(Image image:listNameIds) {
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
					String imageName  = com.mybookingpal.server.ImageService.getImageThumbFileName(image.getName());
					images.add(imageLocation + imageName);
				}
			} else if(image.getType().equals(Image.Type.Linked.name().toLowerCase())) {
				images.add(image.getUrl());
			}
		}
		
		return images;
	}

}