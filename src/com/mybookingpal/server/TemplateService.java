package com.mybookingpal.server;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.mybookingpal.wrapper.ReservationWrapper;

import net.cbtltd.shared.Party;
import net.cbtltd.shared.Reservation;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateService {
	
	private final Configuration cfg;
	
	private static final String RESERVATTION_TEMPLATE 	= "reservationmail.ftl";
	private static final String CANCELLATTION_TEMPLATE 	= "cancellationmail.ftl";
	
	@Inject
	public TemplateService(Configuration cfg) {
		
		this.cfg = cfg;
	}
	
	public String buildReservationEmail(ReservationWrapper reservationWrapper) {
		String output = "";
		// Prepare the template input:
	    Map<String, Object> input = new HashMap<String, Object>();
	    input.put("title", "Reservation Email");
	    input.put("reservationWrapper", reservationWrapper);
	    
	    // Get the template. Template is cached. So it's efficient to use the getTemplate call.
	    Template template;
		try {
			template = cfg.getTemplate(TemplateService.RESERVATTION_TEMPLATE);
			StringWriter writer = new StringWriter();
			template.process(input, writer);
			output = writer.toString();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return output;
		
	}
	
	public String buildCancellationEmail(ReservationWrapper reservationWrapper) {
		String output = "";
		// Prepare the template input:
	    Map<String, Object> input = new HashMap<String, Object>();
	    input.put("title", "Cancellation Email");
	    input.put("reservationWrapper", reservationWrapper);
	    
	    // Get the template. Template is cached. So it's efficient to use the getTemplate call.
	    Template template;
		try {
			template = cfg.getTemplate(TemplateService.CANCELLATTION_TEMPLATE);
			StringWriter writer = new StringWriter();
			template.process(input, writer);
			output = writer.toString();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return output;
		
	}

}
