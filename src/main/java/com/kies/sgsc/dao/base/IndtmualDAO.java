package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface IndtmualDAO {

	public int insertIndtmual(Map inMap);
	public String getIndtmualKey();
	public int deleteIndtmual(Map inMap);
	public int updateIndtmual(Map inMap);
	public Map getIndtmual_mual_sid(Map inMap);
	public Map getPrevIndtmual_mual_sid(Map inMap);
	public Map getNextIndtmual_mual_sid(Map inMap);
	public List<Map> listIndtmual(Map inMap);
	public List<Map> pageIndtmual(Map inMap);
	public int countIndtmual(Map inMap);
}