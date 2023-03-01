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
import com.kies.sgsc.dao.base.EqmtRiskAnayRtmeLoadHourlyDAO;

@Service
public class EqmtRiskAnayRtmeLoadHourlyService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	EqmtRiskAnayRtmeLoadHourlyDAO eqmtRiskAnayRtmeLoadHourlyDAO;

	public int insertEqmtRiskAnayRtmeLoadHourly(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertEqmtRiskAnayRtmeLoadHourly:"+inMap);
		return eqmtRiskAnayRtmeLoadHourlyDAO.insertEqmtRiskAnayRtmeLoadHourly(inMap);
	}

	public int deleteEqmtRiskAnayRtmeLoadHourly(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteEqmtRiskAnayRtmeLoadHourly:"+inMap);
		return eqmtRiskAnayRtmeLoadHourlyDAO.deleteEqmtRiskAnayRtmeLoadHourly(inMap);
	}

	public int updateEqmtRiskAnayRtmeLoadHourly(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateEqmtRiskAnayRtmeLoadHourly:"+inMap);
		return eqmtRiskAnayRtmeLoadHourlyDAO.updateEqmtRiskAnayRtmeLoadHourly(inMap);
	}

	public List<Map> listEqmtRiskAnayRtmeLoadHourly_anys_ymdh(Map inMap){
		logger.debug("getEqmtRiskAnayRtmeLoadHourly_anys_ymdh:"+inMap);
		return eqmtRiskAnayRtmeLoadHourlyDAO.listEqmtRiskAnayRtmeLoadHourly_anys_ymdh(inMap);
	}

	public List<Map> listEqmtRiskAnayRtmeLoadHourly_anys_sys_cd(Map inMap){
		logger.debug("getEqmtRiskAnayRtmeLoadHourly_anys_sys_cd:"+inMap);
		return eqmtRiskAnayRtmeLoadHourlyDAO.listEqmtRiskAnayRtmeLoadHourly_anys_sys_cd(inMap);
	}

	public List<Map> listEqmtRiskAnayRtmeLoadHourly_risk_type_id(Map inMap){
		logger.debug("getEqmtRiskAnayRtmeLoadHourly_risk_type_id:"+inMap);
		return eqmtRiskAnayRtmeLoadHourlyDAO.listEqmtRiskAnayRtmeLoadHourly_risk_type_id(inMap);
	}

	public Map getEqmtRiskAnayRtmeLoadHourly_eqmt_id(Map inMap){
		logger.debug("getEqmtRiskAnayRtmeLoadHourly_eqmt_id:"+inMap);
		return eqmtRiskAnayRtmeLoadHourlyDAO.getEqmtRiskAnayRtmeLoadHourly_eqmt_id(inMap);
	}

	public List<Map> listEqmtRiskAnayRtmeLoadHourly(Map inMap){
		logger.debug("listEqmtRiskAnayRtmeLoadHourly:"+inMap);
		return eqmtRiskAnayRtmeLoadHourlyDAO.listEqmtRiskAnayRtmeLoadHourly(inMap);
	}

	public List<Map> pageEqmtRiskAnayRtmeLoadHourly(Map inMap){
		logger.debug("pageEqmtRiskAnayRtmeLoadHourly:"+inMap);
		return eqmtRiskAnayRtmeLoadHourlyDAO.pageEqmtRiskAnayRtmeLoadHourly(inMap);
	}

	public int countEqmtRiskAnayRtmeLoadHourly(Map inMap){
		logger.debug("countEqmtRiskAnayRtmeLoadHourly:"+inMap);
		return eqmtRiskAnayRtmeLoadHourlyDAO.countEqmtRiskAnayRtmeLoadHourly(inMap);
	}

}