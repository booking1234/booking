/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.DatatypeConverter;

import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.GoogleLocationLimitException;
import net.cbtltd.rest.GoogleLocationProcessor;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.server.api.AdjustmentMapper;
import net.cbtltd.server.api.CountryCodeMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.PropertyMinStayMapper;
import net.cbtltd.server.api.RegionCodeMapper;
import net.cbtltd.server.api.RegionMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.api.TaxMapper;
import net.cbtltd.server.api.YieldMapper;
import net.cbtltd.server.cron4j.Scheduler;
import net.cbtltd.server.project.PartyIds;
import net.cbtltd.shared.Adjustment;
import net.cbtltd.shared.CountryCode;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.MinStay;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Region;
import net.cbtltd.shared.RegionCode;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.Tax;
import net.cbtltd.shared.Yield;
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.partner.PartnerCreate;
import net.cbtltd.shared.partner.PartnerDelete;
import net.cbtltd.shared.partner.PartnerRead;
import net.cbtltd.shared.partner.PartnerTable;
import net.cbtltd.shared.partner.PartnerUpdate;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.allen_sauer.gwt.log.client.Log;
import com.allen_sauer.gwt.voices.client.util.StringUtil;
import com.google.code.geocoder.model.GeocoderResultType;
import com.mybookingpal.config.RazorConfig;
import com.mybookingpal.utils.ISO8859CharacterNormalizer;

/** The Class PartnerService records partner API settings and support functions. */
public class PartnerService
implements IsService {

	private static final Logger LOG = Logger.getLogger(PartnerService.class.getName());
	private static final ArrayList<Scheduler> schedulers = new ArrayList<Scheduler>();
	private static Map<String,String> countryCodes;
	private static Map<String,String> regionCodes;
	private static PartnerService service;
	private static final Hashtable<String, IsPartner> HANDLERS = new Hashtable<String, IsPartner>();
    
	private static        String[]  partnerlivepricingids ;
	private static final  String[]  livePricingIds = {HasUrls.ABBREVATION_INTERHOME_AG, HasUrls.ABBREVATION_NEXTPAX_MAIN_ACCOUNT, HasUrls.ABBERVATION_DANCENTER_NEXTPAX,
		HasUrls.ABBERVATION_HOGENBOOM_NEXTPAX, HasUrls.ABBERVATION_HAPPYHOME_NEXTPAX, HasUrls.ABBERVATION_GRANALACANT_NEXTPAX, HasUrls.ABBREVATION_HOLIDAYHOME_NEXTPAX,
		HasUrls.ABBREVATION_INTERCHALET_NEXTPAX, HasUrls.ABBREVATION_INTERHOME_NEXTPAX, HasUrls.ABBREVATION_NOVASOL_NEXTPAX, HasUrls.ABBREVATION_ROOMPOT_NEXTPAX,
		HasUrls.ABBREVATION_TOPICTRAVEL_NEXTPAX, HasUrls.ABBREVATION_TUIFERIENHAUS_NEXTPAX, HasUrls.ABBREVATION_UPHILLTRAVEL_NEXTPAX, HasUrls.ABBREVATION_VACASOL_NEXTPAX,
		HasUrls.ABBREVATION_BELVILLA_NEXTPAX, HasUrls.ABBREVATION_BUNGALOWNET_NEXTPAX, HasUrls.ABBREVATION_LAKE_TAHOE_ACCOMODATIONS, HasUrls.ABBREVATION_MAXXTON,
		HasUrls.ABBREVATION_RENTAL_UNITED, HasUrls.ABBREVATION_CIIRUS, HasUrls.ABBREVATION_RESORT_MANAGEMENT_SYSTEMS,
		HasUrls.ABBREVATION_INTERHOME_PATHWAY, HasUrls.ABBREVATION_STREAMLINE, HasUrls.ABBREVATION_LODGIX, HasUrls. ABBREVATION_LEISURELINK, HasUrls.ABBREVATION_BAREFOOT, HasUrls.ABBREVATION_APARTSMENT_APART, HasUrls.ABBREVATION_HOMEAWAY_ISI};
	 /*
	 * @return single instance of PartnerService
	 */
	public static synchronized PartnerService getInstance() {
		if (service == null) {service = new PartnerService();}
		return service;
	}

	public static Map<String,String> getCountryCodes(){
		if (countryCodes == null) {
			countryCodes = new HashMap<String, String>();
			SqlSession sqlSession = null;
			try {
				sqlSession = RazorServer.openSession();

				CountryCodeMapper mapper = sqlSession
						.getMapper(CountryCodeMapper.class);
				for (CountryCode code : mapper.getCountryCodes()) {
					countryCodes.put(code.getCountryString().toLowerCase(),
							code.getCountryCode());
				}
			} catch (Exception e) {
				Log.error("DB Exception fetching country codes ", e);
			} finally {
				if (sqlSession != null)
					sqlSession.close();
			}
		}
		return countryCodes;
	}
	
	public static String getCountryCode(String countryName){
		
		if(countryName!=null){
			countryName=countryName.toLowerCase();
		}else{
			return null;
		}
		if(PartnerService.getCountryCodes().containsKey(countryName)){
			return PartnerService.getCountryCodes().get(countryName);
		}
		return countryName.toUpperCase();
	}
	public static Map<String,String> getRegionCodes(){
		if (regionCodes == null) {
			regionCodes = new HashMap<String, String>();
			SqlSession sqlSession = null;
			try {
				sqlSession = RazorServer.openSession();

				RegionCodeMapper mapper = sqlSession
						.getMapper(RegionCodeMapper.class);
				for (RegionCode code : mapper.getRegionCodes()) {
					regionCodes.put(code.getRegionName().toLowerCase(),
							code.getRegionCode());
				}
			} catch (Exception e) {
				Log.error("DB Exception fetching country codes ", e);
			} finally {
				if (sqlSession != null)
					sqlSession.close();
			}
		}
		return regionCodes;
	}
	
	public static String getRegionCode(String regionName){
		if(regionName!=null){
			regionName=regionName.toLowerCase();
		}else{
			return null;
		}
		if(PartnerService.getRegionCodes().containsKey(regionName)){
			return PartnerService.getRegionCodes().get(regionName);
		}
		if(regionName.length()==2) return regionName;
		return null;
	}
	
	public static String[] getPartnerlivePricingIds(){
		
		if(partnerlivepricingids== null)
		{
			partnerlivepricingids = RazorServer.openSession().getMapper(PartnerMapper.class).getPartnerPartyIDByAbbreviation(livePricingIds).toArray(new String[0]);
		}
		return partnerlivepricingids;
	}
	/**
	 * Gets the input stream with no SSL certificate.
	 * 
	 * @see http://vmustafayev4en.blogspot.com/2012/05/avoiding-javaxnetsslsslhandshakeexcepti.html
	 * @see javax.net.ssl.SSLHandshakeException
	 *
	 * @param urlname the URL of the service.
	 * @param apikey the API key in username:password format.
	 * @return the input stream.
	 * @throws Throwable the exception if not successful - eg not 200
	 */
	public static InputStream getInputStreamNoSSL(String urlname, String apikey, String rq) throws Throwable {
		TrustManager[] trustAllCerts = new TrustManager[] {  
				new X509TrustManager() {  
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
						return null;  
					}  

					public void checkClientTrusted(  
							java.security.cert.X509Certificate[] certs, String authType) {  
					}  

					public void checkServerTrusted(  
							java.security.cert.X509Certificate[] certs, String authType) {  
					}  
				}  
		}; 

		// Install the all-trusting trust manager  
		SSLContext sc = SSLContext.getInstance("SSL");  
		sc.init(null, trustAllCerts, new java.security.SecureRandom());  
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());  

		HostnameVerifier hv = new HostnameVerifier() {  
			public boolean verify(String urlHostName, SSLSession session) {  
				LOG.debug("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
				return true;  
			}
		};

		URL url = new URL(urlname);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();  
		connection.setHostnameVerifier(hv);
		connection.setDoOutput(true);
		String encoding = DatatypeConverter.printBase64Binary(apikey.getBytes());
		connection.setRequestProperty("Authorization", "Basic " + encoding);
		if (rq == null) {connection.setRequestMethod("GET");}
		else {
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/xml");
			OutputStream os = connection.getOutputStream();
			os.write(rq.getBytes());
			os.flush();
		}
		return connection.getInputStream();
	}

	/**
	 * Gets the input stream with HTTP GET.
	 *
	 * @param urlname the URL of the service.
	 * @param apikey the API key in username:password format.
	 * @return the input stream.
	 * @throws Throwable the exception if not successful - eg not 200
	 */
	public static InputStream getInputStream(String urlname, String apikey) throws Throwable {
		GetMethod get = new GetMethod(urlname);
		String encoding = DatatypeConverter.printBase64Binary(apikey.getBytes());
		get.addRequestHeader("Authorization", "Basic " + encoding);
		get.addRequestHeader("Accept" , "application/xml");
		HttpClient httpclient = new HttpClient();
		int rs = httpclient.executeMethod(get); //TODO: throw exception if not 200
		return get.getResponseBodyAsStream();
	}

	/**
	 * Returns the response stream with HTTP POST.
	 *
	 * @param urlname the URL of the service.
	 * @param apikey the API key in username:password format.
	 * @param rq the request string.
	 * @return the input stream.
	 * @throws Throwable the exception if not successful - eg not 200
	 */	
	public static InputStream setInputStream(String urlname, String apikey, String rq) throws Throwable {
		PostMethod post = new PostMethod(urlname);
		String encoding = DatatypeConverter.printBase64Binary(apikey.getBytes());
		post.addRequestHeader("Authorization", "Basic " + encoding);
		post.addRequestHeader("Accept" , "application/xml");
		RequestEntity entity = new StringRequestEntity(rq, "application/xml", null);
		post.setRequestEntity(entity);

		HttpClient httpclient = new HttpClient();
		int rs = httpclient.executeMethod(post); //TODO: throw exception if not 200
		return post.getResponseBodyAsStream();
	}

	/**
	 * Gets the request handler for a party.
	 *
	 * @param sqlSession the current SQL session.
	 * @param altpartyid the alternate party using the API.
	 * @return the request handler.
	 */
	private static synchronized IsPartner getRequestHandler (SqlSession sqlSession, String altpartyid) {
		if (HANDLERS.containsKey(altpartyid)) {return HANDLERS.get(altpartyid);}
		else {return getRequestHandler(sqlSession.getMapper(PartnerMapper.class).exists(altpartyid));}
	}

	/**
	 * Gets the request handler and registers it if it does not exist.
	 *
	 * TODO: make handler registration more dynamic if possible
	 * @param sqlSession the current SQL session.
	 * @param partner the alternate party using the API.
	 * @return the request handler.
	 */
	private static synchronized IsPartner getRequestHandler (Partner partner) {
		LOG.debug("getRequestHandler " + partner);
		SqlSession sqlSession = RazorServer.openSession();
		if (partner == null) {
		//	throw new ServiceException(Error.party_id, "Request Handler ID");
		}
		if (partner!=null && !HANDLERS.containsKey(partner.getPartyid())) {
			if (partner.hasOrganizationid(PartyIds.PARTY_BOOKT_ID)) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.bookt.A_Handler(partner));}
			else if (partner.hasOrganizationid(PartyIds.PARTY_CIIRUS_ID)) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.ciirus.A_Handler(partner));}
			else if (partner.hasOrganizationid(PartyIds.PARTY_INTERHOME_ID)) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.interhome.A_Handler(partner));}
			else if (partner.hasOrganizationid(PartyIds.PARTY_KIGO_ID)) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.kigo.A_Handler(partner));}
			else if (partner.hasOrganizationid(PartyIds.PARTY_LEADERSTAY_ID)) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.leaderstay.A_Handler(partner));}
			else if (partner.hasOrganizationid(PartyIds.PARTY_OPENBOOK_ID)) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.openbook.A_Handler(partner));}
			else if (partner.hasOrganizationid(PartyIds.PARTY_RCI_ID)) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.rci.A_Handler(partner));}
			else if (partner.hasOrganizationid(PartyIds.PARTY_RTR_ID)) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.rtr.A_Handler(partner));}
			else if (partner.hasOrganizationid(PartyIds.PARTY_SUMMITCOVE_ID)) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.summitcove.A_Handler(partner));}
			else if (partner.hasOrganizationid(PartyIds.PARTY_RENTALS_UNITED_ID)) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.ru.A_Handler(partner));}
			else if (partner.hasOrganizationid(PartyIds.FLIPKEY_PARTNER_ID)) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.flipkey.xmlfeed.A_Handler(partner));}
			else if (partner.hasOrganizationid(PartyIds.PARTY_LAKETAHOEACCOMODATIONS_ID)) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.laketahoeaccommodations.A_Handler(partner));}
			else if (partner.hasOrganizationid(RazorConfig.getHomeawayISIId())) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.homeaway.resco.datafeed.A_Handler(partner));}
			else if (partner.hasOrganizationid(RazorConfig.getHomeawayEscapiaId())) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.homeaway.escapianet.datafeed.A_Handler(partner));}
			else if (partner.hasOrganizationid(sqlSession.getMapper(PartnerMapper.class).partyIDFromEmail(HasUrls.PARTY_LAKETAHOEACCOMODATIONS_EMAIL))) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.laketahoeaccommodations.A_Handler(partner));}
			else if (partner.hasOrganizationid(sqlSession.getMapper(PartnerMapper.class).partyIDFromEmail(HasUrls.PARTY_MAXXTON_EMAIL))) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.maxxton.A_Handler(partner));}
			else if (partner.hasOrganizationid(sqlSession.getMapper(PartnerMapper.class).partyIDFromEmail(HasUrls.PARTY_NEXTPAX_EMAIL))) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.nextpax.A_Handler(partner));}
			else if (partner.hasOrganizationid(sqlSession.getMapper(PartnerMapper.class).partyIDFromEmail(HasUrls.PARTY_AAXSYS_EMAIL))) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.aaxsys.A_Handler(partner));}
			else if (partner.hasOrganizationid(sqlSession.getMapper(PartnerMapper.class).partyIDFromEmail(HasUrls.PARTY_STREAMLINE_EMAIL))) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.streamline.A_Handler(partner));}
			else if (partner.hasOrganizationid(sqlSession.getMapper(PartnerMapper.class).partyIDFromEmail(HasUrls.PARTY_RMS_EMAIL))) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.rms.A_Handler(partner));}
			else if (partner.hasOrganizationid(sqlSession.getMapper(PartnerMapper.class).partyIDFromEmail(HasUrls.PARTY_PATHWAY_EMAIL))) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.pathway.A_Handler(partner));}
			else if (partner.hasOrganizationid(sqlSession.getMapper(PartnerMapper.class).partyIDFromEmail(HasUrls.PARTY_WEBCHALET_EMAIL))) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.webchalet.A_Handler(partner));}
			else if (partner.hasOrganizationid(sqlSession.getMapper(PartnerMapper.class).partyIDFromEmail(HasUrls.PARTY_LODGIX_EMAIL))) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.lodgix.A_Handler(partner));}
			else if (partner.hasOrganizationid(sqlSession.getMapper(PartnerMapper.class).partyIDFromEmail(HasUrls.PARTY_LEISURELINK_EMAIL))) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.leisurelink.A_Handler(partner));}
			else if (partner.hasOrganizationid(sqlSession.getMapper(PartnerMapper.class).partyIDFromEmail(HasUrls.PARTY_BAREFOOT_EMAIL))) {HANDLERS.put(partner.getPartyid(), new net.cbtltd.rest.barefoot.A_Handler(partner));}
			else {throw new ServiceException(Error.service_absent, partner.getOrganizationid());}
		
		LOG.debug("getRequestHandler " + partner.getPartyid() + " " + HANDLERS.get(partner.getPartyid()));
		return HANDLERS.get(partner.getPartyid());
		}
		return null;
	}

	public static final String getProductname(String name, String locationname, String productid) {
		if (name == null || name.trim().isEmpty()) {return locationname + " " + productid;}
		else return name.trim()  + " " + productid;
	}

	/**
	 * Executes the PartnerCreate action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Partner execute(SqlSession sqlSession, PartnerCreate action) {
		LOG.debug("PartnerCreate " + action);
		try {sqlSession.getMapper(PartnerMapper.class).create(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the PartnerRead action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Partner execute(SqlSession sqlSession, PartnerRead action) {
		Partner alert = null;
		try {alert = sqlSession.getMapper(PartnerMapper.class).read(action.getId());}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return alert;
	}

	/**
	 * Executes the PartnerUpdate action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Partner execute(SqlSession sqlSession, PartnerUpdate action) {
		LOG.debug("PartnerUpdate in " + action);
		try {
			sqlSession.getMapper(PartnerMapper.class).update(action);
			sqlSession.commit();
			stopSchedulers();
			startSchedulers();
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Partner, action);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the PartnerDelete action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Partner execute(SqlSession sqlSession, PartnerDelete action) {
		try {sqlSession.getMapper(PartnerMapper.class).delete(action.getId());}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return null;
	}

	/**
	 * Executes the PartnerTable action to read an alert table for a property.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Partner> execute(SqlSession sqlSession, PartnerTable action) {
		LOG.debug("PartnerTable in " + action);
		Table<Partner> table = new Table<Partner>();
		try {
			table.setDatasize(sqlSession.getMapper(PartnerMapper.class).count(action));
			table.setValue(sqlSession.getMapper(PartnerMapper.class).list(action));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("PartnerTable out " + table);
		return table;
	}

	// TODO : Temporary fix for disabling Interhome reservation
	public static final void createReservation(SqlSession sqlSession, Reservation reservation) {
		if (getRequestHandler(sqlSession, reservation.getAltpartyid())!=null && getRequestHandler(sqlSession, reservation.getAltpartyid()).isAvailable(sqlSession, reservation)) {
			getRequestHandler(sqlSession, reservation.getAltpartyid()).createReservation(sqlSession, reservation);
		}
		else {
			ArrayList<NameId> collisions = new ArrayList<NameId>();
			collisions.add(new NameId(Partner.ANOTHER));
			reservation.setCollisions(collisions);
		}
	}
	
	public static final Map<String, String> createReservationAndPayment(SqlSession sqlSession, Reservation reservation, CreditCard creditCard) {
		Map<String, String> resultMap = null;
		if (getRequestHandler(sqlSession, reservation.getAltpartyid()).isAvailable(sqlSession, reservation)/* && reservation.getAltpartyid() != HasUrls.PARTY_INTERHOME_ID*/) {
			resultMap =	getRequestHandler(sqlSession, reservation.getAltpartyid()).createReservationAndPayment(sqlSession, reservation, creditCard);
		}
		else {
			ArrayList<NameId> collisions = new ArrayList<NameId>();
			collisions.add(new NameId(Partner.ANOTHER));
			reservation.setCollisions(collisions);
			resultMap = new HashMap<String, String>();
			resultMap.put(GatewayHandler.STATE, GatewayHandler.DECLINED);
			resultMap.put(GatewayHandler.ERROR_MSG, Error.pms_reservation_reject.getDetailedMessage());
		}
		return resultMap;
	}
	
	public static final void inquireReservation(SqlSession sqlSession, Reservation reservation) {
		if (getRequestHandler(sqlSession, reservation.getAltpartyid()).isAvailable(sqlSession, reservation)) {
			getRequestHandler(sqlSession, reservation.getAltpartyid()).inquireReservation(sqlSession, reservation);
		} else {
			ArrayList<NameId> collisions = new ArrayList<NameId>();
			collisions.add(new NameId(Partner.ANOTHER));
			reservation.setCollisions(collisions);
		}
	}
	
	public static ReservationPrice readPrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency) {
		return getRequestHandler(sqlSession, reservation.getAltpartyid()).readPrice(sqlSession, reservation, productAltId, currency);
	}

	public static final void confirmReservation(SqlSession sqlSession, Reservation reservation) {
		getRequestHandler(sqlSession, reservation.getAltpartyid()).confirmReservation(sqlSession, reservation);
	}

	public static final void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		getRequestHandler(sqlSession, reservation.getAltpartyid()).cancelReservation(sqlSession, reservation);
	}

	//	public static final void readReservation(SqlSession sqlSession, Reservation reservation) {
	//		getRequestHandler(sqlSession, reservation.getAltpartyid()).readReservation(sqlSession, reservation);
	//	}
	
	/**
	 * Gets the region code from its name and country code.
	 *
	 * @param sqlSession the current SQL session.
	 * @param id the region ID or code.
	 * @param name the region name.
	 * @param country the country code.
	 * @return the region code.
	 */
	public synchronized static final Region getRegion(
			SqlSession sqlSession,
			String id,
			String name,
			String country
			) {
		Region region = new Region();
		region.setId(id);
		region.setName(name);
		region.setCountry(country);
		Region exists = sqlSession.getMapper(RegionMapper.class).name(region);
		if (exists == null) {sqlSession.getMapper(RegionMapper.class).create(region);}
		else {region = exists;}
		return region;
	}

	/**
	 * Gets the location from its name, region and country and creates it if it does not exist.
	 *
	 * @param sqlSession the current SQL session.
	 * @param name the name of the location
	 * @param region the region (state, province, county) preferably using its ISO subdivision code
	 * @param country the ISO country code
	 * @return the location object
	 */
	public synchronized static final Location getLocation(SqlSession sqlSession, String name, String region, String country) {
		if (StringUtils.isBlank(name) || StringUtils.isBlank(country)) {return null;}
		Location location = new Location();
		String[] args = name.split("\\(");
		location.setName(args[0]);
		if (country.equals("US")){location.setRegion(region);}
		location.setCountry(country);
		location.setState(Location.CREATED);
		Location exists = sqlSession.getMapper(LocationMapper.class).exists(location);
		//
		if (exists != null) {
			location = exists;
		}
		else {
			 location = GoogleLocationProcessor.getGoogleLocation(sqlSession, name, region, country);
		}
		LOG.debug("getLocation  " + location.getId());
		return location;
	}
	
	/**
	 * Gets the location from its name, region, latitude, longitude and country and creates it if it does not exist.
	 *
	 * @param sqlSession the current SQL session.
	 * @param name the name of the location
	 * @param region the region (state, province, county) preferably using its ISO subdivision code
	 * @param country the ISO country code
	 * @return the location object
	 * @throws GoogleLocationLimitException 
	 */
	public synchronized static final Location getLocation(SqlSession sqlSession, 
															String name, 
															String region, 
															String country, 
															Double latitude, 
															Double longitude) 
															throws GoogleLocationLimitException {
		return getLocation(sqlSession, name, region, country, latitude, longitude, null);
	}
	
	public synchronized static final Location getLocation(SqlSession sqlSession, 
				String name, 
				String region, 
				String country, 
				Double latitude, 
				Double longitude,
				String zipCode) 
				throws GoogleLocationLimitException {
//		if ((name == null || country == null || latitude == null || longitude == null) && (latitude == null || longitude == null || country == null)) {
//			LOG.error("Returning null location: " + name + ' ' + region + ' ' + ' ' + country + ' ' + latitude + ' ' 
//												  + longitude + ' ' +  zipCode);
//			return null;
//		}
		if ((name == null || country == null) && (latitude == null || longitude == null)) {
			LOG.error("Returning null location: " + name + ' ' + region + ' ' + ' ' + country + ' ' + latitude + ' ' 
					+ longitude + ' ' +  zipCode);
			return null;
		}
		
		Location location = new Location();
		country = PartnerService.getCountryCode(country);
		
		if(StringUtils.isNotBlank(name)) {
			location.setName(name.split("\\(")[0]);
		}
		if ("US".equalsIgnoreCase(country)) {
			if(StringUtils.isNotBlank(region))
			{
				region = region.trim();
				if(region.length() > 2) region = PartnerService.getRegionCode(region);
				else
					region = region.toUpperCase();
			}
			else
			{
				region = null;
			}
			location.setRegion(region);
		}
		if(country!=null && country.length()<=2) { location.setCountry(country); }
		location.setState(Location.CREATED);
		
		Location exists = sqlSession.getMapper(LocationMapper.class).exists(location);
		if (exists != null) {
			location = exists;
			boolean update = false;
			
			if(exists.getParentID() == 0) {
				exists.setParentID(Integer.parseInt(exists.getId()));
				LOG.error("Updating google location parent id with: " + exists.getId() + " gname: " + exists.getGname());
				update = true;
			}
			if(StringUtils.isAllLowerCase(exists.getRegion()) && region != null)
			{
				LOG.error("Updating lower case region: " + exists.getRegion() + " with " + region);
				exists.setRegion(region);
				update = true;
			}
			if(update){
				sqlSession.getMapper(LocationMapper.class).update(exists);
			}
			
		}
		else {
			 LOG.error("Looking up Google GEO Code API: " + location.getName());
			 if(StringUtils.isEmpty(zipCode)) {
				 location = GoogleLocationProcessor.getGoogleLocation(name, region, latitude, longitude, country);
			 }
			 else {
				 location = GoogleLocationProcessor.getGoogleLocation(name, region, latitude, longitude, country, zipCode);
			 }
			 if(location != null && (StringUtils.isBlank(country) || location.getCountry().equalsIgnoreCase(country)) && StringUtils.isNotBlank(location.getGname())) {
				 ArrayList<Location> locations = sqlSession.getMapper(LocationMapper.class).googleExists(location);
				 if(StringUtils.isEmpty(location.getRegion()) || location.getRegion().length() > 3) {
					 location.setRegion(null);
				 }
				 
				 country = location.getCountry();
				 
				 if ("US".equalsIgnoreCase(country)) {
					 	if(StringUtils.isNotEmpty(region) && region.length() <= 2)
					 		location.setRegion(region);
					 	else
					 	{
					 		if(location.getAdminarea_lvl_1() != null) 
							{
								LOG.error("Updating region using Admin lvl 1: " + location.getAdminarea_lvl_1());
								location.setRegion(PartnerService.getRegionCode(location.getAdminarea_lvl_1()));
							}
					 	}
				 }
				 
				 // if it does not exist, let's create one from google response
				 if(locations == null || locations.size() == 0) {
					// use the gname as name
					location.setName(location.getGname());

					LOG.error("Creating location from the Google name/country: " + name + " : " + country);
					sqlSession.getMapper(LocationMapper.class).create(location);
					// now update parent id
					exists = sqlSession.getMapper(LocationMapper.class).googleExists(location).get(0);
					LOG.error("Updating parentID of created record with location name: " + name);
					if(exists.getParentID() == 0){
						exists.setParentID(Integer.parseInt(exists.getId()));
						sqlSession.getMapper(LocationMapper.class).update(exists);
					}
				 }
				 else {
					 for (Location exist : locations) {
						if(exist.getState().equals(Location.CREATED) || locations.size() == 1) {
							if(isLocationUpdateRequired(exist, location)) {
								LOG.error("Updating google location: " + exist.getName() + " gname: " + exist.getGname() + " id: " + exist.getId() + " parent id: " + exist.getParentID());
								exist.setState(Location.CREATED);
								sqlSession.getMapper(LocationMapper.class).update(exist);
							}
							location = exist;
							break;
						}
					 }
				 }
			 }
			 // google did not find the place, we will create a new location from the feed data
			 else {
				 LOG.error("Creating location from the feed name/country: " + name + " : " + country);
				 location = new Location();
				 if(StringUtils.isNotBlank(country) && country.length() <= 2 && StringUtils.isNotBlank(name)) {
					 location.setName(name);
					 location.setRegion(region);
					 location.setState(Location.CONFLICT);
					 location.setLatitude(latitude);
					 location.setLongitude(longitude);
					 location.setCountry(country);
					 location.setLocationtype(GeocoderResultType.LOCALITY.value().toLowerCase());
					 sqlSession.getMapper(LocationMapper.class).create(location);
					 // now update parent id
					 exists = sqlSession.getMapper(LocationMapper.class).exists(location);
					 
					 LOG.error("Updating parentID of created record with location name: " + name);
					 exists.setParentID(Integer.parseInt(exists.getId()));
					 sqlSession.getMapper(LocationMapper.class).update(exists);

				 }
				 else{
					 location = null;
				 }
			 }
		}
		if(location != null)	
			LOG.error("getLocation  " + location.getId());
		return location;
	}
	
	
	/**
	 * Gets the location from its zipcode and and country and creates it if it does not exist.
	 *
	 * @param sqlSession the current SQL session.
	 * @param name the name of the location
	 * @param region the region (state, province, county) preferably using its ISO subdivision code
	 * @param country the ISO country code
	 * @param latitude the latitude of the location
	 * @param longitude the longitude of the location
	 * @param altitude the altitude of the location
	 * @return the location object
	 * @throws GoogleLocationLimitException 
	 */
	public synchronized static final Location getLocation(SqlSession sqlSession, String zipCode, String country, Double latitude, Double longitude) throws GoogleLocationLimitException {
		if ((zipCode == null || country == null || latitude == null || longitude == null) && (latitude == null || longitude == null || country == null)) {return null;}

		Location location = new Location();
		country = PartnerService.getCountryCode(country);
		LOG.info("Looking up Google GEO Code API using zipCode: " + zipCode + " " + latitude + " " + longitude);
		location = GoogleLocationProcessor.getGoogleLocation(zipCode, latitude, longitude, country);
		if(location != null && (StringUtils.isBlank(country) || location.getCountry().equalsIgnoreCase(country))
				&& StringUtils.isNotBlank(location.getGname())) {
			ArrayList<Location> exists = sqlSession.getMapper(LocationMapper.class).googleExists(location);
			country = location.getCountry();
			
			if ("US".equalsIgnoreCase(country)) {
		 		if(location.getAdminarea_lvl_1() != null) {
					LOG.error("Updating region using Admin lvl 1: " + location.getAdminarea_lvl_1());
					location.setRegion(PartnerService.getRegionCode(location.getAdminarea_lvl_1()));
				}
			}
			// if it does not exist, let's create one from google response
			if(exists == null || exists.size() == 0) {
				if(location.getLocationtype().equalsIgnoreCase(GeocoderResultType.LOCALITY.value()) 
						|| location.getLocationtype().equalsIgnoreCase(GeocoderResultType.SUBLOCALITY.value())) {
					// use the gname as name
					location.setName(location.getGname());
					location.setState(Location.CREATED);
					
					LOG.info("Creating location from the Google name/country: " + location.getName() + " : " + country);
					sqlSession.getMapper(LocationMapper.class).create(location);
					
					// now update parent id
					Location exist = sqlSession.getMapper(LocationMapper.class).googleExists(location).get(0);
					LOG.error("Updating parentID of created record with location name: " + location.getGname());

					if(exist.getParentID() == 0)
					{
						exist.setParentID(Integer.parseInt(exist.getId()));
						sqlSession.getMapper(LocationMapper.class).update(exist);
					}
				}
			}
			else {
				for (Location exist : exists) {
					LOG.error("Multiple locations with GName: " + exist.getGname());
					if(exist.getState().equals(Location.CREATED) || exists.size() == 1) {
						if(isLocationUpdateRequired(exist,location)) {
							LOG.error("Updating google location: " + exist.getName() + " gname: " + exist.getGname() + " id: " + exist.getId());
							sqlSession.getMapper(LocationMapper.class).update(exist);
						}
						location = exist;
						break;
					}
				}
			}
			
			LOG.error("getLocation  " + location.getId());
		}
		
		return location;
	}
	
	private static boolean isLocationUpdateRequired(Location existingRecord, Location searchedRecord)
	{
		boolean update = false;
		if(StringUtils.isEmpty(existingRecord.getName()) || ISO8859CharacterNormalizer.containsHexOrDecCode(existingRecord.getName()) 
				|| existingRecord.getName().contains("?")) {
			existingRecord.setName(searchedRecord.getName());
			update = true;
		}
		if(existingRecord.getGname().contains("?")) {
			existingRecord.setGname(searchedRecord.getGname());
			update = true;
		}
		if(existingRecord.getParentID() == 0) {
			existingRecord.setParentID(Integer.parseInt(existingRecord.getId()));
			update = true;
		}
		if(StringUtils.isEmpty(existingRecord.getRegion()) || StringUtils.isAllLowerCase(existingRecord.getRegion())){
			existingRecord.setRegion(searchedRecord.getRegion());
			update = true;
		}
		if(existingRecord.getLocationtype() == null){
			existingRecord.setLocationtype(GeocoderResultType.LOCALITY.value().toLowerCase());
			update = true;
		}
		
		return update;
	}

	/**
	 * Gets the party or creates it if it does not exist.
	 *
	 * @param sqlSession the current SQL session.
	 * @param altpartyid the foreign API party ID
	 * @param altid the foreign ID
	 * @return the product
	 */
	public synchronized static Party getParty(SqlSession sqlSession, String altpartyid, String altid, Party.Type type) {
		Party party = sqlSession.getMapper(PartyMapper.class).altread(new NameId(altpartyid, altid));
		if (party == null) {
			party = new Party();
			party.setAltpartyid(altpartyid);
			party.setAltid(altid);
			party.setCreatorid(Party.CBT_LTD_PARTY);
			party.setState(Party.CREATED);
			party.setOrganizationid(altpartyid);
			sqlSession.getMapper(PartyMapper.class).create(party);
			RelationService.create(sqlSession, Relation.ORG_PARTY_ + type.name(), altpartyid, party.getId());
		}
		LOG.debug("getParty  " + party.getId());
		return party;
	}

	/**
	 * Gets the product or creates it if it does not exist.
	 *
	 * @param sqlSession the current SQL session.
	 * @param altpartyid the foreign API party ID
	 * @param altid the foreign product ID
	 * @return the product
	 */
	public synchronized static Product getProduct(SqlSession sqlSession, String altpartyid, String altid) {
		Product product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(altpartyid, altid));
		if (product == null) {
			product = new Product();
			product.setAltpartyid(altpartyid);
			product.setAltid(altid);
			product.setState(Product.INITIAL);
			product.setType(Product.Type.Accommodation.name());
			product.setOrganizationid(altpartyid);
			product.setAltSupplierId(altpartyid);
			product.setSupplierid(altpartyid);
			product.setOwnerid(altpartyid);
			product.setInquireState("Send e-mail");
			sqlSession.getMapper(ProductMapper.class).create(product);
		}
		// replace the replace call..replace call was throwing lock exception
		RelationService.create(sqlSession, Relation.ORGANIZATION_PRODUCT, altpartyid, product.getId());
		LOG.debug("getProduct  " + altpartyid + ", " + altid + ", " + product.getId());
		if (product.hasState(Product.CREATED) || product.hasState(Product.INITIAL)) {
			return product;
		} else {return null;}
	}
	
	public synchronized static Product createProduct(SqlSession sqlSession, Product product) {
		Product exist = sqlSession.getMapper(ProductMapper.class).altread(new NameId(product.getAltpartyid(), product.getAltid()));
		if (exist == null) {
			product.setState(Product.CREATED);
			sqlSession.getMapper(ProductMapper.class).create(product);
			// replace the replace call..replace call was throwing lock exception
			RelationService.create(sqlSession, Relation.ORGANIZATION_PRODUCT, product.getAltpartyid(), product.getId());
			LOG.debug("createProduct  " + product.getAltpartyid() + ", " + product.getAltid() + ", " + product.getId());
		}
		return product;
	}
	
	public synchronized static void updateProduct(SqlSession sqlSession, Product product) {
		Product exist = sqlSession.getMapper(ProductMapper.class).altread(new NameId(product.getAltpartyid(), product.getAltid()));
		if (exist != null) {
			sqlSession.getMapper(ProductMapper.class).update(product);
			// replace the replace call..replace call was throwing lock exception
			LOG.debug("updateProduct  " + product.getAltpartyid() + ", " + product.getAltid() + ", " + product.getId());
		}
	}
	
	
	@Deprecated
	public synchronized static Product getProduct(SqlSession sqlSession, NameId name) {
		Product product = sqlSession.getMapper(ProductMapper.class).nameRead(name);
		if (product == null) {
			
			sqlSession.getMapper(ProductMapper.class).create(product);
		}
		
		return product;
	}
	
	/**
	 * Gets the product or null it if it does not exist.
	 *
	 * @param sqlSession the current SQL session.
	 * @param name 
	 * @return the product
	 */
	public static Product getProductWithoutInsert(SqlSession sqlSession, NameId name) {
		Product product = sqlSession.getMapper(ProductMapper.class).nameRead(name);
		return product;
	}
	
	/**
	 * Gets the product or null it if it does not exist.
	 *
	 * @param sqlSession the current SQL session.
	 * @param name 
	 * @return the product
	 */
	public static Product getProductWithoutInsert(SqlSession sqlSession, Product product) {
		Product prod = sqlSession.getMapper(ProductMapper.class).exists(product);
		return prod;
	}
	
	/**
	 * Gets the product or creates it if it does not exist.
	 *
	 * @param sqlSession the current SQL session.
	 * @param altpartyid the foreign API party ID
	 * @param altid the foreign product ID
	 * @return the product
	 */
	public synchronized static Product getProduct(SqlSession sqlSession, String altpartyid, String altid, boolean create) {
		Product product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(altpartyid, altid));
		if (create && product == null) {
			product = new Product();
			product.setAltpartyid(altpartyid);
			product.setAltid(altid);
			product.setState(Product.INITIAL);
			product.setType(Product.Type.Accommodation.name());
			product.setOrganizationid(altpartyid);
			product.setAltSupplierId(altpartyid);
			product.setSupplierid(altpartyid);
			product.setOwnerid(altpartyid);
			product.setInquireState("Send e-mail");
			sqlSession.getMapper(ProductMapper.class).create(product);
			
			// replaced the replace call..replace call was throwing lock exception
			RelationService.create(sqlSession, Relation.ORGANIZATION_PRODUCT, altpartyid, product.getId());
		}
		if(product != null)
			LOG.debug("getProduct  " + altpartyid + ", " + altid + ", " + product.getId());
		return product;
	}

	/**
	 * Gets the product foreign ID.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation for the product
	 * @return the product altid
	 */
	public synchronized static String getProductaltid(SqlSession sqlSession, Reservation reservation) {
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
    	if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}
		return product.getAltid();
	}

	/**
	 * Creates the new schedule items of the partner.
	 *
	 * @param sqlSession the current SQL session.
	 * @param product the property being scheduled
	 * @param fromdate the date from which it is scheduled
	 * @param todate the date to which it is scheduled
	 * @param version the latest version time stamp
	 */
	public synchronized static void createSchedule(SqlSession sqlSession, Product product, Date fromdate, Date todate, Date version) {
		Reservation reservation = new Reservation();
		reservation.setProductid(product.getId());
		reservation.setFromdate(fromdate);
		reservation.setTodate(todate);
		Reservation exists = sqlSession.getMapper(ReservationMapper.class).exists(reservation);	
		if (exists == null) {sqlSession.getMapper(ReservationMapper.class).create(reservation);}
		else {reservation = exists;}
		reservation.setOrganizationid(product.getAltpartyid());
		reservation.setAltpartyid(product.getAltpartyid());
		reservation.setState(Reservation.State.Closed.name());
		reservation.setCurrency(product.getCurrency());
		reservation.setUnit(product.getUnit());
		reservation.setName(reservation.getId());
		reservation.setVersion(version);
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		LOG.debug("createSchedule " + reservation.getProductid() + " " + reservation.getVersion() + " " + reservation.getFromdate() + " " + reservation.getTodate());
	}
	
	/**
	 * Cancels the old schedule items of the product.
	 *
	 * @param sqlSession the current SQL session.
	 * @param product the product for which schedule items are to be cancelled.
	 * @param version the latest version time stamp.
	 */
	public synchronized static void cancelSchedule(SqlSession sqlSession, Product product, Date version) {
		Reservation reservation = new Reservation();
		reservation.setProductid(product.getId());
		reservation.setVersion(version);
		sqlSession.getMapper(ReservationMapper.class).cancelversionbydate(reservation);
		LOG.debug("cancelSchedule " + product.getId() + " " + version);
	}
	
	public synchronized static void cancelSchedule(SqlSession sqlSession, Reservation reservation) {
		sqlSession.getMapper(ReservationMapper.class).cancelversion(reservation);
	}
	
	/**
	 * Update only changed reservations, so version will no be changed in reservation which are not changed
	 * Process:
	 * 1. Load all reservation for one property from database in one list. 
	 * 2. In second list store every reservation from API.
	 * 3. For every reservation from API:
	 *     a. If exist in DB remove that from loaded DB list. 
	 *     b. If not exist just add to database. (Do not update if same exist - do not change version)
	 * 4. At the end got throw all reservation from DB list (if something left there) and remove (set state to cancel) that reservation from DB.
	 * 
	 * @param sqlSession
	 * @param product
	 * @param reservationsFromApi
	 * @param version
	 */
	public synchronized static void updateProductSchedules(SqlSession sqlSession, Product product, List<Reservation> reservationsFromApi, Date version) {
		Reservation findReservation = new Reservation();
		findReservation.setProductid(product.getId());
		ArrayList<Reservation> reservationFromDB = sqlSession.getMapper(ReservationMapper.class).productApiReserveredDates(findReservation);
		
		for(Reservation reservation : reservationsFromApi){
			reservation.setState(Reservation.State.Closed.name());

			if (!reservationFromDB.contains(reservation)) {
				reservation.setOrganizationid(product.getAltpartyid());
				reservation.setAltpartyid(product.getAltpartyid());
				reservation.setCurrency(product.getCurrency());
				reservation.setUnit(product.getUnit());
				reservation.setName(reservation.getId());
				reservation.setVersion(version);
				sqlSession.getMapper(ReservationMapper.class).create(reservation);
			}
			else {
				reservationFromDB.remove(reservation);
			}
		}
		
		if(reservationFromDB.size()>0){
			ArrayList<String> reservationIdList = new ArrayList<String>();
			for(Reservation currentReservation : reservationFromDB){
				reservationIdList.add(currentReservation.getId());
			}
			sqlSession.getMapper(ReservationMapper.class).cancelreservationlist(reservationIdList);
		}
				
		LOG.debug("updateProductSchedule, productId=" + product.getId()+", " + version);
	}
	
	
	
	public synchronized static void updateProductPrices(SqlSession sqlSession, Product product, String entityType, 
			List<Price> pricesFromApi, Date version, Boolean feeWithoutDates, Date minimumToDateForUpdate) {
		Price findPrices = new Price();
		findPrices.setEntityid(product.getId());
		findPrices.setPartyid(product.getAltpartyid());
		findPrices.setEntitytype(entityType);
		//set first and last date for DB searching 
		//(this is necessary because of index on price table)
		if(pricesFromApi.size()>0){
			findPrices.setDate(pricesFromApi.get(0).getDate());
			findPrices.setTodate(pricesFromApi.get(pricesFromApi.size()-1).getTodate());
		}
		ArrayList<Price> pricesFromDB = sqlSession.getMapper(PriceMapper.class).readByEntityIdAndEntityType(findPrices);
		
		for(Price price : pricesFromApi){
			price.setState(Price.CREATED);

			if(feeWithoutDates){
				// This is for situation when method list.contains can not be used
				// because for fees often we do not have from - to date values.
				// In that case we set today and some date in future, and 
				// method Price.equals will not find these fees for every next day, 
				// and fee will be updated. So here we finding values manually in List
				// but this will not be too much time consuming because we do not have 
				// big numbers of fee per property, and also if fee exist in DB already
				// (this will be in most situation) that fee will be on top of this list.
				// Also here is check if toDate in DB is below some minimum toDate
				boolean feeExist = false;
				for(int i=0;i<pricesFromDB.size();i++){
					if(price.equalsWithoutDates(pricesFromDB.get(i)) && pricesFromDB.get(i).getTodate().after(minimumToDateForUpdate)){
						pricesFromDB.remove(i);
						feeExist = true;
						break;
					}
				}
				if(!feeExist){
					price.setVersion(version);
					sqlSession.getMapper(PriceMapper.class).create(price);
				}
			}else{
				if (!pricesFromDB.contains(price)) {
					price.setVersion(version);
					sqlSession.getMapper(PriceMapper.class).create(price);
				}
				else {
					pricesFromDB.remove(price);
				}
			}
		}
		
		if(pricesFromDB.size()>0){
			ArrayList<String> priceIdList = new ArrayList<String>();
			for(Price currentPrice : pricesFromDB){
				priceIdList.add(currentPrice.getId());
			}
			sqlSession.getMapper(PriceMapper.class).cancelpricelist(priceIdList);
		}
				
		LOG.debug("updateProductPrices, productId=" + product.getId()+", " + version);
	}
	
	
	public synchronized static void updateProductTaxes(SqlSession sqlSession,  Product product, List<Tax> taxesFromApi, Date version){
		//finding previous taxes from db
		Tax action = new Tax();
		action.setProductId(product.getId());
		action.setPartyid(product.getAltpartyid());
		//setting mandatory type=null to have both (mandatory and optional)
		//so we do not need do this 2 times
		//mandatoryType field by default = mandatory
		action.setMandatoryType(null);
		ArrayList <Tax> taxesFromDB = sqlSession.getMapper(TaxMapper.class).readbyproductid(action);
		for(Tax currentTax : taxesFromApi){
			currentTax.setState(Tax.CREATED);
			if (!taxesFromDB.contains(currentTax)) {
				currentTax.setVersion(version);
				sqlSession.getMapper(TaxMapper.class).create(currentTax);
				//RelationService.replace(sqlSession, Relation.PRODUCT_TAX, productId, currentTax.getId());
			}
			else {
				taxesFromDB.remove(currentTax);
			}
		}
		
		if(taxesFromDB.size()>0){
			ArrayList<String> taxIdList = new ArrayList<String>();
			for(Tax currentTaxForCancel : taxesFromDB){
				taxIdList.add(currentTaxForCancel.getId());
			}
			sqlSession.getMapper(TaxMapper.class).canceltaxlist(taxIdList);
		}
		
		LOG.debug("updateProductTaxes, productId=" + product.getId()+", " + version);
	}
	
	
	public synchronized static void updateProductAdjustments(SqlSession sqlSession, Product product, List<Adjustment> adjustmentsFromApi, Date version){
		//finding previous adjustment from DB
		Adjustment action = new Adjustment();
		action.setProductID(product.getId());
		action.setPartyID(product.getAltpartyid());
		action.setState(Adjustment.Created);
		
		ArrayList <Adjustment> adjustmentFromDB = sqlSession.getMapper(AdjustmentMapper.class).readbyproductandstate(action);
		for(Adjustment currentAdjustment : adjustmentsFromApi){
			currentAdjustment.setState(Adjustment.Created);
			if (!adjustmentFromDB.contains(currentAdjustment)) {
				currentAdjustment.setVersion(version);
				sqlSession.getMapper(AdjustmentMapper.class).create(currentAdjustment);
			}
			else {
				adjustmentFromDB.remove(currentAdjustment);
			}
		}
		
		if(adjustmentFromDB.size()>0){
			ArrayList<String> adjustmentIdList = new ArrayList<String>();
			for(Adjustment currentAdjustmentForCancel : adjustmentFromDB){
				adjustmentIdList.add(String.valueOf(currentAdjustmentForCancel.getID()));
			}
			sqlSession.getMapper(AdjustmentMapper.class).canceladjustmentlist(adjustmentIdList);
		}
		
		LOG.debug("updateProductAdjustments, productId=" + product.getId()+", " + version);
	}
	
	
	/*
	 * This function is for updating product yield with name "Length of Stay" or name "Weekend" 
	 */
	public synchronized static void updateProductRateYields(SqlSession sqlSession, Product product, List<Yield> yieldsFromApi, Date version){
		//finding previous Length of Stay yields from DB
		Yield action = new Yield();
		action.setEntityid(product.getId());
		action.setEntitytype(Yield.PRODUCT);
		action.setName(Yield.LENGTH_OF_STAY);
		action.setState(Yield.CREATED);
		
		ArrayList <Yield> yieldFromDB = sqlSession.getMapper(YieldMapper.class).readByProductState(action);
		for(Yield currentYield : yieldsFromApi){
			if (!yieldFromDB.contains(currentYield)) {
				currentYield.setVersion(version);
				sqlSession.getMapper(YieldMapper.class).create(currentYield);
			}
			else {
				yieldFromDB.remove(currentYield);
			}
		}
		
		if(yieldFromDB.size()>0){
			ArrayList<String> yieldIdList = new ArrayList<String>();
			for(Yield currentYieldForCancel : yieldFromDB){
				//we cancel only yield with name weekend or length of stay
				//because maybe there is some other fees
				if(currentYieldForCancel.getName().equalsIgnoreCase(Yield.LENGTH_OF_STAY) || currentYieldForCancel.getName().equalsIgnoreCase(Yield.WEEKEND)){
					yieldIdList.add(String.valueOf(currentYieldForCancel.getId()));
				}
			}
			sqlSession.getMapper(YieldMapper.class).cancelYieldList(yieldIdList);
		}
		
		LOG.debug("updateProductYields, productId=" + product.getId()+", " + version);
	}
	
	/**
	 * Creates the new minstay items.
	 *
	 * @param sqlSession the current SQL session.
	 * @param value the minstay day value
	 * @param product the property being scheduled
	 * @param fromdate the date from which minstay value is valid
	 * @param todate the date to which it is valid
	 * @param version the latest version time stamp
	 */
	public synchronized static void createMinStay(SqlSession sqlSession, Integer value, Product product, Date fromdate, Date todate, Date version) {
		MinStay minstay = new MinStay();
		minstay.setProductid(product.getId());
		minstay.setFromdate(fromdate);
		minstay.setTodate(todate);
		MinStay exists = sqlSession.getMapper(PropertyMinStayMapper.class).exists(minstay);	
		if (exists == null) {sqlSession.getMapper(PropertyMinStayMapper.class).create(minstay);}
		else {minstay = exists;}
		minstay.setValue(value);
		minstay.setSupplierid(product.getSupplierid());
		minstay.setVersion(version);
		sqlSession.getMapper(PropertyMinStayMapper.class).update(minstay);
		LOG.debug("createMinStay " + minstay.getProductid() + " " + minstay.getVersion() + " " + minstay.getFromdate() + " " + minstay.getTodate());
	}
	
	/**
	 * Deletes the old minstay items.
	 *
	 * @param sqlSession the current SQL session.
	 * @param product the product for which minstay items are to be deleted.
	 * @param version the latest version time stamp.
	 */
	
	public synchronized static void deleteMinStay(SqlSession sqlSession, Product product, Date version) {
		MinStay minstay = new MinStay();
		minstay.setProductid(product.getId());
		minstay.setVersion(version);
		sqlSession.getMapper(PropertyMinStayMapper.class).deleteversion(minstay);
		LOG.debug("cancelSchedule " + product.getId() + " " + version);
	}
	
	/**
	 * Stop scheduler.
	 */
	public static synchronized  void stopSchedulers () {
		LOG.debug("stopSchedulers");
		for(Scheduler scheduler : schedulers) {
			if (scheduler.isStarted()) {scheduler.stop();}
		}
		schedulers.clear();
	}

	/**
	 * Start scheduler.
	 */
	public static synchronized void startSchedulers () {
		LOG.debug("startSchedulers");
		final SqlSession sqlSession = RazorServer.openSession();
		try {
			PartnerTable action = new PartnerTable();
			action.setState(Partner.CREATED);
			action.setOrderby(Partner.PARTYNAME);
			action.setStartrow(0);
			action.setNumrows(Integer.MAX_VALUE);
			final ArrayList<Partner> partners = sqlSession.getMapper(PartnerMapper.class).list(action);

			if (partners == null) {return;}

			for (final Partner partner : partners) {
				LOG.debug("Schedule  " + partner);

				if (partner.hasAlertcron()) {
					Scheduler readAlerts = new Scheduler();
					schedulers.add(readAlerts);
					LOG.debug("readAlerts " + partner.getPartyid() + " " + partner.getAlertwait() + " " + partner.getAlertcron());
					readAlerts.setTimeZone(TimeZone.getTimeZone("GMT"));
					readAlerts.schedule(partner.getAlertcron(), new Runnable() {
						public void run() {
							IsPartner handler = getRequestHandler(partner);
							if (handler.stopped(IsPartner.API.Alert)) {
								handler.start(IsPartner.API.Alert);
								handler.readAlerts();
								handler.stop(IsPartner.API.Alert);
							}
						}
					});
					readAlerts.start();
				}

				if (partner.hasPricecron()) {
					Scheduler readPrices = new Scheduler();
					schedulers.add(readPrices);
					LOG.debug("readPrices " + partner.getPartyid() + " " + partner.getPricewait() + " " + partner.getPricecron());
					readPrices.setTimeZone(TimeZone.getTimeZone("GMT"));
					readPrices.schedule(partner.getPricecron(), new Runnable() {
						public void run() {
							IsPartner handler = getRequestHandler(partner);
							if (handler.stopped(IsPartner.API.Price)) {
								handler.start(IsPartner.API.Price);
								handler.readPrices();
								handler.stop(IsPartner.API.Price);
							}
						}
					});
					readPrices.start();
				}

				if (partner.hasProductcron()) {
					Scheduler readProducts = new Scheduler();
					schedulers.add(readProducts);
					LOG.debug("readProducts " + partner.getPartyid() + " " + partner.getProductwait() + " " + partner.getProductcron());
					readProducts.setTimeZone(TimeZone.getTimeZone("GMT"));
					readProducts.schedule(partner.getProductcron(), new Runnable() {
						public void run() {
							IsPartner handler = getRequestHandler(partner);
							if (handler.stopped(IsPartner.API.Product)) {
								handler.start(IsPartner.API.Product);
								handler.readProducts();
								handler.stop(IsPartner.API.Product);
							}
						}
					});
					readProducts.start();
				}

				if (partner.hasSchedulecron()) {
					Scheduler readSchedule = new Scheduler();
					schedulers.add(readSchedule);
					LOG.debug("readSchedule " + partner.getPartyid() + " " + partner.getSchedulewait() + " " + partner.getSchedulecron());
					readSchedule.setTimeZone(TimeZone.getTimeZone("GMT"));
					readSchedule.schedule(partner.getSchedulecron(), new Runnable() {
						public void run() {
							IsPartner handler = getRequestHandler(partner);
							if (handler.stopped(IsPartner.API.Schedule)) {
								handler.start(IsPartner.API.Schedule);
								handler.readSchedule();
								handler.stop(IsPartner.API.Schedule);
							}
						}
					});
					readSchedule.start();
				}

				if (partner.hasSpecialcron()) {
					Scheduler readSpecials = new Scheduler();
					schedulers.add(readSpecials);
					LOG.debug("readSpecials " + partner.getPartyid() + " " + partner.getSpecialwait() + " " + partner.getSpecialcron());
					readSpecials.setTimeZone(TimeZone.getTimeZone("GMT"));
					readSpecials.schedule(partner.getSpecialcron(), new Runnable() {
						public void run() {
							IsPartner handler = getRequestHandler(partner);
							if (handler.stopped(IsPartner.API.Special)) {
								handler.start(IsPartner.API.Special);
								handler.readSpecials();
								handler.stop(IsPartner.API.Special);
							}
						}
					});
					readSpecials.start();
				}
			}
		}
		catch (Throwable x) {
			sqlSession.rollback();
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
	}
}
