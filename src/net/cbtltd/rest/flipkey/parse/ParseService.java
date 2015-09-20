/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.flipkey.parse;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.EventMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.RateMapper;
import net.cbtltd.server.project.PartyIds;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Rate;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.api.HasUrls;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** The Class ParseService is to retrieve guest ratings and reviews from FlipKey. */
public class ParseService {

	private static final Logger LOG = Logger.getLogger(ParseService.class.getName());
	private static final DateFormat FDF = new SimpleDateFormat("MM/dd/yyyy");
	
	/**
	 * Get Flipkey Reviews.
	 */
	public static void reviews() {
		LOG.debug("ParseService reviews");
		SqlSession sqlSession = RazorServer.openSession();
		try {
			ArrayList<String> productids = sqlSession.getMapper(ProductMapper.class).flipkeyproducts(HasUrls.FLIPKEY_CLIENTS);
			for (String productid : productids) {getReviews(sqlSession, productid);}
			sqlSession.commit();
		} catch (Throwable x) {
			sqlSession.rollback();
			LOG.error("Flipkey Parse Failed " + x.getMessage());
		}
		finally {sqlSession.close();}
	}

	/**
	 * Gets the reviews.
	 *
	 * @param sqlSession the current SQL session.
	 * @param productid the ID of the product.
	 * @return the product reviews.
	 * @throws RuntimeException the runtime exception.
	 */
	public static void getReviews(SqlSession sqlSession, String productid) throws RuntimeException {
		try {
			LOG.debug("ParseService getReviews " + productid);
			JAXBContext ctx = JAXBContext.newInstance(new Class[] {Rsp.class});
			Unmarshaller um = ctx.createUnmarshaller();
			URL url = new URL("http://data.flipkey.com/feeds/reviews_remoteid/28816157edbf16d560e14a816c9d107c/1620/" + productid);
			Rsp rsp = (Rsp) um.unmarshal(url);
			if (rsp == null) {return;}
			Stats stats = rsp.getStats();
			if (stats == null || !rsp.getStat().equalsIgnoreCase("ok")) {return;}
			Reviews reviews = rsp.getReviews();
			LOG.debug("getReviews reviews " + reviews.getReview().size());
			for (Review review : reviews.getReview()) {
				String eventid = sqlSession.getMapper(EventMapper.class).flipkeyexists(review.getId().toString());
				if (eventid != null ) {continue;}
				
				Rate rate = new Rate();
				rate.setCustomerid(PartyIds.PARTY_FLIPKEY_ID);
				rate.setName(Rate.OVERALL);
				rate.setProductid(productid);
				rate.setQuantity(getInteger(review.getRating()) + 1);
				rate.setType(Rate.RATING);

				Event<Rate> event = new Event<Rate>();
				event.addItem(rate);
				event.setActivity(NameId.Type.Reservation.name());
				event.setActorid(Party.NO_ACTOR);
				event.setDate(FDF.parse(review.getFdate()));
				StringBuilder sb = new StringBuilder();
				sb.append("Review by " + review.getDisplayname() + "\n" + Text.stripHTML(review.getText()));
				if (review.getMgrtext() != null && !review.getMgrtext().equalsIgnoreCase("")) {
					sb.append("\n\nManager's Response\n" + Text.stripHTML(review.getMgrtext()));
				}
				event.setNotes(sb.toString().substring(0, Math.min(sb.toString().length(), 3000)));
				event.setProcess(Event.Type.Rate.name());
				event.setState(Event.CREATED);
				event.setType(Event.NOMINAL);
				
				event.setOrganizationid(PartyIds.PARTY_FLIPKEY_ID);
				event.setName(review.getId().toString());
				sqlSession.getMapper(EventMapper.class).create(event);
				//LOG.debug("getReviews event " + event);

				rate.setEventid(event.getId());
				sqlSession.getMapper(RateMapper.class).create(rate);
			}
		}
		catch(ParseException x) {throw new RuntimeException(Error.date_format.getMessage());}
		catch(MalformedURLException x) {throw new RuntimeException(Error.rest_invalid.getMessage());}
		catch(JAXBException x) {throw new RuntimeException(Error.xml_invalid.getMessage());}
	}

	/**
	 * Gets the integer value of the specified BigDecimal value.
	 *
	 * @param value the specified BigDecimal value.
	 * @return the integer value.
	 */
	public static Integer getInteger(BigDecimal value) {
		if (value == null) {return null;}
		else {return Integer.valueOf(value.toString());}
	}

}
