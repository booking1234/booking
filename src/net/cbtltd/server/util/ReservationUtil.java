package net.cbtltd.server.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.cbtltd.rest.response.QuoteDetail;
import net.cbtltd.server.ReservationService;
import net.cbtltd.shared.Fee;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Tax;
import net.cbtltd.shared.api.HasPrice;

public class ReservationUtil {

	public ReservationUtil() {
	}

	/*
	 * Increments an existing price and creates one and adds it to the quote detail if it does not exist.
	 *
	 * @param hasprice the reservation or quote being priced
	 * @param id the price, yield, feature or tax ID
	 * @param name the price, yield, feature or tax name
	 * @param type the price, yield, feature or tax type
	 * @param value the value to be added
	 */
	public static void addQuotedetail(
			HasPrice hasprice, 
			String id, 
			String name, 
			String type, 
			String partyname, 
			Double quantity,
			String unit,
			Double value,
			Date fromdate,
			Date todate) {
		Collection<Price> quotedetail = hasprice.getQuotedetail();
		for (Price price : quotedetail) {
			if (price.hasId(id)) {
				price.addValue(value);
				return;
			}
		}
		Price price = new Price();
		price.setId(id);
		price.setEntitytype(NameId.Type.Reservation.name());
		price.setEntityid(hasprice.getReservationid());
		price.setName(name);
		price.setType(type);
		price.setPartyname(partyname);
		price.setQuantity(quantity);
		price.setUnit(unit);
		if (fromdate != null) {
			price.setDate(fromdate);
		}
		if (todate != null){
			price.setTodate(todate);
		}
		//		price.setUnit(Unit.EA);
		//TODO: Extend to use rule to calculate the price
		price.setValue(value/quantity);
		price.setCurrency(hasprice.getCurrency());
		quotedetail.add(price);
		ReservationService.LOG.debug("addQuotedetail " + price);
	}
	
	/**
	 * Prepare a list of quote details for reservation price response.
	 * 
	 * @param hasPrice Reservation which include list of prices used for price amount calculations
	 * @return  list of quote detail for response
	 */
	public static List<QuoteDetail> getReservationPriceQuoteDetails(HasPrice hasPrice){
		
		List<QuoteDetail> resultQuoteDetails = new ArrayList<QuoteDetail>();
		
		if (hasPrice != null){
			
			Collection<Price> quotedetail = hasPrice.getQuotedetail();
			for (Price price : quotedetail) {
				
				if (price.hasType(Price.MANDATORY) || price.hasType(Fee.EntityTypeEnum.MANDATORY.getName())) {
					resultQuoteDetails.add(new QuoteDetail(String.valueOf(price.getValue()), hasPrice.getCurrency(), price.getName(), NameId.Type.Mandatory.name(), "", true));
				}
				
				if (price.hasType(NameId.Type.MandatoryPerDay.name())) {
					resultQuoteDetails.add(new QuoteDetail(String.valueOf(price.getValue()), hasPrice.getCurrency(), price.getName(), NameId.Type.MandatoryPerDay.name(), "", true));
				}
				
				if (price.hasType(Price.OPTIONAL) || price.hasType(Fee.EntityTypeEnum.OPTIONAL.getName())) {
					resultQuoteDetails.add(new QuoteDetail(String.valueOf(price.getValue()), hasPrice.getCurrency(), price.getName(), Price.OPTIONAL, "", false));
				}
				
				if (price.hasType(Price.TAX_INCLUDED) && !price.getName().equalsIgnoreCase(Price.INCLUDED)) {
					resultQuoteDetails.add(new QuoteDetail(String.valueOf(Math.abs(price.getValue())), hasPrice.getCurrency(), price.getName(), Tax.Type.SalesTaxIncluded.name(), "", true));
				}
				
				if (price.hasType(Price.TAX_EXCLUDED)) {
					resultQuoteDetails.add(new QuoteDetail(String.valueOf(price.getValue()), hasPrice.getCurrency(), price.getName(), "", "", true));
				}
				
				if (price.hasType(Price.TAX_ON_TAX)) {
					resultQuoteDetails.add(new QuoteDetail(String.valueOf(price.getValue()), hasPrice.getCurrency(), price.getName(), Tax.Type.SalesTaxOnTax.name(), "", true));
				}
				
				if (price.hasType(Price.NOT_TAXABLE)) {
					resultQuoteDetails.add(new QuoteDetail(String.valueOf(price.getValue()), hasPrice.getCurrency(), price.getName(), NameId.Type.Mandatory.name(), "", true));
				}
			}
		}
		
		return resultQuoteDetails;
	}
	
	

}
