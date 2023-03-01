package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface EquipmentDAO {

	public int insertEquipment(Map inMap);
	public String getEquipmentKey();
	public int deleteEquipment(Map inMap);
	public int updateEquipment(Map inMap);
	public Map getEquipment_eqmt_id(Map inMap);
	public List<Map> listEquipment(Map inMap);
	public List<Map> pageEquipment(Map inMap);
	public int countEquipment(Map inMap);
	public List<Map> getEquipment_all_name();
}