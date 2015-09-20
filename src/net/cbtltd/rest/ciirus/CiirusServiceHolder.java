package net.cbtltd.rest.ciirus;

import java.math.BigDecimal;
import java.net.URL;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import net.cbtltd.rest.ciirus.ciiruspaymentxml.com.ciirus.xml.CiirusXMLAddCCPayment;
import net.cbtltd.rest.ciirus.ciiruspaymentxml.com.ciirus.xml.CiirusXMLAddCCPaymentSoap;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.ArrayOfCommunities;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.ArrayOfManagementCompanies;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.ArrayOfPropertyClasses;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.ArrayOfPropertyDetails;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.ArrayOfPropertyTypes;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.ArrayOfRate;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.ArrayOfReservations;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.ArrayOfString;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.CiirusXML;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.CiirusXMLSoap;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.SBookingDetails;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.SBookingResult;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.StrucSearchFilterOptions;
import net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml.StrucSearchOptions;
import net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml.AddBookingParameters;
import net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml.ArrayOfGuest;
import net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml.ArrayOfReservation;
import net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml.CiirusXMLAdditionalFunctions1;
import net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml.CiirusXMLAdditionalFunctions1Soap;
import net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml.CleaningFeeSettings;
import net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml.GetExtrasResult;
import net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml.SAddBookingResult;
import net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml.TaxRates;

/**
 * API Username = EricMason API Password = YiPL46Rfi Roger Parry
 * roger@ciirus.com
 */

public class CiirusServiceHolder {
	// private static final Logger LOG =
	// Logger.getLogger(CiirusServiceHolder.class.getName());
	private static final QName SERVICE_NAME = new QName("http://xml.ciirus.com/", "CiirusXML");
	private static final QName SERVICE_NAME_NEW = new QName("http://xml.ciirus.com/", "CiirusXMLAdditionalFunctions1");
	private static final QName SERVICE_NAME_PAYMENT = new QName("http://tempuri.org/", "CiirusXMLAddCCPayment");

	/**
	 * Gets the port to which to connect.
	 *
	 * @return the port.
	 */
	private static CiirusXMLSoap getPort() {
		URL wsdlURL = CiirusXML.CIIRUSXML_WSDL_LOCATION;
		CiirusXML ss = new CiirusXML(wsdlURL, SERVICE_NAME);

		return ss.getCiirusXMLSoap();
	}

	private static CiirusXMLAdditionalFunctions1Soap getPortNew() {
		URL wsdlURL = CiirusXMLAdditionalFunctions1.CIIRUSXMLADDITIONALFUNCTIONS1_WSDL_LOCATION;
		CiirusXMLAdditionalFunctions1 ss = new CiirusXMLAdditionalFunctions1(wsdlURL, SERVICE_NAME_NEW);

		return ss.getCiirusXMLAdditionalFunctions1Soap();
	}

	private static CiirusXMLAddCCPaymentSoap getPortPayment() {
		URL wsdlURL = CiirusXMLAddCCPayment.CIIRUSXMLADDCCPAYMENT_WSDL_LOCATION;
		CiirusXMLAddCCPayment ss = new CiirusXMLAddCCPayment(wsdlURL, SERVICE_NAME_PAYMENT);

		return ss.getCiirusXMLAddCCPaymentSoap();
	}

	public SBookingResult makeBooking(String apiUsername, String apiPassword, SBookingDetails bd) {
		return getPort().makeBooking(apiUsername, apiPassword, bd);
	}

	public ArrayOfPropertyDetails getProperties(String apiUsername, String apiPassword, String arriveDate, String departDate,
			StrucSearchFilterOptions filterOptions, StrucSearchOptions searchOptions) {
		return getPort().getProperties(apiUsername, apiPassword, arriveDate, departDate, filterOptions, searchOptions);
	}

	public ArrayOfPropertyTypes getPropertyTypes(String apiUsername, String apiPassword) {
		return getPort().getPropertyTypes(apiUsername, apiPassword);
	}

	public ArrayOfPropertyClasses getPropertyClasses(String apiUsername, String apiPassword) {
		return getPort().getPropertyClasses(apiUsername, apiPassword);
	}

	public boolean isPropertyAvailable(String apiUsername, String apiPassword, int propertyID, String arrivalDate, String departureDate) {
		return getPort().isPropertyAvailable(apiUsername, apiPassword, propertyID, arrivalDate, departureDate);
	}

	public ArrayOfReservations getReservations(String apiUsername, String apiPassword, int propertyID) {
		return getPort().getReservations(apiUsername, apiPassword, propertyID);
	}

	public ArrayOfRate getPropertyRates(String apiUserName, String apiPassword, int propertyID) {
		return getPort().getPropertyRates(apiUserName, apiPassword, propertyID);
	}

	public ArrayOfString getImageList(String apiUserName, String apiPassword, int propertyID) {
		return getPort().getImageList(apiUserName, apiPassword, propertyID);
	}

	public String getDescriptions(String apiUserName, String apiPassword, int propertyID) {
		return getPort().getDescriptions(apiUserName, apiPassword, propertyID);
	}

	public ArrayOfCommunities getCommunities(String username, String password) {
		return getPort().getCommunities(username, password);
	}

	public ArrayOfManagementCompanies getManagementCompanyList(String apiUserName, String apiPassword) {
		return getPort().getManagementCompanyList(apiUserName, apiPassword);
	}

	public String cancelBooking(String apiUsername, String apiPassword, int bookingID) {
		return getPortNew().cancelBooking(apiUsername, apiPassword, bookingID);
	}

	public ArrayOfReservation getAllReservations(String apiUsername, String apiPassword) {
		return getPortNew().getAllReservations(apiUsername, apiPassword);
	}

	public CleaningFeeSettings getCleaningFee(String apiUsername, String apiPassword, int propertyID) {
		return getPortNew().getCleaningFee(apiUsername, apiPassword, propertyID);
	}

	public TaxRates getTaxRates(String apiUsername, String apiPassword, int propertyID) {
		return getPortNew().getTaxRates(apiUsername, apiPassword, propertyID);
	}

	public GetExtrasResult getExtras(String apiUsername, String apiPassword, int propertyID) {
		return getPortNew().getExtras(apiUsername, apiPassword, propertyID);
	}

	public String getDescriptions(String apiUsername, String apiPassword, int propertyID, boolean getPlainText) {
		return getPortNew().getDescriptions(apiUsername, apiPassword, propertyID, getPlainText);
	}

	public String addGuests(String apiUsername, String apiPassword, int bookingID, ArrayOfGuest guests) {
		return getPortNew().addGuests(apiUsername, apiPassword, bookingID, guests);
	}

	public String addGuestPayment(String apiUsername, String apiPassword, int bookingID, String paymentDate, BigDecimal paymentAmount,
			String description) {
		return getPortNew().addGuestPayment(apiUsername, apiPassword, bookingID, paymentDate, paymentAmount, description);
	}

	public SAddBookingResult addBooking(String apiUsername, String apiPassword, AddBookingParameters bookingParameters) {
		return getPortNew().addBooking(apiUsername, apiPassword, bookingParameters);
	}

	public String addCCPayment(String apiUsername, String apiPassword, XMLGregorianCalendar arrivalDate, String guestEmail, String cardType, 
			String cardNumber, String securityCode, String expiryMonth, String expiryYear, String name, String billingAddress, String billingZip,
			String billingCountry, String billingCity, String billingState, double paymentAmount, int propertyID, int bookingID) {
		return getPortPayment().addCCPayment(apiUsername, apiPassword, arrivalDate, guestEmail, cardType, cardNumber, securityCode, expiryMonth,
				expiryYear, name, billingAddress, billingZip, billingCountry, billingCity, billingState, paymentAmount, propertyID, bookingID);
	}
}