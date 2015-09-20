package com.mybookingpal.utils;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ImageMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.RelationMapper;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.server.api.YieldMapper;
import net.cbtltd.shared.Image;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Yield;

import org.apache.ibatis.session.SqlSession;

public class PropertyRater {
	
	private static final double dataWeightage = 0.05;
	private static final double attributeWeightage = 0.05;
	private static final double descriptionWeightage = 0.15;
	private static final double contentsWeightage = 0.05;
	private static final double optionsWeightage = 0.05;
	private static final double mapWeightage = 0.15;
	private static final double imageWeightage = 0.30;
	private static final double priceWeightage = 0.15;
	private static final double yieldWeightage = 0.05;
	
	private SqlSession sqlSession;
	private String propertyId;
	private Product product;
	
	private double calculateDataProgress()
	{
		double progress = 0;
		
		progress += isNullOrEmpty(product.getName()) ? 0.0 : 25.0;
		progress += isNullOrEmpty(product.getSupplierid()) ? 0.0 : 25.0;
		progress += (product.getDiscount() == null) ? 0.0 : 25.0;
		progress += (product.getCommissionValue() == null) ? 0.0 : 25.0;
		return progress * dataWeightage;
		
	}
	
	private boolean isNullOrEmpty(String input)
	{
		if(input == null || input.trim().equals(""))
			return true;
		return false;
	}
	
	private double calculateDescriptionProgress()
	{
		Text result = sqlSession.getMapper(TextMapper.class).read("Product" + propertyId + "Public");
		
		String description = result.getNotes();
		
		/* set 1 when description length is greater than 299*/
		double progress = description.length() < 300 ? description.length()*100.0/300.0 : 100.0;
	
		return progress * descriptionWeightage;
	}
	
	private double calculateAttributeProgress()
	{
		Relation relation = new Relation();
		relation.setLink("Product Attribute");
		relation.setHeadid(propertyId);
		
		ArrayList<Relation> attributeList = sqlSession.getMapper(RelationMapper.class).list(relation);
		
		double progress = 0.0;
		
		/* set full progress if attribute count is greater than 3*/
		progress = attributeList.size() < 4 ? attributeList.size() * 100.0/4.0 : 100.0;
		
		return progress * attributeWeightage;
	}
	
	private double calculateContentsProgress()
	{
		Text text = new Text();
		text.setId(propertyId + "_property");
		text.setName("property");
		
		Text result = sqlSession.getMapper(TextMapper.class).read("Product" + propertyId + "Contents");
		
		String content = result.getNotes();
		
		double progress = content.length() < 30 ? content.length() * 100.0/30.0 : 100.0;
		
		return progress * contentsWeightage;
	}
	
	private double calculateOptionsProgress()
	{
		Text text = new Text();
		text.setId(propertyId + "_property");
		text.setName("property");
		
		Text result = sqlSession.getMapper(TextMapper.class).read("Product" + propertyId + "Contents");
		
		String options = result.getNotes();
		
		double progress = options.length() < 30 ? options.length() * 100.0/30.0 : 100.0;
		
		return progress * optionsWeightage;
	}
	
	private double calculateMapProgress()
	{
		double progress = ((product.getLatitude() == null) || (product.getLongitude() == null)) ? 0 : 100;
		return progress * mapWeightage;
	}
	
	private double calculateImageProgress()
	{
		NameId action = new NameId();
		action.setId(propertyId);
		
		ArrayList<Image> images = sqlSession.getMapper(ImageMapper.class).imagesbyproductid(action);
		
		int imageCount = images.size();
		
		/* set full progress if there are more than 9 images*/
		double progress = imageCount < 10 ? imageCount * 10.0 : 100.0;
		
		return progress * imageWeightage;
	}
	
	private double calculatePriceProgress()
	{
		Price price = new Price();
		
		price.setEntityid(propertyId);
		
		ArrayList<Price> prices = sqlSession.getMapper(PriceMapper.class).readByEntityId(price);
		
		int numDaysAhead = 0;
		
		if(!prices.isEmpty())
		{
			Date earliestDate = prices.get(0).getDate();
			Date latestDate = prices.get(0).getTodate();
			
			for(int i = 1; i < prices.size(); i++)
			{
				Date fromDate = prices.get(i).getDate();
				Date toDate = prices.get(i).getTodate();
				
				/* Determine earliest date*/
				if(fromDate.before(earliestDate))
				{
					earliestDate = fromDate;
				}
				
				/* Determine latest date*/
				if(toDate.after(latestDate))
				{
					latestDate = toDate;
				}
			}
			
			numDaysAhead = Model.getDuration(earliestDate, latestDate);
		}
		
		double progress = numDaysAhead < 180 ? numDaysAhead * 100.0/180.0 : 100.0;
		
		return progress * priceWeightage;
	}
	
	private double calculateYieldProgress()
	{
		Yield yield = new Yield();
		yield.setEntityid(propertyId);
		yield.setEntitytype("Product");
		
		int ruleCount = sqlSession.getMapper(YieldMapper.class).countbyentity(yield);
		
		/* set full progress if there are more than 4 rules*/
		double progress = ruleCount < 5 ?  ruleCount * 100.0/5.0 : 100.0;
		
		return progress * yieldWeightage;
	}
	
	public void updateRatingForPropertyId(String propertyId)
	{
		sqlSession = RazorServer.openSession();
		this.propertyId = propertyId;
		this.product = sqlSession.getMapper(ProductMapper.class).read(propertyId);
		
		double progress = calculateProgress();
		
		//Query for updating rating to be implemented
		
		sqlSession.close();
	}
	
	public double calculateProgress()
	{
		double progress = 0.0;
		
		progress += calculateDataProgress();
		progress += calculateAttributeProgress();
		progress += calculateDescriptionProgress();
		progress += calculateContentsProgress();
		progress += calculateOptionsProgress();
		progress += calculateMapProgress();
		progress += calculateImageProgress();
		progress += calculatePriceProgress();
		progress += calculateYieldProgress();
		
		return progress;
	}
}
