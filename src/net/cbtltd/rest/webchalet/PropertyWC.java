package net.cbtltd.rest.webchalet;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class PropertyWC {
	
	  private List<String> amenities;
	  private int bathrooms;
	  private int bedrooms;
	  private String description;
	  private String external_id;
	  private String name;
	  private LocationWC location;
	  private List<String> photos;
	  private PriceWC price;
	  private List<RateWC> rate_table;
	  private int sleeps;
	  private List<String> tags;
	  private String url;
	  private String property_manager;
	  
	  
	  public int getBathrooms()
      {
         return this.bathrooms;
      }
	  
	  public int getBedrooms()
      {
         return this.bedrooms;
      }
	  
	  public String getDesription(){
		  return this.description;
	  }
	  
	  public String getPropertyID(){
		  return this.external_id;
	  }
	  
	  public String getName(){
		  return this.name;
	  }
	  
	  public List<String> getAmenities(){
		  return this.amenities;
	  }
	  
	  public LocationWC getLocation(){
		  return this.location;
	  }
	  
	  public List<String> getPhotos(){
		  return this.photos;
	  }
	  
	  public PriceWC getPrice(){
		  return this.price;
	  }
	  
	  public List<RateWC> getRate(){
		  return this.rate_table;
	  }
	  
	  public int getNumberRates(){
		  return this.rate_table.size();
	  }
	  
	  public Date getMinimumDate() throws ParseException{
		  int numberRates = this.getNumberRates();
		  Date minDate = null;
		  if(numberRates>0)
			  minDate = this.rate_table.get(0).getStartDate();  
		  for(int i=1;i<numberRates;i++){  
			  if(this.rate_table.get(i).getStartDate().compareTo(minDate)<0 ){  
				  minDate = this.rate_table.get(i).getStartDate();  
			  }  
		  } 		   
		  return minDate;
	  } 
	  
	  public Date getMaximumDate() throws ParseException{
		  int numberRates = this.getNumberRates();
		  Date maxDate = null;
		  if(numberRates>0)
			  maxDate = this.rate_table.get(0).getEndDate();  
		  for(int i=1;i<numberRates;i++){  
		     if(this.rate_table.get(i).getEndDate().compareTo(maxDate)>0 ){  
		    	 maxDate = this.rate_table.get(i).getEndDate();  
		     }  
		  }  
		  return maxDate;
	  } 
	  
	  public int getSleeps(){
		  return this.sleeps;
	  }
	  
	  public List<String> getTags(){
		  return this.tags;
	  }
      
	  public String getURL(){
		  return this.url;
	  }
	  
	  public String getPropertyManager(){
		  return this.property_manager;
	  }
	 
	
}
