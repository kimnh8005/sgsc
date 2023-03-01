package com.kies.sgsc.controller.upo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kies.sgsc.comm.exception.BusinessException;
import com.kies.sgsc.comm.exception.CodeContrants;
import com.kies.sgsc.comm.util.Utils;
import com.kies.sgsc.service.EqmtCtrlService;
import com.kies.sgsc.service.upo.UpoService;



/**
 * 
 * @author nhkim
 *  
 */
@RestController
@RequestMapping(value = "upo")
public class UpoController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	UpoService upoService;
	
	@Autowired
	EqmtCtrlService eqmtCtrlService;

	@RequestMapping(value = "/eqmt/control/status", method = RequestMethod.GET)
	public Map getEqmtControllerStatus(@RequestParam Map<String, String> inMap) throws Throwable{
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			list = upoService.get_transmitter_info();
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	@RequestMapping(value = "/eqmt/control/status/history", method = RequestMethod.GET)
	public Map eqmtControllerStatusHistory(@RequestParam Map<String, String> inMap) throws Throwable{
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			list = eqmtCtrlService.getEqmtCtrlStatusHistory();
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	@PostMapping(value="/eqmt/control/status")
	public Map insertControlInfoStatus(@RequestBody Map<String, String> inMap , HttpServletRequest request) {
		System.out.println("=====================");
		System.out.println(request.getRemoteAddr());
		System.out.println("=====================");
		Map retMap = new HashMap();
		try {
			
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = format.format(date);
			inMap.put("DATE",dateString);
			inMap.put("CMD_ID","20");
			inMap.put("FULL_PACKET","02");
			inMap.put("STATUS","0");
			
			
			int resCNt = upoService.insertControlInfoStatus(inMap);
			
			if(resCNt==0) {
				throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음");
			}else {
				Map map = new HashMap();
				if(!inMap.get("NODE_ID").toString().contentEquals(" ") && !inMap.get("TRANSMITTER_ID").toString().contentEquals(" ")) {
					map.put("ip",request.getRemoteAddr().toString());
					map.put("status","1");
					map.put("tag_ctrl_id",inMap.get("TRANSMITTER_ID").toString());
					map.put("node_id",inMap.get("NODE_ID"));
					map.put("content",inMap.get("CONTENT"));
					if(map.containsKey("regist_id") == false || map.containsValue("regist_id") == false) {
						System.out.println("121212121");
						map.put("regist_id","sgsc_system");						
					}else {
						map.put("regist_id",inMap.get("REGIST_ID"));	
					}
					eqmtCtrlService.insertEqmtCtrlHistory(map);
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
	
}