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
import com.kies.sgsc.dao.base.HumanErrPrpsDAO;

@Service
public class HumanErrPrpsService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	HumanErrPrpsDAO humanErrPrpsDAO;

	public int insertHumanErrPrps(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertHumanErrPrps:"+inMap);
		return humanErrPrpsDAO.insertHumanErrPrps(inMap);
	}

	public String getHumanErrPrpsKey() {
		return humanErrPrpsDAO.getHumanErrPrpsKey();
	}

	public int deleteHumanErrPrps(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteHumanErrPrps:"+inMap);
		return humanErrPrpsDAO.deleteHumanErrPrps(inMap);
	}

	public int updateHumanErrPrps(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateHumanErrPrps:"+inMap);
		return humanErrPrpsDAO.updateHumanErrPrps(inMap);
	}

	public Map getHumanErrPrps_props_sid(Map inMap){
		logger.debug("getHumanErrPrps_props_sid:"+inMap);
		return humanErrPrpsDAO.getHumanErrPrps_props_sid(inMap);
	}

	public List<Map> listHumanErrPrps(Map inMap){
		logger.debug("listHumanErrPrps:"+inMap);
		return humanErrPrpsDAO.listHumanErrPrps(inMap);
	}
	
	public List<Map> get7DayRiskRate(Map inMap){
		logger.debug("get7DayRiskRate:"+inMap);
		return humanErrPrpsDAO.get7DayRiskRate(inMap);
	}
	
	public Map getDayRiskRate(Map inMap){
		logger.debug("getDayRiskRate:"+inMap);
		return humanErrPrpsDAO.getDayRiskRate(inMap);
	}
	

	public List<Map> pageHumanErrPrps(Map inMap){
		logger.debug("pageHumanErrPrps:"+inMap);
		return humanErrPrpsDAO.pageHumanErrPrps(inMap);
	}

	public int countHumanErrPrps(Map inMap){
		logger.debug("countHumanErrPrps:"+inMap);
		return humanErrPrpsDAO.countHumanErrPrps(inMap);
	}

	
	//휴먼에러제안 통합완료건수
	public List<Map> listHumanErrPrpsApp(Map inMap){
		logger.debug("listHumanErrPrpsApp:"+inMap);
		return humanErrPrpsDAO.listHumanErrPrpsApp(inMap);
	}
	
	public List<Map> pageHumanErrPrpsApp(Map inMap){
		logger.debug("pageHumanErrPrpsApp:"+inMap);
		return humanErrPrpsDAO.pageHumanErrPrpsApp(inMap);
	}

	public int countHumanErrPrpsApp(Map inMap){
		logger.debug("countHumanErrPrpsApp:"+inMap);
		return humanErrPrpsDAO.countHumanErrPrpsApp(inMap);
	}
}