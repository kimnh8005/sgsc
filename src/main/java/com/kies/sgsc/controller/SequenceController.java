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
import com.kies.sgsc.service.SequenceService;

@RestController
@RequestMapping("comm")
public class SequenceController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	SequenceService sequenceService;

	@GetMapping(value = "/sequence")
	public List<Map> listSequence(@RequestParam Map<String, String> inMap){
		logger.debug("listSequence:"+inMap);
		List<Map> list = null;
		try {
			list = sequenceService.listSequence(inMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return list;
	}

	@GetMapping(value = "/sequence/{seq_id}")
	public Map getSequence_seq_id(@PathVariable("seq_id") String seq_id ,@RequestParam Map<String, String> inMap){
		Map retMap = null;
		try {
			inMap.put("seq_id", seq_id);
			logger.debug("getSequence_seq_id:"+inMap);
			retMap = sequenceService.getSequence_seq_id(inMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"단건조회오류",e.toString());
		}
		return retMap;
	}

	@PostMapping(value="/sequence")
	public void insertSequence(@RequestBody Map<String, String> inMap) {
		logger.debug("insertSequence:"+inMap);
		try {
			int resCNt = sequenceService.insertSequence(inMap);
			if(resCNt==0) {
				throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
			}
		}catch(DuplicateKeyException e) {
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_DUPLICATE,"이미 존재하는 항목",e.toString());
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_INSERT,"단건등록 ",e.toString());
		}
	}

	@PostMapping(value="/allsequence")
	public void insertAllSequence(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("insertAllSequence:"+inMapList);
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = sequenceService.insertSequence(inMap);
				if(resCNt==0) {
					throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
				}
			}
		}catch(DuplicateKeyException e) {
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_DUPLICATE,"이미 존재하는 항목",e.toString());
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_INSERT,"단건등록 ",e.toString());
		}
	}

	@PutMapping(value="/sequence")
	public void updateSequence(@RequestBody Map<String, String> inMap) {
		logger.debug("updateSequence:"+inMap);
		try {
			int resCNt = sequenceService.updateSequence(inMap);
			if(resCNt==0) {
				throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
			}
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_UPDATE,"단건수정 ",e.toString());
		}
	}

	@PutMapping(value="/allsequence")
	public void updateAllSequence(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("updateAllSequence:"+inMapList);
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = sequenceService.updateSequence(inMap);
				if(resCNt==0) {
					throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
				}
			}
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_UPDATE,"다건수정 ",e.toString());
		}
	}

	@DeleteMapping(value="/sequence")
	public void deleteSequence(@RequestBody Map<String, String> inMap) {
		logger.debug("deleteSequence:"+inMap);
		try {
			int resCNt = sequenceService.deleteSequence(inMap);
			if(resCNt==0) {
				throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
			}
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_DELETE,"다건삭제 ",e.toString());
		}
	}

	@DeleteMapping(value="/allsequence")
	public void deleteAllSequence(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("deleteAllSequence:"+inMapList);
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = sequenceService.deleteSequence(inMap);
				if(resCNt==0) {
					throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
				}
			}
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_DELETE,"다건삭제 ",e.toString());
		}
	}

	@GetMapping(value = "/pagesequence")
	public Map pageSequence(
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
			List<Map> list =  sequenceService.pageSequence(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", sequenceService.countSequence(searchMap));
			restulMap.put("indexlist", "하단 인덱스 정보.");
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT," 페이징 조회 오류",e.toString());
		}
		return restulMap;
	}

}