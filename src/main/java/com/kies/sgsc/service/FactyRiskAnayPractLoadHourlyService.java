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
import com.kies.sgsc.dao.base.FactyRiskAnayPractLoadHourlyDAO;

@Service
public class FactyRiskAnayPractLoadHourlyService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	FactyRiskAnayPractLoadHourlyDAO factyRiskAnayPractLoadHourlyDAO;

	public int insertFactyRiskAnayPractLoadHourly(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertFactyRiskAnayPractLoadHourly:"+inMap);
		return factyRiskAnayPractLoadHourlyDAO.insertFactyRiskAnayPractLoadHourly(inMap);
	}

	public int deleteFactyRiskAnayPractLoadHourly(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteFactyRiskAnayPractLoadHourly:"+inMap);
		return factyRiskAnayPractLoadHourlyDAO.deleteFactyRiskAnayPractLoadHourly(inMap);
	}

	public int updateFactyRiskAnayPractLoadHourly(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateFactyRiskAnayPractLoadHourly:"+inMap);
		return factyRiskAnayPractLoadHourlyDAO.updateFactyRiskAnayPractLoadHourly(inMap);
	}

	public List<Map> listFactyRiskAnayPractLoadHourly_anys_ymdh(Map inMap){
		logger.debug("getFactyRiskAnayPractLoadHourly_anys_ymdh:"+inMap);
		return factyRiskAnayPractLoadHourlyDAO.listFactyRiskAnayPractLoadHourly_anys_ymdh(inMap);
	}

	public List<Map> listFactyRiskAnayPractLoadHourly_anys_sys_cd(Map inMap){
		logger.debug("getFactyRiskAnayPractLoadHourly_anys_sys_cd:"+inMap);
		return factyRiskAnayPractLoadHourlyDAO.listFactyRiskAnayPractLoadHourly_anys_sys_cd(inMap);
	}

	public List<Map> listFactyRiskAnayPractLoadHourly_risk_type_id(Map inMap){
		logger.debug("getFactyRiskAnayPractLoadHourly_risk_type_id:"+inMap);
		return factyRiskAnayPractLoadHourlyDAO.listFactyRiskAnayPractLoadHourly_risk_type_id(inMap);
	}

	public Map getFactyRiskAnayPractLoadHourly_facty_id(Map inMap){
		logger.debug("getFactyRiskAnayPractLoadHourly_facty_id:"+inMap);
		return factyRiskAnayPractLoadHourlyDAO.getFactyRiskAnayPractLoadHourly_facty_id(inMap);
	}

	public List<Map> listFactyRiskAnayPractLoadHourly(Map inMap){
		logger.debug("listFactyRiskAnayPractLoadHourly:"+inMap);
		return factyRiskAnayPractLoadHourlyDAO.listFactyRiskAnayPractLoadHourly(inMap);
	}

	public List<Map> pageFactyRiskAnayPractLoadHourly(Map inMap){
		logger.debug("pageFactyRiskAnayPractLoadHourly:"+inMap);
		return factyRiskAnayPractLoadHourlyDAO.pageFactyRiskAnayPractLoadHourly(inMap);
	}

	public int countFactyRiskAnayPractLoadHourly(Map inMap){
		logger.debug("countFactyRiskAnayPractLoadHourly:"+inMap);
		return factyRiskAnayPractLoadHourlyDAO.countFactyRiskAnayPractLoadHourly(inMap);
	}

}