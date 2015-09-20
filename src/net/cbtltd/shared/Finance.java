/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.List;

public class Finance extends Entity {

	public static final String CBT_USD_FINANCE = "1";
	public static final String CBT_USD_FINANCE_NAME = "Razor Payment Gateway";

	public static final String CBT_ZAR_FINANCE = "2";
	public static final String CBT_ZAR_FINANCE_NAME = "Razor Payment Gateway";

	public static final Double CBT_ZAR_FINANCE_FEE = 0.03;
	public static final Double CBT_ZAR_BOOKING_FEE = 0.02;

	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";
	public static final String SUSPENDED = "Suspended";
	public static final String[] STATES = { INITIAL, CREATED, SUSPENDED, FINAL };

	public static final String CARD_UNENCRYPTED = "4";

	public enum Type {Bank, Card, Cash};
	public enum CardType {AX, DC, DS, ER, IC, MC, VI};
	
//	AMEX = "AX";
//	DINERS = "DC";
//	DISCOVER = "DS";
//	EN_ROUTE = "ER";
//	INVALID = "IC";
//	MASTERCARD = "MC";
//	VISA = "VI";
	
	/**
	 * Checks if the string number is a number.
	 * 
	 * @param number
	 *            the string number.
	 * @return true, if the string number is a number.
	 */
	public static boolean isNumber(String number) {
		try {
			Double.valueOf(number).doubleValue();
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Checks if the specified credit card number is valid.
	 * 
	 * @param number
	 *            the credit card number.
	 * @return true if a valid credit card number, otherwise false. Card codes
	 *         Access XS Access Air ZA American Express/Optima Card AX
	 *         Australian Bankcard AB Bank of Boroda "Bobcard" BB Bank of Hawaii
	 *         BH Bank Card of Japan BC Canara Bank "Cancard" CD Carte Aurore AU
	 *         Carte Blanche CB Citizens and Southern National Bank CS Cofinoga
	 *         Credit Card CG Comites ID Connecticut Bank and Trust Company CU
	 *         Diamond Credit MD Diners Club/American Torch Club/Sun Diners Club
	 *         DC Empire Card MT Eurocard EC Federation Nationale de l’Industrie
	 *         Hoteliere FH HiperCard HC Japan Credit Bureau JC Mastercard CA
	 *         Million Credit Service MC OTB Card OT Select Credit SR Shoppers
	 *         Charge SC Trust Card TC Union Credit UD United Debit Credit
	 *         Center UI VISA International VI Walker Bankcard WB Airline Credit
	 *         Cards Air New Zealand Travel Card NZ Alia Personal Credit Card RJ
	 *         Aloha Airlines Credit Card TS American Airlines Personal Credit
	 *         Card AA Braathens ASA Bracard BU China Airlines
	 *         "Dynasty Travel Card" DT Delta Air Lines Credit Card DL Garuda
	 *         Executive Credit Card GA Japan Airlines JALCARD JL Lufthansa
	 *         Senator Card LH Qantas Airways Charge Card QF Universal Air
	 *         Travel Plan (UATP) TP Merchandising and Banking Cards Bankcard
	 *         New Zealand BZ Discover Card/Bravo Card/Private Issue/Novus Card
	 *         Products DS
	 * 
	 *         Test Credit Card Numbers American Express 378282246310005
	 *         American Express 371449635398431 American Express Corporate
	 *         378734493671000 Australian BankCard 5610591081018250 Diners Club
	 *         30569309025904 Diners Club 38520000023237 Discover
	 *         6011111111111117 Discover 6011000990139424 JCB 3530111333300000
	 *         JCB 3566002020360505 MasterCard 5555555555554444 MasterCard
	 *         5105105105105100 Visa 4111111111111111 Visa 4012888888881881 Visa
	 *         4222222222222 Note : Even though this number has a different
	 *         character count than the other test numbers, it is the correct
	 *         and functional number. Dankort (PBS) 76009244561 Dankort (PBS)
	 *         5019717010103742 Switch/Solo (Paymentech) 6331101999990016
	 */
	public static boolean isCreditCardNumber(String number) {
		return !getCardType(number).equals(CardType.IC.name());
	}

	/**
	 * Checks if the specified credit card number is not valid.
	 * 
	 * @param number
	 *            the credit card number.
	 * @return true if not a valid credit card number, otherwise false.
	 */
	public static boolean notCreditCardNumber(String number) {
		return getCardType(number).equals(CardType.IC.name());
	}

	/**
	 * @return the type of this credit card
	 */
	public CardType getCardType() {
		return getCardType(accountnumber);
	}
	
	/**
	 * @return the type of this credit card
	 */
	public boolean hasCardType(CardType cardtype) {
		return getCardType(accountnumber) == cardtype;
	}
	
	/**
	 * @return the type of the credit card
	 * @param number
	 */
	public static CardType getCardType(String number) {

		if (!isNumber(number) || !luhnValidate(number)) {
			return CardType.IC;
		}

		if (number.length() == 16
				&& Integer.parseInt(number.substring(0, 2)) >= 51
				&& Integer.parseInt(number.substring(0, 2)) <= 55) {
			return CardType.MC;
		}

		if ((number.length() == 13 || number.length() == 16)
				&& Integer.parseInt(number.substring(0, 1)) == 4) {
			return CardType.VI;
		}

		if (number.length() == 15
				&& (Integer.parseInt(number.substring(0, 2)) == 34 || Integer
						.parseInt(number.substring(0, 2)) == 37)) {
			return CardType.AX;
		}

		if (number.length() == 16
				&& Integer.parseInt(number.substring(0, 5)) == 6011) {
			return CardType.DS;
		}

		if (number.length() == 14
				&& ((Integer.parseInt(number.substring(0, 2)) == 36 || Integer
						.parseInt(number.substring(0, 2)) == 38) || Integer
						.parseInt(number.substring(0, 3)) >= 300
						&& Integer.parseInt(number.substring(0, 3)) <= 305)) {
			return CardType.DC;
		}

		if (number.length() == 15
				&& (Integer.parseInt(number.substring(0, 4)) == 2014 || Integer
						.parseInt(number.substring(0, 4)) == 2149)) {
			return CardType.ER;
		}

		return CardType.IC;

	}

	/**
	 * The Luhn algorithm is basically a CRC type system for checking the
	 * validity of an entry. All major credit cards use numbers that will pass
	 * the Luhn check. Also, all of them are based on MOD 10.
	 * 
	 * @return true if the credit card number satisfies the Luhn test.
	 * @param number
	 */
	private static boolean luhnValidate(String cardNumber) {
		// number must be validated as 0..9 numeric first!!
		int digits = cardNumber.length();
		int oddOrEven = digits & 1;
		long sum = 0;
		for (int count = 0; count < digits; count++) {
			int digit = 0;
			try {
				digit = Integer.parseInt(cardNumber.charAt(count) + "");
			} catch (NumberFormatException e) {
				return false;
			}
			if (((count & 1) ^ oddOrEven) == 0) {
				digit *= 2;
				if (digit > 9) {
					digit -= 9;
				}
			}
			sum += digit;
		}
		return (sum == 0) ? false : (sum % 10 == 0);
	}

	/**
	 * @return list of credit card months
	 */
	private static final String[] MONTHS = { "01", "02", "03", "04", "05",
			"06", "07", "08", "09", "10", "11", "12" };

	public static final List<NameId> getMonths() {
		return NameId.getList(MONTHS, MONTHS);
	}

	/**
	 * @return list of credit card years
	 */
	private static final String[] YEARS = { "2011", "2012", "2013", "2014",
			"2015", "2016", "2017", "2018", "2019", "2020" };

	public static final List<NameId> getYears() {
		return NameId.getList(YEARS, YEARS);
	}

	public static final String NAME = "finance.name";
	public static final String OWNERNAME = "party.name";
	public static final String STATE = "finance.state";
	public static final String TYPE = "finance.type";
	public static final String BANKNAME = "finance.bankname";
	public static final String BRANCHNAME = "finance.branchname";
	public static final String BRANCHNUMBER = "finance.branchnumber";
	public static final String ACCOUNTNUMBER = "finance.accountnumber";
	public static final String IBANSWIFT = "finance.ibanswift";

	private String ownerid;
	private String accountid;
	private String ownername;
	private String bankname;
	private String branchname;
	private String branchnumber;
	private String accountnumber;
	private String ibanswift;
	private String contactperson;
	private String phonenumber;
	private String currency;
	private String unit;
	private String month;
	private String year;
	private String code;
	private String notes;

	public Service service() {return Service.FINANCE;}

	public Finance() {}

	public Finance(String id) {
		this.id = id;
	}

	public String getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}

	public boolean noOwnerid() {
		return ownerid == null || ownerid.isEmpty()
				|| ownerid.equals(Model.ZERO);
	}

	public String getOwnername() {
		return ownername;
	}

	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public boolean noAccountid() {
		return accountid == null || accountid.isEmpty()
				|| accountid.equals(Model.ZERO);
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getBranchnumber() {
		return branchnumber;
	}

	public void setBranchnumber(String branchnumber) {
		this.branchnumber = branchnumber;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public boolean noAccountnumber(boolean card) {
		return accountnumber == null || accountnumber.isEmpty()
				|| (card && !isCreditCardNumber(accountnumber));
	}

	public boolean hasAccountnumber(boolean card) {
		return !noAccountnumber(card);
	}

	public String getIbanswift() {
		return ibanswift;
	}

	public void setIbanswift(String ibanswift) {
		this.ibanswift = ibanswift;
	}

	public String getContactperson() {
		return contactperson;
	}

	public void setContactperson(String contactperson) {
		this.contactperson = contactperson;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean noCurrency() {
		return currency == null || currency.isEmpty();
	}

	public boolean hasCurrency(String currency) {
		return this.currency != null && this.currency.equals(currency);
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public boolean noMonth() {
		return month == null || month.isEmpty();
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public boolean noYear() {
		return year == null || year.isEmpty();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean noCode() {
		return code == null || code.isEmpty();
	}

	 public String getYyyymm() {
		 return noMonth() || noYear() ? null : ("20" + year) + month;
	 }
	// public void setNotes(String notes) {
	// this.notes = notes;
	// }

	// ---------------------------------------------------------------------------------
	// Public text shared among all organizations in several languages
	// ---------------------------------------------------------------------------------
	// public static final String getPublicId(String id){
	// return NameId.Type.Finance.name() + id + Text.Type.Public.name();
	// }

	// public static Text getPublicText(String id){
	// return new Text(getPublicId(id), null, Text.Type.Public,
	// Text.Format.HTML, new Date(), null, Language.EN);
	// }

	// public String getPublicId() {
	// return getPublicId(id);
	// }
	//
	public Text getPublicText() {
		return getText(NameId.Type.Finance, id, Text.Code.Public);
	}

	public void setPublicText(Text value) {
		setText(NameId.Type.Finance, id, Text.Code.Public, value);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Finance [ownerid=");
		builder.append(ownerid);
		builder.append(", accountid=");
		builder.append(accountid);
		builder.append(", ownername=");
		builder.append(ownername);
		builder.append(", bankname=");
		builder.append(bankname);
		builder.append(", branchname=");
		builder.append(branchname);
		builder.append(", branchnumber=");
		builder.append(branchnumber);
		builder.append(", accountnumber=");
		builder.append(accountnumber);
		builder.append(", ibanswift=");
		builder.append(ibanswift);
		builder.append(", contactperson=");
		builder.append(contactperson);
		builder.append(", phonenumber=");
		builder.append(phonenumber);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", month=");
		builder.append(month);
		builder.append(", year=");
		builder.append(year);
		builder.append(", code=");
		builder.append(code);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", organizationid=");
		builder.append(organizationid);
		builder.append(", status=");
		builder.append(status);
		builder.append(", state=");
		builder.append(state);
		builder.append(", values=");
		builder.append(values);
		builder.append(", attributes=");
		builder.append(attributemap);
		builder.append(", texts=");
		builder.append(texts);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}
}