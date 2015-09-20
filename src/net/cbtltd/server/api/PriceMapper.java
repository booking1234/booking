/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mybookingpal.service.annotations.SqlUpdateMarker;

import net.cbtltd.json.price.WeeklyPrice;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.api.HasPrice;
import net.cbtltd.shared.price.PriceCurrency;
import net.cbtltd.shared.price.PriceTable;
import net.cbtltd.shared.price.PriceTableConverted;
import net.cbtltd.shared.price.PriceType;
import net.cbtltd.shared.price.PriceUnit;
import net.cbtltd.shared.price.PriceWeek;
import net.cbtltd.soap.ota.server.OtaRate;

public interface PriceMapper
extends AbstractMapper<Price> {

	void deletebyexample(Price action);
	Price altidfromdate(Reservation action);
	Price exists(Price action); 
	Price existsAltID(Price action); 
	void cancelversion(Price action);
	void cancelversionbypartyid(Price action);
	void cancelpricelist(List<String> priceIdList);
	void deactivatereservationpricesbyversion(Price action);
	Price readbydate(Price action);
	Price readexactmatch(Price action);
	Price getcheckinprice(List<String> priceids);
	Price getpropertydetailcheckinprice(Price action);
	ArrayList<Price> readbytype(Price action);

	void copy(NameId oldnew);
	Double value(Price price);

	Integer count(PriceTable action);
	ArrayList<Price> list(PriceTable action);
	ArrayList<Price> pricedetails(HasPrice action);

	Integer productfeaturecount(Price action);
	ArrayList<Price> productfeaturelist(Price action);

	ArrayList<Price> entityfeature(Price action);
	ArrayList<Price> quotedetail(String reservationid);
	ArrayList<Price> readallassociatedprices(Price action);
	Price readperdiemtax(Price action);
	ArrayList<Price> readsplitpriceperiods(Price action);
	Integer count(PriceTableConverted action);
	ArrayList<Price> list(PriceTableConverted action);

	Integer servicecount(PriceTable action);
	ArrayList<Price> servicelist(PriceTable action);

	ArrayList<NameId> pricecurrency (PriceCurrency action);
	ArrayList<NameId> pricetype (PriceType action);
	ArrayList<NameId> priceunit (PriceUnit action);
	
	Date maxVersion(Price action);
	
	//REST
	ArrayList<Price> restread(Price action);
	ArrayList<Price> restreadLimitedRows(Price action);
	ArrayList<Price> getpricetable(Price action);
	//OTA
	ArrayList<OtaRate> otarates(OtaRate action);
	//JSON 
	ArrayList<net.cbtltd.json.price.PriceWidgetItem> pricewidget(PriceTable action);
	ArrayList<net.cbtltd.json.price.PriceWidgetItem> featurewidget(PriceTable action);
	
	ArrayList<Price> productsByPartyId(String partyid);
	ArrayList<Price> existsPrice(Price action);
	
	List<WeeklyPrice> getPricesByWeeks(PriceWeek action);
	
	//XML Writer
	List<Price> listallactiveprices();
	void delete(String id);
	void deleteByAltId(String altId);
	void deleteByEntityId(String entityid);
	ArrayList<Price> readByEntityId(Price price);
	ArrayList<Price> readByEntityIdAndEntityType(Price price);
	Price readByFromDate(Price price);
	Price readContractFee(Price price);
	void insertList(@Param("list") List<Price> prices);
	ArrayList<Price> readByEntityIdAndDate(Price price);
	ArrayList<Price> readByEntityIdAndDateRange(Price price);
	
	@SqlUpdateMarker @Override
	void create(Price action);
	
	@SqlUpdateMarker @Override
	void update(Price action);
	
	@SqlUpdateMarker @Override
	void write(Price action);
	Price getExtraCost(Price price);
	
}