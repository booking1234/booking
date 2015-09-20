package net.cbtltd.rest.flipkey.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.cbtltd.rest.flipkey.History;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ReservationMapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.net.httpserver.HttpServer;

@Path("/jerseyTest")
public class FlipKeyRestTest {

    private static final String LOCALHOST = "http://localhost:9998/";

    private static HttpServer server;

    @GET
    @Produces("text/plain")
    @Path("/hellotest")
    public String testSystem(){
        return "Hello. This is a test";
    }

    @BeforeClass
    public static void setUp() throws Exception{
        System.out.println("Creating server");
        PackagesResourceConfig  packagesResourceConfig =new PackagesResourceConfig ("net.cbtltd.rest.flipkey");
   	  server = HttpServerFactory.create(LOCALHOST, packagesResourceConfig);

        System.out.println("Starting server");
        server.start();

        System.out.println("HTTP server started");
        System.out.println("Running tests...");

        testResourceAtUrl(new URL(LOCALHOST + "jerseyTest/hellotest"));
    }

    private static String testResourceAtUrl(URL url) throws Exception {

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String firstLineOfText = reader.readLine();
            System.out.println("Read: " + firstLineOfText);

            System.out.println("System was initialized correctly. About to run actual tests...");

            connection.disconnect();

            return firstLineOfText;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new Exception("could not establish connection to " + url.toExternalForm());
    }

    @Test
    public void testGetHistoryList() throws Exception {
    	System.out.println("Test Case started");
        String activationText = testResourceAtUrl(new URL(LOCALHOST + "flipkey/history"));
       System.out.println("Test Case Completed  "+activationText);
    	/*try{
    	System.out.println("Test Case started");
    	SqlSession sqlSession = RazorServer.openSession();
    	System.out.println("sqlSession "+sqlSession);
    	ReservationMapper mapper=sqlSession.getMapper(ReservationMapper.class);
    	//flipkeyhistory();
    	
    	System.out.println("mapper "+mapper);
    	List<History> listHistory=mapper.flipkeyhistory();
    	System.out.println("Test Case Completed "+listHistory.size());
    	}catch(Exception e){
    		e.printStackTrace();
    	}*/
    }

    /**
    * Destroy the server
    */
    @AfterClass
    public static void tearDown() throws IOException{
        System.out.println("Stopping server");
        server.stop(0);
        System.out.println("Server stopped");
    }

}
