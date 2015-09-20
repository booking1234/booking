/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;
import java.util.List;

import net.cbtltd.json.Parameter;
import net.cbtltd.json.image.ImageWidgetItem;
import net.cbtltd.json.nameid.NameIdWidgetItem;
import net.cbtltd.json.product.ProductWidgetItem;
import net.cbtltd.shared.Area;
import net.cbtltd.shared.AttributeAction;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.NameIdType;
import net.cbtltd.shared.Position;
import net.cbtltd.shared.Product;
import net.cbtltd.soap.ota.server.OtaSearchParameter;

import org.apache.ibatis.annotations.Param;

public interface ProductMapper
extends AbstractMapper<Product> {
	
	Product exists(Product action);
	Double commission(String supplierid);
	Double discount(String supplierid);
	
	Product readonlineaccomodation(String id);
	
	Integer count(Product action);
	ArrayList<Product> list(Product action);
	ArrayList<Product> altlist(String altpartyid);
	ArrayList<Product> componentlist(String productid);
	void restoreparty(String partyid);
	void suspendparty(String partyid);
	
	ArrayList<Product> hotelsearch(OtaSearchParameter action); //HotelSearchRS
	String valuenameid(AttributeAction action);//Ota
	
	ArrayList<Position> positions(Product action);
	
	ArrayList<NameId> nameidnooffline(NameIdAction action);
	ArrayList<NameId> nameidnopartof(NameIdAction action);
	ArrayList<NameId> nameidbyaccountid(NameIdAction action);
	ArrayList<NameId> nameidbylocationid(String[] locationids); //NameIdByLocation
	ArrayList<NameId> nameidbyparentlocationid(String[] locationids);
	// List of product in location with collisions.
	ArrayList<String> idsbyparentlocationandprice(@Param("locationid") String locationid, @Param("fromdate") String fromdate, @Param("todate") String todate);
	ArrayList<String> inquireonlyidsbyparentlocationandprice(@Param("locationid") String locationid, @Param("fromdate") String fromdate, @Param("todate") String todate);
	// List of product in location with collisions.
	ArrayList<String> idswithreservationcollision(@Param("locationid") String locationid, @Param("fromdate") String fromdate, @Param("todate") String todate);
	ArrayList<NameId> nameidbyarea(Area action);
	ArrayList<NameId> nameidtype(NameIdType action); //product asset type
	ArrayList<NameId> nameiddynamic(); //dynamic price products
	ArrayList<NameId> type(); //product type
	ArrayList<NameId> channelProductsNameIdByName(NameIdAction action);
	
	//REST SQL queries
	net.cbtltd.rest.Property property(String productid);
	
	List<net.cbtltd.rest.registration.ProductInfo> productInfoListBySupplier(NameIdAction altpartyid);
	
	String productCountBySupplier(String partyid);
	String selectedProductCountBySupplier(String partyid);
	String productCountByAltSupplier(@Param("altsupplierid") String altpmid, @Param("altpartyid") String pmsid);
	void updateSupplierIdByAltSupplier(Product action);
	
	List<net.cbtltd.rest.Property> propertylist(String altpartyid);
	
	Integer productforpos(NameId action);
	ArrayList<NameId> productotas(String list); //ProductOtas
	ArrayList<NameId> otatypes(); //OtaTypes
	ArrayList<String> flipkeyproducts(String supplierid); //Flipkey IDs

	//JSON SQL queries
	ProductWidgetItem productwidget(String productid);
	ArrayList<NameIdWidgetItem> nameidwidget(Parameter action);
	ArrayList<NameIdWidgetItem> jsonnameids(String[] productids);
	ArrayList<ImageWidgetItem> imagewidget(Parameter action);

	//Foreign SQL queries
	Product altread(NameId action);
	List<Product> altPartyread(NameId action);
	Product nameRead(NameId action);
	int changeLogInMin(NameId action);
	List<Product> readAllSupplier(NameIdAction nameIdAction);
	List<Product> readAll(NameIdAction nameIdAction);
	void initializeProductsNotInFeed(Product action);
	
	void cancelversion(Product action);
	
	ArrayList<String> activeproductid();
	
	ArrayList<Product> getProductsForChannel(String id);
	
	ArrayList<String> getProductsWithAltIdAndAltPartyIdasNullbyVersion(String version);
	List<String> getInActiveProductsWithAltIdAndAltPartyIdasNull(String version);
	ArrayList<String> activeproductidListbyChannelPartner(NameIdAction channelPartnerId);
	ArrayList<String> activeProductIdListBySupplier(NameIdAction supplierId);
	ArrayList<Product> activeListOfPropertyManagersAndCommision();
	ArrayList<String> activeProductAltIdListBySupplier(String partyId);
	
	//Location check
	ArrayList<Product> productIdListWithNullLocationBySupplierId(String supplierId);
	
	// roomids 
	void insertRoomIds(List<NameId> roomIds);
	List<String> selectRoomsForProduct(String productid);
	void deleteRoom(NameId roomId); 
}
