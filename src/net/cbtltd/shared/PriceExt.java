package net.cbtltd.shared;

public class PriceExt extends ModelTable {
protected int priceID;
protected String rateID;
protected int closedOnArrival;
protected int closedOnDep;
protected int closed;
public int getPriceID() {
	return priceID;
}
public void setPriceID(int priceID) {
	this.priceID = priceID;
}
public int getClosedOnArrival() {
	return closedOnArrival;
}
public void setClosedOnArrival(int closedOnArrival) {
	this.closedOnArrival = closedOnArrival;
}
public int getClosedOnDep() {
	return closedOnDep;
}
public void setClosedOnDep(int closedOnDep) {
	this.closedOnDep = closedOnDep;
}
public int getClosed() {
	
	return closed;
}
public void setClosed(int closed) {
	this.closed = closed;
}
@Override
public Service service() {
	return Service.PRICE;
}
public String getRateID() {
	return rateID;
}
public void setRateID(String rateID) {
	this.rateID = rateID;
}
}
