package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface NotiFileDAO {

	public int insertNotiFile(Map inMap);
	public int deleteNotiFile(Map inMap);
	public int updateNotiFile(Map inMap);
	public List<Map> listNotiFile_file_seq(Map inMap);
	public Map getNotiFile_noti_id(Map inMap);
	public List<Map> listNotiFile(Map inMap);
	public List<Map> pageNotiFile(Map inMap);
	public int countNotiFile(Map inMap);
}