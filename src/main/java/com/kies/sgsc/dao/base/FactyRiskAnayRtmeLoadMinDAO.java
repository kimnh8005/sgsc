package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface FactyRiskAnayRtmeLoadMinDAO {

	public int insertFactyRiskAnayRtmeLoadMin(Map inMap);
	public int deleteFactyRiskAnayRtmeLoadMin(Map inMap);
	public int updateFactyRiskAnayRtmeLoadMin(Map inMap);
	public List<Map> listFactyRiskAnayRtmeLoadMin_anys_ymdhi(Map inMap);
	public List<Map> listFactyRiskAnayRtmeLoadMin_anys_sys_cd(Map inMap);
	public List<Map> listFactyRiskAnayRtmeLoadMin_risk_type_id(Map inMap);
	public Map getFactyRiskAnayRtmeLoadMin_facty_id(Map inMap);
	public List<Map> listFactyRiskAnayRtmeLoadMin(Map inMap);
	public List<Map> pageFactyRiskAnayRtmeLoadMin(Map inMap);
	public int countFactyRiskAnayRtmeLoadMin(Map inMap);
	
	
	public List<Map> listProcsLoadMinSimulRiskNum(Map inMap);
	
}