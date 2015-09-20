/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.server.api.UnspscMapper;
import net.cbtltd.server.api.YieldMapper;
import net.cbtltd.shared.Attribute;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.Downloaded;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.NameIdType;
import net.cbtltd.shared.Position;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.product.NoOfflineNameId;
import net.cbtltd.shared.product.NoPartofNameId;
import net.cbtltd.shared.product.ProductCopy;
import net.cbtltd.shared.product.ProductCreate;
import net.cbtltd.shared.product.ProductDelete;
import net.cbtltd.shared.product.ProductExists;
import net.cbtltd.shared.product.ProductList;
import net.cbtltd.shared.product.ProductPositions;
import net.cbtltd.shared.product.ProductRead;
import net.cbtltd.shared.product.ProductTable;
import net.cbtltd.shared.product.ProductUnspsc;
import net.cbtltd.shared.product.ProductUpdate;
import net.cbtltd.shared.product.WidgetProduct;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.mybookingpal.server.ImageService;

/** The Class ProductService responds to product (property) requests. */
public class ProductService
implements IsService {

	private static final Logger LOG = Logger.getLogger(ProductService.class.getName());
	private static ProductService service;

	/**
	 * Gets the single instance of ProductService to manage Product instances.
	 * @see net.cbtltd.shared.Product
	 *
	 * @return single instance of ProductService.
	 */
	public static synchronized ProductService getInstance() {
		if (service == null){service = new ProductService();}
		return service;
	}

	/**
	 * Executes the ProductCreate action to create a Product instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Product execute(SqlSession sqlSession, ProductCreate action) {
		try {
			sqlSession.getMapper(ProductMapper.class).create(action);
			action.setProductImageRootLocation(ImageService.getProductImageLocation(sqlSession, action));
			RelationService.create(sqlSession, Relation.ORGANIZATION_PRODUCT, action.getOrganizationid(), action.getId());
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the ProductExists action to read a Product instance if it exists and create it if it does not.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Product execute(SqlSession sqlSession, ProductExists action) {
		Product product = null;
		try {
			product = sqlSession.getMapper(ProductMapper.class).exists(action);
			if (product != null) {RelationService.create(sqlSession, Relation.ORGANIZATION_PRODUCT, action.getOrganizationid(), product.getId());}
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return product;
	}

	/**
	 * Executes the ProductRead action to read a rich Product instance for an application.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Product execute(SqlSession sqlSession, ProductRead action) {
		Product product = null;
		try {
			product = sqlSession.getMapper(ProductMapper.class).read(action.getId());
			product.setFiles(sqlSession.getMapper(TextMapper.class).productfilenameid(product.getId()));
//			product.setImageurls(sqlSession.getMapper(TextMapper.class).imageidsbynameid(new NameId(NameId.Type.Product.name(), action.getId())));
			product.setImageurls((ArrayList<String>)ImageService.getProductThumbAndOriginImageURLs(sqlSession, action.getId()));
			product.setProductImageRootLocation(ImageService.getProductImageLocation(sqlSession, product));
			product.setValues(RelationService.read(sqlSession, Relation.PRODUCT_VALUE, product.getId(), null));
			product.setAttributemap(RelationService.readMap(sqlSession, Relation.PRODUCT_ATTRIBUTE, product.getId(), Attribute.PROPERTY));
		}
		catch (Throwable x) {
			sqlSession.rollback();
			MonitorService.log(x);
		}
		LOG.debug("ProductRead " + product);
		return product;
	}

	/**
	 * Executes the WidgetProduct action to read a basic Product instance for a widget.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Product execute(SqlSession sqlSession, WidgetProduct action) {
		Product product = null;
		try {
			product = sqlSession.getMapper(ProductMapper.class).read(action.getId());
//			product.setImageurls(sqlSession.getMapper(TextMapper.class).imageidsbynameid(new NameId(NameId.Type.Product.name(), action.getId())));
			product.setImageurls((ArrayList<String>)ImageService.getProductThumbnailImageURLs(sqlSession, action.getId()));
			product.setProductImageRootLocation(ImageService.getProductImageLocation(sqlSession, product));
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return product;
	}

	/**
	 * Executes the ProductUpdate action to update a Product instance.
	 * Updates related object graph.
	 * Deletes related download relations to allow download of potentially changed objects.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Product execute(SqlSession sqlSession, ProductUpdate action) {
		try {
			Product exists = sqlSession.getMapper(ProductMapper.class).read(action.getId());
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Product, action);
			if (action != null && exists != null && action.hashCode() != exists.hashCode()) {action.setVersion(new Date());}
			sqlSession.getMapper(ProductMapper.class).update(action);
			action.setImageurls((ArrayList<String>)ImageService.getProductThumbAndOriginImageURLs(sqlSession, action.getId()));
			action.setProductImageRootLocation(ImageService.getProductImageLocation(sqlSession, action));
			RelationService.replace(sqlSession, Relation.PRODUCT_VALUE, action.getId(), action.getValues());
			RelationService.replace(sqlSession, Relation.PRODUCT_ATTRIBUTE, action.getId(), action.getAttributemap());
			RelationService.unload(sqlSession, Downloaded.PRODUCT_DOWNLOAD, action.getId(), null);
			TextService.update(sqlSession, action.getTexts());
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Product, action);
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the ProductDelete action to delete a Product instance.
	 * This deletes the relation between the product and the current organization, not the product instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Product execute(SqlSession sqlSession, ProductDelete action) {
		try {RelationService.delete(sqlSession, Relation.ORGANIZATION_PRODUCT, action.getOrganizationid(), action.getId());}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return null;
	}

	/**
	 * Executes the ProductCopy action to copy a Product instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Product execute(SqlSession sqlSession, ProductCopy action) {
		try {
			action.setName(Model.BLANK);
//			action.setPartofid(null);
			final String oldId = action.getId();
			sqlSession.getMapper(ProductMapper.class).create(action);
			final String newId = action.getId();
			TextService.copy(sqlSession, NameId.Type.Product.name(), oldId, newId, action);
			sqlSession.getMapper(PriceMapper.class).copy(new NameId(oldId, newId));
			sqlSession.getMapper(YieldMapper.class).copy(new NameId(oldId, newId));
			RelationService.replace(sqlSession, Relation.PRODUCT_VALUE, newId, action.getValues());
			RelationService.replace(sqlSession, Relation.PRODUCT_ATTRIBUTE, newId, action.getAttributemap());
			RelationService.replace(sqlSession, Relation.ORGANIZATION_PRODUCT, action.getOrganizationid(), newId);

			//TODO: copy product values
			/*ArrayList<NameId> images = sqlSession.getMapper(TextMapper.class).imagesbynameid(new NameId(NameId.Type.Product.name(), oldId));						
			List<NameId> images = ImageService.getProductRegularImageURLsAndDescription(sqlSession, oldId);			
			UploadFileService.copyImages(sqlSession, NameId.Type.Product, newId, Language.EN, (ArrayList<NameId>)images);*/			
			//LOG.debug("ProductCopy images " + oldId + " " + newId + " " + images);
			action.setImageurls(new ArrayList<String>());
			action.setProductImageRootLocation(ImageService.getProductImageLocation(sqlSession, action));
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Prepend to string list.
	 *
	 * @param text to prepend
	 * @param items the items
	 * @return string list with text prepended
	 */
	public static final ArrayList<String> prependToList(SqlSession sqlSession, String entityId, ArrayList<String> items) {
		if (items == null) {return null;}
		ArrayList<String> result = new ArrayList<String>();
		for (String item : items) {
			String imageUrl = UploadFileService.getImageUrl(sqlSession, entityId);
			if (item != null) result.add(imageUrl + item);
		}
		return result;
	}
	
	/**
	 * Prepend to image list.
	 *
	 * @param text to prepend
	 * @param items the items
	 * @return string list with text prepended
	 */
	public static final ArrayList<NameId> prependToListNameId(SqlSession sqlSession, String entityId, ArrayList<NameId> items) {
		if (items == null) {return null;}
		ArrayList<NameId> result = new ArrayList<NameId>();
		String imageUrl = UploadFileService.getImageUrl(sqlSession, entityId);
		for (NameId item : items) {
			if (item != null) {
				NameId imageNameId = new NameId(item.getName(), imageUrl + item.getId());
				result.add(imageNameId);
			}
		}
		return result;
	}

	/**
	 * Executes the ProductList action to read a list of Product instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Product> execute(SqlSession sqlSession, ProductList action) {
		Table<Product> table = new Table<Product>();
		try {
			table.setDatasize(sqlSession.getMapper(ProductMapper.class).count(action));
			table.setValue(sqlSession.getMapper(ProductMapper.class).list(action));
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the ProductTable action to read a list of component Product instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Product> execute(SqlSession sqlSession, ProductTable action) {
		LOG.debug("ProductTable " + action);
		Table<Product> table = new Table<Product>();
		try {table.setValue(sqlSession.getMapper(ProductMapper.class).componentlist(action.getId()));}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("ProductTable " + table);
		return table;
	}

	/**
	 * Executes the NameIdAction action to read a list of product NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdAction action) {
		LOG.debug("NameIdAction " + action);
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(ProductMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(ProductMapper.class).nameidbyname(action));}
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("NameIdAction " + table);
		return table;
	}

	/**
	 * Executes the NoOfflineNameId action to check that a Product instance is not off line.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NoOfflineNameId action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(ProductMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(ProductMapper.class).nameidnopartof(action));}
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the NoPartofNameId action to check that a Product instance is not part of itself.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NoPartofNameId action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(ProductMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(ProductMapper.class).nameidnopartof(action));}
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the ProductUnspsc action to read the UNSPSC code of a Product instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, ProductUnspsc action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(ProductMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(UnspscMapper.class).nameidbyname(action));}
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}
	
	/**
	 * Executes the NameIdType action to read a list of product asset type NameId instances.
	 * NOTE this does not get the product types but the types of its assets!
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdType action) {
		Table<NameId> table = new Table<NameId>();
		try {table.setValue(sqlSession.getMapper(ProductMapper.class).nameidtype(action));}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}
	
	/**
	 * Executes the NameIdAction action to read a list of product NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Position> execute(SqlSession sqlSession, ProductPositions action) {
		LOG.debug("ProductPositions " + action);
		Table<Position> table = new Table<Position>();
		try {table.setValue(sqlSession.getMapper(ProductMapper.class).positions(action));}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("ProductPositions " + table);
		return table;
	}
}