package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface MenuDAO {

	public int insertMenu(Map inMap);
	public String getMenuKey();
	public int deleteMenu(Map inMap);
	public int updateMenu(Map inMap);
	public Map getMenu_menu_sid(Map inMap);
	public List<Map> listMenu(Map inMap);
	public List<Map> pageMenu(Map inMap);
	public int countMenu(Map inMap);
}