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
import com.kies.sgsc.dao.base.ProcesRiskAnayPractLoadDailyDAO;

@Service
public class ProcesRiskAnayPractLoadDailyService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	ProcesRiskAnayPractLoadDailyDAO procesRiskAnayPractLoadDailyDAO;

	public int insertProcesRiskAnayPractLoadDaily(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertProcesRiskAnayPractLoadDaily:"+inMap);
		return procesRiskAnayPractLoadDailyDAO.insertProcesRiskAnayPractLoadDaily(inMap);
	}

	public int deleteProcesRiskAnayPractLoadDaily(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteProcesRiskAnayPractLoadDaily:"+inMap);
		return procesRiskAnayPractLoadDailyDAO.deleteProcesRiskAnayPractLoadDaily(inMap);
	}

	public int updateProcesRiskAnayPractLoadDaily(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateProcesRiskAnayPractLoadDaily:"+inMap);
		return procesRiskAnayPractLoadDailyDAO.updateProcesRiskAnayPractLoadDaily(inMap);
	}

	public List<Map> listProcesRiskAnayPractLoadDaily_anys_ymd(Map inMap){
		logger.debug("getProcesRiskAnayPractLoadDaily_anys_ymd:"+inMap);
		return procesRiskAnayPractLoadDailyDAO.listProcesRiskAnayPractLoadDaily_anys_ymd(inMap);
	}

	public List<Map> listProcesRiskAnayPractLoadDaily_anys_sys_cd(Map inMap){
		logger.debug("getProcesRiskAnayPractLoadDaily_anys_sys_cd:"+inMap);
		return procesRiskAnayPractLoadDailyDAO.listProcesRiskAnayPractLoadDaily_anys_sys_cd(inMap);
	}

	public List<Map> listProcesRiskAnayPractLoadDaily_risk_type_id(Map inMap){
		logger.debug("getProcesRiskAnayPractLoadDaily_risk_type_id:"+inMap);
		return procesRiskAnayPractLoadDailyDAO.listProcesRiskAnayPractLoadDaily_risk_type_id(inMap);
	}

	public Map getProcesRiskAnayPractLoadDaily_procs_id(Map inMap){
		logger.debug("getProcesRiskAnayPractLoadDaily_procs_id:"+inMap);
		return procesRiskAnayPractLoadDailyDAO.getProcesRiskAnayPractLoadDaily_procs_id(inMap);
	}

	public List<Map> listProcesRiskAnayPractLoadDaily(Map inMap){
		logger.debug("listProcesRiskAnayPractLoadDaily:"+inMap);
		return procesRiskAnayPractLoadDailyDAO.listProcesRiskAnayPractLoadDaily(inMap);
	}

	public List<Map> pageProcesRiskAnayPractLoadDaily(Map inMap){
		logger.debug("pageProcesRiskAnayPractLoadDaily:"+inMap);
		return procesRiskAnayPractLoadDailyDAO.pageProcesRiskAnayPractLoadDaily(inMap);
	}

	public int countProcesRiskAnayPractLoadDaily(Map inMap){
		logger.debug("countProcesRiskAnayPractLoadDaily:"+inMap);
		return procesRiskAnayPractLoadDailyDAO.countProcesRiskAnayPractLoadDaily(inMap);
	}

}