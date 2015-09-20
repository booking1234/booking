package net.cbtltd.server.mail;

import net.cbtltd.server.ServiceException;
import net.cbtltd.shared.Error;

public class PMConfirmationMailBuilder {

	StringBuilder contentString;

	public PMConfirmationMailBuilder() {
		super();
		contentString = new StringBuilder();
	}
	
	public String getDepositMail(PMConfirmationMailContent content) {
		if(!content.isDepositPaymentFieldsComplete()) {
			throw new ServiceException(Error.parameter_absent, "in PM confirmation email");
		}
		contentString = new StringBuilder();
		contentString.append(content.buildPropertyInfoPart());
		contentString.append(content.buildTotalPaymentPart());
		contentString.append(content.buildDepositPart());
		contentString.append(content.buildPaymentDetailsPart());
		contentString.append(content.buildCreditCardPart());
		contentString.append(content.buildSecondPaymentInfoPart()); 
		contentString.append(content.buildRenterInfoPart()); 
		contentString.append(content.buildThanksPart());
		return contentString.toString();
	}
	
	public String getFullMail(PMConfirmationMailContent content) {
		if(!content.isFullPaymentFieldsComplete()) {
			throw new ServiceException(Error.parameter_absent, "in PM confirmation email");
		}
		contentString = new StringBuilder();
		contentString.append(content.buildPropertyInfoPart());
		contentString.append(content.buildTotalPaymentPart());
		contentString.append(content.buildPaymentDetailsPart());
		contentString.append(content.buildCreditCardPart());
		contentString.append(content.buildRenterInfoPart()); 
		contentString.append(content.buildThanksPart());
		return contentString.toString();
	}
	
	public String getWithoutPaymentMail(PMConfirmationMailContent content) {
		if(!content.isWithoutPaymentFieldsComplete()) {
			throw new ServiceException(Error.parameter_absent, "in PM confirmation email");
		}
		contentString = new StringBuilder();
		contentString.append(content.buildPropertyInfoPart());
		contentString.append(content.buildTotalPaymentPart());
		contentString.append(content.buildPaymentDetailsPart());
		contentString.append(content.buildRenterInfoPart()); 
		contentString.append(content.buildThanksPart());
		return contentString.toString();
	}
}
