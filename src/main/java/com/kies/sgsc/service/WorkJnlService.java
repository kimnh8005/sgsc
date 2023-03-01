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
import com.kies.sgsc.dao.base.WorkJnlDAO;

@Service
public class WorkJnlService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	WorkJnlDAO workJnlDAO;

	public int insertWorkJnl(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertWorkJnl:"+inMap);
		return workJnlDAO.insertWorkJnl(inMap);
	}

	public String getWorkJnlKey() {
		return workJnlDAO.getWorkJnlKey();
	}

	public int deleteWorkJnl(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteWorkJnl:"+inMap);
		return workJnlDAO.deleteWorkJnl(inMap);
	}

	public int updateWorkJnl(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateWorkJnl:"+inMap);
		return workJnlDAO.updateWorkJnl(inMap);
	}

	public Map getWorkJnl_jnl_sid(Map inMap){
		logger.debug("getWorkJnl_jnl_sid:"+inMap);
		return workJnlDAO.getWorkJnl_jnl_sid(inMap);
	}

	public List<Map> listWorkJnl(Map inMap){
		logger.debug("listWorkJnl:"+inMap);
		return workJnlDAO.listWorkJnl(inMap);
	}

	public List<Map> pageWorkJnl(Map inMap){
		logger.debug("pageWorkJnl:"+inMap);
		return workJnlDAO.pageWorkJnl(inMap);
	}

	public int countWorkJnl(Map inMap){
		logger.debug("countWorkJnl:"+inMap);
		return workJnlDAO.countWorkJnl(inMap);
	}

}