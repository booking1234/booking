/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.Date;

import net.cbtltd.shared.api.HasTableService;

/** The Class Account carries account data between client and server. */
public class Account
extends Model
implements HasTableService {

	//Standard Accounts
	public static final String ACCOUNTS_PAYABLE = "1";
	public static final String ACCOUNTS_PAYABLE_NAME = "Accounts Payable";
	public static final String ACCOUNTS_RECEIVABLE = "2";
	public static final String ACCOUNTS_RECEIVABLE_NAME = "Accounts Receivable";
	public static final String ASSET_COST = "8";
	public static final String ACCUMULATED_DEPRECIATION = "9";
	public static final String FINANCE = "17";
	public static final String FINANCE_NAME = "Bank & Cash";
	public static final String CARD_MERCHANT_FEES = "120";
	public static final String CARD_MERCHANT_FEES_NAME = "Card Merchant Fees";
	public static final String COST_OF_SALES = "16";
	public static final String CURRENCY_CONTROL = "90";
	public static final String CURRENCY_CONTROL_NAME = "Currency Control";
	public static final String DEPRECIATION_EXPENSE = "20";
	public static final String INVENTORY = "36";
	public static final String MAINTENANCE_COST = "59";
	public static final String MAINTENANCE_COST_NAME = "Repairs & Maintenance";
	public static final String PURCHASE_SUSPENSE = "13";
	public static final String TRADING_LOANS = "40";
	public static final String TRADING_LOANS_NAME = "Loan Account";
	public static final String SALES_REVENUE = "61";
	public static final String SALES_REVENUE_NAME = "Sales Revenue";
	public static final String SERVICE_COST = "104";
	public static final String SERVICE_COST_NAME = "Servicing Costs";
	public static final String VAT_INPUT = "71";
	public static final String VAT_INPUT_NAME = "Input VAT";
	public static final String VAT_OUTPUT = "72";
	public static final String VAT_OUTPUT_NAME = "Output VAT";
	public static final String TAX_CONTROL = "70";
	
	public static final String[] MANDATORY_ACCOUNTS = { ACCOUNTS_PAYABLE,
			ACCOUNTS_RECEIVABLE, ACCUMULATED_DEPRECIATION, ASSET_COST,
			FINANCE, COST_OF_SALES, CURRENCY_CONTROL, DEPRECIATION_EXPENSE,
			INVENTORY, PURCHASE_SUSPENSE, SALES_REVENUE, VAT_INPUT, VAT_OUTPUT };

	//States
	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";
	public static final String SUSPENDED = "Suspended";
	public static final String[] STATES = { INITIAL, CREATED, SUSPENDED, FINAL };
	
	//Subledgers
//	public static final String CAPITAL = "Capital";
//	public static final String COS = "Cost of Sales";
//	public static final String CURRENT_ASSET = "Current Asset";
//	public static final String CURRENT_LIABILITY = "Current Liability";
//	public static final String EXPENSE = "Expense";
//	public static final String FIXED_ASSET = "Fixed Asset";
//	public static final String INCOME = "Income";
//	public static final String LT_LIABILITY = "Long Term Liability";
//	public static final String[] TYPES = { CAPITAL, COS, CURRENT_ASSET,
//		CURRENT_LIABILITY, EXPENSE, FIXED_ASSET, INCOME, LT_LIABILITY };

	public enum Type { Capital, CostOfSales, CurrentAsset, CurrentLiability, Expense, FixedAsset, Income, LongTermLiability };

	public static final String[] TYPES = { Type.Capital.name(), Type.CurrentAsset.name(), Type.CurrentLiability.name(),
		Type.Expense.name(), Type.FixedAsset.name(), Type.Income.name(), Type.LongTermLiability.name()};

	private String subledger;
    private String type;
    private Date fromdate;
    private Date todate;
    private String currency;
    private String notes;
//	private ArrayList<String> organizations;
//	private ArrayList<String> eventtypes;
//	private ArrayList<String> subledgertypes;
//	private ArrayList<String> steps;
	private String orderby;
	private int startrow;
	private int numrows;

	
	public Service service() {return Service.ACCOUNT;}

	public String getSubledger() {
		return subledger;
	}

	
	public void setSubledger(String subledger) {
		this.subledger = subledger;
	}

	
	public String getType() {
		return type;
	}

	
	public void setType(String type) {
		this.type = type;
	}

	
	public Date getFromdate() {
		return fromdate;
	}

	
	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	
	public Date getTodate() {
		return todate;
	}

	
	public void setTodate(Date todate) {
		this.todate = todate;
	}

	
	public String getCurrency() {
		return currency;
	}

	
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	
	public String getNotes() {
		return notes;
	}

	
	public void setNotes(String notes) {
		this.notes = notes;
	}

	
//	public ArrayList<String> getOrganizations() {
//		return organizations;
//	}
//
//	
//	public void setOrganizations(ArrayList<String> organizations) {
//		this.organizations = organizations;
//	}
//
//	
//	public ArrayList<String> getEventtypes() {
//		return eventtypes;
//	}
//
//	
//	public void setEventtypes(ArrayList<String> eventtypes) {
//		this.eventtypes = eventtypes;
//	}
//
//	
//	public ArrayList<String> getSubledgertypes() {
//		return subledgertypes;
//	}
//
//	
//	public void setSubledgertypes(ArrayList<String> subledgertypes) {
//		this.subledgertypes = subledgertypes;
//	}
//
//	
//	public ArrayList<String> getSteps() {
//		return steps;
//	}
//
//	
//	public void setSteps(ArrayList<String> steps) {
//		this.steps = steps;
//	}

	
	public String getOrderby() {
		return orderby;
	}

	
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	
	public boolean noOrderby() {
		return orderby == null || orderby.isEmpty();
	}

	
	public int getStartrow() {
		return startrow;
	}

	
	public void setStartrow(int startrow) {
		this.startrow = startrow;
	}

	
	public int getNumrows() {
		return numrows;
	}

	
	public void setNumrows(int numrows) {
		this.numrows = numrows;
	}

	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("\nAccount ").append(super.toString());
		sb.append("\nType ").append(getType());
		sb.append(" Subledger ").append(getSubledger());
		sb.append(" Fromdate ").append(getFromdate());
		sb.append(" Todate ").append(getTodate());
		sb.append(" Currency ").append(getCurrency());
		sb.append(" Notes ").append(getNotes());
		sb.append(" OrderBy ").append(getOrderby());
		sb.append(" StartRow ").append(getStartrow());
		sb.append(" NumRows ").append(getNumrows());
		return sb.toString();
	}
}