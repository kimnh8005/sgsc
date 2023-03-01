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
import com.kies.sgsc.dao.base.EqmtTagDAO;

@Service
public class EqmtTagService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	EqmtTagDAO eqmtTagDAO;

	public int insertEqmtTag(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertEqmtTag:"+inMap);
		return eqmtTagDAO.insertEqmtTag(inMap);
	}

	public String getEqmtTagKey() {
		return eqmtTagDAO.getEqmtTagKey();
	}

	public int deleteEqmtTag(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteEqmtTag:"+inMap);
		return eqmtTagDAO.deleteEqmtTag(inMap);
	}

	public int updateEqmtTag(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateEqmtTag:"+inMap);
		return eqmtTagDAO.updateEqmtTag(inMap);
	}

	public Map getEqmtTag_meeq_id(Map inMap){
		logger.debug("getEqmtTag_meeq_id:"+inMap);
		return eqmtTagDAO.getEqmtTag_meeq_id(inMap);
	}

	public List<Map> listEqmtTag(Map inMap){
		logger.debug("listEqmtTag:"+inMap);
		return eqmtTagDAO.listEqmtTag(inMap);
	}

	public List<Map> pageEqmtTag(Map inMap){
		logger.debug("pageEqmtTag:"+inMap);
		return eqmtTagDAO.pageEqmtTag(inMap);
	}

	public int countEqmtTag(Map inMap){
		logger.debug("countEqmtTag:"+inMap);
		return eqmtTagDAO.countEqmtTag(inMap);
	}

}