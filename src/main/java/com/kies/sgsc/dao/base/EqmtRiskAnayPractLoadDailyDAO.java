package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface EqmtRiskAnayPractLoadDailyDAO {

	public int insertEqmtRiskAnayPractLoadDaily(Map inMap);
	public int deleteEqmtRiskAnayPractLoadDaily(Map inMap);
	public int updateEqmtRiskAnayPractLoadDaily(Map inMap);
	public List<Map> listEqmtRiskAnayPractLoadDaily_anys_ymd(Map inMap);
	public List<Map> listEqmtRiskAnayPractLoadDaily_anys_sys_cd(Map inMap);
	public Map getEqmtRiskAnayPractLoadDaily_risk_type_id(Map inMap);
	public List<Map> listEqmtRiskAnayPractLoadDaily(Map inMap);
	public List<Map> pageEqmtRiskAnayPractLoadDaily(Map inMap);
	public int countEqmtRiskAnayPractLoadDaily(Map inMap);
}