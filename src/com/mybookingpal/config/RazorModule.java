package com.mybookingpal.config;


import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.mybookingpal.eventbus.ChannelEventListener;
import com.mybookingpal.eventbus.EventBusManager;
import com.mybookingpal.eventbus.SqlEventPublisher;
import com.mybookingpal.freemarker.ConfigurationBuilder;
import com.mybookingpal.server.EmailService;
import com.mybookingpal.server.TemplateService;

import freemarker.template.Configuration;


public class RazorModule extends AbstractModule {
//	private EventBus sqlEventBus = new EventBus("SQL DML Events");
	private EventBus sqlEventBus =  new AsyncEventBus(java.util.concurrent.Executors.newCachedThreadPool()); 
	
	@Override
	protected void configure() {
		System.out.println("Loading Razor Module...");
		bind(Configuration.class).toProvider(new Provider() {
		      public Configuration get() {
		        return ConfigurationBuilder.getCurrentConfiguration();
		      }
		    });
		bind(TemplateService.class);
		requestStaticInjection(EmailService.class);
		
		// initialize event bus
		bind(ChannelEventListener.class);
		bind(EventBus.class).toInstance(sqlEventBus);
		bind(SqlEventPublisher.class);
		requestStaticInjection(EventBusManager.class);
//		bindListener(Matchers.any(), new TypeListener() {
//			@Override
//			public <I> void hear(TypeLiteral<I> arg0, TypeEncounter<I> arg1) {
//				arg1.register(new InjectionListener<I>() {
//			           @Override public void afterInjection(final I instance) {
//			        	   sqlEventBus.register(instance);
//			           }
//			       });
//				
//			}
//			});
	}

}
