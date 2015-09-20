/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.soap.ota;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.cbtltd.soap.ota.server.OTACancelRQ;
import net.cbtltd.soap.ota.server.OTACancelRS;
import net.cbtltd.soap.ota.server.OTAHotelAvailRQ;
import net.cbtltd.soap.ota.server.OTAHotelAvailRS;
import net.cbtltd.soap.ota.server.OTAHotelDescriptiveInfoRQ;
import net.cbtltd.soap.ota.server.OTAHotelDescriptiveInfoRS;
import net.cbtltd.soap.ota.server.OTAHotelRatePlanRQ;
import net.cbtltd.soap.ota.server.OTAHotelRatePlanRS;
import net.cbtltd.soap.ota.server.OTAHotelResNotifRQ;
import net.cbtltd.soap.ota.server.OTAHotelResNotifRS;
import net.cbtltd.soap.ota.server.OTAHotelResRQ;
import net.cbtltd.soap.ota.server.OTAHotelResRS;
import net.cbtltd.soap.ota.server.OTAHotelSearchRQ;
import net.cbtltd.soap.ota.server.OTAHotelSearchRS;
import net.cbtltd.soap.ota.server.OTAPingRQ;
import net.cbtltd.soap.ota.server.OTAPingRS;
import net.cbtltd.soap.ota.server.OTAProfileCreateRQ;
import net.cbtltd.soap.ota.server.OTAProfileCreateRS;
import net.cbtltd.soap.ota.server.OTAReadRQ;
import net.cbtltd.soap.ota.server.OTAResRetrieveRS;
import net.cbtltd.soap.ota.server.ObjectFactory;

/**
 * The Class RazorPortType is the SEI (service end point interface) to the Razor SOAP services.
 * @see <pre>http://www.soapui.org/IDE-Plugins/eclipse-plugin.html</pre>
 * @see <pre>http://united-coders.com/phillip-steffensen/developing-a-simple-soap-webservice-using-spring-301-and-apache-cxf-226</pre>
 * 				
 */
@WebService(
		name = "RazorPortType",
        targetNamespace = "http://www.opentravel.org/OTA/2003/05"
)
@XmlSeeAlso({ObjectFactory.class, ObjectFactory.class})
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT, use=SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface RazorPortType {

	/**
	 * Gets the hotel rate plan.
	 *
	 * @param otaHotelRatePlanRQ the OTA hotel rate plan request.
	 * @return the rate plan response.
	 */
	@WebResult(name = "OTA_HotelRatePlanRS", targetNamespace = "http://www.opentravel.org/OTA/2003/05", partName = "OTA_HotelRatePlanRS")
	@WebMethod(operationName = "GetRate")
	public OTAHotelRatePlanRS getRate(
			@WebParam(partName = "OTA_HotelRatePlanRQ", name = "otaHotelRatePlanRQ", targetNamespace = "http://www.opentravel.org/OTA/2003/05")
			OTAHotelRatePlanRQ otaHotelRatePlanRQ
	);

	/**
	 * Creates the reservation for the request.
	 *
	 * @param otaHotelResNotifRQ the OTA hotel reservation notification request.
	 * @return the hotel reservation notification response.
	 */
	@WebResult(name = "OTA_HotelResNotifRS", targetNamespace = "http://www.opentravel.org/OTA/2003/05", partName = "OTA_HotelResNotifRS")
	@WebMethod(operationName = "CreateReservation")
	public OTAHotelResNotifRS createReservation(
			@WebParam(partName = "OTA_HotelResNotifRQ", name = "otaHotelResNotifRQ", targetNamespace = "http://www.opentravel.org/OTA/2003/05")
			OTAHotelResNotifRQ otaHotelResNotifRQ
	);

	/**
	 * Creates the reservation for the eRes request.
	 *
	 * @param otaHotelResNotifRQ the eRes OTA hotel reservation notification request.
	 * @return the hotel reservation notification response.
	 */
	@WebResult(name = "OTA_HotelResRS", targetNamespace = "http://www.opentravel.org/OTA/2003/05", partName = "OTA_HotelResNotifRS")
	@WebMethod(operationName = "EresReservation")
	public OTAHotelResRS eresReservation(
			@WebParam(partName = "OTA_HotelResRQ", name = "otaHotelResRQ", targetNamespace = "http://www.opentravel.org/OTA/2003/05")
			OTAHotelResRQ otaHotelResRQ
	);

	/**
	 * Creates the party for the request.
	 *
	 * @param otaProfileCreateRQ the OTA profile create request.
	 * @return the profile create response.
	 */
	@WebResult(name = "OTA_ProfileCreateRS", targetNamespace = "http://www.opentravel.org/OTA/2003/05", partName = "OTA_ProfileCreateRS")
	@WebMethod(operationName = "CreateParty")
	public OTAProfileCreateRS createParty(
			@WebParam(partName = "OTA_ProfileCreateRQ", name = "otaProfileCreateRQ", targetNamespace = "http://www.opentravel.org/OTA/2003/05")
			OTAProfileCreateRQ otaProfileCreateRQ
	);

	/**
	 * Gets the reservation for the request.
	 *
	 * @param otaReadRQ the OTA read request.
	 * @return the reservation response.
	 */
	@WebResult(name = "OTA_ResRetrieveRS", targetNamespace = "http://www.opentravel.org/OTA/2003/05", partName = "OTA_ResRetrieveRS")
	@WebMethod(operationName = "GetReservation")
	public OTAResRetrieveRS getReservation(
			@WebParam(partName = "OTA_ReadRQ", name = "otaReadRQ", targetNamespace = "http://www.opentravel.org/OTA/2003/05")
			OTAReadRQ otaReadRQ
	);

	/**
	 * Cancel reservation for the request.
	 *
	 * @param otaCancelRQ the OTA cancel request.
	 * @return the cancel response.
	 */
	@WebResult(name = "OTA_CancelRS", targetNamespace = "http://www.opentravel.org/OTA/2003/05", partName = "OTA_CancelRS")
	@WebMethod(operationName = "CancelReservation")
	public OTACancelRS cancelReservation(
			@WebParam(partName = "OTA_CancelRQ", name = "otaCancelRQ", targetNamespace = "http://www.opentravel.org/OTA/2003/05")
			OTACancelRQ otaCancelRQ
	);

	/**
	 * Ping.
	 *
	 * @param otaPingRQ the OTA ping request.
	 * @return the ping response.
	 */
	@WebResult(name = "OTA_PingRS", targetNamespace = "http://www.opentravel.org/OTA/2003/05", partName = "OTA_PingRS")
	@WebMethod(operationName = "Ping")
	public OTAPingRS ping(
			@WebParam(partName = "OTA_PingRQ", name = "otaPingRQ", targetNamespace = "http://www.opentravel.org/OTA/2003/05")
			OTAPingRQ otaPingRQ
	);

	/**
	 * Gets the availability for the request.
	 *
	 * @param otaHotelAvailRQ the OTA hotel availability request.
	 * @return the availability response.
	 */
	@WebResult(name = "OTA_HotelAvailRS", targetNamespace = "http://www.opentravel.org/OTA/2003/05", partName = "OTA_HotelAvailRS")
	@WebMethod(operationName = "GetAvailability")
	public OTAHotelAvailRS getAvailability(
			@WebParam(partName = "OTA_HotelAvailRQ", name = "otaHotelAvailRQ", targetNamespace = "http://www.opentravel.org/OTA/2003/05")
			OTAHotelAvailRQ otaHotelAvailRQ
	);

	/**
	 * Gets the hotel descriptive information for the request.
	 *
	 * @param otaHotelDescriptiveInfoRQ the OTA hotel descriptive information request.
	 * @return the descriptive information response.
	 */
	@WebResult(name = "OTA_HotelDescriptiveInfoRS", targetNamespace = "http://www.opentravel.org/OTA/2003/05", partName = "OTA_HotelDescriptiveInfoRS")
	@WebMethod(operationName = "GetDescription")
	public OTAHotelDescriptiveInfoRS getDescription(
			@WebParam(partName = "OTA_HotelDescriptiveInfoRQ", name = "otaHotelDescriptiveInfoRQ", targetNamespace = "http://www.opentravel.org/OTA/2003/05")
			OTAHotelDescriptiveInfoRQ otaHotelDescriptiveInfoRQ
	);

	/**
	 * Find property for the request.
	 *
	 * @param otaHotelSearchRQ the OTA hotel search request.
	 * @return the hotel search response.
	 */
	@WebResult(name = "OTA_HotelSearchRS", targetNamespace = "http://www.opentravel.org/OTA/2003/05", partName = "OTA_HotelSearchRS")
	@WebMethod(operationName = "FindProperty")
	public OTAHotelSearchRS findProperty(
			@WebParam(partName = "OTA_HotelSearchRQ", name = "otaHotelSearchRQ", targetNamespace = "http://www.opentravel.org/OTA/2003/05")
			OTAHotelSearchRQ otaHotelSearchRQ
	);
	
	/**
	 * Gets the eRes availability for the request.
	 *
	 * @param otaHotelAvailRQ the eRes OTA hotel availability request.
	 * @return the availability response.
	 */
	@WebResult(name = "OTA_HotelAvailRS", targetNamespace = "http://www.opentravel.org/OTA/2003/05", partName = "OTA_HotelAvailRS")
	@WebMethod(operationName = "EresAvailability")
	public OTAHotelAvailRS eresAvailability(
			@WebParam(partName = "OTA_HotelAvailRQ", name = "otaHotelAvailRQ", targetNamespace = "http://www.opentravel.org/OTA/2003/05")
			OTAHotelAvailRQ otaHotelAvailRQ
	);

}
