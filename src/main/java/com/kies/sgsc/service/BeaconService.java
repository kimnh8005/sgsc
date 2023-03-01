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
import com.kies.sgsc.dao.base.BeaconDAO;

@Service
public class BeaconService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	BeaconDAO BeaconDAO;

	public int insertBeaconInfo(Map<String, String> inMap){
		logger.debug("insertBeaconInfo:"+inMap);
		return BeaconDAO.insertBeaconInfo(inMap);
	}

}