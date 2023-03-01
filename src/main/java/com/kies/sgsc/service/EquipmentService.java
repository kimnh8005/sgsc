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
import com.kies.sgsc.dao.base.EquipmentDAO;

@Service
public class EquipmentService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	EquipmentDAO equipmentDAO;

	public int insertEquipment(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertEquipment:"+inMap);
		return equipmentDAO.insertEquipment(inMap);
	}

	public String getEquipmentKey() {
		return equipmentDAO.getEquipmentKey();
	}

	public int deleteEquipment(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteEquipment:"+inMap);
		return equipmentDAO.deleteEquipment(inMap);
	}

	public int updateEquipment(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateEquipment:"+inMap);
		return equipmentDAO.updateEquipment(inMap);
	}

	public Map getEquipment_eqmt_id(Map inMap){
		logger.debug("getEquipment_eqmt_id:"+inMap);
		return equipmentDAO.getEquipment_eqmt_id(inMap);
	}

	public List<Map> listEquipment(Map inMap){
		logger.debug("listEquipment:"+inMap);
		return equipmentDAO.listEquipment(inMap);
	}

	public List<Map> pageEquipment(Map inMap){
		logger.debug("pageEquipment:"+inMap);
		return equipmentDAO.pageEquipment(inMap);
	}

	public int countEquipment(Map inMap){
		logger.debug("countEquipment:"+inMap);
		return equipmentDAO.countEquipment(inMap);
	}
	

	public  List<Map> getEquipment_all_name(){
		return equipmentDAO.getEquipment_all_name();
	}

}