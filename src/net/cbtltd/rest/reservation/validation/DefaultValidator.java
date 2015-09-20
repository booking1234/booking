package net.cbtltd.rest.reservation.validation;

import java.text.ParseException;
import java.util.Date;

import net.cbtltd.rest.ReservationRequest;
import net.cbtltd.rest.reservation.CreateReservationContent;
import net.cbtltd.server.ServiceException;
import net.cbtltd.shared.Error;

import org.apache.commons.lang.StringUtils;

import com.ibm.icu.util.Calendar;

public class DefaultValidator implements Validator {

	@Override
	public void validate(CreateReservationContent content) throws ParseException {
		ReservationRequest request = content.getReservationRequest();
		String address = resetValue(request.getAddress());
		String country = resetValue(request.getCountry());
		String city = resetValue(request.getCity());
		String state = resetValue(request.getState());
		request.setAddress(address);
		request.setCountry(country);
		request.setAddress(city);
		request.setCountry(state);
		
		if(StringUtils.isEmpty(request.getPos()) || StringUtils.isEmpty(request.getProductId()) || StringUtils.isEmpty(request.getFromDate()) || 
				StringUtils.isEmpty(request.getToDate()) || StringUtils.isEmpty(request.getEmailAddress()) || StringUtils.isEmpty(request.getFamilyName()) ||
				StringUtils.isEmpty(request.getFirstName()) || StringUtils.isEmpty(request.getPhoneNumber()) || request.getCardType() == null ||  
				request.getChild() == null || StringUtils.isEmpty(request.getCurrency())  || request.getBirthDay() == null || request.getBirthMonth() == null || 
				request.getBirthYear() == null || request.getAmount() == null || request.getZip() == null ) {
			throw new ServiceException(Error.parameter_absent);
		}
		
		if(request.getAdult() == null || request.getAdult() < 1 ){
			throw new ServiceException(Error.missing_adult_parameter);
		}
		if( StringUtils.isEmpty(request.getCardNumber()) || StringUtils.isEmpty(request.getCardMonth()) || 
				StringUtils.isEmpty(request.getCardYear()) || request.getCvc() == null) {
			throw new ServiceException(Error.credit_card_error);
		}

		if(request.getFromDate().compareToIgnoreCase(request.getToDate()) > 0) {
			throw new ServiceException(Error.date_range);
		}
		
		Date birthDate = null;
		if(request.getBirthYear() != null && request.getBirthMonth() != null && request.getBirthDay() != null) {
			birthDate = stringToDate(request.getBirthYear(), request.getBirthMonth() - 1, request.getBirthDay());
		}
		
		content.setBirthDate(birthDate);
	}

	private static String resetValue(String value) {
		if(value != null && value.equals("0")) {
			return null;
		} else {
			return value;
		}
	}

	public static Date stringToDate(Integer year, Integer month, Integer day) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return calendar.getTime();
	}
}
