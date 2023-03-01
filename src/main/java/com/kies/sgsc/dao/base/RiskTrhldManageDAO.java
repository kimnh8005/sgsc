package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface RiskTrhldManageDAO {

	public int insertRiskTrhldManage(Map inMap);
	public String getRiskTrhldManageKey();
	public int deleteRiskTrhldManage(Map inMap);
	public int updateRiskTrhldManage(Map inMap);
	public Map getRiskTrhldManage_trhld_sid(Map inMap);
	public List<Map> listRiskTrhldManage(Map inMap);
	public List<Map> listFactyRiskTrhld(Map inMap);
	
	public List<Map> pageRiskTrhldManage(Map inMap);
	public int countRiskTrhldManage(Map inMap);
	
	public List<Map> pageProcsRiskTrhld(Map inMap);
	public int countProcsRiskTrhld(Map inMap);
	
	public List<Map> pageEqmtRiskTrhld(Map inMap);
	public int countEqmtRiskTrhld(Map inMap);
	
	public Map getCalcRiskTrhld(Map inMap);
}