package com.kies.sgsc.service;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kies.sgsc.dao.base.EqmtCtrlDAO;


@Service
public class EqmtCtrlService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	EqmtCtrlDAO eqmtCtrlDAO;

	public int insertEqmtCtrlHistory(Map inMap){
		logger.debug("insertEqmtCtrlHistory:"+inMap);
		return eqmtCtrlDAO.insertEqmtCtrlHistory(inMap);
	}
	
	public List<Map> getEqmtCtrlStatusHistory() {
		return eqmtCtrlDAO.getEqmtCtrlStatusHistory();
	}

}