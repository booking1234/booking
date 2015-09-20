package net.cbtltd.rest.reservation.initialization;

import java.text.ParseException;
import java.util.Date;

import org.apache.ibatis.session.SqlSession;

import net.cbtltd.json.JSONService;
import net.cbtltd.rest.Constants;
import net.cbtltd.rest.ReservationRequest;
import net.cbtltd.rest.reservation.CreateReservationContent;
import net.cbtltd.rest.response.ReservationResponse;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.CreditCardType;

public class DefaultInitializator implements Initializable {

	@Override
	public void initialize(CreateReservationContent content) throws ParseException {
		SqlSession sqlSession = RazorServer.openSession();
		try {
			ReservationRequest request = content.getReservationRequest();
			ReservationResponse response = content.getReservationResponse();
			// Credit card initialization
			CreditCard creditCard = new CreditCard();
			creditCard.setFirstName(request.getFirstName());
			creditCard.setLastName(request.getFamilyName());
			creditCard.setMonth(request.getCardMonth());
			request.setCardNumber(request.getCardNumber().trim());
			creditCard.setNumber(request.getCardNumber());
			creditCard.setType(CreditCardType.get(request.getCardType()));
			creditCard.setYear(request.getCardYear());
			creditCard.setSecurityCode(request.getCvc().toString());
			
			// Initialization of necessary instances
			Product product = sqlSession.getMapper(ProductMapper.class).read(request.getProductId());
			
			if (!product.getState().equals(Product.CREATED)) {
				throw new ServiceException(Error.product_inactive);
			}
			int supplierId = Integer.valueOf(product.getSupplierid());
			PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(supplierId);
			if (propertyManagerInfo == null) {
				throw new ServiceException(Error.database_cannot_find_property_manager,	String.valueOf(supplierId));
			}
	
			response.setPropertyName(product.getName());
			response.setPropertyAddress(ReservationService.getPropertyLocation(sqlSession, product));
	
			// Initialization end
			Party channelPartnerParty = JSONService.getParty(sqlSession, request.getPos());
	
			if (channelPartnerParty == null) {
				throw new ServiceException(Error.reservation_agentid);
			}

			Party customer = processCustomer(sqlSession, request.getEmailAddress(), request.getFamilyName(), request.getFirstName(), product,
					request.getPhoneNumber(), channelPartnerParty, product.getSupplierid(), channelPartnerParty, request.getAddress(), request.getCountry(),
					request.getCity(), request.getZip(), request.getState(), content.getBirthDate());
			Party propertyManager = sqlSession.getMapper(PartyMapper.class).read(propertyManagerInfo.getPropertyManagerId().toString());
			response.setPropertyManagerName(propertyManager.getName());
			response.setPropertyManagerEmail(propertyManager.getEmailaddress());
			response.setPropertyManagerPhone(propertyManager.getDayphone());
	
			content.setReservation(PaymentHelper.prepareReservation(channelPartnerParty, customer, product, propertyManagerInfo, request.getFromDate(), request.getToDate(),
					request.getNotes(), request.getProductId(), request.getAdult(), request.getChild()));
			content.setCreditCard(creditCard);
			content.setProduct(product);
			content.setChannelPartnerParty(channelPartnerParty);
			content.setCustomer(customer);
			content.setPropertyManager(propertyManager);
			content.setPropertyManagerInfo(propertyManagerInfo);
		} finally {
			sqlSession.close();
		}
	}

	private static Party processCustomer(SqlSession sqlSession, String emailAddress, String familyName, String firstName, Product product, String phoneNumber, 
			Party channelPartnerParty, String organizationId, Party agent, String address, String country, String city, String zip, String state, Date birthDate) {
		  Party customer = sqlSession.getMapper(PartyMapper.class).readbyemailaddress(emailAddress);
		  if(customer == null) {
		   customer = JSONService.getDetailedCustomer(sqlSession, emailAddress, familyName, firstName, 
		     product.getSupplierid(), phoneNumber, channelPartnerParty, address, country, city, zip, state, birthDate);
		  } else {
			   customer.setDayphone(phoneNumber);
			   customer.setEmailaddress(emailAddress);
			   customer.setName(familyName, firstName);
			   customer.setState(Party.CREATED);
			   customer.setBirthdate(birthDate);
			   customer.setCreatorid(agent == null ? organizationId : agent.getId());
			   customer.setCountry(country);
			   customer.setCity(city);
			   customer.setRegion(state == null || state.equals("0") ? "" : state);
			   customer.setCurrency(agent == null ? Currency.Code.USD.name() : agent.getCurrency());
			   customer.setLanguage(agent == null ? Language.EN : agent.getLanguage());
			   customer.setLocalAddress(address);
			   customer.setPostalcode(zip);
			   customer.setUsertype(Constants.RENTER_USER_TYPE);
			   sqlSession.getMapper(PartyMapper.class).update(customer);
		}
		sqlSession.commit();
		return customer;
	}
}
