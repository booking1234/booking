package net.cbtltd.shared.registration;

import net.cbtltd.rest.AbstractRegistration;

public class RegistrationRespStepFactory {

	private static RegistrationRespStepFactory instance;

	private RegistrationRespStepFactory() {
	}

	public static RegistrationRespStepFactory getInstance() {
		if (instance == null) {
			instance = new RegistrationRespStepFactory();
		}

		return instance;
	}

	public RegistrationResponse getStepResponse(String request) {

		RegistrationResponse response;
		String stepId = AbstractRegistration.GetJsonParam(request, "step");

		if (stepId.equals("2")) {
			response = new SecondStepResponse(request);
		} else if (stepId.equals("3")) {
			response = new ThirdStepResponse(request);
		} else if (stepId.equals("4")) {
			response = new FourthStepResponse(request);
		} else if (stepId.equals("5")) {
			response = new FifthStepResponse(request);
		} else if (stepId.equals("6")) {
			response = new SixthStepResponse(request);
		} else if (stepId.equals("7")) {
			response = new SeventhStepResponse(request);
		} else {
			response = new SecondStepResponse(request);
		}

		return response;

	}

	public RegistrationResponse getFullResponse(String pos) {

		RegistrationResponse response;

		/*if (stepId == 2) {*/
			response = new FullDataResponse(pos);
		/*} else if (stepId == 3) {
			response = new ThirdStepResponse();
		} else if (stepId == 4) {
			response = new FourthStepResponse();
		} else {
			response = new ThirdStepResponse();
		}*/

		return response;

	}

}
