package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface FactyRiskAnayRtmeLoadHourlyDAO {

	public int insertFactyRiskAnayRtmeLoadHourly(Map inMap);
	public int deleteFactyRiskAnayRtmeLoadHourly(Map inMap);
	public int updateFactyRiskAnayRtmeLoadHourly(Map inMap);
	public List<Map> listFactyRiskAnayRtmeLoadHourly_anys_ymdh(Map inMap);
	public List<Map> listFactyRiskAnayRtmeLoadHourly_anys_sys_cd(Map inMap);
	public List<Map> listFactyRiskAnayRtmeLoadHourly_risk_type_id(Map inMap);
	public Map getFactyRiskAnayRtmeLoadHourly_facty_id(Map inMap);
	public List<Map> listFactyRiskAnayRtmeLoadHourly(Map inMap);
	public List<Map> pageFactyRiskAnayRtmeLoadHourly(Map inMap);
	public int countFactyRiskAnayRtmeLoadHourly(Map inMap);
}