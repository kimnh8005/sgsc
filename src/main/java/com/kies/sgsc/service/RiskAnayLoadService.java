package com.kies.sgsc.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kies.sgsc.dao.base.RiskAnyLoadDAO;

@Service
public class RiskAnayLoadService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	
	@Autowired
	RiskAnyLoadDAO riskAnyLoadDAO;

	public int insertThAcdntScnrsAnayLoadMin(Map inMap){

		return riskAnyLoadDAO.insertThAcdntScnrsAnayLoadMin(inMap);
	}
	
	public int insertThAcdntScnrsAnayLoadHourly(Map inMap) {
		
		return riskAnyLoadDAO.insertThAcdntScnrsAnayLoadHourly(inMap);
	}
	
	public int insertThAcdntScnrsAnayLoadDaily(Map inMap) {
		
		return riskAnyLoadDAO.insertThAcdntScnrsAnayLoadDaily(inMap);
	}
}
