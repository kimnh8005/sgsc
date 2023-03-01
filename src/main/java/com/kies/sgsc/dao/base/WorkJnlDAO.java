package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface WorkJnlDAO {

	public int insertWorkJnl(Map inMap);
	public String getWorkJnlKey();
	public int deleteWorkJnl(Map inMap);
	public int updateWorkJnl(Map inMap);
	public Map getWorkJnl_jnl_sid(Map inMap);
	public List<Map> listWorkJnl(Map inMap);
	public List<Map> pageWorkJnl(Map inMap);
	public int countWorkJnl(Map inMap);
}