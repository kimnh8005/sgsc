package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface FactyRiskAnayPractLoadDailyDAO {

	public int insertFactyRiskAnayPractLoadDaily(Map inMap);
	public int deleteFactyRiskAnayPractLoadDaily(Map inMap);
	public int updateFactyRiskAnayPractLoadDaily(Map inMap);
	public List<Map> listFactyRiskAnayPractLoadDaily_anys_ymd(Map inMap);
	public List<Map> listFactyRiskAnayPractLoadDaily_anys_sys_cd(Map inMap);
	public List<Map> listFactyRiskAnayPractLoadDaily_risk_type_id(Map inMap);
	public Map getFactyRiskAnayPractLoadDaily_facty_id(Map inMap);
	public List<Map> listFactyRiskAnayPractLoadDaily(Map inMap);
	public List<Map> pageFactyRiskAnayPractLoadDaily(Map inMap);
	public int countFactyRiskAnayPractLoadDaily(Map inMap);
}