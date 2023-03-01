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
import com.kies.sgsc.dao.base.NoticeDAO;

@Service
public class NoticeService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	NoticeDAO noticeDAO;

	public int insertNotice(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertNotice:"+inMap);
		return noticeDAO.insertNotice(inMap);
	}

	public String getNoticeKey() {
		return noticeDAO.getNoticeKey();
	}

	public int deleteNotice(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteNotice:"+inMap);
		return noticeDAO.deleteNotice(inMap);
	}

	public int updateNotice(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateNotice:"+inMap);
		return noticeDAO.updateNotice(inMap);
	}

	public Map getNotice_noti_sid(Map inMap){
		logger.debug("getNotice_noti_sid:"+inMap);
		return noticeDAO.getNotice_noti_sid(inMap);
	}
	
	public Map getNextNotice_noti_sid(Map inMap){
		logger.debug("getNextNotice_noti_sid:"+inMap);
		return noticeDAO.getNextNotice_noti_sid(inMap);
	}
	
	public Map getPrevNotice_noti_sid(Map inMap){
		logger.debug("getPrevNotice_noti_sid:"+inMap);
		return noticeDAO.getPrevNotice_noti_sid(inMap);
	}

	public List<Map> listNotice(Map inMap){
		logger.debug("listNotice:"+inMap);
		return noticeDAO.listNotice(inMap);
	}

	public List<Map> pageNotice(Map inMap){
		logger.debug("pageNotice:"+inMap);
		return noticeDAO.pageNotice(inMap);
	}

	public int countNotice(Map inMap){
		logger.debug("countNotice:"+inMap);
		return noticeDAO.countNotice(inMap);
	}

}