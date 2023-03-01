package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface ProcesRiskAnayRtmeLoadMinDAO {

	public int insertProcesRiskAnayRtmeLoadMin(Map inMap);
	public int deleteProcesRiskAnayRtmeLoadMin(Map inMap);
	public int updateProcesRiskAnayRtmeLoadMin(Map inMap);
	public List<Map> listProcesRiskAnayRtmeLoadMin_anys_ymdhi(Map inMap);
	public List<Map> listProcesRiskAnayRtmeLoadMin_anys_sys_cd(Map inMap);
	public List<Map> listProcesRiskAnayRtmeLoadMin_risk_type_id(Map inMap);
	public Map getProcesRiskAnayRtmeLoadMin_procs_id(Map inMap);
	public List<Map> listProcesRiskAnayRtmeLoadMin(Map inMap);
	public List<Map> pageProcesRiskAnayRtmeLoadMin(Map inMap);
	public int countProcesRiskAnayRtmeLoadMin(Map inMap);
	
	public List<Map> listEqmtLoadMinSimulRiskNum(Map inMap);
}