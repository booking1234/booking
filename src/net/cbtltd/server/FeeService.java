/**
 * @author	Marko Ovuka
 */
package net.cbtltd.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import net.cbtltd.server.api.FeeMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.shared.Fee;
import net.cbtltd.shared.Product;

public class FeeService implements IsService {

	private static FeeService service;
	private static final Logger LOG = Logger.getLogger(FeeService.class.getName());

	/**
	 * Gets the single instance of FeeService to create lists of Fee instances.
	 * @see net.cbtltd.shared.Fee
	 *
	 * @return single instance of FeeService
	 */
	public static synchronized FeeService getInstance() {
		if (service == null) {service = new FeeService();}
		return service;
	}

	
	public static synchronized Fee createDefault(){
		Fee fee = new Fee();
		fee.setEntityType(Fee.MANDATORY);
		fee.setState(Fee.INITIAL);
		fee.setTaxType(Fee.TAXABLE);
		fee.setUnit(Fee.NOT_APPLICABLE);
		fee.setValueType(Fee.PERCENT);

		return fee;
	}
	
	
	public synchronized static void updateProductFees(SqlSession sqlSession, Product product, List<Fee> feesFromApi, Date version,
		boolean feeWithoutDates, Date minimumToDateForUpdate){
	//finding previous fees from DB
	Fee action = new Fee();
	action.setProductId(product.getId());
	action.setPartyId(product.getAltpartyid());
	action.setState(Fee.CREATED);
	
	ArrayList <Fee> feeFromDB = sqlSession.getMapper(FeeMapper.class).readbyproductandstate(action);
	for(Fee currentFee : feesFromApi){
		currentFee.setState(Fee.CREATED);
		if(feeWithoutDates){
			// This is for situation when method list.contains can not be used
			// because for fees often we do not have from - to date values.
			// In that case we set first date and some date in future, and 
			// method Fee.equals will not find these fees for every next day, 
			// and fee will be updated. So here we finding values manually in List
			// but this will not be too much time consuming because we do not have 
			// big numbers of fee per property, and also if fee exist in DB already
			// (this will be in most situation) that fee will be on top of this list.
			// Also here is check if toDate in DB is below some minimum toDate
			boolean feeExist = false;
			for(int i=0;i<feeFromDB.size();i++){
				if(currentFee.equalsWithoutDates(feeFromDB.get(i)) && feeFromDB.get(i).getToDate().after(minimumToDateForUpdate)){
					feeFromDB.remove(i);
					feeExist = true;
					break;
				}
			}
			if(!feeExist){
				currentFee.setVersion(version);
				sqlSession.getMapper(FeeMapper.class).create(currentFee);
			}
		}else{
			if (!feeFromDB.contains(currentFee)) {
				currentFee.setVersion(version);
				sqlSession.getMapper(FeeMapper.class).create(currentFee);
			}
			else {
				feeFromDB.remove(currentFee);
			}
		}
		
	}
	
	if(feeFromDB.size()>0){
		ArrayList<Integer> feeIdList = new ArrayList<Integer>();
		for(Fee currentFeeForCancel : feeFromDB){
			feeIdList.add(currentFeeForCancel.getId());
		}
		sqlSession.getMapper(FeeMapper.class).cancelfeelist(feeIdList);
	}
	
	LOG.debug("updateProductFees, productId=" + product.getId()+", " + version);
}
	
	
	
}

