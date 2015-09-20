package net.cbtltd.shared;

import org.apache.log4j.Logger;

import net.cbtltd.server.cron4j.SchedulingPattern;

public final class ProductUpdateInterval {
	private static final Logger LOG = Logger.getLogger(ProductUpdateInterval.class
			.getName());
	static final String DEFAULT_PATTERN="* 1 * * *";
	static final String IMAGEDATA="imagedata";
	static final String PRICEDATA="pricedata";
	static final String BOOKEDDATE="bookeddate";
	static final String MINSTAY="minstay";
	static final String DESCRIPTION="description";
	static final String ATTRIBUTE="attribute";

	public static final ProductUpdateInterval imageDataUpdateInterval=new ProductUpdateInterval(IMAGEDATA,DEFAULT_PATTERN);
	public static final ProductUpdateInterval priceDataUpdateInterval=new ProductUpdateInterval(PRICEDATA,DEFAULT_PATTERN);
	public static final ProductUpdateInterval bookedDateUpdateInterval=new ProductUpdateInterval(BOOKEDDATE,DEFAULT_PATTERN);
	public static final ProductUpdateInterval minStayUpdateInterval=new ProductUpdateInterval(MINSTAY,DEFAULT_PATTERN);
	public static final ProductUpdateInterval descriptionUpdateInterval=new ProductUpdateInterval(DESCRIPTION,DEFAULT_PATTERN);
	public static final ProductUpdateInterval attributeUpdateInterval=new ProductUpdateInterval(ATTRIBUTE,DEFAULT_PATTERN);
	public ProductUpdateInterval() {
	}
	public static ProductUpdateInterval getInstance(String field){
		if(field.equalsIgnoreCase(IMAGEDATA)) return imageDataUpdateInterval;
		if(field.equalsIgnoreCase(PRICEDATA)) return priceDataUpdateInterval;
		if(field.equalsIgnoreCase(BOOKEDDATE)) return bookedDateUpdateInterval;
		if(field.equalsIgnoreCase(MINSTAY)) return minStayUpdateInterval;
		if(field.equalsIgnoreCase(DESCRIPTION)) return descriptionUpdateInterval;
		if(field.equalsIgnoreCase(ATTRIBUTE)) return attributeUpdateInterval;
		return new ProductUpdateInterval(field,DEFAULT_PATTERN );
	}
	public static void setInstance(ProductUpdateInterval productUpdateInterval){
		ProductUpdateInterval paramter=getInstance(productUpdateInterval.getField());
		paramter.setCronString(productUpdateInterval.getCronString());
		try{
			paramter.isRequiredUpdate = new SchedulingPattern(productUpdateInterval.getCronString()).match(new java.util.Date().getTime());
		}catch(Exception e){
			e.printStackTrace();
		}
		LOG.info("### Parameter update for "+paramter.field+"("+paramter.cronString+") will "+(paramter.isRequiredUpdate?"be updated ":"not be updated"));
	}
	
 private ProductUpdateInterval(String field, String daysInterval) {
		super();
		this.field = field;
		this.cronString = daysInterval;
	}
private String field;
private String cronString;
private boolean isRequiredUpdate=true;
/**
 * @return the field
 */
public String getField() {
	return field;
}
/**
 * @param field the field to set
 */
public void setField(String field) {
	this.field = field;
}

/**
 * @return the cronString
 */
public String getCronString() {
	return cronString;
}
/**
 * @param cronString the cronString to set
 */
public void setCronString(String cronString) {
	this.cronString = cronString;
}
/**
 * @return the isRequiredUpdate
 */
public boolean isRequiredUpdate() {
	return isRequiredUpdate;
}
 
}
