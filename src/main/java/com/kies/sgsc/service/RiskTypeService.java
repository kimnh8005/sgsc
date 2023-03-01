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
import com.kies.sgsc.dao.base.RiskTypeDAO;

/**
 * 위험도타입
 * @author leeju
 *
 */
@Service
public class RiskTypeService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	RiskTypeDAO riskTypeDAO;

	public int insertRiskType(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertRiskType:"+inMap);
		return riskTypeDAO.insertRiskType(inMap);
	}

	public String getRiskTypeKey() {
		return riskTypeDAO.getRiskTypeKey();
	}

	public int deleteRiskType(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteRiskType:"+inMap);
		return riskTypeDAO.deleteRiskType(inMap);
	}

	public int updateRiskType(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateRiskType:"+inMap);
		return riskTypeDAO.updateRiskType(inMap);
	}

	public Map getRiskType_risk_type_id(Map inMap){
		logger.debug("getRiskType_risk_type_id:"+inMap);
		return riskTypeDAO.getRiskType_risk_type_id(inMap);
	}

	public List<Map> listRiskType(Map inMap){
		logger.debug("listRiskType:"+inMap);
		return riskTypeDAO.listRiskType(inMap);
	}

	public List<Map> pageRiskType(Map inMap){
		logger.debug("pageRiskType:"+inMap);
		return riskTypeDAO.pageRiskType(inMap);
	}

	public int countRiskType(Map inMap){
		logger.debug("countRiskType:"+inMap);
		return riskTypeDAO.countRiskType(inMap);
	}

}