package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface EqmtRiskAnayRtmeLoadMinDAO {

	public int insertEqmtRiskAnayRtmeLoadMin(Map inMap);
	public int deleteEqmtRiskAnayRtmeLoadMin(Map inMap);
	public int updateEqmtRiskAnayRtmeLoadMin(Map inMap);
	public List<Map> listEqmtRiskAnayRtmeLoadMin_anys_ymdhi(Map inMap);
	public List<Map> listEqmtRiskAnayRtmeLoadMin_anys_sys_cd(Map inMap);
	public List<Map> listEqmtRiskAnayRtmeLoadMin_risk_type_id(Map inMap);
	public Map getEqmtRiskAnayRtmeLoadMin_eqmt_id(Map inMap);
	public List<Map> listEqmtRiskAnayRtmeLoadMin(Map inMap);
	public List<Map> pageEqmtRiskAnayRtmeLoadMin(Map inMap);
	public int countEqmtRiskAnayRtmeLoadMin(Map inMap);
}