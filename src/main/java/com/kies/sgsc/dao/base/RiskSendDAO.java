package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface RiskSendDAO {

	public int insertRiskSend(Map inMap);
	public int deleteRiskSend(Map inMap);
	public int updateRiskSend(Map inMap);
	public List<Map> listRiskSend_send_rnd(Map inMap);
	public Map getRiskSend_send_sid(Map inMap);
	public List<Map> listRiskSend(Map inMap);
	public List<Map> pageRiskSend(Map inMap);
	public int countRiskSend(Map inMap);
}