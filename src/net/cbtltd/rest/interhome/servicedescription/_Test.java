package net.cbtltd.rest.interhome.servicedescription;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.project.PartyIds;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.api.HasUrls;

import org.apache.ibatis.session.SqlSession;

/**
 * This class was generated by Apache CXF 2.4.0
 * 2012-08-09T13:20:11.648+02:00
 * Generated source version: 2.4.0
 * 
 */
public final class _Test {

	private _Test() {}

	public static void main(String args[]) throws Exception {
		SqlSession sqlSession = RazorServer.openSession();
		try {
			JAXBContext jc = JAXBContext.newInstance("net.cbtltd.rest.interhome.servicedescription");
			Unmarshaller um = jc.createUnmarshaller();
			
			Descriptions2 descriptions = (Descriptions2) um.unmarshal(new java.io.FileInputStream( "C:/servicedescription_en.xml" ));

			int i = 0;
			for (Description2 description : descriptions.getDescription2()) {
				System.out.println(i++ + " " + description);
				//net.cbtltd.shared.Description2 action = new net.cbtltd.shared.Description2();
				Product product = sqlSession.getMapper(ProductMapper.class).altread(new NameId(PartyIds.PARTY_INTERHOME_ID, description.getCode()));
				if (product == null) {throw new ServiceException(Error.product_id, description.getCode());}
//				sqlSession.getMapper(AlertMapper.class).create(action);
//				sqlSession.commit();
			}
		} catch (Throwable x) {x.printStackTrace();}

		System.out.println("Finished...");
		System.exit(0);
	}
}