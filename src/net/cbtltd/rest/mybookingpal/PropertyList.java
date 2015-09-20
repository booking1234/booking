package net.cbtltd.rest.mybookingpal;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.NameIdAction;

public class PropertyList {

	public static ArrayList<String> getActivePropertyList(SqlSession sqlSession) {
		return sqlSession.getMapper(ProductMapper.class).activeproductid();
	}

	public static ArrayList<String> getActivePropertyListBySupplier(SqlSession sqlSession, String supplierID) {
		NameIdAction supplierId = new NameIdAction();
		supplierId.setId(supplierID);
		return sqlSession.getMapper(ProductMapper.class).activeProductIdListBySupplier(supplierId);
	}

	public static ArrayList<String> getActivePropertyListByChannelPartner(SqlSession sqlSession, String channelPartnerID) {
		NameIdAction channelPartnerId = new NameIdAction();
		channelPartnerId.setId(channelPartnerID);
		return sqlSession.getMapper(ProductMapper.class).activeproductidListbyChannelPartner(channelPartnerId);
	}



}
