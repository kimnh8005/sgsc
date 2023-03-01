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
import com.kies.sgsc.dao.base.NotiFileDAO;

@Service
public class NotiFileService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	NotiFileDAO notiFileDAO;

	public int insertNotiFile(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertNotiFile:"+inMap);
		return notiFileDAO.insertNotiFile(inMap);
	}

	public int deleteNotiFile(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteNotiFile:"+inMap);
		return notiFileDAO.deleteNotiFile(inMap);
	}

	public int updateNotiFile(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateNotiFile:"+inMap);
		return notiFileDAO.updateNotiFile(inMap);
	}

	public List<Map> listNotiFile_file_seq(Map inMap){
		logger.debug("getNotiFile_file_seq:"+inMap);
		return notiFileDAO.listNotiFile_file_seq(inMap);
	}

	public Map getNotiFile_noti_id(Map inMap){
		logger.debug("getNotiFile_noti_id:"+inMap);
		return notiFileDAO.getNotiFile_noti_id(inMap);
	}

	public List<Map> listNotiFile(Map inMap){
		logger.debug("listNotiFile:"+inMap);
		return notiFileDAO.listNotiFile(inMap);
	}

	public List<Map> pageNotiFile(Map inMap){
		logger.debug("pageNotiFile:"+inMap);
		return notiFileDAO.pageNotiFile(inMap);
	}

	public int countNotiFile(Map inMap){
		logger.debug("countNotiFile:"+inMap);
		return notiFileDAO.countNotiFile(inMap);
	}

}