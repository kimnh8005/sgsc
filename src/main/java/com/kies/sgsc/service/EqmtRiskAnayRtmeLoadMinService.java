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
import com.kies.sgsc.dao.base.EqmtRiskAnayRtmeLoadMinDAO;

@Service
public class EqmtRiskAnayRtmeLoadMinService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	EqmtRiskAnayRtmeLoadMinDAO eqmtRiskAnayRtmeLoadMinDAO;

	public int insertEqmtRiskAnayRtmeLoadMin(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertEqmtRiskAnayRtmeLoadMin:"+inMap);
		return eqmtRiskAnayRtmeLoadMinDAO.insertEqmtRiskAnayRtmeLoadMin(inMap);
	}

	public int deleteEqmtRiskAnayRtmeLoadMin(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteEqmtRiskAnayRtmeLoadMin:"+inMap);
		return eqmtRiskAnayRtmeLoadMinDAO.deleteEqmtRiskAnayRtmeLoadMin(inMap);
	}

	public int updateEqmtRiskAnayRtmeLoadMin(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateEqmtRiskAnayRtmeLoadMin:"+inMap);
		return eqmtRiskAnayRtmeLoadMinDAO.updateEqmtRiskAnayRtmeLoadMin(inMap);
	}

	public List<Map> listEqmtRiskAnayRtmeLoadMin_anys_ymdhi(Map inMap){
		logger.debug("getEqmtRiskAnayRtmeLoadMin_anys_ymdhi:"+inMap);
		return eqmtRiskAnayRtmeLoadMinDAO.listEqmtRiskAnayRtmeLoadMin_anys_ymdhi(inMap);
	}

	public List<Map> listEqmtRiskAnayRtmeLoadMin_anys_sys_cd(Map inMap){
		logger.debug("getEqmtRiskAnayRtmeLoadMin_anys_sys_cd:"+inMap);
		return eqmtRiskAnayRtmeLoadMinDAO.listEqmtRiskAnayRtmeLoadMin_anys_sys_cd(inMap);
	}

	public List<Map> listEqmtRiskAnayRtmeLoadMin_risk_type_id(Map inMap){
		logger.debug("getEqmtRiskAnayRtmeLoadMin_risk_type_id:"+inMap);
		return eqmtRiskAnayRtmeLoadMinDAO.listEqmtRiskAnayRtmeLoadMin_risk_type_id(inMap);
	}

	public Map getEqmtRiskAnayRtmeLoadMin_eqmt_id(Map inMap){
		logger.debug("getEqmtRiskAnayRtmeLoadMin_eqmt_id:"+inMap);
		return eqmtRiskAnayRtmeLoadMinDAO.getEqmtRiskAnayRtmeLoadMin_eqmt_id(inMap);
	}

	public List<Map> listEqmtRiskAnayRtmeLoadMin(Map inMap){
		logger.debug("listEqmtRiskAnayRtmeLoadMin:"+inMap);
		return eqmtRiskAnayRtmeLoadMinDAO.listEqmtRiskAnayRtmeLoadMin(inMap);
	}

	public List<Map> pageEqmtRiskAnayRtmeLoadMin(Map inMap){
		logger.debug("pageEqmtRiskAnayRtmeLoadMin:"+inMap);
		return eqmtRiskAnayRtmeLoadMinDAO.pageEqmtRiskAnayRtmeLoadMin(inMap);
	}

	public int countEqmtRiskAnayRtmeLoadMin(Map inMap){
		logger.debug("countEqmtRiskAnayRtmeLoadMin:"+inMap);
		return eqmtRiskAnayRtmeLoadMinDAO.countEqmtRiskAnayRtmeLoadMin(inMap);
	}

}