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
import com.kies.sgsc.dao.base.ProcesRiskAnayRtmeLoadMinDAO;

@Service
public class ProcesRiskAnayRtmeLoadMinService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	ProcesRiskAnayRtmeLoadMinDAO procesRiskAnayRtmeLoadMinDAO;

	public int insertProcesRiskAnayRtmeLoadMin(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertProcesRiskAnayRtmeLoadMin:"+inMap);
		return procesRiskAnayRtmeLoadMinDAO.insertProcesRiskAnayRtmeLoadMin(inMap);
	}

	public int deleteProcesRiskAnayRtmeLoadMin(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteProcesRiskAnayRtmeLoadMin:"+inMap);
		return procesRiskAnayRtmeLoadMinDAO.deleteProcesRiskAnayRtmeLoadMin(inMap);
	}

	public int updateProcesRiskAnayRtmeLoadMin(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateProcesRiskAnayRtmeLoadMin:"+inMap);
		return procesRiskAnayRtmeLoadMinDAO.updateProcesRiskAnayRtmeLoadMin(inMap);
	}

	public List<Map> listProcesRiskAnayRtmeLoadMin_anys_ymdhi(Map inMap){
		logger.debug("getProcesRiskAnayRtmeLoadMin_anys_ymdhi:"+inMap);
		return procesRiskAnayRtmeLoadMinDAO.listProcesRiskAnayRtmeLoadMin_anys_ymdhi(inMap);
	}

	public List<Map> listProcesRiskAnayRtmeLoadMin_anys_sys_cd(Map inMap){
		logger.debug("getProcesRiskAnayRtmeLoadMin_anys_sys_cd:"+inMap);
		return procesRiskAnayRtmeLoadMinDAO.listProcesRiskAnayRtmeLoadMin_anys_sys_cd(inMap);
	}

	public List<Map> listProcesRiskAnayRtmeLoadMin_risk_type_id(Map inMap){
		logger.debug("getProcesRiskAnayRtmeLoadMin_risk_type_id:"+inMap);
		return procesRiskAnayRtmeLoadMinDAO.listProcesRiskAnayRtmeLoadMin_risk_type_id(inMap);
	}

	public Map getProcesRiskAnayRtmeLoadMin_procs_id(Map inMap){
		logger.debug("getProcesRiskAnayRtmeLoadMin_procs_id:"+inMap);
		return procesRiskAnayRtmeLoadMinDAO.getProcesRiskAnayRtmeLoadMin_procs_id(inMap);
	}

	public List<Map> listProcesRiskAnayRtmeLoadMin(Map inMap){
		logger.debug("listProcesRiskAnayRtmeLoadMin:"+inMap);
		return procesRiskAnayRtmeLoadMinDAO.listProcesRiskAnayRtmeLoadMin(inMap);
	}

	public List<Map> pageProcesRiskAnayRtmeLoadMin(Map inMap){
		logger.debug("pageProcesRiskAnayRtmeLoadMin:"+inMap);
		return procesRiskAnayRtmeLoadMinDAO.pageProcesRiskAnayRtmeLoadMin(inMap);
	}

	public int countProcesRiskAnayRtmeLoadMin(Map inMap){
		logger.debug("countProcesRiskAnayRtmeLoadMin:"+inMap);
		return procesRiskAnayRtmeLoadMinDAO.countProcesRiskAnayRtmeLoadMin(inMap);
	}
	
	public List<Map> listEqmtLoadMinSimulRiskNum(Map inMap){
		logger.debug("listEqmtLoadMinSimulRiskNum:"+inMap);
		return procesRiskAnayRtmeLoadMinDAO.listEqmtLoadMinSimulRiskNum(inMap);
	}

}