package com.kies.sgsc.dao.base;

import java.util.Map;

public interface RiskAnyLoadDAO {
	
	public int insertThAcdntScnrsAnayLoadMin(Map inMap);
	public int insertThAcdntScnrsAnayLoadHourly(Map inMap);
	public int insertThAcdntScnrsAnayLoadDaily(Map inMap);
	
	

}
