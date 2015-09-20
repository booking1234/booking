/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.soap.ota;

import javax.jws.WebService;

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
import net.cbtltd.soap.ota.server.OtaServiceEres;
import net.cbtltd.soap.ota.server.OtaServiceRazor;

import org.apache.log4j.Logger;

/**
 * The Class RazorPortTypeImpl is the implementation of the SEI (service end point interface) to the Razor SOAP services.
 * @see <pre>http://cxf.apache.org/docs/a-simple-jax-ws-service.html</pre>
 * @see	<pre>http://61.153.44.88/apache/cxf/2.0/developing-a-service.html</pre>
 */

@WebService(endpointInterface="net.cbtltd.soap.ota.RazorPortType")
public class RazorPortTypeImpl implements RazorPortType {

	private static final Logger LOG = Logger.getLogger(RazorPortTypeImpl.class.getName());

	/* (non-Javadoc)
	 * @see net.cbtltd.soap.ota.RazorPortType#cancelReservation(net.cbtltd.server.ota.OTACancelRQ)
	 */
	public OTACancelRS cancelReservation(OTACancelRQ otaCancelRQ) { 
		try {return OtaServiceRazor.cancelRS(otaCancelRQ);} 
		catch (Throwable x) {
			LOG.debug(x.getMessage());
			throw new RuntimeException(x);
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.soap.ota.RazorPortType#getAvailability(net.cbtltd.server.ota.OTAHotelAvailRQ)
	 */
	public OTAHotelAvailRS getAvailability(OTAHotelAvailRQ otaHotelAvailRQ) { 
		try {return OtaServiceRazor.hotelAvailRS(otaHotelAvailRQ);} 
		catch (Throwable x) {
			LOG.debug(x.getMessage());
			throw new RuntimeException(x);
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.soap.ota.RazorPortType#getDescription(net.cbtltd.server.ota.OTAHotelDescriptiveInfoRQ)
	 */
	public OTAHotelDescriptiveInfoRS getDescription(OTAHotelDescriptiveInfoRQ otaHotelDescriptiveInfoRQ) { 
		try {return OtaServiceRazor.hotelDescriptiveInfoRS(otaHotelDescriptiveInfoRQ);} 
		catch (Throwable x) {
			LOG.debug(x.getMessage());
			throw new RuntimeException(x);
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.soap.ota.RazorPortType#ping(net.cbtltd.server.ota.OTAPingRQ)
	 */
	public OTAPingRS ping(OTAPingRQ otaPingRQ) { 
		try {return OtaServiceRazor.pingRS(otaPingRQ);} 
		catch (Throwable x) {
			LOG.debug(x.getMessage());
			throw new RuntimeException(x);
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.soap.ota.RazorPortType#createParty(net.cbtltd.server.ota.OTAProfileCreateRQ)
	 */
	public OTAProfileCreateRS createParty(OTAProfileCreateRQ otaProfileCreateRQ) { 
		try {return OtaServiceRazor.profileCreateRS(otaProfileCreateRQ);} 
		catch (Throwable x) {
			LOG.debug(x.getMessage());
			throw new RuntimeException(x);
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.soap.ota.RazorPortType#getRate(net.cbtltd.server.ota.OTAHotelRatePlanRQ)
	 */
	public OTAHotelRatePlanRS getRate(OTAHotelRatePlanRQ otaHotelRatePlanRQ) { 
		try {return OtaServiceRazor.hotelRatePlanRS(otaHotelRatePlanRQ);} 
		catch (Throwable x) {
			LOG.debug(x.getMessage());
			throw new RuntimeException(x);
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.soap.ota.RazorPortType#createReservation(net.cbtltd.server.ota.OTAHotelResNotifRQ)
	 */
	public OTAHotelResNotifRS createReservation(OTAHotelResNotifRQ otaHotelResNotifRQ) { 
		try {return OtaServiceRazor.hotelResNotifRS(otaHotelResNotifRQ);} 
		catch (Throwable x) {
			LOG.debug(x.getMessage());
			throw new RuntimeException(x);
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.soap.ota.RazorPortType#eresReservation(net.cbtltd.server.ota.OTAHotelResRQ)
	 */
	public OTAHotelResRS eresReservation(OTAHotelResRQ otaHotelResRQ) { 
		try {return OtaServiceEres.hotelResNotifRS(otaHotelResRQ);} 
		catch (Throwable x) {
			LOG.debug(x.getMessage());
			throw new RuntimeException(x);
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.soap.ota.RazorPortType#findProperty(net.cbtltd.server.ota.OTAHotelSearchRQ)
	 */
	public OTAHotelSearchRS findProperty(OTAHotelSearchRQ otaHotelSearchRQ) { 
		try {return OtaServiceRazor.hotelSearchRS(otaHotelSearchRQ);} 
		catch (Throwable x) {
			LOG.debug(x.getMessage());
			throw new RuntimeException(x);
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.soap.ota.RazorPortType#getReservation(net.cbtltd.server.ota.OTAReadRQ)
	 */
	public OTAResRetrieveRS getReservation(OTAReadRQ otaReadRQ) { 
		try {return OtaServiceRazor.resRetrieveRS(otaReadRQ);} 
		catch (Throwable x) {
			LOG.debug(x.getMessage());
			throw new RuntimeException(x);
		}
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.soap.ota.RazorPortType#eresAvailability(net.cbtltd.server.ota.OTAHotelAvailRQ)
	 */
	public OTAHotelAvailRS eresAvailability(OTAHotelAvailRQ otaHotelAvailRQ) { 
		try {return OtaServiceEres.hotelAvailRS(otaHotelAvailRQ);} 
		catch (Throwable x) {
			LOG.debug(x.getMessage());
			throw new RuntimeException(x);
		}
	}

}
