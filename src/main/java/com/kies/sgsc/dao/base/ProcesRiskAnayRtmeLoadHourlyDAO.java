package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface ProcesRiskAnayRtmeLoadHourlyDAO {

	public int insertProcesRiskAnayRtmeLoadHourly(Map inMap);
	public int deleteProcesRiskAnayRtmeLoadHourly(Map inMap);
	public int updateProcesRiskAnayRtmeLoadHourly(Map inMap);
	public List<Map> listProcesRiskAnayRtmeLoadHourly_anys_ymdh(Map inMap);
	public List<Map> listProcesRiskAnayRtmeLoadHourly_anys_sys_cd(Map inMap);
	public List<Map> listProcesRiskAnayRtmeLoadHourly_risk_type_id(Map inMap);
	public Map getProcesRiskAnayRtmeLoadHourly_procs_id(Map inMap);
	public List<Map> listProcesRiskAnayRtmeLoadHourly(Map inMap);
	public List<Map> pageProcesRiskAnayRtmeLoadHourly(Map inMap);
	public int countProcesRiskAnayRtmeLoadHourly(Map inMap);
}