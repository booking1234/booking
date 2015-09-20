package net.cbtltd.rest.jnuit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import net.cbtltd.server.RazorServer;
import com.mybookingpal.server.test.mapper.PriceMapper;
import com.mybookingpal.server.test.mapper.ProductMapper;
import com.mybookingpal.server.test.mapper.RelationMapper;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Relation;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(value = Parameterized.class)
public class IntegrationCheckTablesTest {
	
	private String supplierId;
	private SqlSession sqlSession;
	
	public IntegrationCheckTablesTest(String supplierId)
	{
		this.supplierId = supplierId;
		this.sqlSession = RazorServer.openSession();
	}
	
	@Parameters
	public static Collection<Object[]> configData(){
		/* specify the supplier id for which the tables need to be tested*/
		Object[][] data = new Object[][] {
				{"90640"}
		};
		
		return Arrays.asList(data);
	}
	
	@Test
	public void testForBlankOrNullValuesInProductTable()
	{
		ArrayList<String> productList = sqlSession.getMapper(ProductMapper.class).productIdListWithEmptyValuesBySupplierId(this.supplierId);
		
		int numberOfBlankEntries = productList.size();
		
		/* Test case passes if there are no product ids with empty values in the product table*/
		assertEquals("SupplierId: " + supplierId + " has " + numberOfBlankEntries + " entries with empty values in product table",
				0, numberOfBlankEntries);
	}
	
	@Test
	public void testForDuplicatesInPriceTable()
	{
		ArrayList<Price> priceList = sqlSession.getMapper(PriceMapper.class).countDuplicateReservations(this.supplierId);
		
		int numberOfDuplicates = priceList.size();
		
		/* Test case passes if the resultset of the query is empty*/
		assertEquals("SupplierId: " + supplierId + " has " + numberOfDuplicates + " duplicate entries",
				0, numberOfDuplicates);
	}
	
	@Test
	public void testAttributeCountForProperties()
	{
		ArrayList<Relation> relationList = sqlSession.getMapper(RelationMapper.class).attributeCountLT4ForPropertiesbySupplierId(supplierId);
		
		int numberOfEntriesWithLT4attribs = relationList.size();
		
		/* Test case passes if the resultset is empty*/
		assertEquals("SupplierId: " + supplierId + " has " + numberOfEntriesWithLT4attribs + " properties with less than 4 attributes",
				0, numberOfEntriesWithLT4attribs);
	}
	
	@Test
	public void testPriceConsistencyWithProduct()
	{
		ArrayList<Price> priceList = sqlSession.getMapper(PriceMapper.class).priceEntriesWithInvalidProductId(supplierId);
		
		int numberOfInvalidPriceEntries = priceList.size();
		
		/* Test case passes if the resultset is empty*/
		assertEquals("SupplierId: " + supplierId + " has " + numberOfInvalidPriceEntries + " invalid price entries", 
				0, numberOfInvalidPriceEntries);
	}
	
	@Test
	public void testCancellationPolicyForProducts()
	{
		ArrayList<String> productList = sqlSession.getMapper(ProductMapper.class).getProductsWithNoCancellationPolicy();
		
		/*Test cases passes if the resultset is empty*/
		assertEquals(productList.size() + " products have no cancellation policy",
				0, productList.size());
	}
}
