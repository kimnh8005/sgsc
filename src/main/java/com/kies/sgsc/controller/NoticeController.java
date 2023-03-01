package com.kies.sgsc.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kies.sgsc.comm.config.FileConfig;
import com.kies.sgsc.comm.exception.BusinessException;
import com.kies.sgsc.comm.exception.CodeContrants;
import com.kies.sgsc.comm.util.Utils;
import com.kies.sgsc.service.NoticeService;

/**
 * 공지 사항
 * @author leeju
 */
@RestController
@RequestMapping("comm") 
public class NoticeController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	NoticeService noticeService;
	
	@Autowired
	FileConfig fileConfig;
	
	 /**
	 *공지사항
	 */
	@GetMapping(value = "/notice")
	public Map listNotice(@RequestParam Map<String, String> inMap){
		logger.debug("listNotice:"+inMap);
		Map retMap = new HashMap();
		List<Map> cities = null;
		try {
			cities = noticeService.listNotice(inMap);
			retMap.put("list",cities);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"공지사항 리스트 조회오류",e.toString());
		}
		return retMap;
	}
	
	
	/**
	 * 공지사항 단건조회
	 */
	@GetMapping(value = "/notice/{noti_id}")
	public Map getNotice(@PathVariable("noti_id") String noti_id,@RequestParam Map<String, String> inMap, HttpServletRequest request){
		Map returnMap = new HashMap();
		Map cities = null;
		try {
			inMap.put("noti_sid", noti_id);
			logger.debug("getNotice:"+inMap);
			
			cities = noticeService.getNotice_noti_sid(inMap);
			logger.debug("cities:"+cities);
			
			String serverIp = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
			
			if(cities!=null&& cities.size()>0) { 
				cities.put("downloadurl", serverIp+"/sgsc/comm/notidown/"+cities.get("noti_sid"));
				
				//getPrevNotice_noti_sid
				returnMap.put("prev", noticeService.getPrevNotice_noti_sid(inMap));
				returnMap.put("current", cities);
				returnMap.put("next", noticeService.getNextNotice_noti_sid(inMap));
			}
			Utils.addSuccessMsg(returnMap);
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"공지사항 리스트 조회오류",e.toString());
		}
		return returnMap;
	}
	
	/**
	 * 공지사항 등록
	 */
	@PostMapping(value="/notice")
    public Map insertNotice( MultipartHttpServletRequest request ) {

		String noti_type_cd = Utils.NVL(request.getParameter("noti_type_cd"));
 		String noti_title = Utils.NVL(request.getParameter("noti_title"));
 		String noti_cnt = Utils.NVL(request.getParameter("noti_cnt"));
 		//String fle_seq = Utils.NVL(request.getParameter("fle_seq"),"0");
 		
 		Map retMap = new HashMap();
 		List<File> execList = new ArrayList<File>();
 		try {
	 		Path pathfile = Paths.get(fileConfig.getUploadDir());
		    if(!Files.exists(pathfile)) Files.createDirectories(pathfile);
		    
		    Map inMap = new HashMap();
		    inMap.put("noti_id", noticeService.getNoticeKey());
		    inMap.put("noti_type_cd", noti_type_cd);
		    inMap.put("noti_title", noti_title);
		    inMap.put("noti_cnt", noti_cnt);
		    
		    Iterator<String> itr = request.getFileNames();
		    logger.debug("itr:"+itr);
			if(itr.hasNext()) {

				List<MultipartFile> mpfs = request.getFiles(itr.next());				
				for(int i=0;i<mpfs.size();i++) {
					String img_url =Utils.makeImgKey();
					String real_img_url = fileConfig.getUploadDir()+"/"+img_url;
					String img_nm = mpfs.get(i).getOriginalFilename();
					inMap.put("fle_seq",i);					
					inMap.put("file_nm",img_nm);
					inMap.put("file_phy_nm",img_url);
					
					File file1 = new File(real_img_url); 
					execList.add(file1);
					mpfs.get(i).transferTo(file1);
				}	
			}
			logger.debug("inMap:"+inMap);
		    int resCNt = noticeService.insertNotice(inMap);
		   
 		
    		if(resCNt==0) {
    			throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
    		}
    		
    		Utils.addSuccessMsg(retMap);
    	}catch(DuplicateKeyException e) {
    		execList.stream().forEach( file1->{
    			if(file1.exists()) file1.delete();
    		});
    		e.printStackTrace();
    		throw new BusinessException(CodeContrants.ERR_DUPLICATE,"공지사항 중복 메뉴",e.toString());
    	}catch(Throwable e1) {
    		execList.stream().forEach( file1->{
    			if(file1.exists()) file1.delete();
    		});
    		if(e1 instanceof BusinessException) throw (BusinessException)e1;
    		e1.printStackTrace();
    		throw new BusinessException(CodeContrants.ERR_INSERT,"공지사항 단건등록 ",e1.toString());
    	}
 		return retMap;
    }
	
	@GetMapping(value = "/pagenotice")
	public Map pagenotice(
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
			List<Map> list =  noticeService.pageNotice(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", noticeService.countNotice(searchMap));
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
	
	
	/*
	 * @PostMapping("/uploadFile") public FileUploadResponse
	 * uploadFile(@RequestParam("file") MultipartFile file) { String fileName =
	 * service.storeFile(file);
	 * 
	 * String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	 * .path("/downloadFile/") .path(fileName) .toUriString();
	 * 
	 * return new FileUploadResponse(fileName, fileDownloadUri,
	 * file.getContentType(), file.getSize()); }
	 */
	
	
	/**
	 * 공지사항 수정
	 * @param inMap
	 */
	@PutMapping(value="/notice")
    public Map updateNotice(MultipartHttpServletRequest request ) {
    	logger.debug("updateNotice:"+request.getMultiFileMap());
    	Map retMap = new HashMap();
    	try {
    		String noti_type_cd = Utils.NVL(request.getParameter("noti_type_cd"));
     		String noti_title = Utils.NVL(request.getParameter("noti_title"));
     		String noti_cnt = Utils.NVL(request.getParameter("noti_cnt"));
     		String noti_sid = Utils.NVL(request.getParameter("noti_sid"));
     		String file_nm = Utils.NVL(request.getParameter("file_nm"));
    		
     		Map inMap = new HashMap();
		    inMap.put("noti_sid", noti_sid);
		    inMap.put("noti_type_cd", noti_type_cd);
		    inMap.put("noti_title", noti_title);
		    inMap.put("noti_cnt", noti_cnt);
		    inMap.put("file_nm",file_nm);
		    
    		Map noticeMap = noticeService.getNotice_noti_sid(inMap);
    		boolean isFirst = true;
    		
    		//파일명이 없으면 파일 삭제
    		if(file_nm.equals("") && !Utils.isNone((String)noticeMap.get("file_phy_nm"))) {
    			String real_img_url = fileConfig.getUploadDir()+"/"+noticeMap.get("file_phy_nm");
				logger.debug("real_img_url:"+real_img_url);
				Path file1 = Paths.get(real_img_url); 
				if(Files.exists(file1)) Files.delete(file1);
				inMap.put("file_nm"," ");
				inMap.put("file_phy_nm"," ");
		    }
    		
    		Iterator<String> itr = request.getFileNames();
    		if(itr.hasNext()) {
				List<MultipartFile> mpfs = request.getFiles(itr.next());				
				for(int i=0;i<mpfs.size();i++) {
					if(isFirst && !Utils.isNone((String)noticeMap.get("file_phy_nm"))) {
	 					String real_img_url = fileConfig.getUploadDir()+"/"+noticeMap.get("file_phy_nm");
	 					logger.debug("real_img_url:"+real_img_url);
	 					Path file1 = Paths.get(real_img_url); 
	 					if(Files.exists(file1)) Files.delete(file1);
	 				}
					
					String img_url =Utils.makeImgKey();
					String real_img_url = fileConfig.getUploadDir()+"/"+img_url;
					String img_nm = mpfs.get(i).getOriginalFilename();
					inMap.put("file_seq",i);					
					inMap.put("file_nm",img_nm);
					inMap.put("file_phy_nm",img_url);
					logger.debug("inMap:"+inMap);
					File file1 = new File(real_img_url); 
					mpfs.get(i).transferTo(file1);
				}
				isFirst = false;
 			}
    		
    		int resCNt =noticeService.updateNotice(inMap);
    		if(resCNt==0) {
    			throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
    		}
    		Utils.addSuccessMsg(retMap);
    	}catch(Throwable e) {
    		if(e instanceof BusinessException) throw (BusinessException)e;
    		e.printStackTrace();
    		throw new BusinessException(CodeContrants.ERR_UPDATE,"공지사항 수정 오류",e.toString());
    	}
    	return retMap;
    }
	
	/**
	 * 공지사항 삭제 마스터
	 * @param inMap
	 * @throws Throwable 
	 */
	@DeleteMapping(value="/notice")
    public Map deleteNotice(@RequestBody Map<String, String> inMap ) {
    	logger.debug("deleteNotice:"+inMap);
    	Map retMap = new HashMap();
    	try {
    		
    		//이전파일을 삭제 하고 처리. 공지일련번호로조회
    		Map noticeMap = noticeService.getNotice_noti_sid(inMap);
			String real_img_url = fileConfig.getUploadDir()+"/"+noticeMap.get("file_phy_nm");
			logger.debug("real_img_url:"+real_img_url);
			Path file1 = Paths.get(real_img_url); 
			Files.delete(file1);
    		
    		int resCNt =noticeService.deleteNotice(inMap);
    		if(resCNt==0) {
    			throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
    		}
    		Utils.addSuccessMsg(retMap);
    	}catch(Throwable e) {
    		if(e instanceof BusinessException) throw (BusinessException)e;
    		e.printStackTrace();
    		throw new BusinessException(CodeContrants.ERR_DELETE,"공지사항 삭제 오류",e.toString());
    	}
    	return retMap;
    }
	
	@GetMapping("/notidown/{noti_sid}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String noti_sid, HttpServletRequest request){
		
		Map inMap = new HashMap();
	    inMap.put("noti_sid", noti_sid);
	    logger.debug("noticeMap:"+noti_sid);
		Map noticeMap = noticeService.getNotice_noti_sid(inMap);
		logger.debug("noticeMap:"+noticeMap);
		if(noticeMap.size()==0) {
			throw new BusinessException(CodeContrants.ERR_NOTEXIST,"해당건은 미존재합니다");
		}
		
		Resource resource = null;
		 // Load file as Resource
		Path fileLocation = Paths.get(fileConfig.getUploadDir());
		 logger.debug("fileLocation:"+fileLocation+"  "+noticeMap.get("file_phy_nm"));
		try {
            Path filePath = fileLocation.resolve((String)noticeMap.get("file_phy_nm")).normalize();
            logger.debug("filePath:"+filePath);
            resource = new UrlResource(filePath.toUri());
            
            if(!resource.exists()) {
                throw new BusinessException(CodeContrants.ERR_FILE,(String)noticeMap.get("file_phy_nm") + " 파일을 찾을 수 없습니다.");
            }
        }catch(MalformedURLException e) {
            throw new BusinessException(CodeContrants.ERR_FILE,(String)noticeMap.get("file_phy_nm") + " 파일을 찾을 수 없습니다.", e.toString());
        }
        
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
 
        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
 
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + (String)noticeMap.get("file_nm") + "\"")
                .body(resource);
    }
	
	/*
	 * @GetMapping(value = "/pagenoti") public Map pageNotice(
	 * 
	 * @RequestParam("pagesize") String pagesize,
	 * 
	 * @RequestParam("pageindex") String pageindex,
	 * 
	 * @RequestParam Map<String, String> param) {
	 * logger.debug("pageUser pagesize:"+pagesize);
	 * logger.debug("pageUser pageindex:"+pageindex); //@RequestParam String
	 * pagesize, //@RequestParam String pageindex,
	 * 
	 * Map contion = new HashMap(); Map restulMap = new HashMap(); int ipagesize =
	 * param.get("pagesize")==null?0:Integer.parseInt(pagesize); int ipageindex =
	 * param.get("pageindex")==null?0:Integer.parseInt(pageindex); Map inMap = new
	 * HashMap();
	 * 
	 * Map searchMap= new HashMap(); searchMap.putAll(param);
	 * searchMap.put("startRow", String.valueOf((ipageindex-1) * ipagesize +1));
	 * searchMap.put("endRow", String.valueOf(ipageindex * ipagesize) );
	 * 
	 * logger.debug("searchMap param:"+searchMap); try { List<Map> list =
	 * noticeService.pageNotice(searchMap); restulMap.put("list", list);
	 * restulMap.put("totalcount", noticeService.countNotice(searchMap));
	 * restulMap.put("indexlist", "하단 인덱스 정보."); }catch(Throwable e) { if(e
	 * instanceof BusinessException) throw e; e.printStackTrace(); throw new
	 * BusinessException(CodeContrants.ERR_SELECT,"공지사항 페이징 조회 오류",e.toString()); } return restulMap;
	 * }
	 */

}
