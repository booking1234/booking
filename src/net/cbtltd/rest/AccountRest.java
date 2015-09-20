/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.cbtltd.rest.logging.ServiceTimer;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

/** 
 * The Class AccountRest implements XML services for accounts and financial transactions etc. 
 * @see https://ipp.developer.intuit.com/0010_Intuit_Partner_Platform/0050_Data_Services
 * @see https://ipp.developer.intuit.com/0010_Intuit_Partner_Platform/0050_Data_Services/0030_V3_Data_Services_Specification_Draft
 */
@Path("/account")
@Produces(MediaType.APPLICATION_XML)
public class AccountRest extends AbstractAccount {

	@GET
	@Path("/currencies")
	@Descriptions({ 
	   @Description(value = "Lists currency names and codes", target = DocTarget.METHOD),
	   @Description(value = "Currency List", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Currencies getCurrencyNameIdsXML(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("AccountRest", "getCurrencyNameIdsXML");
		return getCurrencyNameIds(pos, xsl);
	}
	
	@GET
	@Path("/list")
	@Descriptions({ 
	   @Description(value = "Lists account names and IDs", target = DocTarget.METHOD),
	   @Description(value = "Account List", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public Items getAccountListXML(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("AccountRest", "getAccountListXML");
		return getAccountList(pos, xsl);
	}
	
	@GET
	@Path("/subledger")
	@Descriptions({ 
	   @Description(value = "Lists account names and subsidiary ledgers", target = DocTarget.METHOD),
	   @Description(value = "Subsidiary Ledger", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public Items getAccountSubledgerXML(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("AccountRest", "getAccountSubledgerXML");
		return getAccountSubledger(pos, xsl);
	}
	
	@GET
	@Path("/activity")
	@Descriptions({ 
	   @Description(value = "Lists activity subsidiary accounts", target = DocTarget.METHOD),
	   @Description(value = "Activity Subsidiary Account", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public Items getActivitySubaccountXML(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("AccountRest", "getActivitySubaccountXML");
		return getActivitySubaccount(pos, xsl);
	}
	
	@GET
	@Path("/asset")
	@Descriptions({ 
	   @Description(value = "Lists asset subsidiary accounts", target = DocTarget.METHOD),
	   @Description(value = "Asset Subsidiary Account", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public Items getAssetSubaccountXML(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("AccountRest", "getAssetSubaccountXML");
		return getAssetSubaccount(pos, xsl);
	}
	
	@GET
	@Path("/finance")
	@Descriptions({ 
	   @Description(value = "Lists cash and bank subsidiary accounts", target = DocTarget.METHOD),
	   @Description(value = "Finance Subsidiary Account", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public Items getFinanceSubaccountXML(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("AccountRest", "getFinanceSubaccountXML");
		return getFinanceSubaccount(pos, xsl);
	}
	
	@GET
	@Path("/party")
	@Descriptions({ 
	   @Description(value = "Lists company and individual subsidiary accounts", target = DocTarget.METHOD),
	   @Description(value = "Party Subsidiary Account", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public Items getPartySubaccountXML(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("AccountRest", "getPartySubaccountXML");
		return getPartySubaccount(pos, xsl);
	}
	
	@GET
	@Path("/{accountid}/{fromdate}/{todate}/{currency}")
	@Descriptions({ 
	   @Description(value = "Lists transactions by account for specified accountids, dates and currency code", target = DocTarget.METHOD),
	   @Description(value = "Transactions by Account", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})
		public Accounts getAccountTransactionsXML(
			@Description("Account ID") @DefaultValue(Model.ZERO) @PathParam("accountid") String accountid,
			@Description("From Date") @PathParam("fromdate") String fromdate,
			@Description("To Date") @PathParam("todate") String todate,
			@Description("Currency Code") @DefaultValue(Constants.USD) @PathParam("currency") String currency,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("AccountRest", "getAccountTransactionsXML");
		return getAccountTransactions(accountid, fromdate, todate, currency, pos, xsl);
	}
	
	@GET
	@Path("/event/{eventid}/{fromdate}/{todate}")
	@Descriptions({ 
	   @Description(value = "Lists transactions by event for specified eventids", target = DocTarget.METHOD),
	   @Description(value = "Transactions by Event ID", target = DocTarget.RETURN),
	   @Description(value = "Request", target = DocTarget.REQUEST),
	   @Description(value = "Response", target = DocTarget.RESPONSE),
	   @Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public Events getEventTransactionsByIdXML(
			@Description("Event Reference") @DefaultValue(Model.ZERO) @PathParam("eventid") String eventid,
			@Description("From Date") @PathParam("fromdate") String fromdate,
			@Description("To Date") @PathParam("todate") String todate,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("AccountRest", "getEventTransactionsByIdXML");
		return getEventTransactionsById(eventid, fromdate, todate, pos, test, xsl);
	}
	
}
