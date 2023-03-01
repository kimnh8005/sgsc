package com.kies.sgsc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kies.sgsc.comm.exception.BusinessException;
import com.kies.sgsc.comm.exception.CodeContrants;
import com.kies.sgsc.comm.util.Utils;
import com.kies.sgsc.service.UserService;
/**
 * 사용자 관리
 * @author leeju
 *
 */
@RestController
@RequestMapping("comm")
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	UserService userService;

	@GetMapping(value = "/user")
	public Map listUser(@RequestParam Map<String, String> inMap){
		logger.debug("listUser:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			list = userService.listUser(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}

	
	@GetMapping(value = "/user/{user_id}")
	public Map getUser_user_id(@PathVariable("user_id") String user_id ,@RequestParam Map<String, String> inMap){
		Map retMap = null;
		try {
			inMap.put("user_id", user_id);
			logger.debug("getUser_user_id:"+inMap);
			retMap = userService.getUser_user_id(inMap);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"단건조회오류",e.toString());
		}
		return retMap;
	}

	@PostMapping(value="/user")
	public Map insertUser(@RequestBody Map<String, String> inMap) {
		logger.debug("insertUser:"+inMap);
		Map retMap = new HashMap();
		try {
			String password = inMap.get("password");
			if(Utils.isNone(password)) {
				throw new BusinessException(CodeContrants.ERR_PARAMETER,"패스워드 미존재");
			}
			inMap.put("password", Utils.testMD5(password));
			
			int resCNt = userService.insertUser(inMap);
			if(resCNt==0) {
				throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
			}
			Utils.addSuccessMsg(retMap);
		}catch(DuplicateKeyException e) {
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_DUPLICATE,"이미 존재하는 항목",e.toString());
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_INSERT,"단건등록 ",e.toString());
		}
		return retMap;
	}

	@PutMapping(value="/user")
	public Map updateUser(@RequestBody Map<String, String> inMap) {
		logger.debug("updateUser:"+inMap);
		Map retMap = new HashMap();
		try {
		
			inMap.put("password", "");
			int resCNt = userService.updateUser(inMap);
			if(resCNt==0) {
				throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
			}
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_UPDATE,"단건수정 ",e.toString());
		}
		return retMap;
	}
	
	
	
	@PutMapping(value="/usertoken")
	public Map updateUsertToken(@RequestBody Map<String, String> inMap) {
		logger.debug("updateUsertToken:"+inMap);
		Map retMap = new HashMap();
		try {
			String user_id = inMap.get("user_id");
			String token = inMap.get("token"); 
			if(Utils.isNone(user_id)) {
				throw new BusinessException(CodeContrants.ERR_PARAMETER,"사용자아이디 미존재");
			}
			if(Utils.isNone(token)) {
				throw new BusinessException(CodeContrants.ERR_PARAMETER,"token 미존재");
			}
			
			int resCNt = userService.updateUsertToken(inMap);
			if(resCNt==0) {
				throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
			}
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_UPDATE,"단건수정 ",e.toString());
		}
		return retMap;
	}
	
	
	
	
	@PutMapping(value="/changepassword")
	public Map updateUserPass(@RequestBody Map<String, String> inMap) {
		logger.debug("updateUser:"+inMap);
		Map retMap = new HashMap();
		try {

			String user_id = inMap.get("user_id");
			String password = inMap.get("password");
			String cgpassword = inMap.get("changepassword");
			if(Utils.isNone(user_id)) {
				throw new BusinessException(CodeContrants.ERR_PARAMETER,"사용자아이디 미존재");
			}
			if(Utils.isNone(password)) {
				throw new BusinessException(CodeContrants.ERR_PARAMETER,"패스워드 미존재");
			}
			if(Utils.isNone(cgpassword)) {
				throw new BusinessException(CodeContrants.ERR_PARAMETER,"변경 패스워드 미존재");
			}
			
			String scnPassword = Utils.testMD5(password);
			
			logger.debug("getUser_user_id:"+inMap);
			Map userMap = userService.getUser_user_id(inMap);
			if(userMap==null  || userMap.size()==0) {
				throw new BusinessException(CodeContrants.ERR_NOT_USER,"사용자 미존재");
			}
			String dbPassword = (String)userMap.get("password");
			
			logger.debug("dbPassword:"+dbPassword+" ,scnPassword:"+scnPassword);
			if(!dbPassword.equals(scnPassword)) {
				throw new BusinessException(CodeContrants.ERR_PASSWORD,"비밀번호 오류");
			}
			
			inMap.put("password", Utils.testMD5(cgpassword));
			int resCNt = userService.updateUserPassword(inMap);
			if(resCNt==0) {
				throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
			}
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_UPDATE,"단건수정 ",e.toString());
		}
		return retMap;
	}
	

	@DeleteMapping(value="/user")
	public Map deleteUser(@RequestBody Map<String, String> inMap) {
		logger.debug("deleteUser:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = userService.deleteUser(inMap);
			if(resCNt==0) {
				throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
			}
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_DELETE,"단건수정 ",e.toString());
		}
		return retMap;
	}

	@GetMapping(value = "/pageuser")
	public Map pageUser(
		@RequestParam("pagesize") String pagesize,
		@RequestParam("pageindex") String pageindex,
		@RequestParam Map<String, String> param) {

		logger.debug("pageUser pagesize:"+pagesize);
		logger.debug("pageUser pageindex:"+pageindex);
		Map contion = new HashMap();
		Map restulMap = new HashMap();
		int ipagesize = param.get("pagesize")==null?0:Integer.parseInt(pagesize);
		int ipageindex = param.get("pageindex")==null?0:Integer.parseInt(pageindex);
		Map inMap = new HashMap();
		Map searchMap= new HashMap();
		searchMap.putAll(param);
		searchMap.put("startRow", String.valueOf((ipageindex-1) * ipagesize));
		searchMap.put("endRow", ipagesize);//String.valueOf(ipageindex * ipagesize) );
		logger.debug("searchMap param:"+searchMap);
		try {
			List<Map> list =  userService.pageUser(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", userService.countUser(searchMap));
			restulMap.put("pagesize", pagesize);
			restulMap.put("pageindex", pageindex);
			Utils.addSuccessMsg(restulMap);
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT," 페이징 조회 오류",e.toString());
		}
		return restulMap;
	}

}