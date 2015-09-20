package net.cbtltd.rest.homeaway.escapianet.datafeed.test;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.rpc.ServiceException;

import net.cbtltd.rest.homeaway.escapianet.datafeed.Launcher;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Partner;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.escapia.evrn._2007._02.ArrayOfCalendarAvailType;
import com.escapia.evrn._2007._02.ArrayOfSourceType;
import com.escapia.evrn._2007._02.CalendarAvailType;
import com.escapia.evrn._2007._02.EVRNContentService;
import com.escapia.evrn._2007._02.EVRNUnitCalendarAvailBatchRQ;
import com.escapia.evrn._2007._02.EVRNUnitCalendarAvailBatchRS;
import com.escapia.evrn._2007._02.SourceType;
import com.escapia.evrn._2007._02.SourceType.RequestorID;

public class LauncherTest {
	
	static String altpartyid;
	static Launcher launcher;
	SqlSession sqlSession;
	Partner partner;
	private static final Logger LOG = Logger.getLogger(LauncherTest.class
			.getName());
	
	@BeforeClass
	public static void setUp() throws Exception {
		altpartyid = "202";
		System.out.println("altpartyid " + altpartyid +" encrypt : "+Model.encrypt("202"));
		launcher = new Launcher(altpartyid);
	}
	
	@Test
	public void testReadProducts() {
		launcher.readProducts();
	}
	
	/*@Test
	public void testReadPrices() {
		launcher.readPrices();
	}*/
	
	/*public static void main(String[] args) throws RemoteException, ServiceException, DatatypeConfigurationException, MalformedURLException {
		EVRNContentService service = new EVRNContentService();
		EVRNUnitCalendarAvailBatchRQ req = new EVRNUnitCalendarAvailBatchRQ();
		req.setVersion(new BigDecimal(1.000));
		req.setSummaryOnly(true);
		ArrayOfSourceType aos = new ArrayOfSourceType();
		SourceType st = new SourceType();
		RequestorID id = new RequestorID();
		id.setID("Mybookingpal");
		id.setMessagePassword("k6g2anbc!mK9");
		st.setRequestorID(id);
		aos.getSource().add(st);
		req.setPOS(aos);
		ArrayOfCalendarAvailType aoc = new ArrayOfCalendarAvailType();
		CalendarAvailType cat = new CalendarAvailType();
		cat.getStateCodeList().add("FL");
		cat.getStateCodeList().add("HI");
		GregorianCalendar gregory = new GregorianCalendar();
		gregory.setTime(new Date());

		cat.setUpdatedAfter(DatatypeFactory.newInstance()
		        .newXMLGregorianCalendar(
			            gregory));
		cat.getCountryCodeList().add("US");
		cat.getCountryCodeList().add("CA");
		cat.setPropertyManagerCode("1020");
		aoc.getCalendarAvail().add(cat);
		req.setCalenadAvails(aoc);
		EVRNUnitCalendarAvailBatchRS res = service.getBasicHttpBindingIEVRNContentService().unitCalendarAvailBatch(req);
		System.out.println("EVRNUnitCalendarAvailBatchRS " + res);
	}*/
}
