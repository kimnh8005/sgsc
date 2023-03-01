package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface NoticeDAO {

	public int insertNotice(Map inMap);
	public String getNoticeKey();
	public int deleteNotice(Map inMap);
	public int updateNotice(Map inMap);
	public Map getNotice_noti_sid(Map inMap);
	public Map getNextNotice_noti_sid(Map inMap);
	public Map getPrevNotice_noti_sid(Map inMap);
	public List<Map> listNotice(Map inMap);
	public List<Map> pageNotice(Map inMap);
	public int countNotice(Map inMap);
}