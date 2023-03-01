package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface AuthDAO {

	public int insertAuth(Map inMap);
	public String getAuthKey();
	public int deleteAuth(Map inMap);
	public int updateAuth(Map inMap);
	public Map getAuth_auth_sid(Map inMap);
	public List<Map> listAuth(Map inMap);
	public List<Map> pageAuth(Map inMap);
	public int countAuth(Map inMap);
}