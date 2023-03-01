package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface EqmtRiskAnayPractLoadHourlyDAO {

	public int insertEqmtRiskAnayPractLoadHourly(Map inMap);
	public int deleteEqmtRiskAnayPractLoadHourly(Map inMap);
	public int updateEqmtRiskAnayPractLoadHourly(Map inMap);
	public List<Map> listEqmtRiskAnayPractLoadHourly_anys_ymdh(Map inMap);
	public List<Map> listEqmtRiskAnayPractLoadHourly_anys_sys_cd(Map inMap);
	public List<Map> listEqmtRiskAnayPractLoadHourly_risk_type_id(Map inMap);
	public Map getEqmtRiskAnayPractLoadHourly_eqmt_id(Map inMap);
	public List<Map> listEqmtRiskAnayPractLoadHourly(Map inMap);
	public List<Map> pageEqmtRiskAnayPractLoadHourly(Map inMap);
	public int countEqmtRiskAnayPractLoadHourly(Map inMap);
}