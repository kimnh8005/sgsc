package com.kies.sgsc.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import org.slf4j.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import com.kies.sgsc.comm.exception.BusinessException;
import com.kies.sgsc.comm.util.Utils;
import com.kies.sgsc.dao.base.UserDAO;
import com.kies.sgsc.comm.exception.*;
import com.kies.sgsc.service.UserDevicesService;

@RestController
@RequestMapping("comm")
public class UserDevicesController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	UserDevicesService userDevicesService;
	
	@Autowired
	UserDAO userDao;
	
	@GetMapping(value = "/userdevices")
	public Map listUserDevices(@RequestParam Map<String, String> inMap){
		logger.debug("listUserDevices:"+inMap);
		Map retMap = new HashMap();
		try {
			List<Map> list = userDevicesService.listUserDevices(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}

	@GetMapping(value = "/userdevices/{user_id}")
	public Map getUserDevices_user_id(@PathVariable("user_id") String user_id ,@RequestParam Map<String, String> inMap){
		Map retMap = new HashMap();
		try {
			inMap.put("user_id", user_id);
			logger.debug("getUserDevices_user_id:"+inMap);
			retMap = userDevicesService.getUserDevices_user_id(inMap);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"단건조회오류",e.toString());
		}
		return retMap;
	}

	@PostMapping(value="/userdevices")
	public Map insertUserDevices(@RequestBody Map<String, String> inMap) {
		logger.debug("insertUserDevices:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = userDevicesService.insertUserDevices(inMap);
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

	@PostMapping(value="/alluserdevices")
	public Map insertAllUserDevices(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("insertAllUserDevices:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = userDevicesService.insertUserDevices(inMap);
				if(resCNt==0) {
					throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
				}
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

	@PutMapping(value="/userdevices")
	public Map updateUserDevices(@RequestBody Map<String, String> inMap) {
		logger.debug("updateUserDevices:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = userDevicesService.updateUserDevices(inMap);
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
	
	@PutMapping(value="/isuserdevicespush")
	public Map updateUserDevicesPushYn(@RequestBody Map<String, String> inMap) {
		logger.debug("updateUserDevices:"+inMap);
		Map retMap = new HashMap();
		try {
			
			String mobile_push_yn = Utils.NVL(inMap.get("mobile_push_yn"));
			String user_id = Utils.NVL(inMap.get("user_id"));
			if("".equals(mobile_push_yn) || "".equals(user_id)) {
				throw new BusinessException(CodeContrants.ERR_PARAMETER,"USERID OR PUSH YN ");
			}
			
			Map deviceMap = new HashMap();
			deviceMap.put("mobile_push_yn",mobile_push_yn);
			deviceMap.put("user_id",user_id);
			
			Map userInfo =userDao.getUser_user_id(deviceMap);
			if(userInfo==null || userInfo.size()==0) {
				throw new BusinessException(CodeContrants.ERR_NOT_USER,"존재하지 않는 사용자");
			}
			
			userInfo =userDevicesService.getUserDevices_user_id(inMap);
			int resCNt = 0;
			if(userInfo==null || userInfo.size()==0) {
				resCNt =userDevicesService.insertUserDevices(deviceMap);
			}else {
				resCNt = userDevicesService.updateUserDevices(deviceMap);
			}
			
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
	

	@PutMapping(value="/alluserdevices")
	public Map updateAllUserDevices(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("updateAllUserDevices:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = userDevicesService.updateUserDevices(inMap);
				if(resCNt==0) {
					throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
				}
			}
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_UPDATE,"다건수정 ",e.toString());
		}
		return retMap;
	}

	@DeleteMapping(value="/userdevices")
	public Map deleteUserDevices(@RequestBody Map<String, String> inMap) {
		logger.debug("deleteUserDevices:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = userDevicesService.deleteUserDevices(inMap);
			if(resCNt==0) {
				throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
			}
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_DELETE,"다건삭제 ",e.toString());
		}
		return retMap;
	}

	@DeleteMapping(value="/alluserdevices")
	public Map deleteAllUserDevices(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("deleteAllUserDevices:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = userDevicesService.deleteUserDevices(inMap);
				if(resCNt==0) {
					throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
				}
			}
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_DELETE,"다건삭제 ",e.toString());
		}
		return retMap;
	}

	@GetMapping(value = "/pageuserdevices")
	public Map pageUserDevices(
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
			List<Map> list =  userDevicesService.pageUserDevices(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", userDevicesService.countUserDevices(searchMap));
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