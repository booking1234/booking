package net.cbtltd.server.integration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.cbtltd.server.RelationService;
import net.cbtltd.server.api.AttributeMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class AttributeService  implements IsService  {
	private static final Logger LOG = Logger.getLogger(AttributeService.class
			.getName());
	private static AttributeService service;

	/**
	 * Gets the single instance of ProductService to manage Product instances.
	 * 
	 * @see net.cbtltd.shared.Product
	 * 
	 * @return single instance of ProductService.
	 */
	public static synchronized AttributeService getInstance() {
		if (service == null) {
			service = new AttributeService();
		}
		return service;
	}

	public final List<String> fetchAttributeNames(SqlSession sqlSession, List<String> relation) {
		return sqlSession.getMapper(AttributeMapper.class).readAttribute(relation);
		
	}
	
	
	/**
	 * to persist attributes for the product
	 * @param sqlSession
	 * @param product
	 * @param attributes
	 */
	public void persistAttributes(SqlSession sqlSession, Product product,
			List<String> attributes) {

		attributes.removeAll(Collections.singleton(null));

		LOG.info("Updating the attributes of Product " + product.getName());

		RelationService.create(sqlSession, Relation.PRODUCT_OTA_ATTRIBUTE,
				product.getId(), (ArrayList<String>)attributes);

	}
}
