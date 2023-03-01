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
import com.kies.sgsc.dao.base.SendRefugeDAO;

@Service
public class SendRefugeService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	SendRefugeDAO sendRefugeDAO;

	public int insertSendRefuge(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertSendRefuge:"+inMap);
		return sendRefugeDAO.insertSendRefuge(inMap);
	}

	public int deleteSendRefuge(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteSendRefuge:"+inMap);
		return sendRefugeDAO.deleteSendRefuge(inMap);
	}

	public int updateSendRefuge(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateSendRefuge:"+inMap);
		return sendRefugeDAO.updateSendRefuge(inMap);
	}

	public List<Map> listSendRefuge_user_id(Map inMap){
		logger.debug("getSendRefuge_user_id:"+inMap);
		return sendRefugeDAO.listSendRefuge_user_id(inMap);
	}

	public List<Map> listSendRefuge_send_rnd(Map inMap){
		logger.debug("getSendRefuge_send_rnd:"+inMap);
		return sendRefugeDAO.listSendRefuge_send_rnd(inMap);
	}

	public Map getSendRefuge_send_sid(Map inMap){
		logger.debug("getSendRefuge_send_sid:"+inMap);
		return sendRefugeDAO.getSendRefuge_send_sid(inMap);
	}

	public List<Map> listSendRefuge(Map inMap){
		logger.debug("listSendRefuge:"+inMap);
		return sendRefugeDAO.listSendRefuge(inMap);
	}

	public List<Map> pageSendRefuge(Map inMap){
		logger.debug("pageSendRefuge:"+inMap);
		return sendRefugeDAO.pageSendRefuge(inMap);
	}

	public int countSendRefuge(Map inMap){
		logger.debug("countSendRefuge:"+inMap);
		return sendRefugeDAO.countSendRefuge(inMap);
	}

}