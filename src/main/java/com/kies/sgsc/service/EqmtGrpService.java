package com.kies.sgsc.service;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.kies.sgsc.comm.util.Utils;
import com.kies.sgsc.dao.base.EqmtGrpDAO;

@Service
public class EqmtGrpService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	EqmtGrpDAO eqmtGrpDAO;

	public int insertEqmtGrp(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertEqmtGrp:"+inMap);
		return eqmtGrpDAO.insertEqmtGrp(inMap);
	}

	public String getEqmtGrpKey() {
		return eqmtGrpDAO.getEqmtGrpKey();
	}

	public int deleteEqmtGrp(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteEqmtGrp:"+inMap);
		return eqmtGrpDAO.deleteEqmtGrp(inMap);
	}

	public int updateEqmtGrp(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateEqmtGrp:"+inMap);
		return eqmtGrpDAO.updateEqmtGrp(inMap);
	}

	public Map getEqmtGrp_grp_sid(Map inMap){
		logger.debug("getEqmtGrp_grp_sid:"+inMap);
		return eqmtGrpDAO.getEqmtGrp_grp_sid(inMap);
	}

	public List<Map> listEqmtGrp(Map inMap){
		logger.debug("listEqmtGrp:"+inMap);
		return eqmtGrpDAO.listEqmtGrp(inMap);
	}

	public List<Map> pageEqmtGrp(Map inMap){
		logger.debug("pageEqmtGrp:"+inMap);
		return eqmtGrpDAO.pageEqmtGrp(inMap);
	}

	public int countEqmtGrp(Map inMap){
		logger.debug("countEqmtGrp:"+inMap);
		return eqmtGrpDAO.countEqmtGrp(inMap);
	}

}