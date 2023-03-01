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
import com.kies.sgsc.dao.base.SequenceDAO;

@Service
public class SequenceService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	SequenceDAO sequenceDAO;

	public int insertSequence(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertSequence:"+inMap);
		return sequenceDAO.insertSequence(inMap);
	}

	public String getSequenceKey() {
		return sequenceDAO.getSequenceKey();
	}

	public int deleteSequence(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteSequence:"+inMap);
		return sequenceDAO.deleteSequence(inMap);
	}

	public int updateSequence(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateSequence:"+inMap);
		return sequenceDAO.updateSequence(inMap);
	}

	public Map getSequence_seq_id(Map inMap){
		logger.debug("getSequence_seq_id:"+inMap);
		return sequenceDAO.getSequence_seq_id(inMap);
	}

	public List<Map> listSequence(Map inMap){
		logger.debug("listSequence:"+inMap);
		return sequenceDAO.listSequence(inMap);
	}

	public List<Map> pageSequence(Map inMap){
		logger.debug("pageSequence:"+inMap);
		return sequenceDAO.pageSequence(inMap);
	}

	public int countSequence(Map inMap){
		logger.debug("countSequence:"+inMap);
		return sequenceDAO.countSequence(inMap);
	}

}