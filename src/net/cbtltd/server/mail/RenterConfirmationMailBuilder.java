package net.cbtltd.server.mail;

import java.text.ParseException;

import net.cbtltd.server.ServiceException;
import net.cbtltd.shared.Error;

public class RenterConfirmationMailBuilder {
	StringBuilder contentString;

	public RenterConfirmationMailBuilder() {
		super();
		contentString = new StringBuilder();
	}

	public String getDepositMail(RenterConfirmationMailContent content) throws ParseException {
		if(!content.isDepositPaymentFieldsComplete()) {
			throw new ServiceException(Error.parameter_absent, "in renter confirmation email");
		}
		contentString = new StringBuilder();
		contentString.append(content.buildGreetingPart());
		contentString.append(content.buildFirstPaymentPart());
		contentString.append(content.buildSecondPaymentPart());
		contentString.append(content.buildContactPMPart());
		contentString.append(content.buildPaymentInfoPart());
		contentString.append(content.buildDownPaymentPart());
		contentString.append(content.buildFeesPart());
		contentString.append(content.buildCancellationPart());
		contentString.append(content.buildCancellationLink());
		contentString.append(content.buildThanksMessagePart());
		return contentString.toString();
	}
	
	public String getFullMail(RenterConfirmationMailContent content) throws ParseException {
		if(!content.isFullPaymentFieldsComplete()) {
			throw new ServiceException(Error.parameter_absent, "in renter confirmation email");
		}
		contentString = new StringBuilder();
		contentString.append(content.buildGreetingPart());
		contentString.append(content.buildFirstPaymentPart());
		contentString.append(content.buildContactPMPart());
		contentString.append(content.buildPaymentInfoPart());
		contentString.append(content.buildFeesPart());
		contentString.append(content.buildCancellationPart());
		contentString.append(content.buildCancellationLink());
		contentString.append(content.buildThanksMessagePart());
		return contentString.toString();
	}
	
	public String getWithoutPaymentMail(RenterConfirmationMailContent content) {
		if(!content.isWithoutPaymentFieldsComplete()) {
			throw new ServiceException(Error.parameter_absent, "in renter confirmation email");
		}
		contentString = new StringBuilder();
		contentString.append(content.buildMailWithoutPayment());
		return contentString.toString();
	}

}
