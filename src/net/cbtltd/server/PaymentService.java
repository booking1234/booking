package net.cbtltd.server;

import java.util.Date;

import net.cbtltd.rest.Constants;

import org.apache.ibatis.session.SqlSession;

public class PaymentService {
	
	public static Double convertCurrency(SqlSession sqlSession, String fromCurrency, String toCurrency, Double amount) {
		if(Constants.NO_CURRENCY.equalsIgnoreCase(toCurrency) || fromCurrency.equals(toCurrency)) {
			return amount;
		} else {
			Double exchangeRate = WebService.getRate(sqlSession, fromCurrency, toCurrency, new Date());
			return amount * exchangeRate;
		}
	}
	
	public static Double convertCurrencySpecificDate(SqlSession sqlSession, String fromCurrency, String toCurrency, Double amount, Date date) {
		if(Constants.NO_CURRENCY.equalsIgnoreCase(toCurrency) || fromCurrency.equals(toCurrency)) {
			return amount;
		} else {
			Double exchangeRate = WebService.getRate(sqlSession, fromCurrency, toCurrency, date);
			return amount * exchangeRate;
		}
	}
}
