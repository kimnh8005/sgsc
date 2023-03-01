package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface EmeryContactDAO {

	public int insertEmeryContact(Map inMap);
	public String getEmeryContactKey();
	public int deleteEmeryContact(Map inMap);
	public int updateEmeryContact(Map inMap);
	public Map getEmeryContact_emct_sid(Map inMap);
	public List<Map> listEmeryContact(Map inMap);
	public List<Map> pageEmeryContact(Map inMap);
	public int countEmeryContact(Map inMap);
}