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
import com.kies.sgsc.dao.base.CommonCodeDAO;

@Service
public class CommonCodeService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	CommonCodeDAO commonCodeDAO;

	public int insertCommonCode(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertCommonCode:"+inMap);
		return commonCodeDAO.insertCommonCode(inMap);
	}

	public int deleteCommonCode(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteCommonCode:"+inMap);
		return commonCodeDAO.deleteCommonCode(inMap);
	}

	public int updateCommonCode(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateCommonCode:"+inMap);
		return commonCodeDAO.updateCommonCode(inMap);
	}

	public List<Map> listCommonCode_cd_grp_id(Map inMap){
		logger.debug("getCommonCode_cd_grp_id:"+inMap);
		return commonCodeDAO.listCommonCode_cd_grp_id(inMap);
	}

	public Map getCommonCode_cd_id(Map inMap){
		logger.debug("getCommonCode_cd_id:"+inMap);
		return commonCodeDAO.getCommonCode_cd_id(inMap);
	}

	public List<Map> listCommonCode(Map inMap){
		logger.debug("listCommonCode:"+inMap);
		return commonCodeDAO.listCommonCode(inMap);
	}

	public List<Map> pageCommonCode(Map inMap){
		logger.debug("pageCommonCode:"+inMap);
		return commonCodeDAO.pageCommonCode(inMap);
	}

	public int countCommonCode(Map inMap){
		logger.debug("countCommonCode:"+inMap);
		return commonCodeDAO.countCommonCode(inMap);
	}

}