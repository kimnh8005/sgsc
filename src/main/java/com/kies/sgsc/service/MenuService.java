package com.kies.sgsc.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kies.sgsc.comm.util.Utils;
import com.kies.sgsc.dao.base.MenuDAO;

@Service
public class MenuService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	MenuDAO menuDAO;

	public int insertMenu(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertMenu:"+inMap);
		return menuDAO.insertMenu(inMap);
	}

	public String getMenuKey() {
		return menuDAO.getMenuKey();
	}

	public int deleteMenu(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteMenu:"+inMap);
		return menuDAO.deleteMenu(inMap);
	}

	public int updateMenu(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateMenu:"+inMap);
		return menuDAO.updateMenu(inMap);
	}

	public Map getMenu_menu_sid(Map inMap){
		logger.debug("getMenu_menu_sid:"+inMap);
		return menuDAO.getMenu_menu_sid(inMap);
	}

	public List<Map> listMenu(Map inMap){
		logger.debug("listMenu:"+inMap);
		return menuDAO.listMenu(inMap);
	}

	public List<Map> pageMenu(Map inMap){
		logger.debug("pageMenu:"+inMap);
		return menuDAO.pageMenu(inMap);
	}

	public int countMenu(Map inMap){
		logger.debug("countMenu:"+inMap);
		return menuDAO.countMenu(inMap);
	}

}