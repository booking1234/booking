package net.cbtltd.server.mail;

import net.cbtltd.server.ServiceException;
import net.cbtltd.shared.Error;

public class RenterPendingPaymentBuilder {
	StringBuilder contentString;

	public RenterPendingPaymentBuilder() {
		super();
		contentString = new StringBuilder();
	}
	
	public String getSuccessfulMailSubject(RenterPendingPaymentContent content) {
		if(!content.isSuccessfulSubjectComplete()) {
			throw new ServiceException(Error.parameter_absent, "in renter pending payment subject");
		}
		contentString = new StringBuilder();
		contentString.append(content.buildSuccessfulSubject());
		return contentString.toString();
	}
	
	public String getFailedMailSubject(RenterPendingPaymentContent content) {
		if(!content.isFailedSubjectIncomplete()) {
			throw new ServiceException(Error.parameter_absent, "in renter pending payment subject");
		}
		contentString = new StringBuilder();
		contentString.append(content.buildFailedSubject());
		return contentString.toString();
	}
	
	public String getSuccessfulMail(RenterPendingPaymentContent content) {
		if(!content.isSuccessfulMailComplete()) {
			throw new ServiceException(Error.parameter_absent, "in renter pending payment email");
		}
		contentString = new StringBuilder();
		contentString.append(content.buildChargedPart());
		contentString.append(content.buildPropertyManagerInfoPart());
		contentString.append(content.buildThanksPart());
		return contentString.toString();
	}
	
	public String getFailedMail(RenterPendingPaymentContent content) {
		if(!content.isFailedMailComplete()) {
			throw new ServiceException(Error.parameter_absent, "in renter pending payment email");
		}
		contentString = new StringBuilder();
		contentString.append(content.buildUnableChargePart());
		contentString.append(content.buildPropertyManagerInfoPart());
		contentString.append(content.buildThanksPart());
		return contentString.toString();
	}
}
