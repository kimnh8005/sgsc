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
import com.kies.sgsc.dao.base.ProcesRiskAnayPractLoadHourlyDAO;

@Service
public class ProcesRiskAnayPractLoadHourlyService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	ProcesRiskAnayPractLoadHourlyDAO procesRiskAnayPractLoadHourlyDAO;

	public int insertProcesRiskAnayPractLoadHourly(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertProcesRiskAnayPractLoadHourly:"+inMap);
		return procesRiskAnayPractLoadHourlyDAO.insertProcesRiskAnayPractLoadHourly(inMap);
	}

	public int deleteProcesRiskAnayPractLoadHourly(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteProcesRiskAnayPractLoadHourly:"+inMap);
		return procesRiskAnayPractLoadHourlyDAO.deleteProcesRiskAnayPractLoadHourly(inMap);
	}

	public int updateProcesRiskAnayPractLoadHourly(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateProcesRiskAnayPractLoadHourly:"+inMap);
		return procesRiskAnayPractLoadHourlyDAO.updateProcesRiskAnayPractLoadHourly(inMap);
	}

	public List<Map> listProcesRiskAnayPractLoadHourly_anys_ymdh(Map inMap){
		logger.debug("getProcesRiskAnayPractLoadHourly_anys_ymdh:"+inMap);
		return procesRiskAnayPractLoadHourlyDAO.listProcesRiskAnayPractLoadHourly_anys_ymdh(inMap);
	}

	public List<Map> listProcesRiskAnayPractLoadHourly_anys_sys_cd(Map inMap){
		logger.debug("getProcesRiskAnayPractLoadHourly_anys_sys_cd:"+inMap);
		return procesRiskAnayPractLoadHourlyDAO.listProcesRiskAnayPractLoadHourly_anys_sys_cd(inMap);
	}

	public List<Map> listProcesRiskAnayPractLoadHourly_risk_type_id(Map inMap){
		logger.debug("getProcesRiskAnayPractLoadHourly_risk_type_id:"+inMap);
		return procesRiskAnayPractLoadHourlyDAO.listProcesRiskAnayPractLoadHourly_risk_type_id(inMap);
	}

	public Map getProcesRiskAnayPractLoadHourly_procs_id(Map inMap){
		logger.debug("getProcesRiskAnayPractLoadHourly_procs_id:"+inMap);
		return procesRiskAnayPractLoadHourlyDAO.getProcesRiskAnayPractLoadHourly_procs_id(inMap);
	}

	public List<Map> listProcesRiskAnayPractLoadHourly(Map inMap){
		logger.debug("listProcesRiskAnayPractLoadHourly:"+inMap);
		return procesRiskAnayPractLoadHourlyDAO.listProcesRiskAnayPractLoadHourly(inMap);
	}

	public List<Map> pageProcesRiskAnayPractLoadHourly(Map inMap){
		logger.debug("pageProcesRiskAnayPractLoadHourly:"+inMap);
		return procesRiskAnayPractLoadHourlyDAO.pageProcesRiskAnayPractLoadHourly(inMap);
	}

	public int countProcesRiskAnayPractLoadHourly(Map inMap){
		logger.debug("countProcesRiskAnayPractLoadHourly:"+inMap);
		return procesRiskAnayPractLoadHourlyDAO.countProcesRiskAnayPractLoadHourly(inMap);
	}

}