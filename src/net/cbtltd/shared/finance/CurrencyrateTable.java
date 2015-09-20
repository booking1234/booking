/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.finance;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Currencyrate;
import net.cbtltd.shared.ModelTable;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.api.HasTableService;

public class CurrencyrateTable
extends Currency
implements HasTableService {

	private ArrayList<Currencyrate> pairs = new ArrayList<Currencyrate>();
	private String orderby;
	private int startrow;
	private int numrows;

	public CurrencyrateTable() {
		this(Currency.PRICE_CURRENCIES);
	}

	public CurrencyrateTable(String[] currencies) {
		Date now = Time.getDateStart();
		for (int i = 0; i < currencies.length; i++){
			for (int j = 0; j < currencies.length; j++){
				if (i != j) {
					pairs.add(new Currencyrate(currencies[i], currencies[j], now));
				}
			}
		}
	}
	
	public Service service() {return Service.FINANCE;}

	public ArrayList<Currencyrate> getPairs() {
		return pairs;
	}
	
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

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(" OrderBy ").append(getOrderby());
		sb.append(" StartRow ").append(getStartrow());
		sb.append(" NumRows ").append(getNumrows());
		sb.append("\nPairs ").append(getPairs());
		return sb.toString();
	}
}
