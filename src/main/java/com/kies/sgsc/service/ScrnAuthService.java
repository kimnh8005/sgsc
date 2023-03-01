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
import com.kies.sgsc.dao.base.ScrnAuthDAO;

@Service
public class ScrnAuthService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	ScrnAuthDAO scrnAuthDAO;

	public int insertScrnAuth(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertScrnAuth:"+inMap);
		return scrnAuthDAO.insertScrnAuth(inMap);
	}

	public String getScrnAuthKey() {
		return scrnAuthDAO.getScrnAuthKey();
	}

	public int deleteScrnAuth(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteScrnAuth:"+inMap);
		return scrnAuthDAO.deleteScrnAuth(inMap);
	}

	public int updateScrnAuth(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateScrnAuth:"+inMap);
		return scrnAuthDAO.updateScrnAuth(inMap);
	}

	public Map getScrnAuth_scrn_id(Map inMap){
		logger.debug("getScrnAuth_scrn_id:"+inMap);
		return scrnAuthDAO.getScrnAuth_scrn_id(inMap);
	}

	public List<Map> listScrnAuth(Map inMap){
		logger.debug("listScrnAuth:"+inMap);
		return scrnAuthDAO.listScrnAuth(inMap);
	}

	public List<Map> pageScrnAuth(Map inMap){
		logger.debug("pageScrnAuth:"+inMap);
		return scrnAuthDAO.pageScrnAuth(inMap);
	}

	public int countScrnAuth(Map inMap){
		logger.debug("countScrnAuth:"+inMap);
		return scrnAuthDAO.countScrnAuth(inMap);
	}

}