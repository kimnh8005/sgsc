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
import com.kies.sgsc.service.EmeryContactService;

/**
 * 비상연락망
 * @author leeju
 */
@RestController
@RequestMapping("comm")
public class EmeryContactController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	EmeryContactService emeryContactService;

	@GetMapping(value = "/emerycontact")
	public Map listEmeryContact(@RequestParam Map<String, String> inMap){
		logger.debug("listEmeryContact:"+inMap);
		Map retMap = new HashMap();
		List<Map> list = null;
		try {
			list = emeryContactService.listEmeryContact(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}

	@GetMapping(value = "/emerycontact/{emct_sid}")
	public Map getEmeryContact_emct_sid(@PathVariable("emct_sid") String emct_sid ,@RequestParam Map<String, String> inMap){
		Map retMap = new HashMap();
		try {
			inMap.put("emct_sid", emct_sid);
			logger.debug("getEmeryContact_emct_sid:"+inMap);
			retMap = emeryContactService.getEmeryContact_emct_sid(inMap);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"단건조회오류",e.toString());
		}
		return retMap;
	}

	@PostMapping(value="/emerycontact")
	public Map insertEmeryContact(@RequestBody Map<String, String> inMap) {
		logger.debug("insertEmeryContact:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = emeryContactService.insertEmeryContact(inMap);
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

	@PostMapping(value="/allemerycontact")
	public Map insertAllEmeryContact(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("insertAllEmeryContact:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = emeryContactService.insertEmeryContact(inMap);
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

	@PutMapping(value="/emerycontact")
	public Map updateEmeryContact(@RequestBody Map<String, String> inMap) {
		logger.debug("updateEmeryContact:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = emeryContactService.updateEmeryContact(inMap);
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

	@PutMapping(value="/allemerycontact")
	public Map updateAllEmeryContact(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("updateAllEmeryContact:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = emeryContactService.updateEmeryContact(inMap);
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

	@DeleteMapping(value="/emerycontact")
	public Map deleteEmeryContact(@RequestBody Map<String, String> inMap) {
		logger.debug("deleteEmeryContact:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = emeryContactService.deleteEmeryContact(inMap);
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

	@DeleteMapping(value="/allemerycontact")
	public Map deleteAllEmeryContact(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("deleteAllEmeryContact:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = emeryContactService.deleteEmeryContact(inMap);
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

	@GetMapping(value = "/pageemerycontact")
	public Map pageEmeryContact(
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
			List<Map> list =  emeryContactService.pageEmeryContact(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", emeryContactService.countEmeryContact(searchMap));
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