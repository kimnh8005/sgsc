package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface FactyRiskAnayPractLoadHourlyDAO {

	public int insertFactyRiskAnayPractLoadHourly(Map inMap);
	public int deleteFactyRiskAnayPractLoadHourly(Map inMap);
	public int updateFactyRiskAnayPractLoadHourly(Map inMap);
	public List<Map> listFactyRiskAnayPractLoadHourly_anys_ymdh(Map inMap);
	public List<Map> listFactyRiskAnayPractLoadHourly_anys_sys_cd(Map inMap);
	public List<Map> listFactyRiskAnayPractLoadHourly_risk_type_id(Map inMap);
	public Map getFactyRiskAnayPractLoadHourly_facty_id(Map inMap);
	public List<Map> listFactyRiskAnayPractLoadHourly(Map inMap);
	public List<Map> pageFactyRiskAnayPractLoadHourly(Map inMap);
	public int countFactyRiskAnayPractLoadHourly(Map inMap);
}