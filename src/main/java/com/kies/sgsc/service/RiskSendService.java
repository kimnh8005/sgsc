package com.kies.sgsc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.kies.sgsc.comm.exception.BusinessException;
import com.kies.sgsc.comm.exception.CodeContrants;
import com.kies.sgsc.comm.util.FcmMsgUtil;
import com.kies.sgsc.comm.util.Utils;
import com.kies.sgsc.dao.base.RiskSendDAO;
import com.kies.sgsc.dao.base.UserDevicesDAO;

@Service
public class RiskSendService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	RiskSendDAO riskSendDAO;
	
	@Autowired
	UserDevicesDAO userDevicesDAO;

	public int insertRiskSend(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertRiskSend:"+inMap);
		return riskSendDAO.insertRiskSend(inMap);
	}

	public int deleteRiskSend(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteRiskSend:"+inMap);
		return riskSendDAO.deleteRiskSend(inMap);
	}

	public int updateRiskSend(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateRiskSend:"+inMap);
		return riskSendDAO.updateRiskSend(inMap);
	}

	public List<Map> listRiskSend_send_rnd(Map inMap){
		logger.debug("getRiskSend_send_rnd:"+inMap);
		return riskSendDAO.listRiskSend_send_rnd(inMap);
	}

	public Map getRiskSend_send_sid(Map inMap){
		logger.debug("getRiskSend_send_sid:"+inMap);
		return riskSendDAO.getRiskSend_send_sid(inMap);
	}

	public List<Map> listRiskSend(Map inMap){
		logger.debug("listRiskSend:"+inMap);
		return riskSendDAO.listRiskSend(inMap);
	}

	public List<Map> pageRiskSend(Map inMap){
		logger.debug("pageRiskSend:"+inMap);
		return riskSendDAO.pageRiskSend(inMap);
	}

	public int countRiskSend(Map inMap){
		logger.debug("countRiskSend:"+inMap);
		return riskSendDAO.countRiskSend(inMap);
	}
	
	
	/**
	 * ????????? ???????????? ??????. 
	 * facty_id ???????????????
	 * procs_id ???????????????
	 * eqmt_id ???????????????
	 * send_type_cd ??????????????????
	 * send_stat_cd ??????????????????
	 * title ??????
	 * risk_step_cd ????????????
	 * cbm_fixd_rate ????????????_????????????
	 * send_msg ???????????????
	 * 
	 * @param inMap
	 * @return
	
	public Map sendAllMessage(@RequestParam Map<String, String> inMap){
		Map retMap = new HashMap();
		try {
			Map paramMap = new HashMap();
			paramMap.put("mobile_push_yn","Y");
			List<Map> iistDevice = userDevicesDAO.listUserDevices(paramMap);
			
			List<String> tokenList = new ArrayList<String>();
			for(Map tMap : iistDevice) {
				tokenList.add((String)tMap.get("mobile_token"));
			}
			
			inMap.get(key)
			List<String> tList = FcmMsgUtil.sendMessage(tokenList, "?????????????????????", "?????????????????????");
			logger.debug("SECOND:"+tList);
			Utils.addSuccessMsg(retMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"send error",e.toString());
		}
//		System.out.println("Successfully sent message: " + response);
		return retMap;
	} */

}