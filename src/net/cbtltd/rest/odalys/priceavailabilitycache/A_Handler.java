package net.cbtltd.rest.odalys.priceavailabilitycache;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import net.cbtltd.rest.odalys.entity.ProductList;
import net.cbtltd.rest.odalys.entity.ProductList.OdalysProduct;
import net.cbtltd.rest.odalys.entity.ProductList.OdalysProduct.PriceChart.DatedPriceList;
import net.cbtltd.rest.odalys.entity.Segments;
import net.cbtltd.rest.odalys.entity.Segments.Entities.Entity;
import net.cbtltd.rest.odalys.entity.Segments.Entities.Entity.Ats.At.Occupancies.Occupancy;
import net.cbtltd.rest.odalys.entity.Segments.Entities.Entity.Begins.Begin;
import net.cbtltd.rest.odalys.entity.Segments.Entities.Entity.Durations.Duration;
import net.cbtltd.rest.odalys.entity.Segments.SegmentProduct;
import net.cbtltd.rest.odalys.entity.SegmentsRoot;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.PriceService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class A_Handler extends PartnerHandler implements IsPartner {

	private static final Logger LOG = Logger.getLogger(A_Handler.class
			.getName());
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	
	public A_Handler(Partner partner) {
		super(partner);
	}


	


	@Override
	public void readProducts() {
	
	String propertyFileName="PAC_SAMPLE";
					try {

								File file = new File(
										OdalysUtils.ODALYS_XML_LOCAL_FILE_PATH
												+ propertyFileName
												+ OdalysUtils.XML_EXTENSION);
								JAXBContext jaxbContext = JAXBContext
										.newInstance( SegmentsRoot.class);
								Unmarshaller jaxbUnmarshaller = jaxbContext
										.createUnmarshaller();
								SegmentsRoot propertyData = (SegmentsRoot) jaxbUnmarshaller
										.unmarshal(file);
								saveProperty(propertyData);
							} catch (Exception e) {
								LOG.error(
										"Fatal Exception during unmarshalling file "
												+ propertyFileName, e);

							}



				

				long end = System.currentTimeMillis();

//				MonitorService.monitor(message, version);
	}
	
	public void saveProperty(SegmentsRoot propertyData){
		List<net.cbtltd.shared.price.PriceCreate> priceList = null;
		Map<String,net.cbtltd.rest.odalys.entity.Segments.Prices.Price> priceUnitMap=new HashMap<String, Segments.Prices.Price>();
		Map<String,net.cbtltd.rest.odalys.entity.Segments.Entities.Entity.Begins.Begin> dateBeginMap=new HashMap<String, net.cbtltd.rest.odalys.entity.Segments.Entities.Entity.Begins.Begin>();
		Map<String,net.cbtltd.rest.odalys.entity.Segments.Entities.Entity.Durations.Duration> durationMap=new HashMap<String, net.cbtltd.rest.odalys.entity.Segments.Entities.Entity.Durations.Duration>();
		Map<String,net.cbtltd.rest.odalys.entity.Segments.Entities.Entity.Ats.At.Occupancies.Occupancy> occupanyMap=new HashMap<String, net.cbtltd.rest.odalys.entity.Segments.Entities.Entity.Ats.At.Occupancies.Occupancy>();
		
		for(Segments seg :propertyData.getSegments()){
			if("Defaults".equalsIgnoreCase(seg.getWhat())){
				loadPriceAndEntities(seg,priceUnitMap,dateBeginMap,durationMap,occupanyMap);
				}
		}
		for(Segments seg :propertyData.getSegments()){
			if(!"Defaults".equalsIgnoreCase(seg.getWhat())){
				priceList=loadSegment(seg,priceUnitMap,dateBeginMap,durationMap,occupanyMap);
				
			}
		}
		if(priceList==null||priceList.isEmpty()) {return;}
		SqlSession sqlSession = null;
		try {
			sqlSession = RazorServer.openSession();
			for (net.cbtltd.shared.price.PriceCreate price : priceList) {
				try {
					PriceService.getInstance().execute(sqlSession, price);
					sqlSession.commit();
				} catch (Throwable x) {
					if (sqlSession != null)
						sqlSession.rollback();
					LOG.error(x.getMessage(), x);
					x.printStackTrace();
				}
			}
		} finally {
			if (sqlSession != null)
				sqlSession.close();

		}
	}
	
	public List<net.cbtltd.shared.price.PriceCreate> loadSegment(Segments seg,Map<String, net.cbtltd.rest.odalys.entity.Segments.Prices.Price> priceUnitMap, Map<String, Begin> dateBeginMap,
			Map<String, Duration> durationMap,
			Map<String, Occupancy> occupanyMap) {
		String pcode=null,pId=null;
		List<net.cbtltd.shared.price.PriceCreate> priceList=new ArrayList<net.cbtltd.shared.price.PriceCreate>();
		try {
			for (SegmentProduct prd : seg.getSegment()) {
				pcode = prd.getCode().getCValue().toString();
				for (ProductList lst : prd.getContent()) {
					for (OdalysProduct oPrd : lst.getSegment()) {
						pId = oPrd.getRef(); 
						for (DatedPriceList chart : oPrd.getBegins().getBegin()) {
							LOG.info(" pcode : " + pcode + " id : "
									+ pId + " date:" + chart.getDValue()
									+ " ref:" + chart.getRef() + " price:"
									+ chart.getPrice().getPValue()
									+ " priceRef:" + chart.getPrice().getRef()
									+ " Duration:"
									+ chart.getDuration().getRef());
							net.cbtltd.shared.price.PriceCreate price = new net.cbtltd.shared.price.PriceCreate();
							price.setEntitytype(NameId.Type.Product.name());
							price.setEntityid(pId.replace("A", ""));
							price.setPartyid(getAltpartyid());
							price.setName("Weeknight Rate");
							price.setState(Product.CREATED);
							price.setType(NameId.Type.Reservation.name());
							byte interval = durationMap.get(
									chart.getDuration().getRef()).getDValue();
							java.util.Date dt = OdalysUtils.SDF_YYYY_MM_DD
									.parse(chart.getDValue().toString());
							price.setDate(dt);
							Calendar cal = GregorianCalendar.getInstance();
							cal.setTime(dt);
							cal.add(Calendar.DAY_OF_MONTH, new Integer(
									interval - 1));
							price.setTodate(cal.getTime());
							price.setUnit(Unit.DAY);
							price.setValue(chart.getPrice().getPValue()
									.doubleValue());
							price.setMinimum(chart.getPrice().getPValue()
									.doubleValue());
							price.setFactor(1.0);
							price.setCurrency(priceUnitMap.get(
									chart.getPrice().getRef()).getCurrency());
							if ("0".equalsIgnoreCase(dateBeginMap.get(
									chart.getRef()).getQuantity()))
								price.setAvailable(0);
							else if ("Some".equalsIgnoreCase(dateBeginMap.get(
									chart.getRef()).getQuantity()))
								price.setAvailable(1);
							else if ("Many".equalsIgnoreCase(dateBeginMap.get(
									chart.getRef()).getQuantity()))
								price.setAvailable(6);
							price.setRule(OdalysUtils.getDayOfWeek(dt));
							price.setMinStay(Integer.valueOf(occupanyMap.get(pId).getMax()));
							
							priceList.add(price);
						}

					}
				}

			}
		}
		catch (Throwable x) {
			LOG.error(x.getMessage(), x);
			x.printStackTrace();
		} finally {

		}
		return priceList;
	}





	public void loadPriceAndEntities(Segments seg,Map<String, net.cbtltd.rest.odalys.entity.Segments.Prices.Price> priceUnitMap, Map<String, Begin> dateBeginMap,
			Map<String, Duration> durationMap,
			Map<String, Occupancy> occupanyMap) {
		
		for(net.cbtltd.rest.odalys.entity.Segments.Prices.Price price:seg.getPrices().getPrice()){
			priceUnitMap.put(price.getID(),price);
		}
		
		for(Entity entity:seg.getEntities().getEntity()){
			if(entity.getBegins()!=null)
			for(net.cbtltd.rest.odalys.entity.Segments.Entities.Entity.Begins.Begin dateBegin:entity.getBegins().getBegin()){
				dateBeginMap.put(dateBegin.getID(),dateBegin);
			}
			if(entity.getDurations()!=null)
			for(net.cbtltd.rest.odalys.entity.Segments.Entities.Entity.Durations.Duration duration:entity.getDurations().getDuration()){
				durationMap.put(duration.getID(),duration);
			}
			if(entity.getAts()!=null)
			for(net.cbtltd.rest.odalys.entity.Segments.Entities.Entity.Ats.At at:entity.getAts().getAt()){
				if(at.getContent().getOccupancy().size()>0)
					occupanyMap.put(at.getID(),	at.getContent().getOccupancy().get(0));
			}
		}
	}


	@Override
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ReservationPrice readPrice(SqlSession sqlSession,
			Reservation reservation, String productAltId, String currency) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map<String, String> createReservationAndPayment(
			SqlSession sqlSession, Reservation reservation,
			CreditCard creditCard) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void inquireReservation(SqlSession sqlSession,
			Reservation reservation) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void confirmReservation(SqlSession sqlSession,
			Reservation reservation) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void readAlerts() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void readPrices() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readSchedule() {
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


	@Override
	public void readAdditionCosts() {
		// TODO Auto-generated method stub
		
	}

}
