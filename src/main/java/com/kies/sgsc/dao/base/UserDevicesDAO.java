package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface UserDevicesDAO {

	public int insertUserDevices(Map inMap);
	public String getUserDevicesKey();
	public int deleteUserDevices(Map inMap);
	public int updateUserDevices(Map inMap);
	public Map getUserDevices_user_id(Map inMap);
	public List<Map> listUserDevices(Map inMap);
	public List<Map> pageUserDevices(Map inMap);
	public int countUserDevices(Map inMap);
}