package com.kies.sgsc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kies.sgsc.comm.util.Utils;
import com.kies.sgsc.dao.base.GasRiskPrpsDAO;
import com.kies.sgsc.dao.base.RiskTypeDAO;

@Service
public class GasRiskPrpsService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	GasRiskPrpsDAO gasRiskPrpsDAO;
	
	@Autowired
	RiskTypeDAO riskTypeDAO;
	

	public int insertGasRiskPrps(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertGasRiskPrps:"+inMap);
		return gasRiskPrpsDAO.insertGasRiskPrps(inMap);
	}

	public String getGasRiskPrpsKey() {
		return gasRiskPrpsDAO.getGasRiskPrpsKey();
	}

	public int deleteGasRiskPrps(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteGasRiskPrps:"+inMap);
		return gasRiskPrpsDAO.deleteGasRiskPrps(inMap);
	}

	public int updateGasRiskPrps(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateGasRiskPrps:"+inMap);
		return gasRiskPrpsDAO.updateGasRiskPrps(inMap);
	}

	public Map getGasRiskPrps_props_sid(Map inMap){
		logger.debug("getGasRiskPrps_props_sid:"+inMap);
		Map tinMap = new HashMap();
		tinMap.put("risk_cls_cd", "RC0002"); //이상원인
		String causeQuery = Utils.makeCauseQuery(riskTypeDAO.listRiskType(tinMap));
		inMap.put("causequery",causeQuery);
		logger.debug("causeQuery:"+causeQuery);
		
		return gasRiskPrpsDAO.getGasRiskPrps_props_sid(inMap);
	}

	public List<Map> listGasRiskPrps(Map inMap){
		logger.debug("listGasRiskPrps:"+inMap);
		Map tinMap = new HashMap();
		tinMap.put("risk_cls_cd", "RC0002"); //이상원인
		String causeQuery = Utils.makeCauseQuery(riskTypeDAO.listRiskType(tinMap));
		inMap.put("causequery",causeQuery);
		logger.debug("causeQuery:"+causeQuery);
		
		List<Map> resEqumtTop5 =  gasRiskPrpsDAO.listGasRiskPrps(inMap);
		return resEqumtTop5;
	}

	public List<Map> pageGasRiskPrps(Map inMap){
		logger.debug("pageGasRiskPrps:"+inMap);
		
		Map tinMap = new HashMap();
		tinMap.put("risk_cls_cd", "RC0002"); //이상원인
		String causeQuery = Utils.makeCauseQuery(riskTypeDAO.listRiskType(tinMap));
		inMap.put("causequery",causeQuery);
		//logger.debug("causeQuery:"+causeQuery);
		
		tinMap.put("risk_cls_cd", "RC0007"); //이상원인
		String causeconcatquery = Utils.makeConCatCauseQuery(riskTypeDAO.listRiskType(tinMap),"risk_cause");
		
		inMap.put("causeconcatquery",causeconcatquery);
		
		List<Map> resEqumtTop5 = gasRiskPrpsDAO.pageGasRiskPrps(inMap);
		
		
		for(Map oMap : resEqumtTop5) {
			String riskStr = Utils.NVL(oMap.get("risk_cause")).trim();
			if(riskStr.startsWith(",")) {
				riskStr = riskStr.substring(1,riskStr.length());
			}
			if(riskStr.endsWith(",")) {
				riskStr = riskStr.substring(0,riskStr.length()-1);
			}
			oMap.put("risk_cause",riskStr);
		}
		
		
		return resEqumtTop5;
	}

	public int countGasRiskPrps(Map inMap){
		logger.debug("countGasRiskPrps:"+inMap);
		return gasRiskPrpsDAO.countGasRiskPrps(inMap);
	}

}