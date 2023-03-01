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
import com.kies.sgsc.dao.base.EqEqmtTagDataStoreDAO;

@Service
public class EqEqmtTagDataStoreService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	EqEqmtTagDataStoreDAO eqEqmtTagDataStoreDAO;

	public int insertEqEqmtTagDataStore(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertEqEqmtTagDataStore:"+inMap);
		return eqEqmtTagDataStoreDAO.insertEqEqmtTagDataStore(inMap);
	}

	public int deleteEqEqmtTagDataStore(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteEqEqmtTagDataStore:"+inMap);
		return eqEqmtTagDataStoreDAO.deleteEqEqmtTagDataStore(inMap);
	}

	public int updateEqEqmtTagDataStore(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateEqEqmtTagDataStore:"+inMap);
		return eqEqmtTagDataStoreDAO.updateEqEqmtTagDataStore(inMap);
	}

	public List<Map> listEqEqmtTagDataStore_rise_time(Map inMap){
		logger.debug("getEqEqmtTagDataStore_rise_time:"+inMap);
		return eqEqmtTagDataStoreDAO.listEqEqmtTagDataStore_rise_time(inMap);
	}

	public Map getEqEqmtTagDataStore_meeq_id(Map inMap){
		logger.debug("getEqEqmtTagDataStore_meeq_id:"+inMap);
		return eqEqmtTagDataStoreDAO.getEqEqmtTagDataStore_meeq_id(inMap);
	}

	public List<Map> listEqEqmtTagDataStore(Map inMap){
		logger.debug("listEqEqmtTagDataStore:"+inMap);
		return eqEqmtTagDataStoreDAO.listEqEqmtTagDataStore(inMap);
	}

	public List<Map> pageEqEqmtTagDataStore(Map inMap){
		logger.debug("pageEqEqmtTagDataStore:"+inMap);
		return eqEqmtTagDataStoreDAO.pageEqEqmtTagDataStore(inMap);
	}

	public int countEqEqmtTagDataStore(Map inMap){
		logger.debug("countEqEqmtTagDataStore:"+inMap);
		return eqEqmtTagDataStoreDAO.countEqEqmtTagDataStore(inMap);
	}

}