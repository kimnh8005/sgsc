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
import com.kies.sgsc.service.IndtmualService;
/**
 * 사고대응메뉴얼
 * @author leeju
 *
 */
@RestController
@RequestMapping("comm")
public class IndtmualController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	IndtmualService indtmualService;

	@Autowired
	FileConfig fileConfig;
	
	@GetMapping(value = "/indtmual")
	public Map listIndtmual(@RequestParam Map<String, String> inMap){
		logger.debug("listIndtmual:"+inMap);
		Map retMap = new HashMap();
		List<Map> list = null;
		try {
			list = indtmualService.listIndtmual(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}

	@GetMapping(value = "/indtmual/{mual_sid}")
	public Map getIndtmual_mual_sid(@PathVariable("mual_sid") String mual_sid ,@RequestParam Map<String, String> inMap, HttpServletRequest request){
		Map returnMap = new HashMap();
		Map retMap = null;
		try {
			inMap.put("mual_sid", mual_sid);
			logger.debug("getIndtmual_mual_sid:"+inMap);
			
			retMap = indtmualService.getIndtmual_mual_sid(inMap);
			logger.debug("retMap:"+retMap);
			
			String serverIp = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
			
			if(retMap!=null&& retMap.size()>0) { 
				retMap.put("downloadurl", serverIp+"/sgsc/comm/indtmualdown/"+retMap.get("mual_sid"));
				returnMap.put("prev", indtmualService.getPrevIndtmual_mual_sid(inMap));
				returnMap.put("current", retMap);
				returnMap.put("next", indtmualService.getNextIndtmual_mual_sid(inMap));
			}
			Utils.addSuccessMsg(returnMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"단건조회오류",e.toString());
		}
		return returnMap;
	}

	@PostMapping(value="/indtmual")
	public Map insertIndtmual(MultipartHttpServletRequest request) {
		
		String mual_type_cd = Utils.NVL(request.getParameter("mual_type_cd"));
 		String mual_title = Utils.NVL(request.getParameter("mual_title"));
 		String mual_cnt = Utils.NVL(request.getParameter("mual_cnt"));
 		List<File> execList = new ArrayList<File>();
 		Map retMap = new HashMap();
 		try {
	 		Path pathfile = Paths.get(fileConfig.getUploadDir());
		    if(!Files.exists(pathfile)) Files.createDirectories(pathfile);
		    
		    Map inMap = new HashMap();
		    //inMap.put("mual_id", indtmualService.getNoticeKey());
		    inMap.put("mual_type_cd", mual_type_cd);
		    inMap.put("mual_title", mual_title);
		    inMap.put("mual_cnt", mual_cnt);
		    
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
		    int resCNt = indtmualService.insertIndtmual(inMap);
 		
    		if(resCNt==0) {
    			throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
    		}
    		Utils.addSuccessMsg(retMap);
    	}catch(DuplicateKeyException e) {
    		e.printStackTrace();
    		execList.stream().forEach( file1->{
    			if(file1.exists()) file1.delete();
    		});
    		throw new BusinessException(CodeContrants.ERR_DUPLICATE,"사고대응메뉴얼 중복 메뉴",e.toString());
    	}catch(Throwable e1) {
    		if(e1 instanceof BusinessException) throw (BusinessException)e1;
    		e1.printStackTrace();
    		execList.stream().forEach( file1->{
    			if(file1.exists()) file1.delete();
    		});
    		throw new BusinessException(CodeContrants.ERR_INSERT,"사고대응메뉴얼 단건등록 ",e1.toString());
    	}
 		return retMap;
	}


	@PutMapping(value="/indtmual")
	public Map updateIndtmual(MultipartHttpServletRequest request) {
		logger.debug("updateNotice:"+request.getMultiFileMap());
		List<File> execList = new ArrayList<File>();
		Map retMap = new HashMap();
    	try {
    		String mual_type_cd = Utils.NVL(request.getParameter("mual_type_cd"));
     		String mual_title = Utils.NVL(request.getParameter("mual_title"));
     		String mual_cnt = Utils.NVL(request.getParameter("mual_cnt"));
     		String mual_sid = Utils.NVL(request.getParameter("mual_sid"));
     		String file_nm = Utils.NVL(request.getParameter("file_nm"));
    		
     		Map inMap = new HashMap();
		    inMap.put("mual_sid", mual_sid);
		    inMap.put("mual_type_cd", mual_type_cd);
		    inMap.put("mual_title", mual_title);
		    inMap.put("mual_cnt", mual_cnt);
		    inMap.put("file_nm",file_nm);
		    
    		Map lndtmualMap = indtmualService.getIndtmual_mual_sid(inMap);
    		boolean isFirst = true;
    		
    		//파일명이 없으면 파일 삭제
    		if(file_nm.equals("") && !Utils.isNone((String)lndtmualMap.get("file_phy_nm"))) {
    			String real_img_url = fileConfig.getUploadDir()+"/"+lndtmualMap.get("file_phy_nm");
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
					if(isFirst && !Utils.isNone((String)lndtmualMap.get("file_phy_nm"))) {
	 					String real_img_url = fileConfig.getUploadDir()+"/"+lndtmualMap.get("file_phy_nm");
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
					execList.add(file1);
					mpfs.get(i).transferTo(file1);
				}
				isFirst = false;
    		}
    		int resCNt =indtmualService.updateIndtmual(inMap);
    		if(resCNt==0) {
    			throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
    		}
    		Utils.addSuccessMsg(retMap);
    	}catch(Throwable e) {
    		execList.stream().forEach( file1->{
    			if(file1.exists()) file1.delete();
    		});
    		if(e instanceof BusinessException) throw (BusinessException)e;
    		e.printStackTrace();
    		throw new BusinessException(CodeContrants.ERR_UPDATE,"사고대응메뉴얼 수정 오류",e.toString());
    	}
    	return retMap;
	}


	@DeleteMapping(value="/indtmual")
	public Map deleteIndtmual(@RequestBody Map<String, String> inMap) {
		logger.debug("deleteIndtmual:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = indtmualService.deleteIndtmual(inMap);
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

	@DeleteMapping(value="/allindtmual")
	public Map deleteAllIndtmual(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("deleteAllIndtmual:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = indtmualService.deleteIndtmual(inMap);
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

	@GetMapping(value = "/pageindtmual")
	public Map pageIndtmual(
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
			List<Map> list =  indtmualService.pageIndtmual(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", indtmualService.countIndtmual(searchMap));
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

	@GetMapping("/indtmualdown/{mual_sid}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String mual_sid, HttpServletRequest request){
		
		Map inMap = new HashMap();
	    inMap.put("mual_sid", mual_sid);
	    logger.debug("noticeMap:"+mual_sid);
		Map noticeMap = indtmualService.getIndtmual_mual_sid(inMap);
		logger.debug("noticeMap:"+noticeMap);
		if(noticeMap.size()==0) {
			throw new BusinessException(CodeContrants.ERR_NOTEXIST,"해당건은 미존재합니다");
		}
		
		Resource resource = null;
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
        
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
 
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + (String)noticeMap.get("file_nm") + "\"")
                .body(resource);
    }
}