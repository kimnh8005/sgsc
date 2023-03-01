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
import com.kies.sgsc.dao.base.RiskTrhldManageDAO;

@Service
public class RiskTrhldManageService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	RiskTrhldManageDAO riskTrhldManageDAO;

	public int insertRiskTrhldManage(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertRiskTrhldManage:"+inMap);
		return riskTrhldManageDAO.insertRiskTrhldManage(inMap);
	}

	public String getRiskTrhldManageKey() {
		return riskTrhldManageDAO.getRiskTrhldManageKey();
	}

	public int deleteRiskTrhldManage(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteRiskTrhldManage:"+inMap);
		return riskTrhldManageDAO.deleteRiskTrhldManage(inMap);
	}

	public int updateRiskTrhldManage(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateRiskTrhldManage:"+inMap);
		return riskTrhldManageDAO.updateRiskTrhldManage(inMap);
	}

	public Map getRiskTrhldManage_trhld_sid(Map inMap){
		logger.debug("getRiskTrhldManage_trhld_sid:"+inMap);
		return riskTrhldManageDAO.getRiskTrhldManage_trhld_sid(inMap);
	}

	public List<Map> listRiskTrhldManage(Map inMap){
		logger.debug("listRiskTrhldManage:"+inMap);
		return riskTrhldManageDAO.listRiskTrhldManage(inMap);
	}
	
	public List<Map> listFactyRiskTrhld(Map inMap){
		logger.debug("listFactyRiskTrhld:"+inMap);
		return riskTrhldManageDAO.listFactyRiskTrhld(inMap);
	}

	public List<Map> pageRiskTrhldManage(Map inMap){
		logger.debug("pageRiskTrhldManage:"+inMap);
		return riskTrhldManageDAO.pageRiskTrhldManage(inMap);
	}

	public int countRiskTrhldManage(Map inMap){
		logger.debug("countRiskTrhldManage:"+inMap);
		return riskTrhldManageDAO.countRiskTrhldManage(inMap);
	}
	
	public List<Map> pageProcsRiskTrhld(Map inMap){
		logger.debug("pageProcsRiskTrhld:"+inMap);
		return riskTrhldManageDAO.pageProcsRiskTrhld(inMap);
	}

	public int countProcsRiskTrhld(Map inMap){
		logger.debug("countProcsRiskTrhld:"+inMap);
		return riskTrhldManageDAO.countProcsRiskTrhld(inMap);
	}
	
	public List<Map> pageEqmtRiskTrhld(Map inMap){
		logger.debug("pageEqmtRiskTrhld:"+inMap);
		return riskTrhldManageDAO.pageEqmtRiskTrhld(inMap);
	}

	public int countEqmtRiskTrhld(Map inMap){
		logger.debug("countEqmtRiskTrhld:"+inMap);
		return riskTrhldManageDAO.countEqmtRiskTrhld(inMap);
	}
	
	public Map getCalcRiskTrhld(Map inMap){
		logger.debug("getCalcRiskTrhld:"+inMap);
		return riskTrhldManageDAO.getCalcRiskTrhld(inMap);
	}
}