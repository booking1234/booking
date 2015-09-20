package net.cbtltd.rest.bookingdom;

public class BookingDomPriceResult {
	private String calculatedPrice;
	private String fee;
	private String firstPayoff;
	private String overallValue;
	private String deposit;
	private double depositAmount;
	private double feeAmount;
	private double firstPayoffAmount;
	private double overallValueAmount;
	private boolean available;
	private double changeFactor;
	private String apiVersion;
	private String apiMethod;
	private String methodType;
	private String resultCode;
	private String resultText;
	
	public String getCalculatedPrice() {
		return calculatedPrice;
	}
	public void setCalculatedPrice(String calculatedPrice) {
		this.calculatedPrice = calculatedPrice;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getFirstPayoff() {
		return firstPayoff;
	}
	public void setFirstPayoff(String firstPayoff) {
		this.firstPayoff = firstPayoff;
	}
	public String getOverallValue() {
		return overallValue;
	}
	public void setOverallValue(String overallValue) {
		this.overallValue = overallValue;
	}
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	public double getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(double depositAmount) {
		this.depositAmount = depositAmount;
	}
	public double getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(double feeAmount) {
		this.feeAmount = feeAmount;
	}
	public double getFirstPayoffAmount() {
		return firstPayoffAmount;
	}
	public void setFirstPayoffAmount(double firstPayoffAmount) {
		this.firstPayoffAmount = firstPayoffAmount;
	}
	public double getOverallValueAmount() {
		return overallValueAmount;
	}
	public void setOverallValueAmount(double overallValueAmount) {
		this.overallValueAmount = overallValueAmount;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public double getChangeFactor() {
		return changeFactor;
	}
	public void setChangeFactor(double changeFactor) {
		this.changeFactor = changeFactor;
	}
	public String getApiVersion() {
		return apiVersion;
	}
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}
	public String getApiMethod() {
		return apiMethod;
	}
	public void setApiMethod(String apiMethod) {
		this.apiMethod = apiMethod;
	}
	public String getMethodType() {
		return methodType;
	}
	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultText() {
		return resultText;
	}
	public void setResultText(String resultText) {
		this.resultText = resultText;
	}
}
