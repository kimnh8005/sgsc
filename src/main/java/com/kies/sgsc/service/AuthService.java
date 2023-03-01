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
import com.kies.sgsc.dao.base.AuthDAO;

@Service
public class AuthService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	AuthDAO authDAO;

	public int insertAuth(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertAuth:"+inMap);
		return authDAO.insertAuth(inMap);
	}

	public String getAuthKey() {
		return authDAO.getAuthKey();
	}

	public int deleteAuth(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteAuth:"+inMap);
		return authDAO.deleteAuth(inMap);
	}

	public int updateAuth(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateAuth:"+inMap);
		return authDAO.updateAuth(inMap);
	}

	public Map getAuth_auth_sid(Map inMap){
		logger.debug("getAuth_auth_sid:"+inMap);
		return authDAO.getAuth_auth_sid(inMap);
	}

	public List<Map> listAuth(Map inMap){
		logger.debug("listAuth:"+inMap);
		return authDAO.listAuth(inMap);
	}

	public List<Map> pageAuth(Map inMap){
		logger.debug("pageAuth:"+inMap);
		return authDAO.pageAuth(inMap);
	}

	public int countAuth(Map inMap){
		logger.debug("countAuth:"+inMap);
		return authDAO.countAuth(inMap);
	}

}