package com.kies.sgsc.servlet.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.kies.sgsc.comm.config.SessionConfig;
import com.kies.sgsc.comm.exception.BusinessException;
import com.kies.sgsc.comm.exception.CodeContrants;
import com.kies.sgsc.comm.util.Utils;
import com.kies.sgsc.servlet.KiesSessionDto;
import com.kies.sgsc.servlet.KiesSessionManager;
import com.kies.sgsc.servlet.SessionUtil;

@Component
public class JwtInterceptor implements HandlerInterceptor{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	public static final String HEADER_AUTH = "Authorization";
	
	@Autowired
	private KiesSessionManager kiesSessionManager;
	
	@Autowired
	private SessionConfig sessionConfig;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		logger.debug("request.getRequestURI():"+request.getRequestURI());
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			logger.debug("if request options method is options, return true");
			//Access-Control-Allow-Origin: *
			return true; 
		}
		
		String [] url = request.getRequestURI().split("/");
		if(url[2].equalsIgnoreCase("gw")) {
			return true; 
		}
					
		logger.debug("############# JwtInterceptor실행 ####################");
		String token = Utils.NVL(request.getHeader(HEADER_AUTH), request.getHeader(HEADER_AUTH.toLowerCase()));
		
		logger.debug("JwtInterceptor token:"+token +" sessionConfig.getCheck():"+sessionConfig.getCheck());
	
		if(!sessionConfig.getCheck()) {
			 if(token==null) token ="dev";
		}
		
		if("dev".equals(token)) { 
			 //테스트용 
			 KiesSessionDto sessionDto = new KiesSessionDto(); 
			 sessionDto.setToken(token); 
			 Map userinfo = new HashMap();
			 userinfo.put("user_id", "dev"); 
			 userinfo.put("user_nm", "테스트");
			 sessionDto.setData(userinfo);
			 SessionUtil.setUserSession(sessionDto);
		 return true;
	 }
		
		if(token != null && kiesSessionManager.isUsable(token)){
			kiesSessionManager.globalToLocalSession(token);
			return true;
		}else{
			throw new BusinessException(CodeContrants.ERR_NOT_LOGIN,"로그인되지 않은 사용자입니다. 다시 로그인을 해주세요");
		}
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("종료인터셉터");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
}
