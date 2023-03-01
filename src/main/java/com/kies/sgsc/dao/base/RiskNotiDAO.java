package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface RiskNotiDAO {

	public int insertRiskNoti(Map inMap);
	public String getRiskNotiKey();
	public int deleteRiskNoti(Map inMap);
	public int updateRiskNoti(Map inMap);
	public Map getRiskNoti_send_sid(Map inMap);
	public List<Map> listRiskNoti(Map inMap);
	public List<Map> pageRiskNoti(Map inMap);
	public int countRiskNoti(Map inMap);
}