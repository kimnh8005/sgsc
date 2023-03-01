package com.kies.sgsc.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.kies.sgsc.service.HumanErrPrpsService;
/**
 * 휴먼에러제안
 * @author leeju
 *
 */
@RestController
@RequestMapping("work")
public class HumanErrPrpsController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	HumanErrPrpsService humanErrPrpsService;

	@GetMapping(value = "/humanerrprps")
	public Map listHumanErrPrps(@RequestParam Map<String, String> inMap){
		logger.debug("listHumanErrPrps:"+inMap);
		Map retMap = new HashMap();
		List<Map> list = null;
		try {
			list = humanErrPrpsService.listHumanErrPrps(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	
	@GetMapping(value = "/day7rate")
	//public List<Map> get7DayRiskRate(@PathVariable("props_ymd") String props_ymd ,@RequestParam Map<String, String> inMap){
	public Map get7DayRiskRate(@RequestParam Map<String, String> inMap){
		logger.debug("listHumanErrPrps:"+inMap);
		Map retMap = new HashMap();
		List<Map> list = null;
		try {
			
			inMap.put("props_ymd", new  SimpleDateFormat("yyyyMMdd").format(new Date()));
			list = humanErrPrpsService.get7DayRiskRate(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"일주일작업율조회 오류",e.toString());
		}
		return retMap;
	}
	
	@GetMapping(value = "/dayrate")
	public Map getDayRiskRate(@RequestParam Map<String, String> inMap){
		Map retMap = null;
		try {
			//이부분은 가져와야 한다.
			inMap.put("err_rate", "36.35"); //위험도 기준 위험율
			inMap.put("props_ymd", new  SimpleDateFormat("yyyyMMdd").format(new Date()));
			//inMap.put("props_ymd", props_ymd);
			logger.debug("getHumanErrPrps_props_sid:"+inMap);
			retMap = humanErrPrpsService.getDayRiskRate(inMap);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"금일작업오류율 조회 오류",e.toString());
		}
		return retMap;
	}
	

	@GetMapping(value = "/humanerrprps/{props_sid}")
	public Map getHumanErrPrps_props_sid(@PathVariable("props_sid") String props_sid ,@RequestParam Map<String, String> inMap){
		Map retMap = new HashMap();
		try {
			inMap.put("props_sid", props_sid);
			logger.debug("getHumanErrPrps_props_sid:"+inMap);
			retMap = humanErrPrpsService.getHumanErrPrps_props_sid(inMap);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"단건조회오류",e.toString());
		}
		return retMap;
	}

	@PostMapping(value="/humanerrprps")
	public Map insertHumanErrPrps(@RequestBody Map<String, String> inMap) {
		logger.debug("insertHumanErrPrps:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = humanErrPrpsService.insertHumanErrPrps(inMap);
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

	@PostMapping(value="/allhumanerrprps")
	public Map insertAllHumanErrPrps(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("insertAllHumanErrPrps:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = humanErrPrpsService.insertHumanErrPrps(inMap);
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

	@PutMapping(value="/humanerrprps")
	public Map updateHumanErrPrps(@RequestBody Map<String, String> inMap) {
		logger.debug("updateHumanErrPrps:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = humanErrPrpsService.updateHumanErrPrps(inMap);
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

	@PutMapping(value="/allhumanerrprps")
	public Map updateAllHumanErrPrps(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("updateAllHumanErrPrps:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = humanErrPrpsService.updateHumanErrPrps(inMap);
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

	@DeleteMapping(value="/humanerrprps")
	public Map deleteHumanErrPrps(@RequestBody Map<String, String> inMap) {
		logger.debug("deleteHumanErrPrps:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = humanErrPrpsService.deleteHumanErrPrps(inMap);
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

	@DeleteMapping(value="/allhumanerrprps")
	public Map deleteAllHumanErrPrps(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("deleteAllHumanErrPrps:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = humanErrPrpsService.deleteHumanErrPrps(inMap);
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
	
	//휴먼에러제안 통합완료건수
	@GetMapping(value = "/app/humanerrprps")
	public Map listHumanErrPrpsApp(@RequestParam Map<String, String> inMap){
		Map retMap = new HashMap();
		List<Map> list = new ArrayList();
		try {
			logger.debug("listHumanErrPrpsApp:"+inMap);
			list = humanErrPrpsService.listHumanErrPrpsApp(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"단건조회오류",e.toString());
		}
		return retMap;
	}
	
	@GetMapping(value = "/app/pagehumanerrprps")
	public Map pageHumanErrPrpsApp(
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
			List<Map> list =  humanErrPrpsService.pageHumanErrPrpsApp(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", humanErrPrpsService.countHumanErrPrpsApp(searchMap));
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
		

	@GetMapping(value = "/pagehumanerrprps")
	public Map pageHumanErrPrps(
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
			List<Map> list =  humanErrPrpsService.pageHumanErrPrps(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", humanErrPrpsService.countHumanErrPrps(searchMap));
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