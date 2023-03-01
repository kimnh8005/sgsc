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
import com.kies.sgsc.dao.base.FactyRiskAnayRtmeLoadHourlyDAO;

@Service
public class FactyRiskAnayRtmeLoadHourlyService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	FactyRiskAnayRtmeLoadHourlyDAO factyRiskAnayRtmeLoadHourlyDAO;

	public int insertFactyRiskAnayRtmeLoadHourly(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertFactyRiskAnayRtmeLoadHourly:"+inMap);
		return factyRiskAnayRtmeLoadHourlyDAO.insertFactyRiskAnayRtmeLoadHourly(inMap);
	}

	public int deleteFactyRiskAnayRtmeLoadHourly(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteFactyRiskAnayRtmeLoadHourly:"+inMap);
		return factyRiskAnayRtmeLoadHourlyDAO.deleteFactyRiskAnayRtmeLoadHourly(inMap);
	}

	public int updateFactyRiskAnayRtmeLoadHourly(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateFactyRiskAnayRtmeLoadHourly:"+inMap);
		return factyRiskAnayRtmeLoadHourlyDAO.updateFactyRiskAnayRtmeLoadHourly(inMap);
	}

	public List<Map> listFactyRiskAnayRtmeLoadHourly_anys_ymdh(Map inMap){
		logger.debug("getFactyRiskAnayRtmeLoadHourly_anys_ymdh:"+inMap);
		return factyRiskAnayRtmeLoadHourlyDAO.listFactyRiskAnayRtmeLoadHourly_anys_ymdh(inMap);
	}

	public List<Map> listFactyRiskAnayRtmeLoadHourly_anys_sys_cd(Map inMap){
		logger.debug("getFactyRiskAnayRtmeLoadHourly_anys_sys_cd:"+inMap);
		return factyRiskAnayRtmeLoadHourlyDAO.listFactyRiskAnayRtmeLoadHourly_anys_sys_cd(inMap);
	}

	public List<Map> listFactyRiskAnayRtmeLoadHourly_risk_type_id(Map inMap){
		logger.debug("getFactyRiskAnayRtmeLoadHourly_risk_type_id:"+inMap);
		return factyRiskAnayRtmeLoadHourlyDAO.listFactyRiskAnayRtmeLoadHourly_risk_type_id(inMap);
	}

	public Map getFactyRiskAnayRtmeLoadHourly_facty_id(Map inMap){
		logger.debug("getFactyRiskAnayRtmeLoadHourly_facty_id:"+inMap);
		return factyRiskAnayRtmeLoadHourlyDAO.getFactyRiskAnayRtmeLoadHourly_facty_id(inMap);
	}

	public List<Map> listFactyRiskAnayRtmeLoadHourly(Map inMap){
		logger.debug("listFactyRiskAnayRtmeLoadHourly:"+inMap);
		return factyRiskAnayRtmeLoadHourlyDAO.listFactyRiskAnayRtmeLoadHourly(inMap);
	}

	public List<Map> pageFactyRiskAnayRtmeLoadHourly(Map inMap){
		logger.debug("pageFactyRiskAnayRtmeLoadHourly:"+inMap);
		return factyRiskAnayRtmeLoadHourlyDAO.pageFactyRiskAnayRtmeLoadHourly(inMap);
	}

	public int countFactyRiskAnayRtmeLoadHourly(Map inMap){
		logger.debug("countFactyRiskAnayRtmeLoadHourly:"+inMap);
		return factyRiskAnayRtmeLoadHourlyDAO.countFactyRiskAnayRtmeLoadHourly(inMap);
	}

}