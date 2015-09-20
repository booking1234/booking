/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** USD US dollar http://webservices.bermilabs.com/exchange/eur/usd
 * JPY Japanese yen http://webservices.bermilabs.com/exchange/eur/jpy
 * BGN Bulgarian lev http://webservices.bermilabs.com/exchange/eur/bgn
 * CZK Czech koruna http://webservices.bermilabs.com/exchange/eur/czk
 * DKK Danish krone http://webservices.bermilabs.com/exchange/eur/dkk
 * EEK Estonian kroon http://webservices.bermilabs.com/exchange/eur/eek
 * GBP Pound sterling http://webservices.bermilabs.com/exchange/eur/gbp
 * HUF Hungarian forint http://webservices.bermilabs.com/exchange/eur/huf
 * LTL Lithuanian litas http://webservices.bermilabs.com/exchange/eur/ltl
 * LVL Latvian lats http://webservices.bermilabs.com/exchange/eur/lvl
 * PLN Polish zloty http://webservices.bermilabs.com/exchange/eur/pln
 * RON New Romanian leu 1 http://webservices.bermilabs.com/exchange/eur/ron
 * SEK Swedish krona http://webservices.bermilabs.com/exchange/eur/sek
 * SKK Slovak koruna http://webservices.bermilabs.com/exchange/eur/skk
 * CHF Swiss franc http://webservices.bermilabs.com/exchange/eur/chf
 * ISK Icelandic krona http://webservices.bermilabs.com/exchange/eur/isk
 * NOK Norwegian krone http://webservices.bermilabs.com/exchange/eur/nok
 * HRK Croatian kuna http://webservices.bermilabs.com/exchange/eur/hrk
 * RUB Russian rouble http://webservices.bermilabs.com/exchange/eur/rub
 * TRY New Turkish lira 2 http://webservices.bermilabs.com/exchange/eur/try
 * AUD Australian dollar http://webservices.bermilabs.com/exchange/eur/aud
 * BRL Brasilian real http://webservices.bermilabs.com/exchange/eur/brl
 * CAD Canadian dollar http://webservices.bermilabs.com/exchange/eur/cad
 * CNY Chinese yuan renminbi http://webservices.bermilabs.com/exchange/eur/cny
 * HKD Hong Kong dollar http://webservices.bermilabs.com/exchange/eur/hkd
 * IDR Indonesian rupiah http://webservices.bermilabs.com/exchange/eur/idr
 * KRW South Korean won http://webservices.bermilabs.com/exchange/eur/krw
 * MXN Mexican peso http://webservices.bermilabs.com/exchange/eur/mxn
 * MYR Malaysian ringgit http://webservices.bermilabs.com/exchange/eur/myr
 * NZD New Zealand dollar http://webservices.bermilabs.com/exchange/eur/nzd
 * PHP Philippine peso http://webservices.bermilabs.com/exchange/eur/php
 * SGD Singapore dollar http://webservices.bermilabs.com/exchange/eur/sgd
 * THB Thai baht http://webservices.bermilabs.com/exchange/eur/thb
 * ZAR South African rand http://webservices.bermilabs.com/exchange/eur/zar
 */
public class Currency
extends Model {
	
	public enum Code {AUD,BGN,BRL,CAD,CHF,CNY,CZK,DKK,EUR,GBP,HKD,HRK,HUF,IDR,INR,ISK,JPY,
		KRW,LTL,LVL,MXN,MYR,NOK,NZD,PHP,PLN,RON,RUB,SEK,SGD,SKK,THB,TRY,USD,ZAR};
//	public static final String AUD = "AUD";
//	public static final String BGN = "BGN";
//	public static final String BRL = "BRL";
//	public static final String CAD = "CAD";
//	public static final String CNY = "CNY";
//	public static final String CZK = "CZK";
//	public static final String DKK = "DKK";
//	public static final String EUR = "EUR";
//	public static final String HKD = "HKD";
//	public static final String HRK = "HRK";
//	public static final String HUF = "HUF";
//	public static final String IDR = "IDR";
//	public static final String ILS = "ILS";
//	public static final String INR = "INR";
//	public static final String ISK = "ISK";
//	public static final String JPY = "JPY";
//	public static final String KRW = "KRW";
//	public static final String LTL = "LTL";
//	public static final String LVL = "LVL";
//	public static final String MXN = "MXN";
//	public static final String MYR = "MYR";
//	public static final String NOK = "NOK";
//	public static final String NZD = "NZD";
//	public static final String PHP = "PHP";
//	public static final String PLN = "PLN";
//	public static final String RON = "RON";
//	public static final String RUB = "RUB";
//	public static final String GBP = "GBP";
//	public static final String SGD = "SGD";
//	public static final String SEK = "SEK";
//	public static final String SKK = "SKK";
//	public static final String CHF = "CHF";
//	public static final String TWD = "TWD";
//	public static final String THB = "THB";
//	public static final String TRY = "TRY";
//	public static final String USD = "USD";
//	public static final String ZAR = "ZAR";
	
	public static final String[] EXCHANGE_CURRENCIES ={
		Code.AUD.name(), 
		Code.BGN.name(), 
		Code.BRL.name(), 
		Code.CAD.name(), 
		Code.CHF.name(), 
		Code.CNY.name(), 
		Code.CZK.name(), 
		Code.DKK.name(), 
		Code.EUR.name(), 
		Code.GBP.name(), 
		Code.HKD.name(), 
		Code.HRK.name(), 
		Code.HUF.name(), 
		Code.IDR.name(), 
		Code.INR.name(), 
		Code.ISK.name(), 
		Code.JPY.name(), 
		Code.KRW.name(), 
		Code.LTL.name(), 
		Code.LVL.name(), 
		Code.MXN.name(), 
		Code.MYR.name(), 
		Code.NOK.name(), 
		Code.NZD.name(), 
		Code.PHP.name(), 
		Code.PLN.name(), 
		Code.RON.name(), 
		Code.RUB.name(), 
		Code.SEK.name(), 
		Code.SGD.name(), 
		Code.SKK.name(), 
		Code.THB.name(), 
		Code.TRY.name(), 
		Code.USD.name(), 
		Code.ZAR.name()
	};
	
	private static final String[] EXCHANGE_CURRENCY_NAMES = {
			 "Australian Dollar",
			 "Bulgarian Lev",
			 "Brasilian Real",
			 "Canadian Dollar",
			 "Swiss Franc",
			 "Chinese Yuan Renminbi",
			 "Czech Koruna",
			 "Danish Krone",
			 "Euro",
			 "Pound Sterling",
			 "Hong Kong Dollar",
			 "Croatian Kuna",
			 "Hungarian Forint",
			 "Indonesian Rupiah",
			 "Indian Rupee",
			 "Icelandic Krona",
			 "Japanese Yen",
			 "South Korean Won",
			 "Lithuanian Litas",
			 "Latvian Lats",
			 "Mexican Peso",
			 "Malaysian Ringgit",
			 "Norwegian Krone",
			 "New Zealand Dollar",
			 "Philippine Peso",
			 "Polish Zloty",
			 "New Romanian Leu",
			 "Russian Rouble",
			 "Swedish Krona",
			 "Singapore Dollar",
			 "Slovak Koruna",
			 "Thai Baht",
			 "Turkish Lira",
			 "US Dollar",
			 "Rand"
	};

	private static final List<String> convertibleCurrencies = Arrays.asList(EXCHANGE_CURRENCIES);
	public static final boolean isConvertible(String currency){
		return convertibleCurrencies.contains(currency);
	}
	
	public static final ArrayList<NameId> getConvertibleCurrencyNameIds() {return NameId.getList(EXCHANGE_CURRENCY_NAMES, EXCHANGE_CURRENCIES);}

//	private static final String[] PAYPAL_CURRENCIES = {AUD, CAD, CZK, DKK, EUR, HKD, HUF, ILS, JPY, MXN, NOK, NZD, PHP, PLN, GBP, SGD, SEK, CHF, TWD, THB, USD};
//	private static final List<String> paypalCurrencies = Arrays.asList(PAYPAL_CURRENCIES);

//	public static final boolean isPaypal(String currency){
//		return paypalCurrencies.contains(currency);
//	}

	public static final String[] PRICE_CURRENCIES ={Code.EUR.name(), Code.GBP.name(), Code.USD.name(), Code.JPY.name(), Code.ZAR.name()};

	public static final String CODE = "code";
	public static final String NAME = "name";
	
	private String number;
    private Short decimals;
    private Boolean convertible;
    private Boolean paypal;
    private Boolean jetpay;

	public Service service() {return Service.FINANCE;}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Short getDecimals() {
		return decimals;
	}

	public void setDecimals(Short decimals) {
		this.decimals = decimals;
	}

	public Boolean getConvertible() {
		return convertible;
	}

	public void setConvertible(Boolean convertible) {
		this.convertible = convertible;
	}

	public Boolean getPaypal() {
		return paypal;
	}

	public void setPaypal(Boolean paypal) {
		this.paypal = paypal;
	}

	public Boolean getJetpay() {
		return jetpay;
	}

	public void setJetpay(Boolean jetpay) {
		this.jetpay = jetpay;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Currency [number=");
		builder.append(number);
		builder.append(", decimals=");
		builder.append(decimals);
		builder.append(", convertible=");
		builder.append(convertible);
		builder.append(", paypal=");
		builder.append(paypal);
		builder.append(", jetpay=");
		builder.append(jetpay);
		builder.append(", state=");
		builder.append(state);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
}