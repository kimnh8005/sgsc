package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface ProcessDAO {

	public int insertProcess(Map inMap);
	public String getProcessKey();
	public int deleteProcess(Map inMap);
	public int updateProcess(Map inMap);
	public Map getProcess_procs_id(Map inMap);
	public List<Map> listProcess(Map inMap);
	public List<Map> pageProcess(Map inMap);
	public int countProcess(Map inMap);
}