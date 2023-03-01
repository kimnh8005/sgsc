package com.kies.sgsc.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kies.sgsc.comm.util.Utils;
import com.kies.sgsc.dao.base.UserDAO;;

@Service
public class UserService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	UserDAO userDAO;

	public int insertUser(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertUser:"+inMap);
		return userDAO.insertUser(inMap);
	}

	public String getUserKey() { 
		return userDAO.getUserKey();
	}

	public int deleteUser(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteUser:"+inMap);
		return userDAO.deleteUser(inMap);
	}

	public int updateUser(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateUser:"+inMap);
		return userDAO.updateUser(inMap);
	}
	
	public int updateUserPassword(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateUserPassword:"+inMap);
		return userDAO.updateUserPassword(inMap);
	}
	
	public int updateUsertToken(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateUsertToken:"+inMap);
		return userDAO.updateUsertToken(inMap);
	}
	
	

	public Map getUser_user_id(Map inMap){
		logger.debug("getUser_user_id:"+inMap);
		return userDAO.getUser_user_id(inMap);
	}

	public List<Map> listUser(Map inMap){
		logger.debug("listUser:"+inMap);
		return userDAO.listUser(inMap);
	}

	public List<Map> pageUser(Map inMap){
		logger.debug("pageUser:"+inMap);
		return userDAO.pageUser(inMap);
	}

	public int countUser(Map inMap){
		logger.debug("countUser:"+inMap);
		return userDAO.countUser(inMap);
	}
	
	public List<Map> listUserDevice(Map inMap){
		logger.debug("listUserDevice:"+inMap);
		return userDAO.listUserDevice(inMap);
	}

}