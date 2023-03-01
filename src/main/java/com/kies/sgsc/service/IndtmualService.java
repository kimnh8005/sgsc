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
import com.kies.sgsc.dao.base.IndtmualDAO;

@Service
public class IndtmualService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	IndtmualDAO indtmualDAO;

	public int insertIndtmual(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertIndtmual:"+inMap);
		return indtmualDAO.insertIndtmual(inMap);
	}

	public String getIndtmualKey() {
		return indtmualDAO.getIndtmualKey();
	}

	public int deleteIndtmual(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteIndtmual:"+inMap);
		return indtmualDAO.deleteIndtmual(inMap);
	}

	public int updateIndtmual(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateIndtmual:"+inMap);
		return indtmualDAO.updateIndtmual(inMap);
	}

	public Map getIndtmual_mual_sid(Map inMap){
		logger.debug("getIndtmual_mual_sid:"+inMap);
		return indtmualDAO.getIndtmual_mual_sid(inMap);
	}
	
	public Map getPrevIndtmual_mual_sid(Map inMap){
		logger.debug("getPrevIndtmual_mual_sid:"+inMap);
		return indtmualDAO.getPrevIndtmual_mual_sid(inMap);
	}
	
	public Map getNextIndtmual_mual_sid(Map inMap){
		logger.debug("getNextIndtmual_mual_sid:"+inMap);
		return indtmualDAO.getNextIndtmual_mual_sid(inMap);
	}

	public List<Map> listIndtmual(Map inMap){
		logger.debug("listIndtmual:"+inMap);
		return indtmualDAO.listIndtmual(inMap);
	}

	public List<Map> pageIndtmual(Map inMap){
		logger.debug("pageIndtmual:"+inMap);
		return indtmualDAO.pageIndtmual(inMap);
	}

	public int countIndtmual(Map inMap){
		logger.debug("countIndtmual:"+inMap);
		return indtmualDAO.countIndtmual(inMap);
	}

}