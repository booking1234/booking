/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.PatternSyntaxException;

import javax.servlet.ServletContext;

import net.cbtltd.client.RazorService;
import net.cbtltd.json.JSONService;
import net.cbtltd.rest.flipkey.parse.ParseService;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.cron4j.Scheduler;
import net.cbtltd.server.job.PendingPayment;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.api.HasResponse;
import net.cbtltd.shared.api.HasService;
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.trial.server.SmsService;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mybookingpal.config.RazorConfig;

/**
 * The Class RazorServer is the server side for Razor GWT client.
 * <p>
 * A request is dispatched by the execute method to its Service according to the signature of its action.
 * This allows services to be compact and closely allied to the data table(s) that persist their instances.
 * Each service has a single instance accessed via its static getInstance() method.
 * The required service method is dynamically resolved by the value of its action and is invoked.
 * The value returned by the service method forms the response sent back to the client.
 * <p>
 * The class also has a scheduler based on the UNIX cron command.
 * This is used to schedule the execution of periodic housekeeping tasks.
 */
public final class RazorServer
extends RemoteServiceServlet
implements RazorService {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(RazorServer.class.getName());
	private static final HashMap<Service, IsService> SERVICES = new HashMap<Service, IsService>();
	private static SqlSessionFactory sqlMapper = null;
	
	public static final String ROOT_DIRECTORY = getApplicationRootDirectory() ; 
	/** 
	 * Instantiates a new razor server, its services, and starts the scheduler.
	 * This can be replaced by a dynamic handler manager but has the benefit of simplicity.
	 */
	public RazorServer() {
		super();
		SERVICES.put(Service.ACCOUNT, AccountService.getInstance());
		SERVICES.put(Service.ALERT, AlertService.getInstance());
		SERVICES.put(Service.ASSET, AssetService.getInstance());
		SERVICES.put(Service.ATTRIBUTE, AttributeService.getInstance());
		SERVICES.put(Service.AUDIT, AuditService.getInstance());
		SERVICES.put(Service.CONTRACT, ContractService.getInstance());
		SERVICES.put(Service.FINANCE, FinanceService.getInstance());
		SERVICES.put(Service.JOURNAL, JournalService.getInstance());
		SERVICES.put(Service.IMAGE, ImageService.getInstance());
		SERVICES.put(Service.IMAGETEXT, ImageTextService.getInstance());
		SERVICES.put(Service.LICENSE, LicenseService.getInstance());
		SERVICES.put(Service.LOCATION, LocationService.getInstance());
		SERVICES.put(Service.MAIL, MailService.getInstance());
		SERVICES.put(Service.MONITOR, MonitorService.getInstance());
		SERVICES.put(Service.PARTNER, PartnerService.getInstance());
		SERVICES.put(Service.PARTY, PartyService.getInstance());
		SERVICES.put(Service.PRICE, PriceService.getInstance());
		SERVICES.put(Service.PRODUCT, ProductService.getInstance());
		SERVICES.put(Service.RATE, RateService.getInstance());
		SERVICES.put(Service.REPORT, ReportService.getInstance());
		SERVICES.put(Service.RESERVATION, ReservationService.getInstance());
		SERVICES.put(Service.SESSION, SessionService.getInstance());
		SERVICES.put(Service.SMS, SmsService.getInstance());
		SERVICES.put(Service.TASK, TaskService.getInstance());
		SERVICES.put(Service.TAX, TaxService.getInstance());
		SERVICES.put(Service.TEXT, TextService.getInstance());
		SERVICES.put(Service.WORKFLOW, WorkflowService.getInstance());
//		startScheduler();
//		PartnerService.startSchedulers();
	}

	/**
	 * Opens the database SQL session using the parameters in the specified configuration file.
	 * The database can be tuned using these parameters - but lazy loading may NOT be used.
	 * Connections are drawn from and returned to the pool as soon as possible.
	 * Ensure that service methods execute with minimum latency - long running tasks should run in forked threads. 
	 *
	 * @return the current SQL session.
	 */
	public static SqlSession openSession() {
		LOG.debug("openSession " + sqlMapper);
		if (sqlMapper == null) {
			try {
				String resource = "net/cbtltd/server/xml/Configuration.xml";
				Reader reader = Resources.getResourceAsReader(resource);
				sqlMapper = new SqlSessionFactoryBuilder().build(reader, RazorConfig.getEnvironmentId());
				LOG.debug("Using Configuration: "+RazorConfig.getEnvironmentId());
				LOG.debug("openSession " + sqlMapper);
			}
			catch (Throwable x) {
				LOG.error(x.getMessage());
				x.printStackTrace();
			}
		}
		return sqlMapper.openSession();
	}
	
	/**
	 * Opens the database SQL session using the parameters in the specified configuration file.
	 * The database can be tuned using these parameters - but lazy loading may NOT be used.
	 * Connections are drawn from and returned to the pool as soon as possible.
	 * Ensure that service methods execute with minimum latency - long running tasks should run in forked threads. 
	 *
	 * @return the current SQL session.
	 */
	public static SqlSession openBatchSession() {
		LOG.debug("openSession " + sqlMapper);
		if (sqlMapper == null) {
			try {
				String resource = "net/cbtltd/server/xml/Configuration.xml";
				Reader reader = Resources.getResourceAsReader(resource);
				sqlMapper = new SqlSessionFactoryBuilder().build(reader);
				LOG.debug("openSession " + sqlMapper);
			}
			catch (Throwable x) {
				LOG.error(x.getMessage());
				x.printStackTrace();
			}
		}
		return sqlMapper.openSession(ExecutorType.BATCH);
	}

	/**
	 * Gets the current instance of the specified service.
	 *
	 * @param service the specified service.
	 * @return the service instance.
	 */
	public static IsService getService(Service service) {return SERVICES.get(service);}
	
	/**
	 * Gets the reservation service.
	 *
	 * @return the reservation service.
	 */
	public static ReservationService getReservationService() {return (ReservationService)SERVICES.get(Service.RESERVATION);}
	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#getServletContext()
	 */
	public ServletContext getServletContext() {return super.getServletContext();}
	
	/**
	 * Gets the real path to the servlet location.
	 *
	 * @return the path to the servlet.
	 */
	public String getPath(){return getServletContext().getRealPath("/");}

	/**
	 * Request.
	 *
	 * @param request the request
	 * @return the string
	 * @throws SerializationException the serialization exception
	 */
	public String request(String request) throws SerializationException {
		LOG.debug("Invoking " + request.toString());
		return request;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.RazorService#send(net.cbtltd.shared.api.HasService)
	 */
	public HasResponse send(HasService action) throws SerializationException {
		try {
			Date timestamp = new Date();
			HasResponse response = execute(action);
			MonitorService.monitor(action.getClass().getSimpleName(), timestamp);
			return response;
		}
		catch (PatternSyntaxException x) {
			LOG.error("PatternSyntaxException " + x.toString());
			throw new SerializationException("PatternSyntaxException");
		}
	}

	/**
	 * Executes the specified action on the action.service() service.
	 *
	 * @param action the action to be executed.
	 * @return the response.
	 * @throws SerializationException the serialization exception
	 */
	public HasResponse execute(HasService action)  throws SerializationException {

		HasResponse response = null;
		LOG.debug("\n\nRazorServer execute " + action.getClass().getName() + "\naction " + action);
		String classname = action.service().classname();

		try {
			Class<?> c = Class.forName(classname); // say AccountService
			Method[] allMethods = c.getDeclaredMethods(); // can be static
			for (Method m : allMethods) {
				String mname = m.getName();
				if (mname.equals("execute")) {
					Type[] pType = m.getGenericParameterTypes();
					if (pType[1].toString().equals("class " + action.getClass().getName())) {
						SqlSession sqlSession = openSession();
						try {
							m.setAccessible(true);
							Object t = getService(action.service());
							response = (HasResponse) m.invoke(t, sqlSession, action);
							sqlSession.commit();
						} 
						catch (IllegalAccessException x) {LOG.error("IllegalAccessException " + action.getClass().getName() + "\n" + x.getMessage());} 
						catch (InvocationTargetException x) {LOG.error("InvocationTargetException exception " + action.getClass().getName() + "\n" + x.getMessage());} 
						catch (Throwable x) {
							sqlSession.rollback();
							MonitorService.log(x);
						}
						finally {sqlSession.close();}
					}
				}
			}
		} 
		catch (ClassNotFoundException x) {
			MonitorService.log(x);
			LOG.error("ClassNotFoundException " + action.getClass().getName() + "\n" + x.getMessage());
		}
		return response;
	}

	/**
	 * Schedules runnable tasks according to CRON patterns
	 * @see	Scheduler at http://www.sauronsoftware.it/projects/cron4j/manual.php
	 * A UNIX crontab-like pattern is a string split in five space separated parts. 
	 * Each part is intended as:
	 * 
	 * 1 Minutes sub-pattern. During which minutes of the hour should the task been launched? 
	 * The values range is from 0 to 59. 
	 * 
	 * 2 Hours sub-pattern. During which hours of the day should the task been launched? 
	 * The values range is from 0 to 23. 
	 * 
	 * 3 Days of month sub-pattern. During which days of the month should the task been launched? 
	 * The values range is from 1 to 31. 
	 * The special value "L" can be used to recognize the last day of month.
	 * 
	 * 4 Months sub-pattern. During which months of the year should the task been launched? 
	 * The values range is from 1 (January) to 12 (December), otherwise this sub-pattern allows 
	 * the aliases "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov" and "dec".
	 * 
	 * 5 Days of week sub-pattern. During which days of the week should the task been launched? 
	 * The values range is from 0 (Sunday) to 6 (Saturday), otherwise this sub-pattern allows 
	 * the aliases "sun", "mon", "tue", "wed", "thu", "fri" and "sat".
	 * 
	 * The star wildcard character is also admitted, indicating "every minute of the hour", 
	 * "every hour of the day", "every day of the month", "every month of the year" and 
	 * "every day of the week", according to the sub-pattern in which it is used.
	 */
	private void startScheduler () {
		
		//TODO: LicenseService.license(new Date(113, 8, 30)); //30 Sep 2013
		//TODO: LicenseService.balance();
		//TODO: PartyService.progress();
		//TODO:	ReservationService.specialrefresh();
		//TODO: ParseService.reviews();
		//TODO: TextService.createAudio(); //initialize audio files
		//TODO: MonitorService.emailLog();

//TODO:		Scheduler runMonitor = new Scheduler();
//		runMonitor.schedule("0 * * * *", new Runnable() { //each hour
//			public void run() {
//				MonitorService.monitor();
//			}
//		});
//		runMonitor.start();

		Scheduler runProgress = new Scheduler();
		runProgress.schedule("15 13 * * *", new Runnable() { //each day at 13h15
			public void run() {PartyService.progress();}
		});
		runProgress.start();

		Scheduler runSpecial = new Scheduler();
		runSpecial.schedule("30 13 * * *", new Runnable() { //each day at 13h30
			public void run() {ReservationService.specialrefresh();}
		});
		runSpecial.start();

		Scheduler runReviews = new Scheduler();
		runReviews.schedule("0 1 * * *", new Runnable() { //each day at 1h00
			public void run() {ParseService.reviews();}
		});
		runReviews.start();

		Scheduler runPendingTransactions = new Scheduler();
		runPendingTransactions.schedule("59 23 * * *", new Runnable() { // each day at 23h00
			public void run() {PendingPayment.execute();}
		});
		runPendingTransactions.start();
		
		if (isLive()) {
			LOG.error("Start license schedulers at " + new Date());
			Scheduler runLicense = new Scheduler();
			runLicense.schedule("0 0 1 * *", new Runnable() { //start of month end day
				public void run() {LicenseService.license(Time.addDuration(new Date(), -1, Time.DAY));}
			});
			runLicense.start();
	
			Scheduler runBalance = new Scheduler();
			runBalance.schedule("10 14 * * sun", new Runnable() { //each sunday at 14h10
				public void run() {LicenseService.balance();}
			});
			runBalance.start();
		}
	}
	/** 
	 * @return true if using windows environment. 
	 */
	
	public static boolean isWindowsEnviroment()
	{
		return System.getProperty("os.name").toLowerCase().indexOf("win") >=0; 
	}
	
	public static String getApplicationRootDirectory()
	{
		String hostname = getHostName();
		String directory =HasUrls.ROOT_DIRECTORY; 
		String osName = System.getProperty("os.name").toLowerCase();
		boolean isLinux = osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0 || osName.indexOf("aix") > 0;

		if(hostname.compareToIgnoreCase("uat.mybookingpal.com")==0 || isLinux){
			directory = HasUrls.ROOT_LINUX_DIRECTORY;
		}
		else if(hostname.compareToIgnoreCase("razor-cloud.com")==0){
			directory = HasUrls.ROOT_DIRECTORY;
		}
		else{
			directory =HasUrls.ROOT_DIRECTORY; 
		}
		return directory;
	}
	/** 
	 * @returns hostname from java: http://stackoverflow.com/questions/7348711/recommended-way-to-get-hostname-in-java 
	 */               
	public static String getHostName() 
	{
		String hostname = ""; 
		try {
			hostname =  InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} //finally{return hostname;}
		return hostname;
	}
	/**
	 * Checks if the live.txt file exists
	 * 
	 * @return true if the live.txt file exists
	 */
	public static boolean isLive() {
		boolean live = true;
		try {Resources.getResourceAsReader("net/cbtltd/server/live.txt");}
		catch (IOException x) {live = false;}
		return live;
	}
	
	public static void main(String[] args) throws ParseException {
		Calendar c = Calendar.getInstance();
		Date date = JSONService.DF.parse("2015-01-31");
		Locale.setDefault(Locale.US);
		c.setTime(date);
		System.out.println(c.getTime());
		LicenseService.license(c.getTime());
	}
}

