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
import com.kies.sgsc.dao.base.EmeryContactDAO;

@Service
public class EmeryContactService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	EmeryContactDAO emeryContactDAO;

	public int insertEmeryContact(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertEmeryContact:"+inMap);
		return emeryContactDAO.insertEmeryContact(inMap);
	}

	public String getEmeryContactKey() {
		return emeryContactDAO.getEmeryContactKey();
	}

	public int deleteEmeryContact(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteEmeryContact:"+inMap);
		return emeryContactDAO.deleteEmeryContact(inMap);
	}

	public int updateEmeryContact(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateEmeryContact:"+inMap);
		return emeryContactDAO.updateEmeryContact(inMap);
	}

	public Map getEmeryContact_emct_sid(Map inMap){
		logger.debug("getEmeryContact_emct_sid:"+inMap);
		return emeryContactDAO.getEmeryContact_emct_sid(inMap);
	}

	public List<Map> listEmeryContact(Map inMap){
		logger.debug("listEmeryContact:"+inMap);
		return emeryContactDAO.listEmeryContact(inMap);
	}

	public List<Map> pageEmeryContact(Map inMap){
		logger.debug("pageEmeryContact:"+inMap);
		return emeryContactDAO.pageEmeryContact(inMap);
	}

	public int countEmeryContact(Map inMap){
		logger.debug("countEmeryContact:"+inMap);
		return emeryContactDAO.countEmeryContact(inMap);
	}

}