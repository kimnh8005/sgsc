package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface ProcesRiskAnayPractLoadHourlyDAO {

	public int insertProcesRiskAnayPractLoadHourly(Map inMap);
	public int deleteProcesRiskAnayPractLoadHourly(Map inMap);
	public int updateProcesRiskAnayPractLoadHourly(Map inMap);
	public List<Map> listProcesRiskAnayPractLoadHourly_anys_ymdh(Map inMap);
	public List<Map> listProcesRiskAnayPractLoadHourly_anys_sys_cd(Map inMap);
	public List<Map> listProcesRiskAnayPractLoadHourly_risk_type_id(Map inMap);
	public Map getProcesRiskAnayPractLoadHourly_procs_id(Map inMap);
	public List<Map> listProcesRiskAnayPractLoadHourly(Map inMap);
	public List<Map> pageProcesRiskAnayPractLoadHourly(Map inMap);
	public int countProcesRiskAnayPractLoadHourly(Map inMap);
}