package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface ModuleRiskAnlayStoreDAO {

	public int insertModuleRiskAnlayStore(Map inMap);
	public int deleteModuleRiskAnlayStore(Map inMap);
	public int updateModuleRiskAnlayStore(Map inMap);
	public Map getModuleRiskAnlayStore(Map inMap);
	public List<Map> listModuleRiskAnlayStore(Map inMap);
	public List<Map> pageModuleRiskAnlayStore(Map inMap);
	public int countModuleRiskAnlayStore(Map inMap);
}