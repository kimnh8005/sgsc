package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface HumanErrPrpsDAO {

	public int insertHumanErrPrps(Map inMap);
	public String getHumanErrPrpsKey();
	public int deleteHumanErrPrps(Map inMap);
	public int updateHumanErrPrps(Map inMap);
	public Map getHumanErrPrps_props_sid(Map inMap);
	public List<Map> listHumanErrPrps(Map inMap);
	public List<Map> pageHumanErrPrps(Map inMap);
	public int countHumanErrPrps(Map inMap);
	
	public List<Map> get7DayRiskRate(Map inMap);
	public Map getDayRiskRate(Map inMap);
	
	//휴먼에러제안 통합완료건수
	public List<Map> listHumanErrPrpsApp(Map inMap);
	
	public List<Map> pageHumanErrPrpsApp(Map inMap);
	public int countHumanErrPrpsApp(Map inMap);
}