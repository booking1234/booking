package net.cbtltd.server.util.price;

public class PriceComplexValue {
	
	public PriceComplexValue() {
		super();
	}

	private double totPrice = 0.0;
	private double totQuote = 0.0;
	
	// extra item
	private Double totExtra = 0.0;

	public void addTotalPrice(double value){
		this.totPrice += value;
	}
	
	public void addTotalQuote(double value){
		this.totQuote += value;
	}
	
	public void addTotalExtra(double value){
		this.totExtra += value;
	}
	
	public double getTotPrice() {
		return totPrice;
	}

	public void setTotPrice(double totPrice) {
		this.totPrice = totPrice;
	}

	public double getTotQuote() {
		return totQuote;
	}

	public void setTotQuote(double totQuote) {
		this.totQuote = totQuote;
	}

	public Double getTotExtra() {
		return totExtra;
	}

	public void setTotExtra(Double totExtra) {
		this.totExtra = totExtra;
	}
	
	
	
}
