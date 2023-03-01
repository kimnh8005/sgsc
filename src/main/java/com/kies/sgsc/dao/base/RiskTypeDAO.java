package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface RiskTypeDAO {

	public int insertRiskType(Map inMap);
	public String getRiskTypeKey();
	public int deleteRiskType(Map inMap);
	public int updateRiskType(Map inMap);
	public Map getRiskType_risk_type_id(Map inMap);
	public List<Map> listRiskType(Map inMap);
	public List<Map> pageRiskType(Map inMap);
	public int countRiskType(Map inMap);
}