package com.mybookingpal.guice.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mybookingpal.config.RazorModule;



public class GuiceLoader implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContextListener destroyed");
	}
 
        //Run this before web application is started
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("ServletContextListener started...Loading GUICE Dependencies");	
		Injector injector = Guice.createInjector(new RazorModule());
	}
}
