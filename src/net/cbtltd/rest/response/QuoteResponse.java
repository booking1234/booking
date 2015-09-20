/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.response;

import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.rest.error.ApiResponse;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.shared.License;
import net.cbtltd.shared.PropertyManagerSupportCC;

@XmlRootElement(name="quotes")
@XmlAccessorType(XmlAccessType.FIELD)
public class QuoteResponse extends ApiResponse {

	private Double price;
	private Double quote;
	private Double deposit;
	private Boolean available;
	private String currency;
	private Set<CancellationItem> cancellationItems;
	private Double tax;
	private Double damageInsurance;
	private Double cleaningFee;
	private Double firstPayment;
	private Double secondPayment;
	private String secondPaymentDate;
	private String propertyName;
	private String imageUrl;
	private Boolean paymentSupported;
	private PropertyManagerSupportCC propertyManagerSupportCC;
	private String fromTime;
	private String toTime;
	private String termsLink;
	private Integer minstay;
	
	@XmlElement(name = "quote_details")
	private ReservationPrice reservationPrice;
	
	public QuoteResponse() {
		super();
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getQuote() {
		return quote;
	}

	public void setQuote(Double quote) {
		this.quote = quote;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public Boolean isAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean hasCurrency(String currency) {
		return this.currency != null && this.currency.equalsIgnoreCase(currency);
	}
	
	public Set<CancellationItem> getCancellationItems() {
		return cancellationItems;
	}
	
	public void setCancellationItems(Set<CancellationItem> cancellationItems) {
		this.cancellationItems = cancellationItems;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getDamageInsurance() {
		return damageInsurance;
	}

	public void setDamageInsurance(Double damageInsurance) {
		this.damageInsurance = damageInsurance;
	}

	public Double getCleaningFee() {
		return cleaningFee;
	}

	public void setCleaningFee(Double cleaningFee) {
		this.cleaningFee = cleaningFee;
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

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean isPaymentSupported() {
		return paymentSupported;
	}

	public void setPaymentSupported(Boolean paymentSupported) {
		this.paymentSupported = paymentSupported;
	}

	public ReservationPrice getReservationPrice() {
		return reservationPrice;
	}

	public void setReservationPrice(ReservationPrice reservationPrice) {
		this.reservationPrice = reservationPrice;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public PropertyManagerSupportCC getPropertyManagerSupportCC() {
		return propertyManagerSupportCC;
	}

	public void setPropertyManagerSupportCC(PropertyManagerSupportCC propertyManagerSupportCC) {
		this.propertyManagerSupportCC = propertyManagerSupportCC;
	}

	public String getSecondPaymentDate() {
		return secondPaymentDate;
	}

	public void setSecondPaymentDate(String secondPaymentDate) {
		this.secondPaymentDate = secondPaymentDate;
	}

	public String getTermsLink() {
		return termsLink;
	}

	public void setTermsLink(String termsLink) {
		this.termsLink = termsLink;
	}

	public Integer getMinstay() {
		return minstay;
	}

	public void setMinstay(Integer minstay) {
		this.minstay = minstay;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QuoteResponse [price=");
		builder.append(price);
		builder.append(", quote=");
		builder.append(quote);
		builder.append(", deposit=");
		builder.append(deposit);
		builder.append(", available=");
		builder.append(available);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", minstay=");
		builder.append(minstay);
		builder.append("]");
		return builder.toString();
	}
}
