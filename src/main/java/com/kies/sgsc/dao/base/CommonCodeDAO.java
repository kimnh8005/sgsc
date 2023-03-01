package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface CommonCodeDAO {

	public int insertCommonCode(Map inMap);
	public int deleteCommonCode(Map inMap);
	public int updateCommonCode(Map inMap);
	public List<Map> listCommonCode_cd_grp_id(Map inMap);
	public Map getCommonCode_cd_id(Map inMap);
	public List<Map> listCommonCode(Map inMap);
	public List<Map> pageCommonCode(Map inMap);
	public int countCommonCode(Map inMap);
}