package com.kies.sgsc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kies.sgsc.comm.exception.BusinessException;
import com.kies.sgsc.comm.exception.CodeContrants;
import com.kies.sgsc.comm.util.Utils;
import com.kies.sgsc.service.AuthService;
import com.kies.sgsc.service.UserDevicesService;
import com.kies.sgsc.service.UserService;
import com.kies.sgsc.servlet.KiesSessionManager;
import com.kies.sgsc.servlet.interceptor.JwtInterceptor;
/**
 * 로그인 처리
 *
 */
@RestController
@RequestMapping("sys")
public class LoginController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	AuthService authService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserDevicesService userDevicesService;
	
	@Autowired
    private KiesSessionManager kiesSessionManager;

	@PostMapping(value="/login")
    public Map login(@RequestBody Map<String, String> inMap, HttpServletRequest request, HttpServletResponse response){
        
		//토큰정리
		//kiesSessionManager.removeSessionObject();
				
		logger.info("inMap:"+inMap);
    	String userid = inMap.get("user_id");    	
    	String device = Utils.NVL(inMap.get("device"),"W"); //접속 장비
    	String password = (String)inMap.get("password");
    	
    	if(password==null || password.length()==0) {
        	throw new BusinessException(CodeContrants.ERR_PARAMETER,"패스워드 미입력"); 
        }
    	
        Map loginMember = userService.getUser_user_id(inMap);
        if(loginMember==null || loginMember.size()==0) {
        	throw new BusinessException(CodeContrants.ERR_NOT_USER,"미존재 사용자"); 
        }
        
        if( loginMember.get("auth_sid") == "50") { //일반사용자
        	throw new BusinessException(CodeContrants.ERR_NOAUTH_USR,"권한이 없는 계정"); 
        }
        
        loginMember.put("device",device);
        
        String dbpassword = (String)loginMember.get("password");
        String scnpassword = Utils.testMD5((String)inMap.get("password"));
        
        logger.debug("dbpassword:"+dbpassword+" ,scnpassword:"+scnpassword);
        
        if(!dbpassword.equals(scnpassword)) {
        	throw new BusinessException(CodeContrants.ERR_PASSWORD,"비밀번호 오류");  
        }
        
        //토큰이 이미 존재 하는경우,
        String token = request.getHeader(JwtInterceptor.HEADER_AUTH);
        if(token != null && kiesSessionManager.isUsable(token)){
        	response.setHeader(JwtInterceptor.HEADER_AUTH, token); 
        	throw new BusinessException(CodeContrants.ERR_LOGIN_USR,"이미 로그인한 사용자"); 
        	//return loginMember;
        }
        
        //토큰생성.
        token = kiesSessionManager.login(loginMember);
        response.setHeader(JwtInterceptor.HEADER_AUTH, token);  //중요."Authorization",
        loginMember.put(JwtInterceptor.HEADER_AUTH,token);
        
        //디바이스정보
        
        if(!"".equals(Utils.NVL(inMap.get("mobile_token"))) ){
        	 Map deviceMap = userDevicesService.getUserDevices_user_id(inMap);
        	 if(deviceMap!=null && deviceMap.size()>0) {
        		 userDevicesService.updateUserDevices(inMap);
             }else {
             	userDevicesService.insertUserDevices(inMap);
             }
        }
        
        Map userDeviceMap =userDevicesService.getUserDevices_user_id(inMap);
        loginMember.put("mobile_push_yn",Utils.NVL(userDeviceMap.get("mobile_push_yn"),"N"));
        
        Utils.addSuccessMsg(loginMember);
        return loginMember;
    }
	
	@GetMapping(value="/test")
    public Map test(@RequestParam Map<String, String> inMap){
		Map result = new HashMap();
		logger.debug("test _ jwtService.getCliams():"+kiesSessionManager.getCliams());
		result.put("testres","결과");
        return result;
    }
	
	@PostMapping(value="/logout")
    public Map logout(@RequestBody Map<String, String> inMap, HttpServletResponse response){
		Map retMap = new HashMap();
		kiesSessionManager.logout();
		//kiesSessionManager.removeSessionObject();
		Utils.addSuccessMsg(retMap);
		return retMap;
    }
	
	

}