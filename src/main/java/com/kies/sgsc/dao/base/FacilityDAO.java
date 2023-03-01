package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface FacilityDAO {

	public int insertFacility(Map inMap);
	public String getFacilityKey();
	public int deleteFacility(Map inMap);
	public int updateFacility(Map inMap);
	public Map getFacility_facty_id(Map inMap);
	public List<Map> listFacility(Map inMap);
	public List<Map> pageFacility(Map inMap);
	public int countFacility(Map inMap);
}