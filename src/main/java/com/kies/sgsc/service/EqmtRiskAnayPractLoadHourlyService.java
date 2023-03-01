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
import com.kies.sgsc.dao.base.EqmtRiskAnayPractLoadHourlyDAO;

@Service
public class EqmtRiskAnayPractLoadHourlyService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	EqmtRiskAnayPractLoadHourlyDAO eqmtRiskAnayPractLoadHourlyDAO;

	public int insertEqmtRiskAnayPractLoadHourly(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertEqmtRiskAnayPractLoadHourly:"+inMap);
		return eqmtRiskAnayPractLoadHourlyDAO.insertEqmtRiskAnayPractLoadHourly(inMap);
	}

	public int deleteEqmtRiskAnayPractLoadHourly(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteEqmtRiskAnayPractLoadHourly:"+inMap);
		return eqmtRiskAnayPractLoadHourlyDAO.deleteEqmtRiskAnayPractLoadHourly(inMap);
	}

	public int updateEqmtRiskAnayPractLoadHourly(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateEqmtRiskAnayPractLoadHourly:"+inMap);
		return eqmtRiskAnayPractLoadHourlyDAO.updateEqmtRiskAnayPractLoadHourly(inMap);
	}

	public List<Map> listEqmtRiskAnayPractLoadHourly_anys_ymdh(Map inMap){
		logger.debug("getEqmtRiskAnayPractLoadHourly_anys_ymdh:"+inMap);
		return eqmtRiskAnayPractLoadHourlyDAO.listEqmtRiskAnayPractLoadHourly_anys_ymdh(inMap);
	}

	public List<Map> listEqmtRiskAnayPractLoadHourly_anys_sys_cd(Map inMap){
		logger.debug("getEqmtRiskAnayPractLoadHourly_anys_sys_cd:"+inMap);
		return eqmtRiskAnayPractLoadHourlyDAO.listEqmtRiskAnayPractLoadHourly_anys_sys_cd(inMap);
	}

	public List<Map> listEqmtRiskAnayPractLoadHourly_risk_type_id(Map inMap){
		logger.debug("getEqmtRiskAnayPractLoadHourly_risk_type_id:"+inMap);
		return eqmtRiskAnayPractLoadHourlyDAO.listEqmtRiskAnayPractLoadHourly_risk_type_id(inMap);
	}

	public Map getEqmtRiskAnayPractLoadHourly_eqmt_id(Map inMap){
		logger.debug("getEqmtRiskAnayPractLoadHourly_eqmt_id:"+inMap);
		return eqmtRiskAnayPractLoadHourlyDAO.getEqmtRiskAnayPractLoadHourly_eqmt_id(inMap);
	}

	public List<Map> listEqmtRiskAnayPractLoadHourly(Map inMap){
		logger.debug("listEqmtRiskAnayPractLoadHourly:"+inMap);
		return eqmtRiskAnayPractLoadHourlyDAO.listEqmtRiskAnayPractLoadHourly(inMap);
	}

	public List<Map> pageEqmtRiskAnayPractLoadHourly(Map inMap){
		logger.debug("pageEqmtRiskAnayPractLoadHourly:"+inMap);
		return eqmtRiskAnayPractLoadHourlyDAO.pageEqmtRiskAnayPractLoadHourly(inMap);
	}

	public int countEqmtRiskAnayPractLoadHourly(Map inMap){
		logger.debug("countEqmtRiskAnayPractLoadHourly:"+inMap);
		return eqmtRiskAnayPractLoadHourlyDAO.countEqmtRiskAnayPractLoadHourly(inMap);
	}

}