package net.cbtltd.server.mail;

import net.cbtltd.server.ServiceException;
import net.cbtltd.shared.Error;

public class PMPendingPaymentMailBuilder {

	StringBuilder contentString;

	public PMPendingPaymentMailBuilder() {
		super();
		contentString = new StringBuilder();
	}
	
	public String getSuccessfulMailSubject(PMPendingPaymentMailContent content) {
		if(!content.isSuccessfulSubjectComplete()) {
			throw new ServiceException(Error.parameter_absent, "in PM pending payment subject");
		}
		contentString = new StringBuilder();
		contentString.append(content.buildSuccessfulSubject());
		return contentString.toString();
	}
	
	public String getFailedMailSubject(PMPendingPaymentMailContent content) {
		if(!content.isFailedSubjectComplete()) {
			throw new ServiceException(Error.parameter_absent, "in PM pending payment subject");
		}
		contentString = new StringBuilder();
		contentString.append(content.buildFailedSubject());
		return contentString.toString();
	}
	
	public String getSuccessfulMail(PMPendingPaymentMailContent content) {
		if(!content.isSuccessfulMailComplete()) {
			throw new ServiceException(Error.parameter_absent, "in PM pending payment email");
		}
		contentString = new StringBuilder();
		contentString.append(content.buildRemainingBalancePart());
		contentString.append(content.buildPropertyInfoPart());
		contentString.append(content.buildPaymentDetailsPart());
		contentString.append(content.buildRenterInfoPart());
		contentString.append(content.buildAdditionalInfoPart());
		contentString.append(content.buildThanksPart());
		return contentString.toString();
	}
	
	public String getFailedMail(PMPendingPaymentMailContent content) {
		if(!content.isFailedMailComplete()) {
			throw new ServiceException(Error.parameter_absent, "in PM pending payment email");
		}
		contentString = new StringBuilder();
		contentString.append(content.buildUnableCollectPart());
		contentString.append(content.buildPropertyInfoPart());
		contentString.append(content.buildPaymentDetailsPart());
		contentString.append(content.buildRenterInfoPart());
		contentString.append(content.buildAdditionalInfoPart());
		contentString.append(content.buildThanksPart());
		return contentString.toString();
	}
}
