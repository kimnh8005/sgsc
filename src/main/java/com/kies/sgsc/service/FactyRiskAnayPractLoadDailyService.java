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
import com.kies.sgsc.dao.base.FactyRiskAnayPractLoadDailyDAO;

@Service
public class FactyRiskAnayPractLoadDailyService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	FactyRiskAnayPractLoadDailyDAO factyRiskAnayPractLoadDailyDAO;

	public int insertFactyRiskAnayPractLoadDaily(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertFactyRiskAnayPractLoadDaily:"+inMap);
		return factyRiskAnayPractLoadDailyDAO.insertFactyRiskAnayPractLoadDaily(inMap);
	}

	public int deleteFactyRiskAnayPractLoadDaily(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteFactyRiskAnayPractLoadDaily:"+inMap);
		return factyRiskAnayPractLoadDailyDAO.deleteFactyRiskAnayPractLoadDaily(inMap);
	}

	public int updateFactyRiskAnayPractLoadDaily(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateFactyRiskAnayPractLoadDaily:"+inMap);
		return factyRiskAnayPractLoadDailyDAO.updateFactyRiskAnayPractLoadDaily(inMap);
	}

	public List<Map> listFactyRiskAnayPractLoadDaily_anys_ymd(Map inMap){
		logger.debug("getFactyRiskAnayPractLoadDaily_anys_ymd:"+inMap);
		return factyRiskAnayPractLoadDailyDAO.listFactyRiskAnayPractLoadDaily_anys_ymd(inMap);
	}

	public List<Map> listFactyRiskAnayPractLoadDaily_anys_sys_cd(Map inMap){
		logger.debug("getFactyRiskAnayPractLoadDaily_anys_sys_cd:"+inMap);
		return factyRiskAnayPractLoadDailyDAO.listFactyRiskAnayPractLoadDaily_anys_sys_cd(inMap);
	}

	public List<Map> listFactyRiskAnayPractLoadDaily_risk_type_id(Map inMap){
		logger.debug("getFactyRiskAnayPractLoadDaily_risk_type_id:"+inMap);
		return factyRiskAnayPractLoadDailyDAO.listFactyRiskAnayPractLoadDaily_risk_type_id(inMap);
	}

	public Map getFactyRiskAnayPractLoadDaily_facty_id(Map inMap){
		logger.debug("getFactyRiskAnayPractLoadDaily_facty_id:"+inMap);
		return factyRiskAnayPractLoadDailyDAO.getFactyRiskAnayPractLoadDaily_facty_id(inMap);
	}

	public List<Map> listFactyRiskAnayPractLoadDaily(Map inMap){
		logger.debug("listFactyRiskAnayPractLoadDaily:"+inMap);
		return factyRiskAnayPractLoadDailyDAO.listFactyRiskAnayPractLoadDaily(inMap);
	}

	public List<Map> pageFactyRiskAnayPractLoadDaily(Map inMap){
		logger.debug("pageFactyRiskAnayPractLoadDaily:"+inMap);
		return factyRiskAnayPractLoadDailyDAO.pageFactyRiskAnayPractLoadDaily(inMap);
	}

	public int countFactyRiskAnayPractLoadDaily(Map inMap){
		logger.debug("countFactyRiskAnayPractLoadDaily:"+inMap);
		return factyRiskAnayPractLoadDailyDAO.countFactyRiskAnayPractLoadDaily(inMap);
	}

}