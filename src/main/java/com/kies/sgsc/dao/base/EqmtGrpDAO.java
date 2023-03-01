package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface EqmtGrpDAO {

	public int insertEqmtGrp(Map inMap);
	public String getEqmtGrpKey();
	public int deleteEqmtGrp(Map inMap);
	public int updateEqmtGrp(Map inMap);
	public Map getEqmtGrp_grp_sid(Map inMap);
	public List<Map> listEqmtGrp(Map inMap);
	public List<Map> pageEqmtGrp(Map inMap);
	public int countEqmtGrp(Map inMap);
}