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
import com.kies.sgsc.dao.base.ProcesRiskAnayRtmeLoadHourlyDAO;

@Service
public class ProcesRiskAnayRtmeLoadHourlyService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	ProcesRiskAnayRtmeLoadHourlyDAO procesRiskAnayRtmeLoadHourlyDAO;

	public int insertProcesRiskAnayRtmeLoadHourly(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertProcesRiskAnayRtmeLoadHourly:"+inMap);
		return procesRiskAnayRtmeLoadHourlyDAO.insertProcesRiskAnayRtmeLoadHourly(inMap);
	}

	public int deleteProcesRiskAnayRtmeLoadHourly(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteProcesRiskAnayRtmeLoadHourly:"+inMap);
		return procesRiskAnayRtmeLoadHourlyDAO.deleteProcesRiskAnayRtmeLoadHourly(inMap);
	}

	public int updateProcesRiskAnayRtmeLoadHourly(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateProcesRiskAnayRtmeLoadHourly:"+inMap);
		return procesRiskAnayRtmeLoadHourlyDAO.updateProcesRiskAnayRtmeLoadHourly(inMap);
	}

	public List<Map> listProcesRiskAnayRtmeLoadHourly_anys_ymdh(Map inMap){
		logger.debug("getProcesRiskAnayRtmeLoadHourly_anys_ymdh:"+inMap);
		return procesRiskAnayRtmeLoadHourlyDAO.listProcesRiskAnayRtmeLoadHourly_anys_ymdh(inMap);
	}

	public List<Map> listProcesRiskAnayRtmeLoadHourly_anys_sys_cd(Map inMap){
		logger.debug("getProcesRiskAnayRtmeLoadHourly_anys_sys_cd:"+inMap);
		return procesRiskAnayRtmeLoadHourlyDAO.listProcesRiskAnayRtmeLoadHourly_anys_sys_cd(inMap);
	}

	public List<Map> listProcesRiskAnayRtmeLoadHourly_risk_type_id(Map inMap){
		logger.debug("getProcesRiskAnayRtmeLoadHourly_risk_type_id:"+inMap);
		return procesRiskAnayRtmeLoadHourlyDAO.listProcesRiskAnayRtmeLoadHourly_risk_type_id(inMap);
	}

	public Map getProcesRiskAnayRtmeLoadHourly_procs_id(Map inMap){
		logger.debug("getProcesRiskAnayRtmeLoadHourly_procs_id:"+inMap);
		return procesRiskAnayRtmeLoadHourlyDAO.getProcesRiskAnayRtmeLoadHourly_procs_id(inMap);
	}

	public List<Map> listProcesRiskAnayRtmeLoadHourly(Map inMap){
		logger.debug("listProcesRiskAnayRtmeLoadHourly:"+inMap);
		return procesRiskAnayRtmeLoadHourlyDAO.listProcesRiskAnayRtmeLoadHourly(inMap);
	}

	public List<Map> pageProcesRiskAnayRtmeLoadHourly(Map inMap){
		logger.debug("pageProcesRiskAnayRtmeLoadHourly:"+inMap);
		return procesRiskAnayRtmeLoadHourlyDAO.pageProcesRiskAnayRtmeLoadHourly(inMap);
	}

	public int countProcesRiskAnayRtmeLoadHourly(Map inMap){
		logger.debug("countProcesRiskAnayRtmeLoadHourly:"+inMap);
		return procesRiskAnayRtmeLoadHourlyDAO.countProcesRiskAnayRtmeLoadHourly(inMap);
	}

}