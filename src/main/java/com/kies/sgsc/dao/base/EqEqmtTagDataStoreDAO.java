package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface EqEqmtTagDataStoreDAO {

	public int insertEqEqmtTagDataStore(Map inMap);
	public int deleteEqEqmtTagDataStore(Map inMap);
	public int updateEqEqmtTagDataStore(Map inMap);
	public List<Map> listEqEqmtTagDataStore_rise_time(Map inMap);
	public Map getEqEqmtTagDataStore_meeq_id(Map inMap);
	public List<Map> listEqEqmtTagDataStore(Map inMap);
	public List<Map> pageEqEqmtTagDataStore(Map inMap);
	public int countEqEqmtTagDataStore(Map inMap);
}