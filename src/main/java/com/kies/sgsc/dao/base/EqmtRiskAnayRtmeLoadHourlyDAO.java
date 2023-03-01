package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface EqmtRiskAnayRtmeLoadHourlyDAO {

	public int insertEqmtRiskAnayRtmeLoadHourly(Map inMap);
	public int deleteEqmtRiskAnayRtmeLoadHourly(Map inMap);
	public int updateEqmtRiskAnayRtmeLoadHourly(Map inMap);
	public List<Map> listEqmtRiskAnayRtmeLoadHourly_anys_ymdh(Map inMap);
	public List<Map> listEqmtRiskAnayRtmeLoadHourly_anys_sys_cd(Map inMap);
	public List<Map> listEqmtRiskAnayRtmeLoadHourly_risk_type_id(Map inMap);
	public Map getEqmtRiskAnayRtmeLoadHourly_eqmt_id(Map inMap);
	public List<Map> listEqmtRiskAnayRtmeLoadHourly(Map inMap);
	public List<Map> pageEqmtRiskAnayRtmeLoadHourly(Map inMap);
	public int countEqmtRiskAnayRtmeLoadHourly(Map inMap);
}