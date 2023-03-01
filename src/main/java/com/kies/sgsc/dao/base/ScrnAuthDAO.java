package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface ScrnAuthDAO {

	public int insertScrnAuth(Map inMap);
	public String getScrnAuthKey();
	public int deleteScrnAuth(Map inMap);
	public int updateScrnAuth(Map inMap);
	public Map getScrnAuth_scrn_id(Map inMap);
	public List<Map> listScrnAuth(Map inMap);
	public List<Map> pageScrnAuth(Map inMap);
	public int countScrnAuth(Map inMap);
}