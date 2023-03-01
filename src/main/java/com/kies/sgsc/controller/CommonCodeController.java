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
import com.kies.sgsc.service.CommonCodeService;

/**
 * 공통코드 관리
 * @author leeju
 *
 */
@RestController
@RequestMapping("comm")
public class CommonCodeController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	CommonCodeService commonCodeService;

	@GetMapping(value = "/code")
	public Map listCommonCode(@RequestParam Map<String, String> inMap){
		logger.debug("listCommonCode:"+inMap);
		Map retMap = new HashMap();
		List<Map> list = null;
		try {
			list = commonCodeService.listCommonCode(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}

	@GetMapping(value = "/code/{cd_grp_id}")
	public Map listCommonCode_cd_grp_id(@PathVariable("cd_grp_id") String cd_grp_id ,@RequestParam Map<String, String> inMap){
		Map retMap = new HashMap();
		List<Map> list = null;
		try {
			inMap.put("cd_grp_id", cd_grp_id);
			logger.debug("getCommonCode_cd_grp_id:"+inMap);
			list = commonCodeService.listCommonCode_cd_grp_id(inMap);
			retMap.put("list",list);
			
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"단건조회오류",e.toString());
		}
		return retMap;
	}

	@GetMapping(value = "/code/{cd_grp_id}/{cd_id}")
	public Map getCommonCode_cd_id(@PathVariable("cd_grp_id") String cd_grp_id ,@PathVariable("cd_id") String cd_id ,@RequestParam Map<String, String> inMap){
		Map retMap = new HashMap();
		try {
			inMap.put("cd_grp_id", cd_grp_id);
			inMap.put("cd_id", cd_id);
			logger.debug("getCommonCode_cd_id:"+inMap);
			retMap = commonCodeService.getCommonCode_cd_id(inMap);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"단건조회오류",e.toString());
		}
		return retMap;
	}

	@PostMapping(value="/code")
	public Map insertCommonCode(@RequestBody Map<String, String> inMap) {
		logger.debug("insertCommonCode:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = commonCodeService.insertCommonCode(inMap);
			if(resCNt==0) {
				throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
			}
			Utils.addSuccessMsg(retMap);
		}catch(DuplicateKeyException e) {
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_DUPLICATE,"존재하는 메뉴",e.toString());
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_INSERT,"단건등록 ",e.toString());
		}
		return retMap;
	}
	
	@PostMapping(value="/allcode")
	public Map insertAllCommonCode(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("insertAllCommonCode:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = commonCodeService.insertCommonCode(inMap);
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
	

	@PutMapping(value="/code")
	public Map updateCommonCode(@RequestBody Map<String, String> inMap) {
		logger.debug("updateCommonCode:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = commonCodeService.updateCommonCode(inMap);
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

	@DeleteMapping(value="/code")
	public Map deleteCommonCode(@RequestBody Map<String, String> inMap) {
		logger.debug("deleteCommonCode:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = commonCodeService.deleteCommonCode(inMap);
			if(resCNt==0) {
				throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
			}
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_DELETE,"단건삭제 ",e.toString());
		}
		return retMap;
	}

	@GetMapping(value = "/pagecode")
	public Map pageCommonCode(
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
			List<Map> list =  commonCodeService.pageCommonCode(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", commonCodeService.countCommonCode(searchMap));
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