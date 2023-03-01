package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface EqmtTagDAO {

	public int insertEqmtTag(Map inMap);
	public String getEqmtTagKey();
	public int deleteEqmtTag(Map inMap);
	public int updateEqmtTag(Map inMap);
	public Map getEqmtTag_meeq_id(Map inMap);
	public List<Map> listEqmtTag(Map inMap);
	public List<Map> pageEqmtTag(Map inMap);
	public int countEqmtTag(Map inMap);
}