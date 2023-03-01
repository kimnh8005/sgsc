package com.kies.sgsc.servlet;

import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kies.sgsc.comm.config.SessionConfig;
import com.kies.sgsc.servlet.filter.CrossDomainSiteAllowFilter;
import com.kies.sgsc.servlet.interceptor.JwtInterceptor;
import com.kies.sgsc.servlet.interceptor.LoggerInterceptor;
import com.kies.sgsc.servlet.listener.SgscServletContextListener;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Autowired 
	private SessionConfig sessionConfig;
	
	
	/** * Add Index Page */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/index.html");
	}
	
	@Autowired
	private JwtInterceptor jwtInterceptor;
	
	 @Override
	 public void addCorsMappings(CorsRegistry registry) {
		 logger.debug("addCorsMappings:"+registry);
		 registry.addMapping("/**").allowedOrigins("*").exposedHeaders(JwtInterceptor.HEADER_AUTH);
		// .allowedMethods("GET","POST","DELETE","PUT","OPTIONS");
    }
		
	/** Register intercepter **/
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		logger.debug("addInterceptors : skipUris"+sessionConfig.getSkipUrl());
		
		registry.addInterceptor(new LoggerInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns( sessionConfig.getSkipUrl());
		
		registry.addInterceptor(jwtInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns( sessionConfig.getSkipUrl());
		
		//registry.addInterceptor(new LoggerInterceptor());
		//registry.addInterceptor(jwtInterceptor).excludePathPatterns("/auth/login");
		//.excludePathPatterns(sessionConfig.getSkipUrl())
		// .addPathPatterns("/*");
		// .excludePathPatterns(skipUris);
//		.addPathPatterns("/*")
//        .excludePathPatterns("/test/**/")
//        .excludePathPatterns("/users/login"); //로그인 쪽은 예외처리를 한다.
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/*
		 * registry.addResourceHandler("/html/**")
		 * .addResourceLocations("classpath:/html/") .setCachePeriod(20);
		 */
    }


	@Bean
    public FilterRegistrationBean filterRegistrationBean() {
      FilterRegistrationBean bean = new FilterRegistrationBean(new CrossDomainSiteAllowFilter());
      bean.addUrlPatterns("/*");
      // 특정 서블릿들에서만사용
      //bean.addServletRegistrationBeans(new ServletRegistrationBean[] {countryServlet()});
      return bean;
    }
    
    @Bean
    public FilterRegistrationBean encodingFilterBean() {
      FilterRegistrationBean registrationBean = new FilterRegistrationBean();
      CharacterEncodingFilter characterEncodingFilter =new CharacterEncodingFilter();
      characterEncodingFilter.setForceEncoding(true);
      characterEncodingFilter.setEncoding("UTF-8");
      registrationBean.setFilter(characterEncodingFilter);
      return registrationBean;
    }
	
    @Bean
    public ServletListenerRegistrationBean<ServletContextListener> listenerRegistrationBean() {
      ServletListenerRegistrationBean<ServletContextListener> bean = 
          new ServletListenerRegistrationBean();
      bean.setListener(new SgscServletContextListener());
     // bean.setListener(new MykafkaConsumerProperties()); 
      return bean;

    }
	
}

