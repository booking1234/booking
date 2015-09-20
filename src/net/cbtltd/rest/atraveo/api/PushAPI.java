package net.cbtltd.rest.atraveo.api;
//
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import net.cbtltd.json.JSONService;
import net.cbtltd.rest.AbstractReservation;
import net.cbtltd.rest.Available;
import net.cbtltd.rest.Constants;
import net.cbtltd.rest.ReservationRest;
import net.cbtltd.rest.atraveo.datamodel.AtraveoResponse;
import net.cbtltd.rest.atraveo.datamodel.ResponseFactory;
import net.cbtltd.rest.atraveo.xmlexport.ExportUtils;
import net.cbtltd.rest.response.WeeklyPriceResponse;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.integration.PriceService;
import net.cbtltd.server.integration.ProductService;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.price.PriceRange;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

@Path("/atraveo")
public class PushAPI extends AbstractReservation {
	private static final Logger LOG = Logger.getLogger(PushAPI.class.getName());
	ResponseFactory factory=new ResponseFactory();
	
	
	@GET
	@Path("/availability")
	public AtraveoResponse availability(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Product Id") @QueryParam("objid") String productid) {
		SqlSession sqlSession = null;
		AtraveoResponse response=factory.createResponse();
		try {
			sqlSession = RazorServer.openSession();
			String message = "/atraveo/availabilty?pos=" + pos + "&objid=" + productid ;
			System.out.println(message);
			LOG.debug(message);
			Product product=ProductService.getInstance().getProduct(sqlSession, productid);
			List<Price> prices= PriceService.getInstance().get(sqlSession, product.getEntitytype(), product.getId(), 
					product.getAltpartyid()==null?product.getSupplierid():product.getAltpartyid(), null);
			Date startingDate=new Date();
			Calendar end = Calendar.getInstance();
		    end.setTime(startingDate);
		    end.add(Calendar.YEAR, 1);
		    StringBuilder builder = ExportUtils.getAvailabilityString(sqlSession, product,
					prices, startingDate, end.getTime());
		    response.setStartDate(startingDate);
		    response.setVacancy(builder.toString());
		}catch(Exception e){
			response.setErrNo(String.valueOf(AtraveoError.ERR18.getCode()));
		}finally{
				sqlSession.close();
		}
		
		return response;
		
//		ReservationUtils reservationUtils=new ReservationUtils();
//		reservationUtils.fetchAndUpdateReservationBasedOnNotification(hotelId, reservationId);
	}
	
	@GET
	@Path("/rate")
	public AtraveoResponse rate(
			@Description("Mandatory POS Code")@QueryParam("pos") String pos,
			@Description("Product Id") @QueryParam("objid") String productid,
			@Description("From date") @QueryParam("datefrom") String dateString,
			@Description("To date") @QueryParam("dateto") String toDateString,
			@Description("No. of Person") @QueryParam("persons") String persons) {
		WeeklyPriceResponse priceResponse=null;
		SqlSession sqlSession = null;
		AtraveoResponse response=factory.createResponse();
		try {
			sqlSession = RazorServer.openSession();
			String message = "/atraveo/rate?pos=" + pos + "&objid=" + productid + "&datefrom=" + dateString + "&dateto=" + toDateString + "&persons=" + persons ;
			LOG.debug(message);
			Product product=ProductService.getInstance().getProduct(sqlSession, productid);
			pos=(pos==null)?Model.encrypt(product.getAltpartyid()):pos;
			Available  available=AbstractReservation.getAvailable(productid, dateString, toDateString, pos, null);
			if(available.getAvailable()){
				response.setVacancy("Y");
			}
			priceResponse=AbstractReservation.getWeeklyPrices(pos, productid, dateString, toDateString, product.getCurrency());
			double total=0;
			if(priceResponse.getPriceRanges()!=null){
				for (PriceRange priceRange : priceResponse.getPriceRanges()) {
					total+=priceRange.getAvgPrice();
				}
			}else if(priceResponse.getPrices()!=null){
					for (Price price : priceResponse.getPrices()) {
						total+=price.getValue();
					}
				}
			response.setPrice(String.valueOf(total));
		}catch(Exception e){
			response.setErrNo(String.valueOf(AtraveoError.ERR18.getCode()));
		}finally{
			sqlSession.close();
		}
		return response;
	}
	
	@GET
	@Path("/booking")
	public AtraveoResponse booking(
			@Description("Mandatory POS Code") @QueryParam("pos") String pos,
			@Description("Product Id") @QueryParam("objid") String productid,
			@Description("From date") @QueryParam("datefrom") String datefrom,
			@Description("To date") @QueryParam("dateto") String toDateString,
			@Description("No. of Person") @QueryParam("persons") String persons,
			@Description("No. of Pets") @QueryParam("pets") String pets,
			
			@Description("Salutation") @QueryParam("salutation") String salutation,
			@Description("Name") @QueryParam("name") String name,
			@Description("Firstname") @QueryParam("firstname") String firstname,
			@Description("Street") @QueryParam("street") String street,
			@Description("Zip") @QueryParam("zip") String zip,
			@Description("City") @QueryParam("city") String city,
			@Description("Country") @QueryParam("country") String country,
			@Description("Phone") @QueryParam("phone") String phone,
			@Description("Fax") @QueryParam("fax") String fax,
			@Description("Email") @QueryParam("email") String email,
			@Description("Birthday") @QueryParam("birthday") String birthday,
			@Description("Bookingnumber") @QueryParam("bookingnumber") String bookingnumber,
			@Description("Remarks") @QueryParam("remarks") String remarks,
			
			
			@Description("Payment") @QueryParam("payment") String payment,
			@Description("Credit Card Type") @QueryParam("creditcardtype") String creditcardtype,
			@Description("Credit Card Number") @QueryParam("creditcardnumber") String creditcardnumber,
			@Description("Valid To") @QueryParam("validto") String validto,
			@Description("Check Digit") @QueryParam("checkdigit") String checkdigit
			) {
		//payment=CreditCard&creditcardtype=VISA&creditcardnumber=1234567890123456&validto=2015-10&checkdigit=456
		net.cbtltd.shared.Reservation reservation=new Reservation();
		SqlSession sqlSession = null;
		AtraveoResponse response=factory.createResponse();
		try {
			sqlSession = RazorServer.openSession();
			Product product=ProductService.getInstance().getProduct(sqlSession, productid);
			pos=(pos==null)?Model.encrypt(product.getAltpartyid()):pos;
		if(productid==null||persons==null||name==null||firstname==null||street==null||zip==null||city==null||country==null)
			return getErrorResponse(AtraveoError.ERR12);
		reservation.setProductid(productid);
		if(datefrom==null||toDateString==null) return getErrorResponse(AtraveoError.ERR37);
		try{
		reservation.setFromdate( JSONService.DF.parse(datefrom) );
		}catch(ParseException ex){ return getErrorResponse(AtraveoError.ERR24); }
		try{
			reservation.setTodate( JSONService.DF.parse(toDateString) );
		}catch(ParseException ex){ return getErrorResponse(AtraveoError.ERR21); }
		try{
			reservation.setAdult( Integer.parseInt(persons) );
		}catch(NumberFormatException ex){ return getErrorResponse(AtraveoError.ERR37); }
		reservation.setName(name);
		reservation.setFirstname(firstname);
		reservation.setFamilyname(salutation);
		reservation.setAddrress(street);
		reservation.setCity(city);
		reservation.setZip(zip);
		reservation.setCountry(country);
		reservation.setPhoneNumber(phone);
		reservation.setEmailaddress(email);
		reservation.setMessage(remarks);
		
		if(payment!=null&&payment.equalsIgnoreCase("CreditCard")){
			reservation.setCardholder(name);
			reservation.setCardnumber(creditcardnumber);
			reservation.setCardType(creditcardtype);
			reservation.setCardcode(checkdigit);
			Calendar valid=Calendar.getInstance();
			valid.setTime(JSONService.DF.parse(validto+"-01"));
			reservation.setCardmonth(String.valueOf(valid.get(Calendar.MONTH)) );
			reservation.setCardyear(String.valueOf(valid.get(Calendar.YEAR)) );
		}
		reservation.setState(Reservation.State.Reserved.name());
		reservation.setCurrency(product.getCurrency()!=null?product.getCurrency():"EUR");
		net.cbtltd.shared.Reservation booking=ReservationRest.postReservation(reservation, pos, null);
		if(booking.getReservationid()==null){
			return getErrorResponse(AtraveoError.ERR07);
		}else{
			response.setOrderID(booking.getReservationid());
			response.setTotalPrice(String.valueOf(booking.getPrice()));
		}
		}
		catch(NullPointerException e){
			return getErrorResponse(AtraveoError.ERR37);
		}
		catch(Exception e){
			return getErrorResponse(AtraveoError.ERR07);
		}finally{
			sqlSession.close();
		}
		return response;
	}
	public AtraveoResponse getErrorResponse(AtraveoError error)
	{
		AtraveoResponse response=factory.createResponse();
		response.setErrNo(error.getCode());
		return response;
	}
	
	
	
}
