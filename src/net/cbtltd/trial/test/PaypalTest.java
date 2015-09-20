package net.cbtltd.trial.test;

import java.util.Map;

import net.cbtltd.rest.paypal.PaypalHandlerNew;
import net.cbtltd.rest.paypal.PaypalHelper;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.shared.finance.gateway.GatewayInfo;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.ibatis.session.SqlSession;

public class PaypalTest {
	public static void main(String[] args) throws Exception {
		GatewayInfo payPalNew = new GatewayInfo();
		PaypalHandlerNew paypalHandlerNew = new PaypalHandlerNew(payPalNew);
//			test();
		SqlSession sqlSession = null;
		try {
			sqlSession = RazorServer.openSession();
			Map<String, String> payment = paypalHandlerNew.createPaymentByCreditCard(sqlSession, "USD", 12., "12345");
			System.out.println(payment);
		} catch (ServiceException e) {
			String json = PaypalHelper.getJsonFromString(e.getMessage());
			System.out.println("Error: " + PaypalHelper.getParamValueFromJson(json, "error_message"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}
	
	public static void test() throws Throwable{
		String URL = "https://api.sandbox.paypal.com/v1/payments/payment/PAY-2XR800907F429382MKEBWOSA";
		getConnection(URL, "");
	}
	
	private static final void getConnection(String URL, String rq) throws Throwable {
		//String URL = "http://localhost/xml/rest/reservation?pos=a3a2e74b809e0e87";
		GetMethod post = new GetMethod(URL);
	    post.addRequestHeader("Accept" , "application/xml");
	    RequestEntity entity = new StringRequestEntity(rq, "application/xml", null);
//	    post.setRequestEntity(entity);
	    HttpClient httpclient = new HttpClient();		   
	    int rs = httpclient.executeMethod(post);
		System.out.println("rs=" + rs + " " + post.getResponseBodyAsString());
	}
	
}
