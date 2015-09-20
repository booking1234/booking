package net.cbtltd.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.script.Handleable;
import net.cbtltd.shared.Location;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderAddressComponent;
import com.google.code.geocoder.model.GeocoderGeometry;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderResultType;
import com.google.code.geocoder.model.GeocoderStatus;
import com.google.code.geocoder.model.LatLng;

public final class GoogleLocationProcessor implements Handleable {
	
	private static final Logger LOG = Logger.getLogger(GoogleLocationProcessor.class.getName());
	private static final Geocoder geocoder = new Geocoder();
	
	private static final String POST_TOWN = "postal_town";
	
	public static void main() throws Exception {
		LOG.error("Check Location database. Status: started");
		SqlSession sqlSession = RazorServer.openSession();
		StringBuilder sb = new StringBuilder();
		GeocodeResponse geocoderResponse;
		GeocoderRequest geocoderRequest;
		ArrayList<Location> locations;
		//Location prevLocation = new Location();
		
/*		geocoderRequest = new GeocoderRequestBuilder().setAddress("Paris,FR").setLanguage("en").getGeocoderRequest();
		GeocodeResponse geoResp = geocoder.geocode(geocoderRequest);
		int c = geoResp.getResults().size();*/
		
		String currCountry = null;
		String prevCountry = null;
		String currName = null;
		String prevName = null;
		String currID = null;
		String rs = null;
		int count;
		int i = 0;
		try {
/*			File file = new File("d:/MissingLocations.txt");
			FileOutputStream fop = new FileOutputStream(file);
			if (!file.exists()) file.createNewFile();*/
/*			final AdvancedGeoCoder advancedGeoCoder = new AdvancedGeoCoder();
			advancedGeoCoder.getHttpClient().getHostConfiguration();*/
			
			locations = sqlSession.getMapper(LocationMapper.class).readByGName();
			for (Location location : locations) {
					currCountry = location.getCountry();
					currName = location.getName();
					currID = location.getId();
					i++;
					// URL - https://maps.googleapis.com/maps/api/geocode/json?address=*,*&sensor=false
					if (currCountry.equals(prevCountry) && currName.equals(prevName)) {
						if (currCountry.equals("US")) {geocoderRequest = new GeocoderRequestBuilder().setAddress(currName + ", " + location.getRegion() + ", " + currCountry).setLanguage("en").getGeocoderRequest();} 
							else {
							geocoderRequest = new GeocoderRequestBuilder().setAddress(currName + ", " + currCountry).setLanguage("en").getGeocoderRequest();
							try {
							    Thread.sleep(250);
							} catch(InterruptedException ex) {
							    Thread.currentThread().interrupt();
							}
							if ((geocoderResponse = geocoder.geocode(geocoderRequest)) == null) {LOG.error("Error from server Location: ID=" + currID + " Name=" + currName + " Country="  + currCountry);continue;}
							
							GeocoderStatus status = geocoderResponse.getStatus();
							if (status == GeocoderStatus.OK) {
								count = getCountLoc(geocoderResponse);
								if (count == 0) location.setState("Mismatch");
								else if (count == 1) location.setState(Location.DEPRECATED);
								else if (count > 1) location.setState("Conflict");
							} else if (status == GeocoderStatus.ZERO_RESULTS) {location.setState("Missing");}
							else if (status == GeocoderStatus.OVER_QUERY_LIMIT) {
								LOG.error("You have reached query limit. Sorry.");
								break;
							}
							else LOG.error("Something went wrong... Status: " + status);	
							
							sqlSession.getMapper(LocationMapper.class).update(location);
							sqlSession.commit();
							
							rs = i + " Duplicate Location: ID=" + currID + " Name=" + currName + " Country="  + currCountry;
							LOG.error(rs);
							sb.append(rs + "\n");
							continue;
							}
					} else geocoderRequest = new GeocoderRequestBuilder().setAddress(currName + ", " + currCountry).setLanguage("en").getGeocoderRequest();
					try {
					    Thread.sleep(250);
					} catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					}
					if ((geocoderResponse = geocoder.geocode(geocoderRequest)) == null) {LOG.error("Error from server Location: ID=" + currID + " Name=" + currName + " Country="  + currCountry);continue;}
					
					GeocoderStatus status = geocoderResponse.getStatus();
					if (status == GeocoderStatus.OK) {
						List<GeocoderResult> results = geocoderResponse.getResults();
						count = getCountLoc(geocoderResponse);
						if (count > 1) {
							//two or more location types found
							location.setState("Conflict");
							rs = i + " Two or more instances found for Location: ID=" + currID + " Name=" + currName + " Country="  + currCountry;
							sb.append(rs + "\n");
						}
						else if (count == 1) {
							location.setState(Location.CREATED);
							for (GeocoderResult result : results){
								GeocoderGeometry geo = result.getGeometry();
								location.setLatitude(geo.getLocation().getLat().doubleValue());
								location.setLongitude(geo.getLocation().getLng().doubleValue());
								
								List<GeocoderAddressComponent> components = result.getAddressComponents();
								for (GeocoderAddressComponent component : components){
									String types = component.getTypes().toString();
									if (types.startsWith("[locality,")){location.setGname(component.getLongName());}
									if (types.startsWith("[administrative_area_level_1,")){location.setAdminarea_lvl_1(component.getLongName());}
							    	if (types.startsWith("[administrative_area_level_2,")){location.setAdminarea_lvl_2(component.getLongName());}
							    }
							}
							rs = i + " Location: ID=" + currID + " Name=" + currName + " Country="  + currCountry + " updated with " + location.getGname() + "--" + location.getAdminarea_lvl_1() + ":" + location.getAdminarea_lvl_2();						
						}
						else if (count == 0) {
							location.setState("Mismatch");
							rs = i + " Mismatch Location: ID=" + currID + " Name=" + currName + " Country="  + currCountry;
							sb.append(rs + "\n");
						}
						else rs = "Something went wrong... Status: " + status;
					}
					else if (status == GeocoderStatus.ZERO_RESULTS){
						location.setState("Missing");
						rs = i + " Match not found for Location: ID=" + currID + " Name=" + currName + " Country="  + currCountry;
						sb.append(rs + "\n");
					}
					else if (status == GeocoderStatus.OVER_QUERY_LIMIT) {
						LOG.error("You have reached query limit. Sorry.");
						break;
					}
					else rs = "Something went wrong... Status: " + status;

					sqlSession.getMapper(LocationMapper.class).update(location);
					sqlSession.commit();
					prevCountry = currCountry;
					prevName = currName;
					LOG.error(rs);
			}
/*			byte[] contentInBytes = sb.toString().getBytes();
			fop.write(contentInBytes);
			fop.flush();
			fop.close();*/
		}
		catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace();
			LOG.error("Check Location database. Status: terminated");
		}
		finally {sqlSession.close();}
		LOG.error("Check Location database. Status: finished");
	}
	
	/**
	 * Counts total *locality* match of each result type 
	 * @param geocoderResponse
	 * @return count
	 */
	private final static int getCountLoc (GeocodeResponse geocoderResponse) {
		int count = 0;
		//searches for each type in each result
/*		List<GeocoderResult> results = geocoderResponse.getResults();
		for (GeocoderResult result : results){
			List<GeocoderAddressComponent> components = result.getAddressComponents();
			for (GeocoderAddressComponent component : components){
				String types = component.getTypes().toString();
				if (types.startsWith("[locality,")){count++;}
				}*/
		//searches only for result type
		List<GeocoderResult> results = geocoderResponse.getResults();
		for (GeocoderResult result : results){
			if (result.getTypes().toString().contains("[locality, political]")){count++;}
		}
		return count;		
	}
	
	public static final Location getGoogleLocation(SqlSession sqlSession, String name, String region, String country) {
		Location location = new Location();
		GeocodeResponse geocoderResponse;
		GeocoderRequest geocoderRequest;
		int count;
		try {
			location.setName(name);
			location.setCountry(country);
			location.setState(Location.CREATED);

			if (country.equals("US")) {
				geocoderRequest = new GeocoderRequestBuilder().setAddress(name + ", " + region + ", " + country).setLanguage("en").getGeocoderRequest();
				//location.setRegion(region);
			} else {geocoderRequest = new GeocoderRequestBuilder().setAddress(name + ", " + country).setLanguage("en").getGeocoderRequest();}
			try {
			    Thread.sleep(250);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}	
				
		if ((geocoderResponse = geocoder.geocode(geocoderRequest)) != null) {
				GeocoderStatus status = geocoderResponse.getStatus();
				if (status == GeocoderStatus.OK) {
					List<GeocoderResult> results = geocoderResponse.getResults();
					count = results.size();
					
					if (count == 1) {
						GeocoderResult result = results.get(0);
						GeocoderGeometry geo = result.getGeometry();
						location.setLatitude(geo.getLocation().getLat().doubleValue());
						location.setLongitude(geo.getLocation().getLng().doubleValue());
						String locationtype = result.getTypes().toString();
						String[] type = locationtype.split(",");
						location.setLocationtype(type[0].replace("[", ""));
						List<GeocoderAddressComponent> components = result.getAddressComponents();
						for (GeocoderAddressComponent component : components){
							String types = component.getTypes().toString();
							if (types.startsWith(locationtype)){location.setGname(component.getLongName());}
							if (types.startsWith("[administrative_area_level_1,")){location.setAdminarea_lvl_1(component.getLongName());}
						   	if (types.startsWith("[administrative_area_level_2,")){location.setAdminarea_lvl_2(component.getLongName());}
						}	
						ArrayList<Location> exists = sqlSession.getMapper(LocationMapper.class).googleExists(location);
						if (exists.size() == 1) {
							return exists.get(0);
						} else if (exists.size() > 1 && location.getGname() != null) {
							for (Location exist : exists) {
								exist.setState("Conflict");
								sqlSession.getMapper(LocationMapper.class).update(exist);
							}
						}
					}
				} else if (status == GeocoderStatus.OVER_QUERY_LIMIT) {
					LOG.error("You have reached query limit. Sorry.");
				}
			}
		}
		catch (Throwable x) {
			sqlSession.rollback();
			x.printStackTrace(); 
			}
		location.setState("Conflict");
		sqlSession.getMapper(LocationMapper.class).create(location);
		return location;
	}
	
	public static final Location getGoogleLocation(String name, String region, double latitude, double longitude, String zipCode, String country) throws GoogleLocationLimitException {
		Location location = new Location();
		GeocodeResponse geocoderResponse;
		GeocoderRequest geocoderRequest;
		int count;
		
		location.setName(name);
		location.setCountry(country);
		location.setState(Location.CREATED);

		if (country.equals("US")) {
			geocoderRequest = new GeocoderRequestBuilder().setAddress(name + ", " + region + ", " + country).setLanguage("en").getGeocoderRequest();
			location.setRegion(region);
		} else {
			geocoderRequest = new GeocoderRequestBuilder().setAddress(name + ", " + country).setLanguage("en").getGeocoderRequest();
		}
		try {
		    Thread.sleep(250);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}	
				
		if ((geocoderResponse = geocoder.geocode(geocoderRequest)) != null) {
			GeocoderStatus status = geocoderResponse.getStatus();
			if (status == GeocoderStatus.OK) {
				List<GeocoderResult> results = geocoderResponse.getResults();
				count = results.size();
				Map<String, Location> map = new HashMap<String, Location>();	
				if (count >= 1) {
					for (GeocoderResult result : geocoderResponse.getResults()) {
						Location gLocation = GoogleLocationProcessor.getLocationFromResponse(location, result);
						map.put(gLocation.getLocationtype(), gLocation);
					}
					location = GoogleLocationProcessor.selectLocation(map);
					LOG.debug("Location found by place name look up: " + location);
				}
				else {
					location = GoogleLocationProcessor.getGoogleLocation(zipCode, latitude, longitude, country);
				}
			} 
			else if(status == GeocoderStatus.ZERO_RESULTS) {
				location = GoogleLocationProcessor.getGoogleLocation(latitude, longitude, country);
			}
			else if (status == GeocoderStatus.OVER_QUERY_LIMIT) {
				LOG.error("You have reached query limit. Sorry.");
				throw new  GoogleLocationLimitException("You have reached API limit.");
			}
		}
		return location;
	}
	
	public static final Location getGoogleLocation(String name, String region, double latitude, double longitude, String country) throws GoogleLocationLimitException {
		Location location = new Location();
		GeocodeResponse geocoderResponse;
		GeocoderRequest geocoderRequest;
		int count;
		
		location.setName(name);
		location.setCountry(country);
		location.setState(Location.CREATED);

		if ("US".equalsIgnoreCase(country)) {
			geocoderRequest = new GeocoderRequestBuilder().setAddress(name + ", " + region + ", " + country).setLanguage("en").getGeocoderRequest();
			location.setRegion(region);
		} else {
			geocoderRequest = new GeocoderRequestBuilder().setAddress(name + ", " + country).setLanguage("en").getGeocoderRequest();
		}
		try {
		    Thread.sleep(250);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}	
				
		if ((geocoderResponse = geocoder.geocode(geocoderRequest)) != null) {
			GeocoderStatus status = geocoderResponse.getStatus();
			if (status == GeocoderStatus.OK) {
				List<GeocoderResult> results = geocoderResponse.getResults();
				count = results.size();
				Map<String, Location> map = new HashMap<String, Location>();	
				if (count >= 1) {
					for (GeocoderResult result : geocoderResponse.getResults()) {
						Location gLocation = GoogleLocationProcessor.getLocationFromResponse(location, result);
						map.put(gLocation.getLocationtype(), gLocation);
					}
					location = GoogleLocationProcessor.selectLocation(map);
					LOG.debug("Location found by place name look up: " + location);
				}
				else {
					location = GoogleLocationProcessor.getGoogleLocation(latitude, longitude, country);
				}
			} 
			else if(status == GeocoderStatus.ZERO_RESULTS) {
				location = GoogleLocationProcessor.getGoogleLocation(latitude, longitude, country);
			}
			else if (status == GeocoderStatus.OVER_QUERY_LIMIT) {
				LOG.error("You have reached query limit. Sorry.");
				throw new  GoogleLocationLimitException("You have reached API limit.");
			}
		}
		return location;
	}
	
	/**
	 * Reverse Geo lookup for lat and long
	 * @param address
	 * @return
	 * @throws GoogleLocationLimitException
	 */
	public static final Location getGoogleLocation(String address) throws GoogleLocationLimitException {
		Location location = new Location();
		GeocodeResponse geocoderResponse;
		GeocoderRequest geocoderRequest;
		int count;
		
		location.setState(Location.CREATED);

		geocoderRequest = new GeocoderRequestBuilder().setAddress(address).setLanguage("en").getGeocoderRequest();
		
		
		try {
		    Thread.sleep(250);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}	
				
		if ((geocoderResponse = geocoder.geocode(geocoderRequest)) != null) {
			GeocoderStatus status = geocoderResponse.getStatus();
			if (status == GeocoderStatus.OK) {
				List<GeocoderResult> results = geocoderResponse.getResults();
				count = results.size();
				Map<String, Location> map = new HashMap<String, Location>();	
				if (count >= 1) {
					for (GeocoderResult result : geocoderResponse.getResults()) {
						Location gLocation = GoogleLocationProcessor.getLocationFromResponse(location, result);
						map.put(gLocation.getLocationtype(), gLocation);
					}
					location = GoogleLocationProcessor.selectLocation(map);
					LOG.debug("Location found by place name look up: " + location);
				}
				
			} 
			else if(status == GeocoderStatus.ZERO_RESULTS) {
				location = null;
			}
			else if (status == GeocoderStatus.OVER_QUERY_LIMIT) {
				LOG.error("You have reached query limit. Sorry.");
				throw new  GoogleLocationLimitException("You have reached API limit.");
			}
		}
		return location;
	}
	
	public static final Location getGoogleLocation(String zipCode, double latitude, double longitude, String country) throws GoogleLocationLimitException {
		// first look up the location by lat/long
		// if it does not return the results than try looking up by zip code
		Location location = GoogleLocationProcessor.getGoogleLocation(latitude, longitude, country);
		if(location == null || StringUtils.isBlank(location.getGname())) {
			GeocodeResponse geocoderResponse;
			GeocoderRequest geocoderRequest;
					
			geocoderRequest = new GeocoderRequestBuilder().setAddress(zipCode + ", " + country).setLanguage("en").getGeocoderRequest();
			
			try {
			    Thread.sleep(250);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}	
			
			if ((geocoderResponse = geocoder.geocode(geocoderRequest)) != null) {
				GeocoderStatus status = geocoderResponse.getStatus();
				
				if (status == GeocoderStatus.OK) {
					location = new Location();
					location.setCountry(country);
					location.setState(Location.CREATED);
	
					List<GeocoderResult> results = geocoderResponse.getResults();
					int count = results.size();
					Map<String, Location> map = new HashMap<String, Location>();
					if (count >= 1) {
						for (GeocoderResult result : geocoderResponse.getResults()) {
							Location gLocation = GoogleLocationProcessor.getLocationFromResponse(location, result);
							map.put(gLocation.getLocationtype(), gLocation);
						}
						location = GoogleLocationProcessor.selectLocation(map);
					}
					else {
						LOG.debug("Looking up using lat/long.: " + latitude + " " + longitude);
						location = GoogleLocationProcessor.getGoogleLocation(latitude, longitude, country);
					}
				} 
				else if (status == GeocoderStatus.OVER_QUERY_LIMIT) {
					LOG.error("You have reached query limit. Sorry.");
					throw new  GoogleLocationLimitException("You have reached API limit.");
				}
			
			}
		}
		LOG.debug("Location found by zipCode/Geo look up: " + location);
		return location;
	}
	
	public static final Location getGoogleLocation(double latitude, double longitude,  String country) throws GoogleLocationLimitException {
		Location location = null;
		GeocodeResponse geocoderResponse;
		GeocoderRequest geocoderRequest;
		int count;
		Map<String, Location> map = new HashMap<String, Location>();
		geocoderRequest = new GeocoderRequestBuilder().setLocation(new LatLng(new BigDecimal(latitude),new BigDecimal(longitude))).setRegion(country).setLanguage("en").getGeocoderRequest();
		
		try {
		    Thread.sleep(250);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}	
		
		if ((geocoderResponse = geocoder.geocode(geocoderRequest)) != null) {
			GeocoderStatus status = geocoderResponse.getStatus();
			
			if (status == GeocoderStatus.OK) {
				location = new Location();
				location.setCountry(country);
				location.setState(Location.CREATED);

				List<GeocoderResult> results = geocoderResponse.getResults();
				count = results.size();
				
				if (count >= 1) {
					for (GeocoderResult result : geocoderResponse.getResults()) {
						Location gLocation = GoogleLocationProcessor.getLocationFromResponse(location, result);
						if(!map.containsKey(gLocation.getLocationtype())) {
							map.put(gLocation.getLocationtype(), gLocation);
						}
					}
				}
			} 
			else if (status == GeocoderStatus.OVER_QUERY_LIMIT) {
				LOG.error("You have reached query limit. Sorry.");
				throw new  GoogleLocationLimitException("You have reached API limit.");
			}
		}
		
		return GoogleLocationProcessor.selectLocation(map);
	}
	
	/**
	 * 
	 * @param map
	 * @return Locality, Postal Code, Sublocality, Neighbourhood, Street Address in that order
	 */
	public static Location selectLocation(Map<String, Location> map) {
		Location location = null;
		if(map.get(GeocoderResultType.SUBLOCALITY.value()) != null)
			location = map.get(GeocoderResultType.SUBLOCALITY.value());
		else if(map.get(GeocoderResultType.LOCALITY.value()) != null)
			location = map.get(GeocoderResultType.LOCALITY.value());
		else if(map.get(GeocoderResultType.ADMINISTRATIVE_AREA_LEVEL_3.value()) != null)
			location = map.get(GeocoderResultType.ADMINISTRATIVE_AREA_LEVEL_3.value());
		else if(map.get(POST_TOWN) != null)
			location = map.get(POST_TOWN);
		else if(map.get(GeocoderResultType.POSTAL_CODE.value()) != null)
			location = map.get(GeocoderResultType.POSTAL_CODE.value());
		else if(map.get(GeocoderResultType.SUBLOCALITY.value()) != null)
			location = map.get(GeocoderResultType.SUBLOCALITY.value());
		else if(map.get(GeocoderResultType.NEIGHBORHOOD.value()) != null)
			location = map.get(GeocoderResultType.NEIGHBORHOOD.value());
		else if(map.get(GeocoderResultType.STREET_ADDRESS.value()) != null)
			location = map.get(GeocoderResultType.STREET_ADDRESS.value());
		else if(map.get(GeocoderResultType.PREMISE.value()) != null)
			location = map.get(GeocoderResultType.PREMISE.value());
		else 
			location = null;    ///ignore all other results...we can lookup others if we decide to build POI db 
		return location;
	}
	
	private static Location getLocationFromResponse(final Location location, GeocoderResult result) {
		
		Location clone = new GeoLocation().clone(location);
		if(result.getTypes().size() > 0)
			clone.setLocationtype(result.getTypes().get(0));
		else
			clone.setLocationtype("Partial Match");
		for (GeocoderAddressComponent component : result.getAddressComponents()){
			String types = component.getTypes().toString();
			if (types.contains(GeocoderResultType.SUBLOCALITY.value())) {	
				clone.setLatitude(result.getGeometry().getLocation().getLat().doubleValue());
				clone.setLongitude(result.getGeometry().getLocation().getLng().doubleValue());
				clone.setGname(component.getLongName());
				clone.setLocationtype(GeocoderResultType.SUBLOCALITY.value().toLowerCase());
			}
			else if (types.contains(GeocoderResultType.LOCALITY.value())) {	
				clone.setLatitude(result.getGeometry().getLocation().getLat().doubleValue());
				clone.setLongitude(result.getGeometry().getLocation().getLng().doubleValue());
				clone.setGname(component.getLongName());
				if(clone.getLocationtype() == null || !clone.getLocationtype().equals(GeocoderResultType.SUBLOCALITY.value().toLowerCase()))
					clone.setLocationtype(GeocoderResultType.LOCALITY.value().toLowerCase());
			}
			if (types.contains(GoogleLocationProcessor.POST_TOWN)) {	
				clone.setLatitude(result.getGeometry().getLocation().getLat().doubleValue());
				clone.setLongitude(result.getGeometry().getLocation().getLng().doubleValue());
				clone.setGname(component.getLongName());
			}
			if (types.contains(GeocoderResultType.ADMINISTRATIVE_AREA_LEVEL_1.value())) {
				clone.setAdminarea_lvl_1(component.getLongName());
			}
		   	if (types.contains(GeocoderResultType.ADMINISTRATIVE_AREA_LEVEL_2.value())) {
		   		clone.setAdminarea_lvl_2(component.getLongName());
		   	}
		   	if (types.contains(GeocoderResultType.ADMINISTRATIVE_AREA_LEVEL_3.value())) {
		   		if(StringUtils.isBlank(clone.getGname())) {
		   			clone.setLatitude(result.getGeometry().getLocation().getLat().doubleValue());
		   			clone.setLongitude(result.getGeometry().getLocation().getLng().doubleValue());
		   			clone.setGname(component.getLongName());
		   		}
		   	}
		   	if (types.contains(GeocoderResultType.COUNTRY.value())) {
		   		clone.setCountry(component.getShortName());
		   	}
		   	if (types.contains(GeocoderResultType.POSTAL_CODE.value())) {
		   		clone.setZipCode(component.getShortName());
		   	}
		}
		return clone;
	}
	
	/** 
	 * calculates the distance between 
	 * two locations in MILES 
	 * 
	 */
	public static double distance(double lat1, double lng1, double lat2, double lng2) {

	    double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);

	    double sindLat = Math.sin(dLat / 2);
	    double sindLng = Math.sin(dLng / 2);

	    double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
	        * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

	    double dist = earthRadius * c;

	    return dist; // output distance, in MILES
	}
	
	@Override
	public void readProducts() {
		try {main();} catch (Exception e) {e.printStackTrace();}
	}

	@Override
	public void readPrices() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readSchedules() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readAlerts() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void readLocations() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readSpecials() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readDescriptions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readImages() {
		// TODO Auto-generated method stub
		
	}
}