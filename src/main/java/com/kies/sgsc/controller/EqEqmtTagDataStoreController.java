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
import com.kies.sgsc.service.EqEqmtTagDataStoreService;

/**
 * 게이트웨이_측정장비_데이터적재
 * @author leeju
 */
@RestController
@RequestMapping("")
public class EqEqmtTagDataStoreController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	EqEqmtTagDataStoreService eqEqmtTagDataStoreService;

	@GetMapping(value = "/eqeqmttagdatastore")
	public Map listEqEqmtTagDataStore(@RequestParam Map<String, String> inMap){
		logger.debug("listEqEqmtTagDataStore:"+inMap);
		Map retMap = new HashMap();
		List<Map> list = null;
		try {
			list = eqEqmtTagDataStoreService.listEqEqmtTagDataStore(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}

	@GetMapping(value = "/eqeqmttagdatastore/{rise_time}")
	public Map listEqEqmtTagDataStore_rise_time(@PathVariable("rise_time") String rise_time ,@RequestParam Map<String, String> inMap){
		Map retMap = new HashMap();
		try {
			inMap.put("rise_time", rise_time);
			logger.debug("getEqEqmtTagDataStore_rise_time:"+inMap);
			List<Map> list = eqEqmtTagDataStoreService.listEqEqmtTagDataStore_rise_time(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"단건조회오류",e.toString());
		}
		return retMap;
	}

	@GetMapping(value = "/eqeqmttagdatastore/{rise_time}/{meeq_id}")
	public Map getEqEqmtTagDataStore_meeq_id(@PathVariable("rise_time") String rise_time ,@PathVariable("meeq_id") String meeq_id ,@RequestParam Map<String, String> inMap){
		Map retMap = new HashMap();
		try {
			inMap.put("rise_time", rise_time);
			inMap.put("meeq_id", meeq_id);
			logger.debug("getEqEqmtTagDataStore_meeq_id:"+inMap);
			retMap = eqEqmtTagDataStoreService.getEqEqmtTagDataStore_meeq_id(inMap);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"단건조회오류",e.toString());
		}
		return retMap;
	}

	@PostMapping(value="/eqeqmttagdatastore")
	public Map insertEqEqmtTagDataStore(@RequestBody Map<String, String> inMap) {
		logger.debug("insertEqEqmtTagDataStore:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = eqEqmtTagDataStoreService.insertEqEqmtTagDataStore(inMap);
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

	@PostMapping(value="/alleqeqmttagdatastore")
	public Map insertAllEqEqmtTagDataStore(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("insertAllEqEqmtTagDataStore:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = eqEqmtTagDataStoreService.insertEqEqmtTagDataStore(inMap);
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

	@PutMapping(value="/eqeqmttagdatastore")
	public Map updateEqEqmtTagDataStore(@RequestBody Map<String, String> inMap) {
		logger.debug("updateEqEqmtTagDataStore:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = eqEqmtTagDataStoreService.updateEqEqmtTagDataStore(inMap);
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

	@PutMapping(value="/alleqeqmttagdatastore")
	public Map updateAllEqEqmtTagDataStore(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("updateAllEqEqmtTagDataStore:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = eqEqmtTagDataStoreService.updateEqEqmtTagDataStore(inMap);
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

	@DeleteMapping(value="/eqeqmttagdatastore")
	public Map deleteEqEqmtTagDataStore(@RequestBody Map<String, String> inMap) {
		logger.debug("deleteEqEqmtTagDataStore:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = eqEqmtTagDataStoreService.deleteEqEqmtTagDataStore(inMap);
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

	@DeleteMapping(value="/alleqeqmttagdatastore")
	public Map deleteAllEqEqmtTagDataStore(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("deleteAllEqEqmtTagDataStore:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = eqEqmtTagDataStoreService.deleteEqEqmtTagDataStore(inMap);
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

	@GetMapping(value = "/pageeqeqmttagdatastore")
	public Map pageEqEqmtTagDataStore(
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
			List<Map> list =  eqEqmtTagDataStoreService.pageEqEqmtTagDataStore(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", eqEqmtTagDataStoreService.countEqEqmtTagDataStore(searchMap));
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