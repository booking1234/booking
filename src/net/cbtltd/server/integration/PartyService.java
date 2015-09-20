package net.cbtltd.server.integration;

import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.shared.Party;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class PartyService implements IsService {

	private static final Logger LOG = Logger.getLogger(PartyService.class.getName());
	private static PartyService service;

	/**
	 * Gets the single instance of PriceService to manage Price and Yield management instances and tables.
	 *
	 * @return single instance of PriceService.
	 * @see net.cbtltd.shared.Price
	 * @see net.cbtltd.shared.Yield
	 */
	public static synchronized PartyService getInstance() {
		if (service == null) {service = new PartyService();}
		return service;
	}
	
	public Party getParty(SqlSession sqlSession,String partyId){
	return	sqlSession.getMapper(PartyMapper.class).read(partyId);
	}
	public void updateParty(SqlSession sqlSession,Party party){
		sqlSession.getMapper(PartyMapper.class).update(party);
		}
}
