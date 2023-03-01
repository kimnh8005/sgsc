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
import com.kies.sgsc.dao.base.FacilityDAO;

@Service
public class FacilityService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	FacilityDAO facilityDAO;

	public int insertFacility(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertFacility:"+inMap);
		return facilityDAO.insertFacility(inMap);
	}

	public String getFacilityKey() {
		return facilityDAO.getFacilityKey();
	}

	public int deleteFacility(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteFacility:"+inMap);
		return facilityDAO.deleteFacility(inMap);
	}

	public int updateFacility(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateFacility:"+inMap);
		return facilityDAO.updateFacility(inMap);
	}

	public Map getFacility_facty_id(Map inMap){
		logger.debug("getFacility_facty_id:"+inMap);
		return facilityDAO.getFacility_facty_id(inMap);
	}

	public List<Map> listFacility(Map inMap){
		logger.debug("listFacility:"+inMap);
		return facilityDAO.listFacility(inMap);
	}

	public List<Map> pageFacility(Map inMap){
		logger.debug("pageFacility:"+inMap);
		return facilityDAO.pageFacility(inMap);
	}

	public int countFacility(Map inMap){
		logger.debug("countFacility:"+inMap);
		return facilityDAO.countFacility(inMap);
	}

}