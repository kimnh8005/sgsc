package com.kies.sgsc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Arrays;
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

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.kies.sgsc.comm.exception.BusinessException;
import com.kies.sgsc.comm.exception.CodeContrants;
import com.kies.sgsc.comm.util.FcmMsgUtil;
import com.kies.sgsc.comm.util.Utils;
import com.kies.sgsc.service.SendRefugeService;

/**
 * 위험도 발송이력
 * @author leeju
 *
 */
@RestController
@RequestMapping("comm")
public class SendRefugeController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	SendRefugeService sendRefugeService;
	
	@GetMapping(value = "/fcmsend")
	public Map sendMobile1(@RequestParam Map<String, String> inMap){
		Map retMap = new HashMap();
		try {
			String receiverToken = "dTEbwQx6Sn2umFUXkSH-TC:APA91bFG8-83zo2PeGJKdI7YKRP55f6Y8mP8gvsr_cUOms6pQcuCaGHUrd-ZuUEztYdBCEDuAif3UBpsiMramM1a72LJW4mr8WywfjCdRiUPT4KbKhEbaoAgXNXl3R6oQSpoRGBVOjAj";
			String resMsg = FcmMsgUtil.sendMessage(receiverToken,"테스트메시지","테스트내용");
			
			String retStr = FcmMsgUtil.sendMessage(receiverToken, "병렬단건테스트", "병렬단건테스트");
			logger.debug("FIRST:"+retStr);
			List tokenList = Arrays.asList(
				new String[] {
						receiverToken,
						receiverToken, 
						receiverToken,
						receiverToken,
						receiverToken,
						receiverToken,
						receiverToken,
						receiverToken,
						receiverToken,
						receiverToken
				}
			);
			List<String> tList = FcmMsgUtil.sendMessage(tokenList, "병렬다건테스트", "병렬다건테스트");
			logger.debug("SECOND:"+tList);
			Utils.addSuccessMsg(retMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"send error",e.toString());
		}
//		System.out.println("Successfully sent message: " + response);
		return retMap;
	}
	
	@GetMapping(value = "/sendmobile")
	public Map sendMobile(@RequestParam Map<String, String> inMap){
		// This registration token comes from the client FCM SDKs.
		String registrationToken = "dTEbwQx6Sn2umFUXkSH-TC:APA91bFG8-83zo2PeGJKdI7YKRP55f6Y8mP8gvsr_cUOms6pQcuCaGHUrd-ZuUEztYdBCEDuAif3UBpsiMramM1a72LJW4mr8WywfjCdRiUPT4KbKhEbaoAgXNXl3R6oQSpoRGBVOjAj";

		// See documentation on defining a message payload.
		Message message = Message.builder()
		.putData("score", "850")
		.putData("time", "2:45")
		.setToken(registrationToken)
		.build();

		// Send a message to the device corresponding to the provided
		// registration token.
		String response;
		try {
			response = FirebaseMessaging.getInstance().send(message);
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"send error",e.toString());
		}
//		System.out.println("Successfully sent message: " + response);
		return new HashMap();
	}
	
	@GetMapping(value = "/sendrefuge")
	public Map listSendRefuge(@RequestParam Map<String, String> inMap){
		logger.debug("listSendRefuge:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			list = sendRefugeService.listSendRefuge(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}

	@GetMapping(value = "/sendrefuge/{user_id}")
	public Map listSendRefuge_user_id(@PathVariable("user_id") String user_id ,@RequestParam Map<String, String> inMap){
		Map retMap = new HashMap();
		try {
			inMap.put("user_id", user_id);
			logger.debug("getSendRefuge_user_id:"+inMap);
			List<Map> list = sendRefugeService.listSendRefuge_user_id(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"단건조회오류",e.toString());
		}
		return retMap;
	}

	@GetMapping(value = "/sendrefuge/{user_id}/{send_rnd}")
	public Map listSendRefuge_send_rnd(@PathVariable("user_id") String user_id ,@PathVariable("send_rnd") String send_rnd ,@RequestParam Map<String, String> inMap){
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			inMap.put("user_id", user_id);
			inMap.put("send_rnd", send_rnd);
			logger.debug("getSendRefuge_send_rnd:"+inMap);
			list = sendRefugeService.listSendRefuge_send_rnd(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"단건조회오류",e.toString());
		}
		return retMap;
	}

	@GetMapping(value = "/sendrefuge/{user_id}/{send_rnd}/{send_sid}")
	public Map getSendRefuge_send_sid(@PathVariable("user_id") String user_id ,@PathVariable("send_rnd") String send_rnd ,@PathVariable("send_sid") String send_sid ,@RequestParam Map<String, String> inMap){
		Map retMap = null;
		try {
			inMap.put("user_id", user_id);
			inMap.put("send_rnd", send_rnd);
			inMap.put("send_sid", send_sid);
			logger.debug("getSendRefuge_send_sid:"+inMap);
			retMap = sendRefugeService.getSendRefuge_send_sid(inMap);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"단건조회오류",e.toString());
		}
		return retMap;
	}

	@PostMapping(value="/sendrefuge")
	public Map insertSendRefuge(@RequestBody Map<String, String> inMap) {
		logger.debug("insertSendRefuge:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = sendRefugeService.insertSendRefuge(inMap);
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

	@PostMapping(value="/allsendrefuge")
	public Map insertAllSendRefuge(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("insertAllSendRefuge:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = sendRefugeService.insertSendRefuge(inMap);
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

	@PutMapping(value="/sendrefuge")
	public Map updateSendRefuge(@RequestBody Map<String, String> inMap) {
		logger.debug("updateSendRefuge:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = sendRefugeService.updateSendRefuge(inMap);
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

	@PutMapping(value="/allsendrefuge")
	public Map updateAllSendRefuge(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("updateAllSendRefuge:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = sendRefugeService.updateSendRefuge(inMap);
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

	@DeleteMapping(value="/sendrefuge")
	public Map deleteSendRefuge(@RequestBody Map<String, String> inMap) {
		logger.debug("deleteSendRefuge:"+inMap);
		Map retMap = new HashMap();
		try {
			int resCNt = sendRefugeService.deleteSendRefuge(inMap);
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

	@DeleteMapping(value="/allsendrefuge")
	public Map deleteAllSendRefuge(@RequestBody List<Map<String, String>> inMapList) {
		logger.debug("deleteAllSendRefuge:"+inMapList);
		Map retMap = new HashMap();
		try {
			for(Map<String,String> inMap : inMapList) {
				int resCNt = sendRefugeService.deleteSendRefuge(inMap);
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

	@GetMapping(value = "/pagesendrefuge")
	public Map pageSendRefuge(
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
			List<Map> list =  sendRefugeService.pageSendRefuge(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", sendRefugeService.countSendRefuge(searchMap));
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