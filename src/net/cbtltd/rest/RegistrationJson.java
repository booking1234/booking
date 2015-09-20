/*
 * 
 */
package net.cbtltd.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.cbtltd.rest.error.ApiResponse;
import net.cbtltd.rest.registration.ChannelPartnerResponse;
import net.cbtltd.rest.registration.LoginResponse;
import net.cbtltd.rest.registration.StatusResponse;
import net.cbtltd.shared.Credentials;
import net.cbtltd.shared.registration.ManagerRequest;
import net.cbtltd.shared.registration.ManagerResponse;
import net.cbtltd.shared.registration.RegistrationHelper;
import net.cbtltd.shared.registration.RegistrationRespStepFactory;
import net.cbtltd.shared.registration.RegistrationResponse;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.RETURN;

// TODO: Auto-generated Javadoc
/**
 * The Class RegistrationRest.
 * 
 * 
 */
@Path("/registration")
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationJson extends AbstractRegistration {

	private static final Gson GSON = new Gson();
	private static final RegistrationHelper HELPER = new RegistrationHelper();
	
	@GET
	@Path("/status")
	public synchronized StatusResponse getManagerStep(@Description("User email") @QueryParam("user") String userEmail,
			@Description("Party ID") @QueryParam("pos") String partyId) {

		return getRegistartionStatus(userEmail, partyId);
	}
	
	@GET
	@Path("/usercheck")
	public synchronized String userCheck(@QueryParam("user") String user, @QueryParam("password") String password){
		
		return HELPER.validateUserId(user, password);
	}
	
	
	@POST
	@Path("/login")
	public synchronized LoginResponse Login(String request) {

		Credentials credentials = GSON.fromJson(request, Credentials.class);			
		return doLogin(credentials.getUsername(), credentials.getPassword());
		
		// TODO: ADD encrypted manager id
	}

	@GET
	@Path("/login")
	public synchronized String Login(@QueryParam("user") String user, @QueryParam("password") String password) {
		
		LoginResponse loginResponse = doLogin(user, password);
	
		return "OK";
	}
	
	@GET
	@Path("/logout")
	public synchronized String Logout() {
		return doLogout();	
	}

	@POST
	@Path("/")
	@Descriptions({ @Description(value = "Create the property manager", target = DocTarget.METHOD),
			@Description(value = "Response code", target = DocTarget.RETURN), @Description(value = "Request", target = DocTarget.REQUEST),
			@Description(value = "Response", target = DocTarget.RESPONSE), @Description(value = "Resource", target = DocTarget.RESOURCE) })
	@Consumes(MediaType.APPLICATION_JSON)
	public synchronized ChannelPartnerResponse createManagerJson(String request) {

		String pos = GetJsonParam(request, "pos"); // CreatorID
		String managerPos = GetJsonParam(request, "managerpos");
		String account = GetJsonParam(request, "account"); // AltPartyID
		String companyName = GetJsonParam(request, "company");
		String firstName = GetJsonParam(request, "firstname");
		String lastName = GetJsonParam(request, "lastname");
		String address = GetJsonParam(request, "address");
		String city = GetJsonParam(request, "city");
		String state = GetJsonParam(request, "state");
		String code = GetJsonParam(request, "code");
		String countryId = GetJsonParam(request, "countryid");
		String telephone = GetJsonParam(request, "phone");
		String email = GetJsonParam(request, "email");
		String password = GetJsonParam(request, "password");
//		String step = GetJsonParam(request, "step");

		// TODO: ADD encrypted manager id

		return doCreateManager(pos, managerPos, account, companyName, firstName, lastName, address, city, state, code, countryId, telephone, email,
				password);
	}

	@POST
	@Path("/step")
	@Descriptions({ @Description(value = "Registration tep", target = DocTarget.METHOD),
			@Description(value = "Channel Partners list", target = DocTarget.RETURN),
			@Description(value = "Request", target = DocTarget.REQUEST), @Description(value = "Response", target = DocTarget.RESPONSE),
			@Description(value = "Resource", target = DocTarget.RESOURCE) })
	public synchronized RegistrationResponse doNextStep(String request) {

		RegistrationRespStepFactory stepFactory = RegistrationRespStepFactory.getInstance();
		return stepFactory.getStepResponse(request);

	}

	@GET
	@Path("/step")
	@Descriptions({ @Description(value = "Registration step", target = DocTarget.METHOD),
			@Description(value = "Info correspondent to the step", target = DocTarget.RETURN),
			@Description(value = "Post json string", target = DocTarget.REQUEST), @Description(value = "Response", target = DocTarget.RESPONSE),
			@Description(value = "Resource", target = DocTarget.RESOURCE) })
	public synchronized RegistrationResponse getNextStep(@Description("Encrypted Manager ID") @QueryParam("pos") String pos,
			@Description("Step ID") @QueryParam("step") String stepID) {

		RegistrationRespStepFactory stepFactory = RegistrationRespStepFactory.getInstance();
		/*return stepFactory.getStepResponse(Integer.parseInt(stepID));*/
		return stepFactory.getFullResponse(pos);
	}
	
	@POST
	@Path("/manager")
	@Consumes(MediaType.APPLICATION_JSON)
	@Descriptions({ @Description(value = "Register property manager", target = DocTarget.METHOD),
			@Description(value = "Status response", target = DocTarget.RETURN),
			@Description(value = "Request", target = DocTarget.REQUEST), 
			@Description(value = "Response", target = DocTarget.RESPONSE),
			@Description(value = "Resource", target = DocTarget.RESOURCE) })
	public synchronized ManagerResponse doManagerRegistration(
			@Description("Manager to register") ManagerRequest request) {
		return registerManager(request);

	}

}

