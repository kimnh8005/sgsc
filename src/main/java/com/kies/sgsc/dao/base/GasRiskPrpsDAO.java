package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface GasRiskPrpsDAO {

	public int insertGasRiskPrps(Map inMap);
	public String getGasRiskPrpsKey();
	public int deleteGasRiskPrps(Map inMap);
	public int updateGasRiskPrps(Map inMap);
	public Map getGasRiskPrps_props_sid(Map inMap);
	public List<Map> listGasRiskPrps(Map inMap);
	public List<Map> pageGasRiskPrps(Map inMap);
	public int countGasRiskPrps(Map inMap);
}