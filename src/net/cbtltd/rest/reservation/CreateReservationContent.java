package net.cbtltd.rest.reservation;

import java.util.Date;
import java.util.Map;

import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.ReservationRequest;
import net.cbtltd.rest.response.ReservationResponse;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.PaymentGatewayProvider;
import net.cbtltd.shared.PaymentTransaction;
import net.cbtltd.shared.PendingTransaction;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.finance.gateway.CreditCard;

/**
 * Class that contains all of the objects needed for creating reservation flow.
 * 
 * @author rmelnyk
 *
 */
public class CreateReservationContent {
	private ReservationResponse reservationResponse;
	private ReservationRequest reservationRequest;
	private Date birthDate;
	private String currency;
	private String chargeType;
	private Reservation reservation;
	private CreditCard creditCard;
	private Product product;
	private Party channelPartnerParty;
	private Party customer;
	private Party propertyManager;
	private PropertyManagerInfo propertyManagerInfo;
	private GatewayHandler handler;
	private PaymentTransaction paymentTransaction;
	private PendingTransaction pendingTransaction;
	private PaymentGatewayProvider paymentGatewayProvider;
	private Map<String, String> resultMap;
	private int paymentGatewayId;
	private ManagerToGateway managerToGateway;
	private Double amountToCharge;
	private Double firstPayment;
	private Double secondPayment;
	private Boolean reservationFinished;
	
	public ReservationResponse getReservationResponse() {
		return reservationResponse;
	}

	public void setReservationResponse(ReservationResponse reservationResponse) {
		this.reservationResponse = reservationResponse;
	}

	public ReservationRequest getReservationRequest() {
		return reservationRequest;
	}

	public void setReservationRequest(ReservationRequest reservationRequest) {
		this.reservationRequest = reservationRequest;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Party getChannelPartnerParty() {
		return channelPartnerParty;
	}

	public void setChannelPartnerParty(Party channelPartnerParty) {
		this.channelPartnerParty = channelPartnerParty;
	}

	public Party getCustomer() {
		return customer;
	}

	public void setCustomer(Party customer) {
		this.customer = customer;
	}

	public Party getPropertyManager() {
		return propertyManager;
	}

	public void setPropertyManager(Party propertyManager) {
		this.propertyManager = propertyManager;
	}

	public PropertyManagerInfo getPropertyManagerInfo() {
		return propertyManagerInfo;
	}

	public void setPropertyManagerInfo(PropertyManagerInfo propertyManagerInfo) {
		this.propertyManagerInfo = propertyManagerInfo;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public GatewayHandler getHandler() {
		return handler;
	}

	public void setHandler(GatewayHandler handler) {
		this.handler = handler;
	}

	public PendingTransaction getPendingTransaction() {
		return pendingTransaction;
	}

	public void setPendingTransaction(PendingTransaction pendingTransaction) {
		this.pendingTransaction = pendingTransaction;
	}

	public PaymentTransaction getPaymentTransaction() {
		return paymentTransaction;
	}

	public void setPaymentTransaction(PaymentTransaction paymentTransaction) {
		this.paymentTransaction = paymentTransaction;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public PaymentGatewayProvider getPaymentGatewayProvider() {
		return paymentGatewayProvider;
	}

	public void setPaymentGatewayProvider(PaymentGatewayProvider paymentGatewayProvider) {
		this.paymentGatewayProvider = paymentGatewayProvider;
	}

	public Map<String, String> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, String> resultMap) {
		this.resultMap = resultMap;
	}

	public ManagerToGateway getManagerToGateway() {
		return managerToGateway;
	}

	public void setManagerToGateway(ManagerToGateway managerToGateway) {
		this.managerToGateway = managerToGateway;
	}

	public int getPaymentGatewayId() {
		return paymentGatewayId;
	}

	public void setPaymentGatewayId(int paymentGatewayId) {
		this.paymentGatewayId = paymentGatewayId;
	}

	public Double getAmountToCharge() {
		return amountToCharge;
	}

	public void setAmountToCharge(Double amountToCharge) {
		this.amountToCharge = amountToCharge;
	}

	public Double getFirstPayment() {
		return firstPayment;
	}

	public void setFirstPayment(Double firstPayment) {
		this.firstPayment = firstPayment;
	}

	public Double getSecondPayment() {
		return secondPayment;
	}

	public void setSecondPayment(Double secondPayment) {
		this.secondPayment = secondPayment;
	}

	public Boolean isReservationFinished() {
		return reservationFinished;
	}

	public void setReservationFinished(Boolean reservationFinished) {
		this.reservationFinished = reservationFinished;
	}
}
