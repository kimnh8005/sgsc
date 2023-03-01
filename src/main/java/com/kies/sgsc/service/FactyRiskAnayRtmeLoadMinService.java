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
import com.kies.sgsc.dao.base.FactyRiskAnayRtmeLoadMinDAO;

@Service
public class FactyRiskAnayRtmeLoadMinService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	FactyRiskAnayRtmeLoadMinDAO factyRiskAnayRtmeLoadMinDAO;

	public int insertFactyRiskAnayRtmeLoadMin(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertFactyRiskAnayRtmeLoadMin:"+inMap);
		return factyRiskAnayRtmeLoadMinDAO.insertFactyRiskAnayRtmeLoadMin(inMap);
	}

	public int deleteFactyRiskAnayRtmeLoadMin(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteFactyRiskAnayRtmeLoadMin:"+inMap);
		return factyRiskAnayRtmeLoadMinDAO.deleteFactyRiskAnayRtmeLoadMin(inMap);
	}

	public int updateFactyRiskAnayRtmeLoadMin(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateFactyRiskAnayRtmeLoadMin:"+inMap);
		return factyRiskAnayRtmeLoadMinDAO.updateFactyRiskAnayRtmeLoadMin(inMap);
	}

	public List<Map> listFactyRiskAnayRtmeLoadMin_anys_ymdhi(Map inMap){
		logger.debug("getFactyRiskAnayRtmeLoadMin_anys_ymdhi:"+inMap);
		return factyRiskAnayRtmeLoadMinDAO.listFactyRiskAnayRtmeLoadMin_anys_ymdhi(inMap);
	}

	public List<Map> listFactyRiskAnayRtmeLoadMin_anys_sys_cd(Map inMap){
		logger.debug("getFactyRiskAnayRtmeLoadMin_anys_sys_cd:"+inMap);
		return factyRiskAnayRtmeLoadMinDAO.listFactyRiskAnayRtmeLoadMin_anys_sys_cd(inMap);
	}

	public List<Map> listFactyRiskAnayRtmeLoadMin_risk_type_id(Map inMap){
		logger.debug("getFactyRiskAnayRtmeLoadMin_risk_type_id:"+inMap);
		return factyRiskAnayRtmeLoadMinDAO.listFactyRiskAnayRtmeLoadMin_risk_type_id(inMap);
	}

	public Map getFactyRiskAnayRtmeLoadMin_facty_id(Map inMap){
		logger.debug("getFactyRiskAnayRtmeLoadMin_facty_id:"+inMap);
		return factyRiskAnayRtmeLoadMinDAO.getFactyRiskAnayRtmeLoadMin_facty_id(inMap);
	}

	public List<Map> listFactyRiskAnayRtmeLoadMin(Map inMap){
		logger.debug("listFactyRiskAnayRtmeLoadMin:"+inMap);
		return factyRiskAnayRtmeLoadMinDAO.listFactyRiskAnayRtmeLoadMin(inMap);
	}

	public List<Map> pageFactyRiskAnayRtmeLoadMin(Map inMap){
		logger.debug("pageFactyRiskAnayRtmeLoadMin:"+inMap);
		return factyRiskAnayRtmeLoadMinDAO.pageFactyRiskAnayRtmeLoadMin(inMap);
	}

	public int countFactyRiskAnayRtmeLoadMin(Map inMap){
		logger.debug("countFactyRiskAnayRtmeLoadMin:"+inMap);
		return factyRiskAnayRtmeLoadMinDAO.countFactyRiskAnayRtmeLoadMin(inMap);
	}

	
	public List<Map> listProcsLoadMinSimulRiskNum(Map inMap){
		logger.debug("listProcsLoadMinSimulRiskNum:"+inMap);
		return factyRiskAnayRtmeLoadMinDAO.listProcsLoadMinSimulRiskNum(inMap);
	}
}