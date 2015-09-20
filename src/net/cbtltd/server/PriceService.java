/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.YieldMapper;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.DoubleResponse;
import net.cbtltd.shared.Downloaded;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.Yield;
import net.cbtltd.shared.price.PriceCreate;
import net.cbtltd.shared.price.PriceCurrency;
import net.cbtltd.shared.price.PriceDelete;
import net.cbtltd.shared.price.PriceRead;
import net.cbtltd.shared.price.PriceTable;
import net.cbtltd.shared.price.PriceTableConverted;
import net.cbtltd.shared.price.PriceType;
import net.cbtltd.shared.price.PriceUnit;
import net.cbtltd.shared.price.PriceUpdate;
import net.cbtltd.shared.price.PriceValue;
import net.cbtltd.shared.price.ProductFeatureTable;
import net.cbtltd.shared.price.QuoteDetailTable;
import net.cbtltd.shared.price.ServicePriceTable;
import net.cbtltd.shared.price.WidgetPriceTable;
import net.cbtltd.shared.yield.YieldCreate;
import net.cbtltd.shared.yield.YieldDelete;
import net.cbtltd.shared.yield.YieldRead;
import net.cbtltd.shared.yield.YieldTable;
import net.cbtltd.shared.yield.YieldUpdate;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** The Class PriceService responds to price requests. */
public class PriceService
implements IsService {

	private static final Logger LOG = Logger.getLogger(PriceService.class.getName());
	private static PriceService service;

	/**
	 * Gets the single instance of PriceService to manage Price and Yield management instances and tables.
	 *
	 * @return single instance of PriceService.
	 * @see net.cbtltd.shared.Price
	 * @see net.cbtltd.shared.Yield
	 */
	public static synchronized PriceService getInstance() {
		if (service == null) {service = new PriceService();}
		return service;
	}

	/**
	 * Executes the PriceCreate action to create a Price instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Price execute(SqlSession sqlSession, PriceCreate action) {
		try{sqlSession.getMapper(PriceMapper.class).create(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}
	
	public final Price executeCreate(SqlSession sqlSession, Price action) {
		try{sqlSession.getMapper(PriceMapper.class).create(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the PriceRead action to read a Price instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Price execute(SqlSession sqlSession, PriceRead action) {
		Price price = null;
		try {price = sqlSession.getMapper(PriceMapper.class).read(action.getId());}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return price;
	}
	
	public final Price exists(SqlSession sqlSession, PriceRead action) {
		Price price = null;
		try {
			price = sqlSession.getMapper(PriceMapper.class).exists(action);
					
		} catch (Throwable x) {
			sqlSession.rollback();
			MonitorService.log(x);
		}
		return price;
	}
	
	public final Price exists(SqlSession sqlSession, Price action) {
		Price price = null;
		try {
			price = sqlSession.getMapper(PriceMapper.class).exists(action);
					
		} catch (Throwable x) {
			sqlSession.rollback();
			MonitorService.log(x);
		}
		return price;
	}

	/**
	 * Executes the PriceUpdate action to update a Price instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Price execute(SqlSession sqlSession, PriceUpdate action) {
		LOG.debug("PriceUpdate in " + action);
		try {
			Price exists = sqlSession.getMapper(PriceMapper.class).read(action.getId());
			if (action != null && exists != null && action.hashCode() != exists.hashCode()) {action.setVersion(new Date());}
			sqlSession.getMapper(PriceMapper.class).update(action);
			RelationService.unload(sqlSession, Downloaded.PRICE_DOWNLOAD, action.getEntityid(), null);
			TextService.update(sqlSession, action.getTexts());
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Price, action);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("PriceUpdate out " + action);
		return action;
	}
	
	public final Price executeUpdate(SqlSession sqlSession, Price action) {
		LOG.debug("PriceUpdate in " + action);
		try {
			Price exists = sqlSession.getMapper(PriceMapper.class).read(
					action.getId());
			if (action != null && exists != null
					&& action.hashCode() != exists.hashCode()) {
				action.setVersion(new Date());
			}
			sqlSession.getMapper(PriceMapper.class).update(action);
			RelationService.unload(sqlSession, Downloaded.PRICE_DOWNLOAD,
					action.getEntityid(), null);
			TextService.update(sqlSession, action.getTexts());
			MonitorService.update(sqlSession, Data.Origin.CONSOLE,
					NameId.Type.Price, action);
		} catch (Throwable x) {
			sqlSession.rollback();
			MonitorService.log(x);
		}
		LOG.debug("PriceUpdate out " + action);
		return action;
	}

	/**
	 * Executes the PriceDelete action to delete a Price instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Price execute(SqlSession sqlSession, PriceDelete action) {
		try {
			action.setState(Price.FINAL);
			sqlSession.getMapper(PriceMapper.class).update(action);
			RelationService.unload(sqlSession, Downloaded.PRICE_DOWNLOAD, action.getEntityid(), null);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the PriceValue action to calculate a Price value.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the double response
	 */
	public final DoubleResponse execute(SqlSession sqlSession, PriceValue action) {
		DoubleResponse response = new DoubleResponse();
		try {response.setValue(sqlSession.getMapper(PriceMapper.class).value(action));}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return response;
	}

	/**
	 * Executes the PriceTable action to read a Price table.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Price> execute(SqlSession sqlSession, PriceTable action) {
		LOG.debug("PriceTable " + action);
		Table<Price> table = new Table<Price>();
		try {
			table.setDatasize(sqlSession.getMapper(PriceMapper.class).count(action));
			table.setValue(sqlSession.getMapper(PriceMapper.class).list(action));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("\nPriceTable " + table);
		return table;
	}

	/**
	 * Executes the PriceTableConverted action to read a Price table converted to a target currency.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Price> execute(SqlSession sqlSession, PriceTableConverted action) {
		LOG.debug("PriceTableConverted " + action);
		Table<Price> table = new Table<Price>();
		try {
			table.setDatasize(sqlSession.getMapper(PriceMapper.class).count(action));
			ArrayList<Price> prices = sqlSession.getMapper(PriceMapper.class).list(action);
			for (Price price : prices) {
				Double exchangerate = WebService.getRate(sqlSession, price.getCurrency(), action.getCurrency(), new Date());
				price.setValue(price.getValue() * exchangerate);
				price.setMinimum(price.getMinimum() * exchangerate);
				price.setCurrency(action.getCurrency());
			}
			table.setValue(prices);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("PriceTableConverted " + table);
		return table;
	}

	/**
	 * Executes the ProductFeatureTable action to read a feature table of a property.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Price> execute(SqlSession sqlSession, ProductFeatureTable action) {
		Table<Price> response = new Table<Price>();
		try {
			response.setDatasize(sqlSession.getMapper(PriceMapper.class).productfeaturecount(action));
			response.setValue(sqlSession.getMapper(PriceMapper.class).productfeaturelist(action));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return response;
	}

	/**
	 * Executes the QuoteDetailTable action to read the quote detail table of a reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Price> execute(SqlSession sqlSession, QuoteDetailTable action) {
		Table<Price> response = new Table<Price>();
		try {response.setValue(sqlSession.getMapper(PriceMapper.class).quotedetail(action.getEntityid()));}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return response;
	}

	/**
	 * Executes the PriceWidgetTable action to read a Price table for the price widget.
	 * The action get the supplier from the specified product instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Price> execute(SqlSession sqlSession, WidgetPriceTable action) {
		Table<Price> table = new Table<Price>();
		try {
			Product product = sqlSession.getMapper(ProductMapper.class).read(action.getEntityid());
			action.setPartyid(product.getSupplierid());
			ArrayList<Price> prices = sqlSession.getMapper(PriceMapper.class).list(action);
			LOG.debug("WidgetPriceTable action " + action + "\nprices " + prices);
			for (Price price : prices) {
				Double exchangerate = WebService.getRate(sqlSession, price.getCurrency(), action.getCurrency(), new Date());
				price.setValue(price.getValue() * exchangerate);
				price.setMinimum(price.getMinimum() * exchangerate);
				price.setCurrency(action.getCurrency());
			}
			table.setValue(prices);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("WidgetPriceTable out " + table);
		return table;
	}

	/**
	 * Executes the ServicePriceTable action to read a service Price table.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Price> execute(SqlSession sqlSession, ServicePriceTable action) {
		Table<Price> response = new Table<Price>();
		try {
			response.setDatasize(sqlSession.getMapper(PriceMapper.class).servicecount(action));
			response.setValue(sqlSession.getMapper(PriceMapper.class).servicelist(action));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return response;
	}

	/**
	 * Executes the YieldCreate action to create a Yield management instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the yield
	 */
	public final Yield execute(SqlSession sqlSession, YieldCreate action) {
		try {sqlSession.getMapper(YieldMapper.class).create(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the YieldRead action to read a Yield management instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the yield
	 */
	public final Yield execute(SqlSession sqlSession, YieldRead action) {
		Yield yield = null;
		try {yield = sqlSession.getMapper(YieldMapper.class).read(action.getId());}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return yield;
	}

	/**
	 * Executes the YieldUpdate action to update a Yield management instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the yield
	 */
	public final Yield execute(SqlSession sqlSession, YieldUpdate action) {
		try {sqlSession.getMapper(YieldMapper.class).update(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the YieldDelete action to delete a Yield management instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the yield
	 */
	public final Yield execute(SqlSession sqlSession, YieldDelete action) {
		try {
			action.setState(Yield.FINAL);
			sqlSession.getMapper(YieldMapper.class).update(action);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the YieldTable action to read a Yield management table.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Yield> execute(SqlSession sqlSession, YieldTable action) {
		Table<Yield> table = new Table<Yield>();
		try {table.setValue(sqlSession.getMapper(YieldMapper.class).listbyentity(action));}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the PriceCurrency action to read a list of currency NameId instances from the price table.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, PriceCurrency action) {
		Table<NameId> table = new Table<NameId>();
		try {table.setValue(sqlSession.getMapper(PriceMapper.class).pricecurrency(action));}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the PriceType action to read a list of price type NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, PriceType action) {
		Table<NameId> table = new Table<NameId>();
		try {table.setValue(sqlSession.getMapper(PriceMapper.class).pricetype(action));}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the PriceUnit action to read a list of price unit NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, PriceUnit action) {
		Table<NameId> table = new Table<NameId>();
		try {table.setValue(sqlSession.getMapper(PriceMapper.class).priceunit(action));}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}
	
	/**
	 * Executes the PriceReplace action to replace a list of prices with a file of CSV records.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
//	public final PriceReplace execute(SqlSession sqlSession, PriceReplace action) {
//		try {
//			sqlSession.getMapper(PriceMapper.class).erase(action);
//			BufferedReader inputStream = new BufferedReader(new FileReader(action.getFilename()));
//			PriceCSV price = new PriceCSV();
//			String csv = null;
//			while ((csv = inputStream.readLine()) != null) {
//				price.setCSV(csv);
//				sqlSession.getMapper(PriceMapper.class).create(price);
//			}
//			inputStream.close();
//		}
//		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
//		return action;
//	}

	/**
	 * Executes the PriceAppend action to append a list of prices from a file of CSV records.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
//	public final PriceAppend execute(SqlSession sqlSession, PriceAppend action) {
//		try {
//			BufferedReader inputStream = new BufferedReader(new FileReader(action.getFilename()));
//			PriceCSV price = new PriceCSV();
//			String csv = null;
//			while ((csv = inputStream.readLine()) != null) {
//				price.setCSV(csv);
//				sqlSession.getMapper(PriceMapper.class).update(price);
//			}
//			inputStream.close();
//		}
//		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
//		return action;
//	}

	/**
	 * Executes the PriceExport action to export a CSV list of prices.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
//	public final PriceExport execute(SqlSession sqlSession, PriceExport action) {
//		try {
//			PrintWriter outputStream = null;
//			outputStream = new PrintWriter(new FileWriter(action.getFilename()));
//			ArrayList<PriceCSV> prices = sqlSession.getMapper(PriceMapper.class).export(action);
//			for (PriceCSV price : prices) {outputStream.println(price.getCSV());}
//			outputStream.close();
//		}
//		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
//		return action;
//	}
	
	/**
	 * Executes the PriceNameId action to read a list of price NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdAction action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(PriceMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(PriceMapper.class).nameidbyname(action));}
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}
}
