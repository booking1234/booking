/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.TaxMapper;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.Tax;
import net.cbtltd.shared.tax.TaxCreate;
import net.cbtltd.shared.tax.TaxDelete;
import net.cbtltd.shared.tax.TaxExists;
import net.cbtltd.shared.tax.TaxRead;
import net.cbtltd.shared.tax.TaxTable;
import net.cbtltd.shared.tax.TaxUpdate;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** The Class TaxService responds to tax related requests. */
public class TaxService
implements IsService {

	private static final Logger LOG = Logger.getLogger(TaxService.class.getName());
	private static TaxService service;

	/**
	 * Gets the single instance of TaxService to manage Product instances and related requests.
	 *
	 * @return single instance of TaxService.
	 */
	public static synchronized TaxService getInstance() {
		if (service == null){service = new TaxService();}
		return service;
	}
	
	/**
	 * Gets the tax rate on the on the specified value.
	 *
	 * @param sqlSession the current SQL session.
	 * @param id the id of the tax instance to be used to calculate the tax rate.
	 * @param value the taxable amount.
	 * @return the tax rate.
	 */
	protected static Double getTax(SqlSession sqlSession, String id, Double value) {
		Tax tax = null;
		try {tax = sqlSession.getMapper(TaxMapper.class).read(id);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return (tax == null) ? 0.0 : tax.getAmount();
	}

	/**
	 * Gets the tax payable on the on the specified value.
	 *
	 * @param sqlSession the current SQL session.
	 * @param id the id of the tax instance to be used to calculate the tax payable.
	 * @param value the taxable amount.
	 * @return the tax payable.
	 */
	protected static Double getTotal(SqlSession sqlSession, String id, Double value) {
		Tax tax = null;
		try {tax = sqlSession.getMapper(TaxMapper.class).read(id);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return (tax == null) ? 0.0 : tax.getTaxExcluded(value);
	}
	
//	/**
//	 * Gets the tax payable on the on the specified value.
//	 *
//	 * @param sqlSession the current SQL session.
//	 * @param id the id of the tax instance to be used to calculate the tax payable.
//	 * @param value the taxable amount.
//	 * @return the tax payable.
//	 */
//	protected static ArrayList<Price> getDetail(SqlSession sqlSession, PriceTable action) {
//		return sqlSession.getMapper(TaxMapper.class).taxdetail(action);
//	}
	
	/**
	 * Executes the TaxCreate action to create a new tax instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Tax execute(SqlSession sqlSession, TaxCreate action) {
		try{sqlSession.getMapper(TaxMapper.class).create(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}
	
	/**
	 * Executes the TaxExists action to get a tax instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Tax execute(SqlSession sqlSession, TaxExists action) {
		Tax tax = null;
		try {tax = sqlSession.getMapper(TaxMapper.class).exists(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return tax;
	}
	
	/**
	 * Executes the TaxRead action to get a tax instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Tax execute(SqlSession sqlSession, TaxRead action) {
		Tax tax = null;
		try {tax = sqlSession.getMapper(TaxMapper.class).read(action.getId());}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return tax;
	}
	
	/**
	 * Executes the TaxUpdate action to update a tax instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Tax execute(SqlSession sqlSession, TaxUpdate action) {
		LOG.debug("TaxUpdate " + action.hasEntitytype(NameId.Type.Party.name()) + ", " + action);
		try {
			sqlSession.getMapper(TaxMapper.class).update(action);
			if (action.hasEntitytype(NameId.Type.Party.name())) {RelationService.create(sqlSession, Relation.PARTY_TAX, action.getEntityid(), action.getId());}
			else {RelationService.create(sqlSession, Relation.PRODUCT_TAX, action.getEntityid(), action.getId());}
			//MonitorService.update(sqlSession, NameId.Type.Tax, action);
		} catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}
	
	/**
	 * Executes the TaxDelete action to delete the relation from product to tax.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Tax execute(SqlSession sqlSession, TaxDelete action) {
		LOG.debug("TaxDelete " + action);
		try {
			if (action.hasEntitytype(NameId.Type.Party.name())) {RelationService.delete(sqlSession, Relation.PARTY_TAX, action.getEntityid(), action.getId());}
			else {RelationService.delete(sqlSession, Relation.PRODUCT_TAX, action.getEntityid(), action.getId());}
		} catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}
	
	/**
	 * Executes the TaxTable action to read a list of tax NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Tax> execute(SqlSession sqlSession, TaxTable action) {
		Table<Tax> table = new Table<Tax>();
		try {
			table.setDatasize(sqlSession.getMapper(TaxMapper.class).count(action));
			table.setValue(sqlSession.getMapper(TaxMapper.class).list(action));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}
	
	/**
	 * Executes the NameIdAction action to read a list of tax NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdAction action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(TaxMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(TaxMapper.class).nameidbyname(action));}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}
}
