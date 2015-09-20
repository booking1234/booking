package net.cbtltd.rest.leisurelink;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

public class LeisurelinkService {
	private final static String URL = "https://connect.leisurelink.com/Prerelease/RGWservice.asmx/";
	private final static String channelId = "99";
	private final static String password = "LL$99test0814";
	private final static String countryCode = "US";
	
	public String OTA_HotelSearchRQ() {
		StringBuilder rq = new StringBuilder();
		rq.append("<?xml version=\"1.0\"?>");
		rq.append("<OTA_HotelSearchRQ xmlns=\"http://www.opentravel.org/OTA/2003/05\" EchoToken=\" \" Version=\"1.001\">");
		rq.append("<POS>");
		rq.append("	<Source>");
		rq.append("		<RequestorID Type=\"13\" ID=\"99\" MessagePassword=\"");
		rq.append(password);
		rq.append("\"/>");
		rq.append("	</Source>");
		rq.append("</POS>");
		rq.append("<Criteria>");
		rq.append("	<Criterion>");
		rq.append("		<Address>");	
		rq.append("			<CountryName Code =\"US\"/>");
		rq.append("		</Address>");
		rq.append("	</Criterion>");
		rq.append("</Criteria>");
		rq.append("</OTA_HotelSearchRQ>");
		
		System.out.println();

		PostMethod post = new PostMethod(URL + "OTARequest");
		post.addParameter("OTARequest", rq.toString());
		
		return "";
	}
}
