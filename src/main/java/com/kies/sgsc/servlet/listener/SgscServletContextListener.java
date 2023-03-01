package com.kies.sgsc.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Value;

public class SgscServletContextListener implements ServletContextListener {
	
	  public void contextInitialized(ServletContextEvent e) {
	      System.out.println("############## SgscServletContextListener Context Initialized ##########");
	  }

	  public void contextDestroyed(ServletContextEvent e) {
	    System.out.println("############## SgscServletContextListener Context Destroyed  ##########");
	  }
}
