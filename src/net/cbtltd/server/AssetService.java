/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import net.cbtltd.server.api.AssetMapper;
import net.cbtltd.server.api.DepreciationMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.shared.Asset;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.asset.AssetCreate;
import net.cbtltd.shared.asset.AssetDelete;
import net.cbtltd.shared.asset.AssetExists;
import net.cbtltd.shared.asset.AssetRead;
import net.cbtltd.shared.asset.AssetTable;
import net.cbtltd.shared.asset.AssetUpdate;
import net.cbtltd.shared.asset.DepeciationNameId;
import net.cbtltd.shared.asset.LocationNameId;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** The Class AssetService responds to asset actions. */
public class AssetService
implements IsService {

	private static final Logger LOG = Logger.getLogger(AssetService.class.getName());
	private static AssetService service;

	/**
	 * Gets the single instance of AssetService.
	 *
	 * @return single instance of AssetService
	 */
	public static synchronized AssetService getInstance() {
		if (service == null) {service = new AssetService();}
		return service;
	}

	/**
	 * Executes the AssetCreate action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Asset execute(SqlSession sqlSession, AssetCreate action) {
		try {
			sqlSession.getMapper(AssetMapper.class).create(action);
			RelationService.create(sqlSession, Relation.ORGANIZATION_ASSET, action.getOrganizationid(), action.getId());
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the AssetExists action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Asset execute(SqlSession sqlSession, AssetExists action) {
		Asset asset = null;
		try {
			asset = sqlSession.getMapper(AssetMapper.class).exists(action);
			if (asset != null) {RelationService.create(sqlSession, Relation.ORGANIZATION_ASSET, action.getOrganizationid(), asset.getId());}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return asset;
	}

	/**
	 * Executes the AssetRead action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Asset execute(SqlSession sqlSession, AssetRead action) {
		Asset asset = null;
		try {
			asset = sqlSession.getMapper(AssetMapper.class).read(action.getId());
			asset.setImageurls(sqlSession.getMapper(TextMapper.class).imageidsbynameid(new NameId(NameId.Type.Asset.name(), action.getId())));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return asset;
	}

	/**
	 * Executes the AssetUpdate action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Asset execute(SqlSession sqlSession, AssetUpdate action) {
		try {
			sqlSession.getMapper(AssetMapper.class).update(action);
			TextService.update(sqlSession, action.getTexts());
			RelationService.create(sqlSession, Relation.ORGANIZATION_ASSET, action.getOrganizationid(), action.getId());
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Asset, action);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the AssetDelete action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Asset execute(SqlSession sqlSession, AssetDelete action) {
		try {RelationService.delete(sqlSession, Relation.ORGANIZATION_ASSET, action.getOrganizationid(), action.getId());}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return null;
	}

	/**
	 * Executes the AssetTable action to read an asset table for a property.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Asset> execute(SqlSession sqlSession, AssetTable action) {
		LOG.debug("AssetTable in " + action);
		Table<Asset> table = new Table<Asset>();
		try {
			table.setDatasize(sqlSession.getMapper(AssetMapper.class).count(action));
			table.setValue(sqlSession.getMapper(AssetMapper.class).list(action));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("AssetTable out " + table);
		return table;
	}

	/**
	 * Executes the asset NameIdAction action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdAction action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(AssetMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(AssetMapper.class).nameidbyname(action));}
			table.setDatasize(action.getNumrows());
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the DepeciationNameId action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public Table<NameId> execute(SqlSession sqlSession, DepeciationNameId action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(DepreciationMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(DepreciationMapper.class).nameidbyname(action));}
			table.setDatasize(action.getNumrows());
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the asset LocationNameId action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	protected final Table<NameId> execute(SqlSession sqlSession, LocationNameId action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(AssetMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(AssetMapper.class).namebylocation(action));}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}
}