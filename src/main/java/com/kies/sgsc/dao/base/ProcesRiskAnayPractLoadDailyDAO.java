package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface ProcesRiskAnayPractLoadDailyDAO {

	public int insertProcesRiskAnayPractLoadDaily(Map inMap);
	public int deleteProcesRiskAnayPractLoadDaily(Map inMap);
	public int updateProcesRiskAnayPractLoadDaily(Map inMap);
	public List<Map> listProcesRiskAnayPractLoadDaily_anys_ymd(Map inMap);
	public List<Map> listProcesRiskAnayPractLoadDaily_anys_sys_cd(Map inMap);
	public List<Map> listProcesRiskAnayPractLoadDaily_risk_type_id(Map inMap);
	public Map getProcesRiskAnayPractLoadDaily_procs_id(Map inMap);
	public List<Map> listProcesRiskAnayPractLoadDaily(Map inMap);
	public List<Map> pageProcesRiskAnayPractLoadDaily(Map inMap);
	public int countProcesRiskAnayPractLoadDaily(Map inMap);
}