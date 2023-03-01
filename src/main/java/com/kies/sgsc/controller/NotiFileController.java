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
import com.kies.sgsc.service.NotiFileService;

@RestController
@RequestMapping("")
public class NotiFileController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	NotiFileService notiFileService;

	@GetMapping(value = "/notifile")
	public List<Map> listNotiFile(@RequestParam Map<String, String> inMap){
		logger.debug("listNotiFile:"+inMap);
		List<Map> list = null;
		try {
			list = notiFileService.listNotiFile(inMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return list;
	}

	@GetMapping(value = "/notifile/{file_seq}")
	public List<Map> listNotiFile_file_seq(@PathVariable("file_seq") String file_seq ,@RequestParam Map<String, String> inMap){
		List<Map> retMap = null;
		try {
			inMap.put("file_seq", file_seq);
			logger.debug("getNotiFile_file_seq:"+inMap);
			retMap = notiFileService.listNotiFile_file_seq(inMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"단건조회오류",e.toString());
		}
		return retMap;
	}

	@GetMapping(value = "/notifile/{file_seq}/{noti_id}")
	public Map getNotiFile_noti_id(@PathVariable("file_seq") String file_seq ,@PathVariable("noti_id") String noti_id ,@RequestParam Map<String, String> inMap){
		Map retMap = null;
		try {
			inMap.put("file_seq", file_seq);
			inMap.put("noti_id", noti_id);
			logger.debug("getNotiFile_noti_id:"+inMap);
			retMap = notiFileService.getNotiFile_noti_id(inMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"단건조회오류",e.toString());
		}
		return retMap;
	}

	@PostMapping(value="/notifile")
	public void insertNotiFile(@RequestBody Map<String, String> inMap) {
		logger.debug("insertNotiFile:"+inMap);
		try {
			int resCNt = notiFileService.insertNotiFile(inMap);
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

	@PostMapping(value="/allnotifile")
	public void insertAllNotiFile(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("insertAllNotiFile:"+inMapList);
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = notiFileService.insertNotiFile(inMap);
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

	@PutMapping(value="/notifile")
	public void updateNotiFile(@RequestBody Map<String, String> inMap) {
		logger.debug("updateNotiFile:"+inMap);
		try {
			int resCNt = notiFileService.updateNotiFile(inMap);
			if(resCNt==0) {
				throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
			}
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_UPDATE,"단건수정 ",e.toString());
		}
	}

	@PutMapping(value="/allnotifile")
	public void updateAllNotiFile(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("updateAllNotiFile:"+inMapList);
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = notiFileService.updateNotiFile(inMap);
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

	@DeleteMapping(value="/notifile")
	public void deleteNotiFile(@RequestBody Map<String, String> inMap) {
		logger.debug("deleteNotiFile:"+inMap);
		try {
			int resCNt = notiFileService.deleteNotiFile(inMap);
			if(resCNt==0) {
				throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
			}
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_DELETE,"다건삭제 ",e.toString());
		}
	}

	@DeleteMapping(value="/allnotifile")
	public void deleteAllNotiFile(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("deleteAllNotiFile:"+inMapList);
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = notiFileService.deleteNotiFile(inMap);
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

	@GetMapping(value = "/pagenotifile")
	public Map pageNotiFile(
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
			List<Map> list =  notiFileService.pageNotiFile(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", notiFileService.countNotiFile(searchMap));
			restulMap.put("indexlist", "하단 인덱스 정보.");
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT," 페이징 조회 오류",e.toString());
		}
		return restulMap;
	}

}