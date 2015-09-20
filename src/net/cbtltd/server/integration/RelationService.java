package net.cbtltd.server.integration;

import java.util.List;

import net.cbtltd.server.api.DownloadMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.RelationMapper;
import net.cbtltd.shared.Downloaded;
import net.cbtltd.shared.Relation;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class RelationService implements IsService {
	private static final Logger LOG = Logger.getLogger(RelationService.class
			.getName());
	private static RelationService service;
	
	private RelationService(){
		
	}

	/**
	 * Gets the single instance of ProductService to manage Product instances.
	 * 
	 * @see net.cbtltd.shared.Product
	 * 
	 * @return single instance of ProductService.
	 */
	public static synchronized RelationService getInstance() {
		if (service == null) {
			service = new RelationService();
		}
		return service;
	}
	
	/**
	 * Gets the list of relations for the specified relation parameter.
	 *
	 * @param sqlSession the current SQL session.
	 * @param relation the specified relation.
	 * @return the array list
	 */
	public static final List<Relation> getListRelation(SqlSession sqlSession, Relation relation) {
		return sqlSession.getMapper(RelationMapper.class).list(relation);
	}
	public static final List<String> getListRelationLineIds(SqlSession sqlSession, Relation relation) {
		return sqlSession.getMapper(RelationMapper.class).lineids(relation);
	}
	
	/**
	 * Creates the relations from one entity to many entities.
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param headid the ID of the first (head) entity.
	 * @param lineids the list of the IDs of the second (line) entities.
	 */
	public static final void create(SqlSession sqlSession, String link, String headid, List<String> lineids) {
		if (lineids != null) {
			for (String lineid : lineids) {
				create(sqlSession, new Relation(link, headid, lineid));
			}
		}
	}
	
	/**
	 * Creates the specified relation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param relation the specified relation.
	 */
	private static final void create(SqlSession sqlSession, Relation relation) {
		if (!exists(sqlSession, relation)) {sqlSession.getMapper(RelationMapper.class).create(relation);}
	}
	
	/*
	 * Gets the specified relation if it exists.
	 *
	 * @param sqlSession the current SQL session.
	 * @param relation the relation
	 * @return true if the relation exists
	 */
	private static final boolean exists(SqlSession sqlSession, Relation relation) {
		return sqlSession.getMapper(RelationMapper.class).exists(relation) != null;
	}
	
	/**
	 * Unloads the download/upload relation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param downloaded the enum of the relation.
	 * @param headid the ID of the first (head) entity.
	 * @param lineid the ID of the second (line) entity.
	 */
	public static final void unload(SqlSession sqlSession, Downloaded downloaded, String headid, String lineid) {
		sqlSession.getMapper(DownloadMapper.class).delete(new Relation(downloaded, headid, lineid));
	}
}
