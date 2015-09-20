/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.bookt;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.cbtltd.rest.bookt.ArrayOfKeyValueOfdateTimeint.KeyValueOfdateTimeint;
import net.cbtltd.server.ServiceException;
import net.cbtltd.shared.Error;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;

/**
 * The Class SOAPClient is the test application for Bookt SOAP web services.
 * @see <pre>http://cxf.apache.org/docs/</pre>
 * @see	<pre>http://mail-archives.apache.org/mod_mbox/cxf-issues/201105.mbox/%3C122353421.26874.1304646183148.JavaMail.tomcat@hel.zones.apache.org%3E</pre>
 */
public final class TestSOAP {

	public static final String ENDPOINT_ADDRESS = "http://connect.bookt.com/svc/connect.svc";
	public static final String APIKEY = "89eea03e-b659-474f-9fd3-8ab86240651b";
//	public static final String KISSIMMEE = "c6464e12-dced-4b6a-b28f-2db8d1c751b3";
	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	private static final ObjectFactory OF = new ObjectFactory();
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");

	private TestSOAP() {}

	public static void main(String args[]) throws Exception {
		try {
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(IConnect.class);
			factory.setAddress(ENDPOINT_ADDRESS);
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			IConnect port = (IConnect) factory.create();

			if (false) {
				LOG.debug("Invoking getAvailability...");
				Integer propertyID = 12591; //12593; //24233; //24232; //24248;
				String rateType = "";
				Date startDate = DF.parse("2013-02-01");
				Date endDate = DF.parse("2013-06-33");
				LOG.debug("getAvailability rs=" + propertyID + ", " + rateType + ", " + startDate + ", " + endDate);
				ArrayOfKeyValueOfdateTimeint rs = port.getAvailability(APIKEY, propertyID, rateType, getXGC(startDate), getXGC(endDate));
				LOG.debug("getAvailability rs=" + rs);

				List<KeyValueOfdateTimeint> availability = rs.getKeyValueOfdateTimeint();
				if (availability == null || availability.isEmpty()) {LOG.debug("getAvailability.kv=empty");}
				else {
					for (KeyValueOfdateTimeint available : availability) {
						LOG.debug("getAvailability rs=" + available.toString());
					}
				}
			}
			if (false) {
				LOG.debug("Invoking cancelBooking...");
				String bookingID = "";
				Boolean useInternalID = null;
				Double refundAmount = 0.0;
				Integer options = null;
				Booking rs = port.cancelBooking(APIKEY, bookingID, useInternalID, getBigDecimal(refundAmount), options);
				LOG.debug("cancelBooking rs=" + rs);
			}
			if (false) {
				LOG.debug("Invoking getBooking...");
				String rq = "";
				Boolean _useInternalID = null;
				LOG.debug("getBooking.rq=" + rq);
				Booking rs = port.getBooking(APIKEY, rq, _useInternalID);
				LOG.debug("getBooking rs=" + rs);
			}
			if (false) {
				LOG.debug("Invoking getProperty...");
				String id = "24245"; //24245
				boolean useInternalID = true;
				Property rs = port.getProperty(APIKEY, id, useInternalID);
				LOG.debug("getProperty rs " + rs);
				JAXBContext.newInstance(ObjectFactory.class).createMarshaller().marshal(rs, System.out);
			}
			if (true) {
				LOG.debug("Invoking getPropertyIDs...");
				String lastMod = "2010-07-01T00:00:00";
				ArrayOfint rs = port.getPropertyIDs(APIKEY, getXGC(new Date(1,1,1)));
				List<Integer> propertyids = rs.getInt();
				LOG.debug("getPropertyIDs rs=" + rs + "\n" + propertyids);
			}
		}
		catch (Throwable x) {
			LOG.debug(x.getMessage());
			x.printStackTrace();
		}
		finally{System.exit(0);}
	}
	
	protected static BigDecimal getBigDecimal(Double value) {
		if (value == null) {return null;}
		else {return new BigDecimal(String.valueOf(value));}
	}

	protected static final XMLGregorianCalendar getXGC (Date date) {
		XMLGregorianCalendar xgc = null;
		try {
			xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			xgc.setDay(cal.get(Calendar.DAY_OF_MONTH));
			xgc.setMonth(cal.get(Calendar.MONTH)+1); // +1 because XMLGregorianCalendar is from 1 to 12 while Calendar.MONTH is from 0 to 11 !!!
			xgc.setYear(cal.get(Calendar.YEAR));
			xgc.setTime(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
			//GregorianCalendar c = xgc.toGregorianCalendar();
			//Date fecha = c.getTime();
			//java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
		} 
		catch (DatatypeConfigurationException e) {throw new ServiceException(Error.date_format, date.toString());}
		return xgc;
	}
}
