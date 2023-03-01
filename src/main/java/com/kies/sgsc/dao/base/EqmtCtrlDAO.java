package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface EqmtCtrlDAO {

	public int insertEqmtCtrlHistory(Map inMap);
	public List<Map> getEqmtCtrlStatusHistory();

}