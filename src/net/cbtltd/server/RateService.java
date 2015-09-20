/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import net.cbtltd.server.api.EventMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.RateMapper;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.Rate;
import net.cbtltd.shared.Serial;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.VisualizationData;
import net.cbtltd.shared.rate.RateColumnChart;
import net.cbtltd.shared.rate.RateLineChart;
import net.cbtltd.shared.rate.RateNote;
import net.cbtltd.shared.rate.RateNoteTable;
import net.cbtltd.shared.rate.RateUpdate;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/**
 * The Class RateService manages feedback from guests.
 * Ratings are also imported from Flipkey as follows:
 * The request URL for the web service takes up to five parameters:
 * 1. Key: The API key used to authorize Your request.
 * 2. FrontDesk ID: The ID of Your FlipKey Frontdesk
 * 3. FlipKey Property ID: This is the property You are requesting reviews for; this ID is the ID that is used on Your site.
 * When using the reviews_localid request, You must specify the FlipKey property ID of the property You are requesting.
 * When using the review_remoteid request, You must specify the remote ID of the property. As a fallback, the server will
 * check if the ID provided matches a FlipKey Property ID.
 * 4. Search Size (optional): Parameter for the number of reviews requested. Defaults to the maximum, which is 50.
 * 5. Page (optional): Current page of reviews requested. Defaults to 1.
 * 
 * @see <pre>http://data.flipkey.com/feeds/reviews_localid/28816157edbf16d560e14a816c9d107c/1620/(FLIPKEY PROPERTY ID)/(SEARCH SIZE)/(PAGE)</pre>
 * @see <pre>http://data.flipkey.com/feeds/reviews_remoteid/28816157edbf16d560e14a816c9d107c/1620/(REMOTE ID)/(SEARCH SIZE)/(PAGE)</pre>
 * @see <pre>http://shrubbery.mynetgear.net/c/display/W/Parsing+XML+with+Java+6+and+JAXB</pre>
 */
public class RateService
implements IsService {

	private static final Logger LOG = Logger.getLogger(RateService.class.getName());
	private static RateService service;

	/**
	 * Gets the single instance of RateService to manage guest feedback (rating).
	 *
	 * @return single instance of RateService
	 */
	public static synchronized RateService getInstance() {
		if (service == null) {service = new RateService();}
		return service;
	}

	/**
	 *Executes the RateUpdate action to update a Rate event.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Event<Rate> execute(SqlSession sqlSession, RateUpdate action) {
		try {if (action.hasId()) {sqlSession.getMapper(EventMapper.class).update(action);}
		else {
			action.setName(SessionService.pop(sqlSession, action.getOrganizationid(), Serial.RATE));
			sqlSession.getMapper(EventMapper.class).create(action);
		}
		String productid = null;
		for (Rate item : action.getItems()) {
			item.setEventid(action.getId());
			if (item.noId()) {sqlSession.getMapper(RateMapper.class).create(item);}
			else {sqlSession.getMapper(RateMapper.class).update(item);}
			productid = item.getProductid();
		}
		if (productid != null) {sqlSession.getMapper(RateMapper.class).updateproduct(productid);}
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 *Executes the RateColumnChart action to read a table of rating VisualizationData instances for graphical display.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public static final Table<VisualizationData> execute(SqlSession sqlSession, RateColumnChart action) {
		Table<VisualizationData> table = new Table<VisualizationData>();
		try {table.setValue(sqlSession.getMapper(RateMapper.class).columnchart(action.getProductid()));
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 *Executes the RateLineChart action to read a table of rating VisualizationData instances for graphical display.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public static final Table<VisualizationData> execute(SqlSession sqlSession, RateLineChart action) {
		Table<VisualizationData> table = new Table<VisualizationData>();
		try {table.setValue(sqlSession.getMapper(RateMapper.class).linechart(action.getProductid()));}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 *Executes the RateNoteTable action to read a table of RateNote instances containing guest comments.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public static final Table<RateNote> execute(SqlSession sqlSession, RateNoteTable action) {
		Table<RateNote> table = new Table<RateNote>();
		try {table.setValue(sqlSession.getMapper(RateMapper.class).review(action));}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}
}
