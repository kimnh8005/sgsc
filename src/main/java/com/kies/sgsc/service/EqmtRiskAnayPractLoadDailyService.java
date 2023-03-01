package com.kies.sgsc.service;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.kies.sgsc.comm.util.Utils;
import com.kies.sgsc.dao.base.EqmtRiskAnayPractLoadDailyDAO;

@Service
public class EqmtRiskAnayPractLoadDailyService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	EqmtRiskAnayPractLoadDailyDAO eqmtRiskAnayPractLoadDailyDAO;

	public int insertEqmtRiskAnayPractLoadDaily(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertEqmtRiskAnayPractLoadDaily:"+inMap);
		return eqmtRiskAnayPractLoadDailyDAO.insertEqmtRiskAnayPractLoadDaily(inMap);
	}

	public int deleteEqmtRiskAnayPractLoadDaily(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteEqmtRiskAnayPractLoadDaily:"+inMap);
		return eqmtRiskAnayPractLoadDailyDAO.deleteEqmtRiskAnayPractLoadDaily(inMap);
	}

	public int updateEqmtRiskAnayPractLoadDaily(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateEqmtRiskAnayPractLoadDaily:"+inMap);
		return eqmtRiskAnayPractLoadDailyDAO.updateEqmtRiskAnayPractLoadDaily(inMap);
	}

	public List<Map> listEqmtRiskAnayPractLoadDaily_anys_ymd(Map inMap){
		logger.debug("getEqmtRiskAnayPractLoadDaily_anys_ymd:"+inMap);
		return eqmtRiskAnayPractLoadDailyDAO.listEqmtRiskAnayPractLoadDaily_anys_ymd(inMap);
	}

	public List<Map> listEqmtRiskAnayPractLoadDaily_anys_sys_cd(Map inMap){
		logger.debug("getEqmtRiskAnayPractLoadDaily_anys_sys_cd:"+inMap);
		return eqmtRiskAnayPractLoadDailyDAO.listEqmtRiskAnayPractLoadDaily_anys_sys_cd(inMap);
	}

	public Map getEqmtRiskAnayPractLoadDaily_risk_type_id(Map inMap){
		logger.debug("getEqmtRiskAnayPractLoadDaily_risk_type_id:"+inMap);
		return eqmtRiskAnayPractLoadDailyDAO.getEqmtRiskAnayPractLoadDaily_risk_type_id(inMap);
	}

	public List<Map> listEqmtRiskAnayPractLoadDaily(Map inMap){
		logger.debug("listEqmtRiskAnayPractLoadDaily:"+inMap);
		return eqmtRiskAnayPractLoadDailyDAO.listEqmtRiskAnayPractLoadDaily(inMap);
	}

	public List<Map> pageEqmtRiskAnayPractLoadDaily(Map inMap){
		logger.debug("pageEqmtRiskAnayPractLoadDaily:"+inMap);
		return eqmtRiskAnayPractLoadDailyDAO.pageEqmtRiskAnayPractLoadDaily(inMap);
	}

	public int countEqmtRiskAnayPractLoadDaily(Map inMap){
		logger.debug("countEqmtRiskAnayPractLoadDaily:"+inMap);
		return eqmtRiskAnayPractLoadDailyDAO.countEqmtRiskAnayPractLoadDaily(inMap);
	}

}