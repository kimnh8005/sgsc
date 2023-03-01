package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface UserDAO {

	public int insertUser(Map inMap);
	public String getUserKey();
	public int deleteUser(Map inMap);
	public int updateUser(Map inMap);
	public int updateUserPassword(Map inMap);
	public int updateUsertToken(Map inMap);
	public Map getUser_user_id(Map inMap);
	public List<Map> listUser(Map inMap);
	public List<Map> pageUser(Map inMap);
	public int countUser(Map inMap);
	
	public List<Map> listUserDevice(Map inMap);
}