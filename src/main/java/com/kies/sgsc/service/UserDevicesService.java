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
import com.kies.sgsc.dao.base.UserDevicesDAO;

@Service
public class UserDevicesService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	UserDevicesDAO userDevicesDAO;

	public int insertUserDevices(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertUserDevices:"+inMap);
		return userDevicesDAO.insertUserDevices(inMap);
	}

	public String getUserDevicesKey() {
		return userDevicesDAO.getUserDevicesKey();
	}

	public int deleteUserDevices(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteUserDevices:"+inMap);
		return userDevicesDAO.deleteUserDevices(inMap);
	}

	public int updateUserDevices(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateUserDevices:"+inMap);
		return userDevicesDAO.updateUserDevices(inMap);
	}

	public Map getUserDevices_user_id(Map inMap){
		logger.debug("getUserDevices_user_id:"+inMap);
		return userDevicesDAO.getUserDevices_user_id(inMap);
	}

	public List<Map> listUserDevices(Map inMap){
		logger.debug("listUserDevices:"+inMap);
		return userDevicesDAO.listUserDevices(inMap);
	}

	public List<Map> pageUserDevices(Map inMap){
		logger.debug("pageUserDevices:"+inMap);
		return userDevicesDAO.pageUserDevices(inMap);
	}

	public int countUserDevices(Map inMap){
		logger.debug("countUserDevices:"+inMap);
		return userDevicesDAO.countUserDevices(inMap);
	}

}