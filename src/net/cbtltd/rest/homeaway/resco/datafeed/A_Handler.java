package net.cbtltd.rest.homeaway.resco.datafeed;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import net.cbtltd.rest.homeaway.HomeAwayUtils;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.response.QuoteDetail;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.WebService;
import net.cbtltd.server.api.ImageMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.integration.AttributeService;
import net.cbtltd.server.integration.ImageService;
import net.cbtltd.server.integration.LocationService;
import net.cbtltd.server.integration.PartnerService;
import net.cbtltd.server.integration.PriceService;
import net.cbtltd.server.integration.ProductService;
import net.cbtltd.server.integration.PropertyService;
import net.cbtltd.server.integration.ReservationService;
import net.cbtltd.server.integration.TextService;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Image;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerSupportCC;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.jdom.Element;

import com.google.gwt.thirdparty.guava.common.collect.ArrayListMultimap;
import com.google.gwt.thirdparty.guava.common.collect.Multimap;
import com.instantsoftware.secureweblinkplusapi.ClsAddress;
import com.instantsoftware.secureweblinkplusapi.ClsBookingPolicy;
import com.instantsoftware.secureweblinkplusapi.ClsBookingRequest;
import com.instantsoftware.secureweblinkplusapi.ClsChangeLogInfo;
import com.instantsoftware.secureweblinkplusapi.ClsCharge;
import com.instantsoftware.secureweblinkplusapi.ClsCreditCard;
import com.instantsoftware.secureweblinkplusapi.ClsMinNightsInfo;
import com.instantsoftware.secureweblinkplusapi.ClsNonAvailDates;
import com.instantsoftware.secureweblinkplusapi.ClsOwnerInfo;
import com.instantsoftware.secureweblinkplusapi.ClsPartnerAuthentication;
import com.instantsoftware.secureweblinkplusapi.ClsPerson;
import com.instantsoftware.secureweblinkplusapi.ClsPictureInfo;
import com.instantsoftware.secureweblinkplusapi.ClsPropIndex;
import com.instantsoftware.secureweblinkplusapi.ClsProperty;
import com.instantsoftware.secureweblinkplusapi.ClsResBook;
import com.instantsoftware.secureweblinkplusapi.ClsResChangeLogInfo;
import com.instantsoftware.secureweblinkplusapi.ClsResContractInfo;
import com.instantsoftware.secureweblinkplusapi.ClsResQuery;
import com.instantsoftware.secureweblinkplusapi.ClsResQueryInfo;
import com.instantsoftware.secureweblinkplusapi.ClsSeasonAddons;
import com.instantsoftware.secureweblinkplusapi.ClsSeasonRates;
import com.instantsoftware.secureweblinkplusapi.ObjectFactory;
import com.instantsoftware.secureweblinkplusapi.WsWeblinkPlusAPI;
import com.instantsoftware.secureweblinkplusapi.WsWeblinkPlusAPISoap;
import com.mybookingpal.config.RazorConfig;

/**
 * @author nibodha
 * 
 */
public class A_Handler extends PartnerHandler implements IsPartner {

	private static final Logger LOG = Logger.getLogger(A_Handler.class
			.getName());

	WsWeblinkPlusAPISoap proxy;
	private String coid = null;
	private Map<String, Partner> partyIdvsCoid = new HashMap<String, Partner>();
	private Partner parentPartner = null;
	private boolean initialLoad = false;
	private boolean persistFlag = true;
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public String getParentAltpartyid() {
		return parentPartner.getAltpartyid();
	}

	public void add(String coidRec, Partner partnerChild) {
		partyIdvsCoid.put(coidRec, partnerChild);
	}

	public String getCoid() {
		return (coid == null) ? getApikey() : coid;
	}

	public void setCoid(String coid) {
		this.coid = coid;
		if ((coid != null && partyIdvsCoid.get(coid) != null)) {
			partner = partyIdvsCoid.get(coid);
		}
	}

	public Set<String> getAllCoid() {
		return partyIdvsCoid.keySet();
	}

	public URL getUrl() {
		try {
			return new URL(RazorConfig.getValue(HomeAwayUtils.WEBLINK_WSDL));
		} catch (MalformedURLException e) {
			LOG.error(e.getMessage(), e);
		}
		return new WsWeblinkPlusAPI().getWSDLDocumentLocation();
	}

	public A_Handler(Partner partner) {
		super(partner);
		parentPartner = partner;
		proxy = new WsWeblinkPlusAPI(getUrl()).getWsWeblinkPlusAPISoap();
		authenticate();
	}

	@Override
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		if (!isOnline()) {
			return false;
		}
		boolean isavailable = false;
		String productid = reservation.getProductid();
		if(reservation.getProduct()==null) reservation.setProduct(ProductService.getInstance().getProduct(sqlSession, reservation.getProductid()) );
		List<ClsResChangeLogInfo> changeLogInfo = proxy
				.getReservationChangeLog(
						getUserName(),
						getPassword(),
						getCoid(),
						reservation.getProduct().getAltid(),
						PartnerService.changeLogInMin(sqlSession,
								getAltpartyid()), "ALL", 0, 10000)
				.getClsResChangeLogInfo();
		if (changeLogInfo != null && !changeLogInfo.isEmpty()) {
			Set<Date> reservedDates = new TreeSet<Date>();
			Set<Date> cancelledDates = new TreeSet<Date>();
			for (ClsResChangeLogInfo clsResChangeLogInfo : changeLogInfo) {
				if (clsResChangeLogInfo.getStrStatusFlag()
						.equalsIgnoreCase("X")) {
					cancelledDates.addAll(getDatesBetween(
							clsResChangeLogInfo.getDtStartdate(),
							clsResChangeLogInfo.getDtEndDate()));
				} else {
					reservedDates.addAll(getDatesBetween(
							clsResChangeLogInfo.getDtStartdate(),
							clsResChangeLogInfo.getDtEndDate()));
				}
			}
			for (Date date : reservedDates) {
				PartnerService.createSchedule(sqlSession, reservation.getProduct(), date, date,
						new Date());
			}
			for (Date date : cancelledDates) {
				PartnerService.deleteReservation(sqlSession, productid,
						date);
			}
		}
		for (Date reservedDate : PartnerService.getReservationList(sqlSession,
				productid)) {
			if(reservedDate==null) continue;
			isavailable = reservedDate.after(reservation.getDate())
					&& reservedDate.before(reservation.getTodate());
			
//			System.out.println(reservation.getDate() + " -to-"+reservation.getTodate() + " === "+reservedDate +"  ("+isavailable+")");
			if(isavailable) return false;
		}

		// List<ClsProperty> property = proxy.getPropertyDesc(getUserName(),
		// getPassword(), getCoid(), reservation.getProductname(), null,
		// null, true).getClsProperty();
		// if (property == null || property.size() == 0) {
		// return false;
		// }
		// boolean isavailable = false;
		// final ClsProperty prop = property.get(0);
		// for (Date reservedDate : getReservedDates(prop)) {
		// isavailable = reservedDate.after(reservation.getDate())
		// && reservedDate.before(reservation.getTodate());
		// }

		return true;

	}

	/*
	 * for creating reservation
	 * 
	 * @see
	 * net.cbtltd.shared.api.IsPartner#createReservation(org.apache.ibatis.session
	 * .SqlSession, net.cbtltd.shared.Reservation)
	 */
	@Override
	public void createReservation(SqlSession sqlSession, Reservation reservation) {

		ClsBookingRequest objBookingRequest = new ClsBookingRequest();

		ClsAddress clsAddress = new ClsAddress();
		clsAddress.setStrAddress1(reservation.getAddrress());
		clsAddress.setStrAddress2(reservation.getAddrress());
		clsAddress.setStrCity(reservation.getCity());
		clsAddress.setStrCountry(reservation.getCountry());
		clsAddress.setStrHomePhone(reservation.getPhoneNumber());
		clsAddress.setStrState(reservation.getState());
		clsAddress.setStrWorkPhone(reservation.getPhoneNumber());
		clsAddress.setStrZip(reservation.getZip());
		ClsCreditCard clsCreditCard = new ClsCreditCard();

		if (hasSupportsCreditCardToken()) {
			clsCreditCard.setStrToken(HomeAwayUtils.getToken(reservation
					.getCardnumber()));
		} else {
			clsCreditCard.setStrCCNumber(reservation.getCardnumber());
		}
		if(reservation.getProduct()==null) reservation.setProduct(ProductService.getInstance().getProduct(sqlSession, reservation.getProductid()) );
		clsCreditCard.setStrName(reservation.getCardholder());
		clsCreditCard.setStrEmail(reservation.getEmailaddress());
		clsCreditCard.setStrCCType(reservation.getCardType());
		clsCreditCard
				.setIntExpYear(Integer.parseInt(reservation.getCardyear()));
		clsCreditCard.setIntExpMonth(Integer.parseInt(reservation
				.getCardmonth()));
		clsCreditCard.setObjBillingAddress(clsAddress);
		objBookingRequest.setObjCreditCardDetails(clsCreditCard);
		objBookingRequest.setDblCCAmount(reservation.getCost());
		// objBookingRequest.setStrOwnerCode(getOwner(reservation.getProductname()));
		objBookingRequest.setStrCOID(getCoid());
		objBookingRequest.setIntAdults(reservation.getAdult());
		objBookingRequest.setIntChildren(reservation.getChild());
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(reservation.getFromdate());
		reservation.getFromdate();
		try {
			objBookingRequest.setDtCheckIn(DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c));

		} catch (DatatypeConfigurationException e) {
			objBookingRequest.setDtCheckIn(null);
		}
		int nights = (int) HomeAwayUtils.daysBetween(reservation.getFromdate(),
				reservation.getTodate());
		objBookingRequest.setIntNights(nights);
		objBookingRequest.setStrProperty(reservation.getProduct().getAltid());

		ClsPerson clsPerson = new ClsPerson();
		clsPerson.setObjAddress(clsAddress);
		clsPerson.setStrEmail(reservation.getEmailaddress());
		clsPerson.setStrFirstName(reservation.getFirstname());
		clsPerson.setStrLastName(reservation.getFamilyname());
		clsPerson
				.setStrMiddleInitial(reservation.getFamilyname() != null ? reservation
						.getFamilyname().substring(0, 1) : "");
		objBookingRequest.setObjGuestDetails(clsPerson);

		ClsResBook clsBook = proxy.createBooking(objBookingRequest);
//		reservation.setConfirmationId(clsBook.getStrBookingNo());

		reservation.setCost(clsBook.getDblTotalCost());
		ClsBookingPolicy policy;
		GregorianCalendar today = new GregorianCalendar();
		c.setTime(new Date());
		try {
			policy = proxy.getBookingPolicies(getCoid(),
					reservation.getProduct().getAltid(), DatatypeFactory.newInstance()
							.newXMLGregorianCalendar(today), DatatypeFactory
							.newInstance().newXMLGregorianCalendar(c), nights,
					false);
			StringBuilder notes = new StringBuilder();
			if (policy.getStrRentalAgreement() != null
					|| !policy.getStrRentalAgreement().isEmpty())
				notes.append("Rental Agreement:\n").append(
						policy.getStrRentalAgreement());
			if (policy.getStrReservationContract() != null
					|| !policy.getStrReservationContract().isEmpty())
				notes.append("Reservation Contract:\n").append(
						policy.getStrReservationContract());
			if (policy.getStrCancellationPolicy() != null
					|| !policy.getStrCancellationPolicy().isEmpty())
				notes.append("Cancellation Policy:\n").append(
						policy.getStrCancellationPolicy());
			reservation.setNotes(notes.toString());
		} catch (DatatypeConfigurationException e) {
		}

	}

	@Override
	public ReservationPrice readPrice(SqlSession sqlSession,
			Reservation reservation, String productAltId, String currency) {
		ReservationPrice reservationPrice = new ReservationPrice();
		List<QuoteDetail> quoteDetails = new ArrayList<QuoteDetail>();
		try {
			reservation.setQuotedetail(new ArrayList<net.cbtltd.shared.Price>());
			reservation.setCurrency(currency);
			reservationPrice.setCurrency(currency);
			
			if(reservation.getProduct()==null) reservation.setProduct(ProductService.getInstance().getProduct(sqlSession, reservation.getProductid()) );
			Product product = reservation.getProduct();
			
			ClsResQueryInfo queryInfo = new ClsResQueryInfo();
			generateEnquiry(queryInfo, reservation);
			ClsResQuery query = proxy.getReservationQuery(queryInfo);
	
			Date version = new Date();
			Double propertyPriceValue = query.getDblTotalGoods();
			Double totalPriceValue = query.getDblTotalCost();
			Double depositValue = query.getDblDues();
			Double taxesAndFeesValue = query.getDblTotalTax();
			
			String propertyCurrency = product.getCurrency();
			Double currencyRate = WebService.getRate(sqlSession, propertyCurrency, currency, new Date());
	
			if(!propertyCurrency.equalsIgnoreCase(currency)){ 
				totalPriceValue = PaymentHelper.getAmountWithTwoDecimals(totalPriceValue * currencyRate);
				propertyPriceValue = PaymentHelper.getAmountWithTwoDecimals(propertyPriceValue * currencyRate);
				depositValue = PaymentHelper.getAmountWithTwoDecimals(depositValue * currencyRate);
				taxesAndFeesValue = PaymentHelper.getAmountWithTwoDecimals(taxesAndFeesValue * currencyRate);
			}
			
			net.cbtltd.shared.Price propertyPrice = new net.cbtltd.shared.Price();
			propertyPrice = new net.cbtltd.shared.Price();
			propertyPrice.setEntitytype(NameId.Type.Reservation.name());
			propertyPrice.setEntityid(reservation.getId());
			propertyPrice.setType(net.cbtltd.shared.Price.RATE);
			propertyPrice.setName(net.cbtltd.shared.Price.RATE);
			propertyPrice.setState(net.cbtltd.shared.Price.CREATED);
			propertyPrice.setDate(version);
			propertyPrice.setQuantity(1.0);
			propertyPrice.setUnit(Unit.EA);
			propertyPrice.setValue(propertyPriceValue);
			propertyPrice.setCurrency(reservation.getCurrency());
			reservation.getQuotedetail().add(propertyPrice);
			
			//fees
			List<ClsCharge> fees = query.getArrCharges().getClsCharge();
			for(ClsCharge fee : fees){
				Double feeAmountValue = PaymentHelper.getAmountWithTwoDecimals((fee.getDblAmount() + fee.getDblTax()) * currencyRate);
				
				QuoteDetail chargeFeeQd = new QuoteDetail(String.valueOf(feeAmountValue), currency, fee.getStrDesc(), "Included in the price", fee.getStrDesc(), true);
				quoteDetails.add(chargeFeeQd);
				
				net.cbtltd.shared.Price chargeFeePrice = new net.cbtltd.shared.Price();
				chargeFeePrice = new net.cbtltd.shared.Price();
				chargeFeePrice.setEntitytype(NameId.Type.Reservation.name());
				chargeFeePrice.setEntityid(reservation.getId());			
				chargeFeePrice.setType(net.cbtltd.shared.Price.MANDATORY);
				chargeFeePrice.setName(fee.getStrDesc());
				chargeFeePrice.setState(net.cbtltd.shared.Price.CREATED);
				chargeFeePrice.setDate(version);
				chargeFeePrice.setQuantity(1.0);
				chargeFeePrice.setUnit(Unit.EA);
				chargeFeePrice.setValue(feeAmountValue);
				chargeFeePrice.setCurrency(reservation.getCurrency());
				reservation.getQuotedetail().add(chargeFeePrice);
			}
			
			
			reservationPrice.setTotal(totalPriceValue);
			reservationPrice.setPrice(totalPriceValue);
			reservationPrice.setQuoteDetails(quoteDetails);
	
			reservation.setPrice(totalPriceValue);
			reservation.setQuote(totalPriceValue);
			reservation.setDeposit(depositValue);
	
			reservation.setExtra(0.0);
			Double discountfactor = net.cbtltd.server.ReservationService.getDiscountfactor(sqlSession, reservation);
			reservation.setCost(PaymentHelper.getAmountWithTwoDecimals(reservation.getPrice() * discountfactor));
		} catch (ParseException e) {
			LOG.error(e);
			reservation.setCost(0.00);
		}		
		 
		return reservationPrice;
		
	}

	@Override
	public Map<String, String> createReservationAndPayment(
			SqlSession sqlSession, Reservation reservation,
			CreditCard creditCard) {
		ClsBookingRequest objBookingRequest = new ClsBookingRequest();

		ClsAddress clsAddress = new ClsAddress();
		clsAddress.setStrAddress1(reservation.getAddrress());
		clsAddress.setStrAddress2(reservation.getAddrress());
		clsAddress.setStrCity(reservation.getCity());
		clsAddress.setStrCountry(reservation.getCountry());
		clsAddress.setStrHomePhone(reservation.getPhoneNumber());
		clsAddress.setStrState(reservation.getState());
		clsAddress.setStrWorkPhone(reservation.getPhoneNumber());
		clsAddress.setStrZip(reservation.getZip());
		ClsCreditCard clsCreditCard = new ClsCreditCard();
		if(reservation.getProduct()==null) reservation.setProduct(ProductService.getInstance().getProduct(sqlSession, reservation.getProductid()) );
		if (hasSupportsCreditCardToken()) {
			clsCreditCard.setStrToken(HomeAwayUtils.getToken(creditCard
					.getNumber()));
		} else {
			clsCreditCard.setStrCCNumber(creditCard.getNumber());
		}
		clsCreditCard.setStrName(creditCard.getFirstName() + " "
				+ creditCard.getLastName());
		clsCreditCard.setStrEmail(reservation.getEmailaddress());
		clsCreditCard.setStrCCType(creditCard.getType().name());
		clsCreditCard.setIntExpYear(Integer.parseInt(creditCard.getYear()));
		clsCreditCard.setIntExpMonth(Integer.parseInt(creditCard.getMonth()));
		clsCreditCard.setObjBillingAddress(clsAddress);
		objBookingRequest.setObjCreditCardDetails(clsCreditCard);
		objBookingRequest.setDblCCAmount(reservation.getCost());
		// objBookingRequest.setStrOwnerCode(getOwner(reservation.getProductname()));
		objBookingRequest.setStrCOID(getCoid());
		objBookingRequest.setIntAdults(reservation.getAdult());
		objBookingRequest.setIntChildren(reservation.getChild());
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(reservation.getFromdate());
		reservation.getFromdate();
		try {
			objBookingRequest.setDtCheckIn(DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c));
		} catch (DatatypeConfigurationException e) {
			objBookingRequest.setDtCheckIn(null);
		}
		objBookingRequest.setIntNights((int) HomeAwayUtils.daysBetween(
				reservation.getFromdate(), reservation.getTodate()));
		objBookingRequest.setStrProperty(reservation.getProduct().getAltid());

		ClsPerson clsPerson = new ClsPerson();
		clsPerson.setObjAddress(clsAddress);
		clsPerson.setStrEmail(reservation.getEmailaddress());
		clsPerson.setStrFirstName(reservation.getFirstname());
		clsPerson.setStrLastName(reservation.getFamilyname());
		clsPerson
				.setStrMiddleInitial(reservation.getFamilyname() != null ? reservation
						.getFamilyname().substring(0, 1) : "");
		objBookingRequest.setObjGuestDetails(clsPerson);

		ClsResBook clsBook = proxy.createBooking(objBookingRequest);
//		reservation.setConfirmationId(clsBook.getStrBookingNo());

		reservation.setCost(clsBook.getDblTotalCost());
		return null;
	}

	/*
	 * for inquire
	 * 
	 * @see
	 * net.cbtltd.shared.api.IsPartner#inquireReservation(org.apache.ibatis.
	 * session.SqlSession, net.cbtltd.shared.Reservation)
	 */
	@Override
	public void inquireReservation(SqlSession sqlSession,
			Reservation reservation) {
		// proxy.getPropertyIndexes(getUserName(),
		// getPassword(), getCOID(), null);
		if(reservation.getProduct()==null) reservation.setProduct(ProductService.getInstance().getProduct(sqlSession, reservation.getProductid()) );
		ClsResQueryInfo queryInfo = new ClsResQueryInfo();
		generateEnquiry(queryInfo, reservation);
		ClsResQuery qry = proxy.getReservationQuery(queryInfo);
		// reservation.setCost(qry.getDblTotalCost());
		reservation.setTaxrate(qry.getDblTotalTax());
		reservation.setCost(qry.getDblDues());
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

	/*
	 * To read all the product and update our db
	 * 
	 * @see net.cbtltd.shared.api.IsPartner#readProducts()
	 */
	@Override
	public void readProducts() {
		try {
			loadPropertyData(isInitialLoad());
		} catch (RemoteException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@Override
	public void readSchedule() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readSpecials() {
		// List<ClsSpecialsInfo> clsSpecials=
		// proxy.getPropertySpecials(getUserName(), getPassword(),
		// getCoid()).getClsSpecialsInfo();
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

	/**
	 * to load product
	 * 
	 * @param isInitalLoad
	 * @throws RemoteException
	 */
	public void loadPropertyData(boolean isInitalLoad) throws RemoteException {
		List<String> coids = new ArrayList<String>(getAllCoid());
		int count=0;
		if (coids.isEmpty())
			coids.add(getCoid());
		SqlSession sqlSession = RazorServer.openSession();
		try{
		for (String coid : coids) {
			setCoid(coid);
			List<ClsPropIndex> indexes = proxy.getPropertyIndexes(
					getUserName(), getPassword(), coid, null).getClsPropIndex();
//			log("Total products : " +indexes.size()+"\n");
			try {
				if (isInitalLoad) {
					for (ClsPropIndex index : indexes) {
						count++;
						LOG.info(count+" : currently running");
						try {
							buildAndSaveProduct(sqlSession, null, index);
							sqlSession.commit();
							sqlSession.clearCache();
						} catch (Exception e) {
							LOG.error(e.getMessage(), e);
							sqlSession.rollback();
						}
						
					}
					
				} else {
					
					subsequentLoad( indexes);
				}
				loadCommonDetails( indexes);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}
		} finally {

			if (sqlSession != null)
				sqlSession.close();
		}
	}

	/**
	 * contract info and supported cards info are updated
	 * 
	 * @param sqlSession
	 * @param indexes
	 * @throws RemoteException
	 */
	private void loadCommonDetails(
			List<ClsPropIndex> indexes) throws RemoteException {
		SqlSession sqlSession = RazorServer.openSession();
//		ClsWebConfig conf=proxy.getStartupInfo(getUserName(), getPassword(), getCoid());
//		for (Iterator<ClsAttributeInfo> iterator = conf.getArrStayUSAAttributesList().getClsAttributeInfo().iterator(); iterator.hasNext();) {
//			ClsAttributeInfo attributeInfo = (ClsAttributeInfo) iterator.next();
//			System.out.println(attributeInfo.getStrAttrName());
//		}
//		System.out.println("--");
//		for (Iterator<ClsAttributeInfo> iterator = conf.getAttributesList().getClsAttributeInfo().iterator(); iterator.hasNext();) {
//			ClsAttributeInfo attributeInfo = (ClsAttributeInfo) iterator.next();
//			System.out.println(attributeInfo.getStrAttrName());
//		}
		
		ClsResContractInfo info = proxy.getReservationContractInfo(
				getUserName(), getPassword(), getCoid());
		TextService
				.createDescription(
						sqlSession,
						new Text(getAltpartyid() + "_Contract", "Contract",
								Text.Type.Text, new Date(), info.getStrPara1()
										+ info.getStrPara2()
										+ info.getStrPara3()
										+ info.getStrPara4()
										+ info.getStrPara5(), "EN"));

		PropertyManagerSupportCC support = PropertyService.getInstance()
				.getPropertyManagerInfo(sqlSession,
						Integer.parseInt(getAltpartyid()));
		if (info.isBlnCCEnabled()) {
			if (info.getArrCCList() != null) {
				support.setSupportVISA(info.getArrCCList().getString()
						.contains("VISA"));
				support.setSupportMC(info.getArrCCList().getString()
						.contains("MC"));
				support.setSupportDISCOVER(info.getArrCCList().getString()
						.contains("DISC"));
				support.setSupportAE(info.getArrCCList().getString()
						.contains("AMEX"));
				support.setSupportJCB(info.getArrCCList().getString()
						.contains("JCB"));
				support.setSupportDINERSCLUB(info.getArrCCList().getString()
						.contains("DINERSCLUBS"));
			}
		} else {
			support.setNone(true);
		}
		PropertyService.getInstance().updatePropertySupportCCInfo(sqlSession,
				support);
		sqlSession.close();
	}

	/**
	 * for subsequent load by checking change log
	 * 
	 * @param sqlSession
	 * @param indexes
	 * @throws RemoteException
	 */
	private void subsequentLoad(
			List<ClsPropIndex> indexes) throws RemoteException {
		SqlSession sqlSession = RazorServer.openSession();
		Multimap<String, String> logMap = null;
		Map<String, ClsPropIndex> indexMap = null;
		List<Object> changeLogs = proxy.getChangeLogInfo(getUserName(),
				getPassword(), getCoid(),
				PartnerService.changeLogInMin(sqlSession, getAltpartyid()),
				"ALL").getAnyType();
		if (changeLogs == null || changeLogs.size() == 0) {
			return;
		}
		logMap = ArrayListMultimap.create();
		indexMap = new HashMap<String, ClsPropIndex>();
		for (ClsPropIndex index : indexes) {
			indexMap.put(index.getStrId(), index);
		}
		for (Object log : changeLogs) {
			if (log instanceof ClsChangeLogInfo) {
				ClsChangeLogInfo chgLog = (ClsChangeLogInfo) log;
				logMap.put(chgLog.getStrPropId(), chgLog.getStrChangeLog());
			}
		}
		for (String key : logMap.keys()) {
			try {
				buildAndSaveProduct(sqlSession, logMap, indexMap.get(key));
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				sqlSession.rollback();
			}
		}
		sqlSession.close();
	}

	/**
	 * to build product object and persisting it logMap is for reference to
	 * decide what are all the details of product needs to be updated
	 * 
	 * @param sqlSession
	 * @param logMap
	 * @param index
	 * @param coid
	 * @throws Exception
	 */
	public Product buildAndSaveProduct(SqlSession sqlSession,
			Multimap<String, String> logMap, final ClsPropIndex index)
			throws Exception {

		StringBuilder builder = new StringBuilder();
		Product product;
		String countryCode, stateCode = null;
		String altPartyId = getAltpartyid();
		boolean isNewProduct = false;

		if (logMap != null && !logMap.containsKey(index.getStrId())) {
			return null;
		}
		product = ProductService.getInstance().getProduct(sqlSession,
				altPartyId, index.getStrId());
		if (product == null) {
			isNewProduct = true;
			product = new Product();
		}

		List<ClsProperty> property = proxy.getPropertyDesc(getUserName(),
				getPassword(), getCoid(), index.getStrId(), null, null, true)
				.getClsProperty();
		if (property == null || property.size() == 0) {
			return null;
		}
		final ClsProperty prop = property.get(0);
		product.setName(index.getStrName());
		product.setAltid(index.getStrId());
		product.setAltpartyid(altPartyId);
		product.setState(Product.CREATED);
		product.setType(Product.Type.Accommodation.name());
		product.setUnit(Unit.DAY);
		product.setOrganizationid(getCoid());
		product.setOwnerid(getCoid());
		product.setAltSupplierId(getParentAltpartyid());
		product.setSupplierid(altPartyId);
		product.setOwnerid(getParentAltpartyid());
		product.setQuantity(1);
		product.setInquireState(Product.USE_API);
		product.setCurrency(getCurrency());
		// product.setName(prop.getStrPropId());

		countryCode = PartnerService.getCountryCode(prop.getStrCountry());
		if (StringUtils.isNotEmpty(prop.getStrAddress1())) {
			builder.append(prop.getStrAddress1()).append(", ");
		}
		if (StringUtils.isNotEmpty(prop.getStrAddress2())) {
			builder.append(prop.getStrAddress2()).append(", ");
		}
		if (StringUtils.isNotEmpty(prop.getStrCity())) {
			builder.append(prop.getStrCity()).append(" ");
		}
		if (StringUtils.isNotEmpty(prop.getStrZip())) {
			builder.append(prop.getStrZip()).append(", ");
		}
		if (StringUtils.isNotEmpty(prop.getStrState())) {
			stateCode = PartnerService.getRegionCode(prop.getStrState());
			builder.append(prop.getStrState()).append(" ");
		}

		if (StringUtils.isNotEmpty(countryCode)) {
			builder.append(countryCode);
		}

		product.setAddress(new String[] { prop.getStrAddress1(),
				prop.getStrAddress2() });
		product.setPhysicaladdress(builder.toString());
		product.setBathroom((int) prop.getDblBaths());
		if (prop.getDblAvgRating() != null)
			product.setRating(prop.getDblAvgRating().intValue());
		product.setBathroom((int) prop.getDblBaths());
		product.setRoom((int) prop.getDblBeds());
		product.setLatitude(prop.getDblLatitude());
		product.setLongitude(prop.getDblLongitude());
		product.setVersion(new Date());
		if (prop.getDblLatitude() != null && prop.getDblLongitude() != null){
			product.setLocationid(LocationService.getInstance().getLocation(
					prop.getStrArea(), stateCode, countryCode,
					prop.getStrZip(), prop.getDblLatitude(),
					prop.getDblLongitude(), sqlSession));
		}
		product.setInquireState(Product.USE_API);
		product.setUseonepricerow(false);
		if (isPersistFlag()) {
			product = ProductService.getInstance().persistProduct(sqlSession,
					product, isNewProduct);
		} else {
			return product;
		}
		log("\n"+product.getId()+"\t"+index.getStrId()+"\t\t"+index.getStrName()+"\t\t\t");
		log("loc:"+product.getLocationid()+"\t");
		if (logMap == null
				|| (logMap.get(prop.getStrPropId()) != null && logMap.get(
						prop.getStrPropId()).contains("PROPERTY"))) {
			Set<String> amenities = getAttributes(prop);
			AttributeService.getInstance().persistAttributes(sqlSession,
					product, new ArrayList<String>(amenities));
			log("atr:"+amenities.size()+"\t");

			List<ClsMinNightsInfo> minNightinfo = prop.getArrMinNightsInfo()
					.getClsMinNightsInfo();
			persistMinstay(sqlSession, product, minNightinfo);

			List<ClsPictureInfo> picInfo = prop.getArrPicList()
					.getClsPictureInfo();
			persistImage(sqlSession, product, picInfo);
			
			
			TextService.createDescription(sqlSession,
					new Text(product.getPublicId(), "product", Text.Type.Text,
							new Date(), prop.getStrDescPlainText(), "EN"));
			
		}
		if (logMap == null
				|| (logMap.get(prop.getStrPropId()) != null && logMap.get(
						prop.getStrPropId()).contains("AVAILABILITY"))) {

			ReservationService.getInstance().persistBookedDate(sqlSession,
					product, new ArrayList<Date>(getReservedDates(prop)));
			// if(availableDates.size()!=0){
			//
			// List<Date> bookedDate = new ArrayList<Date>();
			// List<Date> availDate=new ArrayList<Date>(availableDates);
			// GregorianCalendar startdt=new GregorianCalendar();
			// startdt.setTime( availableDates.first() );
			// GregorianCalendar enddt=new GregorianCalendar();
			// enddt.setTime( availableDates.last() );
			//
			// for (Date date = startdt.getTime(); !startdt.after(enddt );
			// startdt.add(
			// Calendar.DAY_OF_MONTH, 1), date = startdt.getTime()) {
			// if(Collections.binarySearch(availDate, date,
			// HomeAwayUtils.getComparator())<=-1)
			// {
			// bookedDate.add(date);
			// }
			// }
			// ReservationService.getInstance().persistBookedDate(sqlSession,
			// product,bookedDate);
			// }
		}

		if (logMap == null
				|| (logMap.get(prop.getStrPropId()) != null && logMap.get(
						prop.getStrPropId()).contains("PRICING"))) {

			List<Price> dbPrices = PriceService.getInstance().get(sqlSession,
					NameId.Type.Product.name(), product.getId(),
					product.getAltpartyid(), product.getAltid());
			
			dbPrices.addAll(PriceService.getInstance().get(sqlSession,
					NameId.Type.Feature.name(), product.getId(),
					product.getAltpartyid(), product.getAltid()));
			
			PriceService.getInstance().persistPriceData(sqlSession, dbPrices,
					getAllPrice(prop, product));
		}
		return product;

	}

	public Set<String> getAttributes(final ClsProperty prop) {
		Set<String> amenities = new HashSet<String>();
		for (String string : prop.getArrAttributes().getString()) {
			
			if (HomeAwayUtils.getATTRIBUTES().containsKey(string))
				amenities.add(HomeAwayUtils.getATTRIBUTES().get(
						string));
			else if (HomeAwayUtils.getATTRIBUTES().containsKey(string.toLowerCase()))
				amenities.add(HomeAwayUtils.getATTRIBUTES().get(
						string.toLowerCase()));
			else {
				amenities.add(getAbstractMatch(HomeAwayUtils.getATTRIBUTES(),
						string));
			}
		}
		// update attribute of product
		// Hard Coding property for vacation rental
		amenities.add("PCT53");
		return amenities;
	}

	private String getAbstractMatch(Map<String, String> attributes, String attr) {
		for (Map.Entry<String, String> attribute : attributes.entrySet()) {
			if (attribute.getKey().contains(attr)
					|| attr.matches(attribute.getKey()))
				return attribute.getValue();
		}
		return null;
	}

	public List<Price> getAllPrice(ClsProperty prop, Product product) {
		List<ClsSeasonAddons> seaAdd = prop.getArrseasonAddons()
				.getClsSeasonAddons();
		List<Price> lst = generatePriceAddon(seaAdd, product);

		List<ClsSeasonRates> rates = prop.getArrSeasonRates()
				.getClsSeasonRates();
		lst.addAll(generatePriceRate(rates, product));
		log("price:"+lst.size()+"\t");
		return lst;
	}

	public Set<Date> getReservedDates(ClsProperty prop) {
		List<ClsNonAvailDates> dates = prop.getArrNonAvailDates()
				.getClsNonAvailDates();
		Set<Date> nonAvailableDates = new HashSet<Date>();
		for (ClsNonAvailDates clsNonAvailDates : dates) {
			nonAvailableDates.addAll(getDatesBetween(
					clsNonAvailDates.getDtFromDate(),
					clsNonAvailDates.getDtToDate()));
		}
		log("resv:"+nonAvailableDates.size()+"\t");
		return nonAvailableDates;
	}

	public List<Date> getDatesBetween(XMLGregorianCalendar stDate,
			XMLGregorianCalendar endDate) {
		GregorianCalendar start = stDate.toGregorianCalendar();
		GregorianCalendar end = endDate.toGregorianCalendar();
		List<Date> nonAvailableDates = new ArrayList<Date>();
		for (Date date = start.getTime(); !start.after(end); start.add(
				Calendar.DAY_OF_MONTH, 1), date = start.getTime()) {
			nonAvailableDates.add(date);
		}
		return nonAvailableDates;
	}

	/**
	 * to build price info
	 * 
	 * @param seaAdd
	 * @param product
	 * @return
	 */
	private List<Price> generatePriceAddon(List<ClsSeasonAddons> seaAdd,
			Product product) {
		List<Price> listPrice = new ArrayList<Price>();
		if (seaAdd == null || seaAdd.size() == 0)
			return listPrice;
		for (ClsSeasonAddons clsSeasonAddon : seaAdd) {
			Price price = new Price();
			price.setEntityid(product.getId());
			price.setPartyid(product.getAltpartyid());
			price.setAltid(product.getAltid());
			price.setType(NameId.Type.Reservation.name());
			price.setOrganizationid(product.getAltpartyid());
			price.setEntitytype(NameId.Type.Feature.name());
			price.setAvailable(1);
			price.setFactor(1.0);
			price.setState(Price.CREATED);
			price.setCurrency(product.getCurrency());
			price.setCost(0.0);
			price.setDate(clsSeasonAddon.getDtBeginDate().toGregorianCalendar()
					.getTime());
			price.setTodate(clsSeasonAddon.getDtEndDate().toGregorianCalendar()
					.getTime());
			price.setRule(HomeAwayUtils.getDayOfWeek(price.getDate()));
			if ("Daily".equalsIgnoreCase(clsSeasonAddon.getStrChargeBasis())) {
				price.setName("Daily Rates");
				price.setUnit(Unit.DAY);
			} else if ("Weekly".equalsIgnoreCase(clsSeasonAddon
					.getStrChargeBasis())) {
				price.setName("Week Rates");
				price.setUnit(Unit.WEE);
			} else if ("Monthly".equalsIgnoreCase(clsSeasonAddon
					.getStrChargeBasis())) {
				price.setName("Monthly Rate");
				price.setUnit(Unit.MON);
			}
			price.setMaxStay(clsSeasonAddon.getIntMaxNights());
			price.setMinStay(clsSeasonAddon.getIntMinNights());
			listPrice.add(price);
		}
		return listPrice;
	}

	/**
	 * to build priceinfo
	 * 
	 * @param rates
	 * @param product
	 * @return
	 */
	private List<Price> generatePriceRate(List<ClsSeasonRates> rates,
			Product product) {
		List<Price> listPrice = new ArrayList<Price>();
		for (ClsSeasonRates clsSeasonRates : rates) {
			Price price = new Price();
			price.setAltid(product.getAltid());
			price.setEntityid(product.getId());
			price.setPartyid(product.getAltpartyid());
			price.setType(NameId.Type.Reservation.name());
			price.setOrganizationid(product.getAltpartyid());
			price.setEntitytype(NameId.Type.Product.name());
			price.setAvailable(1);
			price.setFactor(1.0);
			price.setState(Price.CREATED);
			price.setCurrency(product.getCurrency());
			price.setCost(clsSeasonRates.getDblRate());
			price.setMinimum(clsSeasonRates.getDblRate() );
			price.setDate(clsSeasonRates.getDtBeginDate().toGregorianCalendar()
					.getTime());
			price.setTodate(clsSeasonRates.getDtEndDate().toGregorianCalendar()
					.getTime());
			price.setRule(HomeAwayUtils.getDayOfWeek(price.getDate()));
			if (clsSeasonRates.getStrChargeBasis().startsWith("Daily")) {
				price.setName("Daily Rates");
				price.setUnit(Unit.DAY);
			} else if ("Weekly".equalsIgnoreCase(clsSeasonRates
					.getStrChargeBasis())) {
				price.setName("Week Rates");
				price.setUnit(Unit.WEE);
			} else if ("Monthly".equalsIgnoreCase(clsSeasonRates
					.getStrChargeBasis())) {
				price.setName("Monthly Rate");
				price.setUnit(Unit.MON);
			}
			listPrice.add(price);
		}
		return listPrice;
	}

	public ClsResQueryInfo generateEnquiry(ClsResQueryInfo queryInfo,
			Reservation reservation) {
		
		queryInfo.setStrUserId(getUserName());
		queryInfo.setStrPassword(getPassword());
		queryInfo.setStrCOID(getCoid());

		queryInfo.setIntAdults(reservation.getAdult());
		queryInfo.setIntChildren(reservation.getChild());
		queryInfo.setIntNights(reservation.getDuration(Time.DAY).intValue());
		queryInfo.setStrAddress1(reservation.getAddrress());
		queryInfo.setStrAddress2("");
		queryInfo.setStrCheckIn(reservation.getCheckin());
		queryInfo.setStrCity(reservation.getCity());
		queryInfo.setStrCountry(reservation.getCountry());
		queryInfo.setStrEmail(reservation.getEmailaddress());
		queryInfo.setStrFName(reservation.getFirstname());
		queryInfo.setStrHowHeard(reservation.getAgentname());
		queryInfo.setStrHPhone(reservation.getPhoneNumber());
		queryInfo.setStrLName(reservation.getCustomername());
		queryInfo.setStrPromotionCode(null);
		queryInfo.setStrProperty(reservation.getProduct().getAltid());
		queryInfo.setStrProvince(null);
		queryInfo.setStrResType(null);
//		queryInfo.setStrState(PartnerService.getCountryCode(reservation
//				.getCountry()));
		queryInfo.setStrState(reservation.getState());
		queryInfo.setStrTourOperator(null);
		queryInfo.setStrWPhone(reservation.getPhoneNumber());
		queryInfo.setStrZIP(reservation.getZip());
		return queryInfo;
	}

	/**
	 * to persist instay
	 * 
	 * @param sqlSession
	 * @param product
	 * @param minNightinfo
	 */
	private void persistMinstay(SqlSession sqlSession, Product product,
			List<ClsMinNightsInfo> minNightinfo) {
		for (ClsMinNightsInfo clsMinNightsInfo : minNightinfo) {
			PartnerService.createMinStay(sqlSession,
					clsMinNightsInfo.getIntMinNights(), product,
					clsMinNightsInfo.getDtBeginDate().toGregorianCalendar()
							.getTime(), clsMinNightsInfo.getDtEndDate()
							.toGregorianCalendar().getTime(), new Date());
		}
	}

	/**
	 * to construct and persist image details
	 * 
	 * @param sqlSession
	 * @param product
	 * @param picInfo
	 */
	private void persistImage(SqlSession sqlSession, Product product,
			List<ClsPictureInfo> picInfo) {
		if (picInfo != null && picInfo.size() > 0) {
			List<Image> existingImages = sqlSession
					.getMapper(ImageMapper.class).imagesbyproductid(
							new NameId(product.getId()));
			List<String> urls = new ArrayList<String>();
			for (Image image : existingImages) {
				// if(image.getUrl().equalsIgnoreCase(url)) return;
				urls.add(image.getUrl());
			}
			ArrayList<NameId> images=new ArrayList<NameId>();
			for (ClsPictureInfo clsPictureInfo : picInfo) {
				
				
				String name = null;
				String url = clsPictureInfo.getStrURL();
				if (urls.contains(url))
					continue;
				if (clsPictureInfo.getStrImageName() != null) {
					name = clsPictureInfo.getStrImageName();

				} else {
					name = clsPictureInfo.getStrURL();
					name = name.substring(name.lastIndexOf('/') + 1,
							name.lastIndexOf('.'));
				}
				if (name.length() > 100)
					name = name.substring(name.length() - 100);
				if(HomeAwayUtils.isDownloadRequired(clsPictureInfo.getIntWidth(),clsPictureInfo.getIntHeight())){
					images.add(new NameId(name,url));
				}
				else { ImageService.persistImage(name,
						Integer.parseInt(product.getId()), "EN", "",
						net.cbtltd.shared.Image.Type.Linked, url, true, false,
						sqlSession);
				}
			}
			if(!images.isEmpty()){
				log("img:"+images.size()+"\t");
                try {
					// ImageService.uploadImages(sqlSession, NameId.Type.Product, product.getId(), Language.EN, images);
				} catch (Throwable e) {
				}
               }
		}
	}

	/**
	 * @return user name
	 */
	public String getUserName() {
		return HomeAwayUtils.getCredentials().getUserPrincipal().getName();
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return HomeAwayUtils.getCredentials().getPassword();
	}

	// /**
	// * @return company id
	// */
	// public String getCoid() {
	// if (!coids.isEmpty())
	// return coids.get(0);
	// return (getAllCoid()!=null && !coids.isEmpty())?coids.get(0):"";
	// }
	//
	// /**
	// * @return company id
	// */
	// public List<String> getAllCoid() {
	// coids.clear();
	// coids.addAll(Arrays.asList(getApikey().split(",")));
	// // List<ClsCustomerInfo> allCust = proxy.getCustomerList(getUserName(),
	// // getPassword()).getClsCustomerInfo();
	// // if (allCust == null || allCust.size() == 0)
	// // return null;
	// // for (ClsCustomerInfo clsCustomerInfo : allCust) {
	// // coids.add(clsCustomerInfo.getStrCoId() );
	// // }
	// return coids;
	// }

	/**
	 * @return company id
	 */
	public String getOwner(String property) {
		List<ClsOwnerInfo> allOwner = proxy.getOwnerStartupInfo(getUserName(),
				getPassword(), getCoid()).getClsOwnerInfo();
		if (allOwner == null || allOwner.isEmpty())
			return null;
		if (property == null)
			allOwner.get(0).getStrOwner();
		for (ClsOwnerInfo clsOwnerInfo : allOwner) {
			if (clsOwnerInfo.getStrPropId().equalsIgnoreCase(property))
				return clsOwnerInfo.getStrOwner();
		}
		return null;
	}

	public void authenticate() {
		ClsPartnerAuthentication partner = new ClsPartnerAuthentication();
		partner.setStrUserID(getUserName());
		partner.setStrPassword(getPassword());
		ObjectFactory obj = new ObjectFactory();
		final JAXBElement<ClsPartnerAuthentication> requesterCredentials = obj
				.createClsPartnerAuthentication(partner);
		@SuppressWarnings("rawtypes")
		List<Handler> handlersList = new ArrayList<Handler>();
		final Binding binding = ((BindingProvider) proxy).getBinding();
		handlersList.add(new SOAPHandler<SOAPMessageContext>() {
			@Override
			public boolean handleMessage(final SOAPMessageContext context) {
				try {
					final Boolean outbound = (Boolean) context
							.get("javax.xml.ws.handler.message.outbound");
					if (outbound != null && outbound) {
						final Marshaller marshaller = JAXBContext.newInstance(
								ClsPartnerAuthentication.class)
								.createMarshaller();
						// adding header because otherwise it's null
						final SOAPHeader soapHeader = context.getMessage()
								.getSOAPPart().getEnvelope().addHeader();
						// marshalling instance (appending) to SOAP header's xml
						// node
						marshaller.marshal(requesterCredentials, soapHeader);
					}
				} catch (final Exception e) {
					throw new RuntimeException(e);
				}
				return true;
			}

			@Override
			public boolean handleFault(SOAPMessageContext context) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void close(MessageContext context) {
				// TODO Auto-generated method stub

			}

			@Override
			public Set<QName> getHeaders() {
				// TODO Auto-generated method stub
				return null;
			}

		});
		binding.setHandlerChain(handlersList);
	}

	public boolean hasSupportsCreditCardToken() {
		return proxy.getStartupInfo(getUserName(), getPassword(), getCoid())
				.isBlnSupportsCreditCardTokens();
	}

	public boolean isOnline() {
		return proxy.getSocketConnectionStatus(getUserName(), getPassword(),
				getCoid()).equalsIgnoreCase("online");
	}

	/**
	 * @return the initialLoad
	 */
	public final boolean isInitialLoad() {
		return initialLoad;
	}

	/**
	 * @param initialLoad
	 *            the initialLoad to set
	 */
	public final void setInitialLoad(boolean initialLoad) {
		this.initialLoad = initialLoad;
	}

	/**
	 * @return the persistFlag
	 */
	public boolean isPersistFlag() {
		return persistFlag;
	}

	/**
	 * @param persistFlag
	 *            the persistFlag to set
	 */
	public void setPersistFlag(boolean persistFlag) {
		this.persistFlag = persistFlag;
	}
	public static void log(String content) {
		
			LOG.info(content);
//			try {
//			File file = new File("/usr/razor/razor-isi/razorisi/test_file.txt");
// 
//			if (!file.exists()) {
//				file.createNewFile();
//			}
// 
//			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
//			BufferedWriter bw = new BufferedWriter(fw);
//			bw.append(content);
//			System.out.print(content);
//			bw.close();
// 
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}
