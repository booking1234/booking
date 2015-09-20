/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import java.util.Date;
import java.util.HashMap;

import net.cbtltd.server.api.DataMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.MonitorMapper;
import net.cbtltd.server.cron4j.Scheduler;
import net.cbtltd.server.monitor.Counter;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Mail;
import net.cbtltd.shared.Monitor;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.api.HasData;
import net.cbtltd.server.config.RazorHostsConfig;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.mybookingpal.config.RazorConfig;

/** The Class MonitorService monitors the number and duration of request-responses by the server. */
public class MonitorService
implements IsService {
	
	private static final Logger LOG = Logger.getLogger(MonitorService.class.getName());
//	private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	private static final HashMap<String, Counter> CALLS = new HashMap<String, Counter>();
	private static final StringBuilder SB = new StringBuilder();
	private static MonitorService service;
	private static Scheduler runMonitor = null;
	
	/**
	 * Gets the single instance of MonitorService to manage Monitor instances.
	 * @see net.cbtltd.shared.Monitor
	 *
	 * @return single instance of MonitorService.
	 */
	public static synchronized MonitorService getInstance() {
		if (service == null) {service = new MonitorService();}
		return service;
	}

	/**
	 * Monitors the count and duration of calls
	 *
	 * @param key the key to identify the request being monitored.
	 * @param timestamp the instant when the request is received by the server.
	 */
	public static void monitor(String key, Date timestamp) {

		if (runMonitor == null) {
			runMonitor = new Scheduler();
			runMonitor.schedule("0 * * * *", new Runnable() { //each hour
				public void run() {
					MonitorService.monitor();
				}
			});
			runMonitor.start();
		}
		
		Counter counter = CALLS.get(key);
		if (counter == null) {counter = new Counter();}
		counter.count++;
		counter.duration += Time.toDouble((new Date()).getTime() - timestamp.getTime(), Time.SECOND);
		CALLS.put(key, counter);
	}

	/** Persist the monitor instances - executed periodically by the RazorServer scheduler. */
	public static void monitor() {
		LOG.debug("Save CALLS " + CALLS);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			Monitor monitor = new Monitor();
			monitor.setDate(new Date());
			for (String name : CALLS.keySet()) {
				Counter counter = CALLS.get(name);
				if(counter.count == 0) {continue;}
				monitor.setName(name);
				monitor.setCount(counter.count);
				counter.count = 0;
				monitor.setDuration(counter.duration);
				counter.duration = 0.0;
				sqlSession.getMapper(MonitorMapper.class).create(monitor);
				sqlSession.commit();
			}
		} catch (Throwable x) {LOG.error(x.getMessage()+"CALLS error " + CALLS);}
		finally {sqlSession.close();}
	}

	/** 
	 * Collects server exceptions which are emailed periodically by the RazorServer scheduler.
	 * 
	 * @param x the exception to be logged.
	 */
	public static void log(Throwable x) {
		Date date = new Date();
		SB.append(date);
		if (x == null) {LOG.error("Null Message");}
		else {
			SB.append(x.getMessage());
			LOG.error("log " + x.getMessage());
		}
	}

	/** 
	 * Emails server exceptions periodically by the RazorServer scheduler.
	 */
	public static void emailLog() {
		if (SB.length() > 0) {
			Date date = new Date();
			Mail mail = new Mail();
			mail.setSubject("Razor Error Log " + date);
			mail.setContent(SB.toString());
			mail.setRecipients(RazorHostsConfig.getMonitorEmailAddress());
			//TODO:MailService.send(mail);
			SB.delete(0, SB.length());
		}
	}
	
	/**
	 * Update data.
	 *
	 * @param sqlSession the current SQL session.
	 * @param datatype the data table type.
	 * @param hasdata the object that is updated.
	 */
	public static void update (
			SqlSession sqlSession,
			Data.Origin origin,
			NameId.Type datatype,
			HasData hasdata
		) {
		Data data = new Data();
		try {
			String callStack = "\nHOST_NAME:" + RazorHostsConfig.getApplicationUrl();
			callStack += "\nEvent stack trace:\n";
			StackTraceElement[] currentStackTrace = Thread.currentThread().getStackTrace();
			Integer j = 0;
			for (int i = 1; (i < currentStackTrace.length && j < 10); i++){
				if (currentStackTrace != null && currentStackTrace.length > i){
					if(currentStackTrace[i].getClassName().contains("net.cbtltd")){
						callStack += "class_name:\"" +	currentStackTrace[i].getClassName() + "\", method_name: \"" 
								+ currentStackTrace[i].getMethodName() + "\" line: "+ currentStackTrace[i].getLineNumber() + ".\n";
							j++;						
					}
						
				}
			}
			data.setOrganizationid(hasdata.getOrganizationid());
			data.setActorid(hasdata.getActorid());
			data.setOrigin(origin.name()); 
			data.setDatatype(datatype.name());
			data.setDataid(hasdata.getId());
			data.setState(hasdata.getState());
			data.setTostring(hasdata.toString() + callStack);
			sqlSession.getMapper(DataMapper.class).create(data);
		}
		catch (Throwable x) {
			x.printStackTrace();
			log(new ServiceException(Error.monitor_data, data == null ? "null" : data.toString()));
		}
	}
	
	public static Date getLastUpdate(
			SqlSession sqlSession, 
			NameId.Type datatype,
			String dataid
			) {
		Data action = new Data();
		action.setDatatype(datatype.name());
		action.setDataid(dataid);
		return sqlSession.getMapper(DataMapper.class).last(action);
	}
}
