package com.mybookingpal.freemarker;

import java.util.Locale;

import com.mybookingpal.server.api.junit.EmailServiceTest;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;


public class ConfigurationBuilder {

	public static Configuration getCurrentConfiguration() {
		Configuration cfg = new Configuration();
		// Where do we load the templates from:
	    cfg.setClassForTemplateLoading(ConfigurationBuilder.class, "templates");
	    
	    // Some other recommended settings:
	    cfg.setDefaultEncoding("UTF-8");
	    cfg.setLocale(Locale.US);
	    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		return cfg;
	}

}
