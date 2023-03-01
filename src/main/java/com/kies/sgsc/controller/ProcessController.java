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
import com.kies.sgsc.service.ProcessService;
/**
 * 생산공정
 * @author leeju
 *
 */
@RestController
@RequestMapping("comm")
public class ProcessController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	ProcessService processService;

	@GetMapping(value = "/process")
	public Map listProcess(@RequestParam Map<String, String> inMap){
		logger.debug("listProcess:"+inMap);
		Map retMap = new HashMap();
		List<Map> list = null;
		try {
			list = processService.listProcess(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}

	@GetMapping(value = "/process/{procs_id}")
	public Map getProcess_procs_id(@PathVariable("procs_id") String procs_id ,@RequestParam Map<String, String> inMap){
		Map retMap = new HashMap();
		try {
			inMap.put("procs_id", procs_id);
			logger.debug("getProcess_procs_id:"+inMap);
			retMap = processService.getProcess_procs_id(inMap);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"단건조회오류",e.toString());
		}
		return retMap;
	}

	@PostMapping(value="/process")
	public Map insertProcess(@RequestBody Map<String, String> inMap) {
		logger.debug("insertProcess:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = processService.insertProcess(inMap);
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

	@PostMapping(value="/allprocess")
	public Map insertAllProcess(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("insertAllProcess:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = processService.insertProcess(inMap);
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

	@PutMapping(value="/process")
	public Map updateProcess(@RequestBody Map<String, String> inMap) {
		logger.debug("updateProcess:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = processService.updateProcess(inMap);
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

	@PutMapping(value="/allprocess")
	public Map updateAllProcess(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("updateAllProcess:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = processService.updateProcess(inMap);
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

	@DeleteMapping(value="/process")
	public Map deleteProcess(@RequestBody Map<String, String> inMap) {
		logger.debug("deleteProcess:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = processService.deleteProcess(inMap);
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

	@DeleteMapping(value="/allprocess")
	public Map deleteAllProcess(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("deleteAllProcess:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = processService.deleteProcess(inMap);
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

	@GetMapping(value = "/pageprocess")
	public Map pageProcess(
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
			List<Map> list =  processService.pageProcess(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", processService.countProcess(searchMap));
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