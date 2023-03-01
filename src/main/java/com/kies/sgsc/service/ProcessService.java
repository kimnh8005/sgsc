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
import com.kies.sgsc.dao.base.ProcessDAO;

@Service
public class ProcessService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	ProcessDAO processDAO;

	public int insertProcess(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertProcess:"+inMap);
		return processDAO.insertProcess(inMap);
	}

	public String getProcessKey() {
		return processDAO.getProcessKey();
	}

	public int deleteProcess(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteProcess:"+inMap);
		return processDAO.deleteProcess(inMap);
	}

	public int updateProcess(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateProcess:"+inMap);
		return processDAO.updateProcess(inMap);
	}

	public Map getProcess_procs_id(Map inMap){
		logger.debug("getProcess_procs_id:"+inMap);
		return processDAO.getProcess_procs_id(inMap);
	}

	public List<Map> listProcess(Map inMap){
		logger.debug("listProcess:"+inMap);
		return processDAO.listProcess(inMap);
	}

	public List<Map> pageProcess(Map inMap){
		logger.debug("pageProcess:"+inMap);
		return processDAO.pageProcess(inMap);
	}

	public int countProcess(Map inMap){
		logger.debug("countProcess:"+inMap);
		return processDAO.countProcess(inMap);
	}

}