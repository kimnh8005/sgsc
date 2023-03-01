package com.kies.sgsc.servlet.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CrossDomainSiteAllowFilter implements Filter {
	
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
		
		final HttpServletResponse response = (HttpServletResponse) res;
	    response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
	    response.setHeader("Access-Control-Max-Age", "3600");
	    response.setHeader("Access-Control-Allow-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
	            "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
	    
	    //System.out.println("ParamLogFilter doFilter() is invoked.");
	    Enumeration<String> params = req.getParameterNames();
	    while (params.hasMoreElements()) {
	      String param=params.nextElement();
	      //System.out.println("Parameter:"+param+"\tValue:"+req.getParameter(param));
	    }
	    chain.doFilter(req, res);
	  }

	  public void init(FilterConfig config) throws ServletException {
	    
	  }

	  public void destroy() {

	  }
}
